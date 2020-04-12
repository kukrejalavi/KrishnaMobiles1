package com.example.krishnamobiles;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.multidots.fingerprintauth.AuthErrorCodes;
import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;

public class fingerprintlogin extends AppCompatActivity  implements FingerPrintAuthCallback {

    FingerPrintAuthHelper mfingerPrintAuthHelper;
TextView txtuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isConnected(fingerprintlogin.this)) buildDialog(fingerprintlogin.this).show();
        else {
            setContentView(R.layout.activity_fingerprintlogin);


            //setContentView(R.layout.activity_fingerprintlogin);
            this.setFinishOnTouchOutside(false);
            txtuser = findViewById(R.id.txtuser);


            Intent intent = getIntent();
            String s = intent.getStringExtra("username");
            txtuser.setText("Welcome," + s);

            mfingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this, this);
        }
    }

    protected void onResume() {
        super.onResume();
        mfingerPrintAuthHelper.startAuth();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void onPause() {
        super.onPause();
        mfingerPrintAuthHelper.stopAuth();
    }

    @Override
    public void onNoFingerPrintHardwareFound() {
        Toast.makeText(this, "no finger sensor", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNoFingerPrintRegistered() {

    }

    @Override
    public void onBelowMarshmallow() {
        Toast.makeText(this, "does not support", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
String username=txtuser.getText().toString();
String[] user=username.split(",");

        intent.putExtra("username",user[1]);

        startActivity(intent);

        finish();
    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
        switch (errorCode) {    //Parse the error code for recoverable/non recoverable error.
            case AuthErrorCodes.CANNOT_RECOGNIZE_ERROR:
                //Toast.makeText(this, "cannot recognize", Toast.LENGTH_LONG).show();
                //Cannot recognize the fingerprint scanned.
                break;
            case AuthErrorCodes.NON_RECOVERABLE_ERROR:
                //This is not recoverable error. Try other options for user authentication. like pin, password.
                break;
            case AuthErrorCodes.RECOVERABLE_ERROR:
                //Any recoverable error. Display message to the user.
                break;

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

    }


