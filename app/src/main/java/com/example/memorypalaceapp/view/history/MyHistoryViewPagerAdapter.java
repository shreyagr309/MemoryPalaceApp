package com.example.memorypalaceapp.view.history;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
public class MyHistoryViewPagerAdapter extends FragmentStateAdapter
{
    //We are creating arraylist because we have to add three fragments in the list
    private ArrayList<Fragment> fragmentArrayList=new ArrayList<>();//Data Source
    //Constructor of FragmentStateAdapter
    public MyHistoryViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) //responsible for crating and returning a
    //fragment for a specific position within ViewPager2
    {
        return fragmentArrayList.get(position);
    }
    @Override
    public int getItemCount() {
        return fragmentArrayList.size();
    }

    public void addFragment(Fragment fragment)
    {
        fragmentArrayList.add(fragment);

    }
}