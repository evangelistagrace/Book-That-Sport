package com.example.universitysportsfacilitybookingapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FacilitiesActivity extends AppCompatActivity {
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facilities);

        //hide action bar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        db = new DatabaseHelper(this);
        Button btnBadminton = findViewById(R.id.btnBadminton);
        Intent currentIntent = getIntent();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


        // handle clicks for facilities buttons
        btnBadminton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create badminton object
                Cursor cursor = db.getFacility(1);

                if (cursor.moveToFirst()) {
                    Facility facility = new Facility(1, cursor.getString(1),
                            cursor.getString(2), cursor.getString(3),
                            cursor.getInt(4), cursor.getString(5));

                    currentIntent.putExtra("username", currentIntent.getStringExtra("username"));
                    currentIntent.putExtra("facilityObject", facility);
                    // Fragment 1
                    Fragment fragment = new FacilityFragment();
                    loadFragment(fragment);
                }
            }
        });

        // set actions for bottom menu
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_facilities:
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        return true;
                    case R.id.action_bookings:
//                        currentIntent.putExtra("adminObject", admin);
//                        fragment = new AdminInsightFragment();
//                        loadFragment(fragment);
//                        return true;
                    case R.id.action_profile:
//                        currentIntent.putExtra("adminObject", admin);
//                        fragment = new AdminProfileFragment();
//                        loadFragment(fragment);
//                        return true;
                }
                return false;
            }
        });

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.setReorderingAllowed(true);
        transaction.commit();
    }
}
