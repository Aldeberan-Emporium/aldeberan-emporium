package com.example.aldeberan.AdminFragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.aldeberan.Activity.home_product;
import com.example.aldeberan.R;
import com.example.aldeberan.models.ProductModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AdminPanelUpdateProductFragment extends Fragment implements View.OnClickListener{
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
    public AlphaAnimation alphaAnimation;

    View myFragmentView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.activity_admin_panel_update_product, container, false);

        ((home_product) getActivity()).setActionBarTitle("Update Bread");

        //Predeclare components
        prodNameLbl = myFragmentView.findViewById(R.id.prodName);
        prodSKULbl = myFragmentView.findViewById(R.id.prodSKU);
        prodStockLbl = myFragmentView.findViewById(R.id.prodStock);
        prodPriceLbl = myFragmentView.findViewById(R.id.prodPrice);
        prodAvailSwitch = myFragmentView.findViewById(R.id.prodAvail);
        prodImgView = myFragmentView.findViewById(R.id.prodImg);

        retrieveData();

        //Firebase Cloud Storage reference
        storageRef = FirebaseStorage.getInstance().getReference();

        //Buttons
        submitBtn = myFragmentView.findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(this);
        resetBtn = myFragmentView.findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(this);
        imgBtn = myFragmentView.findViewById(R.id.imageBtn);
        imgBtn.setOnClickListener(this);

        //On Submit
        onSubmitThrobber = myFragmentView.findViewById(R.id.onSubmitThrobber);
        onSubmitView = myFragmentView.findViewById(R.id.onSubmitView);

        //Set SKU when product name on change
        EditText prodName = myFragmentView.findViewById(R.id.prodName);
        EditText prodSKU = myFragmentView.findViewById(R.id.prodSKU);

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

        prodName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        //Limit product stock to 5 digits (max 99999)
        EditText prodStock = myFragmentView.findViewById(R.id.prodStock);
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

        prodStock.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        //Limit product price to 7,2 decimal places (max 99999.99)
        EditText prodPrice = myFragmentView.findViewById(R.id.prodPrice);
        prodPrice.setFilters(new InputFilter[] {filter});

        prodPrice.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        return myFragmentView;
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
            result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (result.getResultCode() == 100){
                    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
                        if (Environment.isExternalStorageManager()){
                            pickImgFromGallery();
                        }
                    }
                }
                else if (result.getResultCode() != 100 && result.getResultCode() != 101){
                    imgURI = data.getData();
                    Log.i("IMGURI", imgURI.toString());
                    if (imgURI != null){
                        ImageView img = myFragmentView.findViewById(R.id.prodImg);
                        img.setImageURI(imgURI);
                    }
                }
                else{
                    takePermissions();
                }
            }
    });

    private void pickImgFromGallery(){
        String[] mimeTypes = {"image/jpeg", "image/jpg", "image/png"};
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                .setType("image/*")
                .putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("requestCode", 102);
        activityResultLauncher.launch(intent);
    }

    private boolean isPermissionGranted(){
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager();
        }
        else{
            int readExternalStorage = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
            return readExternalStorage == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void takePermissions(){
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
            try{
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package%s", getActivity().getApplicationContext().getPackageName())));
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
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
        }
    }

    //Hide keyboard when out of focus
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) this.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
                ImageView img = myFragmentView.findViewById(R.id.prodImg);

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
                        //Redirect back to load products fragment
                        AdminPanelLoadProductFragment productFragment= new AdminPanelLoadProductFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, productFragment)
                                .addToBackStack(null)
                                .commit();
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
                                onSubmitAnim();
                                //Redirect back to load products fragment
                                AdminPanelLoadProductFragment productFragment= new AdminPanelLoadProductFragment();
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, productFragment)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            else {
                                Log.i("UP","Upload failed: ");
                                img.setImageResource(R.drawable.upload_img_error);
                            }
                        });
                    }

                }
                else{
                    Toast.makeText(getContext(), "All inputs are required!", Toast.LENGTH_LONG).show();
                    img.setImageResource(R.drawable.upload_img_error);
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

    private void onSubmitAnim() {
        //On load throbber fade out
        alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(2000);
        onSubmitThrobber.startAnimation(alphaAnimation);
        onSubmitView.startAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                onSubmitThrobber.setVisibility(View.VISIBLE);
                onSubmitView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                onSubmitThrobber.setVisibility(View.GONE);
                onSubmitView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    private String getFileExt(Uri uri){
        ContentResolver cr = getActivity().getContentResolver();
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
        prevProdName = getArguments().getString("prodName");
        prevProdSKU = getArguments().getString("prodSKU");
        prevProdImg = getArguments().getString("prodImg");
        prevProdID = Integer.parseInt(getArguments().getString("prodID"));
        prevProdStock = Integer.parseInt(getArguments().getString("prodStock"));
        prevProdAvail = Integer.parseInt(getArguments().getString("prodAvail"));
        prevProdPrice = Double.parseDouble(getArguments().getString("prodPrice"));

        prodNameLbl.setText(prevProdName);
        prodSKULbl.setText(prevProdSKU);
        prodStockLbl.setText(Integer.toString(prevProdStock));
        prodPriceLbl.setText(Double.toString(prevProdPrice));
        boolean isChecked = prevProdAvail == 1 ? true : false;
        prodAvailSwitch.setChecked(isChecked);
        Glide.with(this).load(prevProdImg).override(450, 450).into(prodImgView);
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
