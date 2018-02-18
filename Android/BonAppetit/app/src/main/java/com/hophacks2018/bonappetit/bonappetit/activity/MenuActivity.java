package com.hophacks2018.bonappetit.bonappetit.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.vision.text.TextBlock;
import com.hophacks2018.bonappetit.bonappetit.R;
import com.hophacks2018.bonappetit.bonappetit.util.Globals;
import com.hophacks2018.bonappetit.bonappetit.util.GraphicOverlay;
import com.hophacks2018.bonappetit.bonappetit.util.OcrGraphic;
import com.squareup.picasso.Picasso;

import java.io.File;

public class MenuActivity extends AppCompatActivity {

    private ImageView imageView;
    private String imagePath;
    private RelativeLayout relativeLayout;
    private SparseArray<TextBlock> sparseArray;
    private GraphicOverlay<OcrGraphic> mGraphicOverlay;

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        imagePath  = intent.getStringExtra("image");

        imageView = (ImageView) findViewById(R.id.Imgae);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        relativeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        mGraphicOverlay = (GraphicOverlay<OcrGraphic>) findViewById(R.id.graphicOverlay);

        gestureDetector = new GestureDetector(this, new CaptureGestureListener());

        //set the background image
        File f = new File(imagePath);
        Picasso.with(this).load(f).into(imageView);

        Globals globals = (Globals)getApplication();
        sparseArray =  (SparseArray<TextBlock>) globals.getTextBlockSparseArray();

        if (sparseArray == null)
        {
            Log.d("MenuActivity", "sparse null");
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        for (int i = 0; i < sparseArray.size(); i++) {
            TextBlock textBlock = (TextBlock) globals.getTextBlockSparseArray().valueAt(i);
            if (textBlock != null && textBlock.getValue() != null) {
                OcrGraphic ocrGraphic = new OcrGraphic(mGraphicOverlay, textBlock);
                mGraphicOverlay.add(ocrGraphic);
            }
            /*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            if (textBlock == null)
            {
                Log.d("MenuActivity", "textBlock null");
            }

            RectF rect = new RectF(textBlock.getBoundingBox());
            *//*rect.left = translateX(rect.left);
            rect.top = translateY(rect.top);
            rect.right = translateX(rect.right);
            rect.bottom = translateY(rect.bottom);*//*

            Button btn = new Button(this);
            btn.setTop((int)rect.top);
            btn.setLeft((int)rect.left);
            btn.setHeight((int) (rect.top - rect.bottom));
            btn.setWidth((int) (rect.right - rect.left));

            btn.setId(i);
            final int id_ = btn.getId();
            btn.setText("button " + id_);
            btn.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            relativeLayout.addView(btn, params);
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),
                            "Button clicked index = " + id_, Toast.LENGTH_SHORT).show();
                }
            });*/
        }
    }
    /**
     *
     * @param rawX - the raw position of the tap
     * @param rawY - the raw position of the tap.
     * @return true if the tap was on a TextBlock
     */
    private boolean onTap(float rawX, float rawY) {
        OcrGraphic graphic = mGraphicOverlay.getGraphicAtLocation(rawX, rawY);
        TextBlock text = null;
        if (graphic != null) {
            text = graphic.getTextBlock();
            if (text != null && text.getValue() != null) {
                Log.d("tap", "text data is being spoken! " + text.getValue());
            }
            else {
                Log.d("tap", "text data is null");
            }
        }
        else {
            Log.d("tap","no text detected");
        }
        return text != null;
    }

    private class CaptureGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return onTap(e.getRawX(), e.getRawY()) || super.onSingleTapConfirmed(e);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        boolean c = gestureDetector.onTouchEvent(e);

        return c || super.onTouchEvent(e);
    }

}
