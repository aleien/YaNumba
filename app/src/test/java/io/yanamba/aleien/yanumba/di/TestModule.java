package io.yanamba.aleien.yanumba.di;

import android.content.Context;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;
import io.yanamba.aleien.yanumba.android.CallsMonitoringService;
import io.yanamba.aleien.yanumba.android.App;
import io.yanamba.aleien.yanumba.utils.HelperUtils;

@Module
public class TestModule extends AppModule {
    // TODO: Нужно придумать, как настраивать мок HelperUtils, чтобы убрать это безобразие
    public static boolean serviceRunning;

    public TestModule(App app) {
        super(app);
    }

    @Provides
    public HelperUtils provideUtils(Context context) {
        HelperUtils helperUtils = Mockito.mock(HelperUtils.class);
        Mockito.when(helperUtils.isMyServiceRunning(CallsMonitoringService.class)).thenReturn(serviceRunning);
        return helperUtils;
    }
}
