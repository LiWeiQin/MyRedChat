package com.example.qinda.myredapplication.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Qinda on 2016/1/10.
 */
public class PackageUtils {

    public static boolean isAppInstalled(Context context, String packageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        return null != packageInfo ? true : false;
    }

    public static boolean couldOpen(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        if (isAppInstalled(context, packageName)) {
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(packageName);
            return couldOpen(pm, resolveIntent);
        }
        return false;
    }


    public static boolean couldOpen(PackageManager pm, Intent resolveIntent) {
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(resolveIntent, 0);
        if (null != resolveInfos && !resolveInfos.isEmpty()) {
            Iterator<ResolveInfo> iterator = resolveInfos.iterator();
            return iterator.hasNext() ? true : false;
        }
        return false;
    }

    public static void openApp(Context context, String packageName) {

        PackageManager pm = context.getPackageManager();

        String realPackageName = packageName;
        List<PackageInfo> appInfos = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
        Iterator<PackageInfo> itInfo = appInfos.iterator();
        while (itInfo.hasNext()) {
            PackageInfo info = itInfo.next();
            if (info.packageName.equalsIgnoreCase(packageName)) {
                realPackageName = info.packageName;
            }
        }

        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(realPackageName);

        List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);
        if (apps == null)
            return;
        Iterator<ResolveInfo> it = apps.iterator();
        ResolveInfo ri = null;
        if (it.hasNext()) {
            ri = it.next();
        } else {
            Toast.makeText(context, "Can not open application", Toast.LENGTH_SHORT).show();
        }
        if (ri != null) {
            String className = ri.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            ComponentName cn = new ComponentName(realPackageName, className);

            intent.setComponent(cn);
            context.startActivity(intent);
        }
    }



}
