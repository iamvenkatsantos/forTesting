package com.onedata.sunexchange.logintask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;

import android.util.Log;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.onedata.sunexchange.logintask.utilities.StatusCode;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class viewProfileScreen extends AppCompatActivity {
    Button personal_edit,company_edit;
    ImageView map,faxIMV,emp,internet;
    String firstname,lastname,user,companyID,emailID,companyName,address,fax,mobile,website,res_addString,stateSring,pincodeString,cityString,countryString;
    String roleName=null;
    TextView EMAIL,PHONENUMBER,WEBSITE,ADDRESS,COMPANY,company_info,faxTV;
    View line;
    RelativeLayout relativeLayout;
    TextView fullName,email,first,last,websiteName,mobileNO,CompanyName,Address,res_add,state,pincode,city,country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.viewprofilescreen);

      final SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);

        //this.getSupportActionBar().setDisplayShowHomeEnabled(true);
       // this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        personal_edit=(Button)findViewById(R.id.personal_edit);
        company_edit=(Button)findViewById(R.id.company_edit);
        map=findViewById(R.id.mapIMV);
        faxIMV=findViewById(R.id.faxIMV);
        emp=findViewById(R.id.employeeIMV);
        internet=findViewById(R.id.internetIMV);
        company_info=findViewById(R.id.company_info);
        fullName = (TextView) findViewById(R.id.userName);
        email = (TextView) findViewById(R.id.email);
        first = (TextView) findViewById(R.id.firstName);
        last = (TextView) findViewById(R.id.lastName);
        websiteName = (TextView) findViewById(R.id.website);
        mobileNO = (TextView) findViewById(R.id.mobile);
        CompanyName = (TextView) findViewById(R.id.companyName);
        Address = (TextView) findViewById(R.id.address);
        faxTV = (TextView) findViewById(R.id.fax);
        res_add=findViewById(R.id.res_address);
        line=findViewById(R.id.line);
        relativeLayout=findViewById(R.id.relly_company);
        Intent viewProfile = getIntent();
       // Log.d("json value",""+user.getId());
        getCall();




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent in = new Intent(viewProfileScreen.this, dashBoardScreen.class);
                startActivity(in);
                this.finish();

                return true;
            case R.id.log_out:
                Intent intn = new Intent(viewProfileScreen.this, loginScreen.class);
                startActivity(intn);
                this.finish();
                Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void getActivity() {

        Intent in = new Intent(viewProfileScreen.this, dashBoardScreen.class);

        startActivity(in);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        getActivity();
    }

    public void getCall(){
        final SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        JsonParser request = new JsonParser
                (Request.Method.GET, "http://192.168.1.128:8080/api/v1/getprofile/"+sharedPreferences.getInt("UserId", 0),null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            state=findViewById(R.id.state);
                            city=findViewById(R.id.city);
                            pincode=findViewById(R.id.pin);
                            country=findViewById(R.id.country);
                            JSONObject data = response.getJSONObject("data");
                            JSONObject headers = response.getJSONObject("headers");
                            //  boolean com=false;
                            Log.d("json", "onResponse: "+data.toString());
                            firstname= String.valueOf(data.get("firstName"));
                            roleName=String.valueOf(data.get("role"));
                            check();
                            res_addString=String.valueOf(data.get("personalAddress"));
                            stateSring=String.valueOf(data.get("state"));
                            cityString=String.valueOf(data.get("city"));
                            pincodeString=String.valueOf(data.get("pincode"));
                            countryString=String.valueOf(data.get("country"));
                            if(res_addString.equals("null")){
                                res_add.setText("*need to be fill*");
                                res_addString="*need to be fill*";
                                stateSring="*need to be fill*";
                                cityString="*need to be fill*";
                                countryString="*need to be fill*";
                               /* state.setText("*need to be fill*");
                                city.setText("*need to be fill*");*/
                                pincode.setText("*need to be fill*");
                               /* country.setText("*need to be fill*");*/



                            }else{
                               /* res_add.setText(res_addString);
                                state.setText(stateSring);
                                city.setText(cityString);*/
                                pincode.setText(pincodeString);
                                res_add.setText(countryString);
                            }
                            check();
                            lastname= String.valueOf(data.get("lastName"));
                            user= String.valueOf(data.get("username"));
                            if(roleName.equalsIgnoreCase("CUSTOMER")){
                                //Toast.makeText(getApplicationContext(),"if",Toast.LENGTH_LONG).show();
                                emailID="";
                                companyName= "";
                                address ="";
                                mobile="";
                                website="";
                                email.setText("");
                                fullName.setText(user);/*
                                first.setText(firstname);
                                last.setText(lastname);*/
                                pincode.setText(pincodeString);
                                mobileNO.setText(mobile);
                                websiteName.setText("");
                                mobileNO.setText("");
                                CompanyName.setText("");
                                Address.setText("");
                            }
                            else if(roleName.equalsIgnoreCase("COMPANY_ADMINISTRATOR")){
                                Log.d("error","+++++++++++++++++++++++++++++++++++++++++++");
                                emailID=String.valueOf(data.get("email"));
                                companyName= String.valueOf(data.get("companyName"));
                                address =String.valueOf(data.get("address"));
                                mobile=String.valueOf(data.get("contactNo"));
                                fax=String.valueOf(data.get("faxNo"));
                                companyID=String.valueOf(data.get("companyId"));
                                website=String.valueOf(data.get("website"));
                                Log.d("error","+++++++++++++++++++++++++++++++++++++++++++"+emailID);
                                email.setText(emailID);
                                fullName.setText(user);
                                /*first.setText(firstname);
                                last.setText(lastname);*/
                                websiteName.setText(website);
                                mobileNO.setText(mobile);
                                CompanyName.setText(companyName);
                                Address.setText(address);
                                faxTV.setText(fax);
                                // Toast.makeText(getApplicationContext(),"else",Toast.LENGTH_LONG).show();
                            }

                            //role.setText(String.valueOf(data.get("role")));

                        } catch (JSONException e) {
                            Log.e("log", Log.getStackTraceString(e));



                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("missing fields", ""+error.networkResponse.data);
                        int statcode = error.networkResponse.statusCode;
                        String message = StatusCode.errorResponseToaster(statcode);
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }

                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorisation",sharedPreferences.getString("Token","") );
                Log.d("Token","======================================================"+sharedPreferences.getString("Token",""));
                return params;
            }};

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);


    }
    public void check() {
        Log.d("role", "Role:" + roleName);
        if (roleName.equalsIgnoreCase("CUSTOMER")) {
            personal_edit.setVisibility(View.VISIBLE);
            company_edit.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
           /* COMPANY.setVisibility(View.GONE);
            EMAIL.setVisibility(View.GONE);
            ADDRESS.setVisibility(View.GONE);*/
            fullName.setVisibility(View.GONE);
            /*email.setVisibility(View.GONE);
            first.setVisibility(View.GONE);
            last.setVisibility(View.GONE);*/
            websiteName.setVisibility(View.GONE);
            mobileNO.setVisibility(View.GONE);
            CompanyName.setVisibility(View.GONE);
            Address.setVisibility(View.GONE);
           /* PHONENUMBER.setVisibility(View.GONE);*/
            map.setVisibility(View.GONE);
            faxIMV.setVisibility(View.GONE);
            internet.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.GONE);
            emp.setVisibility(View.GONE);
            faxTV.setVisibility(View.GONE);
            company_info.setVisibility(View.GONE);
            personal_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(viewProfileScreen.this, editScreen.class);
                    in.putExtra("firstname", firstname);
                    in.putExtra("lastname", lastname);
                    in.putExtra("username", user);
                    in.putExtra("name", roleName);
                    in.putExtra("companyName", companyName);
                    in.putExtra("email", emailID);
                    in.putExtra("faxNo", fax);
                    in.putExtra("address", address);
                    in.putExtra("companyId", companyID);
                    in.putExtra("mobile", mobile);
                    in.putExtra("website", website);
                    in.putExtra("role", roleName);
                    startActivity(in);
                    finish();
                }
            });
        } else {
            personal_edit.setVisibility(View.INVISIBLE);
            company_edit.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
            /*COMPANY.setVisibility(View.VISIBLE);
            EMAIL.setVisibility(View.VISIBLE);
            ADDRESS.setVisibility(View.VISIBLE);*//*
            fullName.setVisibility(View.VISIBLE);
            *//*email.setVisibility(View.VISIBLE);
            first.setVisibility(View.VISIBLE);
            last.setVisibility(View.VISIBLE);*/
            faxTV.setVisibility(View.VISIBLE);
            websiteName.setVisibility(View.VISIBLE);
            mobileNO.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.VISIBLE);
            CompanyName.setVisibility(View.VISIBLE);
            Address.setVisibility(View.VISIBLE);
           /* PHONENUMBER.setVisibility(View.VISIBLE);
            WEBSITE.setVisibility(View.VISIBLE);*/
            map.setVisibility(View.VISIBLE);
            faxIMV.setVisibility(View.VISIBLE);
            internet.setVisibility(View.VISIBLE);
            emp.setVisibility(View.VISIBLE);
            company_info.setVisibility(View.VISIBLE);
            company_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(viewProfileScreen.this, editScreen.class);
                    in.putExtra("firstname", firstname);
                    in.putExtra("lastname", lastname);
                    in.putExtra("username", user);
                    in.putExtra("name", roleName);
                    in.putExtra("companyName", companyName);
                    in.putExtra("email", emailID);
                    in.putExtra("faxNo", fax);
                    in.putExtra("address", address);
                    in.putExtra("companyId", companyID);
                    in.putExtra("mobile", mobile);
                    in.putExtra("website", website);
                    in.putExtra("role", roleName);
                    startActivity(in);
                    finish();
                }
            });
            }
        }
    }

