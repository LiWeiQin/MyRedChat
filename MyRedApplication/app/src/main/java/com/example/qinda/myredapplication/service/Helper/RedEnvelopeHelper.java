package com.example.qinda.myredapplication.service.Helper;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.example.qinda.myredapplication.R;
import com.example.qinda.myredapplication.utils.Logutils;

import java.util.List;

/**
 * Created by Qinda on 2016/1/10.
 */

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class RedEnvelopeHelper {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void openNotification(AccessibilityEvent event) {
        if (!(event.getParcelableData() instanceof Notification))
            return;
        Notification mNotification = (Notification) event.getParcelableData();
        PendingIntent mPendingIntent = mNotification.contentIntent;
        try {
            mPendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static AccessibilityNodeInfo getLastWechatRedEnvelopeNodeById(AccessibilityNodeInfo info) {
        if (null == info)
            return null;
        //TextView com.tencent.mm:id/v8 领取红包,parent:LinearLayout
        List<AccessibilityNodeInfo> list = info.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/uv");
        for (int i = list.size() - 1; i > 0; i--) {
            if ("android.widget.LinearLayout".equals(list.get(i).getClassName()))
                return list.get(i);
        }

        return null;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static AccessibilityNodeInfo getLastWechatRedEnvelopeNodeByText(AccessibilityNodeInfo info, Context context) {
        if (info == null)
            return null;
        List<AccessibilityNodeInfo> resultList = info.findAccessibilityNodeInfosByText(context.getString(R.string.wechat_acc_service_red_envelope_list_identification));
        if (null != resultList && resultList.isEmpty()) {
            for (int i = resultList.size() - 1; i > 0; i--) {
                AccessibilityNodeInfo parent = resultList.get(i).getParent();
                if (parent != null) {
                    return parent;
                }
            }
        }

        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static AccessibilityNodeInfo getWechatRedEnvelopeOpenDetailNode(AccessibilityNodeInfo info) {
        if (null == info)
            return null;
        List<AccessibilityNodeInfo> list = info.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/aqx");
        AccessibilityNodeInfo tempNode = null;
        for (int i = list.size() - 1; i > 0; i--) {
            tempNode = list.get(i);
            Logutils.e("WechatAccService", "eee" + tempNode.isVisibleToUser() + "-" + tempNode.isEnabled());
            if ("android.widget.TextView".equals(tempNode.getClassName()) && tempNode.isVisibleToUser()) {
                return tempNode;
            }
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static AccessibilityNodeInfo getWechatRedEnvelopeOpenNode(AccessibilityNodeInfo info) {
        if (null == info)
            return null;
        List<AccessibilityNodeInfo> list = info.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/ar6");
        AccessibilityNodeInfo tempNode = null;
        for (int i = 0; i < list.size(); i++) {
            tempNode = list.get(i);
            Logutils.e("WechatAccService", "e2ee" + tempNode.isVisibleToUser() + "-" + tempNode.isEnabled());
            if ("android.widget.Button".equals(tempNode.getClassName()) && tempNode.isVisibleToUser()) {
                return tempNode;
            }
        }
        return null;
    }
}
