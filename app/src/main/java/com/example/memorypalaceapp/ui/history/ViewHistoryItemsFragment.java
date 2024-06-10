package com.example.memorypalaceapp.ui.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.ims.RcsUceAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.memorypalaceapp.R;
import com.example.memorypalaceapp.databinding.FragmentViewHistoryItemsBinding;
import com.example.memorypalaceapp.databinding.HistoryListItemBinding;
import com.example.memorypalaceapp.model.HistoryItems;
import com.example.memorypalaceapp.model.RoomsDatabase;
import com.example.memorypalaceapp.viewmodel.RoomsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewHistoryItemsFragment extends Fragment
{

    private FragmentViewHistoryItemsBinding fragmentViewHistoryItemsBinding;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private ArrayList<HistoryItems> historyItemsArrayList;
    private RoomsViewModel roomsViewModel;

    public ViewHistoryItemsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentViewHistoryItemsBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_view_history_items
                ,container,false);
        View rootView= fragmentViewHistoryItemsBinding.getRoot();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Recycler View
        recyclerView=fragmentViewHistoryItemsBinding.recyclerView;
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //Initialize the arrayList
        historyItemsArrayList=new ArrayList<>();
        //Initialize the View Model
        roomsViewModel= new ViewModelProvider(requireActivity()).get(RoomsViewModel.class);
        // Loading the Data from ROOM DB
        roomsViewModel.getAllHistoryItems().observe(getViewLifecycleOwner(), new Observer<List<HistoryItems>>() {
            //Clears the content of arraylist, before showing the full data
            @Override
            public void onChanged(List<HistoryItems> historyItems) {
                historyItemsArrayList.clear();
                for(HistoryItems hi:historyItems){

                    Log.v("TAGYS",""+hi.getName());
                    historyItemsArrayList.add(hi);
                }
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });
        // Initialize the adapter and set it to RecyclerView
        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerViewAdapter.setHistoryItems(historyItemsArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}