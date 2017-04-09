package com.csi.salesdistribution;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Splash extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1500;
    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);


        if ( connectivityManager.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connectivityManager.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connectivityManager.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            Intent intent = new Intent(Splash.this, Dashboard.class);
            Toast toast= Toast.makeText(getApplicationContext(), "  Connected  ", Toast.LENGTH_LONG);
            View view=toast.getView();
            view.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4caf50")));
            view.setBackgroundResource(R.drawable.toast_style);
            toast.show();
            new Handler().postDelayed(new Runnable() {


                @Override
                public void run() {
                    Intent i = new Intent(Splash.this, Login.class);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_TIME_OUT);
            //return true;
        }
        else if( connectivityManager.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                connectivityManager.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context, R.style.MyDialogTheme);

            // set title
            alertDialogBuilder.setTitle(R.string.connectionTitle);

            // set dialog message/icon etc
            alertDialogBuilder.setMessage(R.string.connectinErrorMessage)
                    .setCancelable(false)
                    //.setIcon(R.drawable.logout_icon)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                           finish();

                        }
                    });



            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        }
    }
}


