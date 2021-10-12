package com.example.aldeberan.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aldeberan.R;
import com.example.aldeberan.models.CartModel;
import com.example.aldeberan.storage.UserStorage;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;
    UserStorage us;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.def_log_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // [END config_signin]

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        SignInButton btn = findViewById(R.id.sign_in_button);
        btn.setOnClickListener(this);

        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            updateUI(currentUser);
        }else{
            updateUI(null);
        }

    }
    // [END on_start_check_user]

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            System.out.println(user.getIdToken(true));
                            updateUI(user);

                            Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();

                            finish();
                            //Intent Lintent = new Intent(Login.this, home_product.class); //dun open this line back pls
                            //startActivity(Lintent); //dun open this line back pls
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if (result.getResultCode() == RESULT_OK) {
                Intent intent = result.getData();

                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    assert account != null;
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }

        }
    });
    // [END signin]

    //sign out
    public void signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            TextView txtv = findViewById(R.id.loginStatus);
            txtv.setText("Signed out");
        });

        SharedPreferences sharedPreferences = getSharedPreferences("CurrentUser",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("name", "Please Sign In");
        myEdit.putString("id", "");
        myEdit.putString("photoURL", "");
        myEdit.putString("email", "");

        myEdit.apply();

        Toast.makeText(Login.this, "Logged out", Toast.LENGTH_SHORT).show();

        finish();
        //Intent Lintent = new Intent(Login.this, home_product.class); //dun open this line back pls
        //startActivity(Lintent); //dun open this line back pls
    }
    //sign out

    private void updateUI(FirebaseUser user) {
        //to do
        us = new UserStorage(this);
        if (user != null){
            TextView txtv = findViewById(R.id.loginStatus);
            txtv.setText("User ID: " + user.getUid());

            String userID = user.getUid();

            CartModel cm = new CartModel();
            cm.checkIfUserExist(userID);
            cm.readQuoteByUser(user.getUid(), response -> {

                String userName = user.getDisplayName();
                String ID = user.getUid();
                String photoURL = user.getPhotoUrl().toString();
                String email = user.getEmail();

                int quoteID = response.get(0).getQuoteID();
                us.saveUser(userName, ID, photoURL, email, quoteID);

                System.out.println("wtf?" + userName + ID + photoURL + email + quoteID);
            });
        }else{
            TextView txtv = findViewById(R.id.loginStatus);
            txtv.setText("User ID: Please Sign in");
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.sign_in_button:
                resultLauncher.launch(new Intent(mGoogleSignInClient.getSignInIntent()));
                break;
            case R.id.logout:
                signOut();
                break;
        }
    }
}
