package com.example.aldeberan.MapFragment;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aldeberan.Activity.Homepage;
import com.example.aldeberan.Activity.OrderActivity;
import com.example.aldeberan.MapFragment.DirectionHelpers.IGoogleAPI;
import com.example.aldeberan.R;
import com.example.aldeberan.models.OrderModel;
import com.example.aldeberan.storage.MapStorage;
import com.example.aldeberan.storage.OrderStorage;
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
    int orderID;

    IGoogleAPI mService;

    Marker shopLocationMarker; //start
    Marker userLocationMarker; //end
    Marker senderLocationMarker; //in between/delivery
    private LatLng startPosition, endPosition, userAddress;
    private int index, next;
    private double lat, lng, prevLat, prevLng, latestDistance, duration;
    private float v;
    private String durationLeft, distanceLeft;
    private int distanceInt, speedInt;
    TextView distanceLeftLbl, durationLeftLbl, etaLbl, distLbl, deliveredLbl;
    LinearLayout loadingMapBox;
    View onLoadingView;
    ConstraintLayout cardView;
    public AlphaAnimation alphaAnimation, reverseAnimation;

    public UserStorage us;
    public String userID;
    public MapStorage ms;
    public OrderStorage os;

    public OrderModel om;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        us = new UserStorage(this);
        os = new OrderStorage(this);
        ms = new MapStorage(this);
        om = new OrderModel();
        context = this;
        distanceLeftLbl = findViewById(R.id.distanceLeftLbl);
        durationLeftLbl = findViewById(R.id.durationLeftLbl);
        etaLbl = findViewById(R.id.etaLbl);
        distLbl = findViewById(R.id.distLbl);
        deliveredLbl = findViewById(R.id.deliveredLbl);
        loadingMapBox = findViewById(R.id.loadingMapBox);
        onLoadingView = findViewById(R.id.onLoadingView);
        cardView = findViewById(R.id.cardView);

        polylineList = new ArrayList<>();
        mService = Common.getGoogleAPI();

        index = -1;
        next = 1;

        onLoadAnim();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            orderID = os.getOrderID();
            double lat = extras.getDouble("lat");
            double lng = extras.getDouble("lng");
            userAddress = new LatLng(lat, lng);
            Log.i("LAT", String.valueOf(lat));
            Log.i("LNG", String.valueOf(lng));
        }
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

        //userAddress = new LatLng(2.988382328709302, 101.79601227801334);

        //Set bakery location (MMU Address)
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(2.9279965093182874, 101.64193258318224));
        shopLocationMarker = mMap.addMarker(markerOptions.title("Aldeberan Emporium"));

        userLocationMarker = mMap.addMarker(new MarkerOptions().position(userAddress).title(us.getName() + "'s Address"));

        prevLat = shopLocationMarker.getPosition().latitude;
        prevLng = shopLocationMarker.getPosition().longitude;

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

        if (ms.sharedPreferences.contains("status")){
            polylineList = decodePoly(ms.getPolyline());
            duration = ms.getDuration();
            distanceInt = ms.getDistance();
            index = ms.getIndex();
            next = ms.getNext();
            prevLat = ms.getPrevLat();
            prevLng = ms.getPrevLng();
            lat = ms.getLat();
            lng = ms.getLng();
            Handler handler = new Handler();
            handler.postDelayed(() -> pathBuilderAnimation(), 1500);
        }
        else{
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
                                        durationLeft = legs.getJSONObject(i).getJSONObject("duration").getString("text");
                                        duration = legs.getJSONObject(i).getJSONObject("duration").getDouble("value");
                                        distanceLeft = legs.getJSONObject(i).getJSONObject("distance").getString("text");
                                        distanceInt = legs.getJSONObject(i).getJSONObject("distance").getInt("value");
                                        polylineList = decodePoly(polyline);
                                        ms.saveGeocode(polyline);
                                    }
                                    pathBuilderAnimation();
                                }catch (Exception e){
                                }
                            }
                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                            }
                        });

            } catch (Exception e){
            }
        }
    }

    //Build polyline and start sender animation
    public void pathBuilderAnimation(){
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

        senderLocationMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(prevLat, prevLng))
                    .flat(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.motor)).title("Aldeberan Emporium's Delivery Worker"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(senderLocationMarker.getPosition(), 10));

        BackgroundThread backgroundThread = new BackgroundThread();
        new Thread(backgroundThread).start();
    }

    class BackgroundThread implements Runnable{
        @Override
        public void run() {
            Handler handler = new Handler(Looper.getMainLooper());
            speedInt = (int) (distanceInt / duration);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (index < polylineList.size() - 1) {
                        index++;
                        next = index + 1;
                        ms.savePolyIndex(index, next);
                        ms.saveStatus("shipping");
                    }
                    if (index < polylineList.size() - 1) {
                        startPosition = polylineList.get(index);
                        endPosition = polylineList.get(next);
                    }
                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                    valueAnimator.setDuration(10);
                    valueAnimator.setInterpolator(new LinearInterpolator());
                    valueAnimator.addUpdateListener(valueAnimator1 -> {
                        prevLat = senderLocationMarker.getPosition().latitude;
                        prevLng = senderLocationMarker.getPosition().longitude;
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

                        latestDistance = distance(prevLat, prevLng, lat, lng);
                        ms.saveLatLng(prevLat, prevLng, lat, lng);
                        //Calculate the time required based on each distance travelled
                        distanceInt = (int) (distanceInt - Math.floor(latestDistance));
                        distanceLeftLbl.setText(distanceInt/1000 + "km");
                        ms.saveDistance(distanceInt);

                        //Preprocess seconds into hour and minutes
                        duration = duration - (latestDistance / speedInt);
                        ms.saveDuration(duration);
                        int sec = (int) (duration % 60);
                        int min = (int) ((duration/60) % 60);
                        int hour = (int) ((duration/60)/60);
                        if (hour > 0){
                            durationLeftLbl.setText(hour+"hr "+min+"min");
                        }
                        else if (hour == 0 && min != 0 && min >= 0){
                            if (min > 10) {
                                durationLeftLbl.setText(min+"min");
                            }
                            else{
                                durationLeftLbl.setText(min+"min "+sec+"sec");
                            }
                        }
                        else {
                            if (sec < 0){
                                durationLeftLbl.setText("Arriving soon...");
                            }
                            else{
                                durationLeftLbl.setText(sec+"sec");
                            }
                        }
                    });
                    valueAnimator.start();
                    handler.postDelayed(this, 500);
                    if (index == polylineList.size() - 1) {
                        deliveredLbl.setVisibility(View.VISIBLE);
                        distanceLeftLbl.setVisibility(View.GONE);
                        etaLbl.setVisibility(View.GONE);
                        distLbl.setVisibility(View.GONE);
                        durationLeftLbl.setVisibility(View.GONE);
                        valueAnimator.end();
                        handler.removeCallbacksAndMessages(null);
                        ms.removeStatus();
                        Thread.interrupted();
                        om.updateOrderStatus(orderID, "delivered");
                        //Intent orderHist = new Intent(MapsActivity.this, OrderActivity.class);
                        //startActivity(orderHist);
                        finish();
                    }
                }
            }, 3000);
        }
    }

    private void onLoadAnim() {
        alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(3000);
        reverseAnimation = new AlphaAnimation(0.0f, 1.0f);
        reverseAnimation.setDuration(3000);
        loadingMapBox.startAnimation(alphaAnimation);
        onLoadingView.startAnimation(alphaAnimation);
        cardView.startAnimation(reverseAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                loadingMapBox.setVisibility(View.VISIBLE);
                onLoadingView.setVisibility(View.VISIBLE);
                cardView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                loadingMapBox.setVisibility(View.GONE);
                onLoadingView.setVisibility(View.GONE);
                cardView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
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

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515 * 1.609344 * 1000;
            return (dist);
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent homeIntent = new Intent(MapsActivity.this, Homepage.class);
        startActivity(homeIntent);
    }
}