package com.claxtastic.newsgateway;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.squareup.picasso.Picasso;
import java.util.Locale;

public class ArticleFragment extends Fragment {
    private static final String TAG = "ArticleFragment";
    public ArticleFragment() {}

    public static ArticleFragment newInstance(Article article, int index, int max) {
        ArticleFragment articleFragment = new ArticleFragment();
        Bundle bundle = new Bundle(1);

        bundle.putSerializable("ARTICLE_DATA", article);
        bundle.putSerializable("INDEX", index);
        bundle.putSerializable("TOTAL_COUNT", max);

        articleFragment.setArguments(bundle);
        return articleFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentLayout = inflater.inflate(R.layout.fragment_article, container, false);

        if (getArguments() == null)
            return fragmentLayout;

        final Article currentArticle = (Article) getArguments().getSerializable("ARTICLE_DATA");

        if (currentArticle == null)
            return fragmentLayout;

        int index = getArguments().getInt("INDEX");
        int total = getArguments().getInt("TOTAL_COUNT");

        TextView headlineView = fragmentLayout.findViewById(R.id.textview_headline);
        TextView dateView = fragmentLayout.findViewById(R.id.textview_date);
        TextView authorView = fragmentLayout.findViewById(R.id.tetview_author);
        ImageView imageView = fragmentLayout.findViewById(R.id.imageview_image);
        final TextView articleTextView = fragmentLayout.findViewById(R.id.textview_article_text);
        TextView countView = fragmentLayout.findViewById(R.id.textview_count);

        headlineView.setText(currentArticle.getTitle());
        dateView.setText(currentArticle.getPublishedAt());

        if (currentArticle.getAuthor() == null)
            authorView.setText("");
        else
            authorView.setText(currentArticle.getAuthor());

        if (currentArticle.getUrlToImage() == null) {
            Picasso.get().load(R.drawable.noimage).into(imageView);
        } else {
            Picasso.get()
                    .load(currentArticle.getUrlToImage())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.brokenimage)
                    .into(imageView);
        }
        articleTextView.setText(currentArticle.getDescription());
        countView.setText(String.format(Locale.US, "%d of %d", index, total));

        /* Set onClick for all the article data views */
        headlineView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onInfoClick(currentArticle.getUrl()); }});
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onInfoClick(currentArticle.getUrl()); }});
        authorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onInfoClick(currentArticle.getUrl()); }});
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onInfoClick(currentArticle.getUrl()); }});
        articleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onInfoClick(currentArticle.getUrl()); }});

        return fragmentLayout;
    }

    public void onInfoClick(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse(url));
        startActivity(browserIntent);
    }
}
