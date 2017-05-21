package com.csi.salesdistribution;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.csi.salesdistribution.Utility.Constants;
import com.csi.salesdistribution.Utility.Util;
import com.csi.salesdistribution.Utility.Constants;
import com.csi.salesdistribution.Utility.Util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    Button buttonLogin;
    TextView forget_password, new_account;
    private EditText editTextUserName;
    private EditText editTextPassword;
    private ImageView imageViewLogo;
    Animation animation;
    public static final String USER_NAME = "USERNAME";
    public static final String EMP_CODE="EMPCODE";
    public static final String NAME="NAME";
    public static final String API_TOKEN="APITOKEN";
    public static final String TERRITORY="TERRITORY";
    public static final String TERRITORY_CODE="TERRITORY_CODE";
    public static final String ID="ID";
   //sharedpreference
    public static final String PREFS_NAME = "LoginPrefs";


    String username;
    String password;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        //Animation
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);
        imageViewLogo.setAnimation(animation);

        //sharedPreference
        sharedPreferences=getSharedPreferences("login", MODE_PRIVATE);
        if(sharedPreferences.contains(USER_NAME) && sharedPreferences.contains(EMP_CODE)){
            startActivity(new Intent(Login.this,Dashboard.class));
            finish();   //finish current activity
        }
        // login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent i=new Intent(Login.this,Dashboard.class);
                //startActivity(i);
                username = editTextUserName.getText().toString();
                password = editTextPassword.getText().toString();


                Log.d("username",username);
                if (TextUtils.isEmpty(username)) {
                    editTextUserName.setError("Please enter username");
                    editTextUserName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    editTextPassword.setError("Please enter password");
                    editTextPassword.requestFocus();
                    return;
                }

                login(username, password);

            }

            private void login(final String username, final String password) {

                class LoginAsync extends AsyncTask<String, Void, String> {

                    private Dialog loadingDialog;
                    private String empCode="Test";
                    private String name="Test";
                    private String apiToken="text";
                    private  String id="text";
                    private  String territoryName="text";
                    private  String territoryCode="text";

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loadingDialog = ProgressDialog.show(Login.this, "Please wait", "Loading...");
                    }

                    @Override
                    protected String doInBackground(String... params) {
                        String uname = params[0];
                        String pass = params[1];
                        String result = "FAIL";


                        InputStream is = null;
                        try {
                            JSONObject jsonObjectLogInReq = new JSONObject();
                            jsonObjectLogInReq.put("username", uname);
                            jsonObjectLogInReq.put("password", pass);
                            String logInResponse = Util.sendHttpRequest(Constants.Api.API_LOG_IN, jsonObjectLogInReq);
                            JSONObject jsonObjectResponse = new JSONObject(logInResponse);
                            Log.i("Login",jsonObjectResponse.getString(Constants.STATUS));
                            String executionStatus = "success";
                            if(executionStatus.equalsIgnoreCase(jsonObjectResponse.getString(Constants.STATUS))){
                                Log.i("LoginIF",jsonObjectResponse.getString(Constants.MESSAGE));
                                JSONObject userData = jsonObjectResponse.getJSONObject("data");


                                id = userData.getString(Constants.LogInFields.ID);
                                name = userData.getString(Constants.LogInFields.NAME);
                                String username = userData.getString(Constants.LogInFields.USER_NAME);
                                empCode = userData.getString(Constants.LogInFields.EMP_CODE);
                                apiToken = userData.getString(Constants.LogInFields.API_TOKEN);
                                String userStatus = userData.getString(Constants.LogInFields.STATUS);

                                result = jsonObjectResponse.getString(Constants.STATUS);

                                JSONObject teritory = userData.getJSONObject("teritory");
                                territoryName=teritory.getString(Constants.LogInFields.TERRITORY);
                                territoryCode=teritory.getString(Constants.LogInFields.TERRITORY_CODE);




                                Log.i("LoginIF",jsonObjectResponse.getString(Constants.LogInFields.STATUS));


                           }else {
                                Log.i("LoginElse",jsonObjectResponse.getString(Constants.MESSAGE));
                                String message = jsonObjectResponse.getString(Constants.MESSAGE);
                                result = jsonObjectResponse.getString(Constants.STATUS);

                            }


                            //region Commented Code
                            /*List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                            nameValuePairs.add(new BasicNameValuePair("username", uname));
                            nameValuePairs.add(new BasicNameValuePair("password", pass));*//*


                            HttpClient httpClient = new DefaultHttpClient();
                            HttpPost httpPost = new HttpPost("http://192.168.5.103:8000/api/v1/login");
                            httpPost.setEntity(new UrlEncodedFormEntity(jsonObject.toString()));
                            HttpResponse response = httpClient.execute(httpPost);
                            HttpEntity entity = response.getEntity();
                            is = entity.getContent();



                            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                            StringBuilder sb = new StringBuilder();

                            String line = null;
                            while ((line = reader.readLine()) != null) {
                                sb.append(line + "\n");
                            }
                            result = sb.toString();*/
                            //endregion

                        } catch (Exception e) {
                            Log.d("ExpLogin",e.getMessage());
                            e.printStackTrace();
                        }
                        Log.d("LoginResponse",result);
                        return result;
                    }

                    @Override
                    protected void onPostExecute(String result) {

                        loadingDialog.dismiss();
                        if (result.equalsIgnoreCase("success")) {

                            Intent intent = new Intent(Login.this, Dashboard.class);
                            /*Toast toast= Toast.makeText(getApplicationContext(), "  Login Successfull  ", Toast.LENGTH_LONG);
                            View view=toast.getView();
                            view.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4caf50")));
                            view.setBackgroundResource(R.drawable.toast_style);
                            toast.show();
                            */

                            //sharedPreference
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString(USER_NAME, username);
                            editor.putString(EMP_CODE, empCode);
                            editor.putString(API_TOKEN, apiToken);
                            editor.putString(TERRITORY, territoryName);
                            editor.putString(TERRITORY_CODE, territoryCode);
                            editor.commit();
                            finish();
                            startActivity(intent);
                            //close shared preference

                            //Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
                            //intent.putExtra(USER_NAME, username);
                         /*   Bundle extra=new Bundle();
                            extra.putString(USER_NAME, username);
                            extra.putString(EMP_CODE, empCode);
                            extra.putString(NAME, name);
                            extra.putString(API_TOKEN, apiToken);
                            intent.putExtras(extra);
                            finish();
                            startActivity(intent);
                        */




                        } else {
                            //Toast.makeText(getApplicationContext(), "Invalid User Name or Password", Toast.LENGTH_LONG).show();
                            Toast toast= Toast.makeText(getApplicationContext(), " Invalid User Name or Password ", Toast.LENGTH_LONG);
                            View view=toast.getView();
                            view.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff0000")));
                            view.setBackgroundResource(R.drawable.toast_style_error);
                            toast.show();
                        }
                    }
                }

                LoginAsync la = new LoginAsync();
                la.execute(username, password);





                /*
                final ProgressDialog progressDialog = new ProgressDialog(Login.this,
                        R.style.AppTheme);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Here you can send the extras.
                        Intent i = new Intent(Login.this, Dashboard.class);
                        startActivity(i);

                        // close this activity
                        finish();
                    }
                }, 2000);
                */

            }
        });


        // forget password
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Login.this, Dashboard.class);
                //startActivity(intent);
                Toast.makeText(Login.this, "Forget Password", Toast.LENGTH_LONG).show();
            }
        });

        // Link Signup

    }


    private void initUI() {

        buttonLogin = (Button) findViewById(R.id.login);
        forget_password = (TextView) findViewById(R.id.forget_password);
        editTextUserName = (EditText) findViewById(R.id.userId);
        editTextPassword = (EditText) findViewById(R.id.password);
        imageViewLogo = (ImageView) findViewById(R.id.logo);
    }

    //Show internet connection

}
