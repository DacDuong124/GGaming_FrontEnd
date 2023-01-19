package com.example.ggaming_frontend.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Game {
    private String title;
    private String price;
    private ArrayList<Category> categories;
    private String desc;

    private String img;

//    public Game(JSONObject object) {
//        try {
//            this.title = object.getString("title");
//            this.desc = object.getString("description");
//
//            JSONArray jsonCategories = object.getJSONArray("categories");
//            ArrayList<Category> categories = new ArrayList<>();
//            for (int i = 0; i < jsonCategories.length(); i++) {
//                Category category = new Category(jsonCategories.getJSONObject(i).toString(), "");
//                System.out.println("cccccccccc: " + category.getTitle());
//                System.out.println("cccccccccc: " + category.getThumbSrc());
//                categories.add(category);
//            }
//
//            this.categories = categories;
//            this.price = object.getString("price");
////            this.thumbSrc = object.getString("thumbSrc");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    public Game() {
    }

    public Game(String title, String price, ArrayList<Category> categories, String desc, String img) {
        this.title = title;
        this.price = price;
        this.categories = categories;
        this.desc = desc;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
