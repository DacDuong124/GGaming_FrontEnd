package com.example.ggaming_frontend;

import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
            WindowManager.LayoutParams params = point_dialog.getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            point_dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params );
           // point_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            ImageButton close = point_dialog.findViewById(R.id.close_btn);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    point_dialog.dismiss();
                }
            });

            Button pay_btn = point_dialog.findViewById(R.id.pay_btn);
            pay_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    point_dialog.dismiss();
                }
            });
            point_dialog.show();
        });

        card_button.setOnClickListener(v -> {
            card_dialog = new Dialog(CartFragment.this.getContext());
            card_dialog.setContentView(R.layout.card_dialog);
            WindowManager.LayoutParams params = card_dialog.getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            card_dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params );
            ImageButton close = card_dialog.findViewById(R.id.close_btn);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    card_dialog.dismiss();
                }
            });

            Button pay_btn = card_dialog.findViewById(R.id.pay_btn);
            pay_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    card_dialog.dismiss();
                }
            });

            RadioGroup card_group = card_dialog.findViewById(R.id.card_group);
            RadioButton card1 = card_dialog.findViewById(R.id.card1);
            RadioButton card2 = card_dialog.findViewById(R.id.card2);
            RadioButton card3 = card_dialog.findViewById(R.id.card3);
            RadioButton card4 = card_dialog.findViewById(R.id.card4);
            TextView selected_card = card_dialog.findViewById(R.id.selected_card);
            TextView card1_info = card_dialog.findViewById(R.id.card1_info);
            TextView card2_info = card_dialog.findViewById(R.id.card2_info);
            TextView card3_info = card_dialog.findViewById(R.id.card3_info);
            TextView card4_info = card_dialog.findViewById(R.id.card4_info);

            card_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if(i == R.id.card1){
                        selected_card.setText(card1_info.getText().toString());
                    }
                    else if(i == R.id.card2){
                        selected_card.setText(card2_info.getText().toString());
                    }
                    else if(i == R.id.card3){
                        selected_card.setText(card3_info.getText().toString());
                    }
                    else if(i == R.id.card4){
                        selected_card.setText(card4_info.getText().toString());
                    }
                }
            });
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