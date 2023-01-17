package com.example.ggaming_frontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ggaming_frontend.components.GameCard;
import com.example.ggaming_frontend.models.Category;
import com.example.ggaming_frontend.models.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CategoryListActivity extends AppCompatActivity {
    RecyclerView listGames;
    private ArrayList<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        initComponents();
        loadGames();

        LinearLayout filterByPrice = (LinearLayout) findViewById(R.id.filterByPrice);

        filterByPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

//    public CategoryListActivity(ArrayList<Category> categories,int position) {
//        this.categories = categories ;
//        TextView categoryTitle = findViewById(R.id.gameCategoryTitle);
//        categoryTitle.setText(categories.get(position).getTitle());
//    }

    private void initComponents() {
        listGames = findViewById(R.id.listGameCategory);
        ImageView backActionIcon2 = findViewById(R.id.backActionIcon2);
        backActionIcon2.setOnClickListener(view -> {
            Intent intent = new Intent();
            setResult(200, intent);
            finish();

        });

        Intent intent = getIntent();
        String categoryTitle = (String) intent.getExtras().get("category");
        TextView categoryGameTitle = findViewById(R.id.gameCategoryTitle);
        categoryGameTitle.setText(categoryTitle);


    }

    private void loadGames() {
        LinearLayoutManager layoutManager= new LinearLayoutManager(CategoryListActivity.this,LinearLayoutManager.VERTICAL, false);
        listGames.setLayoutManager(layoutManager);
        listGames.addItemDecoration(new VerticalSpaceItemDecoration(16) );

        try {
            ArrayList<Game> topSellerGameArray = new ArrayList<Game>();
            JSONObject obj = new JSONObject(loadJSONFromAsset("games.json"));
            JSONArray GamesJsonArray = obj.getJSONArray("games");

            for (int i = 0; i < GamesJsonArray.length(); i++) {
                Game GameObj = new Game(GamesJsonArray.getJSONObject(i));
                topSellerGameArray.add(GameObj);

            }
            GameCard gameCardView = new GameCard(CategoryListActivity.this , topSellerGameArray);
            listGames.setAdapter(gameCardView);
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