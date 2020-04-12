package com.example.krishnamobiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
TextView txtuser,txtheading;
String loginname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (!InternetAccess.isConnected(MainActivity.this)) InternetAccess.buildDialog(MainActivity.this).show();
            else {
                setContentView(R.layout.activity_main);
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                getSupportActionBar().setTitle("Krishna Distributors");
                CharSequence hh = getSupportActionBar().getTitle();

                String c = hh.toString();


                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.addDrawerListener(toggle);
                toggle.syncState();

                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

                View header = navigationView.getHeaderView(0);
                txtuser = (TextView) header.findViewById(R.id.user);
                Intent intent = getIntent();
                loginname = intent.getStringExtra("username");

                txtuser.setText("Welcome, " + loginname);

                FragmentManager fragmentManager = getSupportFragmentManager();
                SpecialSchemesFragment schemesFragment = new SpecialSchemesFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, schemesFragment).commit();
                schemesFragment.loginname = this.loginname;

                navigationView.setNavigationItemSelectedListener(this);
            }
        }catch(Exception ex) {

        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
         moveTaskToBack(true);
            System.exit(1);
            finish();
        }



    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_orders) {
            try {
                if (!InternetAccess.isConnected(MainActivity.this)) InternetAccess.buildDialog(MainActivity.this).show();
                else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    OrderFragment orderFragment = new OrderFragment();
                    orderFragment.loginname = this.loginname;
                    fragmentManager.beginTransaction().replace(R.id.content_frame, orderFragment).commit();
                    orderFragment.type = "Orders";

                    getSupportActionBar().setTitle("Make Order");
                }
            }catch(Exception e){

            }

        }
        else if (id == R.id.nav_payment) {
            try {
                if (!InternetAccess.isConnected(MainActivity.this)) InternetAccess.buildDialog(MainActivity.this).show();
                else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    OrderFragment orderFragment = new OrderFragment();
                    orderFragment.loginname = this.loginname;
                    fragmentManager.beginTransaction().replace(R.id.content_frame, orderFragment).commit();
                    orderFragment.type = "Payments";

                    getSupportActionBar().setTitle("Payments");
                }
            } catch (Exception e) {

            }

        }

        else if (id == R.id.viewtargets) {
            try {
                if (!InternetAccess.isConnected(MainActivity.this)) InternetAccess.buildDialog(MainActivity.this).show();
                else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    TargetFragment targetFragment = new TargetFragment();
                    targetFragment.loginname = this.loginname;
                    fragmentManager.beginTransaction().replace(R.id.content_frame, targetFragment).commit();
                    getSupportActionBar().setTitle("Targets");

                }
            } catch (Exception e) {

            }
        }

        else if (id == R.id.viewstock) {
            try {
                if (!InternetAccess.isConnected(MainActivity.this)) InternetAccess.buildDialog(MainActivity.this).show();
                else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    ViewStock viewStock = new ViewStock();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, viewStock).commit();

                    getSupportActionBar().setTitle("Stock");
                }
            } catch (Exception e) {

            }
        }
        else if (id == R.id.stockreport) {
            try {
                if (!InternetAccess.isConnected(MainActivity.this)) InternetAccess.buildDialog(MainActivity.this).show();
                else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    OrderFragment orderFragment = new OrderFragment();
                    orderFragment.loginname = this.loginname;
                    fragmentManager.beginTransaction().replace(R.id.content_frame, orderFragment).commit();
                    orderFragment.type = "RetailerStock";
                    getSupportActionBar().setTitle("Retailer Stock");
                }
            } catch (Exception e) {

            }
        }
        else if (id == R.id.pricedrop) {
            try {
                if (!InternetAccess.isConnected(MainActivity.this)) InternetAccess.buildDialog(MainActivity.this).show();
                else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    OrderFragment orderFragment = new OrderFragment();
                    orderFragment.loginname = this.loginname;
                    fragmentManager.beginTransaction().replace(R.id.content_frame, orderFragment).commit();

                    orderFragment.type = "PriceDrop";
                    getSupportActionBar().setTitle("Price Drop");
                }
            } catch (Exception e) {

            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





}
