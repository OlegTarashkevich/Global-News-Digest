package com.sonejka.news.di.module;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.sonejka.news.di.annotation.ForApplication;
import com.sonejka.news.network.API;
import com.sonejka.news.network.MockNetworkService;
import com.sonejka.news.network.NetworkService;
import com.sonejka.news.network.NetworkServicesContainer;
import com.sonejka.news.network.RxErrorHandlingCallAdapterFactory;
import com.sonejka.news.util.DataUtil;
import com.sonejka.news.util.Logger;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Oleg Tarashkevich on 31/03/2017.
 */

@Module
public class NetworkModule {

    @Provides
    @ForApplication
    NetworkServicesContainer provideNetworkServicesContainer(@Named(GsonModule.IDENTITY) Gson gson, @Named(OkHttpClientModule.API_CLIENT) OkHttpClient httpClient, DataUtil dataUtil){
        return new NetworkServicesContainer(gson, httpClient, dataUtil);
    }

    @Provides
    @ForApplication
    Picasso providePicasso(@ForApplication Context context, @Named(OkHttpClientModule.PICASSO_CLIENT) OkHttpClient okHttpClient) {
        return new Picasso.Builder(context)
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        if (uri != null)
                            Logger.i("Picasso onImageLoadFailed: " + exception.toString() + "\n" + uri.toString());
                        else
                            Logger.i("Picasso onImageLoadFailed: " + exception.toString());
                    }
                })
                .downloader(new OkHttp3Downloader(okHttpClient)).executor(Executors.newSingleThreadExecutor())
                .build();
    }
}
