package com.example.memorypalaceapp;

//This class will provide the clean api, for the data access, to the rest of the app
// In this class we basically initialize the data sources(Rooms Database in our case) and Dao.

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository
{
    //We also need to create the executor out of Executor Service,
    // for executing the database operations, in the background thread.
    private int numberofThreads=4;
    private ExecutorService executor;
    private final HistoryItemsDAO historyItemsDAO;//DAO available

    // Create the constructor to initialize the above variables.
    //inside (), we are passing application, because in order to use the function getInstance(),
    // We need to pass context, and Application is a sub class of context
    public Repository(Application application)
    {
        //Get the instance of the database, and initialize the historyItemsDAO

      RoomsDatabase roomsDatabase= RoomsDatabase.getInstance(application); //Data Source(Rooms database)

      this.historyItemsDAO= roomsDatabase.historyItemsDAO();

      //new Single thread is best for sequential task execution without overlap.
      //executor= Executors.newSingleThreadExecutor();
        // newFixedThreadPool ensures that 4 threads or tasks can run concurrently.
        //this will improve the responsiveness of the app
        executor=Executors.newFixedThreadPool(numberofThreads);//We can not make database operations to run on the main thread
    }

    //Create functions for performing the database operations
    //Methods in DAO, being executed from Repository

    public void addHistoryItems(HistoryItems historyItems)
    {
        // runnable interface, will run the task on the separated thread.
        //It represents a unit of work that can be executed  asynchronously.
        executor.execute(new Runnable() {
            @Override
            public void run() {
                historyItemsDAO.insert(historyItems);
            }
        });
    }
    public void deleteHistoryItems(HistoryItems historyItems){
        executor.execute(new Runnable() {
            @Override
            public void run() {

                historyItemsDAO.delete(historyItems);
            }
        });
    }
    //We can not use a background thread to run on the LiveData
    // And since we are just returning the names and showing it using the LiveData
    //LiveData runs on main thread, and  it will update the Ui, if there is any underlying
    //changes in the data
    public LiveData<List<String>> getAllnames()
    {
        return historyItemsDAO.getAllNames();

    }
    public LiveData<List<String>> getAlldescriptions()
    {
        return historyItemsDAO.getAllDescriptions();
    }
    public LiveData<List<String>> getAllUrlOfAudioFile()
    {
        return historyItemsDAO.getAllUrlOfAudioFile();
    }
    public LiveData<List<String>> getAllUrlOfImageFile()
    {
        return historyItemsDAO.getAllUrlsOfImageFile();
    }
    public LiveData<List<Date>> getAllDates()
    {
        return historyItemsDAO.getAllDates();
    }
    public LiveData<List<Long>> getAllTimeStamps()
    {
        return historyItemsDAO.getAllTimeStamps();
    }
    public LiveData<List<String>> getAllHistoryItems(){
        return historyItemsDAO.getAllHistoryItems();
    }

    public LiveData<String> getName(int id){
        return  historyItemsDAO.getName(id);
    }

    public LiveData<String> getDescription(int id){
        return  historyItemsDAO.getDescription(id);
    }

    public LiveData<String> getImageUrl(int id){
        return  historyItemsDAO.getUrlOfImageFile(id);
    }

    public LiveData<String> getAudioUrl(int id){
        return  historyItemsDAO.getUrlOfAudioFile(id);
    }

    public LiveData<Date> getDate(int id){
        return  historyItemsDAO.getDate(id);
    }
    public LiveData<Long> getTimeStamp(int id){
        return  historyItemsDAO.getTimeStamp(id);
    }

  public void  updateName(String name,int id)
  {
      historyItemsDAO.updateName(name,id);
  }
    public void  updateDescription(String description,int id)
    {
        historyItemsDAO.updateDescription(description,id);
    }
    public void  updateImageUrl(String imageurl,int id)
    {
        historyItemsDAO.updateImageUrl(imageurl,id);
    }
    public void  updateAudioUrl(String audiourl ,int id)
    {
        historyItemsDAO.updateAudioUrl(audiourl,id);
    }
    public void updateDate(Date date, int id)
    {

        historyItemsDAO.updateDate(date,id);
    }
    public void updateTimeStamp(long timestamp,int id){

        historyItemsDAO.updateTimeStamp(timestamp,id);
    }
    public void deleteItem(int id)
    {
        historyItemsDAO.deleteItem(id);
    }
}