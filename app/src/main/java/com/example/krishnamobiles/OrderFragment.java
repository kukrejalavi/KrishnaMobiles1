package com.example.krishnamobiles;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class OrderFragment extends Fragment implements SearchView.OnQueryTextListener,RecyclerViewClickListener {
    RecyclerViewClickListener listener;
    String loginname;
    RecyclerView recycler;
    DataAdapter isi;
    String type;
    String result;
    ArrayList<DataAdapter> dataAdapter;
    OrderFragment.CustomAdapter adapter;

    View view;
    SwipeRefreshLayout swipeRefreshLayout;

    @TargetApi(Build.VERSION_CODES.O)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            if (!InternetAccess.isConnected(getContext()))
                InternetAccess.buildDialog(getContext()).show();
            else {
                view = getLayoutInflater().inflate(R.layout.fragment_order, container, false);
                setHasOptionsMenu(true);
                swipeRefreshLayout = view.findViewById(R.id.swipe);
                swipeRefreshLayout.setRefreshing(false);
                recycler = view.findViewById(R.id.recycler);
                dataAdapter = new ArrayList<>();

                loaddata();

                listener = (view, position) -> {
                    try {
                        if (!InternetAccess.isConnected(getContext()))
                            InternetAccess.buildDialog(getContext()).show();
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage("Do u want to make order?");

                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = new Intent(getContext(), OrderActivity.class);

                                    intent.putExtra("name", dataAdapter.get(position).getShopname());
                                    intent.putExtra("loginname", loginname);
                                    intent.putExtra("pagetype", type);
                                    startActivity(intent);
                                }
                            });



                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getContext(),SignatureFragment.class);

                                    intent.putExtra("name", dataAdapter.get(position).getShopname());
                                    intent.putExtra("loginname", loginname);
                                    intent.putExtra("pagetype", type);
                                    startActivity(intent);
                                }
                            });

                            builder.show();
                        }

                    } catch (Exception e) {
                    }
                };

                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        dataAdapter.clear();
                        swipeRefreshLayout.setRefreshing(false);
                        swipeRefreshLayout.clearAnimation();
                        loaddata();
                    }

                });


                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recycler.setLayoutManager(layoutManager);

                adapter = new CustomAdapter(getContext(), dataAdapter, listener);
                recycler.setAdapter(adapter);



            }
        }
catch (Exception e) {
        }
        return view;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void loaddata()
    {

            GetRetailerNames getRetailerNames = new GetRetailerNames(getContext(), new colorinterface() {
            @Override
            public void onTaskComplete(String result) {
                if (!result.isEmpty()) {
                    swipeRefreshLayout.setRefreshing(true);
                    String s = result;
                    String removedbrackets = s.replaceAll("\\[", "").replaceAll("\\]", "");
                    String removeddoublequotes = removeDoubleQuotes(removedbrackets);
                    String[] s1 = removeddoublequotes.split(";");

                    List<String> newstring = new ArrayList<String>();
                    for (int i = 0; i < s1.length; i++) {

                        newstring.add(s1[i]);
                    }

                    for (int i = 0; i < newstring.size(); i++) {
                        String[] ss = newstring.get(i).split(",");
                        if (!ss[0].equals(""))
                            isi = new DataAdapter(ss[0]);

                        if (ss[0].equals("") && !ss[1].isEmpty())
                            isi = new DataAdapter(ss[1]);

                        dataAdapter.add(isi);
                    }

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recycler.setLayoutManager(layoutManager);

                    adapter = new CustomAdapter(getContext(), dataAdapter, listener);
                    recycler.setAdapter(adapter);




                    swipeRefreshLayout.setRefreshing(false);
                    }
            }


        });

        getRetailerNames.execute("register", loginname);



    }



    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.item_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(this);

        searchView.setQueryHint("Search");


        super.onCreateOptionsMenu(menu, inflater);
    }


    public static String removeDoubleQuotes(String input) {

        StringBuilder sb = new StringBuilder();

        char[] tab = input.toCharArray();
        for (char current : tab) {
            if (current != '"')
                sb.append(current);
        }

        return sb.toString();
    }



    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    public boolean onQueryTextChange(String newText) {

return  false;

    }

    @Override
    public void onClick(View view, int position) {

    }



    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyHolder> {
        Context context;
        RecyclerViewClickListener listener;
        List<DataAdapter> data;
        List<DataAdapter> data1;

        public CustomAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.rowdata, parent, false);

            return new CustomAdapter.MyHolder(v, listener);
        }

        public CustomAdapter(Context context, List<DataAdapter> data, RecyclerViewClickListener listener) {
            this.context = context;
            this.data = data;
            data1 = new ArrayList<>(data);
            this.listener = listener;
        }
        private Filter exampleFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<DataAdapter> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(data1);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (DataAdapter item : data1) {
                        if (item.getShopname().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }


            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data.clear();
                data.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };




        public Filter getFilter() {
            return exampleFilter;
        }
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.txtshopname.setText(data.get(position).getShopname());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView txtshopname;
            RecyclerViewClickListener mListener;
            public MyHolder(View itemView, RecyclerViewClickListener mListener) {
                super(itemView);
                txtshopname = itemView.findViewById(R.id.shopname);
                this.mListener=mListener;
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                mListener.onClick(v, getAdapterPosition());
            }
        }

    }

    public class DataAdapter {

        String shopname;

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }


        public DataAdapter(String shopname) {
            this.shopname=shopname;

        }


    }
}



