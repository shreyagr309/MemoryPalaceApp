package com.example.memorypalaceapp.viewmodel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.memorypalaceapp.model.HistoryItems;

// This View Model class is used for communicating between the fragments
public class SharedViewModel extends ViewModel
{
    private MutableLiveData<HistoryItems> deletedItem = new MutableLiveData<>();
    //private MutableLiveData<String>updatedName=new MutableLiveData<>();
    //private int pos;
    public void setDeletedItem(HistoryItems item) {
        deletedItem.setValue(item);
    }
    public LiveData<HistoryItems> getDeletedItem()
    {
        return deletedItem;
    }
//    public void setupdatedName(String name) {
//        updatedName.setValue(name);
//    }
//
//    public LiveData<String> getUpdatedName(){
//
//        return updatedName;
//    }
//
//public int getPos() {
//    return pos;
//}

//    public void setPos(int pos) {
//        this.pos = pos;
//    }
}