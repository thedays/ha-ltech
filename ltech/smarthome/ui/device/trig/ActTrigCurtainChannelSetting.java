package com.ltech.smarthome.ui.device.trig;

import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.IActionWithReturn;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActTrigCurtainChannelSetBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.ui.device.trig.TrigRepository;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.view.dialog.SingleSelectItemDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActTrigCurtainChannelSetting extends VMActivity<ActTrigCurtainChannelSetBinding, ActTrigCurtainChannelSettingVM> {
    int currentChannel;
    private boolean groupControl;
    BaseQuickAdapter<TrigRepository.TrigItem, BaseViewHolder> keyAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_trig_curtain_channel_set;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.app_str_channel_setting));
        setBackImage(R.mipmap.icon_back);
        ((ActTrigCurtainChannelSettingVM) this.mViewModel).mode = getIntent().getIntExtra(Constants.SUB_TYPE, 4);
        if (((ActTrigCurtainChannelSettingVM) this.mViewModel).mode != 3) {
            if (((ActTrigCurtainChannelSettingVM) this.mViewModel).mode == 1) {
                ((ActTrigCurtainChannelSettingVM) this.mViewModel).keyItemList = TrigRepository.getDefaultCurtain2ItemList();
            } else {
                ((ActTrigCurtainChannelSettingVM) this.mViewModel).keyItemList = TrigRepository.getDefaultCurtainItemList();
            }
        } else {
            ((ActTrigCurtainChannelSettingVM) this.mViewModel).keyItemList = TrigRepository.getDreamCurtainChannelItemList();
        }
        BaseQuickAdapter<TrigRepository.TrigItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<TrigRepository.TrigItem, BaseViewHolder>(R.layout.item_smart_panel_key_set, ((ActTrigCurtainChannelSettingVM) this.mViewModel).keyItemList) { // from class: com.ltech.smarthome.ui.device.trig.ActTrigCurtainChannelSetting.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, TrigRepository.TrigItem item) {
                String str;
                helper.setText(R.id.tv_main, item.name).setGone(R.id.tv_sub_text, item.channel != -1);
                int i = item.channel;
                if (i == 1) {
                    str = ActTrigCurtainChannelSetting.this.getString(R.string.app_str_channel) + "1";
                } else if (i == 2) {
                    str = ActTrigCurtainChannelSetting.this.getString(R.string.app_str_channel) + "2";
                } else if (i == 3) {
                    str = ActTrigCurtainChannelSetting.this.getString(R.string.app_str_channel) + "1、" + ActTrigCurtainChannelSetting.this.getString(R.string.app_str_channel) + "2";
                } else if (i == 4) {
                    str = ActTrigCurtainChannelSetting.this.getString(R.string.app_str_channel) + "3";
                } else if (i == 8) {
                    str = ActTrigCurtainChannelSetting.this.getString(R.string.app_str_channel) + "4";
                } else if (i != 12) {
                    str = "";
                } else {
                    str = ActTrigCurtainChannelSetting.this.getString(R.string.app_str_channel) + "3、" + ActTrigCurtainChannelSetting.this.getString(R.string.app_str_channel) + "4";
                }
                helper.setText(R.id.tv_sub_text, str);
                helper.setTextColor(R.id.tv_sub_text, ActTrigCurtainChannelSetting.this.getResources().getColor((item.channel == 3 || item.channel == 12) ? R.color.color_light_gray : R.color.color_text_gray));
            }
        };
        this.keyAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigCurtainChannelSetting$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActTrigCurtainChannelSetting.this.lambda$initView$0(baseQuickAdapter2, view, i);
            }
        });
        this.keyAdapter.bindToRecyclerView(((ActTrigCurtainChannelSetBinding) this.mViewBinding).rvContent);
        ((ActTrigCurtainChannelSetBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this, 1, false));
        ((ActTrigCurtainChannelSetBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (((ActTrigCurtainChannelSettingVM) this.mViewModel).keyItemList.get(i).channel == 3 || ((ActTrigCurtainChannelSettingVM) this.mViewModel).keyItemList.get(i).channel == 12) {
            return;
        }
        showTipDialog(i);
    }

    private void showTipDialog(final int pos) {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.app_str_trig_channel_setting_tip)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigCurtainChannelSetting$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showTipDialog$1;
                lambda$showTipDialog$1 = ActTrigCurtainChannelSetting.this.lambda$showTipDialog$1(pos, baseDialog, view);
                return lambda$showTipDialog$1;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showTipDialog$1(int i, BaseDialog baseDialog, View view) {
        showSelectDialog(i);
        return false;
    }

    private void showSelectDialog(final int pos) {
        ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < TrigRepository.getDefaultCurtainChannelItemList().size(); i++) {
            SingleSelectItemDialog.ListItem listItem = new SingleSelectItemDialog.ListItem();
            listItem.name = TrigRepository.getDefaultCurtainChannelItemList().get(i).name;
            if (((ActTrigCurtainChannelSettingVM) this.mViewModel).mode != 3) {
                arrayList.add(listItem);
                arrayList2.add(TrigRepository.getDefaultCurtainChannelItemList().get(i));
            } else if (pos == 0 || pos == 1) {
                if (i == 0 || i == 1) {
                    arrayList.add(listItem);
                    arrayList2.add(TrigRepository.getDefaultCurtainChannelItemList().get(i));
                }
            } else if ((pos == 3 || pos == 4) && (i == 2 || i == 3)) {
                arrayList.add(listItem);
                arrayList2.add(TrigRepository.getDefaultCurtainChannelItemList().get(i));
            }
        }
        SingleSelectItemDialog.asDefault().setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setSelectList(arrayList).setTitle(getString(R.string.please_choose)).setItemClickAction(new IActionWithReturn() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigCurtainChannelSetting$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IActionWithReturn
            public final Object act(Object obj) {
                Boolean lambda$showSelectDialog$2;
                lambda$showSelectDialog$2 = ActTrigCurtainChannelSetting.this.lambda$showSelectDialog$2(arrayList2, (Integer) obj);
                return lambda$showSelectDialog$2;
            }
        }).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigCurtainChannelSetting$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActTrigCurtainChannelSetting.this.lambda$showSelectDialog$3(pos, (Integer) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$showSelectDialog$2(List list, Integer num) {
        this.currentChannel = ((TrigRepository.TrigItem) list.get(num.intValue())).spanCount;
        return Boolean.TRUE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectDialog$3(int i, Integer num) {
        int i2 = ((ActTrigCurtainChannelSettingVM) this.mViewModel).keyItemList.get(i).spanCount;
        TrigRepository.TrigItem trigItem = ((ActTrigCurtainChannelSettingVM) this.mViewModel).keyItemList.get(i);
        trigItem.channel = this.currentChannel;
        this.keyAdapter.setData(i, trigItem);
        ((ActTrigCurtainChannelSettingVM) this.mViewModel).updateExtParam(i2, this.currentChannel);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActTrigCurtainChannelSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActTrigCurtainChannelSettingVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActTrigCurtainChannelSettingVM) this.mViewModel).deviceId = getIntent().getLongExtra("device_id", -1L);
        ((ActTrigCurtainChannelSettingVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActTrigCurtainChannelSettingVM) this.mViewModel).samePlace = getIntent().getBooleanExtra(Constants.SAME_PLACE, true);
        this.groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        ((ActTrigCurtainChannelSettingVM) this.mViewModel).items.observe(this, new Observer<List<TrigRepository.TrigItem>>() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigCurtainChannelSetting.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<TrigRepository.TrigItem> trigItems) {
                ActTrigCurtainChannelSetting.this.keyAdapter.replaceData(trigItems);
            }
        });
        if (!this.groupControl) {
            ((ActTrigCurtainChannelSettingVM) this.mViewModel).controlObject.setValue(Injection.repo().device().getDeviceById(((ActTrigCurtainChannelSettingVM) this.mViewModel).controlId));
        } else {
            ((ActTrigCurtainChannelSettingVM) this.mViewModel).controlObject.setValue(Injection.repo().group().getGroupById(((ActTrigCurtainChannelSettingVM) this.mViewModel).controlId));
        }
        ((ActTrigCurtainChannelSettingVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigCurtainChannelSetting$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActTrigCurtainChannelSetting.this.lambda$startObserve$4(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Object obj) {
        ((ActTrigCurtainChannelSettingVM) this.mViewModel).loadChanel();
    }
}