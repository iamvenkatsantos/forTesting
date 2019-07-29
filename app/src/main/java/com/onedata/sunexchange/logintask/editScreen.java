package com.onedata.sunexchange.logintask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class editScreen extends AppCompatActivity {
    TextView addTV,phnoTV,webTV,acc_per,reset_per,acc_com,reset_com;
    Button edit_per,edit_com;
    EditText first,last,user,web,phno,add,res_add,pincode,state,city,country;
    String firstname,companyID,lastname,userName,name,email,fax,companyName,address,mobile,website,roleName;
    RequestQueue requestQueue;
    JSONObject jsonObject = new JSONObject();
    SharedPreferences sharedPreferences;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.editscreen);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        first=findViewById(R.id.FIRST);
        last=findViewById(R.id.LAST);
        addTV=findViewById(R.id.add);
        phnoTV=findViewById(R.id.Phone);
        phno=findViewById(R.id.phoneNumber);
        webTV=findViewById(R.id.web);
        web=findViewById(R.id.website);
        add=findViewById(R.id.address);
        edit_com=findViewById(R.id.edit_company);
        edit_per=findViewById(R.id.edit_personal);
        //acc_com=findViewById(R.id.USERNAME);
        acc_per=findViewById(R.id.textView7_personal);
        //reset_com=findViewById(R.id.USERNAME);
        reset_per=findViewById(R.id.tvReset_personal);
        acc_com=findViewById(R.id.textView7_company);
        //reset_com=findViewById(R.id.USERNAME);
        reset_com=findViewById(R.id.tvReset_company);
        user=findViewById(R.id.USERNAME);
        relativeLayout=findViewById(R.id.relly5);
        res_add=findViewById(R.id.res_address);
        pincode=findViewById(R.id.pincode);
        state=findViewById(R.id.state);
        city=findViewById(R.id.city);
        country=findViewById(R.id.country);
        firstname=getIntent().getExtras().getString("firstname");
        lastname=getIntent().getExtras().getString("lastname");
        userName=getIntent().getExtras().getString("username");
        name=getIntent().getExtras().getString("name");
        email=getIntent().getExtras().getString("email");
        fax=getIntent().getExtras().getString("faxNo");
        companyName=getIntent().getExtras().getString("companyName");
        address=getIntent().getExtras().getString("address");
        mobile=getIntent().getExtras().getString("mobile");
        website=getIntent().getExtras().getString("website");
        roleName=getIntent().getExtras().getString("name");
        companyID=getIntent().getExtras().getString("companyId");
        roleName="COMPANY_ADMINISTRATOR";
        if(roleName.equals("CUSTOMER")){

            edit_per.setVisibility(View.VISIBLE);
            acc_per.setVisibility(View.VISIBLE);
            reset_per.setVisibility(View.VISIBLE);
            edit_com.setVisibility(View.GONE);
            acc_com.setVisibility(View.GONE);
            reset_com.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.GONE);
            first.setText(firstname);
            last.setText(lastname);
            user.setText(userName);
            web.setText(website);
            add.setText(address);
            phno.setText(mobile);

        }else if(roleName.equals("COMPANY_ADMINISTRATOR")){
            relativeLayout.setVisibility(View.VISIBLE);
            first.setText(firstname);
            last.setText(lastname);
            user.setText(userName);
            web.setText(website);
            add.setText(address);
            phno.setText(mobile);
            edit_per.setVisibility(View.GONE);
            acc_per.setVisibility(View.GONE);
            reset_per.setVisibility(View.GONE);
            edit_com.setVisibility(View.VISIBLE);
            acc_com.setVisibility(View.VISIBLE);
            reset_com.setVisibility(View.VISIBLE);
        }
        sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);


        reset_per.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), loginScreen.class);
                startActivity(i);
                finish();
            }
        });
        edit_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEditdetails();
            }
        });
        edit_per.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onEditdetails();
            }
        });

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
        Intent in = new Intent(editScreen.this, viewProfileScreen.class);
        startActivity(in);
        this.finish();

    }

    @Override
    public void onBackPressed() {
        getActivity();
        super.onBackPressed();
    }
    public void onEditdetails(){
        try {
            if(name.equals("CUSTOMER")){
                jsonObject.put("firstName",  first.getText());
                jsonObject.put("lastName",  last.getText());
                jsonObject.put("username",  user.getText());
                jsonObject.put("id", sharedPreferences.getInt("UserId", 0));
                jsonObject.put("companyName",  companyName);
                jsonObject.put("address", address);
                jsonObject.put("contactNo", mobile);
                jsonObject.put("website",  website);
                jsonObject.put("email",  email);
                jsonObject.put("faxNo",  fax);
                jsonObject.put("role",  roleName);
                jsonObject.put("companyId",  companyID);
                jsonObject.put("personalAddress", res_add.getText());
                jsonObject.put("state", state.getText());
                jsonObject.put("city", city.getText());
                jsonObject.put("pincode", pincode.getText());
                jsonObject.put("country", country.getText());
            }
            else
            {

                jsonObject.put("firstName",  first.getText());
                jsonObject.put("lastName",  last.getText());
                jsonObject.put("username",  user.getText());
                jsonObject.put("id", sharedPreferences.getInt("UserId", 0));
                jsonObject.put("companyName",  companyName);
                jsonObject.put("address", add.getText());
                jsonObject.put("contactNo", phno.getText());
                jsonObject.put("website",  web.getText());
                jsonObject.put("email",  email);
                jsonObject.put("faxNo",  fax);
                jsonObject.put("companyId",  companyID);
                jsonObject.put("role",  roleName);
                jsonObject.put("personalAddress", res_add.getText());
                jsonObject.put("state", state.getText());
                jsonObject.put("city", city.getText());
                jsonObject.put("pincode", pincode.getText());
                jsonObject.put("country", country.getText());
            }


        } catch (Exception e) {

        }
        Log.d("password", "onCreate: "+jsonObject.toString());
        JsonParser request = new JsonParser
                (Request.Method.PUT, "http://192.168.1.128:8080/api/v1/updateuser", jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(), "Update Successfully", Toast.LENGTH_LONG).show();

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

                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Intent userProfile = getIntent();
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorisation", sharedPreferences.getString("Token", ""));
                return params;
            }


        };
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);




    }
}
