package com.ltech.smarthome.ui.device.smartpanel;

import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectRelateKey extends BaseSmartPanelActionListStringActivity<ActSelectBinding, ActSmartPanelSelectActionVM> implements ISelectAction {
    private int controlType;
    private String[] datas = new String[0];

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
        setTitle(getString(R.string.app_str_select_key));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        ((ActSmartPanelSelectActionVM) this.mViewModel).relatePosition = getIntent().getIntExtra(Constants.RELATED_POSITION, -1);
        this.controlType = getIntent().getIntExtra(Constants.LIGHT_TYPE, 0);
        ((ActSmartPanelSelectActionVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        ((ActSmartPanelSelectActionVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActSmartPanelSelectActionVM) this.mViewModel).relateType = getIntent().getIntExtra(Constants.GROUP_RELATE, -1);
    }

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListStringActivity
    protected List<String> getList() {
        return new ArrayList();
    }

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListStringActivity
    protected void convertView(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        if (((ActSmartPanelSelectActionVM) this.mViewModel).relateType == 2) {
            ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject = Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.RELATE_ID, -1L));
            initData(((ActSmartPanelSelectActionVM) this.mViewModel).relateObject);
        } else if (((ActSmartPanelSelectActionVM) this.mViewModel).relateType == 1) {
            ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.RELATE_ID, -1L));
            initData(((ActSmartPanelSelectActionVM) this.mViewModel).relateObject);
        }
        if (((ActSmartPanelSelectActionVM) this.mViewModel).groupControl) {
            ((ActSmartPanelSelectActionVM) this.mViewModel).controlGroup = Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
            ((ActSmartPanelSelectActionVM) this.mViewModel).relateInfoAssistant = new RelateInfoAssistant(((ActSmartPanelSelectActionVM) this.mViewModel).controlGroup);
            return;
        }
        ((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        ((ActSmartPanelSelectActionVM) this.mViewModel).relateInfoAssistant = new RelateInfoAssistant(((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x016f, code lost:
    
        if (r3.equals(com.ltech.smarthome.model.product.ProductId.ID_SMART_SWITCH_S3_PRO) == false) goto L26;
     */
    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListStringActivity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onItemClick(int r10) {
        /*
            Method dump skipped, instructions count: 564
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.smartpanel.ActSelectRelateKey.onItemClick(int):void");
    }

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListStringActivity
    protected void save() {
        if (Injection.state().isConnectOuterNet()) {
            showLoadingDialog(getString(R.string.subscribing));
            if (((ActSmartPanelSelectActionVM) this.mViewModel).groupControl) {
                ((ActSmartPanelSelectActionVM) this.mViewModel).subscribe(this.selectPosition);
                return;
            }
            String str = ((ActSmartPanelSelectActionVM) this.mViewModel).productId;
            str.hashCode();
            switch (str) {
                case "122041818260301":
                case "122041818283501":
                case "122041818304701":
                case "3895993722014848":
                case "3683369128495808":
                case "4249823578721536":
                case "3701704216101056":
                case "123031312002001":
                case "221042516351701":
                case "123072510445601":
                case "221030816330401":
                case "3701703750123712":
                case "121031814513301":
                case "121042516340801":
                case "121042516345401":
                    ((ActSmartPanelSelectActionVM) this.mViewModel).subscribe(this.selectPosition);
                    break;
                default:
                    ((ActSmartPanelSelectActionVM) this.mViewModel).rcSubscribe(this.selectPosition);
                    break;
            }
            return;
        }
        SmartToast.showShort(R.string.no_network);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x006c, code lost:
    
        if (r0.equals(com.ltech.smarthome.model.product.ProductId.ID_SMART_SWITCH_S2C) == false) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void initData(java.lang.Object r9) {
        /*
            Method dump skipped, instructions count: 604
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.smartpanel.ActSelectRelateKey.initData(java.lang.Object):void");
    }
}