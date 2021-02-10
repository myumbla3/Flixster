package edu.citymail.flixter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.citymail.flixter.adapters.MovieAdapter;
import edu.citymail.flixter.models.Movie;
import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    //makes a get request on the URL to get the currently playing movies
    //URL is saved as a constant called NOW_PLAYING_URL, API key is embedded in the URL so need to pass seperately
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    // making a tag so we can easily log data
    public static final String TAG = "MainActivity";

    //makes it a member variable to implement easily
    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies =findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

        //create the adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        //set the adapter on the recycler view
        rvMovies.setAdapter(movieAdapter);

        //set a layout manager on the rvfor the rv to know how to set the views on the layout screen
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        //an instance of asyncHttpClient
        AsyncHttpClient client = new AsyncHttpClient();
        //make get request on the URL and passing a callback
        //JSON HTTP response handler done b/c the movie database API is returning JSON
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results:" + results.toString());
                    //THIS gives a list of movie objects
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies:" + movies.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");

            }
        });

        //



    }
}