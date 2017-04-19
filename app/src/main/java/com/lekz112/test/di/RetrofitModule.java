package com.lekz112.test.di;

import com.google.gson.Gson;
import com.lekz112.test.di.util.ApplicationScope;
import com.lekz112.test.service.network.APIEndpoint;
import com.lekz112.test.service.network.NetworkDelayInterceptor;
import com.lekz112.test.service.network.StubAPIEndpoint;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.lekz112.test.di.EndPointModule.API_ENDPOINT;

@Module
public class RetrofitModule {

    @ApplicationScope
    @Provides
    public APIEndpoint githubEndpoint(@Named(API_ENDPOINT) HttpUrl httpUrl, Retrofit.Builder retrofit) {
        return new StubAPIEndpoint();
        //return retrofit.baseUrl(httpUrl).build().create(APIEndpoint.class);
    }

    @Provides
    public Retrofit.Builder retrofit(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson));
    }

    @Provides
    public Gson gson() {
        return new Gson();
    }

    @Provides
    public OkHttpClient client(HttpLoggingInterceptor httpLoggingInterceptor,
                               NetworkDelayInterceptor networkDelayInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(networkDelayInterceptor)
                .build();
    }

    @Provides
    public NetworkDelayInterceptor networkDelayInterceptor() {
        return new NetworkDelayInterceptor(0, TimeUnit.SECONDS);
    }

    @Provides
    public HttpLoggingInterceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }
}
