package com.ufo.socketioandroiddemo;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ufo.extend.NoScrollViewPager;
import com.ufo.socketioandroiddemo.message.view.ChatFragment;
import com.ufo.socketioandroiddemo.message.view.ContactFragment;
import com.ufo.socketioandroiddemo.setting.SettingFragment;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private NoScrollViewPager mViewPager;
    private TabLayout mTabLayout;

    String[] mTitles = {"消息", "联系人", "设置"};
    int[] mDrawables = {R.drawable.ic_message, R.drawable.ic_people, R.drawable.ic_setting};





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (NoScrollViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_custom, null);
                TextView textView = (TextView) view.findViewById(R.id.tab_custom_title);
                textView.setText(mTitles[i]);
                textView.setCompoundDrawablesWithIntrinsicBounds(0, mDrawables[i], 0, 0);

                tab.setCustomView(view);
            }
        }


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                setTabSelected(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setTabSelected(tab.getPosition(),false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setTabSelected(0, true);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setTabSelected(int index, boolean selected) {

        View customView = mTabLayout.getTabAt(index).getCustomView();
        TextView textView = (TextView) customView.findViewById(R.id.tab_custom_title);

        if (textView != null) {
            if (selected) {
                textView.setTextColor(getResources().getColor(R.color.colorAccent));
                setTextViewDrawableColor(textView, R.color.colorAccent);
            }
            else {
                textView.setTextColor(getResources().getColor(R.color.colorSecondaryText));
                setTextViewDrawableColor(textView, R.color.colorSecondaryText);
            }
        }


    }

    private void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_IN));
            }
        }

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
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position){
                case 0:
                    return ChatFragment.newInstance();
                case 1:
                    return ContactFragment.newInstance();
                case 2:
                    return SettingFragment.newInstance();
                default:
                    break;
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
