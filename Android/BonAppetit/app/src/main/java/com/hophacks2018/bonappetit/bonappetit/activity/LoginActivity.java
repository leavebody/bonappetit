package com.hophacks2018.bonappetit.bonappetit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hophacks2018.bonappetit.bonappetit.R;
import com.hophacks2018.bonappetit.bonappetit.service.ApiService;
import com.hophacks2018.bonappetit.bonappetit.service.ModelResult;
import com.hophacks2018.bonappetit.bonappetit.service.ServiceCallBack;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }
}
