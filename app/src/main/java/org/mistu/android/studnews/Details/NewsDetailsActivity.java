package org.mistu.android.studnews.Details;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import org.mistu.android.studnews.AppConstants;
import org.mistu.android.studnews.R;
import org.mistu.android.studnews.Utils.Data;
import org.mistu.android.studnews.Utils.News;
import org.mistu.android.studnews.Visual.ZoomOutPageTransformer;

import java.util.List;

public class NewsDetailsActivity extends FragmentActivity
        implements NewsDetailsFragment.OnNewsDetailsFragmentInteractionListener {

    private List<News> newsList;
    private int clickedPosition;
    private int screenCount;
    private ViewPager viewPager;
    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        if ( savedInstanceState != null ) {
            return;
        }

        Bundle args = getIntent().getExtras();
        if (args != null) {
            newsList = (List<News>) args.getSerializable(AppConstants.NEWS_LIST);
            clickedPosition = args.getInt(AppConstants.INDEX, 0);
        }

        viewPager = (ViewPager) findViewById(R.id.news_detail_activity_view_pager);
        adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.setCurrentItem(clickedPosition);

        //set view pager to start from clickedPosition
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            News news = newsList.get(position);
            return NewsDetailsFragment.newInstance(news.getImageUrl(), news.getHeading(), news.getNewsText(), news.getRefUrl());
        }

        @Override
        public int getCount() {
            return newsList.size();
        }
    }
    

    @Override
    public void onNewsDetailsFragmentInteraction(Data data) {

    }

}
