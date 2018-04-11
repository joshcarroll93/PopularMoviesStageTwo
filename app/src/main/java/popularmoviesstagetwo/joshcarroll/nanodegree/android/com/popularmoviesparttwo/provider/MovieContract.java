package popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Josh on 09/04/2018.
 */

public class MovieContract {

    public static final String AUTHORITY = "popularmoviesstagetwo.joshcarroll.nanodegree.android";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_POSTER_PATH = "poster_path";
    }
}
