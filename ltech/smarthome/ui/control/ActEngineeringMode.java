package com.ltech.smarthome.ui.control;

import android.graphics.Typeface;
import android.os.SystemClock;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.common.ListItem;
import com.ltech.smarthome.databinding.ActEngineeringModeBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.net.response.place.GetPlaceDataResponse;
import com.ltech.smarthome.nfc.ActNfcRestore;
import com.ltech.smarthome.ui.control.ActEngineeringMode;
import com.ltech.smarthome.ui.device.light.ActAutoNetTimeSetting;
import com.ltech.smarthome.ui.device.light.PowerState;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.upgrade.ActBtOta;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.TextClickUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.DeviceFrequencyAndIntervalDialog;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.SelectDimDepthDialog;
import com.ltech.smarthome.view.dialog.SelectKValueDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog;
import com.ltech.smarthome.view.dialog.SelectPowerStateBatchDialog;
import com.ltech.smarthome.view.dialog.TimeIntervalSelectDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.sun.jna.platform.win32.WinError;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class ActEngineeringMode extends BaseNormalActivity<ActEngineeringModeBinding> {
    private BaseQuickAdapter<ListItem, BaseViewHolder> mAdapter;
    private long placeId;
    public MutableLiveData<Integer> chooseTabEvent = new MutableLiveData<>(0);
    public MutableLiveData<JSONObject> placeDataResult = new MutableLiveData<>();
    private List<ListItem> itemList = new ArrayList();
    private boolean multiMode = false;
    private boolean selectAll = false;
    private PowerState powerState = new PowerState();
    private int powerFadeTime = WinError.ERROR_VOLUME_NOT_SIS_ENABLED;
    private int sceneTime = 3000;
    private int onTime = 3000;
    private int offTime = 3000;
    private int[] ctArray = {2700, 6500};
    private int[] dimArray = {1, 0};
    private int[] nightMode = new int[5];
    private int[] gatewayNightMode = new int[5];
    final int COUNTS = 10;
    final long DURATION = 5000;
    long[] mHits = new long[10];
    final int COUNTS1 = 5;
    final long DURATION1 = 5000;
    long[] mHits1 = new long[5];

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_engineering_mode;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.app_str_batch_set));
        setEditString(getString(R.string.multi_select));
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, 0L);
        ((ActEngineeringModeBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.control.ActEngineeringMode$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActEngineeringMode.this.lambda$initView$0((View) obj);
            }
        }));
        initAdapter();
        queryPlaceData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        switch (view.getId()) {
            case R.id.tv_bottom /* 2131298484 */:
                multiSet();
                break;
            case R.id.tv_gateway /* 2131298670 */:
                this.chooseTabEvent.setValue(2);
                break;
            case R.id.tv_light /* 2131298737 */:
                this.chooseTabEvent.setValue(0);
                break;
            case R.id.tv_panel /* 2131298858 */:
                this.chooseTabEvent.setValue(1);
                break;
        }
    }

    /* renamed from: com.ltech.smarthome.ui.control.ActEngineeringMode$1, reason: invalid class name */
    class AnonymousClass1 extends BaseQuickAdapter<ListItem, BaseViewHolder> {
        AnonymousClass1(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(final BaseViewHolder helper, final ListItem item) {
            helper.setText(R.id.tv_title, item.getTitle()).setGone(R.id.tv_sub, item.getSubTitle() != null).setGone(R.id.iv_select, ActEngineeringMode.this.multiMode).setImageResource(R.id.iv_select, item.isSelected() ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
            ((AppCompatTextView) helper.getView(R.id.tv_title)).getPaint().setFakeBoldText(true);
            ((AppCompatTextView) helper.getView(R.id.tv_sub)).getPaint().setFakeBoldText(true);
            if (item.getSubTitle() != null) {
                helper.setText(R.id.tv_sub, item.getSubTitle());
            }
            if (ActEngineeringMode.this.placeDataResult.getValue() != null && item.getFlag() != null) {
                String backData = ActEngineeringMode.this.getBackData(item.getFlag());
                if (ActEngineeringMode.this.multiMode) {
                    if (backData.startsWith(MqttTopic.MULTI_LEVEL_WILDCARD)) {
                        TextClickUtil.setCircleText((AppCompatTextView) helper.getView(R.id.tv_value), backData.substring(0, 7), backData.substring(7));
                    } else {
                        helper.setText(R.id.tv_value, backData);
                    }
                } else {
                    helper.setText(R.id.tv_value, "");
                }
            }
            helper.getView(R.id.iv_select).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.ActEngineeringMode$1$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ActEngineeringMode.AnonymousClass1.this.lambda$convert$0(item, helper, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(ListItem listItem, BaseViewHolder baseViewHolder, View view) {
            listItem.setSelected(!listItem.isSelected());
            ActEngineeringMode.this.mAdapter.notifyItemChanged(baseViewHolder.getBindingAdapterPosition());
            ActEngineeringMode.this.checkSelectAll();
        }
    }

    private void initAdapter() {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(R.layout.item_batch_setting, new ArrayList());
        this.mAdapter = anonymousClass1;
        anonymousClass1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.control.ActEngineeringMode$$ExternalSyntheticLambda10
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActEngineeringMode.this.lambda$initAdapter$1(baseQuickAdapter, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActEngineeringModeBinding) this.mViewBinding).rvContent);
        ((ActEngineeringModeBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        List<ListItem> settingList = getSettingList(0);
        this.itemList = settingList;
        this.mAdapter.replaceData(settingList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAdapter$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int key = this.mAdapter.getItem(i).getKey();
        if (key == 1) {
            showPowerStateDialog();
            return;
        }
        if (key == 2) {
            showSelectTimeDialog();
            return;
        }
        if (key == 3) {
            NavUtils.destination(ActEngineeringModeOnOff.class).withLong(Constants.PLACE_ID, this.placeId).withInt(Constants.ON_TIME_VALUE, this.onTime).withInt(Constants.OFF_TIME_VALUE, this.offTime).withBoolean(Constants.MULTI_SELECT, this.multiMode).navigation(this);
            return;
        }
        if (key == 6) {
            showDimDepthDialog();
            return;
        }
        if (key == 7) {
            showKValueDialog();
            return;
        }
        switch (key) {
            case 12:
            case 14:
                showNightModeDialog();
                break;
            case 13:
                showSelectSceneTimeDialog();
                break;
            case 15:
                NavUtils.destination(ActNfcRestore.class).navigation(this);
                break;
        }
    }

    private void changeViewMode() {
        this.multiMode = !this.multiMode;
        List<ListItem> settingList = getSettingList(0);
        this.itemList = settingList;
        this.mAdapter.replaceData(settingList);
        if (this.multiMode) {
            setTitle("");
            setBackImage(0);
            setBackString(getString(R.string.finish));
            setEditString(getString(R.string.app_str_select_all));
            ((ActEngineeringModeBinding) this.mViewBinding).layoutTab.setVisibility(8);
            ((ActEngineeringModeBinding) this.mViewBinding).layoutBottom.setVisibility(0);
            return;
        }
        setTitle(getString(R.string.app_str_batch_set));
        setBackImage(R.mipmap.icon_back);
        setBackString("");
        setEditString(getString(R.string.multi_select));
        ((ActEngineeringModeBinding) this.mViewBinding).layoutTab.setVisibility(0);
        ((ActEngineeringModeBinding) this.mViewBinding).layoutBottom.setVisibility(8);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.chooseTabEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.ActEngineeringMode$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEngineeringMode.this.lambda$startObserve$2((Integer) obj);
            }
        });
        this.placeDataResult.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.ActEngineeringMode$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEngineeringMode.this.lambda$startObserve$3((JSONObject) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        AppCompatTextView appCompatTextView = ((ActEngineeringModeBinding) this.mViewBinding).tvLight;
        int intValue = num.intValue();
        int i = R.drawable.shape_white_round_bg_10;
        appCompatTextView.setBackgroundResource(intValue == 0 ? R.drawable.shape_white_round_bg_10 : 0);
        ((ActEngineeringModeBinding) this.mViewBinding).tvLight.setTypeface(num.intValue() == 0 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        ((ActEngineeringModeBinding) this.mViewBinding).tvPanel.setBackgroundResource(num.intValue() == 1 ? R.drawable.shape_white_round_bg_10 : 0);
        ((ActEngineeringModeBinding) this.mViewBinding).tvPanel.setTypeface(num.intValue() == 1 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        AppCompatTextView appCompatTextView2 = ((ActEngineeringModeBinding) this.mViewBinding).tvGateway;
        if (num.intValue() != 2) {
            i = 0;
        }
        appCompatTextView2.setBackgroundResource(i);
        ((ActEngineeringModeBinding) this.mViewBinding).tvGateway.setTypeface(num.intValue() == 2 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        ((ActEngineeringModeBinding) this.mViewBinding).leftLine.setVisibility(num.intValue() == 2 ? 0 : 8);
        ((ActEngineeringModeBinding) this.mViewBinding).rightLine.setVisibility(num.intValue() != 0 ? 8 : 0);
        setEditString(num.intValue() == 0 ? getString(R.string.multi_select) : "             ");
        List<ListItem> settingList = getSettingList(num.intValue());
        this.itemList = settingList;
        this.mAdapter.replaceData(settingList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(JSONObject jSONObject) {
        parseBackData(jSONObject);
        this.mAdapter.notifyDataSetChanged();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        queryPlaceData();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (this.multiMode) {
            changeViewMode();
        } else {
            super.back();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkSelectAll() {
        this.selectAll = true;
        Iterator<ListItem> it = this.itemList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            } else if (!it.next().isSelected()) {
                this.selectAll = false;
                break;
            }
        }
        if (this.selectAll) {
            setEditString(getString(R.string.app_str_cancel_select_all));
        } else {
            setEditString(getString(R.string.app_str_select_all));
        }
    }

    private List<ListItem> getSettingList(int tab) {
        ArrayList arrayList = new ArrayList();
        if (tab != 0) {
            if (tab == 1) {
                arrayList.add(new ListItem(12, getString(R.string.app_str_night_mode), getString(R.string.app_str_pro_night_mode_tip), UpdateBackDataRequest.NIGHT_MODE));
                return arrayList;
            }
            arrayList.add(new ListItem(14, getString(R.string.app_str_night_mode), getString(R.string.app_str_night_mode_tip), UpdateBackDataRequest.GATEWAY_NIGHT_MODE));
            return arrayList;
        }
        arrayList.add(new ListItem(1, getString(R.string.app_str_power_on_status), getString(R.string.app_str_elec_state_set_tip), UpdateBackDataRequest.POWER_STATUS));
        arrayList.add(new ListItem(13, getString(R.string.app_str_scene_on_tip), getString(R.string.app_str_scene_on_content), UpdateBackDataRequest.SCENE_FADE_TIME));
        arrayList.add(new ListItem(2, getString(R.string.app_str_power_on_tip), getString(R.string.app_str_elec_tip), UpdateBackDataRequest.POWER_FADE_TIME));
        arrayList.add(new ListItem(3, getString(R.string.app_on_off_time_tip), getString(R.string.app_str_on_off_tip_content), UpdateBackDataRequest.FADE_TIME));
        arrayList.add(new ListItem(7, getString(R.string.k_range), getString(R.string.k_range_tip), UpdateBackDataRequest.CT_RANGE));
        arrayList.add(new ListItem(6, getString(R.string.dim_depth_set), getString(R.string.app_str_dim_depth_content), UpdateBackDataRequest.DIM_DEPTH));
        if (!this.multiMode) {
            arrayList.add(new ListItem(15, getString(R.string.restore_factory), getString(R.string.batch_restore_factory_tip)));
        }
        return arrayList;
    }

    private void parseBackData(JSONObject jsonObject) {
        this.powerState = new PowerState(ReplaceHelper.instance().parseBackupData(jsonObject, UpdateBackDataRequest.POWER_STATUS), true);
        this.sceneTime = ReplaceHelper.instance().parseBackupData(jsonObject, UpdateBackDataRequest.SCENE_FADE_TIME)[0];
        this.powerFadeTime = ReplaceHelper.instance().parseBackupData(jsonObject, UpdateBackDataRequest.POWER_FADE_TIME)[0];
        int[] parseBackupData = ReplaceHelper.instance().parseBackupData(jsonObject, UpdateBackDataRequest.FADE_TIME);
        this.onTime = parseBackupData[0];
        this.offTime = parseBackupData[1];
        this.ctArray = ReplaceHelper.instance().parseBackupData(jsonObject, UpdateBackDataRequest.CT_RANGE);
        this.dimArray = ReplaceHelper.instance().parseBackupData(jsonObject, UpdateBackDataRequest.DIM_DEPTH);
        this.nightMode = ReplaceHelper.instance().parseBackupData(jsonObject, UpdateBackDataRequest.NIGHT_MODE);
        this.gatewayNightMode = ReplaceHelper.instance().parseBackupData(jsonObject, UpdateBackDataRequest.GATEWAY_NIGHT_MODE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getBackData(String key) {
        int i;
        key.hashCode();
        switch (key) {
            case "kelvin":
                return String.format(Locale.US, "%dK-%dK", Integer.valueOf(this.ctArray[0]), Integer.valueOf(this.ctArray[1]));
            case "switch_night_mode":
                return this.nightMode[0] == 0 ? getString(R.string.function_close) : String.format(Locale.US, "%02d:%02d-%02d:%02d", Integer.valueOf(this.nightMode[1]), Integer.valueOf(this.nightMode[2]), Integer.valueOf(this.nightMode[3]), Integer.valueOf(this.nightMode[4]));
            case "powerStatus":
                List<SelectPowerOnStateDialog.OnOffState> generateContentList = SelectPowerOnStateDialog.generateContentList();
                int size = generateContentList.size();
                for (i = 0; i < size; i++) {
                    if (this.powerState.state == generateContentList.get(i).getMainValue()) {
                        if (this.powerState.state == 4) {
                            return this.powerState.getStateContent(this, null);
                        }
                        return generateContentList.get(i).getName();
                    }
                }
                return "";
            case "dimmerDepth":
                int[] iArr = this.dimArray;
                int i2 = iArr[0];
                int i3 = iArr[1];
                if (i2 != 2) {
                    return getString(R.string.mode_default);
                }
                String[] stringArray = getResources().getStringArray(R.array.dim_depth);
                if (i3 >= 1) {
                    return stringArray[i3 - 1];
                }
                return getString(R.string.mode_default);
            case "gateway_night_mode":
                return this.gatewayNightMode[0] == 0 ? getString(R.string.function_close) : String.format(Locale.US, "%02d:%02d-%02d:%02d", Integer.valueOf(this.gatewayNightMode[1]), Integer.valueOf(this.gatewayNightMode[2]), Integer.valueOf(this.gatewayNightMode[3]), Integer.valueOf(this.gatewayNightMode[4]));
            case "powerFadeTime":
                return String.format(Locale.US, "%.1f%s", Float.valueOf(this.powerFadeTime / 1000.0f), getString(R.string.sec));
            case "sceneFadeTime":
                return String.format(Locale.US, "%.1f%s", Float.valueOf(this.sceneTime / 1000.0f), getString(R.string.sec));
            default:
                return "";
        }
    }

    private void showNightModeDialog() {
        TimeIntervalSelectDialog asDefault = TimeIntervalSelectDialog.asDefault();
        boolean z = false;
        if (this.chooseTabEvent.getValue().intValue() != 1 ? this.gatewayNightMode[0] == 0 : this.nightMode[0] == 0) {
            z = true;
        }
        asDefault.setClose(z).setSelectStartMinPosition(this.chooseTabEvent.getValue().intValue() == 1 ? this.nightMode[1] : this.gatewayNightMode[1]).setSelectStartSecPosition(this.chooseTabEvent.getValue().intValue() == 1 ? this.nightMode[2] : this.gatewayNightMode[2]).setSelectEndMinPosition(this.chooseTabEvent.getValue().intValue() == 1 ? this.nightMode[3] : this.gatewayNightMode[3]).setSelectEndSecPosition(this.chooseTabEvent.getValue().intValue() == 1 ? this.nightMode[4] : this.gatewayNightMode[4]).setTitle(getString(R.string.app_str_night_mode)).setActionTitle(getString(R.string.function_open)).setCloseTitle(getString(R.string.function_close)).setSelectListener(new TimeIntervalSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.control.ActEngineeringMode.2
            @Override // com.ltech.smarthome.view.dialog.TimeIntervalSelectDialog.SelectListener
            public void confirm(int startMinPosition, int startSecPosition, int endMinPosition, int endSecPosition) {
                int[] iArr = {startMinPosition, startSecPosition, endMinPosition, endSecPosition};
                if (ActEngineeringMode.this.chooseTabEvent.getValue().intValue() == 1) {
                    ActEngineeringMode.this.nightMode = new int[]{1, startMinPosition, startSecPosition, endMinPosition, endSecPosition};
                } else {
                    ActEngineeringMode.this.gatewayNightMode = new int[]{1, startMinPosition, startSecPosition, endMinPosition, endSecPosition};
                }
                NavUtils.destination(ActChangeEngineerMode.class).withLong(Constants.PLACE_ID, ActEngineeringMode.this.placeId).withInt(Constants.ENGINEER_SET_TYPE, ActEngineeringMode.this.chooseTabEvent.getValue().intValue() == 1 ? 12 : 14).withIntArray(Constants.NIGHT_MODE_TIME_VALUE, iArr).navigation(ActivityUtils.getTopActivity());
                ReplaceHelper.instance().backupPlaceData(ActEngineeringMode.this.activity, UpdateBackDataRequest.NIGHT_MODE, CmdAssistant.getSettingCmdAssistant(null, new int[0]).setSmartPanelMode(0, 0, 1, 0, startMinPosition, startSecPosition, endMinPosition, endSecPosition));
            }

            @Override // com.ltech.smarthome.view.dialog.TimeIntervalSelectDialog.SelectListener
            public void close() {
                SharedPreferenceUtil.edit().keepShared(Constants.NIGHT_MODE_TIME_VALUE, "");
                NavUtils.destination(ActChangeEngineerMode.class).withLong(Constants.PLACE_ID, ActEngineeringMode.this.placeId).withInt(Constants.ENGINEER_SET_TYPE, ActEngineeringMode.this.chooseTabEvent.getValue().intValue() == 1 ? 12 : 14).navigation(ActivityUtils.getTopActivity());
                ReplaceHelper.instance().backupPlaceData(ActEngineeringMode.this.activity, UpdateBackDataRequest.NIGHT_MODE, CmdAssistant.getSettingCmdAssistant(null, new int[0]).setSmartPanelMode(0, 0, 0, 0, 0, 0, 0, 0));
            }
        }).showDialog(this.activity);
    }

    private void showSelectTimeDialog() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (int i2 = 0; i2 < 100; i2++) {
            arrayList.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        ArrayList arrayList2 = new ArrayList();
        while (i < 10) {
            arrayList2.add(i == 0 ? "000" : String.valueOf(i * 100));
            i++;
        }
        TimeSelectDialog.asDefault().setTitle(getString(R.string.please_select_time)).setSaveText(getSaveText()).setMinList(arrayList).setSecList(arrayList2).withUnit(true).setMinUnit(getString(R.string.sec)).setSecUnit(getString(R.string.ms)).setSelectMinPosition(this.powerFadeTime / 1000).setSelectSecPosition((this.powerFadeTime % 1000) / 100).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.control.ActEngineeringMode$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActEngineeringMode.this.lambda$showSelectTimeDialog$4(i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectTimeDialog$4(int i, int i2) {
        this.powerFadeTime = (i * 1000) + (i2 * 100);
        SharedPreferenceUtil.edit().keepShared(Constants.POWER_ON_TIME_VALUE, this.powerFadeTime);
        ReplaceHelper.instance().backupPlaceData(this, UpdateBackDataRequest.POWER_FADE_TIME, CmdAssistant.getLightCmdAssistant(null, new int[0]).setPowerOnTime(this.powerFadeTime));
        if (this.multiMode) {
            this.mAdapter.notifyDataSetChanged();
        } else {
            NavUtils.destination(ActChangeEngineerMode.class).withLong(Constants.PLACE_ID, this.placeId).withInt(Constants.ENGINEER_SET_TYPE, 2).navigation(this);
        }
    }

    private void showPowerStateDialog() {
        SelectPowerStateBatchDialog.asDefault().setSaveText(getSaveText()).setPowerState(this.powerState).setOnSaveListener(new SelectPowerStateBatchDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.control.ActEngineeringMode$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.view.dialog.SelectPowerStateBatchDialog.OnSaveListener
            public final boolean onSave(PowerState powerState) {
                boolean lambda$showPowerStateDialog$5;
                lambda$showPowerStateDialog$5 = ActEngineeringMode.this.lambda$showPowerStateDialog$5(powerState);
                return lambda$showPowerStateDialog$5;
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showPowerStateDialog$5(PowerState powerState) {
        SharedPreferenceUtil.edit().keepShared(Constants.ON_OFF_STATE_MAIN_VALUE, GsonUtils.toJson(powerState));
        ReplaceHelper.instance().backupPlaceData(this.activity, UpdateBackDataRequest.POWER_STATUS, CmdAssistant.getLightCmdAssistant(null, new int[0]).setOnState(powerState));
        if (this.multiMode) {
            this.mAdapter.notifyDataSetChanged();
        } else {
            NavUtils.destination(ActChangeEngineerMode.class).withLong(Constants.PLACE_ID, this.placeId).withInt(Constants.ENGINEER_SET_TYPE, 1).navigation(ActivityUtils.getTopActivity());
        }
        return true;
    }

    private void showDimDepthDialog() {
        SelectDimDepthDialog.asDefault().setTitle(getString(R.string.dim_depth)).setConfirmString(getSaveText()).setSelectPosition(this.dimArray[0] - 1).setProgress(21 - this.dimArray[1]).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.control.ActEngineeringMode$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEngineeringMode.this.lambda$showDimDepthDialog$6((Integer) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDimDepthDialog$6(Integer num) {
        this.dimArray[0] = num.intValue() == 0 ? 1 : 2;
        this.dimArray[1] = num.intValue();
        SharedPreferenceUtil.edit().keepShared(Constants.DIM_DEPTH_VALUE, num.intValue());
        ReplaceHelper.instance().backupPlaceData(this, UpdateBackDataRequest.DIM_DEPTH, CmdAssistant.getLightCmdAssistant(null, new int[0]).setDimDepth(num.intValue()));
        if (this.multiMode) {
            this.mAdapter.notifyDataSetChanged();
        } else {
            NavUtils.destination(ActChangeEngineerMode.class).withLong(Constants.PLACE_ID, this.placeId).withInt(Constants.ENGINEER_SET_TYPE, 6).navigation(this);
        }
    }

    private void showSelectSceneTimeDialog() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (int i2 = 0; i2 < 100; i2++) {
            arrayList.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        ArrayList arrayList2 = new ArrayList();
        while (i < 10) {
            arrayList2.add(i == 0 ? "000" : String.valueOf(i * 100));
            i++;
        }
        TimeSelectDialog.asDefault().setTitle(getString(R.string.please_select_time)).setSaveText(getSaveText()).setMinList(arrayList).setSecList(arrayList2).withUnit(true).setMinUnit(getString(R.string.sec)).setSecUnit(getString(R.string.ms)).setSelectMinPosition(this.sceneTime / 1000).setSelectSecPosition((this.sceneTime % 1000) / 100).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.control.ActEngineeringMode$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActEngineeringMode.this.lambda$showSelectSceneTimeDialog$7(i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectSceneTimeDialog$7(int i, int i2) {
        this.sceneTime = (i * 1000) + (i2 * 100);
        SharedPreferenceUtil.edit().keepShared(Constants.SCENE_ON_TIME_VALUE, this.sceneTime);
        ReplaceHelper.instance().backupPlaceData(this, UpdateBackDataRequest.SCENE_FADE_TIME, CmdAssistant.getLightCmdAssistant(null, new int[0]).setSceneOnTime(this.sceneTime));
        if (this.multiMode) {
            this.mAdapter.notifyDataSetChanged();
        } else {
            NavUtils.destination(ActChangeEngineerMode.class).withLong(Constants.PLACE_ID, this.placeId).withInt(Constants.ENGINEER_SET_TYPE, 13).navigation(this);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (this.chooseTabEvent.getValue().intValue() == 0) {
            if (this.multiMode) {
                this.selectAll = !this.selectAll;
                Iterator<ListItem> it = this.itemList.iterator();
                while (it.hasNext()) {
                    it.next().setSelected(this.selectAll);
                }
                this.mAdapter.notifyDataSetChanged();
                if (this.selectAll) {
                    setEditString(getString(R.string.app_str_cancel_select_all));
                    return;
                } else {
                    setEditString(getString(R.string.app_str_select_all));
                    return;
                }
            }
            changeViewMode();
            return;
        }
        long[] jArr = this.mHits;
        System.arraycopy(jArr, 1, jArr, 0, jArr.length - 1);
        long[] jArr2 = this.mHits;
        jArr2[jArr2.length - 1] = SystemClock.uptimeMillis();
        if (this.mHits[0] >= SystemClock.uptimeMillis() - 5000) {
            showSettingDialog();
        }
    }

    private void showSettingDialog() {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.setting)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.app_str_change_device_send_times));
        arrayList.add(getString(R.string.app_str_change_device_ttl));
        arrayList.add(getString(R.string.auto_net_time));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.control.ActEngineeringMode$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEngineeringMode.this.lambda$showSettingDialog$8((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.setOutCancel(false);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSettingDialog$8(Integer num) {
        if (num.intValue() == 0) {
            showEditTimesDialog();
        } else if (num.intValue() == 1) {
            showEditTTLDialog();
        } else if (num.intValue() == 2) {
            NavUtils.destination(ActAutoNetTimeSetting.class).withLong(Constants.PLACE_ID, this.placeId).navigation(this);
        }
    }

    protected void showEditTTLDialog() {
        EditDialog.asDefault().setHint(getString(R.string.app_str_ttl_scope)).setTitle(getString(R.string.app_str_change_device_ttl)).setInputType(2).setInputEmptyTip(getString(R.string.input_number)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.control.ActEngineeringMode$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEngineeringMode.this.lambda$showEditTTLDialog$9((String) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditTTLDialog$9(String str) {
        if (Integer.parseInt(str) > 32 || Integer.parseInt(str) < 4) {
            SmartToast.showShort(getString(R.string.app_str_out_of_range));
        } else {
            SharedPreferenceUtil.edit().keepShared(Constants.DEVICE_TTL_VALUE, Integer.parseInt(str));
            NavUtils.destination(ActChangeEngineerMode.class).withLong(Constants.PLACE_ID, this.placeId).withInt(Constants.ENGINEER_SET_TYPE, 4).navigation(ActivityUtils.getTopActivity());
        }
    }

    protected void showEditTimesDialog() {
        DeviceFrequencyAndIntervalDialog.asDefault().setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.control.ActEngineeringMode$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEngineeringMode.this.lambda$showEditTimesDialog$10((DeviceFrequencyAndIntervalDialog.FrequencyAndIntervalBean) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditTimesDialog$10(DeviceFrequencyAndIntervalDialog.FrequencyAndIntervalBean frequencyAndIntervalBean) {
        if (frequencyAndIntervalBean.getFrequency() > 10 || frequencyAndIntervalBean.getFrequency() < 1 || frequencyAndIntervalBean.getInterval() > 300 || frequencyAndIntervalBean.getInterval() < 100) {
            SmartToast.showShort(getString(R.string.app_str_out_of_range));
            return;
        }
        SharedPreferenceUtil.edit().keepShared(Constants.SEND_FREQUENCY_VALUE, frequencyAndIntervalBean.getFrequency());
        SharedPreferenceUtil.edit().keepShared(Constants.SEND_INTERVAL_VALUE, frequencyAndIntervalBean.getInterval());
        NavUtils.destination(ActChangeEngineerMode.class).withLong(Constants.PLACE_ID, this.placeId).withInt(Constants.ENGINEER_SET_TYPE, 5).navigation(ActivityUtils.getTopActivity());
    }

    private void showKValueDialog() {
        SelectKValueDialog.asDefault().setSaveText(getSaveText()).setMin(this.ctArray[0]).setMax(this.ctArray[1]).setOnSaveListener(new SelectKValueDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.control.ActEngineeringMode.3
            @Override // com.ltech.smarthome.view.dialog.SelectKValueDialog.OnSaveListener
            public void cancel() {
            }

            @Override // com.ltech.smarthome.view.dialog.SelectKValueDialog.OnSaveListener
            public boolean onSave(SelectKValueDialog.OnOffState onOffState, int selectPos) {
                ActEngineeringMode.this.ctArray[0] = onOffState.getMin();
                ActEngineeringMode.this.ctArray[1] = onOffState.getMax();
                SharedPreferenceUtil.edit().keepShared(Constants.MIN_K, onOffState.getMin());
                SharedPreferenceUtil.edit().keepShared(Constants.MAX_K, onOffState.getMax());
                ReplaceHelper.instance().backupPlaceData(ActEngineeringMode.this.activity, UpdateBackDataRequest.CT_RANGE, CmdAssistant.getLightCmdAssistant(null, new int[0]).setKRange(onOffState.getMin(), onOffState.getMax()));
                if (ActEngineeringMode.this.multiMode) {
                    ActEngineeringMode.this.mAdapter.notifyDataSetChanged();
                } else {
                    NavUtils.destination(ActChangeEngineerMode.class).withLong(Constants.PLACE_ID, ActEngineeringMode.this.placeId).withInt(Constants.ENGINEER_SET_TYPE, 7).navigation(ActivityUtils.getTopActivity());
                }
                return true;
            }
        }).showDialog(this);
    }

    private String getSaveText() {
        return getString(this.multiMode ? R.string.save : R.string.next);
    }

    private void multiSet() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (ListItem listItem : this.itemList) {
            if (listItem.isSelected()) {
                arrayList.add(Integer.valueOf(listItem.getKey()));
                int key = listItem.getKey();
                if (key == 1) {
                    SharedPreferenceUtil.edit().keepShared(Constants.ON_OFF_STATE_MAIN_VALUE, GsonUtils.toJson(this.powerState));
                } else if (key == 2) {
                    SharedPreferenceUtil.edit().keepShared(Constants.POWER_ON_TIME_VALUE, this.powerFadeTime);
                } else if (key == 3) {
                    SharedPreferenceUtil.edit().keepShared(Constants.ON_TIME_VALUE, this.onTime);
                    SharedPreferenceUtil.edit().keepShared(Constants.OFF_TIME_VALUE, this.offTime);
                } else if (key == 6) {
                    SharedPreferenceUtil.edit().keepShared(Constants.DIM_DEPTH_VALUE, this.dimArray[1]);
                } else if (key == 7) {
                    SharedPreferenceUtil.edit().keepShared(Constants.MIN_K, this.ctArray[0]);
                    SharedPreferenceUtil.edit().keepShared(Constants.MAX_K, this.ctArray[1]);
                } else if (key == 13) {
                    SharedPreferenceUtil.edit().keepShared(Constants.SCENE_ON_TIME_VALUE, this.sceneTime);
                }
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        NavUtils.destination(ActChangeEngineerModeMulti.class).withLong(Constants.PLACE_ID, this.placeId).withIntegerArrayList(Constants.ENGINEER_SET_TYPE, arrayList).navigation(ActivityUtils.getTopActivity());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void titleClick() {
        long[] jArr = this.mHits1;
        System.arraycopy(jArr, 1, jArr, 0, jArr.length - 1);
        long[] jArr2 = this.mHits1;
        jArr2[jArr2.length - 1] = SystemClock.uptimeMillis();
        if (this.mHits1[0] < SystemClock.uptimeMillis() - 5000 || !bleIsOk()) {
            return;
        }
        NavUtils.destination(ActBtOta.class).withLong(Constants.PLACE_ID, this.placeId).navigation(this.activity);
    }

    private void queryPlaceData() {
        ((ObservableSubscribeProxy) Injection.net().queryPlaceData(this.placeId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.ActEngineeringMode$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActEngineeringMode.this.lambda$queryPlaceData$11((GetPlaceDataResponse) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryPlaceData$11(GetPlaceDataResponse getPlaceDataResponse) throws Exception {
        this.placeDataResult.setValue(JSON.parseObject(GsonUtils.toJson(getPlaceDataResponse.getPlaceData())));
    }
}