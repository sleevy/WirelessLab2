package edu.georgiasouthern.cr04956.wirelesslab2;

import static android.R.attr.id;

/**
 * Created by Cameron Rhodes on 3/11/2017.
 */

public class Country {
    private String name;
    private int flagId;
    private Continent continent;

    public Country(String sName, int id, Continent sContinent) {
        name = sName;
        flagId = id;
        continent = sContinent;
    }

    public Continent getContinent() {
        return continent;
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

    public enum Continent {
        AFRICA ("Africa"),
        ASIA ("Asia"),
        NORTH_AMERICA ("North America"),
        SOUTH_AMERICA ("South America"),
        EUROPE ("Europe");

        private String continent;
        Continent(String cont) {
            continent = cont;
        }

        public String getContinentName() {
            return continent;
        }
    }
}
