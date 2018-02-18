package com.hophacks2018.bonappetit.bonappetit.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.hophacks2018.bonappetit.bonappetit.R;
import com.hophacks2018.bonappetit.bonappetit.models.Food;
import com.hophacks2018.bonappetit.bonappetit.util.FoodAdapter;

import java.util.ArrayList;

import static android.widget.LinearLayout.VERTICAL;

public class ResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Food> foodArrayList;
    private FoodAdapter foodAdapter;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        buttonNext = (Button) findViewById(R.id.nextButton);
        buttonNext.setOnClickListener(onClickListener);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        foodArrayList = new ArrayList<>();
        foodArrayList.add(new Food("mapo doufu", "spicy doufu ", "spicy", ""));
        foodArrayList.add(new Food("mapo potato", "spicy potato", "spicy, potato", ""));
        foodArrayList.add(new Food("potato chips", "Potato chips ", "potato, chips", ""));
        foodArrayList.add(new Food("sushi", "rice and fish ", "fish, raw, rice", ""));

        //set Adapter
        foodAdapter = new FoodAdapter(this, foodArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(foodAdapter);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            Intent nextActivity = new Intent(ResultActivity.this, RecommendActivity.class);
            startActivity(nextActivity);
            //slide from right to left
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
            System.gc();
        }
    };
}