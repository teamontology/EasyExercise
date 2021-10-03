package com.example.myapplication.databases.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.databases.entities.Sport;

import java.util.List;

@Dao
public interface SportDao {

    @Query("SELECT * FROM sport")
    List<Sport> getSportList();

    @Query("DELETE FROM sport") // TODO: can't resolve symbol 'sport'
    void deleteAllSport();

    @Insert
    void insertSport(Sport... sport);

    @Update
    void updateSport(Sport... sport);

    @Delete
    void deleteSport(Sport... sport);
}