package com.example.lahacks.lahacks3;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.Fragment;
import java.io.IOException;
import java.lang.Object;
//import com.google.android.gms.location.LocationClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import android.app.Activity;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import java.lang.Object;

import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.maps.model.Circle;
import android.Manifest;

import java.util.List;
import java.util.Random;
import android.location.Geocoder;
import com.google.android.gms.identity.intents.Address;
import java.util.Locale;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.app.Activity;
import android.view.Menu;
import android.app.NotificationManager;
import android.app.Notification;
import android.app.Service;
import android.app.PendingIntent;
import android.support.v4.app.NotificationCompat;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private int status;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static boolean mLocationPermissionGranted = false;
    private Location mLastLocation = new Location("location");
    private Marker mCurrLocationMarker;
    //private TextView area = new TextView(this);

    private SupportMapFragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toast.makeText(this, "Area is dangerous!",
                Toast.LENGTH_LONG).show();

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        TextView tv = new TextView(this);
        tv.setText("Dynamic layouts ftw!");
        ll.addView(tv);


        mMapFragment = (SupportMapFragment) (getSupportFragmentManager().findFragmentById(R.id.map));
        ViewGroup.LayoutParams params = mMapFragment.getView().getLayoutParams();
        params.height = 1200;
        mMapFragment.getView().setLayoutParams(params);



        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                        .setContentTitle("My notification")
                        .setContentText("Area of current location is dangerous!")
                        .setPriority(Notification.PRIORITY_HIGH);;

        Intent resultIntent = new Intent(this, MapsActivity.class);
// Because clicking the notification opens a new ("special") activity, there's
// no need to create an artificial back stack.
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);


// Sets an ID for the notification
        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());



        //TextView tv = new TextView(getApplicationContext());
        //parentView.addView(tv);

        //LinearLayout layout = new LinearLayout(this);
        //layout.setOrientation(LinearLayout.VERTICAL);

        //area = new TextView(this);
        //area.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        mLastLocation.setLatitude(34.0050);
        mLastLocation.setLongitude(-118.3139);
        checkLocationPermission();
        //layout.addView(area);
        //root.addView(area);


        //  status = GoogleApiAvailability.isGooglePlayServicesAvailable(Context context);
      //  if (status != ConnectionResult.SUCCESS) {
      //      if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
      //          GooglePlayServicesUtil.getErrorDialog(status, this,
      // /                 100).show();
      //      }
      //  }


//        mGoogleApiClient = new GoogleApiClient.Builder(this)
 //               .enableAutoManage( this, 0, (GoogleApiClient.OnConnectionFailedListener) this)
  //              .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
   //             .addApi(LocationServices.API)
    //            .addApi(Places.GEO_DATA_API)
     ///           .addApi(Places.PLACE_DETECTION_API)
      //          .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
       //         .build();
        //mGoogleApiClient.connect();
   }

    /*
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }


     // Request location permission, so that we can get the location of the
     // device. The result of the permission request is handled by a callback,
     //onRequestPermissionsResult.

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mLastKnownLocation = null;
        }
    }
*/
    /*
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                            String permissions[],
                                            int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }
*/
   /* @Override
    protected void onStart() {
        super.onStart();
        if( mGoogleApiClient != null )
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }*/

/*
    private void getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        // A step later in the tutorial adds the code to get the device location.
    }

*/
  /*  private void displayPlacePicker() {
        if( mGoogleApiClient == null || !mGoogleApiClient.isConnected() )
            return;
        int PLACE_PICKER_REQUEST = 1;
        Context context = getApplicationContext();


        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult( builder.build(this), PLACE_PICKER_REQUEST );
        } catch ( GooglePlayServicesRepairableException e ) {
            Log.d( "PlacesAPI Demo", "GooglePlayServicesRepairableException thrown" );
        } catch ( GooglePlayServicesNotAvailableException e ) {
            Log.d( "PlacesAPI Demo", "GooglePlayServicesNotAvailableException thrown" );
        }

    }*/

   /* public void onConnected(Bundle connectionHint) {
       SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
              .findFragmentById(R.id.map);
       mapFragment.getMapAsync(this);
    }*/
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    //public static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    public void checkLocationPermission(){
        Log.d("MapActivity", "checkLocationPermission");
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("MapActivity", "checkLocationPermission-permission not granted yet");
            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.d("MapActivity", "checkLocationPermission-show explanation");
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                Log.d("MapActivity", "checkLocationPermission-no explanation");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        } else {
            Log.d("MapActivity", "checkLocationPermission-approved permission");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("MapActivity", "onRequestPermissionResult");

        Log.d("MapActivity", "requestCode is" + requestCode);
        Log.d("MapActivity", "size of grantResults" + grantResults.length);
        Log.d("MapActivity", "val grantResults" + grantResults[0]);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                final int numOfRequest = grantResults.length;
                //final boolean isGranted = numOfRequest == 1 &&
                //        PackageManager.PERMISSION_GRANTED != grantResults[numOfRequest - 1];
                final boolean isGranted = true;

                if (isGranted) {
                    // Permission was granted.
                    //if (ContextCompat.checkSelfPermission(this,
                    //        Manifest.permission.ACCESS_FINE_LOCATION)
                    //        == PackageManager.PERMISSION_GRANTED) {
                    //    if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                    //    }
                    //    mMap.setMyLocationEnabled(true);
                   //}

                } else {
                    Log.d("MapActivity", "failed to request permission");
                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            //You can add here other case statements according to your requirement.
        }
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d("MapActivity", "onMapReady");

        String line = "";
        String csvSplitBy = ",";
        mMap = googleMap;

        float default_lat = (float)34.0065;
        float default_long = (float)-118.3148;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<android.location.Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(default_lat, default_long, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String cityName = addresses.get(0).getAddressLine(0);
        String stateName = addresses.get(0).getAddressLine(1);
        String countryName = addresses.get(0).getAddressLine(2);

        Log.d("MapActivity", "city is" + cityName + " " + stateName + " " + countryName);

        //area.setText(cityName);

        LatLng locate = new LatLng(default_lat, default_long);
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {

            int value1 = rand.nextInt(30);
            int value2 = rand.nextInt(30);
            float latitude = Float.valueOf(default_lat+(float)(value1*0.001));
            float longitude = Float.valueOf(default_long+(float)(value2*0.001));
            locate = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(locate).title("Sexual Assault Incident"));
            mMap.addMarker(new MarkerOptions().position(locate));
        }


        //mMap.moveCamera(CameraUpdateFactory.newLatLng(locate));

        // ... get a map.
        // Add a circle in Sydney
        Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(default_lat, default_long))
                .radius(2000)
                .strokeColor(Color.RED));

        float zoomLevel = (float)21.0;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locate, zoomLevel));

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        //updateLocationUI();
        //getDeviceLocation();

    }

    protected synchronized void buildGoogleApiClient(){
        Log.d("MapActivity", "buildGoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("MapActivity", "onConnected");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        if (mLastLocation != null)
            onLocationChanged(mLastLocation);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("MapActivity", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("MapActivity", "onConnectionFailed");

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("MapActivity", "onLocationChanged");
        mLastLocation = location;
        Log.d("Latitude is", String.valueOf(mLastLocation.getLatitude()));
        Log.d("Longitude is" +
                "", String.valueOf(mLastLocation.getLongitude()));
        Log.d("MapActivity", "location is" + mLastLocation.getProvider());
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }
}
