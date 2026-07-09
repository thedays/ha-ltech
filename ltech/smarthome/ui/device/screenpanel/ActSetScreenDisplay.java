package com.ltech.smarthome.ui.device.screenpanel;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSetScreenDisplayBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.ScreenIconUtils;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.SetScreenDisplayDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class ActSetScreenDisplay extends VMActivity<ActSetScreenDisplayBinding, ActSmartPanelVM> {
    private int[] backgroundResArray;
    private int controlType;
    protected BaseQuickAdapter<String, BaseViewHolder> displayAdapter;
    protected BaseQuickAdapter<RelateInfoItem, BaseViewHolder> infoAdapter;
    private GridLayoutManager mGridLayoutManager;
    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mScreenGridLayoutManager;
    private LinearLayoutManager mScreenLinearLayoutManager;
    private RelatedInfoExtParam.RelateInfo relateInfo;
    protected BaseQuickAdapter<RelateInfoItem, BaseViewHolder> screenAdapter;
    private int[] screenBgRes;
    private SetScreenDisplayDialog setScreenDialog;
    private int spanCount;
    private int zoneCount;
    private int selectPosition = -1;
    private String name = ".";

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_set_screen_display;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.set_display));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        LHomeLog.i(getClass(), "message_send startObserve() enter");
        ((ActSmartPanelVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActSmartPanelVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActSmartPanelVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActSmartPanelVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        if (((ActSmartPanelVM) this.mViewModel).groupControl) {
            startGroupObserve();
        } else {
            startDeviceObserve();
        }
        ((ActSmartPanelVM) this.mViewModel).updateUIEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSetScreenDisplay$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSetScreenDisplay.this.lambda$startObserve$0(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Object obj) {
        ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(obj);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
    }

    private void startObjectObserve() {
        if (((ActSmartPanelVM) this.mViewModel).groupControl) {
            ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(Injection.repo().group().getGroupById(((ActSmartPanelVM) this.mViewModel).controlId));
            return;
        }
        Device deviceById = Injection.repo().device().getDeviceById(((ActSmartPanelVM) this.mViewModel).controlId);
        try {
            if (((ActSmartPanelVM) this.mViewModel).controlObject.getValue() == null) {
                ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(deviceById);
            } else {
                if (HelpUtils.compareObject((Device) ((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), deviceById)) {
                    return;
                }
                ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(deviceById);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startDeviceObserve() {
        ((ActSmartPanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSetScreenDisplay$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSetScreenDisplay.this.lambda$startDeviceObserve$1(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDeviceObserve$1(Object obj) {
        LHomeLog.i(getClass(), "message_send (device)mViewModel.controlObject enter");
        if (obj instanceof Device) {
            Device device = (Device) obj;
            String productId = device.getProductId();
            productId.hashCode();
            switch (productId) {
                case "122041818260301":
                    this.spanCount = 1;
                    this.zoneCount = 1;
                    this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s1pro);
                    this.screenBgRes = new int[]{R.mipmap.s1_screen};
                    break;
                case "122041818283501":
                    this.spanCount = 2;
                    this.zoneCount = 2;
                    this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s2pro);
                    this.screenBgRes = new int[]{R.mipmap.s2_screen2_1, R.mipmap.s2_screen2_2};
                    break;
                case "122041818304701":
                    this.spanCount = 3;
                    this.zoneCount = 3;
                    this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s3pro);
                    this.screenBgRes = new int[]{R.mipmap.s3_screen3_1, R.mipmap.s3_screen3_2, R.mipmap.s3_screen3_3};
                    break;
                case "122111615282701":
                    this.spanCount = 1;
                    this.zoneCount = 1;
                    this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_sqpro);
                    this.screenBgRes = new int[]{R.mipmap.s1_screen};
                    this.name = "";
                    break;
            }
            initRelatedInfoView(device);
        }
    }

    private void startGroupObserve() {
        ((ActSmartPanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSetScreenDisplay$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSetScreenDisplay.this.lambda$startGroupObserve$2(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startGroupObserve$2(Object obj) {
        if (obj instanceof Group) {
            Group group = (Group) obj;
            int lightColorType = ProductRepository.getLightColorType((Object) group);
            if (lightColorType == 9) {
                this.spanCount = 2;
                this.zoneCount = 2;
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s2pro);
                this.screenBgRes = new int[]{R.mipmap.s2_screen2_1, R.mipmap.s2_screen2_2};
            } else if (lightColorType == 10) {
                this.spanCount = 3;
                this.zoneCount = 3;
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s3pro);
                this.screenBgRes = new int[]{R.mipmap.s3_screen3_1, R.mipmap.s3_screen3_2, R.mipmap.s3_screen3_3};
            } else {
                this.spanCount = 1;
                this.zoneCount = 1;
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s1pro);
                this.screenBgRes = new int[]{R.mipmap.s1_screen};
            }
            initRelatedInfoView(group);
        }
    }

    private void initRelatedInfoView(Device device) {
        ((ActSmartPanelVM) this.mViewModel).initPanelState(device);
        ((ActSmartPanelVM) this.mViewModel).initRelateInfoList(device);
        initInfoAdapter();
        initScreenAdapter();
        this.screenAdapter.replaceData(((ActSmartPanelVM) this.mViewModel).relatedInfoList);
        initDisplayAdapter();
    }

    private void initRelatedInfoView(Group group) {
        ((ActSmartPanelVM) this.mViewModel).initPanelState(group);
        ((ActSmartPanelVM) this.mViewModel).initRelateInfoList(group);
        initInfoAdapter();
        initScreenAdapter();
        this.screenAdapter.replaceData(((ActSmartPanelVM) this.mViewModel).relatedInfoList);
        initDisplayAdapter();
    }

    private void initInfoAdapter() {
        BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.infoAdapter;
        if (baseQuickAdapter == null) {
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_smart_panel_key, ((ActSmartPanelVM) this.mViewModel).relatedInfoList) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSetScreenDisplay.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, RelateInfoItem item) {
                    LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.layout_content);
                    ViewGroup.LayoutParams layoutParams = helper.itemView.getLayoutParams();
                    int i = ActSetScreenDisplay.this.spanCount;
                    if (i == 2 || i == 3) {
                        linearLayout.setGravity(81);
                        linearLayout.setPadding(0, 0, 0, Utils.dip2px(this.mContext, 30.0f));
                        layoutParams.height = ActSetScreenDisplay.this.mGridLayoutManager.getHeight();
                    } else {
                        linearLayout.setGravity(81);
                        linearLayout.setPadding(0, 0, 0, Utils.dip2px(this.mContext, 30.0f));
                        layoutParams.height = ActSetScreenDisplay.this.mLinearLayoutManager.getHeight();
                    }
                    helper.itemView.setBackgroundResource(ActSetScreenDisplay.this.backgroundResArray[helper.getBindingAdapterPosition()]);
                    helper.setText(R.id.tv_device_name, ActSetScreenDisplay.this.name);
                    helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(ActSetScreenDisplay.this, R.color.colorPrimary));
                    ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
                }
            };
            this.infoAdapter = baseQuickAdapter2;
            baseQuickAdapter2.bindToRecyclerView(((ActSetScreenDisplayBinding) this.mViewBinding).rvKeyInfo);
            int i = this.spanCount;
            if (i == 2 || i == 3) {
                RecyclerView recyclerView = ((ActSetScreenDisplayBinding) this.mViewBinding).rvKeyInfo;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, this.spanCount);
                this.mGridLayoutManager = gridLayoutManager;
                recyclerView.setLayoutManager(gridLayoutManager);
            } else {
                RecyclerView recyclerView2 = ((ActSetScreenDisplayBinding) this.mViewBinding).rvKeyInfo;
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                this.mLinearLayoutManager = linearLayoutManager;
                recyclerView2.setLayoutManager(linearLayoutManager);
                ((ActSetScreenDisplayBinding) this.mViewBinding).rvKeyInfo.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSetScreenDisplay.2
                    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        outRect.set(0, 0, 0, 0);
                    }
                });
            }
            ((ActSetScreenDisplayBinding) this.mViewBinding).rvKeyInfo.setHasFixedSize(true);
            ((DefaultItemAnimator) ((ActSetScreenDisplayBinding) this.mViewBinding).rvKeyInfo.getItemAnimator()).setSupportsChangeAnimations(false);
            return;
        }
        baseQuickAdapter.replaceData(((ActSmartPanelVM) this.mViewModel).relatedInfoList);
    }

    private void initScreenAdapter() {
        if (this.screenAdapter != null) {
            return;
        }
        BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_screen_panel, ((ActSmartPanelVM) this.mViewModel).relatedInfoList) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSetScreenDisplay.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, RelateInfoItem item) {
                RelatedInfoExtParam.RelateInfo relateInfo;
                ViewGroup.LayoutParams layoutParams = helper.itemView.getLayoutParams();
                int i = ActSetScreenDisplay.this.spanCount;
                if (i == 2 || i == 3) {
                    layoutParams.height = ActSetScreenDisplay.this.mScreenGridLayoutManager.getHeight();
                } else {
                    layoutParams.height = ActSetScreenDisplay.this.mScreenLinearLayoutManager.getHeight();
                }
                helper.itemView.setBackgroundResource(ActSetScreenDisplay.this.screenBgRes[helper.getBindingAdapterPosition()]);
                boolean z = ((ActSmartPanelVM) ActSetScreenDisplay.this.mViewModel).relateInfoAssistant.getSwitchScreenBigIcon() == 2;
                ViewGroup.LayoutParams layoutParams2 = ((ImageView) helper.getView(R.id.iv_screen)).getLayoutParams();
                TextView textView = (TextView) helper.getView(R.id.tv_screen);
                if (z) {
                    layoutParams2.width = SizeUtils.dp2px(35.0f);
                    layoutParams2.height = SizeUtils.dp2px(35.0f);
                    textView.setTextSize(18.0f);
                } else {
                    layoutParams2.width = SizeUtils.dp2px(25.0f);
                    layoutParams2.height = SizeUtils.dp2px(25.0f);
                    textView.setTextSize(13.0f);
                }
                if (ActSetScreenDisplay.this.selectPosition == -1 || ActSetScreenDisplay.this.relateInfo == null) {
                    relateInfo = item.relateInfo;
                } else {
                    relateInfo = ActSetScreenDisplay.this.relateInfo;
                }
                if (ActSetScreenDisplay.this.selectPosition == -1 || ActSetScreenDisplay.this.selectPosition == helper.getBindingAdapterPosition()) {
                    if (relateInfo == null || relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeNone.getValue()) {
                        helper.setGone(R.id.iv_screen, false);
                        helper.setGone(R.id.tv_screen, true);
                        helper.setText(R.id.tv_screen, "");
                        return;
                    }
                    if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIcon.getValue()) {
                        helper.setGone(R.id.iv_screen, true);
                        helper.setGone(R.id.tv_screen, false);
                        if (ScreenIconUtils.getScreenIconRes(relateInfo.iconIndex) != 0) {
                            helper.setImageResource(R.id.iv_screen, ScreenIconUtils.getScreenIconRes(relateInfo.iconIndex));
                            return;
                        }
                        return;
                    }
                    if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIconWord.getValue()) {
                        helper.setGone(R.id.iv_screen, true);
                        helper.setGone(R.id.tv_screen, true);
                        helper.setText(R.id.tv_screen, relateInfo.screenStr);
                        if (z) {
                            helper.setGone(R.id.iv_screen, false);
                            helper.setText(R.id.tv_screen, StringUtils.replaceString(relateInfo.screenStr, true));
                        } else {
                            helper.setText(R.id.tv_screen, relateInfo.screenStr);
                        }
                        if (ScreenIconUtils.getScreenIconRes(relateInfo.iconIndex) != 0) {
                            helper.setImageResource(R.id.iv_screen, ScreenIconUtils.getScreenIconRes(relateInfo.iconIndex));
                            return;
                        }
                        return;
                    }
                    helper.setGone(R.id.iv_screen, false);
                    helper.setGone(R.id.tv_screen, true);
                    if (z) {
                        helper.setText(R.id.tv_screen, StringUtils.replaceString(relateInfo.screenStr, false));
                        return;
                    } else {
                        helper.setText(R.id.tv_screen, relateInfo.screenStr);
                        return;
                    }
                }
                helper.setGone(R.id.iv_screen, false);
                helper.setGone(R.id.tv_screen, false);
            }
        };
        this.screenAdapter = baseQuickAdapter;
        baseQuickAdapter.bindToRecyclerView(((ActSetScreenDisplayBinding) this.mViewBinding).rvScreenInfo);
        int i = this.spanCount;
        if (i == 2 || i == 3) {
            RecyclerView recyclerView = ((ActSetScreenDisplayBinding) this.mViewBinding).rvScreenInfo;
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, this.spanCount);
            this.mScreenGridLayoutManager = gridLayoutManager;
            recyclerView.setLayoutManager(gridLayoutManager);
        } else {
            RecyclerView recyclerView2 = ((ActSetScreenDisplayBinding) this.mViewBinding).rvScreenInfo;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            this.mScreenLinearLayoutManager = linearLayoutManager;
            recyclerView2.setLayoutManager(linearLayoutManager);
            ((ActSetScreenDisplayBinding) this.mViewBinding).rvScreenInfo.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSetScreenDisplay.4
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(0, 0, 0, 0);
                }
            });
        }
        ((ActSetScreenDisplayBinding) this.mViewBinding).rvScreenInfo.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSetScreenDisplayBinding) this.mViewBinding).rvScreenInfo.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private void initDisplayAdapter() {
        final boolean z = ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getSwitchScreenBigIcon() == 2;
        if (this.displayAdapter != null) {
            return;
        }
        String[] stringArray = getResources().getStringArray(R.array.screen_display);
        int i = this.spanCount;
        if (i != 1) {
            if (i == 2) {
                stringArray = new String[]{getString(R.string.screen_dislay_1), getString(R.string.screen_dislay_2)};
            }
        } else if (ProductId.ID_SMART_SWITCH_SQ_PRO.equals(((ActSmartPanelVM) this.mViewModel).productId)) {
            stringArray = new String[]{getString(R.string.screen_dislay_sq)};
        } else {
            stringArray = new String[]{getString(R.string.screen_dislay_1)};
        }
        BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(this, R.layout.item_screen_display, Arrays.asList(stringArray)) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSetScreenDisplay.5
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_name, item);
            }
        };
        this.displayAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSetScreenDisplay$$ExternalSyntheticLambda4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                ActSetScreenDisplay.this.lambda$initDisplayAdapter$3(z, baseQuickAdapter2, view, i2);
            }
        });
        this.displayAdapter.bindToRecyclerView(((ActSetScreenDisplayBinding) this.mViewBinding).rvDisplay);
        ((ActSetScreenDisplayBinding) this.mViewBinding).rvDisplay.setLayoutManager(new LinearLayoutManager(this));
        ((ActSetScreenDisplayBinding) this.mViewBinding).rvDisplay.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSetScreenDisplay.7
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(5.0f));
            }
        });
        ((ActSetScreenDisplayBinding) this.mViewBinding).rvDisplay.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDisplayAdapter$3(boolean z, BaseQuickAdapter baseQuickAdapter, View view, int i) {
        SetScreenDisplayDialog setScreenDisplayDialog = this.setScreenDialog;
        if (setScreenDisplayDialog != null && setScreenDisplayDialog.isAdded() && this.setScreenDialog.isVisible()) {
            return;
        }
        this.selectPosition = i;
        this.relateInfo = null;
        this.screenAdapter.notifyDataSetChanged();
        int i2 = 0;
        SetScreenDisplayDialog.setIconIndex(((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getRelateInfo(i) == null ? 0 : ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getRelateInfo(i).iconIndex);
        SetScreenDisplayDialog screenText = SetScreenDisplayDialog.asDefault(z).setScreenText(((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getRelateInfo(i) == null ? "" : ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getRelateInfo(i).screenStr);
        if (((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getRelateInfo(i) != null && (((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getRelateInfo(i).screenType != 3 || !z)) {
            i2 = ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getRelateInfo(i).screenType;
        }
        SetScreenDisplayDialog onDialogCallback = screenText.setScreenType(i2).setOnDialogCallback(new SetScreenDisplayDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSetScreenDisplay.6
            @Override // com.ltech.smarthome.view.dialog.SetScreenDisplayDialog.OnDialogCallback
            public boolean onSave() {
                ActSetScreenDisplay actSetScreenDisplay = ActSetScreenDisplay.this;
                actSetScreenDisplay.setScreenData(actSetScreenDisplay.selectPosition);
                return false;
            }

            @Override // com.ltech.smarthome.view.dialog.SetScreenDisplayDialog.OnDialogCallback
            public void onCancel() {
                ActSetScreenDisplay.this.selectPosition = -1;
                ActSetScreenDisplay.this.screenAdapter.notifyDataSetChanged();
            }

            @Override // com.ltech.smarthome.view.dialog.SetScreenDisplayDialog.OnDialogCallback
            public void onScreenChanged(int screenType, String screenStr, int iconIndex) {
                ActSetScreenDisplay.this.relateInfo = new RelatedInfoExtParam.RelateInfo();
                ActSetScreenDisplay.this.relateInfo.screenType = screenType;
                ActSetScreenDisplay.this.relateInfo.screenStr = screenStr;
                ActSetScreenDisplay.this.relateInfo.iconIndex = iconIndex;
                ActSetScreenDisplay.this.screenAdapter.notifyDataSetChanged();
            }
        });
        this.setScreenDialog = onDialogCallback;
        onDialogCallback.setHeight((int) (getWindowManager().getDefaultDisplay().getHeight() * 0.17f));
        this.setScreenDialog.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setScreenData(final int position) {
        byte[] bArr;
        int i;
        final byte[] bArr2;
        final int i2;
        int i3;
        if (this.relateInfo == null) {
            return;
        }
        if (((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getRelateInfo(position) != null && ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getRelateInfo(position).objectId != 0 && ((ActSmartPanelVM) this.mViewModel).relatedInfoList.get(position).infoName == null) {
            ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getRelateInfo(position).objectId = 0L;
        }
        int i4 = 1;
        try {
        } catch (UnsupportedEncodingException e) {
            e = e;
            bArr = null;
        }
        if (this.relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeWord.getValue()) {
            String[] split = this.relateInfo.screenStr.split("\n");
            byte[] bytes = split.length > 0 ? split[0].getBytes("gb2312") : null;
            try {
                r5 = split.length > 1 ? split[1].getBytes("gb2312") : null;
                byte[] bArr3 = bytes;
                bArr2 = r5;
                r5 = bArr3;
            } catch (UnsupportedEncodingException e2) {
                e = e2;
                bArr = bytes;
                i = 1;
                e.printStackTrace();
                int i5 = i;
                bArr2 = null;
                i2 = i5;
                showLoadingDialog("");
                final int i6 = i4;
                final byte[] bArr4 = bArr;
                CmdAssistant.getDeviceAssistant(((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), new int[0]).setPanelScreenData(this.activity, position, i6, bArr4, i2, bArr2, new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSetScreenDisplay$$ExternalSyntheticLambda1
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActSetScreenDisplay.this.lambda$setScreenData$4(position, i6, bArr4, i2, bArr2, (ResponseMsg) obj);
                    }
                });
            }
        } else {
            if (this.relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIcon.getValue()) {
                try {
                    int sendIconIndex = ScreenIconUtils.getSendIconIndex(this.relateInfo.iconIndex);
                    bArr2 = null;
                    r5 = new byte[]{(byte) ((sendIconIndex >> 8) & 255), (byte) (sendIconIndex & 255)};
                    i3 = 0;
                } catch (UnsupportedEncodingException e3) {
                    e = e3;
                    bArr = null;
                    i4 = 2;
                    i = 0;
                    e.printStackTrace();
                    int i52 = i;
                    bArr2 = null;
                    i2 = i52;
                    showLoadingDialog("");
                    final int i62 = i4;
                    final byte[] bArr42 = bArr;
                    CmdAssistant.getDeviceAssistant(((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), new int[0]).setPanelScreenData(this.activity, position, i62, bArr42, i2, bArr2, new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSetScreenDisplay$$ExternalSyntheticLambda1
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj) {
                            ActSetScreenDisplay.this.lambda$setScreenData$4(position, i62, bArr42, i2, bArr2, (ResponseMsg) obj);
                        }
                    });
                }
            } else if (this.relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIconWord.getValue()) {
                try {
                    int sendIconIndex2 = ScreenIconUtils.getSendIconIndex(this.relateInfo.iconIndex);
                    bArr = new byte[]{(byte) ((sendIconIndex2 >> 8) & 255), (byte) (sendIconIndex2 & 255)};
                } catch (UnsupportedEncodingException e4) {
                    e = e4;
                    bArr = null;
                }
                try {
                    bArr2 = this.relateInfo.screenStr.getBytes("gb2312");
                    r5 = bArr;
                    i3 = 1;
                } catch (UnsupportedEncodingException e5) {
                    e = e5;
                    i4 = 2;
                    i = 1;
                    e.printStackTrace();
                    int i522 = i;
                    bArr2 = null;
                    i2 = i522;
                    showLoadingDialog("");
                    final int i622 = i4;
                    final byte[] bArr422 = bArr;
                    CmdAssistant.getDeviceAssistant(((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), new int[0]).setPanelScreenData(this.activity, position, i622, bArr422, i2, bArr2, new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSetScreenDisplay$$ExternalSyntheticLambda1
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj) {
                            ActSetScreenDisplay.this.lambda$setScreenData$4(position, i622, bArr422, i2, bArr2, (ResponseMsg) obj);
                        }
                    });
                }
            } else {
                bArr2 = null;
            }
            i4 = 2;
            bArr = r5;
            i2 = i3;
            showLoadingDialog("");
            final int i6222 = i4;
            final byte[] bArr4222 = bArr;
            CmdAssistant.getDeviceAssistant(((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), new int[0]).setPanelScreenData(this.activity, position, i6222, bArr4222, i2, bArr2, new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSetScreenDisplay$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSetScreenDisplay.this.lambda$setScreenData$4(position, i6222, bArr4222, i2, bArr2, (ResponseMsg) obj);
                }
            });
        }
        i3 = 1;
        bArr = r5;
        i2 = i3;
        showLoadingDialog("");
        final int i62222 = i4;
        final byte[] bArr42222 = bArr;
        CmdAssistant.getDeviceAssistant(((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), new int[0]).setPanelScreenData(this.activity, position, i62222, bArr42222, i2, bArr2, new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSetScreenDisplay$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSetScreenDisplay.this.lambda$setScreenData$4(position, i62222, bArr42222, i2, bArr2, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScreenData$4(int i, int i2, byte[] bArr, int i3, byte[] bArr2, ResponseMsg responseMsg) {
        dismissLoadingDialog();
        if (responseMsg != null && (responseMsg.getStateCode() == 0 || responseMsg.getStateCode() == 153)) {
            RelatedInfoExtParam.RelateInfo relateInfo = ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getRelateInfo(i);
            if (relateInfo == null) {
                relateInfo = new RelatedInfoExtParam.RelateInfo();
            }
            relateInfo.screenType = this.relateInfo.screenType;
            relateInfo.screenStr = this.relateInfo.screenStr;
            relateInfo.iconIndex = this.relateInfo.iconIndex;
            ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.setRelateInfo(i, relateInfo);
            ((ActSmartPanelVM) this.mViewModel).uploadData();
            if (((ActSmartPanelVM) this.mViewModel).groupControl) {
                return;
            }
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.TEXT, CmdAssistant.getDeviceAssistant(((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), new int[0]).setPanelScreenData(i, i2, bArr, i3, bArr2));
            ReplaceHelper.instance().backupIndexData(this.activity, ((Device) ((ActSmartPanelVM) this.mViewModel).controlObject.getValue()).getDeviceId(), i + 1);
            return;
        }
        showErrorDialog(getString(R.string.save_fail));
    }
}