package com.asquare.grocerybuddy.views.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.asquare.grocerybuddy.Model.StoreDetailsModel;
import com.asquare.grocerybuddy.R;
import com.asquare.grocerybuddy.RoomDatabase.MyRoomDataBase;
import com.asquare.grocerybuddy.RoomDatabase.StoreDao;
import com.asquare.grocerybuddy.utils.AppConstants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DecimalFormat;


@RequiresApi(api = Build.VERSION_CODES.N)
public class DeliveryDetailFragment extends Fragment {

    private FusedLocationProviderClient fusedLocationClient;
    private int storeId, totalPrice;
    Double deliveyPrice;
    StoreDao storeDao;
    TextView tvDistance, tvDestination, tvPrice;
    Button btConfirm,btBack;

    private MyRoomDataBase myRoomDataBase;
    private StoreDetailsModel storeDetailsModel;

    ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts
                            .RequestMultiplePermissions(), result -> {
                        Boolean fineLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_FINE_LOCATION, false);
                        Boolean coarseLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_COARSE_LOCATION, false);
                        if (fineLocationGranted != null && fineLocationGranted) {
                            // Precise location access granted.
                        } else if (coarseLocationGranted != null && coarseLocationGranted) {
                            // Only approximate location access granted.
                        } else {
                            // No location access granted.
                        }
                    }
            );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_delivery_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvDistance = view.findViewById(R.id.tv_distance);
        tvDestination = view.findViewById(R.id.tv_destination);
        tvPrice = view.findViewById(R.id.tv_price);
        btConfirm = view.findViewById(R.id.bt_confirm);
        btBack=view.findViewById(R.id.bt_back);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        getLastLocation();

        MyRoomDataBase myRoomDataBaseinstance = MyRoomDataBase.getMyRoomInstance(requireContext());
        storeDao = myRoomDataBaseinstance.storeDao();

        if (getArguments() != null) {
            storeId = getArguments().getInt(AppConstants.STORE_ID);
            totalPrice = getArguments().getInt(AppConstants.TOTAL_PRICE);
            storeDetailsModel = storeDao.getLatLng(storeId);
        }

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCartFragment();
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

    }

    private void getLastLocation() {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions

        if (checkLocationPermission()) {
            checkGps();
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Toast.makeText(requireContext(), "Lat=" + location.getLatitude() + "Lng=" + location.getLongitude(), Toast.LENGTH_SHORT).show();
                                //addMarker(location.getLatitude(), location.getLongitude(), "");

                                Double distance = distance(location.getLatitude(), location.getLongitude(), storeDetailsModel.getLatitude(), storeDetailsModel.getLng(), 'K');
                                DecimalFormat df = new DecimalFormat("0.00");
                                tvDistance.setText(String.valueOf(df.format(distance) + "KM"));
                                tvDestination.setText("Lat=" + location.getLatitude() + "Lng=" + location.getLongitude());
                                tvPrice.setText("$" + String.valueOf(df.format(distance * 4)));
                                deliveyPrice=distance*4;
                            }
                        }
                    });
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(requireContext())
                        .setTitle("Location Permission")
                        .setMessage("Location Permission")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                locationPermissionRequest.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});

                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                locationPermissionRequest.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});

            }
            return false;
        } else {
            return true;
        }
    }

    private void checkGps() {
        LocationManager lm = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            new AlertDialog.Builder(requireContext())
                    .setMessage("GPS is not enable")
                    .setPositiveButton("openSetting", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            requireActivity().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    });
        }
    }

    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private void openCartFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.TOTAL_PRICE, totalPrice);
        bundle.putDouble(AppConstants.DELIVERY_AMOUNT, deliveyPrice);
        CartFragment cartFragment = new CartFragment();
        cartFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.mainContainer, cartFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}