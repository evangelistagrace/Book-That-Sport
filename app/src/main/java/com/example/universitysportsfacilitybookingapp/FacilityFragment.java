package com.example.universitysportsfacilitybookingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    TextView facilityTitle;
    TextView facilityAddress;
    TextView facilityOpeningHours;
    TextView facilityMaxPax;
    TextView facilityContact;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_facility, container, false);

        // init
        currentIntent = getActivity().getIntent();
        facility = (Facility) currentIntent.getSerializableExtra("facilityObject");
        facilityTitle = (TextView) view.findViewById(R.id.facilityTitle);
        facilityAddress = (TextView) view.findViewById(R.id.facilityAddress);
        facilityOpeningHours = (TextView) view.findViewById(R.id.facilityOpeningHours);
        facilityMaxPax = (TextView) view.findViewById(R.id.facilityMaxPax);
        facilityContact = (TextView) view.findViewById(R.id.facilityContact);

        // set toolbar
        Toolbar toolbar = view.findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        // set information
        ViewPager viewPager = view.findViewById(R.id.image_gallery);
        int[] mImageIds = new int[]{};

        // set images for image gallery
        switch (facility.getFacilityName()) {
            case "Badminton":
                mImageIds = new int[]{R.drawable.badminton_1, R.drawable.badminton_2, R.drawable.badminton_3};
                break;
            default:
                break;
        }


        ImageAdapter adapter = new ImageAdapter(getActivity(), mImageIds);
        viewPager.setAdapter(adapter);


        facilityTitle.setText(facility.getFacilityName());
        facilityAddress.setText(facility.getFacilityAddress());
        facilityOpeningHours.setText(facility.getFacilityOpeningHours());
        facilityMaxPax.setText(facility.getFacilityMaxPax() + " Pax");
        facilityContact.setText(facility.getFacilityContact());

        return view;
    }
}