package io.yanamba.aleien.yanumba.android;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.ObbInfo;
import android.support.annotation.NonNull;

import io.yanamba.aleien.yanumba.di.AppComponent;
import io.yanamba.aleien.yanumba.di.AppModule;
import io.yanamba.aleien.yanumba.di.DaggerAppComponent;

public class App extends Application {
    @NonNull private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent component() {
        return appComponent;
    }

    @Override
    public ComponentName startService(Intent service) {
        Object o = new Object();
        return super.startService(service);
    }
}
