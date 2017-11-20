package com.hansung.android.restaurants;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
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
        if (cursor.moveToLast()) {
            buffer.append(cursor.getString(1) + " \n");
            buffer.append(cursor.getString(2) + " \n");
            buffer.append(cursor.getString(3) + " \n");

        }
        TextView result = (TextView) findViewById(R.id.textView1);
        result.setText(buffer);
        buffer.setLength(0);
    }


/*
    private void viewAllToListText() {

        Cursor cursor = mDbHelper2.getAllUsersByMethod2();
        TextView result = (TextView)findViewById(R.id.listtext);



        StringBuffer buffer = new StringBuffer();
        if (cursor.moveToNext()) {
            buffer.append(cursor.getString(1)+" \n");
            buffer.append(cursor.getString(2)+" \n");
            buffer.append(cursor.getString(3)+" \n");
           // buffer.append(cursor.getString(4)+" \n");

        }
        result.setText(buffer);
        buffer.setLength(0);
    }
*/


}
