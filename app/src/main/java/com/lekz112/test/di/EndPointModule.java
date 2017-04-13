package com.lekz112.test.di;

import com.lekz112.test.di.util.ApplicationScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;

@Module
public class EndPointModule {

    public static final String API_ENDPOINT = "APIEndpoint";

    @ApplicationScope
    @Provides
    @Named(API_ENDPOINT)
    public HttpUrl httpUrl() {
        // TODO: get from BuildConfig
        return HttpUrl.parse("http://localhost/");
    }

}
