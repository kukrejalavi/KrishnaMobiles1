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




public class BackgroundSignature extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;
    BackgroundSignature(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        String register_url = "https://krishnadistributor.000webhostapp.com/addsignaturedetails.php";
        if(type.equals("register")) {
            try {
                String txtname = params[1];
                String txtloginname = params[2];
                String txtdatetime= params[3];
                String txtimg= params[4];


                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("txtname","UTF-8")+"="+URLEncoder.encode(txtname,"UTF-8")+"&"
                        +URLEncoder.encode("txtloginname","UTF-8")+"="+URLEncoder.encode(txtloginname,"UTF-8")+"&"
                        +URLEncoder.encode("txtdatetime","UTF-8")+"="+URLEncoder.encode(txtdatetime,"UTF-8")+"&"
                        + URLEncoder.encode("txtimg","UTF-8")+"="+URLEncoder.encode(txtimg,"UTF-8");

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
    protected void onPostExecute(String result) {

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}



