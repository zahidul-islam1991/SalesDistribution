package com.csi.salesdistribution;

import com.csi.salesdistribution.Model.Customer;
import com.csi.salesdistribution.Model.Market;
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

/**
 * Created by shohan on 3/20/17.
 */


public class JsonParseMarket {

    String apiToken;
    public JsonParseMarket(String apiToken){
        this.apiToken = apiToken;
    }

    public List<SuggestGetSet> getParseJsonWCF(String sName)
    {
        List<SuggestGetSet> ListData = new ArrayList<SuggestGetSet>();
        try {
            String temp=sName.replace(" ", "%20");
            URL js = new URL(Constants.Api.API_MARKET+temp);

            URLConnection jc = js.openConnection();
            jc.addRequestProperty("Authorization","Bearer "+apiToken);
            jc.addRequestProperty("Accept","application/json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(jc.getInputStream()));
            String line = reader.readLine();
            JSONObject jsonResponse = new JSONObject(line);
            //JSONObject
            JSONArray jsonArray = jsonResponse.getJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //try
                Market market = new Market();
                market.setMarketID(jsonObject.getString(Constants.Market.MARKET_CODE));
                market.setMarketDes(jsonObject.getString(Constants.Market.MARKET_DESCRIPTION));
                Constants.marketList.add(market);

                ListData.add(new SuggestGetSet(market.getMarketID(),market.getMarketDes()));
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return ListData;

    }

    
}

