package com.onedata.sunexchange.logintask;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Pie extends AppCompatActivity {
    PieChart pcht;
    Button line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);
        pcht=findViewById(R.id.piechrt);
     /*   getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        pcht.setUsePercentValues(true);
        pcht.getDescription().setEnabled(false);
        pcht.setExtraOffsets(5,10,5,5);
        pcht.setDragDecelerationFrictionCoef(0.95f);
        pcht.setDrawHoleEnabled(true);
        pcht.setHoleColor(Color.WHITE);
        pcht.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> y= new ArrayList<>();
        y.add(new PieEntry(44,"jan"));
        y.add(new PieEntry(90,"march"));
        y.add(new PieEntry(68,"may"));
        y.add(new PieEntry(70,"jul"));
        y.add(new PieEntry(54,"sept"));
        pcht.animateY(1000, Easing.EaseInOutCubic);

        PieDataSet dataSet=new PieDataSet(y,"Months");
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data=new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pcht.setData(data);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent in = new Intent(Pie.this, dashBoardScreen.class);
                startActivity(in);
                this.finish();

                return true;
            case R.id.log_out:
                Intent intn = new Intent(Pie.this, loginScreen.class);
                startActivity(intn);
                this.finish();
                Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void getActivity() {
        Intent in = new Intent(Pie.this, dashBoardScreen.class);
        startActivity(in);
        this.finish();

    }

    @Override
    public void onBackPressed() {
        getActivity();
        super.onBackPressed();
    }

}
