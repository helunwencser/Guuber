package guuber.cmu.edu.activities.passenger;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.cmu.guuber.guuber.R;

/**
 * Created by wangziming on 4/9/16.
 */
public class EndServiceActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LocationManager locationManager;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_activity_end_service);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.passenger_end_map);
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
        } else {
        }

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            Location loc = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            updateLocation(loc);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
