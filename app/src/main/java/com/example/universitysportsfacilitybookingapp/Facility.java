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
   int max_pax;
   String contact;

    Facility(int id, String name, String address, String opening_hours, int max_pax, String contact) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.opening_hours = opening_hours;
        this.max_pax = max_pax;
        this.contact = contact;
    }

    public String getFacilityName() {
        return this.name;
    }
    public String getFacilityAddress() {
        return this.address;
    }

}
