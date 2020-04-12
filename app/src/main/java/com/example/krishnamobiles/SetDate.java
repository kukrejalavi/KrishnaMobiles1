package com.example.krishnamobiles;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class SetDate {
  static  Calendar mCurrentDate;
    static  String day, month, year, hour, min, sec;

    public static String Date(final EditText txtdate, final Context ctx){


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

        if(Integer.parseInt(month) == 9) {
            month = Integer.parseInt(month) + 1 + "";
        }
        else {
            if (month.length() == 1) {


                month = "0" + (Integer.parseInt(month) + 1);
            } else {
                month = String.valueOf(mCurrentDate.get(Calendar.MONTH));
                month = String.valueOf(Integer.parseInt(month) + 1);
            }
        }

String datetime = day + "/" + month + "/" + year;
        txtdate.setText(datetime);





        txtdate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ctx, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthofyear, int dayOfMonth) {
                        monthofyear = monthofyear + 1;
                        if (monthofyear < 10) {
                            if (dayOfMonth < 10)

                                txtdate.setText("0" + dayOfMonth + "/0" + monthofyear + "/" + year);
                            else
                                txtdate.setText(dayOfMonth + "/0" + monthofyear + "/" + year);
                        } else {
                            if (dayOfMonth < 10)

                                txtdate.setText("0" + dayOfMonth + "/" + monthofyear + "/" + year);
                            else
                                txtdate.setText(dayOfMonth + "/" +  monthofyear + "/" + year);
                        }
                    }
                }, Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
                datePickerDialog.show();
            }
        });

return datetime;
    }

    public static String Date(final TextView txtdate, final Context ctx){


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

        if(Integer.parseInt(month) == 9) {
            month = Integer.parseInt(month) + 1 + "";
        }
        else {
            if (month.length() == 1) {


                month = "0" + (Integer.parseInt(month) + 1);
            } else {
                month = String.valueOf(mCurrentDate.get(Calendar.MONTH));
                month = String.valueOf(Integer.parseInt(month) + 1);
            }
        }

        String datetime = day + "/" + month + "/" + year;
        txtdate.setText(datetime);





        txtdate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ctx, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthofyear, int dayOfMonth) {
                        monthofyear = monthofyear + 1;
                        if (monthofyear < 10) {
                            if (dayOfMonth < 10)

                                txtdate.setText("0" + dayOfMonth + "/0" + monthofyear + "/" + year);
                            else
                                txtdate.setText(dayOfMonth + "/0" + monthofyear + "/" + year);
                        } else {
                            if (dayOfMonth < 10)

                                txtdate.setText("0" + dayOfMonth + "/" + monthofyear + "/" + year);
                            else
                                txtdate.setText(dayOfMonth + "/" +  monthofyear + "/" + year);
                        }
                    }
                }, Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
                datePickerDialog.show();
            }
        });

        return datetime;
    }
}
