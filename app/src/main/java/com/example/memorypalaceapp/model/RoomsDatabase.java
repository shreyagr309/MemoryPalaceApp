package com.example.memorypalaceapp.model;
import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
// By using @Database, we declare this class as the Room Database class which will return the instance of the database.
// It contains the database holder and serves as the main access point for the underlying connection to app's persisted, relational data.
@Database(entities = {HistoryItems.class},version =1)
public abstract class RoomsDatabase extends RoomDatabase// Abstract because Room will generate the necessary database implementation code at compile time.
{
    // we'll use DAO interfaces to perform database operations,
    // and the database class serves as the entry point to access
    // the database instance and provides abstract methods for accessing DAOs.
    public abstract HistoryItemsDAO historyItemsDAO(); //Create abstract methods of DAO Classes
    //Will follow Singleton Pattern, which means only single
    // instance of database is used across the android app.
    private static RoomsDatabase dbInstance; //Create dbInstance variable
   public static synchronized RoomsDatabase getInstance(Context context){

       if(dbInstance==null)
       {
           dbInstance= Room.databaseBuilder(context.getApplicationContext(),
                   RoomsDatabase.class,"roomsdb"
                           //AddAutoMigrationSpec will support below schema changes
                        //Adding a new column to an existing table.
                     //Renaming an existing column.
                   // Changing the data type of a column (e.g., from INTEGER to TEXT).
                   ).fallbackToDestructiveMigration()
//                   .addAutoMigrationSpec(MyMigrationSpec.getinstance())
                   //.fallbackToDestructiveMigration()
                   //.addMigrations is used for supporting the schema changes
                   // that are not supported by AUTO MIGRATION.
//                   .addMigrations(new Migration(1,2) {
//                       @Override
//                       public void migrate(@NonNull SupportSQLiteDatabase database)
//                       {
//
//                           database.execSQL("ALTER TABLE history_items ADD COLUMN new_column INTEGER DEFAULT 0");
//                       }
//                   })
                   .build();// The Fallback to Migration method is used. If a newer version of the database is available and no migration paths are found,
                                                            // Room will drop the old database and recreate it with the new schema.
       }
       //When application launches for first time,
       // above statements inside if will be executed, and we will return the dbInstance
       // and when application launches for second and so on, above if won't be executed(Singleton Pattern)
       //So if there is any instance created in the previous executions or commands,
       // then I need to return this.
       return dbInstance;
   }
}