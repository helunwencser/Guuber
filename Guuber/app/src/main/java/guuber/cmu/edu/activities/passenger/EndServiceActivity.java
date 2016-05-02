package guuber.cmu.edu.activities.passenger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.dbLayout.TransactionDBController;
import guuber.cmu.edu.entities.Transaction;
import guuber.cmu.edu.messageConst.ActivityNames;
import guuber.cmu.edu.messageConst.ClientMessageKind;
import guuber.cmu.edu.messageConst.Operation;
import guuber.cmu.edu.messageConst.ServerMessageKind;
import guuber.cmu.edu.ws.remote.GuuberService;

/**
 * Created by wangziming on 4/9/16.
 */
public class EndServiceActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LocationManager locationManager;
    private Marker marker;

    private String driver;

    private ResultReceiver resultReceiver;

    private Double startLon;
    private Double startLat;

    private Double destLon;
    private Double destLat;

    private Date startTime;
    private Date endTime;

    private String myName;

    private TransactionDBController transactionDBController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_activity_end_service);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.passenger_end_map);
        mapFragment.getMapAsync(this);

        resultReceiver = new PassengerEndResultReceiver(null);

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

        Intent intent = getIntent();
        driver = intent.getStringExtra("driver");

        Intent parameters = getIntent();
        myName = parameters.getStringExtra("username");
        destLon = Double.parseDouble(intent.getStringExtra("destLon"));
        destLat = Double.parseDouble(intent.getStringExtra("destLat"));

        Intent mess2 = new Intent(EndServiceActivity.this, GuuberService.class);
        mess2.putExtra("operation", Operation.SENDMESSAGE);
        mess2.putExtra("message", ServerMessageKind.ENDRIDE);
        mess2.putExtra("receiver", resultReceiver);
        mess2.putExtra("activityName", ActivityNames.PASSENGERENDSERVICEACTIVITY);
        startService(mess2);

        startTime = new Date();

        transactionDBController = new TransactionDBController(this);

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
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            if (startLon == null) {
                startLon = lon;
                startLat = lat;
            }
        } else {
        }

    }

    public String getCost() {
        if (endTime == null) {
            endTime = new Date();
        }
        long passed = endTime.getTime() - startTime.getTime();
        Double cost = Math.ceil((double) passed/1000.0/60.0) * 0.8;
        return String.format("$%.2f", cost);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.zoomTo(18));

        try {
            Location loc = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            updateLocation(loc);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("ParcelCreator")
    public class PassengerEndResultReceiver extends ResultReceiver {

        public PassengerEndResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            String response = resultData.getString("response");
            if (response == null || response.length() == 0) {
                return;
            }

            if (response.equals(ClientMessageKind.ENDRIDE)) {
                endTime = new Date();

                String startLocation = "" + startLat + "," + startLon;
                String endLocation = "" + destLat + "," + destLon;
                String cost = getCost();

                Transaction transaction= new Transaction(driver,myName,startTime.toString(),
                        endTime.toString(),startLocation,endLocation,cost);
                transactionDBController.insertTransaction(transaction);

                Intent intent = new Intent(EndServiceActivity.this, FindDriverActivity.class);
                intent.putExtra("username", myName);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }
    }
}
