package com.csi.salesdistribution.Utility;

import android.util.Log;

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
import org.json.JSONObject;

import java.io.InputStream;


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
    //region Commented Code
    /*    public static String sendHttpRequest(JSONObject jsonObject) {
        try {
            int TIMEOUT_MILLISEC = 10000;

            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
            HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);

            HttpClient client = new DefaultHttpClient(httpParams);
            HttpPost request = new HttpPost(Constants.API_LOG_IN);
            request.setEntity(new ByteArrayEntity(jsonObject.toString().getBytes("UTF8")));

            HttpResponse response = client.execute(request);

            InputStream in = response.getEntity().getContent();
            return response.
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }*/
    //endregion
}
