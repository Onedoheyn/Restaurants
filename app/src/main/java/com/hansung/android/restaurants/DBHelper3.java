package com.hansung.android.restaurants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//===========================메뉴정보를 저장하는 데이터베이스==================================
public class DBHelper3 extends SQLiteOpenHelper {
    final static String TAG="SQLiteDBTest";

    public DBHelper3(Context context) {
        super(context, UserContract3.DB_NAME3, null, UserContract3.DATABASE_VERSION3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,getClass().getName()+".onCreate()");
        db.execSQL(UserContract3.Users.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG,getClass().getName() +".onUpgrade()");
        db.execSQL(UserContract3.Users.DELETE_TABLE);
        onCreate(db);
    }

    public long insertUserByMethod(String image, String image2,  String image3,  String image4)  {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract3.Users.KEY_IMAGE,image);
        values.put(UserContract3.Users.KEY_IMAGE2,image2);
        values.put(UserContract3.Users.KEY_IMAGE3,image3);
        values.put(UserContract3.Users.KEY_IMAGE4,image4);

        return db.insert(UserContract3.Users.TABLE_NAME,null,values);
    }


    public Cursor getAllUsersBySQL() {
        String sql = "Select * FROM " + UserContract.Users.TABLE_NAME;
        return getReadableDatabase().rawQuery(sql,null);
    }

    public Cursor getAllUsersByMethod() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(UserContract3.Users.TABLE_NAME,null,null,null,null,null,null);
    }

}