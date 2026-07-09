package com.ltech.smarthome.ui.permission;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.core.app.ActivityCompat;
import com.jaeger.library.StatusBarUtil;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActGetPermissionBinding;
import com.ltech.smarthome.model.Injection;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

/* loaded from: classes4.dex */
public class ActGetLocationPermission extends BaseNormalActivity<ActGetPermissionBinding> {
    private static final int REQUEST_ENABLE_LOCATION = 1022;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_get_permission;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColorNoTranslucent(this, 0);
        String[] strArr = {Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION};
        if (AndPermission.hasPermissions((Activity) this, strArr)) {
            check();
        } else {
            requestPermission(strArr);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void check() {
        if (!Injection.state().isLocationEnabled(this)) {
            MessageDialog.show(this, getString(R.string.tips), getString(R.string.open_gps_tip)).setCancelable(false).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.permission.ActGetLocationPermission$$ExternalSyntheticLambda0
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$check$0;
                    lambda$check$0 = ActGetLocationPermission.this.lambda$check$0(baseDialog, view);
                    return lambda$check$0;
                }
            }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.permission.ActGetLocationPermission$$ExternalSyntheticLambda1
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$check$1;
                    lambda$check$1 = ActGetLocationPermission.this.lambda$check$1(baseDialog, view);
                    return lambda$check$1;
                }
            });
        } else {
            setResult(100);
            ActivityCompat.finishAfterTransition(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$check$0(BaseDialog baseDialog, View view) {
        startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 1022);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$check$1(BaseDialog baseDialog, View view) {
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
                getPermissionFail();
            }
        }
    }
}