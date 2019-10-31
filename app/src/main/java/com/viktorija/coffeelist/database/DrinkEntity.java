package com.viktorija.coffeelist.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "drinks")
public class DrinkEntity {

    @PrimaryKey(autoGenerate = true)
    private int mId;

    private String mName;

    private String mDescription;

    // Constructor (default)
    public DrinkEntity(int id, String name, String description) {
        this.mId = id;
        this.mName = name;
        this.mDescription = description;
    }

    @Ignore
    public DrinkEntity(String name, String description) {
        this.mName = name;
        this.mDescription = description;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description ;
    }
}
