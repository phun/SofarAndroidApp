package com.example.sofarsounds;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * Created by phun on 3/28/14.
 */
public class CameraFragment extends Fragment {

    private static final String TAG = "CameraFragment";

    private Camera mCamera;
    private CameraPreview mPreview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.camera_fragment, container, false);

        // Create an instance of Camera
        mCamera = getCameraInstance();

        if (mCamera != null) {
            mCamera.setDisplayOrientation(90);
        }

        Log.v(TAG, mCamera.toString());

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(rootView.getContext(), mCamera);
        FrameLayout preview = (FrameLayout) rootView.findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        final Button nextButton = (Button) rootView.findViewById(R.id.skip_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showMapScreen();
            }
        });

        return rootView;
    }

    private boolean hasCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true;
        } else {
            return false;
        }
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        int cameraCount = 0;
        int camId = -1;
        try {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            cameraCount = Camera.getNumberOfCameras();

            for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
                Camera.getCameraInfo(camIdx, cameraInfo);
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    camId = camIdx;
                }
            }

            if (camId > -1) {
                c = Camera.open(camId);
            } else {
                c = Camera.open();
            }

        }
        catch (Exception e){
            Log.v(TAG, "No camera hardware.");
        }
        return c; // returns null if camera is unavailable
    }

    private void showMapScreen() {
        Fragment newFragment = new MapListFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.initial_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}