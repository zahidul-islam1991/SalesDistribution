package com.csi.salesdistribution;

import com.csi.salesdistribution.Model.Product;
import com.csi.salesdistribution.Utility.Constants;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;




public class JsonParseProduct {

    String apiToken;
    public JsonParseProduct(String apiToken){
        this.apiToken = apiToken;
    }

    public List<SuggestGetSet> getParseJsonWCF(String sName)
    {
        List<SuggestGetSet> ListData = new ArrayList<SuggestGetSet>();
        try {
            String temp=sName.replace(" ", "%20");
            URL url = new URL(Constants.Api.API_PRODUCT+temp);
            
            URLConnection urlConnection = url.openConnection();
            urlConnection.addRequestProperty("Authorization","Bearer "+apiToken);
            urlConnection.addRequestProperty("Accept","application/json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = reader.readLine();
            JSONObject jsonResponse = new JSONObject(line);
            //JSONObject
            JSONArray jsonArray = jsonResponse.getJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                //try
                Product product = new Product();
                product.setProd_id(jsonObject.getString(Constants.Product.PRODUCT_ID));
                product.setName(jsonObject.getString(Constants.Product.NAME));
                //product.setRate(jsonObject.getString(Constants.Product.RATE));
                //new
                product.setTradePrice(jsonObject.getString(Constants.Product.RATE));
                product.setTradeValue(jsonObject.getString(Constants.Product.RATE));
                //product.setVat(Constants.StaticValue.VAT);
                product.setVat(jsonObject.getString(Constants.Product.VAT));
                product.setDiscount(jsonObject.getString(Constants.Product.DISCOUNT));
                //product.setVat(jsonObject.getString(Constants.Product.VAT));
                //product.setTradeValue(jsonObject.getString(Constants.Product.TRADE_VALUE));
                product.setPack_size(jsonObject.getString(Constants.Product.PACK_SIZE));
                Constants.productList.add(product);

                ListData.add(new SuggestGetSet(product.getProd_id(),product.getName()));


            }

        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return ListData;

    }

    
}

