package edu.georgiasouthern.cr04956.wirelesslab2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private final int NUM_SENTENCES = 4;
    private String URLPrefix = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&exintro=&exsentences=";
    private String URLSuffix = "&titles=";

//    private String countryName = "Cuba";
    private String[] countries = {
            "Cuba",
            "Haiti",
            "United States",
            "Jamaica",
            "Canada",

            "Brazil",
            "Argentina",
            "Chile",
            "Peru",
            "Venezuela",

            "France",
            "Germany",
            "Poland",
            "Greece",
            "Portugal",

            "Russia",
            "China",
            "Japan",
            "India",
            "South Korea",

            "South Africa",
            "Ethiopia",
            "Mali",
            "Morocco",
            "Nigeria"
    };
    private int countryIndex = 0;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeTask(countries[countryIndex]).execute();
//        final TextView txt = (TextView)findViewById(R.id.textView);
        Button btn = (Button) findViewById(R.id.btnRetrieve);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                countryIndex++;
                countryIndex = countryIndex % countries.length;
                makeTask(countries[countryIndex]).execute();

        }
        });

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
                TextView out = (TextView) findViewById(R.id.textView);
                out.setText(Html.fromHtml(result));
            }
        };

    }

    private String retrieveText(String title) throws IOException, JSONException{
        URL query = new URL(URLPrefix + NUM_SENTENCES + URLSuffix + URLEncoder.encode(title, "utf-8"));
        HttpURLConnection conn = (HttpURLConnection) query.openConnection();
        InputStream in = conn.getInputStream();
        BufferedReader read = new BufferedReader(new InputStreamReader(in));
        StringBuilder response = new StringBuilder();
        String line;
        while((line = read.readLine()) != null) {
            Log.i("change text", line);
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
