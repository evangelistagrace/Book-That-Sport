package com.example.universitysportsfacilitybookingapp;

public class Booking {
    int ID;
    int userID;
    String facilityName;
    String facilityAddress;
    String date;
    String time;
    String pax;
    String status;

    Booking(int ID, int userID, String facilityName, String facilityAddress, String date, String time, String pax, String status) {
        this.ID = ID;
        this.userID = userID;
        this.facilityName = facilityName;
        this.facilityAddress = facilityAddress;
        this.date = date;
        this.time = time;
        this.pax = pax;
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public String getFacilityAddress() {
        return facilityAddress;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPax() {
        return pax;
    }
}
