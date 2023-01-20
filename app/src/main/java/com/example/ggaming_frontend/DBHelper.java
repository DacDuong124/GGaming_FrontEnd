package com.example.ggaming_frontend;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.ggaming_frontend.models.CartItem;
import com.example.ggaming_frontend.models.Category;
import com.example.ggaming_frontend.models.User;
import org.checkerframework.checker.units.qual.C;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
  //  private static final String DB_NAME = User.getDocId();
   // private String userID = User.getDocId();

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, null, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS userID(id INTEGER PRIMARY KEY AUTOINCREMENT ,title TEXT NOT NULL, price FLOAT NOT NULL, categories TEXT NOT NULL, img TEXT NOT NULL) ");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    //insert

    public void Insert(String _title, float _price,String _categories, String _img){
        SQLiteDatabase sdb = getWritableDatabase();
        sdb.execSQL("INSERT INTO userID (title, price, categories, img) VALUES('" + _title+ "' + '" + _price+ "','" + _categories+ "','" + _img+ "'); ");
    }
    // we do not need to update the game so we just make insert and delete only

    //delete

    public void Delete(int _id){
        SQLiteDatabase sdb = getWritableDatabase();
        sdb.execSQL("DELETE FROM userID WHERE id = '" +_id +"'");
    }
    //select
    public ArrayList<CartItem> getCartlist(){
        ArrayList<CartItem> cartItems = new ArrayList<>();
        SQLiteDatabase sdb = getReadableDatabase();
        Cursor cursor = sdb.rawQuery("SELECT * FROM userID ORDER BY title DESC",null);
        if(cursor.getCount() == 0){
            cursor.moveToFirst();
        }
        return cartItems;
    }
}
