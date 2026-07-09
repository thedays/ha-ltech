package com.ltech.smarthome.ui.automation;

import android.content.Intent;
import android.view.View;
import com.ltech.imageclip.fragment.ActivityResultHelper;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActLocationPermissionDescriptionBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.ui.automation.ISelectCondition;
import com.ltech.smarthome.ui.permission.ActGetBgLocationPermissionFitAndroid10;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class ActLocationPermissionDescription extends BaseNormalActivity<ActLocationPermissionDescriptionBinding> implements ISelectCondition {
    private boolean leaveSomewhere;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_location_permission_description;
    }

    @Override // com.ltech.smarthome.ui.automation.ISelectCondition
    public /* synthetic */ void setSelectResult(BaseNormalActivity baseNormalActivity) {
        ISelectCondition.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.ui.automation.ISelectCondition
    public /* synthetic */ void setSelectStatusResult(BaseNormalActivity baseNormalActivity) {
        ISelectCondition.CC.$default$setSelectStatusResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.app_str_use_your_location));
        setBackImage(R.mipmap.icon_back);
        ((ActLocationPermissionDescriptionBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.automation.ActLocationPermissionDescription$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActLocationPermissionDescription.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        int id = view.getId();
        if (id == R.id.tv_no_thanks) {
            back();
        } else {
            if (id != R.id.tv_open) {
                return;
            }
            getLocationPermissionForAndroid10();
        }
    }

    private void getLocationPermissionForAndroid10() {
        ActivityResultHelper.init(this).startActivityForResult(ActGetBgLocationPermissionFitAndroid10.class, new ActivityResultHelper.Callback() { // from class: com.ltech.smarthome.ui.automation.ActLocationPermissionDescription$$ExternalSyntheticLambda1
            @Override // com.ltech.imageclip.fragment.ActivityResultHelper.Callback
            public final void onActivityResult(int i, Intent intent) {
                ActLocationPermissionDescription.this.lambda$getLocationPermissionForAndroid10$1(i, intent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getLocationPermissionForAndroid10$1(int i, Intent intent) {
        if (100 == i) {
            Injection.limiter().reset(Injection.keyCreator().placeUserKey(getIntent().getLongExtra(Constants.PLACE_ID, -1L)));
            NavUtils.destination(ActSelectHomeMember.class).withLong(Constants.PLACE_ID, getIntent().getLongExtra(Constants.PLACE_ID, -1L)).withBoolean(Constants.LEAVE_SOMEWHERE, getIntent().getBooleanExtra(Constants.LEAVE_SOMEWHERE, false)).withDefaultRequestCode().navigation(this);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3003) {
            setSelectResult(this);
        }
    }
}