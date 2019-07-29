package com.onedata.sunexchange.logintask;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.onedata.sunexchange.logintask.bean.KycFirstStep;
import com.onedata.sunexchange.logintask.bean.UserProfile;
import com.onedata.sunexchange.logintask.utilities.StatusCode;
import com.scrounger.countrycurrencypicker.library.Buttons.CountryCurrencyButton;
import com.scrounger.countrycurrencypicker.library.Country;
import com.scrounger.countrycurrencypicker.library.Currency;
import com.scrounger.countrycurrencypicker.library.Listener.CountryCurrencyPickerListener;


import org.json.JSONObject;

import java.util.Calendar;

import java.util.HashMap;
import java.util.Map;

public class kycPersonal extends AppCompatActivity {
    Button personal_b;
    RequestQueue requestQueue;
    JSONObject jsonObject = new JSONObject();
    KycFirstStep kycFirstStep;
    String kycFirstJson, countryName;
    SharedPreferences sharedPreferences;
    private DatePickerDialog.OnDateSetListener Dob;
    String roleName,fistNameString,lastNameString,middleNameString,dobString,addressString,areaString,cityString,streetString,stateString,countryString,pincodeString,businessNameString,businessAddressString,taxCountrStringy,taxSameCountryString,tinNumberString,taxAnotherCountryString;
    String[] filename;
    String firstOrSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
     // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_kyc_personal);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        personal_b=(Button)findViewById(R.id.personal);
        sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        final SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
        final EditText firstName =(EditText) findViewById(R.id.firstName);
        final EditText lastName =(EditText) findViewById(R.id.lastName);
        final EditText middleName =(EditText) findViewById(R.id.middleName);
        final TextView dateOfBirth =findViewById(R.id.dob);
        final EditText residentialAddress =(EditText) findViewById(R.id.res_address);
        final EditText area =(EditText) findViewById(R.id.area);
        final EditText city =(EditText) findViewById(R.id.city);
        final EditText street =(EditText) findViewById(R.id.street);
        final EditText state =(EditText) findViewById(R.id.state);
        final CountryCurrencyButton country = findViewById(R.id.country);
        final EditText pincode =(EditText) findViewById(R.id.pincode);

