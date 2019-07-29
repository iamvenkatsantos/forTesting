package com.onedata.sunexchange.logintask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.google.gson.Gson;
import com.onedata.sunexchange.logintask.bean.UserProfile;

public class DashScreenPresonal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String username,role;
    SharedPreferences sharedPreferences;
    TextView usernameTV,roleTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_screen_presonal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        UserProfile user = gson.fromJson(sharedPreferences.getString("UserDetail", ""), UserProfile.class);
        role=sharedPreferences.getString("roleName","");
        username=sharedPreferences.getString("userName","");
        DrawerLayout drawer = findViewById(R.id.drawer_layout_personal);
        NavigationView navigationView = findViewById(R.id.nav_view_personal);
        View headerView =navigationView.getHeaderView(0);
        usernameTV=headerView.findViewById(R.id.user);
        roleTV=headerView.findViewById(R.id.role);
        usernameTV.setText(username);
        roleTV.setError(role);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }
    public void clickEvent(View v)
    {
//FUNCTION FOR INTENT TO VEIW SCREEN
        switch (v.getId()){
            case R.id.img:
                Intent login = getIntent();
                Intent in=new Intent(DashScreenPresonal.this, viewProfileScreen.class);
                in.putExtra("UserProfile",login.getParcelableExtra("UserProfile"));
                startActivity(in);
                finish();
                break;

            case R.id.pieChart:
                Intent inpie=new Intent(DashScreenPresonal.this, Pie.class);
                startActivity(inpie);
                finish();
                break;

            case R.id.lineChart:
                Intent inline=new Intent(DashScreenPresonal.this, Linecht.class);
                startActivity(inline);
                finish();
                break;

        }


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout_personal);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            getActivity();
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.kyc_personal) {
            Intent login = getIntent();
            Intent in=new Intent(DashScreenPresonal.this, kycPersonal.class);
            //   UserProfile user =login.getParcelableExtra("UserProfile");
            Log.e("log", "token"+login.getStringExtra("token"));
            in.putExtra("token",login.getStringExtra("token"));
            in.putExtra("firstOrSecond","FIRSTTIME");
            in.putExtra("userId",login.getStringExtra("userId"));
            startActivity(in);
            finish();

            // Handle the camera action
        } else if(id==R.id.kyc_View){
            Intent in=new Intent(DashScreenPresonal.this, KycViewDetails.class);
            startActivity(in);
            finish();

        }
        else if (id == R.id.log_out) {

        }
        else if (id == R.id.change_password) {

            Intent in=new Intent(DashScreenPresonal.this, changePassword.class);
            startActivity(in);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout_personal);
        /* usernameTV.setText(username);
        roleTV.setText(role);*/
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void getActivity() {
        Intent in = new Intent(DashScreenPresonal.this, loginScreen.class);
        startActivity(in);
        this.finish();

    }

}
