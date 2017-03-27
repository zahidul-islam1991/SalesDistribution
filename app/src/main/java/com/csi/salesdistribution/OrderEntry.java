package com.csi.salesdistribution;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.csi.salesdistribution.Model.Product;
import com.csi.salesdistribution.Utility.Constants;
import com.csi.salesdistribution.Utility.MonthToText;
import com.csi.salesdistribution.Utility.Util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import static com.csi.salesdistribution.R.id.textView;

public class OrderEntry extends AppCompatActivity {
      TextView textViewSalesPerson;
      TextView textViewEmployeCode;
      EditText editTextOrderDate, editTextDeliveryDate;
      EditText editTextQuantity1,editTextQuantity2;
      AutoCompleteTextView autoCompleteCustomer,autoCompleteProductName1,autoCompleteProductName2,autoCompleteProductName3,autoCompleteProductName4,autoCompleteProductName5,autoCompleteProductName6,autoCompleteProductName7,autoCompleteProductName8,autoCompleteProductName9,autoCompleteProductName10;
      String apiToken;
      EditText editTextpack1;
      EditText editTextTradevalue1;
      EditText editTextTradeprice1;
      EditText editTextVat1;
      EditText editTextDiscount1;
      EditText editTextNetAmount1;
      Button buttonCalculate1;
      Calendar calendar;
      int year,day,month,time;
      ArrayList<Product> productArrayList = new ArrayList<Product>();
     String rate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_entry);

        initToolbar();
        initUI();


        //get ApiToken
        Intent i = getIntent();
        Bundle b = i.getBundleExtra("personBdl");
        apiToken= b.getString("apiToken");





        //Order Date picker
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DATE);

        editTextOrderDate.setText(MonthToText.mothNameText(day + "-" + (month + 1) + "-" + year));
        editTextDeliveryDate.setText(MonthToText.mothNameText(day + "-" + (month + 1) + "-" + year));

        editTextOrderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int monthOfYear, int dayOfMonth) {
                        editTextOrderDate.setText(MonthToText.mothNameText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + mYear));
                    }
                }, year, month, day);
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show();

            }
        });
         editTextDeliveryDate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePicker view, int mYear, int monthOfYear, int dayOfMonth) {
                         editTextOrderDate.setText(MonthToText.mothNameText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + mYear));
                     }
                 }, year, month, day);
                 datePickerDialog.setTitle("Select Date");
                 datePickerDialog.show();

             }
         });

        //autoComplete customer
        autoCompleteCustomer.setAdapter(new SuggestionAdapter(this, autoCompleteCustomer.getText().toString(),apiToken));
        //autoComplete Product
        autoCompleteProductName1.setAdapter(new SuggestionAdapterProduct(this, autoCompleteProductName1.getText().toString(),apiToken));
        autoCompleteProductName2.setAdapter(new SuggestionAdapterProduct(this, autoCompleteProductName2.getText().toString(),apiToken));
        autoCompleteProductName3.setAdapter(new SuggestionAdapterProduct(this, autoCompleteProductName3.getText().toString(),apiToken));
        autoCompleteProductName4.setAdapter(new SuggestionAdapterProduct(this, autoCompleteProductName4.getText().toString(),apiToken));
        autoCompleteProductName5.setAdapter(new SuggestionAdapterProduct(this, autoCompleteProductName5.getText().toString(),apiToken));
        autoCompleteProductName6.setAdapter(new SuggestionAdapterProduct(this, autoCompleteProductName6.getText().toString(),apiToken));
        autoCompleteProductName7.setAdapter(new SuggestionAdapterProduct(this, autoCompleteProductName7.getText().toString(),apiToken));
        autoCompleteProductName8.setAdapter(new SuggestionAdapterProduct(this, autoCompleteProductName8.getText().toString(),apiToken));
        autoCompleteProductName9.setAdapter(new SuggestionAdapterProduct(this, autoCompleteProductName9.getText().toString(),apiToken));
        autoCompleteProductName10.setAdapter(new SuggestionAdapterProduct(this, autoCompleteProductName10.getText().toString(),apiToken));





        autoCompleteCustomer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    if(autoCompleteCustomer.getText().toString().trim().length()<3){
                        autoCompleteCustomer.setError("Please enter atleast 3 character");
                    }
                    else {
                        autoCompleteCustomer.setError(null);
                    }
                }
            }
        });


        // Order date and delivary date
        //EditText orderdate=(EditText)findViewById(R.id.order_date);
        //EditText delivarydate=(EditText)findViewById(R.id.delivary_date);
       // EditText editTextMarket=(EditText)findViewById(R.id.market);


        //Datepicker for order date


        Spinner payment_method= (Spinner) findViewById(R.id.spinner_payment_method);


        ArrayAdapter payment=ArrayAdapter.createFromResource(this, R.array.PAYMENT, android.R.layout.simple_spinner_item);
        payment.setDropDownViewResource(R.layout.spinner_list);
        payment_method.setAdapter(payment);

        //Expand more product entry

    }

    //Methode For GetActivity OrderDatePicker
    private Context getActivity() {
        return this;
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

        buttonCalculate1=(Button) findViewById(R.id.calculate1);
        //EditText Order & Delivery Date
        editTextOrderDate=(EditText) findViewById(R.id.order_date);
        editTextDeliveryDate=(EditText) findViewById(R.id.delivary_date);

        //autoComplete textview
        autoCompleteCustomer=(AutoCompleteTextView) findViewById(R.id.Order_customer_name);
        autoCompleteProductName1=(AutoCompleteTextView) findViewById(R.id.ProductName1);
        autoCompleteProductName2=(AutoCompleteTextView) findViewById(R.id.ProductName2);
        autoCompleteProductName3=(AutoCompleteTextView) findViewById(R.id.ProductName3);
        autoCompleteProductName4=(AutoCompleteTextView) findViewById(R.id.ProductName4);
        autoCompleteProductName5=(AutoCompleteTextView) findViewById(R.id.ProductName5);
        autoCompleteProductName6=(AutoCompleteTextView) findViewById(R.id.ProductName6);
        autoCompleteProductName7=(AutoCompleteTextView) findViewById(R.id.ProductName7);
        autoCompleteProductName8=(AutoCompleteTextView) findViewById(R.id.ProductName8);
        autoCompleteProductName9=(AutoCompleteTextView) findViewById(R.id.ProductName9);
        autoCompleteProductName10=(AutoCompleteTextView) findViewById(R.id.ProductName10);

        //first product entry calculation
        autoCompleteProductName1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               String productName1 = autoCompleteProductName1.getText().toString();
                if (productName1 != null || !productName1.equalsIgnoreCase("")) {
                    Product product = Util.getProductByName(productName1);
                    editTextTradeprice1.setText(product.getRate());
                    editTextTradevalue1.setText(product.getRate());
                    editTextpack1.setText(product.getPack_size());

                    productArrayList.add(product);
                    //calculation
                    buttonCalculate1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String quan=editTextQuantity1.getText().toString();
                            String price=editTextTradeprice1.getText().toString();
                            String vat=editTextVat1.getText().toString();
                            String dis=editTextDiscount1.getText().toString();

                            if (TextUtils.isEmpty(quan)) {
                                editTextQuantity1.setError("Please enter Quantity");
                                editTextQuantity1.requestFocus();
                                return;
                            }
                            if (TextUtils.isEmpty(vat)) {
                                editTextVat1.setError("Please enter VAT");
                                editTextVat1.requestFocus();
                                return;
                            }


                            double quantity=Double.parseDouble(quan);
                            double price1=Double.parseDouble(price);
                            double realVat=Double.parseDouble(vat);
                            double discount=Double.parseDouble(dis);

                            double pricewithoutVAT,pricewithVAT,netTotal;
                            pricewithoutVAT=price1*quantity;
                            double VAT=(pricewithoutVAT * realVat)/100;
                            pricewithVAT=pricewithoutVAT+VAT;
                            netTotal=pricewithVAT-discount;
                            editTextNetAmount1.setText(String.valueOf(netTotal));
                        }
                    });
                }
            }
        });
        autoCompleteProductName2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String productName2 = autoCompleteProductName2.getText().toString();

                if (productName2 != null || !productName2.equalsIgnoreCase("")) {
                    Product product = Util.getProductByName(productName2);
                    editTextQuantity2.setText(product.getRate());
                    productArrayList.add(product);
                }
            }
        });

        //Quantity
        editTextQuantity1 =(EditText) findViewById(R.id.quantity1);
        editTextQuantity2=(EditText) findViewById(R.id.quantity2);
        //pack_size
        editTextpack1=(EditText) findViewById(R.id.pack1);
        //Tradevalue
        editTextTradevalue1=(EditText) findViewById(R.id.tradeValue1);
        //Tradeprice
        editTextTradeprice1=(EditText) findViewById(R.id.tradePrice1);
        //Net Amount
        editTextNetAmount1=(EditText) findViewById(R.id.netAmount1);
        //Vat
        editTextVat1=(EditText) findViewById(R.id.vat1);
        //Discount
        editTextDiscount1=(EditText) findViewById(R.id.discountValue1);

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
