package com.onedata.sunexchange.logintask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.onedata.sunexchange.logintask.bean.Role;
import com.onedata.sunexchange.logintask.bean.UserProfile;
import com.onedata.sunexchange.logintask.utilities.StatusCode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class KycViewDetails extends AppCompatActivity {
TextView fistNameTV,lastNameTV,middleNameTV,dobTV,addressTV,areaTV,cityTV,streetTV,stateTV,countryTV,pincodeTV,businessNameTV,businessAddressTV,taxCountryTV,taxSameCountryTV,tinNumberTV,taxAnotherCountryTV;;
String roleName;
ScrollView scroll_view;
Button edit_personal,edit_company,viewdoc_personal,viewdoc_company;
TextView company;
LinearLayout linearLayout;
RelativeLayout relativeLayout;
String fistName,lastName,middleName,dob,address,area,city,street,state,country,pincode,businessName,businessAddress,taxCountry,taxSameCountry,tinNumber,taxAnotherCountry;
String[] files;
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc_view_details);
        fistNameTV=findViewById(R.id.firstName);
        lastNameTV=findViewById(R.id.lastName);
        middleNameTV=findViewById(R.id.middleName);
        dobTV=findViewById(R.id.DateofBirth);
        addressTV=findViewById(R.id.address);
        areaTV=findViewById(R.id.area);
        cityTV=findViewById(R.id.city);
        scroll_view=findViewById(R.id.scrollView);
        streetTV=findViewById(R.id.street);
        sharedPreferences = getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
        stateTV=findViewById(R.id.state);
        countryTV=findViewById(R.id.country);
        pincodeTV=findViewById(R.id.pincode);
        edit_personal=findViewById(R.id.edit_personal);
        edit_company=findViewById(R.id.edit_company);
        viewdoc_personal=findViewById(R.id.viewdoc_personal);
        viewdoc_company=findViewById(R.id.viewdoc_company);
        company=findViewById(R.id.companyid);
        relativeLayout=findViewById(R.id.rel2);
        linearLayout=findViewById(R.id.linear);
        taxAnotherCountryTV=findViewById(R.id.taxAnotherCountry);
        taxSameCountryTV=findViewById(R.id.taxSameCountry);
        taxCountryTV=findViewById(R.id.taxCountry);
        tinNumberTV=findViewById(R.id.tin_no);
        businessAddressTV=findViewById(R.id.businessAddress);
        businessNameTV=findViewById(R.id.businessName);
        getCall();
        viewdoc_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(KycViewDetails.this, KycGalleryView.class);
                in.putExtra("firstname",fistName);
                in.putExtra("lastname",lastName);
                in.putExtra("middlename",middleName);
                in.putExtra("dateOfBirth",dob);
                in.putExtra("roleName",roleName);
                in.putExtra("residentialAddress",address);
                in.putExtra("area",area);
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
        viewdoc_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(KycViewDetails.this, KycGalleryView.class);
                in.putExtra("firstname",fistName);
                in.putExtra("lastname",lastName);
                in.putExtra("middlename",middleName);
                in.putExtra("dateOfBirth",dob);
                in.putExtra("roleName",roleName);
                in.putExtra("residentialAddress",address);
                in.putExtra("area",area);
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
    public void getCall(){
        final SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        JsonParser request = new JsonParser
                (Request.Method.GET, "http://192.168.1.128:8080/api/v1/getkycdetails/"+sharedPreferences.getInt("UserId", 0),null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            JSONObject data = response.getJSONObject("data");
                            JSONObject headers = response.getJSONObject("headers");
                            Gson gson = new Gson();
                            String userDetail = sharedPreferences.getString("UserDetail","");
                            UserProfile use = gson.fromJson(userDetail,UserProfile.class);
                            String role =  use.getRole().get(0).getName();
                            roleName=role;
                            Log.d("json", "onResponse: "+data.toString());
                            //Log.d("json", "String: "+role);
                            fistName= String.valueOf(data.get("firstName"));
                            lastName= String.valueOf(data.get("lastName"));
                            middleName= String.valueOf(data.get("middleName"));
                            dob= String.valueOf(data.get("dateOfBirth"));
                            Toast.makeText(getApplicationContext(),role,Toast.LENGTH_LONG).show();
                            address= String.valueOf(data.get("residentialAddress"));
                            area= String.valueOf(data.get("area"));
                            city= String.valueOf(data.get("city"));
                            street= String.valueOf(data.get("street"));
                            state= String.valueOf(data.get("state"));
                            country= String.valueOf(data.get("country"));
                            pincode= String.valueOf(data.get("pincode"));
                            JSONArray jsonArray = (JSONArray) data.get("files");
                                 files=new String[jsonArray.length()];
                            for(int i=0; i<files.length; i++) {
                                files[i]=jsonArray.getString(i);

                            }
                            Log.d("files", "onResponse: "+files[0]);

                            //roleName=String.valueOf(data.get("name"));
                            if(role.equals("CUSTOMER")){
// Gets the layout params that will allow you to resize the layout
                                viewdoc_personal.setVisibility(View.VISIBLE);
                                viewdoc_company.setVisibility(View.GONE);
                                company.setVisibility(View.GONE);
                                relativeLayout.setVisibility(View.GONE);
                             /*   ScrollView relativeLayout =  findViewById(R.id.scrollView);
                                relativeLayout.getLayoutParams().height = 1000;
                                relativeLayout.getLayoutParams().width = LinearLayout.LayoutParams.FILL_PARENT;*/

                            }else
                                {
                                    viewdoc_personal.setVisibility(View.GONE);
                                    viewdoc_company.setVisibility(View.VISIBLE);
                                company.setVisibility(View.VISIBLE);
                                relativeLayout.setVisibility(View.VISIBLE);
                                businessName=String.valueOf(data.get("businessName"));
                                businessAddress=String.valueOf(data.get("businessAddress"));
                                taxCountry=String.valueOf(data.get("taxCountry"));
                                taxSameCountry=String.valueOf(data.get("isTaxSameCountry"));
                                tinNumber=String.valueOf(data.get("tinNo"));
                                taxAnotherCountry=String.valueOf(data.get("isTaxAnotherCountry"));
                                taxAnotherCountryTV.setText(taxAnotherCountry);
                                taxSameCountryTV.setText(taxSameCountry);
                                tinNumberTV.setText(tinNumber);
                                taxCountryTV.setText(taxCountry);
                                businessNameTV.setText(businessName);
                                businessAddressTV.setText(businessAddress);
                                /*ScrollView relativeLayout =  findViewById(R.id.scrollView);
                                relativeLayout.getLayoutParams().height = 3500;
                                relativeLayout.getLayoutParams().width = LinearLayout.LayoutParams.FILL_PARENT;*/

                                //scroll_view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, 350));
                            }

                                //Toast.makeText(getApplicationContext(),"if",Toast.LENGTH_LONG).show();
                                fistNameTV.setText(fistName);
                                lastNameTV.setText(lastName);
                              /*  middleNameTV.setText(middleName);
                                areaTV.setText(area);*/
                                dobTV.setText(dob);
                                addressTV.setText(address);
                                /*cityTV.setText(city);
                                streetTV.setText(street);
                                stateTV.setText(state);
                                countryTV.setText(country);
                                pincodeTV.setText(pincode);*/

                                /*fistNameTV.setText(fistName);
                                lastNameTV.setText(lastName);
                                middleNameTV.setText(middleName);
                                areaTV.setText(area);
                                addressTV.setText(address);
                                cityTV.setText(city);
                                streetTV.setText(street);
                                stateTV.setText(state);
                                countryTV.setText(country);
                                pincodeTV.setText(pincode);*/



                            //role.setText(String.valueOf(data.get("role")));

                        } catch (JSONException e) {
                            Log.e("log", Log.getStackTraceString(e));



                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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
                return params;
            }};

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
    private void getActivity() {
        String role;
        role = sharedPreferences.getString("roleName", "");
        if (role.equals("COMPANY_ADMINISTRATOR")) {
            Intent in = new Intent(KycViewDetails.this, dashBoardScreen.class);
            in.putExtra("firstOrSecond", roleName);
            startActivity(in);
            this.finish();
        } else
        {
            Intent in = new Intent(KycViewDetails.this, DashScreenPresonal.class);
            in.putExtra("firstOrSecond", roleName);
            startActivity(in);
            this.finish();
        }

    }
    @Override
    public void onBackPressed() {
        getActivity();
        super.onBackPressed();
    }
}
