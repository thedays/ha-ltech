package com.ltech.smarthome.ui.device.super_panel;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseListActivity;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActAddKeySetAction extends BaseListActivity<GoItem> {
    private long placeId;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_go;
    }

    @Override // com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.add_action));
        setBackImage(R.mipmap.icon_back);
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActAddKeySetAction$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActAddKeySetAction.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ((GoItem) this.mAdapter.getData().get(i)).getAction().execute();
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<GoItem> getList() {
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(new GoItem().setMainText(getString(R.string.bind_device)).setImageRes(R.mipmap.ic_auto_device).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActAddKeySetAction$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActAddKeySetAction.this.lambda$getList$1();
            }
        })));
        arrayList.add(new GoItem().setMainText(getString(R.string.bind_group)).setImageRes(R.mipmap.ic_device_light_group).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActAddKeySetAction$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActAddKeySetAction.this.lambda$getList$2();
            }
        })));
        arrayList.add(new GoItem().setMainText(getString(R.string.bind_scene)).setImageRes(R.mipmap.ic_auto_scene).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActAddKeySetAction$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActAddKeySetAction.this.lambda$getList$3();
            }
        })));
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getList$1() {
        SceneHelper.goSelectAction(this, 1, this.placeId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getList$2() {
        SceneHelper.goSelectAction(this, 2, this.placeId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getList$3() {
        SceneHelper.goSelectAction(this, 4, this.placeId);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, GoItem item) {
        helper.setBackgroundRes(R.id.iv_icon, item.getImageRes()).setText(R.id.tv_main, item.getMainText()).setImageResource(R.id.iv_go, item.getGoRes());
        ((AppCompatTextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            finishActivity();
        }
    }
}