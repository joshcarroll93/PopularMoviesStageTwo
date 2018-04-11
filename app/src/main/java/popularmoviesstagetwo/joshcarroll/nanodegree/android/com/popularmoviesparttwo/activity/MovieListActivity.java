package popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.R;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.adapter.MovieAdapter;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.adapter.ViewPagerAdapter;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.backgroundtask.MovieListQuery;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.data.Movie;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.fragment.MovieTabLayoutFragment;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.utils.JsonUtils;
import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.utils.NetworkUtils;

public class MovieListActivity extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        getSupportActionBar().hide();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

//        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
//        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

    }

//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater menuInflater = getMenuInflater();
//
//        menuInflater.inflate(R.menu.filter_search_menu, menu);
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        if(id == R.id.popular){
//
//            Toast.makeText(getApplicationContext(),"Popular", Toast.LENGTH_LONG).show();
//            pageNumber = 1;
//            makeMovieSearchQuery(getString(R.string.popular_api));
//            mMovieAdapter.clearList();
//            return true;
//        }
//        if(id == R.id.top_rated){
//
//            Toast.makeText(getApplicationContext(), "Top Rated", Toast.LENGTH_LONG).show();
//            pageNumber = 1;
//            makeMovieSearchQuery(getString(R.string.top_rated_api));
//            mMovieAdapter.clearList();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
