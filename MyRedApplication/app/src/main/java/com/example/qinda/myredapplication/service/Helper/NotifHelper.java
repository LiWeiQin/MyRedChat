package com.example.qinda.myredapplication.service.Helper;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import com.example.qinda.myredapplication.MyRedApplication;
import com.example.qinda.myredapplication.R;
import com.example.qinda.myredapplication.ui.MainActivity;

/**
 * Created by Qinda on 2016/1/10.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class NotifHelper {

    private static NotifHelper mNotifHelper = null;
    private NotificationManager mNotificationManager = null;
    private static Object INSTANCE_LOCK = new Object();
    public static final int TYPE_WECHAT_SERVICE_RUNNING = 1;


    public static NotifHelper getInstance() {
        if (null == mNotifHelper) {
            synchronized (INSTANCE_LOCK) {
                mNotifHelper = new NotifHelper();
            }
        }
        return mNotifHelper;
    }

    private NotifHelper() {
        mNotificationManager = (NotificationManager) MyRedApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void cancelByType(int type) {
        if (null != mNotificationManager) {
            mNotificationManager.cancel(type);
        }
    }

    public void notify(String title, String message, String tickerText, int type) {
        try {
            Context mContext = MyRedApplication.getInstance();
            Intent intent = new Intent();
            PendingIntent contentIntent = null;
            switch (type) {
                case TYPE_WECHAT_SERVICE_RUNNING:
                    intent.setClass(mContext, MainActivity.class);
                    contentIntent = PendingIntent.getActivity(mContext, TYPE_WECHAT_SERVICE_RUNNING, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    Notification.Builder builder = new Notification.Builder(mContext);
                    builder.setContentIntent(contentIntent)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setWhen(System.currentTimeMillis())
                            .setAutoCancel(false)
                            .setContentTitle(title)
                            .setContentText(message)
                            .setTicker(tickerText)
                            .setDefaults(Notification.DEFAULT_LIGHTS)
                            .setPriority(Notification.PRIORITY_MAX);
                    mNotificationManager.notify(type, builder.getNotification());
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
