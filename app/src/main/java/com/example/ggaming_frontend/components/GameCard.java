package com.example.ggaming_frontend.components;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ggaming_frontend.Constants;
import com.example.ggaming_frontend.GameDetailActivity;
import com.example.ggaming_frontend.R;
import com.example.ggaming_frontend.models.Category;
import com.example.ggaming_frontend.models.Game;

import java.io.InputStream;
import java.util.ArrayList;

public class GameCard extends RecyclerView.Adapter<GameCard.ViewHolder>  {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Game> games;



    public GameCard(Context context, ArrayList<Game> games) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.games = games;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_game_card, parent, false);
        return new GameCard.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.gameTitle.setText(games.get(position).getTitle());

        StringBuilder allCategoriesString = new StringBuilder();
        ArrayList<Category> categories = games.get(position).getCategories();
        for (int i = 0; i < categories.size(); i++) {
            Category curCategory = categories.get(i);
            if (i == categories.size() - 1) {
                allCategoriesString.append(curCategory.getTitle());
            } else {
                allCategoriesString.append(curCategory.getTitle()).append(", ");
            }
        }
        holder.gameCategory.setText(allCategoriesString.toString());

        holder.price.setText(games.get(position).getPrice());
        new DownloadImageTask(holder.thumbnail).execute(games.get(position).getImg());
        holder.whole.setOnClickListener(view -> {
            Intent intent = new Intent(context, GameDetailActivity.class);
            intent.putExtra(Constants.FSGames.titleField, games.get(position).getTitle());
            intent.putExtra(Constants.FSGames.descField, games.get(position).getDesc());
            intent.putExtra(Constants.FSGames.categoriesField, (CharSequence) allCategoriesString);
            intent.putExtra(Constants.FSGames.priceField, games.get(position).getPrice());
            intent.putExtra(Constants.FSGames.imgField, games.get(position).getImg());
            context.startActivity(intent);
        });
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

    @Override
    public int getItemCount() {
        return games.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView gameTitle;
        TextView gameCategory;
        ImageView thumbnail;
        TextView price;
        View whole;

        public ViewHolder(View v) {
            super(v);
            whole = v;
            gameTitle = v.findViewById(R.id.gameTitle);
            gameCategory = v.findViewById(R.id.gameDetailCategoryTextView);
            thumbnail = v.findViewById(R.id.gameThumbnail);
            price = v.findViewById(R.id.price);

        }
    }
}