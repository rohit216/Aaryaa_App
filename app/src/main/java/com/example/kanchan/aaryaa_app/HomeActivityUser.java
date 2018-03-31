package com.example.kanchan.aaryaa_app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivityUser extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Animation fabOpen,fabClose,fabClockwise,fabAnticlock;
    FloatingActionButton fab;

    Intent intent;

    FloatingActionButton fab_adminCall,fab_nearestHospital,fab_nearestPolice;
    private boolean isOpen= false;

    DatabaseReference caseRef;
    private FirebaseUser user;
    private DatabaseReference CaseRef;
    private FirebaseAuth mAuth;
    SharedStateDistrictDetails details;
    private DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       fab = (FloatingActionButton) findViewById(R.id.fab);



        fab_adminCall = findViewById(R.id.fab_adminCall);
        fab_nearestHospital = findViewById(R.id.fab_nearestHospital);
        fab_nearestPolice = findViewById(R.id.fab_nearestPolice);

        fab_nearestPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(getApplicationContext(),NearestPlaces.class);
                intent.putExtra("police","police");
                intent.putExtra("hospital","");
                startActivity(intent);
            }
        });

        details = new SharedStateDistrictDetails(getApplicationContext());
        fab_nearestHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),NearestPlaces.class);
                intent.putExtra("police","");
                intent.putExtra("hospital","hospital");
                startActivity(intent);

            }
        });


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        mref = FirebaseDatabase.getInstance().getReference("AdminCredential");
        caseRef = mref;
        CaseRef = caseRef.child("State").child(details.getState()).child("District").child(details.getDistrict()).child(details.getCenter()).child("WHL Admin").child("Women").child(user.getUid()).child("cases");
        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);

        fabClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);

        fabClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_clockwise);

        fabAnticlock = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_anticlockwise);

//            fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(isOpen){

                    fab_adminCall.startAnimation(fabClose);
                    fab_nearestHospital.startAnimation(fabClose);
                    fab_nearestPolice.startAnimation(fabClose);
                    fab.startAnimation(fabAnticlock);
                    fab_adminCall.setClickable(false);
                    fab_nearestHospital.setClickable(false);
                    fab_nearestPolice.setClickable(false);
                    isOpen = false;
                }
                else{

                    fab_adminCall.startAnimation(fabOpen);
                    fab_nearestHospital.startAnimation(fabOpen);
                    fab_nearestPolice.startAnimation(fabOpen);
                    fab.startAnimation(fabClockwise);
                    fab_adminCall.setClickable(true);
                    fab_nearestHospital.setClickable(true);
                    fab_nearestPolice.setClickable(true);
                    isOpen = true;
                }



            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onStart(){

        super.onStart();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_activity_user, menu);
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
            return true;
        }

        if (id == R.id.action_signOut) {

            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(),Home.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.support.v4.app.Fragment fragment =null;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_home) {

            fragment = new BlankFragment();
            transaction.replace(R.id.content_home,fragment);
            transaction.addToBackStack(null);
            transaction.commit();


        }  else if (id == R.id.nav_viewComplaints) {


            fragment = new ViewComplaint();
            transaction.replace(R.id.content_home,fragment);
            transaction.addToBackStack(null);
            transaction.commit();


        } else if (id == R.id.nav_caseTrack) {


            fragment = new CaseTracking();
            transaction.replace(R.id.content_home,fragment);
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (id == R.id.nav_share) {


            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/html");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>This is the text that will be shared.</p>"));
            startActivity(Intent.createChooser(sharingIntent,"Share using"));


        } else if (id == R.id.nav_feedback) {


        }
        else if (id == R.id.nav_rateUs) {


        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void changeProfile(View view){

        Toast.makeText(getApplicationContext(),"Comming soon",Toast.LENGTH_LONG).show();

    }

}
