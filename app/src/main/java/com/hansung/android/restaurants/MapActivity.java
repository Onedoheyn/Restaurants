package com.hansung.android.restaurants;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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

    EditText inputedit;
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

//
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    final int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION=0;
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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
                    LatLng location1 = new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude());
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location1,15));
                    mGoogleMap.addMarker(
                            new MarkerOptions().
                                    position(location1).
                                    title(input)
                    );
                    Toast.makeText(getApplicationContext(),
                            "위도 "+mCurrentLocation.getLatitude()+"경도 "+mCurrentLocation.getLongitude(),
                            Toast.LENGTH_SHORT)
                            .show();


                    //Toast.makeText(getApplicationContext(), mCurrentLocation.getLatitude() +  Toast.LENGTH_SHORT).show();
                    //updateUI();
                } else
                    Toast.makeText(getApplicationContext(), "no_location_detected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override

    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        mGoogleMap.setOnMarkerClickListener(this);
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

                intent.putExtra("name",inputedit.getText().toString());




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
     inputedit = (EditText) findViewById(R.id.edittext);
        TextView mResultText = (TextView) findViewById(R.id.textview);
        String input = inputedit.getText().toString();

        try {
            Geocoder geocoder = new Geocoder(this, Locale.KOREA);
            List<Address> addresses = geocoder.getFromLocationName(input, 1);
            if (addresses.size() > 0) {
                Address bestResult = (Address) addresses.get(0);

                mResultText.setText(String.format("[ %s , %s ]",
                        bestResult.getLatitude(),
                        bestResult.getLongitude()));

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




}
