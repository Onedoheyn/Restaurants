package com.hansung.android.restaurants;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by pc on 2017-11-19.
 */

public class ListActivity extends AppCompatActivity {
    private DBHelper2 mDbHelper2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        mDbHelper2 = new DBHelper2(this);
      //  viewAllToListText();
        Cursor cursor = mDbHelper2.getAllUsersBySQL();
        StringBuffer buffer = new StringBuffer();
        ImageView CameraImage = (ImageView)findViewById(R.id.imageView1);
        if (cursor.moveToLast()) {
            buffer.append(cursor.getString(1) + " \n");
            buffer.append(cursor.getString(2) + " \n");
            buffer.append(cursor.getString(3) + " \n");
            CameraImage.setImageURI(Uri.parse(cursor.getString(4)));

        }
        TextView result = (TextView) findViewById(R.id.textView1);
        result.setText(buffer);
        buffer.setLength(0);
    }

}
