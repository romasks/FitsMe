package ru.fitsme.android.presentation.fragments.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MainViewPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public MainViewPageAdapter(FragmentManager fragmentManger, List<Fragment> fragments) {
        super(fragmentManger);
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

}
