package com.onedata.sunexchange.logintask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.onedata.sunexchange.logintask.utilities.StatusCode;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class signupScreen extends AppCompatActivity {
    TextView comTV,addTV,phoneTV,accountCom,TVsigninCom,accountP,TVsigninPersonal,faxTV,websiteTV;
    public EditText firstName,lastName,userName,email,password,reTypePassword,address,companyName,phoneNo,webiste,fax;
    CheckBox checkBox;
    Button signupPersonal,signupcom;
    RequestQueue requestQueue;
    JSONObject jsonObject;
    int flag;
    public static final String SHARED_PREFS="sharedPerfs";
    public static final String USERNAME="text";
    public static final String FIRSTNAME="text";
    public static final String LASTNAME="text";
    public static final String EMAIL="text";
    public static final String PASSWORD="text";
    public static final String RETYPEPASSWORD="text";
    public static final String ADDRESS="text";
    public static final String COMPANYNAME="text";
    public static final String PHONENUMEBR="text";
    public static final String WEBSITE="text";
    public static final String FAX="text";

    String first;
    String last;
    String user;
    String company,add,phoneNumber,Pass,reType,emailID,websiteString,faxString;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.signupscreen);
       /* getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation((float) 20.4);
        getSupportActionBar().setTitle("                   SIGN UP");
        getSupportActionBar().setDisplayShowTitleEnabled(true);*/
        comTV=findViewById(R.id.comTV);
        addTV=findViewById(R.id.addTV);
        faxTV =findViewById(R.id.faxTv);
        websiteTV=findViewById(R.id.websiteTV);
        phoneTV=findViewById(R.id.phoneTV);
        webiste=findViewById(R.id.website);
        fax=findViewById(R.id.fax);
        accountCom=findViewById(R.id.account);
        TVsigninCom=findViewById(R.id.SigninCompany);
        accountP=findViewById(R.id.textView14);
        TVsigninPersonal=findViewById(R.id.SigninPersonal);
        signupPersonal=findViewById(R.id.signupPersonal);
        signupcom=findViewById(R.id.signupCompany);
        checkBox=findViewById(R.id.checkBox);
        //UserDetails
        phoneNo=findViewById(R.id.phoneNumber);
        companyName=findViewById(R.id.company);
        address=findViewById(R.id.address);
        firstName =findViewById(R.id.firstName);
        lastName =findViewById(R.id.lastName);
        userName =findViewById(R.id.userName);
        password =findViewById(R.id.password);
        reTypePassword =findViewById(R.id.confirmPassword);
        companyName = findViewById(R.id.company);
        address = findViewById(R.id.address);
        phoneNo = findViewById(R.id.phoneNumber);
        email =findViewById(R.id.email);
        //JSON
        loadData();
        updateData();
         jsonObject = new JSONObject();
         TVsigninCom.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i = new Intent(getApplicationContext(), loginScreen.class);
                 startActivity(i);
                 finish();
             }
         });
         TVsigninPersonal.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i = new Intent(getApplicationContext(), loginScreen.class);
                 startActivity(i);
                 finish();
             }
         });


        signupPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                try {
                    jsonObject.put("firstName",  firstName.getText());
                    jsonObject.put("lastName",  lastName.getText());
                    jsonObject.put("username",  userName.getText());
                    jsonObject.put("email",  email.getText());
                    jsonObject.put("password",  password.getText());
                    jsonObject.put("retypedPassword",   reTypePassword.getText());
                    Log.d("password", "onCreate: "+reTypePassword.getText()+""+password.getText());
                    jsonObject.put( "isCompany", false);
                    jsonObject.put( "companyName",  "");
                    jsonObject.put("faxNo",  "");
                    jsonObject.put(  "website", "");
                    jsonObject.put( "address",  "");
                    jsonObject.put( "contactNo",  "");


                } catch (Exception e) {

                }
                Log.d("password", "onCreate: "+jsonObject.toString());
                               JsonParser request = new JsonParser
                        (Request.Method.POST, "http://192.168.1.128:8080/register", jsonObject, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Intent i = new Intent(getApplicationContext(), loginScreen.class);
                                    startActivity(i);
                                    finish();
                                } catch (Exception e) {
                                    Log.e("log", Log.getStackTraceString(e));
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                int statcode = error.networkResponse.statusCode;
                                String message = StatusCode.errorResponseToaster(statcode);
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

                                //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }

                        });
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);





            }
        });
        signupcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveData();
                try {
                    jsonObject.put("firstName",  firstName.getText());
                    jsonObject.put("lastName",  lastName.getText());
                    jsonObject.put("username",  userName.getText());
                    jsonObject.put("email",  email.getText());
                    jsonObject.put("password",  password.getText());
                    jsonObject.put("retypedPassword",   reTypePassword.getText());
                    Log.d("password", "onCreate: "+reTypePassword.getText()+""+password.getText());
                    jsonObject.put( "isCompany", true);
                    jsonObject.put( "companyName",  companyName.getText());
                    jsonObject.put("contactNo",  phoneNo.getText());
                    jsonObject.put(  "website", webiste.getText());
                    jsonObject.put( "address",  address.getText());
                    jsonObject.put( "faxNo",  fax.getText());


                } catch (Exception e) {

                }
                Log.d("password", "onCreate: "+jsonObject.toString());
                JsonParser request = new JsonParser
                        (Request.Method.POST, "http://192.168.1.128:8080/register", jsonObject, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Intent i = new Intent(getApplicationContext(), loginScreen.class);
                                    startActivity(i);
                                    finish();
                                } catch (Exception e) {
                                    Log.e("log", Log.getStackTraceString(e));
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                int statcode = error.networkResponse.statusCode;
                                String message = StatusCode.errorResponseToaster(statcode);
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

                                //  Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }

                        });
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);

            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    comTV.setVisibility(View.VISIBLE);
                    addTV.setVisibility(View.VISIBLE);
                    phoneTV.setVisibility(View.VISIBLE);
                    address.setVisibility(View.VISIBLE);
                    webiste.setVisibility(View.VISIBLE);
                    websiteTV.setVisibility(View.VISIBLE);
                    fax.setVisibility(View.VISIBLE);
                    faxTV.setVisibility(View.VISIBLE);
                    companyName.setVisibility(View.VISIBLE);
                    phoneNo.setVisibility(View.VISIBLE);
                    accountCom.setVisibility(View.VISIBLE);
                    TVsigninCom.setVisibility(View.VISIBLE);
                    signupPersonal.setVisibility(View.GONE);
                    signupcom.setVisibility(View.VISIBLE);
                    accountP.setVisibility(View.GONE);
                    TVsigninPersonal.setVisibility(View.GONE);


                }else
                {
                    comTV.setVisibility(View.GONE);
                    addTV.setVisibility(View.GONE);
                    phoneTV.setVisibility(View.GONE);
                    address.setVisibility(View.GONE);
                    companyName.setVisibility(View.GONE);
                    phoneNo.setVisibility(View.GONE);
                    accountCom.setVisibility(View.GONE);
                    TVsigninCom.setVisibility(View.GONE);
                    signupPersonal.setVisibility(View.VISIBLE);
                    signupcom.setVisibility(View.GONE);
                    accountP.setVisibility(View.VISIBLE);
                    webiste.setVisibility(View.GONE);
                    websiteTV.setVisibility(View.GONE);
                    fax.setVisibility(View.GONE);
                    faxTV.setVisibility(View.GONE);
                    TVsigninPersonal.setVisibility(View.VISIBLE);



                }



            }
        });
    }

    private void updateData() {



    }

    public void saveData(){

        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(USERNAME,userName.getText().toString());
        editor.putString(FIRSTNAME,firstName.getText().toString());
        editor.putString(LASTNAME,lastName.getText().toString());
        editor.putString(PASSWORD,password.getText().toString());
        editor.putString(RETYPEPASSWORD,reTypePassword.getText().toString());
        editor.putString(EMAIL,email.getText().toString());
        editor.putString(COMPANYNAME,companyName.getText().toString());
        editor.putString(ADDRESS,address.getText().toString());
        editor.putString(PHONENUMEBR,phoneNo.getText().toString());
        editor.putString(WEBSITE,webiste.getText().toString());
        editor.putString(FAX,fax.getText().toString());
        editor.apply();
   //  Toast.makeText(this,"Data are Saved",Toast.LENGTH_LONG).show();

    }
   public void loadData(){
       SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        first=sharedPreferences.getString(FIRSTNAME,"");
        last=sharedPreferences.getString(LASTNAME,"");
        user=sharedPreferences.getString(USERNAME,"");
        emailID=sharedPreferences.getString(EMAIL,"");
        company=sharedPreferences.getString(COMPANYNAME,"");
        Pass=sharedPreferences.getString(PASSWORD,"");
        reType=sharedPreferences.getString(RETYPEPASSWORD,"");
        add=sharedPreferences.getString(ADDRESS,"");
        phoneNumber=sharedPreferences.getString(PHONENUMEBR,"");
       websiteString=sharedPreferences.getString(WEBSITE,"");
       faxString=sharedPreferences.getString(FAX,"");
        //Toast.makeText(this,"data are Uploaded",Toast.LENGTH_LONG).show();
       /*firstName.setText(first);
       lastName.setText(last);
       userName.setText(user);
       email.setText(emailID);
       companyName.setText(company);
       password.setText(Pass);
       reTypePassword.setText(reType);
       address.setText(add);
       phoneNo.setText(phoneNumber);
*/
   }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                getActivity();

                return true;
            case R.id.log_out:
                Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void getActivity() {
        Intent in = new Intent(signupScreen.this, loginScreen.class);
        startActivity(in);
        this.finish();

    }

    @Override
    public void onBackPressed() {
        getActivity();
        super.onBackPressed();
    }

}
