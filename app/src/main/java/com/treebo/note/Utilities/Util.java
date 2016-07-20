package com.treebo.note.Utilities;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by sumanta on 21/7/16.
 */
public abstract class Util {

    public static void hideKeypad(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            hideKeypad(activity, view);
        }
    }

    public static void hideKeypad(Context context, View target) {
        if (context == null || target == null) {
            return;
        }
        InputMethodManager inputMethodManager = getInputMethodManager(context);
        inputMethodManager.hideSoftInputFromWindow(target.getWindowToken(), 0);
    }

    private static InputMethodManager getInputMethodManager(Context context) {
        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }
}
