package com.example.memorypalaceapp.view.history;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memorypalaceapp.R;
import com.example.memorypalaceapp.databinding.FragmentListImagesBinding;

import java.util.List;

public class RecyclerViewAdapterImages extends RecyclerView.Adapter<RecyclerViewAdapterImages.ImageViewHolder>
{
    private List<String> imagesList;
    private FragmentListImagesBinding fragmentListImagesBinding;
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // Inflate the layout
        fragmentListImagesBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.fragment_list_images,parent,false );
        return new ImageViewHolder(fragmentListImagesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position)
    {
        //Initialize the Array List
        //HistoryItems currentName=namesList.get(position);
        String currentImageUrl=imagesList.get(position);
        //fragmentListNamesBinding.setHistoryItems(currentName);
        holder.fragmentListImagesBinding.setImageUrl(currentImageUrl);
    }
    @Override
    public int getItemCount() {
        return imagesList==null?0:imagesList.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder
    {
        private FragmentListImagesBinding fragmentListImagesBinding;
        public ImageViewHolder(@NonNull FragmentListImagesBinding fragmentListImagesBinding ) {
            super(fragmentListImagesBinding.getRoot());
            this.fragmentListImagesBinding=fragmentListImagesBinding;
        }
    }
    public void setImagesList(List<String> imagesList) {
        this.imagesList = imagesList;
        notifyDataSetChanged();
    }
}