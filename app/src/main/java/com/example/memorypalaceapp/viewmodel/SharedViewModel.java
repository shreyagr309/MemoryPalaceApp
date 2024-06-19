package com.example.memorypalaceapp.viewmodel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.memorypalaceapp.databinding.FragmentAddHistoryItemsBinding;
import com.example.memorypalaceapp.model.HistoryItems;

// This View Model class is used for communicating between the fragments
// and also, for saving the data of the widgets, if the screen rotates.
public class SharedViewModel extends ViewModel
{
    //Variables for saving the data, if the screen rotates.
    private FragmentAddHistoryItemsBinding fragmentAddHistoryItemsBinding;
    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<String> desc = new MutableLiveData<>();
    private MutableLiveData<String> imageUrl = new MutableLiveData<>();
    private MutableLiveData<String> date = new MutableLiveData<>();
    private MutableLiveData<HistoryItems> deletedItem = new MutableLiveData<>();
    public LiveData<String> getName() {
        return name;
    }
    public void setName(String name)
    {
        this.name.setValue(name);
    }
    public LiveData<String> getDesc() {
        return desc;
    }
    public void setDesc( String desc) {
        this.desc.setValue(desc);
    }
    public LiveData<String> getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(  String imageUrl)
    {
        this.imageUrl.setValue(imageUrl);
    }

    public LiveData<String> getDate() {
        return date;
    }
    public void setDate( String date) {
        this.date.setValue(date);
    }

//    public void setDeletedItem(MutableLiveData<HistoryItems> deletedItem) {
//        this.deletedItem = deletedItem;
//    }
    //private MutableLiveData<String>updatedName=new MutableLiveData<>();
    //private int pos;
    public void setDeletedItem(HistoryItems item) {
        deletedItem.setValue(item);
    }
    public LiveData<HistoryItems> getDeletedItem() {
        return deletedItem;
    }
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