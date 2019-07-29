package com.onedata.sunexchange.logintask;

import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import com.onedata.sunexchange.logintask.utilities.CustomParamJsonRequest;


import com.onedata.sunexchange.logintask.utilities.StatusCode;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Forgotpassword extends AppCompatActivity {
    Button send;
    EditText email;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_forgotpassword);
        send=(Button)findViewById(R.id.sendlink);
        email=findViewById(R.id.email);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("log",email.getText().toString());
                Map<String, String> params = new HashMap();
                params.put("email",email.getText().toString() );
                CustomParamJsonRequest request = new   CustomParamJsonRequest
                        (Request.Method.GET, "http://192.168.1.128:8080/api/noauth/reset/password?email="+email.getText().toString(),params, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Toast.makeText(getApplicationContext(),"Link send Successfully",Toast.LENGTH_LONG).show();


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
                               // Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }

                        });

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
        Intent in = new Intent(Forgotpassword.this, loginScreen.class);
        startActivity(in);
        this.finish();

    }

    @Override
    public void onBackPressed() {
        getActivity();
        super.onBackPressed();
    }

}
