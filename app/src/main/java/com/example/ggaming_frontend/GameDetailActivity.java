package com.example.ggaming_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class GameDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        initComponents();
    }

    private void initComponents() {
        ImageView backActionIcon = findViewById(R.id.backActionIcon);
        backActionIcon.setOnClickListener(view -> {
            Intent intent = new Intent();
            setResult(200, intent);
            finish();

        });

    }
}