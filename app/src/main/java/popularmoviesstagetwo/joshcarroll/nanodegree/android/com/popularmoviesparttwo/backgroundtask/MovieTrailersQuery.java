package popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.backgroundtask;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.List;

import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.activity.MovieDetailActivity;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data.Trailer;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.utils.JsonUtils;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.utils.NetworkUtils;

/**
 * Created by Josh on 09/04/2018.
 */

public class MovieTrailersQuery extends AsyncTask<URL, Void, String> {

    private WeakReference<MovieDetailActivity> movieTrailersTaskWeakReference;

    public MovieTrailersQuery(MovieDetailActivity movieDetailActivity){
        movieTrailersTaskWeakReference = new WeakReference<MovieDetailActivity>(movieDetailActivity);

    }

    @Override
    protected String doInBackground(URL... urls) {
        URL searchURL = urls[0];
        String trailerResult = null;

        try{
            trailerResult = NetworkUtils.getResponseFromHttpUrl(searchURL);
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        return trailerResult;
    }

    @Override
    protected void onPostExecute(String trailersResult) {
        MovieDetailActivity movieDetailActivity = movieTrailersTaskWeakReference.get();

        if(trailersResult != null){
            try {
                List<Trailer> trailers = JsonUtils.getMovieTrailers(trailersResult);
                movieDetailActivity.movieTrailersAdapter.addTrailers(trailers);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
