package com.ltech.smarthome.ui.my;

import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.databinding.ActAboutBinding;
import com.ltech.smarthome.net.response.VersionInfo;
import com.ltech.smarthome.service.UpdateApkService;
import com.ltech.smarthome.ui.ifly.WebViewActivity;
import com.ltech.smarthome.ui.item.SettingItem;
import com.ltech.smarthome.ui.my.ActAbout;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.websocketlog.WSLog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.util.TextInfo;
import com.smart.dialog.v3.MessageDialog;

/* loaded from: classes4.dex */
public class ActAbout extends VMActivity<ActAboutBinding, ActAboutVM> {
    VersionInfo versionInfo;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_about;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.about));
        setBackImage(R.mipmap.icon_back);
        ((ActAboutBinding) this.mViewBinding).tvAppVersion.setText(ExifInterface.GPS_MEASUREMENT_INTERRUPTED + AppUtils.getAppVersionName());
        this.versionInfo = new VersionInfo();
        if (getIntent().getBooleanExtra("newversion", false)) {
            this.versionInfo.setAppversionnum(getIntent().getIntExtra("versioncode", 0));
            this.versionInfo.setAppversioncode(getIntent().getStringExtra("versionname"));
            this.versionInfo.setFileurl(getIntent().getStringExtra("fileurl"));
            Log.i("VersionInfo", "VersionInfo=" + this.versionInfo.toString());
            ((ActAboutVM) this.mViewModel).mObservableList.add(new SettingItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.app_check_version)).setGoRes(R.mipmap.icon_more).setSubText(ActivityUtils.getTopActivity().getString(R.string.app_find_new_version)).setMainResShow(false).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.my.ActAbout$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActAbout.this.lambda$initView$0();
                }
            })));
            ((ActAboutVM) this.mViewModel).versionObservable.set(this.versionInfo);
        } else {
            ((ActAboutVM) this.mViewModel).mObservableList.add(new SettingItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.app_check_version)).setGoRes(R.mipmap.icon_more).setSubText(ActivityUtils.getTopActivity().getString(R.string.last_version)).setMainResShow(false));
        }
        ((ActAboutVM) this.mViewModel).mObservableList.add(new SettingItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.reg_protocol_part1)).setGoRes(R.mipmap.icon_more).setMainResShow(false).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.my.ActAbout$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, ActivityUtils.getTopActivity().getString(R.string.user_agreement_url)).navigation(ActivityUtils.getTopActivity());
            }
        })));
        ((ActAboutVM) this.mViewModel).mObservableList.add(new SettingItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.reg_protocol_part2)).setGoRes(R.mipmap.icon_more).setMainResShow(false).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.my.ActAbout$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, ActivityUtils.getTopActivity().getString(R.string.privacy_agreement_url)).navigation(ActivityUtils.getTopActivity());
            }
        })));
        ((ActAboutBinding) this.mViewBinding).ivAppIcon.setOnClickListener(new AnonymousClass1());
        ((ActAboutBinding) this.mViewBinding).tvAppNo.setOnClickListener(new View.OnClickListener(this) { // from class: com.ltech.smarthome.ui.my.ActAbout.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, "https://beian.miit.gov.cn").navigation(ActivityUtils.getTopActivity());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0() {
        ((ActAboutVM) this.mViewModel).showUpgradeAppDialogEvent.call();
    }

    /* renamed from: com.ltech.smarthome.ui.my.ActAbout$1, reason: invalid class name */
    class AnonymousClass1 implements View.OnClickListener {
        static final int COUNTS = 5;
        static final long DURATION = 3000;
        long[] mHits = new long[5];

        AnonymousClass1() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            long[] jArr = this.mHits;
            System.arraycopy(jArr, 1, jArr, 0, jArr.length - 1);
            long[] jArr2 = this.mHits;
            jArr2[jArr2.length - 1] = SystemClock.uptimeMillis();
            if (this.mHits[0] >= SystemClock.uptimeMillis() - DURATION) {
                EditDialog.asDefault().setContent("").setTitle("输入日志名称").setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.my.ActAbout$1$$ExternalSyntheticLambda0
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActAbout.AnonymousClass1.lambda$onClick$0((String) obj);
                    }
                }).showDialog(ActAbout.this);
            }
        }

        static /* synthetic */ void lambda$onClick$0(String str) {
            if (TextUtils.isEmpty(str)) {
                SmartToast.showShort("请输入日志名称");
                return;
            }
            WSLog.getInstance().setEnable(str, true);
            SmartToast.showShort("开启云日志模式,名称  " + str);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActAboutVM) this.mViewModel).showUpgradeAppDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.my.ActAbout$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAbout.this.lambda$startObserve$3((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        if (this.versionInfo.getAppversionnum() > 0) {
            findNewVersion(this.versionInfo);
        }
    }

    private void findNewVersion(final VersionInfo versionInfo) {
        TextInfo textInfo = new TextInfo();
        textInfo.setGravity(GravityCompat.START);
        MessageDialog.show(this, getString(R.string.app_find_new_version) + "V " + versionInfo.getAppversioncode(), getString(R.string.find_new_version_tip)).setOkButton(getString(R.string.update_now), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.my.ActAbout$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$findNewVersion$4;
                lambda$findNewVersion$4 = ActAbout.this.lambda$findNewVersion$4(versionInfo, baseDialog, view);
                return lambda$findNewVersion$4;
            }
        }).setCancelButton(getString(R.string.update_later)).setMessageTextInfo(textInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$findNewVersion$4(VersionInfo versionInfo, BaseDialog baseDialog, View view) {
        Intent intent = new Intent(this, (Class<?>) UpdateApkService.class);
        intent.putExtra("versionInfo", versionInfo);
        startService(intent);
        return false;
    }
}