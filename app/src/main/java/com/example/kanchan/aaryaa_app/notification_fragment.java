package com.example.kanchan.aaryaa_app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class notification_fragment extends Fragment {



    public static notification_fragment newInstance() {
        notification_fragment fragment = new notification_fragment();
        return fragment;
    }
    public notification_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification_fragment, container, false);
    }

}
