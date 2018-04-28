package popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.fragment.MovieTabLayoutFragment;

/**
 * Created by Josh on 09/04/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return MovieTabLayoutFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        switch (position) {
            case 0:
                MovieTabLayoutFragment popularMoviesFragment = (MovieTabLayoutFragment) createdFragment;
                break;
            case 1:
                MovieTabLayoutFragment topRatedMoviesFragment = (MovieTabLayoutFragment) createdFragment;
                break;
            case 2:
                MovieTabLayoutFragment favouriteMoviesFragment = (MovieTabLayoutFragment) createdFragment;
        }
        return createdFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Popular";

            case 1:
                return "Top Rated";

            case 2:
                return "Favourites";
        }
        return null;
    }
}