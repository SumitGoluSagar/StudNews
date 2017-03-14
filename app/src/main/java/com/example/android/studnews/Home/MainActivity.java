package com.example.android.studnews.Home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.android.studnews.App;
import com.example.android.studnews.AppConstants;
import com.example.android.studnews.Details.NewsDetailsActivity;
import com.example.android.studnews.R;
import com.example.android.studnews.Utils.Data;
import com.example.android.studnews.Utils.DataUtils;
import com.example.android.studnews.Utils.News;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NewsFragment.OnNewsFragmentInteractionListener, CategoryFragment.OnCategoryFragmentInteractionListener {

    private int[] imgList = {R.drawable.physical1, R.drawable.scroll_image3, R.drawable.jee_prep1, R.drawable.jee_prep2,
            R.drawable.jee_prep6, R.drawable.jee_prep5, R.drawable.jee_prep4, R.drawable.jee_prep3,
            R.drawable.inorganic, R.drawable.physical1};

    private FloatingActionButton fab;
    private CategoryFragment categoryFragment;
    private FragmentManager fragmentManager;

    private NewsFragment newsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager= getSupportFragmentManager();
        categoryFragment = CategoryFragment.newInstance("dfv", "d d");

        if (savedInstanceState != null) {
            return;
        }


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryFragment.show(fragmentManager, "Dialog Fragment");
            }
        });

        setNewsFragment();
    }

    private void setNewsFragment() {
        newsFragment = NewsFragment.newInstance(AppConstants.mainNewsUrl, "Trending");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.news_frag_container, newsFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNewsFragmentInteractionListener(List<News> newsList, int position, View view) {
        Intent intent = new Intent(this, NewsDetailsActivity.class);

        Bundle args = new Bundle();
        args.putSerializable(AppConstants.NEWS_LIST, (ArrayList)newsList);
        args.putInt(AppConstants.INDEX, position);
        intent.putExtras(args);

        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent, bundle);
    }

    @Override
    public void showFab(boolean toShow) {
        if(toShow){
            fab.show();
        } else {
            fab.hide();
        }
    }

    @Override
    public void onCategoryFragmentInteraction(String category) {
        Toast.makeText(this, "Category clicked is " + category, Toast.LENGTH_SHORT ).show();
        categoryFragment.dismiss();
        loadNewsByCategory(category);
    }

    private void loadNewsByCategory(String category){
        newsFragment.loadNewsDataFromUrl(AppConstants.categoryNewsUrl + category);
    }



}
