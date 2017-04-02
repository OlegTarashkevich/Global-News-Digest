package com.sonejka.news.mvp.view.widget;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.sonejka.news.R;
import com.sonejka.news.mvp.model.Source;
import com.sonejka.news.mvp.view.activity.BaseActivity;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oleg Tarashkevich on 01.04.17.
 */

public class SourceCardView extends CardView {

    @BindView(R.id.source_logo_view) ImageView logoImageView;
    @BindView(R.id.source_name_text_view) TextView nameTextView;
    @BindView(R.id.source_description_text_view) TextView descriptionTextView;
    @BindView(R.id.source_url_text_view) TextView urlTextView;

    @BindDimen(R.dimen.z_size) int logoSize;

    @Inject Picasso picasso;

    public SourceCardView(Context context) {
        this(context, null);
    }

    public SourceCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SourceCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.cardview_source, this);
        ButterKnife.bind(this);

        if (isInEditMode()) return;
        BaseActivity activity = (BaseActivity) getContext();
        activity.getActivityComponent().inject(this);
    }

    public void setSource(Source.Entry entry) {
        if (entry == null) {
            setVisibility(INVISIBLE);
        } else {
            setVisibility(VISIBLE);

            nameTextView.setText(entry.getInfo());
            descriptionTextView.setText(entry.getDescription());
            urlTextView.setText(entry.getUrl());

            picasso.load(entry.getUrlsToLogos().getMedium())
                    .centerInside()
                    .resize(logoSize, logoSize)
                    .onlyScaleDown()
                    .into(logoImageView);
        }
    }
}
