package popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.backgroundtask;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.List;

import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.activity.MovieDetailActivity;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data.Review;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.utils.JsonUtils;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.utils.NetworkUtils;

/**
 * Created by Josh on 09/04/2018.
 */

public class MovieReviewsQuery extends AsyncTask<URL, Void, String> {

    WeakReference<MovieDetailActivity> movieDetailActivityWeakReference;

    public MovieReviewsQuery(MovieDetailActivity movieDetailActivity){
        movieDetailActivityWeakReference = new WeakReference<MovieDetailActivity>(movieDetailActivity);
    }


    @Override
    protected String doInBackground(URL... urls) {
        URL searchURL = urls[0];
        String reviewResult = null;

        try{
            reviewResult = NetworkUtils.getResponseFromHttpUrl(searchURL);
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        return reviewResult;
    }

    @Override
    protected void onPostExecute(String reviewResults) {
        MovieDetailActivity movieDetailActivity =  movieDetailActivityWeakReference.get();

        if(reviewResults != null){
            try{
                List<Review> reviews = JsonUtils.getMovieReviews(reviewResults);
                movieDetailActivity.movieReviewsAdapter.addReviews(reviews);
            }catch (JSONException jse){
                jse.printStackTrace();
            }
        }
    }
}
