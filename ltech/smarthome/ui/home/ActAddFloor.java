package com.ltech.smarthome.ui.home;

import android.content.Intent;
import android.os.Bundle;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActAddFloorBinding;
import com.ltech.smarthome.utils.Constants;

/* loaded from: classes4.dex */
public class ActAddFloor extends VMActivity<ActAddFloorBinding, ActAddFloorVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_add_floor;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setEditString(getString(R.string.save));
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.add_floor));
        ((ActAddFloorVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, 0L);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        ((ActAddFloorVM) this.mViewModel).createFloor(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras;
        super.onActivityResult(requestCode, resultCode, data);
        if (1001 != resultCode || data == null || (extras = data.getExtras()) == null) {
            return;
        }
        ((ActAddFloorVM) this.mViewModel).addRoom(extras.getString(Constants.ROOM_NAME));
    }
}