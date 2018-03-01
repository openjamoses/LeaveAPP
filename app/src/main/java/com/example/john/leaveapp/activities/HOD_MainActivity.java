package com.example.john.leaveapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.fragments.us_fragments.Report_Fragment;
import com.example.john.leaveapp.fragments.us_fragments.US_DFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 2/28/18.
 */

public class HOD_MainActivity extends AppCompatActivity {
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