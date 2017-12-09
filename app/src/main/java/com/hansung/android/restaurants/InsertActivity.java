package com.hansung.android.restaurants;
//안드로이드 강의 9주차 ContentProvider 코드 참조
import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
public class InsertActivity extends AppCompatActivity {

    private static final int PICK_FROM_CAMERA = 1;
    private ImageView imgview;
    final static String TAG = "SQLITEDBTEST";

    final int REQUEST_CODE_READ_CONTACTS = 1;



    EditText mName;
    EditText mAddress;
    EditText mPhone;


    private DBHelper mDbHelper;
    private DBHelper3 mDbHelper3;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert);

        ImageButton buttonCamera = (ImageButton) findViewById(R.id.Camera);


        buttonCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                dispatchTakePictureIntent();
            }
        });


        //---등록하기 버튼 누를 시 MainActivity로 넘어감---//
        Button btn = (Button) findViewById(R.id.Insert);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent new_intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(new_intent);
                insertRecord();
            }
        });

        mName = (EditText) findViewById(R.id.editText1);
        mAddress = (EditText) findViewById(R.id.editText2);
        mPhone = (EditText) findViewById(R.id.editText3);

        mDbHelper = new DBHelper(this);
        mDbHelper3 = new DBHelper3(this);

        viewAllToTextView2();
        viewAllToTextView3();
            //-----------권한 확인 -------- //

        /*
        if (ContextCompat.checkSelfPermission(InsertActivity.this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) { // 권한이 없으므로, 사용자에게 권한 요청 다이얼로그 표시
            ActivityCompat.requestPermissions(InsertActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
        } else // 권한 있음! 해당 데이터나 장치에 접근!
            getContacts();
*/

    }

    String mPhotoFileName;
    File mPhotoFile;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private String currentDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }
    // 카메라 앱으로 찍은 이미지를 저장할 파일 객체 생성
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            mPhotoFileName = "IMG" + currentDateFormat() + ".jpg";
            mPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), mPhotoFileName);

            if (mPhotoFile != null) {
                // 생성된 파일 객체에 대한 Uri 객체를 얻기
                Uri imageUri = FileProvider.getUriForFile(this, "com.hansung.android.restaurants", mPhotoFile);
                //authority에 패키지이름 고쳐주기
                // Uri 객체를 Extras를 통해 카메라 앱으로 전달
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } else
                Toast.makeText(getApplicationContext(), "file null", Toast.LENGTH_SHORT).show();
        }

    }
//이미지버튼으로 카메라로 찍은사진 저장
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (mPhotoFileName != null) {
                mPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), mPhotoFileName);
                Uri uri = Uri.fromFile(mPhotoFile);
                ImageButton ImageView = (ImageButton) findViewById(R.id.Camera);
                ImageView.setImageURI(uri);
                //mAdapter.addItem(new MediaItem(MediaItem.SDCARD, mPhotoFileName, Uri.fromFile(mPhotoFile), MediaItem.IMAGE));
            }


        }
    }


//데이터 베이스 코드






    private void insertRecord() {
        EditText name = (EditText) findViewById(R.id.editText1);
        EditText address = (EditText) findViewById(R.id.editText4);
        EditText phone = (EditText) findViewById(R.id.editText5);

        String photo = mPhotoFile.getAbsolutePath(); //http://sexy.pe.kr/tc/763 참조

        long nOfRows = mDbHelper.insertUserByMethod(name.getText().toString(), address.getText().toString(), phone.getText().toString(), photo);
        if (nOfRows > 0) {
            Toast.makeText(this, nOfRows + " Record Inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No Record Inserted", Toast.LENGTH_SHORT).show();
        }
        //오류나면 data-data-databases 에서 db삭제하고 다시해보기

    }



    private void viewAllToTextView2() {
        EditText address2  = (EditText) findViewById(R.id.editText2);
        Cursor cursor = mDbHelper3.getAllUsersBySQL();

        StringBuffer buffer = new StringBuffer();
        if(cursor.moveToLast()){
            buffer.append(cursor.getString(1) + " \n");
        }
        address2.setText(buffer);
    }
    private void viewAllToTextView3() {
        EditText address3 = (EditText) findViewById(R.id.editText3);

        Cursor cursor = mDbHelper3.getAllUsersBySQL();

        StringBuffer buffer = new StringBuffer();
        if(cursor.moveToLast()){
            buffer.append(cursor.getString(2) + " \n");
        }


        address3.setText(buffer);
    }

}



