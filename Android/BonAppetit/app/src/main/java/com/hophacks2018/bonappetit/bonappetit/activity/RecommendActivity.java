package com.hophacks2018.bonappetit.bonappetit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.hophacks2018.bonappetit.bonappetit.R;
import com.hophacks2018.bonappetit.bonappetit.models.Food;
import com.hophacks2018.bonappetit.bonappetit.util.FoodAdapter;
import com.hophacks2018.bonappetit.bonappetit.util.Globals;
import com.hophacks2018.bonappetit.bonappetit.vector.Preference;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import static android.widget.LinearLayout.VERTICAL;

public class RecommendActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Food> foodArrayList;
    private FoodAdapter foodAdapter;
    private Button buttonPre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        Globals globals = (Globals) getApplication();
        foodArrayList = globals.getScanResult().getAllFoods();
        this.sort();
        buttonPre = (Button) findViewById(R.id.preButton);
        buttonPre.setOnClickListener(onClickListener);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        foodArrayList = new ArrayList<>();
//        foodArrayList.add(new Food("mapo doufu", "spicy doufu ", "spicy", ""));
//        foodArrayList.add(new Food("mapo potato", "spicy potato", "spicy, potato", ""));
//        foodArrayList.add(new Food("potato chips", "Potato chips ", "potato, chips", ""));
//        foodArrayList.add(new Food("sushi", "rice and fish ", "fish, raw, rice", ""));

        //set Adapter
        foodAdapter = new FoodAdapter(this, foodArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),  VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(foodAdapter);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            Intent nextActivity = new Intent(RecommendActivity.this, MenuActivity.class);
            startActivity(nextActivity);
            //push from bottom to top
            //overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            //slide from right to left
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
            System.gc();
        }
    };

    private void sort () {
        Preference kkk = new Preference();
        double[] store = new double[foodArrayList.size()];
        TreeMap<Double, Food> sorted = new TreeMap<>();
        for (Food fd : foodArrayList) {
            double storeobj = kkk.computeDot(fd.getFeatureVector());
            sorted.put(storeobj, fd);
        }
        ArrayList<Food> result = new ArrayList<>();
        for (Map.Entry<Double, Food> entry : sorted.entrySet()) {
            result.add(entry.getValue());
        }
        foodArrayList = result;
    }

}
