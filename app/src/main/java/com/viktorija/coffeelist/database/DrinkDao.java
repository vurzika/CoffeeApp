package com.viktorija.coffeelist.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

// Dao - interface
//get methods wrap with live data

@Dao
public interface DrinkDao {

    @Insert
    void insertDrink(DrinkEntity drinkEntity);

    @Query("SELECT * FROM drinks WHERE mId = :id")
    LiveData<DrinkEntity>loadDrinkById(int id);

    // Declare a method to update one news article
    // Annotate the method with @Update.
    @Update
    void update(DrinkEntity drinkEntity);


    // method to insert all drinks into the DrinkDatabase
    @Insert
    void insertAllDrinks(List<DrinkEntity> drinks);

    // method to get all drinks from the DrinkDatabase to display them on the screen
    @Query("SELECT * FROM drinks")
    LiveData<List<DrinkEntity>> getAllDrinks();

    // delete all drinks
    @Query("DELETE FROM drinks")
    void deleteAll();

    @Query("DELETE FROM drinks WHERE mId = :id")
    void deleteDrink (int id);
}
