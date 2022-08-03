package com.asquare.grocerybuddy.views.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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
import android.widget.EditText;
import android.widget.Toast;

import com.asquare.grocerybuddy.R;
import com.asquare.grocerybuddy.views.activities.DashboardActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private double mLatitide = 0.0;
    private double mLongitude = 0.0;

    private GoogleMap mMap;
    private Button btPostalCode, btLocateMe, btstores;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private FusedLocationProviderClient fusedLocationClient;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


        //todo: get last location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());


        btPostalCode = view.findViewById(R.id.button2);
        btLocateMe = view.findViewById(R.id.button);

        btstores = view.findViewById(R.id.btstores);


        clicklistners();
    }

    private void clicklistners() {

        btPostalCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPostalCodeDialog();
            }
        });
        btLocateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btPostalCode.setText("Enter Your Postal Code");
                getLastLocation();
            }
        });

        btstores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeFragment();
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
                                addMarker(location.getLatitude(), location.getLongitude(), "");
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

    private void openPostalCodeDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("please enter postal code");

        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
        builder.setView(customLayout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText editText = customLayout.findViewById(R.id.editText);

                getLetLongViaPostalCode(editText.getText().toString());

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

    }


    private void addMarker(Double mLatitide, Double mLongitude, String markerTitle) {
        LatLng position = new LatLng(mLatitide, mLongitude);
        mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(markerTitle));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                position, 15);
        mMap.animateCamera(location);
    }

    private void getLetLongViaPostalCode(String zipCode) {
        final Geocoder geocoder = new Geocoder(requireContext());

        try {
            List<Address> addresses = geocoder.getFromLocationName(zipCode, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                // Use the address as needed
                String message = String.format("Latitude: %f, Longitude: %f", address.getLatitude(), address.getLongitude());
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();

                addMarker(address.getLatitude(), address.getLongitude(), address.getAddressLine(0));

                btPostalCode.setText("Location for " + zipCode);
            } else {
                // Display appropriate message when Geocoder services are not available
                Toast.makeText(requireContext(), "Unable to geocode zipcode", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            // handle exception
        }
    }


    private void openHomeFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment paymentFragment = new HomeFragment();
        fragmentTransaction.replace(R.id.mainContainer, paymentFragment);
        fragmentTransaction.commit();
    }

}