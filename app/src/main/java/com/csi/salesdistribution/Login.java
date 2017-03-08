package com.csi.salesdistribution;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    Button buttonLogin;
    TextView forget_password, new_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         initUI();

        // login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent i=new Intent(Login.this,Dashboard.class);
                //startActivity(i);
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

            }
        });

        // forget password
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, "Forget Password" , Toast.LENGTH_LONG).show();
            }
        });

        // Link Signup

    }


    private void initUI() {

         buttonLogin = (Button) findViewById(R.id.login);
        forget_password = (TextView) findViewById(R.id.forget_password);

    }
}
