package com.example.kanchan.aaryaa_app;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

public class Home extends AppCompatActivity {

    AlertDialog dialog;
    View loginRoleView;
    String User,WhlStaff,MgmtCommt;
    RadioButton user ,WHLStaff,mgmt;

    FirebaseAuth mAuth;
    private RadioButton whlAdmin;
    private String WhlAdmin;
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loginRoleView = getLayoutInflater().inflate(R.layout.loginrole,null); //dusari layout parent act var inflate karnyasthi

        user =(RadioButton)loginRoleView.findViewById(R.id.radioUser);
        WHLStaff=(RadioButton) loginRoleView.findViewById(R.id.radioStaff);
        mgmt=(RadioButton) loginRoleView.findViewById(R.id.radioMgmt);
        whlAdmin=(RadioButton) loginRoleView.findViewById(R.id.radioAdministrator);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        mAuth =FirebaseAuth.getInstance();

        openLoginRole();

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User = user.getText().toString();
                Intent intent = new Intent(getApplicationContext(),Authentication.class);
                intent.putExtra("user",User);
                intent.putExtra("Staff","staff");
                intent.putExtra("MgmtComm","mgmt");
                intent.putExtra("WhlAdmin","admin");
                startActivity(intent);
                finish();
            }
        });

        WHLStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WhlStaff = WHLStaff.getText().toString();
                Intent intent = new Intent(getApplicationContext(),Authentication.class);
                intent.putExtra("Staff",WhlStaff);
                intent.putExtra("user","user");
                intent.putExtra("MgmtComm","mgmt");
                intent.putExtra("WhlAdmin","admin");
                startActivity(intent);
                finish();
            }
        });

        whlAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WhlAdmin = whlAdmin.getText().toString();
                Intent intent = new Intent(getApplicationContext(),Authentication.class);
                intent.putExtra("WhlAdmin",WhlAdmin);
                intent.putExtra("user","user");
                intent.putExtra("MgmtComm","mgmt");
                intent.putExtra("Staff","staff");
                startActivity(intent);
                finish();
            }
        });

        mgmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MgmtCommt = mgmt.getText().toString();
                Intent intent = new Intent(getApplicationContext(),Authentication.class);
                intent.putExtra("MgmtComm",MgmtCommt);
                intent.putExtra("user","user");
                intent.putExtra("Staff","staff");
                intent.putExtra("WhlAdmin","admin");
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onStart()
    {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        //Toast.makeText(getApplicationContext(),user.toString(),Toast.LENGTH_LONG).show();
        if(user!=null)
        {
            startActivity(new Intent(getApplicationContext(),HomeActivityUser.class));
        }
    }

    public void openLoginRole()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        dialog =builder.create();
        dialog.setView(loginRoleView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

}
