package com.csi.salesdistribution;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView textviewSalesPerson;
    TextView textViewEmployeCode;
    TextView textView;
    public static String name="text";
    Context context=this;
    SharedPreferences sharedPreferences;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        textView=(TextView) findViewById(R.id.textView);
        TextView textView1=(TextView)findViewById(R.id.textView2);
        //textViewApiToken=(TextView) findViewById(R.id.textViewApiToken);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);
        textView1.startAnimation(animation);

        //Intent intent=getIntent();
        //String username=intent.getStringExtra(Login.USER_NAME);

       /* Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String username_string = extras.getString(Login.USER_NAME);
        String empCode=extras.getString(Login.EMP_CODE);
        String name=extras.getString(Login.NAME);
        String apiToken=extras.getString(Login.API_TOKEN);
        String id=extras.getString(Login.ID);
*/
        sharedPreferences=getSharedPreferences("login", MODE_PRIVATE);
        String username,apiToken,empCode,territoryName,territoryCode;
        username=sharedPreferences.getString(Login.USER_NAME, "");
        apiToken=sharedPreferences.getString(Login.API_TOKEN,"");
        empCode=sharedPreferences.getString(Login.EMP_CODE,"");
        territoryCode=sharedPreferences.getString(Login.TERRITORY_CODE,"");
        textView.setText(apiToken);


        //valid internet connection
        if ( connectivityManager.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connectivityManager.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connectivityManager.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            /*Toast toast = Toast.makeText(getApplicationContext(), "  Connected  ", Toast.LENGTH_LONG);
            View view = toast.getView();
            view.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4caf50")));
            view.setBackgroundResource(R.drawable.toast_style);
            toast.show();
            */
        }

       else if( connectivityManager.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                connectivityManager.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context, R.style.MyDialogTheme);
            alertDialogBuilder.setTitle(R.string.connectionTitle);
            alertDialogBuilder.setMessage(R.string.connectinErrorMessage)
                    .setCancelable(false)
                    //.setIcon(R.drawable.logout_icon)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Dashboard.this,Customer_Resistration.class);
                Bundle b = new Bundle();
                b.putString("name", textviewSalesPerson.getText().toString());
                b.putString("empCode", textViewEmployeCode.getText().toString());
                b.putString("apiToken",textView.getText().toString());
                intent.putExtra("personBdl", b);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#888888")));

        View header = navigationView.getHeaderView(0);
        textviewSalesPerson = (TextView) header.findViewById(R.id.salesPersonName);
        textViewEmployeCode= (TextView) header.findViewById(R.id.employe_code);
        Intent i=getIntent();
        //String sals=intent.getStringExtra(Login.USER_NAME);

        //String empcode=extras.getString(Login.EMP_CODE);
        //String api=extras.getString(Login.API_TOKEN);


        //textviewSalesPerson.setText(username_string);
       // textViewEmployeCode.setText(empcode);
        textviewSalesPerson.setText(username);
        textViewEmployeCode.setText(empCode);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context, R.style.MyDialogTheme);
            alertDialogBuilder.setTitle(R.string.exit);
            alertDialogBuilder.setMessage(R.string.exitMessage)
                    .setCancelable(false)
                    .setIcon(R.drawable.exit_icon)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_order) {
            Intent intent = new Intent(this, OrderEntry.class);
            Bundle b = new Bundle();
            b.putString("name", textviewSalesPerson.getText().toString());
            b.putString("empCode", textViewEmployeCode.getText().toString());
            b.putString("apiToken",textView.getText().toString());
            intent.putExtra("personBdl", b);
            startActivity(intent);
        } else if (id == R.id.nav_create_customer) {
            Intent intent= new Intent(Dashboard.this,Customer_Resistration.class);
            Bundle b = new Bundle();
            b.putString("name", textviewSalesPerson.getText().toString());
            b.putString("empCode", textViewEmployeCode.getText().toString());
            b.putString("apiToken",textView.getText().toString());
            intent.putExtra("personBdl", b);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
           
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.logout) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context, R.style.MyDialogTheme);
            alertDialogBuilder.setTitle(R.string.logout);
            alertDialogBuilder.setMessage(R.string.logoutMessage)
                    .setCancelable(false)
                    .setIcon(R.drawable.logout_icon)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            sharedPreferences=getSharedPreferences("login", MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.clear();
                            editor.commit();
                            startActivity(new Intent(Dashboard.this,Login.class));
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}
