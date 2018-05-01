package popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.R;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.activity.MovieDetailActivity;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.adapter.MovieAdapter;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.backgroundtask.MovieFavouritesQuery;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.backgroundtask.MovieListQuery;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data.Movie;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.listener.NewFavouriteListener;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.utils.NetworkUtils;

@SuppressWarnings("ConstantConditions")
public class MovieTabLayoutFragment extends Fragment implements NewFavouriteListener, Serializable {

    private static final String ARG_SECTION_NUMBER = "section_number";
    public ProgressBar mLoadingIndicator;
    public RecyclerView mRecyclerView;
    public MovieAdapter mMovieAdapter;
    private TextView mErrorMessageDisplay;
    private int pageNumber = 1;
    public List<Movie> favourites = new ArrayList<>();
    private ArrayList<Movie> movies = new ArrayList<>();

    public MovieTabLayoutFragment() {

    }

    public static MovieTabLayoutFragment newInstance(int sectionNumber) {
        MovieTabLayoutFragment movieTabLayoutFragment = new MovieTabLayoutFragment();
        Bundle args = new Bundle();
        Log.d("", "Fragment : " + sectionNumber);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        movieTabLayoutFragment.setArguments(args);
        return movieTabLayoutFragment;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mMovieAdapter.getItemCount() > 0)
            movies = (ArrayList<Movie>) mMovieAdapter.getMovies();

        if (movies.size() > 0)
            outState.putParcelableArrayList("restored_movies", movies);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final int fragmentNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        if (savedInstanceState != null) {
            movies = savedInstanceState.getParcelableArrayList("restored_movies");
        }

        final View view = inflater.inflate(R.layout.fragment_movie_tab_layout, container, false);

        mErrorMessageDisplay = view.findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = view.findViewById(R.id.pb_loading_indicator);
        mRecyclerView = view.findViewById(R.id.my_recycler_view);

        int orientation = getResources().getConfiguration().orientation;

        RecyclerView.LayoutManager mLayoutManager;
        if (orientation == 1) {
            mLayoutManager = new GridLayoutManager(getActivity(), 2);
        } else {
            mLayoutManager = new GridLayoutManager(getActivity(), 3);
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        mMovieAdapter = new MovieAdapter(getContext(), new ArrayList<Movie>());
        mMovieAdapter.setFragNumber(fragmentNumber);
        mRecyclerView.setAdapter(mMovieAdapter);
        MovieDetailActivity.setListener(this);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!recyclerView.canScrollVertically(RecyclerView.VERTICAL)) {

                    if (pageNumber < 10) {

                        if (fragmentNumber == 1) {
                            pageNumber += 1;
                            makeMovieSearchQuery(getString(R.string.popular_api));
                        } else if (fragmentNumber == 2) {
                            pageNumber += 1;
                            makeMovieSearchQuery(getString(R.string.top_rated_api));
                        }

                    }
                }
            }
        });

        if (fragmentNumber == 1) {
            //PopularMovies
            pageNumber = 1;
            makeMovieSearchQuery(getString(R.string.popular_api));
        } else if (fragmentNumber == 2) {
            //topRatedMovies
            pageNumber = 1;
            makeMovieSearchQuery(getString(R.string.top_rated_api));
        } else if (fragmentNumber == 3) {
            //favourites
            makeFavouritesQuery();
        }
        return view;
    }

    public void makeMovieSearchQuery(String sortBy) {
        //checking for restored state
        if (movies.size() > 0 && pageNumber <= 1) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mMovieAdapter.clearList();
            mMovieAdapter.addItems(movies);
        } else {
            URL movieSearchUrl = NetworkUtils.buildUrlForMovieList(sortBy, pageNumber);
            new MovieListQuery(this).execute(movieSearchUrl);
        }
    }

    public void makeFavouritesQuery() {
        mMovieAdapter.clearList();
        new MovieFavouritesQuery(this).execute();
    }

    public void showJsonDataView() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public void showErrorMessage(String errorMsg) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mErrorMessageDisplay.setText(errorMsg);
    }

    public void showProgressBar() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void newFavourite() {
        makeFavouritesQuery();
    }

}
