package edu.georgiasouthern.cr04956.wirelesslab2;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Cameron Rhodes on 3/11/2017.
 */

public class CountryStorage {

//    private String[] countries = {
//            "Cuba",
//            "Haiti",
//            "United States",
//            "Jamaica",
//            "Canada",
//
//            "Brazil",
//            "Argentina",
//            "Chile",
//            "Peru",
//            "Venezuela",
//
//            "France",
//            "Germany",
//            "Poland",
//            "Greece",
//            "Portugal",
//
//            "Russia",
//            "China",
//            "Japan",
//            "India",
//            "South Korea",
//
//            "South Africa",
//            "Ethiopia",
//            "Mali",
//            "Morocco",
//            "Nigeria"
//    };

//    private MainActivity.CountryAdapter observer;

    private Country[] nAmerica = {
            new Country("Cuba", R.drawable.cu, Country.Continent.NORTH_AMERICA),
            new Country("Haiti", R.drawable.ht, Country.Continent.NORTH_AMERICA),
            new Country("United States", R.drawable.us, Country.Continent.NORTH_AMERICA),
            new Country("Jamaica", R.drawable.jm, Country.Continent.NORTH_AMERICA),
            new Country("Canada", R.drawable.ca, Country.Continent.NORTH_AMERICA)
    };

    private Country[] sAmerica = {
            new Country("Brazil", R.drawable.br, Country.Continent.SOUTH_AMERICA),
            new Country("Argentina", R.drawable.ar, Country.Continent.SOUTH_AMERICA),
            new Country("Chile", R.drawable.cl, Country.Continent.SOUTH_AMERICA),
            new Country("Peru", R.drawable.pe, Country.Continent.SOUTH_AMERICA),
            new Country("Venezuela", R.drawable.ve, Country.Continent.SOUTH_AMERICA)
    };

    private Country[] europe = {
            new Country("France", R.drawable.fr, Country.Continent.EUROPE),
            new Country("Germany", R.drawable.de, Country.Continent.EUROPE),
            new Country("Poland", R.drawable.pl, Country.Continent.EUROPE),
            new Country("Greece", R.drawable.gr, Country.Continent.EUROPE),
            new Country("Portugal", R.drawable.pt, Country.Continent.EUROPE)
    };

    private Country[] asia  = {
            new Country("China", R.drawable.cn, Country.Continent.ASIA),
            new Country("Japan", R.drawable.jp, Country.Continent.ASIA),
            new Country("India", R.drawable.in, Country.Continent.ASIA),
            new Country("South Korea", R.drawable.kr, Country.Continent.ASIA),
            new Country("Israel", R.drawable.il, Country.Continent.ASIA)
    };

    private Country[] africa = {
            new Country("South Africa", R.drawable.za, Country.Continent.AFRICA),
            new Country("Ethiopia", R.drawable.et, Country.Continent.AFRICA),
            new Country("Mali", R.drawable.ml, Country.Continent.AFRICA),
            new Country("Morocco", R.drawable.ma, Country.Continent.AFRICA),
            new Country("Nigeria", R.drawable.ng, Country.Continent.AFRICA)
    };

    private ArrayList<Country> countries;

    private CountryStorage() {
        countries = new ArrayList<Country>(10);
        initializeCountryList();
    }

    private static CountryStorage theInstance;
    public static CountryStorage getCountryStorage() {
        if(theInstance == null) {
            theInstance = new CountryStorage();
        }
        return theInstance;
    }

    private void initializeCountryList() {
        for (int i = 0; i < 2; i++) {
            countries.add(nAmerica[i]);
            countries.add(sAmerica[i]);
            countries.add(europe[i]);
            countries.add(asia[i]);
            countries.add(africa[i]);
        }
    }

    public void resetCountryList() {
        countries.clear();
        initializeCountryList();
//        notifyDataChanged();
    }

    private void removeNorthAmerica() {
        removeCountries(nAmerica);
    }

    private void removeAsia() {
        removeCountries(asia);
    }

    private void removeEurope() {
        removeCountries(europe);
    }

    private void removeAfrica() {
        removeCountries(africa);
    }

    private void removeSouthAmerica() {
        removeCountries(sAmerica);
    }

    private void removeCountries(Country[] continent) {
      for(Country c : continent) {
          if(countries.contains(c)) {
              countries.remove(c);
          }
      }
//      notifyDataChanged();
    }


    public Country getCountry(int index) {
     return countries.get(index);
    }

    public void removeCountriesBasedOnCountry(Country c) {
//        for(int i = 0; i < 5; i++) {
//            if(nAmerica[i].equals(c)) {
//                removeNorthAmerica();
//                return;
//            } else if(sAmerica[i].equals(c)) {
//                removeSouthAmerica();
//                return;
//            } else if(europe[i].equals(c)) {
//                removeEurope();
//                return;
//            } else if(asia[i].equals(c)) {
//                removeAsia();
//                return;
//            } else if(africa[i].equals(c)) {
//                removeAfrica();
//                return;
//            }
//        }
        switch (c.getContinent()) {
            case NORTH_AMERICA:
                removeNorthAmerica();
                break;
            case SOUTH_AMERICA:
                removeSouthAmerica();
                break;
            case ASIA:
                removeAsia();
                break;
            case EUROPE:
                removeEurope();
                break;
            case AFRICA:
                removeAfrica();
                break;
        }
    }

    public void addBasedOnCountry(Country c) {
        switch (c.getContinent()) {
            case AFRICA: addAfrica();
                break;
            case NORTH_AMERICA: addNorthAmerica();
                break;
            case SOUTH_AMERICA: addSouthAmerica();
                break;
            case ASIA: addAsia();
                break;
            case EUROPE: addEurope();
                break;
        }
//        for(int i = 0; i < 5; i++) {
//            if(nAmerica[i].equals(c)) {
//                addNorthAmerica();
//                return;
//            } else if(sAmerica[i].equals(c)) {
//                addSouthAmerica();
//                return;
//            } else if(europe[i].equals(c)) {
//                addEurope();
//                return;
//            } else if(asia[i].equals(c)) {
//                addAsia();
//                return;
//            } else if(africa[i].equals(c)) {
//                addAfrica();
//                return;
//            }
//        }
    }

    private void addNorthAmerica() {
        addCountries(nAmerica);
    }

    private void addSouthAmerica() {
        addCountries(sAmerica);
    }

    private void addEurope() {
        addCountries(europe);
    }

    private void addAsia() {
        addCountries(asia);
    }

    private void addAfrica() {
        addCountries(africa);
    }

    private void addCountries(Country[] continent) {
        for(Country c : continent) {
            if(!countries.contains(c)) {
                countries.add(c);
            }
        }
//        notifyDataChanged();
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public int size() {
        return countries.size();
    }

//    public void setObserver(MainActivity.CountryAdapter adapter) {
//        observer = adapter;
//    }
//
//    public void notifyDataChanged() {
//        if(observer != null) {
//            observer.notifyDataSetChanged();
//        }
//    }
}
