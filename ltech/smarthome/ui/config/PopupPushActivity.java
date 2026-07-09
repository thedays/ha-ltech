package com.ltech.smarthome.ui.config;

import android.os.Bundle;
import com.alibaba.sdk.android.push.AndroidPopupActivity;
import com.smart.message.utils.LHomeLog;
import java.util.Map;

/* loaded from: classes4.dex */
public class PopupPushActivity extends AndroidPopupActivity {
    static final String TAG = "PopupPushActivity";

    @Override // com.alibaba.sdk.android.push.AndroidPopupActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.alibaba.sdk.android.push.AndroidPopupActivity
    protected void onSysNoticeOpened(String title, String summary, Map<String, String> extMap) {
        LHomeLog.d(getClass(), "OnPushSysNoticeOpened, title: " + title + ", content: " + summary + ", extMap: " + extMap);
    }
}