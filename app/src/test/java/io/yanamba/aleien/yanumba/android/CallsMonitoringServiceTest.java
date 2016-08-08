package io.yanamba.aleien.yanumba.android;

import android.content.Intent;
import android.telephony.TelephonyManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowTelephonyManager;
import org.robolectric.util.ServiceController;

import io.yanamba.aleien.yanumba.BuildConfig;

import static android.content.Context.TELEPHONY_SERVICE;
import static org.mockito.Mockito.times;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23, application = TestApp.class)
public class CallsMonitoringServiceTest {

    @Test
    public void searchSucceeded() {
        // if ok, show results on screen
        ServiceController<CallsMonitoringService> controller = Robolectric.buildService(CallsMonitoringService.class);
        CallsMonitoringService service = controller.create().get();

        TelephonyManager tim = (TelephonyManager) RuntimeEnvironment.application.getSystemService(TELEPHONY_SERVICE);
        ShadowTelephonyManager telephonyManager = Shadows.shadowOf(tim);
        telephonyManager.getListener().onCallStateChanged(TelephonyManager.CALL_STATE_RINGING, "+11111111111");

        // TODO: Дальше нужно разобраться с тестированием rx
        // Next time, folks!
        Mockito.verify(service.searchResultsWindow, times(1)).setText(Mockito.any());

    }

    @Test
    public void searchFailed() {
        // if error contains "Ой!", show error message on screen


        //todo: use proxy list to retry
    }

    @Test
    public void connectionError() {
        // if anything else show standard error message
    }

}