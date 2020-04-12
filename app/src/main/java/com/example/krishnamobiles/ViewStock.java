package com.example.krishnamobiles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewStock extends Fragment implements SearchView.OnQueryTextListener {

    RecyclerView recycler;
    ArrayList<ViewStock.DataAdapter> dataAdapter;
    ViewStock.DataAdapter isi;
    ViewStock.MyAdapter adapter;
    TextView txtfilescount;
    LinearLayout linearLayout;
    View view;
    SwipeRefreshLayout swipeRefreshLayout;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            if (!InternetAccess.isConnected(getContext()))
                InternetAccess.buildDialog(getContext()).show();
            else {
                view = inflater.inflate(R.layout.fragment_view_stock, container, false);
                setHasOptionsMenu(true);

                swipeRefreshLayout = view.findViewById(R.id.swipe);
                swipeRefreshLayout.setRefreshing(false);
                recycler = view.findViewById(R.id.recycler);
                txtfilescount=view.findViewById(R.id.filescount);
                linearLayout=view.findViewById(R.id.fileslinearlayout);
                dataAdapter = new ArrayList<>();


                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!txtfilescount.getText().toString().isEmpty()){
                            startActivity(new Intent(getContext(),ViewFiles.class));
                        }
                    }
                });


                loaddata();
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        dataAdapter.clear();
                        swipeRefreshLayout.setRefreshing(false);
                        loaddata();
                    }

                });
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recycler.setLayoutManager(layoutManager);

                adapter = new ViewStock.MyAdapter(getContext(), dataAdapter);
                recycler.setAdapter(adapter);

            }
        } catch (Exception e) {
        }
        return view;
    }


    public void loaddata(){

        GetAvailableStock getAvailableStock = new GetAvailableStock(getContext(), new colorinterface() {
            @Override
            public void onTaskComplete(String result) {
                if (!result.isEmpty()) {
                    swipeRefreshLayout.setRefreshing(true);
                    String s = result;

                    String[] s1 = s.split(":");
                    List<String> newstring = new ArrayList<String>();
                    for (int i = 0; i < s1.length; i++) {
                        newstring.add(s1[i]);
                    }

                    for (int i = 0; i < newstring.size(); i++) {
                        String[] ss = newstring.get(i).split(",");
                        isi = new ViewStock.DataAdapter(ss[0], ss[1], ss[2]);
                        dataAdapter.add(isi);
                    }

                    adapter = new ViewStock.MyAdapter(getContext(), dataAdapter);
                    recycler.setAdapter(adapter);

                    swipeRefreshLayout.setRefreshing(false);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getAvailableStock.execute("register");



        GetUploadedFilesCount getUploadedFilesCount=new GetUploadedFilesCount(getContext(), new colorinterface() {
            @Override
            public void onTaskComplete(String result) {
                    if(!result.isEmpty()){
                        swipeRefreshLayout.setRefreshing(true);
                        String removedbrackets = result.replaceAll("\\[", "").replaceAll("\\]", "");

                        txtfilescount.setText(removedbrackets);

                    }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getUploadedFilesCount.execute("register");

    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.item_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(this);

        searchView.setQueryHint("Search");

        super.onCreateOptionsMenu(menu, inflater);

    }



    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.getFilter().filter(s);
        return true;
    }

    public class MyAdapter extends RecyclerView.Adapter<ViewStock.MyAdapter.MyHolder> implements Filterable {
        Context context;

        ArrayList<DataAdapter> mArrayList;
        ArrayList<DataAdapter> mFilteredList;
    public MyAdapter(Context context,    ArrayList<ViewStock.DataAdapter> mArrayList) {
        this.context = context;
        this.mArrayList = mArrayList;
        mFilteredList = mArrayList;
    }

        public Filter getFilter() {
            return new Filter(){

                protected FilterResults performFiltering(CharSequence charSequence) {
   String charString=charSequence.toString();
       if(charString.isEmpty()){
    mFilteredList=mArrayList;
}
       else
{
    ArrayList<DataAdapter> filteredList = new ArrayList<>();


    for(DataAdapter dataAdapter:mArrayList)
    {
        if(dataAdapter.getModelno().toLowerCase().contains(charString) || dataAdapter.getColor().toLowerCase().contains(charString)
        || dataAdapter.getQty().toLowerCase().contains(charString))
            filteredList.add(dataAdapter);
    }
    mFilteredList = filteredList;
}
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = mFilteredList;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    mFilteredList = (ArrayList<DataAdapter>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }




        @Override
        public ViewStock.MyAdapter. MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.rowdata_availablestock, parent, false);

            return new ViewStock.MyAdapter.MyHolder(v);
        }



        public void onBindViewHolder(ViewStock.MyAdapter.MyHolder holder, int position) {
            holder.txtmodelno.setText(mFilteredList.get(position).getModelno());
            holder.txtqty.setText(mFilteredList.get(position).getQty());
            holder.txtcolor.setText(mFilteredList.get(position).getColor());
        }

        @Override
        public int getItemCount() {
            return mFilteredList.size();
        }


        public class MyHolder extends RecyclerView.ViewHolder{

            TextView txtmodelno,txtqty,txtcolor;

            public MyHolder(View itemView) {
                super(itemView);
                txtmodelno = (TextView)itemView.findViewById(R.id.modelno);
                txtqty = (TextView)itemView.findViewById(R.id.qty);
                txtcolor=(TextView)itemView.findViewById(R.id.clr);

            }
        }

    }
    public class DataAdapter{

        String modelno,color,qty;

        public String getModelno() {
            return modelno;
        }

        public void setModelno(String modelno) {
            this.modelno = modelno;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public DataAdapter(String modelno,String color,String qty) {
this.setModelno(modelno);
            this.setQty(qty);
            this.setColor(color);
        }


    }
}
