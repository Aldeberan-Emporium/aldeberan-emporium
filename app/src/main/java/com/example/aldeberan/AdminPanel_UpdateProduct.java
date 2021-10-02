package com.example.aldeberan;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aldeberan.models.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminPanel_UpdateProduct extends AppCompatActivity implements View.OnClickListener{

    ProductModel pm = new ProductModel();
    public Uri imgURI;
    StorageReference storageRef;
    Button imgBtn;
    Button submitBtn;
    Button resetBtn;
    ProgressBar onSubmitThrobber;
    View onSubmitView;
    TextView prodNameLbl;
    TextView prodSKULbl;
    TextView prodStockLbl;
    TextView prodPriceLbl;
    Switch prodAvailSwitch;
    ImageView prodImgView;
    public String prevProdName;
    public String prevProdSKU;
    public String prevProdImg;
    public int prevProdID;
    public int prevProdStock;
    public int prevProdAvail;
    public double prevProdPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel_update_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Predeclare components
        prodNameLbl = findViewById(R.id.prodName);
        prodSKULbl = findViewById(R.id.prodSKU);
        prodStockLbl = findViewById(R.id.prodStock);
        prodPriceLbl = findViewById(R.id.prodPrice);
        prodAvailSwitch = findViewById(R.id.prodAvail);
        prodImgView = findViewById(R.id.prodImg);

        retrieveData();

        //Firebase Cloud Storage reference
        storageRef = FirebaseStorage.getInstance().getReference();

        //Buttons
        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(this);
        resetBtn = findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(this);
        imgBtn = findViewById(R.id.imageBtn);
        imgBtn.setOnClickListener(this);

        //On Submit
        onSubmitThrobber = findViewById(R.id.onSubmitThrobber);
        onSubmitView = findViewById(R.id.onSubmitView);

        //Set SKU when product name on change
        EditText prodName = findViewById(R.id.prodName);
        EditText prodSKU = findViewById(R.id.prodSKU);

        prodName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                prodSKU.setText(regexValidator(prodName.getText().toString()));
            }
        });

        //Limit product stock to 5 digits (max 99999)
        EditText prodStock = findViewById(R.id.prodStock);
        prodStock.setFilters(new InputFilter[] {new InputFilter.LengthFilter(5)});


        InputFilter filter = new InputFilter() {
            final int maxDigitsBeforeDecimalPoint=7;
            final int maxDigitsAfterDecimalPoint=2;

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source
                        .subSequence(start, end).toString());
                if (!builder.toString().matches(
                        "(([1-9]{1})([0-9]{0,"+(maxDigitsBeforeDecimalPoint-1)+"})?)?(\\.[0-9]{0,"+maxDigitsAfterDecimalPoint+"})?"

                )) {
                    if(source.length()==0)
                        return dest.subSequence(dstart, dend);
                    return "";
                }
                return null;
            }
        };

        //Limit product price to 7,2 decimal places (max 99999.99)
        EditText prodPrice = findViewById(R.id.prodPrice);
        prodPrice.setFilters(new InputFilter[] {filter});
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
                    if (result.getResultCode() == 100){
                        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
                            if (Environment.isExternalStorageManager()){
                                pickImgFromGallery();
                            }
                        }
                    }
                    else{
                        takePermissions();
                    }
                }
            });

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imgURI = data.getData();
        if (imgURI != null){
            ImageView img = findViewById(R.id.prodImg);
            img.setImageURI(imgURI);
        }
    }

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

                int prodAvail = prodAvailSwitch.isChecked() ? 1 : 0;
                String prodName = prodNameLbl.getText().toString();
                String prodSKU = prodSKULbl.getText().toString();
                String prodStockStr = prodStockLbl.getText().toString();
                String prodPriceStr = prodPriceLbl.getText().toString();

                if (!prodName.isEmpty() && !prodStockStr.isEmpty() && !prodPriceStr.isEmpty()){
                    int prodStock = Integer.parseInt(prodStockStr);
                    Double prodPrice = Double.parseDouble(prodPriceStr);
                    submitBtn.setVisibility(View.GONE);
                    resetBtn.setVisibility(View.GONE);
                    onSubmitThrobber.setVisibility(View.VISIBLE);
                    onSubmitView.setVisibility(View.VISIBLE);
                    imgBtn.setEnabled(false);
                    prodNameLbl.setEnabled(false);
                    prodStockLbl.setEnabled(false);
                    prodPriceLbl.setEnabled(false);
                    prodAvailSwitch.setEnabled(false);

                    if (imgURI == null){
                        pm.updateProduct(prevProdID, prodName, prodSKU, prodAvail, prodStock, prodPrice, prevProdImg);
                        onSubmitThrobber.setVisibility(View.GONE);
                        onSubmitView.setVisibility(View.GONE);
                        finish();
                        Intent intent = new Intent(AdminPanel_UpdateProduct.this, AdminPanel_LoadProduct.class);
                        startActivity(intent);
                    }
                    else{
                        //Remove old image
                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                        StorageReference fileRef = storageRef.child("products/"+prevProdSKU+"."+extValidator(prevProdImg));

                        // Delete the file
                        fileRef.delete().addOnSuccessListener(aVoid -> {
                            Log.i("DELETESUCCESS", "File deleted from firebase!");
                        }).addOnFailureListener(exception -> {
                            Log.i("DELETEFAILED", "File failed to delete!");
                        });

                        //Upload new file
                        StorageReference childRef = storageRef.child("products/" + prodSKU + "." + extValidator(prevProdImg));

                        childRef.putFile(imgURI).continueWithTask(task -> {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return childRef.getDownloadUrl();
                        }).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                String prodImg = downloadUri.toString();
                                pm.updateProduct(prevProdID, prodName, prodSKU, prodAvail, prodStock, prodPrice, prodImg);
                                Log.i("UP","Upload success: " + downloadUri);
                                onSubmitThrobber.setVisibility(View.GONE);
                                onSubmitView.setVisibility(View.GONE);
                                finish();
                                Intent intent = new Intent(AdminPanel_UpdateProduct.this, AdminPanel_LoadProduct.class);
                                startActivity(intent);
                            } else {
                                Log.i("UP","Upload failed: ");
                            }
                        });
                    }

                }
                else{
                    Toast.makeText(this, "All inputs are required!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.imageBtn:
                if (isPermissionGranted()){
                    pickImgFromGallery();
                }
                else{
                    takePermissions();
                }
                break;
            case R.id.resetBtn:
                retrieveData();
                break;
        }
    }

    private String getFileExt(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private String regexValidator(String input){
        char[] charArr = input.toLowerCase().toCharArray();
        String output = "";
        for (int i=0; i<charArr.length;i++) {
            if (charArr[i] == 'a' || charArr[i] == 'b' || charArr[i] == 'c' ||
                    charArr[i] == 'd' || charArr[i] == 'e' || charArr[i] == 'f' ||
                    charArr[i] == 'g' || charArr[i] == 'h' || charArr[i] == 'i' ||
                    charArr[i] == 'j' || charArr[i] == 'k' || charArr[i] == 'l' ||
                    charArr[i] == 'm' || charArr[i] == 'n' || charArr[i] == 'o' ||
                    charArr[i] == 'p' || charArr[i] == 'q' || charArr[i] == 'r' ||
                    charArr[i] == 's' || charArr[i] == 't' || charArr[i] == 'u' ||
                    charArr[i] == 'v' || charArr[i] == 'w' || charArr[i] == 'x' ||
                    charArr[i] == 'y' || charArr[i] == 'z' || charArr[i] == '0' ||
                    charArr[i] == '1' || charArr[i] == '2' || charArr[i] == '3' ||
                    charArr[i] == '4' || charArr[i] == '5' || charArr[i] == '6' ||
                    charArr[i] == '7' || charArr[i] == '8' || charArr[i] == '9') {
                output += String.valueOf(charArr[i]);
            }
            else{
                output += "_";
            }
        }
        return output;
    }

    private void retrieveData(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            prevProdName = extras.getString("prodName");
            prevProdSKU = extras.getString("prodSKU");
            prevProdImg = extras.getString("prodImg");
            prevProdID = Integer.parseInt(extras.getString("prodID"));
            prevProdStock = Integer.parseInt(extras.getString("prodStock"));
            prevProdAvail = Integer.parseInt(extras.getString("prodAvail"));
            prevProdPrice = Double.parseDouble(extras.getString("prodPrice"));

            prodNameLbl.setText(prevProdName);
            prodSKULbl.setText(prevProdSKU);
            prodStockLbl.setText(Integer.toString(prevProdStock));
            prodPriceLbl.setText(Double.toString(prevProdPrice));
            boolean isChecked = prevProdAvail == 1 ? true : false;
            prodAvailSwitch.setChecked(isChecked);
            Glide.with(this).load(prevProdImg).override(450, 450).into(prodImgView);
        }
    }

    //Check file extension from url
    public String extValidator(String prodImg){
        //Only jpg, jpeg, png files are valid atm
        String[] extArr = {"jpg", "jpeg", "png"};
        String ext = "";
        for (int i = 0; i < extArr.length; i++){
            if (prodImg.contains(extArr[i])){
                ext = extArr[i];
            }
        }
        return ext;
    }
}