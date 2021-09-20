package com.example.universitysportsfacilitybookingapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FacilityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FacilityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FacilityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BadmintonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FacilityFragment newInstance(String param1, String param2) {
        FacilityFragment fragment = new FacilityFragment();
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

    // variables
    View view;
    Intent currentIntent;
    Facility facility;
    TextView fragmentTitle;
    TextView facilityAddress;
    TextView facilityOpeningHours;
    TextView facilityContact;
    FloatingActionButton fab;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_facility, container, false);

        // init
        currentIntent = getActivity().getIntent();
        facility = (Facility) currentIntent.getSerializableExtra("facilityObject");
        fragmentTitle = (TextView) view.findViewById(R.id.fragmentTitle);
        facilityAddress = (TextView) view.findViewById(R.id.facilityAddress);
        facilityOpeningHours = (TextView) view.findViewById(R.id.facilityOpeningHours);
        facilityContact = (TextView) view.findViewById(R.id.facilityContact);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);


        // set toolbar
        Toolbar toolbar = view.findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        // set images for image gallery
        ViewPager viewPager = view.findViewById(R.id.image_gallery);
        int[] mImageIds = new int[]{};

        switch (facility.getFacilityName()) {
            case "Badminton":
                mImageIds = new int[]{R.drawable.badminton_1, R.drawable.badminton_2, R.drawable.badminton_3};
                break;
            default:
                break;
        }

        ImageAdapter adapter = new ImageAdapter(getActivity(), mImageIds);
        viewPager.setAdapter(adapter);

        // set facility details
        fragmentTitle.setText(facility.getFacilityName());
        facilityAddress.setText(facility.getFacilityAddress());
        facilityOpeningHours.setText(facility.getFacilityOpeningHours());
        facilityContact.setText(facility.getFacilityContact());

        // fab action
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //popup new fragment and pass selected date to new fragment
                popBookingFragment();
            }

        };


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                dpd.show();

                dpd.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.mustard));
                dpd.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.mustard));


            }
        });

        return view;
    }

    private void popBookingFragment() {
        String myFormat = "dd/MM/YYYY";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        // pass booking information to next fragment
        currentIntent.putExtra("selectedDate", sdf.format(myCalendar.getTime()));

        Fragment fragment = new BookingFragment();
        loadFragment(fragment);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        androidx.fragment.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.setReorderingAllowed(true);
        transaction.commit();
    }
}