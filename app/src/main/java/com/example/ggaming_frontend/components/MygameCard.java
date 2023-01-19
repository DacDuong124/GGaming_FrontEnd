package com.example.ggaming_frontend.components;

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

import androidx.annotation.NonNull;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ggaming_frontend.CategoryListActivity;
import com.example.ggaming_frontend.GameDetailActivity;
import com.example.ggaming_frontend.MyGamesFragment;
import com.example.ggaming_frontend.R;
import com.example.ggaming_frontend.models.Category;
import com.example.ggaming_frontend.models.Game;

import java.io.InputStream;
import java.text.CollationElementIterator;
import java.util.ArrayList;

public class MygameCard extends RecyclerView.Adapter<MygameCard.ViewHolder>{
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Game> mygameCard;

    public MygameCard(LayoutInflater inflater, Context context, ArrayList<Game> mygameCard) {
        this.inflater = inflater;
        this.context = context;
        this.mygameCard = mygameCard;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mygame_item, parent, false);
        return new MygameCard.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MygameCard.ViewHolder holder, int position) {
        holder.game_title.setText(mygameCard.get(position).getTitle());
        new DownloadImageTask(holder.game_image).execute("https://images.pexels.com/photos/163036/mario-luigi-yoschi-figures-163036.jpeg?auto=compress&cs=tinysrgb&w=80&h=802&dpr=1");
        holder.whole.setOnClickListener(view -> {
            Intent intent = new Intent(context, GameDetailActivity.class);
            context.startActivity(intent);

        });
    }


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
        return mygameCard.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView game_title;
        ImageView game_image;
        TextView price;
        View whole;

        public ViewHolder(View v) {
            super(v);
            whole = v;
            game_title = v.findViewById(R.id.game_title);
            game_image = v.findViewById(R.id.game_image);
            price = v.findViewById(R.id.game_price);

        }
    }
}



