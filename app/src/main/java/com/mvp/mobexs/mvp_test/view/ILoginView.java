package com.mvp.mobexs.mvp_test.view;

/**
 * Created by Oleg Tarashkevich on 27/03/2017.
 */

public interface ILoginView {

    void onClearText();
    void onLoginResult(boolean validate);
    void onSetProgressBarVisibility(int visibility);
}
