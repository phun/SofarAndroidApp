package com.example.sofarsounds;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.parse.ParseAnalytics;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.example.sofarsounds.AttendRequestFragment.OnRequestSubmit} interface
 * to handle interaction events.
 * Use the {@link AttendRequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class AttendRequestFragment extends Fragment {

    static final int REQUEST_VIDEO_CAPTURE = 1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "city";
    private static final String ARG_PARAM2 = "date";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button recordButton;

    private OnRequestSubmit mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param city Parameter 1.
     * @param date Parameter 2.
     * @return A new instance of fragment AttendRequestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AttendRequestFragment newInstance(String city, String date) {
        AttendRequestFragment fragment = new AttendRequestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, city);
        args.putString(ARG_PARAM2, date);
        fragment.setArguments(args);
        return fragment;
    }
    public AttendRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ParseAnalytics.trackEvent("AttendRequestFragment");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_attend_request, container, false);

        ((TextView) rootView.findViewById(R.id.request_city)).setText("Sofar " + mParam1);
        ((TextView) rootView.findViewById(R.id.request_date)).setText(mParam2);
        final TextView textbox = ((TextView) rootView.findViewById(R.id.request_text));
        ((Button) rootView.findViewById(R.id.request_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onRequestSubmit(textbox.getText().toString());
                }
            }
        });

        Button requestButton = (Button) rootView.findViewById(R.id.request_button);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayRequestSent();
            }
        });

        recordButton = (Button) rootView.findViewById(R.id.record_button);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakeVideoIntent();
            }
        });

        return rootView;
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnRequestSubmit) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnRequestSubmit");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Uri videoUri = data.getData();
            Log.v("c", videoUri.toString());
            final Dialog dialog = new Dialog(getActivity());

            MediaController mediaController = new MediaController(this.getActivity());

            dialog.setContentView(R.layout.fragment_video);
            dialog.setTitle("Add Video?");
            final VideoView videoView = (VideoView) dialog.findViewById(R.id.videoView);
            videoView.setVideoURI(videoUri);
            videoView.setMediaController(mediaController);
            videoView.start();

            Button addButton = (Button) dialog.findViewById(R.id.add);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    recordButton.setText("Video Added (" + videoView.getDuration() + "ms)");
                }
            });

            Button cancelButton = (Button) dialog.findViewById(R.id.cancel);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }

    private void displayRequestSent() {
        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.attend_request_dialog);
        dialog.setTitle("Request Sent!");

        final Button okay = (Button) dialog.findViewById(R.id.okay);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRequestSubmit {
        // TODO: Update argument type and name
        public void onRequestSubmit(String request);
    }

}
