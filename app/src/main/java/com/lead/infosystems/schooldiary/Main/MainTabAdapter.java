package com.lead.infosystems.schooldiary.Main;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lead.infosystems.schooldiary.R;

import java.util.ArrayList;
import java.util.List;

public class MainTabAdapter extends Fragment implements PostDialog.IRefereshHome {

    ViewPager viewPager;
    TabLayout tabLayout;
    FloatingActionButton fab;
    View rootview;
    int currentTab;
    private final int HOME_TAB = 0;
    private final int QA_TAB = 1;
    private final int CHAT_TAB = 2;
    private final int NOTIFICATION_TAB = 3;
    ViewPagerAdapter adapter;
    public MainTabAdapter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_main_frag, container, false);

        viewPager = (ViewPager) rootview.findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        setupViewPager(viewPager);
        fab = (FloatingActionButton) rootview.findViewById(R.id.post_new_fab);
        tabLayout = (TabLayout) rootview.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                currentTab = viewPager.getCurrentItem();
                if(currentTab == HOME_TAB){
                    loadHomeFragDialog();
                }else if(currentTab == QA_TAB){
                    Log.e("tab","1");
                }
                else if(currentTab == CHAT_TAB){
                    Log.e("tab","2");
                }
                else if(currentTab == NOTIFICATION_TAB){
                    Log.e("tab","3");
                }
            }
        });
        return rootview;
    }

    public void loadHomeFragDialog(){
        android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
        PostDialog postDialog = new PostDialog(this);
        postDialog.show(fragmentManager,"frag");
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(new FragTabHome(),"ONE");
        adapter.addFragment(new FragTabQA(),"TWO");
        adapter.addFragment(new FragTabChat(), "THREE");
        adapter.addFragment(new FragTabNotifications(), "FOUR");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void refereshHome() {
     //   adapter.getItem(viewPager.getCurrentItem()).onResume();
       // adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_question_answer);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_message);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_net);
    }
}
