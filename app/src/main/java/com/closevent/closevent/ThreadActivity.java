package com.closevent.closevent;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Henri on 18/03/2016.
 */
public class ThreadActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
        }
        else if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.
            //ThreadFragment thread = new ThreadFragment();
            //thread.setArguments(getIntent().getExtras());
            //getSupportFragmentManager().beginTransaction().add(R.id.eventListContainer, thread).commit();
        }
    }
}
