package com.example.krishnamobiles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SpecialSchemesFragment extends Fragment  {

TextView txt,txt1,txt2;
    String loginname,s;
    View view;
    SwipeRefreshLayout swipeRefreshLayout;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            if (!InternetAccess.isConnected(getContext()))
                InternetAccess.buildDialog(getContext()).show();
            else {
                view = inflater.inflate(R.layout.fragment_specialschemes, container, false);

                swipeRefreshLayout = view.findViewById(R.id.swipe);
                swipeRefreshLayout.setRefreshing(false);
                txt = view.findViewById(R.id.txtview);
                txt1 = view.findViewById(R.id.txtview1);
                txt2 = view.findViewById(R.id.txtview2);

                txt.setMovementMethod(new ScrollingMovementMethod());
                txt1.setMovementMethod(new ScrollingMovementMethod());
                txt2.setMovementMethod(new ScrollingMovementMethod());

                loaddata();

            }
     swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
         @Override
         public void onRefresh() {
txt.setText("");
             txt1.setText("");
             txt2.setText("");

             loaddata();
         }
     });


        }catch(Exception e){}


        return  view;
    }


    public void loaddata()
    {

        GetSpecialSchemes getSpecialSchemes = new GetSpecialSchemes(getContext(), new priceinterface() {
            @Override
            public void onTaskComplete(String result) {
                if (!result.isEmpty()) {
                    s = result;
                    String removedbrackets = s.replaceAll("\\[", "").replaceAll("\\]", "");

                    String removeddoublequotes = removeDoubleQuotes(removedbrackets);

                    String[] de = removeddoublequotes.split(";");

                    List<String[]> gg = new ArrayList<>();

                    for (int i = 0; i < de.length; i++) {

                        String g = de[i].replaceAll("^\"|\"$", "");
                        String good = removeDoubleQuotes(g);
                        String[] kk = good.split(",");
                        gg.add(kk);
                    }

                    for (int i = 0; i < gg.size(); i++) {
                        String[] h = gg.get(i);


                        if (h.length == 2) {
                            if (h[0].contains(","))
                                h[0] = h[0].replace(",", "");


                            if (h[1].contains(","))
                                h[1] = h[1].replace(",", "");

                            if (h[0].equals("Jio")) {
                                String first = txt.getText().toString();
                                if (first.isEmpty())
                                    txt.setText(h[1]);
                                else
                                    txt.setText(first + "\n" + h[1]);
                            }
                            if (h[0].equals("Lava")) {
                                String second = txt1.getText().toString();
                                if (second.isEmpty())
                                    txt1.setText(h[1]);
                                else
                                    txt1.setText(second + "\n" + h[1]);
                            }

                            if (h[0].equals("Techno")) {
                                String third = txt2.getText().toString();
                                if (third.isEmpty())
                                    txt2.setText(h[1]);
                                else
                                    txt2.setText(third + "\n" + h[1]);
                            }
                        }


                        if (h.length == 3) {
                            if (h[0].isEmpty()) {

                                if (h[1].contains(","))
                                    h[1] = h[1].replace(",", "");


                                if (h[2].contains(","))
                                    h[2] = h[2].replace(",", "");

                                if (h[1].equals("Jio")) {
                                    String first = txt.getText().toString();
                                    if (first.isEmpty())
                                        txt.setText(h[2]);
                                    else
                                        txt.setText(first + "\n" + h[2]);
                                }

                                if (h[1].equals("Lava")) {
                                    String second = txt1.getText().toString();
                                    if (second.isEmpty())
                                        txt1.setText(h[2]);
                                    else
                                        txt1.setText(second + "\n" + h[2]);
                                }
                                if (h[1].equals("Techno")) {
                                    String third = txt2.getText().toString();
                                    if (third.isEmpty())
                                        txt2.setText(h[2]);
                                    else
                                        txt2.setText(third + "\n" + h[2]);
                                }
                            }
                        }
                    }
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        getSpecialSchemes.execute("register", loginname);

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

    public boolean onBackPressed() {
        return true;
    }



    }
