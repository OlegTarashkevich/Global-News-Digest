package com.sonejka.news.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.sonejka.news.R;
import com.sonejka.news.mvp.model.NewsError;
import com.sonejka.news.network.RetrofitException;

/**
 * Created by Oleg Tarashkevich on 04.04.17.
 */

public final class ErrorUtil {

    public static String handleError(Context context, Throwable e, @StringRes int resId) {
        return handleError(context, e, context.getString(resId));
    }

    public static String handleError(Context context, Throwable e, String message) {

        if (e instanceof RetrofitException) {
            RetrofitException retrofitError = (RetrofitException) e;
            Logger.e(retrofitError.toString());
//            Crashlytics.log(retrofitError.toString());
        }

        boolean isNetworkError = e != null && e instanceof RetrofitException && ((RetrofitException) e).getKind() == RetrofitException.Kind.NETWORK;

        if (isNetworkError) {
            message = context.getString(R.string.error_network);
        } else {
            if (e instanceof RetrofitException) {
                try {
                    RetrofitException retrofitError = (RetrofitException) e;
                    NewsError newsError = retrofitError.getErrorBodyAs(NewsError.class);
                    if (newsError != null)
                        message = newsError.getMessage();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }

        if (TextUtil.isNotEmpty(message)) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//            Crashlytics.log("Error: " + message);
        }

//        Crashlytics.logException(e);
        Logger.e(e);

        return message;
    }
}
