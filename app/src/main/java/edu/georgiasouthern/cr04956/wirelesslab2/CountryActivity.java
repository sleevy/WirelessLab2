package edu.georgiasouthern.cr04956.wirelesslab2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static android.provider.Contacts.SettingsColumns.KEY;

/**
 * Created by Cameron Rhodes on 3/11/2017.
 */

public class CountryActivity extends AppCompatActivity {



    private final float DEFAULT_RATING = 5.0f;
    private int MAX_HEIGHT = 400;
    //    private String countryName = "Cuba";
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
//    private int countryIndex = 0;
    private Country currentCountry;
    private final String KEY_NOTES = "Notes";
    private final String KEY_RATING = "Rating";

    protected void onCreate( Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        Intent i = getIntent();
        int index = i.getIntExtra(MainActivity.EXTRA_COUNTRY_INDEX, 0);
        final CountryStorage cs = CountryStorage.getCountryStorage();
        currentCountry = cs.getCountry(index);
        makeTask(currentCountry.getName()).execute();

        TextView countryName = (TextView) findViewById(R.id.countryName);
        countryName.setText(currentCountry.getName());

        ImageView theFlag = (ImageView) findViewById(R.id.countryFlag);
        theFlag.setImageResource(currentCountry.getFlagId());

        final Button yesButton = (Button) findViewById(R.id.btnYes);

        final Button noButton = (Button) findViewById(R.id.btnNo);

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cs.removeCountriesBasedOnCountry(currentCountry);
                yesButton.setEnabled(false);
                noButton.setEnabled(false);
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cs.addBasedOnCountry(currentCountry);
                yesButton.setEnabled(false);
                noButton.setEnabled(false);
            }
        });

        //sharedpreferences?

        SharedPreferences prefs = getSharedPreferences(currentCountry.getName(), 0);
        String notes = prefs.getString(KEY_NOTES, "");

        EditText txtNote = (EditText) findViewById(R.id.txtNotes);
        txtNote.setText(notes);

        final SharedPreferences.Editor editor = prefs.edit();

        txtNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editor.putString(KEY_NOTES, s.toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        //TODO: make rating bar persistent
        RatingBar ratingBar = (RatingBar) findViewById(R.id.countryRating);

        float rating = prefs.getFloat(KEY_RATING, DEFAULT_RATING);

        ratingBar.setRating(rating);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                editor.putFloat(KEY_RATING, rating);
                editor.apply();
            }
        });



        editor.apply();
    }

    private AsyncTask<String, Void, String> makeTask(final String title) {

        return new AsyncTask<String, Void, String>() {
            private String txt;
            @Override
            protected String doInBackground(String[] params) {
                try{
                    txt = retrieveText(title);

                } catch(IOException | JSONException ioe) {
                    ioe.printStackTrace();
                }
                return txt;
            }

            @Override
            protected void onPostExecute(String result) {
                TextView out = (TextView) findViewById(R.id.countryInfo);
                out.setText(Html.fromHtml(result));
                ScrollView sv = (ScrollView) findViewById(R.id.countryInfoScroll);
                if(sv.getHeight() > MAX_HEIGHT) {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,MAX_HEIGHT);
                    sv.setLayoutParams(lp);
                }
            }
        };

    }

    private String retrieveText(String title) throws IOException, JSONException {
        String URLSuffix = "&titles=";
        String URLPrefix = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&exintro=";//&exsentences=
        URL query = new URL(URLPrefix /*+ NUM_SENTENCES*/ + URLSuffix + URLEncoder.encode(title, "utf-8"));
        HttpURLConnection conn = (HttpURLConnection) query.openConnection();
        InputStream in = conn.getInputStream();
        BufferedReader read = new BufferedReader(new InputStreamReader(in));
        StringBuilder response = new StringBuilder();
        String line;
        while((line = read.readLine()) != null) {
//            Log.i("change text", line);
            response.append(line);
        }
        String feedback = response.toString();
        JSONObject result = new JSONObject(feedback);
        JSONObject child = result.getJSONObject("query");
        child = child.getJSONObject("pages");
        child = child.getJSONObject(child.names().getString(0));

        return child.getString("extract");
    }
}
