package com.example.musictraining;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GameWon extends AppCompatActivity {

    SharedPreferences sharedata;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_won);
    }

    public void doneAndback(View view) {
        sharedata = getSharedPreferences("award", MODE_PRIVATE);
        editor = sharedata.edit();//获取Editor

        editor.putBoolean("finish", true);
        editor.commit();

        Intent intent = new Intent(GameWon.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
