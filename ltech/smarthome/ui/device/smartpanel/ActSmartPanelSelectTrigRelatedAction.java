package com.ltech.smarthome.ui.device.smartpanel;

import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.trig.TrigRepository;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSmartPanelSelectTrigRelatedAction extends BaseSmartPanelActionListStringActivity<ActSelectBinding, ActSmartPanelSelectActionVM> implements ISelectAction {
    private boolean isScene;
    private RelatedInfoExtParam.RelateInfo relateInfo;
    private int subType;

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListStringActivity
    protected int itemLayout() {
        return R.layout.item_select;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$edit$1(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListStringActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
    }

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListStringActivity
    protected List<String> getList() {
        this.isScene = getIntent().getBooleanExtra(Constants.IS_SCENE, true);
        int intExtra = getIntent().getIntExtra(Constants.SUB_TYPE, 2);
        this.subType = intExtra;
        if (this.isScene) {
            if (intExtra == 2) {
                return Arrays.asList(NameRepository.getTrigScene8ActionName(this));
            }
            return Arrays.asList(NameRepository.getTrigSceneActionName(this));
        }
        if (intExtra == 0) {
            return Arrays.asList(NameRepository.getTrigCurtainActionName(this));
        }
        return Arrays.asList(NameRepository.getTrigDreamCurtainActionName(this));
    }

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListStringActivity
    protected void convertView(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSmartPanelSelectActionVM) this.mViewModel).relatePosition = getIntent().getIntExtra(Constants.RELATED_POSITION, -1);
        ((ActSmartPanelSelectActionVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActSmartPanelSelectActionVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        ((ActSmartPanelSelectActionVM) this.mViewModel).relateType = getIntent().getIntExtra(Constants.GROUP_RELATE, 1);
        if (((ActSmartPanelSelectActionVM) this.mViewModel).relateType == 2) {
            ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject = Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.RELATE_ID, -1L));
            this.relateInfo = RelatedInfoExtParam.RelateInfo.RelatedGroupInfo(((Group) ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject).getGroupId());
        } else {
            ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.RELATE_ID, -1L));
            this.relateInfo = RelatedInfoExtParam.RelateInfo.RelateCurtainDeviceInfo(((Device) ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject).getDeviceId());
        }
        this.relateInfo.type = 7;
        if (((ActSmartPanelSelectActionVM) this.mViewModel).groupControl) {
            ((ActSmartPanelSelectActionVM) this.mViewModel).controlGroup = Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
            ((ActSmartPanelSelectActionVM) this.mViewModel).relateInfoAssistant = new RelateInfoAssistant(((ActSmartPanelSelectActionVM) this.mViewModel).controlGroup);
        } else {
            ((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
            ((ActSmartPanelSelectActionVM) this.mViewModel).relateInfoAssistant = new RelateInfoAssistant(((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice);
        }
        if (this.isScene || this.subType == 0 || !isG4()) {
            return;
        }
        setDataList(Arrays.asList(NameRepository.getTrigDreamCurtainActionName2(this)));
    }

    private boolean isG4() {
        return ((ActSmartPanelSelectActionVM) this.mViewModel).groupControl ? ProductRepository.getLightColorType((Object) ((ActSmartPanelSelectActionVM) this.mViewModel).controlGroup) == 21 : ProductRepository.getLightColorType((Object) ((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice) == 21;
    }

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListStringActivity
    protected void onItemClick(int position) {
        super.onItemClick(position);
        if (this.isScene) {
            this.relateInfo.action = position + 1;
        } else if (this.subType == 0) {
            this.relateInfo.action = TrigRepository.getDefaultCurtainItemList().get(position).spanCount;
        } else if (isG4()) {
            this.relateInfo.action = TrigRepository.getDefaultDreamCurtainItemList2().get(position).spanCount;
        } else {
            this.relateInfo.action = TrigRepository.getDefaultDreamCurtainItemList().get(position).spanCount;
        }
        ((ActSmartPanelSelectActionVM) this.mViewModel).relateInfoAssistant.setRelateInfo(((ActSmartPanelSelectActionVM) this.mViewModel).relatePosition, this.relateInfo);
    }

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListStringActivity
    protected void save() {
        if (this.isScene) {
            ((ActSmartPanelSelectActionVM) this.mViewModel).subscribe(this.relateInfo.type, this.subType);
        } else if (this.subType == 0) {
            ((ActSmartPanelSelectActionVM) this.mViewModel).subscribe(this.relateInfo.type, 3);
        } else {
            ((ActSmartPanelSelectActionVM) this.mViewModel).subscribe(this.relateInfo.type, 4);
        }
    }
}