package com.csi.salesdistribution;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import android.widget.Toast;

import com.csi.salesdistribution.Model.Customer;
import com.csi.salesdistribution.Model.Product;
import com.csi.salesdistribution.Utility.Constants;
import com.csi.salesdistribution.Utility.MonthToText;
import com.csi.salesdistribution.Utility.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import static com.csi.salesdistribution.R.id.textView;

public class OrderEntry extends AppCompatActivity {
      TextView textViewSalesPerson;
      TextView textViewEmployeCode;
      EditText editTextOrderDate, editTextDeliveryDate,editTextMarketPlace;
      String productName1,productName2,productName3,productName4,productName5,productName6,productName7,productName8,productName9,productName10;
      EditText editTextQuantity1,editTextQuantity2,editTextQuantity3,editTextQuantity4,editTextQuantity5,editTextQuantity6,editTextQuantity7,editTextQuantity8,editTextQuantity9,editTextQuantity10;
      AutoCompleteTextView autoCompleteCustomer,autoCompleteProductName1,autoCompleteProductName2,autoCompleteProductName3,autoCompleteProductName4,autoCompleteProductName5,autoCompleteProductName6,autoCompleteProductName7,autoCompleteProductName8,autoCompleteProductName9,autoCompleteProductName10;
      String apiToken;
      EditText editTextpack1,editTextpack2,editTextpack3,editTextpack4,editTextpack5,editTextpack6,editTextpack7,editTextpack8,editTextpack9,editTextpack10;
      EditText editTextTradevalue1,editTextTradevalue2,editTextTradevalue3,editTextTradevalue4,editTextTradevalue5,editTextTradevalue6,editTextTradevalue7,editTextTradevalue8,editTextTradevalue9,editTextTradevalue10;
      EditText editTextTradeprice1,editTextTradeprice2,editTextTradeprice3,editTextTradeprice4,editTextTradeprice5,editTextTradeprice6,editTextTradeprice7,editTextTradeprice8,editTextTradeprice9,editTextTradeprice10;
      EditText editTextVat1,editTextVat2,editTextVat3,editTextVat4,editTextVat5,editTextVat6,editTextVat7,editTextVat8,editTextVat9,editTextVat10;
      EditText editTextDiscount1,editTextDiscount2,editTextDiscount3,editTextDiscount4,editTextDiscount5,editTextDiscount6,editTextDiscount7,editTextDiscount8,editTextDiscount9,editTextDiscount10;
      EditText editTextNetAmount1,editTextNetAmount2,editTextNetAmount3,editTextNetAmount4,editTextNetAmount5,editTextNetAmount6,editTextNetAmount7,editTextNetAmount8,editTextNetAmount9,editTextNetAmount10;
      Spinner payment_method;

