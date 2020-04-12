package com.example.krishnamobiles;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PaymentFragment extends Fragment {
RadioButton txtrbt1,txtrbt2;

TextView txtbillno,txtdate,txtchqdate,txtamt;
Button savebtn,clearbtn;
    Calendar mCurrentDate;
    String loginname;
    String day, month, year, hour, min, sec;
    String retailername;
    View view;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            if (!InternetAccess.isConnected(getContext()))
                InternetAccess.buildDialog(getContext()).show();
            else {

                view = getLayoutInflater().inflate(R.layout.fragment_payment, container, false);

                txtrbt1 = view.findViewById(R.id.rbt1);
                txtrbt2 = view.findViewById(R.id.rbt2);
                txtbillno = view.findViewById(R.id.bill_no);
                txtdate = view.findViewById(R.id.date);
                txtchqdate = view.findViewById(R.id.chqdate);
                txtamt = view.findViewById(R.id.amount);


                savebtn = view.findViewById(R.id.save);
                clearbtn = view.findViewById(R.id.clear);

                mCurrentDate = Calendar.getInstance();
                day = String.valueOf(mCurrentDate.get(Calendar.DAY_OF_MONTH));
                month = String.valueOf(mCurrentDate.get(Calendar.MONTH));
                year = String.valueOf(mCurrentDate.get(Calendar.YEAR));
                hour = String.valueOf(mCurrentDate.get(Calendar.HOUR));
                min = String.valueOf(mCurrentDate.get(Calendar.MINUTE));
                sec = String.valueOf(mCurrentDate.get(Calendar.SECOND));

                if (hour.length() == 1)
                    hour = "0" + hour;

                if (min.length() == 1)
                    min = "0" + min;

                if (sec.length() == 1)
                    sec = "0" + sec;


                if (day.length() == 1) {
                    day = "0" + (Integer.parseInt(day));
                } else {
                    day = String.valueOf(mCurrentDate.get(Calendar.DAY_OF_MONTH));
                }


                if (month.length() == 1) {
                    month = "0" + (Integer.parseInt(month) + 1);
                } else {
                    month = String.valueOf(mCurrentDate.get(Calendar.MONTH));
                }

                txtdate.setText(SetDate.Date(txtdate, getContext()));




                txtchqdate.setText(SetDate.Date(txtchqdate, getContext()));



                txtrbt1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtchqdate.setVisibility(View.GONE);
                        if (txtrbt2.isChecked())
                            txtrbt2.setChecked(false);
                    }
                });


                txtrbt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtchqdate.setVisibility(View.VISIBLE);
                        if (txtrbt1.isChecked())
                            txtrbt1.setChecked(false);
                    }
                });


                savebtn.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {


                        try {
                            if (!InternetAccess.isConnected(getContext()))
                                InternetAccess.buildDialog(getContext()).show();
                            else {

                                String retailer = retailername;
                                String billno = txtbillno.getText().toString();
                                String amount = txtamt.getText().toString();
                                String salesman = loginname;


                                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                                String Time = df.format(new Date());


                                String ddmmyyyy = txtdate.getText().toString();
                                String yyyymmdd = ddmmyyyy.split("/")[2] + "-" + ddmmyyyy.split("/")[1] + "-" + ddmmyyyy.split("/")[0];
                                String date = yyyymmdd + " " + Time;


                                String chqdateddmmyyyy = txtchqdate.getText().toString();
                                String chqdateyyyymmdd = chqdateddmmyyyy.split("/")[2] + "-" + chqdateddmmyyyy.split("/")[1] + "-" + chqdateddmmyyyy.split("/")[0];
                                String chqdate = chqdateyyyymmdd + " " + Time;


                                String type;
                                if (txtrbt1.isChecked()) {
                                    type = "Cash";
                                    chqdate = "No Date";
                                } else
                                    type = "Cheque";

                                if (!billno.isEmpty() && !amount.isEmpty() && !date.isEmpty() && !type.isEmpty()) {
                                    BackgroundPayment backgroundWorker = new BackgroundPayment(getContext());
                                    backgroundWorker.execute("register", retailer, billno, amount, chqdate, date, type, salesman);
                                    txtbillno.setText("");
                                    txtamt.setText("");
                                    if (txtchqdate.getVisibility() == View.VISIBLE)
                                        txtchqdate.setText(SetDate.Date(txtchqdate, getContext()));
                                    txtdate.setText(SetDate.Date(txtdate, getContext()));
                                    txtrbt1.setChecked(false);
                                    txtrbt2.setChecked(false);
                                    txtchqdate.setVisibility(view.INVISIBLE);
                                } else
                                    Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                        }
                    }
                });


                clearbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (!InternetAccess.isConnected(getContext()))
                                InternetAccess.buildDialog(getContext()).show();
                            else {
                                if (txtrbt1.isChecked()) {

                                    txtdate.setText(day + "/" + month + "/" + year);
                                    txtbillno.setText("");
                                    txtamt.setText("");
                                    txtrbt1.setChecked(false);

                                }
                                if (txtrbt2.isChecked()) {
                                    txtdate.setText(day + "/" + month + "/" + year);
                                    txtchqdate.setText(day + "/" + month + "/" + year);
                                    txtbillno.setText("");
                                    txtamt.setText("");
                                    txtrbt2.setChecked(false);
                                    txtchqdate.setVisibility(View.INVISIBLE);

                                }
                            }
                        }catch(Exception e){}
                            }
                });
            }
        }catch(Exception e){}

        return view;
    }

}
