package com.example.universitysportsfacilitybookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText stud_id, stud_password;
    Button btn_login;
    TextView link_register;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //hide action bar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        db = new DatabaseHelper(this);
        stud_id = findViewById(R.id.stud_id);
        stud_password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        link_register = findViewById(R.id.link_register);


        // create admin if there isn't one since login page is the first to initiate
        int adminId = db.checkUser("admin", "123");

        if (adminId < 0) { // no admin record found, so add admin
            db.addUser("admin@mail.com", "admin", "123");
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = stud_id.getText().toString().trim();
                String password = stud_password.getText().toString().trim();

                int userId = db.checkUser(username, password);

                if (userId > -1) { // user record found, so login
                    Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                    Intent testIntent = new Intent(LoginActivity.this, FacilitiesActivity.class);
//                    adminIntent.putExtra("USERNAME", username);
//                    adminIntent.putExtra("PASSWORD", password);
//                    adminIntent.putExtra("ID", userId);
                    testIntent.putExtra("username", username);
                    startActivity(testIntent);
//                    finish();
//                    return;
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }

}
