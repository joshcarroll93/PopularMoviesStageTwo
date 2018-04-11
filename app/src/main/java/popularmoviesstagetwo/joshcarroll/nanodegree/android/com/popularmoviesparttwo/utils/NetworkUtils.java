package popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.utils;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.BuildConfig;

/**
 * Created by Josh on 27/03/2018.
 */

public class NetworkUtils {

    private final static String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final static String API_KEY = BuildConfig.THE_MOVIE_DB_API_KEY;
    private final static String QUERY_API_KEY = "api_key";
    private final static String QUERY_PAGE_NUMBER = "page";

    public static URL buildUrlForMovieList(String sortBy, int pageNumber) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(sortBy)
                .appendQueryParameter(QUERY_PAGE_NUMBER, String.valueOf(pageNumber))
                .appendQueryParameter(QUERY_API_KEY, API_KEY)
                .build();

        String TAG = "NETWORK_UTILS";
        Log.i(TAG, builtUri.toString());

        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.d(TAG, url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildUrlForMovieDetail(String movieId){

        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(movieId)
                .appendQueryParameter(QUERY_API_KEY, API_KEY)
                .build();

        String TAG = "NETWORK_UTILS";

        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.d(TAG, url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildUrlForTrailersOrReviews(String movieId, String pathType){
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(movieId)
                .appendPath(pathType)
                .appendQueryParameter(QUERY_API_KEY, API_KEY)
                .build();

        URL url = null;
        try{
            url = new URL(builtUri.toString());
            Log.d("NETWORK_UTILS", url.toString());
        }catch (MalformedURLException murle){
            murle.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        BufferedReader reader = null;
        String moviesJsonStr = null;

        try {

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();

            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            if (builder.length() == 0) {
                return null;
            }

            moviesJsonStr = builder.toString();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            urlConnection.disconnect();

            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return moviesJsonStr;
    }
}
