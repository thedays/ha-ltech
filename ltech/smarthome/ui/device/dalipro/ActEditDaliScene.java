package com.ltech.smarthome.ui.device.dalipro;

import android.view.View;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.FragmentUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelectDaliAddBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.scene_param.LocalDeviceParam;
import com.ltech.smarthome.utils.Constants;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class ActEditDaliScene extends BaseNormalActivity<ActSelectDaliAddBinding> {
    private FtDaliAdd ftEdit;
    public Map<Long, LocalDeviceParam> lastActionMap = new HashMap();
    private long sceneId;

    static /* synthetic */ boolean lambda$back$3(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_dali_add;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.app_str_select_all));
        setTitle(getString(R.string.app_str_edit_scene));
        this.sceneId = getIntent().getLongExtra(Constants.SCENE_ID, 0L);
        final Scene sceneBySceneId = Injection.repo().scene().getSceneBySceneId(this.sceneId);
        this.lastActionMap = DaliProHelper.convert2DaliSceneActionMap(sceneBySceneId.getSceneContents());
        this.ftEdit = FtDaliAdd.newInstance(sceneBySceneId.getSceneId(), 2);
        FragmentUtils.add(getSupportFragmentManager(), this.ftEdit, R.id.layout_content);
        ((ActSelectDaliAddBinding) this.mViewBinding).tvBottom.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActEditDaliScene$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActEditDaliScene.this.lambda$initView$1(sceneBySceneId, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(Scene scene, View view) {
        if (scene.getSceneContents() != null && scene.getSceneContents().size() != 0) {
            MessageDialog.show(this, getString(R.string.dali_scene_save_tip), "").setOkButton(getString(R.string.confirm)).setCancelButton(getString(R.string.cancel)).setOnOkButtonClickListener(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActEditDaliScene$$ExternalSyntheticLambda5
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view2) {
                    boolean lambda$initView$0;
                    lambda$initView$0 = ActEditDaliScene.this.lambda$initView$0(baseDialog, view2);
                    return lambda$initView$0;
                }
            });
        } else {
            this.ftEdit.saveClickEvent.call();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$0(BaseDialog baseDialog, View view) {
        this.ftEdit.saveClickEvent.call();
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        this.ftEdit.editClickEvent.call();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (this.ftEdit.checkActionChange(this.lastActionMap)) {
            MessageDialog.show(this, "", getString(R.string.need_save_action)).setOkButton(getString(R.string.ir_exit), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActEditDaliScene$$ExternalSyntheticLambda3
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$back$2;
                    lambda$back$2 = ActEditDaliScene.this.lambda$back$2(baseDialog, view);
                    return lambda$back$2;
                }
            }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActEditDaliScene$$ExternalSyntheticLambda4
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    return ActEditDaliScene.lambda$back$3(baseDialog, view);
                }
            }).setCancelable(false);
        } else {
            super.back();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$back$2(BaseDialog baseDialog, View view) {
        super.back();
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.ftEdit.refreshEdit.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActEditDaliScene$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditDaliScene.this.lambda$startObserve$4((Boolean) obj);
            }
        });
        this.ftEdit.selectNumber.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActEditDaliScene$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditDaliScene.this.lambda$startObserve$5((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Boolean bool) {
        if (bool.booleanValue()) {
            setEditString(getString(R.string.app_str_cancel_select_all));
        } else {
            setEditString(getString(R.string.app_str_select_all));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Integer num) {
        if (num.intValue() == 0) {
            ((ActSelectDaliAddBinding) this.mViewBinding).tvBottom.setAlpha(0.5f);
        } else {
            ((ActSelectDaliAddBinding) this.mViewBinding).tvBottom.setAlpha(1.0f);
        }
        ((ActSelectDaliAddBinding) this.mViewBinding).tvBottom.setText(String.format(getString(R.string.dali_scene_save), num));
    }
}