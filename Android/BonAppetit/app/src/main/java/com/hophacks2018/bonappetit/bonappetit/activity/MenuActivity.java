package com.hophacks2018.bonappetit.bonappetit.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.hophacks2018.bonappetit.bonappetit.R;
import com.squareup.picasso.Picasso;

import java.io.File;

public class MenuActivity extends AppCompatActivity {

    private ImageView imageView;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        imagePath  = intent.getStringExtra("image");

        imageView = (ImageView) findViewById(R.id.Imgae);

        File f = new File(imagePath);
        Picasso.with(this).load(f).into(imageView);
    }

}
