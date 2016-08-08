package io.yanamba.aleien.yanumba.utils;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import javax.inject.Inject;

import io.yanamba.aleien.yanumba.R;

import static android.content.Context.WINDOW_SERVICE;
import static android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
import static android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;

public final class WindowManagerHelper {
    private HelperUtils helperUtils;

    @Inject
    public WindowManagerHelper(HelperUtils helperUtils) {
        this.helperUtils = helperUtils;
    }

    public static void setupWindow(View window) {
        Context context = window.getContext();
        WindowManager wm = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        wm.addView(window, generateWindowManagerParams());

        window.setOnTouchListener((v, ev) -> {
            RectF viewBounds = new RectF(window.getX(),
                    window.getY(),
                    window.getLayoutParams().width,
                    window.getLayoutParams().height);

            if (viewBounds.contains(ev.getX(), ev.getY())) {
                hideWindow(window);
                return true;
            }
            return false;

        });
    }

    public static void hideWindow(View window) {
        window.animate()
                .translationX(Resources.getSystem().getDisplayMetrics().widthPixels)
                .alpha(0)
                .setDuration(700)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new SimpleAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        window.setVisibility(View.GONE);
                        animation.removeListener(this);
                    }
                })
                .start();
    }

    public void showWindow(View window) {
        resetWindowState(window);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            revealWindow(window);
        } else {
            animateWindow(window);
        }
    }

    private void animateWindow(View window) {
        window.setTranslationX(window.getContext().getResources().getDisplayMetrics().widthPixels);
        window.setAlpha(0);
        window.animate()
                .alpha(1)
                .translationX(0)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void revealWindow(View window) {
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                window,
                window.getRight(),
                window.getHeight() / 2,
                0,
                window.getWidth());
        window.setVisibility(View.VISIBLE);
        circularReveal.start();
    }

    public void resetWindowState(View window) {
        window.setAlpha(1f);
        window.setTranslationX(0f);
    }

    public TextView createResultsView(Context context) {
        TextView window = new TextView(context);
        int p = helperUtils.dpToPx(16);
        window.setBackgroundResource(R.drawable.rounded_borders);
        window.setTextColor(Color.BLACK);
        window.setPadding(p, p, p, p);
        return window;
    }

    public static WindowManager.LayoutParams generateWindowManagerParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                (int) Resources.getSystem().getDisplayMetrics().density * 400,
                (int) Resources.getSystem().getDisplayMetrics().density * 300,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        | FLAG_SHOW_WHEN_LOCKED
                        | FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        return params;
    }
}