      //Result
      EditText editTextTotalNoItem,editTextTotalTradeValue,editTextTotalDiscountValue,editTextTotalVAT,editTextTotalNetAmount;
      Button buttonSave;
      double pricewithoutVAT1,pricewithoutVAT2,pricewithoutVAT3,pricewithoutVAT4,pricewithoutVAT5,pricewithoutVAT6,pricewithoutVAT7,pricewithoutVAT8,pricewithoutVAT9,pricewithoutVAT10;
      double resultTradeValue,TotalVAT;
      double pricewithVAT;
      double discount1,discount2,discount3,discount4,discount5,discount6,discount7,discount8,discount9,discount10,TotalDiscount;
      double VAT1,VAT2,VAT3,VAT4,VAT5,VAT6,VAT7,VAT8,VAT9,VAT10;
      double netTotal,TotalNetAmount;
      Calendar calendar;
      int year,day,month,time;
      EditText editTextCustomerID;
      ArrayList<Product> productArrayList = new ArrayList<Product>();
      ArrayList<Customer> customerArrayList = new ArrayList<Customer>();
      String rate,quan;
      String defaultValue="0";
      String pattern = "^([a-zA-Z ]*)$";
      String invalidValu=":";
      String marketCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_entry);

        initToolbar();
        initUI();
        Calculation();


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

       // today try for substring
       autoCompleteCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               String cutomerNmae = autoCompleteCustomer.getText().toString();
               if(cutomerNmae.contains(invalidValu)){
                   autoCompleteCustomer.setError("This is not a name \nChoose a valid Name");
               }
               try {
                   if (cutomerNmae != null || !cutomerNmae.equalsIgnoreCase("")) {
                       Customer customer = Util.getCustomerByName(cutomerNmae);
                       //editTextMarketPlace.setText(customer.getPhone());
                       marketCode= customer.getCareOf();
                       editTextMarketPlace.setText(marketCode);
                   }
               }catch (Exception e){e.printStackTrace();}
           }
       });



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



        //save Product entry
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int totalProduct = Integer.parseInt(editTextTotalNoItem.getText().toString());
                String quantity = "", discount = "", netAmount = "";
                String orderDate = "", deliveryDate = "", paymentMethod = "", customerName = "", marketPlace = "";
                String totalNoItem = "", totalTradeValue = "", totalDiscount = "", totalVat = "", totalNetAmount = "";



                for (int i = 0; i < totalProduct; i++) {
                    String productName = "";

                    if (i == 0) {
                        productName = autoCompleteProductName1.getText().toString();
                        quantity = editTextQuantity1.getText().toString();
                        discount = editTextDiscount1.getText().toString();
                        netAmount = editTextNetAmount1.getText().toString();
                    } else if (i == 1) {
                        productName = autoCompleteProductName2.getText().toString();
                        quantity = editTextQuantity2.getText().toString();
                        discount = editTextDiscount2.getText().toString();
                        netAmount = editTextNetAmount2.getText().toString();
                    } else if (i == 2) {
                        productName = autoCompleteProductName3.getText().toString();
                        quantity = editTextQuantity3.getText().toString();
                        discount = editTextDiscount3.getText().toString();
                        netAmount = editTextNetAmount3.getText().toString();
                    } else if (i == 3) {
                        productName = autoCompleteProductName4.getText().toString();
                        quantity = editTextQuantity4.getText().toString();
                        discount = editTextDiscount4.getText().toString();
                        netAmount = editTextNetAmount4.getText().toString();
                    } else if (i == 4) {
                        productName = autoCompleteProductName5.getText().toString();
                        quantity = editTextQuantity5.getText().toString();
                        discount = editTextDiscount5.getText().toString();
                        netAmount = editTextNetAmount5.getText().toString();
                    } else if (i == 5) {
                        productName = autoCompleteProductName6.getText().toString();
                        quantity = editTextQuantity6.getText().toString();
                        discount = editTextDiscount6.getText().toString();
                        netAmount = editTextNetAmount6.getText().toString();
                    } else if (i == 6) {
                        productName = autoCompleteProductName7.getText().toString();
                        quantity = editTextQuantity7.getText().toString();
                        discount = editTextDiscount7.getText().toString();
                        netAmount = editTextNetAmount7.getText().toString();
                    } else if (i == 7) {
                        productName = autoCompleteProductName8.getText().toString();
                        quantity = editTextQuantity8.getText().toString();
                        discount = editTextDiscount8.getText().toString();
                        netAmount = editTextNetAmount8.getText().toString();
                    } else if (i == 8) {
                        productName = autoCompleteProductName9.getText().toString();
                        quantity = editTextQuantity9.getText().toString();
                        discount = editTextDiscount9.getText().toString();
                        netAmount = editTextNetAmount9.getText().toString();
                    } else if (i == 9) {
                        productName = autoCompleteProductName10.getText().toString();
                        quantity = editTextQuantity10.getText().toString();
                        discount = editTextDiscount10.getText().toString();
                        netAmount = editTextNetAmount10.getText().toString();
                    }

                    Product product = Util.getProductByName(productName);
                    product.setQuantity(quantity);
                    product.setDiscount(discount);
                    product.setNetAmount(netAmount);
                    productArrayList.add(product);
                }


                orderDate = editTextOrderDate.getText().toString();
                deliveryDate = editTextDeliveryDate.getText().toString();
                paymentMethod = payment_method.getSelectedItem().toString();
                customerName = autoCompleteCustomer.getText().toString();

                Customer customer=Util.getCustomerByName(customerName);



                marketPlace = editTextMarketPlace.getText().toString();
                totalNoItem = editTextTotalNoItem.getText().toString();
                totalTradeValue = editTextTotalTradeValue.getText().toString();
                totalDiscount = editTextTotalDiscountValue.getText().toString();
                totalVat = editTextTotalVAT.getText().toString();
                totalNetAmount = editTextTotalNetAmount.getText().toString();

                if (TextUtils.isEmpty(customerName)) {
                    autoCompleteCustomer.setError("Enter Customer name");
                    autoCompleteCustomer.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(marketPlace)) {
                    editTextMarketPlace.setError("Enter Market name");
                    editTextMarketPlace.requestFocus();
                    return;
                }
                if (productName1==null){
                    autoCompleteProductName1.setError("Enter Product name");
                    autoCompleteProductName1.requestFocus();
                    return;
                }
                if (quan==null){
                    editTextQuantity1.setError("Enter Quantity");
                    editTextQuantity1.requestFocus();
                    return;
                }



                JSONObject data = new JSONObject();

                try {

                    ArrayList<String> productNamelist = new ArrayList<String>();
                    ArrayList<String> productQuantitylist = new ArrayList<String>();
                    ArrayList<String> productPackSizelist = new ArrayList<String>();
                    ArrayList<String> productTradeValuelist = new ArrayList<String>();
                    ArrayList<String> productTradePricelist = new ArrayList<String>();
                    ArrayList<String> productDiscountlist = new ArrayList<String>();
                    ArrayList<String> productVatlist = new ArrayList<String>();
                    ArrayList<String> productNetAmountlist = new ArrayList<String>();
                    ArrayList<String> customerlist = new ArrayList<String>();

                    for (Product product : productArrayList) {
                        productNamelist.add(product.getProd_id());
                        productQuantitylist.add(product.getQuantity());
                        productPackSizelist.add(product.getPack_size());
                        productTradeValuelist.add(product.getTradeValue());
                        productTradePricelist.add(product.getTradePrice());
                        productDiscountlist.add(product.getDiscount());
                        productVatlist.add(product.getVat());
                        productNetAmountlist.add(product.getNetAmount());
                    }
                    customerlist.add(customer.getId());

                    data.put("product", productNamelist);
                    data.put("quantity", productQuantitylist);
                    data.put("packSize", productPackSizelist);
                    data.put("tradeValue", productTradeValuelist);
                    data.put("tradePrice", productTradePricelist);
                    data.put("discount", productDiscountlist);
                    data.put("vat", productVatlist);
                    data.put("netAmount", productNetAmountlist);

                    data.put("orderDate", orderDate);
                    data.put("deliveryDate", deliveryDate);
                    data.put("paymentMethod", paymentMethod);
                    //data.put("customerName", customerName);
                    data.put("customerName", customerlist);
                    data.put("marketPlace", marketPlace);

                    data.put("totalNoItem", totalNoItem);
                    data.put("totalTradeValue", totalTradeValue);
                    data.put("totalDiscount", totalDiscount);
                    data.put("totalVat", totalVat);
                    data.put("totalNetAmount", totalNetAmount);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

               //add background inline class here
                class SendDataToServer extends AsyncTask<String,String,String> {
                    private Dialog loadingDialog;
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loadingDialog = ProgressDialog.show(OrderEntry.this, "Please wait", "Loading...");
                    }

                    @Override
                    protected String doInBackground(String... params) {
                        String JsonResponse = null;
                        String JsonDATA = params[0];


                        HttpURLConnection urlConnection = null;
                        BufferedReader reader = null;

                        try {
                            URL url = new URL(Constants.Api.API_ORDER_ENTRY);
                            urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setDoOutput(true);
                            // is output buffer writter
                            urlConnection.setRequestMethod("POST");
                            urlConnection.setRequestProperty("Content-Type", "application/json");
                            urlConnection.setRequestProperty("Accept", "application/json");
                            //set headers and method
                            urlConnection.addRequestProperty("Authorization","Bearer "+apiToken);
                            urlConnection.addRequestProperty("Accept","application/json");
                            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                            writer.write(JsonDATA);
                            // json data
                            writer.close();
                            InputStream inputStream = urlConnection.getInputStream();
                            //input stream
                            StringBuffer buffer = new StringBuffer();
                            if (inputStream == null) {
                                // Nothing to do.
                                return null;
                            }
                            reader = new BufferedReader(new InputStreamReader(inputStream));

                            String inputLine;
                            while ((inputLine = reader.readLine()) != null)
                                buffer.append(inputLine + "\n");
                            if (buffer.length() == 0) {
                                // Stream was empty. No point in parsing.
                                return null;
                            }
                            JsonResponse = buffer.toString();
                            Log.i("TAG",JsonResponse);
                            try {
//send to post execute
                                return JsonResponse;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        finally {
                            if (urlConnection != null) {
                                urlConnection.disconnect();
                            }
                            if (reader != null) {
                                try {
                                    reader.close();
                                } catch (final IOException e) {
                                    // Log.e(TAG, "Error closing stream", e);
                                }
                            }
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        loadingDialog.dismiss();
                        Toast toast= Toast.makeText(getApplicationContext(), "  Order entry successfull  ", Toast.LENGTH_LONG);
                        View view1=toast.getView();
                        view1.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4caf50")));
                        view1.setBackgroundResource(R.drawable.toast_style);
                        toast.show();
                        finish();
                    }

                }

                if (data.length() > 0) {
                    new SendDataToServer().execute(String.valueOf(data));

                }
            }
        });

        ArrayAdapter payment=ArrayAdapter.createFromResource(this, R.array.PAYMENT, android.R.layout.simple_spinner_item);
        payment.setDropDownViewResource(R.layout.spinner_list);
        payment_method.setAdapter(payment);
        payment_method.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Expand more product entry

    }


    private void Calculation() {

                 //1st product
                 autoCompleteProductName1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                     @Override
                     public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                         productName1 = autoCompleteProductName1.getText().toString();
                         if (productName1 != null || !productName1.equalsIgnoreCase("")) {
                             Product product = Util.getProductByName(productName1);
                             editTextTradeprice1.setText(product.getTradePrice());
                             editTextTradevalue1.setText(product.getTradeValue());
                             editTextpack1.setText(product.getPack_size());
                            // editTextVat1.setText(Constants.StaticValue.VAT);
                             editTextVat1.setText(product.getVat());
                             editTextDiscount1.setText(product.getDiscount());
                             editTextTotalNoItem.setText("1");
                             //productArrayList.add(product);
                             editTextQuantity1.addTextChangedListener(new TextWatcher() {
                                 @Override
                                 public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                 }

                                 @Override
                                 public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                     quan=editTextQuantity1.getText().toString();
                                     if (quan.matches(pattern)){
                                         editTextQuantity1.setError("Enter valid quantity");
                                         editTextQuantity1.requestFocus();
                                         return;
                                     }
                                     if(quan.equals(defaultValue)){
                                         editTextQuantity1.setError("Enter valid quantity");
                                         editTextQuantity1.requestFocus();
                                         return;
                                     }
                                     quan = (quan.isEmpty()) ? "0" :quan;
                                     String price=editTextTradeprice1.getText().toString();
                                     String vat=editTextVat1.getText().toString();
                                     String dis=editTextDiscount1.getText().toString();
                                     double quantity=Double.parseDouble(quan);
                                     double price1=Double.parseDouble(price);
                                     double realVat=Double.parseDouble(vat);
                                    try {
                                        discount1 = Double.parseDouble(dis);
                                    }
                                    catch (Exception e){}
                                      //pricewithVAT,netTotal;
                                     pricewithoutVAT1=price1*quantity;
                                     VAT1=(pricewithoutVAT1 * realVat)/100;
                                     pricewithVAT=pricewithoutVAT1+VAT1;
                                     netTotal=pricewithVAT-discount1;
                                     editTextNetAmount1.setText(String.valueOf(netTotal));
                                     //resut
                                     editTextTotalNoItem.setText("1");
                                     editTextTotalTradeValue.setText(String.valueOf(pricewithoutVAT1));
                                     editTextTotalDiscountValue.setText(String.valueOf(discount1));
                                     editTextTotalVAT.setText(String.valueOf(VAT1));
                                     TotalNetAmount=(pricewithoutVAT1+VAT1)-discount1;
                                     editTextTotalNetAmount.setText(String.valueOf(TotalNetAmount));

                                 }

                                 @Override
                                 public void afterTextChanged(Editable editable) {

                                 }
                             });
                             editTextDiscount1.addTextChangedListener(new TextWatcher() {
                                 @Override
                                 public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                 }

                                 @Override
                                 public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                     String dis=editTextDiscount1.getText().toString();
                                     dis = (dis.isEmpty()) ? "0" :dis;
                                     if (dis.matches(pattern)){
                                         editTextDiscount1.setError("Invalid");
                                         editTextDiscount1.requestFocus();
                                         //editTextDiscount1.setText("0");
                                         return;
                                     }
                                     discount1=Double.parseDouble(dis);

                                     //double priceithVAT,netTotal;
                                     netTotal=pricewithVAT-discount1;
                                     editTextNetAmount1.setText(String.valueOf(netTotal));
                                     //resut
                                     editTextTotalNoItem.setText("1");
                                     editTextTotalTradeValue.setText(String.valueOf(pricewithoutVAT1));
                                     editTextTotalDiscountValue.setText(String.valueOf(discount1));
                                     TotalNetAmount=(pricewithoutVAT1+VAT1)-discount1;
                                     editTextTotalNetAmount.setText(String.valueOf(TotalNetAmount));

                                 }

                                 @Override
                                 public void afterTextChanged(Editable editable) {

                                 }
                             });

                         }
                     }
                 });

       //2nd Product
        autoCompleteProductName2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                productName2 = autoCompleteProductName2.getText().toString();
                if (productName2 != null || !productName2.equalsIgnoreCase("")) {
                    Product product = Util.getProductByName(productName2);
                    editTextTradeprice2.setText(product.getTradePrice());
                    editTextTradevalue2.setText(product.getTradeValue());
                    editTextpack2.setText(product.getPack_size());
                    //editTextVat2.setText(Constants.StaticValue.VAT);
                    editTextVat2.setText(product.getVat());
                    editTextDiscount2.setText(product.getDiscount());
                    editTextTotalNoItem.setText("2");
                    //productArrayList.add(product);
                    editTextQuantity2.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            String quan=editTextQuantity2.getText().toString();
                            if (quan.matches(pattern)){
                                editTextQuantity2.setError("Enter valid quantity");
                                editTextQuantity2.requestFocus();
                                return;
                            }
                            if(quan.equals(defaultValue)){
                                editTextQuantity2.setError("Enter valid quantity");
                                editTextQuantity2.requestFocus();
                                return;
                            }
                            quan = (quan.isEmpty()) ? "0" :quan;
                            String price=editTextTradeprice2.getText().toString();
                            String vat=editTextVat2.getText().toString();
                            String dis=editTextDiscount2.getText().toString();
                            double quantity=Double.parseDouble(quan);
                            double price1=Double.parseDouble(price);
                            double realVat=Double.parseDouble(vat);
                            try {
                                discount2 = Double.parseDouble(dis);
                            }catch (Exception e){}
                            //pricewithVAT,netTotal;
                            pricewithoutVAT2=price1*quantity;
                             VAT2=(pricewithoutVAT2 * realVat)/100;
                            pricewithVAT=pricewithoutVAT2+VAT2;
                            netTotal=pricewithVAT-discount2;
                            editTextNetAmount2.setText(String.valueOf(netTotal));
                            //resut
                            editTextTotalNoItem.setText("2");
                            resultTradeValue=pricewithoutVAT1+pricewithoutVAT2;
                            TotalDiscount= discount1+discount2;
                            TotalVAT=VAT1+VAT2;
                            editTextTotalTradeValue.setText(String.valueOf(resultTradeValue));
                            editTextTotalDiscountValue.setText(String.valueOf(TotalDiscount));
                            editTextTotalVAT.setText(String.valueOf(TotalVAT));
                            TotalNetAmount=(resultTradeValue+TotalVAT)-TotalDiscount;
                            editTextTotalNetAmount.setText(String.valueOf(TotalNetAmount));

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                    editTextDiscount2.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            String dis=editTextDiscount2.getText().toString();
                            dis = (dis.isEmpty()) ? "0" :dis;
                            if (dis.matches(pattern)){
                                editTextDiscount2.setError("Invalid");
                                editTextDiscount2.requestFocus();
                                return;
                            }
                            discount2=Double.parseDouble(dis);
                            //double priceithVAT,netTotal;
                            netTotal=pricewithVAT-discount2;
                            editTextNetAmount2.setText(String.valueOf(netTotal));
                            //resut
                            editTextTotalNoItem.setText("2");
                            resultTradeValue=pricewithoutVAT1+pricewithoutVAT2;
                            TotalDiscount=discount1+discount2;
                            editTextTotalTradeValue.setText(String.valueOf(resultTradeValue));
                            editTextTotalDiscountValue.setText(String.valueOf(TotalDiscount));
                            TotalNetAmount=(resultTradeValue+TotalVAT)-TotalDiscount;
                            editTextTotalNetAmount.setText(String.valueOf(TotalNetAmount));
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                }
            }
        });
                 //3rd Product
        autoCompleteProductName3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                productName3 = autoCompleteProductName3.getText().toString();
                if (productName3 != null || !productName3.equalsIgnoreCase("")) {
                    Product product = Util.getProductByName(productName3);
                    editTextTradeprice3.setText(product.getTradePrice());
                    editTextTradevalue3.setText(product.getTradeValue());
                    editTextpack3.setText(product.getPack_size());
                    //editTextVat3.setText(Constants.StaticValue.VAT);
                    editTextVat3.setText(product.getVat());
                    editTextDiscount3.setText(product.getDiscount());
                    editTextTotalNoItem.setText("3");
                   // productArrayList.add(product);
                    editTextQuantity3.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            String quan=editTextQuantity3.getText().toString();
                            if (quan.matches(pattern)){
                                editTextQuantity3.setError("Enter valid quantity");
                                editTextQuantity3.requestFocus();
                                return;
                            }
                            if(quan.equals(defaultValue)){
                                editTextQuantity3.setError("Enter valid quantity");
                                editTextQuantity3.requestFocus();
                                return;
                            }
                            quan = (quan.isEmpty()) ? "0" :quan;
                            String price=editTextTradeprice3.getText().toString();
                            String vat=editTextVat3.getText().toString();
                            String dis=editTextDiscount3.getText().toString();
                            double quantity=Double.parseDouble(quan);
                            double price1=Double.parseDouble(price);
                            double realVat=Double.parseDouble(vat);
                            try {
                                discount3 = Double.parseDouble(dis);
                            }catch (Exception e){}
                            //pricewithVAT,netTotal;
                            pricewithoutVAT3=price1*quantity;
                             VAT3=(pricewithoutVAT3 * realVat)/100;
                            pricewithVAT=pricewithoutVAT3+VAT3;
                            netTotal=pricewithVAT-discount3;
                            editTextNetAmount3.setText(String.valueOf(netTotal));
                            //resut
                            editTextTotalNoItem.setText("3");
                            resultTradeValue=pricewithoutVAT1+pricewithoutVAT2+pricewithoutVAT3;
                            TotalDiscount=discount1+discount2+discount3;
                            TotalVAT= VAT1+VAT2+VAT3;
                            editTextTotalTradeValue.setText(String.valueOf(resultTradeValue));
                            editTextTotalDiscountValue.setText(String.valueOf(TotalDiscount));
                            editTextTotalVAT.setText(String.valueOf(TotalVAT));
                            TotalNetAmount=(resultTradeValue+TotalVAT)-TotalDiscount;
                            editTextTotalNetAmount.setText(String.valueOf(TotalNetAmount));

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                    editTextDiscount3.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            String dis=editTextDiscount3.getText().toString();
                            dis = (dis.isEmpty()) ? "0" :dis;
                            if (dis.matches(pattern)){
                                editTextDiscount3.setError("Invalid");
                                editTextDiscount3.requestFocus();
                                return;
                            }
                             discount3=Double.parseDouble(dis);
                            //double priceithVAT,netTotal;
                            netTotal=pricewithVAT-discount3;
                            editTextNetAmount3.setText(String.valueOf(netTotal));
                            //resut
                            editTextTotalNoItem.setText("3");
                            resultTradeValue=pricewithoutVAT1+pricewithoutVAT2+pricewithoutVAT3;
                            TotalDiscount=discount1+discount2+discount3;
                            editTextTotalTradeValue.setText(String.valueOf(resultTradeValue));
                            editTextTotalDiscountValue.setText(String.valueOf(TotalDiscount));
                            TotalNetAmount=(resultTradeValue+TotalVAT)-TotalDiscount;
                            editTextTotalNetAmount.setText(String.valueOf(TotalNetAmount));
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                }
            }
        });



                       //4th product
        autoCompleteProductName4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                productName4 = autoCompleteProductName4.getText().toString();
                if (productName4 != null || !productName4.equalsIgnoreCase("")) {
                    Product product = Util.getProductByName(productName4);
                    editTextTradeprice4.setText(product.getTradePrice());
                    editTextTradevalue4.setText(product.getTradeValue());
                    editTextpack4.setText(product.getPack_size());
                    //editTextVat4.setText(Constants.StaticValue.VAT);
                    editTextVat4.setText(product.getVat());
                    editTextDiscount4.setText(product.getDiscount());
                    editTextTotalNoItem.setText("4");
                   // productArrayList.add(product);
                    editTextQuantity4.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            String quan=editTextQuantity4.getText().toString();
                            if (quan.matches(pattern)){
                                editTextQuantity4.setError("Enter valid quantity");
                                editTextQuantity4.requestFocus();
                                return;
                            }
                            if(quan.equals(defaultValue)){
                                editTextQuantity4.setError("Enter valid quantity");
                                editTextQuantity4.requestFocus();
                                return;
                            }
                            quan = (quan.isEmpty()) ? "0" :quan;
                            String price=editTextTradeprice4.getText().toString();
                            String vat=editTextVat4.getText().toString();
                            String dis=editTextDiscount4.getText().toString();
                            double quantity=Double.parseDouble(quan);
                            double price1=Double.parseDouble(price);
                            double realVat=Double.parseDouble(vat);
                            try {
                                discount4 = Double.parseDouble(dis);
                            }catch (Exception e){}
                            //pricewithVAT,netTotal;
                            pricewithoutVAT4=price1*quantity;
                            VAT4=(pricewithoutVAT4 * realVat)/100;
                            pricewithVAT=pricewithoutVAT4+VAT4;
                            netTotal=pricewithVAT-discount4;
                            editTextNetAmount4.setText(String.valueOf(netTotal));
                            //resut
                            editTextTotalNoItem.setText("4");
                            resultTradeValue=pricewithoutVAT1+pricewithoutVAT2+pricewithoutVAT3+pricewithoutVAT4;
                            TotalDiscount=discount1+discount2+discount3+discount4;
                            TotalVAT= VAT1+VAT2+VAT3+VAT4;
                            editTextTotalTradeValue.setText(String.valueOf(resultTradeValue));
                            editTextTotalDiscountValue.setText(String.valueOf(TotalDiscount));
                            editTextTotalVAT.setText(String.valueOf(TotalVAT));
                            TotalNetAmount=(resultTradeValue+TotalVAT)-TotalDiscount;
                            editTextTotalNetAmount.setText(String.valueOf(TotalNetAmount));

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                    editTextDiscount4.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            String dis=editTextDiscount4.getText().toString();
                            dis = (dis.isEmpty()) ? "0" :dis;
                            if (dis.matches(pattern)){
                                editTextDiscount4.setError("Invalid");
                                editTextDiscount4.requestFocus();
                                return;
                            }
                             discount4=Double.parseDouble(dis);
                            netTotal=pricewithVAT-discount4;
                            editTextNetAmount4.setText(String.valueOf(netTotal));
                            //resut
                            editTextTotalNoItem.setText("4");
                            resultTradeValue=pricewithoutVAT1+pricewithoutVAT2+pricewithoutVAT3+pricewithoutVAT4;
                            TotalDiscount=discount1+discount2+discount3+discount4;
                            editTextTotalTradeValue.setText(String.valueOf(resultTradeValue));
                            editTextTotalDiscountValue.setText(String.valueOf(TotalDiscount));
                            TotalNetAmount=(resultTradeValue+TotalVAT)-TotalDiscount;
                            editTextTotalNetAmount.setText(String.valueOf(TotalNetAmount));
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                }
            }
        });


                      
                        //5th product
        autoCompleteProductName5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                productName5 = autoCompleteProductName5.getText().toString();
                if (productName5 != null || !productName5.equalsIgnoreCase("")) {
                    Product product = Util.getProductByName(productName5);
                    editTextTradeprice5.setText(product.getTradePrice());
                    editTextTradevalue5.setText(product.getTradeValue());
                    editTextpack5.setText(product.getPack_size());
                   // editTextVat5.setText(Constants.StaticValue.VAT);
                    editTextVat5.setText(product.getVat());
                    editTextDiscount5.setText(product.getDiscount());
                    editTextTotalNoItem.setText("5");
                    //productArrayList.add(product);
                    editTextQuantity5.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            String quan=editTextQuantity5.getText().toString();
                            if (quan.matches(pattern)){
                                editTextQuantity5.setError("Enter valid quantity");
                                editTextQuantity5.requestFocus();
                                return;
                            }
                            if(quan.equals(defaultValue)){
                                editTextQuantity5.setError("Enter valid quantity");
                                editTextQuantity5.requestFocus();
                                return;
                            }
                            quan = (quan.isEmpty()) ? "0" :quan;
                            String price=editTextTradeprice5.getText().toString();
                            String vat=editTextVat5.getText().toString();
                            String dis=editTextDiscount5.getText().toString();
                            double quantity=Double.parseDouble(quan);
                            double price1=Double.parseDouble(price);
                            double realVat=Double.parseDouble(vat);
                            try {
                                discount5 = Double.parseDouble(dis);
                            }catch (Exception e){}
                            //pricewithVAT,netTotal;
                            pricewithoutVAT5=price1*quantity;
                             VAT5=(pricewithoutVAT5 * realVat)/100;
                            pricewithVAT=pricewithoutVAT5+VAT5;
                            netTotal=pricewithVAT-discount5;
                            editTextNetAmount5.setText(String.valueOf(netTotal));
                            //resut
                            editTextTotalNoItem.setText("5");
                            resultTradeValue=pricewithoutVAT1+pricewithoutVAT2+pricewithoutVAT3+pricewithoutVAT4+pricewithoutVAT5;
                            TotalDiscount=discount1+discount2+discount3+discount4+discount5;
                            TotalVAT= VAT1+VAT2+VAT3+VAT4+VAT4+VAT5;
                            editTextTotalTradeValue.setText(String.valueOf(resultTradeValue));
                            editTextTotalDiscountValue.setText(String.valueOf(TotalDiscount));
                            editTextTotalVAT.setText(String.valueOf(TotalVAT));
                            TotalNetAmount=(resultTradeValue+TotalVAT)-TotalDiscount;
                            editTextTotalNetAmount.setText(String.valueOf(TotalNetAmount));

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                    editTextDiscount5.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            String dis=editTextDiscount5.getText().toString();
                            dis = (dis.isEmpty()) ? "0" :dis;
                            if (dis.matches(pattern)){
                                editTextDiscount5.setError("Invalid");
                                editTextDiscount5.requestFocus();
                                return;
                            }
                            discount5=Double.parseDouble(dis);
                            netTotal=pricewithVAT-discount5;
                            editTextNetAmount5.setText(String.valueOf(netTotal));
                            //resut
                            editTextTotalNoItem.setText("5");
                            resultTradeValue=pricewithoutVAT1+pricewithoutVAT2+pricewithoutVAT3+pricewithoutVAT4+pricewithoutVAT5;
                            TotalDiscount=discount1+discount2+discount3+discount4+discount5;
                            editTextTotalTradeValue.setText(String.valueOf(resultTradeValue));
                            editTextTotalDiscountValue.setText(String.valueOf(TotalDiscount));
                            TotalNetAmount=(resultTradeValue+TotalVAT)-TotalDiscount;
                            editTextTotalNetAmount.setText(String.valueOf(TotalNetAmount));
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                }
            }
        });


                     //6th product
        autoCompleteProductName6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                productName6 = autoCompleteProductName6.getText().toString();
                if (productName6 != null || !productName6.equalsIgnoreCase("")) {
                    Product product = Util.getProductByName(productName6);
                    editTextTradeprice6.setText(product.getTradePrice());
                    editTextTradevalue6.setText(product.getTradeValue());
                    editTextpack6.setText(product.getPack_size());
                   // editTextVat6.setText(Constants.StaticValue.VAT);
                    editTextVat6.setText(product.getVat());
                    editTextDiscount6.setText(product.getDiscount());
                    editTextTotalNoItem.setText("6");
                    //productArrayList.add(product);
                    editTextQuantity6.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            String quan=editTextQuantity6.getText().toString();
                            if (quan.matches(pattern)){
                                editTextQuantity6.setError("Enter valid quantity");
                                editTextQuantity6.requestFocus();
                                return;
                            }
                            if(quan.equals(defaultValue)){
                                editTextQuantity6.setError("Enter valid quantity");
                                editTextQuantity6.requestFocus();
                                return;
                            }
                            quan = (quan.isEmpty()) ? "0" :quan;
                            String price=editTextTradeprice6.getText().toString();
                            String vat=editTextVat6.getText().toString();
                            String dis=editTextDiscount6.getText().toString();
                            double quantity=Double.parseDouble(quan);
                            double price1=Double.parseDouble(price);
                            double realVat=Double.parseDouble(vat);
                            try {
                                discount6 = Double.parseDouble(dis);
                            }catch (Exception e){}
                            //pricewithVAT,netTotal;
                            pricewithoutVAT6=price1*quantity;
                             VAT6=(pricewithoutVAT6 * realVat)/100;
                            pricewithVAT=pricewithoutVAT6+VAT6;
                            netTotal=pricewithVAT-discount6;
                            editTextNetAmount6.setText(String.valueOf(netTotal));
                            //resut
                            editTextTotalNoItem.setText("6");
                            resultTradeValue=pricewithoutVAT1+pricewithoutVAT2+pricewithoutVAT3+pricewithoutVAT4+pricewithoutVAT5+pricewithoutVAT6;
                            TotalDiscount=discount1+discount2+discount3+discount4+discount5+discount6;
                            TotalVAT= VAT1+VAT2+VAT3+VAT4+VAT5+VAT6;
                            editTextTotalTradeValue.setText(String.valueOf(resultTradeValue));
                            editTextTotalDiscountValue.setText(String.valueOf(TotalDiscount));
                            editTextTotalVAT.setText(String.valueOf(TotalVAT));
                            TotalNetAmount=(resultTradeValue+TotalVAT)-TotalDiscount;
                            editTextTotalNetAmount.setText(String.valueOf(TotalNetAmount));

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                    editTextDiscount6.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            String dis=editTextDiscount6.getText().toString();
                            dis = (dis.isEmpty()) ? "0" :dis;
                            if (dis.matches(pattern)){
                                editTextDiscount6.setError("Invalid");
                                editTextDiscount6.requestFocus();
                                return;
                            }
                            discount6=Double.parseDouble(dis);
                            netTotal=pricewithVAT-discount6;
                            editTextNetAmount6.setText(String.valueOf(netTotal));
                            //resut
                            editTextTotalNoItem.setText("6");
                            resultTradeValue=pricewithoutVAT1+pricewithoutVAT2+pricewithoutVAT3+pricewithoutVAT4+pricewithoutVAT5+pricewithoutVAT6;
                            TotalDiscount=discount1+discount2+discount3+discount4+discount5+discount6;
                            editTextTotalTradeValue.setText(String.valueOf(resultTradeValue));
                            editTextTotalDiscountValue.setText(String.valueOf(TotalDiscount));
                            TotalNetAmount=(resultTradeValue+TotalVAT)-TotalDiscount;
                            editTextTotalNetAmount.setText(String.valueOf(TotalNetAmount));
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                }
            }
        });


        //7th product
        autoCompleteProductName7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                productName7 = autoCompleteProductName7.getText().toString();
                if (productName7 != null || !productName7.equalsIgnoreCase("")) {
                    Product product = Util.getProductByName(productName6);
                    editTextTradeprice7.setText(product.getTradePrice());
                    editTextTradevalue7.setText(product.getTradeValue());
                    editTextpack7.setText(product.getPack_size());
                    //editTextVat7.setText(Constants.StaticValue.VAT);
                    editTextVat7.setText(product.getVat());
                    editTextDiscount7.setText(product.getDiscount());
                    editTextTotalNoItem.setText("7");
                    //productArrayList.add(product);
                    editTextQuantity7.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            String quan=editTextQuantity7.getText().toString();
                            if (quan.matches(pattern)){
                                editTextQuantity7.setError("Enter valid quantity");
                                editTextQuantity7.requestFocus();
                                return;
                            }
                            if(quan.equals(defaultValue)){
                                editTextQuantity7.setError("Enter valid quantity");
                                editTextQuantity7.requestFocus();
                                return;
                            }
                            quan = (quan.isEmpty()) ? "0" :quan;
                            String price=editTextTradeprice7.getText().toString();
                            String vat=editTextVat7.getText().toString();
                            String dis=editTextDiscount7.getText().toString();
                            double quantity=Double.parseDouble(quan);
                            double price1=Double.parseDouble(price);
                            double realVat=Double.parseDouble(vat);
                            try {
                                discount7 = Double.parseDouble(dis);
                            }catch (Exception e){}
                            //pricewithVAT,netTotal;
                            pricewithoutVAT7=price1*quantity;
                             VAT7=(pricewithoutVAT7 * realVat)/100;
                            pricewithVAT=pricewithoutVAT7+VAT7;
                            netTotal=pricewithVAT-discount7;
                            editTextNetAmount7.setText(String.valueOf(netTotal));
                            //resut
                            editTextTotalNoItem.setText("7");
                            resultTradeValue=pricewithoutVAT1+pricewithoutVAT2+pricewithoutVAT3+pricewithoutVAT4+pricewithoutVAT5+pricewithoutVAT6+pricewithoutVAT7;
                            TotalDiscount=discount1+discount2+discount3+discount4+discount5+discount6+discount7;
                            TotalVAT= VAT1+VAT2+VAT3+VAT4+VAT5+VAT6+VAT7;
                            editTextTotalTradeValue.setText(String.valueOf(resultTradeValue));
                            editTextTotalDiscountValue.setText(String.valueOf(TotalDiscount));
                            editTextTotalVAT.setText(String.valueOf(TotalVAT));
                            TotalNetAmount=(resultTradeValue+TotalVAT)-TotalDiscount;
                            editTextTotalNetAmount.setText(String.valueOf(TotalNetAmount));

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                    editTextDiscount7.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            String dis=editTextDiscount7.getText().toString();
                            dis = (dis.isEmpty()) ? "0" :dis;
                            if (dis.matches(pattern)){
                                editTextDiscount7.setError("Invalid");
                                editTextDiscount7.requestFocus();
                                return;
                            }
                            discount7=Double.parseDouble(dis);
                            netTotal=pricewithVAT-discount7;
                            editTextNetAmount7.setText(String.valueOf(netTotal));
                            //resut
                            editTextTotalNoItem.setText("7");
                            resultTradeValue=pricewithoutVAT1+pricewithoutVAT2+pricewithoutVAT3+pricewithoutVAT4+pricewithoutVAT5+pricewithoutVAT6+pricewithoutVAT7;
                            TotalDiscount=discount1+discount2+discount3+discount4+discount5+discount6+discount7;
                            editTextTotalTradeValue.setText(String.valueOf(resultTradeValue));
                            editTextTotalDiscountValue.setText(String.valueOf(TotalDiscount));
                            TotalNetAmount=(resultTradeValue+TotalVAT)-TotalDiscount;
                            editTextTotalNetAmount.setText(String.valueOf(TotalNetAmount));
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                }
            }
        });


                        //8th product
        autoCompleteProductName8.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                productName8 = autoCompleteProductName8.getText().toString();
                if (productName8 != null || !productName8.equalsIgnoreCase("")) {
                    Product product = Util.getProductByName(productName8);
                    editTextTradeprice8.setText(product.getTradePrice());
                    editTextTradevalue8.setText(product.getTradeValue());
                    editTextpack8.setText(product.getPack_size());
                    //editTextVat8.setText(Constants.StaticValue.VAT);
                    editTextVat8.setText(product.getVat());
                    editTextDiscount8.setText(product.getDiscount());
                    editTextTotalNoItem.setText("8");
                    //productArrayList.add(product);
                    editTextQuantity8.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            String quan=editTextQuantity8.getText().toString();
                            if (quan.matches(pattern)){
                                editTextQuantity8.setError("Enter valid quantity");
                                editTextQuantity8.requestFocus();
                                return;
                            }
                            if(quan.equals(defaultValue)){
                                editTextQuantity8.setError("Enter valid quantity");
                                editTextQuantity8.requestFocus();
                                return;
                            }
                            quan = (quan.isEmpty()) ? "0" :quan;
                            String price=editTextTradeprice8.getText().toString();
                            String vat=editTextVat8.getText().toString();
                            String dis=editTextDiscount8.getText().toString();
                            double quantity=Double.parseDouble(quan);
                            double price1=Double.parseDouble(price);
                            double realVat=Double.parseDouble(vat);
                            try {
                                discount8 = Double.parseDouble(dis);
                            }catch (Exception e){}
                            //pricewithVAT,netTotal;
                            pricewithoutVAT8=price1*quantity;
                             VAT8=(pricewithoutVAT8 * realVat)/100;
                            pricewithVAT=pricewithoutVAT8+VAT8;
                            netTotal=pricewithVAT-discount8;
                            editTextNetAmount8.setText(String.valueOf(netTotal));
                            //resut
                            editTextTotalNoItem.setText("8");
                            resultTradeValue=pricewithoutVAT1+pricewithoutVAT2+pricewithoutVAT3+pricewithoutVAT4+pricewithoutVAT5+pricewithoutVAT6+pricewithoutVAT7+pricewithoutVAT8;
                            TotalDiscount=discount1+discount2+discount3+discount4+discount5+discount6+discount7+discount8;
                            TotalVAT= VAT1+VAT2+VAT3+VAT4+VAT5+VAT6+VAT7+VAT8;
                            editTextTotalTradeValue.setText(String.valueOf(resultTradeValue));
                            editTextTotalDiscountValue.setText(String.valueOf(TotalDiscount));
                            editTextTotalVAT.setText(String.valueOf(TotalVAT));
                            TotalNetAmount=(resultTradeValue+TotalVAT)-TotalDiscount;
                            editTextTotalNetAmount.setText(String.valueOf(TotalNetAmount));

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                    editTextDiscount8.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            String dis=editTextDiscount8.getText().toString();
                            dis = (dis.isEmpty()) ? "0" :dis;
                            if (dis.matches(pattern)){
                                editTextDiscount8.setError("Invalid");
                                editTextDiscount8.requestFocus();
                                return;
                            }
                             discount8=Double.parseDouble(dis);
                            netTotal=pricewithVAT-discount8;
                            editTextNetAmount8.setText(String.valueOf(netTotal));
                            //resut
                            editTextTotalNoItem.setText("8");
                            resultTradeValue=pricewithoutVAT1+pricewithoutVAT2+pricewithoutVAT3+pricewithoutVAT4+pricewithoutVAT5+pricewithoutVAT6+pricewithoutVAT7+pricewithoutVAT8;
                            TotalDiscount=discount1+discount2+discount3+discount4+discount5+discount6+discount7+discount8;
                            editTextTotalTradeValue.setText(String.valueOf(resultTradeValue));
                            editTextTotalDiscountValue.setText(String.valueOf(TotalDiscount));
                            TotalNetAmount=(resultTradeValue+TotalVAT)-TotalDiscount;
                            editTextTotalNetAmount.setText(String.valueOf(TotalNetAmount));
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                }
            }
        });



                  //9th product
        autoCompleteProductName9.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                productName9 = autoCompleteProductName9.getText().toString();
                if (productName9 != null || !productName9.equalsIgnoreCase("")) {
                    Product product = Util.getProductByName(productName9);
                    editTextTradeprice9.setText(product.getTradePrice());
                    editTextTradevalue9.setText(product.getTradeValue());
                    editTextpack9.setText(product.getPack_size());
                    //editTextVat9.setText(Constants.StaticValue.VAT);
                    editTextVat9.setText(product.getVat());
                    editTextDiscount9.setText(product.getDiscount());
                    editTextTotalNoItem.setText("9");
                    //productArrayList.add(product);
                    editTextQuantity9.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            String quan=editTextQuantity9.getText().toString();
                            if (quan.matches(pattern)){
                                editTextQuantity9.setError("Enter valid quantity");
                                editTextQuantity9.requestFocus();
                                return;
                            }
                            if(quan.equals(defaultValue)){
                                editTextQuantity9.setError("Enter valid quantity");
                                editTextQuantity9.requestFocus();
                                return;
                            }
                            quan = (quan.isEmpty()) ? "0" :quan;
                            String price=editTextTradeprice9.getText().toString();
                            String vat=editTextVat9.getText().toString();
                            String dis=editTextDiscount9.getText().toString();
                            double quantity=Double.parseDouble(quan);
                            double price1=Double.parseDouble(price);
                            double realVat=Double.parseDouble(vat);
                            try {
                                discount9 = Double.parseDouble(dis);
                            }catch (Exception e){}
                            //pricewithVAT,netTotal;
                            pricewithoutVAT9=price1*quantity;
                             VAT9=(pricewithoutVAT9 * realVat)/100;
                            pricewithVAT=pricewithoutVAT9+VAT9;
                            netTotal=pricewithVAT-discount9;
                            editTextNetAmount9.setText(String.valueOf(netTotal));
                            //resut
                            editTextTotalNoItem.setText("9");
                            resultTradeValue=pricewithoutVAT1+pricewithoutVAT2+pricewithoutVAT3+pricewithoutVAT4+pricewithoutVAT5+pricewithoutVAT6+pricewithoutVAT7+pricewithoutVAT8+pricewithoutVAT9;
                            TotalDiscount=discount1+discount2+discount3+discount4+discount5+discount6+discount7+discount8+discount9;
                            TotalVAT= VAT1+VAT2+VAT3+VAT4+VAT5+VAT6+VAT7+VAT9;
                            editTextTotalTradeValue.setText(String.valueOf(resultTradeValue));
                            editTextTotalDiscountValue.setText(String.valueOf(TotalDiscount));
                            editTextTotalVAT.setText(String.valueOf(TotalVAT));
                            TotalNetAmount=(resultTradeValue+TotalVAT)-TotalDiscount;
                            editTextTotalNetAmount.setText(String.valueOf(TotalNetAmount));

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                    editTextDiscount9.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            String dis=editTextDiscount9.getText().toString();
                            dis = (dis.isEmpty()) ? "0" :dis;
                            if (dis.matches(pattern)){
                                editTextDiscount9.setError("Invalid");
                                editTextDiscount9.requestFocus();
                                return;
                            }
                             discount9=Double.parseDouble(dis);
                            netTotal=pricewithVAT-discount9;
                            editTextNetAmount9.setText(String.valueOf(netTotal));
                            //resut
                            editTextTotalNoItem.setText("9");
                            resultTradeValue=pricewithoutVAT1+pricewithoutVAT2+pricewithoutVAT3+pricewithoutVAT4+pricewithoutVAT5+pricewithoutVAT6+pricewithoutVAT7+pricewithoutVAT8+pricewithoutVAT9;
                            TotalDiscount=discount1+discount2+discount3+discount4+discount5+discount6+discount7+discount8+discount9;
                            editTextTotalTradeValue.setText(String.valueOf(resultTradeValue));
                            editTextTotalDiscountValue.setText(String.valueOf(TotalDiscount));
                            TotalNetAmount=(resultTradeValue+TotalVAT)-TotalDiscount;
                            editTextTotalNetAmount.setText(String.valueOf(TotalNetAmount));
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                }
            }
        });


                //10th product
        autoCompleteProductName10.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                productName10 = autoCompleteProductName10.getText().toString();
                if (productName10 != null || !productName10.equalsIgnoreCase("")) {
                    Product product = Util.getProductByName(productName10);
                    editTextTradeprice10.setText(product.getTradePrice());
                    editTextTradevalue10.setText(product.getTradeValue());
                    editTextpack10.setText(product.getPack_size());
                    //editTextVat10.setText(Constants.StaticValue.VAT);
                    editTextVat10.setText(product.getVat());
                    editTextDiscount10.setText(product.getDiscount());
                    editTextTotalNoItem.setText("10");
                    //productArrayList.add(product);
                    editTextQuantity10.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            String quan=editTextQuantity10.getText().toString();
                            if (quan.matches(pattern)){
                                editTextQuantity10.setError("Enter valid quantity");
                                editTextQuantity10.requestFocus();
                                return;
                            }
                            if(quan.equals(defaultValue)){
                                editTextQuantity10.setError("Enter valid quantity");
                                editTextQuantity10.requestFocus();
                                return;
                            }
                            quan = (quan.isEmpty()) ? "0" :quan;
                            String price=editTextTradeprice10.getText().toString();
                            String vat=editTextVat10.getText().toString();
                            String dis=editTextDiscount10.getText().toString();
                            double quantity=Double.parseDouble(quan);
                            double price1=Double.parseDouble(price);
                            double realVat=Double.parseDouble(vat);
                            try {
                                discount10 = Double.parseDouble(dis);
                            }catch (Exception e){}
                            //pricewithVAT,netTotal;
                            pricewithoutVAT10=price1*quantity;
                             VAT10=(pricewithoutVAT10 * realVat)/100;
                            pricewithVAT=pricewithoutVAT10+VAT10;
                            netTotal=pricewithVAT-discount10;
                            editTextNetAmount10.setText(String.valueOf(netTotal));
                            //resut
                            editTextTotalNoItem.setText("10");
                            resultTradeValue=pricewithoutVAT1+pricewithoutVAT2+pricewithoutVAT3+pricewithoutVAT4+pricewithoutVAT5+pricewithoutVAT6+pricewithoutVAT7+pricewithoutVAT8+pricewithoutVAT9+pricewithoutVAT10;
                            TotalDiscount=discount1+discount2+discount3+discount4+discount5+discount6+discount7+discount8+discount9+discount10;
                            TotalVAT= VAT1+VAT2+VAT3+VAT4+VAT5+VAT6+VAT7+VAT8+VAT9+VAT10;
                            editTextTotalTradeValue.setText(String.valueOf(resultTradeValue));
                            editTextTotalDiscountValue.setText(String.valueOf(TotalDiscount));
                            editTextTotalVAT.setText(String.valueOf(TotalVAT));
                            TotalNetAmount=(resultTradeValue+TotalVAT)-TotalDiscount;
                            editTextTotalNetAmount.setText(String.valueOf(TotalNetAmount));

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                    editTextDiscount10.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            String dis=editTextDiscount10.getText().toString();
                            dis = (dis.isEmpty()) ? "0" :dis;
                            if (dis.matches(pattern)){
                                editTextDiscount10.setError("Invalid");
                                editTextDiscount10.requestFocus();
                                return;
                            }
                             discount10=Double.parseDouble(dis);
                            netTotal=pricewithVAT-discount10;
                            editTextNetAmount10.setText(String.valueOf(netTotal));
                            //resut
                            editTextTotalNoItem.setText("10");
                            resultTradeValue=pricewithoutVAT1+pricewithoutVAT2+pricewithoutVAT3+pricewithoutVAT4+pricewithoutVAT5+pricewithoutVAT6+pricewithoutVAT7+pricewithoutVAT8+pricewithoutVAT9+pricewithoutVAT10;
                            TotalDiscount=discount1+discount2+discount3+discount4+discount5+discount6+discount7+discount8+discount9+discount10;
                            editTextTotalTradeValue.setText(String.valueOf(resultTradeValue));
                            editTextTotalDiscountValue.setText(String.valueOf(TotalDiscount));
                            TotalNetAmount=(resultTradeValue+TotalVAT)-TotalDiscount;
                            editTextTotalNetAmount.setText(String.valueOf(TotalNetAmount));
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                }
            }
        });



    }



    //Methode For GetActivity OrderDatePicker
    private Context getActivity() {
        return this;
    }

    //save Product entry




    //Find UI
    public void initUI() {
        final RelativeLayout produclayout=(RelativeLayout) findViewById(R.id.ProductrelativeLayout);
        //Linearlayout entry
        RelativeLayout l1=(RelativeLayout) findViewById(R.id.l1);
        final RelativeLayout l2=(RelativeLayout) findViewById(R.id.l2);
        final RelativeLayout l3=(RelativeLayout) findViewById(R.id.l3);
        final RelativeLayout l4=(RelativeLayout) findViewById(R.id.l4);
        final RelativeLayout l5=(RelativeLayout) findViewById(R.id.l5);
        final RelativeLayout l6=(RelativeLayout) findViewById(R.id.l6);
        final RelativeLayout l7=(RelativeLayout) findViewById(R.id.l7);
        final RelativeLayout l8=(RelativeLayout) findViewById(R.id.l8);
        final RelativeLayout l9=(RelativeLayout) findViewById(R.id.l9);
        final RelativeLayout l10=(RelativeLayout) findViewById(R.id.l10);

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

        //button Calculate

        //EditText Order & Delivery Date
        editTextOrderDate=(EditText) findViewById(R.id.order_date);
        editTextDeliveryDate=(EditText) findViewById(R.id.delivary_date);
        payment_method= (Spinner) findViewById(R.id.spinner_payment_method);
        editTextMarketPlace= (EditText) findViewById(R.id.market);
       // editTextCustomerID= (EditText) findViewById(R.id.customerID);

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



        //Quantity
        editTextQuantity1 =(EditText) findViewById(R.id.quantity1);
        editTextQuantity2 =(EditText) findViewById(R.id.quantity2);
        editTextQuantity3 =(EditText) findViewById(R.id.quantity3);
        editTextQuantity4 =(EditText) findViewById(R.id.quantity4);
        editTextQuantity5 =(EditText) findViewById(R.id.quantity5);
        editTextQuantity6 =(EditText) findViewById(R.id.quantity6);
        editTextQuantity7 =(EditText) findViewById(R.id.quantity7);
        editTextQuantity8 =(EditText) findViewById(R.id.quantity8);
        editTextQuantity9 =(EditText) findViewById(R.id.quantity9);
        editTextQuantity10 =(EditText) findViewById(R.id.quantity10);
        //pack_size
        editTextpack1=(EditText) findViewById(R.id.pack1);
        editTextpack2=(EditText) findViewById(R.id.pack2);
        editTextpack3=(EditText) findViewById(R.id.pack3);
        editTextpack4=(EditText) findViewById(R.id.pack4);
        editTextpack5=(EditText) findViewById(R.id.pack5);
        editTextpack6=(EditText) findViewById(R.id.pack6);
        editTextpack7=(EditText) findViewById(R.id.pack7);
        editTextpack8=(EditText) findViewById(R.id.pack8);
        editTextpack9=(EditText) findViewById(R.id.pack9);
        editTextpack10=(EditText) findViewById(R.id.pack10);
        //Tradevalue
        editTextTradevalue1=(EditText) findViewById(R.id.tradeValue1);
        editTextTradevalue2=(EditText) findViewById(R.id.tradeValue2);
        editTextTradevalue3=(EditText) findViewById(R.id.tradeValue3);
        editTextTradevalue4=(EditText) findViewById(R.id.tradeValue4);
        editTextTradevalue5=(EditText) findViewById(R.id.tradeValue5);
        editTextTradevalue6=(EditText) findViewById(R.id.tradeValue6);
        editTextTradevalue7=(EditText) findViewById(R.id.tradeValue7);
        editTextTradevalue8=(EditText) findViewById(R.id.tradeValue8);
        editTextTradevalue9=(EditText) findViewById(R.id.tradeValue9);
        editTextTradevalue10=(EditText) findViewById(R.id.tradeValue10);
        //Tradeprice
        editTextTradeprice1=(EditText) findViewById(R.id.tradePrice1);
        editTextTradeprice2=(EditText) findViewById(R.id.tradePrice2);
        editTextTradeprice3=(EditText) findViewById(R.id.tradePrice3);
        editTextTradeprice4=(EditText) findViewById(R.id.tradePrice4);
        editTextTradeprice5=(EditText) findViewById(R.id.tradePrice5);
        editTextTradeprice6=(EditText) findViewById(R.id.tradePrice6);
        editTextTradeprice7=(EditText) findViewById(R.id.tradePrice7);
        editTextTradeprice8=(EditText) findViewById(R.id.tradePrice8);
        editTextTradeprice9=(EditText) findViewById(R.id.tradePrice9);
        editTextTradeprice10=(EditText) findViewById(R.id.tradePrice10);
        //Net Amount
        editTextNetAmount1=(EditText) findViewById(R.id.netAmount1);
        editTextNetAmount2=(EditText) findViewById(R.id.netAmount2);
        editTextNetAmount3=(EditText) findViewById(R.id.netAmount3);
        editTextNetAmount4=(EditText) findViewById(R.id.netAmount4);
        editTextNetAmount5=(EditText) findViewById(R.id.netAmount5);
        editTextNetAmount6=(EditText) findViewById(R.id.netAmount6);
        editTextNetAmount7=(EditText) findViewById(R.id.netAmount7);
        editTextNetAmount8=(EditText) findViewById(R.id.netAmount8);
        editTextNetAmount9=(EditText) findViewById(R.id.netAmount9);
        editTextNetAmount10=(EditText) findViewById(R.id.netAmount10);
        //Vat
        editTextVat1=(EditText) findViewById(R.id.vat1);
        editTextVat2=(EditText) findViewById(R.id.vat2);
        editTextVat3=(EditText) findViewById(R.id.vat3);
        editTextVat4=(EditText) findViewById(R.id.vat4);
        editTextVat5=(EditText) findViewById(R.id.vat5);
        editTextVat6=(EditText) findViewById(R.id.vat6);
        editTextVat7=(EditText) findViewById(R.id.vat7);
        editTextVat8=(EditText) findViewById(R.id.vat8);
        editTextVat9=(EditText) findViewById(R.id.vat9);
        editTextVat10=(EditText) findViewById(R.id.vat10);
        //Discount
        editTextDiscount1=(EditText) findViewById(R.id.discountValue1);
        editTextDiscount2=(EditText) findViewById(R.id.discountValue2);
        editTextDiscount3=(EditText) findViewById(R.id.discountValue3);
        editTextDiscount4=(EditText) findViewById(R.id.discountValue4);
        editTextDiscount5=(EditText) findViewById(R.id.discountValue5);
        editTextDiscount6=(EditText) findViewById(R.id.discountValue6);
        editTextDiscount7=(EditText) findViewById(R.id.discountValue7);
        editTextDiscount8=(EditText) findViewById(R.id.discountValue8);
        editTextDiscount9=(EditText) findViewById(R.id.discountValue9);
        editTextDiscount10=(EditText) findViewById(R.id.discountValue10);
        //Result View
        editTextTotalNoItem=(EditText) findViewById(R.id.noOfItem);
        editTextTotalTradeValue= (EditText) findViewById(R.id.TotalTradeValue);
        editTextTotalDiscountValue= (EditText) findViewById(R.id.TotalDiscountValue);
        editTextTotalVAT=(EditText) findViewById(R.id.TotalVat);
        editTextTotalNetAmount= (EditText) findViewById(R.id.TotalNetAmount);
        buttonSave = (Button) findViewById(R.id.save);

        buttonexpand1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(productName1)) {
                    autoCompleteProductName1.setError("Enter product name");
                    autoCompleteProductName1.requestFocus();
                    return;
                }
                String error=editTextQuantity1.getText().toString();
                if (TextUtils.isEmpty(error)) {
                    editTextQuantity1.setError("Enter Quantity");
                    editTextQuantity1.requestFocus();
                    return;
                }
                l2.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = produclayout.getLayoutParams();
                params.height=780 ;
                produclayout.setLayoutParams(params);
            }
        });
        buttonexpand2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(productName2)) {
                    autoCompleteProductName2.setError("Enter product name");
                    autoCompleteProductName2.requestFocus();
                    return;
                }
                String error=editTextQuantity2.getText().toString();
                if (TextUtils.isEmpty(error)) {
                    editTextQuantity2.setError("Enter Quantity");
                    editTextQuantity2.requestFocus();
                    return;
                }

                l3.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = produclayout.getLayoutParams();
                params.height=1180 ;
                produclayout.setLayoutParams(params);
            }
        });
        buttonexpand3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(productName3)) {
                    autoCompleteProductName3.setError("Enter product name");
                    autoCompleteProductName3.requestFocus();
                    return;
                }
                String error=editTextQuantity3.getText().toString();
                if (TextUtils.isEmpty(error)) {
                    editTextQuantity3.setError("Enter Quantity");
                    editTextQuantity3.requestFocus();
                    return;
                }
                l4.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = produclayout.getLayoutParams();
                params.height=1560 ;
                produclayout.setLayoutParams(params);
            }
        });
        buttonexpand4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(productName4)) {
                    autoCompleteProductName4.setError("Enter product name");
                    autoCompleteProductName4.requestFocus();
                    return;
                }
                String error=editTextQuantity4.getText().toString();
                if (TextUtils.isEmpty(error)) {
                    editTextQuantity4.setError("Enter Quantity");
                    editTextQuantity4.requestFocus();
                    return;
                }
                l5.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = produclayout.getLayoutParams();
                params.height=1950 ;
                produclayout.setLayoutParams(params);
            }
        });
        buttonexpand5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(productName5)) {
                    autoCompleteProductName5.setError("Enter product name");
                    autoCompleteProductName5.requestFocus();
                    return;
                }
                String error=editTextQuantity5.getText().toString();
                if (TextUtils.isEmpty(error)) {
                    editTextQuantity5.setError("Enter Quantity");
                    editTextQuantity5.requestFocus();
                    return;
                }
                l6.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = produclayout.getLayoutParams();
                params.height=2340 ;
                produclayout.setLayoutParams(params);
            }
        });
        buttonexpand6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(productName6)) {
                    autoCompleteProductName6.setError("Enter product name");
                    autoCompleteProductName6.requestFocus();
                    return;
                }
                String error=editTextQuantity6.getText().toString();
                if (TextUtils.isEmpty(error)) {
                    editTextQuantity6.setError("Enter Quantity");
                    editTextQuantity6.requestFocus();
                    return;
                }
                l7.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = produclayout.getLayoutParams();
                params.height=2730 ;
                produclayout.setLayoutParams(params);
            }
        });
        buttonexpand7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(productName7)) {
                    autoCompleteProductName7.setError("Enter product name");
                    autoCompleteProductName7.requestFocus();
                    return;
                }
                String error=editTextQuantity7.getText().toString();
                if (TextUtils.isEmpty(error)) {
                    editTextQuantity7.setError("Enter Quantity");
                    editTextQuantity7.requestFocus();
                    return;
                }
                l8.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = produclayout.getLayoutParams();
                params.height=3120 ;
                produclayout.setLayoutParams(params);
            }
        });
        buttonexpand8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(productName8)) {
                    autoCompleteProductName8.setError("Enter product name");
                    autoCompleteProductName8.requestFocus();
                    return;
                }
                String error=editTextQuantity8.getText().toString();
                if (TextUtils.isEmpty(error)) {
                    editTextQuantity8.setError("Enter Quantity");
                    editTextQuantity8.requestFocus();
                    return;
                }
                l9.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = produclayout.getLayoutParams();
                params.height=3510 ;
                produclayout.setLayoutParams(params);
            }
        });
        buttonexpand9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(productName9)) {
                    autoCompleteProductName9.setError("Enter product name");
                    autoCompleteProductName9.requestFocus();
                    return;
                }
                String error=editTextQuantity9.getText().toString();
                if (TextUtils.isEmpty(error)) {
                    editTextQuantity9.setError("Enter Quantity");
                    editTextQuantity9.requestFocus();
                    return;
                }
                l10.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = produclayout.getLayoutParams();
                params.height=3900 ;
                produclayout.setLayoutParams(params);
            }
        });
        buttonexpand10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast= Toast.makeText(getApplicationContext(), "  Maximum item already fullfill  ", Toast.LENGTH_LONG);
                View view1=toast.getView();
                view1.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4caf50")));
                view1.setBackgroundResource(R.drawable.toast_style_error);
                toast.show();
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
