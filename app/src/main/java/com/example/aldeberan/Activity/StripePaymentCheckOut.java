package com.example.aldeberan.Activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.aldeberan.MapFragment.MapsActivity;
import com.example.aldeberan.R;
import com.example.aldeberan.models.CartModel;
import com.example.aldeberan.models.MapModel;
import com.example.aldeberan.models.OrderModel;
import com.example.aldeberan.storage.OrderStorage;
import com.example.aldeberan.storage.UserStorage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StripePaymentCheckOut extends AppCompatActivity {
    private static final String BACKEND_URL = "https://fierce-chamber-24927.herokuapp.com/"; //never change this url
    private OkHttpClient httpClient = new OkHttpClient();
    private String paymentIntentClientSecret;
    private Stripe stripe;
    private TextView amountTextView;
    private OrderStorage os;
    private OrderModel om;
    private UserStorage us;
    private CartModel cm;
    ProgressBar progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_stripe);

        progress = findViewById(R.id.progress);
        progress.setVisibility(View.GONE);

        getSupportActionBar().hide();

        ConstraintLayout constraintLayout = findViewById(R.id.stripe_constrain);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(0);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        os = new OrderStorage(this);
        us = new UserStorage(this);
        om = new OrderModel();
        cm = new CartModel();

        amountTextView = findViewById(R.id.amountTextView);
        amountTextView.setText(String.valueOf(os.getTotal()));

        stripe = new Stripe(
                getApplication(),
                //publishableKey, never touch
                Objects.requireNonNull("pk_test_51JgYIxKPfEZYnk5WHXPITPG5oh9A0TurNQJ8SfQcHqwe34BkjlofjNGIzSTyzoPFmZLcc5YfQNLxDLFy2ptee7So00ka5eDOaS")
        );
        startCheckout();
    }
    private void startCheckout() {
        // Create a PaymentIntent by calling the server's endpoint.
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        double amount = Double.valueOf(amountTextView.getText().toString()) * 100;

        Map<String, Object> payMap = new HashMap<>();
        Map<String, Object> itemMap = new HashMap<>();
        List<Map<String, Object>> itemList = new ArrayList<>();
        payMap.put("currency", "myr"); //stick to myr
        itemMap.put("id", "photo_subscription");
        itemMap.put("amount", amount);
        itemList.add(itemMap);
        payMap.put("items", itemList);
        String json = new Gson().toJson(payMap);

        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder().url(BACKEND_URL + "create-payment-intent").post(body).build(); //request endpoint
        httpClient.newCall(request).enqueue(new PayCallback(this));
        // Hook up the pay button to the card widget and stripe instance
        Button payButton = findViewById(R.id.payButton);
        payButton.setOnClickListener((View view) -> {
            progress.setVisibility(View.VISIBLE);

            CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
            PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();

            if (params != null) {
                ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                stripe.confirmPayment(this, confirmParams);
            }
        });
    }
    private void displayAlert(@NonNull String title,
                              @Nullable String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
        builder.setPositiveButton("Ok", null);
        builder.create().show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }
    private void onPaymentSuccess(@NonNull final Response response) throws IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> responseMap = gson.fromJson(Objects.requireNonNull(response.body()).string(), type);
        paymentIntentClientSecret = responseMap.get("clientSecret");
    }
    private static final class PayCallback implements Callback {
        @NonNull private final WeakReference<StripePaymentCheckOut> activityRef;
        PayCallback(@NonNull StripePaymentCheckOut activity) {
            activityRef = new WeakReference<>(activity);
        }
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            final StripePaymentCheckOut activity = activityRef.get();
            if (activity == null) {
                return;
            }
            activity.runOnUiThread(() ->
                    Toast.makeText(activity, "Error: " + e.toString(), Toast.LENGTH_LONG).show()
            );
        }
        @Override
        public void onResponse(@NonNull Call call, @NonNull final Response response)
                throws IOException {
            final StripePaymentCheckOut activity = activityRef.get();
            if (activity == null) {
                return;
            }
            if (!response.isSuccessful()) {
                activity.runOnUiThread(() -> Toast.makeText(activity, "Error: " + response.toString(), Toast.LENGTH_LONG).show()
                );
            } else {
                activity.onPaymentSuccess(response);
            }
        }
    }
    private final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull private final WeakReference<StripePaymentCheckOut> activityRef;
        PaymentResultCallback(@NonNull StripePaymentCheckOut activity) {
            activityRef = new WeakReference<>(activity);
        }
        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            progress.setVisibility(View.GONE);

            final StripePaymentCheckOut activity = activityRef.get();
            if (activity == null) {
                return;
            }
            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                activity.displayAlert("Payment completed", "Payment verified. Thank you");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String currentTime = sdf.format(new Date());
                om.addOrder(us.getID(), currentTime, os.getTotal(), "shipping", (response) -> {
                    os.saveOrderID(response);
                    om.addOrderItem(response, us.getQuoteID());
                    om.addOrderAddress(response, os.getRecipient(), os.getContact(), os.getLine1(), os.getLine2(), os.getCode(), os.getCity(), os.getState(), os.getCountry());
                    om.addOrderPayment(response, "Card", paymentIntent.getId());
                    cm.updateQuoteStatus(us.getQuoteID());
                    cm.addQuote(us.getID(), 0., 0, res -> us.setQuoteID(res));
                    Log.i("qid", String.valueOf(us.getQuoteID()));
                    om.sendOrderMail(us.getName(), us.getEmail(), response);
                });

                Log.i("STRIPE_ID",paymentIntent.getId());
                toMap();
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                activity.displayAlert(
                        "Payment failed",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage()
                );
            }
        }
        @Override
        public void onError(@NonNull Exception e) {
            final StripePaymentCheckOut activity = activityRef.get();
            if (activity == null) {
                return;
            }
            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString());
        }
    }

    public void toMap(){
        //Pass in Order ID and Order Address
        String address = os.getLine1()+","+os.getLine2()+","+os.getCode()+","+os.getCity()+","+os.getState()+","+os.getCountry();

        MapModel mm = new MapModel();
        mm.getLatLng(address, (lat, lng) -> {
            finish();
            Intent mapIntent = new Intent(StripePaymentCheckOut.this, MapsActivity.class);
            mapIntent.putExtra("lat", lat);
            mapIntent.putExtra("lng", lng);
            startActivity(mapIntent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        Intent homeIntent = new Intent(this, Homepage.class);
        startActivity(homeIntent);
    }
}