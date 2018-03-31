package com.example.kanchan.aaryaa_app;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class StateDestrictDetails extends AppCompatActivity {

    String stateArr[] = new String[]{"Andra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Madya Pradesh", "Maharashtra"}, districtArr[] = new String[]{"Nashik", "Sangli", "Kolhapur"};
    String distArr[] = new String[]{"Sangli","Kolhapur","Nashik","Latur"};
    SharedStateDistrictDetails details;
    AutoCompleteTextView state,district;
    DatabaseReference mref;
    EditText center;
    String CenterName;
    Boolean result;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.state_district_dialog);
        details = new SharedStateDistrictDetails(getApplicationContext());
        state = findViewById(R.id.stateDialog);
        district = findViewById(R.id.districtDialog);
        center = findViewById(R.id.centerDialog);
        mref = FirebaseDatabase.getInstance().getReference("AdminCredential");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, stateArr);
        state.setAdapter(adapter);
        ArrayAdapter<String> districtAdpter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, distArr);
        district.setAdapter(districtAdpter);
       // Toast.makeText(getApplicationContext(),"Center Name"+checkCenter(),Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),"Filled Stautus"+ details.isFiled(),Toast.LENGTH_LONG).show();
    }
@Override
    public void onStart(){

        super.onStart();

        if(details.isFiled()){

            startActivity(new Intent(getApplicationContext(),Home.class));
        }
    }


    public void saveDetails(View view){

        String State,District,Center;
        State = state.getText().toString();
        District = district.getText().toString();
        Center = center.getText().toString();

        if(State.isEmpty()){

            state.setError("Required");
        }

        else if(District.isEmpty()){

            district.setError("Required");
        }

        else if(Center.isEmpty()){

            center.setError("Required");
        }
        else
        {
            details.createSession(State,District,Center);
            startActivity(new Intent(getApplicationContext(),Home.class));
            finish();
////
//
//            try {
//
//                checkCenter(State,District,Center);
//            }
//            catch (Exception ex){
//
//                Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_SHORT).show();
//            }



        }
    }

//    private void checkCenter(String state, String district,  final String CenterN) {
//
//        try {
//
//            mref.child("State").child(state).child("District").child(district).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
////
////                    for (DataSnapshot cr : dataSnapshot.getChildren()) {
////
////                        CenterName = cr.child("CenterName").getValue().toString();
////                        if (CenterName.equals(CenterN)) {
////
////                            startActivity(new Intent(getApplicationContext(),Home.class));
////                            finish();
////
////                        } else {
////
////                            center.setError("Please check your Center Name");
////                            center.setFocusable(true);
////                            center.setText("");
////                        }
////                    }
//
//                    CenterName = dataSnapshot.getValue().toString();
//                    Toast.makeText(getApplicationContext(),CenterName + dataSnapshot.getValue().toString(),Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        }catch (Exception ex){
//
//        }
//    }

}
