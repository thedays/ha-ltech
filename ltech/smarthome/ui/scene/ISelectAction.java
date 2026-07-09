package com.ltech.smarthome.ui.scene;

import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;

/* loaded from: classes4.dex */
public interface ISelectAction {
    void saveAction(BaseNormalActivity activity, boolean isLocalScene);

    /* renamed from: setSelectResult */
    void lambda$save$1(BaseNormalActivity activity);

    /* renamed from: com.ltech.smarthome.ui.scene.ISelectAction$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$setSelectResult(ISelectAction _this, BaseNormalActivity activity) {
            activity.setResult(3001);
            activity.finishActivity();
        }

        public static void $default$saveAction(final ISelectAction _this, final BaseNormalActivity activity, boolean isLocalScene) {
            if (isLocalScene) {
                _this.lambda$save$1(activity);
            } else {
                SceneHelper.instance().saveSelectResult(activity, new IAction() { // from class: com.ltech.smarthome.ui.scene.ISelectAction$$ExternalSyntheticLambda0
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ISelectAction.this.lambda$save$1(activity);
                    }
                });
            }
        }
    }
}