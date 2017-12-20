package com.hansung.android.restaurants;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

//---------------------------메인 액티비티 프래그먼트------------------------------------------

public class RestaurantFragment extends Fragment{

    int mCurCheckPosition = -1;


    public interface OnTitleSelectedListener {
        public void onTitleSelected(int i);
    }

    public RestaurantFragment(){
    }

    public DBHelper mDBHelper;
    public DBHelper2 mDBHelper2;

    EditText mName;
    EditText mAddress;
    EditText mPhone;
    ImageButton mImage;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.insertresult, container, false);

        mName = (EditText) rootView.findViewById(R.id.editText1);
        mAddress = (EditText) rootView.findViewById(R.id.editText2);
        mPhone = (EditText) rootView.findViewById(R.id.editText3);
        mImage = (ImageButton) rootView.findViewById(R.id.Camera);

        mDBHelper = new DBHelper(getActivity());

        TextView textview1 = (TextView) rootView.findViewById(R.id.text1);
        TextView textview2 = (TextView) rootView.findViewById(R.id.text2);
        TextView textview3 = (TextView) rootView.findViewById(R.id.text3);
        ImageView imageview = (ImageView) rootView.findViewById(R.id.CM);

        Cursor cursor = mDBHelper.getAllUsersBySQL();

        while (cursor.moveToNext()) {
            textview1.setText(cursor.getString(1));
            textview2.setText(cursor.getString(2));
            textview3.setText(cursor.getString(3));
            imageview.setImageURI(Uri.parse(cursor.getString(4)));
        }

        mDBHelper2 = new DBHelper2(getActivity());
        Cursor cursor2 = mDBHelper2.getAllUsersByMethod2();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.row, cursor2, new String[]{
                UserContract2.Users2.KEY_NAME,
                UserContract2.Users2.KEY_ADDRESS,
                UserContract2.Users2.KEY_IMAGE},
                new int[]{ R.id.textView1, R.id.textView2, R.id.imageView1}, 0);

        ListView lv = (ListView)rootView.findViewById(R.id.listview);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Adapter adapter = parent.getAdapter();

                Intent intent = new Intent();

                intent.putExtra("title", i);

                Activity activity = getActivity();
                ((OnTitleSelectedListener)activity).onTitleSelected(i);

            }
        });
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        return rootView;
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mCurCheckPosition = savedInstanceState.getInt("curChoice", -1);
            if (mCurCheckPosition >= 0) {
                Activity activity = getActivity(); // activity associated with the current fragment
                ((OnTitleSelectedListener)activity).onTitleSelected(mCurCheckPosition);

                ListView lv = (ListView) getView().findViewById(R.id.listview);
                lv.setSelection(mCurCheckPosition);
                lv.smoothScrollToPosition(mCurCheckPosition);
            }
        }
    }

    //    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }
}