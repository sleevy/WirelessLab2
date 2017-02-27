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

    private static String URLPrefix = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&exintro=&exsentences=5&titles=";
    private static final String TAG = "edu.georgiasouthern.cr04956.wirelesslab2.MAIN";
    private String countryName = "United States";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final TextView txt = (TextView)findViewById(R.id.textView);
        Button btn = (Button) findViewById(R.id.btnRetrieve);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                makeTask(countryName).execute();

        }
        });

//            changeText("Haiti", txt);


//        } catch(MalformedURLException murle) {
//          murle.printStackTrace();
//        } catch(IOException ioe) {
//            ioe.printStackTrace();
//        } catch(JSONException jsone) {
//            jsone.printStackTrace();
//        }

    }


    private AsyncTask<String, Void, String> makeTask(final String title) {

        return new AsyncTask<String, Void, String>() {
            private String txt;
            @Override
            protected String doInBackground(String[] params) {
                try{
                    txt = retrieveText(title);

                } catch(MalformedURLException murle) {
                    murle.printStackTrace();
                } catch(IOException ioe) {
                    ioe.printStackTrace();
                } catch(JSONException jsone) {
                    jsone.printStackTrace();
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
        URL query = new URL(URLPrefix + URLEncoder.encode(title, "utf-8"));
        HttpURLConnection conn = (HttpURLConnection) query.openConnection();
//        String feedback = conn.getContent();
        InputStream in = conn.getInputStream();
        BufferedReader read = new BufferedReader(new InputStreamReader(in));
        StringBuffer response = new StringBuffer();
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


        String extract = child.getString("extract");
//        TextView out = (TextView) findViewById(R.id.textView);
        return extract;
//        out.setText(Html.fromHtml(extract));
    }
}