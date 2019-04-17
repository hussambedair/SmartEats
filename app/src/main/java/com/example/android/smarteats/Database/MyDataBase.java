package com.example.android.smarteats.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.smarteats.API.Models.MealsResponse.Owner;
import com.example.android.smarteats.API.Models.MealsResponse.Response;
import com.example.android.smarteats.Database.DAOs.MealsDao;

@Database(entities = {Response.class}, version = 3, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {

    public abstract MealsDao mealsDao();

    private static MyDataBase myDataBase;

    public static MyDataBase getInstance(Context context) {
        if (myDataBase == null) { //Create new object

            myDataBase= Room.databaseBuilder(context.getApplicationContext(),
                    MyDataBase.class, "Meals-DataBase")
                    .fallbackToDestructiveMigration()
                    //.allowMainThreadQueries() //this will allow us do the database calls in the main thread, but it is not a good practice
                    .build();

        }

        return myDataBase;


    }




}
