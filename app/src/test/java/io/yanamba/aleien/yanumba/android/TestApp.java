package io.yanamba.aleien.yanumba.android;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Component;
import io.yanamba.aleien.yanumba.di.AppComponent;
import io.yanamba.aleien.yanumba.di.DaggerAppComponent;
import io.yanamba.aleien.yanumba.di.TestModule;

/**
 * Created by aleien on 08.08.16.
 */

public class TestApp extends App {
    @Singleton
    @Component(modules = {TestModule.class})
    public interface TestApplicationComponent extends AppComponent {

    }

    @NonNull private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new TestModule(this))
                .build();
    }

    public AppComponent component() {
        return appComponent;
    }

}
