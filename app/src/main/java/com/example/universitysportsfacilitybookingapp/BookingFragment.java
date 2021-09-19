package com.example.universitysportsfacilitybookingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

        // set toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        //set fragment title
        fragmentTitle.setText("Booking for " + facility.getFacilityName());


        // slot checkbox handlers
        slot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox)v;
                if (cb.isChecked()) {
                    Toast.makeText(getActivity(), "SLOT 1 CHECKED",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "SLOT 1 UNCHECKED",Toast.LENGTH_SHORT).show();
                }
            }
        });

        slot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox)v;
                if (cb.isChecked()) {
                    Toast.makeText(getActivity(), "SLOT 2 CHECKED",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "SLOT 2 UNCHECKED",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}