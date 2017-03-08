package com.csi.salesdistribution;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class OrderEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_entry);

        initToolbar();
        initUI();

        Spinner payment_method= (Spinner) findViewById(R.id.spinner_payment_method);

        ArrayAdapter payment=ArrayAdapter.createFromResource(this, R.array.PAYMENT, android.R.layout.simple_spinner_item);
        payment.setDropDownViewResource(R.layout.spinner_list);
        payment_method.setAdapter(payment);

        //Expand more product entry

    }
//Find UI
    public void initUI() {
        final RelativeLayout produclayout=(RelativeLayout) findViewById(R.id.ProductrelativeLayout);
        //Linearlayout entry
        LinearLayout l1=(LinearLayout)findViewById(R.id.l1);
        final LinearLayout l2=(LinearLayout)findViewById(R.id.l2);
        final LinearLayout l3=(LinearLayout)findViewById(R.id.l3);
        final LinearLayout l4=(LinearLayout)findViewById(R.id.l4);
        final LinearLayout l5=(LinearLayout)findViewById(R.id.l5);
        final LinearLayout l6=(LinearLayout)findViewById(R.id.l6);
        final LinearLayout l7=(LinearLayout)findViewById(R.id.l7);
        final LinearLayout l8=(LinearLayout)findViewById(R.id.l8);
        final LinearLayout l9=(LinearLayout)findViewById(R.id.l9);
        final LinearLayout l10=(LinearLayout)findViewById(R.id.l10);

      //button entry
        final Button buttonexpand1= (Button) findViewById(R.id.b1);
        Button buttonexpand2= (Button) findViewById(R.id.b2);
        Button buttonexpand3= (Button) findViewById(R.id.b3);
        Button buttonexpand4= (Button) findViewById(R.id.b4);
        Button buttonexpand5= (Button) findViewById(R.id.b5);
        Button buttonexpand6= (Button) findViewById(R.id.b6);
        Button buttonexpand7= (Button) findViewById(R.id.b7);
        Button buttonexpand8= (Button) findViewById(R.id.b8);
        Button buttonexpand9= (Button) findViewById(R.id.b9);
        Button buttonexpand10= (Button) findViewById(R.id.b10);

        buttonexpand1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l2.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = produclayout.getLayoutParams();
                params.height=360 ;
                produclayout.setLayoutParams(params);
            }
        });
        buttonexpand2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l3.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = produclayout.getLayoutParams();
                params.height=540 ;
                produclayout.setLayoutParams(params);
            }
        });
        buttonexpand3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l4.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = produclayout.getLayoutParams();
                params.height=720 ;
                produclayout.setLayoutParams(params);
            }
        });
        buttonexpand4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l5.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = produclayout.getLayoutParams();
                params.height=900 ;
                produclayout.setLayoutParams(params);
            }
        });
        buttonexpand5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l6.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = produclayout.getLayoutParams();
                params.height=1090 ;
                produclayout.setLayoutParams(params);
            }
        });
        buttonexpand6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l7.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = produclayout.getLayoutParams();
                params.height=1260 ;
                produclayout.setLayoutParams(params);
            }
        });
        buttonexpand7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l8.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = produclayout.getLayoutParams();
                params.height=1450 ;
                produclayout.setLayoutParams(params);
            }
        });
        buttonexpand8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l9.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = produclayout.getLayoutParams();
                params.height=1620 ;
                produclayout.setLayoutParams(params);
            }
        });
        buttonexpand9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l10.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = produclayout.getLayoutParams();
                params.height=1800 ;
                produclayout.setLayoutParams(params);
            }
        });



    }


    private void initToolbar() {

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Order Entry");
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
