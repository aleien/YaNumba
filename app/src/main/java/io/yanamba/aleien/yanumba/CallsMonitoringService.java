package io.yanamba.aleien.yanumba;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import javax.inject.Inject;

import io.yanamba.aleien.yanumba.ui.App;
import io.yanamba.aleien.yanumba.utils.WindowManagerUtils;

public class CallsMonitoringService extends Service {
    private TextView searchResultsWindow;
    @Inject Searcher searcher;
    @Inject WindowManagerUtils windowHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        ((App) getApplication()).component().inject(this);
        searchResultsWindow = windowHelper.createResultsView(this);
        WindowManagerUtils.setupWindow(searchResultsWindow);

        TelephonyManager tim = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        tim.listen(new PhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class PhoneStateListener extends android.telephony.PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    searcher.search(incomingNumber)
                            .subscribe(searchResult -> {
                                        searchResultsWindow.setText(searchResult);
                                        // Что делать, если нас забанили?
                                        // 1) настроить подклчение нашего приложения через прокси
                                        // 2) написать свой микро-прокси, с запросом к яндекс. xml
                                        windowHelper.showWindow(searchResultsWindow);
                                    },
                                    Throwable::printStackTrace);

                    if (searchResultsWindow != null) {
                        searchResultsWindow.setText(incomingNumber);
                        windowHelper.showWindow(searchResultsWindow);
                    }

            }
        }
    }


}
