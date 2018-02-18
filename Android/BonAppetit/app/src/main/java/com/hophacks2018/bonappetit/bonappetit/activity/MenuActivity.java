package com.hophacks2018.bonappetit.bonappetit.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.vision.text.TextBlock;
import com.hophacks2018.bonappetit.bonappetit.R;
import com.hophacks2018.bonappetit.bonappetit.models.Food;
import com.hophacks2018.bonappetit.bonappetit.util.Globals;
import com.hophacks2018.bonappetit.bonappetit.util.GraphicOverlay;
import com.hophacks2018.bonappetit.bonappetit.util.HistoryDBHelper;
import com.hophacks2018.bonappetit.bonappetit.util.OcrGraphic;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



public class MenuActivity extends AppCompatActivity {

    private ImageView imageView;
    private String imagePath;
    private RelativeLayout relativeLayout;
    private SparseArray<TextBlock> sparseArray;
    private GraphicOverlay<OcrGraphic> mGraphicOverlay;
    private Button buttonNext;
    private Button buttonPre;

    private GestureDetector gestureDetector;

    private HistoryDBHelper historyDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Globals globals = (Globals) getApplication();
        historyDBHelper = HistoryDBHelper.getInstance(this);

        buttonNext = (Button) findViewById(R.id.nextButton);
        buttonNext.setOnClickListener(onClickListener);
        buttonPre = (Button) findViewById(R.id.buttonPre);
        buttonPre.setOnClickListener(onClickListenerPre);

        imagePath = globals.getImagePath();

        imageView = (ImageView) findViewById(R.id.Imgae);
        /*relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        relativeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        */
        mGraphicOverlay = (GraphicOverlay<OcrGraphic>) findViewById(R.id.graphicOverlay);

        gestureDetector = new GestureDetector(this, new CaptureGestureListener());

        //set the background image
        File f = new File(imagePath);
        Picasso.with(this).load(f).into(imageView);


        sparseArray = (SparseArray<TextBlock>) globals.getTextBlockSparseArray();

        if (sparseArray == null) {
            Log.d("MenuActivity", "sparse null");
        }

        /*DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;*/

        for (int i = 0; i < sparseArray.size(); i++) {
            TextBlock textBlock = (TextBlock) globals.getTextBlockSparseArray().valueAt(i);
            if (textBlock != null && textBlock.getValue() != null  && !textBlock.getValue().contains("\n")) {
                OcrGraphic ocrGraphic = new OcrGraphic(mGraphicOverlay, textBlock);
                mGraphicOverlay.add(ocrGraphic);
            }
        }
    }

    /**
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
                Globals globals = (Globals) getApplication();
                ArrayList<Food> foodArrayList = (ArrayList<Food>) globals.getScanResult().getAllFoods();

                Food myFood = null;
                for (Food food : foodArrayList) {
                    if (food.getRawName().equals(text.getValue())) {
                        myFood = food;
                    }
                }
                showDialog(myFood);
                Log.d("tap", "text data is being spoken! " + text.getValue());
            } else {
                Log.d("tap", "text data is null");
            }
        } else {
            Log.d("tap", "no text detected");
        }
        return text != null;
    }

    private void showDialog(final Food food) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.layout_food_dialog, null);
        // Set the custom layout as alert dialog view
        builder.setView(dialogView);

        // Get the custom alert dialog view widgets reference
        final Button buttonOrder = (Button) dialogView.findViewById(R.id.buttonOrder);
        TextView textName = (TextView) dialogView.findViewById(R.id.foodName);
        TextView textDetail = (TextView) dialogView.findViewById(R.id.foodDetail);
        final ImageView imageView = (ImageView) dialogView.findViewById(R.id.foodImage);

        textDetail.setText(food.getDescription());
        textName.setText(food.getName());

        if (food.getImage() != null) {
            ImageView mImageView;
            String url = food.getImage();
            Log.d("imageurl", url);

// Retrieves an image specified by the URL, displays it in the UI.
            ImageRequest request = new ImageRequest(url,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            imageView.setImageBitmap(bitmap);
                        }
                    }, 0, 0, null,
                    new Response.ErrorListener() {
                @Override
                        public void onErrorResponse(VolleyError error) {
                imageView.setImageResource(R.drawable.camera);
                        }
                    });
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);

        } else {
            imageView.setBackgroundResource(R.drawable.camera);
        }


        // Create the alert dialog
        final AlertDialog dialog = builder.create();

        // Set negative/no button click listener
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("order", "isClicked");
                if (food.isOrdered()){
                    buttonOrder.setBackgroundResource(R.drawable.yes_gray);
                    //todo detele from database Name???? RawName??
                    historyDBHelper.delete(food.getRawName());
                }
                else {
                    buttonOrder.setBackgroundResource(R.drawable.yes_green);
                    //Todo insert into database
                    Date currentTime = Calendar.getInstance().getTime();
                    historyDBHelper.insert(food.getRawName(), food.getImage(), currentTime, food.getFeatureVector());
                }
            }
        });

        dialog.show();
    }

    private Bitmap StringToBitMap(String image) {
        try {
            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
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

    View.OnClickListener onClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            Intent nextActivity = new Intent(MenuActivity.this, RecommendActivity.class);
            startActivity(nextActivity);
            //slide from right to left
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
            System.gc();
        }
    };

    View.OnClickListener onClickListenerPre = new View.OnClickListener() {

        public void onClick(View v) {
            Intent nextActivity = new Intent(MenuActivity.this, CameraActivity.class);
            startActivity(nextActivity);
            finish();
            System.gc();
        }
    };

}
