package popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Josh on 27/03/2018.
 */

public class MovieDetail implements Parcelable {

    private String MDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

    private String mTitle;
    private int mRuntime;
    private String mReleaseDate;
    private double mVoteAverage;
    private String mOverview;
    private String mPosterPath;
    private String mBackdropPath;
    private String mTagline;

    public MovieDetail(String title, int runtime, String releaseDate, double voteAverage, String overview, String posterPath, String backdropPath, String tagline) {

        mTitle = title;
        mRuntime = runtime;
        mReleaseDate = releaseDate;
        mVoteAverage = voteAverage;
        mOverview = overview;
        mPosterPath = posterPath;
        mBackdropPath = backdropPath;
        mTagline = tagline;
    }

    private MovieDetail(Parcel parcel) {
        mTitle = parcel.readString();
        mRuntime = parcel.readInt();
        mReleaseDate = parcel.readString();
        mVoteAverage = parcel.readDouble();
        mOverview = parcel.readString();
        mPosterPath = parcel.readString();
        mBackdropPath = parcel.readString();
        mTagline = parcel.readString();
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getmRuntime() {
        return mRuntime;
    }

    public void setmRuntime(int mRuntime) {
        this.mRuntime = mRuntime;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public void setmReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public double getmVoteAverage() {
        return mVoteAverage;
    }

    public void setmVoteAverage(double voteAverage) {
        this.mVoteAverage = voteAverage;
    }

    public String getmOverview() {
        return mOverview;
    }

    public void setmOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public String getmPosterPath() {
        String MDB_IMAGE_SIZE_342 = "w342/";
        return MDB_IMAGE_BASE_URL + MDB_IMAGE_SIZE_342 + mPosterPath;
    }

    public void setmPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public String getmBackdropPath() {
        String MDB_IMAGE_SIZE_500 = "w500/";
        return MDB_IMAGE_BASE_URL + MDB_IMAGE_SIZE_500 + mBackdropPath;
    }

    public void setmBackdropPath(String mBackdropPath) {
        this.mBackdropPath = mBackdropPath;
    }

    public String getmTagline() {
        return mTagline;
    }

    public void setmTagline(String mTagline) {
        this.mTagline = mTagline;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeInt(mRuntime);
        parcel.writeString(mReleaseDate);
        parcel.writeDouble(mVoteAverage);
        parcel.writeString(mOverview);
        parcel.writeString(mPosterPath);
        parcel.writeString(mBackdropPath);
        parcel.writeString(mTagline);
    }

    public static final Creator<MovieDetail> CREATOR = new Creator<MovieDetail>() {
        @Override
        public MovieDetail createFromParcel(Parcel parcel) {
            return new MovieDetail(parcel);
        }

        @Override
        public MovieDetail[] newArray(int i) {
            return new MovieDetail[i];
        }
    };
}
