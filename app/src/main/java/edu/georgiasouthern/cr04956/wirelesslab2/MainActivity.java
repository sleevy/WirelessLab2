package edu.georgiasouthern.cr04956.wirelesslab2;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.country;


public class MainActivity extends FragmentActivity {




    private RecyclerView countryRecycler;
    private CountryStorage cs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cs = CountryStorage.getCountryStorage();

        final CountryAdapter adapter = new CountryAdapter();

        Button reset = (Button) findViewById(R.id.btnReset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cs.initializeCountryList();
                adapter.notifyDataSetChanged();
            }
        });

        countryRecycler = (RecyclerView) findViewById(R.id.countryRecycler);

        countryRecycler.setHasFixedSize(true);

        countryRecycler.setLayoutManager(new LinearLayoutManager(this));

        countryRecycler.setAdapter(adapter);


    }

    private class CountryAdapter extends RecyclerView.Adapter<CountryHolder> {
        public static final int VIEW_HEIGHT = 100;

        public CountryAdapter() {
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

            layout.setOnClickListener(new CountryListener());

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

    private class CountryListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int pos = countryRecycler.getChildLayoutPosition(v);
            Country c = cs.getCountry(pos);
            String name = c.getName();
            Log.i("onClick", name);
            Intent toCountryActivity = new Intent();
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
