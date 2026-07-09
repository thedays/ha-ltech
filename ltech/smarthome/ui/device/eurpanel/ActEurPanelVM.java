package com.ltech.smarthome.ui.device.eurpanel;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.model.repo.ifun.IDevice;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.ui.scene.ActSelectCgdPro;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.ParamMap;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.ltech.smarthome.view.dialog.RgbFunctionTipDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.ltech.smarthome.view.dialog.SelectListSubLineDialog;
import com.smart.message.ResponseMsg;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActEurPanelVM extends BaseViewModel {

    /* renamed from: b, reason: collision with root package name */
    public int f6270b;
    public boolean batchSetScene;
    public long controlId;
    public int cwBrt;
    public int g;
    public boolean groupControl;
    public int lightType;
    public long placeId;
    public String productId;
    public int r;
    public RelateInfoAssistant relateInfoAssistant;
    public Listing<Group> request;
    public int rgbBrt;
    public int w;
    public MutableLiveData<Object> controlObject = new MutableLiveData<>();
    public MediatorLiveData<List<Device>> deviceList = new MediatorLiveData<>();
    public MediatorLiveData<List<Group>> groupList = new MediatorLiveData<>();
    public MutableLiveData<Boolean> isOpen = new MutableLiveData<>(true);
    public MutableLiveData<List<Integer>> selectZoneList = new MutableLiveData<>(new ArrayList(Arrays.asList(0, 0, 0, 0)));
    public MutableLiveData<List<Integer>> stateList = new MutableLiveData<>(new ArrayList(Arrays.asList(0, 0, 0, 0)));
    public MutableLiveData<List<Integer>> functionStateList = new MutableLiveData<>(new ArrayList(Arrays.asList(1, 1, 1, 1, 1)));
    public SingleLiveEvent<Void> refreshEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> modeClickEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showBindDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showDoubtDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Integer> setLightEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> batchRefreshScene = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> batchSceneFinish = new SingleLiveEvent<>();
    public int cwState = 1;
    public int rgbState = 1;
    public RecyclerView.ItemDecoration decoration = new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM.1
        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), 0);
        }
    };
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM$$ExternalSyntheticLambda10
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActEurPanelVM.this.lambda$new$0((View) obj);
        }
    });

    static /* synthetic */ void lambda$setOpenState$1(ResponseMsg responseMsg) {
    }

    public boolean getBit(long value, int bitPosition) {
        return (value & (1 << bitPosition)) != 0;
    }

    public void initRelateInfoList(Object object) {
        RelateInfoUtils.initRelateInfoList(object);
        this.relateInfoAssistant = RelateInfoUtils.relateInfoAssistant;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.iv_doubt /* 2131297028 */:
                if (this.isOpen.getValue().booleanValue()) {
                    this.showDoubtDialogEvent.call();
                    break;
                }
                break;
            case R.id.iv_function_doubt /* 2131297065 */:
                RgbFunctionTipDialog.asDefault().showDialog((FragmentActivity) ActivityUtils.getTopActivity());
                break;
            case R.id.iv_mode /* 2131297137 */:
                if (this.isOpen.getValue().booleanValue()) {
                    this.modeClickEvent.call();
                    break;
                }
                break;
            case R.id.iv_rgb /* 2131297213 */:
                this.showDialogEvent.call();
                break;
            case R.id.iv_switch /* 2131297281 */:
                setBindSwitch(0, !this.isOpen.getValue().booleanValue() ? 1 : 0);
                this.isOpen.setValue(Boolean.valueOf(!r2.getValue().booleanValue()));
                break;
            case R.id.layout_brt_100 /* 2131297376 */:
                if (this.isOpen.getValue().booleanValue()) {
                    this.setLightEvent.setValue(100);
                    break;
                }
                break;
            case R.id.layout_brt_25 /* 2131297377 */:
                if (this.isOpen.getValue().booleanValue()) {
                    this.setLightEvent.setValue(25);
                    break;
                }
                break;
            case R.id.layout_brt_50 /* 2131297378 */:
                if (this.isOpen.getValue().booleanValue()) {
                    this.setLightEvent.setValue(50);
                    break;
                }
                break;
            case R.id.layout_brt_75 /* 2131297379 */:
                if (this.isOpen.getValue().booleanValue()) {
                    this.setLightEvent.setValue(75);
                    break;
                }
                break;
            case R.id.tv_wait_bind /* 2131299101 */:
                if (this.isOpen.getValue().booleanValue()) {
                    this.showBindDialogEvent.call();
                    break;
                }
                break;
        }
    }

    public void setOpenState(List<Integer> list) {
        if (isEmptyGroup(this.controlObject.getValue(), new boolean[0])) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
        }
        sb.reverse();
        CmdAssistant.getLightCmdAssistant(this.controlObject.getValue(), getZoneNum()).sendSwitchValue(ActivityUtils.getTopActivity(), Integer.parseInt(sb.toString(), 2), new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM$$ExternalSyntheticLambda17
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelVM.lambda$setOpenState$1((ResponseMsg) obj);
            }
        });
    }

    public void setTotalBrt(int progress, boolean finish) {
        int i = this.functionStateList.getValue().get(3).intValue() == 1 ? progress : 0;
        if (this.functionStateList.getValue().get(4).intValue() != 1) {
            progress = 0;
        }
        getLightCmdHelper().sendTotalBrt(getContext(), i, progress, finish);
    }

    public void setRgbBrt(int progress, boolean finish) {
        if (this.functionStateList.getValue().get(4).intValue() != 1) {
            progress = 0;
        }
        getLightCmdHelper().sendRgbBrt(getContext(), progress, finish);
    }

    public void setCW(int progress, boolean finish) {
        getLightCmdHelper().sendWy(getContext(), progress, finish);
    }

    public void refreshPanelData(ResponseMsg msg) {
        boolean z;
        char c2;
        String resData = msg.getResData();
        if (resData.length() > 16) {
            this.isOpen.setValue(Boolean.valueOf(Integer.parseInt(resData.substring(12, 14), 16) == 1));
            String substring = resData.substring(14, 16);
            ArrayList arrayList = new ArrayList();
            String binaryString = Integer.toBinaryString(Integer.parseInt(substring, 16));
            while (binaryString.length() < 4) {
                binaryString = "0" + binaryString;
            }
            int length = binaryString.toCharArray().length;
            for (int i = 0; i < length; i++) {
                arrayList.add(Integer.valueOf(r2[i] - '0'));
            }
            Collections.reverse(arrayList);
            this.selectZoneList.setValue(arrayList);
            String substring2 = resData.substring(16, 18);
            ArrayList arrayList2 = new ArrayList();
            String binaryString2 = Integer.toBinaryString(Integer.parseInt(substring2, 16));
            while (binaryString2.length() < 4) {
                binaryString2 = "0" + binaryString2;
            }
            int length2 = binaryString2.toCharArray().length;
            for (int i2 = 0; i2 < length2; i2++) {
                arrayList2.add(Integer.valueOf(r2[i2] - '0'));
            }
            Collections.reverse(arrayList2);
            this.stateList.setValue(arrayList2);
            Log.e(getClass().getSimpleName(), "qweqwe : refreshPanelData: " + resData);
            if (this.productId.equals(ProductId.ID_EUR_PANEL_EB5) || this.productId.equals(ProductId.ID_AS_PANEL_U5S) || this.productId.equals(ProductId.ID_AS_PANEL_U4S)) {
                z = resData.length() >= 36;
                if (resData.length() >= 34) {
                    String substring3 = resData.substring(20, 22);
                    ArrayList arrayList3 = new ArrayList();
                    String binaryString3 = Integer.toBinaryString(Integer.parseInt(substring3, 16));
                    c2 = 0;
                    while (binaryString3.length() < 5) {
                        binaryString3 = "0" + binaryString3;
                    }
                    int length3 = binaryString3.toCharArray().length;
                    for (int i3 = 0; i3 < length3; i3++) {
                        arrayList3.add(Integer.valueOf(r6[i3] - '0'));
                    }
                    Collections.reverse(arrayList3);
                    Log.e(getClass().getSimpleName(), "qweqwe : refreshPanelData: " + arrayList3);
                    this.functionStateList.setValue(arrayList3);
                    this.cwState = this.functionStateList.getValue().get(3).intValue();
                    this.rgbState = this.functionStateList.getValue().get(4).intValue();
                    this.r = Integer.parseInt(resData.substring(22, 24), 16);
                    this.g = Integer.parseInt(resData.substring(24, 26), 16);
                    this.f6270b = Integer.parseInt(resData.substring(26, 28), 16);
                    this.w = Integer.parseInt(resData.substring(28, 30), 16);
                    this.rgbBrt = Integer.parseInt(resData.substring(30, 32), 16);
                    this.cwBrt = Integer.parseInt(resData.substring(32, 34), 16);
                    this.refreshEvent.call();
                }
                c2 = 0;
            } else {
                if (this.productId.equals(ProductId.ID_EUR_PANEL_EB2) || this.productId.equals(ProductId.ID_AS_PANEL_U2S)) {
                    z = resData.length() >= 26;
                    if (resData.length() >= 24) {
                        this.w = Integer.parseInt(resData.substring(20, 22), 16);
                        this.cwBrt = Integer.parseInt(resData.substring(22, 24), 16);
                        this.refreshEvent.call();
                    }
                } else if (this.productId.equals(ProductId.ID_EUR_PANEL_EB1) || this.productId.equals(ProductId.ID_AS_PANEL_U1S)) {
                    z = resData.length() >= 24;
                } else {
                    c2 = 0;
                    z = false;
                }
                c2 = 0;
            }
            if (z) {
                int parseInt = Integer.parseInt(resData.substring(resData.length() - 2), 16);
                for (int i4 = 0; i4 < 4; i4++) {
                    if (getBit(parseInt, i4)) {
                        RelatedInfoExtParam.RelateInfo relateInfo = new RelatedInfoExtParam.RelateInfo();
                        relateInfo.type = 3;
                        relateInfo.objectId = i4 + 1;
                        this.relateInfoAssistant.setSceneRelateInfo(i4, relateInfo);
                    }
                }
                Object value = this.controlObject.getValue();
                if (value instanceof Device) {
                    Device device = (Device) value;
                    device.setExtParam(this.relateInfoAssistant.getExtParamString());
                    IDevice device2 = Injection.repo().device();
                    Device[] deviceArr = new Device[1];
                    deviceArr[c2] = device;
                    device2.saveDevice(deviceArr);
                    this.controlObject.setValue(device);
                    return;
                }
                Object value2 = this.controlObject.getValue();
                if (value2 instanceof Group) {
                    Group group = (Group) value2;
                    group.setExtParam(this.relateInfoAssistant.getExtParamString());
                    Injection.repo().group().saveGroup(group);
                    this.controlObject.setValue(group);
                }
            }
        }
    }

    public void refreshBindPanelData(Object object, RelateInfoAssistant relateInfoAssistant, ResponseMsg msg) {
        String resData = msg.getResData();
        if (this.productId.equals(ProductId.ID_RC_B5)) {
            if (resData.length() < 36) {
                return;
            }
        } else if (this.productId.equals(ProductId.ID_RC_B2)) {
            if (resData.length() < 26) {
                return;
            }
        } else if (!this.productId.equals(ProductId.ID_RC_B1) || resData.length() < 24) {
            return;
        }
        int parseInt = Integer.parseInt(resData.substring(resData.length() - 2), 16);
        for (int i = 0; i < 4; i++) {
            if (getBit(parseInt, i)) {
                RelatedInfoExtParam.RelateInfo relateInfo = new RelatedInfoExtParam.RelateInfo();
                relateInfo.type = 3;
                relateInfo.objectId = i + 1;
                relateInfoAssistant.setSceneRelateInfo(i, relateInfo);
            }
        }
        if (object instanceof Device) {
            Device device = (Device) object;
            device.setExtParam(relateInfoAssistant.getExtParamString());
            Injection.repo().device().saveDevice(device);
            MutableLiveData<Object> mutableLiveData = this.controlObject;
            mutableLiveData.setValue(mutableLiveData.getValue());
            return;
        }
        if (object instanceof Group) {
            Group group = (Group) object;
            group.setExtParam(relateInfoAssistant.getExtParamString());
            Injection.repo().group().saveGroup(group);
            if (ProductRepository.isEurPanel(this.controlObject.getValue())) {
                this.controlObject.setValue(group);
            } else {
                MutableLiveData<Object> mutableLiveData2 = this.controlObject;
                mutableLiveData2.setValue(mutableLiveData2.getValue());
            }
        }
    }

    public int getZoneNum() {
        if (this.relateInfoAssistant.getZoneNumber() <= 1) {
            return 1;
        }
        StringBuilder sb = new StringBuilder();
        Iterator<Integer> it = this.selectZoneList.getValue().iterator();
        while (it.hasNext()) {
            sb.append(it.next());
        }
        sb.reverse();
        return Integer.parseInt(sb.toString(), 2);
    }

    public int getStateOnZoneNum() {
        StringBuilder sb = new StringBuilder();
        Iterator<Integer> it = this.stateList.getValue().iterator();
        while (it.hasNext()) {
            sb.append(it.next());
        }
        sb.reverse();
        return Integer.parseInt(sb.toString(), 2);
    }

    public LightAssistant getLightCmdHelper() {
        if (isEmptyGroup(this.controlObject.getValue(), new boolean[0])) {
            return CmdAssistant.getLightCmdAssistant(null, new int[0]);
        }
        return CmdAssistant.getLightCmdAssistant(this.controlObject.getValue(), getZoneNum());
    }

    public void setBindSwitch(int zoneNumber, int openState) {
        if (isEmptyGroup(this.controlObject.getValue(), new boolean[0])) {
            return;
        }
        CmdAssistant.getDeviceAssistant(this.controlObject.getValue(), new int[0]).setEurOpenState(ActivityUtils.getTopActivity(), zoneNumber, openState);
    }

    public void showZoneBindDialog(final int position, RelateInfoItem item, boolean isSingleZone) {
        final String str;
        if (item.relateInfo != null) {
            if (TextUtils.isEmpty(item.relateInfo.name)) {
                if (isZoneBind(item)) {
                    str = item.infoName;
                } else if (isSingleZone) {
                    str = StringUtils.getString(R.string.wait_bind);
                } else {
                    str = getContext().getResources().getStringArray(R.array.eur_panel_zone_key)[position];
                }
            } else {
                str = item.relateInfo.name;
            }
        } else if (isSingleZone) {
            str = StringUtils.getString(R.string.wait_bind);
        } else {
            str = getContext().getResources().getStringArray(R.array.eur_panel_zone_key)[position];
        }
        boolean isZoneBind = isZoneBind(item);
        final ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(StringUtils.getString(R.string.please_choose)).setCancelString(StringUtils.getString(R.string.cancel)).setSelectPosition(-1);
        if (isZoneBind) {
            arrayList.add(new ParamMap(StringUtils.getString(R.string.reset_relate), 0));
        } else {
            arrayList.add(new ParamMap(StringUtils.getString(R.string.bind_light_group), 1));
            if (!isSingleZone && !ProductRepository.isRcB(this.productId)) {
                arrayList.add(new ParamMap(StringUtils.getString(R.string.bind_open_cur_zone), 2));
            }
        }
        if (!ProductId.ID_EUR_PANEL_EB6.equals(this.productId)) {
            arrayList.add(new ParamMap(StringUtils.getString(R.string.customize_name), 3));
            if (isZoneBind) {
                arrayList.add(new ParamMap(StringUtils.getString(R.string.app_str_sonos_sync), 4));
            } else {
                arrayList.add(new ParamMap(StringUtils.getString(R.string.reset_data), 5));
            }
        }
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelVM.this.lambda$showZoneBindDialog$3(arrayList, position, str, (Integer) obj);
            }
        }).setSelectList(Utils.getNameList(arrayList));
        selectPosition.showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showZoneBindDialog$3(List list, int i, String str, Integer num) {
        int value = ((ParamMap) list.get(num.intValue())).getValue();
        if (value == 0) {
            showUnbindDialog(i, true);
            return;
        }
        if (value == 1) {
            NavUtils.destination(ActEurPanelSelectDeviceAndGroup.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, this.controlId).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withInt(Constants.RELATED_POSITION, i).withInt(Constants.LIGHT_TYPE, ProductRepository.isAsPanel(this.controlObject.getValue()) ? AsHelper.convertType(this.controlObject.getValue()) : EurHelper.convertType(this.controlObject.getValue())).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
            return;
        }
        if (value == 2) {
            setBindSwitch(1 << i, 1);
            return;
        }
        if (value == 3) {
            showEditNameDialog(i, str);
            return;
        }
        if (value == 4) {
            showLoadingDialog();
            EurHelper.subscribePublishAdd(getContext(), this.controlObject.getValue(), RelateInfoUtils.getRelateInfoObject(this.relateInfoAssistant.getRelateInfo(i)), i, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM$$ExternalSyntheticLambda7
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActEurPanelVM.this.lambda$showZoneBindDialog$2((Boolean) obj);
                }
            });
        } else {
            if (value != 5) {
                return;
            }
            EurHelper.clearPublishAddress(getContext(), this.controlObject.getValue(), i);
            showSuccessTipDialog(StringUtils.getString(R.string.execute_success));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showZoneBindDialog$2(Boolean bool) {
        if (bool.booleanValue()) {
            showSuccessTipDialog(StringUtils.getString(R.string.sync_success));
        } else {
            showErrorTipDialog(StringUtils.getString(R.string.local_scene_sync_fail));
        }
    }

    private void showEditNameDialog(final int position, String name) {
        EditDialog.asDefault().setContent(name).setTitle(StringUtils.getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelVM.this.lambda$showEditNameDialog$4(position, (String) obj);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$4(int i, String str) {
        if (TextUtils.isEmpty(str)) {
            SmartToast.showShort(StringUtils.getString(R.string.input_name));
        } else {
            this.relateInfoAssistant.eurSetRelateInfo(i, str);
            uploadData(new IAction[0]);
        }
    }

    public void showRelateCurSceneDialog(final int position) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList.add(StringUtils.getString(R.string.save_cur_state_scene));
        if (this.relateInfoAssistant.getSceneLongPress() == 1) {
            arrayList2.add(StringUtils.getString(R.string.save_cur_state_tip));
        } else {
            arrayList2.add("");
        }
        if (EurHelper.isEb125(this.controlObject.getValue())) {
            arrayList.add(StringUtils.getString(R.string.local_scene));
            arrayList2.add("");
            arrayList.add(StringUtils.getString(R.string.dali_scene));
            arrayList2.add("");
        }
        int i = this.lightType;
        if (i > 2) {
            if (position == 0) {
                arrayList.add(StringUtils.getString(R.string.r_value));
            } else if (position == 1) {
                arrayList.add(StringUtils.getString(R.string.g_value));
            } else if (position == 2) {
                arrayList.add(StringUtils.getString(R.string.b_value));
            } else if (i == 3) {
                arrayList.add(StringUtils.getString(R.string.str_saturation));
            } else if (i == 4) {
                arrayList.add(StringUtils.getString(R.string.w_value));
            } else {
                arrayList.add(StringUtils.getString(R.string.wy_value));
            }
            if (this.lightType == 3 && position == 3) {
                arrayList2.add(StringUtils.getString(R.string.change_saturation_tip));
            } else {
                arrayList2.add(StringUtils.getString(R.string.change_value_tip));
            }
        }
        SelectListSubLineDialog.asDefault(false).setTitle(StringUtils.getString(R.string.please_choose)).setCancelString(StringUtils.getString(R.string.cancel)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelVM.this.lambda$showRelateCurSceneDialog$5(position, (Integer) obj);
            }
        }).setSelectList(arrayList, arrayList2).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRelateCurSceneDialog$5(int i, Integer num) {
        if (AsHelper.isNewUb(this.controlObject.getValue())) {
            if (num.intValue() == 0) {
                saveCurScene(i);
                return;
            } else {
                unBindRelateInfo(i, false);
                return;
            }
        }
        int intValue = num.intValue();
        if (intValue == 0) {
            saveCurScene(i);
            return;
        }
        if (intValue == 1) {
            NavUtils.destination(ActSmartPanelSelectScene.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, this.controlId).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withInt(Constants.RELATED_POSITION, i).withBoolean(Constants.BATCH_SET_SCENE, this.batchSetScene).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
        } else if (intValue == 2) {
            NavUtils.destination(ActSelectCgdPro.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, this.controlId).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withInt(Constants.RELATED_POSITION, i).withBoolean(Constants.BATCH_SET_SCENE, this.batchSetScene).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
        } else {
            if (intValue != 3) {
                return;
            }
            unBindRelateInfo(i, false);
        }
    }

    public void showRelatedSceneDialog(final int position, boolean unBind) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(StringUtils.getString(R.string.please_choose)).setCancelString(StringUtils.getString(R.string.cancel)).setSelectPosition(-1);
        if (unBind) {
            arrayList.add(StringUtils.getString(R.string.reset_relate));
            selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM$$ExternalSyntheticLambda13
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActEurPanelVM.this.lambda$showRelatedSceneDialog$6(position, (Integer) obj);
                }
            }).setSelectList(arrayList);
        } else {
            arrayList.add(StringUtils.getString(R.string.local_scene));
            arrayList.add(StringUtils.getString(R.string.dali_scene));
            selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM$$ExternalSyntheticLambda14
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActEurPanelVM.this.lambda$showRelatedSceneDialog$7(position, (Integer) obj);
                }
            }).setSelectList(arrayList);
        }
        selectPosition.showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRelatedSceneDialog$6(int i, Integer num) {
        showUnbindDialog(i, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRelatedSceneDialog$7(int i, Integer num) {
        int intValue = num.intValue();
        if (intValue == 0) {
            NavUtils.destination(ActSmartPanelSelectScene.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, this.controlId).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withInt(Constants.RELATED_POSITION, i).withBoolean(Constants.BATCH_SET_SCENE, this.batchSetScene).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
        } else if (intValue == 1) {
            NavUtils.destination(ActSelectCgdPro.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, this.controlId).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withInt(Constants.RELATED_POSITION, i).withBoolean(Constants.BATCH_SET_SCENE, this.batchSetScene).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
        }
        this.batchSetScene = false;
    }

    private void showUnbindDialog(final int position, final boolean isZone) {
        if (this.batchSetScene) {
            this.relateInfoAssistant.resetRelateSceneInfo(position);
            this.batchRefreshScene.call();
        } else if (ProductRepository.isRcB(this.productId)) {
            RelateInfoUtils.showImageTipDialog((Device) this.controlObject.getValue(), (FragmentActivity) ActivityUtils.getTopActivity(), new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    ActEurPanelVM.this.lambda$showUnbindDialog$8(position, isZone, imageTipDialog);
                }
            });
        } else {
            unBindRelateInfo(position, isZone);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$8(int i, boolean z, ImageTipDialog imageTipDialog) {
        imageTipDialog.dismissDialog();
        unBindRelateInfo(i, z);
    }

    public void unBindRelateInfo(final int position, final boolean isZone) {
        int unicastAddress;
        if (Injection.state().isConnectOuterNet()) {
            showLoadingDialog(StringUtils.getString(R.string.unsubscribing));
            if (this.groupControl) {
                unicastAddress = ((Group) this.controlObject.getValue()).getGroupAddress();
            } else {
                unicastAddress = ((BleParam) ((Device) this.controlObject.getValue()).getParam(BleParam.class)).getUnicastAddress();
            }
            CmdAssistant.getSettingCmdAssistant(null, new int[0]).unSubscribeInEurPanel(ActivityUtils.getTopActivity(), unicastAddress, 1 << position, 2, isZone ? 1 : 2, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM$$ExternalSyntheticLambda6
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActEurPanelVM.this.lambda$unBindRelateInfo$10(isZone, position, (Boolean) obj);
                }
            });
            return;
        }
        SmartToast.showShort(R.string.no_network);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unBindRelateInfo$10(boolean z, final int i, Boolean bool) {
        if (!bool.booleanValue()) {
            dismissLoadingDialog();
            ActivityUtils.getTopActivity().runOnUiThread(new Runnable(this) { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM.2
                @Override // java.lang.Runnable
                public void run() {
                    ViewHelpUtil.showUnBindFailDialog((FragmentActivity) ActivityUtils.getTopActivity());
                }
            });
            return;
        }
        if (z) {
            this.relateInfoAssistant.resetRelateInfo(i);
            uploadData(new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM$$ExternalSyntheticLambda16
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActEurPanelVM.this.lambda$unBindRelateInfo$9(i, (Boolean) obj);
                }
            });
            if (this.groupControl) {
                return;
            }
            ReplaceHelper.instance().backupIndexData(getLifecycleOwner(), ((Device) this.controlObject.getValue()).getDeviceId(), UpdateBackDataRequest.BIND, null, i + 1);
            return;
        }
        this.relateInfoAssistant.resetRelateSceneInfo(i);
        uploadData(new IAction[0]);
        if (this.groupControl) {
            return;
        }
        ReplaceHelper.instance().backupIndexData(getLifecycleOwner(), ((Device) this.controlObject.getValue()).getDeviceId(), UpdateBackDataRequest.SCENE_BIND, null, i + 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unBindRelateInfo$9(int i, Boolean bool) {
        if (ProductRepository.needPublishAddress(this.controlObject.getValue())) {
            EurHelper.clearPublishAddress(ActivityUtils.getTopActivity(), this.controlObject.getValue(), i);
        }
    }

    public boolean isZoneBind(RelateInfoItem item) {
        return (item.infoName == null || item.relateInfo == null || item.relateInfo.objectId == 0) ? false : true;
    }

    public boolean isSceneBind(RelateInfoItem item) {
        return item.type == 3 && item.relateInfo != null && ((item.relateInfo.objectId > 0 && item.relateInfo.objectId <= 4) || item.infoName != null);
    }

    public void uploadData(final IAction<Boolean>... actions) {
        if (!this.groupControl) {
            final Device device = (Device) this.controlObject.getValue();
            ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), this.relateInfoAssistant.getExtParamString()).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM$$ExternalSyntheticLambda18
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEurPanelVM.this.lambda$uploadData$11((Disposable) obj);
                }
            }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM$$ExternalSyntheticLambda19
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActEurPanelVM.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEurPanelVM.this.lambda$uploadData$12(device, actions, obj);
                }
            }, new Consumer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEurPanelVM.this.lambda$uploadData$13((Throwable) obj);
                }
            });
        } else {
            final Group group = (Group) this.controlObject.getValue();
            ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(group.getGroupId(), this.relateInfoAssistant.getExtParamString()).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEurPanelVM.this.lambda$uploadData$14((Disposable) obj);
                }
            }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM$$ExternalSyntheticLambda19
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActEurPanelVM.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEurPanelVM.this.lambda$uploadData$15(group, actions, obj);
                }
            }, new Consumer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEurPanelVM.this.lambda$uploadData$16((Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$11(Disposable disposable) throws Exception {
        showLoadingDialog(StringUtils.getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$12(Device device, IAction[] iActionArr, Object obj) throws Exception {
        device.setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().device().saveDevice(device);
        this.controlObject.setValue(device);
        if (iActionArr.length > 0) {
            iActionArr[0].act(true);
        }
        SmartToast.showShort(R.string.save_success);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$13(Throwable th) throws Exception {
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$14(Disposable disposable) throws Exception {
        showLoadingDialog(StringUtils.getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$15(Group group, IAction[] iActionArr, Object obj) throws Exception {
        group.setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().group().saveGroup(group);
        this.controlObject.setValue(group);
        SmartToast.showShort(R.string.save_success);
        if (iActionArr.length > 0) {
            iActionArr[0].act(true);
        }
        SmartToast.showShort(R.string.save_success);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$16(Throwable th) throws Exception {
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    public void saveCurScene(final int position) {
        showLoadingDialog(StringUtils.getString(R.string.subscribing));
        int unicastAddress = EurHelper.getUnicastAddress(this.controlObject.getValue());
        ArrayList arrayList = new ArrayList();
        arrayList.add(1);
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).subscribeInEurPanel(ActivityUtils.getTopActivity(), 1 << position, unicastAddress, 0, 2, 1, 2, 3, arrayList, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM$$ExternalSyntheticLambda15
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelVM.this.lambda$saveCurScene$17(position, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveCurScene$17(int i, ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            saveAndUploadData(i);
        } else {
            dismissLoadingDialog();
            showFailDialog();
        }
    }

    private void saveAndUploadData(int position) {
        RelatedInfoExtParam.RelateInfo relateInfo = new RelatedInfoExtParam.RelateInfo();
        relateInfo.type = 3;
        relateInfo.objectId = position + 1;
        this.relateInfoAssistant.setSceneRelateInfo(position, relateInfo);
        uploadData(new IAction[0]);
    }

    private void showFailDialog() {
        ActivityUtils.getTopActivity().runOnUiThread(new Runnable(this) { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM.3
            @Override // java.lang.Runnable
            public void run() {
                ViewHelpUtil.showBindFailDialog((FragmentActivity) ActivityUtils.getTopActivity());
            }
        });
    }

    public void executeCurScene(final Context context, int position) {
        showLoadingDialog();
        CmdAssistant.getSceneCmdAssistant(this.controlObject.getValue(), new int[0]).executeEurCurrentScene(context, position + 1, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM$$ExternalSyntheticLambda12
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelVM.this.lambda$executeCurScene$18(context, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$executeCurScene$18(Context context, ResponseMsg responseMsg) {
        dismissLoadingDialog();
        if (responseMsg != null) {
            if (responseMsg.getStateCode() == 0) {
                showSuccessTipDialog(context.getString(R.string.execute_success));
                return;
            } else {
                SmartToast.showCenterShort(context.getString(R.string.execute_fail));
                return;
            }
        }
        SmartToast.showCenterShort(context.getString(R.string.execute_fail));
    }

    public void batchSaveScene(final List<Observable<Integer>> request) {
        ((ObservableSubscribeProxy) Observable.concat(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM.4
            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
                ActEurPanelVM.this.showLoadingDialog(String.format(StringUtils.getString(R.string.app_str_save) + "(%d/%d)", 1, Integer.valueOf(request.size())));
            }

            @Override // io.reactivex.Observer
            public void onNext(Integer i) {
                ActEurPanelVM.this.showLoadingDialog(String.format(StringUtils.getString(R.string.app_str_save) + "(%d/%d)", Integer.valueOf(i.intValue() + 1), Integer.valueOf(request.size())));
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
                ActEurPanelVM.this.showErrorTipDialog(StringUtils.getString(R.string.save_fail));
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                ActEurPanelVM.this.dismissLoadingDialog();
                ActEurPanelVM.this.batchSceneFinish.call();
            }
        });
    }

    protected boolean isEmptyGroup(Object object, boolean... showTip) {
        if (!(object instanceof Group) || !((Group) object).getDeviceIds().isEmpty()) {
            return false;
        }
        if (showTip.length <= 0 || !showTip[0]) {
            return true;
        }
        SmartToast.showShort(StringUtils.getString(R.string.app_str_group_empty));
        return true;
    }
}