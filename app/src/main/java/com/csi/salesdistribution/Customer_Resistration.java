package com.csi.salesdistribution;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.csi.salesdistribution.Utility.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer_Resistration extends AppCompatActivity {
    TextView textViewSalesPerson;
    TextView textViewEmployeCode;

    EditText editTextCustomerName;
    EditText editTextCareOf;
    EditText editTextBazar;
    EditText edittextPostName;
    EditText editTextPostCode;
    EditText editTextMobileNo;
    Button buttonResister;
    Spinner spinner_customer_type;
    Spinner spinner_district;

    RequestQueue requestQueue;
    String apiToken;
    //insert data


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__resistration);
        requestQueue= Volley.newRequestQueue(this);


        initToolbar();
        initUI();

        //get ApiToken
        Intent i = getIntent();
        Bundle b = i.getBundleExtra("personBdl");
        apiToken= b.getString("apiToken");



        ArrayAdapter customer_type=ArrayAdapter.createFromResource(this, R.array.TYPE, android.R.layout.simple_spinner_item);
        customer_type.setDropDownViewResource(R.layout.spinner_list);
        spinner_customer_type.setAdapter(customer_type);

        ArrayAdapter district=ArrayAdapter.createFromResource(this, R.array.DIVISION, android.R.layout.simple_spinner_item);
        district.setDropDownViewResource(R.layout.spinner_list);
        spinner_district.setAdapter(district);


        // Resistation Button click action

        buttonResister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get EditText Data
                final String customer_name=editTextCustomerName.getText().toString();
                final String customer_type=spinner_customer_type.getSelectedItem().toString();
                final String postCode=editTextPostCode.getText().toString();
                final String mobileNo=editTextMobileNo.getText().toString();

                if (TextUtils.isEmpty(customer_name)) {
                    editTextCustomerName.setError("Please enter customer name");
                    editTextCustomerName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(postCode)) {
                    editTextPostCode.setError("Please enter post code");
                    editTextPostCode.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(mobileNo)) {
                    editTextMobileNo.setError("Please enter mobile no");
                    editTextMobileNo.requestFocus();
                    return;
                }


                StringRequest stringRequest= new StringRequest(Request.Method.POST, Constants.Api.API_CUSTOMER, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Customer_Resistration.this,response,Toast.LENGTH_LONG).show();
                        finish();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Customer_Resistration.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept", "application/json" );
                        headers.put("Authorization", "Bearer "+apiToken);

                        //params.put("name", editTextName.getText().toString());
                        //params.put("cust_type",editTextType.getText().toString());
                        // params.put("phone", editTextPhone.getText().toString());
                        // params.put("post_code", editTextPost.getText().toString());
                        return headers;

                    }

                   /*
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        String httpPostBody="name="+editTextName.getText().toString()+"&cust_type=a & phone= 123 & post_code=a";
                        // usually you'd have a field with some values you'd want to escape, you need to do it yourself if overriding getBody. here's how you do it
                        try {
                            httpPostBody=httpPostBody+"&randomFieldFilledWithAwkwardCharacters="+ URLEncoder.encode("{{%stuffToBe Escaped/","UTF-8");
                        } catch (UnsupportedEncodingException exception) {
                            Log.e("ERROR", "exception", exception);
                            // return null and don't pass any POST string if you encounter encoding error
                            return null;
                        }
                        return httpPostBody.getBytes();
                    }

                };
                stringRequest.setShouldCache(false);
                requestQueue.add(stringRequest);
              */

                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name", customer_name);
                        params.put("cust_type",customer_type);
                        params.put("phone", mobileNo);
                        params.put("post_code", postCode);
                        return params;

                    }
                };
                requestQueue.add(stringRequest);

//                JSONObject obj= new JSONObject();
//                try {
//                    obj.put("name", editTextName.getText().toString());
//                    obj.put("cust_type",editTextType.getText().toString());
//                    obj.put("phone", editTextPhone.getText().toString());
//                    obj.put("post_code", editTextPost.getText().toString());
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,DataParseUrl,null,
//                        new Response.Listener<JSONObject>() {
//
//
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                Toast.makeText(MainActivity.this,response.toString(),Toast.LENGTH_LONG).show();
//
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.d("Customer",error.toString());
//                                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
//
//                            }
//
//                        })
//                {
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        Map<String, String> headers = new HashMap<String, String>();
//                        headers.put("Accept", "application/json" );
//                        headers.put("Authorization", "Bearer 0TTrqN0R3ucJOD2k6PM9DvaisRzPuDYX8iKk0JoNzEizwli4gE1489648584");
//
//                        //params.put("name", editTextName.getText().toString());
//                        //params.put("cust_type",editTextType.getText().toString());
//                       // params.put("phone", editTextPhone.getText().toString());
//                       // params.put("post_code", editTextPost.getText().toString());
//                        return headers;
//                    };
//
//                    protected Map<String, String> getBody() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("name", editTextName.getText().toString());
//                        params.put("cust_type",editTextType.getText().toString());
//                         params.put("phone", editTextPhone.getText().toString());
//                         params.put("post_code", editTextPost.getText().toString());
//                        return params;
//                    };
//                };
//                requestQueue.add(jsObjRequest);


            }
        });




    }








    //UI Elements
    private void initUI() {
        editTextCustomerName=(EditText)findViewById(R.id.resistation_customer_name);
        editTextCareOf=(EditText)findViewById(R.id.resistation_care_of);
        editTextBazar=(EditText)findViewById(R.id.bazar);
        edittextPostName=(EditText)findViewById(R.id.postName);
        editTextPostCode=(EditText)findViewById(R.id.postcode);
        editTextMobileNo=(EditText)findViewById(R.id.mobileNo);
        buttonResister=(Button)findViewById(R.id.register);
        spinner_customer_type=(Spinner) findViewById(R.id.spinner_customer_type);
        spinner_district=(Spinner) findViewById(R.id.spinnerdistrict);
    }


    //Toolbar implementation

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
