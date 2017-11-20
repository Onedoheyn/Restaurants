package com.hansung.android.restaurants;

/**
 * Created by pc on 2017-11-21.
 */


import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.ArrayList;

/**
 * Created by Kwanwoo on 2016-09-05.
 */
public class SimpleCursorAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<Restaurant> al;
    LayoutInflater inf;
    public SimpleCursorAdapter(Context context, int layout, ArrayList<Restaurant> al) {
        this.context = context;
        this.layout = layout;
        this.al = al;
        inf = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return al.size();
    }
    @Override
    public Object getItem(int position) {
        return al.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) {
            convertView = inf.inflate(layout, null);
        }
        ImageView iv = (ImageView)convertView.findViewById(R.id.imageView1);
        TextView tvName = (TextView)convertView.findViewById(R.id.textView1);
        TextView tvInfo = (TextView)convertView.findViewById(R.id.textView2);
        TextView gpa = (TextView)convertView.findViewById(R.id.textView2);

        Restaurant m = al.get(position);
        iv.setImageResource(m.img);
        tvName.setText(m.title);
        tvInfo.setText(m.artist);



        return convertView;
    }
}

class Restaurant {
    String title = "";
    int img;
    String artist = "";
    String gpa = "";


    public Restaurant(String title, int img, String artist, String gpa) {
        super();
        this.title = title;
        this.img = img;
        this.artist = artist;
        this.gpa = gpa;
    }
    public Restaurant() {}
}

