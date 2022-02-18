package com.example.booklisting;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();

    public QueryUtils(){}

    public static List<Book> fetchBookData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        return extractFeatureFromJson(jsonResponse);
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if(url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(1000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<Book> extractFeatureFromJson(String bookJSON) {
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }
        ArrayList<Book> books = new ArrayList<>();

        try {
            JSONArray items = new JSONObject(bookJSON).getJSONArray("items");
            for(int i=0; i<items.length(); i++) {
                JSONObject item = items.getJSONObject(i).getJSONObject("volumeInfo");
                String title = item.has("title") ? item.getString("title") : "No title";
                JSONArray authors = item.has("authors") ? item.getJSONArray("authors") : null;
                String language = item.has("averageRating") ? item.getString("language") : "";
                double averageRating = item.has("averageRating") ? item.getDouble("averageRating") : 0.0;
                int ratingsCount = item.has("ratingsCount") ? item.getInt("ratingsCount") : 1;
                String previewLink = item.has("previewLink") ? item.getString("previewLink") : "";
                books.add(new Book(title, authors, language, averageRating, ratingsCount, previewLink));
            }


        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return books;
    }

}
