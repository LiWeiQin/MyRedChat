package com.example.qinda.myredapplication.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Qinda on 2016/1/18.
 */
public class ActivityHelper {


    public static void goHome(Context context) {
        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(mHomeIntent);
    }
}
