package com.example.memorypalaceapp.ui.history;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.memorypalaceapp.R;
import com.example.memorypalaceapp.databinding.HistoryListItemBinding;
import com.example.memorypalaceapp.model.HistoryItems;
import java.util.ArrayList;
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.HistoryItemsViewHolder>
{
    //Data Source
    ArrayList<HistoryItems> historyItems;
    private HistoryListItemBinding historyListItemBinding;
    @SuppressLint("NotifyDataSetChanged")
    public RecyclerViewAdapter()
    {
        //Empty constructor to avoid null pointer exceptions.
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
    public class HistoryItemsViewHolder extends RecyclerView.ViewHolder
    {
        private HistoryListItemBinding historyListItemBinding;
        public HistoryItemsViewHolder(@NonNull HistoryListItemBinding historyListItemBinding) {
            super(historyListItemBinding.getRoot());
            this.historyListItemBinding=historyListItemBinding;
        }
    }
}