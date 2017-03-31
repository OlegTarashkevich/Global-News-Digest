package com.mvp.mobexs.mvp_test.mvp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mvp.mobexs.mvp_test.R;
import com.mvp.mobexs.mvp_test.mvp.model.Article;
import com.mvp.mobexs.mvp_test.mvp.model.Source;
import com.mvp.mobexs.mvp_test.mvp.presenter.news.ArticlePresenter;
import com.mvp.mobexs.mvp_test.mvp.presenter.LoginPresenter;
import com.mvp.mobexs.mvp_test.mvp.presenter.news.SourcePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_login_username) EditText editUser;
    @BindView(R.id.et_login_password) EditText editPass;
    @BindView(R.id.btn_login_login) Button btnLogin;
    @BindView(R.id.btn_login_clear) Button btnClear;
    @BindView(R.id.progress_login) ProgressBar progressBar;

    @Inject LoginPresenter loginPresenter;
    @Inject ArticlePresenter articlePresenter;
    @Inject SourcePresenter sourcePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);

        editUser.setText("mvp");
        editPass.setText("mvp");

        loginPresenter.setView(loginView);
        articlePresenter.setView(new ArticleView());
        sourcePresenter.setView(new SourceView());

        loginPresenter.setProgressBarVisibility(View.GONE);
    }

    @OnClick({R.id.btn_login_login, R.id.btn_login_clear, R.id.btn_load_sources, R.id.btn_load_article})
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_login_clear:
                loginPresenter.clear();
                break;

            case R.id.btn_login_login:
                loginPresenter.setProgressBarVisibility(View.VISIBLE);
                btnLogin.setEnabled(false);
                btnClear.setEnabled(false);
                loginPresenter.doLogin(editUser.getText().toString(), editPass.getText().toString());
                break;

            case R.id.btn_load_sources:
                sourcePresenter.loadData();
                break;

            case R.id.btn_load_article:
                articlePresenter.loadData();
                break;
        }
    }

    private ILoginView loginView = new ILoginView() {
        @Override
        public void onClearText() {
            editUser.setText(null);
            editPass.setText(null);
        }

        @Override
        public void onLoginResult(boolean validate) {
            loginPresenter.setProgressBarVisibility(View.GONE);
            btnLogin.setEnabled(true);
            btnClear.setEnabled(true);
            String message = validate ? "Login Success" : "Login Fail";
            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSetProgressBarVisibility(int visibility) {
            progressBar.setVisibility(visibility);
        }
    };

    private class ArticleView implements INewsView<Article> {
        @Override
        public void onStartLoading() {

        }

        @Override
        public void onFailure(String message) {

        }

        @Override
        public void onLoaded(Article article) {

        }
    }

    private class SourceView implements INewsView<Source> {
        @Override
        public void onStartLoading() {

        }

        @Override
        public void onFailure(String message) {

        }

        @Override
        public void onLoaded(Source source) {

        }
    }
}
