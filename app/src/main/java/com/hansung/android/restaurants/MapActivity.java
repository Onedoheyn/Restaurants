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
        switch (item.getItemId()) {

            case R.id.gps:
                getLastLocation();

                return true;

            case R.id.map_1km:
                item.setChecked(true);

                if(distance<1000)
                {
                    mGoogleMap.clear();
                }
                getaddress();
                return true;


//
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    final int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION = 0;
    private FusedLocationProviderClient mFusedLocationClient;
    Location mCurrentLocation;
    private GoogleMap mGoogleMap;
    String mResultText;
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
                MapActivity.this,            // MainActivity 액티비티의 객체 인스턴스를 나타냄
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},        // 요청할 권한 목록을 설정한 String 배열
                requestCode    // 사용자 정의 int 상수. 권한 요청 결과를 받을 때
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
        Task task = mFusedLocationClient.getLastLocation();       // Task<Location> 객체 반환
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


                    //Toast.makeText(getApplicationContext(), mCurrentLocation.getLatitude() +  Toast.LENGTH_SHORT).show();
                    //updateUI();
                } else
                    Toast.makeText(getApplicationContext(), "no_location_detected", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public double getDistance_1km() {

        Location loc1 = new Location("location 1 name");

        loc1.setLatitude(mCurrentLocation.getLatitude());
        loc1.setLongitude(mCurrentLocation.getLongitude());

        Cursor cursor = DbHelper.getAllUsersBySQL();
        cursor.moveToFirst(); // 처음으로 이동

        while (cursor.moveToNext()) { // 위도랑 경도 찾음
            double x = cursor.getDouble(5);
            double y = cursor.getDouble(6);

            Location loc2 = new Location("location 2 name");
            loc2.setLatitude(x);
            loc2.setLongitude(y);

            distance = loc1.distanceTo(loc2); // 거리 구함(단위:미터)


            // meter = Double.toString(distance);

            // Log.i("거리", String.valueOf(distance));
            // Toast.makeText(this, "거리 :  "+distance, Toast.LENGTH_SHORT).show();
/*
            if(distance>1000)
            {

                MarkerOptions makerOptions = new MarkerOptions();
                makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                        .position(new LatLng(x, y))
                        .visible(false);
                Toast.makeText(this, "출력 :  "+distance, Toast.LENGTH_SHORT).show();
            }
            */
        }

        return distance;
    }






    @Override

    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        mGoogleMap.setOnMarkerClickListener(this);
        Cursor cursor = DbHelper.getAllUsersBySQL();
        cursor.moveToFirst();


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
    public boolean onMarkerClick(Marker marker) {

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
        return true;

    }

    private void getaddress() {
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

    private void insertRecord() {
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
