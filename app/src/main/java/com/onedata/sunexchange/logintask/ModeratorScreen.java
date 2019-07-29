


package com.onedata.sunexchange.logintask;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
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
        import org.json.JSONObject;
        import java.util.HashMap;
        import java.util.Map;

public class ModeratorScreen extends AppCompatActivity {
    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    EditText firstName,lastName,userName,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moderator_screen);
        Button add = (Button)findViewById(R.id.submit);
        firstName = (EditText)findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        userName = (EditText)findViewById(R.id.userName);
        email =(EditText)findViewById(R.id.email);
        sharedPreferences = getSharedPreferences("LoginDetails",MODE_PRIVATE);
        Log.d("token", "onCreate: "+sharedPreferences.getString("Token", ""));
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("firstName", firstName.getText());
                    jsonObject.put("lastName", lastName.getText());
                    jsonObject.put("email", email.getText());
                    jsonObject.put("username", userName.getText());
                } catch (Exception e) {

                }
                JsonParser request = new JsonParser
                        (Request.Method.POST, "http://192.168.1.128:8080/api/v1/registermoderator", jsonObject, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("successs", "onResponse: "+"success");
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }

                        }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Authorisation", sharedPreferences.getString("Token", ""));
                        return params;
                    }
                };

                requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
            }
        });
    }
    private void getActivity() {
        Intent in = new Intent(ModeratorScreen.this, dashBoardScreen.class);
        startActivity(in);
        this.finish();

    }

    @Override
    public void onBackPressed() {
        getActivity();
        super.onBackPressed();
    }
}