package com.example.ggaming_frontend;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.example.ggaming_frontend.components.GameCard;
import com.example.ggaming_frontend.models.Category;
import com.example.ggaming_frontend.models.Game;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class CategoryListActivity extends AppCompatActivity {
    RecyclerView listGames;

    private FirebaseFirestore db;

    private ArrayList<Category> categories = new ArrayList<>();

    private ArrayList<Game> games = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        db = FirebaseFirestore.getInstance();
        initComponents();
        fetchCategories();
//        loadGames();

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

    private void fetchGames() {
        db.collection(Constants.FSGames.gamesCollection)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ArrayList<Category> curCategories = new ArrayList<>();
                                ArrayList<String> categoriesIdList = (ArrayList<String>) document.get(Constants.FSGames.categoriesField);
                                for (String categoryId : categoriesIdList) {
                                    Category category = Category.getCategoryById(categories, categoryId);
                                    curCategories.add(category);
                                }
                                Game game = new Game(
                                        Objects.requireNonNull(document.get(Constants.FSGames.titleField)).toString(), document.get(Constants.FSGames.priceField).toString(), curCategories, document.get(Constants.FSGames.descField).toString(), document.get(Constants.FSGames.imgField).toString());
                                games.add(game);
                            }
                            renderGames();
                        } else {
                            Toast.makeText(CategoryListActivity.this,
                                    "Failed to get list of categories!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void fetchCategories() {
        db.collection(Constants.FSCategories.categoriesCollection)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Category category = document.toObject(Category.class);
                                categories.add(category);
                            }
                            fetchGames();
                        } else {
                            Toast.makeText(CategoryListActivity.this,
                                    "Failed to get list of categories!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private ArrayList<Game> filterGamesByCategory() {
        Intent intent = getIntent();
        String filterCategory = (String) intent.getExtras().get("category");
        System.out.println("ccccc: " + filterCategory);
        ArrayList<Game> filteredGames = new ArrayList<>();

        for (Game game : games) {
            ArrayList<Category> gameCategories = game.getCategories();
            System.out.println("clmmm: " + gameCategories);
            for (Category category : gameCategories) {
                if (category.getTitle().equals(filterCategory))
                    filteredGames.add(game);
            }
        }

        return filteredGames;
    }

    private void renderGames() {
        LinearLayoutManager layoutManager= new LinearLayoutManager(CategoryListActivity.this,LinearLayoutManager.VERTICAL, false);
        listGames.setLayoutManager(layoutManager);
        listGames.addItemDecoration(new VerticalSpaceItemDecoration(16) );
        ArrayList<Game> filteredGames = filterGamesByCategory();
        GameCard gameCardView = new GameCard(CategoryListActivity.this , filteredGames);
        listGames.setAdapter(gameCardView);
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