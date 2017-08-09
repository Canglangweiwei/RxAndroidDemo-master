package com.ysr.myrrr.httputils;

import com.ysr.myrrr.ZoneApplication;

import rx.Subscriber;

@SuppressWarnings("ALL")
public class HttpPost {

    private HttpRetrofit httpRetrofit;

    public HttpPost() {
        httpRetrofit = ZoneApplication.get().httpRetrofit;
    }

    public void getTest(Subscriber<NewsBean> subscriber) {
        httpRetrofit.httpSubscribe(httpRetrofit.getAppService().getTest(), subscriber);
    }
}
