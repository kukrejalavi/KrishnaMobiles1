package com.example.krishnamobiles;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewFiles extends AppCompatActivity {
DownloadManager downloadManager;
TextView txtfile1,txtfile2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_files);
txtfile1=findViewById(R.id.file1);
        txtfile2=findViewById(R.id.file2);



        GetUploadedFilesName getUploadedFilesName=new GetUploadedFilesName(getApplicationContext(), new colorinterface() {
            @Override
            public void onTaskComplete(String result) {
                if(!result.isEmpty()){
                    String removedbrackets = result.replaceAll("\\[", "").replaceAll("\\]", "");

                    String removeddoublequotes = removeDoubleQuotes(removedbrackets);

                    String[] de = removeddoublequotes.split(";");


                    List<String> newlist = new ArrayList<>();

                    for(int i=0;i<de.length;i++){
                        //String g = de[i].replaceAll("^\"|\"$", "");
                        //String good = removeDoubleQuotes(g);
                        newlist.add(de[i]);
                    }

                    //if(newlist.size())
                    txtfile1.setText(de[0]);
//                    if(!newlist.get(1).isEmpty())
                   txtfile2.setText(de[1].replace(",",""));

                }
            }
        });

getUploadedFilesName.execute("register");












txtfile1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
if(!txtfile1.getText().toString().isEmpty())
        downloadManager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        //http://krishnadistributor.000webhostapp.com/uploads/bscit.xlsx
        String websitelink="http://krishnadistributor.000webhostapp.com/uploads/";
        websitelink = websitelink + txtfile1.getText().toString();
        Uri uri=Uri.parse(websitelink);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long reference=downloadManager.enqueue(request);
    }
});


        txtfile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downloadManager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                String websitelink="http://krishnadistributor.000webhostapp.com/uploads/";
                websitelink = websitelink + txtfile2.getText().toString();
                Uri uri=Uri.parse(websitelink);
                DownloadManager.Request request=new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Long reference=downloadManager.enqueue(request);
            }
        });


    }

    public static String removeDoubleQuotes(String input){

        StringBuilder sb = new StringBuilder();

        char[] tab = input.toCharArray();
        for( char current : tab ){
            if( current != '"' )
                sb.append( current );
        }

        return sb.toString();
    }
}
