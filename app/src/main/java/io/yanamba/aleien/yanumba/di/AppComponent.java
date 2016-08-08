package io.yanamba.aleien.yanumba.di;

import javax.inject.Singleton;

import dagger.Component;
import io.yanamba.aleien.yanumba.android.CallsMonitoringService;
import io.yanamba.aleien.yanumba.android.MainActivity;
import io.yanamba.aleien.yanumba.utils.WindowManagerHelper;

/**
 * Created by aleien on 07.08.16.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(MainActivity mainActivity);

    void inject(CallsMonitoringService callsMonitoringService);

    void inject(WindowManagerHelper windowManagerHelper);
}
