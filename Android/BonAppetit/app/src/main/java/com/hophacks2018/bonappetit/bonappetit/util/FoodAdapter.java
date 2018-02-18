package com.hophacks2018.bonappetit.bonappetit.util;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hophacks2018.bonappetit.bonappetit.R;
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
                Toast.makeText(getContext(), food.getName(), Toast.LENGTH_SHORT).show();

                /*final AlertDialog alertDialog = builder.create();
                LayoutInflater inflater = alertDialog.getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.layout_food_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(dialoglayout);
                builder.show();*/

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                final AlertDialog alertDialog = builder.create();
                LayoutInflater inflater = alertDialog.getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.layout_food_dialog, null);

                builder.setView(dialoglayout);
                alertDialog.show();
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
}
