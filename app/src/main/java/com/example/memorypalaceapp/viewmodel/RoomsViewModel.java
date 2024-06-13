package com.example.memorypalaceapp.viewmodel;
import android.app.Application;
import android.health.connect.datatypes.StepsCadenceRecord;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.memorypalaceapp.model.HistoryItems;
import com.example.memorypalaceapp.model.ItemsRepository;

import java.util.List;
public class RoomsViewModel extends AndroidViewModel
{
    private ItemsRepository itemsRepository;
    public RoomsViewModel(@NonNull Application application)
    {
        //Initializing the items repo, and also, calling the constructor of AndroidViewModel by using super
        super(application);
        this.itemsRepository=new ItemsRepository(application);
    }
     public void insertHistoryItems(HistoryItems historyItems){
        itemsRepository.addHistoryItems(historyItems);
     }
     public void deleteHistoryItems(HistoryItems historyItems){
        itemsRepository.deleteHistoryItems(historyItems);
     }
     public LiveData<List<HistoryItems>> getAllHistoryItems()
     {
       return itemsRepository.getAllHistoryItems();
     }
     public void updateName(String name, int id)
     {
         itemsRepository.updateName(name,id);
     }
     public void updateDesc(String desc, int id){
        itemsRepository.updateDescription(desc,id);
     }
     public void UpdateImageUrl(String url, int id){
        itemsRepository.updateImageUrl(url,id);
     }
     public void updateDate(String date, int id){

        itemsRepository.updateDate(date,id);
     }
    public LiveData<List<String>> getAllNames()
    {
        return itemsRepository.getAllnames();
    }

    public LiveData<List<String>> getAllUrlOfImageFile()
    {
        return itemsRepository.getAllUrlOfImageFile();
    }

}