package edu.georgiasouthern.cr04956.wirelesslab2;

/**
 * Created by Cameron Rhodes on 3/11/2017.
 */

public class Country {
    private String name;
    private int flagId;

    public Country(String sName, int id) {
        name = sName;
        flagId = id;
    }

    public String getName() {
        return name;
    }

    public int getFlagId() {
        return flagId;
    }

    public boolean equals(Country other) {
        return name.equals(other.getName()) && flagId == other.getFlagId();
    }
}
