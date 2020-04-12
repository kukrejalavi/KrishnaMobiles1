package com.example.krishnamobiles;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundPayment extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;
    BackgroundPayment(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        String register_url = "https://krishnadistributor.000webhostapp.com/addpayment.php";
        if(type.equals("register")) {
            try {
                String txtretailer = params[1];
                String txtbillno = params[2];
                String txtamt = params[3];
                String txtchqdate = params[4];
                String txtdate = params[5];
                String txttype = params[6];
                String txtsalesperson = params[7];

                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("retailer","UTF-8")+"="+URLEncoder.encode(txtretailer,"UTF-8")+"&"
                        +URLEncoder.encode("billno","UTF-8")+"="+URLEncoder.encode(txtbillno,"UTF-8")+"&"
                + URLEncoder.encode("amount","UTF-8")+"="+URLEncoder.encode(txtamt,"UTF-8")+"&"
                  + URLEncoder.encode("chqdate","UTF-8")+"="+URLEncoder.encode(txtchqdate,"UTF-8")+"&"
                +  URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(txtdate,"UTF-8")+"&"
                +  URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(txttype,"UTF-8")+"&"
                        +  URLEncoder.encode("salesperson","UTF-8")+"="+URLEncoder.encode(txtsalesperson,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }






        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Payment Status");
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("Registration Success..."))
        {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }
        else
        {
            alertDialog.setMessage(result);
            alertDialog.show();
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

