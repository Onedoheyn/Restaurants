package com.hansung.android.restaurants;


import android.provider.BaseColumns;
//메뉴에 대한 데이터베이스
//안드로이드 강의 9주차 sqllitedbtest 참조
public final class UserContract3 {
    public static final String DB_NAME="user3.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UserContract3() {}

    /* Inner class that defines the table contents */
    public static class Users3 implements BaseColumns {
        public static final String TABLE_NAME="Users3";
        public static final String KEY_W = "W";
        public static final String KEY_G="G";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_W + TEXT_TYPE + COMMA_SEP +
                KEY_G + TEXT_TYPE +
                " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}