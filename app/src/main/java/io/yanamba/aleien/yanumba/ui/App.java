package io.yanamba.aleien.yanumba.ui;

import android.app.Application;
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
}
