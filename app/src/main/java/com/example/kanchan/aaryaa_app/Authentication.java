package com.example.kanchan.aaryaa_app;


import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
//import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import android.support.v4.app.ActivityCompat;


public class Authentication extends AppCompatActivity {

    String adminUname,adminPass;
    DatabaseReference adminRef;
    StaffSession staffSession;
    ManagementCommSession mgmtSession;
    SessionManager sessionManager;
    SharedStateDistrictDetails details;
    private static final int RESULT_SPEECH = 1;
    public String user, WhlStaff, MgmtCommt, whlAdmin;
    View userView, StaffView, MgmtView, adminView, adminLoginView, mgmtLoginView;
    private Intent intent;
    EditText userMobNo, editEnteredCode, adminUsername, adminPassword, staffUsername, staffPassword;
    String mobno, statestr, districtstr, centerstr, adminUsernmStr, adminPasswdStr, staffUsernmStr, staffPasswdStr;
    String requestCode, enteredCode;
    AlertDialog dialog;
    View userOtpCheckView;
    AutoCompleteTextView state, district;
    FloatingActionButton fab;
    EditText center;
    FirebaseAuth mAuth;
    DatabaseReference mref;
    String Username, Password;
    FloatingActionButton speak;
    DatabaseReference dref;
    int clickCount = 0;

    DatabaseReference staffRef, staffMainref;

    private com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    Intent adminIntent;

    private ArrayAdapter<String> mgmtAdapter;
    public FloatingActionButton userMobFab;

    private String UseMobNo;
    EditText centerMgmt, mgmtUname, mgmtPasswd;
    String stateMgmtStr, districtMgmtStr, centerMgmtStr, mgmtUnameStr, mgmtPassStr;
    FloatingActionButton mgmtFab;
    View StateDialogView;
    String uname, passwd;
    String role[] = new String[]{"Select Your Role", "Police Officer", "Medical", "Lawyer", "Other"};
    Spinner mgmtRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        mAuth = FirebaseAuth.getInstance();
        userView = getLayoutInflater().inflate(R.layout.userauthentication, null);
        StaffView = getLayoutInflater().inflate(R.layout.staffauthentication, null);
        adminLoginView = getLayoutInflater().inflate(R.layout.adminlogin, null);
        mgmtLoginView = getLayoutInflater().inflate(R.layout.mgmtlogin, null);

        staffSession = new StaffSession(getApplicationContext());
        mgmtSession = new ManagementCommSession(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());
        //Toast.makeText(getApplicationContext(), "User Login Status: " + sessionManager.isLoggedIn(), Toast.LENGTH_LONG).show();
        userMobNo = userView.findViewById(R.id.editUserMobNo);

        userMobFab = userView.findViewById(R.id.userMobSpeak);

        adminUsername = adminLoginView.findViewById(R.id.adminUsername);
        adminPassword = adminLoginView.findViewById(R.id.adminPassword);
        speak = adminLoginView.findViewById(R.id.speak);
        staffUsername = StaffView.findViewById(R.id.staffUsername);
        staffPassword = StaffView.findViewById(R.id.staffPassword);

        mref = FirebaseDatabase.getInstance().getReference("AdminCredential");
        //mref.child("State").child(details.getState()).child("District").child(details.getDistrict()).child(details.getCenter()).child("Women");

        adminRef = FirebaseDatabase.getInstance().getReference();
        //get admin credentials


        //mgmt intialisation
        mgmtRole = mgmtLoginView.findViewById(R.id.spinnerMgmt);
        mgmtAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, role);
        mgmtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mgmtRole.setAdapter(mgmtAdapter);

        mgmtUname = mgmtLoginView.findViewById(R.id.mgmtUsername);
        mgmtPasswd = mgmtLoginView.findViewById(R.id.mgmtPassword);

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                //requestCode = phoneAuthCredential.getSmsCode().toString();

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                requestCode = s;
                Toast.makeText(getApplicationContext(), "Code Sent Successfully", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);

            }
        };

        userOtpCheckView = getLayoutInflater().inflate(R.layout.verificationwindow, null);
        editEnteredCode = userOtpCheckView.findViewById(R.id.editEnteredCode);

        intent = getIntent();

        user = intent.getExtras().getString("user");
        WhlStaff = intent.getExtras().getString("Staff");
        MgmtCommt = intent.getExtras().getString("MgmtComm");
        whlAdmin = intent.getExtras().getString("WhlAdmin");
