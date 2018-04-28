package popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.backgroundtask;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data.Movie;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.fragment.MovieTabLayoutFragment;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.provider.MovieContract;

/**
 * Created by Josh on 15/04/2018.
 */
@SuppressWarnings("ConstantConditions")
public class MovieFavouritesQuery extends AsyncTask<Void, Void, List<Movie>> {

    private WeakReference<MovieTabLayoutFragment> movieTabLayoutFragmentWeakReference;
    private String TAG = getClass().getSimpleName();

    public MovieFavouritesQuery(MovieTabLayoutFragment movieTabLayoutFragment) {
        movieTabLayoutFragmentWeakReference = new WeakReference<>(movieTabLayoutFragment);
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, " in onPreExecute");
        MovieTabLayoutFragment movieTabLayoutFragment = movieTabLayoutFragmentWeakReference.get();
        movieTabLayoutFragment.showProgressBar();

    }

    @Override
    protected List<Movie> doInBackground(Void... nothing) {
        Log.d(TAG, "in doInBackground");
        Cursor data = null;
        MovieTabLayoutFragment movieTabLayoutFragment = movieTabLayoutFragmentWeakReference.get();
        try {
            data = movieTabLayoutFragment.getActivity().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    MovieContract.MovieEntry._ID);

        } catch (Exception e) {
            Log.e("MovieTabFrag", "Failed to asynchronously load data.");
            e.printStackTrace();
        }

        Log.d(TAG, "Number of movies in db: " + data.getCount());

        if (data.getCount() != movieTabLayoutFragment.favourites.size()) {

            movieTabLayoutFragment.favourites.clear();
            while (data.moveToNext()) {
                int movieIdIndex = data.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID);
                int posterPathIndex = data.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH);

                int movieId = data.getInt(movieIdIndex);
                String posterPath = data.getString(posterPathIndex);


                Log.d(TAG, " MovieId: " + movieId);
                Log.d(TAG, "PosterPath: " + posterPath);
                Movie movie = new Movie(movieId, posterPath);
                movieTabLayoutFragment.favourites.add(movie);

                Log.d(TAG, "favourites list size" + movieTabLayoutFragment.favourites.size());
            }


        }


        data.close();


        Log.d(TAG, "FAVS SIZE: " + movieTabLayoutFragment.favourites.size());
        return movieTabLayoutFragment.favourites;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        MovieTabLayoutFragment movieTabLayoutFragment = movieTabLayoutFragmentWeakReference.get();

        Log.d(TAG, "DATABASE SIZE: " + movies.size());
        Log.d(TAG, "FAVOURITES SIZE: " + movieTabLayoutFragment.favourites.size());
        Log.d(TAG, "ADAPTER SIZE: " + movieTabLayoutFragment.mMovieAdapter.getItemCount());


        Log.d(TAG, "inPostExecuteBruv");
        if (movies.size() > 0) {
            Log.d(TAG, "adapterSize: " + movieTabLayoutFragment.mMovieAdapter.getItemCount());

            movieTabLayoutFragment.mMovieAdapter.addItems(movies);

            Log.d(TAG, "adapterSize: " + movieTabLayoutFragment.mMovieAdapter.getItemCount());
            movieTabLayoutFragment.showJsonDataView();
        } else {
            movieTabLayoutFragment.showErrorMessage("No Favourites Saved!");
        }
    }
}
