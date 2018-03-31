package com.example.kanchan.aaryaa_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

/**
 * Created by gdevop on 29/3/18.
 */



public class AdminSetting extends AppCompatActivity {


    View adminChangeUnameView,adminChangePassView,adminAddProfileView;
    SharedStateDistrictDetails details;
    String State,District,Center;
    TextView adminCU,adminCP,adminAP;
    AlertDialog dialog;
    Button adminChangeUB,adminChangePB,adminAddPB;
    DatabaseReference mref;
    EditText ouname,nuname,cuname;
    EditText opass,npass,cpass;
    String ounamestr,nunamestr,cunamestr,adminUname;
    String opassstr,npassstr,cpassstr,adminPass;

    public void onCreate(Bundle SavedInstanceState){

        super.onCreate(SavedInstanceState);
        setContentView(R.layout.admin_setting_view);

        details = new SharedStateDistrictDetails(getApplicationContext());
        adminChangeUnameView = getLayoutInflater().inflate(R.layout.admin_changeuname_view, null);
        adminChangePassView = getLayoutInflater().inflate(R.layout.admin_changepass_view, null);
        adminAddProfileView = getLayoutInflater().inflate(R.layout.admin_addprofile_view, null);

        adminCU =(TextView)findViewById(R.id.changeAdminUname);
        adminCP =(TextView)findViewById(R.id.changeAdminPass);
        adminAP =(TextView)findViewById(R.id.adminAddProfile);

        adminChangeUB = adminChangeUnameView.findViewById(R.id.adminChngeUButton);
        adminChangePB = adminChangePassView.findViewById(R.id.adminChngePButton);

        ouname = adminChangeUnameView.findViewById(R.id.adminOldUname);
        nuname = adminChangeUnameView.findViewById(R.id.adminNewUname);
        cuname = adminChangeUnameView.findViewById(R.id.adminConfirmUname);

        opass = adminChangePassView.findViewById(R.id.adminOldPass);
        npass = adminChangePassView.findViewById(R.id.adminNewPass);
        cpass = adminChangePassView.findViewById(R.id.adminConfirmPass);



        State = details.getState();
        District = details.getDistrict();
        Center = details.getCenter();

        mref = FirebaseDatabase.getInstance().getReference("AdminCredential");



        adminCU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassDialog();
            }
        });

        adminCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUnameDialog();
            }
        });

        adminChangeUB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAdminUname();
            }
        });


        adminChangePB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAdminPass();
            }
        });


    }

    public void changeUnameDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        dialog.setView(adminChangeUnameView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void changePassDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        dialog.setView(adminChangePassView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void changeAdminUname()
    {
        ounamestr = ouname.getText().toString();

        mref.child("State").child(details.getState()).child("District").child(details.getDistrict()).child(details.getCenter()).child("WHL Admin").child("Details").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot cre : dataSnapshot.getChildren()){

                    adminUname = cre.child("username").getValue().toString();

                    if(adminUname.equals(ounamestr))
                    {

                        nunamestr = nuname.getText().toString();
                        cunamestr = cuname.getText().toString();
                        if (!(nunamestr.equals(ounamestr)))
                        {
                            cuname.setError("Username doesn't match");

                        }
                        else
                        {
                            mref.child("State").child(details.getState()).child("District").child(details.getDistrict()).child(details.getCenter()).child("WHL Admin").child("Details").child("username").setValue(nuname);

                        }
                    }
                    else{

                        ouname.setError("Incorrect Username");
                        // Toast.makeText(getApplicationContext(),"Authentication Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public void changeAdminPass()
    {

        opassstr = opass.getText().toString();

        mref.child("State").child(details.getState()).child("District").child(details.getDistrict()).child(details.getCenter()).child("WHL Admin").child("Details").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot cre : dataSnapshot.getChildren()){

                    adminPass = cre.child("password").getValue().toString();

                    if(adminPass.equals(opassstr))
                    {

                        npassstr = npass.getText().toString();
                        cpassstr = cpass.getText().toString();
                        if (!(npassstr.equals(opassstr)))
                        {
                            cpass.setError("Password doesn't match");

                        }
                        else
                        {
                            mref.child("State").child(details.getState()).child("District").child(details.getDistrict()).child(details.getCenter()).child("WHL Admin").child("Details").child("password").setValue(npass);

                        }
                    }
                    else{

                        opass.setError("Incorrect Password");
                        // Toast.makeText(getApplicationContext(),"Authentication Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
