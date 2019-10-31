package com.viktorija.coffeelist.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.viktorija.coffeelist.database.DrinkEntity;
import com.viktorija.coffeelist.database.Repository;

import java.util.List;

// ViewModel helps you to provide data between repository and UI.

public class MainViewModel extends AndroidViewModel {

    private Repository mRepository;
    private LiveData<List<DrinkEntity>> mDrinks;

    // constructor
    public MainViewModel(@NonNull Application application) {
        super(application);
        // getting the list of all names from Repository
        mRepository = Repository.getInstance(application.getApplicationContext());

        mDrinks = mRepository.getDrinkList();
    }

    //Adding sample data automatically
    public LiveData<List<DrinkEntity>> getDrinkList() {
        return mDrinks;
    }

    // Menu related methods

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllDrinks() {
        mRepository.deleteAllDrinks();
    }

    public void deleteDrink(int id) {
        mRepository.deleteDrink(id);
    }

}

