package com.ltech.smarthome.ui.camera.play;

import android.os.Bundle;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.databinding.FtCameraPtzBinding;
import com.ltech.smarthome.utils.ThreadPoolManager;
import com.smart.message.utils.LHomeLog;
import com.videogo.constant.IntentConsts;
import com.videogo.exception.BaseException;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.widget.HandleView;

/* loaded from: classes4.dex */
public class FtCameraPtz extends BaseNormalFragment<FtCameraPtzBinding> {
    private EZCameraInfo ezCameraInfo;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_camera_ptz;
    }

    public static FtCameraPtz newInstance(EZCameraInfo cameraInfo) {
        FtCameraPtz ftCameraPtz = new FtCameraPtz();
        Bundle bundle = new Bundle();
        bundle.putParcelable(IntentConsts.EXTRA_CAMERA_INFO, cameraInfo);
        ftCameraPtz.setArguments(bundle);
        return ftCameraPtz;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.ezCameraInfo = (EZCameraInfo) arguments.getParcelable(IntentConsts.EXTRA_CAMERA_INFO);
        }
        ((FtCameraPtzBinding) this.mViewBinding).handleView.setHandleReaction(new HandleView.HandleReaction() { // from class: com.ltech.smarthome.ui.camera.play.FtCameraPtz.1
            @Override // com.videogo.widget.HandleView.HandleReaction
            public void onDirectionChanged(int direction, boolean cancel) {
                if (direction == 0) {
                    FtCameraPtz.this.ptzOption(EZConstants.EZPTZCommand.EZPTZCommandLeft, cancel ? EZConstants.EZPTZAction.EZPTZActionSTOP : EZConstants.EZPTZAction.EZPTZActionSTART);
                    return;
                }
                if (direction == 1) {
                    FtCameraPtz.this.ptzOption(EZConstants.EZPTZCommand.EZPTZCommandRight, cancel ? EZConstants.EZPTZAction.EZPTZActionSTOP : EZConstants.EZPTZAction.EZPTZActionSTART);
                } else if (direction == 2) {
                    FtCameraPtz.this.ptzOption(EZConstants.EZPTZCommand.EZPTZCommandUp, cancel ? EZConstants.EZPTZAction.EZPTZActionSTOP : EZConstants.EZPTZAction.EZPTZActionSTART);
                } else {
                    if (direction != 3) {
                        return;
                    }
                    FtCameraPtz.this.ptzOption(EZConstants.EZPTZCommand.EZPTZCommandDown, cancel ? EZConstants.EZPTZAction.EZPTZActionSTOP : EZConstants.EZPTZAction.EZPTZActionSTART);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ptzOption(final EZConstants.EZPTZCommand command, final EZConstants.EZPTZAction action) {
        ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.ltech.smarthome.ui.camera.play.FtCameraPtz.2
            @Override // java.lang.Runnable
            public void run() {
                boolean z;
                try {
                    z = EZOpenSDK.getInstance().controlPTZ(FtCameraPtz.this.ezCameraInfo.getDeviceSerial(), FtCameraPtz.this.ezCameraInfo.getCameraNo(), command, action, 2);
                } catch (BaseException e) {
                    e.printStackTrace();
                    z = false;
                }
                LHomeLog.i(FtCameraPtz.class, "controlPTZ ptzCtrl result: " + z);
            }
        });
    }
}