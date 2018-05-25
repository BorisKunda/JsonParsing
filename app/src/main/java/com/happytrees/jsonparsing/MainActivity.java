package com.happytrees.jsonparsing;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AsyncTaskJsonParser asyncTaskJsonParser = new AsyncTaskJsonParser();//instantiate class  READJSON
        asyncTaskJsonParser.execute("https://maps.googleapis.com/maps/api/place/textsearch/json?query=gas+station+Ten%20%D7%A8%D7%9E%D7%9C%D7%94&key=AIzaSyDo6e7ZL0HqkwaKN-GwKgqZnW03FhJNivQ");



        }


 /*
      <Params,Progress,Result>
       Params - The type of the input variables value you want to set to the background process. This can be an array of objects -->doInBackground
       Progress - The type of the objects you are going to enter in the onProgressUpdate method.-->onProgressUpdate
       Result - The type of the result from the operations you have done in the background process.-->onPostExecute()
       */

        //inner class async task
    class AsyncTaskJsonParser extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String...params) {
            return readURL(params[0]);//params[0] refers to  first element in array(because of i =0 ),like array of different urls you need execute
        }

            @Override
            protected void onPostExecute(String content) {

                try {
                    //JSON parsing
                    JSONObject jsonObjectContainer = new JSONObject(content);
                    JSONArray jsonArrayResults = jsonObjectContainer.getJSONArray("results");//"results" is name of array of movies in json link



                        JSONObject resultObject =jsonArrayResults.getJSONObject(0);
                        JSONObject geometryObject = resultObject.getJSONObject("geometry");
                        JSONObject locationObject = geometryObject.getJSONObject("location");
                        Log.e("loc" ," lat " +  locationObject.get("lat") + " lng " +  locationObject.get("lng") );





                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }


        //READ URL
    private String readURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            //create a url object
            URL url = new URL(theUrl);
            //creates a urlConnection object
            URLConnection urlConnection = url.openConnection();
            //wrap the urlConnection in a BufferReader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            //read from the urlConnection via the bufferReader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }


    }
