package com.example.ggaming_frontend;

import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ggaming_frontend.components.GameCard;
import com.example.ggaming_frontend.models.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class CartFragment extends Fragment{

    View parent;
    RecyclerView mygame_list;
    Dialog point_dialog;
    Button point_button;
    Button card_button;

    Dialog card_dialog;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadGames();

    }

    private void loadGames() {
        LinearLayoutManager layoutManager= new LinearLayoutManager(CartFragment.this.getContext(),LinearLayoutManager.VERTICAL, false);
        mygame_list.setLayoutManager(layoutManager);
        mygame_list.addItemDecoration(new CartFragment.VerticalSpaceItemDecoration(16) );

        try {
            ArrayList<Game> topSellerGameArray = new ArrayList<Game>();
            JSONObject obj = new JSONObject(loadJSONFromAsset("games.json"));
            JSONArray GamesJsonArray = obj.getJSONArray("games");

            for (int i = 0; i < GamesJsonArray.length(); i++) {
                Game GameObj = new Game(GamesJsonArray.getJSONObject(i));
                topSellerGameArray.add(GameObj);

            }
            GameCard gameCardView = new GameCard(CartFragment.this.getContext() , topSellerGameArray);
            mygame_list.setAdapter(gameCardView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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
        parent = inflater.inflate(R.layout.fragment_cart, container, false);
        mygame_list = parent.findViewById(R.id.select_list);
        point_button = parent.findViewById(R.id.Point_btn);
        card_button = parent.findViewById(R.id.Card_btn);

        point_button.setOnClickListener(v -> {
            point_dialog = new Dialog(CartFragment.this.getContext());
            point_dialog.setContentView(R.layout.point_dialog);
            point_dialog.show();
        });

        card_button.setOnClickListener(v -> {
            card_dialog = new Dialog(CartFragment.this.getContext());
            card_dialog.setContentView(R.layout.card_dialog);
            card_dialog.show();
        });


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
//    public void onClickPoint(View view) {
//       point_dialog = new Dialog(CartFragment.this.getContext());
//       point_dialog.setContentView(R.layout.point_dialog);
//       point_dialog.show();
//    }
//
//    public void onClickCard(View view) {
//        card_dialog = new Dialog(CartFragment.this.getContext());
//        card_dialog.setContentView(R.layout.card_dialog);
//        card_dialog.show();
//    }
}