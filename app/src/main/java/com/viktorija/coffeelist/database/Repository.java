package com.viktorija.coffeelist.database;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.viktorija.coffeelist.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Repository {

    private static Repository ourInstance;

    private DrinkDatabase mDb;

    // Factory pattern. (Executors is Class that creates instance of other class)
    private Executor executor = Executors.newSingleThreadExecutor();

    // Repository - singleton pattern. Assumes that there is only one thread  - main UI thread,
    // that's why we do not need to use synchronization (as we use with database)
    public static Repository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new Repository(context);
        }
        return ourInstance;
    }

    // Constructor
    public Repository(Context context) {
        mDb = DrinkDatabase.getInstance(context);
    }

    // method to get all drinks from the database
    public LiveData<List<DrinkEntity>> getDrinkList() {
        return mDb.drinkDao().getAllDrinks();
    }

    // Menu related commands

    // method to insert sample data when user clicks on menu option "add sample data"
    public void addSampleData() {
        executor.execute(() -> mDb.drinkDao().insertAllDrinks(SampleData.getAllDrinks()));
    }

    // method to delete all data when user clicks on menu option "delete all"
    public void deleteAllDrinks() {
        executor.execute(() -> mDb.drinkDao().deleteAll());
    }

    // method to insert new drink into the database (from the database)
    public void insertDrink(final DrinkEntity drink) {
        executor.execute(() -> mDb.drinkDao().insertDrink(drink));
    }

    public LiveData<DrinkEntity> loadDrinkById(int id) {
        return mDb.drinkDao().loadDrinkById(id);
    }

    // method to update a drink
    public void updateDrink(final DrinkEntity drink) {
        executor.execute(() -> mDb.drinkDao().update(drink));
    }

    // method to delete a drink
    public void deleteDrink(int id) {
        executor.execute(() -> mDb.drinkDao().deleteDrink(id));
    }
}
