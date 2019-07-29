package com.onedata.sunexchange.logintask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class Linecht extends AppCompatActivity {
    LineChart line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linecht);
        line=findViewById(R.id.linechrt);
      /*  getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        line.setDrawGridBackground(false);
        ArrayList<Entry> y= new ArrayList<>();
        y.add(new Entry(0,44));
        y.add(new Entry(1,54));
        y.add(new Entry(2,67));
        y.add(new Entry(3,13));
        y.add(new Entry(4,55));
        LineDataSet dataSet=new LineDataSet(y,"Months");
        LineData data=new LineData(dataSet);
        data.setValueTextSize(10f);

        line.setData(data);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent in = new Intent(Linecht.this, dashBoardScreen.class);
                startActivity(in);
                this.finish();

                return true;
            case R.id.log_out:
                Intent intn = new Intent(Linecht.this, loginScreen.class);
                startActivity(intn);
                this.finish();
                Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void getActivity() {
        Intent in = new Intent(Linecht.this, dashBoardScreen.class);
        startActivity(in);
        this.finish();

    }

    @Override
    public void onBackPressed() {
        getActivity();
        super.onBackPressed();
    }


}
