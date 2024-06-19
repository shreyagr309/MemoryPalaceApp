package com.example.memorypalaceapp.view.history;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.memorypalaceapp.R;
import com.example.memorypalaceapp.databinding.FragmentViewNamesBinding;
import com.example.memorypalaceapp.model.HistoryItems;
import com.example.memorypalaceapp.viewmodel.RoomsViewModel;
import com.example.memorypalaceapp.viewmodel.SharedViewModel;

import java.util.ArrayList;
import java.util.List;
public class ViewNamesFragment extends Fragment
{

    private SharedViewModel sharedViewModel;


        private FragmentViewNamesBinding viewNamesBinding;
         private RoomsViewModel roomsViewModel;
         private RecyclerView recyclerView;
         private RecyclerViewAdapterNames recyclerViewAdapterNames;
         private ArrayList<String> namesArrayList;
         //private FragmentListNamesBinding fragmentListNamesBinding;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,

                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            viewNamesBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_view_names

                    ,container,false);
            View rootView= viewNamesBinding.getRoot();
            return rootView;
        }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=viewNamesBinding.recyclerViewNames;
        // Initialize the adapter
        recyclerViewAdapterNames = new RecyclerViewAdapterNames();
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //Initialize the arrayList
        namesArrayList=new ArrayList<>();
        //Initialize the View Model
        roomsViewModel= new ViewModelProvider(requireActivity()).get(RoomsViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getDeletedItem().observe(getViewLifecycleOwner(), new Observer<HistoryItems>() {
            @Override
            public void onChanged(HistoryItems deletedItem) {
                if (deletedItem != null) {
                    // Perform deletion in namesArrayList based on the deletedItem
                    namesArrayList.remove(deletedItem.getName()); // Assuming getName() returns the name of HistoryItems
                    recyclerViewAdapterNames.notifyDataSetChanged();
                }
            }
        });

//        sharedViewModel.getUpdatedName().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(String updatedName)
//            {
//                int pos=sharedViewModel.getPos();
//                namesArrayList.clear();
//                if (updatedName != null) {
//                    // Find the index of the item to be updated in namesArrayList
//                    if (pos!= 0 &&pos>=0&& pos<namesArrayList.size()) {
//                        // Update the item at the found index
//                        namesArrayList.set(pos,updatedName);
//                        recyclerViewAdapterNames.notifyItemChanged(pos);
//                    }
//
//                    else {
//                        Toast.makeText(requireContext(),"position is empty",Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//            }
//        });

        roomsViewModel.getAllNames().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> namesList)
            {
                Log.d("ViewNames", "Received names list: " + namesList); // Log received data

                Log.d("ViewNames","Array list inside onChanged"+namesArrayList);
                namesArrayList.clear();
                if(namesList.isEmpty())
                {
                  viewNamesBinding.emptyView.setVisibility(View.VISIBLE);
                }
                else
                {
                     viewNamesBinding.emptyView.setVisibility(View.GONE);
                    Log.d("ViewNames","Array list inside else"+namesArrayList);
                    namesArrayList.addAll(namesList);
                    Log.d("ViewNames","Array list after adding"+namesArrayList);
                    recyclerViewAdapterNames.notifyDataSetChanged();
                }
            }
        });
//        roomsViewModel.getAllNames().observe(getViewLifecycleOwner(), new Observer<List<HistoryItems>>() {
//            @Override
//            public void onChanged(List<HistoryItems> historyItems)
//            {
//               namesArrayList.clear();
//               if(historyItems.isEmpty()){
//                   viewNamesBinding.emptyView.setVisibility(View.VISIBLE);
//               }
//
//               else {
//                   viewNamesBinding.emptyView.setVisibility(View.GONE);
//                  namesArrayList.addAll(historyItems);
//                    recyclerViewAdapterNames.notifyDataSetChanged();
//
//               }
//
//            }
//        });

        recyclerViewAdapterNames.setNamesList(namesArrayList);
        recyclerView.setAdapter(recyclerViewAdapterNames);

        }
//    public void notifyItemDeleted(HistoryItems deletedItem) {
//        // Update UI in ViewNames based on the deleted item
//        int itemIndex = namesArrayList.indexOf(deletedItem);
//
//        if (itemIndex != -1) {
//            // Item found, remove it from the data list
//            namesArrayList.remove(itemIndex);
//            recyclerViewAdapterNames.notifyDataSetChanged();
//        }
//    }
}