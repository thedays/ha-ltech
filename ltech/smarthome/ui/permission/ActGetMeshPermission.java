package com.ltech.smarthome.ui.permission;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.blankj.utilcode.util.ActivityUtils;
import com.jaeger.library.StatusBarUtil;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActGetPermissionBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.JumpPermissionManagement;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import java.util.List;

/* loaded from: classes4.dex */
public class ActGetMeshPermission extends BaseNormalActivity<ActGetPermissionBinding> {
    public static final long NEED_PHONE_STATE_PERMISSION_MAX_TIME = 172800000;
    private static final int REQUEST_ENABLE_BLUETOOTH = 1021;
    private static final int REQUEST_ENABLE_LOCATION = 1022;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_get_permission;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColorNoTranslucent(this, 0);
        final String[] strArr = Build.VERSION.SDK_INT >= 31 ? new String[]{"android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_CONNECT", "android.permission.BLUETOOTH_ADVERTISE"} : new String[]{Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION};
        if (AndPermission.hasPermissions((Activity) this, strArr)) {
            check();
        } else if (System.currentTimeMillis() - SharedPreferenceUtil.queryLongValue(Constants.PHONE_LOCATION_PERMISSION_TIME) >= NEED_PHONE_STATE_PERMISSION_MAX_TIME) {
            SharedPreferenceUtil.edit().keepShared(Constants.PHONE_LOCATION_PERMISSION_TIME, System.currentTimeMillis()).commit();
            MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), getString(R.string.call_location_permission), getString(R.string.call_location_permission_tip)).setOkButton(getString(R.string.i_know), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.permission.ActGetMeshPermission$$ExternalSyntheticLambda1
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$onCreate$3;
                    lambda$onCreate$3 = ActGetMeshPermission.this.lambda$onCreate$3(strArr, baseDialog, view);
                    return lambda$onCreate$3;
                }
            }).setCancelable(false);
        } else {
            showPermissionDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(String[] strArr) {
        AndPermission.with((Activity) this.activity).runtime().permission(strArr).onGranted(new Action() { // from class: com.ltech.smarthome.ui.permission.ActGetMeshPermission$$ExternalSyntheticLambda3
            @Override // com.yanzhenjie.permission.Action
            public final void onAction(Object obj) {
                ActGetMeshPermission.this.lambda$onCreate$0((List) obj);
            }
        }).onDenied(new Action() { // from class: com.ltech.smarthome.ui.permission.ActGetMeshPermission$$ExternalSyntheticLambda4
            @Override // com.yanzhenjie.permission.Action
            public final void onAction(Object obj) {
                ActGetMeshPermission.this.lambda$onCreate$1((List) obj);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onCreate$3(final String[] strArr, BaseDialog baseDialog, View view) {
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.permission.ActGetMeshPermission$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ActGetMeshPermission.this.lambda$onCreate$2(strArr);
            }
        }, 300L);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(List list) {
        check();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(List list) {
        getPermissionFail();
    }

    private void showPermissionDialog() {
        MessageDialog.show(this, getString(R.string.tips), Build.VERSION.SDK_INT >= 31 ? getString(R.string.please_open_ble) : getString(R.string.please_open_gps)).setCancelable(false).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.permission.ActGetMeshPermission$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showPermissionDialog$4;
                lambda$showPermissionDialog$4 = ActGetMeshPermission.this.lambda$showPermissionDialog$4(baseDialog, view);
                return lambda$showPermissionDialog$4;
            }
        }).setCancelButton(getString(R.string.app_str_refuse), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.permission.ActGetMeshPermission.1
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                ActGetMeshPermission.this.setResult(1003);
                ActivityCompat.finishAfterTransition(ActGetMeshPermission.this);
                return false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showPermissionDialog$4(BaseDialog baseDialog, View view) {
        JumpPermissionManagement.GoToSetting(this, 900);
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void check() {
        if (!Injection.state().isBluetoothEnabled()) {
            startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 1021);
        } else if (!Injection.state().isLocationEnabled(this)) {
            MessageDialog.show(this, getString(R.string.tips), getString(R.string.open_gps_tip)).setCancelable(false).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.permission.ActGetMeshPermission$$ExternalSyntheticLambda5
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$check$5;
                    lambda$check$5 = ActGetMeshPermission.this.lambda$check$5(baseDialog, view);
                    return lambda$check$5;
                }
            }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.permission.ActGetMeshPermission$$ExternalSyntheticLambda6
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$check$6;
                    lambda$check$6 = ActGetMeshPermission.this.lambda$check$6(baseDialog, view);
                    return lambda$check$6;
                }
            });
        } else {
            setResult(1002);
            ActivityCompat.finishAfterTransition(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$check$5(BaseDialog baseDialog, View view) {
        startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 1022);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$check$6(BaseDialog baseDialog, View view) {
        getPermissionFail();
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void getPermissionFail() {
        SmartToast.showShort(R.string.permission_deny);
        showPermissionDialog();
    }

    private void connectDeviceFail() {
        setResult(1004);
        ActivityCompat.finishAfterTransition(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1021) {
            if (resultCode == -1) {
                check();
                return;
            } else {
                getPermissionFail();
                return;
            }
        }
        if (requestCode == 1022) {
            if (Injection.state().isLocationEnabled(this)) {
                check();
                return;
            } else {
                getPermissionFail();
                return;
            }
        }
        if (requestCode == 900) {
            setResult(1002);
            ActivityCompat.finishAfterTransition(this);
        }
    }
}