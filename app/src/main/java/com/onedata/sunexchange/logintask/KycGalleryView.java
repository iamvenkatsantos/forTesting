package com.onedata.sunexchange.logintask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.mindorks.placeholderview.PlaceHolderView;
import com.onedata.sunexchange.logintask.bean.GalleryItem;
import com.onedata.sunexchange.logintask.utilities.StatusCode;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class KycGalleryView extends AppCompatActivity implements View.OnClickListener{
    SharedPreferences sharedPreferences;
    ImageView btprevious, btnext;
    Button edit;
    ImageView myImage;
    String[] fileName;
    RequestQueue requestQueue;
    String fistName,roleName,lastName,middleName,dob,address,area,city,street,state,country,pincode,businessName,businessAddress,taxCountry,taxSameCountry,tinNumber,taxAnotherCountry;
    String[] files;
    public int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc_gallery_view);
         Bundle b = getIntent().getExtras();
         fileName = b.getStringArray("files");
     requestQueue = Volley.newRequestQueue(this);

        sharedPreferences = getSharedPreferences("LoginDetails",MODE_PRIVATE);
        Log.d("userid+++++++", "onCreate:+++++++++++++++++++++++++++++++++++++++++ "+sharedPreferences.getInt("userId",0));
        fistName=getIntent().getExtras().getString("firstname");
        lastName =getIntent().getExtras().getString("lastname");
        middleName =getIntent().getExtras().getString("middlename");
        dob=getIntent().getExtras().getString("dateOfBirth");
        address=getIntent().getExtras().getString("residentialAddress");
        area=getIntent().getExtras().getString("area");
        city=getIntent().getExtras().getString("city");
        street=getIntent().getExtras().getString("street");
        state=getIntent().getExtras().getString("state");
        country=getIntent().getExtras().getString("country");
        pincode=getIntent().getExtras().getString("pincode");
        businessName=getIntent().getExtras().getString("businessName");
        businessAddress=getIntent().getExtras().getString("businessAddress");
        taxCountry=getIntent().getExtras().getString("taxCountry");
        taxSameCountry=getIntent().getExtras().getString("isTaxSameCountry");
        taxAnotherCountry=getIntent().getExtras().getString("isTaxAnotherCountry");
        tinNumber=getIntent().getExtras().getString("tinNo");
        Bundle bi = getIntent().getExtras();
        files = bi.getStringArray("files");
        roleName=getIntent().getExtras().getString("roleName");

           changeImage();
            btprevious =  findViewById(R.id.pervious);
            btnext =findViewById(R.id.next);
            edit=findViewById(R.id.edit);
            myImage = (ImageView) findViewById(R.id.myImage);
            btprevious.setOnClickListener(this);
            btnext.setOnClickListener(this);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(KycGalleryView.this, kycPersonal.class);
                    in.putExtra("firstname",fistName);
                    in.putExtra("lastname",lastName);
                    in.putExtra("middlename",middleName);
                    in.putExtra("dateOfBirth",dob);
                    in.putExtra("roleName",roleName);
                    in.putExtra("residentialAddress",address);
                    in.putExtra("area",area);
                    in.putExtra("firstOrSecond","SRCONDTIME");
                    in.putExtra("city",city);
                    in.putExtra("street",street);
                    in.putExtra("state",state);
                    in.putExtra("country",country);
                    in.putExtra("pincode",pincode);
                    Bundle b= new Bundle();
                    b.putStringArray("files",files);
                    in.putExtras(b);
                    in.putExtra("businessName",businessName);
                    in.putExtra("businessAddress",businessAddress);
                    in.putExtra("taxCountry",taxCountry);
                    in.putExtra("isTaxSameCountry",taxAnotherCountry);
                    in.putExtra("tinNo",tinNumber);
                    startActivity(in);
                    finish();
                }
            });
        }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.pervious:
                i++;

                if(i==fileName.length) // switch to 11 because you got 10 images
                {
                    i--; // switch to 10, same reason
                }

                changeImage();
                break;

            case R.id.next:
                i--;

                if(i==-1)
                {
                    i=0; // you can leave it this way or improve it later
                }

                changeImage();
                break;
        }
    }

    public void changeImage()
    {
        myImage = (ImageView) findViewById(R.id.myImage);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        ImageRequest imageRequest = new ImageRequest(
                    "http://192.168.1.128:8080/api/noauth/getkycdetail/"+sharedPreferences.getInt("UserId",0)+"/"+fileName[i], // Image URL
                    new Response.Listener<Bitmap>() { // Bitmap listener
                        @Override
                        public void onResponse(Bitmap response) {
                            myImage.setImageBitmap(response);
                        }
                    },
                    0, // Image width
                    0, // Image height
                    ImageView.ScaleType.CENTER_CROP, // Image scale type
                    Bitmap.Config.RGB_565, //Image decode configuration
                    new Response.ErrorListener() { // Error listener
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Do something with error response
                            error.printStackTrace();
                            int statcode = error.networkResponse.statusCode;
                            String message = StatusCode.errorResponseToaster(statcode);
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        }
                    }
            ) {

                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorisation", sharedPreferences.getString("Token", ""));
                    return params;
                }
            };
            // Add ImageRequest to the RequestQueue
            requestQueue.add(imageRequest);
        }
    }

