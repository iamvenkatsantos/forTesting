package com.onedata.sunexchange.logintask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
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

import com.onedata.sunexchange.logintask.utilities.StatusCode;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class changePassword extends AppCompatActivity {
    Button change;
    RequestQueue requestQueue;
    EditText oldPassword,newPassword,confirmPassword;
    JSONObject jsonObject = new JSONObject();
    SharedPreferences sharedPreferences;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_change_password);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        change=findViewById(R.id.change);
        oldPassword=findViewById(R.id.oldpsd);
        newPassword=findViewById(R.id.newpsd);
        confirmPassword=findViewById(R.id.confirmpsd);
        //TO GET THE BASIC DETAILS FORM SHARED PREFERCENCES
        sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);


        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog=new ProgressDialog(changePassword.this);
                dialog.setTitle("CHANGING");
                dialog.show();
                onChangePassword();
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
        String role;
        role = sharedPreferences.getString("roleName", "");
        if (role.equals("COMPANY_ADMINISTRATOR")) {
            Intent in = new Intent(changePassword.this, dashBoardScreen.class);
            startActivity(in);
            this.finish();
        } else
        {
            Intent in = new Intent(changePassword.this, DashScreenPresonal.class);
            startActivity(in);
            this.finish();
        }

    }
//FUNCTION FOR PRESSING BACK BUTTON
    @Override
    public void onBackPressed() {
        getActivity();
        super.onBackPressed();
    }
// USER DEFINED FUNCTION FOR SET VALUE IN JSON OBJECT
   public void  onChangePassword(){
       try {
           jsonObject.put("oldPassword",  oldPassword.getText());
           jsonObject.put("newPassword",  newPassword.getText());
           jsonObject.put("confirmPassword",  confirmPassword.getText());
           jsonObject.put("id", sharedPreferences.getInt("UserId", 0));

       } catch (Exception e) {

       }
       Log.d("password", "onCreate: "+jsonObject.toString());
       JsonParser request = new JsonParser
               (Request.Method.PUT, "http://192.168.1.128:8080/api/v1/changepassword", jsonObject, new Response.Listener<JSONObject>() {

                   @Override
                   public void onResponse(JSONObject response) {
                       try {
                           dialog.dismiss();
                           onRequest();

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
                       //   Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
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
   public void onRequest()
   {
       new AlertDialog.Builder(this)
               .setTitle("CHANGED")
               .setMessage("THE HAS BEEN CHANGED")
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       Intent login = new Intent(getApplicationContext(), dashBoardScreen.class);
                       startActivity(login);
                       finish();
                   }
               })
               .setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.dismiss();
                   }
               }).create().show();


   }






}
