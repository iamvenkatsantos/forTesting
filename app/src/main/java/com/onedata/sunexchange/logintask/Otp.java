package com.onedata.sunexchange.logintask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.onedata.sunexchange.logintask.bean.Role;
import com.onedata.sunexchange.logintask.bean.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Otp extends AppCompatActivity {
    Button otp;
    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    String username,role;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_otp);
        Intent userDetail = getIntent();
         username = userDetail.getStringExtra("username");
         //Log.d("username",username);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        otp=findViewById(R.id.submitOtp);
        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText otpText = (EditText)findViewById(R.id.otpText);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("username", username);
                    jsonObject.put("otpToken", otpText.getText());

                } catch (Exception e) {

                }


                JsonParser request = new JsonParser
                        (Request.Method.POST, "http://192.168.1.128:8080/login/token", jsonObject, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try { JSONObject data = response.getJSONObject("data");
                                     JSONObject headers = response.getJSONObject("headers");
                                     String header = headers.toString().substring(headers.toString().indexOf("Token"), headers.toString().indexOf(",") - 1);
                                  //  Toast.makeText(getApplicationContext(),"Name:"+ed1.getEditText().getText()+"\n"+"Password:"+ed2.getEditText().getText()+"\n"+headers.toString(),Toast.LENGTH_LONG).show();

                                    Log.d("header response", "header" + header);
                      if (header != null) {
                                Log.d("header response", "data"+data);
                          sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                          editor = sharedPreferences.edit();
                          editor.putInt("UserId", (Integer) data.get("id"));
                          editor.putString("Token", header);
                              //  in.putExtra("header", header);
                                UserProfile user = new UserProfile();
                                user.setId((Integer) data.get("id"));
                                user.setToken(header);
                                user.setFirstName((String) data.get("firstName"));
                                user.setLastName((String) data.get("lastName"));
                                user.setUsername((String) data.get("username"));
                                List<Role> listRole = new ArrayList<>();
                                JSONArray jsonArray = (JSONArray) data.get("role");
                                for (int i = 0; i < jsonArray.length(); i++)
                                    listRole.add(new Role(
                                            jsonArray.getJSONObject(i).getInt("id"),
                                            role=jsonArray.getJSONObject(i).getString("name")));
                                 user.setRole(listRole);
                                 Gson gson = new Gson();
                                 String userDetail = gson.toJson(user);
                                 editor.putString("UserDetail", userDetail);
                                 editor.putString("userName", user.getUsername());
                                 editor.putString("roleName", user.getRole().get(0).getName());
                                 Log.d("role","=========================="+user.getRole().get(0).getName());
                                 editor.commit();
                         if(role.equals("COMPANY_ADMINISTRATOR")) {
                             Intent in = new Intent(Otp.this, dashBoardScreen.class);
                              startActivity(in);
                              finish();
                          }else{
                             Intent in = new Intent(Otp.this, DashScreenPresonal.class);
                             startActivity(in);
                             finish();
                         }

                          //editor.putString("UserDetail", userDetail);

                               }} catch (JSONException e) {
                                    Log.e("log", Log.getStackTraceString(e));
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }

                        }) ;

                requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
            }
        });
        TextView resend = (TextView) findViewById(R.id.resendOtp);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("username", username);


                } catch (Exception e) {

                }


                JsonParser request = new JsonParser
                        (Request.Method.POST, "http://192.168.1.128:8080/login/pre/resend", jsonObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(getApplicationContext(),"Resent Successfully",Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }

                        });
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
            }
        });
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void getActivity() {
        Intent in = new Intent(Otp.this, loginScreen.class);
        startActivity(in);
        this.finish();

    }

    @Override
    public void onBackPressed() {
        getActivity();
        super.onBackPressed();
    }


}