        firstOrSecond=getIntent().getExtras().getString("firstOrSecond");
        if(firstOrSecond.equals("FIRSTTIME"))
        {
            Gson gson = new Gson();
            UserProfile user = gson.fromJson(sharedPreferences.getString("UserDetail", ""), UserProfile.class);
            firstName.setText(user.getFirstName());
            lastName.setText(user.getLastName());
        }else{
            fistNameString=getIntent().getExtras().getString("firstname");
            lastNameString =getIntent().getExtras().getString("lastname");
            middleNameString =getIntent().getExtras().getString("middlename");
            dobString=getIntent().getExtras().getString("dateOfBirth");
            addressString=getIntent().getExtras().getString("residentialAddress");
            areaString=getIntent().getExtras().getString("area");
            cityString=getIntent().getExtras().getString("city");
            streetString=getIntent().getExtras().getString("street");
            stateString=getIntent().getExtras().getString("state");
            countryString=getIntent().getExtras().getString("country");
            pincodeString=getIntent().getExtras().getString("pincode");
            businessNameString=getIntent().getExtras().getString("businessName");
            businessAddressString=getIntent().getExtras().getString("businessAddress");
            taxCountrStringy=getIntent().getExtras().getString("taxCountry");
            taxSameCountryString=getIntent().getExtras().getString("isTaxSameCountry");
            taxAnotherCountryString=getIntent().getExtras().getString("isTaxAnotherCountry");
            tinNumberString=getIntent().getExtras().getString("tinNo");
            Bundle b = getIntent().getExtras();
            final String[] files = b.getStringArray("files");
            filename=files;
            roleName=getIntent().getExtras().getString("roleName");
            firstName.setText(fistNameString);
            lastName.setText(lastNameString);
            middleName.setText(middleNameString);
            dateOfBirth.setText(dobString);
            residentialAddress.setText(addressString);
            area.setText(areaString);
            city.setText(cityString);
            street.setText(streetString);
            state.setText(stateString);
           // country.setCountry(countryString);
            pincode.setText(pincodeString);
        }






        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int date=calendar.get(Calendar.DATE);
                DatePickerDialog pickerDialog=new DatePickerDialog(kycPersonal.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,Dob,year,month,date);
                pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pickerDialog.show();
            }
        });
        Dob=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            month=month+1;
            String date=day+"/"+month+"/"+year;
            dateOfBirth.setText(date);
            }
        };

        country.setOnClickListener(new CountryCurrencyPickerListener() {
            @Override
            public void onSelectCountry(Country country) {

                if (country.getCurrency() == null) {
                    /*Toast.makeText(kycPersonal.this,
                            String.format("name: %s\ncode: %s", country.getName(), country.getCode())
                            , Toast.LENGTH_SHORT).show();
                    */countryName=country.getName();
                } else {
                    Toast.makeText(kycPersonal.this,
                            String.format("name: %s\ncurrencySymbol: %s", country.getName(), country.getCurrency().getSymbol())
                            , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSelectCurrency(Currency currency) {

            }
        });

        personal_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    KycFirstStep kycFirstStep = new KycFirstStep();
                    kycFirstStep.setFirstName(firstName.getText().toString());
                    kycFirstStep.setLastName(lastName.getText().toString());
                    kycFirstStep.setMiddleName(middleName.getText().toString());
                    kycFirstStep.setDateOfBirth(dateOfBirth.getText().toString());
                    kycFirstStep.setResidentialAddress(residentialAddress.getText().toString());
                    kycFirstStep.setArea(area.getText().toString());
                    kycFirstStep.setCity(city.getText().toString());
                    kycFirstStep.setStreet(street.getText().toString());
                    kycFirstStep.setState(state.getText().toString());
                    kycFirstStep.setCountry( country.getText().toString());
                    kycFirstStep.setPincode(Integer.parseInt(pincode.getText().toString()));
                    jsonObject.put("firstName", firstName.getText());
                    jsonObject.put("lastName", lastName.getText());
                    jsonObject.put("middleName", middleName.getText());
                    jsonObject.put("dateOfBirth", dateOfBirth.getText());
                    jsonObject.put("residentialAddress", residentialAddress.getText());
                    jsonObject.put("area", area.getText());
                    jsonObject.put("city", city.getText());
                    jsonObject.put("street", street.getText());
                    jsonObject.put("state", state.getText());
                    jsonObject.put("country",countryName );
                    jsonObject.put("pincode", Integer.parseInt(pincode.getText().toString()));
                    Log.e("log", "kycfirst"+jsonObject.toString());

                    Gson gson = new Gson();
                  kycFirstJson = gson.toJson(kycFirstStep);
                    Log.d("log", "kycfirst"+kycFirstJson);
                } catch (Exception e) {

                }


                JsonParser request = new JsonParser
                        (Request.Method.POST, "http://192.168.1.128:8080/api/v1/kyc/first",jsonObject, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Intent in=new Intent(kycPersonal.this, kycTax.class);
                                    in.putExtra("KycPersonal",kycFirstJson);
                                    in.putExtra("files",filename);
                                    in.putExtra("businessName",businessNameString);
                                    in.putExtra("businessAddress",businessAddressString);
                                    in.putExtra("taxCountry",taxAnotherCountryString);
                                    in.putExtra("isTaxSameCountry",taxSameCountryString);
                                    in.putExtra("isTaxAnotherCountry",taxAnotherCountryString);
                                    in.putExtra("tinNo",tinNumberString);
                                    in.putExtra("roleName",roleName);
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
                        Intent userProfile = getIntent();
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
                Intent in = new Intent(kycPersonal.this, dashBoardScreen.class);
                startActivity(in);
                this.finish();

                return true;
            case R.id.log_out:
                Intent intn = new Intent(kycPersonal.this, loginScreen.class);
                startActivity(intn);
                this.finish();
                Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void getActivity() {
        String role;
        role = sharedPreferences.getString("roleName", "");
        if (role.equals("COMPANY_ADMINISTRATOR")) {
            Intent in = new Intent(kycPersonal.this, dashBoardScreen.class);
            in.putExtra("firstOrSecond", roleName);
            startActivity(in);
            this.finish();
        } else
        {
            Intent in = new Intent(kycPersonal.this, DashScreenPresonal.class);
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
