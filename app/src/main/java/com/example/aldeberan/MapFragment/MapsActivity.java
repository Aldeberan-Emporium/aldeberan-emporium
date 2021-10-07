package com.example.aldeberan.MapFragment;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.example.aldeberan.MapFragment.DirectionHelpers.IGoogleAPI;
import com.example.aldeberan.R;
import com.example.aldeberan.storage.UserStorage;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.aldeberan.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public int ACCESS_LOCATION_REQUEST_CODE = 10001;
    private Polyline polyline, senderPolyline;
    private List<LatLng> polylineList;
    private PolylineOptions polylineOptions, senderPolylineOptions;
    Context context;

    IGoogleAPI mService;

    Marker shopLocationMarker; //start
    Marker userLocationMarker; //end
    Marker senderLocationMarker; //in between/delivery
    private LatLng startPosition, endPosition;
    private int index, next;
    private double lat, lng;
    private float v;

    public UserStorage us;
    public String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        us = new UserStorage(this);
        context = this;

        polylineList = new ArrayList<>();
        mService = Common.getGoogleAPI();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setTrafficEnabled(false);
        mMap.setIndoorEnabled(false);
        mMap.setBuildingsEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(false);

        //Set bakery location (MMU Address)
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(2.9279965093182874, 101.64193258318224));
        shopLocationMarker = mMap.addMarker(markerOptions.title("Aldeberan Emporium"));

        userLocationMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(5.375991955595361, 100.53635499667726)).title(us.getName() + "'s Address"));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //We can show user a dialog why this permission is necessary
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
            }
        }

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
        .target(googleMap.getCameraPosition().target).zoom(17).bearing(30).tilt(45).build()));

        String requestURL = null;

        try {
            requestURL = getUrl(shopLocationMarker.getPosition(), userLocationMarker.getPosition(), "driving");
            mService.getDataFromGoogleApi(requestURL)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().toString());
                                JSONArray jsonArray = jsonObject.getJSONArray("routes");
                                for (int i = 0; i < jsonArray.length(); i++){
                                    JSONObject route = jsonArray.getJSONObject(i);
                                    JSONObject poly = route.getJSONObject("overview_polyline");
                                    String polyline = poly.getString("points");
                                    JSONArray legs = route.getJSONArray("legs");
                                    //JSONObject legsObj = legs.getJSONObject(i);

                                    //String distanceTxt = distance.getString("distance");
                                    polylineList = decodePoly(polyline);
                                }

                                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                for (LatLng latlng: polylineList){
                                    builder.include(latlng);
                                }
                                LatLngBounds bounds = builder.build();
                                CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 2);
                                mMap.animateCamera(mCameraUpdate);

                                polylineOptions = new PolylineOptions();
                                polylineOptions.width(20);
                                polylineOptions.color(Color.BLUE);
                                polylineOptions.startCap(new SquareCap());
                                polylineOptions.endCap(new SquareCap());
                                polylineOptions.jointType(JointType.ROUND);
                                polylineOptions.addAll(polylineList);
                                polyline = mMap.addPolyline(polylineOptions);

                                senderPolylineOptions = new PolylineOptions();
                                senderPolylineOptions.width(20);
                                senderPolylineOptions.color(Color.BLUE);
                                senderPolylineOptions.startCap(new SquareCap());
                                senderPolylineOptions.endCap(new SquareCap());
                                senderPolylineOptions.jointType(JointType.ROUND);
                                senderPolylineOptions.addAll(polylineList);
                                senderPolyline = mMap.addPolyline(senderPolylineOptions);

                                mMap.addMarker(new MarkerOptions().position(polylineList.get(polylineList.size() - 1)));

                                ValueAnimator polylineAnimator = ValueAnimator.ofInt(0, 100);
                                polylineAnimator.setDuration(2000);
                                polylineAnimator.setInterpolator(new LinearInterpolator());
                                polylineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                        List<LatLng> points = polyline.getPoints();
                                        int percentValue = (int) valueAnimator.getAnimatedValue();
                                        int size = points.size();
                                        int newPoints = (int) (size * (percentValue / 100.0f));
                                        List<LatLng> p = points.subList(0, newPoints);
                                        senderPolyline.setPoints(p);
                                    }
                                });
                                polylineAnimator.start();

                                senderLocationMarker = mMap.addMarker(new MarkerOptions().position(shopLocationMarker.getPosition())
                                .flat(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.motor)).title("Aldeberan Emporium's Delivery Worker"));

                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(senderLocationMarker.getPosition(), 10));

                                Handler handler = new Handler();
                                index = -1;
                                next = 1;
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (index < polylineList.size() - 1) {
                                            index++;
                                            next = index + 1;
                                        }
                                        if (index < polylineList.size() - 1) {
                                            startPosition = polylineList.get(index);
                                            endPosition = polylineList.get(next);
                                        }
                                        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                                        valueAnimator.setDuration(3000);
                                        valueAnimator.setInterpolator(new LinearInterpolator());
                                        valueAnimator.addUpdateListener(valueAnimator1 -> {
                                            v = valueAnimator1.getAnimatedFraction();
                                            lng = v * endPosition.longitude + (1 - v)
                                                    * startPosition.longitude;
                                            lat = v * endPosition.latitude + (1 - v)
                                                    * startPosition.latitude;
                                            LatLng newPos = new LatLng(lat, lng);
                                            senderLocationMarker.setPosition(newPos);
                                            senderLocationMarker.setAnchor(0.5f, 0.5f);
                                            senderLocationMarker.setRotation(getBearing(startPosition, newPos));
                                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(senderLocationMarker.getPosition(), 10));
                                        });
                                        valueAnimator.start();
                                        handler.postDelayed(this, 3000);
                                        if (index == polylineList.size() - 1) {
                                            Toast.makeText(context, "Product delivered!", Toast.LENGTH_LONG).show();
                                            valueAnimator.end();
                                            handler.removeCallbacksAndMessages(null);
                                        }
                                    }
                                }, 3000);
                            }catch (Exception e){

                            }
                        }

                        private List<LatLng> decodePoly(String polyline) {
                            List<LatLng> poly = new ArrayList<>();
                            int index = 0, len = polyline.length();
                            int lat = 0, lng = 0;

                            while (index < len) {
                                int b, shift = 0, result = 0;
                                do {
                                    b = polyline.charAt(index++) - 63;
                                    result |= (b & 0x1f) << shift;
                                    shift += 5;
                                } while (b >= 0x20);
                                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                                lat += dlat;

                                shift = 0;
                                result = 0;
                                do {
                                    b = polyline.charAt(index++) - 63;
                                    result |= (b & 0x1f) << shift;
                                    shift += 5;
                                } while (b >= 0x20);
                                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                                lng += dlng;

                                LatLng p = new LatLng((((double) lat / 1E5)),
                                        (((double) lng / 1E5)));
                                poly.add(p);
                            }

                            return poly;
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });

        } catch (Exception e){}
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.MAPS_API_KEY);
        return url;
    }

    private float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }
}