package com.ltech.smarthome.ui.automation;

import com.ltech.smarthome.base.BaseNormalActivity;

/* loaded from: classes4.dex */
public interface ISelectCondition {
    void setSelectResult(BaseNormalActivity activity);

    void setSelectStatusResult(BaseNormalActivity activity);

    /* renamed from: com.ltech.smarthome.ui.automation.ISelectCondition$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$setSelectResult(ISelectCondition _this, BaseNormalActivity activity) {
            activity.setResult(3003);
            activity.finishActivity();
        }

        public static void $default$setSelectStatusResult(ISelectCondition _this, BaseNormalActivity activity) {
            activity.setResult(5013);
            activity.finishActivity();
        }
    }
}