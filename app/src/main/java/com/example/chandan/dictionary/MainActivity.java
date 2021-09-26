package com.example.chandan.dictionary;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AntonymsFragment
        .OnFragmentInteractionListener, SynonymsFragment.OnFragmentInteractionListener,
        MeaningFragment.OnFragmentInteractionListener, SentencesFragment
                .OnFragmentInteractionListener, SearchView.OnQueryTextListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    FragmentAdapter fragmentAdapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    String word;
    ConnectivityManager cm;
    NetworkInfo nf;
    static TextView emptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        viewPager = (ViewPager) findViewById(R.id.viewpagerid);
        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fm);
        Log.d("oncreate", "passing fragment references");


        fragmentAdapter.addFragment(new MeaningFragment());
        fragmentAdapter.addFragment(new SentencesFragment());
        fragmentAdapter.addFragment(new SynonymsFragment());
        fragmentAdapter.addFragment(new AntonymsFragment());
        cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        nf = cm.getActiveNetworkInfo();
        emptyView = (TextView) findViewById(R.id.empty_view);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (TextUtils.isEmpty(word)) {
                } else {
                    Log.d("wordis", word);
                    if (nf != null && nf.isConnected()) {
                        //emptyView.setVisibility(View.INVISIBLE);
                        if (position == 0) {
                            new MeaningFragment().connectToNetwork(word);
                        }
                        if (position == 1) {
                            new SentencesFragment().connectToNetwork(word);
                        }
                        if (position == 2) {
                            new SynonymsFragment().connectToNetwork(word);
                        }
                        if (position == 3) {
                            new AntonymsFragment().connectToNetwork(word);
                        }
                    } else {
                        emptyView.setText("NO NETWORK");
                        Toast.makeText(MainActivity.this, "NO NETWORK", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelected(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string
                .drawer_open, R.string.drawer_closed);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search) {

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        word = query;
        if (nf != null && nf.isConnected()) {

            new MeaningFragment().connectToNetwork(query);
            //new SentencesFragment().connectToNetwork(query);
        } else {
            Toast.makeText(MainActivity.this, "NO NETWORK", Toast.LENGTH_SHORT).show();
        }
        // new AntonymsFragment().connectToNetwork(query);
        // new SynonymsFragment().connectToNetwork(query);
        return true;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}