//        getMobNo();
        details = new SharedStateDistrictDetails(getApplicationContext());
        //Toast.makeText(getApplicationContext(),details.getState()+details.getDistrict()+details.getCenter(),Toast.LENGTH_SHORT).show();
        updateUi();
        //    updateUi();

    }

    @Override
    public void onStart() {

        super.onStart();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }


    public void updateUi() {
        try {
        //    Toast.makeText(getApplicationContext(), "In update", Toast.LENGTH_LONG).show();
            // Toast.makeText(getApplicationContext(), "In update", Toast.LENGTH_LONG).show();

            if (user.equals("User")) {

                 setContentView(userView);
            }

            if (WhlStaff.equals("WHL Staff")) {

                if (staffSession.isLoggedIn()) {
                    startActivity(new Intent(getApplicationContext(),admin_nav_activity.class));
                }
                else{
                    setContentView(StaffView);
                }

            }

            if (MgmtCommt.equals("Management Committee")) {

                if (mgmtSession.isLoggedIn()) {
                    startActivity(new Intent(getApplicationContext(),admin_nav_activity.class));
                }
                else {
                    setContentView(mgmtLoginView);
                }
            }

            if (whlAdmin.equals("WHL Administrator")) {

                if (sessionManager.isLoggedIn()) {
                   startActivity(new Intent(getApplicationContext(),admin_nav_activity.class));
                }
                else {
                    setContentView(adminLoginView);
                }
            }

        } catch (Exception ex) {

        }

    }

    // User Authentiation
    public void requestOtp(View view) {

        mobno = userMobNo.getText().toString();
        if (mobno.isEmpty()) {
            userMobNo.setError("MobNo can't be blank");
        } else if (mobno.length() != 10) {
            userMobNo.setError("Enter 10 digits only");
        } else {

            DatabaseReference mobRef = mref;
            mobRef.child("State").child(details.getState()).child("District").child(details.getDistrict()).child(details.getCenter()).child("WHL Admin").child("Women").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                            String no =mobno;
                            UseMobNo = (String) data.child("contact_number").getValue().toString();

                            if (!(no.equals(UseMobNo))) {

                                Toast.makeText(getApplicationContext(), "Please Provide Registered Mob No", Toast.LENGTH_LONG).show();
                                //break;
                            } else {

                                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                        "+91"+no, 60, TimeUnit.SECONDS, Authentication.this, callbacks);
                                openVerificationWindow();
                            }
                        }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
    }

    public void openVerificationWindow() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        dialog.setView(userOtpCheckView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    public void verifyCode(View view) {
        enteredCode = editEnteredCode.getText().toString();
        verifyEnteredCode(requestCode, enteredCode);
    }

    private void verifyEnteredCode(String requestCode, String enteredCode) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(requestCode, enteredCode);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    startActivity(new Intent(getApplicationContext(), HomeActivityUser.class));

                } else {

                    editEnteredCode.setError("Enter correct code");
                }
            }
        });

    }

    public void userSpeakNow(View view) {

        Toast.makeText(getApplicationContext(),"Say 181",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

        try {

            startActivityForResult(intent, RESULT_SPEECH);
            userMobNo.setText("");

        } catch (ActivityNotFoundException a) {

            Toast t = Toast.makeText(getApplicationContext(),
                    "Opps! Your device doesn't support Speech to Text",
                    Toast.LENGTH_SHORT);
            t.show();
        }

    }


// Admin Authentication

    public void adminLogin(View view) {

        adminUsernmStr = adminUsername.getText().toString();
        adminPasswdStr = adminPassword.getText().toString();

         if (adminUsernmStr.isEmpty()) {
            adminUsername.setError("Username required");
        } else if (adminPasswdStr.isEmpty()) {
            adminPassword.setError("Password required");
        }
        else {

             loginToAdmin(adminUsernmStr,adminPasswdStr);
         }
    }

    private void loginToAdmin(final String adminU, final String adminP) {

        try {
            adminRef.child("AdminCredential").child("State").child(details.getState()).child("District").child(details.getDistrict()).child(details.getCenter()).child("WHL Admin").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot cre : dataSnapshot.getChildren()) {

                        adminUname = cre.child("username").getValue().toString();
                        adminPass = cre.child("password").getValue().toString();

                        if (adminUname.equals(adminU) && adminPass.equals(adminP)) {

                            sessionManager.createLoginSession(adminU);

                            finish();
                            startActivity(new Intent(getApplicationContext(), admin_nav_activity.class));

                        } else {

                            Toast.makeText(getApplicationContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Toast.makeText(getApplicationContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch(Exception ex){

        }
    }


//staff login

    public void staffLogin(View view) {
        staffUsernmStr = staffUsername.getText().toString();
        staffPasswdStr = staffPassword.getText().toString();

        if (staffUsernmStr.isEmpty()) {
            staffUsername.setError("Username required");
        } else if (staffPasswdStr.isEmpty()) {
            staffPassword.setError("Password required");
        } else {

            getStaffCredentials(staffUsernmStr, staffPasswdStr);
        }
    }

//mgmt authentication(state,district,center,proceedButton)

    public void mgmtLogin(View view) {
        mgmtUnameStr = mgmtUname.getText().toString();
        mgmtPassStr = mgmtPasswd.getText().toString();

        if (mgmtUnameStr.isEmpty()) {
            state.setError("This field is required");
        } else if (mgmtPassStr.isEmpty()) {
            district.setError("This field is required");
        } else {
            getManagementCredentials(mgmtUnameStr, mgmtPassStr);
        }

    }

    public void getManagementCredentials(final String mgmtUnameStr, final String mgmtPassStr) {
        DatabaseReference mngRef = mref.child("State").child(details.getState()).child("District").child(details.getDistrict()).child(details.getCenter()).child("Management Commitee");
        mngRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot credentials : dataSnapshot.getChildren()) {
                    uname = credentials.child("Username").getValue().toString();
                    passwd = credentials.child("Password").getValue().toString();

                    if (mgmtUnameStr.equals(uname) && mgmtPassStr.equals(passwd)) {
                        mgmtSession.createMgmtSession(mgmtUnameStr);
                        startActivity(new Intent(getApplicationContext(), Mgmt_Nav_activity.class));
                        finish();
                    } else {

                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void getStaffCredentials(final String staffUnameStr, final String staffPassStr) {
       // Toast.makeText(getApplicationContext(), details.getState(), Toast.LENGTH_SHORT).show();
        DatabaseReference staffRef = mref.child("State").child(details.getState()).child("District").child(details.getDistrict()).child(details.getCenter()).child("WHL Admin").child("Staff");

        staffRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot credentials : dataSnapshot.getChildren()) {
                    uname = credentials.child("staffid").getValue().toString();
                    passwd = credentials.child("staff_password").getValue().toString();

                    if (staffUnameStr.equals(uname) && staffPassStr.equals(passwd)) {

                        staffSession.createStaffSession(staffUnameStr);
                        finish();

                        startActivity(new Intent(getApplicationContext(), admin_nav_activity.class));
                    } else {

                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    //speech to text
    public void speakNow(View view) {
        Intent intent = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

        try {
            startActivityForResult(intent, RESULT_SPEECH);
            adminUsername.setText("");
        } catch (ActivityNotFoundException a) {
            Toast t = Toast.makeText(getApplicationContext(),
                    "Opps! Your device doesn't support Speech to Text",
                    Toast.LENGTH_SHORT);
            t.show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //  Activity activity = new Activity();

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {


                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String username = text.get(0).toString();
                    adminUsername.setText(username.toUpperCase().trim().replaceAll("( )+", " "));
                    //userMobNo.setText(username.toUpperCase().trim().replaceAll("( )", " "));
                    String MobNo = username.toUpperCase().trim().replaceAll("( )", " ");

                    if(MobNo.equals("181")){

                        callTo181(MobNo);
                    }
                    else{

                        Toast.makeText(getApplicationContext(),"Please say 181",Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            }

        }

    }

    private void callTo181(String mobNo) {

        Toast.makeText(getApplicationContext(),"Call is forwarding to 181",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_CALL);
        if(mobNo.isEmpty()){

            intent.setData(Uri.parse("tel: 181"));
        }
        else{

            intent.setData(Uri.parse("tel:"+mobNo));
        }

        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){

            requestPermission();
        }
        else{
            startActivity(intent);
        }


    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);
    }

}

