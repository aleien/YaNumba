package io.yanamba.aleien.yanumba.di;

import javax.inject.Singleton;

import dagger.Component;
import io.yanamba.aleien.yanumba.CallsMonitoringService;
import io.yanamba.aleien.yanumba.ui.MainActivity;
import io.yanamba.aleien.yanumba.utils.WindowManagerUtils;

/**
 * Created by aleien on 07.08.16.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(MainActivity mainActivity);

    void inject(CallsMonitoringService callsMonitoringService);

    void inject(WindowManagerUtils windowManagerUtils);
}
