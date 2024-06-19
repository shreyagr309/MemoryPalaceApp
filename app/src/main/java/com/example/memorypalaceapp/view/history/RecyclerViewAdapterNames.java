package com.example.memorypalaceapp.view.history;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memorypalaceapp.R;
import com.example.memorypalaceapp.databinding.FragmentListNamesBinding;

import java.util.List;

public class RecyclerViewAdapterNames extends RecyclerView.Adapter<RecyclerViewAdapterNames.NamesViewHolder>
{

    private List<String> namesList;
    private FragmentListNamesBinding fragmentListNamesBinding;
@NonNull
    @Override
    public NamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // Inflate the layout
       fragmentListNamesBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.fragment_list_names,parent,false );
            return new NamesViewHolder(fragmentListNamesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NamesViewHolder holder, int position)
    {
        //Initialize the Array List
        //HistoryItems currentName=namesList.get(position);
        String currentName=namesList.get(position);
        //fragmentListNamesBinding.setHistoryItems(currentName);
        holder.fragmentListNamesBinding.setName(currentName);
    }
    @Override
    public int getItemCount() {
        return namesList==null?0:namesList.size();
    }
    public class NamesViewHolder extends RecyclerView.ViewHolder
 {
     private FragmentListNamesBinding fragmentListNamesBinding;
     public NamesViewHolder(@NonNull FragmentListNamesBinding fragmentListNamesBinding ) {
         super(fragmentListNamesBinding.getRoot());
         this.fragmentListNamesBinding=fragmentListNamesBinding;
     }
 }
 public void setNamesList(List<String> namesList) {
        this.namesList = namesList;
        notifyDataSetChanged();
    }
}