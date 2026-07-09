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
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.ui.device.light.ActSetLightOnState;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.ScaleView;
import com.smart.message.ResponseMsg;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSetLightOnState extends BaseNormalActivity<ActSelectBinding> {
    private Object controlObject;
    private MultipleItemRvAdapter<OnOffState, BaseViewHolder> mAdapter;
    private int selectPosition = -1;
    private int lastSelectPosition = -1;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.rv_content;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.light_on_state));
        setEditString(getString(R.string.save));
        MultipleItemRvAdapter<OnOffState, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<OnOffState, BaseViewHolder>(getContentList()) { // from class: com.ltech.smarthome.ui.device.light.ActSetLightOnState.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(OnOffState onOffState) {
                return onOffState.getType();
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<OnOffState, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.light.ActSetLightOnState.1.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_select;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return OnOffState.TYPE_DEFAULT;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, OnOffState data, int position) {
                        helper.setText(R.id.tv_name, data.getName());
                        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                        if (helper.getAdapterPosition() == ActSetLightOnState.this.selectPosition) {
                            helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true);
                        } else {
                            helper.setGone(R.id.iv_select, false);
                        }
                    }
                });
                this.mProviderDelegate.registerProvider(new AnonymousClass2());
            }

            /* renamed from: com.ltech.smarthome.ui.device.light.ActSetLightOnState$1$2, reason: invalid class name */
            class AnonymousClass2 extends BaseItemProvider<OnOffState, BaseViewHolder> {
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_select_on_off_diy;
                }

                AnonymousClass2() {
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return OnOffState.TYPE_DIY;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(BaseViewHolder helper, OnOffState data, final int position) {
                    helper.setText(R.id.tv_name, data.getName());
                    ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                    if (helper.getAdapterPosition() == ActSetLightOnState.this.selectPosition) {
                        helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true).setGone(R.id.scaleview, true);
                        ((ScaleView) helper.getView(R.id.scaleview)).setUnit("%");
                        ((ScaleView) helper.getView(R.id.scaleview)).setCurPosition(data.getSubValue());
                        ((ScaleView) helper.getView(R.id.scaleview)).setIValueChangeListener(new ScaleView.IValueChangeListener() { // from class: com.ltech.smarthome.ui.device.light.ActSetLightOnState$1$2$$ExternalSyntheticLambda0
                            @Override // com.ltech.smarthome.view.ScaleView.IValueChangeListener
                            public final void onValueChange(int i, int i2, boolean z) {
                                ActSetLightOnState.AnonymousClass1.AnonymousClass2.this.lambda$convert$0(position, i, i2, z);
                            }
                        });
                        return;
                    }
                    helper.setGone(R.id.iv_select, false).setGone(R.id.scaleview, false);
                    ((ScaleView) helper.getView(R.id.scaleview)).setIValueChangeListener(null);
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$0(int i, int i2, int i3, boolean z) {
                    if (z) {
                        ((OnOffState) ActSetLightOnState.this.mAdapter.getData().get(i)).setSubValue(i2 + i3);
                    }
                }
            }
        };
        this.mAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActSetLightOnState$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSetLightOnState.this.lambda$initView$0(baseQuickAdapter, view, i);
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
            Injection.repo().group().getGroupFromDb(longExtra).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActSetLightOnState$$ExternalSyntheticLambda2
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActSetLightOnState.this.lambda$startObserve$1((Group) obj);
                }
            });
        } else {
            Injection.repo().device().getDeviceFromDb(longExtra).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActSetLightOnState$$ExternalSyntheticLambda3
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActSetLightOnState.this.lambda$startObserve$2((Device) obj);
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

    private List<OnOffState> getContentList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new OnOffState(OnOffState.TYPE_DEFAULT, getString(R.string.brt_100), 1, 0));
        arrayList.add(new OnOffState(OnOffState.TYPE_DEFAULT, getString(R.string.brt_0), 2, 0));
        arrayList.add(new OnOffState(OnOffState.TYPE_DEFAULT, getString(R.string.brt_remember), 3, 0));
        arrayList.add(new OnOffState(OnOffState.TYPE_DIY, getString(R.string.brt_diy), 4, 50));
        return arrayList;
    }

    private void queryState() {
        showLoading();
        CmdAssistant.getLightCmdAssistant(this.controlObject, new int[0]).queryOnState(this, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActSetLightOnState$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSetLightOnState.this.lambda$queryState$3((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryState$3(ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getResData() != null && responseMsg.getResData().length() >= 18) {
            int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
            int size = this.mAdapter.getData().size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                if (parseInt == this.mAdapter.getData().get(i).mainValue) {
                    this.selectPosition = i;
                    this.mAdapter.getData().get(this.selectPosition).setSubValue(LightUtils.brt2Progress(Integer.parseInt(responseMsg.getResData().substring(18, 20), 16)));
                    this.mAdapter.notifyDataSetChanged();
                    break;
                }
                i++;
            }
        }
        showContent();
    }

    private static final class OnOffState {
        private static int TYPE_DEFAULT = 1;
        private static int TYPE_DIY = 2;
        private int mainValue;
        private String name;
        private int subValue;
        private int type;

        OnOffState(int type, String name, int mainValue, int subValue) {
            this.type = type;
            this.name = name;
            this.mainValue = mainValue;
            this.subValue = subValue;
        }

        int getType() {
            return this.type;
        }

        String getName() {
            return this.name;
        }

        int getMainValue() {
            return this.mainValue;
        }

        int getSubValue() {
            return this.subValue;
        }

        void setSubValue(int subValue) {
            this.subValue = subValue;
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.selectPosition == -1) {
            SmartToast.showShort(R.string.please_choose);
        } else {
            CmdAssistant.getLightCmdAssistant(this.controlObject, new int[0]).setOnState(this, this.mAdapter.getData().get(this.selectPosition).getMainValue(), LightUtils.progress2Brt(r0.getSubValue()), LightUtils.progress2Brt(r0.getSubValue()), new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActSetLightOnState$$ExternalSyntheticLambda4
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSetLightOnState.this.lambda$edit$4((Boolean) obj);
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