package com.loscache.firebirdone.gui;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.loscache.firebirdone.R;
import com.loscache.firebirdone.data.DataReaderDbContext;
import com.loscache.firebirdone.data.DataReaderDbHelper;

import static android.hardware.Sensor.TYPE_PROXIMITY;

public class InfoActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private SensorManager sensorManager;
    private GesturesListener gesturesListener;

    // Db Context
    private DataReaderDbContext dbContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

/*        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

       /* TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);*/
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gesturesListener = new GesturesListener((sensorManager.getDefaultSensor(TYPE_PROXIMITY)).getMaximumRange());

    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(gesturesListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(gesturesListener, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(gesturesListener, sensorManager.getDefaultSensor(TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
        dbContext = new DataReaderDbContext(new DataReaderDbHelper(getApplicationContext()));
    }

    @Override
    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(gesturesListener);
        dbContext.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // Return the current tab
            Fragment tab = null;
            //Bundle dbContextBundle = new Bundle();
            //dbContextBundle.putSerializable("dbcontext", dbContext);
            //dbContextBundle.putString("dbcontext", "hola");
            switch (position){
                case 0:
                    tab = new InfoFragment();
                    return tab;
                case 1:
                    tab = new HistoryFragment();
                    ((HistoryFragment) tab).dbContext = dbContext;
                    return tab;
                case 2:
                    tab = new GesturesFragment();
                    return tab;
                default:
                    tab = new InfoFragment();
                    return tab;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "INFORMACION";
                case 1:
                    return "HISTORIAL";
                case 2:
                    return "GESTOS";
            }
            return null;
        }
    }
}
