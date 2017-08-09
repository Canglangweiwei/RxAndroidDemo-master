package com.ysr.myrrr.httputils;

import com.google.gson.Gson;
import com.ysr.myrrr.utils.LogUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@SuppressWarnings("ALL")
public class HttpRetrofit {

    public HttpRetrofit() {
        super();
    }

    /**
     * 设置调用OkHttpClient
     */
    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient().newBuilder()
                .addInterceptor(getHttpLoggingInterceptor())
                .retryOnConnectionFailure(true) //失败重连
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 设置线程订阅转换
     */
    public <T> void httpSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        observable.map(new Func1<T, T>() {

            @Override
            public T call(T t) {
                //在这里进行json转换并返回
                return t;
            }
        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 关联接口
     */
    public AppService getAppService() {
        return getRetrofit().create(AppService.class);
    }

    /**
     * 初始化Retrofit
     */
    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .client(getOkHttpClient())
                .baseUrl("http://c.m.163.com/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    /**
     * 日志打印
     */
    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {

            @Override
            public void log(String message) {
                LogUtils.e("结果打印：", message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
    }
}
