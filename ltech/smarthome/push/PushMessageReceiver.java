package com.ltech.smarthome.push;

import android.content.Context;
import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.aliyun.ams.emas.push.AgooMessageReceiver;
import java.util.Map;
import timber.log.Timber;

/* loaded from: classes4.dex */
public class PushMessageReceiver extends MessageReceiver {
    @Override // com.alibaba.sdk.android.push.MessageReceiver
    protected void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        Timber.tag(AgooMessageReceiver.TAG).d("Receive notification, title: " + title + ", summary: " + summary + ", extraMap: " + extraMap, new Object[0]);
    }

    @Override // com.alibaba.sdk.android.push.MessageReceiver
    protected void onMessage(Context context, CPushMessage cPushMessage) {
        Timber.tag(AgooMessageReceiver.TAG).d("onMessage, messageId: " + cPushMessage.getMessageId() + ", title: " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent(), new Object[0]);
        PushDataHelper.handlePushData(cPushMessage);
    }

    @Override // com.alibaba.sdk.android.push.MessageReceiver
    protected void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Timber.tag(AgooMessageReceiver.TAG).d("onNotificationOpened, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap, new Object[0]);
    }

    @Override // com.alibaba.sdk.android.push.MessageReceiver
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        Timber.tag(AgooMessageReceiver.TAG).d("onNotificationClickedWithNoAction, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap, new Object[0]);
    }

    @Override // com.alibaba.sdk.android.push.MessageReceiver
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        Timber.tag(AgooMessageReceiver.TAG).d("onNotificationReceivedInApp, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap + ", openType:" + openType + ", openActivity:" + openActivity + ", openUrl:" + openUrl, new Object[0]);
    }

    @Override // com.alibaba.sdk.android.push.MessageReceiver
    protected void onNotificationRemoved(Context context, String messageId) {
        Timber.tag(AgooMessageReceiver.TAG).d("onNotificationRemoved", new Object[0]);
    }
}