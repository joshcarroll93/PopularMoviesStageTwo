package popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.activity;

import android.content.ContentValues;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.R;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.adapter.MovieReviewsAdapter;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.adapter.MovieTrailersAdapter;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.backgroundtask.MovieDetailQuery;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.backgroundtask.MovieReviewsQuery;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.backgroundtask.MovieTrailersQuery;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data.Movie;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data.MovieDetail;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data.Review;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data.Trailer;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.provider.MovieContract;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.utils.NetworkUtils;

public class MovieDetailActivity extends AppCompatActivity {


    public MovieDetail mMovieDetail;
    public MovieTrailersAdapter movieTrailersAdapter;
    public MovieReviewsAdapter movieReviewsAdapter;
    private Movie mMovie;
    private ProgressBar progressBar;
    private RelativeLayout detailLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getSupportActionBar().hide();

        toolbarSetup();

        if(getIntent().getExtras() != null){
            mMovie = getIntent().getParcelableExtra("movie");
        }
        int movieId = mMovie.getId();

        detailLayout = findViewById(R.id.movie_detail_layout);
        progressBar = findViewById(R.id.pb_movie_detail);
        FloatingActionButton fabFavourites = findViewById(R.id.button_add_favourite);

        URL detailsUrl = NetworkUtils.buildUrlForMovieDetail(String.valueOf(movieId));
        new MovieDetailQuery(this).execute(detailsUrl);

        URL trailersUrl = NetworkUtils.buildUrlForTrailersOrReviews(String.valueOf(movieId), "videos");
        new MovieTrailersQuery(this).execute(trailersUrl);

        URL reviewsUrl = NetworkUtils.buildUrlForTrailersOrReviews(String.valueOf(movieId), "reviews");
        new MovieReviewsQuery(this).execute(reviewsUrl);

        RecyclerView trailerRecyclerView = findViewById(R.id.trailer_recycler_view);
        movieTrailersAdapter = new MovieTrailersAdapter(new ArrayList<Trailer>(), this);
        trailerRecyclerView.setAdapter(movieTrailersAdapter);
        RecyclerView.LayoutManager trailersManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trailerRecyclerView.setLayoutManager(trailersManager);

        RecyclerView reviewRecyclerView = findViewById(R.id.review_recycler_view);
        movieReviewsAdapter = new MovieReviewsAdapter(new ArrayList<Review>(), this);
        reviewRecyclerView.setAdapter(movieReviewsAdapter);
        RecyclerView.LayoutManager reviewsManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reviewRecyclerView.setLayoutManager(reviewsManager);

        fabFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            ContentValues contentValues = new ContentValues();

            contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, mMovie.getId());
            contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, mMovie.getPosterPath());

            Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);

            if (uri != null) {
                Snackbar.make(view, uri.toString(), Snackbar.LENGTH_SHORT).show();
            }
            }
        });
    }

    private void toolbarSetup(){
        final Toolbar actionbar = findViewById(R.id.toolbar);

        if (null != actionbar) {
            actionbar.setNavigationIcon(R.drawable.ic_arrow_back_white_18dp);

            actionbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavUtils.navigateUpFromSameTask(MovieDetailActivity.this);
                }
            });
        }
    }
    public void initViews(){

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitle(mMovieDetail.getmTitle());

        ImageView toolbarImage = findViewById(R.id.toolbarImage);
        ImageView moviePoster = findViewById(R.id.id_detail_poster);

        TextView releaseDate = findViewById(R.id.id_release_date);
        TextView mOverview = findViewById(R.id.id_overview);
        TextView runtime = findViewById(R.id.id_runtime);
        TextView movieRating = findViewById(R.id.id_rating);

        Picasso.with(getApplicationContext()).load(mMovieDetail.getmBackdropPath())
                .into(toolbarImage);
        Picasso.with(getApplicationContext()).load(mMovieDetail.getmPosterPath())
                .into(moviePoster);

        String time = String.valueOf(mMovieDetail.getmRuntime()) + " mins";
        String upToNCharacters = mMovieDetail.getmReleaseDate().substring(0, Math.min(mMovieDetail.getmReleaseDate().length(), 4));
        String outOften = String.valueOf(mMovieDetail.getmVoteAverage()) + "/10";

        releaseDate.setText(upToNCharacters);
        mOverview.setText(mMovieDetail.getmOverview());
        runtime.setText(time);
        movieRating.setText(outOften);
    }

    public void showLoadingBar(){
        progressBar.setVisibility(View.VISIBLE);
        detailLayout.setVisibility(View.INVISIBLE);
    }

    public void showDetails(){
        detailLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
