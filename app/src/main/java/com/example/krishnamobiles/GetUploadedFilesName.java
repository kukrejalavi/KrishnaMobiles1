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

public class GetUploadedFilesName extends AsyncTask<String,Void,String>{

    String finalresult;
    Context context;
    AlertDialog alertDialog;
    private colorinterface mCallback;
    public   GetUploadedFilesName (Context ctx, colorinterface demointerface) {
        context = ctx;
        mCallback=(colorinterface) demointerface;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        String register_url = "http://krishnadistributor.000webhostapp.com/getuploadedfilenames.php";
        if(type.equals("register")) {
            try {

                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                //   String post_data = URLEncoder.encode("txtretailername","UTF-8")+"="+URLEncoder.encode(txtretailername,"UTF-8");


                // bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line ;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                finalresult=result;
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return finalresult;
    }



    @Override
    protected void onPostExecute(String result) {
        mCallback.onTaskComplete(result);

    }



}
