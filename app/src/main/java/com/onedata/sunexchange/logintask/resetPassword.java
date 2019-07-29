package com.onedata.sunexchange.logintask;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.onedata.sunexchange.logintask.utilities.StatusCode;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class resetPassword extends AppCompatActivity {
    Button reset;
    EditText confirm, newpass;
    RequestQueue requestQueue;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_reset_password);
        reset=findViewById(R.id.reset);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        confirm=findViewById(R.id.confirmpsd);
        newpass=findViewById(R.id.newpsd);
        Uri uri = getIntent().getData();
        final List pathVariable =uri.getPathSegments();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final JSONObject jsonObject = new JSONObject();
                try {
                    if(confirm.getText().toString().equals(newpass.getText().toString())) {
                        jsonObject.put("newPassword", confirm.getText());
                        jsonObject.put("passwordResetKey",pathVariable.get(2));
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Password Mismatch",Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.e("log", "+++++"+e.toString());
                }
                dialog=new ProgressDialog(resetPassword.this);
                dialog.setTitle("Uploading");
                dialog.setMessage("please wait...");
                dialog.show();

                JsonParser request = new JsonParser
                        (Request.Method.POST, "http://192.168.1.128:8080/api/noauth/reset/password", jsonObject, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Toast.makeText(getApplicationContext(),"Successfully Updated",Toast.LENGTH_LONG).show();
                                    Intent in = new Intent(getApplicationContext(), loginScreen.class);
                                    dialog.dismiss();
                                    startActivity(in);
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

                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Intent userProfile = getIntent();
                        String a = "header passed";
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("Authorisation", userProfile.getStringExtra("token"));

                        return params;
                    }


                };
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void getActivity() {
        Intent in = new Intent(resetPassword.this, loginScreen.class);
        startActivity(in);
        this.finish();


    }

    @Override
    public void onBackPressed() {
        getActivity();
        super.onBackPressed();
    }
    }
