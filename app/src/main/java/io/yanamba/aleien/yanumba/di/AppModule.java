package io.yanamba.aleien.yanumba.di;

import android.app.Application;
import android.content.Context;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.yanamba.aleien.yanumba.App;
import io.yanamba.aleien.yanumba.SearchApi;
import io.yanamba.aleien.yanumba.utils.HelperUtils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by aleien on 07.08.16.
 */

@Module
public class AppModule {
    private Context context;
    public static final String YANDEX_BASE_URL = "http://yandex.ru/";
    public static final String SEARCH_QUERY = "search/?text=";
    public static final String YA_BASE_URL = "http://ya.ru/";

    public AppModule(App app) {
        context = app.getApplicationContext();
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient(Interceptor userAgent) {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())
//                .addInterceptor(userAgent)
                .build();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(YANDEX_BASE_URL)
                .client(client)
                .build();
    }

    @Singleton
    @Provides
    public SearchApi provideSearchApi(Retrofit restClient) {
        return restClient.create(SearchApi.class);
    }

    @Singleton
    @Provides
    public Interceptor provideUserAgentInterceptor() {
        return chain -> {
            Request originalRequest = chain.request();
            Request requestWithUserAgent = originalRequest.newBuilder()
                    .header("User-Agent", "Android Chrome")
                    .build();
            return chain.proceed(requestWithUserAgent);
        };
    }

    @Provides
    public HelperUtils provideUtils(Context context) {
        return new HelperUtils(context);
    }
}
