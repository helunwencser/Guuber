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

import com.directions.route.AbstractRouting;
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
import java.util.Date;
import java.util.List;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.dbLayout.TransactionDBController;
import guuber.cmu.edu.entities.Transaction;
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
    private Marker passengerMarker;

    private Double destLon;
    private Double destLat;

    private Double passengerLon;
    private Double passengerLat;

    private Double startLon;
    private Double startLat;

    private List<Polyline> routeLines;

    private Routing routing;

    private String passenger;

    private Date startTime;
    private Date endTime;

    private String myName;

    private TransactionDBController transactionDBController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_activity_end_service);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.driver_end_map);
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

        Intent parameters = getIntent();
        myName = parameters.getStringExtra("username");
        System.out.println("EndService Username: " + myName);

        Button endButton =
                (Button) findViewById(R.id.driver_endButton);
        endButton.setOnClickListener(endButtonClicked);

        Intent intent = getIntent();
        destLon = Double.parseDouble(intent.getStringExtra("destLon"));
        destLat = Double.parseDouble(intent.getStringExtra("destLat"));
        passengerLon = Double.parseDouble(intent.getStringExtra("passengerLon"));
        passengerLat = Double.parseDouble(intent.getStringExtra("passengerLat"));
        passenger = intent.getStringExtra("passenger");

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

            if (routing == null) {
                LatLng start = new LatLng(lat, lon);
                LatLng pass = new LatLng(passengerLat, passengerLon);
                LatLng end = new LatLng(destLat, destLon);
                routing = new Routing.Builder()
                        .travelMode(Routing.TravelMode.DRIVING)
                        .withListener(this)
                        .waypoints(start, pass, end)
                        .build();
                routing.execute();
            }
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
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        try {
            Location loc = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

            updateLocation(loc);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        LatLng place = new LatLng(destLat, destLon);
        destMarker = mMap.addMarker(new MarkerOptions().position(place)
                .title("destination"));
        destMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        LatLng loc = new LatLng(passengerLat, passengerLon);
        passengerMarker = mMap.addMarker(new MarkerOptions().position(loc)
                .title("passenger"));
        passengerMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
    }

    View.OnClickListener endButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent mess = new Intent(EndServiceActivity.this, GuuberService.class);
            mess.putExtra("operation", Operation.SENDMESSAGE);
            mess.putExtra("message", ServerMessageKind.ENDRIDE + ":" + passenger);
            mess.putExtra("activityName", ActivityNames.DRIVERENDSERVICEACTIVITY);
            startService(mess);

            endTime = new Date();

            String startLocation = "" + startLat + "," + startLon;
            String endLocation = "" + destLat + "," + destLon;
            String cost = getCost();

            Transaction transaction= new Transaction(0,myName, passenger,startTime.toString(),
                                        endTime.toString(),startLocation,endLocation,cost);
            transactionDBController.insertTransaction(transaction);

            Intent intent = new Intent(EndServiceActivity.this, FindPassengerActivity.class);
            intent.putExtra("username", myName);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    };

    @Override
    public void onRoutingFailure(RouteException e) {

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
}