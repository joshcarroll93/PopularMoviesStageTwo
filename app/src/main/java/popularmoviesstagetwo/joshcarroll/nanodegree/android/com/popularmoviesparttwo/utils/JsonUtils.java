package popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data.Movie;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data.MovieDetail;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data.Review;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data.Trailer;

/**
 * Created by Josh on 27/03/2018.
 */

public class JsonUtils {

    private final static String MDB_MOVIE_ID = "id";
    private final static String MDB_POSTER_PATH = "poster_path";
    private final static String MDB_TITLE = "title";
    private final static String MDB_RELEASE_DATE = "release_date";
    private final static String MDB_VOTE_AVERAGE = "vote_average";
    private final static String MDB_OVERVIEW = "overview";
    private final static String MDB_RUNTIME = "runtime";
    private final static String MDB_BACKDROP_PATH = "backdrop_path";
    private final static String MDB_TAG_LINE = "tagline";
    private final static String MDB_KEY = "key";
    private final static String MDB_NAME = "name";
    private final static String MDB_SITE = "site";
    private final static String MDB_TYPE = "type";
    private final static String MDB_AUTHOR = "author";
    private final static String MDB_CONTENT = "content";
    private final static String MDB_RESULT = "results";

    public static List<Movie> getMovieStringsFromJson(String movieJsonStr)
            throws JSONException {

        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray movieArray = movieJson.getJSONArray(MDB_RESULT);

        String TAG = "JSON_UTILS";
        Log.d(TAG, movieArray.toString());

        List<Movie> movieList = new ArrayList<>();

        for (int i = 0; i < movieArray.length(); i++) {

            JSONObject jsonMovie = movieArray.getJSONObject(i);


            int id = (int) jsonMovie.get(MDB_MOVIE_ID);
            String posterPath = (String) jsonMovie.get(MDB_POSTER_PATH);


            Movie movie = new Movie(0, "");
            movie.setId(id);
            movie.setPosterPath(posterPath);

            movieList.add(movie);
        }
        return movieList;
    }

    public static MovieDetail getMovieForDetailActivity(String movieJsonStr)
            throws JSONException {

        JSONObject jsonMovie = new JSONObject(movieJsonStr);

        String title = (String) jsonMovie.get(MDB_TITLE);
        int runtime = (int) jsonMovie.get(MDB_RUNTIME);
        String releaseDate = (String) jsonMovie.get(MDB_RELEASE_DATE);
        double voteAverage = (double) jsonMovie.get(MDB_VOTE_AVERAGE);
        String overView = (String) jsonMovie.get(MDB_OVERVIEW);
        String posterPath = (String) jsonMovie.get(MDB_POSTER_PATH);
        String backdropPath = (String) jsonMovie.get(MDB_BACKDROP_PATH);
        String tagLine = (String) jsonMovie.get(MDB_TAG_LINE);

        Log.d("JSON_UTILS", title);

        MovieDetail movieDetail = new MovieDetail("", 0, "", 0.0, "", "", "", "");

        movieDetail.setmTitle(title);
        movieDetail.setmRuntime(runtime);
        movieDetail.setmReleaseDate(releaseDate);
        movieDetail.setmVoteAverage(voteAverage);
        movieDetail.setmOverview(overView);
        movieDetail.setmPosterPath(posterPath);
        movieDetail.setmBackdropPath(backdropPath);
        movieDetail.setmTagline(tagLine);

        return movieDetail;
    }

    public static List<Trailer> getMovieTrailers(String trailerJson)
            throws JSONException{

        JSONObject movieJson = new JSONObject(trailerJson);
        JSONArray movieArray = movieJson.getJSONArray(MDB_RESULT);

        List<Trailer> trailers = new ArrayList<>();

        for(int i = 0; i < movieArray.length(); i++){
            JSONObject result = movieArray.getJSONObject(i);

            String key = (String)result.get(MDB_KEY);
            String name = (String)result.get(MDB_NAME);
            String site = (String)result.get(MDB_SITE);
            String type = (String)result.get(MDB_TYPE);

            Trailer trailer = new Trailer("", "", "","");
            trailer.setKey(key);
            trailer.setName(name);
            trailer.setSite(site);
            trailer.setType(type);

            Log.d("JSON_UTILS", trailer.getName());

            trailers.add(trailer);
        }
        return trailers;
    }

    public static List<Review> getMovieReviews(String reviewsJson)
            throws JSONException{

        JSONObject rawJsonReviews = new JSONObject(reviewsJson);
        JSONArray reviewResultArray = rawJsonReviews.getJSONArray(MDB_RESULT);

        List<Review> reviews = new ArrayList<>();

        for (int i = 0; i < reviewResultArray.length(); i++){

            JSONObject reviewItem = reviewResultArray.getJSONObject(i);

            String author = (String)reviewItem.get(MDB_AUTHOR);
            String content = (String)reviewItem.get(MDB_CONTENT);

            Review review = new Review("", "");
            review.setAuthor(author);
            review.setContent(content);

            Log.d("JSON_UTILS", review.getAuthor());

            reviews.add(review);
        }
        return reviews;
    }
}