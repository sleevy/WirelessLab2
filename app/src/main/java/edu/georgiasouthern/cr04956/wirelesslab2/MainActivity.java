package edu.georgiasouthern.cr04956.wirelesslab2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends FragmentActivity {




    private RecyclerView countryRecycler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countryRecycler = (RecyclerView) findViewById(R.id.countryRecycler);

        countryRecycler.setHasFixedSize(true);

        countryRecycler.setLayoutManager(new LinearLayoutManager(this));

        CountryAdapter adapter = new CountryAdapter();
        countryRecycler.setAdapter(adapter);


    }

    private class CountryAdapter extends RecyclerView.Adapter<CountryHolder> {
        public CountryStorage cs;
        public static final int VIEW_HEIGHT = 100;

        public CountryAdapter() {
                cs = CountryStorage.getCountryStorage();
        }

        @Override
        public int getItemCount() {
            return cs.size();
        }

        @Override
        public CountryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
            //size,margin,padding,layout params
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int pxHeight = (int)((VIEW_HEIGHT * displayMetrics.density) + 0.5);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,pxHeight);
            layout.setLayoutParams(params);


            CountryHolder ch = new CountryHolder(layout);
            return ch;
        }

        @Override
        public void onBindViewHolder(CountryHolder holder, int position)
        {
            Country country = cs.getCountry(position);
            holder.flagImage.setImageResource(country.getFlagId());
            holder.countryName.setText(country.getName());

        }

    }

    private class CountryHolder extends RecyclerView.ViewHolder {
            public ImageView flagImage;
            public TextView countryName;
        public CountryHolder(View itemView) {
            super(itemView);
            flagImage = (ImageView) itemView.findViewById(R.id.itemCountryFlag);
            countryName = (TextView) itemView.findViewById(R.id.itemCountryName);

        }
    }


}
