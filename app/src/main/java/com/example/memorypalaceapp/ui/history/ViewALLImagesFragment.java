package com.example.memorypalaceapp.ui.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.memorypalaceapp.R;
import com.example.memorypalaceapp.databinding.FragmentViewAllImagesBinding;
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

    @BindingAdapter({"loadimage"})
    public static void loadImage(ImageView imageView, String url)
    {
        if (url == null || url.isEmpty())
        {
//                int placeholderResId = imageView.getResources().getIdentifier(
//                        "baseline_add_a_photo_24.xml", "drawable", imageView.getContext().getPackageName());
            Glide.with(imageView.getRootView().getContext())
                    .load(R.drawable.baseline_add_a_photo_24) // Default or placeholder image
                    .apply(RequestOptions.circleCropTransform()) // Apply circular crop
                    .into(imageView);

//                Picasso.get()
//                        .load(placeholderResId)
//                        .into(imageView);
        } else
        {
            Glide.with(imageView)
                    .load(url)
                    .into(imageView);
//                Picasso.get()
//                        .load(url)
//                        .into(imageView);
        }
    }
}