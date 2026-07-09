package com.ltech.smarthome.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import com.ltech.imageclip.fragment.ActivityResultHelper;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActCreateHomeBinding;
import com.ltech.smarthome.ui.permission.ActGetLocationPermission;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.smart.dialog.v3.MessageDialog;

/* loaded from: classes4.dex */
public class ActCreateHome extends VMActivity<ActCreateHomeBinding, ActCreateHomeVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_create_home;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        setEditString(getString(R.string.save));
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.create_home));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r3) {
        ActivityResultHelper.init(this).startActivityForResult(ActGetLocationPermission.class, new ActivityResultHelper.Callback() { // from class: com.ltech.smarthome.ui.home.ActCreateHome$$ExternalSyntheticLambda1
            @Override // com.ltech.imageclip.fragment.ActivityResultHelper.Callback
            public final void onActivityResult(int i, Intent intent) {
                ActCreateHome.this.lambda$startObserve$0(i, intent);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActCreateHomeVM) this.mViewModel).selectHomePositionEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.home.ActCreateHome$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCreateHome.this.lambda$startObserve$1((Void) obj);
            }
        });
        ((ActCreateHomeVM) this.mViewModel).creatHomeLocationTipEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.home.ActCreateHome.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActCreateHome.this.showLoactionTipDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(int i, Intent intent) {
        if (100 == i) {
            NavUtils.destination(ActHomePosition.class).withDefaultRequestCode().navigation(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLoactionTipDialog() {
        MessageDialog.show(this, getString(R.string.choose_home_location), "").setCustomView(getLayoutInflater().inflate(R.layout.item_create_home_loacation_tip, (ViewGroup) null));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        ((ActCreateHomeVM) this.mViewModel).createHome(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras;
        super.onActivityResult(requestCode, resultCode, data);
        if (1001 == resultCode) {
            if (data == null || (extras = data.getExtras()) == null) {
                return;
            }
            ((ActCreateHomeVM) this.mViewModel).addRoom(extras.getString(Constants.ROOM_NAME));
            return;
        }
        if (5000 != resultCode || data == null) {
            return;
        }
        ((ActCreateHomeVM) this.mViewModel).homeLocation.set(data.getStringExtra(Constants.ADDRESS));
        ((ActCreateHomeVM) this.mViewModel).latitude = data.getDoubleExtra(Constants.LATITUDE, -1.0d);
        ((ActCreateHomeVM) this.mViewModel).longitude = data.getDoubleExtra(Constants.LONGITUDE, -1.0d);
    }
}