package edu.georgiasouthern.cr04956.wirelesslab2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.provider.ContactsContract;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static edu.georgiasouthern.cr04956.wirelesslab2.MainActivity.CountryAdapter.VIEW_HEIGHT;


public class MainActivity extends Activity {


    public static final String EXTRA_COUNTRY_INDEX = "edu.georgiasouthern.cr04956.MainActivity.index";

    private RecyclerView countryRecycler;
    private CountryStorage cs;
    private boolean isCardView = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cs = CountryStorage.getCountryStorage();

        CountryAdapter adapter = new CountryAdapter();

//        cs.setObserver(adapter);

        Button reset = (Button) findViewById(R.id.btnReset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Reset onClick", "Resetting country list");
                cs.resetCountryList();
                updateUIType();
                countryRecycler.getAdapter().notifyDataSetChanged();
            }
        });

        countryRecycler = (RecyclerView) findViewById(R.id.countryRecycler);

        countryRecycler.setHasFixedSize(true);

        countryRecycler.setLayoutManager(new LinearLayoutManager(this));

        countryRecycler.setAdapter(adapter);

        //todo change to card layout if few in list
       decor = new GridSpacingItemDecoration(2, dpTopx(10), true);

    }

//    private void updateRecyclerViewLayout() {
//        if(cs.size() <= 4) {
//            countryRecycler.setLayoutManager(new CardLayoutManager(this));
//        } else {
//            countryRecycler.setLayoutManager(new LinearLayoutManager(this));
//        }
//    }

    protected class CountryAdapter extends RecyclerView.Adapter<CountryHolder> {
        public static final int VIEW_HEIGHT = 100;
        public static final int CARD_WIDTH = 210;
        public static final int CARD_HEIGHT = 500;
        public static final int HORIZONTAL_MARGIN = 10;
        public static final int VERT_MARGIN = 10;
        public CountryAdapter() {
        }

        @Override
        public int getItemCount() {
            return cs.size();
        }

        @Override
        public CountryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LinearLayout layout;
            LinearLayout.LayoutParams params;
            if(isCardView) {
                layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country_card, parent, false);
//                int pxHeight = dpTopx(CARD_HEIGHT);
//                int pxWidth = dpTopx(CARD_WIDTH);
//                params = new LinearLayout.LayoutParams(pxWidth, pxHeight);
//                params.leftMargin = HORIZONTAL_MARGIN;
//                params.rightMargin = HORIZONTAL_MARGIN;
//                params.topMargin = VERT_MARGIN;
//                params.bottomMargin = VERT_MARGIN;
//                layout.setLayoutParams(params);

                layout.setGravity(Gravity.CENTER);

            } else {
                layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);

                int pxHeight = dpTopx(VIEW_HEIGHT);
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pxHeight);

                layout.setLayoutParams(params);
            }
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
            holder.continentName.setText(country.getContinent().getContinentName());
        }

    }

    private class CountryListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int pos = countryRecycler.getChildPosition(v);
            Country c = cs.getCountry(pos);
            String name = c.getName();
            Log.i("onClick", name);
            Intent toCountryActivity = new Intent(MainActivity.this, CountryActivity.class);
            toCountryActivity.putExtra(EXTRA_COUNTRY_INDEX, pos);
            startActivityForResult(toCountryActivity, 1);
        }
    }
    private GridSpacingItemDecoration decor;
    public void updateUIType() {
        boolean wasCardView = isCardView;
        isCardView = cs.size() <= 4;
        if(wasCardView == isCardView) {
            return;
        }
        if(isCardView) {
            GridLayoutManager man = new GridLayoutManager(this, 2);
//            man.offsetChildrenHorizontal(10);
//            man.offsetChildrenVertical(5);
            countryRecycler.addItemDecoration(decor);
            countryRecycler.setLayoutManager(man);
        } else {
            countryRecycler.removeItemDecoration(decor);
            countryRecycler.setLayoutManager(new LinearLayoutManager(this));
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        updateUIType();
        countryRecycler.getAdapter().notifyDataSetChanged();
    }

    private int dpTopx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5);
    }
    private class CountryHolder extends RecyclerView.ViewHolder {
            public ImageView flagImage;
            public TextView countryName;
            public TextView continentName;
        public CountryHolder(View itemView) {
            super(itemView);
            if(isCardView) {
                flagImage = (ImageView) itemView.findViewById(R.id.imgCardFlag);
                countryName = (TextView) itemView.findViewById(R.id.txtCardCountryName);
                continentName = (TextView) itemView.findViewById(R.id.txtCardContinent);
            } else {
                flagImage = (ImageView) itemView.findViewById(R.id.itemCountryFlag);
                countryName = (TextView) itemView.findViewById(R.id.itemCountryName);
                continentName = (TextView) itemView.findViewById(R.id.txtContinent);
            }
        }


    }

    /**
     * http://www.androidhive.info/2016/05/android-working-with-card-view-and-recycler-view/
     * this code was written, presumably, by Ravi Tamada and is available at tbe above link
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

}
