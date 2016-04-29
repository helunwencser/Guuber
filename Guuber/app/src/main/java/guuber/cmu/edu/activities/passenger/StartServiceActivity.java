package guuber.cmu.edu.activities.passenger;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;

import edu.cmu.guuber.guuber.R;

import guuber.cmu.edu.dbLayout.MessageDBController;
import guuber.cmu.edu.entities.Message;

public class StartServiceActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private Marker marker;
    private EditText messageInput;
    private TextView messageHistory;

    private MessageDBController meassageDBController;

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
        messageInput = (EditText) findViewById(R.id.passenger_start_input);
        messageHistory = (TextView) findViewById(R.id.passenger_start_history);
        messageHistory.setMovementMethod(new ScrollingMovementMethod());

        meassageDBController = new MessageDBController(this);
    }

    public void updateLocation(Location location) {
        if (location != null && mMap != null) {
            if (marker != null) {
                marker.remove();
            }
            double lon = location.getLongitude();
            double lat = location.getLatitude();
            LatLng sydney = new LatLng(lat, lon);
            marker = mMap.addMarker(new MarkerOptions().position(sydney).title("My position"));
            //marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
            int scrollAmount = messageHistory.getLayout().getLineTop(messageHistory.getLineCount())
                    - messageHistory.getHeight();
            if (scrollAmount > 0) {
                messageHistory.scrollTo(0, scrollAmount);
            }
            else {
                messageHistory.scrollTo(0, 0);
            }
            String senderid = "me";
            String receiverid = "other";
            Message message = new Message(senderid, receiverid, current, new Date().toString());
            meassageDBController.insertMessage(message);
        }
    };


    View.OnClickListener cancelButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(StartServiceActivity.this, FindDriverActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
