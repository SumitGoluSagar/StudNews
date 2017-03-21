package org.mistu.android.studnews.Home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import org.mistu.android.studnews.App;
import org.mistu.android.studnews.R;
import org.mistu.android.studnews.Utils.News;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFragment.OnNewsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {

    private static final String ARG_PARAM1 = "urlToHit";
    private static final String ARG_PARAM2 = "category";

    private String urlToHit;
    private String category;

    private OnNewsFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<News> newsList;
    private Context context;
    private boolean isFabShown = true;
    private ObjectMapper mapper;

   /* private int[] imgList = {R.drawable.physical1, R.drawable.scroll_image3, R.drawable.jee_prep1, R.drawable.jee_prep2,
            R.drawable.jee_prep6, R.drawable.jee_prep5, R.drawable.jee_prep4, R.drawable.jee_prep3,
            R.drawable.inorganic, R.drawable.physical1};*/

    public NewsFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param urlToHit Parameter 1.
     * @param category Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    public static NewsFragment newInstance(String urlToHit, String category) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, urlToHit);
        args.putString(ARG_PARAM2, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            urlToHit = getArguments().getString(ARG_PARAM1);
            category = getArguments().getString(ARG_PARAM2);
        }
        newsList = new ArrayList<>();
        mapper = new ObjectMapper();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = inflater.getContext();
        View fragView = inflater.inflate(R.layout.fragment_news, container, false);
        adapter = new NewsAdapter(newsList, mListener);

        recyclerView = (RecyclerView) fragView.findViewById(R.id.news_frag_rv);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if (dy > 0 || dy<0 && isFabShown){
                    isFabShown = !isFabShown;
                    mListener.showFab(false);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mListener.showFab(true);
                    isFabShown = !isFabShown;
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        recyclerView.setAdapter(adapter);
        loadNewsDataFromUrl(urlToHit);
        return fragView;
    }

    public void loadNewsDataFromUrl(String urlToHit) {
        /*Calendar calendar = Calendar.getInstance();
        long serverDate = App.getInstance().getRequestQueue().getCache().get(urlToHit).serverDate;
        Log.d("SERVER_DATE ", serverDate + "");
        if(getMinutesDifference(serverDate, calendar.getTimeInMillis()) >=30){
            Log.d("DIFFERENCE IN TIME", getMinutesDifference(serverDate, calendar.getTimeInMillis()) + "");
            App.getInstance().getRequestQueue().getCache().invalidate(urlToHit, true);
        }

        Cache cache = App.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(urlToHit);
        if(entry != null){
            Log.d("CACHE_HIT ", urlToHit);
            try {
                String data = new String(entry.data, "UTF-8");
                // handle data, like converting it to xml, json, bitmap etc.,
                newsList = mapper.readValue(data, new TypeReference<ArrayList<News>>() {});
                Log.d("NEWS_LIST", newsList.toString());
                adapter.setNewsList(newsList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {*/
            // Cached response doesn't exists. Make network call here
            Log.d("CACHE_MISS ", urlToHit);
            JsonArrayRequest req = new JsonArrayRequest(urlToHit,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("Volley Response", response.toString());
                            try {
                                newsList = mapper.readValue(response.toString(), new TypeReference<ArrayList<News>>() {});
                                Log.d("NEWS_LIST", newsList.toString());
                                adapter.setNewsList(newsList);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley Error", "Error: " + error.getMessage());
                }
            });
            // Adding request to request queue
            App.getInstance().addToRequestQueue(req);
        // }

    }

    public void setSearchResult(String searchedText){
        ArrayList<News> searchResult = new ArrayList<>();
        for(News news : newsList){
            if (news.getHeading().toLowerCase().contains(searchedText)) {
                searchResult.add(news);
            }
            else if (news.getNewsText().toLowerCase().contains(searchedText)) {
                searchResult.add(news);
            }
            else if (!news.getTag1().isEmpty() && news.getTag1().toLowerCase().contains(searchedText)) {
                searchResult.add(news);
            }
        }
        adapter.setNewsList(searchResult);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNewsFragmentInteractionListener) {
            mListener = (OnNewsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNewsFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnNewsFragmentInteractionListener {
        void onNewsFragmentInteractionListener(List<News> newsList, int position, View view);
        void showFab(boolean toShow);
    }

}
