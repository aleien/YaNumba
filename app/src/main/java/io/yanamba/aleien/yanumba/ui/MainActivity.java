package io.yanamba.aleien.yanumba.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ToggleButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import io.yanamba.aleien.yanumba.CallsMonitoringService;
import io.yanamba.aleien.yanumba.R;
import io.yanamba.aleien.yanumba.utils.HelperUtils;
import timber.log.Timber;

// Главная и единственная активити приложения. Отвечает за включение / выключение сервиса
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toggleButton)
    ToggleButton toggleButton;

    @Inject HelperUtils helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).component().inject(this);
        setContentView(R.layout.activity_main);

        checkForService();

        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());

    }

    private void checkForService() {
        if (helper.isMyServiceRunning(CallsMonitoringService.class)) {
            ToggleButton button = (ToggleButton) findViewById(R.id.toggleButton);
            assert button != null;
            button.setChecked(true);
        }
    }

    @OnCheckedChanged(R.id.toggleButton)
    public void startService(boolean checked) {
        Intent service = new Intent(this, CallsMonitoringService.class);
        if (checked) {
            if (!helper.isMyServiceRunning(CallsMonitoringService.class)) {
                startService(service);
            }
        } else {
            stopService(service);
        }
    }

}
