package popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.animation.Transformation;

/**
 * Created by Josh on 28/03/2018.
 */

public class Trailer implements Parcelable {

    private String key;
    private String name;
    private String site;
    private String type;

    public Trailer(String key, String name, String site, String type) {
        this.key = key;
        this.name = name;
        this.site = site;
        this.type = type;
    }

    private Trailer(Parcel parcel) {
        key = parcel.readString();
        name = parcel.readString();
        site = parcel.readString();
        type = parcel.readString();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel parcel) {
            return new Trailer(parcel);
        }

        @Override
        public Trailer[] newArray(int i) {
            return new Trailer[0];
        }
    };
}
