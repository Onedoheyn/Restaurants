package com.hansung.android.restaurants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper2 extends SQLiteOpenHelper {
    final static String TAG="SQLiteDBTest2";

    public DBHelper2(Context context) {
        super(context, UserContract2.DB_NAME, null, UserContract2.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,getClass().getName()+".onCreate()");
        db.execSQL(UserContract2.Users2.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG,getClass().getName() +".onUpgrade()");
        db.execSQL(UserContract2.Users2.DELETE_TABLE);
        onCreate(db);
    }

    public Cursor getAllUsersBySQL() {
        String sql = "Select * FROM " + UserContract2.Users2.TABLE_NAME;
        return getReadableDatabase().rawQuery(sql,null);
    }

    public long insertUserByMethod2(String name2, String address2, String phone2,String image2) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.Users.KEY_NAME, name2);
        values.put(UserContract.Users.KEY_ADDRESS, address2);
        values.put(UserContract.Users.KEY_PHONE,phone2);
        values.put(UserContract.Users.KEY_IMAGE,image2);

        return db.insert(UserContract.Users.TABLE_NAME,null,values);
    }

    public Cursor getAllUsersByMethod2() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(UserContract2.Users2.TABLE_NAME,null,null,null,null,null,null);
    }
}