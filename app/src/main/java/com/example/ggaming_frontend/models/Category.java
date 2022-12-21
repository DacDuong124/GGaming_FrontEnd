package com.example.ggaming_frontend.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Category {
    private String title;
    private String thumbSrc;

    public Category(JSONObject object) {
        try {
            this.title = object.getString("title");
            this.thumbSrc = object.getString("thumbSrc");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public String getThumbSrc() {
        return thumbSrc;
    }
}
