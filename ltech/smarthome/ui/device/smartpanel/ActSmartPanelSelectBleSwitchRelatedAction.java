package com.ltech.smarthome.ui.device.smartpanel;

import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSmartPanelSelectBleSwitchRelatedAction extends BaseSmartPanelActionListActivity<ActSelectBinding, ActSmartPanelSelectActionVM> {
    private RelatedInfoExtParam.RelateInfo relateInfo;

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListActivity
    protected int itemLayout() {
        return R.layout.item_select;
    }

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        ((ActSelectBinding) this.mViewBinding).tvTip.setText(getString(R.string.key_bind_tip));
        ((ActSelectBinding) this.mViewBinding).tvTip.setVisibility(0);
    }

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListActivity
    protected List<BindValue> getList() {
        return LightBindUtilsKt.getSwitchList();
    }

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListActivity
    protected void convertView(BaseViewHolder helper, BindValue item) {
        helper.setText(R.id.tv_name, item.getNameRes()).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSmartPanelSelectActionVM) this.mViewModel).relatePosition = getIntent().getIntExtra(Constants.RELATED_POSITION, -1);
        ((ActSmartPanelSelectActionVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        ((ActSmartPanelSelectActionVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActSmartPanelSelectActionVM) this.mViewModel).relateType = getIntent().getIntExtra(Constants.GROUP_RELATE, -1);
        if (((ActSmartPanelSelectActionVM) this.mViewModel).relateType == 2) {
            ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject = Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.RELATE_ID, -1L));
            this.relateInfo = RelatedInfoExtParam.RelateInfo.RelatedGroupInfo(((Group) ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject).getGroupId());
        } else if (((ActSmartPanelSelectActionVM) this.mViewModel).relateType == 1) {
            ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.RELATE_ID, -1L));
            this.relateInfo = RelatedInfoExtParam.RelateInfo.RelatedDeviceInfo(((Device) ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject).getDeviceId());
        }
        if (((ActSmartPanelSelectActionVM) this.mViewModel).groupControl) {
            ((ActSmartPanelSelectActionVM) this.mViewModel).controlGroup = Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
            ((ActSmartPanelSelectActionVM) this.mViewModel).relateInfoAssistant = new RelateInfoAssistant(((ActSmartPanelSelectActionVM) this.mViewModel).controlGroup);
            return;
        }
        ((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        ((ActSmartPanelSelectActionVM) this.mViewModel).relateInfoAssistant = new RelateInfoAssistant(((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice);
    }

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListActivity
    protected void onItemClick(int position) {
        super.onItemClick(position);
        RelatedInfoExtParam.RelateInfo relateInfo = this.relateInfo;
        if (relateInfo != null) {
            if (position == 0) {
                relateInfo.action = 4;
            } else if (position == 1) {
                relateInfo.action = 5;
            } else if (position == 2) {
                relateInfo.action = 6;
            }
            ((ActSmartPanelSelectActionVM) this.mViewModel).relateInfoAssistant.setRelateInfo(((ActSmartPanelSelectActionVM) this.mViewModel).relatePosition, this.relateInfo);
        }
    }

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListActivity
    protected void save() {
        ((ActSmartPanelSelectActionVM) this.mViewModel).subscribe();
    }
}