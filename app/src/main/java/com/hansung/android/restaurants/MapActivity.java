package com.hansung.android.restaurants;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.SupportMapFragment;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import android.location.Location;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.maps.model.LatLng;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener{

    private DBHelper DbHelper;
    private DBHelper3 mDbHelper3;
    double DIS;
    double distance = 0;
    EditText inputedit;
    final int REQUEST_CODE_READ_CONTACTS = 1;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    }

d onCreate(Bundle savedInstanceState) {
        super.onCreatesavedInstanceState);
        setContentView(R.layout.map)


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            }
        });3(this);
(SupportMapFragment) getSuppor
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);






        if (!checkLocationPermissions()) {
            requestLocationPermissions(REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION);
        } else{
            getLastLocation();
        }



        }




    private boolean checkLocationPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermissions(int requestCode) {
        ActivityCompat.requestPermissions(
                MapActivity.this,            // MainActivity 액티비티의 객체 인스턴스를 나타냄
                new String[]{Manifest.permis
    public void onRequestPer
                        && grantResults[0] Toast.makeText(
        }       } else {
          MissingPermission")
    private void LastLocation() {
            public voi onSuccess(Location location) {
                // Got last kown location. In some rare situations this can be null.
                if (location != nullion;
                    LatLng location1 =rker(1).
                                    title(input)
                    );
                    Toast.makeText(getApplicationContex(),
                            "위도 "+mCurrentLocation.getLatitude()+"경도 "+mCurrentLocation.getLongitude(),
                            Toast.LENGTH_SHORT)
                            .show()

ationContext(), mCurrentLocation.getLatitude() +  Toast.LENGTH_SHORT).show();
                    //updateUI();
                } else
                    Toast.makeText(getApplicationContext(), "no_location_detected", Toast.LENGTH_SHORT).show();
            }
        });
    }



   // public double getGunWoo()
    //{


        //double si = CmCurrentLocation.getLatitude();
        //si 가 현재 위

       // Location lc1= new Location("location 1 name");
       // Locatio loc2 = new Location("location 2 name");
etLatitude(mCurrentLocation.getLatitude());
       // loc1.setLongitude(mCurrentLocation.getLongitude());

        //------------여기에 DB의 맛집 위도경도 들가야함--------------//
        //loc2.setLatitude(locarion2 .getLatitude());
       /





    public void o
        mGoogleMapeToFirt();
//        int z;

//        for(z=0; z < cursor.getPosition(); z++){
        while (cursor.moveToNext()) {
            double x = cursor.getDouble(5);
            double y = cursor.getDouble(6);

            MarkerOptions makerOptions = new MarkerOptions();
            makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                    .position(new LatLng(x, y))
                    .title("마커") // 타이틀.
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

            // 2. 마커 생성 (마커를 나타냄)
            mGoogleMap.addMarker(makerOptions);
        }
    }

    @Override
public  boolean onMarkerClick(Marker marker){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("등록");
        alert.setMessage("새로운 맛집등록하시겠습니까 ? ");
        alert.setPositiveButton("네", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(getApplicationContext(), InsertActivity.class);



                insertRecord();

                startActivity(intent);
            }
        });
        alert.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alert.show();
        return  true;

    }
    private void getaddress() {
     inputed




        try {
            Geocoder geocoder = new Geocoder(this, Locale.KOREA);
            List<Address> addresses = geocoder.getFromLocationName(input, 1);
            if (addresses.size() > 0) {
                Address bestResult = (Address) addresses.get(0);

                mResultText.setText(String.format("[ %s  ]",
                        bes
                mResultText3.setText(String.format("[ %s ]",
                        mCurrentLocation.getLatitude()));

                mResultText4.setText(String.format("[ %s ]",
                        mCurrentLocation.getLongitude()));

                /// 위치설정
                LatLng location1 = new LatLng(bestResult.getLatitude(), bestResult.getLongitude());
                ///해당위치로 가기
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location1, 15));
                //// 마커추가하기
                mGoogleMap.addMarker(
                        new MarkerOptions().
                                position(location1).
                                title(input)

                );

            }
    }

    private void insertRecord() {extView) findViewById(R.id.textview);
        TextView mResultText2 = (TextView) findViewById(R.id.textview2);
        TextView mResultText3 = (TextView) findViewById(R.id.textview3);
        TextView mResultText4 = (TextView) findViewById(R.id.textview4);
        String input = inputedit.getText().toString();
this, Locale.KOREA);
            List<Address> addresses = geocoder.getFromLocationName(input,1);
            if (addresses.size() >0) {
                Address address = addresses.get(0);
                mResultText.setText(String.format("%s", address.getLatitude()));
                mResultText2.setText(String.format("%s",address.getLongitude()));
                mResultText3.setText(String.format("%s",mCurrentLocatddress.getLatitude(), address.getLongitude());
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
                mGoogleMap.addMarker(
                        new MarkerOptions().
                                position(location).
                                title(input)
                );
                mGoogleMap.setOnMarkerClickListener(this);
            }
cord Inserted", Toast.LENGTH_SHORT).show();
        } else {d Inserted", Toast.LENGTH_SHORT).show();
        }
        //오류나면 data-data-databases 에서 db삭제하고 다시해보기

    }

}
