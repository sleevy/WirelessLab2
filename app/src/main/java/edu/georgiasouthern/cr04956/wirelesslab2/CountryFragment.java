package edu.georgiasouthern.cr04956.wirelesslab2;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

/**
 * Created by Cameron Rhodes on 3/11/2017.
 */

public class CountryFragment extends Fragment {



    public CountryFragment() {
        super();
    }

    private int MAX_HEIGHT = 200;
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_country,container,false);


//        makeTask(v, countries[countryIndex]).execute();

    //        final TextView txt = (TextView)findViewById(R.id.textView);
//    Button btn = (Button) v.findViewById(R.id.btnRetrieve);
//        btn.setOnClickListener(new View.OnClickListener()
//
//    {
//        public void onClick (View v){
//        countryIndex++;
//        countryIndex = countryIndex % countries.length;
//        makeTask(v, countries[countryIndex]).execute();
//
//    }
//    });

        return v;
    }

    private AsyncTask<String, Void, String> makeTask(final View v, final String title) {

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
                TextView out = (TextView) v.findViewById(R.id.countryInfo);
                out.setText(Html.fromHtml(result));
                ScrollView sv = (ScrollView) v.findViewById(R.id.countryInfoScroll);
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
