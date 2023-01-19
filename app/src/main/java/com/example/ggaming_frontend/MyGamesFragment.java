package com.example.ggaming_frontend;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class MyGamesFragment extends Fragment {
    View parent;
    RecyclerView mygame_list;

    private ArrayList<Category> categories = new ArrayList<>();

    private ArrayList<Game> games = new ArrayList<>();

    private FirebaseFirestore db;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();
        fetchCategories();
//        loadGames();
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
                        } else {
                            Toast.makeText(getActivity(),
                                    "Failed to get list of categories!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public static <E> List<E> pickNRandomElements(List<E> list, int n, Random r) {
        int length = list.size();

        if (length < n) return null;

        //We don't need to shuffle the whole list
        for (int i = length - 1; i >= length - n; --i)
        {
            Collections.swap(list, i , r.nextInt(i + 1));
        }
        return list.subList(length - n, length);
    }

    public static <E> List<E> pickNRandomElements(List<E> list, int n) {
        return pickNRandomElements(list, n, ThreadLocalRandom.current());
    }

private void renderGames() {
    LinearLayoutManager layoutManager= new LinearLayoutManager(MyGamesFragment.this.getContext(),LinearLayoutManager.VERTICAL, false);
    mygame_list.setLayoutManager(layoutManager);
    mygame_list.addItemDecoration(new MyGamesFragment.VerticalSpaceItemDecoration(16) );
    ArrayList<Game> randomGames = new ArrayList<>(pickNRandomElements(games, 5));
    GameCard gameCardView = new GameCard(MyGamesFragment.this.getContext() , randomGames);
    mygame_list.setAdapter(gameCardView);
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_my_games, container, false);
        mygame_list = parent.findViewById(R.id.mygame_list);
        return parent;
    }
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