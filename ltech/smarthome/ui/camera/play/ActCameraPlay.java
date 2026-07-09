package com.ltech.smarthome.ui.camera.play;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.blankj.utilcode.util.ActivityUtils;
import com.google.android.material.tabs.TabLayout;
import com.ltech.imageclip.util.FileUtil;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActCameraPlayBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.CameraParam;
import com.ltech.smarthome.net.response.camera.GetTokenResponse;
import com.ltech.smarthome.singleton.PathManager;
import com.ltech.smarthome.ui.camera.EZManager;
import com.ltech.smarthome.ui.device.camera.EzConstants;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.ThreadPoolManager;
import com.ltech.smarthome.utils.VersionUtils;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.message.utils.LHomeLog;
import com.sun.jna.platform.win32.WinError;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.videogo.common.EzConfig;
import com.videogo.common.RemoteListContant;
import com.videogo.common.VideoFileUtil;
import com.videogo.errorlayer.ErrorInfo;
import com.videogo.exception.BaseException;
import com.videogo.exception.ErrorCode;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZOpenSDKListener;
import com.videogo.openapi.EZPlaybackStreamParam;
import com.videogo.openapi.EZPlayer;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.videogo.openapi.bean.EZDeviceRecordFile;
import com.videogo.openapi.bean.EZVideoQualityInfo;
import com.videogo.ui.querylist.ClickedListItem;
import com.videogo.ui.util.EZUtils;
import com.videogo.ui.util.RemoteListUtil;
import com.videogo.util.ConnectionDetector;
import com.videogo.util.LocalInfo;
import com.videogo.util.Utils;
import com.videogo.widget.CustomRect;
import com.videogo.widget.CustomTouchListener;
import com.videogo.widget.HandleView;
import com.yanzhenjie.permission.runtime.Permission;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActCameraPlay extends VMActivity<ActCameraPlayBinding, ActCameraPlayVM> implements Handler.Callback {
    public static final int LANDSCAPE = 1;
    public static final int PORTRAIT = 0;
    private int cameraNum;
    public ClickedListItem currentClickItemFile;
    public boolean isTalk;
    private Handler mHandler;
    protected SurfaceView mRealPlaySv;
    protected SurfaceView mRealPlaySv2;
    private int playType;
    private long recordTime;
    private PopupWindow mQualityPopupWindow = null;
    private EZDeviceInfo mDeviceInfo = null;
    private EZCameraInfo mCameraInfo = null;
    private LocalInfo mLocalInfo = null;
    private boolean isRealPlay = true;
    private EZConstants.EZVideoLevel mCurrentQulityMode = EZConstants.EZVideoLevel.VIDEO_LEVEL_HD;
    private float mRealRatio = 0.5625f;
    private int mRealStatus = 0;
    private DecimalFormat df = new DecimalFormat("00");
    private boolean isRecord = false;
    private long mStreamFlow = 0;
    public EZDeviceRecordFile currentDeviceRecordInfo = null;
    protected SurfaceHolder mRealPlaySh = null;
    protected SurfaceHolder mRealPlaySh2 = null;
    protected int mOrientation = 0;
    private int visibleStatus = 0;
    private final int RECORD_AUDIO_PERMISSION_CODE = 666;
    private final int WRITE_STORAGE_PERMISSION_CODE = WinError.ERROR_CANT_TERMINATE_SELF;
    private CustomTouchListener mRealPlayTouchListener = new CustomTouchListener() { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay.7
        @Override // com.videogo.widget.CustomTouchListener
        public boolean canDrag(int direction) {
            return false;
        }

        @Override // com.videogo.widget.CustomTouchListener
        public boolean canZoom(float scale) {
            return false;
        }

        @Override // com.videogo.widget.CustomTouchListener
        public void onDoubleClick(View v, MotionEvent e) {
        }

        @Override // com.videogo.widget.CustomTouchListener
        public void onDrag(int direction, float distance, float rate) {
        }

        @Override // com.videogo.widget.CustomTouchListener
        public void onEnd(int mode) {
        }

        @Override // com.videogo.widget.CustomTouchListener
        public void onZoom(float scale) {
        }

        @Override // com.videogo.widget.CustomTouchListener
        public void onZoomChange(float scale, CustomRect oRect, CustomRect curRect) {
        }

        @Override // com.videogo.widget.CustomTouchListener
        public void onSingleClick() {
            if (ActCameraPlay.this.mOrientation == 1) {
                if (((ActCameraPlayBinding) ActCameraPlay.this.mViewBinding).layoutController3.getVisibility() == 8) {
                    ((ActCameraPlayBinding) ActCameraPlay.this.mViewBinding).layoutController3.setVisibility(0);
                } else {
                    ((ActCameraPlayBinding) ActCameraPlay.this.mViewBinding).layoutController3.setVisibility(8);
                }
            }
        }
    };
    private View.OnTouchListener mOnHalfTouchListener = new View.OnTouchListener() { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay.9
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View v, MotionEvent event) {
            LHomeLog.i(FtCameraTalk.class, "mOnTouchListener:" + event.getAction());
            int action = event.getAction();
            if (action == 0) {
                ((ActCameraPlayVM) ActCameraPlay.this.mViewModel).ezPlayer.setVoiceTalkStatus(true);
                ((ActCameraPlayBinding) ActCameraPlay.this.mViewBinding).spreadviewTalk.setVisibility(0);
                ((ActCameraPlayBinding) ActCameraPlay.this.mViewBinding).spreadviewTalkStop.setVisibility(8);
                ((ActCameraPlayBinding) ActCameraPlay.this.mViewBinding).spreadviewTalk.setAnimate(false);
                ((ActCameraPlayBinding) ActCameraPlay.this.mViewBinding).spreadviewTalk.requestLayout();
                ((ActCameraPlayBinding) ActCameraPlay.this.mViewBinding).spreadviewTalk.setAnimate(true);
            } else if (action == 1 || action == 3) {
                ((ActCameraPlayVM) ActCameraPlay.this.mViewModel).ezPlayer.setVoiceTalkStatus(false);
                ((ActCameraPlayBinding) ActCameraPlay.this.mViewBinding).spreadviewTalk.setVisibility(8);
                ((ActCameraPlayBinding) ActCameraPlay.this.mViewBinding).spreadviewTalkStop.setVisibility(0);
            }
            return true;
        }
    };
    private View.OnClickListener mOnFullTouchListener = new View.OnClickListener() { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay.10
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (ActCameraPlay.this.isTalk) {
                ActCameraPlay.this.controlTalk(false);
            } else {
                ActCameraPlay.this.controlTalk(true);
            }
        }
    };
    private Runnable setRecordRunnable = new Runnable() { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay.13
        @Override // java.lang.Runnable
        public void run() {
            if (ActCameraPlay.this.mViewBinding != null) {
                ((ActCameraPlayBinding) ActCameraPlay.this.mViewBinding).tvRecordTime.setText(ActCameraPlay.this.getRecordTimeString());
            }
            ActCameraPlay.this.recordTime++;
            ActCameraPlay.this.getMainHandler().postDelayed(ActCameraPlay.this.setRecordRunnable, 1000L);
        }
    };
    private Runnable kbPreSecondRunnable = new Runnable() { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay.16
        @Override // java.lang.Runnable
        public void run() {
            ActCameraPlay actCameraPlay = ActCameraPlay.this;
            actCameraPlay.updateRealPlayFlowTv(((ActCameraPlayVM) actCameraPlay.mViewModel).ezPlayer.getStreamFlow());
            ActCameraPlay.this.showKbPreSecond();
        }
    };
    private int mBackstatus = 0;

    private void setRealPlayTalkUI() {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_camera_play;
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void onViewCreated() {
        super.onViewCreated();
        ((ActCameraPlayVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
    }

    public void showTitle(boolean showTitle) {
        ((ActCameraPlayBinding) this.mViewBinding).setShowTitle(Boolean.valueOf(showTitle));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        showTitle(true);
        this.playType = getIntent().getIntExtra(EzConstants.PLAY_TYPE, 0);
        ((ActCameraPlayBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActCameraPlay.this.lambda$initView$0((View) obj);
            }
        }));
        ((ActCameraPlayBinding) this.mViewBinding).handleViewFull.setHandleReaction(new HandleView.HandleReaction() { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay.1
            @Override // com.videogo.widget.HandleView.HandleReaction
            public void onDirectionChanged(int direction, boolean cancel) {
                if (direction == 0) {
                    ActCameraPlay.this.ptzOption(EZConstants.EZPTZCommand.EZPTZCommandLeft, cancel ? EZConstants.EZPTZAction.EZPTZActionSTOP : EZConstants.EZPTZAction.EZPTZActionSTART);
                    return;
                }
                if (direction == 1) {
                    ActCameraPlay.this.ptzOption(EZConstants.EZPTZCommand.EZPTZCommandRight, cancel ? EZConstants.EZPTZAction.EZPTZActionSTOP : EZConstants.EZPTZAction.EZPTZActionSTART);
                } else if (direction == 2) {
                    ActCameraPlay.this.ptzOption(EZConstants.EZPTZCommand.EZPTZCommandUp, cancel ? EZConstants.EZPTZAction.EZPTZActionSTOP : EZConstants.EZPTZAction.EZPTZActionSTART);
                } else {
                    if (direction != 3) {
                        return;
                    }
                    ActCameraPlay.this.ptzOption(EZConstants.EZPTZCommand.EZPTZCommandDown, cancel ? EZConstants.EZPTZAction.EZPTZActionSTOP : EZConstants.EZPTZAction.EZPTZActionSTART);
                }
            }
        });
        initRealPlayView();
        initPlaybackView();
        if (this.mOrientation == 0) {
            initWindow(0);
        } else {
            setRequestedOrientation(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00e1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$initView$0(android.view.View r4) {
        /*
            Method dump skipped, instructions count: 342
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.camera.play.ActCameraPlay.lambda$initView$0(android.view.View):void");
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActCameraPlayVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(((ActCameraPlayVM) this.mViewModel).controlId));
        ((ActCameraPlayVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCameraPlay.this.lambda$startObserve$1((Device) obj);
            }
        });
        checkToken();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        setTitle(device.getDeviceName());
        int cameraNum = ((CameraParam) device.getParam(CameraParam.class)).getCameraNum();
        this.cameraNum = cameraNum;
        if (cameraNum > 1) {
            ((ActCameraPlayBinding) this.mViewBinding).surfaceView2.setVisibility(0);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavHelper.goSetting(((ActCameraPlayVM) this.mViewModel).controlDevice.getValue());
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.isRealPlay) {
            int i = this.mRealStatus;
            if (i == 0 || i == 4 || i == 5 || i == 2) {
                startRealPlay();
                return;
            }
            return;
        }
        if (this.mRealStatus == 3) {
            resumePlayback();
        }
    }

    private void checkToken() {
        ((ObservableSubscribeProxy) Injection.net().getPlaceToken(((ActCameraPlayVM) this.mViewModel).controlDevice.getValue().getPlaceId(), false).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActCameraPlay.this.lambda$checkToken$2((GetTokenResponse) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkToken$2(GetTokenResponse getTokenResponse) throws Exception {
        if (TextUtils.isEmpty(getTokenResponse.getAccesstoken())) {
            return;
        }
        EZManager.instance().setAccesstoken(getTokenResponse.getAccesstoken());
        initData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public EZCameraInfo getCamera(int cameraNo) {
        return EZUtils.getCameraInfoFromDevice(this.mDeviceInfo, cameraNo);
    }

    private void initData() {
        this.mHandler = new Handler(this);
        LocalInfo localInfo = LocalInfo.getInstance();
        this.mLocalInfo = localInfo;
        localInfo.setSoundOpen(false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.mLocalInfo.setScreenWidthHeight(displayMetrics.widthPixels, displayMetrics.heightPixels);
        ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                ActCameraPlay.this.lambda$initData$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$3() {
        try {
            LHomeLog.i(getClass(), "accessToken = " + EZOpenSDK.getInstance().getEZAccessToken().getAccessToken());
            this.mDeviceInfo = EZOpenSDK.getInstance().getDeviceInfo(((ActCameraPlayVM) this.mViewModel).controlDevice.getValue().getDevicesn());
            if (getCamera(1) != null) {
                initRealPlayView2();
            }
            EZCameraInfo cameraInfoFromDevice = EZUtils.getCameraInfoFromDevice(this.mDeviceInfo, 0);
            this.mCameraInfo = cameraInfoFromDevice;
            if (cameraInfoFromDevice == null) {
                return;
            }
            this.mCurrentQulityMode = cameraInfoFromDevice.getVideoLevel();
            setQualityTv();
            int i = this.mRealStatus;
            if (i == 0 || i == 4 || i == 5 || i == 2) {
                startRealPlay();
            }
            getMainHandler().post(new Runnable() { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay.2
                @Override // java.lang.Runnable
                public void run() {
                    if (ActCameraPlay.this.getSupportTalk() == 1) {
                        ((ActCameraPlayBinding) ActCameraPlay.this.mViewBinding).talkViewFull.setOnClickListener(ActCameraPlay.this.mOnFullTouchListener);
                    } else {
                        ((ActCameraPlayBinding) ActCameraPlay.this.mViewBinding).talkViewFull.setOnTouchListener(ActCameraPlay.this.mOnHalfTouchListener);
                    }
                    ActCameraPlay.this.initFragment();
                }
            });
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initFragment() {
        if (this.mViewBinding == 0) {
            return;
        }
        ((ActCameraPlayVM) this.mViewModel).initTabList(this.mCameraInfo, getSupportPtz(), getSupportTalk() != 0);
        ((ActCameraPlayBinding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay.3
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return ((ActCameraPlayVM) ActCameraPlay.this.mViewModel).fragmentList.get(position);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return ((ActCameraPlayVM) ActCameraPlay.this.mViewModel).fragmentList.size();
            }
        });
        ((ActCameraPlayBinding) this.mViewBinding).viewpager.setUserInputEnabled(false);
        if (((ActCameraPlayBinding) this.mViewBinding).tabs.getTabCount() == 0) {
            int i = 0;
            while (i < ((ActCameraPlayVM) this.mViewModel).tabContentList.size()) {
                ((ActCameraPlayBinding) this.mViewBinding).tabs.addTab(((ActCameraPlayBinding) this.mViewBinding).tabs.newTab());
                ((ActCameraPlayVM) this.mViewModel).changeTab(((ActCameraPlayBinding) this.mViewBinding).tabs.getTabAt(i), i, i == 0);
                i++;
            }
            ((ActCameraPlayBinding) this.mViewBinding).tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay.4
                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabSelected(TabLayout.Tab tab) {
                    int position = tab.getPosition();
                    if (position < ((ActCameraPlayVM) ActCameraPlay.this.mViewModel).fragmentList.size()) {
                        ((ActCameraPlayBinding) ActCameraPlay.this.mViewBinding).viewpager.setCurrentItem(tab.getPosition());
                        ((ActCameraPlayVM) ActCameraPlay.this.mViewModel).changeTab(tab, tab.getPosition(), true);
                        if (position == ((ActCameraPlayVM) ActCameraPlay.this.mViewModel).getTalkTabIndex() && ActCameraPlay.this.checkAudioPermission()) {
                            ActCameraPlay.this.controlTalk(true);
                            return;
                        }
                        return;
                    }
                    if (position != ((ActCameraPlayVM) ActCameraPlay.this.mViewModel).fragmentList.size()) {
                        if (position == ((ActCameraPlayVM) ActCameraPlay.this.mViewModel).fragmentList.size() + 1) {
                            ActCameraPlay.this.record();
                            return;
                        }
                        return;
                    }
                    ActCameraPlay.this.shot();
                }

                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabUnselected(TabLayout.Tab tab) {
                    if (tab.getPosition() < ((ActCameraPlayVM) ActCameraPlay.this.mViewModel).fragmentList.size()) {
                        ((ActCameraPlayVM) ActCameraPlay.this.mViewModel).changeTab(tab, tab.getPosition(), false);
                    }
                    if (tab.getPosition() == ((ActCameraPlayVM) ActCameraPlay.this.mViewModel).getTalkTabIndex()) {
                        ActCameraPlay.this.controlTalk(false);
                    }
                }

                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabReselected(TabLayout.Tab tab) {
                    if (tab.getPosition() != ((ActCameraPlayVM) ActCameraPlay.this.mViewModel).fragmentList.size()) {
                        if (tab.getPosition() == ((ActCameraPlayVM) ActCameraPlay.this.mViewModel).fragmentList.size() + 1) {
                            ActCameraPlay.this.record();
                            return;
                        }
                        return;
                    }
                    ActCameraPlay.this.shot();
                }
            });
        }
        ((ActCameraPlayBinding) this.mViewBinding).tabs.selectTab(((ActCameraPlayBinding) this.mViewBinding).tabs.getTabAt(0));
    }

    private void initRealPlayView() {
        SurfaceView surfaceView = ((ActCameraPlayBinding) this.mViewBinding).surfaceView;
        this.mRealPlaySv = surfaceView;
        SurfaceHolder holder = surfaceView.getHolder();
        this.mRealPlaySh = holder;
        holder.addCallback(new SurfaceHolder.Callback() { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay.5
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder holder2, int format, int width, int height) {
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder holder2) {
                if (((ActCameraPlayVM) ActCameraPlay.this.mViewModel).ezPlayer != null) {
                    ((ActCameraPlayVM) ActCameraPlay.this.mViewModel).ezPlayer.setSurfaceHold(holder2);
                }
                ActCameraPlay.this.mRealPlaySh = holder2;
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder holder2) {
                if (((ActCameraPlayVM) ActCameraPlay.this.mViewModel).ezPlayer != null) {
                    ((ActCameraPlayVM) ActCameraPlay.this.mViewModel).ezPlayer.setSurfaceHold(null);
                }
                ActCameraPlay.this.mRealPlaySh = null;
            }
        });
        this.mRealPlaySv.setOnTouchListener(this.mRealPlayTouchListener);
    }

    private void initRealPlayView2() {
        SurfaceView surfaceView = ((ActCameraPlayBinding) this.mViewBinding).surfaceView2;
        this.mRealPlaySv2 = surfaceView;
        SurfaceHolder holder = surfaceView.getHolder();
        this.mRealPlaySh2 = holder;
        holder.addCallback(new SurfaceHolder.Callback() { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay.6
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder holder2, int format, int width, int height) {
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder holder2) {
                if (((ActCameraPlayVM) ActCameraPlay.this.mViewModel).ezPlayer2 != null) {
                    ((ActCameraPlayVM) ActCameraPlay.this.mViewModel).ezPlayer2.setSurfaceHold(holder2);
                }
                ActCameraPlay.this.mRealPlaySh2 = holder2;
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder holder2) {
                if (((ActCameraPlayVM) ActCameraPlay.this.mViewModel).ezPlayer2 != null) {
                    ((ActCameraPlayVM) ActCameraPlay.this.mViewModel).ezPlayer2.setSurfaceHold(null);
                }
                ActCameraPlay.this.mRealPlaySh2 = null;
            }
        });
        this.mRealPlaySv2.setOnTouchListener(this.mRealPlayTouchListener);
    }

    private void initPlaybackView() {
        ((ActCameraPlayBinding) this.mViewBinding).progressSeekbar.setMax(1000);
        ((ActCameraPlayBinding) this.mViewBinding).progressSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay.8
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar arg0) {
                int progress = arg0.getProgress();
                if (progress == 1000) {
                    ActCameraPlay.this.stopPlayBack();
                    ActCameraPlay.this.handlePlaySegmentOver();
                    return;
                }
                if (ActCameraPlay.this.currentClickItemFile != null) {
                    long beginTime = ActCameraPlay.this.currentClickItemFile.getBeginTime();
                    long endTime = ActCameraPlay.this.currentClickItemFile.getEndTime();
                    long j = (endTime - beginTime) / 1000;
                    long j2 = (progress * j) + beginTime;
                    LHomeLog.i(ActCameraPlay.class, "onSeekBarStopTracking, begin time:" + beginTime + " endtime:" + endTime + " avg:" + j + " MAX:1000 tracktime:" + j2);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date(j2));
                    if (((ActCameraPlayVM) ActCameraPlay.this.mViewModel).ezPlayer != null) {
                        ((ActCameraPlayVM) ActCameraPlay.this.mViewModel).ezPlayer.seekPlayback(calendar);
                    }
                    if (((ActCameraPlayVM) ActCameraPlay.this.mViewModel).ezPlayer2 != null) {
                        ((ActCameraPlayVM) ActCameraPlay.this.mViewModel).ezPlayer2.seekPlayback(calendar);
                    }
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                if (ActCameraPlay.this.currentClickItemFile != null) {
                    ((ActCameraPlayBinding) ActCameraPlay.this.mViewBinding).beginTimeTv.setText(RemoteListUtil.convToUIDuration(((int) (((ActCameraPlay.this.currentClickItemFile.getEndTime() - ActCameraPlay.this.currentClickItemFile.getBeginTime()) * arg1) / 1000)) / 1000));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void record() {
        if (checkWriteStoragePermission(WinError.ERROR_CANT_TERMINATE_SELF)) {
            if (!this.isRecord) {
                ((ActCameraPlayVM) this.mViewModel).ezPlayer.startLocalRecordWithFile(FileUtil.getEzRecordFilePath(this));
                if (((ActCameraPlayVM) this.mViewModel).ezPlayer2 != null) {
                    SystemClock.sleep(10L);
                    ((ActCameraPlayVM) this.mViewModel).ezPlayer2.startLocalRecordWithFile(FileUtil.getEzRecordFilePath(this));
                }
                this.recordTime = 0L;
                getMainHandler().post(this.setRecordRunnable);
            } else {
                stopRecord();
            }
            this.isRecord = !this.isRecord;
            showRecordTime();
            setRecordButtonView();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void shot() {
        if (checkWriteStoragePermission(WinError.ERROR_CANT_TERMINATE_SELF)) {
            showLoadingDialog(getString(R.string.saving));
            Bitmap capturePicture = ((ActCameraPlayVM) this.mViewModel).ezPlayer.capturePicture();
            if (capturePicture != null) {
                FileUtil.saveCameraBitmap(this, capturePicture, FileUtil.getEzShotFilePath(this));
                if (((ActCameraPlayVM) this.mViewModel).ezPlayer2 != null) {
                    SystemClock.sleep(10L);
                    FileUtil.saveCameraBitmap(this, ((ActCameraPlayVM) this.mViewModel).ezPlayer2.capturePicture(), FileUtil.getEzShotFilePath(this));
                }
                Utils.showToast(this, R.string.video_monitor_image_capture_success);
            } else {
                Utils.showToast(this, R.string.video_monitor_image_capture_failed);
            }
            dismissLoadingDialog();
        }
    }

    public boolean isRecordPage() {
        return ((ActCameraPlayBinding) this.mViewBinding).viewpager.getCurrentItem() == ((ActCameraPlayVM) this.mViewModel).getPlaybackTabIndex();
    }

    private boolean getSupportPtz() {
        EZDeviceInfo eZDeviceInfo;
        if (((ActCameraPlayVM) this.mViewModel).ezPlayer == null || (eZDeviceInfo = this.mDeviceInfo) == null) {
            return false;
        }
        return eZDeviceInfo.isSupportPTZ() || this.mDeviceInfo.isSupportZoom();
    }

    public int getSupportTalk() {
        EZDeviceInfo eZDeviceInfo;
        if (((ActCameraPlayVM) this.mViewModel).ezPlayer != null && (eZDeviceInfo = this.mDeviceInfo) != null) {
            if (eZDeviceInfo.isSupportTalk() == EZConstants.EZTalkbackCapability.EZTalkbackFullDuplex) {
                return 1;
            }
            if (this.mDeviceInfo.isSupportTalk() == EZConstants.EZTalkbackCapability.EZTalkbackHalfDuplex) {
                return 3;
            }
        }
        return 0;
    }

    public void controlTalk(boolean open) {
        if (this.isRecord) {
            stopRecord();
            this.isRecord = !this.isRecord;
            showRecordTime();
            SystemClock.sleep(100L);
        }
        if (open) {
            Utils.showToast(this, R.string.start_voice_talk);
            ((ActCameraPlayVM) this.mViewModel).ezPlayer.closeSound();
            this.mLocalInfo.setSoundOpen(false);
            setSoundButtonView();
            ((ActCameraPlayVM) this.mViewModel).ezPlayer.startVoiceTalk();
            return;
        }
        ((ActCameraPlayVM) this.mViewModel).ezPlayer.stopVoiceTalk();
        ((ActCameraPlayVM) this.mViewModel).ezPlayer.openSound();
        this.mLocalInfo.setSoundOpen(true);
        setSoundButtonView();
    }

    private void showView(int orientation) {
        if (orientation == 1) {
            showTitle(false);
            ((ActCameraPlayBinding) this.mViewBinding).ivFullBack.setVisibility(0);
            ((ActCameraPlayBinding) this.mViewBinding).tabs.setVisibility(8);
            ((ActCameraPlayBinding) this.mViewBinding).layoutController.setVisibility(8);
            ((ActCameraPlayBinding) this.mViewBinding).layoutController3.setVisibility(8);
            ((ActCameraPlayBinding) this.mViewBinding).viewpager.setVisibility(8);
            return;
        }
        showTitle(true);
        ((ActCameraPlayBinding) this.mViewBinding).ivFullBack.setVisibility(8);
        ((ActCameraPlayBinding) this.mViewBinding).tabs.setVisibility(0);
        ((ActCameraPlayBinding) this.mViewBinding).layoutController.setVisibility(0);
        ((ActCameraPlayBinding) this.mViewBinding).layoutController3.setVisibility(8);
        ((ActCameraPlayBinding) this.mViewBinding).viewpager.setVisibility(0);
        ((ActCameraPlayBinding) this.mViewBinding).handleViewFull.setVisibility(8);
        ((ActCameraPlayBinding) this.mViewBinding).talkViewFull.setVisibility(8);
    }

    private void changeVideo(int tab) {
        AppCompatTextView appCompatTextView = ((ActCameraPlayBinding) this.mViewBinding).tvVideo1;
        int i = R.drawable.shape_gray_bt_20;
        appCompatTextView.setBackgroundResource(tab == 0 ? R.drawable.shape_gray_bt_20 : R.drawable.shape_black_bg_20);
        ((ActCameraPlayBinding) this.mViewBinding).tvVideo1.setTypeface(tab == 0 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        AppCompatTextView appCompatTextView2 = ((ActCameraPlayBinding) this.mViewBinding).tvVideo2;
        if (tab != 1) {
            i = R.drawable.shape_black_bg_20;
        }
        appCompatTextView2.setBackgroundResource(i);
        ((ActCameraPlayBinding) this.mViewBinding).tvVideo2.setTypeface(tab == 1 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        ((ActCameraPlayBinding) this.mViewBinding).surfaceView.setVisibility(tab == 0 ? 0 : 8);
        ((ActCameraPlayBinding) this.mViewBinding).surfaceView2.setVisibility(tab != 1 ? 8 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ptzOption(final EZConstants.EZPTZCommand command, final EZConstants.EZPTZAction action) {
        ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay.11
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EZOpenSDK.getInstance().controlPTZ(ActCameraPlay.this.mCameraInfo.getDeviceSerial(), ActCameraPlay.this.mCameraInfo.getCameraNo(), command, action, 1);
                } catch (BaseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        if (this.mCameraInfo == null) {
            return;
        }
        if (this.mRealStatus != 2) {
            stopRealPlay();
        } else if (this.mBackstatus == 5) {
            pausePlayback();
        }
        if (this.isTalk) {
            controlTalk(false);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int i = newConfig.orientation == 2 ? 1 : 0;
        this.mOrientation = i;
        initWindow(i);
    }

    protected void initWindow(int orientation) {
        if (this.cameraNum == 2) {
            if (orientation == 1) {
                ((ActCameraPlayBinding) this.mViewBinding).layoutTab.setVisibility(0);
                changeVideo(0);
                this.mRealPlaySv.getLayoutParams().height = getResources().getDisplayMetrics().heightPixels;
                this.mRealPlaySv2.getLayoutParams().height = getResources().getDisplayMetrics().heightPixels;
            } else {
                ((ActCameraPlayBinding) this.mViewBinding).layoutTab.setVisibility(8);
                ((ActCameraPlayBinding) this.mViewBinding).surfaceView.setVisibility(0);
                ((ActCameraPlayBinding) this.mViewBinding).surfaceView2.setVisibility(0);
                this.mRealPlaySv.getLayoutParams().height = com.ltech.smarthome.utils.Utils.dp2px(this, 200.0f);
                this.mRealPlaySv2.getLayoutParams().height = com.ltech.smarthome.utils.Utils.dp2px(this, 200.0f);
            }
        } else if (orientation == 1) {
            this.mRealPlaySv.getLayoutParams().height = getResources().getDisplayMetrics().heightPixels;
        } else {
            this.mRealPlaySv.getLayoutParams().height = com.ltech.smarthome.utils.Utils.dp2px(this, 215.0f);
        }
        showView(orientation);
    }

    private void startRealPlay() {
        int i = this.mRealStatus;
        if (i == 1 || i == 3 || !ConnectionDetector.isNetworkAvailable(this)) {
            return;
        }
        if (getCamera(0) != null) {
            this.isRealPlay = true;
            this.mRealStatus = 1;
            ((ActCameraPlayVM) this.mViewModel).ezPlayer = EZOpenSDK.getInstance().createPlayer(this.mCameraInfo.getDeviceSerial(), getCamera(0).getCameraNo());
            if (((ActCameraPlayVM) this.mViewModel).ezPlayer == null) {
                return;
            }
            if (this.mDeviceInfo.getIsEncrypt() == 1) {
                ((ActCameraPlayVM) this.mViewModel).ezPlayer.setPlayVerifyCode(((CameraParam) ((ActCameraPlayVM) this.mViewModel).controlDevice.getValue().getParam(CameraParam.class)).getVerifyCode());
            }
            ((ActCameraPlayVM) this.mViewModel).ezPlayer.setHandler(this.mHandler);
            ((ActCameraPlayVM) this.mViewModel).ezPlayer.setSurfaceHold(this.mRealPlaySh);
            ((ActCameraPlayVM) this.mViewModel).ezPlayer.startRealPlay();
        }
        if (getCamera(1) != null) {
            this.isRealPlay = true;
            this.mRealStatus = 1;
            ((ActCameraPlayVM) this.mViewModel).ezPlayer2 = EZOpenSDK.getInstance().createPlayer(this.mCameraInfo.getDeviceSerial(), getCamera(1).getCameraNo());
            if (((ActCameraPlayVM) this.mViewModel).ezPlayer2 == null) {
                return;
            }
            if (this.mDeviceInfo.getIsEncrypt() == 1) {
                ((ActCameraPlayVM) this.mViewModel).ezPlayer2.setPlayVerifyCode(((CameraParam) ((ActCameraPlayVM) this.mViewModel).controlDevice.getValue().getParam(CameraParam.class)).getVerifyCode());
            }
            ((ActCameraPlayVM) this.mViewModel).ezPlayer2.setHandler(this.mHandler);
            ((ActCameraPlayVM) this.mViewModel).ezPlayer2.setSurfaceHold(this.mRealPlaySh2);
            ((ActCameraPlayVM) this.mViewModel).ezPlayer2.startRealPlay();
        }
    }

    private void stopRealPlay() {
        this.mRealStatus = 2;
        if (((ActCameraPlayVM) this.mViewModel).ezPlayer != null) {
            ((ActCameraPlayVM) this.mViewModel).ezPlayer.stopRealPlay();
        }
        if (((ActCameraPlayVM) this.mViewModel).ezPlayer2 != null) {
            ((ActCameraPlayVM) this.mViewModel).ezPlayer2.stopRealPlay();
        }
        if (this.mViewBinding != 0) {
            ((ActCameraPlayBinding) this.mViewBinding).ivPlay.setImageResource(R.mipmap.icon_camera_ic_start_play);
            ((ActCameraPlayBinding) this.mViewBinding).ivFullPlay.setImageResource(R.mipmap.icon_camera_ic_start_play);
        }
    }

    private void showLoadingView(boolean isShow) {
        if (this.mViewBinding != 0) {
            if (isShow) {
                ((ActCameraPlayBinding) this.mViewBinding).ivOffline.setVisibility(8);
                ((ActCameraPlayBinding) this.mViewBinding).tvLoading.setText(R.string.load);
            }
            ((ActCameraPlayBinding) this.mViewBinding).tvLoading.setVisibility(isShow ? 0 : 8);
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message msg) {
        if (isFinishing()) {
            return false;
        }
        LHomeLog.i(ActCameraPlay.class, "handleMessage:" + msg.what);
        int i = msg.what;
        if (i != 100) {
            if (i == 201) {
                handlePlaySegmentOver();
            } else if (i == 205) {
                handleFirstFrame(msg);
            } else if (i == 221) {
                this.mBackstatus = 0;
            } else if (i != 4012) {
                if (i == 5000) {
                    updateRemotePlayUI();
                } else if (i == 380061) {
                    handlePlaySegmentOver();
                } else if (i == 102) {
                    handlePlaySuccess();
                } else if (i == 103) {
                    handleError(msg.obj);
                } else if (i == 105) {
                    handleSetVideoModeSuccess();
                    dismissLoadingDialog();
                } else if (i == 106) {
                    showErrorDialog(getResources().getString(R.string.req_faild));
                } else {
                    switch (i) {
                        case 113:
                            this.isTalk = true;
                            ((FtCameraTalk) ((ActCameraPlayVM) this.mViewModel).fragmentList.get(((ActCameraPlayVM) this.mViewModel).getTalkTabIndex())).changeTalkView(true);
                            break;
                        case 114:
                            handleVoiceTalkFailed((ErrorInfo) msg.obj);
                            break;
                        case 115:
                            this.isTalk = false;
                            ((FtCameraTalk) ((ActCameraPlayVM) this.mViewModel).fragmentList.get(((ActCameraPlayVM) this.mViewModel).getTalkTabIndex())).changeTalkView(false);
                            break;
                        default:
                            switch (i) {
                            }
                    }
                }
            } else if (msg.arg1 == 380061) {
                handlePlaySegmentOver();
            }
            return false;
        }
        handleGetCameraInfoSuccess();
        showLoadingView(true);
        return false;
    }

    private void handleVoiceTalkFailed(ErrorInfo errorInfo) {
        switch (errorInfo.errorCode) {
            case ErrorCode.ERROR_TTS_MSG_REQ_TIMEOUT /* 360001 */:
            case ErrorCode.ERROR_TTS_MSG_SVR_HANDLE_TIMEOUT /* 360002 */:
            case ErrorCode.ERROR_TTS_WAIT_TIMEOUT /* 361001 */:
            case ErrorCode.ERROR_TTS_HNADLE_TIMEOUT /* 361002 */:
                Utils.showToast(this.activity, R.string.realplay_play_talkback_request_timeout, errorInfo.errorCode);
                break;
            case ErrorCode.ERROR_CAS_AUDIO_SOCKET_ERROR /* 382101 */:
            case ErrorCode.ERROR_CAS_AUDIO_RECV_ERROR /* 382102 */:
            case ErrorCode.ERROR_CAS_AUDIO_SEND_ERROR /* 382103 */:
                Utils.showToast(this.activity, R.string.realplay_play_talkback_network_exception, errorInfo.errorCode);
                break;
            case ErrorCode.ERROR_TRANSF_DEVICE_OFFLINE /* 400901 */:
                Utils.showToast(this.activity, R.string.camera_not_online);
                break;
            case ErrorCode.ERROR_TRANSF_DEVICE_TALKING /* 400904 */:
                Utils.showToast(this.activity, R.string.realplay_play_talkback_fail_reason);
                break;
            case ErrorCode.ERROR_TRANSF_DEVICE_PRIVACYON /* 400905 */:
                Utils.showToast(this.activity, R.string.realplay_play_talkback_fail_privacy);
                break;
            default:
                Utils.showToast(this.activity, R.string.realplay_play_talkback_fail, errorInfo.errorCode);
                break;
        }
    }

    private void handleError(Object obj) {
        String errorTip;
        int i = obj != null ? ((ErrorInfo) obj).errorCode : 0;
        switch (i) {
            case ErrorCode.ERROR_WEB_CODE_ERROR /* 101011 */:
                errorTip = Utils.getErrorTip(this, R.string.check_feature_code_fail, i);
                break;
            case ErrorCode.ERROR_WEB_HARDWARE_SIGNATURE_OP_ERROR /* 120005 */:
                errorTip = Utils.getErrorTip(this, R.string.check_feature_code_fail, i);
                break;
            case 380045:
                errorTip = getString(R.string.remoteplayback_over_link);
                break;
            case ErrorCode.ERROR_INNER_STREAM_TIMEOUT /* 400034 */:
                errorTip = getString(R.string.realplay_fail_connect_device);
                break;
            case ErrorCode.ERROR_INNER_VERIFYCODE_NEED /* 400035 */:
            case ErrorCode.ERROR_INNER_VERIFYCODE_ERROR /* 400036 */:
                errorTip = "verify code error";
                break;
            case ErrorCode.ERROR_TRANSF_DEVICE_OFFLINE /* 400901 */:
                EZCameraInfo eZCameraInfo = this.mCameraInfo;
                if (eZCameraInfo != null) {
                    eZCameraInfo.setIsShared(0);
                }
                String string = getString(R.string.camera_not_online);
                ((ActCameraPlayBinding) this.mViewBinding).ivOffline.setVisibility(0);
                ((ActCameraPlayBinding) this.mViewBinding).tvLoading.setText(string);
                return;
            case ErrorCode.ERROR_TRANSF_ACCESSTOKEN_ERROR /* 400902 */:
                errorTip = "access token error";
                break;
            case ErrorCode.ERROR_TRANSF_TERMINAL_BINDING /* 400903 */:
                errorTip = "请在萤石客户端关闭终端绑定Please close the terminal binding on the ezviz client";
                break;
            default:
                errorTip = Utils.getErrorTip(this, R.string.realplay_play_fail, i);
                break;
        }
        Toast.makeText(this, errorTip, 0).show();
        ((ActCameraPlayBinding) this.mViewBinding).tvLoading.setText(R.string.realplay_play_fail);
    }

    private void handleGetCameraInfoSuccess() {
        setRealPlayTalkUI();
        setVideoLevel();
    }

    private void setVideoLevel() {
        this.mCameraInfo.setVideoLevel(this.mCurrentQulityMode.getVideoLevel());
    }

    private void handlePlaySuccess() {
        showLoadingView(false);
        hideKbPreSecond();
        showKbPreSecond();
        this.mRealStatus = 3;
        this.mRealRatio = 0.5625f;
        ((ActCameraPlayBinding) this.mViewBinding).ivPlay.setImageResource(R.mipmap.icon_camera_ic_stop_play);
        ((ActCameraPlayBinding) this.mViewBinding).ivFullPlay.setImageResource(R.mipmap.icon_camera_ic_stop_play);
        ((ActCameraPlayBinding) this.mViewBinding).tvQuality.setVisibility(0);
        ((ActCameraPlayBinding) this.mViewBinding).tvFullQuality.setVisibility(0);
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay.12
            @Override // java.lang.Runnable
            public void run() {
                if (ActCameraPlay.this.mOrientation == 0) {
                    ActCameraPlay.this.saveShotImage();
                }
            }
        }, 1000L);
    }

    private void showHideFullScreenSubView() {
        ((ActCameraPlayBinding) this.mViewBinding).handleViewFull.setVisibility(this.visibleStatus);
        ((ActCameraPlayBinding) this.mViewBinding).ivFullSound.setVisibility(this.visibleStatus);
        ((ActCameraPlayBinding) this.mViewBinding).ivFullShot.setVisibility(this.visibleStatus);
        ((ActCameraPlayBinding) this.mViewBinding).ivFullRecord.setVisibility(this.visibleStatus);
        ((ActCameraPlayBinding) this.mViewBinding).ivFullTalk.setVisibility(this.visibleStatus);
        ((ActCameraPlayBinding) this.mViewBinding).ivSmall.setVisibility(this.visibleStatus);
    }

    /* renamed from: com.ltech.smarthome.ui.camera.play.ActCameraPlay$17, reason: invalid class name */
    static /* synthetic */ class AnonymousClass17 {
        static final /* synthetic */ int[] $SwitchMap$com$videogo$openapi$EZConstants$EZVideoLevel;

        static {
            int[] iArr = new int[EZConstants.EZVideoLevel.values().length];
            $SwitchMap$com$videogo$openapi$EZConstants$EZVideoLevel = iArr;
            try {
                iArr[EZConstants.EZVideoLevel.VIDEO_LEVEL_FLUNET.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$videogo$openapi$EZConstants$EZVideoLevel[EZConstants.EZVideoLevel.VIDEO_LEVEL_BALANCED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$videogo$openapi$EZConstants$EZVideoLevel[EZConstants.EZVideoLevel.VIDEO_LEVEL_HD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$videogo$openapi$EZConstants$EZVideoLevel[EZConstants.EZVideoLevel.VIDEO_LEVEL_SUPERCLEAR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private void setQualityTv() {
        int i = AnonymousClass17.$SwitchMap$com$videogo$openapi$EZConstants$EZVideoLevel[this.mCurrentQulityMode.ordinal()];
        if (i == 1) {
            ((ActCameraPlayBinding) this.mViewBinding).tvQuality.setText(R.string.quality_flunet);
            ((ActCameraPlayBinding) this.mViewBinding).tvFullQuality.setText(R.string.quality_flunet);
            return;
        }
        if (i == 2) {
            ((ActCameraPlayBinding) this.mViewBinding).tvQuality.setText(R.string.quality_balanced);
            ((ActCameraPlayBinding) this.mViewBinding).tvFullQuality.setText(R.string.quality_balanced);
        } else if (i == 3) {
            ((ActCameraPlayBinding) this.mViewBinding).tvQuality.setText(R.string.quality_hd);
            ((ActCameraPlayBinding) this.mViewBinding).tvFullQuality.setText(R.string.quality_hd);
        } else {
            if (i != 4) {
                return;
            }
            ((ActCameraPlayBinding) this.mViewBinding).tvQuality.setText(R.string.quality_super_hd);
            ((ActCameraPlayBinding) this.mViewBinding).tvFullQuality.setText(R.string.quality_super_hd);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getRecordTimeString() {
        return String.format("%s:%s:%s", this.df.format((this.recordTime / 3600) % 24), this.df.format((this.recordTime / 60) % 60), this.df.format(this.recordTime % 60));
    }

    private void showRecordTime() {
        if (this.isRecord) {
            ((ActCameraPlayBinding) this.mViewBinding).ivRecordTime.setVisibility(0);
            ((ActCameraPlayBinding) this.mViewBinding).tvRecordTime.setVisibility(0);
        } else {
            ((ActCameraPlayBinding) this.mViewBinding).ivRecordTime.setVisibility(8);
            ((ActCameraPlayBinding) this.mViewBinding).tvRecordTime.setVisibility(8);
        }
    }

    private void stopRecord() {
        EZOpenSDKListener.EZStreamDownloadCallback eZStreamDownloadCallback = new EZOpenSDKListener.EZStreamDownloadCallback() { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay.14
            @Override // com.videogo.openapi.EZOpenSDKListener.EZStreamDownloadCallback
            public void onError(EZOpenSDKListener.EZStreamDownloadError code) {
            }

            @Override // com.videogo.openapi.EZOpenSDKListener.EZStreamDownloadCallback
            public void onSuccess(String filepath) {
                FileUtil.saveVideo2Album(ActCameraPlay.this.activity, new File(filepath));
            }
        };
        ((ActCameraPlayVM) this.mViewModel).ezPlayer.setStreamDownloadCallback(eZStreamDownloadCallback);
        if (((ActCameraPlayVM) this.mViewModel).ezPlayer2 != null) {
            ((ActCameraPlayVM) this.mViewModel).ezPlayer2.setStreamDownloadCallback(eZStreamDownloadCallback);
        }
        ((ActCameraPlayVM) this.mViewModel).ezPlayer.stopLocalRecord();
        if (((ActCameraPlayVM) this.mViewModel).ezPlayer2 != null) {
            ((ActCameraPlayVM) this.mViewModel).ezPlayer2.stopLocalRecord();
        }
        Utils.showToast(this, R.string.video_monitor_video_record_success);
        getMainHandler().removeCallbacks(this.setRecordRunnable);
    }

    private void setSoundButtonView() {
        if (this.mLocalInfo.isSoundOpen()) {
            ((ActCameraPlayBinding) this.mViewBinding).ivSound.setImageResource(R.mipmap.icon_camera_ic_sound);
            ((ActCameraPlayBinding) this.mViewBinding).ivFullSound.setImageResource(R.mipmap.icon_camera_ic_sound);
        } else {
            ((ActCameraPlayBinding) this.mViewBinding).ivSound.setImageResource(R.mipmap.icon_camera_ic_quiet);
            ((ActCameraPlayBinding) this.mViewBinding).ivFullSound.setImageResource(R.mipmap.icon_camera_ic_quiet);
        }
    }

    private void setRecordButtonView() {
        int size = ((ActCameraPlayVM) this.mViewModel).tabContentList.size() - 1;
        if (this.mViewBinding != 0) {
            if (this.isRecord) {
                ((ActCameraPlayVM) this.mViewModel).changeTab(((ActCameraPlayBinding) this.mViewBinding).tabs.getTabAt(size), size, true);
                ((ActCameraPlayBinding) this.mViewBinding).ivFullRecord.setImageResource(R.mipmap.icon_camera_tab_video_sel);
            } else {
                ((ActCameraPlayVM) this.mViewModel).changeTab(((ActCameraPlayBinding) this.mViewBinding).tabs.getTabAt(size), size, false);
                ((ActCameraPlayBinding) this.mViewBinding).ivFullRecord.setImageResource(R.mipmap.icon_camera_full_video_default);
                ((ActCameraPlayVM) this.mViewModel).changeTab(((ActCameraPlayBinding) this.mViewBinding).tabs.getTabAt(((ActCameraPlayBinding) this.mViewBinding).viewpager.getCurrentItem()), ((ActCameraPlayBinding) this.mViewBinding).viewpager.getCurrentItem(), true);
            }
        }
    }

    private void showQualityDialog() {
        if (this.mCameraInfo == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        Iterator<EZVideoQualityInfo> it = this.mCameraInfo.getVideoQualityInfos().iterator();
        while (it.hasNext()) {
            int videoLevel = it.next().getVideoLevel();
            if (videoLevel == 0) {
                arrayList.add(getResources().getString(R.string.quality_flunet));
                arrayList2.add(0);
            } else if (videoLevel == 1) {
                arrayList.add(getResources().getString(R.string.quality_balanced));
                arrayList2.add(1);
            } else if (videoLevel == 2) {
                arrayList.add(getResources().getString(R.string.quality_hd));
                arrayList2.add(2);
            } else if (videoLevel == 3) {
                arrayList.add(getResources().getString(R.string.quality_super_hd));
                arrayList2.add(3);
            }
        }
        SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActCameraPlay.this.lambda$showQualityDialog$4(arrayList2, (Integer) obj);
            }
        }).setSelectList(arrayList).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showQualityDialog$4(List list, Integer num) {
        int intValue = ((Integer) list.get(num.intValue())).intValue();
        if (intValue == 0) {
            setQualityMode(EZConstants.EZVideoLevel.VIDEO_LEVEL_FLUNET);
            return;
        }
        if (intValue == 1) {
            setQualityMode(EZConstants.EZVideoLevel.VIDEO_LEVEL_BALANCED);
        } else if (intValue == 2) {
            setQualityMode(EZConstants.EZVideoLevel.VIDEO_LEVEL_HD);
        } else {
            if (intValue != 3) {
                return;
            }
            setQualityMode(EZConstants.EZVideoLevel.VIDEO_LEVEL_SUPERCLEAR);
        }
    }

    private void closeQualityPopupWindow() {
        PopupWindow popupWindow = this.mQualityPopupWindow;
        if (popupWindow != null) {
            dismissPopWindow(popupWindow);
            this.mQualityPopupWindow = null;
        }
    }

    private void dismissPopWindow(PopupWindow popupWindow) {
        if (popupWindow == null || this.activity.isFinishing()) {
            return;
        }
        popupWindow.dismiss();
    }

    private void setQualityMode(final EZConstants.EZVideoLevel mode) {
        if (!ConnectionDetector.isNetworkAvailable(this.activity)) {
            Utils.showToast(this.activity, R.string.realplay_set_fail_network);
        } else if (((ActCameraPlayVM) this.mViewModel).ezPlayer != null) {
            getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    ActCameraPlay.this.lambda$setQualityMode$5();
                }
            }, 100L);
            ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.ltech.smarthome.ui.camera.play.ActCameraPlay.15
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        EZManager.instance().getSDK().setVideoLevel(ActCameraPlay.this.mCameraInfo.getDeviceSerial(), ActCameraPlay.this.mCameraInfo.getCameraNo(), mode.getVideoLevel());
                        if (((ActCameraPlayVM) ActCameraPlay.this.mViewModel).ezPlayer2 != null) {
                            EZManager.instance().getSDK().setVideoLevel(ActCameraPlay.this.getCamera(1).getDeviceSerial(), ActCameraPlay.this.getCamera(1).getCameraNo(), mode.getVideoLevel());
                        }
                        ActCameraPlay.this.mCurrentQulityMode = mode;
                        Message obtain = Message.obtain();
                        obtain.what = 105;
                        ActCameraPlay.this.mHandler.sendMessage(obtain);
                    } catch (BaseException e) {
                        ActCameraPlay.this.mCurrentQulityMode = EZConstants.EZVideoLevel.VIDEO_LEVEL_FLUNET;
                        e.printStackTrace();
                        Message obtain2 = Message.obtain();
                        obtain2.what = 106;
                        ActCameraPlay.this.mHandler.sendMessage(obtain2);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setQualityMode$5() {
        showLoadingDialog(getString(R.string.setting_video_level));
    }

    private void handleSetVideoModeSuccess() {
        setQualityTv();
        closeQualityPopupWindow();
        setVideoLevel();
        if (this.mRealStatus == 3) {
            stopRealPlay();
            SystemClock.sleep(500L);
            startRealPlay();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRealPlayFlowTv(long streamFlow) {
        long j = streamFlow - this.mStreamFlow;
        if (j < 0) {
            j = 0;
        }
        String format = String.format("%.2f k/s ", Float.valueOf(j / 1024.0f));
        if (this.mViewBinding != 0) {
            ((ActCameraPlayBinding) this.mViewBinding).tvSpeed.setVisibility(0);
            ((ActCameraPlayBinding) this.mViewBinding).tvSpeed.setText(format);
        }
        this.mStreamFlow = streamFlow;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showKbPreSecond() {
        getMainHandler().postDelayed(this.kbPreSecondRunnable, 1000L);
    }

    private void hideKbPreSecond() {
        getMainHandler().removeCallbacks(this.kbPreSecondRunnable);
    }

    private int dp2px(int dipValue) {
        return (int) ((dipValue * getResources().getDisplayMetrics().density) + 0.5f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveShotImage() {
        Bitmap capturePicture;
        Bitmap capturePicture2 = ((ActCameraPlayVM) this.mViewModel).ezPlayer.capturePicture();
        if (capturePicture2 != null) {
            FileUtil.saveCameraBitmap(this, capturePicture2, PathManager.getCacheDir(ActivityUtils.getTopActivity()) + "/" + PathManager.getCameraPicName(this.mDeviceInfo.getDeviceSerial()));
        }
        if (((ActCameraPlayVM) this.mViewModel).ezPlayer2 == null || (capturePicture = ((ActCameraPlayVM) this.mViewModel).ezPlayer2.capturePicture()) == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(PathManager.getCacheDir(ActivityUtils.getTopActivity()));
        sb.append("/");
        sb.append(PathManager.getCameraPicName(this.mDeviceInfo.getDeviceSerial() + "_2"));
        FileUtil.saveCameraBitmap(this, capturePicture, sb.toString());
    }

    public boolean checkAudioPermission() {
        String[] strArr;
        int checkSelfPermission;
        if (Build.VERSION.SDK_INT >= 23) {
            if (VersionUtils.isAndroidQ()) {
                strArr = new String[]{Permission.RECORD_AUDIO};
            } else {
                strArr = new String[]{Permission.RECORD_AUDIO, Permission.READ_EXTERNAL_STORAGE};
            }
            checkSelfPermission = ActivityUtils.getTopActivity().checkSelfPermission(Permission.RECORD_AUDIO);
            if (checkSelfPermission != 0) {
                ActivityCompat.requestPermissions(ActivityUtils.getTopActivity(), strArr, 666);
                return false;
            }
        }
        return true;
    }

    public EZPlayer getPlayer() {
        return ((ActCameraPlayVM) this.mViewModel).ezPlayer;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (5001 == resultCode) {
            finishActivity();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (getResources().getConfiguration().orientation != 1) {
            setRequestedOrientation(1);
        } else {
            super.onBackPressed();
        }
    }

    public void startPlayback() {
        if (this.currentDeviceRecordInfo != null) {
            showLoadingView(true);
            startRecordOriginVideo();
            ((ActCameraPlayVM) this.mViewModel).ezPlayer.startPlaybackV2(EZPlaybackStreamParam.createBy(this.currentDeviceRecordInfo));
            if (((ActCameraPlayVM) this.mViewModel).ezPlayer2 != null) {
                ((ActCameraPlayVM) this.mViewModel).ezPlayer2.startPlaybackV2(EZPlaybackStreamParam.createBy(this.currentDeviceRecordInfo));
            }
        }
    }

    private void startRecordOriginVideo() {
        String str = EzConfig.getStreamsFolder(this) + "/origin_video_play_back_" + getSimpleTimeInfoForTmpFile() + ".ps";
        VideoFileUtil.startRecordOriginVideo(((ActCameraPlayVM) this.mViewModel).ezPlayer, str);
        if (((ActCameraPlayVM) this.mViewModel).ezPlayer2 != null) {
            VideoFileUtil.startRecordOriginVideo(((ActCameraPlayVM) this.mViewModel).ezPlayer2, str);
        }
    }

    private String getSimpleTimeInfoForTmpFile() {
        return new SimpleDateFormat("yyyyMMdd_HH_mm_ss").format(new Date());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopPlayBack() {
        try {
            if (((ActCameraPlayVM) this.mViewModel).ezPlayer != null) {
                ((ActCameraPlayVM) this.mViewModel).ezPlayer.stopPlayback();
                ((ActCameraPlayVM) this.mViewModel).ezPlayer.stopLocalRecord();
                ((ActCameraPlayBinding) this.mViewBinding).ivPlay.setImageResource(R.mipmap.icon_camera_ic_start_play);
                ((ActCameraPlayBinding) this.mViewBinding).ivFullPlay.setImageResource(R.mipmap.icon_camera_ic_start_play);
            }
            if (((ActCameraPlayVM) this.mViewModel).ezPlayer2 != null) {
                ((ActCameraPlayVM) this.mViewModel).ezPlayer2.stopPlayback();
                ((ActCameraPlayVM) this.mViewModel).ezPlayer2.stopLocalRecord();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pausePlayback() {
        if (this.mBackstatus == 5) {
            this.mBackstatus = 3;
            if (((ActCameraPlayVM) this.mViewModel).ezPlayer != null) {
                ((ActCameraPlayVM) this.mViewModel).ezPlayer.pausePlayback();
            }
            if (((ActCameraPlayVM) this.mViewModel).ezPlayer2 != null) {
                ((ActCameraPlayVM) this.mViewModel).ezPlayer2.pausePlayback();
            }
            if (this.mViewBinding != 0) {
                ((ActCameraPlayBinding) this.mViewBinding).ivPlay.setImageResource(R.mipmap.icon_camera_ic_start_play);
                ((ActCameraPlayBinding) this.mViewBinding).ivFullPlay.setImageResource(R.mipmap.icon_camera_ic_start_play);
            }
        }
    }

    private void resumePlayback() {
        if (this.mRealStatus == 3) {
            if (((ActCameraPlayVM) this.mViewModel).ezPlayer != null) {
                ((ActCameraPlayVM) this.mViewModel).ezPlayer.resumePlayback();
            }
            if (((ActCameraPlayVM) this.mViewModel).ezPlayer2 != null) {
                ((ActCameraPlayVM) this.mViewModel).ezPlayer2.resumePlayback();
            }
            this.mBackstatus = 5;
            if (this.mViewBinding != 0) {
                ((ActCameraPlayBinding) this.mViewBinding).ivPlay.setImageResource(R.mipmap.icon_camera_ic_stop_play);
                ((ActCameraPlayBinding) this.mViewBinding).ivFullPlay.setImageResource(R.mipmap.icon_camera_ic_stop_play);
            }
        }
    }

    public void timeBucketUIInit(long beginTime, long endTime) {
        String convToUIDuration = RemoteListUtil.convToUIDuration(((int) (endTime - beginTime)) / 1000);
        if (this.mViewBinding != 0) {
            ((ActCameraPlayBinding) this.mViewBinding).beginTimeTv.setText(RemoteListContant.VIDEO_DUAR_BEGIN_INIT);
            ((ActCameraPlayBinding) this.mViewBinding).endTimeTv.setText(convToUIDuration);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePlaySegmentOver() {
        LHomeLog.e(getClass(), "handlePlaySegmentOver");
        stopPlayBack();
        ((ActCameraPlayBinding) this.mViewBinding).progressArea.setVisibility(8);
    }

    private void handleFirstFrame(Message msg) {
        this.isRealPlay = false;
        showLoadingView(false);
        ((ActCameraPlayBinding) this.mViewBinding).tvQuality.setVisibility(8);
        ((ActCameraPlayBinding) this.mViewBinding).tvFullQuality.setVisibility(8);
        ((ActCameraPlayBinding) this.mViewBinding).ivFullPtz.setVisibility(8);
        ((ActCameraPlayBinding) this.mViewBinding).ivFullTalk.setVisibility(8);
        if (msg.arg1 != 0) {
            this.mRealRatio = msg.arg2 / msg.arg1;
        }
        this.mBackstatus = 5;
        ((ActCameraPlayBinding) this.mViewBinding).ivPlay.setImageResource(R.mipmap.icon_camera_ic_stop_play);
        ((ActCameraPlayBinding) this.mViewBinding).ivFullPlay.setImageResource(R.mipmap.icon_camera_ic_stop_play);
        ((ActCameraPlayBinding) this.mViewBinding).progressArea.setVisibility(0);
        if (this.mLocalInfo.isSoundOpen()) {
            ((ActCameraPlayVM) this.mViewModel).ezPlayer.openSound();
            if (((ActCameraPlayVM) this.mViewModel).ezPlayer2 != null) {
                ((ActCameraPlayVM) this.mViewModel).ezPlayer2.openSound();
            }
        } else {
            ((ActCameraPlayVM) this.mViewModel).ezPlayer.closeSound();
            if (((ActCameraPlayVM) this.mViewModel).ezPlayer2 != null) {
                ((ActCameraPlayVM) this.mViewModel).ezPlayer2.closeSound();
            }
        }
        this.mHandler.sendEmptyMessage(5000);
        ((ActCameraPlayBinding) this.mViewBinding).progressSeekbar.setVisibility(0);
    }

    private void updateRemotePlayUI() {
        Calendar oSDTime;
        if (((ActCameraPlayVM) this.mViewModel).ezPlayer != null && this.mBackstatus == 5 && (oSDTime = ((ActCameraPlayVM) this.mViewModel).ezPlayer.getOSDTime()) != null) {
            handlePlayProgress(oSDTime);
        }
        this.mHandler.sendEmptyMessageAtTime(5000, SystemClock.uptimeMillis() + 1000);
    }

    private void handlePlayProgress(Calendar osdTime) {
        long timeInMillis = osdTime.getTimeInMillis();
        long beginTime = this.currentClickItemFile.getBeginTime();
        long endTime = this.currentClickItemFile.getEndTime();
        int i = (int) ((r0 * 1000) / (endTime - beginTime));
        ((ActCameraPlayBinding) this.mViewBinding).progressSeekbar.setProgress(i);
        LHomeLog.i(getClass(), "handlePlayProgress, begin time:" + beginTime + " endtime:" + endTime + " osdTime:" + osdTime.getTimeInMillis() + " progress:" + i);
        ((ActCameraPlayBinding) this.mViewBinding).beginTimeTv.setText(RemoteListUtil.convToUIDuration((int) ((timeInMillis - beginTime) / 1000)));
    }
}