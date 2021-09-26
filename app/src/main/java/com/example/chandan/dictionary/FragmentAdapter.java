package com.example.chandan.dictionary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by chandan on 05-07-2017.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments = new ArrayList<>();
    String []titles = {"Word-Meaning","Sentences","Synonyms","Antonyms"};


    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    public void addFragment(Fragment f)
    {
     fragments.add(f);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
