package com.hophacks2018.bonappetit.bonappetit.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.hophacks2018.bonappetit.bonappetit.R;
import com.hophacks2018.bonappetit.bonappetit.models.Food;
import com.hophacks2018.bonappetit.bonappetit.models.ReviewObj;
import com.hophacks2018.bonappetit.bonappetit.util.FoodAdapter;
import com.hophacks2018.bonappetit.bonappetit.util.HistoryDBHelper;
import com.hophacks2018.bonappetit.bonappetit.util.ReviewAdapter;
import com.hophacks2018.bonappetit.bonappetit.vector.Preference;

import java.text.ParseException;
import java.util.ArrayList;

import static android.widget.LinearLayout.VERTICAL;

public class ReviewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Food> reviewList = new ArrayList<>();
    private ReviewObj[] reviewObjs;
    private ReviewAdapter reviewAdapter;
    private Button buttonPre;
    private HistoryDBHelper historyDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        historyDBHelper = HistoryDBHelper.getInstance(this);
        try {
            reviewObjs = (ReviewObj[]) historyDBHelper.getTen();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        for (ReviewObj reviewObj : reviewObjs){
            if (reviewObj != null)
                reviewList.add(new Food(reviewObj.name, reviewObj.path, reviewObj.features));
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        buttonPre = (Button) findViewById(R.id.preButton);

        buttonPre.setOnClickListener(onClickListener);

        reviewList.add(new Food("mapo doufu", "spicy doufu ", "spicy", ""));
        reviewList.add(new Food("mapo potato", "spicy potato", "spicy, potato", ""));
        reviewList.add(new Food("potato chips", "Potato chips ", "potato, chips", ""));
        reviewList.add(new Food("sushi", "rice and fish ", "fish, raw, rice", ""));

        //set Adapter
        reviewAdapter = new ReviewAdapter(this, reviewList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),  VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(reviewAdapter);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            //delete from database, training
            Preference preference = new Preference();
            historyDBHelper = HistoryDBHelper.getInstance(getApplicationContext());
            for (Food food : reviewList){
                if (food.getRate() != 0) {
                    preference.updateVec(food, food.getRate()/5.0 * 2 - 1);
                    historyDBHelper.delete(food.getRawName());
                }
            }

            Intent nextActivity = new Intent(ReviewActivity.this, CameraActivity.class);
            startActivity(nextActivity);
            finish();
            System.gc();
        }
    };
}
