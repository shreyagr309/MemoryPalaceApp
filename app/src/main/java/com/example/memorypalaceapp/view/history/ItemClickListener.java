package com.example.memorypalaceapp.view.history;
import android.view.View;
//We need to implement this listener in MyViewHolder
public interface ItemClickListener
{
    void onCLick(View v, int position);
}