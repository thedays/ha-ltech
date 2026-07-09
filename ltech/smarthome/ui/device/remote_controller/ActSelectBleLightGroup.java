package com.ltech.smarthome.ui.device.remote_controller;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseSelectGroupActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.smartpanel.ActSelectRelateKey;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectBleLightGroup extends BaseSelectGroupActivity {
    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_device_manage;
    }

    @Override // com.ltech.smarthome.base.BaseSelectGroupActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActSelectBleLightGroup$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSelectBleLightGroup.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int lightColorType = ProductRepository.getLightColorType(this.mAdapter.getData().get(i));
        if (lightColorType != 18) {
            switch (lightColorType) {
                case 8:
                case 9:
                case 10:
                case 11:
                    break;
                default:
                    NavUtils.destination(ActSelectRelatedAction.class).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withLong(Constants.RELATE_ID, ((Group) this.mAdapter.getData().get(i)).getId()).withBoolean(Constants.GROUP_CONTROL, true).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withDefaultRequestCode().navigation(this);
                    break;
            }
            return;
        }
        NavUtils.destination(ActSelectRelateKey.class).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withLong(Constants.RELATE_ID, ((Group) this.mAdapter.getData().get(i)).getId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, getIntent().getIntExtra(Constants.GROUP_RELATE, -1)).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withBoolean(Constants.GROUP_CONTROL, getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false)).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, Group item) {
        helper.setText(R.id.tv_device_name, item.getGroupName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setGone(R.id.tv_place_info, false);
        ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseSelectGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        handleData(this.mRequest, new IAction() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActSelectBleLightGroup$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectBleLightGroup.this.lambda$startObserve$1((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Group group = (Group) it.next();
            if ("3".equalsIgnoreCase(group.getModuleType()) && group.getDeviceIds() != null) {
                arrayList.add(group);
            }
        }
        if (arrayList.isEmpty()) {
            showEmpty();
        } else {
            this.mAdapter.setNewData(arrayList);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3001) {
            finishActivity();
        }
    }
}