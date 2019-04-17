package com.example.android.smarteats.API;

import com.example.android.smarteats.API.Models.MealsResponse.Response;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServices {

    @GET("orgs/google/repos")
    public Call<ArrayList<Response>> getMeals();


}
