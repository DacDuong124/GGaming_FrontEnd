package com.example.ggaming_frontend;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private Fragment mCurrentFragment;

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {

        super.setPrimaryItem(container, position, object);
        mCurrentFragment = ((Fragment) object);
    }

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new MyGamesFragment();
            case 2:
                return new CartFragment();
            default:
                return new HomeFragment();
        }

    }


    @Override
    public int getCount() {
        return 3;
    }
}
