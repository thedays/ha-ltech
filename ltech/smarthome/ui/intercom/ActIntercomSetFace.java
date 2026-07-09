package com.ltech.smarthome.ui.intercom;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import com.akuvox.ble_nfc_library.utils.Log;
import com.akuvox.face_recognition.callback.OnProcessCallback;
import com.akuvox.face_recognition.fragment.AkFaceRecognitionFragment;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActIntercomSetFaceBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.response.intercom.UploadFaceResponse;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.CenterTipDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.io.File;

/* loaded from: classes4.dex */
public class ActIntercomSetFace extends VMActivity<ActIntercomSetFaceBinding, ActIntercomSettingVM> {
    private AkFaceRecognitionFragment mFragment = null;
    private int mTimeout = 5;
    private final int COUNTDOWN_MSG = 100;
    private Handler mCountdownHandler = new Handler(Looper.getMainLooper()) { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSetFace.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ActIntercomSetFace actIntercomSetFace = ActIntercomSetFace.this;
            actIntercomSetFace.mTimeout--;
            if (ActIntercomSetFace.this.mTimeout == 0) {
                ActIntercomSetFace.this.setFaceFail();
            }
            ActIntercomSetFace.this.mCountdownHandler.sendEmptyMessageDelayed(100, 1000L);
        }
    };

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_intercom_set_face;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.manager_face));
        ((ActIntercomSettingVM) this.mViewModel).faceStatus = getIntent().getIntExtra(Constants.FACE_STATUS, 0);
        ((ActIntercomSettingVM) this.mViewModel).isExistFace.setValue(Boolean.valueOf(((ActIntercomSettingVM) this.mViewModel).faceStatus == 1));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActIntercomSettingVM) this.mViewModel).isExistFace.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSetFace$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercomSetFace.this.lambda$startObserve$0((Boolean) obj);
            }
        });
        ((ActIntercomSettingVM) this.mViewModel).startRecordEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSetFace$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercomSetFace.this.lambda$startObserve$1((Void) obj);
            }
        });
        ((ActIntercomSettingVM) this.mViewModel).uploadSuccessEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSetFace$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercomSetFace.this.lambda$startObserve$2((Boolean) obj);
            }
        });
        ((ActIntercomSettingVM) this.mViewModel).deleteFaceInfoEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSetFace$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercomSetFace.this.lambda$startObserve$4((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Boolean bool) {
        if (bool.booleanValue()) {
            ((ActIntercomSetFaceBinding) this.mViewBinding).layoutRecordSuccess.setVisibility(0);
            ((ActIntercomSetFaceBinding) this.mViewBinding).layoutRecordStart.setVisibility(8);
            ((ActIntercomSetFaceBinding) this.mViewBinding).layoutRecording.setVisibility(8);
        } else {
            ((ActIntercomSetFaceBinding) this.mViewBinding).layoutRecordSuccess.setVisibility(8);
            ((ActIntercomSetFaceBinding) this.mViewBinding).layoutRecordStart.setVisibility(0);
            ((ActIntercomSetFaceBinding) this.mViewBinding).layoutRecording.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r3) {
        ((ActIntercomSetFaceBinding) this.mViewBinding).layoutRecordStart.setVisibility(8);
        ((ActIntercomSetFaceBinding) this.mViewBinding).layoutRecordSuccess.setVisibility(8);
        ((ActIntercomSetFaceBinding) this.mViewBinding).layoutRecording.setVisibility(0);
        initAkFaceRecognitionFragment();
        this.mTimeout = 5;
        startCountdown();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(((ActIntercomSetFaceBinding) this.mViewBinding).ivFaceBg, "rotation", 0.0f, 360.0f);
        ofFloat.setDuration(800L);
        ofFloat.setRepeatCount(-1);
        ofFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Boolean bool) {
        ((ActIntercomSettingVM) this.mViewModel).isExistFace.setValue(bool);
        ((ActIntercomSetFaceBinding) this.mViewBinding).tvDeleteFace.setVisibility(8);
        ((ActIntercomSetFaceBinding) this.mViewBinding).tvFaceSuccess.setText(getString(R.string.record_face_success));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r2) {
        CenterTipDialog.asDefault().setTitle(getString(R.string.confirm_delete_face_into)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setConfirmColor(R.color.dialog_text_color).setOnConfirmListener(new CenterTipDialog.OnConfirmListener() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSetFace$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.view.dialog.CenterTipDialog.OnConfirmListener
            public final void onConfirm() {
                ActIntercomSetFace.this.lambda$startObserve$3();
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3() {
        ((ActIntercomSettingVM) this.mViewModel).deleteFaceInfo();
    }

    private void initAkFaceRecognitionFragment() {
        AkFaceRecognitionFragment akFaceRecognitionFragment = this.mFragment;
        if (akFaceRecognitionFragment == null) {
            this.mFragment = AkFaceRecognitionFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, this.mFragment).commit();
            this.mFragment.setProcessCallBack(new OnProcessCallback() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSetFace.2
                @Override // com.akuvox.face_recognition.callback.OnProcessCallback
                public void onRecognitionStart() {
                    ActIntercomSetFace.this.stopCountdown();
                    Log.e("Face detection start");
                }

                @Override // com.akuvox.face_recognition.callback.OnProcessCallback
                public void onRecognitionPause() {
                    Log.e("Face detection paused");
                    ActIntercomSetFace.this.setFaceFail();
                }

                @Override // com.akuvox.face_recognition.callback.OnProcessCallback
                public void onRecognitionSuccess(File file) {
                    ActIntercomSetFace.this.uploadFaceData(file);
                    ActIntercomSetFace.this.pauseRec();
                    Log.e("Face detection success");
                }

                @Override // com.akuvox.face_recognition.callback.OnProcessCallback
                public void onRecognitionFailed() {
                    Log.e("Face detection failed");
                }
            });
            return;
        }
        akFaceRecognitionFragment.resumeRec();
    }

    private void startCountdown() {
        if (this.mCountdownHandler == null) {
            return;
        }
        stopCountdown();
        this.mCountdownHandler.sendEmptyMessageDelayed(100, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopCountdown() {
        Handler handler = this.mCountdownHandler;
        if (handler == null) {
            return;
        }
        handler.removeMessages(100);
        this.mTimeout = 5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadFaceData(File file) {
        ((ObservableSubscribeProxy) Injection.net().uploadFaceFile(file, String.format(Constants.POST_UPLOAD_FACE, Injection.intercom().getAccessToken())).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSetFace$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomSetFace.this.lambda$uploadFaceData$5((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSetFace$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActIntercomSetFace.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSetFace$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomSetFace.this.lambda$uploadFaceData$6((UploadFaceResponse) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSetFace$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomSetFace.this.lambda$uploadFaceData$7((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadFaceData$5(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.uploading));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadFaceData$6(UploadFaceResponse uploadFaceResponse) throws Exception {
        ((ActIntercomSettingVM) this.mViewModel).uploadSuccessEvent.setValue(Boolean.valueOf(uploadFaceResponse.getResult() == 0));
        if (uploadFaceResponse.getResult() != 0) {
            setFaceFail();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadFaceData$7(Throwable th) throws Exception {
        setFaceFail();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFaceFail() {
        pauseRec();
        stopCountdown();
        SmartToast.showCenterShort(getString(R.string.record_face_fail));
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSetFace$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                ActIntercomSetFace.this.lambda$setFaceFail$8();
            }
        }, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setFaceFail$8() {
        ((ActIntercomSettingVM) this.mViewModel).isExistFace.setValue(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pauseRec() {
        AkFaceRecognitionFragment akFaceRecognitionFragment = this.mFragment;
        if (akFaceRecognitionFragment == null) {
            return;
        }
        akFaceRecognitionFragment.pauseRec();
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        pauseRec();
        stopCountdown();
        this.mCountdownHandler.removeCallbacksAndMessages(null);
    }
}