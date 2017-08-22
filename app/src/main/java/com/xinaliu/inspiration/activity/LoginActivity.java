package com.xinaliu.inspiration.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xinaliu.inspiration.R;

public class LoginActivity extends AppCompatActivity {

    public static void startActivity(Context context){
        context.startActivity(new Intent(context,LoginActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
