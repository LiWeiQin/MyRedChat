package com.example.qinda.myredapplication.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.app.Notification;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.example.qinda.myredapplication.R;
import com.example.qinda.myredapplication.service.Helper.NotifHelper;
import com.example.qinda.myredapplication.service.Helper.RedEnvelopeHelper;
import com.example.qinda.myredapplication.ui.MainActivity;
import com.example.qinda.myredapplication.utils.ActivityHelper;
import com.example.qinda.myredapplication.utils.Logutils;

/**
 * Created by Qinda on 2016/1/10.
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class WetchatAcService extends AccessibilityService {

    public static void log(String message) {
        Logutils.e("WechatAccService", message);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onServiceConnected() {
        AccessibilityServiceInfo mAccessibilityServiceInfo = getServiceInfo();
        if (null == mAccessibilityServiceInfo) {
            mAccessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
            mAccessibilityServiceInfo.flags |= AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS;
            mAccessibilityServiceInfo.packageNames = new String[]{MainActivity.WECHAT_PACKAGENAME};
            mAccessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
            mAccessibilityServiceInfo.notificationTimeout = 10;
            setServiceInfo(mAccessibilityServiceInfo);
        }
        // 4.0之后可通过xml进行配置,以下加入到Service里面

        NotifHelper.getInstance().notify(getString(R.string.app_name), getString(R.string.service_wetchat_name), getString(R.string.service_wetchat_name), NotifHelper.TYPE_WECHAT_SERVICE_RUNNING);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        log(event.getClassName()+"eee");
        if (null == event)
            return;
        handleNotificationChange(event);
        if (null == event.getSource())
            return;
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            CharSequence currentActivityNameClassName = event.getClassName();
            if ("com.tencent.mm.ui.LauncherUI".equals(currentActivityNameClassName)) {// 聊天以及主页 chat page and the main page
                log("Chat page");
                handleChatPage(event.getSource());
            } else if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI".equals(currentActivityNameClassName)) {//打开红包主页 red envelope open page
                log("LuckyMoneyReceiveUI page");
                handleLuckyMoneyReceivePage(event.getSource());
            } else if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI".equals(currentActivityNameClassName)) {// 红包详情主页 red envelope detail page
                handleLuckyMoneyDetailPage(event.getSource());
            } else {
                log(currentActivityNameClassName + " page");
            }
        }

    }

    private void handleLuckyMoneyDetailPage(AccessibilityNodeInfo source) {
        if (source == null)
            return;
        ActivityHelper.goHome(this);
    }

    private void handleLuckyMoneyReceivePage(AccessibilityNodeInfo info) {
        if (null == info)
            return;
        AccessibilityNodeInfo nodeDetail = RedEnvelopeHelper.getWechatRedEnvelopeOpenDetailNode(info);
        if (null != nodeDetail) { //红包已经被打开了
            ActivityHelper.goHome(this);
        } else {
            AccessibilityNodeInfo nodeOpen = RedEnvelopeHelper.getWechatRedEnvelopeOpenNode(info);
            if (null != nodeOpen) {
                nodeOpen.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                nodeOpen.recycle();
            } else {// this page is loading red envelope data, no action
            }
        }
    }

    private void handleChatPage(AccessibilityNodeInfo info) {
        if (null == info)
            return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            AccessibilityNodeInfo tempNode = RedEnvelopeHelper.getLastWechatRedEnvelopeNodeById(info);
            if (null != tempNode) {
                tempNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                tempNode.recycle();
            }
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            AccessibilityNodeInfo tempNode = RedEnvelopeHelper.getLastWechatRedEnvelopeNodeByText(info, this);
            if (tempNode != null) {
                tempNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                tempNode.recycle();
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleNotificationChange(AccessibilityEvent event) {
        log("event.eventType:" + event.getEventType());
        if (null == event)
            return;
        if (!(event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED))
            return;
        if (event.getParcelableData() instanceof Notification) {
            Notification mNotification = (Notification) event.getParcelableData();
            if (null != mNotification.tickerText && mNotification.tickerText.toString().contains(getString(R.string.wechat_acc_service_red_envelope_notification_identification))) {
                log("来红包啦 get red envelope message");
                RedEnvelopeHelper.openNotification(event);
            }
        }

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotifHelper.getInstance().cancelByType(NotifHelper.TYPE_WECHAT_SERVICE_RUNNING);
    }
}
