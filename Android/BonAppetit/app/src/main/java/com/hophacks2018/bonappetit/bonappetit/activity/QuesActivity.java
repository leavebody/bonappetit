package com.hophacks2018.bonappetit.bonappetit.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hophacks2018.bonappetit.bonappetit.R;
import com.hophacks2018.bonappetit.bonappetit.models.Food;
import com.hophacks2018.bonappetit.bonappetit.models.ReviewObj;
import com.hophacks2018.bonappetit.bonappetit.util.Globals;
import com.hophacks2018.bonappetit.bonappetit.util.HistoryDBHelper;
import com.hophacks2018.bonappetit.bonappetit.util.ReviewAdapter;
import com.hophacks2018.bonappetit.bonappetit.vector.Preference;

import java.util.ArrayList;

import static android.widget.LinearLayout.VERTICAL;

public class QuesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Food> reviewList = new ArrayList<>();
    private ReviewObj[] reviewObjs;
    private ReviewAdapter reviewAdapter;
    private Button buttonNext;
    private HistoryDBHelper historyDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques);

        Globals globals = (Globals) getApplication();
        globals.getQuesList();

        /*for (ReviewObj reviewObj : reviewObjs){
            if (reviewObj != null)
                reviewList.add(new Food(reviewObj.name, reviewObj.path, reviewObj.features));
        }*/

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        buttonNext = (Button) findViewById(R.id.nextButton);

        buttonNext.setOnClickListener(onClickListener);

        /*reviewList.add(new Food("mapo doufu", "spicy doufu ", "spicy", ""));
        reviewList.add(new Food("mapo potato", "spicy potato", "spicy, potato", ""));
        reviewList.add(new Food("potato chips", "Potato chips ", "potato, chips", ""));
        reviewList.add(new Food("sushi", "rice and fish ", "fish, raw, rice", ""));
*/
        //set Adapter
        reviewAdapter = new ReviewAdapter(this, globals.getQuesList());
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
            for (Food food : reviewList){
                if (food.getRate() != 0) {
                    preference.updateVec(food, food.getRate()/5.0 * 2 - 1);
                }
            }

            Intent nextActivity = new Intent(QuesActivity.this, CameraActivity.class);
            startActivity(nextActivity);
            finish();
            System.gc();
        }
    };

}
