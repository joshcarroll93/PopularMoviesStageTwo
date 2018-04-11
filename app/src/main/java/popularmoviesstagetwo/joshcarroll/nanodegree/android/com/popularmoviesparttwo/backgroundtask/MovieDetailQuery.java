package popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.backgroundtask;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;

import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.activity.MovieDetailActivity;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.utils.JsonUtils;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.utils.NetworkUtils;

/**
 * Created by Josh on 09/04/2018.
 */

public  class MovieDetailQuery extends android.os.AsyncTask<URL, Void, String>{
    String TAG = "MovieDetailTask";

    WeakReference<MovieDetailActivity> movieDetailActivityWeakReference;

    public MovieDetailQuery(MovieDetailActivity movieDetailActivity){
        movieDetailActivityWeakReference = new WeakReference<>(movieDetailActivity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MovieDetailActivity movieDetailActivity = movieDetailActivityWeakReference.get();
        movieDetailActivity.showLoadingBar();
    }

    @Override
    protected String doInBackground(URL... urls) {
        Log.d(TAG, "in doInBackground");

        URL searchURL = urls[0];
        Log.i(TAG, searchURL.toString());
        String movieResult = null;

        try{
            movieResult = NetworkUtils.getResponseFromHttpUrl(searchURL);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        return movieResult;
    }

    @Override
    protected void onPostExecute(String movieResult) {
        MovieDetailActivity movieDetailActivity = movieDetailActivityWeakReference.get();

        try{
            movieDetailActivity.mMovieDetail = JsonUtils.getMovieForDetailActivity(movieResult);
            movieDetailActivity.initViews();
            movieDetailActivity.showDetails();
        }
        catch(JSONException jse){
            jse.printStackTrace();
        }
    }
}
