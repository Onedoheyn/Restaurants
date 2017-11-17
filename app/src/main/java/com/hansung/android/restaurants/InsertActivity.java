package com.hansung.android.restaurants;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert);
        imgview = (ImageView) findViewById(R.id.Camera);
        ImageButton buttonCamera = (ImageButton) findViewById(R.id.Camera);


        buttonCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                dispatchTakePictureIntent();
            }
        });

        Button btn = (Button) findViewById(R.id.Insert);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent new_intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(new_intent);
            }
        });


    }

    String mPhotoFileName;
    File mPhotoFile;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private String currentDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //1. 카메라 앱으로 찍은 이미지를 저장할 파일 객체 생성
            mPhotoFileName = "IMG" + currentDateFormat() + ".jpg";
            mPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), mPhotoFileName);

            if (mPhotoFile != null) {
                //2. 생성된 파일 객체에 대한 Uri 객체를 얻기
                Uri imageUri = FileProvider.getUriForFile(this, "com.hansung.android.restaurants", mPhotoFile);
                //authority에 패키지이름 고쳐주기
                //3. Uri 객체를 Extras를 통해 카메라 앱으로 전달
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } else
                Toast.makeText(getApplicationContext(), "file null", Toast.LENGTH_SHORT).show();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (mPhotoFileName != null) {
                mPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), mPhotoFileName);

                ImageButton ImageView = (ImageButton) findViewById(R.id.Camera);
                ImageView.setImageURI(Uri.fromFile(mPhotoFile));
                //mAdapter.addItem(new MediaItem(MediaItem.SDCARD, mPhotoFileName, Uri.fromFile(mPhotoFile), MediaItem.IMAGE));
            }


        }
    }
}
