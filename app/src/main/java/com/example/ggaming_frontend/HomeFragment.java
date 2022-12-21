package com.example.ggaming_frontend;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ggaming_frontend.components.CategoryCard;
import com.example.ggaming_frontend.components.GameCard;
import com.example.ggaming_frontend.models.Category;
import com.example.ggaming_frontend.models.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class HomeFragment extends Fragment {
    View root;
    RecyclerView listGames;
    RecyclerView listCategories;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadGames();
        loadCategories();
    }


    private void loadCategories() {
        GridLayoutManager gridLayoutManager= new GridLayoutManager(HomeFragment.this.getContext(), 2);
        listCategories.setLayoutManager(gridLayoutManager);
        listCategories.addItemDecoration(new VerticalSpaceItemDecoration(16) );

        try {
            ArrayList<Category> categoriesArray = new ArrayList<Category>();
            JSONObject obj = new JSONObject(loadJSONFromAsset("categories.json"));
            JSONArray CategoriesJsonArray = obj.getJSONArray("categories");

            for (int i = 0; i < CategoriesJsonArray.length(); i++) {
                Category categoryObj = new Category(CategoriesJsonArray.getJSONObject(i));
                categoriesArray.add(categoryObj);

            }
            CategoryCard categoryCard = new CategoryCard(HomeFragment.this.getContext() , categoriesArray);
            listCategories.setAdapter(categoryCard);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void loadGames() {
        LinearLayoutManager layoutManager= new LinearLayoutManager(HomeFragment.this.getContext(),LinearLayoutManager.VERTICAL, false);
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
            GameCard gameCardView = new GameCard(HomeFragment.this.getContext() , topSellerGameArray);
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_home, container, false);
        listGames = root.findViewById(R.id.listTopSellerGames);
        listCategories = root.findViewById(R.id.listCategories);
        return root;
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