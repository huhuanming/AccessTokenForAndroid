package com.example.hu.accesstoken;

import android.app.Application;
import android.content.Context;

/**
 * Created by hu on 14/10/27.
 */
public class ApplicationRunTime extends Application {
    private static Context context;

    public void onCreate(){
        super.onCreate();
        ApplicationRunTime.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ApplicationRunTime.context;
    }
}
