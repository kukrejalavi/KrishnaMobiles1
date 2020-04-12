package com.example.krishnamobiles;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignatureFragment extends AppCompatActivity {
    Button mClear, mGetSign, mCancel;
    File file;
    LinearLayout mContent;
    View view;
    Bitmap bitmap;
    String StoredPath;

    ProgressDialog progressDialog;

    ByteArrayOutputStream byteArrayOutputStream;

    byte[] byteArray;
    ImageView imageView;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private SignaturePad mSignaturePad;
    private Button mClearButton;
    private Button mSaveButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        try {
            if (!InternetAccess.isConnected(SignatureFragment.this))
                InternetAccess.buildDialog(SignatureFragment.this).show();
            else {
                super.onCreate(savedInstanceState);

                //    verifyStoragePermissions(this);
                setContentView(R.layout.fragment_signature);
                imageView = findViewById(R.id.signature_pad_description);
                mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);

                Intent intent=getIntent();

                String name=intent.getStringExtra("name");
                String loginname=intent.getStringExtra("loginname");


                mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
                    @Override
                    public void onStartSigning() {
                        Toast.makeText(SignatureFragment.this, "OnStartSigning", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSigned() {
                        mSaveButton.setEnabled(true);
                        mClearButton.setEnabled(true);
                    }

                    @Override
                    public void onClear() {
                        mSaveButton.setEnabled(false);
                        mClearButton.setEnabled(false);
                    }
                });

                mClearButton = (Button) findViewById(R.id.clear_button);
                mSaveButton = (Button) findViewById(R.id.save_button);

                mClearButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSignaturePad.clear();
                    }
                });

                mSaveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();

                        imageView.setImageBitmap(signatureBitmap);

                        //Bitmap bitmap = BitmapFactory.decodeResource(getResources());
                        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.id.signature_pad_description);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        signatureBitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
                        byte[] bytes = stream.toByteArray();


                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String datetime=simpleDateFormat.format(new Date());

                        String image_str = Base64.encodeToString(bytes, Base64.DEFAULT);

BackgroundSignature backgroundSignature=new BackgroundSignature(getApplicationContext());
backgroundSignature.execute("register",name,loginname,datetime,image_str);


finish();
                    }
                });
            }
        } catch (Exception e) {
        }

    }
}





