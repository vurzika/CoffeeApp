package com.viktorija.coffeelist.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.viktorija.coffeelist.utilities.SampleData;

import java.util.concurrent.Executors;

// If at least one method in the class is abstract, the whole class is abstract
@Database(entities = {DrinkEntity.class}, version = 1, exportSchema = false)
public abstract class DrinkDatabase extends RoomDatabase {

    // static - one database
    public static final String DATABASE_NAME = "Drinks.db";

    // Make the SightDatabase a singleton to prevent having multiple instances of the database opened at the same time.
    // volatile means it can only be referenced from main memory, Static - one instance. Method getInstance also is static.
    public static volatile DrinkDatabase instance;

    // abstract method, Room implements it.
    // public because it is rooms requirement
    public abstract DrinkDao drinkDao();

    public static DrinkDatabase getInstance(Context context) {
        if (instance == null) {
            // synchronization - to make sure that two parts of the application do not try to create it at the same time
            synchronized (RoomDatabase.class) {
                if(instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            DrinkDatabase.class, DATABASE_NAME)
                            // import initial data from the database
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                    importInitialData();
                                }
                            })
                            .build();
                }
            }
        }
        return instance;
    }

    private static void importInitialData() {
        Executors.newSingleThreadExecutor()
                .execute(() -> {
                    instance.drinkDao().deleteAll();
                    instance.drinkDao().insertAllDrinks(SampleData.getAllDrinks());
                });

    }
}
