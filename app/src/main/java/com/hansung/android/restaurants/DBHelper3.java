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

public class DBHelper3 extends SQLiteOpenHelper {
    final static String TAG="SQLiteDBTest3";

    public DBHelper3(Context context) {
        super(context, UserContract3.DB_NAME, null, UserContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,getClass().getName()+".onCreate()");
        db.execSQL(UserContract3.Users3.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG,getClass().getName() +".onUpgrade()");
        db.execSQL(UserContract3.Users3.DELETE_TABLE);
        onCreate(db);
    }



    public Cursor getAllUsersBySQL() {
        String sql = "Select * FROM " + UserContract.Users.TABLE_NAME;
        return getReadableDatabase().rawQuery(sql,null);
    }



    public long insertUserByMethod( String w, String g) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract3.Users3.KEY_W, w);
        values.put(UserContract3.Users3.KEY_G, g);


        return db.insert(UserContract.Users.TABLE_NAME,null,values);
    }


}