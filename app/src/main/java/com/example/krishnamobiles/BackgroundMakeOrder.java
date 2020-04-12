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


public class BackgroundMakeOrder extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;
    BackgroundMakeOrder (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        String register_url = "http://krishnadistributor.000webhostapp.com/insertorders.php";
        if(type.equals("register")) {
            try {
                String retailer = params[1];
                String model = params[2];
                String qty = params[3];
                String color = params[4];
                String amt = params[5];
                String remark = params[6];
                String date=params[7];
                String gtotal=params[8];
                String salesperson=params[9];
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("retailer","UTF-8")+"="+URLEncoder.encode(retailer,"UTF-8")+"&"
                        +URLEncoder.encode("model","UTF-8")+"="+URLEncoder.encode(model,"UTF-8")+"&"
                        +URLEncoder.encode("qty","UTF-8")+"="+URLEncoder.encode(qty,"UTF-8")+"&"
                        +URLEncoder.encode("color","UTF-8")+"="+URLEncoder.encode(color,"UTF-8")+"&"
                        +URLEncoder.encode("amt","UTF-8")+"="+URLEncoder.encode(amt,"UTF-8")+"&"
                        +URLEncoder.encode("remark","UTF-8")+"="+URLEncoder.encode(remark,"UTF-8")+"&"
                +URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(date,"UTF-8") + "&"
                +URLEncoder.encode("gtotal","UTF-8")+"="+URLEncoder.encode(gtotal,"UTF-8")+ "&"
                +URLEncoder.encode("salesperson","UTF-8")+"="+URLEncoder.encode(salesperson,"UTF-8") ;


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

//    @Override
//    protected void onPreExecute() {
//        alertDialog = new AlertDialog.Builder(context).create();
//        alertDialog.setTitle("Login Status");
//    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("Registration Success..."))
        {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }
//        else
//        {
//            alertDialog.setMessage(result);
//            alertDialog.show();
//        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

