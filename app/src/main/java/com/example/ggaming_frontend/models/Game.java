package com.example.ggaming_frontend.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Game {
    private String title;
    private String price;
    private String category;
    private String description;

    public Game(JSONObject object) {
        try {
            this.title = object.getString("title");
            this.description = object.getString("description");
            this.category = object.getString("category");
            this.price = object.getString("price");
//            this.thumbSrc = object.getString("thumbSrc");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

}
