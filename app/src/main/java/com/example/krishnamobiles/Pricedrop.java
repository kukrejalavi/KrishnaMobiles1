package com.example.krishnamobiles;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
public class Pricedrop extends Fragment{
    RecyclerView recycler;
    List<Pricedrop.DataAdapter> dataAdapter;
    List<Pricedrop.DataAdapter> dataAdapter1;
String retailername;
String loginname;
TextView txtpricedrop, txtviewstatus;
    boolean firstclick;
FrameLayout txtframmelayout;
    View view;
    Pricedrop.DataAdapter isi;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            if (!InternetAccess.isConnected(getContext()))
                InternetAccess.buildDialog(getContext()).show();
            else {

                view = inflater.inflate(R.layout.fragment_pricedrop, container, false);
                setHasOptionsMenu(true);

                swipeRefreshLayout = view.findViewById(R.id.swipe);
                swipeRefreshLayout.setRefreshing(false);
                firstclick = true;

//                final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                recycler = view.findViewById(R.id.recycler);
                txtframmelayout = view.findViewById(R.id.framelayout);
                txtviewstatus = view.findViewById(R.id.viewstatus);
                txtpricedrop = view.findViewById(R.id.pricedrop);
                dataAdapter = new ArrayList<>();
                isi = new Pricedrop.DataAdapter();
                for (int i = 0; i < 10; i++)
                    dataAdapter.add(isi);

loaddata();

                dataAdapter1 = new ArrayList<>();
                txtviewstatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (!InternetAccess.isConnected(getContext()))
                                InternetAccess.buildDialog(getContext()).show();
                            else {
                                txtpricedrop.setVisibility(View.INVISIBLE);
                                hideKeyboard(getActivity());
loaddata1();
                            }
                        } catch (Exception e) {
                        }
                    }
                });


                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        if(txtpricedrop.getVisibility()==view.VISIBLE) {
//                            dataAdapter.clear();
     swipeRefreshLayout.setRefreshing(false);
     loaddata();
                        }

                        if(txtpricedrop.getVisibility()==view.INVISIBLE) {
                            dataAdapter1.clear();
                            swipeRefreshLayout.setRefreshing(false);
loaddata1();
                        }
                    }

                });

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recycler.setLayoutManager(layoutManager);

                Pricedrop.MyAdapter adapter = new Pricedrop.MyAdapter(getContext(), dataAdapter);
                recycler.setAdapter(adapter);



            }
        }catch (Exception e){}
        return view;
    }



    public static void hideKeyboard(Activity activity) {
        try{
            InputMethodManager inputManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocusedView = activity.getCurrentFocus();
            if (currentFocusedView != null) {
                inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void loaddata(){

        GetPriceDrop getPriceDrop = new GetPriceDrop(getContext(), new colorinterface() {
            @Override
            public void onTaskComplete(String result) {
                if (!result.isEmpty()) {
                    swipeRefreshLayout.setRefreshing(true);
                    try {
                        if (!InternetAccess.isConnected(getContext()))
                            InternetAccess.buildDialog(getContext()).show();
                        else {
                            txtviewstatus.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setRefreshing(false);

                            isi = new Pricedrop.DataAdapter();
                            for (int i = 0; i < 10; i++)
                                dataAdapter.add(isi);


                            Pricedrop.MyAdapter adapter = new Pricedrop.MyAdapter(getContext(), dataAdapter);
                            recycler.setAdapter(adapter);



                        }
                    } catch (Exception e) {
                    }
                }
            }
        });
        getPriceDrop.execute("register", retailername);

    }

    public void loaddata1(){

        GetPriceDrop getRetailerName = new GetPriceDrop(getContext(), new colorinterface() {
            @Override
            public void onTaskComplete(String result) {
                try {
                    if (!InternetAccess.isConnected(getContext()))
                        InternetAccess.buildDialog(getContext()).show();
                    else {
                        firstclick = true;
                        String s = result;
                        if (!s.isEmpty()) {
                            swipeRefreshLayout.setRefreshing(true);
                            String removedbrackets = s.replaceAll("\\[", "").replaceAll("\\]", "");

                            String[] de = removedbrackets.split(";");

                            if (firstclick) {
                                for (int i = 0; i < de.length; i++) {

                                    String g = de[i].replaceAll("^\"|\"$", "");
                                    String good = removeDoubleQuotes(g);
                                    String[] gg = good.split(",");
                                    Pricedrop.DataAdapter isi1;

                                    if (gg.length != 1) {
                                        if (gg[0].isEmpty()) {
                                            if (gg[4].equals("Accepted"))
                                                isi1 = new Pricedrop.DataAdapter(gg[1], gg[2], gg[3], gg[4]);


                                            else
                                                isi1 = new Pricedrop.DataAdapter(gg[1], gg[2], gg[3], gg[4], gg[5]);

                                            dataAdapter1.add(isi1);
                                        }
                                        if (!gg[0].isEmpty()) {
                                            if (gg[3].equals("Accepted"))
                                                isi1 = new Pricedrop.DataAdapter(gg[0], gg[1], gg[2], gg[3]);

                                            else
                                                isi1 = new Pricedrop.DataAdapter(gg[0], gg[1], gg[2], gg[3], gg[4]);

                                            dataAdapter1.add(isi1);
                                        }
                                    }
                                }
                                firstclick = false;
                            }

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recycler.setLayoutManager(layoutManager);

                            Pricedrop.MyAdapter1 adapter = new Pricedrop.MyAdapter1(getContext(), dataAdapter1);
                            recycler.setAdapter(adapter);
                            swipeRefreshLayout.setRefreshing(false);


                        } else {
                            txtviewstatus.setVisibility(View.INVISIBLE);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                } catch (Exception e) {
                }
            }
        });
        getRetailerName.execute("register", retailername);
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

    public class MyAdapter extends RecyclerView.Adapter<Pricedrop.MyAdapter.MyHolder>{
        Context context;

        List<Pricedrop.DataAdapter> data;


        public MyAdapter(Context context, List<Pricedrop.DataAdapter> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public Pricedrop.MyAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.rowdatapricedrop, parent, false);

            return new Pricedrop.MyAdapter.MyHolder(v);
        }



        public void onBindViewHolder(Pricedrop.MyAdapter.MyHolder holder, int position) {
            holder.txtmodelno.setText(data.get(position).getModelno());
            holder.txtimei.setText(data.get(position).getImei());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        public class MyHolder extends RecyclerView.ViewHolder{

            EditText txtmodelno,txtimei;

            public MyHolder(View itemView) {
                super(itemView);
                txtmodelno = (EditText)itemView.findViewById(R.id.modelno);
                txtimei = (EditText)itemView.findViewById(R.id.imeino);

            }
        }

    }

    public class MyAdapter1 extends RecyclerView.Adapter<Pricedrop.MyAdapter1.MyHolder1>{
        Context context;

        List<Pricedrop.DataAdapter> data;


        public MyAdapter1(Context context, List<Pricedrop.DataAdapter> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public Pricedrop.MyAdapter1.MyHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.rowdataviewpricedrop, parent, false);

            return new Pricedrop.MyAdapter1.MyHolder1(v);
        }



        public void onBindViewHolder(Pricedrop.MyAdapter1.MyHolder1 holder, int position) {
            holder.txtmodelno.setText(data.get(position).getModelno());
            holder.txtimei.setText(data.get(position).getImei());
            holder.txtstatus.setText(data.get(position).getStatus());
            holder.txtdate.setText(data.get(position).getDate());


            if(holder.txtstatus.getText().toString().equals("accept"))
            holder.txtreply.setVisibility(View.INVISIBLE);
else {
                holder.txtreply.setVisibility(View.VISIBLE);
                holder.txtreply.setText(data.get(position).getReply());
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        public class MyHolder1 extends RecyclerView.ViewHolder{

            TextView txtdate,txtmodelno,txtimei,txtstatus,txtreply;

            public MyHolder1(View itemView) {
                super(itemView);
                txtdate = (TextView)itemView.findViewById(R.id.date);
                txtmodelno = (TextView)itemView.findViewById(R.id.modelno);
                txtimei = (TextView)itemView.findViewById(R.id.imeino);
                txtstatus = (TextView)itemView.findViewById(R.id.status);
                txtreply = (TextView) itemView.findViewById(R.id.reply);
            }
        }

    }
    public class DataAdapter{

        String Date,modelno,imei,status,reply;

        public String getDate() {
            return Date;
        }

        public void setDate(String date) {
            this.Date = date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }

        public DataAdapter(String modelno, String imei) {
            this.modelno = modelno;
            this.imei = imei;
        }

        public String getModelno() {
            return modelno;
        }

        public void setModelno(String modelno) {
            this.modelno = modelno;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public DataAdapter() {

        }

        public DataAdapter(String Date,String modelno, String imei,String status,String reply) {
         this.Date=Date;
            this.modelno = modelno;
            this.imei = imei;
            this.status=status;
            this.reply=reply;
        }

        public DataAdapter(String Date,String modelno, String imei,String status) {
            this.Date=Date;
            this.modelno = modelno;
            this.imei = imei;
            this.status=status;
        }
    }



}
