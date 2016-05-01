package guuber.cmu.edu.activities.passenger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import edu.cmu.guuber.guuber.R;

import guuber.cmu.edu.dbLayout.MessageDBController;
import guuber.cmu.edu.entities.Message;
import guuber.cmu.edu.messageConst.ActivityNames;
import guuber.cmu.edu.messageConst.ClientMessageKind;
import guuber.cmu.edu.messageConst.Operation;
import guuber.cmu.edu.messageConst.ServerMessageKind;
import guuber.cmu.edu.resultCode.ResultCode;
import guuber.cmu.edu.service.GuuberService;

public class StartServiceActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private Marker myMarker;
    private Marker destMarker;

    private EditText messageInput;
    private TextView messageHistory;

    private Double currLat;
    private Double currLon;

    private Double destLat;
    private Double destLon;

    private EditText destinationInput;

    private MessageDBController meassageDBController;

    private Map<String, String> allMessages;
    private Map<String, Marker> driverMarkers;

    private String currentDriver;

    private String myName;

    private ResultReceiver resultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_activity_start_service);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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

        Button cancelButton =
                (Button) findViewById(R.id.passenger_start_cancelButton);
        cancelButton.setOnClickListener(cancelButtonClicked);

        Button sendButton =
                (Button) findViewById(R.id.passenger_start_sendButton);
        sendButton.setOnClickListener(sendButtonClicked);

        Button submitButton =
                (Button) findViewById(R.id.passenger_start_submitButton);
        submitButton.setOnClickListener(submitButtonClicked);

        messageInput = (EditText) findViewById(R.id.passenger_start_input);
        destinationInput = (EditText) findViewById(R.id.passenger_destination);
        messageHistory = (TextView) findViewById(R.id.passenger_start_history);
        messageHistory.setMovementMethod(new ScrollingMovementMethod());

        meassageDBController = new MessageDBController(this);

        allMessages = new HashMap<String, String>();
        driverMarkers = new HashMap<String, Marker>();

        resultReceiver = new PassengerStartResultReceiver(null);

        Intent parameters = getIntent();
        myName = parameters.getStringExtra("username");

        Intent intent = new Intent(this, GuuberService.class);
        intent.putExtra("operation", Operation.SENDMESSAGE);
        intent.putExtra("message", ServerMessageKind.PASSENGERREQUESTLOC);
        intent.putExtra("receiver", resultReceiver);
        intent.putExtra("activityName", ActivityNames.PASSENGERSTARTSERVICEACTIVITY);
        startService(intent);
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
            //marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            Intent intent = new Intent(this, GuuberService.class);
            intent.putExtra("operation", Operation.SENDMESSAGE);
            intent.putExtra("message", ServerMessageKind.PASSENGERLOC + ":" + lon + ":" + lat);
            intent.putExtra("receiver", resultReceiver);
            intent.putExtra("activityName", ActivityNames.PASSENGERSTARTSERVICEACTIVITY);
            startService(intent);
            /*
            addDriverMarker("1", lon + 0.005, lat);
            addDriverMarker("2", lon, lat + 0.005);
            addDriverMarker("3", lon - 0.005, lat);
            addDriverMarker("4", lon, lat - 0.005);
            addDriverMarker("5", lon + 0.005, lat + 0.005);
            addDriverMarker("6", lon - 0.005, lat - 0.005);
            addDriverMarker("7", lon - 0.005, lat + 0.005);
            addDriverMarker("8", lon + 0.005, lat - 0.005);*/
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

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(37, -122);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Mountain View"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
            String senderid = myName;
            String receiverid = currentDriver;

            Intent mess = new Intent(StartServiceActivity.this, GuuberService.class);
            mess.putExtra("operation", Operation.SENDMESSAGE);
            mess.putExtra("message", ServerMessageKind.CHATFROMPASSENGER + ":" + receiverid + ":" + current);
            mess.putExtra("receiver", resultReceiver);
            mess.putExtra("activityName", ActivityNames.PASSENGERSTARTSERVICEACTIVITY);
            startService(mess);

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

    View.OnClickListener submitButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (destinationInput.getText().length() > 0) {
                try {
                    Double myLat = myMarker.getPosition().latitude;
                    Double myLon = myMarker.getPosition().longitude;

                    Geocoder geoCoder = new Geocoder(StartServiceActivity.this, Locale.getDefault());
                    String addr = destinationInput.getText().toString();
                    List<Address> addresses = geoCoder.getFromLocationName(addr, Integer.MAX_VALUE,
                                            myLat - 20, myLon - 20, myLat + 20, myLon + 20);

                    if (addresses.size() > 0) {
                        Double lat = null;
                        Double lon = null;

                        if (destMarker != null) {
                            destMarker.remove();
                        }

                        Double minDist = null;
                        for (int i = 0; i < addresses.size(); i++) {
                            Double currLat = addresses.get(i).getLatitude();
                            Double currLon = addresses.get(i).getLongitude();

                            double dist = Math.sqrt(Math.pow((currLat - myLat), 2)
                                                + Math.pow((currLon - myLon), 2));

                            if (minDist == null || dist < minDist) {
                                lat = currLat;
                                lon = currLon;
                                minDist = dist;
                            }
                        }
                        LatLng dest = new LatLng(lat, lon);
                        destLon = lon;
                        destLat = lat;
                        destMarker = mMap.addMarker(new MarkerOptions().position(dest).title("Destination"));
                        destMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    }
                } catch (IOException e) {
                    e.printStackTrace();;
                }
            }

            Intent mess = new Intent(StartServiceActivity.this, GuuberService.class);
            mess.putExtra("operation", Operation.SENDMESSAGE);
            mess.putExtra("message", ServerMessageKind.PASSENGERDEST + ":" + destLon + ":" + destLat);
            mess.putExtra("receiver", resultReceiver);
            mess.putExtra("activityName", ActivityNames.PASSENGERSTARTSERVICEACTIVITY);
            startService(mess);
        }
    };

    View.OnClickListener cancelButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent mess = new Intent(StartServiceActivity.this, GuuberService.class);
            mess.putExtra("operation", Operation.SENDMESSAGE);
            mess.putExtra("message", ServerMessageKind.PASSENGERCANCEL);
            mess.putExtra("receiver", resultReceiver);
            mess.putExtra("activityName", ActivityNames.PASSENGERSTARTSERVICEACTIVITY);
            startService(mess);

            Intent intent = new Intent(StartServiceActivity.this, FindDriverActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    };

    private void addDriverMarker(String driverID, double lon, double lat) {
        if (driverMarkers.get(driverID) != null) {
            driverMarkers.get(driverID).remove();
        }
        LatLng place = new LatLng(lat, lon);
        Marker driverMarker = mMap.addMarker(new MarkerOptions().position(place).title(driverID + " position"));
        driverMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        driverMarkers.put(driverID, driverMarker);
    }

    private void removeDriverMarker(String driverID) {
        if (driverMarkers.get(driverID) != null) {
            driverMarkers.get(driverID).remove();
        }
        driverMarkers.remove(driverID);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (String driver : driverMarkers.keySet()) {
            if (driverMarkers.get(driver).equals(marker)) {
                String history = messageHistory.getText().toString();
                if (history.length() > 0 && currentDriver != null) {
                    allMessages.put(currentDriver, history);
                }
                currentDriver = driver;
                if (allMessages.get(driver) != null) {
                    messageHistory.setText(allMessages.get(driver));
                    scollToBottom();
                } else {
                    messageHistory.setText("Chatting with driver " + driver);
                }
                break;
            }
        }
        return false;
    }

    @SuppressLint("ParcelCreator")
    public class PassengerStartResultReceiver extends ResultReceiver {

        public PassengerStartResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            String response = resultData.getString("response");
            System.out.println("Response from server: " + response);

            final String[] splits = response.split(":");
            if (response == null || response.length() == 0) {
                return;
            }
            String type = splits[0];
            if(type.equals(ClientMessageKind.DRIVERLOC)) {
                final String driver = splits[1];
                final Double lon = Double.parseDouble(splits[2]);
                final Double lat = Double.parseDouble(splits[3]);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addDriverMarker(driver, lon, lat);
                    }
                });
            } else if (type.equals(ClientMessageKind.DRIVERCANCEL)) {
                final String driver = splits[1];
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        removeDriverMarker(driver);
                    }
                });
            } else if (type.equals(ClientMessageKind.STARTRIDE)) {
                final String driver = splits[1];
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (splits.length == 2) {
                            Intent intent = new Intent(StartServiceActivity.this, EndServiceActivity.class);
                            intent.putExtra("driver", driver);
                            intent.putExtra("username", myName);
                            intent.putExtra("destLon", destLon);
                            intent.putExtra("destLat", destLat);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        } else {
                            removeDriverMarker(driver);
                        }
                    }
                });
            } else if (type.equals(ClientMessageKind.CHATFROMDRIVER)) {
                final String driver = splits[1];
                final String content = splits[2];
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (driver.equals(currentDriver)) {
                            String history = messageHistory.getText().toString();
                            messageHistory.setText(history + "\n" + driver + ": " + content);
                        } else {
                            String history = allMessages.get(driver);
                            if (history == null) {
                                history = "";
                            }
                            String result = history + "\n" + driver + ": " + content;
                            allMessages.put(driver, result);
                        }
                        Message message = new Message(driver, myName, content, new Date().toString());
                        meassageDBController.insertMessage(message);
                    }
                });
            } else if (type.equals(ClientMessageKind.DRIVERREQUESTLOC)) {
                if (currLon == null) {
                    return;
                }
                Intent intent = new Intent(StartServiceActivity.this, GuuberService.class);
                intent.putExtra("operation", Operation.SENDMESSAGE);
                intent.putExtra("message", ServerMessageKind.PASSENGERLOC + ":" + currLon + ":" + currLat);
                intent.putExtra("receiver", resultReceiver);
                intent.putExtra("activityName", ActivityNames.PASSENGERSTARTSERVICEACTIVITY);
                startService(intent);
            }
        }
    }
}
