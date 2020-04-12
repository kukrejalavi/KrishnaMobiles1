package com.example.krishnamobiles;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class login_activity extends AppCompatActivity {
    TextView txtnewuser,txterrormsg;
    EditText txtusername, txtpassword;
    Button txtsavebtn;
    String Colors = "";
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);
        sp = getSharedPreferences("login", MODE_PRIVATE);

        if (!isConnected(login_activity.this)) buildDialog(login_activity.this).show();
        else {
            if (sp.getBoolean("logged", false)) {
                String username = sp.getString("name", "");
                Intent intent = new Intent(getApplicationContext(), fingerprintlogin.class);
                intent.putExtra("username", username);
                startActivity(intent);
            } else {
                setContentView(R.layout.activity_login_activity);


                txtnewuser = findViewById(R.id.newuser);
                txterrormsg = findViewById(R.id.errormsg);
                txtusername = findViewById(R.id.username);
                txtpassword = findViewById(R.id.password);
                txtsavebtn = findViewById(R.id.savebtn);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


                txtsavebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetLoginDetails backgroundWorker = new GetLoginDetails(getApplicationContext(), new colorinterface() {
                            @Override
                            public void onTaskComplete(String result) {
                                Colors = result;
                                String[] p = Colors.split(",");
                                if (p.length > 1) {
                                    String p1 = p[0];
                                    String p2 = p[1];

                                    String[] username = p1.split(":");
                                    String[] password = p2.split(":");

                                    String newuser = username[1].replaceAll("[^a-zA-Z0-9]", "");
                                    String newpass = password[1].replaceAll("[^a-zA-Z0-9]", "");


                                    if (!txtusername.getText().toString().isEmpty() && !txtpassword.getText().toString().isEmpty()) {
                                        if (txtusername.getText().toString().equals(newuser) && txtpassword.getText().toString().equals(newpass)) {
                                            {

                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                                                intent.putExtra("username", txtusername.getText().toString());

                                                startActivity(intent);
                                                sp.edit().putBoolean("logged", true).apply();
                                                sp.edit().putString("name", txtusername.getText().toString()).apply();
                                                finish();
                                            }
                                        } else
                                            txterrormsg.setText("Invalid Username/Password");
                                    } else
                                        txterrormsg.setText("Please enter all fields");
                                }
                                if (p[0].equals("Username/Password does not exists"))
                                    txterrormsg.setText("Invalid Username/Password");

                                if (p[0].equals("Password cannot be empty"))
                                    txterrormsg.setText("Please enter Password");

                                if (p[0].equals("Username cannot be empty"))
                                    txterrormsg.setText("Please enter Username");
                            }
                        });
                        backgroundWorker.execute("register", txtusername.getText().toString(), txtpassword.getText().toString());
                    }


                });


                txtnewuser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), Registeractivity.class));

                    }
                });

            }
        }
    }
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }
    @Override
    public void onBackPressed() {

    }
}


