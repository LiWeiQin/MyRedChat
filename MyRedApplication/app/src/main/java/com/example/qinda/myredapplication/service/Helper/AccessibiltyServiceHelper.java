package com.example.qinda.myredapplication.service.Helper;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;

import com.example.qinda.myredapplication.service.WetchatAcService;


/**
 * Created by Qinda on 2016/1/10.
 */
public class AccessibiltyServiceHelper {

    public static void startService(Context context) {
        context.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
    }

    public static boolean isAccessibilitySettingOn(Context context) {
        int accessiblityEnabled = 0;
        boolean accessiblityFound = false;
        final String myAccesiblityservice = context.getPackageName() + "/" + WetchatAcService.class.getName();
        try {
            accessiblityEnabled = Settings.Secure.getInt(context.getApplicationContext().getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        if (accessiblityEnabled == 1) {
            String settingValue = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (!TextUtils.isEmpty(settingValue)) {
                TextUtils.SimpleStringSplitter splitter = new TextUtils.SimpleStringSplitter(':');
                splitter.setString(settingValue);
                while (splitter.hasNext()) {
                    String accesiblityService = splitter.next();
                    if (accesiblityService.equalsIgnoreCase(myAccesiblityservice)) {
                        return true;
                    }
                }
            }
        }
        return accessiblityFound;
    }
}
