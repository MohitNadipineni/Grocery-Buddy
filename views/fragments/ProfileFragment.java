package com.asquare.grocerybuddy.views.fragments;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.asquare.grocerybuddy.R;
import com.asquare.grocerybuddy.RoomDatabase.MyRoomDataBase;
import com.asquare.grocerybuddy.utils.AppConstants;
import com.asquare.grocerybuddy.utils.AppPreference;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    private CircleImageView ivProfile;
    private Button btSave;
    private EditText edFirstName, edLastName;
    private MyRoomDataBase myRoomDataBase;

    Uri cam_uri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivProfile = view.findViewById(R.id.iv_profile);
        btSave = view.findViewById(R.id.btSave);
        edFirstName = view.findViewById(R.id.edFirstName);
        edLastName = view.findViewById(R.id.edLastName);

        edFirstName.setText(AppPreference.getAppPreference(requireContext()).getValue(AppConstants.NAME, ""));
        edLastName.setText(AppPreference.getAppPreference(requireContext()).getValue(AppConstants.Last_NAME, ""));

        if (!AppPreference.getAppPreference(requireContext()).getValue(AppConstants.IMAGE, "").equals("")) {
            ivProfile.setImageURI(Uri.parse(AppPreference.getAppPreference(requireContext()).getValue(AppConstants.IMAGE, "")));
        }

        init();
        ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA, WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    private void init() {

        MyRoomDataBase myRoomDataBaseinstance = MyRoomDataBase.getMyRoomInstance(requireContext());
        myRoomDataBase = myRoomDataBaseinstance;

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickCamera();
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppPreference.getAppPreference(requireContext()).save(AppConstants.Last_NAME, edLastName.getText().toString().trim());
                myRoomDataBase.mainDao().updateItem(AppPreference.getAppPreference(requireContext()).getValue(AppConstants.ID, 1),
                        edFirstName.getText().toString(),
                        edLastName.getText().toString(),
                        AppPreference.getAppPreference(requireContext()).getValue(AppConstants.IMAGE, ""));

                Toast.makeText(requireContext(), "Profile Updated successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void pickCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
        cam_uri = requireContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cam_uri);

        AppPreference.getAppPreference(requireContext()).save(AppConstants.IMAGE, cam_uri.toString());

        startCamera.launch(cameraIntent);

    }


    ActivityResultLauncher<Intent> startCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        ivProfile.setImageURI(cam_uri);
                    }
                }
            });


}