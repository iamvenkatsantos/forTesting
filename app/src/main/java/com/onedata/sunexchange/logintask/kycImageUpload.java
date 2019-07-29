package com.onedata.sunexchange.logintask;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.annotations.SerializedName;
import com.onedata.sunexchange.logintask.helper.AppHelper;
import com.onedata.sunexchange.logintask.utilities.MultipartRequest;
import com.onedata.sunexchange.logintask.utilities.StatusCode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class kycImageUpload extends AppCompatActivity implements View.OnClickListener{

    ImageView img, getNationalIdCard, getLicense, getPassport;
    Button nationalIdCardButton, licenseButton, passportButton;
    Button b1, b2;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    final int CODE_GALLERY_REQUEST = 999;
    Bitmap nationalIdCard, license, passport;
    ProgressDialog dialog;
    RequestQueue requestQueue;
    Uri filepath;
    int flag;
    String encode;
    JSONObject jsonObject;
    int flagForDifferent;
    CheckBox licenseCheck,passportCheck,nationalIDCheck;
    boolean licenseBoolean,passportBoolean,nationalBoolean;
    String kycSecondJson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_image_upload);
        // b1=(Button)findViewById(R.id.choose);
        b2=(Button)findViewById(R.id.upload);
        img=(ImageView)findViewById(R.id.img);

        //getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nationalIdCardButton=findViewById(R.id.aadharButton);
        licenseButton=findViewById(R.id.panButton);
        passportButton=findViewById(R.id.passportButton);
        getNationalIdCard=findViewById(R.id.getAadhar);
        getLicense=findViewById(R.id.getPan);
        getPassport=findViewById(R.id.getPassport);



        licenseCheck=findViewById(R.id.licenseCheck);
        passportCheck=findViewById(R.id.passportCheck);
        nationalIDCheck=findViewById(R.id.nationalIDCheck);
        licenseCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    licenseButton.setVisibility(View.VISIBLE);
                    if(nationalBoolean&&passportBoolean){
                        nationalIdCardButton.setVisibility(View.VISIBLE);
                        passportButton.setVisibility(View.VISIBLE);
                    }else if(passportBoolean){
                        nationalIdCardButton.setVisibility(View.INVISIBLE);
                        passportButton.setVisibility(View.VISIBLE);
                    }else if(nationalBoolean){
                        nationalIdCardButton.setVisibility(View.VISIBLE);
                        passportButton.setVisibility(View.INVISIBLE);
                    }

                    licenseBoolean=b;
                }else{
                    licenseButton.setVisibility(View.INVISIBLE);
                    licenseBoolean=b;
                }
            }
        });
        passportCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    passportButton.setVisibility(View.VISIBLE);
                    if(nationalBoolean&&licenseBoolean){
                        nationalIdCardButton.setVisibility(View.VISIBLE);
                        licenseButton.setVisibility(View.VISIBLE);
                    }else if(licenseBoolean){
                        nationalIdCardButton.setVisibility(View.INVISIBLE);
                        licenseButton.setVisibility(View.VISIBLE);
                    }else if(nationalBoolean){
                        nationalIdCardButton.setVisibility(View.VISIBLE);
                        licenseButton.setVisibility(View.INVISIBLE);
                    }
                    passportBoolean=b;

                }else{
                    passportButton.setVisibility(View.INVISIBLE);
                    passportBoolean=b;
                }
            }
        });
        nationalIDCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    nationalIdCardButton.setVisibility(View.VISIBLE);
                    if(passportBoolean&&licenseBoolean)
                    {
                        licenseButton.setVisibility(View.VISIBLE);
                        passportButton.setVisibility(View.VISIBLE);
                    }else if(passportBoolean){
                        licenseButton.setVisibility(View.INVISIBLE);
                        passportButton.setVisibility(View.VISIBLE);
                    }else if(licenseBoolean){
                        licenseButton.setVisibility(View.VISIBLE);
                        passportButton.setVisibility(View.INVISIBLE);
                    }
                    else
                        nationalBoolean=b;
                }
                else{
                    nationalIdCardButton.setVisibility(View.INVISIBLE);
                    nationalBoolean=b;
                }
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails",MODE_PRIVATE);
                dialog=new ProgressDialog(kycImageUpload.this);
                dialog.setTitle("Uploading");
                dialog.setMessage("please wait...");
                // dialog.show();
                final JSONObject jsonObject = new JSONObject();
                try {
                    Intent userProfile = getIntent();
                    jsonObject.put("kycPersonalDetails", new JSONObject(userProfile.getStringExtra("KycFirstJson")));
                    jsonObject.put("kycBusinessDetail", new JSONObject(userProfile.getStringExtra("KycSecondJson")));
                    Log.d("log", "kycfirst"+jsonObject.toString());
                } catch (Exception e) {
                    Log.d("log", "kycfirst"+e.toString());
                }
                JsonParser request = new JsonParser
                        (Request.Method.POST, "http://192.168.1.128:8080/api/v1/kyc", jsonObject, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.d("header response", "++++++++++success+++++++");
                                    onGetHeader();

                                } catch (Exception e) {
                                    Log.e("log", Log.getStackTraceString(e));
                                }
                            }
                        }, new Response.ErrorListener() {
                            class Error {
                                @SerializedName("field")
                                private String field;
                                @SerializedName("defaultMessage")
                                private String defaultMessage;

                                public String getField() {
                                    return field;
                                }

                                public void setField(String field) {
                                    this.field = field;
                                }

                                public String getDefaultMessage() {
                                    return defaultMessage;
                                }

                                public void setDefaultMessage(String defaultMessage) {
                                    this.defaultMessage = defaultMessage;
                                }
                            }
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                int statcode = error.networkResponse.statusCode;
                                String message =StatusCode.errorResponseToaster(statcode);
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                            }

                        }){

                    @Override
                    public Map<String, String> getHeaders () throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Authorisation", sharedPreferences.getString("Token",""));
                        return params;
                    }


                };
                requestQueue = Volley.newRequestQueue(kycImageUpload.this);
                requestQueue.add(request);


            }

        });

    }



    private void onGetHeader() {
        final SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails",MODE_PRIVATE);
        MultipartRequest multipartRequest = new MultipartRequest(Request.Method.POST, "http://192.168.1.128:8080/api/v1/document/"+sharedPreferences.getInt("UserId", 0), new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                Toast.makeText(getApplicationContext(),"Image Uploaded",Toast.LENGTH_LONG).show();
                Intent in = new Intent(kycImageUpload.this, dashBoardScreen.class);
                startActivity(in);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               /* NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message+" Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message+ " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message+" Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();*/
                int statcode = error.networkResponse.statusCode;
                String message =StatusCode.errorResponseToaster(statcode);
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders () throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorisation", sharedPreferences.getString("Token", ""));
                return params;
            }
            @Override
            protected Map<String, MultipartRequest.DataPart> getByteData() {
                Map<String, MultipartRequest.DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                if(nationalIdCard==null){
                    Log.d("bitmap", "getByteData:+++++++++++++++++ "+null);
                    Log.d("bitmap", "getByteData:+++++++++++++++++ "+null);
                }
                // Log.d("bitmap", "getByteData:+++++++++++++++++ "+AppHelper.getFileDataFromDrawable(getBaseContext(),nationalIdCard).length);
                if(nationalBoolean){
                    params.put("nationalid", new MultipartRequest.DataPart("file_nic.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(),nationalIdCard), "image/jpeg"));

                }
                if(licenseBoolean){
                    params.put("license", new MultipartRequest.DataPart("file_license.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(),license), "image/jpeg"));

                }
                if(passportBoolean){
                    params.put("passport", new MultipartRequest.DataPart("file_passport.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(),passport), "image/jpeg"));

                }

                //  params.put("cover", new MultipartRequest.DataPart("file_cover.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), mCoverImage.getDrawable()), "image/jpeg"));

                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(kycImageUpload.this);
        requestQueue.add(multipartRequest);
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==CODE_GALLERY_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                // Toast.makeText(getApplicationContext(),"PERMISSION granted",Toast.LENGTH_LONG).show();
                startActivityForResult(Intent.createChooser(intent, "select image"), CODE_GALLERY_REQUEST);
            } else {
                Toast.makeText(getApplicationContext(), "PERMISSION DENIED", Toast.LENGTH_LONG).show();

            }
            return;
        }
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(flagForDifferent(flagForDifferent)==0){
            if(flagStatus(flag)==0){

                if(requestCode==CODE_GALLERY_REQUEST&&resultCode==RESULT_OK&&data!=null){
                    filepath=data.getData();
                    try {
                        InputStream inputStream=getContentResolver().openInputStream(filepath);

                        license = BitmapFactory.decodeStream(inputStream);
                        getLicense.requestLayout();
                        getLicense.setImageBitmap(license);

                        //Toast.makeText(getApplicationContext(),"IMAGES SET SUCCESSFULLY" ,Toast.LENGTH_LONG).show();
                    } catch (FileNotFoundException e) {
                        Toast.makeText(getApplicationContext(),e.toString() ,Toast.LENGTH_LONG).show();
                    }
                }


            }
            else{
                if(requestCode==REQUEST_IMAGE_CAPTURE&&resultCode==RESULT_OK)
                {
                    Bundle extras=data.getExtras();
                    Bitmap map= (Bitmap) extras.get("data");
                    jsonObject = new JSONObject();
                    try {
                        jsonObject.put("image",map );
                    }
                    catch (Exception e){

                    }
                    int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, getResources().getDisplayMetrics());
                    getLicense.getLayoutParams().height = dimensionInDp;
                    getLicense.getLayoutParams().width = dimensionInDp;
                    getLicense.requestLayout();
                    getLicense.setImageBitmap(map);
                    license=map;
                }
            }
        }
        else if(flagForDifferent(flagForDifferent)==1)
        {
            if(flagStatus(flag)==0){

                if(requestCode==CODE_GALLERY_REQUEST&&resultCode==RESULT_OK&&data!=null){
                    filepath=data.getData();
                    try {
                        InputStream inputStream=getContentResolver().openInputStream(filepath);

                        nationalIdCard = BitmapFactory.decodeStream(inputStream);
                        getNationalIdCard.requestLayout();
                        getNationalIdCard.setImageBitmap(nationalIdCard);
                        //Toast.makeText(getApplicationContext(),"IMAGES SET SUCCESSFULLY" ,Toast.LENGTH_LONG).show();

                    } catch (FileNotFoundException e) {
                        Toast.makeText(getApplicationContext(),e.toString() ,Toast.LENGTH_LONG).show();
                    }
                }


            }
            else{
                if(requestCode==REQUEST_IMAGE_CAPTURE&&resultCode==RESULT_OK)
                {
                    Bundle extras=data.getExtras();
                    Bitmap map= (Bitmap) extras.get("data");
                    jsonObject = new JSONObject();
                    try {
                        jsonObject.put("image",map );
                    }
                    catch (Exception e){

                    }
                    int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, getResources().getDisplayMetrics());

                    getNationalIdCard.getLayoutParams().height = dimensionInDp;
                    getNationalIdCard.getLayoutParams().width = dimensionInDp;
                    getNationalIdCard.requestLayout();
                    getNationalIdCard.setImageBitmap(map);
                    nationalIdCard=map;
                    //Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();

                }
            }
        }

        else{
            if(flagStatus(flag)==0){

                if(requestCode==CODE_GALLERY_REQUEST&&resultCode==RESULT_OK&&data!=null){
                    filepath=data.getData();
                    try {
                        InputStream inputStream=getContentResolver().openInputStream(filepath);
                        passport = BitmapFactory.decodeStream(inputStream);
                        getPassport.requestLayout();

                        getPassport.setImageBitmap(passport);

                        //Toast.makeText(getApplicationContext(),"IMAGES SET SUCCESSFULLY" ,Toast.LENGTH_LONG).show();
                    } catch (FileNotFoundException e) {
                        Toast.makeText(getApplicationContext(),e.toString() ,Toast.LENGTH_LONG).show();
                    }
                }


            }
            else{
                if(requestCode==REQUEST_IMAGE_CAPTURE&&resultCode==RESULT_OK)
                {
                    Bundle extras=data.getExtras();
                    Bitmap map= (Bitmap) extras.get("data");
                    jsonObject = new JSONObject();
                    try {
                        jsonObject.put("image",map );
                    }
                    catch (Exception e){

                    }
                    int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, getResources().getDisplayMetrics());
                    getPassport.getLayoutParams().height = dimensionInDp;
                    getPassport.getLayoutParams().width = dimensionInDp;
                    getPassport.requestLayout();
                    getPassport.setImageBitmap(map);
                    passport=map;

                }
            }


        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String imageTo(Bitmap bitmap){
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,0,outputStream);
        byte[] imageByte = outputStream.toByteArray();
        encode = Base64.encodeToString(imageByte,Base64.DEFAULT);
        return encode;
    }




    public int flagStatus(int i) {

        flag=i;

        return flag;

    }
  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.image_upload,menu);
        return true;
    }
*/
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity();
                break;

          /*  case R.id.capture:

                Intent Image = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (Image.resolveActivity(getPackageManager()) != null)
                    startActivityForResult(Image, REQUEST_IMAGE_CAPTURE);
                //onSendServer();
                flagStatus(1);
                 break;
            case R.id.choose:
                ActivityCompat.requestPermissions(kycImageUpload.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALLERY_REQUEST);
                flagStatus(0);
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }*/
    private void getActivity() {
        Intent in = new Intent(kycImageUpload.this, kycTax.class);
        startActivity(in);
        this.finish();

    }

    @Override
    public void onBackPressed() {
        getActivity();
        super.onBackPressed();
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.aadharButton:
                PopupMenu aadharMenu=new PopupMenu(kycImageUpload.this,nationalIdCardButton);
                aadharMenu.getMenuInflater().inflate(R.menu.image_upload,aadharMenu.getMenu());
                aadharMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId())
                        {
                            case R.id.capture:
                                Intent Image = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (Image.resolveActivity(getPackageManager()) != null)
                                    startActivityForResult(Image, REQUEST_IMAGE_CAPTURE);
                                //onSendServer();
                                flagStatus(1);
                                break;
                            case R.id.choose :

                                ActivityCompat.requestPermissions(kycImageUpload.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                        CODE_GALLERY_REQUEST);
                                flagStatus(0);
                                break;

                        }


                        return true;
                    }
                });
                aadharMenu.show();
                aadharMenu.setGravity(0);
                flagForDifferent(1);
                break;

            case R.id.panButton:
                PopupMenu panMenu=new PopupMenu(kycImageUpload.this,licenseButton);
                panMenu.getMenuInflater().inflate(R.menu.image_upload,panMenu.getMenu());
                panMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId())
                        {
                            case R.id.capture:
                                Intent Image = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (Image.resolveActivity(getPackageManager()) != null)
                                    startActivityForResult(Image, REQUEST_IMAGE_CAPTURE);
                                //onSendServer();
                                flagStatus(1);
                                break;
                            case R.id.choose :

                                ActivityCompat.requestPermissions(kycImageUpload.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                        CODE_GALLERY_REQUEST);
                                flagStatus(0);
                                break;

                        }


                        return true;
                    }
                });
                panMenu.show();
                panMenu.setGravity(0);
                flagForDifferent(0);
                break;

            case R.id.passportButton:
                PopupMenu passPortMenu=new PopupMenu(kycImageUpload.this,passportButton);
                passPortMenu.getMenuInflater().inflate(R.menu.image_upload,passPortMenu.getMenu());
                passPortMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId())
                        {
                            case R.id.capture:
                                Intent Image = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (Image.resolveActivity(getPackageManager()) != null)
                                    startActivityForResult(Image, REQUEST_IMAGE_CAPTURE);
                                //onSendServer();
                                flagStatus(1);
                                break;
                            case R.id.choose :

                                ActivityCompat.requestPermissions(kycImageUpload.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                        CODE_GALLERY_REQUEST);
                                flagStatus(0);
                                break;

                        }


                        return true;
                    }
                });
                passPortMenu.show();
                passPortMenu.setGravity(0);
                flagForDifferent(-1);
                break;


        }



    }

    private int flagForDifferent(int i) {

        return  flagForDifferent=i;
    }
}

