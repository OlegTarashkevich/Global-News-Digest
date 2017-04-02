package com.sonejka.news.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.sonejka.news.R;
import com.sonejka.news.mvp.model.Article;
import com.sonejka.news.mvp.presenter.news.ArticlePresenter;
import com.sonejka.news.mvp.view.adapter.ArticleRecyclerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oleg Tarashkevich on 01.04.17.
 */

public class ArticlesFragment extends BaseFragment {

    @BindView(R.id.recycler_view) FastScrollRecyclerView recyclerView;

    @Inject ArticlePresenter articlePresenter;
    @Inject ArticleRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, rootView);
        getFragmentComponent().inject(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        articlePresenter.setView(new ArticleView());
        articlePresenter.loadData();
    }

    private class ArticleView implements INewsView<Article> {
        @Override
        public void onStartLoading() {

        }

        @Override
        public void onFailure(String message) {

        }

        @Override
        public void updateRecycleView(Article article) {
            adapter.setData(article.getArticles());
        }
    }
}
