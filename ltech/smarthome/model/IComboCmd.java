package com.ltech.smarthome.model;

import android.content.Context;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.key.KeyInfo;
import com.ltech.smarthome.singleton.ComboCmdHelper;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.param.BleNetworkCmdParam;
import java.util.List;

/* loaded from: classes4.dex */
public interface IComboCmd {
    List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> advanceMode(Object object, int pos);

    List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> generalMode(Object object, int pos);

    List<ComboCmdHelper.Action> getCtKnobActionList();

    List<ComboCmdHelper.Action> getCtLongClickActionList();

    List<ComboCmdHelper.Action> getCtShortClickActionList();

    List<ComboCmdHelper.Action> getDaliCtLongClickActionList();

    List<ComboCmdHelper.Action> getDaliDimLongClickActionList();

    List<ComboCmdHelper.Action> getDaliRgbLongClickActionList();

    List<ComboCmdHelper.Action> getDaliRgbcwLongClickActionList();

    List<ComboCmdHelper.Action> getDaliRgbwLongClickActionList();

    List<BleNetworkCmdParam.ComboCmdAction> getDefaultComboCmdActions(Object relateObject);

    List<BleNetworkCmdParam.ComboCmdAction> getDefaultSelfActions(Object relateObject);

    List<ComboCmdHelper.Action> getDimKnobActionList();

    List<ComboCmdHelper.Action> getDimLongClickActionList();

    List<ComboCmdHelper.Action> getDimShortClickActionList();

    List<ComboCmdHelper.Action> getDreamCurtainKnobActionList();

    List<ComboCmdHelper.Action> getDreamCurtainLongClickActionList();

    KeyInfo getKeyKnob(int zone);

    KeyInfo getKeyLong(int zone);

    KeyInfo getKeyShort(int zone);

    List<KeyInfo> getKeysByZone(int zone);

    List<ComboCmdHelper.Action> getMusicPlayerKnobActionList();

    String getRelateName(int zone);

    Object getRelateObject(int zone);

    List<ComboCmdHelper.Action> getRgbKnobActionList();

    List<ComboCmdHelper.Action> getRgbLongClickActionList();

    List<ComboCmdHelper.Action> getRgbShortClickActionList();

    List<ComboCmdHelper.Action> getRgbcwKnobActionList();

    List<ComboCmdHelper.Action> getRgbcwLongClickActionList();

    List<ComboCmdHelper.Action> getRgbcwShortClickActionList();

    List<ComboCmdHelper.Action> getRgbwKnobActionList();

    List<ComboCmdHelper.Action> getRgbwLongClickActionList();

    List<ComboCmdHelper.Action> getRgbwShortClickActionList();

    List<BleNetworkCmdParam.ComboCmdAction> getSceneDefaultComboCmdActions(Object relateObject, int sceneNum);

    List<ComboCmdHelper.Action> getSpiKnobActionList();

    List<ComboCmdHelper.Action> getSpiLongClickActionList();

    List<ComboCmdHelper.Action> getSpiShortClickActionList();

    String getSubscribeCmd(Object relateObject, BleNetworkCmdParam.ComboCmdAction action, int i);

    boolean isAllBrtCmd(Object obj, String instruct);

    boolean isShowKRange();

    List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> off(Object object);

    List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> on(Object object);

    List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> onOff(Object object);

    String parseCmdForAction(Object o, String wholeDataExtra);

    void selectActionByIndex(int num, Object relateObject, IAction<List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction>> iAction);

    void selectAdvanceMode(int num);

    void selectColor(int red, int green, int blue, int rgbBrt, int wy, int wyBrt);

    void selectGeneralMode(int num);

    void selectSpiMode(int num, boolean listPlay);

    void selectTheme(int num);

    void setKeyData(int zone, List<KeyInfo> list);

    void setKeyData(List<KeyInfo> list);

    void setKeyKnob(int zone, KeyInfo info);

    void setKeyLong(int zone, KeyInfo info);

    void setKeySelf(int zone, KeyInfo info);

    void setKeyShort(int zone, KeyInfo info);

    void subscribeCmd(Context context, Object o, Object relateObject, List<BleNetworkCmdParam.ComboCmdAction> actions, int zone, IAction<ResponseMsg> iAction);

    void subscribeKnobCmd(Context context, Object o, Object relateObject, List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> childActions, int zone, IAction<ResponseMsg> iAction);

    void subscribeLongClickCmd(Context context, Object o, Object relateObject, List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> childActions, int zone, IAction<ResponseMsg> iAction);

    void subscribeShortClickCmd(Context context, Object o, Object relateObject, List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> childActions, int zone, IAction<ResponseMsg> iAction);

    List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> theme(Object object, int pos);

    void unSubscribeAllCmd(Context context, Object o, int zone, IAction<ResponseMsg> iAction);

    void unSubscribeKnobCmd(Context context, Object o, int zone, IAction<ResponseMsg> iAction);

    void unSubscribeLongCmd(Context context, Object o, int zone, IAction<ResponseMsg> iAction);

    void unSubscribeShortCmd(Context context, Object o, int zone, IAction<ResponseMsg> iAction);
}