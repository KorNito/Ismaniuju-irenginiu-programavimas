package com.example.a9ld;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

import java.util.List;

@Dao
public interface MarkerDao {

    //Insert query
    @Insert(onConflict = REPLACE)
    void insert (MarkerEntity marker);

    //Delete query
    @Delete
    void delete(MarkerEntity marker);

    //Delete all query
    @Delete
    void reset(List<MarkerEntity> marker);

    //Delete by title query
    @Query("DELETE FROM marker_table WHERE title = :sTitle")
    void deleteByTitle(String sTitle);

    //Update query
    @Query("UPDATE marker_table SET title = :sTitle WHERE ID = :sID")
    void update(int sID, String sTitle);

    //Get all data query
    @Query("SELECT * from marker_table")
    List<MarkerEntity> getAll();
}
