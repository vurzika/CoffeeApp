package com.viktorija.coffeelist.editor;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.viktorija.coffeelist.database.DrinkEntity;
import com.viktorija.coffeelist.database.Repository;

public class EditorViewModel extends AndroidViewModel {

    private Repository mRepository;

    public EditorViewModel(@NonNull Application application) {
        super(application);

        // using get Instance as Repository is singleton
        mRepository = Repository.getInstance(application.getApplicationContext());
    }

    // get drink from the database based on it's id
    public LiveData<DrinkEntity> loadDrinkById(int id) {
        return mRepository.loadDrinkById(id);
    }

    // Saving new drink into the database
    public void saveDrink(DrinkEntity drink) {
        // Checking, if the name line is empty, or not. If empty - do not add it to the database
        if (TextUtils.isEmpty(drink.getName())) {
            return;
        }
        mRepository.insertDrink(drink);
    }
    // Updating a drink
    public void update(DrinkEntity drink) {
        // Checking, if the name line is empty, or not. If empty - do not add it to the database
        mRepository.updateDrink(drink);
    }
}
