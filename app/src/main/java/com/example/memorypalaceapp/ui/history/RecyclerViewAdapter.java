package com.example.memorypalaceapp.ui.history;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.memorypalaceapp.R;
import com.example.memorypalaceapp.databinding.HistoryListItemBinding;
import com.example.memorypalaceapp.model.HistoryItems;
import java.util.ArrayList;
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.HistoryItemsViewHolder>
{
    //Data Source

    private ActivityResultLauncher<Intent> launcher;
    private ItemClickListener itemClickListener;
    ArrayList<HistoryItems> historyItems;
    private HistoryListItemBinding historyListItemBinding;
    @SuppressLint("NotifyDataSetChanged")
    public RecyclerViewAdapter()
    {
        //Empty constructor to avoid null pointer exceptions.
    }

    //created the method setClickListener, so that it can be called from ViewHistoryItemsFragment
    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }





    @NonNull
    @Override
    public HistoryItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        // Inflate the layout
        historyListItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.history_list_item,parent,false );
        return new HistoryItemsViewHolder(historyListItemBinding);
    }
    @Override
    public void onBindViewHolder(@NonNull HistoryItemsViewHolder holder, int position)
    {
        //Get the current items at their respective position
     HistoryItems currentHistoryItem=historyItems.get(position);
     holder.historyListItemBinding.setHistoryitems(currentHistoryItem);


     //holder.historyListItemBinding.executePendingBindings();
    }
    @Override
    public int getItemCount() {

        if(historyItems!=null){
            return historyItems.size();
        }
        else {
            return 0;
        }
    }
    public void setHistoryItems(ArrayList<HistoryItems> historyItems) {
        this.historyItems = historyItems;
        notifyDataSetChanged();
    }
    public class HistoryItemsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private HistoryListItemBinding historyListItemBinding;
        public HistoryItemsViewHolder(@NonNull HistoryListItemBinding historyListItemBinding)
        {
            super(historyListItemBinding.getRoot());
            this.historyListItemBinding=historyListItemBinding;
            historyListItemBinding.textViewName.setOnClickListener(this);//When you call
            historyListItemBinding.textViewDescription.setOnClickListener(this);
            historyListItemBinding.imageView.setOnClickListener(this);// historyListItemBinding.getRoot(),
                                                                     // it returns the root view of
                                                                     // your item layout,
                                                                      // which is essentially your itemView
        }
        @Override
        public void onClick(View v)
        {

            if(itemClickListener!=null){

                itemClickListener.onCLick(v,getAbsoluteAdapterPosition());

            }
        }
    }






}