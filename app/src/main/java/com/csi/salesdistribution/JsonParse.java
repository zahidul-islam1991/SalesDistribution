package com.csi.salesdistribution;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.csi.salesdistribution.Model.Customer;
import com.csi.salesdistribution.Utility.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shohan on 3/20/17.
 */


public class JsonParse {

    String apiToken;
    public JsonParse(String apiToken){
        this.apiToken = apiToken;
    }

    public List<SuggestGetSet> getParseJsonWCF(String sName)
    {
        List<SuggestGetSet> ListData = new ArrayList<SuggestGetSet>();
        try {
            String temp=sName.replace(" ", "%20");
            URL js = new URL(Constants.Api.API_CUSTOMER_AUTO+temp);

            URLConnection jc = js.openConnection();
            jc.addRequestProperty("Authorization","Bearer "+apiToken);
            jc.addRequestProperty("Accept","application/json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(jc.getInputStream()));
            String line = reader.readLine();
            JSONObject jsonResponse = new JSONObject(line);
            Log.d("customer_autocomplete",line);
            //JSONObject
            JSONArray jsonArray = jsonResponse.getJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //try
                Customer customer=new Customer();
                customer.setId(jsonObject.getString(Constants.Customer.CUSTOMER_ID));
                customer.setName(jsonObject.getString(Constants.Customer.NAME));
                customer.setPhone(jsonObject.getString(Constants.Customer.PHONE));
                //customer.setCareOf(jsonObject.getString(Constants.Customer.CAREOF));
                JSONObject market = jsonObject.getJSONObject("market");
                customer.setCareOf(market.getString(Constants.Customer.CAREOF));


                Constants.customerList.add(customer);
                ListData.add(new SuggestGetSet(customer.getId(),customer.getName()));
                ListData.add(new SuggestGetSet(customer.getId(),"ID: "+customer.getId()+"  "+"Mobile: "+customer.getPhone()));
                //ListData.add(new SuggestGetSet(jsonObject.getString("id"),jsonObject.getString("name")+"    " +"("+jsonObject.getString("phone")+")"));
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return ListData;

    }

    
}

