package com.hophacks2018.bonappetit.bonappetit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

import com.hophacks2018.bonappetit.bonappetit.R;
import com.hophacks2018.bonappetit.bonappetit.models.Food;
import com.hophacks2018.bonappetit.bonappetit.util.FoodAdapter;

import java.util.ArrayList;

import static android.widget.LinearLayout.VERTICAL;

public class ResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Food> foodArrayList;
    private FoodAdapter foodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //set Adapter
        foodAdapter = new FoodAdapter(this, foodArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),  VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(foodAdapter);

        /*gestureLayout.setSwipeGestureListener(new onSwipeGestureListener() {

            @Override
            public void onRightSwipe() {
                Log.i("debug", "onRightSwipe");
            }

            @Override
            public void onLeftSwipe() {
                Log.i("debug", "onLeftSwipe");
            }
        });*/

    }

   /* @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = mTempX = (int) ev.getRawX();
                mDownY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getRawX();
                // 满足此条件屏蔽SildingFinishLayout里面子类的touch事件
                if (Math.abs(moveX - mDownX) > mTouchSlop
                        && Math.abs((int) ev.getRawY() - mDownY) < mTouchSlop) {
                    return true;
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getRawX();
                int deltaX = mTempX - moveX;
//          Log.i("debug", "deltaX:" + deltaX + "mTouchSlop:" + mTouchSlop);
                mTempX = moveX;
                if (Math.abs(moveX - mDownX) > mTouchSlop
                        && Math.abs((int) event.getRawY() - mDownY) < mTouchSlop) {
                    isSilding = true;
                }

                if (Math.abs(moveX - mDownX) >= 0 && isSilding) {
//              mContentView.scrollBy(deltaX, 0);
                    totalMoveX += deltaX;
                }
                break;
            case MotionEvent.ACTION_UP:
                isSilding = false;
//          Log.i("debug", "TotoalMoveX:" + totalMoveX + "viewVidth:" + viewWidth);
                if (Math.abs(totalMoveX) >= viewWidth / 3) {
                    if (totalMoveX > 0) {
                        swipeListener.onLeftSwipe();
                    }else {
                        swipeListener.onRightSwipe();
                    }
                }
                totalMoveX = 0;
                break;
        }

        return true;
    }*/
}
