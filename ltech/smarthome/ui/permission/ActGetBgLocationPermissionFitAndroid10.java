package com.ltech.smarthome.ui.permission;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.core.app.ActivityCompat;
import com.blankj.utilcode.util.AppUtils;
import com.jaeger.library.StatusBarUtil;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActGetPermissionBinding;
import com.ltech.smarthome.model.Injection;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import java.util.List;

/* loaded from: classes4.dex */
public class ActGetBgLocationPermissionFitAndroid10 extends BaseNormalActivity<ActGetPermissionBinding> {
    private static final int REQUEST_ENABLE_LOCATION = 1022;
    String[] permissions = {Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION};
    String permission = Permission.ACCESS_BACKGROUND_LOCATION;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_get_permission;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColorNoTranslucent(this, 0);
        if (AndPermission.hasPermissions((Activity) this, this.permissions)) {
            if (AndPermission.hasPermissions((Activity) this, this.permission)) {
                check();
                return;
            } else if (AndPermission.hasAlwaysDeniedPermission((Activity) this, this.permission)) {
                showLocationTipDialog();
                return;
            } else {
                check();
                return;
            }
        }
        requestPermission(this.permissions);
    }

    private void showLocationTipDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.app_str_get_bg_location_permission_fail_tip)).setOkButton(getString(R.string.app_str_agree), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.permission.ActGetBgLocationPermissionFitAndroid10$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showLocationTipDialog$0;
                lambda$showLocationTipDialog$0 = ActGetBgLocationPermissionFitAndroid10.this.lambda$showLocationTipDialog$0(baseDialog, view);
                return lambda$showLocationTipDialog$0;
            }
        }).setCancelButton(getString(R.string.app_str_refuse), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.permission.ActGetBgLocationPermissionFitAndroid10$$ExternalSyntheticLambda1
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showLocationTipDialog$1;
                lambda$showLocationTipDialog$1 = ActGetBgLocationPermissionFitAndroid10.this.lambda$showLocationTipDialog$1(baseDialog, view);
                return lambda$showLocationTipDialog$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showLocationTipDialog$0(BaseDialog baseDialog, View view) {
        AppUtils.launchAppDetailsSettings();
        ActivityCompat.finishAfterTransition(this);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showLocationTipDialog$1(BaseDialog baseDialog, View view) {
        getPermissionFail();
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void check() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.app_str_bg_location_permission_request_tip)).setOkButton(getString(R.string.app_str_agree), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.permission.ActGetBgLocationPermissionFitAndroid10$$ExternalSyntheticLambda4
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$check$5;
                lambda$check$5 = ActGetBgLocationPermissionFitAndroid10.this.lambda$check$5(baseDialog, view);
                return lambda$check$5;
            }
        }).setCancelButton(getString(R.string.app_str_refuse), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.permission.ActGetBgLocationPermissionFitAndroid10$$ExternalSyntheticLambda5
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$check$6;
                lambda$check$6 = ActGetBgLocationPermissionFitAndroid10.this.lambda$check$6(baseDialog, view);
                return lambda$check$6;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$check$4() {
        AndPermission.with((Activity) this).runtime().permission(this.permission).onGranted(new Action() { // from class: com.ltech.smarthome.ui.permission.ActGetBgLocationPermissionFitAndroid10$$ExternalSyntheticLambda7
            @Override // com.yanzhenjie.permission.Action
            public final void onAction(Object obj) {
                ActGetBgLocationPermissionFitAndroid10.this.lambda$check$2((List) obj);
            }
        }).onDenied(new Action() { // from class: com.ltech.smarthome.ui.permission.ActGetBgLocationPermissionFitAndroid10$$ExternalSyntheticLambda8
            @Override // com.yanzhenjie.permission.Action
            public final void onAction(Object obj) {
                ActGetBgLocationPermissionFitAndroid10.this.lambda$check$3((List) obj);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$check$5(BaseDialog baseDialog, View view) {
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.permission.ActGetBgLocationPermissionFitAndroid10$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                ActGetBgLocationPermissionFitAndroid10.this.lambda$check$4();
            }
        }, 300L);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$check$2(List list) {
        checkGps();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$check$3(List list) {
        showLocationTipDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$check$6(BaseDialog baseDialog, View view) {
        getPermissionFail();
        return false;
    }

    private void checkGps() {
        if (!Injection.state().isLocationEnabled(this)) {
            MessageDialog.show(this, getString(R.string.tips), getString(R.string.open_gps_tip)).setCancelable(false).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.permission.ActGetBgLocationPermissionFitAndroid10$$ExternalSyntheticLambda2
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$checkGps$7;
                    lambda$checkGps$7 = ActGetBgLocationPermissionFitAndroid10.this.lambda$checkGps$7(baseDialog, view);
                    return lambda$checkGps$7;
                }
            }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.permission.ActGetBgLocationPermissionFitAndroid10$$ExternalSyntheticLambda3
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$checkGps$8;
                    lambda$checkGps$8 = ActGetBgLocationPermissionFitAndroid10.this.lambda$checkGps$8(baseDialog, view);
                    return lambda$checkGps$8;
                }
            });
        } else {
            setResult(100);
            ActivityCompat.finishAfterTransition(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$checkGps$7(BaseDialog baseDialog, View view) {
        startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 1022);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$checkGps$8(BaseDialog baseDialog, View view) {
        getGpsPermissionFail();
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1022) {
            if (Injection.state().isLocationEnabled(this)) {
                check();
            } else {
                getGpsPermissionFail();
            }
        }
    }
}