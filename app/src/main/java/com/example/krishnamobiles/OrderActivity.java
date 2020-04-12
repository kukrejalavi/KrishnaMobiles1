package com.example.krishnamobiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderActivity extends AppCompatActivity {
    TextView txtretailername;
    MakeOrder makeOrder=new MakeOrder();
    SignatureFragment signatureFragment=new SignatureFragment();
    RetailerStock retailerStock=new RetailerStock();
    PaymentFragment paymentFragment=new PaymentFragment();
    Pricedrop priceDrop=new Pricedrop();
    String loggedinName,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (!InternetAccess.isConnected(this)) InternetAccess.buildDialog(this).show();
            else {
                setContentView(R.layout.activity_order);


                txtretailername = findViewById(R.id.retailername);

                Intent intent = getIntent();

                String receivedName = intent.getStringExtra("name");
                txtretailername.setText(receivedName);
                loggedinName = intent.getStringExtra("loginname");
                type = intent.getStringExtra("pagetype");
                if (type.equals("Orders")) {
                    try {
                        if (!InternetAccess.isConnected(this))
                            InternetAccess.buildDialog(this).show();
                        else {
                                    FragmentManager fragmentManager = getSupportFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.container, makeOrder).commit();
                        }
                    }catch(Exception e){}
                }
                if (type.equals("Payments")) {
                    try {
                        if (!InternetAccess.isConnected(this))
                            InternetAccess.buildDialog(this).show();
                        else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, paymentFragment).commit();
                    paymentFragment.retailername = txtretailername.getText().toString();
                    paymentFragment.loginname = loggedinName;
                        }
                    }catch(Exception e){}
                }

                if (type.equals("RetailerStock")) {
                    try {
                        if (!InternetAccess.isConnected(this))
                            InternetAccess.buildDialog(this).show();
                        else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, retailerStock).commit();
                    retailerStock.retailername = txtretailername.getText().toString();
                    retailerStock.loginname = loggedinName;
                }
            }catch(Exception e){}
                }

                if (type.equals("PriceDrop")) {
                    try {
                        if (!InternetAccess.isConnected(this))
                            InternetAccess.buildDialog(this).show();
                        else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, priceDrop).commit();
                    priceDrop.retailername = txtretailername.getText().toString();
                    priceDrop.loginname = loggedinName;
                }
    }catch(Exception e){}
                }
            }
        } catch (Exception e) {
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(type.equals("Orders")) {
            try {
                if (!InternetAccess.isConnected(this))
                    InternetAccess.buildDialog(this).show();
                else {
                    getMenuInflater().inflate(R.menu.main, menu);
                }
            }catch(Exception e) {
                }
            }


        if(type.equals("RetailerStock")) {
            try {
                if (!InternetAccess.isConnected(this))
                    InternetAccess.buildDialog(this).show();
                else {
            getMenuInflater().inflate(R.menu.main, menu);
                }
            }catch(Exception e) {
            }
        }

        if (type.equals("PriceDrop") && priceDrop.txtpricedrop.getVisibility()==View.VISIBLE) {
            try {
                if (!InternetAccess.isConnected(this))
                    InternetAccess.buildDialog(this).show();
                else {
                    getMenuInflater().inflate(R.menu.main, menu);
                }
            }catch(Exception e) {
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();


        if (id == R.id.save) {
            if (type.equals("Orders")) {
                try {
                    if (!InternetAccess.isConnected(this))
                        InternetAccess.buildDialog(this).show();
                    else {
                        makeorder();
                    }
                } catch (Exception e) {
                }
            }

            if (type.equals("RetailerStock")) {
                try {
                    if (!InternetAccess.isConnected(this))
                        InternetAccess.buildDialog(this).show();
                    else {
                        retailerstock();
                    }
                } catch (Exception e) {
                }
            }

            if (type.equals("PriceDrop") && priceDrop.txtpricedrop.getVisibility() == View.VISIBLE) {
                try {
                    if (!InternetAccess.isConnected(this))
                        InternetAccess.buildDialog(this).show();
                    else {

                        pricedrop();

                    }
                } catch (Exception e) {
                }
            }

            if (type.equals("PriceDrop") && priceDrop.txtpricedrop.getVisibility() == View.INVISIBLE) {
                try {
                    if (!InternetAccess.isConnected(this))
                        InternetAccess.buildDialog(this).show();
                    else {
                    }
                    responsepricedrop();
                } catch (Exception e) {
                }
            }
        }

        return true;
    }



   public void makeorder() {

       SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       String datetime = df.format(new Date());


       if (!makeOrder.m1.getText().toString().isEmpty()) {

           if (!makeOrder.c1.getSelectedItem().toString().isEmpty() && !makeOrder.p1.getText().toString().isEmpty() && makeOrder.q1.getText().toString().isEmpty()) {
               BackgroundMakeOrder backgroundWorker = new BackgroundMakeOrder(this);
               backgroundWorker.execute("register", txtretailername.getText().toString(),
                       makeOrder.m1.getText().toString(), "1",
                       makeOrder.c1.getSelectedItem().toString(), makeOrder.p1.getText().toString(),
                       makeOrder.r1.getText().toString(), datetime, makeOrder.txttotalamt.getText().toString(), loggedinName
               );
           } else {
               BackgroundMakeOrder backgroundWorker = new BackgroundMakeOrder(this);
               backgroundWorker.execute("register", txtretailername.getText().toString(),
                       makeOrder.m1.getText().toString(), makeOrder.q1.getText().toString(),
                       makeOrder.c1.getSelectedItem().toString(), makeOrder.p1.getText().toString(),
                       makeOrder.r1.getText().toString(), datetime, makeOrder.txttotalamt.getText().toString(), loggedinName
               );
           }
       }

       if (!makeOrder.m2.getText().toString().isEmpty()) {
           if (!makeOrder.c2.getSelectedItem().toString().isEmpty() && !makeOrder.p2.getText().toString().isEmpty() && makeOrder.q2.getText().toString().isEmpty()) {
               BackgroundMakeOrder backgroundWorker = new BackgroundMakeOrder(this);
               backgroundWorker.execute("register", txtretailername.getText().toString(),
                       makeOrder.m2.getText().toString(), "1",
                       makeOrder.c2.getSelectedItem().toString(), makeOrder.p2.getText().toString(),
                       makeOrder.r2.getText().toString(), datetime, makeOrder.txttotalamt.getText().toString(), loggedinName
               );
           } else {
               BackgroundMakeOrder backgroundWorker = new BackgroundMakeOrder(this);
               backgroundWorker.execute("register", txtretailername.getText().toString(),
                       makeOrder.m2.getText().toString(), makeOrder.q2.getText().toString(),
                       makeOrder.c2.getSelectedItem().toString(), makeOrder.p2.getText().toString(),
                       makeOrder.r2.getText().toString(), datetime, makeOrder.txttotalamt.getText().toString(), loggedinName
               );
           }
       }

       if (!makeOrder.m3.getText().toString().isEmpty()) {
           if (!makeOrder.c3.getSelectedItem().toString().isEmpty() && !makeOrder.p3.getText().toString().isEmpty() && makeOrder.q3.getText().toString().isEmpty()) {
               BackgroundMakeOrder backgroundWorker = new BackgroundMakeOrder(this);
               backgroundWorker.execute("register", txtretailername.getText().toString(),
                       makeOrder.m3.getText().toString(), "1",
                       makeOrder.c3.getSelectedItem().toString(), makeOrder.p3.getText().toString(),
                       makeOrder.r3.getText().toString(), datetime, makeOrder.txttotalamt.getText().toString(), loggedinName
               );
           } else {
               BackgroundMakeOrder backgroundWorker = new BackgroundMakeOrder(this);
               backgroundWorker.execute("register", txtretailername.getText().toString(),
                       makeOrder.m3.getText().toString(), makeOrder.q3.getText().toString(),
                       makeOrder.c3.getSelectedItem().toString(), makeOrder.p3.getText().toString(),
                       makeOrder.r3.getText().toString(), datetime, makeOrder.txttotalamt.getText().toString(), loggedinName
               );
           }
       }

       if (!makeOrder.m4.getText().toString().isEmpty()) {
           if (!makeOrder.c4.getSelectedItem().toString().isEmpty() && !makeOrder.p4.getText().toString().isEmpty() && makeOrder.q4.getText().toString().isEmpty()) {
               BackgroundMakeOrder backgroundWorker = new BackgroundMakeOrder(this);
               backgroundWorker.execute("register", txtretailername.getText().toString(),
                       makeOrder.m4.getText().toString(), "1",
                       makeOrder.c4.getSelectedItem().toString(), makeOrder.p4.getText().toString(),
                       makeOrder.r4.getText().toString(), datetime, makeOrder.txttotalamt.getText().toString(), loggedinName
               );
           } else {
               BackgroundMakeOrder backgroundWorker = new BackgroundMakeOrder(this);
               backgroundWorker.execute("register", txtretailername.getText().toString(),
                       makeOrder.m4.getText().toString(), makeOrder.q4.getText().toString(),
                       makeOrder.c4.getSelectedItem().toString(), makeOrder.p4.getText().toString(),
                       makeOrder.r4.getText().toString(), datetime, makeOrder.txttotalamt.getText().toString(), loggedinName
               );
           }
       }


       if (!makeOrder.m5.getText().toString().isEmpty()) {
           if (!makeOrder.c5.getSelectedItem().toString().isEmpty() && !makeOrder.p5.getText().toString().isEmpty() && makeOrder.q5.getText().toString().isEmpty()) {
               BackgroundMakeOrder backgroundWorker = new BackgroundMakeOrder(this);
               backgroundWorker.execute("register", txtretailername.getText().toString(),
                       makeOrder.m5.getText().toString(), "1",
                       makeOrder.c5.getSelectedItem().toString(), makeOrder.p5.getText().toString(),
                       makeOrder.r5.getText().toString(), datetime, makeOrder.txttotalamt.getText().toString(), loggedinName
               );
           } else {
               BackgroundMakeOrder backgroundWorker = new BackgroundMakeOrder(this);
               backgroundWorker.execute("register", txtretailername.getText().toString(),
                       makeOrder.m5.getText().toString(), makeOrder.q5.getText().toString(),
                       makeOrder.c5.getSelectedItem().toString(), makeOrder.p5.getText().toString(),
                       makeOrder.r5.getText().toString(), datetime, makeOrder.txttotalamt.getText().toString(), loggedinName
               );
           }
       }
       if (!makeOrder.m6.getText().toString().isEmpty()) {
           if (!makeOrder.c6.getSelectedItem().toString().isEmpty() && !makeOrder.p6.getText().toString().isEmpty() && makeOrder.q6.getText().toString().isEmpty()) {
               BackgroundMakeOrder backgroundWorker = new BackgroundMakeOrder(this);
               backgroundWorker.execute("register", txtretailername.getText().toString(),
                       makeOrder.m6.getText().toString(), "1",
                       makeOrder.c6.getSelectedItem().toString(), makeOrder.p6.getText().toString(),
                       makeOrder.r6.getText().toString(), datetime, makeOrder.txttotalamt.getText().toString(), loggedinName
               );
           } else {
               BackgroundMakeOrder backgroundWorker = new BackgroundMakeOrder(this);
               backgroundWorker.execute("register", txtretailername.getText().toString(),
                       makeOrder.m6.getText().toString(), makeOrder.q6.getText().toString(),
                       makeOrder.c6.getSelectedItem().toString(), makeOrder.p6.getText().toString(),
                       makeOrder.r6.getText().toString(), datetime, makeOrder.txttotalamt.getText().toString(), loggedinName
               );
           }
       }


       if (!makeOrder.m7.getText().toString().isEmpty()) {
           if (!makeOrder.c7.getSelectedItem().toString().isEmpty() && !makeOrder.p7.getText().toString().isEmpty() && makeOrder.q7.getText().toString().isEmpty()) {
               BackgroundMakeOrder backgroundWorker = new BackgroundMakeOrder(this);
               backgroundWorker.execute("register", txtretailername.getText().toString(),
                       makeOrder.m7.getText().toString(), "1",
                       makeOrder.c7.getSelectedItem().toString(), makeOrder.p7.getText().toString(),
                       makeOrder.r7.getText().toString(), datetime, makeOrder.txttotalamt.getText().toString(), loggedinName
               );
           } else {
               BackgroundMakeOrder backgroundWorker = new BackgroundMakeOrder(this);
               backgroundWorker.execute("register", txtretailername.getText().toString(),
                       makeOrder.m7.getText().toString(), "1",
                       makeOrder.c7.getSelectedItem().toString(), makeOrder.p7.getText().toString(),
                       makeOrder.r7.getText().toString(), datetime, makeOrder.txttotalamt.getText().toString(), loggedinName
               );
           }
       }

       if (!makeOrder.m8.getText().toString().isEmpty()) {
           if (!makeOrder.c8.getSelectedItem().toString().isEmpty() && !makeOrder.p8.getText().toString().isEmpty() && makeOrder.q8.getText().toString().isEmpty()) {
           BackgroundMakeOrder backgroundWorker = new BackgroundMakeOrder(this);
           backgroundWorker.execute("register", txtretailername.getText().toString(),
                   makeOrder.m8.getText().toString(),"1",
                   makeOrder.c8.getSelectedItem().toString(), makeOrder.p8.getText().toString(),
                   makeOrder.r8.getText().toString(), datetime, makeOrder.txttotalamt.getText().toString(), loggedinName
           );
       }
       else {
           BackgroundMakeOrder backgroundWorker = new BackgroundMakeOrder(this);
           backgroundWorker.execute("register", txtretailername.getText().toString(),
                   makeOrder.m8.getText().toString(), makeOrder.q8.getText().toString(),
                   makeOrder.c8.getSelectedItem().toString(), makeOrder.p8.getText().toString(),
                   makeOrder.r8.getText().toString(), datetime, makeOrder.txttotalamt.getText().toString(), loggedinName
           );


       }
   }

       if (!makeOrder.m9.getText().toString().isEmpty()) {
           if (!makeOrder.c9.getSelectedItem().toString().isEmpty() && !makeOrder.p9.getText().toString().isEmpty() && makeOrder.q9.getText().toString().isEmpty()) {
               BackgroundMakeOrder backgroundWorker = new BackgroundMakeOrder(this);
               backgroundWorker.execute("register", txtretailername.getText().toString(),
                       makeOrder.m9.getText().toString(), makeOrder.q9.getText().toString(),
                       makeOrder.c9.getSelectedItem().toString(), makeOrder.p9.getText().toString(),
                       makeOrder.r9.getText().toString(), datetime, makeOrder.txttotalamt.getText().toString(), loggedinName
               );
           }
           else
           {
               BackgroundMakeOrder backgroundWorker = new BackgroundMakeOrder(this);
               backgroundWorker.execute("register", txtretailername.getText().toString(),
                       makeOrder.m9.getText().toString(), makeOrder.q9.getText().toString(),
                       makeOrder.c9.getSelectedItem().toString(), makeOrder.p9.getText().toString(),
                       makeOrder.r9.getText().toString(), datetime, makeOrder.txttotalamt.getText().toString(), loggedinName
               );
           }
       }

       if (!makeOrder.m10.getText().toString().isEmpty()) {
           if (!makeOrder.c10.getSelectedItem().toString().isEmpty() && !makeOrder.p10.getText().toString().isEmpty() && makeOrder.q10.getText().toString().isEmpty()) {
               BackgroundMakeOrder backgroundWorker = new BackgroundMakeOrder(this);
               backgroundWorker.execute("register", txtretailername.getText().toString(),
                       makeOrder.m10.getText().toString(), "1",
                       makeOrder.c10.getSelectedItem().toString(), makeOrder.p10.getText().toString(),
                       makeOrder.r10.getText().toString(), datetime, makeOrder.txttotalamt.getText().toString(), loggedinName
               );
           }
           else
           {
               BackgroundMakeOrder backgroundWorker = new BackgroundMakeOrder(this);
               backgroundWorker.execute("register", txtretailername.getText().toString(),
                       makeOrder.m10.getText().toString(), makeOrder.q10.getText().toString(),
                       makeOrder.c10.getSelectedItem().toString(), makeOrder.p10.getText().toString(),
                       makeOrder.r10.getText().toString(), datetime, makeOrder.txttotalamt.getText().toString(), loggedinName
               );
           }
       }
ClearFileds();

   }



   public void retailerstock()
   {

       SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       String newdate=df.format(new Date());



       if (retailerStock.recycler.getChildCount() > 0) {
           for (int i = 0; i < retailerStock.recycler.getChildCount(); i++) {
               if (retailerStock.recycler.findViewHolderForLayoutPosition(i) instanceof RetailerStock.MyAdapter.MyHolder) {

                   RetailerStock.MyAdapter.MyHolder holder = (RetailerStock.MyAdapter.MyHolder) retailerStock.recycler.findViewHolderForLayoutPosition(i);
                   if (!holder.txtmodelno.getText().toString().isEmpty() && !holder.txtqty.getText().toString().isEmpty() &&
                           !holder.txtclr.getText().toString().isEmpty()) {
                       BackgroundRetailerStock backgroundRetailerStock = new BackgroundRetailerStock(this);
                       backgroundRetailerStock.execute("register", newdate,
                               txtretailername.getText().toString(), loggedinName,
                               holder.txtmodelno.getText().toString(), holder.txtqty.getText().toString(), holder.txtclr.getText().toString());
                   }
               }
           }
       }


       for (int i = 0; i < retailerStock.recycler.getChildCount(); i++) {
           if (retailerStock.recycler.findViewHolderForLayoutPosition(i) instanceof RetailerStock.MyAdapter.MyHolder) {

               RetailerStock.MyAdapter.MyHolder holder = (RetailerStock.MyAdapter.MyHolder) retailerStock.recycler.findViewHolderForLayoutPosition(i);
               if (!holder.txtmodelno.getText().toString().isEmpty() && !holder.txtqty.getText().toString().isEmpty() &&
                       !holder.txtclr.getText().toString().isEmpty()) {
                   holder.txtmodelno.setText("");
                   holder.txtqty.setText("");
                   holder.txtclr.setText("");
               }
           }
       }
   }


   public void pricedrop()
   {

       SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       String newdate=df.format(new Date());


       if (priceDrop.recycler.getChildCount() > 0) {
           for (int i = 0; i < priceDrop.recycler.getChildCount(); i++) {
               if (priceDrop.recycler.findViewHolderForLayoutPosition(i) instanceof Pricedrop.MyAdapter.MyHolder) {

                   Pricedrop.MyAdapter.MyHolder holder = (Pricedrop.MyAdapter.MyHolder) priceDrop.recycler.findViewHolderForLayoutPosition(i);
                   if (!holder.txtmodelno.getText().toString().isEmpty() && !holder.txtimei.getText().toString().isEmpty()) {
                       BackgroundPricedrop backgroundPricedrop = new BackgroundPricedrop(this);
                       backgroundPricedrop.execute("register", newdate,
                               txtretailername.getText().toString(), loggedinName,
                               holder.txtmodelno.getText().toString(), holder.txtimei.getText().toString());
                   }

               }

           }
       }

       for (int i = 0; i < priceDrop.recycler.getChildCount(); i++) {
           if (priceDrop.recycler.findViewHolderForLayoutPosition(i) instanceof Pricedrop.MyAdapter.MyHolder) {

               Pricedrop.MyAdapter.MyHolder holder = (Pricedrop.MyAdapter.MyHolder) priceDrop.recycler.findViewHolderForLayoutPosition(i);
              holder.txtimei.setText("");
               holder.txtmodelno.setText("");
           }

       }


   }


    public void responsepricedrop()
    {
        if (priceDrop.recycler.getChildCount() > 0) {
            for (int i = 0; i < priceDrop.recycler.getChildCount(); i++) {
                if (priceDrop.recycler.findViewHolderForLayoutPosition(i) instanceof Pricedrop.MyAdapter1.MyHolder1) {

                    Pricedrop.MyAdapter1.MyHolder1 holder = (Pricedrop.MyAdapter1.MyHolder1) priceDrop.recycler.findViewHolderForLayoutPosition(i);
                    if (!holder.txtmodelno.getText().toString().isEmpty() && !holder.txtimei.getText().toString().isEmpty()&& !holder.txtreply.getText().toString().isEmpty()) {
                        BackgroundReplyPriceDrop backgroundPricedrop = new BackgroundReplyPriceDrop(this);
                        backgroundPricedrop.execute("register",holder.txtdate.getText().toString(),holder.txtmodelno.getText().toString(),
                                holder.txtimei.getText().toString(),holder.txtstatus.getText().toString(),holder.txtreply.getText().toString());
                    }
                }
            }
        }
    }

    public void ClearFileds()
    {
if(!makeOrder.txttotalamt.getText().toString().isEmpty())
        makeOrder.txttotalamt.setText("");

if(!makeOrder.m1.getText().toString().isEmpty())
        makeOrder.m1.setText("");

        if(!makeOrder.m2.getText().toString().isEmpty())
        makeOrder.m2.setText("");

        if(!makeOrder.m3.getText().toString().isEmpty())
        makeOrder.m3.setText("");

        if(!makeOrder.m4.getText().toString().isEmpty())
        makeOrder.m4.setText("");

        if(!makeOrder.m5.getText().toString().isEmpty())
        makeOrder.m5.setText("");

        if(!makeOrder.m6.getText().toString().isEmpty())
        makeOrder.m6.setText("");

        if(!makeOrder.m7.getText().toString().isEmpty())
        makeOrder.m7.setText("");

        if(!makeOrder.m8.getText().toString().isEmpty())
        makeOrder.m8.setText("");

        if(!makeOrder.m9.getText().toString().isEmpty())
        makeOrder.m9.setText("");

        if(!makeOrder.m10.getText().toString().isEmpty())
        makeOrder.m10.setText("");



        if(!makeOrder.q1.getText().toString().isEmpty())
        makeOrder.q1.setText("");

        if(!makeOrder.q2.getText().toString().isEmpty())
        makeOrder.q2.setText("");

        if(!makeOrder.q3.getText().toString().isEmpty())
        makeOrder.q3.setText("");

        if(!makeOrder.q4.getText().toString().isEmpty())
        makeOrder.q4.setText("");

        if(!makeOrder.q5.getText().toString().isEmpty())
        makeOrder.q5.setText("");

        if(!makeOrder.q6.getText().toString().isEmpty())
        makeOrder.q6.setText("");

        if(!makeOrder.q7.getText().toString().isEmpty())
        makeOrder.q7.setText("");

        if(!makeOrder.q8.getText().toString().isEmpty())
        makeOrder.q8.setText("");

        if(!makeOrder.q9.getText().toString().isEmpty())
        makeOrder.q9.setText("");

        if(!makeOrder.q10.getText().toString().isEmpty())
        makeOrder.q10.setText("");


        if(!makeOrder.p1.getText().toString().isEmpty())
        makeOrder.p1.setText("");

        if(!makeOrder.p2.getText().toString().isEmpty())
        makeOrder.p2.setText("");

        if(!makeOrder.p3.getText().toString().isEmpty())
        makeOrder.p3.setText("");

        if(!makeOrder.p4.getText().toString().isEmpty())
        makeOrder.p4.setText("");

        if(!makeOrder.p5.getText().toString().isEmpty())
        makeOrder.p5.setText("");

        if(!makeOrder.p6.getText().toString().isEmpty())
        makeOrder.p6.setText("");

        if(!makeOrder.p7.getText().toString().isEmpty())
        makeOrder.p7.setText("");

        if(!makeOrder.p8.getText().toString().isEmpty())
        makeOrder.p8.setText("");

        if(!makeOrder.p9.getText().toString().isEmpty())
        makeOrder.p9.setText("");

        if(!makeOrder.p10.getText().toString().isEmpty())
        makeOrder.p10.setText("");



        if(!makeOrder.r1.getText().toString().isEmpty())
        makeOrder.r1.setText("");

        if(!makeOrder.r2.getText().toString().isEmpty())
        makeOrder.r2.setText("");

        if(!makeOrder.r3.getText().toString().isEmpty())
        makeOrder.r3.setText("");

        if(!makeOrder.r4.getText().toString().isEmpty())
        makeOrder.r4.setText("");

        if(!makeOrder.r5.getText().toString().isEmpty())
        makeOrder.r5.setText("");

        if(!makeOrder.r6.getText().toString().isEmpty())
        makeOrder.r6.setText("");

        if(!makeOrder.r7.getText().toString().isEmpty())
        makeOrder.r7.setText("");

        if(!makeOrder.r8.getText().toString().isEmpty())
        makeOrder.r8.setText("");

        if(!makeOrder.r9.getText().toString().isEmpty())
        makeOrder.r9.setText("");

        if(!makeOrder.r10.getText().toString().isEmpty())
        makeOrder.r10.setText("");





    }
}
