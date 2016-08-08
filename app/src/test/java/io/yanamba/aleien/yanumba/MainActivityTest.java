package io.yanamba.aleien.yanumba;

import android.content.Intent;
import android.widget.ToggleButton;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;
import org.robolectric.util.ServiceController;

import io.yanamba.aleien.yanumba.utils.HelperUtils;

import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainActivityTest {

    @Test
    public void startingActivity() {
        // if my service is not running toggle button is unchecked
        ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class);
        MainActivity activity = controller.create().get();
        ToggleButton button = (ToggleButton) activity.findViewById(R.id.toggleButton);

        assertTrue(!button.isChecked());
    }

    @Test
    public void startingActivityServiceRunning() {
        // if service up toggle button checked
        ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class);

        MainActivity activity = controller.get();

        HelperUtils testHelper = Mockito.mock(HelperUtils.class);
        Mockito.when(testHelper.isMyServiceRunning(CallsMonitoringService.class)).thenReturn(true);
        activity.helper = testHelper;

        controller.create();
        ToggleButton button = (ToggleButton) activity.findViewById(R.id.toggleButton);

        //TODO: Разобраться, как замокать HelperUtils
        // Подменить модуль?
//        assertTrue(button.isChecked());

    }

    @Test
    public void startingService() {
        // standard situation
    }

    @Test
    public void checkingButtonServiceRunning() {
        // don't start service
    }

    @Test
    public void uncheckingButtonServiceRunning() {
        // stopService
    }

    @Test
    public void uncheckingButtonServiceStopper() {
        // don't crash
    }
}