package com.example.ggaming_frontend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.ggaming_frontend.components.GameCard;
import com.example.ggaming_frontend.components.WishListCard;
import com.example.ggaming_frontend.models.Game;
import com.example.ggaming_frontend.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    RecyclerView listWishlist;

    private EditText usernameEditText;
    private EditText ageEditText;
    private EditText countryEditText;
    private Button updateBtn;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
//    private StorageReference mStorageRef;

    User currentUserObject = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initFirebaseCurrentUserInfo();
        initComponents();
//        loadWishListItems();
        setUpdateBtnHandler();
    }

    /**
     * Get instances of Firebase FireStore Auth, db, current user
     */
    private void initFirebaseCurrentUserInfo() {
        //Get instances of Firebase FireStore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
        getCurrentUserObject(); //Get current user object info
    }

    /**
     * Get current user object from FireStore
     */
    private void getCurrentUserObject() {
        db.collection(Constants.FSUser.userCollection)
                .whereEqualTo(Constants.FSUser.emailField, currentUser.getEmail())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            currentUserObject = doc.toObject(User.class);
                            renderUserDetails();
                        }
                    }
                });
    }

    /**
     * set update button event handler
     */
    private void setUpdateBtnHandler() {
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> userData = new HashMap<>();
                userData.put(Constants.FSUser.usernameField, usernameEditText.getText().toString());
                userData.put(Constants.FSUser.ageField, Integer.parseInt(ageEditText.getText().toString()));
                userData.put(Constants.FSUser.countryField, countryEditText.getText().toString());

                db.collection(Constants.FSUser.userCollection).document(currentUserObject.getDocId())
                        .update(userData)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(ProfileActivity.this,
                                            "Updated successfully",
                                            Toast.LENGTH_LONG).show();
                                } else {

                                }
                            }
                        });
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void renderUserDetails() {
        usernameEditText.setText(currentUserObject.getUsername());
        ageEditText.setText(currentUserObject.getAge().toString());
        countryEditText.setText(currentUserObject.getCountry());
    }

    private void initComponents() {
        usernameEditText = findViewById(R.id.profileNameEditText);
        ageEditText = findViewById(R.id.profileAgeEditText);
        countryEditText = findViewById(R.id.profileCountryEditText);
        updateBtn = findViewById(R.id.profileUpdateBtn);
        listWishlist = findViewById(R.id.listWishList);

        ImageView backActionIcon = findViewById(R.id.backActionIcon);
        backActionIcon.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            setResult(100, intent);
            finish();

        });


    }

//    private void loadWishListItems() {
//        LinearLayoutManager layoutManager= new LinearLayoutManager(ProfileActivity.this,LinearLayoutManager.VERTICAL, false);
//        listWishlist.setLayoutManager(layoutManager);
//        listWishlist.addItemDecoration(new ProfileActivity.VerticalSpaceItemDecoration(16) );
//
//        try {
//            ArrayList<Game> wishListGameArray = new ArrayList<Game>();
//            JSONObject obj = new JSONObject(loadJSONFromAsset("games.json"));
//            JSONArray GamesJsonArray = obj.getJSONArray("games");
//
//            for (int i = 0; i < GamesJsonArray.length(); i++) {
//                Game GameObj = new Game(GamesJsonArray.getJSONObject(i));
//                wishListGameArray.add(GameObj);
//
//            }
//            WishListCard gameCardView = new WishListCard(ProfileActivity.this, wishListGameArray);
//            listWishlist.setAdapter(gameCardView);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

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