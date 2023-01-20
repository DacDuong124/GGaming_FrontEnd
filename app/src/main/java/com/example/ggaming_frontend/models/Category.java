package com.example.ggaming_frontend.models;

import com.google.firebase.firestore.DocumentId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Category {
    @DocumentId
    private String docId;
    private String title;
    private String thumbSrc;

    public Category() {
    }

    public Category(String docId, String title, String thumbSrc) {
        this.docId = docId;
        this.title = title;
        this.thumbSrc = thumbSrc;
    }

    public Category(JSONObject object) {
        try {
            this.title = object.getString("title");
            this.thumbSrc = object.getString("thumbSrc");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Category getCategoryById(ArrayList<Category> categories, String docId) {
        for (Category category : categories) {
            if (category.getDocId().equals(docId))
                return category;
        }

        return null;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbSrc() {
        return thumbSrc;
    }

    public void setThumbSrc(String thumbSrc) {
        this.thumbSrc = thumbSrc;
    }


}
