package com.hansung.android.restaurants;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LeeChanHee on 2017-11-14.
 */

public class MainActivity extends AppCompatActivity {
    private DBHelper mDbHelper;
    private DBHelper2 mDbHelper2;
    ImageView imageView;









    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertresult);

        Uri imageUri = getIntent().getData();
        imageView.setImageURI(imageUri);



        mDbHelper = new DBHelper(this);
        viewAllToTextView1();

        mDbHelper2 = new DBHelper2(this);
        viewAllToListView();


        Button btn = (Button)findViewById(R.id.buttonCallActivity);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent implicit_intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:02-000-0000"));
                startActivity(implicit_intent);
            }
        });


    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.quick_action1:
                startActivity(new Intent(this, MenuActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void viewAllToTextView1() {
        TextView result = (TextView)findViewById(R.id.text1);
        Cursor cursor = mDbHelper.getAllUsersBySQL();
        StringBuffer buffer = new StringBuffer();
        if (cursor.moveToLast()) {
            buffer.append(cursor.getString(1)+" \n");
            buffer.append(cursor.getString(2)+" \n");
            buffer.append(cursor.getString(3)+" \n");

        }
        result.setText(buffer);
        buffer.setLength(0);
    }


   // private void viewAllToImageView() {
  //      TextView result = (TextView)findViewById(R.id.text1);
   //     Cursor cursor = mDbHelper.getAllUsersBySQL();
   //     StringBuffer buffer = new StringBuffer();
  //      if (cursor.moveToLast()) {
  //          buffer.append(cursor.getString(1)+" \n");


   //     }
   //     result.setImageUri(mPhotoFIleName);
  //      buffer.setLength(0);
   // }













    private void viewAllToListView() {

        Cursor cursor = mDbHelper2.getAllUsersByMethod2();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.list_item, cursor, new String[]{
                UserContract2.Users2.KEY_NAME,
                UserContract2.Users2.KEY_ADDRESS},
                new int[]{ R.id.name, R.id.address}, 0);

        ListView lv = (ListView)findViewById(R.id.listview);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


             Intent intent = new Intent(
                    getApplicationContext(),
                    ListActivity.class);

                startActivity(intent);
            }

        });
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }





//사진












}




// al.add(new Restaurant("아메리카노",R.drawable.img1,"2100원","평점4.5"));
//        al.add(new Restaurant("카라멜 마끼야또",R.drawable.img2,"3200원","평점4.8"));
//        al.add(new Restaurant("Real 에이드",R.drawable.img3,"3000원","평점4.6"));
//        al.add(new Restaurant("카푸치노",R.drawable.img4,"2500원","평점4.9"));
//        al.add(new Restaurant("카페라떼",R.drawable.img5,"3300원","평점3.9"));
//        al.add(new Restaurant("카페모카",R.drawable.img6,"2800원","평점4.2"));
//
//
//    MyAdapter adapter = new MyAdapter(
//            getApplicationContext(),
//            R.layout.row,
//            al);
//
//    ListView lv = (ListView)findViewById(R.id.listView1);
//        lv.setAdapter(adapter);
//
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view,
//        int position, long id) {
//
//            Intent intent = new Intent(
//                    getApplicationContext(),
//                    RestaurantDetail.class);
//
//            intent.putExtra("title", al.get(position).title2);
//            intent.putExtra("img", al.get(position).img);
//            intent.putExtra("artist", al.get(position).artist);
//            intent.putExtra("gpa", al.get(position).gpa);
//
//
//            startActivity(intent);
//        }
//    });
//
//}
//}
//
//
//
//
//
//class MyAdapter extends BaseAdapter {
//    Context context;
//    int layout;
//    ArrayList<Restaurant> al;
//    LayoutInflater inf;
//    public MyAdapter(Context context, int layout, ArrayList<Restaurant> al) {
//        this.context = context;
//        this.layout = layout;
//        this.al = al;
//        inf = (LayoutInflater)context.getSystemService
//                (Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//
//
//    @Override
//    public int getCount() {
//        return al.size();
//    }
//    @Override
//    public Object getItem(int position) {
//        return al.get(position);
//    }
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView==null) {
//            convertView = inf.inflate(layout, null);
//        }
//        ImageView iv = (ImageView)convertView.findViewById(R.id.imageView1);
//        TextView tvName = (TextView)convertView.findViewById(R.id.textView1);
//        TextView tvInfo = (TextView)convertView.findViewById(R.id.textView2);
//        TextView gpa = (TextView)convertView.findViewById(R.id.textView2);
//
//        Restaurant m = al.get(position);
//        iv.setImageResource(m.img);
//        tvName.setText(m.title2);
//        tvInfo.setText(m.artist);
//
//
//
//        return convertView;
//    }
//}
//
//class Restaurant {
//    String title2 = "";
//    int img;
//    String artist = "";
//    String gpa = "";
//
//
//    public Restaurant(String title2, int img, String artist, String gpa) {
//        super();
//        this.title2 = title2;
//        this.img = img;
//        this.artist = artist;
//        this.gpa = gpa;
//    }
//    public Restaurant() {}
//}
//


