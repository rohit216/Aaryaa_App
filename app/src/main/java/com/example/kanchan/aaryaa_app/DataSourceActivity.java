package com.example.kanchan.aaryaa_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataSourceActivity extends AppCompatActivity {

    String stateArr[] = new String[]{"Andra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Madya Pradesh", "Maharashtra"}, districtArr[] = new String[]{"Nashik", "Sangli", "Kolhapur"};
    String distArr[] = new String[]{"Sangli","Kolhapur","Nashik","Latur"};
    AutoCompleteTextView state,district;
    EditText center,uname,pass,stateCode,districtCode;
    DatabaseReference mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_source);
        state = findViewById(R.id.stateDialog);
        district = findViewById(R.id.districtDialog);
        center = findViewById(R.id.centerDialog);
        uname = findViewById(R.id.adminUname);
        pass = findViewById(R.id.adminPass);
        stateCode = findViewById(R.id.stateCode);
        districtCode = findViewById(R.id.districtCode);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, stateArr);
        state.setAdapter(adapter);
        ArrayAdapter<String> districtAdpter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, distArr);
        district.setAdapter(districtAdpter);
        mref = FirebaseDatabase.getInstance().getReference("AdminCredential");
    }

    public void saveDetails(View view){

        String State,District,Center,Uname,Pass,Scode,Dcode;
        State = state.getText().toString();
        District = district.getText().toString();
        Center = center.getText().toString();
        Uname = uname.getText().toString();
        Pass = pass.getText().toString();
        Scode = stateCode.getText().toString();

        Dcode = districtCode.getText().toString();


        if(State.isEmpty()){

            state.setError("Required");
        }

        else if(District.isEmpty()){

            district.setError("Required");
        }

        else if(Center.isEmpty()){

            center.setError("Required");
        }

        else {

            DatabaseReference adminRef = mref;
            mref.child("State").child(State).child("code").setValue(Scode);
            mref.child("State").child(State).child("District").child(District).child("code").setValue(Dcode);
            adminRef.child("State").child(State).child("District").child(District).child(Center).child("WHL Admin").child("Details").child("username").setValue(Uname);
            adminRef.child("State").child(State).child("District").child(District).child(Center).child("WHL Admin").child("Details").child("password").setValue(Pass);
            Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
        }
    }
}
