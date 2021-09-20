package com.example.universitysportsfacilitybookingapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingFragment newInstance(String param1, String param2) {
        BookingFragment fragment = new BookingFragment();
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
    TextView fragmentTitle;
    Toolbar toolbar;
    Intent currentIntent;
    String selectedDate;
    Facility facility;
    EditText username;
    EditText venue;
    EditText date;
    Button btnBookNow;
    DatabaseHelper db;
    int userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_booking, container, false);

        // init
        fragmentTitle = (TextView) view.findViewById(R.id.fragmentTitle);
        toolbar = view.findViewById(R.id.my_toolbar);
        currentIntent = getActivity().getIntent();
        selectedDate = currentIntent.getStringExtra("selectedDate");
        facility = (Facility) currentIntent.getSerializableExtra("facilityObject");
        CheckBox slot1 = (CheckBox) view.findViewById(R.id.slot1);
        CheckBox slot2 = (CheckBox) view.findViewById(R.id.slot2);
        CheckBox slot3 = (CheckBox) view.findViewById(R.id.slot3);
        CheckBox slot4 = (CheckBox) view.findViewById(R.id.slot4);;
        username = (EditText) view.findViewById(R.id.et_student_id);
        venue = (EditText) view.findViewById(R.id.et_venue);
        date = (EditText) view.findViewById(R.id.et_date);
        btnBookNow = (Button) view.findViewById(R.id.btnBookNow);
        ArrayList<CheckBox> checkBoxArr = new ArrayList<>();
        db = new DatabaseHelper(getActivity());
        userID = currentIntent.getIntExtra("userID", 1);

        // set toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        // add all checkbox to array
        checkBoxArr.add(slot1);
        checkBoxArr.add(slot2);
        checkBoxArr.add(slot3);
        checkBoxArr.add(slot4);


        //pre-set details
        fragmentTitle.setText("Booking for " + facility.getFacilityName());
        username.setText(currentIntent.getStringExtra("username"));
        venue.setText(facility.getFacilityAddress());
        date.setText(currentIntent.getStringExtra("selectedDate"));

        // check slot availability
        Cursor cursor = db.getBookings();

        if (cursor.moveToFirst()) {
            do {
                // get slots for the booking date
                if (cursor.getString(3).equals(selectedDate)) {
                    String time = cursor.getString(4);
                    List<String> slotList = new ArrayList<String>(Arrays.asList(time.split(", ")));

                    for (int i=0; i<slotList.size(); i++) {
                        for(int j=0; j<checkBoxArr.size(); j++) {
                            CheckBox cb = (CheckBox) checkBoxArr.get(j);
                            if (cursor.getInt(2) == facility.getId() ) {
                                if (cb.getText().equals(slotList.get(i))) {
                                    if (cursor.getInt(1) == userID) {
                                        cb.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_slot_own_booking_36),null,null);
                                    } else {
                                        cb.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_slot_unavailable_36),null,null);
                                    }
                                }

                            }
                        }
                    }


                }

            } while (cursor.moveToNext());
        }


        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeText = "";
                // check for checked slots
                for(int i=0; i<checkBoxArr.size(); i++) {
                    CheckBox cb = (CheckBox) checkBoxArr.get(i);
                    if (cb.isChecked()) {
                        String slot = "slot" + String.valueOf(i+1);
                        timeText += getStringByIdName(getActivity(), slot) + ", ";
                    }
                }

                // remove trailing comma and whitespace
                timeText = timeText.replaceAll(", $", "");

                Toast.makeText(getActivity(), timeText, Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    public static String getStringByIdName(Context context, String idName) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(idName, "string", context.getPackageName()));
    }
}