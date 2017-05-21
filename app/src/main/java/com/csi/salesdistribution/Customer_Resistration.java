package com.csi.salesdistribution;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.csi.salesdistribution.Model.Market;
import com.csi.salesdistribution.Utility.Constants;
import com.csi.salesdistribution.Utility.Util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
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
    EditText editTextTerritory, editTextThana, editTextAddress1, editTextAddress2, editTextAddress3, editTextPhone, editTextEmail, editTextLandmark;
    EditText edittextPostName;
    AutoCompleteTextView autoCompleteMarket;
    EditText editTextPostCode;
    EditText editTextMobileNo;
    EditText editTextHiddenMarketCode;
    Button buttonResister;
    Spinner spinner_customer_type;
    Spinner spinner_district;
    EditText textviewCustomerType;
    RequestQueue requestQueue;
    String apiToken;
    //insert data
    String pattern = "^[+]?[0-9]{10,13}$";
    String patternCustType = "Select Customer type";
    //shared preference
    SharedPreferences sharedPreferences;
    String territoryName, territoryCode;
    //customer type
    private ArrayList<String> customer_type;
    private JSONArray result;
    int position;
    String customerTypeValue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__resistration);
        requestQueue = Volley.newRequestQueue(this);


        initToolbar();
        initUI();

        //get ApiToken
        Intent i = getIntent();
        Bundle b = i.getBundleExtra("personBdl");
        apiToken = b.getString("apiToken");

        //shared preference
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        territoryName = sharedPreferences.getString(Login.TERRITORY, "");
        territoryCode = sharedPreferences.getString(Login.TERRITORY_CODE, "");
        editTextTerritory.setText(territoryName);

        /*ArrayAdapter customer_type=ArrayAdapter.createFromResource(this, R.array.TYPE, android.R.layout.simple_spinner_item);
        customer_type.setDropDownViewResource(R.layout.spinner_list);
        spinner_customer_type.setAdapter(customer_type);*/

        //spinner customer type
        customer_type = new ArrayList<String>();
        getData();

        //autoComplete market
        autoCompleteMarket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String marketName= autoCompleteMarket.getText().toString();
                Market market=Util.getMarketByName(marketName);
                editTextHiddenMarketCode.setText(market.getMarketID());
            }
        });

        ArrayAdapter district = ArrayAdapter.createFromResource(this, R.array.DIVISION, android.R.layout.simple_spinner_item);
        district.setDropDownViewResource(R.layout.spinner_list);
        spinner_district.setAdapter(district);
        spinner_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor("#888888"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //autocomplete market
        autoCompleteMarket.setAdapter(new SuggestionAdapterMarket(this, autoCompleteMarket.getText().toString(), apiToken));
        //try for market
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String username, empCode;
        username = sharedPreferences.getString(Login.NAME, "");
        empCode = sharedPreferences.getString(Login.EMP_CODE, "");
        //editTextCareOf.setText(empCode);
        // Resistation Button click action
        buttonResister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get EditText Data
                final String customer_name = editTextCustomerName.getText().toString();
                //final String customer_type = spinner_customer_type.getSelectedItem().toString();
                final String postCode = editTextPostCode.getText().toString();
                final String mobileNo = editTextMobileNo.getText().toString();
                final String careOf = editTextCareOf.getText().toString();
                //final String territory = editTextTerritory.getText().toString();
                //final String market = autoCompleteMarket.getText().toString();
                final String market = editTextHiddenMarketCode.getText().toString();
                final String district = spinner_district.getSelectedItem().toString();
                final String address1 = editTextAddress1.getText().toString();
                final String address2 = editTextAddress2.getText().toString();
                final String address3 = editTextAddress3.getText().toString();
                final String thana = editTextThana.getText().toString();
                final String postName = edittextPostName.getText().toString();
                final String phoneNo = editTextPhone.getText().toString();
                final String email = editTextEmail.getText().toString();
                final String landmark = editTextLandmark.getText().toString();
                final String empCode = textViewEmployeCode.getText().toString();
                //mobile validation

                if (customer_name.trim().length() < 6) {
                    editTextCustomerName.setError("Name must be atleast 6 characters long");
                    if (TextUtils.isEmpty(customer_name)) {
                        editTextCustomerName.setError("Please enter customer name");
                        editTextCustomerName.requestFocus();
                    }
                    return;
                }
                //spinner validation
                if (customer_type.equals(patternCustType)) {
                    Toast.makeText(getApplicationContext(), "Pleae choose a valid Customer type", Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "Successfull", Toast.LENGTH_LONG).show();
                    //finish();
                }

                if (TextUtils.isEmpty(postCode)) {
                    editTextPostCode.setError("Please enter post code");
                    editTextPostCode.requestFocus();
                    return;
                }

                if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
                    editTextEmail.setError("Invalid Email Address");
                    editTextEmail.requestFocus();
                }


                if (mobileNo.length() < 11 || mobileNo.length() > 13 || mobileNo.matches(pattern) == false) {
                    editTextMobileNo.setError("Invalid Mobile No");
                    editTextMobileNo.requestFocus();
                    // am_checked=0;
                    if (TextUtils.isEmpty(mobileNo)) {
                        editTextMobileNo.setError("Please enter mobile no");
                        editTextMobileNo.requestFocus();
                    }
                    return;
                }

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.Api.API_CUSTOMER, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Customer_Resistration.this, response, Toast.LENGTH_LONG).show();
                        finish();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Customer_Resistration.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept", "application/json");
                        headers.put("Authorization", "Bearer " + apiToken);
                        return headers;

                    }
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name", customer_name);
                        //params.put("cust_type", customer_type);
                        params.put("cust_type",customerTypeValue);
                        params.put("care_of",careOf);
                        params.put("teri_code",territoryCode);
                        params.put("mkt_code",market);
                        params.put("district",district);
                        params.put("address1",address1);
                        params.put("address2",address2);
                        params.put("address3",address3);
                        params.put("thana",thana);
                        params.put("post_name",postName);
                        params.put("post_code", postCode);
                        params.put("phone", mobileNo);
                        params.put("tele_phone",phoneNo);
                        params.put("email",email);
                        params.put("land_mark",landmark);
                        params.put("mpo_code",empCode);

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

    private void getData() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.Api.API_CUSTOMER_TYPE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            result = j.getJSONArray(Constants.CustomerType.JSON_ARRAY);

                            getCustomerType(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer " + apiToken);

                return headers;

            }
        };
        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getCustomerType(JSONArray j) {
        for (int i = 0; i < j.length(); i++) {
            try {

                JSONObject json = j.getJSONObject(i);
                customer_type.add(json.getString(Constants.CustomerType.CUSTOMER_DESCRIPTION));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Setting adapter to show the items in the spinner
        spinner_customer_type.setAdapter(new ArrayAdapter<String>(Customer_Resistration.this, R.layout.spinner_list, customer_type));
    }

    //Method to get customer name of a particular position
     String getName(int position) {
         String name="";
        try {
            JSONObject json = result.getJSONObject(position);
            name = json.getString(Constants.CustomerType.CUSTOMER_TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return name;
    }

    //UI Elements
    private void initUI() {
        editTextCustomerName=(EditText)findViewById(R.id.resistation_customer_name);
        editTextCareOf=(EditText)findViewById(R.id.resistation_care_of);
        editTextThana=(EditText)findViewById(R.id.thana);
        edittextPostName=(EditText)findViewById(R.id.postName);
        editTextPostCode=(EditText)findViewById(R.id.postcode);
        editTextMobileNo=(EditText)findViewById(R.id.mobileNo);
        buttonResister=(Button)findViewById(R.id.register);
        editTextTerritory=(EditText) findViewById(R.id.territory);
        autoCompleteMarket=(AutoCompleteTextView) findViewById(R.id.market);
        editTextAddress1=(EditText) findViewById(R.id.address1);
        editTextAddress2=(EditText) findViewById(R.id.address2);
        editTextAddress3=(EditText) findViewById(R.id.address3);
        editTextPhone=(EditText) findViewById(R.id.phoneNo);
        editTextEmail=(EditText) findViewById(R.id.email);
        editTextLandmark=(EditText) findViewById(R.id.landmark);
        spinner_customer_type=(Spinner) findViewById(R.id.spinner_customer_type);
        spinner_district=(Spinner) findViewById(R.id.spinnerdistrict);
        textviewCustomerType= (EditText) findViewById(R.id.resistation_custome_type);
        editTextHiddenMarketCode = (EditText) findViewById(R.id.hiddenMarketCode);


        spinner_customer_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                customerTypeValue= getName(position);
                //editTextAddress1.setText(customerTypeValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });
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
