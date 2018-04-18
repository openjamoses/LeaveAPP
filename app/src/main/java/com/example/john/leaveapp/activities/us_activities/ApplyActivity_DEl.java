package com.example.john.leaveapp.activities.us_activities;

import android.support.v7.app.AppCompatActivity;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.john.leaveapp.R;
import com.example.john.leaveapp.fragments.us_fragments.SelectFragment;
import com.example.john.leaveapp.fragments.us_fragments.PlaceholderFragment2;
import com.example.john.leaveapp.utils.Utils;
/**
 * Created by john on 2/25/18.
 */
public class ApplyActivity_DEl extends AppCompatActivity implements SelectFragment.OnFragmentInteractionListener {

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

    ImageView zero, one, two;
    ImageView[] indicators;
    static PlaceholderFragment2 placeholderFragment2;

    int lastLeftValue = 0;

    CoordinatorLayout mCoordinator;


    static final String TAG = "ApplyActivity_DEl";

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
        setContentView(R.layout.activity_pager);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


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
        one = (ImageView) findViewById(R.id.intro_indicator_1);
        two = (ImageView) findViewById(R.id.intro_indicator_2);

        mCoordinator = (CoordinatorLayout) findViewById(R.id.main_content);


        indicators = new ImageView[]{one, two};

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        placeholderFragment2 = new PlaceholderFragment2();

        mViewPager.setCurrentItem(page);
        updateIndicators(page);

        //final int color1 = ContextCompat.getColor(this, R.color.cyan);
        final int color1 = ContextCompat.getColor(this, R.color.white_70);
        final int color2 = ContextCompat.getColor(this, R.color.white_70);
        final int[] colorList = new int[]{color1, color2};
        final ArgbEvaluator evaluator = new ArgbEvaluator();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                /*
                color update
                 */
                try {
                    int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colorList[position], colorList[position == 1 ? position : position + 1]);
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
                   /// case 2:
                        //mViewPager.setBackgroundColor(color3);
                     //   break;
                }


                //mNextBtn.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                //mFinishBtn.setVisibility(position == 2 ? View.VISIBLE : View.GONE);

                mNextBtn.setVisibility(position == 1 ? View.GONE : View.VISIBLE);
                mFinishBtn.setVisibility(position == 1 ? View.VISIBLE : View.GONE);


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
               // Utils.saveSharedSetting(ApplyActivity_DEl.this, US_MainActivity.PREF_USER_FIRST_TIME, "false");

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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String name, String desc) {
        placeholderFragment2.onFragmentInteraction(name,desc);
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
                return SelectFragment.newInstance(position + 1);
            }else {
                return PlaceholderFragment2.newInstance(position + 1);
            }


        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
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
            }
            return null;
        }

    }

}
