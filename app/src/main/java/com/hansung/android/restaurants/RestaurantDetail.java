package com.hansung.android.restaurants;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class RestaurantDetail extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurantdetail);


        TextView tvTitle = (TextView)findViewById(R.id.name);
        TextView tvArtist = (TextView)findViewById(R.id.address);
        TextView gpa = (TextView)findViewById(R.id.phone);



        Intent intent = getIntent();
        tvTitle.setText(intent.getStringExtra("title"));
        tvArtist.setText(intent.getStringExtra("artist"));
        gpa.setText(intent.getStringExtra("gpa"));




    }
}