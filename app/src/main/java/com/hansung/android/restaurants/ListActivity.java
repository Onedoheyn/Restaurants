package com.hansung.android.restaurants;

import android.content.Intent;
import android.content.res.Configuration;
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
//안드로이드 강의 9주차의 SQLiteDBtest 코드 참조
//-----------------------메인 액티비티에 나오는 리스트 클릭시 나오는 액티비티--------------------------
public class ListActivity extends AppCompatActivity {
    //이전 코드
    // private DBHelper2 mDbHelper2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            finish();
            return;
        }

        //-----------------------------------메뉴 프래그 먼트 접근------------------------------------
        MenuFragment details = new MenuFragment();
        details.setSelection(getIntent().getIntExtra("index", -1));
        getSupportFragmentManager().beginTransaction().replace(R.id.details, details).commit();



     //--------------------------------기존 리스트액티비티 코드--------------------------------
        /*
        mDbHelper2 = new DBHelper2(this);
        //리스트뷰 온클릭 액티비티에 데이터베이스 불러옴
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

*/
    }
}
