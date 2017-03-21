package org.mistu.android.studnews.Details;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import org.mistu.android.studnews.R;
import org.mistu.android.studnews.Utils.Data;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsDetailsFragment.OnNewsDetailsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsDetailsFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_IMAGE_URL = "param1";
    private static final String ARG_HEADING = "param2";
    private static final String ARG_CONTENT = "param3";
    private static final String ARG_REF_URL = "param4";

    private String imageUrl;
    private String heading;
    private String content;
    private String refUrl;
    private OnNewsDetailsFragmentInteractionListener mListener;

    private ImageView newsImage;
    private TextView headingTextView;
    private TextView contentTextView;
    private TextView refUrlTextView;

    public NewsDetailsFragment() {
        // Required empty public constructor
    }

    public static NewsDetailsFragment newInstance(String imageUrl, String heading, String content, String refUrl) {
        NewsDetailsFragment fragment = new NewsDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IMAGE_URL, imageUrl);
        args.putString(ARG_HEADING, heading);
        args.putString(ARG_CONTENT, content);
        args.putString(ARG_REF_URL, refUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageUrl = getArguments().getString(ARG_IMAGE_URL);
            heading = getArguments().getString(ARG_HEADING);
            content = getArguments().getString(ARG_CONTENT);
            refUrl = getArguments().getString(ARG_REF_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragView = inflater.inflate(R.layout.fragment_news_details, container, false);
        newsImage = (ImageView) fragView.findViewById(R.id.frag_news_detail_iv);
        headingTextView = (TextView) fragView.findViewById(R.id.frag_news_detail_tag_line);
        contentTextView = (TextView) fragView.findViewById(R.id.frag_news_detail_content);
        refUrlTextView = (TextView) fragView.findViewById(R.id.frag_news_detail_read_more);

        if(refUrl==null || refUrl.isEmpty()){
            refUrlTextView.setVisibility(View.INVISIBLE);
        } else {
            refUrlTextView.setVisibility(View.VISIBLE);
            refUrlTextView.setOnClickListener(this);
        }


        Glide.with(inflater.getContext())
                .load(imageUrl)
                .fitCenter()
                .crossFade()
                .into(newsImage);

        headingTextView.setText(heading);
        contentTextView.setText(content);

        return fragView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNewsDetailsFragmentInteractionListener) {
            mListener = (OnNewsDetailsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNewsDetailsFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == refUrlTextView.getId()) {
            Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(refUrl));
            startActivity(intent);
        }
    }

    public interface OnNewsDetailsFragmentInteractionListener {
        void onNewsDetailsFragmentInteraction(Data data);
    }
}
