package com.ltech.smarthome.ui.device.screenpanel;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActPageScreenPanelBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.ScreenIconUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridLayoutManager;
import com.qw.curtain.lib.Curtain;
import com.qw.curtain.lib.IGuide;
import com.qw.curtain.lib.OnViewInTopClickListener;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.bean.SwitchPanelState;
import com.smart.product_agreement.parser.IPanelParser;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActPageScreenPanel extends BaseControlActivity<ActPageScreenPanelBinding, ActSmartPanelVM> {
    protected List<RelateInfoItem> dataList;
    private int[] imgs;
    protected BaseQuickAdapter<RelateInfoItem, BaseViewHolder> infoAdapter;
    private GridLayoutManager mGridLayoutManager;
    private LinearLayoutManager mLinearLayoutManager;
    protected PagerGridLayoutManager mScreenGridLayoutManager;
    private LinearLayoutManager mScreenLinearLayoutManager;
    protected BaseQuickAdapter<RelateInfoItem, BaseViewHolder> screenAdapter;
    protected boolean[] selectArray = new boolean[8];

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_page_screen_panel;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        super.showContent();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavHelper.goSetting(((ActSmartPanelVM) this.mViewModel).controlObject.getValue());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        LHomeLog.i(getClass(), "message_send startObserve() enter");
        ((ActSmartPanelVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActSmartPanelVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActSmartPanelVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActSmartPanelVM) this.mViewModel).controlType = getIntent().getIntExtra(Constants.LIGHT_TYPE, 0);
        ((ActSmartPanelVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        ((ActSmartPanelVM) this.mViewModel).groups = Injection.repo().group().getGroupListByPlaceId(((ActSmartPanelVM) this.mViewModel).placeId);
        ((ActSmartPanelVM) this.mViewModel).devices = Injection.repo().device().getDeviceListByPlaceId(((ActSmartPanelVM) this.mViewModel).placeId);
        ((ActSmartPanelVM) this.mViewModel).scenes = Injection.repo().scene().getSceneListByPlaceId(((ActSmartPanelVM) this.mViewModel).placeId, true);
        if (((ActSmartPanelVM) this.mViewModel).groupControl) {
            startGroupObserve();
        } else {
            startDeviceObserve();
        }
        ((ActSmartPanelVM) this.mViewModel).updateUIEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActPageScreenPanel$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActPageScreenPanel.this.lambda$startObserve$0(obj);
            }
        });
        ((ActSmartPanelVM) this.mViewModel).screenStateEvent.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActPageScreenPanel.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    AppCompatImageView appCompatImageView = ((ActPageScreenPanelBinding) ActPageScreenPanel.this.mViewBinding).ivScreen;
                    ActPageScreenPanel actPageScreenPanel = ActPageScreenPanel.this;
                    appCompatImageView.setImageResource(actPageScreenPanel.getTheme(((ActSmartPanelVM) actPageScreenPanel.mViewModel).getCurScreen()));
                }
            }
        });
        ((ActSmartPanelVM) this.mViewModel).panelZoneStateLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActPageScreenPanel$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActPageScreenPanel.this.lambda$startObserve$1((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Object obj) {
        ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        int i = 0;
        while (i < list.size()) {
            int i2 = i + 1;
            if (((ActSmartPanelVM) this.mViewModel).relayPositiontMap != null && ((ActSmartPanelVM) this.mViewModel).relayPositiontMap.containsKey(Integer.valueOf(i2))) {
                Integer num = ((ActSmartPanelVM) this.mViewModel).relayPositiontMap.get(Integer.valueOf(i2));
                int byteValue = num == null ? i : num.byteValue();
                this.selectArray[byteValue] = ((SwitchPanelState.SwitchPanelZoneState) list.get(i)).isSwitchOnOff();
                BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.infoAdapter;
                if (baseQuickAdapter != null) {
                    baseQuickAdapter.notifyItemChanged(byteValue);
                }
            }
            i = i2;
        }
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
        LHomeLog.i(getClass(), "message_send (device)getDeviceFromDb enter");
        try {
            if (((ActSmartPanelVM) this.mViewModel).controlObject.getValue() == null) {
                LHomeLog.i(getClass(), "message_send (device)getDeviceFromDb mViewModel.controlObject.getValue()==null");
                ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(deviceById);
            } else {
                if (HelpUtils.compareObject((Device) ((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), deviceById)) {
                    return;
                }
                LHomeLog.i(getClass(), "message_send device changed");
                ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(deviceById);
            }
        } catch (Exception e) {
            LHomeLog.i(getClass(), "message_send (device)getDeviceFromDb Exception" + e.toString());
            e.printStackTrace();
        }
    }

    private void startDeviceObserve() {
        ((ActSmartPanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActPageScreenPanel$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActPageScreenPanel.this.lambda$startDeviceObserve$3(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDeviceObserve$3(Object obj) {
        LHomeLog.i(getClass(), "message_send (device)mViewModel.controlObject enter");
        if (obj instanceof Device) {
            final Device device = (Device) obj;
            setTitle(device.getDeviceName());
            String productId = device.getProductId();
            productId.hashCode();
            if (productId.equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
                ((ActSmartPanelVM) this.mViewModel).screenCount = 3;
                ((ActSmartPanelVM) this.mViewModel).zoneCount = 12;
                this.imgs = HelpUtils.getDrawableResourceArray(this, R.array.g4_pro_bg_panel_bg);
                setBg();
            } else if (productId.equals(ProductId.ID_SMART_PANEL_G4)) {
                ((ActSmartPanelVM) this.mViewModel).screenCount = 3;
                ((ActSmartPanelVM) this.mViewModel).zoneCount = 12;
                this.imgs = HelpUtils.getDrawableResourceArray(this, R.array.g4_bg_panel_bg);
                setBg();
            } else {
                ((ActSmartPanelVM) this.mViewModel).screenCount = 1;
                ((ActSmartPanelVM) this.mViewModel).zoneCount = 1;
            }
            initRelatedInfoView(device);
            ((ActSmartPanelVM) this.mViewModel).queryPanelState(obj, 4, ((ActSmartPanelVM) this.mViewModel).zoneCount);
            MessageManager.getInstance().setPanelSwitchStatusCallBack(new MessageManager.SmartPanelSwitchStatusCallBack() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActPageScreenPanel$$ExternalSyntheticLambda1
                @Override // com.smart.message.MessageManager.SmartPanelSwitchStatusCallBack
                public final void onDataReceive(ResponseMsg responseMsg) {
                    ActPageScreenPanel.this.lambda$startDeviceObserve$2(device, responseMsg);
                }
            });
        }
        ((ActSmartPanelVM) this.mViewModel).queryPanelScreenState(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDeviceObserve$2(Device device, ResponseMsg responseMsg) {
        ((ActSmartPanelVM) this.mViewModel).refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), ((BleParam) device.getParam(BleParam.class)).getUnicastAddress(), 4, responseMsg.getResData()));
    }

    private void initTab() {
        if (((ActSmartPanelVM) this.mViewModel).tabs.isEmpty()) {
            ((ActSmartPanelVM) this.mViewModel).initTabList();
            ((ActPageScreenPanelBinding) this.mViewBinding).vp.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActPageScreenPanel.2
                @Override // androidx.viewpager2.adapter.FragmentStateAdapter
                public Fragment createFragment(int position) {
                    return ((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).tabs.get(position).getFragment();
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public int getItemCount() {
                    return ((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).tabs.size();
                }
            });
            ((ActPageScreenPanelBinding) this.mViewBinding).tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActPageScreenPanel.3
                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabSelected(TabLayout.Tab tab) {
                    ActPageScreenPanel actPageScreenPanel = ActPageScreenPanel.this;
                    actPageScreenPanel.createTabCustomView(actPageScreenPanel.activity, tab, ((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).tabs.get(tab.getPosition()).getTitle(), true);
                    if (tab.getPosition() <= 0 || ActPageScreenPanel.this.mScreenGridLayoutManager.getCurrentPagerIndex() + 1 == tab.getPosition()) {
                        return;
                    }
                    ActPageScreenPanel.this.mScreenGridLayoutManager.scrollToPagerIndex(tab.getPosition() - 1);
                    ((ActPageScreenPanelBinding) ActPageScreenPanel.this.mViewBinding).ivScreen.setImageResource(ActPageScreenPanel.this.getTheme(tab.getPosition() - 1));
                    ((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).changePanelScreen(tab.getPosition() - 1);
                }

                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabUnselected(TabLayout.Tab tab) {
                    ActPageScreenPanel actPageScreenPanel = ActPageScreenPanel.this;
                    actPageScreenPanel.createTabCustomView(actPageScreenPanel.activity, tab, ((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).tabs.get(tab.getPosition()).getTitle(), false);
                }

                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabReselected(TabLayout.Tab tab) {
                    ActPageScreenPanel actPageScreenPanel = ActPageScreenPanel.this;
                    actPageScreenPanel.createTabCustomView(actPageScreenPanel.activity, tab, ((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).tabs.get(tab.getPosition()).getTitle(), true);
                }
            });
            new TabLayoutMediator(((ActPageScreenPanelBinding) this.mViewBinding).tabLayout, ((ActPageScreenPanelBinding) this.mViewBinding).vp, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActPageScreenPanel$$ExternalSyntheticLambda9
                @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
                public final void onConfigureTab(TabLayout.Tab tab, int i) {
                    ActPageScreenPanel.this.lambda$initTab$4(tab, i);
                }
            }).attach();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTab$4(TabLayout.Tab tab, int i) {
        createTabCustomView(this.activity, tab, ((ActSmartPanelVM) this.mViewModel).tabs.get(i).getTitle(), false);
    }

    private void setBg() {
        try {
            Object value = ((ActSmartPanelVM) this.mViewModel).controlObject.getValue();
            if (value instanceof Device) {
                int parseInt = Integer.parseInt(((Device) value).getPanelColor()) - 1;
                int[] iArr = this.imgs;
                if (iArr == null || parseInt >= iArr.length) {
                    return;
                }
                ((ActPageScreenPanelBinding) this.mViewBinding).ivS4.setImageResource(this.imgs[parseInt]);
            }
        } catch (Exception e) {
            LHomeLog.e(getClass(), e.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getTheme(int pos) {
        return ((ActSmartPanelVM) this.mViewModel).themeBgPic[((ActSmartPanelVM) this.mViewModel).themeNum[pos] - 1];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createTabCustomView(Context context, TabLayout.Tab tab, String text, boolean select) {
        View customView = tab.getCustomView();
        if (customView == null) {
            customView = LayoutInflater.from(context).inflate(R.layout.tab_text2, (ViewGroup) null);
            tab.setCustomView(customView);
        }
        AppCompatTextView appCompatTextView = (AppCompatTextView) customView.findViewById(R.id.tv_tab);
        appCompatTextView.setText(text);
        if (select) {
            appCompatTextView.setTextColor(ContextCompat.getColor(context, R.color.color_text_black));
            appCompatTextView.getPaint().setFakeBoldText(true);
            if (LanguageUtils.isRussian(context)) {
                appCompatTextView.setTextSize(15.0f);
                return;
            } else {
                appCompatTextView.setTextSize(16.0f);
                return;
            }
        }
        appCompatTextView.setTextColor(ContextCompat.getColor(context, R.color.color_text_gray));
        appCompatTextView.getPaint().setFakeBoldText(false);
        if (LanguageUtils.isRussian(context)) {
            appCompatTextView.setTextSize(15.0f);
        } else {
            appCompatTextView.setTextSize(16.0f);
        }
    }

    private void startGroupObserve() {
        ((ActSmartPanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActPageScreenPanel$$ExternalSyntheticLambda11
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActPageScreenPanel.this.lambda$startGroupObserve$6(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startGroupObserve$6(Object obj) {
        LHomeLog.i(getClass(), "message_send (group)mViewModel.controlObject enter");
        if (obj instanceof Group) {
            final Group group = (Group) obj;
            setTitle(group.getGroupName());
            ((ActPageScreenPanelBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s1c_shadown_1);
            ((ActPageScreenPanelBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s1c_shadown_3);
            if (ProductRepository.getLightColorType((Object) group) == 21) {
                ((ActSmartPanelVM) this.mViewModel).screenCount = 3;
                ((ActSmartPanelVM) this.mViewModel).zoneCount = 12;
            } else {
                ((ActSmartPanelVM) this.mViewModel).zoneCount = 1;
                ((ActSmartPanelVM) this.mViewModel).screenCount = 1;
            }
            if (group.getDeviceIds().isEmpty()) {
                initRelatedInfoView(group);
            } else {
                initRelatedInfoView(group);
                ((ActSmartPanelVM) this.mViewModel).queryPanelState(obj, 4, ((ActSmartPanelVM) this.mViewModel).zoneCount);
                MessageManager.getInstance().setPanelSwitchStatusCallBack(new MessageManager.SmartPanelSwitchStatusCallBack() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActPageScreenPanel$$ExternalSyntheticLambda10
                    @Override // com.smart.message.MessageManager.SmartPanelSwitchStatusCallBack
                    public final void onDataReceive(ResponseMsg responseMsg) {
                        ActPageScreenPanel.this.lambda$startGroupObserve$5(group, responseMsg);
                    }
                });
            }
            ((ActSmartPanelVM) this.mViewModel).queryPanelScreenState(obj);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startGroupObserve$5(Group group, ResponseMsg responseMsg) {
        ((ActSmartPanelVM) this.mViewModel).refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), group.getGroupAddress(), ((ActSmartPanelVM) this.mViewModel).zoneCount, responseMsg.getResData()));
    }

    private void initRelatedInfoView(final Group group) {
        ((ActSmartPanelVM) this.mViewModel).initPanelState(group);
        ((ActSmartPanelVM) this.mViewModel).initRelateInfoList(group);
        this.selectArray = new boolean[((ActSmartPanelVM) this.mViewModel).relatedInfoList.size()];
        int proPanelCount = ((ActSmartPanelVM) this.mViewModel).getProPanelCount(group);
        if (proPanelCount > 0) {
            if (((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getSwitchShowType() == 0) {
                MessageDialog.show(this, getString(R.string.tips), getString(R.string.group_switch_g4_tip)).setCancelable(false).setOkButton(getString(R.string.change_style), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActPageScreenPanel$$ExternalSyntheticLambda3
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        boolean lambda$initRelatedInfoView$7;
                        lambda$initRelatedInfoView$7 = ActPageScreenPanel.this.lambda$initRelatedInfoView$7(group, baseDialog, view);
                        return lambda$initRelatedInfoView$7;
                    }
                }).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.not_change_style), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActPageScreenPanel$$ExternalSyntheticLambda4
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        boolean lambda$initRelatedInfoView$8;
                        lambda$initRelatedInfoView$8 = ActPageScreenPanel.this.lambda$initRelatedInfoView$8(group, baseDialog, view);
                        return lambda$initRelatedInfoView$8;
                    }
                });
            } else if (((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getSwitchShowType() == 1 && proPanelCount == group.getDeviceIds().size()) {
                ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.setSwitchShowType(2);
                group.setExtParam(((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getExtParamString());
                Injection.repo().group().saveGroup(group);
            }
        }
        if (((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getSwitchShowType() == 2) {
            ((ActPageScreenPanelBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.img_g4pro_bg7);
        } else {
            ((ActPageScreenPanelBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.img_g4_bg7);
        }
        ArrayList arrayList = new ArrayList(((ActSmartPanelVM) this.mViewModel).relatedInfoList.subList(((ActSmartPanelVM) this.mViewModel).getCurScreen() * 4, (((ActSmartPanelVM) this.mViewModel).getCurScreen() + 1) * 4));
        BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.infoAdapter;
        if (baseQuickAdapter == null) {
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_smart_panel_key) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActPageScreenPanel.4
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, RelateInfoItem item) {
                    LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.layout_content);
                    helper.itemView.getLayoutParams().height = ActPageScreenPanel.this.mGridLayoutManager.getHeight() / 2;
                    String[] stringArray = ActPageScreenPanel.this.getResources().getStringArray(R.array.smart_panel_g4_key_select);
                    TextView textView = (TextView) helper.getView(R.id.tv_device_name);
                    TextView textView2 = (TextView) helper.getView(R.id.tv_sub_text);
                    if (helper.getAdapterPosition() % 4 == 0) {
                        linearLayout.setGravity(BadgeDrawable.TOP_START);
                        textView.setGravity(GravityCompat.START);
                        textView2.setGravity(GravityCompat.START);
                    } else if (helper.getAdapterPosition() % 4 == 1) {
                        linearLayout.setGravity(BadgeDrawable.TOP_END);
                        textView.setGravity(GravityCompat.END);
                        textView2.setGravity(GravityCompat.END);
                    } else if (helper.getAdapterPosition() % 4 == 2) {
                        linearLayout.setGravity(BadgeDrawable.BOTTOM_START);
                        textView.setGravity(GravityCompat.START);
                        textView2.setGravity(GravityCompat.START);
                    } else {
                        linearLayout.setGravity(BadgeDrawable.BOTTOM_END);
                        textView.setGravity(GravityCompat.END);
                        textView2.setGravity(GravityCompat.END);
                    }
                    RelatedInfoExtParam.RelateInfo relateInfo = item.relateInfo;
                    if (item.infoName != null && !TextUtils.isEmpty(item.infoName)) {
                        helper.setText(R.id.tv_device_name, item.infoName);
                    } else if (relateInfo != null) {
                        if (relateInfo.name == null || relateInfo.name.isEmpty()) {
                            helper.setText(R.id.tv_device_name, stringArray[helper.getAdapterPosition()]);
                        } else {
                            helper.setText(R.id.tv_device_name, relateInfo.name);
                        }
                    } else {
                        helper.setText(R.id.tv_device_name, stringArray[helper.getAdapterPosition()]);
                    }
                    if (relateInfo == null) {
                        if (((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).relayObjectMap != null && ((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(helper.getAdapterPosition()))) {
                            Object obj = ((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).relayObjectMap.get(Integer.valueOf(helper.getAdapterPosition()));
                            if (obj instanceof Device) {
                                helper.setText(R.id.tv_sub_text, ((Device) obj).getName());
                            } else {
                                helper.setText(R.id.tv_sub_text, ActPageScreenPanel.this.getString(R.string.no_bind_object));
                            }
                        } else {
                            helper.setText(R.id.tv_sub_text, ActPageScreenPanel.this.getString(R.string.no_bind_object));
                        }
                    } else {
                        if (item.infoName == null || item.type == 0) {
                            if (((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).relayObjectMap != null && ((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(relateInfo.switchIndex))) {
                                Object obj2 = ((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).relayObjectMap.get(Integer.valueOf(relateInfo.switchIndex));
                                if (obj2 instanceof Group) {
                                    helper.setText(R.id.tv_sub_text, ((Group) obj2).getName());
                                } else {
                                    helper.setText(R.id.tv_sub_text, ActPageScreenPanel.this.getString(R.string.no_bind_object));
                                }
                            } else {
                                helper.setText(R.id.tv_sub_text, ActPageScreenPanel.this.getString(R.string.no_bind_object));
                            }
                        } else {
                            helper.setText(R.id.tv_sub_text, item.actionInfo);
                        }
                        helper.setTextColor(R.id.tv_sub_text, ContextCompat.getColor(this.mContext, R.color.color_border_gray));
                        helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(this.mContext, R.color.colorPrimary));
                    }
                    ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
                }
            };
            this.infoAdapter = baseQuickAdapter2;
            baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActPageScreenPanel$$ExternalSyntheticLambda5
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    ActPageScreenPanel.this.lambda$initRelatedInfoView$9(baseQuickAdapter3, view, i);
                }
            });
            this.infoAdapter.bindToRecyclerView(((ActPageScreenPanelBinding) this.mViewBinding).rvKeyInfo);
            RecyclerView recyclerView = ((ActPageScreenPanelBinding) this.mViewBinding).rvKeyInfo;
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            this.mGridLayoutManager = gridLayoutManager;
            recyclerView.setLayoutManager(gridLayoutManager);
            ((ActPageScreenPanelBinding) this.mViewBinding).rvKeyInfo.setHasFixedSize(true);
            ((DefaultItemAnimator) ((ActPageScreenPanelBinding) this.mViewBinding).rvKeyInfo.getItemAnimator()).setSupportsChangeAnimations(false);
        } else {
            baseQuickAdapter.replaceData(arrayList);
        }
        initScreenAdapter();
        initTab();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initRelatedInfoView$7(Group group, BaseDialog baseDialog, View view) {
        ((ActSmartPanelVM) this.mViewModel).changeShowType(group, 2);
        ((ActPageScreenPanelBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.img_g4pro_bg7);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initRelatedInfoView$8(Group group, BaseDialog baseDialog, View view) {
        ((ActSmartPanelVM) this.mViewModel).changeShowType(group, 1);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRelatedInfoView$9(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        RelateInfoItem relateInfoItem = this.infoAdapter.getData().get(i);
        RelatedInfoExtParam.RelateInfo relateInfo = relateInfoItem.relateInfo;
        if (relateInfo != null) {
            if (relateInfoItem.infoName == null || relateInfoItem.type == 0) {
                if (((ActSmartPanelVM) this.mViewModel).relayObjectMap != null && ((ActSmartPanelVM) this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(relateInfo.switchIndex))) {
                    this.selectArray[getRealItemPosition(i)] = !this.selectArray[getRealItemPosition(i)];
                    baseQuickAdapter.notifyItemChanged(i);
                    ((ActSmartPanelVM) this.mViewModel).sendSingleOnOff(relateInfo.switchIndex - 1, this.selectArray[getRealItemPosition(i)]);
                    return;
                }
                ((ActSmartPanelVM) this.mViewModel).showBindDialog(getRealItemPosition(i));
                return;
            }
            ((ActSmartPanelVM) this.mViewModel).sendBindCommand(getRealItemPosition(i), this.selectArray[getRealItemPosition(i)]);
            return;
        }
        if (((ActSmartPanelVM) this.mViewModel).relayObjectMap != null && ((ActSmartPanelVM) this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(i + 1))) {
            this.selectArray[getRealItemPosition(i)] = !this.selectArray[getRealItemPosition(i)];
            baseQuickAdapter.notifyItemChanged(i);
            ((ActSmartPanelVM) this.mViewModel).sendSingleOnOff(i, this.selectArray[getRealItemPosition(i)]);
            return;
        }
        ((ActSmartPanelVM) this.mViewModel).showBindDialog(getRealItemPosition(i));
    }

    private int getRealItemPosition(int p) {
        return (((ActSmartPanelVM) this.mViewModel).getCurScreen() * 4) + p;
    }

    private void initRelatedInfoView(Device device) {
        ((ActSmartPanelVM) this.mViewModel).initPanelState(device);
        ((ActSmartPanelVM) this.mViewModel).initRelateInfoList(device);
        this.selectArray = new boolean[((ActSmartPanelVM) this.mViewModel).relatedInfoList.size()];
        ArrayList arrayList = new ArrayList(((ActSmartPanelVM) this.mViewModel).relatedInfoList.subList(((ActSmartPanelVM) this.mViewModel).getCurScreen() * 4, (((ActSmartPanelVM) this.mViewModel).getCurScreen() + 1) * 4));
        BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.infoAdapter;
        if (baseQuickAdapter == null) {
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_smart_panel_key) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActPageScreenPanel.5
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, RelateInfoItem item) {
                    LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.layout_content);
                    helper.itemView.getLayoutParams().height = ActPageScreenPanel.this.mGridLayoutManager.getHeight() / 2;
                    String[] stringArray = ActPageScreenPanel.this.getResources().getStringArray(R.array.smart_panel_g4_key_select);
                    TextView textView = (TextView) helper.getView(R.id.tv_device_name);
                    TextView textView2 = (TextView) helper.getView(R.id.tv_sub_text);
                    if (helper.getAdapterPosition() % 4 == 0) {
                        linearLayout.setGravity(BadgeDrawable.TOP_START);
                        textView.setGravity(GravityCompat.START);
                        textView2.setGravity(GravityCompat.START);
                    } else if (helper.getAdapterPosition() % 4 == 1) {
                        linearLayout.setGravity(BadgeDrawable.TOP_END);
                        textView.setGravity(GravityCompat.END);
                        textView2.setGravity(GravityCompat.END);
                    } else if (helper.getAdapterPosition() % 4 == 2) {
                        linearLayout.setGravity(BadgeDrawable.BOTTOM_START);
                        textView.setGravity(GravityCompat.START);
                        textView2.setGravity(GravityCompat.START);
                    } else {
                        linearLayout.setGravity(BadgeDrawable.BOTTOM_END);
                        textView.setGravity(GravityCompat.END);
                        textView2.setGravity(GravityCompat.END);
                    }
                    RelatedInfoExtParam.RelateInfo relateInfo = item.relateInfo;
                    if (item.infoName != null && !TextUtils.isEmpty(item.infoName)) {
                        helper.setText(R.id.tv_device_name, item.infoName);
                    } else if (relateInfo != null) {
                        if (relateInfo.name == null || relateInfo.name.isEmpty()) {
                            helper.setText(R.id.tv_device_name, stringArray[helper.getAdapterPosition()]);
                        } else {
                            helper.setText(R.id.tv_device_name, relateInfo.name);
                        }
                    } else {
                        helper.setText(R.id.tv_device_name, stringArray[helper.getAdapterPosition()]);
                    }
                    if (relateInfo == null) {
                        if (((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).relayObjectMap != null && ((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(helper.getAdapterPosition()))) {
                            Object obj = ((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).relayObjectMap.get(Integer.valueOf(helper.getAdapterPosition()));
                            if (obj instanceof Device) {
                                helper.setText(R.id.tv_sub_text, ((Device) obj).getName());
                            } else {
                                helper.setText(R.id.tv_sub_text, ActPageScreenPanel.this.getString(R.string.no_bind_object));
                            }
                        } else {
                            helper.setText(R.id.tv_sub_text, ActPageScreenPanel.this.getString(R.string.no_bind_object));
                        }
                    } else {
                        if (item.infoName == null || item.type == 0) {
                            if (((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).relayObjectMap != null && ((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(relateInfo.switchIndex))) {
                                Object obj2 = ((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).relayObjectMap.get(Integer.valueOf(relateInfo.switchIndex));
                                if (obj2 instanceof Device) {
                                    helper.setText(R.id.tv_sub_text, ((Device) obj2).getName());
                                } else {
                                    helper.setText(R.id.tv_sub_text, ActPageScreenPanel.this.getString(R.string.no_bind_object));
                                }
                            } else {
                                helper.setText(R.id.tv_sub_text, ActPageScreenPanel.this.getString(R.string.no_bind_object));
                            }
                        } else {
                            helper.setText(R.id.tv_sub_text, item.actionInfo);
                        }
                        helper.setTextColor(R.id.tv_sub_text, ContextCompat.getColor(this.mContext, R.color.color_border_gray));
                        helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(this.mContext, R.color.colorPrimary));
                    }
                    ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
                }
            };
            this.infoAdapter = baseQuickAdapter2;
            baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActPageScreenPanel$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    ActPageScreenPanel.this.lambda$initRelatedInfoView$10(baseQuickAdapter3, view, i);
                }
            });
            this.infoAdapter.bindToRecyclerView(((ActPageScreenPanelBinding) this.mViewBinding).rvKeyInfo);
            RecyclerView recyclerView = ((ActPageScreenPanelBinding) this.mViewBinding).rvKeyInfo;
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            this.mGridLayoutManager = gridLayoutManager;
            recyclerView.setLayoutManager(gridLayoutManager);
            ((ActPageScreenPanelBinding) this.mViewBinding).rvKeyInfo.setHasFixedSize(true);
            ((DefaultItemAnimator) ((ActPageScreenPanelBinding) this.mViewBinding).rvKeyInfo.getItemAnimator()).setSupportsChangeAnimations(false);
        } else {
            baseQuickAdapter.replaceData(arrayList);
        }
        initScreenAdapter();
        initTab();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRelatedInfoView$10(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        RelateInfoItem relateInfoItem = this.infoAdapter.getData().get(i);
        RelatedInfoExtParam.RelateInfo relateInfo = relateInfoItem.relateInfo;
        if (relateInfo != null) {
            if (relateInfoItem.infoName == null || relateInfoItem.type == 0) {
                if (((ActSmartPanelVM) this.mViewModel).relayObjectMap != null && ((ActSmartPanelVM) this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(relateInfo.switchIndex))) {
                    this.selectArray[getRealItemPosition(i)] = !this.selectArray[getRealItemPosition(i)];
                    baseQuickAdapter.notifyItemChanged(i);
                    ((ActSmartPanelVM) this.mViewModel).sendSingleOnOff(relateInfo.switchIndex - 1, this.selectArray[getRealItemPosition(i)]);
                    return;
                }
                ((ActSmartPanelVM) this.mViewModel).showBindDialog(getRealItemPosition(i));
                return;
            }
            ((ActSmartPanelVM) this.mViewModel).sendBindCommand(getRealItemPosition(i), this.selectArray[getRealItemPosition(i)]);
            return;
        }
        if (((ActSmartPanelVM) this.mViewModel).relayObjectMap != null && ((ActSmartPanelVM) this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(i + 1))) {
            this.selectArray[getRealItemPosition(i)] = !this.selectArray[getRealItemPosition(i)];
            baseQuickAdapter.notifyItemChanged(i);
            ((ActSmartPanelVM) this.mViewModel).sendSingleOnOff(i, this.selectArray[getRealItemPosition(i)]);
            return;
        }
        ((ActSmartPanelVM) this.mViewModel).showBindDialog(getRealItemPosition(i));
    }

    private void initScreenAdapter() {
        BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.screenAdapter;
        if (baseQuickAdapter == null) {
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_page_screen_panel, ((ActSmartPanelVM) this.mViewModel).relatedInfoList) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActPageScreenPanel.6
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, RelateInfoItem item) {
                    ((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).isOpenElderLyMode = ((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).relateInfoAssistant.getSwitchScreenBigIcon() == 2;
                    LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.layout_item_bg);
                    TextView textView = (TextView) helper.getView(R.id.tv_screen);
                    ImageView imageView = (ImageView) helper.getView(R.id.iv_screen);
                    ImageView imageView2 = (ImageView) helper.getView(R.id.iv_screen2);
                    imageView.setVisibility(8);
                    imageView2.setVisibility(8);
                    int dp2px = SizeUtils.dp2px(((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).isOpenElderLyMode ? 54.0f : 55.0f);
                    if (helper.getAdapterPosition() % 4 == 0) {
                        textView.setGravity(8388627);
                        linearLayout.setGravity(BadgeDrawable.TOP_START);
                        linearLayout.setPadding(dp2px, 0, 0, 0);
                    } else {
                        if (helper.getAdapterPosition() % 4 == 3) {
                            textView.setGravity(8388629);
                            linearLayout.setGravity(BadgeDrawable.BOTTOM_END);
                            linearLayout.setPadding(0, 0, dp2px, 0);
                        } else if (helper.getAdapterPosition() % 4 == 2) {
                            textView.setGravity(8388627);
                            linearLayout.setGravity(BadgeDrawable.BOTTOM_START);
                            linearLayout.setPadding(dp2px, 0, 0, 0);
                        } else {
                            textView.setGravity(8388629);
                            linearLayout.setGravity(BadgeDrawable.TOP_END);
                            linearLayout.setPadding(0, 0, dp2px, 0);
                        }
                        imageView = imageView2;
                    }
                    ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                    if (((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).isOpenElderLyMode) {
                        layoutParams.width = SizeUtils.dp2px(19.0f);
                        layoutParams.height = SizeUtils.dp2px(19.0f);
                        textView.setTextSize(15.0f);
                    } else {
                        layoutParams.width = SizeUtils.dp2px(15.0f);
                        layoutParams.height = SizeUtils.dp2px(15.0f);
                        textView.setTextSize(12.0f);
                    }
                    if (LanguageUtils.isRussian(ActPageScreenPanel.this)) {
                        textView.setMaxLines(3);
                    }
                    RelatedInfoExtParam.RelateInfo relateInfo = item.relateInfo;
                    if (relateInfo != null) {
                        boolean z = (item.infoName == null || item.type == 0) ? false : true;
                        if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeNone.getValue() && !z) {
                            imageView.setVisibility(8);
                            helper.setGone(R.id.tv_screen, true);
                            helper.setText(R.id.tv_screen, R.string.no_bind);
                            if (((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).relayObjectMap == null || !((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(relateInfo.switchIndex))) {
                                return;
                            }
                            Object obj = ((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).relayObjectMap.get(Integer.valueOf(relateInfo.switchIndex));
                            if (obj instanceof Device) {
                                helper.setText(R.id.tv_screen, ((Device) obj).getName());
                                return;
                            } else {
                                if (obj instanceof Group) {
                                    helper.setText(R.id.tv_screen, ((Group) obj).getName());
                                    return;
                                }
                                return;
                            }
                        }
                        if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIcon.getValue()) {
                            imageView.setVisibility(0);
                            helper.setGone(R.id.tv_screen, false);
                            if (ScreenIconUtils.getScreenIconRes(relateInfo.iconIndex) != 0) {
                                imageView.setImageResource(ScreenIconUtils.getScreenIconRes(relateInfo.iconIndex));
                                return;
                            }
                            return;
                        }
                        if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIconWord.getValue()) {
                            imageView.setVisibility(0);
                            helper.setGone(R.id.tv_screen, true);
                            if (((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).isOpenElderLyMode) {
                                helper.setText(R.id.tv_screen, StringUtils.replaceString(relateInfo.screenStr, true));
                            } else {
                                helper.setText(R.id.tv_screen, relateInfo.screenStr);
                            }
                            if (ScreenIconUtils.getScreenIconRes(relateInfo.iconIndex) != 0) {
                                imageView.setImageResource(ScreenIconUtils.getScreenIconRes(relateInfo.iconIndex));
                                return;
                            }
                            return;
                        }
                        imageView.setVisibility(8);
                        helper.setGone(R.id.tv_screen, true);
                        if (((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).isOpenElderLyMode) {
                            helper.setText(R.id.tv_screen, StringUtils.replaceString(relateInfo.screenStr, false));
                            return;
                        } else {
                            helper.setText(R.id.tv_screen, relateInfo.screenStr);
                            return;
                        }
                    }
                    imageView.setVisibility(8);
                    helper.setGone(R.id.tv_screen, true);
                    helper.setText(R.id.tv_screen, R.string.no_bind);
                    if (((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).relayObjectMap == null || !((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(helper.getAdapterPosition()))) {
                        return;
                    }
                    Object obj2 = ((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).relayObjectMap.get(Integer.valueOf(helper.getAdapterPosition()));
                    if (obj2 instanceof Device) {
                        helper.setText(R.id.tv_screen, ((Device) obj2).getName());
                    } else if (obj2 instanceof Group) {
                        helper.setText(R.id.tv_screen, ((Group) obj2).getName());
                    }
                }
            };
            this.screenAdapter = baseQuickAdapter2;
            baseQuickAdapter2.bindToRecyclerView(((ActPageScreenPanelBinding) this.mViewBinding).rvScreenInfo);
            RecyclerView recyclerView = ((ActPageScreenPanelBinding) this.mViewBinding).rvScreenInfo;
            PagerGridLayoutManager pagerGridLayoutManager = new PagerGridLayoutManager(2, 2);
            this.mScreenGridLayoutManager = pagerGridLayoutManager;
            recyclerView.setLayoutManager(pagerGridLayoutManager);
            ((ActPageScreenPanelBinding) this.mViewBinding).rvScreenInfo.setHasFixedSize(true);
            ((DefaultItemAnimator) ((ActPageScreenPanelBinding) this.mViewBinding).rvScreenInfo.getItemAnimator()).setSupportsChangeAnimations(false);
            this.mScreenGridLayoutManager.setEnable(false);
            this.mScreenGridLayoutManager.setPagerChangedListener(new PagerGridLayoutManager.PagerChangedListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActPageScreenPanel.7
                @Override // com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridLayoutManager.PagerChangedListener
                public void onPagerCountChanged(int pagerCount) {
                }

                @Override // com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridLayoutManager.PagerChangedListener
                public void onPagerIndexSelected(int prePagerIndex, int currentPagerIndex) {
                    ((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).curScreen.setValue(Integer.valueOf(currentPagerIndex));
                    ActPageScreenPanel.this.infoAdapter.replaceData(new ArrayList(((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).relatedInfoList.subList(((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).getCurScreen() * 4, (((ActSmartPanelVM) ActPageScreenPanel.this.mViewModel).getCurScreen() + 1) * 4)));
                    if (currentPagerIndex > -1 && currentPagerIndex < ((ActPageScreenPanelBinding) ActPageScreenPanel.this.mViewBinding).layoutDot.getChildCount()) {
                        ((ImageView) ((ActPageScreenPanelBinding) ActPageScreenPanel.this.mViewBinding).layoutDot.getChildAt(currentPagerIndex)).setImageResource(R.drawable.dot_white);
                    }
                    if (prePagerIndex > -1 && prePagerIndex < ((ActPageScreenPanelBinding) ActPageScreenPanel.this.mViewBinding).layoutDot.getChildCount()) {
                        ((ImageView) ((ActPageScreenPanelBinding) ActPageScreenPanel.this.mViewBinding).layoutDot.getChildAt(prePagerIndex)).setImageResource(R.drawable.dot_gray);
                    }
                    ((ActPageScreenPanelBinding) ActPageScreenPanel.this.mViewBinding).ivScreen.setImageResource(ActPageScreenPanel.this.getTheme(currentPagerIndex));
                }
            });
            ((ActPageScreenPanelBinding) this.mViewBinding).layoutDot.removeAllViews();
            for (int i = 0; i < ((ActSmartPanelVM) this.mViewModel).screenCount; i++) {
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(3, 3));
                if (i == 0) {
                    imageView.setImageResource(R.drawable.dot_white);
                } else {
                    imageView.setImageResource(R.drawable.dot_gray);
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                layoutParams.leftMargin = 5;
                layoutParams.rightMargin = 5;
                ((ActPageScreenPanelBinding) this.mViewBinding).layoutDot.addView(imageView, layoutParams);
            }
            return;
        }
        baseQuickAdapter.replaceData(((ActSmartPanelVM) this.mViewModel).relatedInfoList);
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    private void showUnbindDialog(final int position) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.reset_relate));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActPageScreenPanel$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActPageScreenPanel.this.lambda$showUnbindDialog$11(position, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$11(int i, Integer num) {
        ((ActSmartPanelVM) this.mViewModel).unBindRelateInfo(i);
    }

    private void showInitGuide() {
        int i;
        if (SharedPreferenceUtil.queryBooleanValue(Constants.NEED_SCREEN_GUIDE, true)) {
            int i2 = ((ActSmartPanelVM) this.mViewModel).zoneCount;
            if (i2 == 2) {
                i = LanguageUtils.isChinese(this.activity) ? R.layout.view_s2_pro_guide : R.layout.view_s2_pro_guide_en;
            } else if (i2 == 3) {
                i = LanguageUtils.isChinese(this.activity) ? R.layout.view_s3_pro_guide : R.layout.view_s3_pro_guide_en;
            } else {
                i = LanguageUtils.isChinese(this.activity) ? R.layout.view_s1_pro_guide : R.layout.view_s1_pro_guide_en;
            }
            SharedPreferenceUtil.edit().keepShared(Constants.NEED_SCREEN_GUIDE, false);
            new Curtain(this).with(((ActPageScreenPanelBinding) this.mViewBinding).viewGuide).setTopView(i).addOnTopViewClickListener(R.id.iv_close, new OnViewInTopClickListener<IGuide>(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActPageScreenPanel.8
                @Override // com.qw.curtain.lib.OnViewInTopClickListener
                public void onClick(View current, IGuide currentHost) {
                    currentHost.dismissGuide();
                }
            }).show();
        }
    }

    private boolean needDarkColor() {
        try {
            Object value = ((ActSmartPanelVM) this.mViewModel).controlObject.getValue();
            if (value instanceof Device) {
                int parseInt = Integer.parseInt(((Device) value).getPanelColor()) - 1;
                if (parseInt == 2 || parseInt == 3 || parseInt == 5 || parseInt == 7) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            LHomeLog.e(getClass(), e.toString());
            return false;
        }
    }
}