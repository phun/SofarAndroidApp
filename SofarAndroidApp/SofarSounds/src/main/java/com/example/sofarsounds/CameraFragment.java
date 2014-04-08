package com.example.sofarsounds;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by phun on 3/28/14.
 */
public class CameraFragment extends Fragment {

    private static final String TAG = "CameraFragment";

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final int CAMERA_RESULT = 3;

    private Context context;
    private Uri capturedImageUri;

    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.camera_fragment, container, false);

        context = rootView.getContext();

        imageView = (ImageView) rootView.findViewById(R.id.imageView);

        final Button captureButton = (Button) rootView.findViewById(R.id.capture_button);
        captureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                File file = new File(Environment.getExternalStorageDirectory(),  (cal.getTimeInMillis()+".jpg"));
                if(!file.exists()){
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }else{
                    file.delete();
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                capturedImageUri = Uri.fromFile(file);
                Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                i.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
                startActivityForResult(i, CAMERA_RESULT);
            }
        });

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

    private void showMapScreen() {
        Fragment newFragment = new MapListFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.initial_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_RESULT) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap( context.getContentResolver(),  capturedImageUri);
                imageView.setBackground(null);
                imageView.setImageBitmap(bitmap);
                Log.v(TAG, "bitmap created.");
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}