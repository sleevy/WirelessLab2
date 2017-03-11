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

    private MainActivity.CountryAdapter observer;

    private Country[] nAmerica = {
            new Country("Cuba", R.drawable.cu),
            new Country("Haiti", R.drawable.ht),
            new Country("United States", R.drawable.us),
            new Country("Jamaica", R.drawable.jm),
            new Country("Canada", R.drawable.ca)
    };

    private Country[] sAmerica = {
            new Country("Brazil", R.drawable.br),
            new Country("Argentina", R.drawable.ar),
            new Country("Chile", R.drawable.cl),
            new Country("Peru", R.drawable.pe),
            new Country("Venezuela", R.drawable.ve)
    };

    private Country[] europe = {
            new Country("France", R.drawable.fr),
            new Country("Germany", R.drawable.de),
            new Country("Poland", R.drawable.pl),
            new Country("Greece", R.drawable.gr),
            new Country("Portugal", R.drawable.pt)
    };

    private Country[] asia  = {
            new Country("China", R.drawable.cn),
            new Country("Japan", R.drawable.jp),
            new Country("India", R.drawable.in),
            new Country("South Korea", R.drawable.kr),
            new Country("Israel", R.drawable.il)
    };

    private Country[] africa = {
            new Country("South Africa", R.drawable.za),
            new Country("Ethiopia", R.drawable.et),
            new Country("Mali", R.drawable.ml),
            new Country("Morocco", R.drawable.ma),
            new Country("Nigeria", R.drawable.ng)
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
        initializeCountryList();
        notifyDataChanged();
    }

    public void removeNorthAmerica() {
        removeCountries(nAmerica);
    }

    public void removeAsia() {
        removeCountries(asia);
    }

    public void removeEurope() {
        removeCountries(europe);
    }

    public void removeAfrica() {
        removeCountries(africa);
    }

    public void removeSouthAmerica() {
        removeCountries(sAmerica);
    }

    private void removeCountries(Country[] continent) {
      for(Country c : continent) {
          if(countries.contains(c)) {
              countries.remove(c);
          }
      }
      notifyDataChanged();
    }


    public Country getCountry(int index) {
     return countries.get(index);
    }

    public void removeCountriesBasedOnCountry(Country c) {
        for(int i = 0; i < 5; i++) {
            if(nAmerica[i].equals(c)) {
                removeNorthAmerica();
                return;
            } else if(sAmerica[i].equals(c)) {
                removeSouthAmerica();
                return;
            } else if(europe[i].equals(c)) {
                removeEurope();
                return;
            } else if(asia[i].equals(c)) {
                removeAsia();
                return;
            } else if(africa[i].equals(c)) {
                removeAfrica();
                return;
            }
        }
    }

    public void addBasedOnCountry(Country c) {
        for(int i = 0; i < 5; i++) {
            if(nAmerica[i].equals(c)) {
                addNorthAmerica();
                return;
            } else if(sAmerica[i].equals(c)) {
                addSouthAmerica();
                return;
            } else if(europe[i].equals(c)) {
                addEurope();
                return;
            } else if(asia[i].equals(c)) {
                addAsia();
                return;
            } else if(africa[i].equals(c)) {
                addAfrica();
                return;
            }
        }
    }

    public void addNorthAmerica() {
        addCountries(nAmerica);
    }

    public void addSouthAmerica() {
        addCountries(sAmerica);
    }

    public void addEurope() {
        addCountries(europe);
    }

    public void addAsia() {
        addCountries(asia);
    }

    public void addAfrica() {
        addCountries(africa);
    }

    private void addCountries(Country[] continent) {
        for(Country c : countries) {
            if(!countries.contains(c)) {
                countries.add(c);
            }
        }
        notifyDataChanged();
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public int size() {
        return countries.size();
    }

    public void setObserver(MainActivity.CountryAdapter adapter) {
        observer = adapter;
    }
    public void notifyDataChanged() {
        if(observer != null) {
            observer.notifyDataSetChanged();
        }
    }
}
