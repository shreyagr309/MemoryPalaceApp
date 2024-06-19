package com.example.memorypalaceapp.view.history;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.memorypalaceapp.R;
import com.example.memorypalaceapp.databinding.ActivityHistoryRoomBinding;
import com.example.memorypalaceapp.viewmodel.RoomsViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HistoryRoomActivity extends AppCompatActivity    {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;


    private RoomsViewModel roomsViewModel;
    private MyHistoryViewPagerAdapter myViewPagerAdapter;
    private ActivityHistoryRoomBinding activityHistoryRoomBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_room);

        activityHistoryRoomBinding = DataBindingUtil.setContentView(this, R.layout.activity_history_room);
        viewPager2 = activityHistoryRoomBinding.viewpager;
        tabLayout = activityHistoryRoomBinding.tabLayout;
        //Adding Fragments to the list in the Adapter class
        myViewPagerAdapter = new MyHistoryViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        myViewPagerAdapter.addFragment(new AddHistoryItemsFragment());
        myViewPagerAdapter.addFragment(new ViewHistoryItemsFragment());
        myViewPagerAdapter.addFragment(new ViewNamesFragment());
        myViewPagerAdapter.addFragment(new ViewALLImagesFragment());
        // Set the orientation in ViewPager2
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        //Connecting the adapter with ViewPager2
        viewPager2.setAdapter(myViewPagerAdapter);
        // Connect tab layout with ViewPager
        //TabLayoutMediator is a utility class provided by android.x, to simplify the integration of tab layout and viewpager.
        new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            //tab represents the current tab layout, position represents the current fragment, within viewpager
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                // tab.setText("Fragment"+(position+1));//position starts with 0.
                switch (position) {
                    case 0:
                        //tab.setText("Fragment 1");
                        tab.setText("Add History Items");
                        tab.setIcon(R.drawable.baseline_add_24);
                        break;
                    case 1:
                        tab.setText("View History Items");
                        tab.setIcon(R.drawable.baseline_view_array_24);
                        break;
                    case 2:
                        tab.setText("View Names");
                        tab.setIcon(R.drawable.baseline_calendar_view_week_24);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.baseline_image_24);
                        tab.setText("View All Images");
                        break;
                }
            }
        }
        ).attach();//The attach method is called at the end to attach the
        // tab layout mediator to the tab layout and Viewpager

    }


}