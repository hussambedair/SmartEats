package com.example.android.smarteats.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.smarteats.API.Models.MealsResponse.Response;
import com.example.android.smarteats.R;

import java.util.List;

public class MealsRecyclerAdapter
        extends RecyclerView.Adapter<MealsRecyclerAdapter.ViewHolder> {

    private int lastSelectedPosition = -1;

    List<Response> mMeals;

    OnItemClickListener onMealClickListener;
    OnItemClickListener onRadioButtonListener;

    public void setOnRadioButtonListener(OnItemClickListener onRadioButtonListener) {
        this.onRadioButtonListener = onRadioButtonListener;
    }

    public void setOnMealClickListener(OnItemClickListener onMealClickListener) {
        this.onMealClickListener = onMealClickListener;
    }

    public MealsRecyclerAdapter(List<Response> mMeals) {
        this.mMeals = mMeals;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int postion) {
        final Response meal = mMeals.get(postion);
        viewHolder.mealName.setText(meal.getName());
        viewHolder.mealDescription.setText(meal.getDescription());

        //since only one radio button is allowed to be selected,
        // this condition un-checks previous selections
        viewHolder.selectionState.setChecked(lastSelectedPosition == postion);

        //attach image by url using Glide library
        Glide.with(viewHolder.itemView)
                .load(meal.getTagsUrl()).into(viewHolder.mealImage);

        if (onMealClickListener != null) {

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMealClickListener.onItemClick(postion,meal);
                }
            });

        }



    }

    @Override
    public int getItemCount() {
        if (mMeals == null) {
            return 0;
        }
        return mMeals.size();
    }

    public void changeData (List <Response> meals) {
        mMeals = meals;
        notifyDataSetChanged();

    }

    public Response getMealAtPosition (int position) {
        return mMeals.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mealName;
        TextView mealDescription;
        ImageView mealImage;
        RadioButton selectionState;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.name_text_view);
            mealDescription = itemView.findViewById(R.id.desc_text_view);
            mealImage = itemView.findViewById(R.id.food_image_view);
            selectionState= itemView.findViewById(R.id.radio_button_view);

            selectionState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });

        }
    }

    public interface OnItemClickListener {
        public void onItemClick(int pos, Response meal);
    }






}
