package com.example.ggaming_frontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.example.ggaming_frontend.components.GameCard;
import com.example.ggaming_frontend.components.WishListCard;
import com.example.ggaming_frontend.models.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    RecyclerView listWishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initComponents();
        loadWishListItems();
    }

    private void initComponents() {
        listWishlist = findViewById(R.id.listWishList);

        ImageView backActionIcon = findViewById(R.id.backActionIcon);
        backActionIcon.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            setResult(100, intent);
            finish();

        });


    }

    private void loadWishListItems() {
        LinearLayoutManager layoutManager= new LinearLayoutManager(ProfileActivity.this,LinearLayoutManager.VERTICAL, false);
        listWishlist.setLayoutManager(layoutManager);
        listWishlist.addItemDecoration(new ProfileActivity.VerticalSpaceItemDecoration(16) );

        try {
            ArrayList<Game> wishListGameArray = new ArrayList<Game>();
            JSONObject obj = new JSONObject(loadJSONFromAsset("games.json"));
            JSONArray GamesJsonArray = obj.getJSONArray("games");

            for (int i = 0; i < GamesJsonArray.length(); i++) {
                Game GameObj = new Game(GamesJsonArray.getJSONObject(i));
                wishListGameArray.add(GameObj);

            }
            WishListCard gameCardView = new WishListCard(ProfileActivity.this, wishListGameArray);
            listWishlist.setAdapter(gameCardView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //load game data from local json file
    private String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = getResources().getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }



    //  ref: https://stackoverflow.com/questions/24618829/how-to-add-dividers-and-spaces-between-items-in-recyclerview
    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int verticalSpaceHeight;

        public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;
        }
    }
}