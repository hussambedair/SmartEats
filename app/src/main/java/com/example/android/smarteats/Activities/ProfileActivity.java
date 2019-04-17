package com.example.android.smarteats.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.android.smarteats.API.APIManager;
import com.example.android.smarteats.API.APIServices;
import com.example.android.smarteats.API.Models.MealsResponse.Response;
import com.example.android.smarteats.Adapters.MealsRecyclerAdapter;
import com.example.android.smarteats.Base.BaseActivity;
import com.example.android.smarteats.Database.MyDataBase;
import com.example.android.smarteats.MealsDialogFragment;
import com.example.android.smarteats.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ProfileActivity extends BaseActivity {

    RecyclerView mealsRecyclerView;
    MealsRecyclerAdapter mealsAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Response> meals = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mealsRecyclerView = findViewById(R.id.meals_recycler_view);
        mealsAdapter = new MealsRecyclerAdapter(null);
        mealsAdapter.setOnMealClickListener(new MealsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, Response meal) {

                MealsDialogFragment mealsDialogFragment =new MealsDialogFragment();
                mealsDialogFragment.setmUserName(meal.getName());
                mealsDialogFragment.setmDescreption(meal.getDescription());
                mealsDialogFragment.show(getSupportFragmentManager(), "meal");

            }
        });

        layoutManager = new LinearLayoutManager(ProfileActivity.this);

        mealsRecyclerView.setAdapter(mealsAdapter);
        mealsRecyclerView.setLayoutManager(layoutManager);

        getData();



        // Add the functionality to swipe items in the
        // recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                        int position = viewHolder.getAdapterPosition();
                        final Response myResponse = mealsAdapter.getMealAtPosition(position);




                        //Using confirmation message:

                        showConfirmationMessage(R.string.warning, R.string.are_you_sure,
                                R.string.yes, new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        // Delete the item
                                        MyDataBase.getInstance(ProfileActivity.this)
                                                .mealsDao()
                                                .removeMeal(myResponse);

                                        List<Response> items = MyDataBase.getInstance(ProfileActivity.this)
                                                .mealsDao()
                                                .getAllMeal();



                                        mealsAdapter.changeData(items);


                                    }
                                }, R.string.no, new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        //dismess
                                        dialog.dismiss();
                                        List<Response> items = MyDataBase.getInstance(ProfileActivity.this)
                                                .mealsDao()
                                                .getAllMeal();

                                        mealsAdapter.changeData(items);

                                    }
                                }).setCancelable(false);


                    }
                });
        helper.attachToRecyclerView(mealsRecyclerView);




    }

    private void getData() {
        APIServices apiService = APIManager.getAPIs();
        apiService.getMeals().enqueue(new Callback<ArrayList<Response>>() {
            @Override
            public void onResponse(Call<ArrayList<Response>> call, retrofit2.Response<ArrayList<Response>> response) {

                Log.i("response","------success");
                meals=response.body();
                mealsAdapter.changeData(meals);

                InsertMealsIntoDataBase thread = new InsertMealsIntoDataBase(meals);

                thread.start();
            }

            @Override
            public void onFailure(Call<ArrayList<Response>> call, Throwable t) {

            }
        });

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    class InsertMealsIntoDataBase extends Thread {

        List<Response> myMeals;

        public InsertMealsIntoDataBase(List<Response> meals) {
            myMeals = meals;

        }

        @Override
        public void run() {

            MyDataBase.getInstance(ProfileActivity.this)
                    .mealsDao()
                    .addMeal(myMeals);

            Log.e("sources thread", "Insertion success");


        }
    }
}
