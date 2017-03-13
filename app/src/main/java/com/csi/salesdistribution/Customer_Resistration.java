package com.csi.salesdistribution;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Customer_Resistration extends AppCompatActivity {
TextView textViewSalesPerson;
    TextView textViewCustomerName;
    TextView textViewEmployeCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__resistration);

        textViewCustomerName=(TextView)findViewById(R.id.customer_name) ;
        /*Intent i = getIntent();
        Bundle b = i.getBundleExtra("personBdl");
        String name = b.getString("name");
        textViewCustomerName.setText(name);
        */
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

        textViewSalesPerson=(TextView)toolbar.findViewById(R.id.toolbarSalesPerson);
        textViewEmployeCode=(TextView)toolbar.findViewById(R.id.toolbarEmployeCode);
        Intent i = getIntent();
        Bundle b = i.getBundleExtra("personBdl");
        String name = b.getString("name");
        String code=b.getString("empCode");
        textViewSalesPerson.setText(name);
        textViewEmployeCode.setText(code);






        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
