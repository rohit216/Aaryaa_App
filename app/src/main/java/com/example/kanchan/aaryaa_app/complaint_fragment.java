package com.example.kanchan.aaryaa_app;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class complaint_fragment extends Fragment {


    private View view;
    public  BarChart chart;
    SharedStateDistrictDetails details;

    DatabaseReference mref;
    public static complaint_fragment newInstance() {
        complaint_fragment fragment = new complaint_fragment();
        return fragment;
    }

    public complaint_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.fragment_complaint_fragment, container, false);
         chart = (BarChart) view.findViewById(R.id.chart);
         details = new SharedStateDistrictDetails(getContext());
         BarData data = new BarData(getXAxisValues(),getDataSet());
         mref = FirebaseDatabase.getInstance().getReference("AdminCredentials");
        chart.setData(data);
        chart.setDescription(details.getCenter() + ", "+details.getDistrict()+ "  "+"("+details.getState()+")");
        //chart.setDrawBarShadow(true);
        chart.animateXY(2000, 2000);
        chart.invalidate();
 return view;
    }

    private ArrayList<IBarDataSet> getDataSet() {

        ArrayList<IBarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(200.000f, 0); // Jan
        valueSet1.add(v1e1);


        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v1e2 = new BarEntry(120.000f, 1); // Jan
        valueSet2.add(v1e2);


        ArrayList<BarEntry> valueSet3 = new ArrayList<>();
        BarEntry v1e3 = new BarEntry(80.00f,2);
        valueSet3.add(v1e3);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Registered Cases");
        barDataSet1.setColor(Color.BLUE);

        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Pending Cases");
        barDataSet2.setColor(Color.RED);

        BarDataSet barDataSet3 = new BarDataSet(valueSet3,"Resolved Cases");
        barDataSet3.setColor(Color.GREEN);

        dataSets = new ArrayList<>();

        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        dataSets.add(barDataSet3);

        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Registered");
        xAxis.add("Pending");
        xAxis.add("Resolved");
        return xAxis;
    }

    public String returnTotalCases(){

        return "data";

    }


}
