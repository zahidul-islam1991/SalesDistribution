package com.csi.salesdistribution;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Customer_Resistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__resistration);

        initToolbar();


        Spinner spinner_customer_type=(Spinner) findViewById(R.id.customer_type);
        Spinner spinner_district=(Spinner) findViewById(R.id.spinnerdistrict);
        ArrayAdapter customer_type=ArrayAdapter.createFromResource(this, R.array.TYPE, android.R.layout.simple_spinner_item);
        customer_type.setDropDownViewResource(R.layout.spinner_list);
        spinner_customer_type.setAdapter(customer_type);

        ArrayAdapter district=ArrayAdapter.createFromResource(this, R.array.DIVISION, android.R.layout.simple_spinner_item);
        district.setDropDownViewResource(R.layout.spinner_list);
        spinner_district.setAdapter(district);


    }

    private void initToolbar() {

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Customer");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
