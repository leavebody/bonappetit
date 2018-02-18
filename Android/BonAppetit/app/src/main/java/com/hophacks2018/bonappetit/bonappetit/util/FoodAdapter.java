package com.hophacks2018.bonappetit.bonappetit.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hophacks2018.bonappetit.bonappetit.R;
import com.hophacks2018.bonappetit.bonappetit.activity.ResultActivity;
import com.hophacks2018.bonappetit.bonappetit.models.Food;

import java.util.ArrayList;

/**
 * Created by yujiaxiao on 2/17/18.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {

    //view holder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView photo;
        public TextView textView;
        private Context context;

        public MyViewHolder(View view, Context context) {
            super(view);
            this.photo = (ImageView) view.findViewById(R.id.foodImage);
            this.textView = (TextView) view.findViewById(R.id.foodName);
            this.context = context;
            view.setOnClickListener(this);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                final Food food = mFood.get(position);
/*
                final AlertDialog alertDialog = builder.create();
                LayoutInflater inflater = alertDialog.getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.layout_food_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(dialoglayout);
                builder.show();*/

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View dialogView = inflater.inflate(R.layout.layout_food_dialog,null);
                // Set the custom layout as alert dialog view
                builder.setView(dialogView);

                // Get the custom alert dialog view widgets reference
                final Button buttonOrder = (Button) dialogView.findViewById(R.id.buttonOrder);
                TextView textName = (TextView) dialogView.findViewById(R.id.foodName);
                TextView textDetail = (TextView) dialogView.findViewById(R.id.foodDetail);
                ImageView imageView = (ImageView) dialogView.findViewById(R.id.foodImage);

                textDetail.setText(food.getDescription());
                textName.setText(food.getName());
                //todo
                if (food.getImage() != null) {
                    Bitmap bitmap = StringToBitMap(food.getImage());
                    BitmapDrawable ob = new BitmapDrawable(getContext().getResources(), bitmap);
                    imageView.setBackgroundDrawable(ob);
                }
                else
                    imageView.setBackgroundResource(R.drawable.camera);

                // Create the alert dialog
                final AlertDialog dialog = builder.create();

                // Set negative/no button click listener
                buttonOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("order", "isClicked");
                        if (food.isOrdered()){
                            buttonOrder.setBackgroundResource(R.drawable.yes_gray);
                            //todo detele from database
                        }
                        else {
                            buttonOrder.setBackgroundResource(R.drawable.yes_green);
                            //Todo insert into database
                        }
                    }
                });

                dialog.show();
            }
        }
    }

    private ArrayList<Food> mFood;
    private Context mContext;

    public FoodAdapter(Context context, ArrayList<Food> mFoodList) {
        mFood = mFoodList;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_food_recyclerview, parent, false);

        return new MyViewHolder(itemView, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // Get the data model based on position
        Food food = mFood.get(position);

        // Set item views based on your views and data model
        BitmapDrawable ob = new BitmapDrawable(getContext().getResources(), food.getImage());
        holder.photo.setBackgroundDrawable(ob);
        holder.textView.setText(food.getRawName());

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mFood.size();
    }


    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

}
