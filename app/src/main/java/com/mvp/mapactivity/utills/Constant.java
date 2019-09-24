package com.mvp.mapactivity.utills;

import android.app.Activity;
import android.support.design.widget.Snackbar;

public class Constant {

    public static final   String BASEURL="http://my.socketconnection.url";


    public static void snackeBarMessage(Activity activity, String msg){
        Snackbar snackbar = Snackbar
                .make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
