package com.ltech.smarthome.ui.device.light;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActAddMusicBinding;
import com.ltech.smarthome.utils.SmartToast;

/* loaded from: classes4.dex */
public class ActAddMusic extends VMActivity<ActAddMusicBinding, ActAddMusicVM> {
    private static final int REQUEST_WRITE_STORAGE = 1;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_add_music;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.input_music));
        setBackString(getString(R.string.cancel));
        setEditString(getString(R.string.finish));
        if (checkWriteStoragePermission(1)) {
            ((ActAddMusicVM) this.mViewModel).initMusicList(this);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        ((ActAddMusicVM) this.mViewModel).saveData();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != 1) {
            return;
        }
        if (grantResults.length > 0 && grantResults[0] == 0) {
            ((ActAddMusicVM) this.mViewModel).initMusicList(this);
        } else {
            SmartToast.showShort(getString(R.string.permission_deny));
            back();
        }
    }
}