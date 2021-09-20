package com.example.universitysportsfacilitybookingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingsFragment newInstance(String param1, String param2) {
        BookingsFragment fragment = new BookingsFragment();
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

    BookingsAdapter adapter;
    DatabaseHelper db;
    Intent currentIntent;
    View view;
    TextView tvNoBookings;
    ArrayList<Booking> bookings = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bookings, container, false);

        //init
        db = new DatabaseHelper(getActivity());
        currentIntent = getActivity().getIntent();

        tvNoBookings = (TextView) view.findViewById(R.id.tvNoBookings);

        // data to populate the RecyclerView with
        //get bookings for current user
        int userID = currentIntent.getIntExtra("userID", 1);
        Cursor cursor = db.getBookings(userID);

        if (cursor.moveToFirst()) {
            do {
                Booking booking;
                Cursor cursor2 = db.getFacility(cursor.getInt(2));
                String facilityName = "";
                String facilityAddress = "";

                // get booking facility name
                if (cursor2.moveToFirst()) {
                    facilityName = cursor2.getString(1);
                    facilityAddress = cursor2.getString(2);
                }
                booking = new Booking(cursor.getInt(0), userID, facilityName, facilityAddress, cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
                bookings.add(booking);

            } while(cursor.moveToNext());
        }

        if (bookings.size() == 0) {
            tvNoBookings.setVisibility(View.VISIBLE);
        } else {
            tvNoBookings.setVisibility(View.INVISIBLE);
        }

        // set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.rvBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BookingsAdapter(getActivity(), bookings);
        recyclerView.setAdapter(adapter);

        return view;
    }
}