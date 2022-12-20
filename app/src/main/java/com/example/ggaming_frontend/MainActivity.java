package com.example.ggaming_frontend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {
        navigationView = findViewById(R.id.bottom_nav);
        navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.action_home:
                    Toast.makeText(this ,"This is home", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.action_my_games:
                    Toast.makeText(this ,"This is my games", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.action_cart:
                    Toast.makeText(this,"This is cart", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });

    }

}