package com.ltech.smarthome.ui.scene;

import android.view.View;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.FragmentUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelectDaliAddBinding;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.ui.device.dalipro.FtDaliAdd;
import com.ltech.smarthome.utils.Constants;

/* loaded from: classes4.dex */
public class ActSelectCgdProDaliAdd extends BaseNormalActivity<ActSelectDaliAddBinding> {
    private long deviceId;
    private FtDaliAdd ftDaliAdd;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_dali_add;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.app_str_select_all));
        int intExtra = getIntent().getIntExtra(Constants.SHOW_TYPE, 0);
        if (intExtra == 1) {
            setTitle(getString(R.string.select_dali_group));
        } else {
            setTitle(getString(R.string.select_dali_add));
        }
        this.deviceId = getIntent().getLongExtra("device_id", 0L);
        if (getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false)) {
            SceneHelper.instance().maskType = MaskType.LOCAL;
        } else {
            SceneHelper.instance().maskType = MaskType.DEVICE_GROUP;
        }
        this.ftDaliAdd = FtDaliAdd.newInstance(this.deviceId, 3, intExtra);
        FragmentUtils.add(getSupportFragmentManager(), this.ftDaliAdd, R.id.layout_content);
        ((ActSelectDaliAddBinding) this.mViewBinding).tvBottom.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectCgdProDaliAdd$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActSelectCgdProDaliAdd.this.lambda$initView$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        this.ftDaliAdd.saveClickEvent.call();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        this.ftDaliAdd.editClickEvent.call();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.ftDaliAdd.refreshEdit.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.ActSelectCgdProDaliAdd$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectCgdProDaliAdd.this.lambda$startObserve$1((Boolean) obj);
            }
        });
        this.ftDaliAdd.selectNumber.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.ActSelectCgdProDaliAdd$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectCgdProDaliAdd.this.lambda$startObserve$2((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Boolean bool) {
        if (bool.booleanValue()) {
            setEditString(getString(R.string.app_str_cancel_select_all));
        } else {
            setEditString(getString(R.string.app_str_select_all));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        ((ActSelectDaliAddBinding) this.mViewBinding).tvBottom.setText(String.format(getString(R.string.finish_with_num), num));
    }
}