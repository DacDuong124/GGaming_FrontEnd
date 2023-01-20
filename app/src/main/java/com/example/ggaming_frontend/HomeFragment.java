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
import android.widget.Toast;

import com.example.ggaming_frontend.components.CategoryCard;
import com.example.ggaming_frontend.components.GameCard;
import com.example.ggaming_frontend.models.Category;
import com.example.ggaming_frontend.models.Game;
import com.example.ggaming_frontend.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;


public class HomeFragment extends Fragment {
    View root;
    RecyclerView listGames;
    RecyclerView listCategories;

    private ArrayList<Category> categories = new ArrayList<>();

    private ArrayList<Game> games = new ArrayList<>();

    private FirebaseFirestore db;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();
        fetchCategories();
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
                            Toast.makeText(getActivity(),
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
                            renderCategories();
                        } else {
                            Toast.makeText(getActivity(),
                                    "Failed to get list of categories!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void renderCategories() {
        GridLayoutManager gridLayoutManager= new GridLayoutManager(HomeFragment.this.getContext(), 2);
        listCategories.setLayoutManager(gridLayoutManager);
        listCategories.addItemDecoration(new VerticalSpaceItemDecoration(16) );
        CategoryCard categoryCard = new CategoryCard(HomeFragment.this.getContext() , categories);
        listCategories.setAdapter(categoryCard);
    }


    private void renderGames() {
        LinearLayoutManager layoutManager= new LinearLayoutManager(HomeFragment.this.getContext(),LinearLayoutManager.VERTICAL, false);
        listGames.setLayoutManager(layoutManager);
        listGames.addItemDecoration(new VerticalSpaceItemDecoration(16) );
        GameCard gameCardView = new GameCard(HomeFragment.this.getContext() , games);
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