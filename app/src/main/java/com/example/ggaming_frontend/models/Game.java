package com.example.ggaming_frontend.models;

import java.util.ArrayList;

public class Game {
    private String title;
    private String price;
    private ArrayList<Category> categories;
    private String desc;

    private String img;

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
