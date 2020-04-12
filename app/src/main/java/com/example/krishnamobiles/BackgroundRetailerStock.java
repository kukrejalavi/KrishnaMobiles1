package com.example.krishnamobiles;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

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

public class BackgroundRetailerStock extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;
    BackgroundRetailerStock(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        String register_url = "https://krishnadistributor.000webhostapp.com/addretailerstock.php";
        if(type.equals("register")) {
            try {
                String txtdate = params[1];
                String txtretailer = params[2];
                String txtsalesman = params[3];
                String txtmodel = params[4];
                String txtqty = params[5];
                String txtclr = params[6];

                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(txtdate,"UTF-8")+"&"
                        +URLEncoder.encode("retailer","UTF-8")+"="+URLEncoder.encode(txtretailer,"UTF-8")+"&"
                        + URLEncoder.encode("salesman","UTF-8")+"="+URLEncoder.encode(txtsalesman,"UTF-8")+"&"
                        + URLEncoder.encode("modelno","UTF-8")+"="+URLEncoder.encode(txtmodel,"UTF-8")+"&"
                        +  URLEncoder.encode("qty","UTF-8")+"="+URLEncoder.encode(txtqty,"UTF-8")+"&"
                        +  URLEncoder.encode("clr","UTF-8")+"="+URLEncoder.encode(txtclr,"UTF-8");


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
        alertDialog.setTitle("");
    }

    @Override
    protected void onPostExecute(String result) {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

