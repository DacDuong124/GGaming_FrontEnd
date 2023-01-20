package com.example.ggaming_frontend;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Query;
import androidx.room.Room;

import com.example.ggaming_frontend.components.GameCard;
import com.example.ggaming_frontend.models.CartItem;
import com.example.ggaming_frontend.models.Category;
import com.example.ggaming_frontend.models.Game;
import com.example.ggaming_frontend.models.User;
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


public class CartFragment extends Fragment{

    View parent;
    RecyclerView mygame_list;
    Dialog point_dialog;
    Button point_button;
    Button card_button;
    Dialog card_dialog;
    TextView totalPriceTextView;
    TextView point_price;
    float totalPrice;
    String cost;


    private ArrayList<Category> categories = new ArrayList<>();

    private ArrayList<Game> games = new ArrayList<>();

    private ArrayList<Game> randomGames = new ArrayList<>();

    private FirebaseFirestore db;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();
        fetchCategories();
    }

    private void renderGames() {
        LinearLayoutManager layoutManager= new LinearLayoutManager(CartFragment.this.getContext(),LinearLayoutManager.VERTICAL, false);
        mygame_list.setLayoutManager(layoutManager);
        mygame_list.addItemDecoration(new CartFragment.VerticalSpaceItemDecoration(16) );


        randomGames = new ArrayList<>(pickNRandomElements(games, 2));
        GameCard gameCardView = new GameCard(CartFragment.this.getContext() , randomGames);
        mygame_list.setAdapter(gameCardView);
        calculateAndRenderTotalPrice();
    }

    @SuppressLint("SetTextI18n")
    private void calculateAndRenderTotalPrice () {
        totalPrice = 0.0F;
        for (Game game : randomGames) {
            totalPrice += Float.parseFloat(game.getPrice());
        }
        totalPriceTextView.setText(Float.toString(totalPrice));
        cost = Float.toString(totalPrice);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_cart, container, false);
        mygame_list = parent.findViewById(R.id.select_list);
        point_button = parent.findViewById(R.id.Point_btn);
        card_button = parent.findViewById(R.id.Card_btn);
        totalPriceTextView = parent.findViewById(R.id.cartTotalPriceTextView);

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

            TextView point_price = point_dialog.findViewById(R.id.item_price);
            point_price.setText(cost);
            TextView result_txt = point_dialog.findViewById(R.id.result_txt);
            float result = 200 - totalPrice ;
            result_txt.setText(Float.toString(result));
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

}