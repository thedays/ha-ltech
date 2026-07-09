package com.ltech.smarthome.ui.automation;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.imageclip.fragment.ActivityResultHelper;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseListActivity;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.ui.automation.ISelectCondition;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.permission.ActGetBgLocationPermissionFitAndroid10;
import com.ltech.smarthome.ui.permission.ActGetLocationPermission;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectAddress extends BaseListActivity<GoItem> implements ISelectCondition {
    private static final int GPS_REQUEST_CODE = 1000;
    private boolean leaveSomewhere;
    String[] permissions = {Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION};
    String permission = Permission.ACCESS_BACKGROUND_LOCATION;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_go_1;
    }

    @Override // com.ltech.smarthome.ui.automation.ISelectCondition
    public /* synthetic */ void setSelectResult(BaseNormalActivity baseNormalActivity) {
        ISelectCondition.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.ui.automation.ISelectCondition
    public /* synthetic */ void setSelectStatusResult(BaseNormalActivity baseNormalActivity) {
        ISelectCondition.CC.$default$setSelectStatusResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectAddress$$ExternalSyntheticLambda5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSelectAddress.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ((GoItem) this.mAdapter.getData().get(i)).getAction().execute();
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<GoItem> getList() {
        ArrayList arrayList = new ArrayList(2);
        arrayList.add(new GoItem().setMainText(getString(R.string.leave_somewhere)).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.automation.ActSelectAddress$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectAddress.this.lambda$getList$1();
            }
        })));
        arrayList.add(new GoItem().setMainText(getString(R.string.reach_somewhere)).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.automation.ActSelectAddress$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectAddress.this.lambda$getList$2();
            }
        })));
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getList$1() {
        this.leaveSomewhere = true;
        goSelectHomeMember();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getList$2() {
        this.leaveSomewhere = false;
        goSelectHomeMember();
    }

    private void goSelectHomeMember() {
        if (getApplicationInfo().targetSdkVersion >= 29) {
            if (Build.VERSION.SDK_INT >= 29) {
                getLocationPermissionForAndoird10();
                return;
            } else {
                getLocationPermission();
                return;
            }
        }
        getLocationPermission();
    }

    private void getLocationPermission() {
        ActivityResultHelper.init(this).startActivityForResult(ActGetLocationPermission.class, new ActivityResultHelper.Callback() { // from class: com.ltech.smarthome.ui.automation.ActSelectAddress$$ExternalSyntheticLambda4
            @Override // com.ltech.imageclip.fragment.ActivityResultHelper.Callback
            public final void onActivityResult(int i, Intent intent) {
                ActSelectAddress.this.lambda$getLocationPermission$3(i, intent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getLocationPermission$3(int i, Intent intent) {
        if (100 == i) {
            Injection.limiter().reset(Injection.keyCreator().placeUserKey(getIntent().getLongExtra(Constants.PLACE_ID, -1L)));
            NavUtils.destination(ActSelectHomeMember.class).withLong(Constants.PLACE_ID, getIntent().getLongExtra(Constants.PLACE_ID, -1L)).withBoolean(Constants.LEAVE_SOMEWHERE, this.leaveSomewhere).withDefaultRequestCode().navigation(this);
        }
    }

    private void getLocationPermissionForAndoird10() {
        if (AndPermission.hasPermissions((Activity) this, this.permissions)) {
            if (AndPermission.hasPermissions((Activity) this, this.permission)) {
                ActivityResultHelper.init(this).startActivityForResult(ActGetBgLocationPermissionFitAndroid10.class, new ActivityResultHelper.Callback() { // from class: com.ltech.smarthome.ui.automation.ActSelectAddress$$ExternalSyntheticLambda2
                    @Override // com.ltech.imageclip.fragment.ActivityResultHelper.Callback
                    public final void onActivityResult(int i, Intent intent) {
                        ActSelectAddress.this.lambda$getLocationPermissionForAndoird10$4(i, intent);
                    }
                });
                return;
            } else {
                goLocationPermissionDescriptionActivity();
                return;
            }
        }
        goLocationPermissionDescriptionActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getLocationPermissionForAndoird10$4(int i, Intent intent) {
        if (100 == i) {
            Injection.limiter().reset(Injection.keyCreator().placeUserKey(getIntent().getLongExtra(Constants.PLACE_ID, -1L)));
            NavUtils.destination(ActSelectHomeMember.class).withLong(Constants.PLACE_ID, getIntent().getLongExtra(Constants.PLACE_ID, -1L)).withBoolean(Constants.LEAVE_SOMEWHERE, this.leaveSomewhere).withDefaultRequestCode().navigation(this);
        }
    }

    private void goMap() {
        ActivityResultHelper.init(this).startActivityForResult(ActGetLocationPermission.class, new ActivityResultHelper.Callback() { // from class: com.ltech.smarthome.ui.automation.ActSelectAddress$$ExternalSyntheticLambda3
            @Override // com.ltech.imageclip.fragment.ActivityResultHelper.Callback
            public final void onActivityResult(int i, Intent intent) {
                ActSelectAddress.this.lambda$goMap$5(i, intent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$goMap$5(int i, Intent intent) {
        if (100 == i) {
            NavUtils.destination(ActMap.class).withLong(Constants.PLACE_ID, getIntent().getLongExtra(Constants.PLACE_ID, -1L)).withBoolean(Constants.LEAVE_SOMEWHERE, this.leaveSomewhere).withDefaultRequestCode().navigation(this);
        }
    }

    private void goLocationPermissionDescriptionActivity() {
        NavUtils.destination(ActLocationPermissionDescription.class).withLong(Constants.PLACE_ID, getIntent().getLongExtra(Constants.PLACE_ID, -1L)).withBoolean(Constants.LEAVE_SOMEWHERE, this.leaveSomewhere).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, GoItem item) {
        helper.setText(R.id.tv_main, item.getMainText()).setImageResource(R.id.iv_go, item.getGoRes());
        ((AppCompatTextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3003) {
            setSelectResult(this);
        }
    }
}