package com.example.a9ld;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

@Entity(tableName = "marker_table")
public class MarkerEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "latitude")
    private double mlatitude;

    @ColumnInfo(name = "longitude")
    private double mlongitude;

    @ColumnInfo(name = "title")
    private String mtitle;

    public double getMlatitude() {
        return mlatitude;
    }

    public void setMlatitude(double mlatitude) {
        this.mlatitude = mlatitude;
    }

    public double getMlongitude() {
        return mlongitude;
    }

    public void setMlongitude(double mlongitude) {
        this.mlongitude = mlongitude;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mTitle) {
        this.mtitle = mTitle;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
