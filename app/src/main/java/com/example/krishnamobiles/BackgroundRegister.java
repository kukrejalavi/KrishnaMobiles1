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




    public class BackgroundRegister extends AsyncTask<String,Void,String> {

        Context context;
        AlertDialog alertDialog;
        BackgroundRegister(Context ctx) {
            context = ctx;
        }
        @Override
        protected String doInBackground(String... params) {
            String type = params[0];

            String register_url = "https://krishnadistributor.000webhostapp.com/addlogindetails.php";
            if(type.equals("register")) {
                try {
                    String txtmobile = params[1];
                    String txtuname = params[2];
                    String txtpass = params[3];


                    URL url = new URL(register_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("mobileno","UTF-8")+"="+URLEncoder.encode(txtmobile,"UTF-8")+"&"
                            +URLEncoder.encode("uname","UTF-8")+"="+URLEncoder.encode(txtuname,"UTF-8")+"&"
                            + URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(txtpass,"UTF-8");

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



