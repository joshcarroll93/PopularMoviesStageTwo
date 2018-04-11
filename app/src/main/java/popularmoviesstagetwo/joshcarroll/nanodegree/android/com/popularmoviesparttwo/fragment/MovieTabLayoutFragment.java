package popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.fragment;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.R;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.adapter.MovieAdapter;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.backgroundtask.MovieListQuery;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data.Movie;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.provider.MovieContract;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.utils.NetworkUtils;

public class MovieTabLayoutFragment extends Fragment  {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int MOVIE_LOADER_ID = 0;

    public ProgressBar mLoadingIndicator;
    public RecyclerView mRecyclerView;
    public MovieAdapter mMovieAdapter;

    private TextView mErrorMessageDisplay;
    private int pageNumber = 1;
    List<Movie> favourites = new ArrayList<>();

    public MovieTabLayoutFragment(){

    }
    public static MovieTabLayoutFragment newInstance(int sectionNumber){
        MovieTabLayoutFragment movieTabLayoutFragment = new MovieTabLayoutFragment();
        Bundle args = new Bundle();
        Log.d("", "Place holder being made for : " + sectionNumber);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        movieTabLayoutFragment.setArguments(args);
        return movieTabLayoutFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_tab_layout, container, false);

        mErrorMessageDisplay = view.findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = view.findViewById(R.id.pb_loading_indicator);

        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mMovieAdapter = new MovieAdapter(getContext(), new ArrayList<Movie>());
        mRecyclerView.setAdapter(mMovieAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!recyclerView.canScrollVertically(1)){

                    if(pageNumber <= 10){
                        Log.d("SCROLL CHANGED", ""+ dx + " " + dy);
                        pageNumber += 1;

                        if(getArguments().getInt(ARG_SECTION_NUMBER) == 1){
                            makeMovieSearchQuery(getString(R.string.popular_api));
                        }else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                            makeMovieSearchQuery(getString(R.string.top_rated_api));
                        }

                    }
                }
            }
        });



        if(getArguments().getInt(ARG_SECTION_NUMBER) == 1){

            pageNumber = 1;
            makeMovieSearchQuery(getString(R.string.popular_api));
            mMovieAdapter.clearList();
            //PopularMovies

        }
        else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2){
            //topRatedMovies
            pageNumber = 1;
            makeMovieSearchQuery(getString(R.string.top_rated_api));
            mMovieAdapter.clearList();
        }
        else if(getArguments().getInt(ARG_SECTION_NUMBER) == 3){
            //favourites
            new loadcursorTask().execute();
        }

        return view;
    }

    public void makeMovieSearchQuery(String sortBy) {
        URL movieSearchUrl = NetworkUtils.buildUrlForMovieList(sortBy, pageNumber);
        new MovieListQuery(this).execute(movieSearchUrl);
    }

    public void showJsonDataView() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public void showErrorMessage() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public void showProgressBar(){
        mLoadingIndicator.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }


    public class loadcursorTask extends AsyncTask<Object, Void, List<Movie>>{

    String TAG = getClass().getSimpleName();
        @Override
        protected void onPreExecute() {
            Log.d(TAG, " in onPreExecute");
            showProgressBar();

        }

        @Override
        protected List<Movie> doInBackground(Object... objects) {
            Log.d(TAG, "in doInBackground");
            Cursor data = null;

            try {
                data = getActivity().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        MovieContract.MovieEntry._ID);

            } catch (Exception e) {
                Log.e("MovieTabFrag", "Failed to asynchronously load data.");
                e.printStackTrace();
            }

            Log.d(TAG, "Number of movies in db: "+data.getCount());
            while(data.moveToNext()){
                int movieIdIndex = data.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID);
                int posterPathIndex = data.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH);

                int movieId = data.getInt(movieIdIndex);
                String posterPath = data.getString(posterPathIndex);

                Log.d(TAG, " MovieId: " +movieId);
                Log.d(TAG, "PosterPath: " + posterPath);
                Movie movie = new Movie(movieId, posterPath);
                Log.d(TAG, posterPath);
                favourites.add(movie);
            }

            data.close();
            return favourites;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {

            Log.d(TAG, "inPostExecuteBruv");
            if(movies.size() > 0){
                Log.d(TAG, "adapterSize: "+ mMovieAdapter.getItemCount());
                mMovieAdapter.clearList();
                mMovieAdapter.addItems(movies);
                Log.d(TAG, "adapterSize: "+ mMovieAdapter.getItemCount());
                showJsonDataView();
            }else{
                showErrorMessage();
            }
        }
    }
}
