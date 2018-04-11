package popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.R;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data.MovieDetail;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data.Trailer;

/**
 * Created by Josh on 28/03/2018.
 */

public class MovieTrailersAdapter extends RecyclerView.Adapter<MovieTrailersAdapter.MovieTrailerViewHolder>{

    private List<Trailer> mTrailers;
    private Context mContext;

    public MovieTrailersAdapter(List<Trailer> trailers, Context context){
        mTrailers = trailers;
        mContext = context;
    }

    public void addTrailers(List<Trailer> newTrailers){
        mTrailers.clear();
        mTrailers.addAll(newTrailers);
        notifyDataSetChanged();
    }
    @Override
    public MovieTrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieTrailerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.trailer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final MovieTrailerViewHolder holder, final int position) {

        final String youtubeBaseUrl = "http://www.youtube.com/watch?v=";
        String thumbnailBaseUrl = "https://img.youtube.com/vi/";

        final String key = mTrailers.get(position).getKey();

        Picasso.with(mContext).load(thumbnailBaseUrl + key + "/hqdefault.jpg").into(holder.playButtonImage);


        holder.playButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(youtubeBaseUrl + key));

                try {
                    mContext.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    mContext.startActivity(webIntent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }


    class MovieTrailerViewHolder extends RecyclerView.ViewHolder {

        private ImageView playButtonImage;

        private MovieTrailerViewHolder(View itemView) {
            super(itemView);
            playButtonImage = itemView.findViewById(R.id.id_play_img);
        }
    }
}
