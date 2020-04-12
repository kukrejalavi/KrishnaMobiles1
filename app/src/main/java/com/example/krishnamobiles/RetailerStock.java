package com.example.krishnamobiles;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class RetailerStock extends Fragment {

    RecyclerView recycler;
    List<DataAdapter> dataAdapter;
    String retailername;
    public static String loginname;
    View view;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            if (!InternetAccess.isConnected(getContext()))
                InternetAccess.buildDialog(getContext()).show();
            else {
                view = inflater.inflate(R.layout.fragment_retailer_stock, container, false);
                setHasOptionsMenu(true);
                swipeRefreshLayout = view.findViewById(R.id.swipe);
                swipeRefreshLayout.setRefreshing(false);

                recycler = view.findViewById(R.id.recycler);

                dataAdapter = new ArrayList<>();
                DataAdapter isi;

                isi = new DataAdapter();
                for (int i = 0; i < 10; i++)
                    dataAdapter.add(isi);

                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        dataAdapter.clear();
                        swipeRefreshLayout.setRefreshing(false);

                        for (int i = 0; i < 10; i++)
                            dataAdapter.add(isi);

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recycler.setLayoutManager(layoutManager);

                        MyAdapter adapter = new MyAdapter(getContext(), dataAdapter);
                        recycler.setAdapter(adapter);
                    }

                });

                swipeRefreshLayout.setRefreshing(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recycler.setLayoutManager(layoutManager);

                MyAdapter adapter = new MyAdapter(getContext(), dataAdapter);
                recycler.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);

            }
            swipeRefreshLayout.setRefreshing(false);
        }catch(Exception e){}

        return  view;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{
        Context context;

        List<DataAdapter> data;


        public MyAdapter(Context context, List<DataAdapter> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.rowdata1, parent, false);

            return new MyHolder(v);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.txtmodelno.setText(data.get(position).getmodelno());
            holder.txtqty.setText(data.get(position).getqty());
            holder.txtclr.setText(data.get(position).getclr());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        public class MyHolder extends RecyclerView.ViewHolder{

            EditText txtmodelno,txtqty,txtclr;

            public MyHolder(View itemView) {
                super(itemView);
                txtmodelno = (EditText)itemView.findViewById(R.id.modelno);
                txtqty = (EditText)itemView.findViewById(R.id.qty);
                txtclr = (EditText)itemView.findViewById(R.id.clr);
            }
        }

    }


    public class DataAdapter{

        String modelno,qty,clr;


        public DataAdapter(String modelno, String qty, String clr) {
            this.modelno = modelno;
            this.qty = qty;
            this.clr = clr;
        }


        public DataAdapter() {

        }

        public String getmodelno() {
            return modelno;
        }

        public void setmodelno(String modelno) {
            this.modelno = modelno;
        }

        public String getqty() {
            return qty;
        }

        public void setqty(String qty) {
            this.qty = qty;
        }

        public String getclr() {
            return clr;
        }

        public void setclr(String clr) {
            this.clr = clr;
        }
    }

}
