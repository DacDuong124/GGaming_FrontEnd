package com.example.ggaming_frontend.components;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ggaming_frontend.R;
import com.example.ggaming_frontend.models.Game;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class WishListCard extends RecyclerView.Adapter<WishListCard.ViewHolder>  {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Game> games;



    public WishListCard(Context context, ArrayList<Game> games) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.games = games;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_wish_list_card, parent, false);
        return new WishListCard.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.gameTitle.setText(games.get(position).getTitle());
        new DownloadImageTask(holder.thumbnail).execute("https://images.pexels.com/photos/163036/mario-luigi-yoschi-figures-163036.jpeg?auto=compress&cs=tinysrgb&w=640&h=312&dpr=1");



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
                InputStream in = new URL(urldisplay).openStream();
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
//        TextView price;
        View whole;

        public ViewHolder(View v) {
            super(v);
            whole = v;
            gameTitle = v.findViewById(R.id.gameTitle);
            thumbnail = v.findViewById(R.id.gameThumbnail);
//            price = v.findViewById(R.id.price);

        }
    }
}