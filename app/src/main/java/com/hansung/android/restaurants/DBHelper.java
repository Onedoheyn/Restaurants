package com.hansung.android.restaurants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.util.Log;
//맛집에 대한 데이터베이스
//안드로이드 9주차 강의 참조

public class DBHelper extends SQLiteOpenHelper {
    final static String TAG="SQLiteDBTest";

    public DBHelper(Context context) {
        super(context, UserContract.DB_NAME, null, UserContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,getClass().getName()+".onCreate()");
        db.execSQL(UserContract.Users.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG,getClass().getName() +".onUpgrade()");
        db.execSQL(UserContract.Users.DELETE_TABLE);
        onCreate(db);
    }



    public Cursor getAllUsersBySQL() {
        String sql = "Select * FROM " + UserContract.Users.TABLE_NAME;
        return getReadableDatabase().rawQuery(sql,null);
    }



    public long insertUserByMethod( String name, String address, String phone, String image ,  String w, String g, String ww, String gg) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.Users.KEY_NAME, name);
        values.put(UserContract.Users.KEY_ADDRESS, address);
        values.put(UserContract.Users.KEY_PHONE,phone);
        values.put(UserContract.Users.KEY_IMAGE,image);
        values.put(UserContract.Users.KEY_W,w);
        values.put(UserContract.Users.KEY_G,g);
        values.put(UserContract.Users.KEY_WW,ww);
        values.put(UserContract.Users.KEY_GG,gg);

        return db.insert(UserContract.Users.TABLE_NAME,null,values);
    }


}