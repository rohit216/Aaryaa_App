package com.example.kanchan.aaryaa_app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CaseTracking extends Fragment {

    View view;
    FirebaseAuth mAuth;
    DatabaseReference mref;
    EditText editcaseId;
    SharedStateDistrictDetails details;
    FirebaseUser user;
    String caseId;
    TextView status;
    Button caseTrack;
    public CaseTracking() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_case_tracking, container, false);

        mAuth = FirebaseAuth.getInstance();
        mref = FirebaseDatabase.getInstance().getReference("AdminCredential");
        editcaseId = view.findViewById(R.id.editCaseID);
        caseTrack = view.findViewById(R.id.caseTrack);
        status = view.findViewById(R.id.status);
        details = new SharedStateDistrictDetails(getContext());
        user = mAuth.getCurrentUser();
        caseTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                caseTrack();
            }
        });
        return view;
    }

    public void caseTrack(){

        try {
            caseId = editcaseId.getText().toString();
            mref.child("State").child(details.getState()).child("District").child(details.getDistrict()).child(details.getCenter()).child("WHL Admin").child("Women").child(user.getUid()).child("cases").child(caseId).child("trace").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Toast.makeText(getContext(), dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();

                    status.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        catch(Exception ex){

            Toast.makeText(getContext(),ex.toString(),Toast.LENGTH_SHORT).show();
        }
    }

}
