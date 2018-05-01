package popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.R;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.activity.MovieDetailActivity;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data.Movie;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.provider.MovieContract;

/**
 * Created by Josh on 27/03/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private String TAG = getClass().getSimpleName();
    private Context mContext;
    private List<Movie> mMovies;
    private int mFragmentNumber;

    public MovieAdapter(Context context, List<Movie> movies) {
        mContext = context;
        mMovies = movies;
        Picasso.with(mContext).setIndicatorsEnabled(false);
    }

    public void setFragNumber(int fragNumber){
        mFragmentNumber = fragNumber;
    }
    public void addItems(List<Movie> newMovies) {
        if (mMovies != null)
            mMovies.addAll(newMovies);

        this.notifyDataSetChanged();
    }

    public void clearList() {

        mMovies.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mMovies != null)
            return mMovies.size();

        return 0;
    }

    public void removeItem(int position) {

        mMovies.remove(position);
        this.notifyItemRemoved(position);
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

    public Movie getItem(int position) {
        return mMovies.get(position);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d(TAG, "Creating a view for the movie");
        return new MovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder, int position) {

        String MDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w780/";

        movieViewHolder.moviePoster.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.ic_launcher));
        Log.i(TAG, mMovies.get(position).getPosterPath());
        Log.i(TAG, MDB_IMAGE_BASE_URL + mMovies.get(position).getPosterPath());
        Picasso.with(mContext).load(MDB_IMAGE_BASE_URL + mMovies.get(position).getPosterPath())
                .resize(780, 1170)
                .centerCrop()
                .placeholder(R.drawable.ic_movie_black_48dp)
                .priority(Picasso.Priority.HIGH)
                .into(movieViewHolder.moviePoster);

        movieViewHolder.moviePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context viewContext = view.getContext();
                Intent intent = new Intent(viewContext, MovieDetailActivity.class);
                intent.putExtra("movie", mMovies.get(movieViewHolder.getAdapterPosition()));

                viewContext.startActivity(intent);
            }
        });

        movieViewHolder.moviePoster.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if(mFragmentNumber == 3){

                    confirmDelete(movieViewHolder.getAdapterPosition());
                    return true;
                }
                return false;
            }
        });
    }
    private void confirmDelete(final int id) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(mContext);
        }
        builder.setTitle("Delete Movie")
                .setMessage("Are you sure you want to delete this movie?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        Movie movie = getItem(id);
                        int movie_id = movie.getId();

                        String stringId = Integer.toString(movie_id);
                        Uri uri = MovieContract.MovieEntry.CONTENT_URI;
                        uri = uri.buildUpon().appendPath(stringId).build();

                        int i = mContext.getContentResolver().delete(uri, null, null);

                        removeItem(id);

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // restore item
                        notifyItemChanged(id);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    static class MovieViewHolder extends RecyclerView.ViewHolder  {

        private ImageView moviePoster;

        private MovieViewHolder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.image_view);
        }
    }
}
