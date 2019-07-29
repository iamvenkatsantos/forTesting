package com.onedata.sunexchange.logintask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.onedata.sunexchange.logintask.bean.ModeratorDetail;
import com.onedata.sunexchange.logintask.helper.MyAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeratorView extends AppCompatActivity {
    private ArrayList<String> arraylist = new ArrayList<>();
    ListView lv;
    String[] username={"venkat","santos","hari","abinesh","anna","mohan"}, Status, Email;
    MyAdapter adapter;
    Context context= ModeratorView.this;
    RequestQueue requestQueue;
    JSONObject jsonObject = new JSONObject();
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_moderator__view);
        sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        lv = (ListView) findViewById(R.id.listView);
      /*  arraylist.add("nagularaj6119@gmail.com");
        arraylist.add("nagularaj6119@gmail.com");
        arraylist.add("nagularaj6119@gmail.com");
        arraylist.add("nagularaj6119@gmail.com");
        arraylist.add("nagularaj6119@gmail.com");
        arraylist.add("nagularaj6119@gmail.com");*/


        //Toast.makeText(getApplicationContext(),username[1]+username[0],Toast.LENGTH_LONG).show();
      getModerator();

    }

    private void getModerator() {
        Log.d("password", "onCreate: "+jsonObject.toString());
        JsonParser request = new JsonParser
                (Request.Method.GET, "http://192.168.1.128:8080/api/v1/getmoderators", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                        List<ModeratorDetail> moderatorDetailList = new ArrayList<>();
                        JSONObject data = response.getJSONObject("data");
                        JSONArray jsonArray = (JSONArray) data.get("list");
                        for (int i = 0; i < jsonArray.length(); i++)
                            moderatorDetailList.add(new ModeratorDetail(jsonArray.getJSONObject(i).getInt("id"),
                                    jsonArray.getJSONObject(i).getString("username"),
                                    jsonArray.getJSONObject(i).getString("email"),
                                    jsonArray.getJSONObject(i).getString("firstName"),
                                    jsonArray.getJSONObject(i).getString("lastName"),
                                    jsonArray.getJSONObject(i).getString("role"),
                                    jsonArray.getJSONObject(i).getInt("companyId"),
                                    jsonArray.getJSONObject(i).getString("companyName"),
                                    jsonArray.getJSONObject(i).getString("verificationKey")
                                    ));
                            adapter = new MyAdapter(context,moderatorDetailList);
                            lv.setAdapter(adapter);
                    }catch (Exception e){}}
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        /*int statcode = error.networkResponse.statusCode;
                        String message = StatusCode.errorResponseToaster(statcode);
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();*/
                          Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
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

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {


            LayoutInflater layoutInflater = getContext().getS

        return convertView;
    }*/
    private void getActivity() {
        Intent in = new Intent(ModeratorView.this, dashBoardScreen.class);
        startActivity(in);
        this.finish();

    }

    @Override
    public void onBackPressed() {
        getActivity();
        super.onBackPressed();
    }
}