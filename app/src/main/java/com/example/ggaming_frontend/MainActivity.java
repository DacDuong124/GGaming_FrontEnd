package com.example.ggaming_frontend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ggaming_frontend.components.GameCard;
import com.example.ggaming_frontend.models.Game;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private ViewPager viewPager;
    RecyclerView listGames;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();


    }




    private void initComponents() {
        viewPager = findViewById(R.id.view_pager);
        navigationView = findViewById(R.id.bottom_nav);
        navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.action_home:
                    viewPager.setCurrentItem(0);

                    break;
                case R.id.action_my_games:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.action_cart:
                    viewPager.setCurrentItem(2);
                    break;
            }
            return true;
        });

        setSwitchMenuItemScreen();

    }


    // ref: https://www.youtube.com/watch?v=oioIYszJWIs
    private void setSwitchMenuItemScreen(){
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        navigationView.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;

                    case 1:
                        navigationView.getMenu().findItem(R.id.action_my_games).setChecked(true);
                        break;

                    case 2:
                        navigationView.getMenu().findItem(R.id.action_cart).setChecked(true);
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}