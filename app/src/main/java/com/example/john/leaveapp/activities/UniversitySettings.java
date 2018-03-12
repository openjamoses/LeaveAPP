package com.example.john.leaveapp.activities;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.fragments.Faculty_Entry;
import com.example.john.leaveapp.fragments.HOD_Entry;
import com.example.john.leaveapp.fragments.US_Entry;
import com.example.john.leaveapp.fragments.UV_Entry;
import com.example.john.leaveapp.utils.Utils;

/**
 * Created by john on 2/28/18.
 */

public class UniversitySettings extends AppCompatActivity  {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    ImageButton mNextBtn;
    Button mSkipBtn, mFinishBtn;

    ImageView zero, one, two,three;
    ImageView[] indicators;
    int lastLeftValue = 0;

    CoordinatorLayout mCoordinator;


    static final String TAG = "ApplyActivity";

    int page = 0;   //  to track page position

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /** TODO::: To be removed...!!!!!!
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         getWindow().getDecorView().setSystemUiVisibility(
         View.SYSTEM_UI_FLAG_LAYOUT_STABLE
         | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
         getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
         }

         **/
        setContentView(R.layout.uv_settings);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mNextBtn = (ImageButton) findViewById(R.id.intro_btn_next);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP)
            mNextBtn.setImageDrawable(
                    Utils.tintMyDrawable(ContextCompat.getDrawable(this, R.drawable.ic_chevron_right_24dp), Color.WHITE)
            );

        mSkipBtn = (Button) findViewById(R.id.intro_btn_skip);
        mFinishBtn = (Button) findViewById(R.id.intro_btn_finish);

        //zero = (ImageView) findViewById(R.id.intro_indicator_0);
        zero = (ImageView) findViewById(R.id.intro_indicator_1);
        one = (ImageView) findViewById(R.id.intro_indicator_2);
        two = (ImageView) findViewById(R.id.intro_indicator_3);
        three = (ImageView) findViewById(R.id.intro_indicator_4);


        mCoordinator = (CoordinatorLayout) findViewById(R.id.main_content);


        indicators = new ImageView[]{zero, one, two,three};

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        mViewPager.setCurrentItem(page);
        updateIndicators(page);

        //final int color1 = ContextCompat.getColor(this, R.color.cyan);
        final int color1 = ContextCompat.getColor(this, R.color.white_70);
        final int color2 = ContextCompat.getColor(this, R.color.white_70);
        final int color3 = ContextCompat.getColor(this, R.color.white_70);
        final int color4 = ContextCompat.getColor(this, R.color.white_70);

        final int[] colorList = new int[]{color1, color2,color3,color4};

        final ArgbEvaluator evaluator = new ArgbEvaluator();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                /*
                color update
                 */
                try {
                    int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colorList[position], colorList[position == 3 ? position : position + 1]);
                    mViewPager.setBackgroundColor(colorUpdate);
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onPageSelected(int position) {

                page = position;

                updateIndicators(page);

                switch (position) {
                    case 0:
                        mViewPager.setBackgroundColor(color1);
                        break;
                    case 1:
                        mViewPager.setBackgroundColor(color2);
                        break;
                    case 2:
                        mViewPager.setBackgroundColor(color2);
                        break;
                    case 3:
                        mViewPager.setBackgroundColor(color2);
                        break;
                    /// case 2:
                    //mViewPager.setBackgroundColor(color3);
                    //   break;
                }


                //mNextBtn.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                //mFinishBtn.setVisibility(position == 2 ? View.VISIBLE : View.GONE);

                mNextBtn.setVisibility(position == 3 ? View.GONE : View.VISIBLE);
                mFinishBtn.setVisibility(position == 3 ? View.VISIBLE : View.GONE);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page += 1;
                mViewPager.setCurrentItem(page, true);
            }
        });

        mSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        mFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //  update 1st time pref
                // Utils.saveSharedSetting(ApplyActivity.this, US_MainActivity.PREF_USER_FIRST_TIME, "false");

            }
        });

    }

    void updateIndicators(int position) {
        for (int i = 1; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */

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
            // getItem is called to instantiate the fragment for the given page.
            // Return a SelectFragment (defined as a static inner class below).
            if (position == 0){
                return UV_Entry.newInstance(position + 1);
            }else if (position == 1){
                return US_Entry.newInstance(position + 1);
            }else if (position == 2){
                return Faculty_Entry.newInstance(position + 1);
            }else {
                return HOD_Entry.newInstance(position + 1);
            }


        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";

            }
            return null;
        }

    }

}
