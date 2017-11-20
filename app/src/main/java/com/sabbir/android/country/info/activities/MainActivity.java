package com.sabbir.android.country.info.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.sabbir.android.country.info.R;
import com.sabbir.android.country.info.constans.AppConstants;
import com.sabbir.android.country.info.fragments.AllCountryFragment;
import com.sabbir.android.country.info.models.Country;
import com.sabbir.android.country.info.tasks.GetAllCountryTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements GetAllCountryTask.GetAllCountryTaskListener {

            ArrayList<Country> countryArrayList;
    BottomNavigationView navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Fragment fragment= AllCountryFragment.newInstance(countryArrayList);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };


    @Override
    protected int getResourceLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {

        GetAllCountryTask getAllCountryTask=new GetAllCountryTask(this,this);
        getAllCountryTask.execute(AppConstants.ALL_COUNTRY_URL);
       // getAllCountryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    @Override
    public void receivedCountryList(List<Country> countries) {
        countryArrayList= (ArrayList<Country>) countries;
        navigation.setSelectedItemId(R.id.navigation_home);
    }
}
