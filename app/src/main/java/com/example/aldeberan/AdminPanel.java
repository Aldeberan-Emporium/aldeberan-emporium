package com.example.aldeberan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aldeberan.models.ProductModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONException;

import java.io.IOException;

public class AdminPanel extends AppCompatActivity implements View.OnClickListener{

    ProductModel pm = new ProductModel();
    public Uri imgURI;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        Button submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(this);
        storageRef = storage.getReference();
    }

    private void pickImgFromGallery(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("requestCode", 102);
        activityResultLauncher.launch(intent);
    }

    private boolean isPermissionGranted(){
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager();
        }
        else{
            int readExternalStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            return readExternalStorage == PackageManager.PERMISSION_GRANTED;
        }
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    if (data.getExtras().getInt("requestCode") == 100){
                        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
                            if (Environment.isExternalStorageManager()){
                                pickImgFromGallery();
                            }
                        }
                    }
                    else if (data.getExtras().getInt("requestCode") == 102){
                        if (data != null){
                            imgURI = data.getData();
                            if (imgURI != null){
                                ImageView img = findViewById(R.id.prodImg);
                                img.setImageURI(imgURI);
                            }
                        }
                    }

                }
            });

    ActivityResultLauncher<String> requestPermissionLauncher =
        registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                pickImgFromGallery();
            } else {
                takePermissions();
            }
        });

    private void takePermissions(){
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
            try{
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package%s", getApplicationContext().getPackageName())));
                intent.putExtra("requestCode", 100);
                activityResultLauncher.launch(intent);
            }catch (Exception e){
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.putExtra("requestCode", 100);
                activityResultLauncher.launch(intent);
            }
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.submitBtn:
                TextView prodName = findViewById(R.id.prodName);
                TextView prodSKU = findViewById(R.id.prodSKU);
                TextView prodStock = findViewById(R.id.prodStock);
                TextView prodPrice = findViewById(R.id.prodPrice);
                String prodImg = "";
                Switch prodAvailSwitch = findViewById(R.id.prodAvail);
                int prodAvail = prodAvailSwitch.isChecked() ? 1 : 0;

                if(imgURI != null) {

                    StorageReference childRef = storageRef.child("/images/"+prodSKU+".jpg");

                    //uploading the image
                    UploadTask uploadTask = childRef.putFile(imgURI);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           //Toast.makeText(this, "Upload successful", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Toast.makeText(this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    //Toast.makeText(this, "Select an image", Toast.LENGTH_SHORT).show();
                }


                try {
                    pm.addProduct(prodName.getEditableText().toString(), prodSKU.getEditableText().toString(), prodAvail, Integer.parseInt(prodStock.getEditableText().toString()), Double.parseDouble(prodPrice.getEditableText().toString()), prodImg);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.imageBtn:
                if (isPermissionGranted()){
                    pickImgFromGallery();
                }
                else{
                    takePermissions();
                }
        }

    }
}