package io.github.grooters.idles.net;

import android.content.Context;
import java.io.File;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofiter {

    public static <T>T getApi(Context context, Class<T> cls, String url, long maxSize){

        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(new Cache(new File(context.getFilesDir().getPath()),maxSize)).build();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(cls);
    }

    public static <T>T getApi(Class<T> cls, String url){

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new LoginIntercept()).build();

        return new Retrofit.Builder().client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(cls);

    }

    public static <T>T getApi(Class<T> cls, String url, int secondTimeOut){

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(secondTimeOut * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(secondTimeOut * 1000, TimeUnit.MILLISECONDS)
                .writeTimeout(secondTimeOut * 1000,  TimeUnit.MILLISECONDS)
                .build();

        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(cls);

    }
}
