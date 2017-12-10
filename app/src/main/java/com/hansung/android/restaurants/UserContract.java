package com.hansung.android.restaurants;


import android.provider.BaseColumns;
//맛집에 대한 데이터베이스
//안드로이드 강의 9주차 sqllitedbtest 참조
public final class UserContract {
    public static final String DB_NAME="user.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UserContract() {}

    /* Inner class that defines the table contents */
    public static class Users implements BaseColumns {
        public static final String TABLE_NAME="Users";
        public static final String KEY_NAME = "Name";
        public static final String KEY_ADDRESS="Address";
        public static final String KEY_PHONE = "Phone";
        public static final String KEY_IMAGE = "Image";
        public static final String KEY_W = "LATITUDE";
        public static final String KEY_G = "LONGITUDE";
        public static final String KEY_WW = "LATITUDE2";
        public static final String KEY_GG = "LONGITUDE2";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_NAME + TEXT_TYPE + COMMA_SEP +
                KEY_ADDRESS + TEXT_TYPE + COMMA_SEP +
                KEY_PHONE + TEXT_TYPE +  COMMA_SEP +
                KEY_IMAGE + TEXT_TYPE + COMMA_SEP +
                KEY_W + TEXT_TYPE + COMMA_SEP +
                KEY_G + TEXT_TYPE + COMMA_SEP +
                KEY_WW + TEXT_TYPE + COMMA_SEP +
                KEY_GG + TEXT_TYPE +
                " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}