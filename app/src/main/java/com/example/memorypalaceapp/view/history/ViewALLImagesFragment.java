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
import com.example.memorypalaceapp.databinding.FragmentViewAllImagesBinding;
import com.example.memorypalaceapp.model.HistoryItems;
import com.example.memorypalaceapp.viewmodel.RoomsViewModel;
import com.example.memorypalaceapp.viewmodel.SharedViewModel;

import java.util.ArrayList;
import java.util.List;


public class ViewALLImagesFragment extends Fragment
{
    private SharedViewModel sharedViewModel;
    private FragmentViewAllImagesBinding viewAllImagesBinding;
    private RoomsViewModel roomsViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapterImages recyclerViewAdapterImages;
    private ArrayList<String> imagesArrayList;
    //private FragmentListNamesBinding fragmentListNamesBinding;



    public ViewALLImagesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment

        viewAllImagesBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_view_all_images

                ,container,false);
        View rootView= viewAllImagesBinding.getRoot();
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=viewAllImagesBinding.recyclerViewImages;
        recyclerViewAdapterImages=new RecyclerViewAdapterImages();
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        imagesArrayList=new ArrayList<>();
        //Initialize the View Model
        roomsViewModel= new ViewModelProvider(requireActivity()).get(RoomsViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        sharedViewModel.getDeletedItem().observe(getViewLifecycleOwner(), new Observer<HistoryItems>() {
            @Override
            public void onChanged(HistoryItems deletedItem)
            {

                if(deletedItem!=null){

                    // Perform deletion in namesArrayList based on the deletedItem
                    imagesArrayList.remove(deletedItem.getImageUrl()); // Assuming getName() returns the name of HistoryItems
                    recyclerViewAdapterImages.notifyDataSetChanged();

                }


            }
        });
        roomsViewModel.getAllUrlOfImageFile().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> imageurlsList)
            {
                imagesArrayList.clear();
                if(imageurlsList.isEmpty()){

                    viewAllImagesBinding.emptyView.setVisibility(View.VISIBLE);
                }

                else {
                    viewAllImagesBinding.emptyView.setVisibility(View.GONE);
                    Log.d("ViewImages","Array list inside else"+imagesArrayList);
                    imagesArrayList.addAll(imageurlsList);
                    Log.d("ViewImages","Array list after adding"+imagesArrayList);
                    recyclerViewAdapterImages.notifyDataSetChanged();
                }


            }
        });


        recyclerViewAdapterImages.setImagesList(imagesArrayList);
        recyclerView.setAdapter(recyclerViewAdapterImages);


    }

}