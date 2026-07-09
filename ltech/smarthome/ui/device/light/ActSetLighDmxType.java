package com.ltech.smarthome.ui.device.light;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSetLighDmxType extends BaseNormalActivity<ActSelectBinding> {
    private Object controlObject;
    private MultipleItemRvAdapter<String, BaseViewHolder> mAdapter;
    private int selectPosition = -1;
    private int lastSelectPosition = -1;
    private DefaultAdapter mDefaultAdapter = new DefaultAdapter();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.rv_content;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(this.mDefaultAdapter);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        queryState();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.ct_dmx_address_type_set));
        setEditString(getString(R.string.save));
        MultipleItemRvAdapter<String, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<String, BaseViewHolder>(getContentList()) { // from class: com.ltech.smarthome.ui.device.light.ActSetLighDmxType.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(String onOffState) {
                return 0;
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<String, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.light.ActSetLighDmxType.1.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_select;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 0;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, String data, int position) {
                        helper.setText(R.id.tv_name, data);
                        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                        if (helper.getAdapterPosition() == ActSetLighDmxType.this.selectPosition) {
                            helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true);
                        } else {
                            helper.setGone(R.id.iv_select, false);
                        }
                    }
                });
            }
        };
        this.mAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActSetLighDmxType$$ExternalSyntheticLambda2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSetLighDmxType.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
        this.mAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((ActSelectBinding) this.mViewBinding).rvContent);
        ((ActSelectBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSelectBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int i2 = this.selectPosition;
        if (i2 != i) {
            this.lastSelectPosition = i2;
            this.selectPosition = i;
            if (i2 != -1) {
                this.mAdapter.notifyItemChanged(i2);
            }
            int i3 = this.selectPosition;
            if (i3 != -1) {
                this.mAdapter.notifyItemChanged(i3);
            }
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        long longExtra = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        if (booleanExtra) {
            Injection.repo().group().getGroupFromDb(longExtra).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActSetLighDmxType$$ExternalSyntheticLambda0
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActSetLighDmxType.this.lambda$startObserve$1((Group) obj);
                }
            });
        } else {
            Injection.repo().device().getDeviceFromDb(longExtra).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActSetLighDmxType$$ExternalSyntheticLambda1
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActSetLighDmxType.this.lambda$startObserve$2((Device) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Group group) {
        if (this.controlObject == null) {
            this.controlObject = group;
            queryState();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Device device) {
        if (this.controlObject == null) {
            this.controlObject = device;
            queryState();
        }
    }

    private List<String> getContentList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.ct_dmx_address_type_C));
        arrayList.add(getString(R.string.ct_dmx_address_type_BRT));
        return arrayList;
    }

    private void queryState() {
        showLoading();
        CmdAssistant.getLightCmdAssistant(this.controlObject, new int[0]).queryCtLightMode(this, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActSetLighDmxType$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSetLighDmxType.this.lambda$queryState$3((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryState$3(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            LHomeLog.i(getClass(), "responseMsg not null");
            LHomeLog.i(getClass(), "res-->" + responseMsg.getResData());
            if (responseMsg.getStateCode() == 153) {
                this.mDefaultAdapter.loadFailStringRes(R.string.app_str_device_not_support);
                showError();
                return;
            }
            if (responseMsg.getResData() != null && responseMsg.getResData().length() >= 18) {
                int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
                int size = this.mAdapter.getData().size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    if (i == parseInt - 1) {
                        this.selectPosition = i;
                        this.mAdapter.notifyDataSetChanged();
                        break;
                    }
                    i++;
                }
            }
            showContent();
            return;
        }
        LHomeLog.i(getClass(), "responseMsg is null");
        this.mDefaultAdapter.loadFailStringRes(R.string.error_loading);
        showError();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.selectPosition == -1) {
            SmartToast.showShort(R.string.please_choose);
        } else {
            CmdAssistant.getLightCmdAssistant(this.controlObject, new int[0]).setCtLightMode(this, this.selectPosition + 1, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActSetLighDmxType$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSetLighDmxType.this.lambda$edit$4((Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$4(Boolean bool) {
        if (bool.booleanValue()) {
            back();
        }
    }
}