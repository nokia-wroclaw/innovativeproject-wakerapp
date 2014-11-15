package com.example.GPS_Wake_upper;

/**
* Created by Adam on 2014-11-13.
*/
import android.app.Activity;
import android.view.View;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.content.Intent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import android.widget.EditText;
import android.location.*;

public class gpsActivity extends Activity implements LocationListener, LocationSource {

    private GoogleMap googleMap;
    public final static String EXTRA_MESSAGE = " com.example.GPS_Wake_upper.MESSAGE";
    public static final LatLng WROCLAW = new LatLng(51.110, 17.030);
    public final static int MAP_ZOOM = 5;
    private LocationManager locationManager;

    private OnLocationChangedListener mListener;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    private LatLng myLocation;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localize);
        createMapView();
        addMarker();
    }

    /**
     * Initialises the mapview
     */
    private void createMapView(){
        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try {
            if(null == googleMap){
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.mapView)).getMap();
                if(googleMap!=null){
                    setUpMap();
                }
                //This is how you register the LocationSource
                //  googleMap.setLocationSource(this);
                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(null == googleMap) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map",Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }

    private void setUpMap() {
        //get LocationMabagerObject from System Service LOCATION_SERVICE
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager != null) {
            boolean gpsIsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean networkIsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (gpsIsEnabled) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            } else if (networkIsEnabled) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            } else {
                //Show an error dialog that GPS is disabled...
                System.out.println("!!!!!!!!! dialog GPS is disabled !!!!!!");
            }
        } else {
            //Show some generic error dialog because something must have gone wrong with location manager.
            System.out.println("!!!!!!!!! something must have gone wrong with location manager !!!!!!");
        }


        googleMap.setMyLocationEnabled(true);


        Location location = new Location(googleMap.getMyLocation());

        if (location != null) {
            System.out.println("\n\n JEEEEEESTEMMMMMMMMMMMMMM         !!!!!!!!!!!!!!!!!!");
            myLocation = new LatLng(location.getLatitude(),
                    location.getLongitude());
        }
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, MAP_ZOOM));

        //Create a cryteria object to retrive provider
        Criteria criteria = new Criteria();
                //Get the name od the best provider
        String provider = locationManager.getBestProvider(criteria, true);
        //Get current location
       location = locationManager.getLastKnownLocation(provider);
//        //set map type
//        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //Get latitude if the current location
        double latitude = location.getLatitude();
        //Get a longtitude of the current location
        double longtitude = location.getLongitude();
        //Create a lating object for the current locatopn
        LatLng latLng = new LatLng(latitude,longtitude);
        //show the current location in GoogleMap
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        //Zone in the GoogleMap
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(20));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longtitude)).title("You are here"));


        googleMap.moveCamera(CameraUpdateFactory.newLatLng(WROCLAW));
        //Zone in the GoogleMap
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        googleMap.setMyLocationEnabled(true);

    }
    /**
     * Adds a marker to the map
     */
    private void addMarker(){

        /** Make sure that the map has been initialised **/
        if(null != googleMap){
            googleMap.addMarker(new MarkerOptions()
                            .position(WROCLAW)
                            .title("Marker")
                            .draggable(true)
            );
           // googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
            googleMap.setMyLocationEnabled(true);

        }
    }

    @Override
    public void onLocationChanged(Location location)
    {
        if( mListener != null )
        {
            mListener.onLocationChanged( location );

            LatLngBounds bounds = this.googleMap.getProjection().getVisibleRegion().latLngBounds;

            if(!bounds.contains(new LatLng(location.getLatitude(), location.getLongitude())))
            {
                //Move the camera to the user's location once it's available!
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
        // TODO Auto-generated method stub
        Toast.makeText(this, "status changed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider)
    {
        // TODO Auto-generated method stub
        Toast.makeText(this, "provider disabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider)
    {
        // TODO Auto-generated method stub
        Toast.makeText(this, "provider enabled", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void activate(OnLocationChangedListener listener)
    {
        mListener = listener;
    }

    @Override
    public void deactivate()
    {
        mListener = null;
    }

    @Override
    public void onPause()
    {
        if(locationManager != null)
        {
            locationManager.removeUpdates(this);
        }

        super.onPause();
    }





   /* public void sendMessage(View view){
        Intent intent = new Intent(this,DisplayMessageActivity.class);

        EditText editText = (EditText) findViewById(R.id.edit_message);

        String message = editText.getText().toString();

        intent.putExtra(EXTRA_MESSAGE, message);

        startActivity(intent);

    }*/
//public class gpsActivity extends Activity {
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//    }
}