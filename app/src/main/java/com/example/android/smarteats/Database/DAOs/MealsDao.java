package com.example.android.smarteats.Database.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.smarteats.API.Models.MealsResponse.Response;

import java.util.List;

@Dao
public interface MealsDao {


    @Insert (onConflict = OnConflictStrategy.REPLACE)
    public void addMeal (List<Response> item) ;

    @Delete
    public void removeMeal (Response item);

    @Update
    public void updateMeal (Response item);

    @Query("select * from Response;")
    public List<Response> getAllMeal();
}
