package com.ltech.smarthome.push;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.placeuser.AgreeInvitationRequest;
import com.ltech.smarthome.net.request.placeuser.AgreePlaceUserRequest;
import com.ltech.smarthome.net.request.placeuser.RefuseInvitationRequest;
import com.ltech.smarthome.net.request.placeuser.RefusePlaceUserRequest;
import com.ltech.smarthome.push.PushDataHelper;
import com.ltech.smarthome.ui.control.ActHome;
import com.ltech.smarthome.ui.control.FtAutomation;
import com.ltech.smarthome.ui.control.FtCloudScene;
import com.ltech.smarthome.ui.control.FtDevice2;
import com.ltech.smarthome.ui.control.FtIntelligence;
import com.ltech.smarthome.ui.control.FtRoom;
import com.ltech.smarthome.ui.control.FtScene;
import com.ltech.smarthome.ui.device.doorsensor.ActDoorSensor;
import com.ltech.smarthome.ui.device.ir.ActAc;
import com.ltech.smarthome.ui.device.sonos.ActSonosPlayControl;
import com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgrade;
import com.ltech.smarthome.ui.replace.ActReplace;
import com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager;
import com.ltech.smarthome.upgrade.ActSpUpgrade;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.EventBusUtils;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.interfaces.OnShowListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.dialog.v3.WaitDialog;
import com.smart.message.MessageManager;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class PushDataHelper {
    private static BaseDialog mcuFailDialog;

    static /* synthetic */ void lambda$showReceiveInvitationDialog$11(Object obj) throws Exception {
    }

    static /* synthetic */ void lambda$showReceiveMemberJoinDialog$1(Object obj) throws Exception {
    }

    static /* synthetic */ void lambda$showReceiveMemberJoinDialog$3(Object obj) throws Exception {
    }

    static /* synthetic */ boolean lambda$showSecurityDialog$7(BaseDialog baseDialog, View view) {
        return false;
    }

    public static void handlePushData(CPushMessage message) {
        HashMap<String, Object> contentData = getContentData(message);
        LHomeLog.e(PushDataHelper.class, contentData.toString());
        int parseInt = Integer.parseInt(getValueString(contentData.get(PushContentParamKey.MESSAGE_TYPE)));
        if (ActivityUtils.getTopActivity() == null) {
            return;
        }
        switch (parseInt) {
            case 1:
                Injection.logout();
                showLogOutMessageDialog(contentData);
                break;
            case 2:
                handleDeviceStatusData(contentData);
                break;
            case 3:
                handleDeviceChange(contentData);
                break;
            case 4:
                handleDeviceChange(contentData);
                break;
            case 8:
                showReceiveInvitationDialog(contentData);
                break;
            case 9:
                showReceiveMemberJoinDialog(contentData);
                break;
            case 10:
            case 11:
                showManagerResponseDialog(contentData);
                break;
            case 12:
            case 13:
            case 14:
                showNoHandleMessageDialog(contentData);
                break;
            case 15:
                showDeleteFamilyMessageDialog(contentData);
                break;
            case 16:
                showNoHandleMessageDialog(contentData);
                break;
            case 17:
                handleGroupChange(contentData);
                break;
            case 18:
                handleSceneChange(contentData);
                break;
            case 19:
                handleAutomationChange(contentData);
                break;
            case 20:
                VoiceCallManager.getInstance().login(Injection.repo().user().getUserId());
                handelVoiceCall(contentData);
                break;
            case 21:
                VoiceCallManager.getInstance().login(Injection.repo().user().getUserId());
                handelVoiceCallStatus(contentData);
                break;
            case 24:
                showSecurityDialog(contentData);
                break;
            case 27:
                refreshSonos(contentData);
                break;
            case 28:
                refreshSuperPanelReplace(contentData);
                break;
            case 29:
                refreshDeviceStatus(contentData);
                break;
            case 30:
                showDeviceMcuUpgrade(contentData);
                break;
        }
    }

    private static void showDeviceMcuUpgrade(HashMap<String, Object> mapData) {
        final ArrayList arrayList = new ArrayList();
        final long asLong = mapData.get(PushContentParamKey.PANEL_ID) instanceof JsonPrimitive ? ((JsonPrimitive) mapData.get(PushContentParamKey.PANEL_ID)).getAsJsonPrimitive().getAsLong() : -1L;
        final int asInt = mapData.get(PushContentParamKey.ACTION) instanceof JsonPrimitive ? ((JsonPrimitive) mapData.get(PushContentParamKey.ACTION)).getAsJsonPrimitive().getAsInt() : -1;
        final int asInt2 = mapData.get(PushContentParamKey.TOTAL_NUM) instanceof JsonPrimitive ? ((JsonPrimitive) mapData.get(PushContentParamKey.TOTAL_NUM)).getAsJsonPrimitive().getAsInt() : -1;
        if (mapData.get("devices") instanceof JsonPrimitive) {
            try {
                JSONArray jSONArray = new JSONArray(((JsonPrimitive) mapData.get("devices")).getAsJsonPrimitive().getAsString());
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(Long.valueOf(jSONArray.optLong(i)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        final ArrayList arrayList2 = new ArrayList();
        if (mapData.get(PushContentParamKey.SUCCESSFUL_DEVICES) instanceof JsonPrimitive) {
            try {
                JSONArray jSONArray2 = new JSONArray(((JsonPrimitive) mapData.get(PushContentParamKey.SUCCESSFUL_DEVICES)).getAsJsonPrimitive().getAsString());
                for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                    arrayList2.add(Long.valueOf(jSONArray2.optLong(i2)));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.push.PushDataHelper.1
            @Override // java.lang.Runnable
            public void run() {
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(asLong);
                int i3 = asInt;
                if (i3 == 2) {
                    SmartToast.showCenterShort(String.format(StringUtils.getString(R.string.app_str_child_mcu_upgrade_stop_tip), Integer.valueOf(arrayList2.size()), Integer.valueOf(asInt2 - arrayList2.size())));
                } else {
                    if (i3 != 0) {
                        if (i3 == 1) {
                            PushDataHelper.showMcuFailDialog(asLong, asInt2, arrayList, arrayList2);
                            return;
                        }
                        if (i3 == 3) {
                            SmartToast.showCenterShort(String.format(StringUtils.getString(R.string.app_str_app_upgrading_fail), deviceByDeviceId.getName()));
                            PushDataHelper.refreshSpUpgrade(false, 1);
                            return;
                        }
                        if (i3 == 4) {
                            SmartToast.showCenterShort(String.format(StringUtils.getString(R.string.app_str_app_upgrading_success), deviceByDeviceId.getName()));
                            PushDataHelper.refreshSpUpgrade(true, 1);
                            return;
                        }
                        if (i3 == 5) {
                            SmartToast.showCenterShort(String.format(StringUtils.getString(R.string.app_str_app_firmware_upgrading_fail), deviceByDeviceId.getName()));
                            PushDataHelper.refreshSpUpgrade(false, 0);
                            return;
                        } else if (i3 == 6) {
                            SmartToast.showCenterShort(String.format(StringUtils.getString(R.string.app_str_app_firmware_upgrading_success), deviceByDeviceId.getName()));
                            PushDataHelper.refreshSpUpgrade(true, 0);
                            return;
                        } else {
                            if (i3 != 7 || PushDataHelper.mcuFailDialog == null) {
                                return;
                            }
                            PushDataHelper.mcuFailDialog.doDismiss();
                            return;
                        }
                    }
                    SmartToast.showCenterShort(String.format(StringUtils.getString(R.string.app_str_child_mcu_upgrade_finish_tip), Integer.valueOf(arrayList2.size()), Integer.valueOf(arrayList.size())));
                    for (Activity activity : ActivityUtils.getActivityList()) {
                        if (activity instanceof ActChildMcuUpgrade) {
                            ((ActChildMcuUpgrade) activity).refreshDevice(asLong, arrayList, arrayList2, false);
                            return;
                        }
                    }
                }
                for (Activity activity2 : ActivityUtils.getActivityList()) {
                    if (activity2 instanceof ActChildMcuUpgrade) {
                        ((ActChildMcuUpgrade) activity2).refresh();
                        return;
                    }
                }
            }
        }, 1500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void refreshSpUpgrade(boolean b2, int i) {
        for (Activity activity : ActivityUtils.getActivityList()) {
            if (activity instanceof ActSpUpgrade) {
                ((ActSpUpgrade) activity).refresh(b2, i);
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void showMcuFailDialog(final long panelId, int totalNum, final List<Long> ids, List<Long> successIds) {
        for (Activity activity : ActivityUtils.getActivityList()) {
            if (activity instanceof ActChildMcuUpgrade) {
                ((ActChildMcuUpgrade) activity).refreshDevice(panelId, ids, successIds, true);
                SmartToast.showCenterShort(String.format(StringUtils.getString(R.string.app_str_child_mcu_upgrade_finish_tip), Integer.valueOf(successIds.size()), Integer.valueOf(ids.size())));
                return;
            }
        }
        StringBuilder sb = new StringBuilder();
        Iterator<Long> it = ids.iterator();
        while (it.hasNext()) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().longValue());
            sb.append("<br>");
            sb.append(deviceByDeviceId.getName());
        }
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), StringUtils.getString(R.string.app_str_device_mcu_upgrade), String.format(StringUtils.getString(R.string.app_str_child_mcu_upgrade_fail_tip), Integer.valueOf(totalNum - ids.size()), Integer.valueOf(ids.size()), sb.toString())).setCancelButton(StringUtils.getString(R.string.i_know)).setOkButton(StringUtils.getString(R.string.app_str_child_mcu_upgrade_continue)).setCancelable(false).setCancelButton(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.push.PushDataHelper.4
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                return false;
            }
        }).setOkButton(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.push.PushDataHelper.3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                for (Activity activity2 : ActivityUtils.getActivityList()) {
                    if (activity2 instanceof ActChildMcuUpgrade) {
                        ((ActChildMcuUpgrade) activity2).send(panelId, ids);
                        return false;
                    }
                }
                ((ObservableSubscribeProxy) Injection.net().sendChildMcuUpgrade(panelId, ids).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((AppCompatActivity) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<Object>(this) { // from class: com.ltech.smarthome.push.PushDataHelper.3.1
                    @Override // io.reactivex.functions.Consumer
                    public void accept(Object o) throws Exception {
                        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), "", StringUtils.getString(R.string.app_str_child_mcu_upgrade)).setOkButton(StringUtils.getString(R.string.i_know)).setCancelable(false).setOkButton(new OnDialogButtonClickListener(this) { // from class: com.ltech.smarthome.push.PushDataHelper.3.1.1
                            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                            public boolean onClick(BaseDialog baseDialog2, View v2) {
                                return false;
                            }
                        });
                    }
                });
                return false;
            }
        }).setOnShowListener(new OnShowListener() { // from class: com.ltech.smarthome.push.PushDataHelper.2
            @Override // com.smart.dialog.interfaces.OnShowListener
            public void onShow(BaseDialog d2) {
                PushDataHelper.mcuFailDialog = d2;
            }
        });
    }

    private static void refreshSonos(HashMap<String, Object> mapData) {
        for (Activity activity : ActivityUtils.getActivityList()) {
            if (activity instanceof ActSonosPlayControl) {
                ((ActSonosPlayControl) activity).refreshData(mapData);
                return;
            }
        }
    }

    private static void refreshSuperPanelReplace(HashMap<String, Object> mapData) {
        int parseInt = Integer.parseInt(String.valueOf(mapData.get(PushContentParamKey.SUPER_PANEL_REPLACE_TYPE)));
        for (Activity activity : ActivityUtils.getActivityList()) {
            if (activity instanceof ActReplace) {
                ((ActReplace) activity).setStatus(parseInt);
                return;
            }
        }
    }

    private static void refreshDeviceStatus(HashMap<String, Object> mapData) {
        long parseLong = Long.parseLong(String.valueOf(mapData.get(PushContentParamKey.DEVICE_ID)));
        String asString = mapData.containsKey(PushContentParamKey.REPORT_INSTRUCT) ? ((JsonPrimitive) mapData.get(PushContentParamKey.REPORT_INSTRUCT)).getAsString() : null;
        for (Activity activity : ActivityUtils.getActivityList()) {
            if (activity instanceof ActDoorSensor) {
                ((ActDoorSensor) activity).refreshData(parseLong);
                return;
            } else if (activity instanceof ActAc) {
                ((ActAc) activity).refreshData(parseLong, asString);
                return;
            }
        }
        if (asString != null) {
            MessageManager.getInstance().handleDeviceCloudData(parseLong, asString);
        }
    }

    private static void handelVoiceCallStatus(HashMap<String, Object> mapData) {
        if (mapData.containsKey(PushContentParamKey.VOICE_CALL_STATUS_TYPE)) {
            int parseInt = Integer.parseInt(String.valueOf(mapData.get(PushContentParamKey.VOICE_CALL_STATUS_TYPE)));
            long parseLong = Long.parseLong(String.valueOf(mapData.get(PushContentParamKey.USER_ID)));
            long parseLong2 = Long.parseLong(String.valueOf(mapData.get(PushContentParamKey.VOICE_CALL_GROUP_ID)));
            if (parseInt == 1) {
                VoiceCallManager.getInstance().leave();
            } else if (parseInt == 2) {
                VoiceCallManager.getInstance().userLeave(parseLong2, parseLong);
            } else if (parseInt == 3) {
                VoiceCallManager.getInstance().userLeave(parseLong2, parseLong);
            }
        }
    }

    private static void handelVoiceCall(HashMap<String, Object> mapData) {
        if (mapData.containsKey(PushContentParamKey.VOICE_CALL_GROUP) && (mapData.get(PushContentParamKey.VOICE_CALL_GROUP) instanceof JsonPrimitive)) {
            try {
                JSONObject jSONObject = new JSONObject(((JsonPrimitive) mapData.get(PushContentParamKey.VOICE_CALL_GROUP)).getAsJsonPrimitive().getAsString());
                if (jSONObject.has(PushContentParamKey.VOICE_CALL_GROUP_ID)) {
                    VoiceCallManager.getInstance().inviteGroupChat(jSONObject.getLong(PushContentParamKey.VOICE_CALL_GROUP_ID));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void handleDeviceChange(HashMap<String, Object> mapData) {
        long parseLong = Long.parseLong(String.valueOf(mapData.get(PushContentParamKey.PLACE_ID)));
        if (parseLong == Injection.repo().home().getSelectPlace().getValue().getPlaceId()) {
            Injection.limiter().reset(Injection.keyCreator().placeInfoKey(parseLong));
            Injection.limiter().reset(Injection.keyCreator().placeListKey());
            refreshFtRomeData();
        }
    }

    private static void handleGroupChange(HashMap<String, Object> mapData) {
        if (Long.valueOf(String.valueOf(mapData.get(PushContentParamKey.PLACE_ID))).longValue() == Injection.repo().home().getSelectPlace().getValue().getPlaceId()) {
            SharedPreferenceUtil.edit().keepShared(Constants.GROUP_CHANGED, true);
            EventBusUtils.post(new LiveBusHelper(9));
        }
    }

    private static void handleSceneChange(HashMap<String, Object> mapData) {
        long longValue = Long.valueOf(String.valueOf(mapData.get(PushContentParamKey.PLACE_ID))).longValue();
        if (longValue == Injection.repo().home().getSelectPlace().getValue().getPlaceId()) {
            Injection.limiter().reset(Injection.keyCreator().sceneKey(longValue));
            refreshFtSceneData();
        }
    }

    private static void handleAutomationChange(HashMap<String, Object> mapData) {
        long longValue = Long.valueOf(String.valueOf(mapData.get(PushContentParamKey.PLACE_ID))).longValue();
        if (longValue == Injection.repo().home().getSelectPlace().getValue().getPlaceId()) {
            Injection.limiter().reset(Injection.keyCreator().automationListKey(longValue));
            refreshFtAutomationData();
        }
    }

    private static void handleDeviceStatusData(HashMap<String, Object> mapData) {
        LHomeLog.i(PushDataHelper.class, "device state--->" + mapData.get("state"));
        LHomeLog.i(PushDataHelper.class, "device deviceid--->" + mapData.get(PushContentParamKey.DEVICE_ID));
        int intValue = Integer.valueOf(String.valueOf(mapData.get("state"))).intValue();
        long longValue = Long.valueOf(String.valueOf(mapData.get(PushContentParamKey.DEVICE_ID))).longValue();
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(longValue);
        if (deviceByDeviceId != null) {
            deviceByDeviceId.setOnlineFlag(intValue);
            Injection.repo().device().saveDevice(deviceByDeviceId);
            refreshGatewayData(deviceByDeviceId);
            if (deviceByDeviceId.getProductId().equals(ProductId.ID_MESH_GATEWAY)) {
                for (Device device : Injection.repo().device().getSubDevice(Injection.repo().home().getSelectPlace().getValue().getPlaceId(), longValue)) {
                    device.setOnlineFlag(intValue);
                    Injection.repo().device().saveDevice(device);
                    refreshGatewayData(device);
                }
            }
        }
    }

    public static void handlePushData(String responseData) {
        HashMap<String, Object> contentData = getContentData(responseData);
        int parseInt = Integer.parseInt(String.valueOf(contentData.get(PushContentParamKey.MESSAGE_TYPE)));
        if (parseInt != 17) {
            switch (parseInt) {
                case 8:
                    showReceiveInvitationDialog(contentData);
                    break;
                case 9:
                    showReceiveMemberJoinDialog(contentData);
                    break;
                case 10:
                case 11:
                    showManagerResponseDialog(contentData);
                    break;
            }
            return;
        }
        SharedPreferenceUtil.edit().keepShared(Constants.GROUP_CHANGED, true);
    }

    public static HashMap<String, Object> getContentData(CPushMessage message) {
        return (HashMap) getBuildGson().fromJson(message.getContent(), HashMap.class);
    }

    public static HashMap<String, Object> getContentData(String json) {
        return (HashMap) getBuildGson().fromJson((String) ((HashMap) GsonUtils.getGson().fromJson(json, GsonUtils.getType(HashMap.class, String.class, String.class))).get("content"), HashMap.class);
    }

    public static void showLoadingDialog(String content) {
        WaitDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), content);
    }

    public static void dismissLoadingDialog() {
        WaitDialog.dismiss(500);
    }

    public static void refreshActData() {
        if (ActivityUtils.getTopActivity() instanceof VMActivity) {
            ((VMActivity) ActivityUtils.getTopActivity()).refreshData();
        }
    }

    private static void refreshHomeActData() {
        for (Activity activity : ActivityUtils.getActivityList()) {
            if (activity instanceof ActHome) {
                ((ActHome) activity).refreshData();
            }
        }
    }

    private static void refreshFtData() {
        FragmentManager supportFragmentManager = ((AppCompatActivity) ActivityUtils.getTopActivity()).getSupportFragmentManager();
        if (FragmentUtils.getTop(supportFragmentManager) instanceof VMFragment) {
            ((VMFragment) FragmentUtils.getTop(supportFragmentManager)).refreshData();
        }
    }

    private static void refreshFtRomeData() {
        SharedPreferenceUtil.edit().keepShared(Constants.GROUP_CHANGED, true);
        SharedPreferenceUtil.edit().keepShared(Constants.DEVICE_CHANGED, true);
        EventBusUtils.post(new LiveBusHelper(9));
    }

    private static void refreshFtSceneData() {
        for (Fragment fragment : FragmentUtils.getFragments(((AppCompatActivity) ActivityUtils.getTopActivity()).getSupportFragmentManager())) {
            if (fragment instanceof FtIntelligence) {
                for (Fragment fragment2 : FragmentUtils.getFragments(fragment.getChildFragmentManager())) {
                    if (fragment2 instanceof FtCloudScene) {
                        ((FtCloudScene) fragment2).refreshData();
                    }
                    if (fragment2 instanceof FtScene) {
                        ((FtScene) fragment2).refreshData();
                    }
                }
            }
        }
    }

    private static void refreshGatewayData(Device device) {
        for (Fragment fragment : FragmentUtils.getFragments(((AppCompatActivity) ActivityUtils.getTopActivity()).getSupportFragmentManager())) {
            if (fragment instanceof FtRoom) {
                for (Fragment fragment2 : FragmentUtils.getFragments(fragment.getChildFragmentManager())) {
                    if (fragment2 instanceof FtDevice2) {
                        ((FtDevice2) fragment2).refreshGatewayData(device);
                    }
                }
            }
        }
    }

    private static void refreshFtAutomationData() {
        for (Fragment fragment : FragmentUtils.getFragments(((AppCompatActivity) ActivityUtils.getTopActivity()).getSupportFragmentManager())) {
            if (fragment instanceof FtIntelligence) {
                for (Fragment fragment2 : FragmentUtils.getFragments(fragment.getChildFragmentManager())) {
                    if (fragment2 instanceof FtAutomation) {
                        ((FtAutomation) fragment2).refreshData();
                    }
                }
            }
        }
    }

    private static void showManagerResponseDialog(HashMap<String, Object> mapData) {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), ActivityUtils.getTopActivity().getString(R.string.tips), getValueString(mapData.get("body"))).setCancelable(false).setOkButton(ActivityUtils.getTopActivity().getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.push.PushDataHelper$$ExternalSyntheticLambda6
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return PushDataHelper.lambda$showManagerResponseDialog$0(baseDialog, view);
            }
        });
    }

    static /* synthetic */ boolean lambda$showManagerResponseDialog$0(BaseDialog baseDialog, View view) {
        Injection.limiter().reset(Injection.keyCreator().placeListKey());
        refreshActData();
        return false;
    }

    private static void showReceiveMemberJoinDialog(final HashMap<String, Object> mapData) {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), ActivityUtils.getTopActivity().getString(R.string.tips), getValueString(mapData.get("body"))).setCancelable(false).setOkButton(ActivityUtils.getTopActivity().getString(R.string.app_str_accept), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.push.PushDataHelper$$ExternalSyntheticLambda11
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return PushDataHelper.lambda$showReceiveMemberJoinDialog$2(mapData, baseDialog, view);
            }
        }).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.app_str_refuse), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.push.PushDataHelper$$ExternalSyntheticLambda12
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return PushDataHelper.lambda$showReceiveMemberJoinDialog$4(mapData, baseDialog, view);
            }
        });
    }

    static /* synthetic */ boolean lambda$showReceiveMemberJoinDialog$2(HashMap hashMap, BaseDialog baseDialog, View view) {
        ((ObservableSubscribeProxy) Injection.net().agreeJoinPlace(new AgreePlaceUserRequest(Long.parseLong(getValueString(hashMap.get(PushContentParamKey.MESSAGE_ID))))).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((AppCompatActivity) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.push.PushDataHelper$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PushDataHelper.lambda$showReceiveMemberJoinDialog$1(obj);
            }
        }, new SmartErrorComsumer());
        return false;
    }

    static /* synthetic */ boolean lambda$showReceiveMemberJoinDialog$4(HashMap hashMap, BaseDialog baseDialog, View view) {
        ((ObservableSubscribeProxy) Injection.net().refuseJoinPlace(new RefusePlaceUserRequest(Long.parseLong(getValueString(hashMap.get(PushContentParamKey.MESSAGE_ID))))).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((AppCompatActivity) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.push.PushDataHelper$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PushDataHelper.lambda$showReceiveMemberJoinDialog$3(obj);
            }
        }, new SmartErrorComsumer());
        return false;
    }

    private static void showSecurityDialog(HashMap<String, Object> mapData) {
        String string;
        switch (Integer.valueOf(getValueString(mapData.get(PushContentParamKey.SECURITY_TYPE))).intValue()) {
            case 1:
                string = ActivityUtils.getTopActivity().getString(R.string.security_push_warning_1);
                break;
            case 2:
                string = ActivityUtils.getTopActivity().getString(R.string.security_push_warning_2);
                break;
            case 3:
                string = ActivityUtils.getTopActivity().getString(R.string.security_push_warning_3);
                break;
            case 4:
                string = ActivityUtils.getTopActivity().getString(R.string.security_push_warning_4);
                break;
            case 5:
                string = ActivityUtils.getTopActivity().getString(R.string.security_push_warning_5);
                break;
            case 6:
                string = ActivityUtils.getTopActivity().getString(R.string.security_push_warning_6);
                break;
            case 7:
                string = ActivityUtils.getTopActivity().getString(R.string.security_push_warning_7);
                break;
            case 8:
                string = ActivityUtils.getTopActivity().getString(R.string.security_push_warning_8);
                break;
            case 9:
                string = ActivityUtils.getTopActivity().getString(R.string.security_push_warning_9);
                break;
            default:
                string = "";
                break;
        }
        showSecurityDialog(getValueString(mapData.get("body")).replace("\\", ""), string);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void showSecurityDialog(final String title, final String message) {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), title, message).setCancelable(false).setOkButton(ActivityUtils.getTopActivity().getString(R.string.security_reminder_later), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.push.PushDataHelper$$ExternalSyntheticLambda2
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return PushDataHelper.lambda$showSecurityDialog$6(title, message, baseDialog, view);
            }
        }).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.security_ignore), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.push.PushDataHelper$$ExternalSyntheticLambda3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return PushDataHelper.lambda$showSecurityDialog$7(baseDialog, view);
            }
        });
    }

    static /* synthetic */ boolean lambda$showSecurityDialog$6(final String str, final String str2, BaseDialog baseDialog, View view) {
        new Handler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.push.PushDataHelper$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                PushDataHelper.showSecurityDialog(str, str2);
            }
        }, 30000L);
        return false;
    }

    private static void showDeleteFamilyMessageDialog(HashMap<String, Object> mapData) {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), ActivityUtils.getTopActivity().getString(R.string.tips), getValueString(mapData.get("body"))).setCancelable(false).setOkButton(ActivityUtils.getTopActivity().getString(R.string.ok));
    }

    private static void showReceiveInvitationDialog(final HashMap<String, Object> data) {
        final SmartErrorComsumer smartErrorComsumer = new SmartErrorComsumer();
        smartErrorComsumer.setCallBackMsg(new SmartErrorComsumer.CallBackMsg() { // from class: com.ltech.smarthome.push.PushDataHelper$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.net.SmartErrorComsumer.CallBackMsg
            public final void handleMsg(String str) {
                PushDataHelper.lambda$showReceiveInvitationDialog$8(str);
            }
        });
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), ActivityUtils.getTopActivity().getString(R.string.tips), getValueString(data.get("body"))).setCancelable(false).setOkButton(ActivityUtils.getTopActivity().getString(R.string.app_str_accept), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.push.PushDataHelper$$ExternalSyntheticLambda9
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return PushDataHelper.lambda$showReceiveInvitationDialog$10(data, smartErrorComsumer, baseDialog, view);
            }
        }).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.app_str_refuse), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.push.PushDataHelper$$ExternalSyntheticLambda10
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return PushDataHelper.lambda$showReceiveInvitationDialog$12(data, baseDialog, view);
            }
        });
    }

    static /* synthetic */ void lambda$showReceiveInvitationDialog$8(String str) {
        Injection.limiter().reset(Injection.keyCreator().placeListKey());
        refreshActData();
    }

    static /* synthetic */ boolean lambda$showReceiveInvitationDialog$10(HashMap hashMap, SmartErrorComsumer smartErrorComsumer, BaseDialog baseDialog, View view) {
        ((ObservableSubscribeProxy) Injection.net().agreeInvitation(new AgreeInvitationRequest(Long.parseLong(getValueString(hashMap.get(PushContentParamKey.MESSAGE_ID))))).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((AppCompatActivity) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.push.PushDataHelper$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PushDataHelper.lambda$showReceiveInvitationDialog$9(obj);
            }
        }, smartErrorComsumer);
        return false;
    }

    static /* synthetic */ void lambda$showReceiveInvitationDialog$9(Object obj) throws Exception {
        Injection.limiter().reset(Injection.keyCreator().placeListKey());
        refreshActData();
    }

    static /* synthetic */ boolean lambda$showReceiveInvitationDialog$12(HashMap hashMap, BaseDialog baseDialog, View view) {
        ((ObservableSubscribeProxy) Injection.net().refuseInvitation(new RefuseInvitationRequest(Long.parseLong(getValueString(hashMap.get(PushContentParamKey.MESSAGE_ID))))).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((AppCompatActivity) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.push.PushDataHelper$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PushDataHelper.lambda$showReceiveInvitationDialog$11(obj);
            }
        }, new SmartErrorComsumer());
        return false;
    }

    /* renamed from: com.ltech.smarthome.push.PushDataHelper$5, reason: invalid class name */
    class AnonymousClass5 implements Runnable {
        static /* synthetic */ boolean lambda$run$0(BaseDialog baseDialog, View view) {
            return false;
        }

        AnonymousClass5() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (ActivityUtils.getTopActivity() != null) {
                MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), ActivityUtils.getTopActivity().getString(R.string.tips), ActivityUtils.getTopActivity().getString(R.string.app_str_login_other_device)).setCancelable(false).setOkButton(ActivityUtils.getTopActivity().getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.push.PushDataHelper$5$$ExternalSyntheticLambda0
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        return PushDataHelper.AnonymousClass5.lambda$run$0(baseDialog, view);
                    }
                });
            }
        }
    }

    public static void showLogOutMessageDialog(HashMap<String, Object> data) {
        new Handler().postDelayed(new AnonymousClass5(), 1000L);
    }

    public static void showNoHandleMessageDialog(HashMap<String, Object> data) {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), ActivityUtils.getTopActivity().getString(R.string.tips), getValueString(data.get("body"))).setCancelable(false).setOkButton(ActivityUtils.getTopActivity().getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.push.PushDataHelper$$ExternalSyntheticLambda1
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return PushDataHelper.lambda$showNoHandleMessageDialog$13(baseDialog, view);
            }
        });
    }

    static /* synthetic */ boolean lambda$showNoHandleMessageDialog$13(BaseDialog baseDialog, View view) {
        Injection.limiter().reset(Injection.keyCreator().placeListKey());
        refreshHomeActData();
        refreshActData();
        return false;
    }

    public static void getNoHandleData() {
        if (ActivityUtils.getTopActivity() == null) {
            return;
        }
        ((ObservableSubscribeProxy) Injection.net().listNoHandleMessage().delaySubscription(1000L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((AppCompatActivity) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.push.PushDataHelper$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PushDataHelper.lambda$getNoHandleData$14((List) obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.push.PushDataHelper.6
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                LHomeLog.i(getClass(), throwable.getMessage());
            }
        });
    }

    static /* synthetic */ void lambda$getNoHandleData$14(List list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            LHomeLog.d(PushDataHelper.class, "response ==>" + GsonUtils.getGson().toJson(list.get(i)));
            handlePushData(GsonUtils.getGson().toJson(list.get(i)));
        }
    }

    public static Gson getBuildGson() {
        return new GsonBuilder().registerTypeAdapter(HashMap.class, new JsonDeserializer<HashMap>() { // from class: com.ltech.smarthome.push.PushDataHelper.7
            @Override // com.google.gson.JsonDeserializer
            public HashMap deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                HashMap hashMap = new HashMap();
                for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
                    hashMap.put(entry.getKey(), entry.getValue());
                }
                return hashMap;
            }
        }).create();
    }

    public static String getValueString(Object object) {
        String valueOf = String.valueOf(object);
        LHomeLog.d(PushDataHelper.class, "before handle result : " + valueOf);
        if (valueOf.startsWith("\"") && valueOf.endsWith("\"")) {
            valueOf = valueOf.substring(1, valueOf.length() - 1);
        }
        LHomeLog.d(PushDataHelper.class, "before handle result : " + valueOf);
        return valueOf;
    }
}