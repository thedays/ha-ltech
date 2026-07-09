package com.ltech.smarthome.ui.scene;

import android.view.View;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.FragmentUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActDaliSelectBinding;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.ui.device.dalipro.FtDaliScene;

/* loaded from: classes4.dex */
public class ActSelectDaliScene extends BaseNormalActivity<ActDaliSelectBinding> {
    private boolean allowMultiSelect;
    private long deviceId;
    private FtDaliScene ftScene;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_dali_select;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.select_dali_scene));
        this.deviceId = getIntent().getLongExtra("device_id", 0L);
        boolean z = SceneHelper.instance().bindingType == 0;
        this.allowMultiSelect = z;
        if (z) {
            this.ftScene = FtDaliScene.newInstance(this.deviceId, true);
        } else {
            this.ftScene = FtDaliScene.newInstance(this.deviceId, false);
            ((ActDaliSelectBinding) this.mViewBinding).tvBottom.setText(R.string.finish);
        }
        FragmentUtils.add(getSupportFragmentManager(), this.ftScene, R.id.layout_content);
        ((ActDaliSelectBinding) this.mViewBinding).tvBottom.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectDaliScene$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActSelectDaliScene.this.lambda$initView$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        SceneHelper.instance().maskType = MaskType.SCENE;
        this.ftScene.saveClickEvent.call();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        this.ftScene.editClickEvent.call();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.ftScene.refreshEdit.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.ActSelectDaliScene$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectDaliScene.this.lambda$startObserve$1((Boolean) obj);
            }
        });
        this.ftScene.selectNumber.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.scene.ActSelectDaliScene$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectDaliScene.this.lambda$startObserve$2((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Boolean bool) {
        if (this.allowMultiSelect) {
            if (bool.booleanValue()) {
                setEditString(getString(R.string.app_str_cancel_select_all));
            } else {
                setEditString(getString(R.string.app_str_select_all));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        if (this.allowMultiSelect) {
            ((ActDaliSelectBinding) this.mViewBinding).tvBottom.setText(String.format(getString(R.string.finish_with_num), num));
        }
    }
}