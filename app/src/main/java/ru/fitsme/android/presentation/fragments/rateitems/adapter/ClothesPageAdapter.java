package ru.fitsme.android.presentation.fragments.rateitems.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ru.fitsme.android.domain.entities.clothes.ClothesPage;
import ru.fitsme.android.presentation.fragments.rateitem.view.RateItemFragment;

public class ClothesPageAdapter extends FragmentStatePagerAdapter {
    private List<ClothesPage> clothesPageList;

    public ClothesPageAdapter(FragmentManager fm, List<ClothesPage> clothesPages) {
        super(fm);
        clothesPageList = clothesPages;

    }

    @Override
    public Fragment getItem(int i) {
        return RateItemFragment.newInstance(clothesPageList.get(i));
    }

    @Override
    public int getCount() {
        return clothesPageList == null ? 0 : clothesPageList.size();
    }
}
