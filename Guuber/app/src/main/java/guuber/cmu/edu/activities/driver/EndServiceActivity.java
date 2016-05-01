package guuber.cmu.edu.activities.driver;

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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.messageConst.ActivityNames;
import guuber.cmu.edu.messageConst.Operation;
import guuber.cmu.edu.messageConst.ServerMessageKind;
import guuber.cmu.edu.service.GuuberService;

/**
 * Created by wangziming on 4/9/16.
 */
public class EndServiceActivity extends FragmentActivity implements OnMapReadyCallback, RoutingListener {
    private GoogleMap mMap;
    private LocationManager locationManager;
    private Marker marker;

    private Marker destMarker;

    private Double destLon;
    private Double destLat;

    private Double startLon;
    private Double startLat;

    private List<Polyline> routeLines;

    private Routing routing;

    private String passenger;

    private ResultReceiver resultReceiver;

    private static final int[] COLORS = new int[]{R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorAccent};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_activity_end_service);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.driver_end_map);
        mapFragment.getMapAsync(this);

        resultReceiver = new DriverEndResultReceiver(null);

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

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            };

            // Register the listener with the Location Manager to receive location updates
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location loc = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            updateLocation(loc);

        } catch (SecurityException e) {
            e.printStackTrace();
        }

        Button endButton =
                (Button) findViewById(R.id.driver_endButton);
        endButton.setOnClickListener(endButtonClicked);

        Intent intent = getIntent();
        destLon = Double.parseDouble(intent.getStringExtra("destLon"));
        destLat = Double.parseDouble(intent.getStringExtra("destLat"));
        passenger = intent.getStringExtra("passenger");

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

            LatLng start = new LatLng(lat, lon);
            LatLng end = new LatLng(destLat, destLon);
            routing = new Routing.Builder()
                    .travelMode(Routing.TravelMode.WALKING)
                    .withListener(this)
                    .waypoints(start, end)
                    .build();
            routing.execute();

            if (startLon == null) {
                startLon = lon;
                startLat = lat;
            }

        } else {
        }

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

        LatLng place = new LatLng(destLat, destLon);
        Marker passengerDestMarker = mMap.addMarker(new MarkerOptions().position(place)
                .title("destination"));
        passengerDestMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
    }

    View.OnClickListener endButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent mess = new Intent(EndServiceActivity.this, GuuberService.class);
            mess.putExtra("operation", Operation.SENDMESSAGE);
            mess.putExtra("message", ServerMessageKind.ENDRIDE + ":" + passenger);
            mess.putExtra("receiver", resultReceiver);
            mess.putExtra("activityName", ActivityNames.DRIVERENDSERVICEACTIVITY);
            startService(mess);

            Intent intent = new Intent(EndServiceActivity.this, FindPassengerActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> arrayList, int index) {

        if(routeLines != null) {
            for (Polyline line : routeLines) {
                line.remove();
            }
        }

        routeLines = new ArrayList<>();

        for (int i = 0; i <arrayList.size(); i++) {

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(R.color.colorPrimary));
            polyOptions.addAll(arrayList.get(i).getPoints());
            Polyline line = mMap.addPolyline(polyOptions);

            routeLines.add(line);
        }
    }

    @Override
    public void onRoutingCancelled() {

    }

    @SuppressLint("ParcelCreator")
    public class DriverEndResultReceiver extends ResultReceiver {

        public DriverEndResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

        }
    }
}