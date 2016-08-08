package io.yanamba.aleien.yanumba.utils;


import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;

import static io.yanamba.aleien.yanumba.di.AppModule.SEARCH_QUERY;
import static io.yanamba.aleien.yanumba.di.AppModule.YANDEX_BASE_URL;

public class HelperUtils {
    private Context context;

    public HelperUtils(Context context) {
        this.context = context.getApplicationContext();
    }

    public int dpToPx(int dp) {
        return (int) Resources.getSystem().getDisplayMetrics().density * dp;
    }

    public String getSearchQuery(String text) {
        return YANDEX_BASE_URL + SEARCH_QUERY + text;
    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


}
