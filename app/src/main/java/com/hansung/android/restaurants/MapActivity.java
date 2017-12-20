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

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    private DBHelper DbHelper;
    private DBHelper3 mDbHelper3;
    private Location mLastLocation;
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
//---- switch case 문으로 GPS버튼, 1,2,3 km 버튼 눌러질때 --//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.gps:
                getLastLocation();
                getDistance4();

                return true;

            case R.id.map_1km:
                item.setChecked(true);
                getDistance1();
                return true;

            case R.id.map_2km:
                item.setChecked(true);
                getDistance2();
                return true;

            case R.id.map_3km:
                item.setChecked(true);
                getDistance3();
                return true;




            default:
                return super.onOptionsItemSelected(item);
        }
    }


    final int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION = 0;
    private FusedLocationProviderClient mFusedLocationClient;
    Location mCurrentLocation;
    private GoogleMap mGoogleMap;
    String input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getaddress();

            }
        });
        DbHelper = new DBHelper(this);
        mDbHelper3 = new DBHelper3(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        if (!checkLocationPermissions()) {
            requestLocationPermissions(REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION);
        } else {
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
                MapActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                requestCode
        );
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation();
                } else {
                    Toast.makeText(this, "Permission required", Toast.LENGTH_SHORT);
                }
            }
        }
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        Task task = mFusedLocationClient.getLastLocation();       // Task<Location> 객체 반환함
        task.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    mCurrentLocation = location;
                    LatLng location1 = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location1, 15));
                    mGoogleMap.addMarker(
                            new MarkerOptions().
                                    position(location1).
                                    title(input)
                    );
                    Toast.makeText(getApplicationContext(),
                            "위도 " + mCurrentLocation.getLatitude() + "경도 " + mCurrentLocation.getLongitude(),
                            Toast.LENGTH_SHORT)
                            .show();
                    //위도 경도를 토스트 메세지로 띄워줌


                    //Toast.makeText(getApplicationContext(), mCurrentLocation.getLatitude() +  Toast.LENGTH_SHORT).show();
                    //updateUI();
                } else
                    Toast.makeText(getApplicationContext(), "no_location_detected", Toast.LENGTH_SHORT).show();
            }
        });
    }


   private void getDistance1() { // 1km 이내

       Location loc1 = new Location("location 1 name");

       loc1.setLatitude(mCurrentLocation.getLatitude());// 현재위치의 위도 설정
       loc1.setLongitude(mCurrentLocation.getLongitude());// 현재위치 경도 설정
       mGoogleMap.clear();

       Cursor cursor = DbHelper.getAllUsersBySQL(); //

        while (cursor.moveToNext()) { // 등록된 맛집의 위도랑 경도 찾음
            double x = cursor.getDouble(5);
            double y = cursor.getDouble(6);

            Location location4 = new Location(" ");
            location4.setLatitude(x); // 데이터베이스 안의 맛집 위도
            location4.setLongitude(y); // 데이터베이스 안의 맛집 경도
            distance = loc1.distanceTo(location4);

            if(distance<1000){ //distance가 1km 이내일 때
                LatLng current_markLocate = new LatLng(loc1.getLatitude(), loc1.getLongitude());
                mGoogleMap.addMarker( new MarkerOptions().
                        position(current_markLocate).
                        title(cursor.getString(2)));
                // current_markLocage 안에 현 위치를 저장하고 마커 뜨게 함

                LatLng markLocate = new LatLng(x, y);
                mGoogleMap.addMarker(
                        new MarkerOptions().
                                position(markLocate).
                                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).
                                title(cursor.getString(2))
                                                   );
                // 데이터베이스 안의 맛집 위치를 markLocate에 저장하고 범위 안에 있으면 마커를 찍게 함

                               }
                       }
    }


    private void getDistance2() {
        Location loc1 = new Location("location 1 name");
        loc1.setLatitude(mCurrentLocation.getLatitude());
        loc1.setLongitude(mCurrentLocation.getLongitude());

        mGoogleMap.clear();

        Cursor cursor = DbHelper.getAllUsersBySQL();

        while (cursor.moveToNext()) { // 위도랑 경도 찾음
            double x = cursor.getDouble(5);
            double y = cursor.getDouble(6);

            Location location4 = new Location(" ");
            location4.setLatitude(x);
            location4.setLongitude(y);
            distance = loc1.distanceTo(location4);

            if(distance<2000){
                LatLng current_markLocate = new LatLng(loc1.getLatitude(), loc1.getLongitude());
                mGoogleMap.addMarker( new MarkerOptions().
                        position(current_markLocate).
                        title(cursor.getString(2)));

                LatLng markLocate = new LatLng(x, y);
                mGoogleMap.addMarker(
                        new MarkerOptions().
                                position(markLocate).
                                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).
                                title(cursor.getString(2))
                );

            }
        }

    }

    private void getDistance3() {

        Location loc1 = new Location("location 1 name");

        loc1.setLatitude(mCurrentLocation.getLatitude());
        loc1.setLongitude(mCurrentLocation.getLongitude());
        mGoogleMap.clear();

        Cursor cursor = DbHelper.getAllUsersBySQL();

        while (cursor.moveToNext()) { // 위도랑 경도 찾음
            double x = cursor.getDouble(5);
            double y = cursor.getDouble(6);

            Location location4 = new Location(" ");
            location4.setLatitude(x);
            location4.setLongitude(y);
            distance = loc1.distanceTo(location4);

            if(distance<3000){
                LatLng current_markLocate = new LatLng(loc1.getLatitude(), loc1.getLongitude());
                mGoogleMap.addMarker( new MarkerOptions().
                        position(current_markLocate).
                        title(cursor.getString(2)));

                LatLng markLocate = new LatLng(x, y);
                mGoogleMap.addMarker(
                        new MarkerOptions().
                                position(markLocate).
                                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).
                                title(cursor.getString(2))
                );
            }
        }

    }

    private void getDistance4() { // GPS 버튼 누르면 등록된 맛집을 뜨게 하는 코드

        Location loc1 = new Location("location 1 name");

        loc1.setLatitude(mCurrentLocation.getLatitude());// 현재 위치의 위도 경도 설정
        loc1.setLongitude(mCurrentLocation.getLongitude());
        mGoogleMap.clear(); // 마커 다 지움

        Cursor cursor = DbHelper.getAllUsersBySQL();

        while (cursor.moveToNext()) { // 위도랑 경도 찾음
            double x = cursor.getDouble(5);
            double y = cursor.getDouble(6);

            Location location4 = new Location(" ");
            location4.setLatitude(x);
            location4.setLongitude(y);
            distance = loc1.distanceTo(location4);

            if(distance<100000000){
                LatLng current_markLocate = new LatLng(loc1.getLatitude(), loc1.getLongitude());
                mGoogleMap.addMarker( new MarkerOptions().
                        position(current_markLocate).
                        title(cursor.getString(2))); // title은 마커 눌렀읗 시 위에 뜨는 내용


                LatLng markLocate = new LatLng(x, y);
                mGoogleMap.addMarker(
                        new MarkerOptions().
                                position(markLocate).
                                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).
                                title(cursor.getString(2))
                );
            }
        }

    }

