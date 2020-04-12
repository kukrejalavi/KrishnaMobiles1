package com.example.krishnamobiles;

import android.content.Context;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class TargetFragment extends Fragment implements SearchView.OnQueryTextListener{

    RecyclerView recycler;
    DataAdapter isi;
    String result;
    String loginname;
    ArrayList<DataAdapter> dataAdapter;
    CustomAdapter adapter;
    View view;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            if (!InternetAccess.isConnected(getContext()))InternetAccess.buildDialog(getContext()).show();
            else {
                view = inflater.inflate(R.layout.fragment_target, container, false);
                recycler = view.findViewById(R.id.recycler);
                dataAdapter = new ArrayList<>();
                setHasOptionsMenu(true);
                swipeRefreshLayout = view.findViewById(R.id.swipe);
                swipeRefreshLayout.setRefreshing(false);
              loaddata();

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


                adapter = new CustomAdapter(getContext(), dataAdapter);
                recycler.setAdapter(adapter);

            }


        } catch (Exception e) {

        }
        return view;
    }
    public void loaddata()
    {

        Getsalesmanandtargets getsalesmanandtargets = new Getsalesmanandtargets(getContext(), new colorinterface() {
            @Override
            public void onTaskComplete(String result) {
                if (!result.isEmpty()) {

                    String s = result;
                    swipeRefreshLayout.setRefreshing(true);
                    String removedbrackets = s.replaceAll("\\[", "").replaceAll("\\]", "");

                    String[] s1 = removedbrackets.split(";");


                    for (int i = 0; i < s1.length; i++) {
                        String h = s1[i];
                        String[] ppp = h.split(";");

                        if (ppp.length == 1) {


                            String[] pp = ppp[0].split(",");

                            if (pp.length == 7) {
                                pp[0] = removeDoubleQuotes(pp[0]);
                                pp[1] = removeDoubleQuotes(pp[1]);
                                pp[2] = removeDoubleQuotes(pp[2]);
                                pp[3] = removeDoubleQuotes(pp[3]);
                                pp[4] = removeDoubleQuotes(pp[4]);
                                pp[5] = removeDoubleQuotes(pp[5]);
                                pp[6] = removeDoubleQuotes(pp[6]);

                                int l1 = Integer.parseInt(pp[1]) - Integer.parseInt(pp[4]);
                                int l2 = Integer.parseInt(pp[2]) - Integer.parseInt(pp[5]);
                                int l3 = Integer.parseInt(pp[3]) - Integer.parseInt(pp[6]);

                                isi = new DataAdapter(pp[0], pp[1], pp[2], pp[3], pp[4], pp[5], pp[6], l1 + "", l2 + "", l3 + "");

                                dataAdapter.add(isi);
                            }
                            if (pp.length == 8) {

                                pp[1] = removeDoubleQuotes(pp[1]);
                                pp[2] = removeDoubleQuotes(pp[2]);
                                pp[3] = removeDoubleQuotes(pp[3]);
                                pp[4] = removeDoubleQuotes(pp[4]);
                                pp[5] = removeDoubleQuotes(pp[5]);
                                pp[6] = removeDoubleQuotes(pp[6]);
                                pp[7] = removeDoubleQuotes(pp[7]);


                                int l1 = Integer.parseInt(pp[2]) - Integer.parseInt(pp[5]);
                                int l2 = Integer.parseInt(pp[3]) - Integer.parseInt(pp[6]);
                                int l3 = Integer.parseInt(pp[4]) - Integer.parseInt(pp[7]);

                                isi = new DataAdapter(pp[1], pp[2], pp[3], pp[4], pp[5], pp[6], pp[7], l1 + "", l2 + "", l3 + "");


                                dataAdapter.add(isi);
                            }

                        }

                    }


                    adapter = new CustomAdapter(getContext(), dataAdapter);
                    recycler.setAdapter(adapter);

                }

                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getsalesmanandtargets.execute("register", loginname);
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

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.getFilter().filter(s);
        return  false;
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.item_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(this);

        searchView.setQueryHint("Search");

        super.onCreateOptionsMenu(menu, inflater);

    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyHolder> {
        Context context;
        List<DataAdapter> data;
        List<DataAdapter> data1;
        public CustomAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.targetrowdata, parent, false);

            return new CustomAdapter.MyHolder(v);
        }


        public CustomAdapter(Context context, List<DataAdapter> data) {
            this.context = context;
            this.data = data;
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

        public void onBindViewHolder(CustomAdapter.MyHolder holder, int position) {
            holder.txtshopname.setText(data.get(position).getShopname());
            holder.txttarget1.setText(data.get(position).getTarget1());
            holder.txttarget2.setText(data.get(position).getTarget2());
            holder.txttarget3.setText(data.get(position).getTarget3());

            holder.txtdone1.setText(data.get(position).getDone1());
            holder.txtdone2.setText(data.get(position).getDone2());
            holder.txtdone3.setText(data.get(position).getDone3());

            holder.txtleftover1.setText(data.get(position).getLeftover1());
            holder.txtleftover2.setText(data.get(position).getLeftover2());
            holder.txtleftover3.setText(data.get(position).getLeftover3());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        public class MyHolder extends RecyclerView.ViewHolder  {

            TextView txtshopname, txttarget1, txttarget2, txttarget3, txtdone1, txtdone2, txtdone3, txtleftover1, txtleftover2, txtleftover3;
            View newview;


            public MyHolder(View itemView) {
                super(itemView);
                txtshopname = itemView.findViewById(R.id.shopname);

                txttarget1 = itemView.findViewById(R.id.target1);
                txttarget2 = itemView.findViewById(R.id.target2);
                txttarget3 = itemView.findViewById(R.id.target3);

                txtdone1 = itemView.findViewById(R.id.done1);
                txtdone2 = itemView.findViewById(R.id.done2);
                txtdone3 = itemView.findViewById(R.id.done3);

                txtleftover1 = itemView.findViewById(R.id.leftover1);
                txtleftover2= itemView.findViewById(R.id.leftover2);
                txtleftover3 = itemView.findViewById(R.id.leftover3);



            }


        }

    }

    public class DataAdapter {

        String shopname, target1, target2, target3, done1, done2, done3, leftover1, leftover2, leftover3;

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getTarget1() {
            return target1;
        }

        public void setTarget1(String target1) {
            this.target1 = target1;
        }

        public String getTarget2() {
            return target2;
        }

        public void setTarget2(String target2) {
            this.target2 = target2;
        }

        public String getTarget3() {
            return target3;
        }

        public void setTarget3(String target3) {
            this.target3 = target3;
        }

        public String getDone1() {
            return done1;
        }

        public void setDone1(String done1) {
            this.done1 = done1;
        }

        public String getDone2() {
            return done2;
        }

        public void setDone2(String done2) {
            this.done2 = done2;
        }

        public String getDone3() {
            return done3;
        }

        public void setDone3(String done3) {
            this.done3 = done3;
        }

        public String getLeftover1() {
            return leftover1;
        }

        public void setLeftover1(String leftover1) {
            this.leftover1 = leftover1;
        }

        public String getLeftover2() {
            return leftover2;
        }

        public void setLeftover2(String leftover2) {
            this.leftover2 = leftover2;
        }

        public String getLeftover3() {
            return leftover3;
        }

        public void setLeftover3(String leftover3) {
            this.leftover3 = leftover3;
        }


        public DataAdapter(String shopname,String target1,String target2,String target3, String done1,String done2,String done3,
                           String leftover1,String leftover2,String leftover3) {
            this.shopname=shopname;
            this.target1 = target1;
            this.target2 = target2;
            this.target3 = target3;

            this.done1 = done1;
            this.done2 = done2;
            this.done3 = done3;


            this.leftover1=leftover1;
            this.leftover2=leftover2;
            this.leftover3=leftover3;

        }


    }



}
