package popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import popularmoviesstagetwo.joshcarroll.nanodegree.android.com.popularmoviesparttwo.fragment.MovieTabLayoutFragment;

/**
 * Created by Josh on 09/04/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private MovieTabLayoutFragment popularMoviesFragment;
    private MovieTabLayoutFragment topRatedMoviesFragment;
    private MovieTabLayoutFragment favouriteMoviesFragment;

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

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        switch (position){
            case 0 :
                popularMoviesFragment = (MovieTabLayoutFragment) createdFragment;
                break;
            case 1 :
                topRatedMoviesFragment = (MovieTabLayoutFragment) createdFragment;
                break;
            case 2 :
                favouriteMoviesFragment = (MovieTabLayoutFragment) createdFragment;
        }
        return createdFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0 :
                return "Popular";

            case 1 :
                return "Top Rated";

            case 2:
                return "Favourites";
        }
        return null;
    }
}