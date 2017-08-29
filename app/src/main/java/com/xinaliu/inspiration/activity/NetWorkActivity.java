package com.xinaliu.inspiration.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.xinaliu.inspiration.R;

public class NetWorkActivity extends Activity implements View.OnClickListener{


    public static void startActivityForResult(Activity context, int requestCode){
        Intent intent = new Intent(context, NetWorkActivity.class);
        context.startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.hint_network, null);
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        view.findViewById(R.id.tv_continue).setOnClickListener(this);
        normalDialog.setView(view);
        normalDialog.show();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_cancel){
            setResult(Activity.RESULT_CANCELED);
        }else if (v.getId() == R.id.tv_continue){
            setResult(Activity.RESULT_OK);
        }
        onBackPressed();
    }
}
