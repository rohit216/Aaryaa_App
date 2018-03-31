package com.example.kanchan.aaryaa_app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ViewComplaint extends Fragment {

    public RecyclerView womenCases;
    FirebaseAuth mAuth;
    SharedStateDistrictDetails details;
    private View view;
    private DatabaseReference CaseRef,caseRef;
    private FirebaseUser user;

    public ViewComplaint() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
try {
    view = inflater.inflate(R.layout.fragment_view_complaint, container, false);
    womenCases = view.findViewById(R.id.womenCases);
    womenCases.setHasFixedSize(true);
    womenCases.setLayoutManager(new LinearLayoutManager(getContext()));
    mAuth = FirebaseAuth.getInstance();
    user = mAuth.getCurrentUser();
    caseRef = FirebaseDatabase.getInstance().getReference("AdminCredential");
    details = new SharedStateDistrictDetails(getContext());
    CaseRef = caseRef.child("State").child(details.getState()).child("District").child(details.getDistrict()).child(details.getCenter()).child("WHL Admin").child("Women").child(user.getUid()).child("cases");

    FirebaseRecyclerAdapter<WomenViewCases, ViewComplaint.CaseDetailsHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<WomenViewCases, ViewComplaint.CaseDetailsHolder>(WomenViewCases.class, R.layout.women_cases, ViewComplaint.CaseDetailsHolder.class, CaseRef) {
        @Override
        protected void populateViewHolder(ViewComplaint.CaseDetailsHolder viewHolder, WomenViewCases model, int position) {

            viewHolder.setCaseId(model.getCaseId());
            viewHolder.setTypeCase(model.getType_case());
            viewHolder.setRegisteredBy(model.getRegistered_by());
            viewHolder.setRegistrationData(model.getRegistration_date());
            viewHolder.setStatus(model.getStatus());
            viewHolder.setState(details.getState());
        }
    };
    womenCases.setAdapter(firebaseRecyclerAdapter);

}catch (Exception ex){

}
        return view;

    }


    public void onStart(){
        super.onStart();

    }

    public class CaseDetailsHolder extends RecyclerView.ViewHolder{


        View mview;
        public CaseDetailsHolder(View itemView) {
            super(itemView);
            mview = itemView;
        }

        public void setCaseId(String caseId) {

            TextView caseid = mview.findViewById(R.id.caseId);
            caseid.setText(caseId);
        }

        public void setStatus(String status) {


            TextView stat = mview.findViewById(R.id.status);
            stat.setText(status);
        }

        public void setTypeCase(String type_case) {

            TextView TypeCase = mview.findViewById(R.id.caseType);
            TypeCase.setText(type_case);
        }

        public void setRegisteredBy(String registered_by) {

            TextView rBy = mview.findViewById(R.id.registeredBy);
            rBy.setText(registered_by);
        }

        public void setRegistrationData(String registration_date) {

            TextView date = mview.findViewById(R.id.date);
            date.setText(registration_date);
        }

        public void setState(String state)
        {

            TextView State = mview.findViewById(R.id.state);
            State.setText(state);
        }
    }



}
