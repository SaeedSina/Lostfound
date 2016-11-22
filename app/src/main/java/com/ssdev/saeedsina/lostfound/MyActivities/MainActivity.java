package com.ssdev.saeedsina.lostfound.MyActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ssdev.saeedsina.lostfound.MyFragments.LoginFragment;
import com.ssdev.saeedsina.lostfound.MyFragments.RegisterFragment;
import com.ssdev.saeedsina.lostfound.MyInterfaces.OnMenuItemSelectedListener;
import com.ssdev.saeedsina.lostfound.R;


public class MainActivity extends AppCompatActivity implements OnMenuItemSelectedListener {
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            LoginFragment firstFragment = new LoginFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }

    }

    @Override
    public void onMenuItemSelected(String frag) {
        switch (frag){
            case "register":
                RegisterFragment registerFragment = new RegisterFragment();

                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                registerFragment.setArguments(getIntent().getExtras());

                // Add the fragment to the 'fragment_container' FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, registerFragment).commit();
                break;

            case "login":
                LoginFragment loginFragment = new LoginFragment();

                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                loginFragment.setArguments(getIntent().getExtras());

                // Add the fragment to the 'fragment_container' FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, loginFragment).commit();
                break;
            case "settings":

            case "pair":

        }
    }

}
