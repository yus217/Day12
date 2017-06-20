package com.example.yuecheng.day12;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // JSON javascript object notation //XML
    //data interchange format
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeatherTask task = new WeatherTask();
        task.execute("http://api.openweathermap.org/data/2.5/weather?q=Allebtown,us&appid=ccb397d609e61e09683ef081cdd29a22");



    }


    public class WeatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String pageURL = strings[0];
            URL url = null;
            HttpURLConnection urlConnection = null;
            String result = "";

            try {
                //convert pageURL to URL
                url = new URL(pageURL);
                //open connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream stream = urlConnection.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(stream);

                int data = streamReader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = streamReader.read();
                }
            } catch (MalformedURLException o) {
                o.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String result) {

           super.onPostExecute(result);

            Double temp = 0.0;
            try{
                JSONObject jsonObject = new JSONObject(result);
                //jsonObject.getJSONObject("main").getDouble("temp");

                temp = jsonObject.getJSONObject("main").getDouble("temp");
            } catch (JSONException e) {
                e.printStackTrace();
            }



            Log.i("Temp: ", Double.toString(temp));
            //Log.i("data: ", result);

        }
    }



}
