package com.example.memorypalaceapp.model;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.memorypalaceapp.BR;
import com.example.memorypalaceapp.R;

import java.util.Date;

//Used @Entity annotator to declare this class as Model class.
@Entity(tableName = "history_items")
public class HistoryItems extends BaseObservable
{
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    //Fields inside this class, will be treated as Columns of the table
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "unique_id_history_items")
   private int id;
    @ColumnInfo(name="descriptionof_items")
    private String description;
    @ColumnInfo(name="nameof_items")
    private String name;
    @ColumnInfo(name="urlof_image")//Url for image to be saved
    private String imageUrl;
    @ColumnInfo(name="urlof_audiofile")// url of AudioFile to be saved
    private  String audioUrl ;
    @ColumnInfo(name="dateof_item")//To store the date or time period relevant to the historical item.
    private String date;
   private long timeStamp;//: To keep track of when the item was created or last modified,
                        // which can be useful for sorting or filtering items.
    public HistoryItems(String description, String name, String imageUrl, String audioUrl, String date, long timeStamp) {
        this.description = description;
        this.name = name;
        this.imageUrl = imageUrl;
        this.audioUrl = audioUrl;
        this.date = date;
        this.timeStamp = timeStamp;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }
    public String getAudioUrl() {
        return audioUrl;
    }
    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public long getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;//Sets the current time in milliseconds.
    }
    public HistoryItems()
    {
    }
//    public HistoryItems(String name){
//
//        this.name=name;
//    }
}