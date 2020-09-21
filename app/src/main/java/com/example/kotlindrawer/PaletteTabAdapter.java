package com.example.kotlindrawer;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @author ChayChan
 * @date 2017/8/1  22:09
 */

public class PaletteTabAdapter extends FragmentStatePagerAdapter {

    private List<PicFragment> mFragments;
    private String[] mTitles;

    public PaletteTabAdapter(FragmentManager fm, List<PicFragment> fragments, String[] titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
