package com.example.krishnamobiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

public class Registeractivity extends AppCompatActivity {

    EditText txtmobileno,txtuname,txtpass;
    TextView txterrormsg;
    Button txtsavebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeractivity);
        this.setFinishOnTouchOutside(false);
        txtmobileno=findViewById(R.id.mobileno);
        txtuname=findViewById(R.id.username);
        txtpass=findViewById(R.id.password);
        txtsavebtn=findViewById(R.id.savebtn);
        txterrormsg = findViewById(R.id.errormsg);

        txtsavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtmobileno.getText().toString().isEmpty() && !txtuname.getText().toString().isEmpty() && !txtpass.getText().toString().isEmpty()) {
                   if(txtmobileno.getText().toString().trim().length()==10) {
                       BackgroundRegister backgroundRegister = new BackgroundRegister(getApplicationContext());

                       backgroundRegister.execute("register", txtmobileno.getText().toString(), txtuname.getText().toString(), txtpass.getText().toString());

                       finish();
                       startActivity(new Intent(getApplicationContext(), login_activity.class));
                   }
                   else
                       txterrormsg.setText("Incorrect Mobile no");
                }
else
    txterrormsg.setText("Please enter all fields");
            }
        });

    }
}
