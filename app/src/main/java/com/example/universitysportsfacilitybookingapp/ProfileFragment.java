package com.example.universitysportsfacilitybookingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View view;
    DatabaseHelper db;
    EditText etOldPassword;
    EditText etNewPassword;
    Button btnLogout, btnSaveChanges;
    TextView tvUsername;
    Intent currentIntent;
    int userID;
    boolean passwordMatch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        //init
        db = new DatabaseHelper(getActivity());
        etOldPassword = (EditText) view.findViewById(R.id.etOldPassword);
        etNewPassword = (EditText) view.findViewById(R.id.etNewPassword);
        currentIntent = getActivity().getIntent();
        userID = currentIntent.getIntExtra("userID", 1);
        tvUsername = (TextView) view.findViewById(R.id.tvUsername);
        btnLogout = (Button) view.findViewById(R.id.btnLogout);
        btnSaveChanges = (Button) view.findViewById(R.id.btnSaveChanges);

        // set customer profile username
        tvUsername.setText(currentIntent.getStringExtra("username"));

        //disable save changes button by default
        disableSaveButton();

        // detect input in both password fields
        etOldPassword.addTextChangedListener(etOldPasswordWatcher);
        etNewPassword.addTextChangedListener(etNewPasswordWatcher);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = etNewPassword.getText().toString().trim();
                long res = db.setUserPassword(userID, newPassword);

                if (res > 0) {
                    Toast.makeText(getActivity(), "Successfully updated password", Toast.LENGTH_SHORT).show();

                    //reset form
                    etOldPassword.setText("");
                    etNewPassword.setText("");
                    disableSaveButton();
                }
            }
        });

        return view;
    }

    private TextWatcher etOldPasswordWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String etOldPassword = (String) s.toString();
            // detect if old password matches user's current password
            String oldPassword = "";

            Cursor cursor = db.getUser(userID);

            if (cursor.moveToFirst()) {
                oldPassword = cursor.getString(2);
            }


            if (etOldPassword.equals(oldPassword)) {
                passwordMatch = true;

            } else {
                passwordMatch = false;
                if (btnSaveChanges.isEnabled()) {
                    disableSaveButton();
                }
            }

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
    };


    private TextWatcher etNewPasswordWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
//            Toast.makeText(getActivity(), s.toString(), Toast.LENGTH_SHORT).show();
            String etNewPassword = (String) s.toString();
            // enable save changes button only if new password length > 0 and old password matches user's current password
            if (etNewPassword.length() > 0 && passwordMatch) {
                //re-enable the save changes button
                enableSaveButton();
            } else {
                disableSaveButton();
            }

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
    };

    public void disableSaveButton() {
        btnSaveChanges.setEnabled(false);
        btnSaveChanges.setBackgroundColor(getResources().getColor(R.color.grey_400));
    }

    public void enableSaveButton() {
        btnSaveChanges.setEnabled(true);
        btnSaveChanges.setBackgroundColor(getResources().getColor(R.color.magenta));
    }
}