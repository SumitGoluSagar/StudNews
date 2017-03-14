package com.example.android.studnews.Home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.studnews.R;

import java.util.ArrayList;

/**
 * Created by kedee on 11/3/17.
 */

public class CategoryFragmentAdapter extends RecyclerView.Adapter<CategoryFragmentAdapter.ViewHolder> {

    private ArrayList<String> categoryList;
    private CategoryFragment.OnCategoryFragmentInteractionListener listener;
    private Context context;

    public CategoryFragmentAdapter(ArrayList<String> categoryList,
                                   CategoryFragment.OnCategoryFragmentInteractionListener listener) {
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_category_list, parent, false);
        return new CategoryFragmentAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.category.setText(categoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView icon;
        TextView category;
        View viewItem;

        public ViewHolder(View itemView) {
            super(itemView);
            viewItem = itemView;
            icon = (ImageView) itemView.findViewById(R.id.item_category_iv);
            category = (TextView) itemView.findViewById(R.id.item_category_tv);
            viewItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onCategoryFragmentInteraction(categoryList.get(getAdapterPosition()));
        }
    }
}
