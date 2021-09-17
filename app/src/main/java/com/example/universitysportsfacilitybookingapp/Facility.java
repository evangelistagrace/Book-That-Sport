package com.example.universitysportsfacilitybookingapp;

import java.io.Serializable;

/*

Name: Facility
Usage: Object for containing facility information

 */
public class Facility implements Serializable {
    private int id;
    private String name;

    Facility(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getFacilityName() {
        return this.name;
    }

}