//    private void GPSGPS(){
//        mGoogleMap.setOnMarkerClickListener(this);
//
//
//        Cursor cursor = DbHelper.getAllUsersBySQL();
//        cursor.moveToFirst();
//
//
//        while (cursor.moveToNext()) {
//            double x = cursor.getDouble(5);
//            double y = cursor.getDouble(6);
//
//            MarkerOptions makerOptions = new MarkerOptions();
//            makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
//                    .position(new LatLng(x, y))
//                    .title("마커") // 타이틀.
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//
//            // 2. 마커 생성 (마커를 나타냄)
//            mGoogleMap.addMarker(makerOptions);
//        }
//    }
//








    @Override

    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        mGoogleMap.setOnMarkerClickListener(this);
//        Cursor cursor = DbHelper.getAllUsersBySQL();
//        cursor.moveToFirst();
//
//
//        while (cursor.moveToNext()) {
//            double x = cursor.getDouble(5);
//            double y = cursor.getDouble(6);
//
//            MarkerOptions makerOptions = new MarkerOptions();
//            makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
//                    .position(new LatLng(x, y))
//                    .title("마커") // 타이틀.
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//
//            // 2. 마커 생성 (마커를 나타냄)
//            mGoogleMap.addMarker(makerOptions);
        
    }

    @Override
    public boolean onMarkerClick(Marker marker) { // 마커 클릭시 발생하는 이벤트

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("등록");
        alert.setMessage("새로운 맛집등록하시겠습니까 ? ");
        alert.setPositiveButton("네", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(getApplicationContext(), InsertActivity.class);
                // 등록을 누르면 InsertActivity로 넘어감(맛집 등록하는 액티비티)


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
        return true;

    }

    private void getaddress() { // Result에 있는 4가지 위도 경도를 표시
        inputedit = (EditText) findViewById(R.id.edittext);
        TextView mResultText = (TextView) findViewById(R.id.textview);
        TextView mResultText2 = (TextView) findViewById(R.id.textview2);
        TextView mResultText3 = (TextView) findViewById(R.id.textview3);
        TextView mResultText4 = (TextView) findViewById(R.id.textview4);
        String input = inputedit.getText().toString();


        try {
            Geocoder geocoder = new Geocoder(this, Locale.KOREA);
            List<Address> addresses = geocoder.getFromLocationName(input, 1);
            if (addresses.size() > 0) {
                Address bestResult = (Address) addresses.get(0);

                mResultText.setText(String.format("[ %s  ]",
                        bestResult.getLatitude()));

                mResultText2.setText(String.format("[ %s ]",
                        bestResult.getLongitude()));

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
        } catch (IOException e) {
            Log.e(getClass().toString(), "Failed in using Geocoder.", e);
            return;
        }
    }

    private void insertRecord() { // Result에 뜬 위도경도들을 DB3에 저장하는 코드
        inputedit = (EditText) findViewById(R.id.edittext);
        TextView mResultText = (TextView) findViewById(R.id.textview);
        TextView mResultText2 = (TextView) findViewById(R.id.textview2);
        TextView mResultText3 = (TextView) findViewById(R.id.textview3);
        TextView mResultText4 = (TextView) findViewById(R.id.textview4);
        String input = inputedit.getText().toString();

        try {
            Geocoder geocoder = new Geocoder(this, Locale.KOREA);
            List<Address> addresses = geocoder.getFromLocationName(input, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                mResultText.setText(String.format("%s", address.getLatitude()));
                mResultText2.setText(String.format("%s", address.getLongitude()));
                mResultText3.setText(String.format("%s", mCurrentLocation.getLatitude()));
                mResultText4.setText(String.format("%s", mCurrentLocation.getLongitude()));

                LatLng location = new LatLng(address.getLatitude(), address.getLongitude());
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
                mGoogleMap.addMarker(
                        new MarkerOptions().
                                position(location).
                                title(input)
                );
                mGoogleMap.setOnMarkerClickListener(this);
            }

        } catch (IOException e) {
            Log.e("LocationService", "Failed in using Geocoder", e);
        }

        long nOfRows = mDbHelper3.insertUserByMethod(mResultText.getText().toString(), mResultText2.getText().toString()
                , mResultText3.getText().toString(), mResultText4.getText().toString());
        if (nOfRows > 0) {
            Toast.makeText(this, nOfRows + " Record Inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No Record Inserted", Toast.LENGTH_SHORT).show();
        }
        //오류나면 data-data-databases 에서 db삭제하고 다시해보기

    }
}
