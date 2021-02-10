package edu.citymail.flixter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//will encapsulate the idea of a movie
public class Movie {

    String backdropPath;
    String posterPath;
    String title;
    String overview;

    //constructor that takes in a JSON object and contructs a movie object
    public Movie(JSONObject jsonObject) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
        //job is to read out the fields that we care about, name we get it exactly from the key given
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");

    }
    //this method returns a list of movie, is going to be called in mainActivity
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie>  movies = new ArrayList<>();
        //iterated until the length of the array
        for (int i=0; i< movieJsonArray.length(); i++){
            //adds a movie at each position of the array, so were calling the constructor from above
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        //returns the list of movies
        return movies;
    }

    //getters so we can easily get each on of them
    public String getPosterPath() {
        //hard coding the image size,
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String getBackdropPath(){
        //hard code the landscape image size
        return String.format("https://image.tmdb.org/t/p/w342/%s",backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
