package com.example.ggaming_frontend;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;


import com.example.ggaming_frontend.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MainActivity extends AppCompatActivity {

    //Firebase, FireStore
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    //Current user info
    User currentUserObject = null;

    private BottomNavigationView navigationView;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        initFirebaseCurrentUserInfo(); //Get all fireStore instances
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
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }



    private void initComponents() {
        viewPager = findViewById(R.id.view_pager);
        navigationView = findViewById(R.id.bottom_nav);
        navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.action_home:
                    viewPager.setCurrentItem(0);

                    break;
                case R.id.action_my_games:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.action_cart:
                    viewPager.setCurrentItem(2);
                    break;
            }
            return true;
        });

        setSwitchMenuItemScreen();


        ImageView profileActionIcon = findViewById(R.id.profileActionIcon);
        profileActionIcon.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

    }


    // ref: https://www.youtube.com/watch?v=oioIYszJWIs
    private void setSwitchMenuItemScreen(){
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        navigationView.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;

                    case 1:
                        navigationView.getMenu().findItem(R.id.action_my_games).setChecked(true);
                        break;

                    case 2:
                        navigationView.getMenu().findItem(R.id.action_cart).setChecked(true);
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}