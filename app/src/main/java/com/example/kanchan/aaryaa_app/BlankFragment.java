package com.example.kanchan.aaryaa_app;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BlankFragment extends Fragment {

    View view;
    TextView open181,open181Chh,WHLWomen;
    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_blank, container, false);
        open181 = view.findViewById(R.id.india);
        open181Chh = view.findViewById(R.id.chh);
        WHLWomen = view.findViewById(R.id.women);

        open181.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                open181();

            }
        });


        open181Chh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                open181Chhatisgarh();

            }
        });


        WHLWomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openWomenAndChildDevelopment();

            }
        });
        return view;
    }

    public void open181()
    {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://181india.in"));
        intent.createChooser(intent,"Open browser");
        startActivity(intent);
    }

    public void open181Chhatisgarh()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://181chhattisgarh.in"));
        intent.createChooser(intent,"Open browser");
        startActivity(intent);
    }

    public void openWomenAndChildDevelopment()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://wcd.nic.in"));
        intent.createChooser(intent,"Open browser");
        startActivity(intent);
    }


}
