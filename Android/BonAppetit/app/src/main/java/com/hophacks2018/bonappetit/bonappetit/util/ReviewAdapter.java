package com.hophacks2018.bonappetit.bonappetit.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hophacks2018.bonappetit.bonappetit.R;
import com.hophacks2018.bonappetit.bonappetit.models.Food;

import java.util.ArrayList;

/**
 * Created by yujiaxiao on 2/18/18.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder>{

//view holder
public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView photo;
    public TextView textView;
    public RatingBar ratingBar;
    private Context context;
    private HistoryDBHelper historyDBHelper;

    public MyViewHolder(View view, Context context) {
        super(view);
        this.photo = (ImageView) view.findViewById(R.id.foodImage);
        this.textView = (TextView) view.findViewById(R.id.foodName);
        this.ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        this.context = context;
        view.setOnClickListener(this);
    }

    // Handles the row being being clicked
    @Override
    public void onClick(View view) {
        int position = getAdapterPosition(); // gets item position
        if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
            final Food food = mFood.get(position);

            food.setRate(ratingBar.getRating());
            //todo delete from database, training
            historyDBHelper = HistoryDBHelper.getInstance(getContext());
            historyDBHelper.delete(food.getName());
        }
    }
}

    private ArrayList<Food> mFood;
    private Context mContext;

    public ReviewAdapter(Context context, ArrayList<Food> mFoodList) {
        mFood = mFoodList;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }


    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_review_adapter, parent, false);

        return new ReviewAdapter.MyViewHolder(itemView, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final ReviewAdapter.MyViewHolder holder, int position) {
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


    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }

    }
}
