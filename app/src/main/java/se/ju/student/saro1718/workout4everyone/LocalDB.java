package se.ju.student.saro1718.workout4everyone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class LocalDB extends SQLiteOpenHelper {
    public LocalDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database =  getWritableDatabase();
        database.execSQL(sql);
    }

    /*public void insertData(String title, String exersiceTitle, ArrayList<desc>, byte[] image){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO WORKOUTS VALUES (?, ?, ?, ?)";

    }*/



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
