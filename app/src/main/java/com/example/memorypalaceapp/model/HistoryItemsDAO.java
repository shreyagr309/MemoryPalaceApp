package com.example.memorypalaceapp.model;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
//By using annotation, @DAO, we can perform database operations(CRUD) inside this Interface
@Dao
public interface HistoryItemsDAO {
    @Insert
    void insert(HistoryItems historyItems);
    @Delete
    void delete(HistoryItems historyItems);
//    @Update
//    void update(HistoryItems historyItems);
    //Used Live Data, for showing the underlying changes to the UI.
    @Query("SELECT nameof_items from history_items")
    LiveData<List<String>> getAllNames();

    //We can use @Query, for writing own custom SQL queries
    @Query("SELECT descriptionof_items from history_items")
    LiveData<List<String>> getAllDescriptions();
    @Query("SELECT urlof_audiofile from history_items")
    LiveData<List<String>> getAllUrlOfAudioFile();
    @Query("SELECT urlof_image from history_items")
    LiveData<List<String>> getAllUrlsOfImageFile();
    @Query("SELECT timeStamp from history_items")
    LiveData<List<Long>> getAllTimeStamps();
    @Query("SELECT dateof_item from history_items")
    LiveData<List<String>> getAllDates();
    //Query for getting all the history items.
    @Query("SELECT * from history_items")
    LiveData<List<HistoryItems>> getAllHistoryItems();
    //Queries for Getting the data by the unique id's
    @Query("SELECT nameof_items from history_items where unique_id_history_items= :id")
    LiveData<String> getName(int id);
    @Query("SELECT descriptionof_items from history_items where unique_id_history_items= :id")
    LiveData<String> getDescription(int id);
    @Query("SELECT urlof_audiofile from history_items where unique_id_history_items= :id")
    LiveData<String> getUrlOfAudioFile(int id);
    @Query("SELECT urlof_image from history_items where unique_id_history_items= :id")
    LiveData<String> getUrlOfImageFile(int id);
    @Query("SELECT dateof_item from history_items where unique_id_history_items= :id")
    LiveData<String> getDate(int id);
    @Query("SELECT timeStamp from history_items where unique_id_history_items= :id")
    LiveData<Long> getTimeStamp(int id);
    //Query for updating the items
    @Query("UPDATE history_items set nameof_items=:name where unique_id_history_items=:id")
    void  updateName(String name,int id);
    @Query("UPDATE history_items set descriptionof_items=:desc where unique_id_history_items=:id")
    void  updateDescription(String desc,int id);
    @Query("UPDATE history_items set urlof_image=:imageUrl where unique_id_history_items=:id")
    void  updateImageUrl(String imageUrl,int id);
    @Query("UPDATE history_items set urlof_audiofile=:audioUrl where unique_id_history_items=:id")
    void  updateAudioUrl(String audioUrl,int id);
    @Query("UPDATE history_items set dateof_item=:date where unique_id_history_items=:id")
    void  updateDate(String date, int id);
    @Query("UPDATE history_items set timeStamp=:timeStamp where unique_id_history_items=:id")
    void  updateTimeStamp(long timeStamp,int id);
    // Queries for deleting the items
//    @Query("DELETE FROM history_items where unique_id_history_items=:id")
//    void deleteItem(int id);

//    @Insert
//    void insertName(String name);
}
