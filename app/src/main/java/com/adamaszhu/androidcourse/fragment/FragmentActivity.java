package com.adamaszhu.androidcourse.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.utility.BaseActivity;
import com.adamaszhu.androidcourse.databinding.ActivityFragmentBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentActivity extends BaseActivity {

    private static String LOG_TAG = FragmentActivity.class.getName();

    private ActivityFragmentBinding binding;
    private GoogleMap googleMap;

    @Override
    public void load() {
        binding = ActivityFragmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void setup() {
        binding.btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLayoutFragment(String.valueOf(binding.btnList.getText()));
            }
        });
        binding.btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupMap();
            }
        });
        getUserLocation();
    }

    private void setLayoutFragment(String layoutType) {
        LayoutFragment fragment = LayoutFragment.newInstant(layoutType);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(layoutType);
        transaction.commit();
    }

    private void getUserLocation() {
        LocationManager locationManager = getSystemService(LocationManager.class);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    double lat = location.getLatitude();
                    double lng = location.getLongitude();
                    if (googleMap != null) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        googleMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title("I am here"));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng ));
                    }
                }
            });
        }
    }

    private void setupMap() {
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mapFragment);
        transaction.commit();
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                FragmentActivity.this.googleMap = googleMap;
            }
        });
    }

    @Override
    public void show() {
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean o) {
                    if (o) {
                        Log.i(LOG_TAG, "Location permission granted");
                    } else {
                        Log.i(LOG_TAG, "Location permission rejected");
                    }
                }
            });
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }
}