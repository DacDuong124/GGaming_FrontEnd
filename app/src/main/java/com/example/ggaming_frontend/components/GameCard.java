package com.example.ggaming_frontend.components;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ggaming_frontend.HomeFragment;
import com.example.ggaming_frontend.R;
import com.example.ggaming_frontend.models.Game;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
        new DownloadImageTask(holder.thumbnail).execute("https://images.unsplash.com/photo-1472457897821-70d3819a0e24?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2069&q=80");



//        holder.whole.setOnClickListener(view -> {
//            Intent intent = new Intent(context, DetailsActivity.class);
//            intent.putExtra("GameData", games.get(position));
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//
//        });
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
        ImageView thumbnail;
        TextView price;
        View whole;

        public ViewHolder(View v) {
            super(v);
            whole = v;
            gameTitle = v.findViewById(R.id.gameTitle);
            thumbnail = v.findViewById(R.id.thumbnail);
            price = v.findViewById(R.id.price);

        }
    }
}