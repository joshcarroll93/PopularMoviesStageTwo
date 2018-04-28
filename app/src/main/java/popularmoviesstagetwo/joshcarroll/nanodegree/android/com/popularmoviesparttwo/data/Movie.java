package popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Josh on 27/03/2018.
 */

public class Movie implements Parcelable {

    private int mId;
    private String mPosterPath;

    public Movie(int id, String posterPath) {

        mId = id;
        mPosterPath = posterPath;
    }

    private Movie(Parcel parcel) {
        mId = parcel.readInt();
        mPosterPath = parcel.readString();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mPosterPath);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };
}
