package com.ltech.smarthome.ui.control;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import anet.channel.util.StringUtils;
import anetwork.channel.util.RequestConstant;
import com.aispeech.dca.DcaListener;
import com.aispeech.dca.DcaSdk;
import com.akuvox.mobile.libcommon.model.media.MediaManager;
import com.alibaba.sdk.android.push.popup.PopupNotifyClick;
import com.alibaba.sdk.android.push.popup.PopupNotifyClickListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jaeger.library.StatusBarUtil;
import com.justalk.cloud.lemon.MtcUeConstants;
import com.ltech.imageclip.fragment.ActivityResultHelper;
import com.ltech.smarthome.MyApplication;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.blemesh.BleMeshManager;
import com.ltech.smarthome.blemesh.IConnectBleCallback;
import com.ltech.smarthome.blemesh.IScanCallback;
import com.ltech.smarthome.blemesh.bean.ExtendedBluetoothDevice;
import com.ltech.smarthome.databinding.ActHomeBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.VersionInfo;
import com.ltech.smarthome.nfc.BaseNfcActivity;
import com.ltech.smarthome.push.PushContentParamKey;
import com.ltech.smarthome.push.PushDataHelper;
import com.ltech.smarthome.service.UpdateApkService;
import com.ltech.smarthome.ui.config.ActMeshScan;
import com.ltech.smarthome.ui.config.ActQrCodeScan;
import com.ltech.smarthome.ui.control.ActHomeVM;
import com.ltech.smarthome.ui.home.ActCreateHome;
import com.ltech.smarthome.ui.intercom.IntercomService;
import com.ltech.smarthome.ui.permission.ActGetMeshPermission;
import com.ltech.smarthome.ui.voicecall.VoiceCallService;
import com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager;
import com.ltech.smarthome.utils.CodeLibraryUtil;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.LocationHelper;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.util.TextInfo;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.yanzhenjie.permission.runtime.Permission;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ActHome extends BaseNfcActivity<ActHomeBinding, FtRoomVM> {
    private long selectFloorId;
    private long selectRoomId;
    private VersionInfo versionInfo;
    private boolean firstIn = true;
    private final ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: com.ltech.smarthome.ui.control.ActHome.17
        @Override // androidx.activity.result.ActivityResultCallback
        public void onActivityResult(ActivityResult result) {
            Intent data;
            final String stringExtra;
            if (result.getResultCode() != -1 || (data = result.getData()) == null || (stringExtra = data.getStringExtra("MT")) == null) {
                return;
            }
            final List<Device> onlineGateways = Injection.repo().device().getOnlineGateways();
            if (onlineGateways.isEmpty()) {
                SmartToast.showCenterShort("没有在线的网关");
                return;
            }
            ArrayList arrayList = new ArrayList();
            for (Device device : onlineGateways) {
                if (ProductId.ID_ANDROID_SUPER_PANEL_12S.equals(device.getProductId())) {
                    arrayList.add(device.getName());
                }
            }
            SelectListDialog.asDefault(true).setSelectList(arrayList).setConfirmString(ActHome.this.getString(R.string.ok)).setConfirmAction(new IAction<Integer>() { // from class: com.ltech.smarthome.ui.control.ActHome.17.1
                @Override // com.ltech.smarthome.base.IAction
                public void act(Integer integer) {
                    ActHome.this.sendMatterQrcode(((Device) onlineGateways.get(integer.intValue())).getDeviceId(), stringExtra);
                }
            }).showDialog(ActHome.this.activity);
        }
    });

    static /* synthetic */ void lambda$setLanguage$10(Object obj) throws Exception {
    }

    static /* synthetic */ void lambda$setLanguage$11(Object obj) throws Exception {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean isSupportExit() {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_home;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.delayInit(getApplication().getApplicationContext());
        if (AppUtils.isAppDebug()) {
            addTestButton();
        }
        showLoadingDialog(getString(R.string.loading), 30000);
        ((FtRoomVM) this.mViewModel).placeList = Injection.repo().home().getPlaceList(this);
        handleData(((FtRoomVM) this.mViewModel).placeList, new IAction() { // from class: com.ltech.smarthome.ui.control.ActHome$$ExternalSyntheticLambda14
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActHome.this.lambda$onCreate$0((List) obj);
            }
        });
        closeAndroidPDialog();
        initDca();
        if (!isNetworkConnected()) {
            SmartToast.showShort(getString(R.string.no_network));
        }
        setLanguage();
        new PopupNotifyClick(new PopupNotifyClickListener(this) { // from class: com.ltech.smarthome.ui.control.ActHome.1
            @Override // com.alibaba.sdk.android.push.popup.PopupNotifyClickListener
            public void onSysNoticeOpened(String title, String summary, Map<String, String> mapData) {
                LHomeLog.d(getClass(), "Receive notification, title: " + title + ", content: " + summary + ", extraMap: " + mapData);
                if (mapData.containsKey(PushContentParamKey.VOICE_CALL_GROUP)) {
                    String str = mapData.get(PushContentParamKey.VOICE_CALL_GROUP);
                    LHomeLog.d(getClass(), "extraMap: " + str);
                    try {
                        JSONObject jSONObject = new JSONObject(str);
                        if (jSONObject.has(PushContentParamKey.VOICE_CALL_GROUP_ID)) {
                            final long j = jSONObject.getLong(PushContentParamKey.VOICE_CALL_GROUP_ID);
                            ThreadUtils.getMainHandler().postDelayed(new Runnable(this) { // from class: com.ltech.smarthome.ui.control.ActHome.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    VoiceCallManager.getInstance().inviteGroupChat(j);
                                }
                            }, 3000L);
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                if (mapData.containsKey(PushContentParamKey.DEVICE_ID)) {
                    Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(Long.parseLong(mapData.get(PushContentParamKey.DEVICE_ID)));
                    if (deviceByDeviceId != null) {
                        NavHelper.goSetting(deviceByDeviceId);
                    }
                }
            }
        }).onCreate(this, getIntent());
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.control.ActHome$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                ActHome.this.lambda$onCreate$1();
            }
        }, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(List list) {
        if (this.firstIn) {
            initFragment();
            initIot();
            initMesh();
            startLocationService();
            Injection.repo().device().resetDeviceOnlineState(-1L);
            PushDataHelper.getNoHandleData();
            this.firstIn = false;
        }
        ((ActHomeBinding) this.mViewBinding).group.setVisibility(8);
        ((ActHomeBinding) this.mViewBinding).groupBg.setVisibility(8);
        ((ActHomeBinding) this.mViewBinding).groupContent.setVisibility(0);
        ((ActHomeBinding) this.mViewBinding).tabs.setVisibility(0);
        ((ActHomeBinding) this.mViewBinding).viewpager.setVisibility(0);
    }

    @Override // com.ltech.smarthome.nfc.BaseNfcActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if ("android.nfc.action.TECH_DISCOVERED".equals(intent.getAction())) {
            Parcelable parcelableExtra = intent.getParcelableExtra("android.nfc.extra.TAG");
            if (parcelableExtra != null) {
                readNfcData((Tag) parcelableExtra);
                return;
            }
            return;
        }
        new PopupNotifyClick(new PopupNotifyClickListener(this) { // from class: com.ltech.smarthome.ui.control.ActHome.2
            @Override // com.alibaba.sdk.android.push.popup.PopupNotifyClickListener
            public void onSysNoticeOpened(String title, String summary, Map<String, String> mapData) {
                LHomeLog.d(getClass(), "new Receive notification, title: " + title + ", content: " + summary + ", extraMap: " + mapData);
                if (mapData.containsKey(PushContentParamKey.DEVICE_ID) && mapData.get(PushContentParamKey.DEVICE_ID) != null) {
                    Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(Long.parseLong(mapData.get(PushContentParamKey.DEVICE_ID)));
                    if (deviceByDeviceId != null) {
                        NavHelper.goSetting(deviceByDeviceId);
                        return;
                    }
                    return;
                }
                if (mapData.containsKey(PushContentParamKey.VOICE_CALL_GROUP)) {
                    String str = mapData.get(PushContentParamKey.VOICE_CALL_GROUP);
                    LHomeLog.d(getClass(), "extraMap: " + str);
                    try {
                        JSONObject jSONObject = new JSONObject(str);
                        if (jSONObject.has(PushContentParamKey.VOICE_CALL_GROUP_ID)) {
                            final long j = jSONObject.getLong(PushContentParamKey.VOICE_CALL_GROUP_ID);
                            ThreadUtils.getMainHandler().postDelayed(new Runnable(this) { // from class: com.ltech.smarthome.ui.control.ActHome.2.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    VoiceCallManager.getInstance().inviteGroupChat(j);
                                }
                            }, 3000L);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).onNewIntent(intent);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((FtRoomVM) this.mViewModel).editRoleMode.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.ActHome$$ExternalSyntheticLambda11
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActHome.this.lambda$startObserve$2((Boolean) obj);
            }
        });
        ((FtRoomVM) this.mViewModel).editSceneMode.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.ActHome$$ExternalSyntheticLambda12
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActHome.this.lambda$startObserve$3((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Boolean bool) {
        if (bool.booleanValue()) {
            ((ActHomeBinding) this.mViewBinding).tabs.setVisibility(8);
        } else {
            ((ActHomeBinding) this.mViewBinding).tabs.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Boolean bool) {
        if (bool.booleanValue()) {
            ((ActHomeBinding) this.mViewBinding).tabs.setVisibility(8);
        } else {
            ((ActHomeBinding) this.mViewBinding).tabs.setVisibility(0);
        }
    }

    @Override // com.ltech.smarthome.nfc.BaseNfcActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (Boolean.TRUE.equals(((FtRoomVM) this.mViewModel).editRoleMode.getValue())) {
            getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.control.ActHome.3
                @Override // java.lang.Runnable
                public void run() {
                    ((FtRoomVM) ActHome.this.mViewModel).editRoleMode.setValue(((FtRoomVM) ActHome.this.mViewModel).editRoleMode.getValue());
                }
            }, 200L);
        }
        if (Boolean.TRUE.equals(((FtRoomVM) this.mViewModel).editSceneMode.getValue())) {
            getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.control.ActHome.4
                @Override // java.lang.Runnable
                public void run() {
                    ((FtRoomVM) ActHome.this.mViewModel).editSceneMode.setValue(((FtRoomVM) ActHome.this.mViewModel).editSceneMode.getValue());
                }
            }, 200L);
        }
        startService();
    }

    public void startService() {
        ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.control.ActHome.5
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (AppUtils.isAppForeground()) {
                        ActHome.this.startService(new Intent(MyApplication.getContext(), (Class<?>) VoiceCallService.class));
                        ActHome.this.startService(new Intent(MyApplication.getContext(), (Class<?>) IntercomService.class));
                    }
                } catch (IllegalStateException e) {
                    e.getClass().getName().equals("android.app.BackgroundServiceStartNotAllowedException");
                    e.printStackTrace();
                }
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: initIntercom, reason: merged with bridge method [inline-methods] */
    public void lambda$onCreate$1() {
        Injection.intercom().init(this, this);
        Injection.intercom().login();
    }

    private void initDca() {
        Injection.dca().init(this);
        Injection.dca().setAuthCallBack(null);
    }

    private void addTestButton() {
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.content);
        Button button = new Button(this);
        button.setId(111111);
        Button button2 = new Button(this);
        button2.setId(222222);
        Button button3 = new Button(this);
        button3.setId(333333);
        Button button4 = new Button(this);
        button4.setId(444444);
        Button button5 = new Button(this);
        button5.setId(555555);
        new Button(this).setId(666666);
        new Button(this).setId(777777);
        new Button(this).setId(888888);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(-2, -2);
        layoutParams.topToTop = 0;
        layoutParams.leftToLeft = 0;
        layoutParams.topMargin = SizeUtils.dp2px(100.0f);
        button.setLayoutParams(layoutParams);
        ConstraintLayout.LayoutParams layoutParams2 = new ConstraintLayout.LayoutParams(-2, -2);
        layoutParams2.topToBottom = button.getId();
        layoutParams2.leftToLeft = 0;
        layoutParams2.topMargin = SizeUtils.dp2px(20.0f);
        button2.setLayoutParams(layoutParams2);
        ConstraintLayout.LayoutParams layoutParams3 = new ConstraintLayout.LayoutParams(-2, -2);
        layoutParams3.topToBottom = button2.getId();
        layoutParams3.leftToLeft = 0;
        layoutParams3.topMargin = SizeUtils.dp2px(20.0f);
        button3.setLayoutParams(layoutParams3);
        ConstraintLayout.LayoutParams layoutParams4 = new ConstraintLayout.LayoutParams(-2, -2);
        layoutParams4.topToBottom = button3.getId();
        layoutParams4.leftToLeft = 0;
        layoutParams4.topMargin = SizeUtils.dp2px(20.0f);
        button4.setLayoutParams(layoutParams4);
        ConstraintLayout.LayoutParams layoutParams5 = new ConstraintLayout.LayoutParams(-2, -2);
        layoutParams5.topToBottom = button4.getId();
        layoutParams5.leftToLeft = 0;
        layoutParams5.topMargin = SizeUtils.dp2px(20.0f);
        button5.setLayoutParams(layoutParams5);
        constraintLayout.addView(button);
        constraintLayout.addView(button2);
        constraintLayout.addView(button3);
        constraintLayout.addView(button4);
        constraintLayout.addView(button5);
        String[] strArr = {"查看当前连接设备", "直连超级面板"};
        button.setText(strArr[0]);
        button2.setText(strArr[1]);
        button3.setText("导出");
        button4.setText("思必驰测试");
        button5.setText("扫码Matter设备");
        button.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.ActHome.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ActHome.this.testGetConnectBlueDevice();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.ActHome.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ActHome.this.testConnectSuperPanel();
            }
        });
        button3.setOnClickListener(new View.OnClickListener(this) { // from class: com.ltech.smarthome.ui.control.ActHome.8
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Injection.mesh().export();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.ActHome.9
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ActHome.this.testDcaGetDeviceList(Constants.DCA_PRODUCT_ID_MINI);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.ActHome.10
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ActHome.this.startForResult.launch(new Intent(ActHome.this.activity, (Class<?>) ActQrCodeScan.class));
            }
        });
    }

    private void testCodeLibrary() {
        LHomeLog.i(RequestConstant.ENV_TEST, getClass(), "codelibrary-->" + CodeLibraryUtil.generateCurtainCodeLibrary(2088));
    }

    private void startLocationService() {
        if (LocationHelper.hasLocationPermission(this)) {
            MyApplication.getLocationService().registerListener(LocationHelper.getLocationListener());
            MyApplication.getLocationService().start();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void showEmpty() {
        dismissLoadingDialog();
        ((ActHomeBinding) this.mViewBinding).group.setVisibility(0);
        ((ActHomeBinding) this.mViewBinding).groupBg.setVisibility(0);
        ((ActHomeBinding) this.mViewBinding).groupContent.setVisibility(8);
        ((ActHomeBinding) this.mViewBinding).tabs.setVisibility(8);
        ((ActHomeBinding) this.mViewBinding).viewpager.setVisibility(8);
        showContent();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        ((FtRoomVM) this.mViewModel).placeList.retry();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((ActHomeBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.control.ActHome$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActHome.this.lambda$initView$4((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$4(View view) {
        int id = view.getId();
        if (id == R.id.bt_create) {
            NavUtils.destination(ActCreateHome.class).navigation(this);
            return;
        }
        if (id == R.id.bt_join) {
            if (checkPhotoPermission()) {
                NavUtils.destination(ActQrCodeScan.class).navigation(this);
            }
        } else {
            if (id != R.id.tv_logout) {
                return;
            }
            showLoadingDialog("");
            Injection.logout();
        }
    }

    private void initIot() {
        Injection.iot().init();
    }

    private void initMesh() {
        Injection.mesh().init();
    }

    private void initFragment() {
        ((FtRoomVM) this.mViewModel).initTabList();
        ((ActHomeBinding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.control.ActHome.11
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return ((FtRoomVM) ActHome.this.mViewModel).getTabContentList().get(position).getFragment();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return ((FtRoomVM) ActHome.this.mViewModel).getTabContentList().size();
            }
        });
        ((ActHomeBinding) this.mViewBinding).viewpager.setUserInputEnabled(false);
        ((ActHomeBinding) this.mViewBinding).tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.control.ActHome.12
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                ActHome actHome = ActHome.this;
                actHome.changeTab(tab, ((FtRoomVM) actHome.mViewModel).getTabContentList().get(tab.getPosition()), true);
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                ActHome actHome = ActHome.this;
                actHome.changeTab(tab, ((FtRoomVM) actHome.mViewModel).getTabContentList().get(tab.getPosition()), false);
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
                ActHome actHome = ActHome.this;
                actHome.changeTab(tab, ((FtRoomVM) actHome.mViewModel).getTabContentList().get(tab.getPosition()), true);
            }
        });
        new TabLayoutMediator(((ActHomeBinding) this.mViewBinding).tabs, ((ActHomeBinding) this.mViewBinding).viewpager, true, false, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ltech.smarthome.ui.control.ActHome$$ExternalSyntheticLambda13
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i) {
                ActHome.this.lambda$initFragment$5(tab, i);
            }
        }).attach();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initFragment$5(TabLayout.Tab tab, int i) {
        changeTab(tab, ((FtRoomVM) this.mViewModel).getTabContentList().get(i), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeTab(TabLayout.Tab tab, ActHomeVM.TabContent tabContent, boolean select) {
        View customView = tab.getCustomView();
        if (customView == null) {
            customView = LayoutInflater.from(this).inflate(R.layout.layout_tab_view, (ViewGroup) null);
            tab.setCustomView(customView);
        }
        AppCompatTextView appCompatTextView = (AppCompatTextView) customView.findViewById(R.id.tv_tab);
        AppCompatImageView appCompatImageView = (AppCompatImageView) customView.findViewById(R.id.iv_tab);
        appCompatTextView.setText(tabContent.getTitleRes());
        if (select) {
            appCompatImageView.setImageResource(tabContent.getSelectIconRes());
            appCompatTextView.setTextColor(ContextCompat.getColor(this, R.color.color_text_red));
        } else {
            appCompatImageView.setImageResource(tabContent.getUnSelectIconRes());
            appCompatTextView.setTextColor(ContextCompat.getColor(this, R.color.color_text_gray));
        }
    }

    private void closeAndroidPDialog() {
        try {
            Class.forName("android.content.pm.PackageParser$Package").getDeclaredConstructor(String.class).setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread", null);
            declaredMethod.setAccessible(true);
            Object invoke = declaredMethod.invoke(null, null);
            Field declaredField = cls.getDeclaredField("mHiddenApiWarningShown");
            declaredField.setAccessible(true);
            declaredField.setBoolean(invoke, true);
        } catch (Exception e2) {
            LHomeLog.d(getClass(), e2.getMessage());
        }
    }

    private void checkVersion() {
        PackageInfo packageInfo;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            packageInfo = null;
        }
        final int i = packageInfo.versionCode;
        ((ObservableSubscribeProxy) Injection.net().checkAppVersion("Android", i + "", getPackageName()).delaySubscription(1000L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.ActHome$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActHome.this.lambda$checkVersion$6(i, (VersionInfo) obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.control.ActHome.13
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                ActHome.this.showPermissionActivity();
                LHomeLog.i(getClass(), throwable.getMessage());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkVersion$6(int i, VersionInfo versionInfo) throws Exception {
        this.versionInfo = versionInfo;
        if (versionInfo.getAppversionnum() > i) {
            findNewVersion(this.versionInfo);
        } else {
            showPermissionActivity();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPermissionActivity() {
        if (SharedPreferenceUtil.queryBooleanValue(Constants.PHONE_LOCATION_PERMISSION_HOME_PAGE_NEED_SHOW, true)) {
            SharedPreferenceUtil.edit().keepShared(Constants.PHONE_LOCATION_PERMISSION_HOME_PAGE_NEED_SHOW, false).commit();
            ActivityResultHelper.init((FragmentActivity) ActivityUtils.getTopActivity()).startActivityForResult(ActGetMeshPermission.class, new ActivityResultHelper.Callback() { // from class: com.ltech.smarthome.ui.control.ActHome$$ExternalSyntheticLambda5
                @Override // com.ltech.imageclip.fragment.ActivityResultHelper.Callback
                public final void onActivityResult(int i, Intent intent) {
                    ActHome.lambda$showPermissionActivity$7(i, intent);
                }
            });
        }
    }

    static /* synthetic */ void lambda$showPermissionActivity$7(int i, Intent intent) {
        if (i == 1002) {
            LHomeLog.i(ActMeshScan.class, "onCreate: GET_MESH_PERMISSION_SUCCESS");
        }
    }

    private void findNewVersion(final VersionInfo versionInfo) {
        TextInfo textInfo = new TextInfo();
        textInfo.setGravity(GravityCompat.START);
        MessageDialog.show(this, getString(R.string.app_find_new_version) + "V " + versionInfo.getAppversioncode(), getString(R.string.find_new_version_tip)).setOkButton(getString(R.string.update_now), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.control.ActHome$$ExternalSyntheticLambda6
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$findNewVersion$8;
                lambda$findNewVersion$8 = ActHome.this.lambda$findNewVersion$8(versionInfo, baseDialog, view);
                return lambda$findNewVersion$8;
            }
        }).setCancelButton(getString(R.string.update_later), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.control.ActHome.14
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                ActHome.this.showPermissionActivity();
                return false;
            }
        }).setMessageTextInfo(textInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$findNewVersion$8(VersionInfo versionInfo, BaseDialog baseDialog, View view) {
        Intent intent = new Intent(this, (Class<?>) UpdateApkService.class);
        intent.putExtra("versionInfo", versionInfo);
        startService(intent);
        return false;
    }

    @Override // com.ltech.smarthome.base.VMActivity
    public void refreshData() {
        ((FtRoomVM) this.mViewModel).placeList.refresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void testConnectSuperPanel() {
        final Device superPanel = Injection.repo().device().getSuperPanel();
        if (superPanel != null) {
            SmartToast.showShort("开始扫描");
            Injection.mesh().startScan(BleMeshManager.MESH_PROXY_UUID, new IScanCallback() { // from class: com.ltech.smarthome.ui.control.ActHome$$ExternalSyntheticLambda10
                @Override // com.ltech.smarthome.blemesh.IScanCallback
                public final void onScanResult(ExtendedBluetoothDevice extendedBluetoothDevice) {
                    ActHome.this.lambda$testConnectSuperPanel$9(superPanel, extendedBluetoothDevice);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$testConnectSuperPanel$9(final Device device, ExtendedBluetoothDevice extendedBluetoothDevice) {
        if (addDeviceForProxy(extendedBluetoothDevice)) {
            SmartToast.showShort("已搜索到超级面板，停止扫描，准备连接");
            Injection.mesh().stopScan();
            Injection.mesh().connect(extendedBluetoothDevice, new IConnectBleCallback(this) { // from class: com.ltech.smarthome.ui.control.ActHome.15
                @Override // com.ltech.smarthome.blemesh.IConnectBleCallback
                public void onConnectSuccess(String btModule, BluetoothDevice connectedDevice) {
                    SmartToast.showShort("连接成功 设备名称--->" + device.getDeviceName());
                    LHomeLog.i(getClass(), "************************onConnectSuccess***************************");
                }

                @Override // com.ltech.smarthome.blemesh.IConnectBleCallback
                public void onConnectFail() {
                    LHomeLog.i(getClass(), "************************onConnectFail***************************");
                }
            });
        }
    }

    public boolean addDeviceForProxy(ExtendedBluetoothDevice bluetoothDevice) {
        LHomeLog.i(getClass(), "ActMeshScan.addDevice: 11  " + bluetoothDevice.getAddress());
        Device superPanel = Injection.repo().device().getSuperPanel();
        return superPanel.getWifiMac().equals(bluetoothDevice.getAddress().replace(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, "")) && ProductRepository.getBleProductInfoByType(superPanel) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void testGetConnectBlueDevice() {
        if (bleIsOk()) {
            BluetoothDevice connectedDevice = Injection.mesh().getConnectedDevice();
            if (connectedDevice != null) {
                List<Device> value = Injection.repo().device().getDeviceListCache().getValue();
                if (value == null || value.isEmpty()) {
                    return;
                }
                for (Device device : value) {
                    if (connectedDevice.getAddress().replace(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, "").equals(device.getWifiMac())) {
                        SmartToast.showShort("当前连接 设备名称--->" + device.getDeviceName() + "  mac-->" + device.getWifiMac());
                        return;
                    }
                }
                return;
            }
            SmartToast.showShort("获取失败");
        }
    }

    private boolean checkPhotoPermission() {
        int checkSelfPermission;
        if (Build.VERSION.SDK_INT >= 23) {
            checkSelfPermission = checkSelfPermission(Permission.CAMERA);
            if (checkSelfPermission != 0) {
                ActivityCompat.requestPermissions(this, new String[]{Permission.CAMERA}, 6666);
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void testDcaGetDeviceList(final String dcaId) {
        DcaSdk.getSmartHomeManager().queryAllSmartHomeAppliance(dcaId, new DcaListener() { // from class: com.ltech.smarthome.ui.control.ActHome.16
            @Override // com.aispeech.dca.DcaListener
            public void onResult(int httpResponseCode, String httpResponseBody) {
                LHomeLog.d("dca", getClass(), "httpResponseCode : " + httpResponseCode);
                LHomeLog.d("dca", getClass(), "httpResponseBody : " + httpResponseBody);
                if (dcaId.equals(Constants.DCA_PRODUCT_ID_MINI)) {
                    ActHome.this.testDcaGetDeviceList(Constants.DCA_PRODUCT_ID_6S);
                }
            }

            @Override // com.aispeech.dca.DcaListener
            public void onFailure(IOException e) {
                LHomeLog.d("dca", getClass(), "onFailure ");
                e.printStackTrace();
            }
        });
    }

    public void setLanguage() {
        String currentLocaleLanguage = LanguageUtils.getCurrentLocaleLanguage(this.activity);
        LHomeLog.i(getClass(), "user_locale-->" + currentLocaleLanguage);
        if (StringUtils.isNotBlank(currentLocaleLanguage)) {
            ((ObservableSubscribeProxy) Injection.net().setLanguage(currentLocaleLanguage.contains(MtcUeConstants.MTC_UE_AUTHCODE_IN_CHN) ? 2 : 1).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.ActHome$$ExternalSyntheticLambda8
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActHome.lambda$setLanguage$10(obj);
                }
            });
        } else {
            ((ObservableSubscribeProxy) Injection.net().setLanguage(Resources.getSystem().getConfiguration().locale.getLanguage().contains(MtcUeConstants.MTC_UE_AUTHCODE_IN_CHN) ? 2 : 1).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.ActHome$$ExternalSyntheticLambda9
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActHome.lambda$setLanguage$11(obj);
                }
            });
        }
    }

    public long getSelectFloorId() {
        return this.selectFloorId;
    }

    public void setSelectFloorId(long selectFloorId) {
        this.selectFloorId = selectFloorId;
    }

    public long getSelectRoomId() {
        return this.selectRoomId;
    }

    public void setSelectRoomId(long selectRoomId) {
        this.selectRoomId = selectRoomId;
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        VoiceCallManager.getInstance().dismissDialog();
        MediaManager.getInstance(this).setSipBackendOnline(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMatterQrcode(long id, String qrcode) {
        String demToHex = com.smart.message.utils.StringUtils.demToHex((qrcode.length() / 2) + 1, 4);
        String stringToHex = com.smart.message.utils.StringUtils.stringToHex(qrcode.replaceFirst("MT:", ""));
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("CharSwitch", "66BB00000000F7" + demToHex + ProductId.BLE_SMART_PANEL_SUB_TYPE_G4 + stringToHex + "EB");
            ((ObservableSubscribeProxy) Injection.net().deviceController(id, jSONObject.toString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.ActHome$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActHome.this.lambda$sendMatterQrcode$12((Disposable) obj);
                }
            }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.control.ActHome$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActHome.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.ActHome$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SmartToast.showShort(R.string.encrypt_password_open_success);
                }
            }, new SmartErrorComsumer());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendMatterQrcode$12(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.loading));
    }
}