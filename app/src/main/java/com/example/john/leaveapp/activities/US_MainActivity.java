package com.example.john.leaveapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.fragments.us_fragments.US_DFragment;
import com.example.john.leaveapp.fragments.us_fragments.Report_Fragment;

import java.util.ArrayList;
import java.util.List;

public class US_MainActivity extends AppCompatActivity {
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.us_mainactivity);
        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
            setSupportActionBar(toolbar);
            int[] icons = {R.mipmap.ic_dashboard,
                    R.mipmap.ic_event,
                    R.mipmap.ic_atm
            };
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            ViewPager viewPager = (ViewPager) findViewById(R.id.main_tab_content);


            setupViewPager(viewPager);


            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0).setText(getResources().getString(R.string.dashboard));
            tabLayout.getTabAt(1).setText(getResources().getString(R.string.report));
            for (int i = 0; i < icons.length-1; i++) {
                tabLayout.getTabAt(i).setIcon(icons[i]);

            }
            tabLayout.getTabAt(0).select();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_university) {
            startActivity(new Intent(context, UniversitySettings.class));
            return true;
        }
        if (id == R.id.action_profile) {
            //startActivity(new Intent(context, SettingsActivity.class));
            Toast.makeText(context,"Not Implemented...!",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.insertNewFragment(new US_DFragment());
        adapter.insertNewFragment(new Report_Fragment());
        //adapter.insertNewFragment(new Menu_Fragment());
        // adapter.insertNewFragment(new SearchFragment());
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void insertNewFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }
    }

}
