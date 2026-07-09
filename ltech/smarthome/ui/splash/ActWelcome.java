package com.ltech.smarthome.ui.splash;

import android.os.Build;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.MyApplication;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActWelcomeBinding;
import com.ltech.smarthome.ui.ifly.WebViewActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.TextClickUtil;
import com.ltech.smarthome.utils.Utils;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.yanzhenjie.permission.runtime.Permission;

/* loaded from: classes4.dex */
public class ActWelcome extends BaseNormalActivity<ActWelcomeBinding> {
    public static final int REQUEST_POST_CAMERA = 1000;
    public static final int REQUEST_POST_NOTIFICATIONS = 2;
    public static final int REQUEST_READ_PHONE_STATE = 1;
    public static final int REQUEST_WRITE_STORAGE = 3;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean isNeedAddMarginTopEqualStatusBar() {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_welcome;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.splash.ActWelcome$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ActWelcome.this.lambda$initView$0();
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0() {
        if (!SharedPreferenceUtil.queryBooleanValue(Constants.NOT_FIRST_IN)) {
            showUseTipDialog();
        } else {
            NavUtils.destination(ActSplash.class).navigation(this);
            finishActivity();
        }
    }

    private void showUseTipDialog() {
        View inflate = LayoutInflater.from(this.activity).inflate(R.layout.dialog_tv, (ViewGroup) null);
        TextView textView = (TextView) inflate.findViewById(R.id.tv);
        String.format(getString(R.string.app_str_first_in_tip_msg), getString(R.string.reg_protocol_part1), getString(R.string.reg_protocol_part2));
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), getString(R.string.app_str_first_in_tip), "").setCancelable(false).setOkButton(getString(R.string.app_str_agree), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.splash.ActWelcome$$ExternalSyntheticLambda1
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showUseTipDialog$1;
                lambda$showUseTipDialog$1 = ActWelcome.this.lambda$showUseTipDialog$1(baseDialog, view);
                return lambda$showUseTipDialog$1;
            }
        }).setCancelButton(getString(R.string.app_str_refuse), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.splash.ActWelcome$$ExternalSyntheticLambda2
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showUseTipDialog$2;
                lambda$showUseTipDialog$2 = ActWelcome.this.lambda$showUseTipDialog$2(baseDialog, view);
                return lambda$showUseTipDialog$2;
            }
        }).setCustomView(inflate);
        TextClickUtil.setTextClick(this, textView, getClickableSpans(), R.string.app_str_first_in_tip_msg, R.string.protocol_part1, R.string.protocol_part2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showUseTipDialog$1(BaseDialog baseDialog, View view) {
        SharedPreferenceUtil.edit().keepShared(Constants.NOT_FIRST_IN, true);
        NavUtils.destination(ActSplash.class).navigation(this);
        finishActivity();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showUseTipDialog$2(BaseDialog baseDialog, View view) {
        finishActivity();
        return false;
    }

    private void checkReadPhonePermission() {
        MyApplication.delayInit(getApplication().getApplicationContext());
        if (ContextCompat.checkSelfPermission(this, Permission.READ_PHONE_STATE) != 0) {
            requestPermission();
            return;
        }
        SharedPreferenceUtil.edit().keepShared(Constants.NOT_FIRST_IN, true);
        NavUtils.destination(ActSplash.class).navigation(this);
        finishActivity();
    }

    private void requestPermission() {
        if (Utils.isYYBFlavor(this)) {
            MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), getString(R.string.call_phone_state_permission), getString(R.string.call_phone_state_permission_tip)).setOkButton(getString(R.string.i_know), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.splash.ActWelcome$$ExternalSyntheticLambda5
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$requestPermission$3;
                    lambda$requestPermission$3 = ActWelcome.this.lambda$requestPermission$3(baseDialog, view);
                    return lambda$requestPermission$3;
                }
            }).setCancelable(false);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Permission.READ_PHONE_STATE}, 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$requestPermission$3(BaseDialog baseDialog, View view) {
        ActivityCompat.requestPermissions(this, new String[]{Permission.READ_PHONE_STATE}, 1);
        return false;
    }

    private ClickableSpan[] getClickableSpans() {
        return new ClickableSpan[]{new ClickableSpan() { // from class: com.ltech.smarthome.ui.splash.ActWelcome.1
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ActWelcome.this.getResources().getColor(R.color.color_text_red));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, ActWelcome.this.getString(R.string.user_agreement_url)).navigation(ActWelcome.this);
            }
        }, new ClickableSpan() { // from class: com.ltech.smarthome.ui.splash.ActWelcome.2
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ActWelcome.this.getResources().getColor(R.color.color_text_red));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, ActWelcome.this.getString(R.string.privacy_agreement_url)).navigation(ActWelcome.this);
            }
        }};
    }

    private void showPermissionDialog() {
        MessageDialog.show(this, getString(R.string.app_str_voice_call_record), getString(R.string.app_str_voice_call_record_tip)).setCancelable(false).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.splash.ActWelcome$$ExternalSyntheticLambda4
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showPermissionDialog$4;
                lambda$showPermissionDialog$4 = ActWelcome.this.lambda$showPermissionDialog$4(baseDialog, view);
                return lambda$showPermissionDialog$4;
            }
        }).setCancelable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showPermissionDialog$4(BaseDialog baseDialog, View view) {
        if (Build.VERSION.SDK_INT >= 31) {
            ActivityCompat.requestPermissions(this, new String[]{Permission.RECORD_AUDIO, Permission.CAMERA, "android.permission.BLUETOOTH_CONNECT", "android.permission.BLUETOOTH_ADVERTISE"}, 1000);
            return false;
        }
        ActivityCompat.requestPermissions(this, new String[]{Permission.RECORD_AUDIO, Permission.CAMERA}, 1000);
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            SharedPreferenceUtil.edit().keepShared(Constants.NOT_FIRST_IN, true);
            NavUtils.destination(ActSplash.class).navigation(this);
            finishActivity();
        } else {
            if (requestCode == 2) {
                checkReadPhonePermission();
                return;
            }
            if (requestCode == 3) {
                checkPermission();
            } else {
                if (requestCode != 1000) {
                    return;
                }
                SharedPreferenceUtil.edit().keepShared(Constants.NOT_FIRST_IN, true);
                NavUtils.destination(ActSplash.class).navigation(this);
                finishActivity();
            }
        }
    }

    private boolean checkPermission() {
        int checkSelfPermission;
        int checkSelfPermission2;
        int checkSelfPermission3;
        int checkSelfPermission4;
        int checkSelfPermission5;
        int checkSelfPermission6;
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= 31) {
            checkSelfPermission3 = checkSelfPermission(Permission.RECORD_AUDIO);
            if (checkSelfPermission3 == 0) {
                checkSelfPermission4 = checkSelfPermission(Permission.CAMERA);
                if (checkSelfPermission4 == 0) {
                    checkSelfPermission5 = checkSelfPermission("android.permission.BLUETOOTH_CONNECT");
                    if (checkSelfPermission5 == 0) {
                        checkSelfPermission6 = checkSelfPermission("android.permission.BLUETOOTH_ADVERTISE");
                        if (checkSelfPermission6 == 0) {
                            return true;
                        }
                    }
                }
            }
            showPermissionDialog();
            return false;
        }
        checkSelfPermission = checkSelfPermission(Permission.RECORD_AUDIO);
        if (checkSelfPermission == 0) {
            checkSelfPermission2 = checkSelfPermission(Permission.CAMERA);
            if (checkSelfPermission2 == 0) {
                return true;
            }
        }
        showPermissionDialog();
        return false;
    }
}