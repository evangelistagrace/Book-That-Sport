package com.example.universitysportsfacilitybookingapp;

import java.io.Serializable;

/*

Name: Facility
Usage: Object for containing facility information

 */
public class Facility implements Serializable {
   int id;
   String name;
   String address;
   String opening_hours;
   String contact;

    Facility(int id, String name, String address, String opening_hours, String contact) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.opening_hours = opening_hours;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }
    public String getFacilityName() {
        return this.name;
    }
    public String getFacilityAddress() {
        return this.address;
    }
    public String getFacilityOpeningHours() {
        return this.opening_hours;
    }
    public String getFacilityContact() {
        return this.contact;
    }

}
