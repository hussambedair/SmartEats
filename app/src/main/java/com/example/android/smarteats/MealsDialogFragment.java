package com.example.android.smarteats;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MealsDialogFragment extends DialogFragment {

    String mUserName;
    String mDescreption;



    public MealsDialogFragment() {

    }

    View view;

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public void setmDescreption(String mDescreption) {
        this.mDescreption = mDescreption;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_meal_item,
                container,false);

        TextView nameTextView = view.findViewById(R.id.name);
        nameTextView.setText(mUserName);
        TextView descTextView = view.findViewById(R.id.desc);
        descTextView.setText(mDescreption);





        return view;
    }
}
