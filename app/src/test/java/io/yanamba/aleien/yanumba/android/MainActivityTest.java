package io.yanamba.aleien.yanumba.android;

import android.content.Intent;
import android.widget.ToggleButton;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import io.yanamba.aleien.yanumba.BuildConfig;
import io.yanamba.aleien.yanumba.R;
import io.yanamba.aleien.yanumba.di.TestModule;

import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, application = TestApp.class)
public class MainActivityTest {
    private ActivityController<MainActivity> controller;

    // TODO: Я вижу, какой код нужно вынести в @Before, но я не знаю как настраивать мок для HelperUtils ):
    @Before
    public void setupActivity() {
        controller = Robolectric.buildActivity(MainActivity.class);
    }

    @After
    public void tearDown() {
        Shadows.shadowOf(RuntimeEnvironment.application).clearStartedServices();
    }


    @Test
    public void startingActivity() {
        // if my service is not running toggle button is unchecked
        MainActivity activity = controller.create().get();
        ToggleButton button = (ToggleButton) activity.findViewById(R.id.toggleButton);

        assertTrue(!button.isChecked());
    }

    @Test
    public void startingActivityServiceRunning() {
        // if service up toggle button checked
        TestModule.serviceRunning = true;
        MainActivity activity = controller.create().get();
        ToggleButton button = (ToggleButton) activity.findViewById(R.id.toggleButton);

        assertTrue(button.isChecked());
    }

    @Test
    public void startingService() {
        // standard situation
        TestModule.serviceRunning = false;
        MainActivity activity = controller.create().get();
        ToggleButton button = (ToggleButton) activity.findViewById(R.id.toggleButton);
        button.setChecked(true);
        Intent service = new Intent(activity, CallsMonitoringService.class);

        Assert.assertEquals(Shadows.shadowOf(RuntimeEnvironment.application).peekNextStartedService().getComponent(), service.getComponent());
    }

    @Test
    public void checkingButtonServiceRunning() {
        // don't start service
        TestModule.serviceRunning = true;
        MainActivity activity = controller.create().get();
        ToggleButton button = (ToggleButton) activity.findViewById(R.id.toggleButton);
        button.setChecked(true);

        Assert.assertEquals(Shadows.shadowOf(RuntimeEnvironment.application).peekNextStartedService(), null);
    }

    @Test
    public void uncheckingButtonServiceRunning() {
        // stopService
        TestModule.serviceRunning = true;
        Intent service = new Intent(RuntimeEnvironment.application, CallsMonitoringService.class);
        RuntimeEnvironment.application.startService(service);
        MainActivity activity = controller.create().get();
        ToggleButton button = (ToggleButton) activity.findViewById(R.id.toggleButton);
        button.setChecked(false);

        Assert.assertEquals(Shadows.shadowOf(RuntimeEnvironment.application).getNextStoppedService().getComponent(), service.getComponent());
    }

    @Test
    public void uncheckingButtonServiceStopped() {
        // don't crash
        TestModule.serviceRunning = false;
        MainActivity activity = controller.create().get();
        ToggleButton button = (ToggleButton) activity.findViewById(R.id.toggleButton);
        button.setChecked(false);

        Assert.assertEquals(Shadows.shadowOf(RuntimeEnvironment.application).getNextStoppedService(), null);

    }
}