package com.csi.salesdistribution.Utility;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.csi.salesdistribution.Customer_Resistration;
import com.csi.salesdistribution.Model.Customer;
import com.csi.salesdistribution.Model.Market;
import com.csi.salesdistribution.Model.Product;
import com.csi.salesdistribution.SuggestGetSet;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Util {

    public static String sendHttpRequest(String url,JSONObject obj) {
        // Create a new HttpClient and Post Header

        HttpParams myParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(myParams, 10000);
        HttpConnectionParams.setSoTimeout(myParams, 10000);
        HttpClient httpclient = new DefaultHttpClient(myParams );
        String json=obj.toString();

        try {

            HttpPost httppost = new HttpPost(url.toString());
            httppost.setHeader("Content-type", "application/json");

            StringEntity se = new StringEntity(obj.toString());
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httppost.setEntity(se);

            HttpResponse response = httpclient.execute(httppost);

            String responseString = EntityUtils.toString(response.getEntity());
            Log.d("HttpResponse",responseString.toLowerCase());
            return responseString;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }


    }

    public static Product getProductById(String productId) {
        for (Product product : Constants.productList) {
            if (product.getProd_id().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public static Product getProductByName(String productName) {
        for (Product product : Constants.productList) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        return null;
    }

    public static Customer getCustomerByName(String customerName) {
        for (Customer customer: Constants.customerList) {
            if (customer.getName().equals(customerName)) {
                return customer;
            }
        }
        return null;
    }
    public static Market getMarketByName(String marketName) {
        for (Market market: Constants.marketList) {
            if (market.getMarketDes().equals(marketName)) {
                return market;
            }
        }
        return null;
    }

    }
