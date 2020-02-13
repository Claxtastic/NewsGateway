package com.claxtastic.newsgateway;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.List;

public class ArticlePagerAdapter extends FragmentPagerAdapter {
    private long baseId = 0;
    private List<Fragment> fragments;

    ArticlePagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
        super(fragmentManager);
        this.fragments = fragments;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) { return fragments.get(position); }

    @Override
    public int getCount() { return fragments.size(); }

    @Override
    public long getItemId(int position) {
        return baseId + position;
    }

    void notifyChangeInPosition(int n) {
        baseId += getCount() + n;
    }
}
