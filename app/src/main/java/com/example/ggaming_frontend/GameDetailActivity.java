package com.example.ggaming_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.InputStream;

public class GameDetailActivity extends AppCompatActivity {

    TextView titleTextView, categoryTextView, priceTextView, descriptionTextView;

    ImageView gameImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        initComponents();
        renderDetails();
    }

    @SuppressLint("SetTextI18n")
    private void renderDetails() {
        Intent intent = getIntent();
        titleTextView.setText((String) intent.getExtras().get(Constants.FSGames.titleField));
        categoryTextView.setText("Category: " + intent.getExtras().get(Constants.FSGames.categoriesField));
        priceTextView.setText("$" + intent.getExtras().get(Constants.FSGames.priceField));
        descriptionTextView.setText((String) intent.getExtras().get(Constants.FSGames.descField));
        new DownloadImageTask(gameImage).execute((String) intent.getExtras().get(Constants.FSGames.imgField));
//        new GameCard.DownloadImageTask(holder.thumbnail).execute(games.get(position).getImg());
    }

    public void onClickAddToCart(View view) {
        Toast.makeText(GameDetailActivity.this,"Item move to cart!",
                Toast.LENGTH_SHORT).show();
    }

    // ref: https://stackoverflow.com/questions/6407324/how-to-display-image-from-url-on-android
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private void initComponents() {
        titleTextView = findViewById(R.id.gameDetailTitleTextView);
        categoryTextView = findViewById(R.id.gameDetailCategoryTextView);
        priceTextView = findViewById(R.id.gameDetailPriceTextView);
        descriptionTextView = findViewById(R.id.gameDetailDescription);
        gameImage = findViewById(R.id.gameDetailImage);
        ImageView backActionIcon = findViewById(R.id.backActionIcon);
        backActionIcon.setOnClickListener(view -> {
            Intent intent = new Intent();
            setResult(200, intent);
            finish();
        });
    }
}