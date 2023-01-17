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
import androidx.recyclerview.widget.RecyclerView;

import com.example.ggaming_frontend.CategoryListActivity;
import com.example.ggaming_frontend.R;
import com.example.ggaming_frontend.models.Category;

import java.io.InputStream;
import java.util.ArrayList;

public class CategoryCard extends RecyclerView.Adapter<CategoryCard.ViewHolder>  {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Category> categories;

    public CategoryCard(Context context, ArrayList<Category> categories) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.categories = categories ;
    }

    @NonNull
    @Override
    public CategoryCard.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_category_card, parent, false);
        return new CategoryCard.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryCard.ViewHolder holder, int position) {
        holder.categoryTitle.setText(categories.get(position).getTitle());
        new DownloadImageTask(holder.thumbnail).execute(categories.get(position).getThumbSrc());

        holder.whole.setOnClickListener(view -> {
            Intent intent = new Intent(context, CategoryListActivity.class);
            intent.putExtra("category", categories.get(position).getTitle());
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
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTitle;
        ImageView thumbnail;

        View whole;

        public ViewHolder(View v) {
            super(v);
            whole = v;
            categoryTitle = v.findViewById(R.id.categoryTitle);
            thumbnail = v.findViewById(R.id.thumbnail);


        }
    }
}
