package com.example.memorypalaceapp;

import androidx.room.migration.AutoMigrationSpec;

public class MyMigrationSpec implements AutoMigrationSpec
{
    private static final MyMigrationSpec myMigrationSpec=new MyMigrationSpec();
    public static MyMigrationSpec getinstance(){
        return  myMigrationSpec;
    }
    // Private constructor to prevent instantiation from outside
    private MyMigrationSpec(){}
}
