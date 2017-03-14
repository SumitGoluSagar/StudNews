package com.example.android.studnews.Home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.studnews.R;
import com.example.android.studnews.Utils.Data;
import com.example.android.studnews.Utils.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kedee on 28/2/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

    private Context context;
    private List<News> newsList;
    private NewsFragment.OnNewsFragmentInteractionListener listener;


    public NewsAdapter(List<News> newsList, NewsFragment.OnNewsFragmentInteractionListener listener ) {
        this.newsList = newsList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_news_modified_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        News newsData = newsList.get(position);
        holder.newsCategory.setText(newsData.getTag1());
        holder.newsTagLine.setText(newsData.getHeading());
        Glide.with(context)
                .load(newsData.getImageUrl())
                .fitCenter()
                .into(holder.newsThumbnail);

    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView newsThumbnail;
        TextView newsCategory;
        TextView newsTagLine;
        View viewItem;

        public ViewHolder(View itemView) {
            super(itemView);
            viewItem = itemView;
            newsThumbnail = (ImageView) itemView.findViewById(R.id.news_thumbnail);
            newsCategory = (TextView) itemView.findViewById(R.id.news_category);
            newsTagLine = (TextView) itemView.findViewById(R.id.news_tagLine);

            viewItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onNewsFragmentInteractionListener(newsList, getAdapterPosition(), view);
        }
    }
}
