package guuber.cmu.edu.activities.driver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.dbLayout.MessageDBController;
import guuber.cmu.edu.entities.Message;
import guuber.cmu.edu.messageConst.ActivityNames;
import guuber.cmu.edu.messageConst.MessageKind;
import guuber.cmu.edu.resultCode.ResultCode;
import guuber.cmu.edu.service.GuuberService;

public class StartServiceActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private Marker myMarker;
    private EditText messageInput;
    private TextView messageHistory;

    private Double currLat;
    private Double currLon;

    private MessageDBController meassageDBController;

    private Map<String, String> allMessages;
    private Map<String, Marker> passengerMarkers;

    private String currentPassenger;

    private ResultReceiver resultReceiver;

    private boolean serviceStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_activity_start_service);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        serviceStarted = false;

        try {
            // Acquire a reference to the system Location Manager
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            // Define a listener that responds to location updates
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    // Called when a new location is found by the network location provider.
                    updateLocation(location);
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                    try {
                        updateLocation(locationManager.getLastKnownLocation(provider));
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }

                public void onProviderEnabled(String provider) {}

                public void onProviderDisabled(String provider) {}
            };

            // Register the listener with the Location Manager to receive location updates
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location loc = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            updateLocation(loc);

        } catch (SecurityException e) {
            e.printStackTrace();
        }

        Button startButton =
                (Button) findViewById(R.id.driver_start_startButton);
        startButton.setOnClickListener(startButtonClicked);

        Button cancelButton =
                (Button) findViewById(R.id.driver_start_cancelButton);
        cancelButton.setOnClickListener(cancelButtonClicked);

        Button sendButton =
                (Button) findViewById(R.id.driver_start_sendButton);
        sendButton.setOnClickListener(sendButtonClicked);
        messageInput = (EditText) findViewById(R.id.driver_start_input);
        messageHistory = (TextView) findViewById(R.id.driver_start_history);
        messageHistory.setMovementMethod(new ScrollingMovementMethod());

        meassageDBController = new MessageDBController(this);

        allMessages = new HashMap<String, String>();
        passengerMarkers = new HashMap<String, Marker>();

        resultReceiver = new DriverStartResultReceiver(null);
    }

    public void updateLocation(Location location) {
        if (location != null && mMap != null) {
            double lon = location.getLongitude();
            double lat = location.getLatitude();

            if (currLat != null && Math.abs(currLat - lat) < 0.00001
                    && Math.abs(lon - currLon) < 0.00001){
                return;
            } else {
                currLat = lat;
                currLon = lon;
            }

            if (myMarker != null) {
                myMarker.remove();
            }

            LatLng sydney = new LatLng(lat, lon);
            myMarker = mMap.addMarker(new MarkerOptions().position(sydney).title("My position"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            Intent intent = new Intent(this, GuuberService.class);
            intent.putExtra("operation", MessageKind.SENDMESSAGE);
            intent.putExtra("message", MessageKind.DRIVERLOC + ":" + lon + ":" + lat);
            intent.putExtra("receiver", resultReceiver);
            intent.putExtra("activityName", ActivityNames.DRIVERSTART);
            intent.putExtra("resultCode", ResultCode.DRIVERLOC);
            startService(intent);
            /*
            addPassengerMarker("1", lon + 0.005, lat);
            addPassengerMarker("2", lon, lat + 0.005);
            addPassengerMarker("3", lon - 0.005, lat);
            addPassengerMarker("4", lon, lat - 0.005);
            addPassengerMarker("5", lon + 0.005, lat + 0.005);
            addPassengerMarker("6", lon - 0.005, lat - 0.005);
            addPassengerMarker("7", lon - 0.005, lat + 0.005);
            addPassengerMarker("8", lon + 0.005, lat - 0.005);*/
        } else {
        }

    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        try {
            Location loc = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            updateLocation(loc);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        mMap.setOnMarkerClickListener(this);
    }

    View.OnClickListener sendButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (messageInput.getText().length() == 0) {
                return;
            }
            String history = messageHistory.getText().toString();
            String current = messageInput.getText().toString();
            messageInput.setText("");
            messageHistory.setText(history + "\n" + "Me: " + current);
            scollToBottom();
            String senderid = "me";
            String receiverid = "other";
            Message message = new Message(senderid, receiverid, current, new Date().toString());
            meassageDBController.insertMessage(message);
        }
    };

    private void scollToBottom() {
        int scrollAmount = messageHistory.getLayout().getLineTop(messageHistory.getLineCount())
                - messageHistory.getHeight();
        if (scrollAmount > 0) {
            messageHistory.scrollTo(0, scrollAmount);
        }
        else {
            messageHistory.scrollTo(0, 0);
        }
    }

    View.OnClickListener startButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (serviceStarted) {
                return;
            }
            serviceStarted = true;
            Intent mess = new Intent(StartServiceActivity.this, GuuberService.class);
            mess.putExtra("operation", MessageKind.SENDMESSAGE);
            mess.putExtra("message", MessageKind.DRIVEREXIT);
            mess.putExtra("receiver", resultReceiver);
            mess.putExtra("activityName", ActivityNames.DRIVERSTART);
            mess.putExtra("resultCode", ResultCode.DRIVEREXIT);
            startService(mess);

            Intent mess2 = new Intent(StartServiceActivity.this, GuuberService.class);
            mess2.putExtra("operation", MessageKind.SENDMESSAGE);
            mess2.putExtra("message", MessageKind.STARTRIDE + ":" + currentPassenger);
            mess2.putExtra("receiver", resultReceiver);
            mess2.putExtra("activityName", ActivityNames.DRIVERSTART);
            mess2.putExtra("resultCode", ResultCode.STARTRIDE);
            startService(mess2);
        }
    };

    View.OnClickListener cancelButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent mess = new Intent(StartServiceActivity.this, GuuberService.class);
            mess.putExtra("operation", MessageKind.SENDMESSAGE);
            mess.putExtra("message", MessageKind.DRIVEREXIT);
            mess.putExtra("receiver", resultReceiver);
            mess.putExtra("activityName", ActivityNames.DRIVERSTART);
            mess.putExtra("resultCode", ResultCode.DRIVEREXIT);
            startService(mess);

            Intent intent = new Intent(StartServiceActivity.this, FindPassengerActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    };

    private void addPassengerMarker(String passengerID, double lon, double lat) {
        if (passengerMarkers.get(passengerID) != null) {
            passengerMarkers.get(passengerID).remove();
        }
        LatLng place = new LatLng(lat, lon);
        Marker passengerMarker = mMap.addMarker(new MarkerOptions().position(place).title(passengerID + " position"));
        passengerMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        passengerMarkers.put(passengerID, passengerMarker);
    }

    private void removePassengerMarker(String passengerID) {
        if (passengerMarkers.get(passengerID) != null) {
            passengerMarkers.get(passengerID).remove();
        }
        passengerMarkers.remove(passengerID);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (serviceStarted) {
            return false;
        }
        for (String passenger : passengerMarkers.keySet()) {
            if (passengerMarkers.get(passenger).equals(marker)) {
                String history = messageHistory.getText().toString();
                if (history.length() > 0 && currentPassenger != null) {
                    allMessages.put(currentPassenger, history);
                }
                currentPassenger = passenger;
                if (allMessages.get(passenger) != null) {
                    messageHistory.setText(allMessages.get(passenger));
                    scollToBottom();
                } else {
                    messageHistory.setText("Chatting with passenger " + passenger);
                }
                break;
            }
        }
        return false;
    }

    @SuppressLint("ParcelCreator")
    public class DriverStartResultReceiver extends ResultReceiver {

        public DriverStartResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if(resultCode == ResultCode.PASSENGERLOC) {
                String response = resultData.getString("response");
                String[] splits = response.split(":");
                final String passenger = splits[1];
                final Double lon = Double.parseDouble(splits[2]);
                final Double lat = Double.parseDouble(splits[3]);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addPassengerMarker(passenger, lon, lat);
                    }
                });
            } else if (resultCode == ResultCode.PASSENGEREXIT) {
                String response = resultData.getString("response");
                String[] splits = response.split(":");
                final String passenger = splits[1];
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        removePassengerMarker(passenger);
                    }
                });
            } else if (resultCode == ResultCode.PASSENGERDEST) {
                String response = resultData.getString("response");
                String[] splits = response.split(":");
                final Double lon = Double.parseDouble(splits[1]);
                final Double lat = Double.parseDouble(splits[2]);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(StartServiceActivity.this, EndServiceActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("passenger", currentPassenger);
                        intent.putExtra("destLon", lon);
                        intent.putExtra("destLat", lat);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }
    }
}
