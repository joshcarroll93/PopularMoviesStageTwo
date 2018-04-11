package popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.backgroundtask;

import android.util.Log;
import android.view.View;

import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.List;

import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data.Movie;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.fragment.MovieTabLayoutFragment;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.utils.JsonUtils;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.utils.NetworkUtils;

/**
 * Created by Josh on 09/04/2018.
 */


public class MovieListQuery extends android.os.AsyncTask<URL, Void, String>{

    private String TAG = getClass().getSimpleName();
    private WeakReference<MovieTabLayoutFragment> movieTabLayoutFragmentWeakReference;

    public MovieListQuery(MovieTabLayoutFragment context){
        movieTabLayoutFragmentWeakReference = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "in onPreExecute");
        MovieTabLayoutFragment movieTabLayoutFragment = movieTabLayoutFragmentWeakReference.get();
        movieTabLayoutFragment.mLoadingIndicator.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(URL... urls) {
        Log.d(TAG, "in doInBackground");

        URL searchURL = urls[0];
        Log.i(TAG, searchURL.toString());
        String movieResults = null;

        try{
            movieResults = NetworkUtils.getResponseFromHttpUrl(searchURL);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        return movieResults;
    }

    @Override
    protected void onPostExecute(String movieResults) {
        MovieTabLayoutFragment movieTabLayoutFragment = movieTabLayoutFragmentWeakReference.get();

        Log.d(TAG, "in onPostExecute");
        movieTabLayoutFragment.mLoadingIndicator.setVisibility(View.INVISIBLE);
        movieTabLayoutFragment.mRecyclerView.setVisibility(View.VISIBLE);
        if (movieResults != null && !movieResults.equals("")) {
            movieTabLayoutFragment.showJsonDataView();
            Log.d(TAG, movieResults);

            try {
                List<Movie> movieList = JsonUtils.getMovieStringsFromJson(movieResults);
                movieTabLayoutFragment.mMovieAdapter.addItems(movieList);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            movieTabLayoutFragment.showErrorMessage();
        }
    }
}