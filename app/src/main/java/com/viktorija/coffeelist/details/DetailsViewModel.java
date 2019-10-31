package com.viktorija.coffeelist.details;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.viktorija.coffeelist.database.DrinkEntity;
import com.viktorija.coffeelist.database.Repository;

public class DetailsViewModel extends AndroidViewModel {

    private Repository mRepository;

    public DetailsViewModel(@NonNull Application application) {
        super(application);

        // using get Instance as Repository is singleton
        mRepository = Repository.getInstance(application.getApplicationContext());
    }

    // get drink from the database based on it's id
    public LiveData<DrinkEntity> loadDrinkById(int id) {
        return mRepository.loadDrinkById(id);
    }
}
