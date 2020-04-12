package com.example.krishnamobiles;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MakeOrder extends Fragment {

    ArrayAdapter<String> adapter;
    String[] newp1, newp2, newp3, newp4, newp5, newp6, newp7, newp8, newp9, newp10;

    AutoCompleteTextView m1, m2, m3, m4, m5, m6, m7, m8, m9, m10;
    EditText q1, q2, q3, q4, q5, q6, q7, q8, q9, q10;
    EditText p1, p2, p3, p4, p5, p6, p7, p8, p9, p10;
    EditText r1, r2, r3, r4, r5, r6, r7, r8, r9, r10;
    Spinner c1, c2, c3, c4, c5, c6, c7, c8, c9, c10;
    String pc1, pc2, pc3, pc4, pc5, pc6, pc7, pc8, pc9, pc10;
    TextView txttotalamt;
    String Desc = "";
    String Colors, Prices = "";
    String result;

    InputStream isr;
    List<String> listdesc = new ArrayList<>();

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            if (!InternetAccess.isConnected(getContext()))
                InternetAccess.buildDialog(getContext()).show();
            else {
                view = getLayoutInflater().inflate(R.layout.fragment_make_order, container, false);

                txttotalamt = view.findViewById(R.id.totalamt);


                m1 = view.findViewById(R.id.m1);
                m2 = view.findViewById(R.id.m2);
                m3 = view.findViewById(R.id.m3);
                m4 = view.findViewById(R.id.m4);
                m5 = view.findViewById(R.id.m5);
                m6 = view.findViewById(R.id.m6);
                m7 = view.findViewById(R.id.m7);
                m8 = view.findViewById(R.id.m8);
                m9 = view.findViewById(R.id.m9);
                m10 = view.findViewById(R.id.m10);


                q1 = view.findViewById(R.id.q1);
                q2 = view.findViewById(R.id.q2);
                q3 = view.findViewById(R.id.q3);
                q4 = view.findViewById(R.id.q4);
                q5 = view.findViewById(R.id.q5);
                q6 = view.findViewById(R.id.q6);
                q7 = view.findViewById(R.id.q7);
                q8 = view.findViewById(R.id.q8);
                q9 = view.findViewById(R.id.q9);
                q10 = view.findViewById(R.id.q10);


                p1 = view.findViewById(R.id.p1);
                p2 = view.findViewById(R.id.p2);
                p3 = view.findViewById(R.id.p3);
                p4 = view.findViewById(R.id.p4);
                p5 = view.findViewById(R.id.p5);
                p6 = view.findViewById(R.id.p6);
                p7 = view.findViewById(R.id.p7);
                p8 = view.findViewById(R.id.p8);
                p9 = view.findViewById(R.id.p9);
                p10 = view.findViewById(R.id.p10);


                r1 = view.findViewById(R.id.r1);
                r2 = view.findViewById(R.id.r2);
                r3 = view.findViewById(R.id.r3);
                r4 = view.findViewById(R.id.r4);
                r5 = view.findViewById(R.id.r5);
                r6 = view.findViewById(R.id.r6);
                r7 = view.findViewById(R.id.r7);
                r8 = view.findViewById(R.id.r8);
                r9 = view.findViewById(R.id.r9);
                r10 = view.findViewById(R.id.r10);


                c1 = view.findViewById(R.id.c1);
                c2 = view.findViewById(R.id.c2);
                c3 = view.findViewById(R.id.c3);
                c4 = view.findViewById(R.id.c4);
                c5 = view.findViewById(R.id.c5);
                c6 = view.findViewById(R.id.c6);
                c7 = view.findViewById(R.id.c7);
                c8 = view.findViewById(R.id.c8);
                c9 = view.findViewById(R.id.c9);
                c10 = view.findViewById(R.id.c10);


                q1.setEnabled(false);
                q2.setEnabled(false);
                q3.setEnabled(false);
                q4.setEnabled(false);
                q5.setEnabled(false);
                q6.setEnabled(false);
                q7.setEnabled(false);
                q8.setEnabled(false);
                q9.setEnabled(false);
                q10.setEnabled(false);


                new getModelnos().execute("");


                TextWatcher watcher1 = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        GetColorsOfModels backgroundWorker = new GetColorsOfModels(getContext(), new colorinterface() {
                            @Override
                            public void onTaskComplete(String result) {
                                try {
                                    if (!InternetAccess.isConnected(getContext()))
                                        InternetAccess.buildDialog(getContext()).show();
                                    else {
                                        Colors = result;
                                        String[] p = Colors.split(",");


                                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, p);
                                        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                                        c1.setAdapter(spinnerArrayAdapter);
                                    }
                                } catch (Exception e) {
                                }
                            }
                        });
                        backgroundWorker.execute("register", m1.getText().toString());


                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };


                TextWatcher watcher2 = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        GetColorsOfModels backgroundWorker = new GetColorsOfModels(getContext(), new colorinterface() {
                            @Override
                            public void onTaskComplete(String result) {
                                try {
                                    if (!InternetAccess.isConnected(getContext()))
                                        InternetAccess.buildDialog(getContext()).show();
                                    else {
                                        Colors = result;
                                        String[] p = Colors.split(",");


                                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, p);
                                        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                                        c2.setAdapter(spinnerArrayAdapter);

                                    }
                                } catch (Exception e) {
                                }
                            }
                        });
                        backgroundWorker.execute("register", m2.getText().toString());

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };


                TextWatcher watcher3 = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        GetColorsOfModels backgroundWorker = new GetColorsOfModels(getContext(), new colorinterface() {
                            @Override
                            public void onTaskComplete(String result) {
                                try {
                                    if (!InternetAccess.isConnected(getContext()))
                                        InternetAccess.buildDialog(getContext()).show();
                                    else {
                                        Colors = result;
                                        String[] p = Colors.split(",");


                                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, p);
                                        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                                        c3.setAdapter(spinnerArrayAdapter);

                                    }
                                } catch (Exception e) {
                                }
                            }
                        });
                        backgroundWorker.execute("register", m3.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };


                TextWatcher watcher4 = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        GetColorsOfModels backgroundWorker = new GetColorsOfModels(getContext(), new colorinterface() {
                            @Override
                            public void onTaskComplete(String result) {
                                try {
                                    if (!InternetAccess.isConnected(getContext()))
                                        InternetAccess.buildDialog(getContext()).show();
                                    else {
                                        Colors = result;
                                        String[] p = Colors.split(",");


                                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, p);
                                        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                                        c4.setAdapter(spinnerArrayAdapter);
                                    }
                                } catch (Exception e) {
                                }
                            }
                        });
                        backgroundWorker.execute("register", m4.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };


                TextWatcher watcher5 = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        GetColorsOfModels backgroundWorker = new GetColorsOfModels(getContext(), new colorinterface() {
                            @Override
                            public void onTaskComplete(String result) {

                                try {
                                    if (!InternetAccess.isConnected(getContext()))
                                        InternetAccess.buildDialog(getContext()).show();
                                    else {


                                        Colors = result;
                                        String[] p = Colors.split(",");


                                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, p);
                                        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                                        c5.setAdapter(spinnerArrayAdapter);
                                    }
                                } catch (Exception e) {
                                }
                            }
                        });
                        backgroundWorker.execute("register", m5.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };


                TextWatcher watcher6 = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        GetColorsOfModels backgroundWorker = new GetColorsOfModels(getContext(), new colorinterface() {
                            @Override
                            public void onTaskComplete(String result) {

                                try {
                                    if (!InternetAccess.isConnected(getContext()))
                                        InternetAccess.buildDialog(getContext()).show();
                                    else {


                                        Colors = result;
                                        String[] p = Colors.split(",");


                                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, p);
                                        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                                        c6.setAdapter(spinnerArrayAdapter);
                                    }
                                } catch (Exception e) {
                                }
                            }
                        });
                        backgroundWorker.execute("register", m6.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };


                TextWatcher watcher7 = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        GetColorsOfModels backgroundWorker = new GetColorsOfModels(getContext(), new colorinterface() {
                            @Override
                            public void onTaskComplete(String result) {

                                try {
                                    if (!InternetAccess.isConnected(getContext()))
                                        InternetAccess.buildDialog(getContext()).show();
                                    else {


                                        Colors = result;
                                        String[] p = Colors.split(",");


                                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, p);
                                        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                                        c7.setAdapter(spinnerArrayAdapter);
                                    }
                                } catch (Exception e) {
                                }
                            }
                        });
                        backgroundWorker.execute("register", m7.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };


                TextWatcher watcher8 = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        GetColorsOfModels backgroundWorker = new GetColorsOfModels(getContext(), new colorinterface() {
                            @Override
                            public void onTaskComplete(String result) {

                                try {
                                    if (!InternetAccess.isConnected(getContext()))
                                        InternetAccess.buildDialog(getContext()).show();
                                    else {


                                        Colors = result;
                                        String[] p = Colors.split(",");


                                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, p);
                                        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                                        c8.setAdapter(spinnerArrayAdapter);

                                    }
                                } catch (Exception e) {
                                }
                            }
                        });
                        backgroundWorker.execute("register", m8.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };


                TextWatcher watcher9 = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        GetColorsOfModels backgroundWorker = new GetColorsOfModels(getContext(), new colorinterface() {
                            @Override
                            public void onTaskComplete(String result) {

                                try {
                                    if (!InternetAccess.isConnected(getContext()))
                                        InternetAccess.buildDialog(getContext()).show();
                                    else {


                                    }
                                    Colors = result;
                                    String[] p = Colors.split(",");


                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, p);
                                    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                                    c9.setAdapter(spinnerArrayAdapter);
                                } catch (Exception e) {
                                }
                            }
                        });
                        backgroundWorker.execute("register", m9.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };


                TextWatcher watcher10 = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        GetColorsOfModels backgroundWorker = new GetColorsOfModels(getContext(), new colorinterface() {
                            @Override
                            public void onTaskComplete(String result) {

                                try {
                                    if (!InternetAccess.isConnected(getContext()))
                                        InternetAccess.buildDialog(getContext()).show();
                                    else {


                                    }
                                    Colors = result;
                                    String[] p = Colors.split(",");


                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, p);
                                    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                                    c10.setAdapter(spinnerArrayAdapter);
                                } catch (Exception e) {
                                }
                            }
                        });
                        backgroundWorker.execute("register", m10.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };


                q1.addTextChangedListener(new TextWatcher() {


                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            if (!InternetAccess.isConnected(getContext()))
                                InternetAccess.buildDialog(getContext()).show();
                            else {

                                String amt = p1.getText().toString();
                                String qty = q1.getText().toString();
                                if (!qty.isEmpty()) {
                                    if (!amt.isEmpty()) {
                                        Integer newprice = Integer.parseInt(amt) * Integer.parseInt(qty);
                                        p1.setText(newprice + "");
                                        txttotalamt.setText(grandtotal());
                                    }

                                }

                                if (qty.isEmpty()) {
                                    p1.setText(newp1[0]);
                                    txttotalamt.setText(grandtotal());
                                }
                            }
                        } catch (Exception e) {
                        }
                    }


                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                q2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        try {
                            if (!InternetAccess.isConnected(getContext()))
                                InternetAccess.buildDialog(getContext()).show();
                            else {

                                String amt = p2.getText().toString();
                                String qty = q2.getText().toString();
                                if (!qty.isEmpty()) {
                                    if (!amt.isEmpty()) {
                                        Integer newprice = Integer.parseInt(amt) * Integer.parseInt(qty);
                                        p2.setText(newprice + "");
                                        txttotalamt.setText(grandtotal());
                                    }

                                }

                                if (qty.isEmpty()) {
                                    p2.setText(newp2[0]);
                                    txttotalamt.setText(grandtotal());
                                }
                            }
                        } catch (Exception e) {
                        }
                    }


                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


                q3.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        try {
                            if (!InternetAccess.isConnected(getContext()))
                                InternetAccess.buildDialog(getContext()).show();
                            else {

                                String amt = p3.getText().toString();
                                String qty = q3.getText().toString();
                                if (!qty.isEmpty()) {
                                    if (!amt.isEmpty()) {
                                        Integer newprice = Integer.parseInt(amt) * Integer.parseInt(qty);
                                        p3.setText(newprice + "");
                                        txttotalamt.setText(grandtotal());
                                    }

                                }

                                if (qty.isEmpty()) {
                                    p3.setText(newp3[0]);
                                    txttotalamt.setText(grandtotal());
                                }
                            }
                        } catch (Exception e) {
                        }
                    }


                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


                q4.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        try {
                            if (!InternetAccess.isConnected(getContext()))
                                InternetAccess.buildDialog(getContext()).show();
                            else {

                                String amt = p4.getText().toString();
                                String qty = q4.getText().toString();
                                if (!qty.isEmpty()) {
                                    if (!amt.isEmpty()) {
                                        Integer newprice = Integer.parseInt(amt) * Integer.parseInt(qty);
                                        p4.setText(newprice + "");
                                        txttotalamt.setText(grandtotal());
                                    }

                                }

                                if (qty.isEmpty()) {
                                    p4.setText(newp4[0]);
                                    txttotalamt.setText(grandtotal());
                                }
                            }
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


                q5.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        try {
                            if (!InternetAccess.isConnected(getContext()))
                                InternetAccess.buildDialog(getContext()).show();
                            else {

                                String amt = p5.getText().toString();
                                String qty = q5.getText().toString();
                                if (!qty.isEmpty()) {
                                    if (!amt.isEmpty()) {
                                        Integer newprice = Integer.parseInt(amt) * Integer.parseInt(qty);
                                        p5.setText(newprice + "");
                                        txttotalamt.setText(grandtotal());
                                    }

                                }

                                if (qty.isEmpty()) {
                                    p5.setText(newp5[0]);
                                    txttotalamt.setText(grandtotal());
                                }
                            }
                        } catch (Exception e) {
                        }
                    }


                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                q6.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {


                        try {
                            if (!InternetAccess.isConnected(getContext()))
                                InternetAccess.buildDialog(getContext()).show();
                            else {

                                String amt = p6.getText().toString();
                                String qty = q6.getText().toString();
                                if (!qty.isEmpty()) {
                                    if (!amt.isEmpty()) {
                                        Integer newprice = Integer.parseInt(amt) * Integer.parseInt(qty);
                                        p6.setText(newprice + "");
                                        txttotalamt.setText(grandtotal());
                                    }

                                }

                                if (qty.isEmpty()) {
                                    p6.setText(newp6[0]);
                                    txttotalamt.setText(grandtotal());
                                }
                            }
                        } catch (Exception e) {
                        }
                    }


                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


                q7.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        try {
                            if (!InternetAccess.isConnected(getContext()))
                                InternetAccess.buildDialog(getContext()).show();
                            else {

                                String amt = p7.getText().toString();
                                String qty = q7.getText().toString();
                                if (!qty.isEmpty()) {
                                    if (!amt.isEmpty()) {
                                        Integer newprice = Integer.parseInt(amt) * Integer.parseInt(qty);
                                        p7.setText(newprice + "");
                                        txttotalamt.setText(grandtotal());
                                    }

                                }

                                if (qty.isEmpty()) {
                                    p7.setText(newp7[0]);
                                    txttotalamt.setText(grandtotal());
                                }
                            }
                        } catch (Exception e) {
                        }
                    }


                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


                q8.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {


                        try {
                            if (!InternetAccess.isConnected(getContext()))
                                InternetAccess.buildDialog(getContext()).show();
                            else {


                                String amt = p8.getText().toString();
                                String qty = q8.getText().toString();
                                if (!qty.isEmpty()) {
                                    if (!amt.isEmpty()) {
                                        Integer newprice = Integer.parseInt(amt) * Integer.parseInt(qty);
                                        p8.setText(newprice + "");
                                        txttotalamt.setText(grandtotal());
                                    }

                                }

                                if (qty.isEmpty()) {
                                    p8.setText(newp8[0]);
                                    txttotalamt.setText(grandtotal());
                                }
                            }
                        } catch (Exception e) {
                        }
                    }


                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


                q9.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {


                        try {
                            if (!InternetAccess.isConnected(getContext()))
                                InternetAccess.buildDialog(getContext()).show();
                            else {


                                String amt = p9.getText().toString();
                                String qty = q9.getText().toString();
                                if (!qty.isEmpty()) {
                                    if (!amt.isEmpty()) {
                                        Integer newprice = Integer.parseInt(amt) * Integer.parseInt(qty);
                                        p9.setText(newprice + "");
                                        txttotalamt.setText(grandtotal());
                                    }

                                }

                                if (qty.isEmpty()) {
                                    p9.setText(newp9[0]);
                                    txttotalamt.setText(grandtotal());
                                }
                            }
                        } catch (Exception e) {
                        }
                    }


                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


                q10.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {


                        try {
                            if (!InternetAccess.isConnected(getContext()))
                                InternetAccess.buildDialog(getContext()).show();
                            else {


                                String amt = p10.getText().toString();
                                String qty = q10.getText().toString();
                                if (!qty.isEmpty()) {
                                    if (!amt.isEmpty()) {
                                        Integer newprice = Integer.parseInt(amt) * Integer.parseInt(qty);
                                        p10.setText(newprice + "");
                                        txttotalamt.setText(grandtotal());
                                    }

                                }

                                if (qty.isEmpty()) {
                                    p10.setText(newp10[0]);
                                    txttotalamt.setText(grandtotal());
                                }
                            }
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                m1.addTextChangedListener(watcher1);
                m2.addTextChangedListener(watcher2);
                m3.addTextChangedListener(watcher3);
                m4.addTextChangedListener(watcher4);
                m5.addTextChangedListener(watcher5);
                m6.addTextChangedListener(watcher6);
                m7.addTextChangedListener(watcher7);
                m8.addTextChangedListener(watcher8);
                m9.addTextChangedListener(watcher9);
                m10.addTextChangedListener(watcher10);


                c1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        GetModelPrice backgroundWorker = new GetModelPrice(getContext(), new priceinterface() {
                            @Override
                            public void onTaskComplete(String result) {

                                try {
                                    if (!InternetAccess.isConnected(getContext()))
                                        InternetAccess.buildDialog(getContext()).show();
                                    else {
                                        newp1 = result.split(",");
                                        p1.setText(newp1[0]);
                                        q1.setEnabled(true);
                                        txttotalamt.setText(grandtotal());
                                    }
                                } catch (Exception e) {
                                }
                            }
                        });
                        backgroundWorker.execute("register", m1.getText().toString(), c1.getSelectedItem().toString());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });


                c2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        GetModelPrice backgroundWorker = new GetModelPrice(getContext(), new priceinterface() {
                            @Override
                            public void onTaskComplete(String result) {

                                try {
                                    if (!InternetAccess.isConnected(getContext()))
                                        InternetAccess.buildDialog(getContext()).show();
                                    else {

                                        newp2 = result.split(",");
                                        p2.setText(newp2[0]);
                                        q2.setEnabled(true);
                                        txttotalamt.setText(grandtotal());
                                    }
                                } catch (Exception e) {
                                }
                            }
                        });
                        backgroundWorker.execute("register", m2.getText().toString(), c2.getSelectedItem().toString());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });

                c3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        GetModelPrice backgroundWorker = new GetModelPrice(getContext(), new priceinterface() {
                            @Override
                            public void onTaskComplete(String result) {

                                try {
                                    if (!InternetAccess.isConnected(getContext()))
                                        InternetAccess.buildDialog(getContext()).show();
                                    else {

                                        newp3 = result.split(",");
                                        p3.setText(newp3[0]);
                                        q3.setEnabled(true);
                                        txttotalamt.setText(grandtotal());
                                    }
                                } catch (Exception e) {
                                }
                            }
                        });
                        backgroundWorker.execute("register", m3.getText().toString(), c3.getSelectedItem().toString());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });


                c4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        GetModelPrice backgroundWorker = new GetModelPrice(getContext(), new priceinterface() {
                            @Override
                            public void onTaskComplete(String result) {

                                try {
                                    if (!InternetAccess.isConnected(getContext()))
                                        InternetAccess.buildDialog(getContext()).show();
                                    else {

                                        newp4 = result.split(",");
                                        p4.setText(newp4[0]);
                                        q4.setEnabled(true);
                                        txttotalamt.setText(grandtotal());
                                    }
                                } catch (Exception e) {
                                }
                            }
                        });
                        backgroundWorker.execute("register", m4.getText().toString(), c4.getSelectedItem().toString());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });


                c5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        GetModelPrice backgroundWorker = new GetModelPrice(getContext(), new priceinterface() {
                            @Override
                            public void onTaskComplete(String result) {

                                try {
                                    if (!InternetAccess.isConnected(getContext()))
                                        InternetAccess.buildDialog(getContext()).show();
                                    else {

                                        newp5 = result.split(",");
                                        p5.setText(newp5[0]);
                                        q5.setEnabled(true);
                                        txttotalamt.setText(grandtotal());
                                    }
                                } catch (Exception e) {
                                }
                            }
                        });
                        backgroundWorker.execute("register", m5.getText().toString(), c5.getSelectedItem().toString());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });


                c6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        GetModelPrice backgroundWorker = new GetModelPrice(getContext(), new priceinterface() {
                            @Override
                            public void onTaskComplete(String result) {

                                try {
                                    if (!InternetAccess.isConnected(getContext()))
                                        InternetAccess.buildDialog(getContext()).show();
                                    else {

                                        newp6 = result.split(",");
                                        p6.setText(newp6[0]);
                                        q6.setEnabled(true);
                                        txttotalamt.setText(grandtotal());
                                    }
                                } catch (Exception e) {
                                }
                            }
                        });
                        backgroundWorker.execute("register", m6.getText().toString(), c6.getSelectedItem().toString());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });


                c7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        GetModelPrice backgroundWorker = new GetModelPrice(getContext(), new priceinterface() {
                            @Override
                            public void onTaskComplete(String result) {

                                try {
                                    if (!InternetAccess.isConnected(getContext()))
                                        InternetAccess.buildDialog(getContext()).show();
                                    else {

                                        newp7 = result.split(",");
                                        p7.setText(newp7[0]);
                                        q7.setEnabled(true);
                                        txttotalamt.setText(grandtotal());
                                    }
                                } catch (Exception e) {
                                }
                            }
                        });
                        backgroundWorker.execute("register", m7.getText().toString(), c7.getSelectedItem().toString());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });


                c8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        GetModelPrice backgroundWorker = new GetModelPrice(getContext(), new priceinterface() {
                            @Override
                            public void onTaskComplete(String result) {

                                try {
                                    if (!InternetAccess.isConnected(getContext()))
                                        InternetAccess.buildDialog(getContext()).show();
                                    else {

                                        newp8 = result.split(",");
                                        p8.setText(newp8[0]);
                                        q8.setEnabled(true);
                                        txttotalamt.setText(grandtotal());
                                    }
                                } catch (Exception e) {
                                }
                            }
                        });
                        backgroundWorker.execute("register", m8.getText().toString(), c8.getSelectedItem().toString());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });

                c9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        GetModelPrice backgroundWorker = new GetModelPrice(getContext(), new priceinterface() {
                            @Override
                            public void onTaskComplete(String result) {

                                try {
                                    if (!InternetAccess.isConnected(getContext()))
                                        InternetAccess.buildDialog(getContext()).show();
                                    else {

                                        newp9 = result.split(",");
                                        p9.setText(newp9[0]);
                                        q9.setEnabled(true);
                                        txttotalamt.setText(grandtotal());
                                    }
                                } catch (Exception e) {
                                }
                            }
                        });
                        backgroundWorker.execute("register", m9.getText().toString(), c9.getSelectedItem().toString());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });

                c10.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        GetModelPrice backgroundWorker = new GetModelPrice(getContext(), new priceinterface() {
                            @Override
                            public void onTaskComplete(String result) {

                                try {
                                    if (!InternetAccess.isConnected(getContext()))
                                        InternetAccess.buildDialog(getContext()).show();
                                    else {

                                        newp10 = result.split(",");
                                        p10.setText(newp10[0]);
                                        q10.setEnabled(true);
                                        txttotalamt.setText(grandtotal());
                                    }
                                } catch (Exception e) {
                                }
                            }
                        });
                        backgroundWorker.execute("register", m10.getText().toString(), c10.getSelectedItem().toString());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });

            }
        } catch (Exception e) {
        }
        return view;
    }


    private String getqtyTotal(EditText rateEditText) {
        if (!rateEditText.getText().toString().isEmpty()) {
            return String.valueOf(Integer.parseInt(rateEditText.getText().toString()));
        }
        return "0";
    }

    private String grandtotal() {
        pc1 = getqtyTotal(p1);
        pc2 = getqtyTotal(p2);
        pc3 = getqtyTotal(p3);
        pc4 = getqtyTotal(p4);
        pc5 = getqtyTotal(p5);
        pc6 = getqtyTotal(p6);
        pc7 = getqtyTotal(p7);
        pc8 = getqtyTotal(p8);
        pc9 = getqtyTotal(p9);
        pc10 = getqtyTotal(p10);


        return Integer.parseInt(pc1) + Integer.parseInt(pc2) + Integer.parseInt(pc3) + Integer.parseInt(pc4) + Integer.parseInt(pc5) + Integer.parseInt(pc6) + Integer.parseInt(pc7) + Integer.parseInt(pc8) + Integer.parseInt(pc9) + Integer.parseInt(pc10) + "";

    }


    private class getModelnos extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... params) {

            try {
                if (!InternetAccess.isConnected(getContext()))
                    InternetAccess.buildDialog(getContext()).show();
                else {

                    result = "";

                    isr = null;

                    try {

                        HttpClient httpclient = new DefaultHttpClient();

                        HttpPost httppost = new HttpPost("http://krishnadistributor.000webhostapp.com/getallmodels.php"); //YOUR PHP SCRIPT ADDRESS

                        HttpResponse response = httpclient.execute(httppost);

                        HttpEntity entity = response.getEntity();

                        isr = entity.getContent();

                    } catch (Exception e) {

                        Log.e("log_tag", "Error in http connection " + e.toString());


                    }

                    //convert response to string

                    try {

                        BufferedReader reader = new BufferedReader(new InputStreamReader(isr, "iso-8859-1"), 8);

                        StringBuilder sb = new StringBuilder();

                        String line = null;

                        while ((line = reader.readLine()) != null) {

                            sb.append(line);

                        }

                        isr.close();


                        result = sb.toString();

                    } catch (Exception e) {

                        Log.e("log_tag", "Error  converting result " + e.toString());

                    }


                    try {


                        JSONArray jArray = new JSONArray(result);

                        for (int i = 0; i < jArray.length(); i++) {

                            JSONObject json = jArray.getJSONObject(i);


                            Desc = Desc + "\n" + json.getString("modelno");

                        }


                    } catch (Exception e) {

                        // TODO: handle exception

                        Log.e("log_tag", "Error Parsing Data " + e.toString());

                    }


                    String[] descriptions = Desc.split("\n");


                    for (int i = 0; i < descriptions.length; i++) {
                        if (!descriptions[i].isEmpty())
                            listdesc.add(descriptions[i]);


                    }


                }
            } catch (Exception e) {
            }

            return "";


        }


        protected void onPostExecute(String result) {

            try {
                if (!InternetAccess.isConnected(getContext()))
                    InternetAccess.buildDialog(getContext()).show();
                else {

                    adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listdesc);
                    adapter.setNotifyOnChange(true);
                    m1.setAdapter(adapter);
                    m2.setAdapter(adapter);
                    m3.setAdapter(adapter);
                    m4.setAdapter(adapter);
                    m5.setAdapter(adapter);
                    m6.setAdapter(adapter);
                    m7.setAdapter(adapter);
                    m8.setAdapter(adapter);
                    m9.setAdapter(adapter);
                    m10.setAdapter(adapter);
                    updateTransport(result);
                    adapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
            }
        }


        private boolean Contains(ArrayAdapter<String> adapter, String value) {
            boolean contains = true;
            try {
                if (!InternetAccess.isConnected(getContext()))
                    InternetAccess.buildDialog(getContext()).show();
                else {

                    contains = false;
                    for (int i = 0; i < adapter.getCount(); i++) {
                        if (adapter.getItem(i).equals(value))
                            contains = true;
                    }
                }
            } catch (Exception e) {
            }
            return contains;
        }

        public void updateTransport(String transport) {

            try {
                if (!InternetAccess.isConnected(getContext()))
                    InternetAccess.buildDialog(getContext()).show();
                else {
                    if (!Contains(adapter, transport)) {
                        adapter.add(transport);
                        adapter.notifyDataSetChanged();
                    }
                }
            } catch (Exception e) {
            }
        }
    }
}
