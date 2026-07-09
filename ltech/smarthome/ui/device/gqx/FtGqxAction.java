package com.ltech.smarthome.ui.device.gqx;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.databinding.FtGqxActionBinding;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.key.KeyInfo;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.singleton.ComboCmdHelper;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;

/* loaded from: classes4.dex */
public class FtGqxAction extends VMFragment<FtGqxActionBinding, ActGqxVM> {
    private int index;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_gqx_action;
    }

    @Override // com.ltech.smarthome.base.VMFragment
    protected boolean shareVM() {
        return true;
    }

    public static FtGqxAction create(int index) {
        FtGqxAction ftGqxAction = new FtGqxAction();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        ftGqxAction.setArguments(bundle);
        return ftGqxAction;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        if (getArguments() != null) {
            this.index = getArguments().getInt("index", 0);
            setImg();
            ((ActGqxVM) this.mViewModel).uiEvent.setValue(true);
        }
    }

    private void setImg() {
        int i = this.index;
        if (i == 0) {
            ((FtGqxActionBinding) this.mViewBinding).ivBind1.setBackgroundResource(ProductId.ID_SMART_PANEL_GQ.equalsIgnoreCase(((ActGqxVM) this.mViewModel).device.getProductId()) ? R.mipmap.pic_gq_click1 : R.mipmap.pic_gqx_click1);
            ((FtGqxActionBinding) this.mViewBinding).ivBind3.setBackgroundResource(ProductId.ID_SMART_PANEL_GQ.equalsIgnoreCase(((ActGqxVM) this.mViewModel).device.getProductId()) ? R.mipmap.pic_gq_longpress1 : R.mipmap.pic_gqx_longpress1);
            ((FtGqxActionBinding) this.mViewBinding).iv1.setBackgroundResource(ProductId.ID_SMART_PANEL_GQ.equalsIgnoreCase(((ActGqxVM) this.mViewModel).device.getProductId()) ? R.mipmap.pic_gq_click1_1 : R.mipmap.pic_gqx_click1_1);
            ((FtGqxActionBinding) this.mViewBinding).iv3.setBackgroundResource(ProductId.ID_SMART_PANEL_GQ.equalsIgnoreCase(((ActGqxVM) this.mViewModel).device.getProductId()) ? R.mipmap.pic_gq_longpress1_1 : R.mipmap.pic_gqx_longpress1_1);
        } else if (i == 1) {
            ((FtGqxActionBinding) this.mViewBinding).ivBind1.setBackgroundResource(ProductId.ID_SMART_PANEL_GQ.equalsIgnoreCase(((ActGqxVM) this.mViewModel).device.getProductId()) ? R.mipmap.pic_gq_click2 : R.mipmap.pic_gqx_click2);
            ((FtGqxActionBinding) this.mViewBinding).ivBind3.setBackgroundResource(ProductId.ID_SMART_PANEL_GQ.equalsIgnoreCase(((ActGqxVM) this.mViewModel).device.getProductId()) ? R.mipmap.pic_gq_longpress2 : R.mipmap.pic_gqx_longpress2);
            ((FtGqxActionBinding) this.mViewBinding).iv1.setBackgroundResource(ProductId.ID_SMART_PANEL_GQ.equalsIgnoreCase(((ActGqxVM) this.mViewModel).device.getProductId()) ? R.mipmap.pic_gq_click2_1 : R.mipmap.pic_gqx_click2_1);
            ((FtGqxActionBinding) this.mViewBinding).iv3.setBackgroundResource(ProductId.ID_SMART_PANEL_GQ.equalsIgnoreCase(((ActGqxVM) this.mViewModel).device.getProductId()) ? R.mipmap.pic_gq_longpress2_1 : R.mipmap.pic_gqx_longpress2_1);
        } else if (i == 2) {
            ((FtGqxActionBinding) this.mViewBinding).ivBind1.setBackgroundResource(ProductId.ID_SMART_PANEL_GQ.equalsIgnoreCase(((ActGqxVM) this.mViewModel).device.getProductId()) ? R.mipmap.pic_gq_click3 : R.mipmap.pic_gqx_click3);
            ((FtGqxActionBinding) this.mViewBinding).ivBind3.setBackgroundResource(ProductId.ID_SMART_PANEL_GQ.equalsIgnoreCase(((ActGqxVM) this.mViewModel).device.getProductId()) ? R.mipmap.pic_gq_longpress3 : R.mipmap.pic_gqx_longpress3);
            ((FtGqxActionBinding) this.mViewBinding).iv1.setBackgroundResource(ProductId.ID_SMART_PANEL_GQ.equalsIgnoreCase(((ActGqxVM) this.mViewModel).device.getProductId()) ? R.mipmap.pic_gq_click3_1 : R.mipmap.pic_gqx_click3_1);
            ((FtGqxActionBinding) this.mViewBinding).iv3.setBackgroundResource(ProductId.ID_SMART_PANEL_GQ.equalsIgnoreCase(((ActGqxVM) this.mViewModel).device.getProductId()) ? R.mipmap.pic_gq_longpress3_1 : R.mipmap.pic_gqx_longpress3_1);
        } else if (i == 3) {
            ((FtGqxActionBinding) this.mViewBinding).ivBind1.setBackgroundResource(ProductId.ID_SMART_PANEL_GQ.equalsIgnoreCase(((ActGqxVM) this.mViewModel).device.getProductId()) ? R.mipmap.pic_gq_click4 : R.mipmap.pic_gqx_click4);
            ((FtGqxActionBinding) this.mViewBinding).ivBind3.setBackgroundResource(ProductId.ID_SMART_PANEL_GQ.equalsIgnoreCase(((ActGqxVM) this.mViewModel).device.getProductId()) ? R.mipmap.pic_gq_longpress4 : R.mipmap.pic_gqx_longpress4);
            ((FtGqxActionBinding) this.mViewBinding).iv1.setBackgroundResource(ProductId.ID_SMART_PANEL_GQ.equalsIgnoreCase(((ActGqxVM) this.mViewModel).device.getProductId()) ? R.mipmap.pic_gq_click4_1 : R.mipmap.pic_gqx_click4_1);
            ((FtGqxActionBinding) this.mViewBinding).iv3.setBackgroundResource(ProductId.ID_SMART_PANEL_GQ.equalsIgnoreCase(((ActGqxVM) this.mViewModel).device.getProductId()) ? R.mipmap.pic_gq_longpress4_1 : R.mipmap.pic_gqx_longpress4_1);
        }
        ((FtGqxActionBinding) this.mViewBinding).iv2.setBackgroundResource(ProductId.ID_SMART_PANEL_GQ.equalsIgnoreCase(((ActGqxVM) this.mViewModel).device.getProductId()) ? R.mipmap.pic_gq_whirl_1 : R.mipmap.pic_gqx_whirl_1);
        ((FtGqxActionBinding) this.mViewBinding).ivBind2.setBackgroundResource(ProductId.ID_SMART_PANEL_GQ.equalsIgnoreCase(((ActGqxVM) this.mViewModel).device.getProductId()) ? R.mipmap.pic_gq_whirl : R.mipmap.pic_gqx_whirl);
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        super.startObserve();
        ((ActGqxVM) this.mViewModel).uiEvent.observe(this, new Observer<Object>() { // from class: com.ltech.smarthome.ui.device.gqx.FtGqxAction.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(Object o) {
                FtGqxAction.this.refresh();
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        refresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refresh() {
        if (this.mViewBinding == 0) {
            return;
        }
        KeyInfo keyShort = ComboCmdHelper.getInstance().getKeyShort(this.index + 1);
        KeyInfo keyKnob = ComboCmdHelper.getInstance().getKeyKnob(this.index + 1);
        KeyInfo keyLong = ComboCmdHelper.getInstance().getKeyLong(this.index + 1);
        if (keyShort == null && keyKnob == null && keyLong == null) {
            ((FtGqxActionBinding) this.mViewBinding).groupAction.setVisibility(8);
            ((FtGqxActionBinding) this.mViewBinding).groupIntro.setVisibility(0);
        } else {
            showAndHideView(keyShort, keyKnob, keyLong);
            TextView textView = ((FtGqxActionBinding) this.mViewBinding).tvDevName;
            Object relateObject = keyShort.getRelateObject();
            textView.setText(relateObject instanceof Role ? ((Role) relateObject).getName() : "");
        }
    }

    private void showAndHideView(KeyInfo info1, KeyInfo info2, KeyInfo info3) {
        if (info1 == null) {
            return;
        }
        Object relateObject = info1.getRelateObject();
        if (relateObject == null) {
            ((FtGqxActionBinding) this.mViewBinding).groupAction.setVisibility(8);
            ((FtGqxActionBinding) this.mViewBinding).groupIntro.setVisibility(0);
            return;
        }
        ((FtGqxActionBinding) this.mViewBinding).groupAction.setVisibility(0);
        ((FtGqxActionBinding) this.mViewBinding).groupIntro.setVisibility(8);
        String parseCmdForAction = ComboCmdHelper.getInstance().parseCmdForAction(relateObject, info1.getInstruction());
        String parseCmdForAction2 = ComboCmdHelper.getInstance().parseCmdForAction(relateObject, info2 != null ? info2.getInstruction() : null);
        String parseCmdForAction3 = ComboCmdHelper.getInstance().parseCmdForAction(relateObject, info3 != null ? info3.getInstruction() : null);
        if (TextUtils.isEmpty(parseCmdForAction)) {
            parseCmdForAction = getString(R.string.no_bind);
        }
        if (TextUtils.isEmpty(parseCmdForAction)) {
            parseCmdForAction2 = getString(R.string.no_bind);
        }
        if (TextUtils.isEmpty(parseCmdForAction)) {
            parseCmdForAction3 = getString(R.string.no_bind);
        }
        hasMorActions(relateObject);
        if (relateObject instanceof Scene) {
            ((FtGqxActionBinding) this.mViewBinding).layoutBind1.setVisibility(0);
            ((FtGqxActionBinding) this.mViewBinding).layoutBind2.setVisibility(8);
            ((FtGqxActionBinding) this.mViewBinding).layoutBind3.setVisibility(8);
            ((FtGqxActionBinding) this.mViewBinding).tvBindAction1.setText(parseCmdForAction);
            return;
        }
        int lightColorType = ProductRepository.getLightColorType(relateObject);
        if (ProductRepository.isDaliLightGroup(relateObject)) {
            lightColorType = DaliProHelper.convertDaliType(relateObject);
        }
        if (lightColorType == 1 || lightColorType == 2 || lightColorType == 3 || lightColorType == 4 || lightColorType == 5 || lightColorType == 12 || lightColorType == 14 || lightColorType == 20 || lightColorType == 10007 || lightColorType == 16 || lightColorType == 17) {
            ((FtGqxActionBinding) this.mViewBinding).layoutBind1.setVisibility(0);
            ((FtGqxActionBinding) this.mViewBinding).layoutBind2.setVisibility(0);
            ((FtGqxActionBinding) this.mViewBinding).layoutBind3.setVisibility(0);
            ((FtGqxActionBinding) this.mViewBinding).tvBindAction1.setText(parseCmdForAction);
            ((FtGqxActionBinding) this.mViewBinding).tvBindAction2.setText(parseCmdForAction2);
            ((FtGqxActionBinding) this.mViewBinding).tvBindAction3.setText(parseCmdForAction3);
            if (ComboCmdHelper.getInstance().isAllBrtCmd(relateObject, info2 != null ? info2.getInstruction() : null)) {
                ((FtGqxActionBinding) this.mViewBinding).tvBindActionSub2.setVisibility(0);
            } else {
                ((FtGqxActionBinding) this.mViewBinding).tvBindActionSub2.setVisibility(8);
            }
        }
    }

    private void hasMorActions(Object o) {
        if (o instanceof Scene) {
            ((FtGqxActionBinding) this.mViewBinding).tvBindActionMore1.setVisibility(4);
            return;
        }
        ((FtGqxActionBinding) this.mViewBinding).tvBindActionMore1.setVisibility(4);
        int lightColorType = ProductRepository.getLightColorType(o);
        if (lightColorType != 1) {
            if (lightColorType == 12 || lightColorType == 14) {
                ((FtGqxActionBinding) this.mViewBinding).tvBindActionMore2.setVisibility(4);
                ((FtGqxActionBinding) this.mViewBinding).tvBindActionMore3.setVisibility(4);
                return;
            }
            if (lightColorType != 16) {
                if (lightColorType == 10007) {
                    ((FtGqxActionBinding) this.mViewBinding).tvBindActionMore3.setVisibility(4);
                    return;
                }
                switch (lightColorType) {
                    case 101:
                    case 102:
                    case 103:
                    case 104:
                    case 105:
                        break;
                    default:
                        ((FtGqxActionBinding) this.mViewBinding).tvBindActionMore1.setVisibility(4);
                        ((FtGqxActionBinding) this.mViewBinding).tvBindActionMore2.setVisibility(0);
                        ((FtGqxActionBinding) this.mViewBinding).tvBindActionMore3.setVisibility(0);
                        break;
                }
                return;
            }
            return;
        }
        ((FtGqxActionBinding) this.mViewBinding).tvBindActionMore2.setVisibility(4);
        ((FtGqxActionBinding) this.mViewBinding).tvBindActionMore3.setVisibility(0);
    }
}