package com.onedata.sunexchange.logintask;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.onedata.sunexchange.logintask.bean.KycSecondStep;
import com.onedata.sunexchange.logintask.utilities.StatusCode;
import com.scrounger.countrycurrencypicker.library.Buttons.CountryCurrencyButton;
import com.scrounger.countrycurrencypicker.library.Country;
import com.scrounger.countrycurrencypicker.library.Currency;
import com.scrounger.countrycurrencypicker.library.Listener.CountryCurrencyPickerListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class kycTax extends AppCompatActivity {
    Button tax;
    RequestQueue requestQueue;
    RadioGroup sameCountry,anotherCountry;
    RadioButton same,another,same_yes,same_no,another_yes,another_no;
    String  kycSecondJson;
    String businessNameString,businessAddressString,taxCountrStringy,taxSameCountryString,tinNumberString,taxAnotherCountryString;
    String countryName,roleName;
    String[] filename;
    CountryCurrencyButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
       // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_kyc_tax);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tax=(Button)findViewById(R.id.tax);
       final EditText business=findViewById(R.id.business);
      final EditText address=findViewById(R.id.address);
        final EditText tinNo=findViewById(R.id.tinno);
        anotherCountry=findViewById(R.id.AnotherCountry);
        same_yes=findViewById(R.id.same_no);
        another_yes=findViewById(R.id.another_yes);
        same_no=findViewById(R.id.same_no);
        another_no=findViewById(R.id.another_no);
       /* tinNumberString=getIntent().getExtras().getString("tinNo");
        if(tinNumberString!=null)
        {
            businessNameString=getIntent().getExtras().getString("businessName");
            businessAddressString=getIntent().getExtras().getString("businessAddress");
            taxCountrStringy=getIntent().getExtras().getString("taxCountry");
            taxSameCountryString=getIntent().getExtras().getString("isTaxSameCountry");
            taxAnotherCountryString=getIntent().getExtras().getString("isTaxAnotherCountry");
            Bundle b = getIntent().getExtras();
            final String[] files = b.getStringArray("files");
            filename=files;
            roleName=getIntent().getExtras().getString("roleName");
            tinNo.setText(tinNumberString);
            business.setText(businessNameString);
            address.setText(businessAddressString);

           if(taxSameCountryString.equals("true")){
               same_yes.setChecked(true);
               another_no.setChecked(true);
            }
            if(taxAnotherCountryString.equals("true")){
                same_no.setChecked(true);
                another_yes.setChecked(true);
            }
        }
*/



      button = (CountryCurrencyButton) findViewById(R.id.button);
        button.setOnClickListener(new CountryCurrencyPickerListener() {
            @Override
            public void onSelectCountry(Country country) {

                if (country.getCurrency() == null) {
                    /*Toast.makeText(kycTax.this,
                            String.format("name: %s\ncode: %s", country.getName(), country.getCode())
                            , Toast.LENGTH_SHORT).show();
                    */countryName=country.getName();
                } else {
                    Toast.makeText(kycTax.this,
                            String.format("name: %s\ncurrencySymbol: %s", country.getName(), country.getCurrency().getSymbol())
                            , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSelectCurrency(Currency currency) {

            }
        });
      sameCountry=findViewById(R.id.SameCountry);

        tax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                KycSecondStep kycSecondStep = new KycSecondStep();
                final SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails",MODE_PRIVATE);
                final JSONObject jsonObject = new JSONObject();
                try {
                    Intent userProfile = getIntent();
                    jsonObject.put("businessName",business.getText());
                    jsonObject.put("businessAddress", address.getText());
                    jsonObject.put( "taxCountry", countryName);

                    if(same_yes.isChecked()){
                        jsonObject.put("isTaxSameCountry", true);
                        jsonObject.put("isTaxAnotherCountry", false);
                        kycSecondStep.setTaxSameCountry(true);
                        kycSecondStep.setTaxAnotherCountry(false);
                    }else{
                        jsonObject.put("isTaxSameCountry", false);
                        jsonObject.put("isTaxAnotherCountry", true);
                        kycSecondStep.setTaxSameCountry(false);
                        kycSecondStep.setTaxAnotherCountry(true);
                    }
                    jsonObject.put("tinNo",tinNo.getText() );
                    jsonObject.put("userId", sharedPreferences.getInt("UserId", 0));
                    kycSecondStep.setBusinessName(business.getText().toString());
                    kycSecondStep.setBusinessAddress(address.getText().toString());
                    kycSecondStep.setTaxCountry(countryName);
                    kycSecondStep.setUserId(sharedPreferences.getInt("UserId", 0));
                    kycSecondStep.setTinNo(tinNo.getText().toString());
                    Gson gson = new Gson();
                    kycSecondJson = gson.toJson(kycSecondStep);
                    Log.d("log", "kycfirst"+kycSecondJson);
                } catch (Exception e) {
                    Log.e("log", "+++++"+e.toString());
                }


                JsonParser request = new JsonParser
                        (Request.Method.POST, "http://192.168.1.128:8080/api/v1/kyc/second", jsonObject, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Intent userProfile = getIntent();
                                    Intent in=new Intent(kycTax.this, kycImageUpload.class);
                                    in.putExtra("KycSecondJson",kycSecondJson);
                                    in.putExtra("KycFirstJson",userProfile.getStringExtra("KycPersonal"));
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
                            }

                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Authorisation", sharedPreferences.getString("Token",""));
                        return params;
                    }


                };
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
            }

        });


    }
    public void CheckButton(View v){
        int radioId;
            switch (v.getId()){
                case R.id.same_yes:
                     radioId=sameCountry.getCheckedRadioButtonId();
                    same=findViewById(radioId);
                    another_no.setChecked(true);
                    //Toast.makeText(getApplicationContext(),same.getText(),Toast.LENGTH_LONG).show();

                    break;
                case R.id.same_no:
                     radioId=sameCountry.getCheckedRadioButtonId();
                     another_yes.setChecked(true);
                     same=findViewById(radioId);
                    //Toast.makeText(getApplicationContext(),same.getText(),Toast.LENGTH_LONG).show();

                    break;
                case R.id.another_yes:
                    radioId=anotherCountry.getCheckedRadioButtonId();
                    another=findViewById(radioId);

                    //Toast.makeText(getApplicationContext(),another.getText(),Toast.LENGTH_LONG).show();
                    break;
                case R.id.another_no:
                    radioId=anotherCountry.getCheckedRadioButtonId();
                    another=findViewById(radioId);

                    //Toast.makeText(getApplicationContext(),another.getText(),Toast.LENGTH_LONG).show();
                    break;
            }

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
                Intent in = new Intent(kycTax.this, kycPersonal.class);
                startActivity(in);
                this.finish();

                return true;
            case R.id.log_out:
                Intent intn = new Intent(kycTax.this, loginScreen.class);
                startActivity(intn);
                this.finish();
                Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void getActivity() {
        Intent in = new Intent(kycTax.this, kycPersonal.class);
        in.putExtra("firstOrSecond","SRCONDTIME");
        startActivity(in);
        this.finish();

    }

    @Override
    public void onBackPressed() {
        getActivity();
        super.onBackPressed();
    }


}
