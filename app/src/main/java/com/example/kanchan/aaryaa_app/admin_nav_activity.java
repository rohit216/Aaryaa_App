package com.example.kanchan.aaryaa_app;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

public class admin_nav_activity extends AppCompatActivity {

    View addStaffView,addMgmtView;
    private AlertDialog dialog;
    EditText addStaffName,addStaffMobNo,addStaffUname ;
    EditText addMgmtName,addMgmtEmail,addMgmtAddress,addMgmtContact;
    String addStaffNameStr,addStaffMobNoStr,addStaffUnameStr;
    String addMgmtNameStr,addMgmtRoleStr,addMgmtEmailStr,addMgmtAddressStr,addMgmtContactStr;
    Spinner addMgmtRole;
    DatabaseReference mref;
    String State,District,Center;
    Button addStaffButton,addMgmtButton;
    BottomNavigationView bottomNavigationView;
    FirebaseAuth mAuth;
    SharedStateDistrictDetails details;
    String[] role = new String[]{"Select your Role","Police","Medical","Lawyer"};
    public SessionManager session;
    StaffSession staffSession;
    long count =0;

    private static final String ALPHA = "0123456789";
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_admin_nav_activity);
             bottomNavigationView = (BottomNavigationView)
                    findViewById(R.id.navigation);
            addStaffView = getLayoutInflater().inflate(R.layout.admin_addstaff_view, null);
            addStaffName = addStaffView.findViewById(R.id.addStaffName);
            addStaffMobNo = addStaffView.findViewById(R.id.addStaffMobNo);
            addStaffUname = addStaffView.findViewById(R.id.addStaffUsername);
            addStaffButton = addStaffView.findViewById(R.id.addStaffButton);

            details = new SharedStateDistrictDetails(getApplicationContext());
            addStaffButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    addStaff();
                }
            });


            staffSession = new StaffSession(getApplicationContext());
            addMgmtView = getLayoutInflater().inflate(R.layout.admin_addmgmt_view,null);
            addMgmtRole = addMgmtView.findViewById(R.id.addMgmtRole);
            addMgmtName = addMgmtView.findViewById(R.id.addMgmtName);
            addMgmtEmail = addMgmtView.findViewById(R.id.addMgmtEmail);
            addMgmtContact = addMgmtView.findViewById(R.id.addMgmtContact);
            addMgmtAddress = addMgmtView.findViewById(R.id.addMgmtAddress);
            addMgmtButton = addMgmtView.findViewById(R.id.addMgmtButton);

            addMgmtButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    addMgmt();
                }
            });




            session = new SessionManager(getApplicationContext());
            details = new SharedStateDistrictDetails(getApplicationContext());

            mAuth = FirebaseAuth.getInstance();

            mref = FirebaseDatabase.getInstance().getReference("AdminCredential");



             bottomNavigationView.setOnNavigationItemSelectedListener
                    (new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            android.support.v4.app.Fragment selectedFragment = null;
                            switch (item.getItemId()) {
                                case R.id.action_item1:
                                    selectedFragment = home_fragment.newInstance();
                                    break;
                                case R.id.action_item2:
                                    selectedFragment = complaint_fragment.newInstance();
                                    break;
                                case R.id.action_item3:
                                    selectedFragment = notification_fragment.newInstance();
                                    break;
                            }
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout, selectedFragment);
                            transaction.commit();
                            return true;
                        }
                    });

             //Manually displaying the first fragment - one time only
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, home_fragment.newInstance());
            transaction.commit();

            if(staffSession.isLoggedIn()) {

                menu.findItem(R.id.action_addStaff).setVisible(false);
                menu.findItem(R.id.action_addManagement).setVisible(false);

               // menu.findItem(R.id.action_addStaff).setEnabled(false);
                //menu.findItem(R.id.action_addManagement).setEnabled(false);
            }
            else{
                menu.findItem(R.id.action_addStaff).setVisible(true);
                menu.findItem(R.id.action_addManagement).setVisible(true);

//                menu.findItem(R.id.action_addStaff).setEnabled(true);
//                menu.findItem(R.id.action_addManagement).setEnabled(true);

            }
        }catch(Exception ex){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            startActivity(new Intent(getApplicationContext(),AdminSetting.class));
        }

        if (id == R.id.action_signOut) {

            //Toast.makeText(getApplicationContext(),"User status"+session.isLoggedIn(),Toast.LENGTH_SHORT).show();
            //details.createSession(details.getState(),details.getDistrict(),details.getCenter());
              session.logoutUser();
              startActivity(new Intent(getApplicationContext(),Home.class));
        }


        if (id == R.id.action_profile) {
            //fetchData();
        }

        if (id == R.id.action_addStaff) {

            try {
                ViewGroup parent = (ViewGroup) addStaffView.getParent();

                if(parent==null) {
                    openDialog();
                }else{
                    parent.removeView(addStaffView);
                    openDialog();
                }
            }
            catch(Exception ex){
                Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_LONG).show();
            }
        }

        if(id == R.id.action_addManagement)
        {
            try
            {
            ViewGroup parent = (ViewGroup) addMgmtView.getParent();

            if(parent==null) {
                openMgmtDialog();
            }else{
                parent.removeView(addMgmtView);
                openMgmtDialog();
            }
            }
            catch(Exception ex){
            Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_LONG).show();
        }
        }

        return super.onOptionsItemSelected(item);
    }




    public void openDialog()
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        dialog = builder.create();
        dialog.setView(addStaffView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void addStaff()
    {

        addStaffNameStr = addStaffName.getText().toString();
        addStaffMobNoStr = addStaffMobNo.getText().toString();
        addStaffUnameStr = addStaffUname.getText().toString();

        if(addStaffNameStr.isEmpty())
        {
            addStaffName.setError("This field is required");
        }
        else if(addStaffMobNoStr.isEmpty())
        {
            addStaffMobNo.setError("This field is required");
        }
        else if(addStaffUnameStr.isEmpty())
        {
            addStaffUname.setError("This field is required");
        }
        else
        {

            String Username="MHSNS"+randomString(2), Password="WHL"+randomString(3);
            DatabaseReference mngRef = mref.child("State").child(details.getState()).child("District").child(details.getDistrict()).child(details.getCenter()).child("Staff Credentials").child(Username);
            mngRef.child("Name").setValue(addStaffNameStr);
            mngRef.child("Contact").setValue(addStaffMobNoStr);
            mngRef.child("Email").setValue(addStaffUnameStr);
            mngRef.child("Username").setValue(Username);
            mngRef.child("Password").setValue(Password);
            mngRef.child("Timestamp").setValue(ServerValue.TIMESTAMP);

            Toast.makeText(getApplicationContext(),"Added Success",Toast.LENGTH_LONG).show();
            String Msg = "Your Username : " + Username + "\n "+
                    "Your Password: " + Password + " ";
            SendEmail email = new SendEmail(getApplicationContext(),addStaffUnameStr,"Aaryaaa",Msg);
            email.execute();
            bottomNavigationView.removeView(addStaffView);
            startActivity(new Intent(getApplicationContext(), admin_nav_activity.class));


        }

    }
  public void addMgmt()
    {
        addMgmtRoleStr = addMgmtRole.getSelectedItem().toString();
        addMgmtNameStr = addMgmtName.getText().toString();
        addMgmtEmailStr =  addMgmtEmail.getText().toString();
        addMgmtContactStr =addMgmtContact.getText().toString();
        addMgmtAddressStr =addMgmtAddress.getText().toString();


        if( addMgmtNameStr .isEmpty())
        {
            addMgmtName.setError("This field is required");
        }
        else if(addMgmtEmailStr.isEmpty())
        {
            addMgmtEmail.setError("This field is required");
        }
        else if(addMgmtContactStr.isEmpty())
        {
            addMgmtContact.setError("This field is required");
        }
        else if(addMgmtAddressStr.isEmpty())
        {
            addMgmtAddress.setError("This field is required");
        }
        else
        {

            String Username="MHBIM"+randomString(2),Password="WHLBI"+randomString(3);
            DatabaseReference mngRef = mref.child("State").child(details.getState()).child("District").child(details.getDistrict()).child(details.getCenter()).child("Management Commitee").child(Username);
            mngRef.child("Role").setValue(addMgmtRoleStr);
            mngRef.child("Name").setValue(addMgmtNameStr);
            mngRef.child("Email").setValue(addMgmtEmailStr);
            mngRef.child("Contact").setValue(addMgmtContactStr);
            mngRef.child("Address").setValue(addMgmtAddressStr);
            mngRef.child("Username").setValue(Username);
            mngRef.child("Password").setValue(Password);
            Toast.makeText(getApplicationContext(),"Added Successfully",Toast.LENGTH_LONG).show();
            String Msg = "Your Username : " + Username + "\n "+
                    "Your Password: " + Password + " ";
            SendEmail email = new SendEmail(getApplicationContext(),addMgmtEmailStr,"Aaryaaa",Msg);
            email.execute();
            bottomNavigationView.removeView(addMgmtView);
            startActivity(new Intent(getApplicationContext(), admin_nav_activity.class));


        }

    }


    public void openMgmtDialog(){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        dialog = builder.create();
        dialog.setView(addMgmtView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    public static String randomString(int count)
    {
        StringBuilder builder = new StringBuilder();
        while(count-- !=0)
        {
            int character = (int)(Math.random()*ALPHA.length());
            builder.append(ALPHA.charAt(character));
        }
        return builder.toString();
    }


    public long getCount(){

        mref.child("State").child(details.getState()).child("District").child(details.getDistrict()).child(details.getCenter()).child("Staff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                count = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return count;
    }

}
