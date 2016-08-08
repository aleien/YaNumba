package io.yanamba.aleien.yanumba.android;

import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.ButterKnife;
import io.yanamba.aleien.yanumba.R;
import io.yanamba.aleien.yanumba.search.Searcher;
import io.yanamba.aleien.yanumba.utils.WindowManagerHelper;

public class CallsMonitoringService extends Service {
    TextView searchResultsWindow;
    @Inject Searcher searcher;
    @Inject WindowManagerHelper windowHelper;
    String errorMessage;
    String callMessage;

    @Override
    public void onCreate() {
        super.onCreate();
        ((App) getApplication()).component().inject(this);
        errorMessage = getResources().getString(R.string.error_message);
        callMessage = getResources().getString(R.string.calling_message);

        searchResultsWindow = windowHelper.createResultsView(this);
        windowHelper.setupWindow(searchResultsWindow);

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
                            .doOnError(Throwable::printStackTrace)
                            .subscribe(searchResult -> {
                                        searchResultsWindow.setText(searchResult);
                                        // Что делать, если нас забанили?
                                        // 1) настроить подклчение нашего приложения через прокси
                                        // 2) написать свой микро-прокси, с запросом к яндекс. xml
                                        windowHelper.showWindow(searchResultsWindow);
                                    },
                                    error -> searchResultsWindow.setText(errorMessage));

                    // debug code
                    if (searchResultsWindow != null) {
                        searchResultsWindow.setText(String.format("%s%s", callMessage, incomingNumber));
                        windowHelper.showWindow(searchResultsWindow);
                    }

            }
        }
    }


}
