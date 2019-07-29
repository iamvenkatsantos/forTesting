package com.onedata.sunexchange.logintask;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class loginScreen extends AppCompatActivity {

    TextView tv,tv1;
    EditText ed1,ed2;

    Button logIn;
    RequestQueue requestQueue;
    ImageView imv;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.loginscreen);


        tv = (TextView) findViewById(R.id.tVSignUp);
        tv1 = (TextView) findViewById(R.id.forgotpassword);

        ed1 = (EditText) findViewById(R.id.username);
        ed2 = (EditText) findViewById(R.id.password);
        logIn = (Button) findViewById(R.id.logIn);
        // imv=(ImageView)findViewById(R.id.imageView);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(loginScreen.this, signupScreen.class);
                startActivity(in);
                finish();
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(loginScreen.this, Forgotpassword.class);
                startActivity(in);
                finish();
            }
        });
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGetHeader();


            }
        });

        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }

    private void onGetHeader() {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", ed1.getText());
            jsonObject.put("password", ed2.getText());

        } catch (Exception e) {

        }


        JsonParser request = new JsonParser
                (Request.Method.POST, "http://192.168.1.128:8080/login/pre", jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //JSONObject data = response.getJSONObject("data");
                          //  JSONObject headers = response.getJSONObject("headers");
                           // String header = headers.toString().substring(headers.toString().indexOf("Token"), headers.toString().indexOf(",") - 1);
                            // JSONObject name = response.getJSONObject("name");
                            //  Toast.makeText(getApplicationContext(),"Name:"+ed1.getEditText().getText()+"\n"+"Password:"+ed2.getEditText().getText()+"\n"+headers.toString(),Toast.LENGTH_LONG).show();

                           // Log.d("header response", "header" + header);
                       /*     if (header != null) {
                                Log.d("header response", "data"+data);

                              //  in.putExtra("header", header);
                                UserProfile user = new UserProfile();
                                user.setId((Integer) data.get("id"));
                                user.setToken(header);
                                user.setFirstName((String) data.get("firstName"));
                                user.setLastName((String) data.get("lastName"));
                                List<Role> listRole = new ArrayList<>();
                                JSONArray jsonArray = (JSONArray) data.get("role");
                                for (int i = 0; i < jsonArray.length(); i++)
                                    listRole.add(new Role(jsonArray.getJSONObject(i).getInt("id"),
                                            jsonArray.getJSONObject(i).getString("name")));
                                user.setRole(listRole);
                                Log.d("header response", ""+user);*/

                                Intent in = new Intent(loginScreen.this, Otp.class);
                                Log.d("username",""+ed1.getText());
                                in.putExtra("username",ed1.getText().toString());
                                startActivity(in);
                                finish();


                        } catch (Exception e) {
                            Log.e("log", Log.getStackTraceString(e));

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        int statcode = error.networkResponse.statusCode;
                        String message = StatusCode.errorResponseToaster(statcode);
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();


                    }

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String a = "header passed";
                Map<String, String> params = new HashMap<String, String>();

                params.put("Authorisation", getName());

                return params;
            }

            public String getName() {
                return "sunexchange";
            }
        };
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }

    @Override
    public void finish() {
        super.finish();
    }

}
