package com.ssdev.saeedsina.lostfound.MyActivities;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserTokenStorageFactory;
import com.ssdev.saeedsina.lostfound.MyClasses.DrawerItem;
import com.ssdev.saeedsina.lostfound.MyClasses.Item;
import com.ssdev.saeedsina.lostfound.MyClasses.MyDrawerAdapter;
import com.ssdev.saeedsina.lostfound.MyFragments.AdListFragment;
import com.ssdev.saeedsina.lostfound.MyFragments.DetailFragment;
import com.ssdev.saeedsina.lostfound.MyFragments.LoginFragment;
import com.ssdev.saeedsina.lostfound.MyFragments.MapFragment;
import com.ssdev.saeedsina.lostfound.MyFragments.MenuFragment;
import com.ssdev.saeedsina.lostfound.MyFragments.PreferencesFragment;
import com.ssdev.saeedsina.lostfound.MyFragments.RegisterFragment;
import com.ssdev.saeedsina.lostfound.MyFragments.SubmitFoundFragment;
import com.ssdev.saeedsina.lostfound.MyFragments.SubmitLostFragment;
import com.ssdev.saeedsina.lostfound.MyInterfaces.OnMenuItemSelectedListener;
import com.ssdev.saeedsina.lostfound.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements OnMenuItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private ActionBar actionBar;
    private MyDrawerAdapter adapter;
    private CharSequence mTitle;
    private List<DrawerItem> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerTitle = getTitle();

        dataList = new ArrayList<>();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);



        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);


        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2B1C27")));
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.actionbar, null);
        actionBar.setCustomView(v);
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
            }
            String userToken = UserTokenStorageFactory.instance().getStorage().get();
            if( (userToken != null && !userToken.equals( "" )) || Backendless.UserService.isValidLogin() )
            {
                changeMenuItems("login");
                this.onMenuItemSelected("menu","splash",null);
            }
            else {
                changeMenuItems("logout");
                this.onMenuItemSelected("login","splash",null);
            }

        }
    }

    public void changeMenuItems(String str){
        dataList.clear();
        if(Objects.equals(str, "login")){
            dataList.add(new DrawerItem("Submit Lost", R.drawable.lost));
            dataList.add(new DrawerItem("Submit Found", R.drawable.found));
            dataList.add(new DrawerItem("AD List", R.drawable.list));
            dataList.add(new DrawerItem("Logout", R.drawable.logout));
            dataList.add(new DrawerItem("Exit", R.drawable.exit));
        }else if(Objects.equals(str, "logout")){
            dataList.add(new DrawerItem("Register", R.drawable.logout));
            dataList.add(new DrawerItem("Login", R.drawable.logout));
            dataList.add(new DrawerItem("Exit", R.drawable.exit));
        }
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
        adapter = new MyDrawerAdapter(this, R.layout.drawer_list_item,
                dataList);
        mDrawerList.setAdapter(adapter);

    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onMenuItemSelected(String toFrag,String fromFrag,Item item) {
        switch (toFrag){
            case "register":
                RegisterFragment registerFragment = new RegisterFragment();
                registerFragment.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, registerFragment,"register").commit();
                break;

            case "login":
                if(fromFrag.equals("menu")){
                    changeMenuItems("logout");
                }
                LoginFragment loginFragment = new LoginFragment();
                loginFragment.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, loginFragment,"login").commit();
                break;
            case "menu":
                changeMenuItems("login");
                MenuFragment menuFragment = new MenuFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, menuFragment,"menu").commit();
                break;

            case "found":
                SubmitFoundFragment foundFragment = new SubmitFoundFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, foundFragment,"found").commit();
                break;
            case "lost":
                SubmitLostFragment lostFragment = new SubmitLostFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, lostFragment,"lost").commit();
                break;
            case "pref":
                PreferencesFragment preferencesFragment = new PreferencesFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, preferencesFragment,"pref").commit();
                break;
            case "about":

                break;
            case "myaccount":

                break;
            case "location":
                MapFragment mapfragment = new MapFragment();
                Bundle bundle=new Bundle();
                bundle.putString("message",fromFrag);
                mapfragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, mapfragment,"location").commit();
                break;
            case "adlist":
                AdListFragment adListFragment = new AdListFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, adListFragment,"adlist").commit();
                break;
            case "detail":
                DetailFragment detailFragment = new DetailFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, detailFragment,"detail").commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(mDrawerList)){
            mDrawerLayout.closeDrawer(mDrawerList);
        }
        else{
            finishAffinity();
        }
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if(fragments.size() == 0){
            finish();
        }
        Fragment f = (fragments.get(0) == null ? fragments.get(1) : fragments.get(0));
        switch (f.getTag()){
            case "menu":
                finish();
                break;
            case "lost":
                this.onMenuItemSelected("menu","lost",null);
                break;
            case "found":
                this.onMenuItemSelected("menu","found",null);
                break;
            case "login":
                finish();
                break;
            case "register":
                finish();
                break;
            case "splash":
                finish();
                break;
            case "location":
                String whoCalled=fragments.get(0).getArguments().getString("message");
                if(whoCalled.equals("found")){
                    this.onMenuItemSelected("found","location",null);
                    break;
                }
                else if(whoCalled.equals("lost")){
                    this.onMenuItemSelected("lost","location",null);
                    break;
                }
                this.onMenuItemSelected("menu","location",null);
                break;
            case "pref":
                this.onMenuItemSelected("menu","pref",null);
                break;
            case "adlist":
                this.onMenuItemSelected("pref","adlist",null);
                break;
            case "detail":
                this.onMenuItemSelected("adlist","detail",null);
                break;

        }
    }
    public void setActionBarTitle(String title){
        TextView tx= (TextView) actionBar.getCustomView().findViewById(R.id.action_bar_title);
        tx.setText(title);
    }

    private class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            selectItem(position);
        }

        private void selectItem(int position) {
            DrawerItem d = dataList.get(position);
            switch (d.getItemName()){
                case "Submit Lost":
                    SubmitLostFragment lostFragment = new SubmitLostFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, lostFragment,"lost").commit();
                    mDrawerLayout.closeDrawer(mDrawerList);
                    break;
                case "Submit Found":
                    SubmitFoundFragment foundFragment = new SubmitFoundFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, foundFragment,"found").commit();
                    mDrawerLayout.closeDrawer(mDrawerList);
                    break;
                case "AD List":
                    PreferencesFragment preferencesFragment = new PreferencesFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, preferencesFragment,"pref").commit();
                    mDrawerLayout.closeDrawer(mDrawerList);
                    break;
                case "Register":
                    RegisterFragment registerFragment = new RegisterFragment();
                    registerFragment.setArguments(getIntent().getExtras());
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, registerFragment,"register").commit();
                    mDrawerLayout.closeDrawer(mDrawerList);
                    break;
                case "Login":
                    LoginFragment loginFragment = new LoginFragment();
                    loginFragment.setArguments(getIntent().getExtras());
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, loginFragment,"login").commit();
                    mDrawerLayout.closeDrawer(mDrawerList);
                    break;
                case "Logout":
                    mDrawerLayout.closeDrawer(mDrawerList);
                    changeMenuItems("logout");
                    if(Backendless.UserService.loggedInUser() != null){
                        Backendless.UserService.logout(new AsyncCallback<Void>() {
                            @Override
                            public void handleResponse(Void response) {
                                LoginFragment loginFragment = new LoginFragment();
                                loginFragment.setArguments(getIntent().getExtras());
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, loginFragment,"login").commit();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    break;
                case "Exit":
                    finishAffinity();
            }
        }

    }
}
