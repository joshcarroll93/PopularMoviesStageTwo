package popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Josh on 29/03/2018.
 */

public class Review implements Parcelable {

    private String author;
    private String content;

    public Review(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public Review(Parcel parcel){
        author = parcel.readString();
        content = parcel.readString();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(author);
        parcel.writeString(content);
    }
    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel parcel) {
            return new Review(parcel);
        }

        @Override
        public Review[] newArray(int i) {
            return new Review[0];
        }
    };
}
