package com.example.a9ld;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.google.android.gms.maps.model.Marker;

@Database(entities = {MarkerEntity.class}, version = 1, exportSchema = false)
public abstract class MarkerRoomDatabase extends RoomDatabase {

    private static MarkerRoomDatabase database;

    private static String DATABASE_NAME = "marker_database";

    public synchronized static MarkerRoomDatabase getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    MarkerRoomDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract MarkerDao markerDao();
}
