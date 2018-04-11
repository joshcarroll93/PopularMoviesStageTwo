package popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.R;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data.Review;

/**
 * Created by Josh on 29/03/2018.
 */

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.ReviewViewHolder> {

    private List<Review> mReviews;
    private Context mContext;

    public MovieReviewsAdapter(List<Review> reviews, Context context) {
        mReviews = reviews;
        mContext = context;
    }

    public void addReviews(List<Review> newReviews){

        if(mReviews != null){
            mReviews.clear();
            mReviews.addAll(newReviews);
        }
        notifyDataSetChanged();
    }
    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReviewViewHolder(LayoutInflater.from(mContext).inflate(R.layout.review_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.author.setText(mReviews.get(position).getAuthor());
        holder.content.setText(mReviews.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{

        private TextView author;
        private TextView content;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.tv_author);
            content = itemView.findViewById(R.id.tv_content);
        }
    }
}
