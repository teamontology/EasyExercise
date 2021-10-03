package com.example.myapplication.databases.rooms;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.databases.daos.SportDao;
import com.example.myapplication.databases.entities.Sport;

@Database(entities = Sport.class, exportSchema = false, version = 1)
public abstract class SportDB extends RoomDatabase {
    private static final String DB_NAME = "sport_db";
    private static SportDB instance;

    public static synchronized SportDB getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), SportDB.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract SportDao getSportDao();
}