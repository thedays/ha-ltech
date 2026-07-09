package com.ltech.smarthome.singleton;

import android.content.Context;
import android.text.TextUtils;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.ltech.smarthome.MyApplication;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.IComboCmd;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.DryContactBleParam;
import com.ltech.smarthome.model.key.KeyInfo;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.product.ProductInfo;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.device.spicontroller.ActSelectSpiForAction;
import com.ltech.smarthome.ui.scene.ActCtSelectColor;
import com.ltech.smarthome.ui.scene.ActDimSelectColor;
import com.ltech.smarthome.ui.scene.ActSelectColor;
import com.ltech.smarthome.ui.scene.ActSelectDefaultMode;
import com.ltech.smarthome.ui.scene.ActSelectDiyMode;
import com.ltech.smarthome.ui.scene.ActSelectThemeMode;
import com.ltech.smarthome.utils.BitUtils;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.ResponseMsg;
import com.smart.message.base.BaseCmd;
import com.smart.message.base.BaseCmdParam;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.param.BleNetworkCmdParam;
import com.smart.product_agreement.param.DeviceCmdParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class ComboCmdHelper implements IComboCmd {
    private int indexNum;
    private Map<Integer, Map<Integer, KeyInfo>> keyMap;
    private IAction<List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction>> mIAction;
    private Object targetRelateObject;

    public static IComboCmd getInstance() {
        return (IComboCmd) Singleton.getSingleton(ComboCmdHelper.class);
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> onOff(Object object) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(on(object));
        arrayList.addAll(off(object));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> on(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getLightCmdAssistant(object, new int[0]).sendOnOff(true)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> off(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getLightCmdAssistant(object, new int[0]).sendOnOff(false)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> theme(Object object, int pos) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getModeCmdAssistant(object, new int[0]).playDefaultMode(pos)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> generalMode(Object object, int pos) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getModeCmdAssistant(object, new int[0]).playGeneralMode(pos, 1, false)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> advanceMode(Object object, int pos) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getModeCmdAssistant(object, new int[0]).playAdvancedMode(pos, 1, false)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> colorValue(Object object, int red, int green, int blue, int rgbBrt, int wy, int wyBrt) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getLightCmdAssistant(object, new int[0]).sentColor(red, green, blue, rgbBrt, wy, wyBrt)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> colorAndOff(Object object, int red, int green, int blue, int rgbBrt, int wy, int wyBrt) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(colorValue(object, red, green, blue, rgbBrt, wy, wyBrt));
        arrayList.addAll(off(object));
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> allBrt(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getLightCmdAssistant(object, new int[0]).sendTotalBrtToGq(0)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> allDaliBrt(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getLightCmdAssistant(object, new int[0]).sendDaliTotalBrtToGq(0)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> dimBrt(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getLightCmdAssistant(object, new int[0]).sendDimBrtToGq(0)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> ctBrt(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getLightCmdAssistant(object, new int[0]).sendCtBrtToGq(0)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> rgbBrt(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getLightCmdAssistant(object, new int[0]).sendRgbBrtToGq(0)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> cw(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getLightCmdAssistant(object, new int[0]).sendCWToGq(0)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> rgb(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getLightCmdAssistant(object, new int[0]).sendRgbToGq(0)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> w(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getLightCmdAssistant(object, new int[0]).sendWHas1to9ToGq(0)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> cwBrt(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getLightCmdAssistant(object, new int[0]).sendWyBrtToGq(0)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> localScene(Object object, int num) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(65025);
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getSceneCmdAssistant(null, new int[0]).executeLocalScene(num)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> daliScene(Object object, int num) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        if (object instanceof Scene) {
            comboCmdChildAction.setAddress(getAddress(Injection.repo().device().getDeviceByDeviceId(((Scene) object).getMacdeviceid())));
        } else {
            comboCmdChildAction.setAddress(getAddress(object));
        }
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getSceneCmdAssistant(object, new int[0]).executeDaliScene(num)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> spiMode(Object object, int num, boolean listPlay) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getModeCmdAssistant(object, new int[0]).playSpiModeWithoutParam(num, listPlay)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> trigOpen(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getDeviceAssistant(object, new int[0]).setOpenCloseVar(1)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> trigStop(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getDeviceAssistant(object, new int[0]).setOpenCloseVar(2)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> trigClose(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getDeviceAssistant(object, new int[0]).setOpenCloseVar(4)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> trigOpenClose(Object object) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(trigOpen(object));
        arrayList.addAll(trigClose(object));
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> trigDreamOpen(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getDeviceAssistant(object, new int[0]).setOpenCloseVar(1)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> trigDreamStop(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getDeviceAssistant(object, new int[0]).setOpenCloseVar(3)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> trigDreamClose(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getDeviceAssistant(object, new int[0]).setOpenCloseVar(2)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> trigDreamRotateLeft(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getDeviceAssistant(object, new int[0]).setOpenCloseVar(4)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> trigDreamRotateRight(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getDeviceAssistant(object, new int[0]).setOpenCloseVar(8)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> trigDreamOpenClose(Object object) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(trigDreamOpen(object));
        arrayList.addAll(trigDreamClose(object));
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> trigDreamRotateLeftRight(Object object) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(trigDreamRotateLeft(object));
        arrayList.addAll(trigDreamRotateRight(object));
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> bleCurtainStop(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setCmdType(3);
        comboCmdChildAction.setCmd(getConvertCmd(deviceCmdParam));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> bleCurtainPercent(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setCmdType(6);
        comboCmdChildAction.setCmd(getConvertCmd(deviceCmdParam));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> musicPlayerPlay(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getBleMusicPlayerAssistant(object, new int[0]).play()));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> musicPlayerStop(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getBleMusicPlayerAssistant(object, new int[0]).stop()));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> musicPlayerStopPlay(Object object) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(musicPlayerStop(object));
        arrayList.addAll(musicPlayerPlay(object));
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> musicPlayerVolume(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getBleMusicPlayerAssistant(object, new int[0]).setVolume(0)));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> musicPlayerLast(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getBleMusicPlayerAssistant(object, new int[0]).last()));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> musicPlayerNext(Object object) {
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        comboCmdChildAction.setAddress(getAddress(object));
        comboCmdChildAction.setCmd(getConvertCmd(CmdAssistant.getBleMusicPlayerAssistant(object, new int[0]).next()));
        arrayList.add(comboCmdChildAction);
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> musicPlayerLastNext(Object object) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(musicPlayerLast(object));
        arrayList.addAll(musicPlayerNext(object));
        return arrayList;
    }

    public List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> onlySelected() {
        return new ArrayList();
    }

    public BleNetworkCmdParam.ComboCmdAction coverShortClickAction(Object relateObject, List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> childActions) {
        return coverComboAction(relateObject, childActions, 2);
    }

    public BleNetworkCmdParam.ComboCmdAction coverLongClickAction(Object relateObject, List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> childActions) {
        return coverComboAction(relateObject, childActions, 4);
    }

    public BleNetworkCmdParam.ComboCmdAction coverKnobAction(Object relateObject, List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> childActions) {
        return coverComboAction(relateObject, childActions, 3);
    }

    public BleNetworkCmdParam.ComboCmdAction coverSelfAction(Object object) {
        DryContactBleParam dryContactBleParam;
        ArrayList arrayList = new ArrayList();
        BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction comboCmdChildAction = new BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction();
        if (object instanceof Scene) {
            comboCmdChildAction.setAddress(getAddress(Injection.repo().device().getDeviceByDeviceId(((Scene) object).getMacdeviceid())));
        } else {
            comboCmdChildAction.setAddress(getAddress(object));
            if (object instanceof Device) {
                Device device = (Device) object;
                ProductInfo productInfoByPid = ProductRepository.getProductInfoByPid(device.getProductId());
                if (productInfoByPid != null && ProductId.ID_BLE_DRY_CONTACT.equalsIgnoreCase(productInfoByPid.getProductId()) && (dryContactBleParam = (DryContactBleParam) device.getParam(DryContactBleParam.class)) != null) {
                    if (dryContactBleParam.getSubType() == 0) {
                        comboCmdChildAction.setAttr(257);
                    } else if (dryContactBleParam.getSubType() == 3) {
                        comboCmdChildAction.setAttr(260);
                    }
                }
            } else if (object instanceof Group) {
                int lightColorType = ProductRepository.getLightColorType(object);
                if (lightColorType == 12) {
                    comboCmdChildAction.setAttr(257);
                } else if (lightColorType == 16) {
                    comboCmdChildAction.setAttr(260);
                }
            }
        }
        arrayList.add(comboCmdChildAction);
        return coverComboAction(object, arrayList, 6);
    }

    public BleNetworkCmdParam.ComboCmdAction coverComboAction(Object relateObject, List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> childActions, int type) {
        BleNetworkCmdParam.ComboCmdAction comboCmdAction = new BleNetworkCmdParam.ComboCmdAction();
        comboCmdAction.setActionType(type);
        comboCmdAction.setDeviceType(getType(relateObject));
        comboCmdAction.setChildActions(childActions);
        return comboCmdAction;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<BleNetworkCmdParam.ComboCmdAction> getSceneDefaultComboCmdActions(Object o, int num) {
        ArrayList arrayList = new ArrayList();
        if (o instanceof Scene) {
            arrayList.add(coverShortClickAction(o, ((Scene) o).getSceneType() == 4 ? daliScene(o, num) : localScene(o, num)));
            return arrayList;
        }
        int lightColorType = ProductRepository.getLightColorType(o);
        if (ProductRepository.isDaliLightGroup(o)) {
            lightColorType = DaliProHelper.convertDaliType(o);
        }
        if (lightColorType == 1) {
            arrayList.add(coverShortClickAction(o, onlySelected()));
            arrayList.add(coverKnobAction(o, dimBrt(o)));
            arrayList.add(coverLongClickAction(o, dimBrt(o)));
            return arrayList;
        }
        if (lightColorType == 2) {
            arrayList.add(coverShortClickAction(o, onlySelected()));
            if (ProductRepository.isDaliLightGroup(o)) {
                arrayList.add(coverKnobAction(o, allDaliBrt(o)));
            } else {
                arrayList.add(coverKnobAction(o, ctBrt(o)));
            }
            arrayList.add(coverLongClickAction(o, cw(o)));
            return arrayList;
        }
        if (lightColorType != 3) {
            if (lightColorType != 4 && lightColorType != 5) {
                if (lightColorType == 12) {
                    arrayList.add(coverShortClickAction(o, onlySelected()));
                    arrayList.add(coverKnobAction(o, trigOpenClose(o)));
                    arrayList.add(coverLongClickAction(o, trigStop(o)));
                    return arrayList;
                }
                if (lightColorType == 14) {
                    arrayList.add(coverShortClickAction(o, onlySelected()));
                    arrayList.add(coverKnobAction(o, bleCurtainPercent(o)));
                    arrayList.add(coverLongClickAction(o, bleCurtainStop(o)));
                    return arrayList;
                }
                if (lightColorType != 20) {
                    if (lightColorType == 10007) {
                        arrayList.add(coverShortClickAction(o, onlySelected()));
                        arrayList.add(coverKnobAction(o, musicPlayerVolume(o)));
                        arrayList.add(coverLongClickAction(o, musicPlayerStopPlay(o)));
                        return arrayList;
                    }
                    if (lightColorType == 16) {
                        arrayList.add(coverShortClickAction(o, onlySelected()));
                        arrayList.add(coverLongClickAction(o, trigDreamRotateLeftRight(o)));
                        arrayList.add(coverKnobAction(o, trigDreamOpenClose(o)));
                        return arrayList;
                    }
                    if (lightColorType != 17) {
                        switch (lightColorType) {
                            case 101:
                                arrayList.add(coverShortClickAction(o, onlySelected()));
                                arrayList.add(coverKnobAction(o, allDaliBrt(o)));
                                arrayList.add(coverLongClickAction(o, allDaliBrt(o)));
                                break;
                            case 102:
                                arrayList.add(coverShortClickAction(o, onlySelected()));
                                arrayList.add(coverKnobAction(o, allDaliBrt(o)));
                                arrayList.add(coverLongClickAction(o, cw(o)));
                                break;
                            case 103:
                            case 104:
                            case 105:
                                arrayList.add(coverShortClickAction(o, onlySelected()));
                                arrayList.add(coverKnobAction(o, allDaliBrt(o)));
                                arrayList.add(coverLongClickAction(o, rgb(o)));
                                break;
                        }
                        return arrayList;
                    }
                }
            }
            arrayList.add(coverShortClickAction(o, onlySelected()));
            if (ProductRepository.isDaliLightGroup(o)) {
                arrayList.add(coverKnobAction(o, allDaliBrt(o)));
            } else {
                arrayList.add(coverKnobAction(o, allBrt(o)));
            }
            arrayList.add(coverLongClickAction(o, rgb(o)));
            return arrayList;
        }
        arrayList.add(coverShortClickAction(o, onlySelected()));
        if (ProductRepository.isDaliLightGroup(o)) {
            arrayList.add(coverKnobAction(o, allDaliBrt(o)));
        } else {
            arrayList.add(coverKnobAction(o, rgbBrt(o)));
        }
        arrayList.add(coverLongClickAction(o, rgb(o)));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<BleNetworkCmdParam.ComboCmdAction> getDefaultSelfActions(Object o) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(coverSelfAction(o));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<BleNetworkCmdParam.ComboCmdAction> getDefaultComboCmdActions(Object o) {
        return getSceneDefaultComboCmdActions(o, 0);
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public String getSubscribeCmd(Object o, BleNetworkCmdParam.ComboCmdAction comboCmdAction, int zone) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(comboCmdAction);
        return getConvertCmd(CmdAssistant.getSettingCmdAssistant(o, new int[0]).subscribeInSwitchPanelComboCmd(zone, arrayList));
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public void subscribeShortClickCmd(Context context, Object o, Object relateObject, List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> childActions, int zone, IAction<ResponseMsg> iAction) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(coverShortClickAction(relateObject, childActions));
        subscribeCmd(context, o, relateObject, arrayList, zone, iAction);
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public void subscribeLongClickCmd(Context context, Object o, Object relateObject, List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> childActions, int zone, IAction<ResponseMsg> iAction) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(coverLongClickAction(relateObject, childActions));
        subscribeCmd(context, o, relateObject, arrayList, zone, iAction);
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public void subscribeKnobCmd(Context context, Object o, Object relateObject, List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> childActions, int zone, IAction<ResponseMsg> iAction) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(coverKnobAction(relateObject, childActions));
        subscribeCmd(context, o, relateObject, arrayList, zone, iAction);
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public void subscribeCmd(Context context, final Object o, final Object relateObject, final List<BleNetworkCmdParam.ComboCmdAction> actions, final int zone, final IAction<ResponseMsg> iAction) {
        CmdAssistant.getSettingCmdAssistant(o, new int[0]).subscribeInSwitchPanelComboCmd(context, 1 << zone, actions, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.singleton.ComboCmdHelper.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                if (responseMsg != null && responseMsg.getStateCode() == 0) {
                    for (int i = 0; i < actions.size(); i++) {
                        BleNetworkCmdParam.ComboCmdAction comboCmdAction = (BleNetworkCmdParam.ComboCmdAction) actions.get(i);
                        KeyInfo keyInfo = new KeyInfo(relateObject);
                        keyInfo.setInstruction(ComboCmdHelper.getInstance().getSubscribeCmd(o, comboCmdAction, 1 << zone));
                        keyInfo.setZoneNum(zone + 1);
                        keyInfo.setActionType(comboCmdAction.getActionType());
                        if (comboCmdAction.getActionType() == 2) {
                            ComboCmdHelper.this.setKeyShort(zone + 1, keyInfo);
                        } else if (comboCmdAction.getActionType() == 3) {
                            ComboCmdHelper.this.setKeyKnob(zone + 1, keyInfo);
                        } else if (comboCmdAction.getActionType() == 4) {
                            ComboCmdHelper.this.setKeyLong(zone + 1, keyInfo);
                        } else if (comboCmdAction.getActionType() == 6) {
                            ComboCmdHelper.this.setKeyLong(zone + 1, keyInfo);
                        }
                    }
                }
                IAction iAction2 = iAction;
                if (iAction2 != null) {
                    iAction2.act(responseMsg);
                }
            }
        });
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public void unSubscribeShortCmd(Context context, Object o, int zone, IAction<ResponseMsg> iAction) {
        unSubscribeCmd(context, o, 2, zone, iAction);
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public void unSubscribeLongCmd(Context context, Object o, int zone, IAction<ResponseMsg> iAction) {
        unSubscribeCmd(context, o, 4, zone, iAction);
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public void unSubscribeKnobCmd(Context context, Object o, int zone, IAction<ResponseMsg> iAction) {
        unSubscribeCmd(context, o, 3, zone, iAction);
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public void unSubscribeAllCmd(Context context, Object o, int zone, IAction<ResponseMsg> iAction) {
        unSubscribeCmd(context, o, 255, zone, iAction);
    }

    private void unSubscribeCmd(Context context, Object o, final int action, final int zone, final IAction<ResponseMsg> iAction) {
        CmdAssistant.getSettingCmdAssistant(o, new int[0]).unsubscribeInSwitchPanelComboCmd(context, 1 << zone, action, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.singleton.ComboCmdHelper.2
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                if (responseMsg != null && responseMsg.getStateCode() == 0) {
                    int i = action;
                    if (i == 2) {
                        ComboCmdHelper.this.setKeyShort(zone + 1, null);
                    } else if (i == 3) {
                        ComboCmdHelper.this.setKeyKnob(zone + 1, null);
                    } else if (i == 4) {
                        ComboCmdHelper.this.setKeyLong(zone + 1, null);
                    } else if (i == 255) {
                        ComboCmdHelper.this.setKeyByZone(zone + 1, -1, null);
                    }
                }
                IAction iAction2 = iAction;
                if (iAction2 != null) {
                    iAction2.act(responseMsg);
                }
            }
        });
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public String parseCmdForAction(Object obj, String instruct) {
        if (instruct == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        String substring = instruct.substring(16);
        if (TextUtils.isEmpty(substring)) {
            return ActivityUtils.getTopActivity().getString(R.string.gqx_action_only_selected);
        }
        while (substring.length() > 10) {
            int parseInt = (Integer.parseInt(substring.substring(8, 10), 16) * 2) + 10;
            arrayList.add(substring.substring(10, parseInt));
            substring = substring.substring(parseInt);
        }
        for (int i = 0; i < arrayList.size(); i++) {
            if (i > 0) {
                sb.append("/");
            }
            sb.append(parseCmd(obj, (String) arrayList.get(i)));
        }
        String sb2 = sb.toString();
        if (sb2.equals(ActivityUtils.getTopActivity().getString(R.string.app_str_all_open) + "/" + ActivityUtils.getTopActivity().getString(R.string.app_str_all_close))) {
            return ActivityUtils.getTopActivity().getString(R.string.dry_curtain_action_1);
        }
        if (sb2.equals(ActivityUtils.getTopActivity().getString(R.string.turn_left) + "/" + ActivityUtils.getTopActivity().getString(R.string.turn_right))) {
            return ActivityUtils.getTopActivity().getString(R.string.dry_curtain_action_3);
        }
        if (sb2.equals(ActivityUtils.getTopActivity().getString(R.string.on_1) + "/" + ActivityUtils.getTopActivity().getString(R.string.off_1))) {
            return ActivityUtils.getTopActivity().getString(R.string.dim_new_action_3);
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(ActivityUtils.getTopActivity().getString(R.string.musicplayer_action_play_last));
        sb3.append("/");
        sb3.append(ActivityUtils.getTopActivity().getString(R.string.musicplayer_action_play_next));
        return sb2.equals(sb3.toString()) ? ActivityUtils.getTopActivity().getString(R.string.musicplayer_action_4) : sb2;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public boolean isAllBrtCmd(Object obj, String instruct) {
        if (instruct == null) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        String substring = instruct.substring(16);
        if (TextUtils.isEmpty(substring)) {
            return false;
        }
        while (substring.length() > 10) {
            int parseInt = (Integer.parseInt(substring.substring(8, 10), 16) * 2) + 10;
            arrayList.add(substring.substring(10, parseInt));
            substring = substring.substring(parseInt);
        }
        if (arrayList.size() == 1) {
            int lightColorType = ProductRepository.getLightColorType(obj);
            if (ProductRepository.isDaliLightGroup(obj)) {
                lightColorType = DaliProHelper.convertDaliType(obj);
            }
            int[] convertStringToArray = BitUtils.convertStringToArray((String) arrayList.get(0));
            if (convertStringToArray.length > 0) {
                int i = convertStringToArray[0];
                if (i == 209 && lightColorType == 1) {
                    return true;
                }
                if (i == 198) {
                    int i2 = convertStringToArray[3];
                    return i2 == 209 || i2 == 225 || (i2 == 214 && lightColorType == 2) || ((i2 == 216 && (lightColorType == 2 || lightColorType == 1)) || (i2 == 212 && lightColorType == 17));
                }
            }
        }
        return false;
    }

    private String parseCmd(Object obj, String cmd) {
        String[] generalModeName;
        String[] advancedModeName;
        int lightColorType = ProductRepository.getLightColorType(obj);
        if (ProductRepository.isDaliLightGroup(obj)) {
            lightColorType = DaliProHelper.convertDaliType(obj);
        }
        int[] convertStringToArray = BitUtils.convertStringToArray(cmd);
        if (convertStringToArray.length > 0) {
            int i = convertStringToArray[0];
            if (i == 204) {
                if (convertStringToArray[3] == 5) {
                    int i2 = convertStringToArray[4];
                    if (i2 == 1) {
                        return ActivityUtils.getTopActivity().getString(R.string.musicplayer_action_play);
                    }
                    if (i2 == 2) {
                        return ActivityUtils.getTopActivity().getString(R.string.musicplayer_action_stop);
                    }
                    if (i2 == 3) {
                        return ActivityUtils.getTopActivity().getString(R.string.musicplayer_action_play_last);
                    }
                    if (i2 == 4) {
                        return ActivityUtils.getTopActivity().getString(R.string.musicplayer_action_play_next);
                    }
                    if (i2 == 6) {
                        return ActivityUtils.getTopActivity().getString(R.string.musicplayer_action_3);
                    }
                    return "";
                }
                return "";
            }
            if (i == 212) {
                int i3 = convertStringToArray[3];
                if ((i3 == 2 || i3 == 9) && (obj instanceof Scene)) {
                    return ActivityUtils.getTopActivity().getString(R.string.app_execute) + "\"" + ((Scene) obj).getSceneName() + "\"";
                }
                return "";
            }
            if (i == 209) {
                if (lightColorType == 1) {
                    return ActivityUtils.getTopActivity().getString(R.string.brt);
                }
                if (lightColorType == 2) {
                    return ActivityUtils.getTopActivity().getString(R.string.type_ct);
                }
                return ActivityUtils.getTopActivity().getString(R.string.color);
            }
            if (i != 210) {
                switch (i) {
                    case 198:
                        int i4 = convertStringToArray[3];
                        if (i4 == 1) {
                            int i5 = convertStringToArray[4];
                            if (lightColorType != 16) {
                                if (i5 == 1) {
                                    return ActivityUtils.getTopActivity().getString(R.string.app_str_all_open);
                                }
                                if (i5 == 2) {
                                    return ActivityUtils.getTopActivity().getString(R.string.curtain_action_2);
                                }
                                if (i5 == 4) {
                                    return ActivityUtils.getTopActivity().getString(R.string.app_str_all_close);
                                }
                                return "";
                            }
                            if (i5 == 1) {
                                return ActivityUtils.getTopActivity().getString(R.string.app_str_all_open);
                            }
                            if (i5 == 2) {
                                return ActivityUtils.getTopActivity().getString(R.string.app_str_all_close);
                            }
                            if (i5 == 3) {
                                return ActivityUtils.getTopActivity().getString(R.string.curtain_action_2);
                            }
                            if (i5 == 4) {
                                return ActivityUtils.getTopActivity().getString(R.string.turn_left);
                            }
                            if (i5 == 8) {
                                return ActivityUtils.getTopActivity().getString(R.string.turn_right);
                            }
                            if (i5 == 12) {
                                return ActivityUtils.getTopActivity().getString(R.string.turn_stop);
                            }
                            return "";
                        }
                        if (i4 != 209) {
                            if (i4 == 216) {
                                if (lightColorType == 2 || lightColorType == 1) {
                                    return ActivityUtils.getTopActivity().getString(R.string.app_str_adjust_brt);
                                }
                                return ActivityUtils.getTopActivity().getString(R.string.app_str_adjust_w_value);
                            }
                            if (i4 != 225) {
                                switch (i4) {
                                    case 211:
                                        return ActivityUtils.getTopActivity().getString(R.string.app_str_adjust_rgb);
                                    case 212:
                                        if (lightColorType == 17) {
                                            return ActivityUtils.getTopActivity().getString(R.string.app_str_adjust_brt);
                                        }
                                        return ActivityUtils.getTopActivity().getString(R.string.app_str_adjust_rgb_brt);
                                    case 213:
                                        if (lightColorType == 2) {
                                            return ActivityUtils.getTopActivity().getString(R.string.app_str_adjust_ct);
                                        }
                                        return ActivityUtils.getTopActivity().getString(R.string.app_str_adjust_cw_value);
                                    case 214:
                                        if (lightColorType == 2) {
                                            return ActivityUtils.getTopActivity().getString(R.string.app_str_adjust_brt);
                                        }
                                        return ActivityUtils.getTopActivity().getString(R.string.app_str_adjust_cw_brt);
                                    default:
                                        return "";
                                }
                            }
                        }
                        if (lightColorType == 2 || lightColorType == 1) {
                            return ActivityUtils.getTopActivity().getString(R.string.app_str_adjust_brt);
                        }
                        return ActivityUtils.getTopActivity().getString(R.string.app_str_adjust_all_brt);
                    case 199:
                        if (Integer.parseInt(cmd.substring(6), 16) == 1) {
                            return ActivityUtils.getTopActivity().getString(R.string.on_1);
                        }
                        return ActivityUtils.getTopActivity().getString(R.string.off_1);
                    case 200:
                        int convertIntToBit = BitUtils.convertIntToBit((convertStringToArray[3] << 8) | convertStringToArray[4]);
                        String[] defaultModeName = NameRepository.getDefaultModeName(MyApplication.getContext());
                        if (convertIntToBit < defaultModeName.length) {
                            return defaultModeName[convertIntToBit];
                        }
                        return "";
                    case 201:
                        int i6 = convertStringToArray[3];
                        int convertIntToBit2 = BitUtils.convertIntToBit((convertStringToArray[6] << 8) | convertStringToArray[7]);
                        if (i6 == 7) {
                            if (convertStringToArray[5] == 1) {
                                return ActivityUtils.getTopActivity().getString(R.string.play_list);
                            }
                            convertIntToBit2 = BitUtils.convertIntToBit((convertStringToArray[10] << 8) | convertStringToArray[11]);
                            generalModeName = NameRepository.getSpiModeName(MyApplication.getContext());
                        } else {
                            generalModeName = NameRepository.getGeneralModeName(MyApplication.getContext());
                        }
                        if (convertIntToBit2 < generalModeName.length) {
                            return generalModeName[convertIntToBit2];
                        }
                        return "";
                    case 202:
                        int convertIntToBit3 = BitUtils.convertIntToBit((convertStringToArray[6] << 8) | convertStringToArray[7]);
                        if (lightColorType == 1) {
                            advancedModeName = NameRepository.getAdvancedDimModeName(MyApplication.getContext());
                        } else if (lightColorType == 2) {
                            advancedModeName = NameRepository.getAdvancedCtModeName(MyApplication.getContext());
                        } else {
                            advancedModeName = NameRepository.getAdvancedModeName(MyApplication.getContext());
                        }
                        if (convertIntToBit3 < advancedModeName.length) {
                            return advancedModeName[convertIntToBit3];
                        }
                        return "";
                    default:
                        return "";
                }
            }
            int i7 = convertStringToArray[3];
            if (i7 == 3) {
                return ActivityUtils.getTopActivity().getString(R.string.pause_curtain);
            }
            if (i7 == 6) {
                return ActivityUtils.getTopActivity().getString(R.string.curtain_action_3);
            }
            return "";
        }
        return "";
    }

    private List<Integer> getType(Object object) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        if (object instanceof Device) {
            Device device = (Device) object;
            ProductInfo productInfoByPid = ProductRepository.getProductInfoByPid(device.getProductId());
            if (productInfoByPid != null) {
                arrayList.add(Integer.valueOf(Integer.parseInt(productInfoByPid.getProductKey(), 16)));
            } else {
                arrayList.add(0);
            }
            if (productInfoByPid != null) {
                if (productInfoByPid.getProductKey().equals("02")) {
                    if (ProductId.ID_BLE_LIGHT_SPI.equals(device.getProductId())) {
                        i = Integer.parseInt(productInfoByPid.getSubProductKey(), 16);
                    } else if (device.getParam(BleParam.class) != null) {
                        i = ((BleParam) device.getParam(BleParam.class)).getDeviceType();
                    }
                } else {
                    i = Integer.parseInt(productInfoByPid.getSubProductKey(), 16);
                }
            }
            arrayList.add(Integer.valueOf(i));
            return arrayList;
        }
        if (object instanceof Group) {
            arrayList.add(0);
            arrayList.add(Integer.valueOf(Integer.parseInt(((Group) object).getControlType())));
            return arrayList;
        }
        arrayList.add(0);
        arrayList.add(0);
        return arrayList;
    }

    private int getAddress(Object object) {
        if (object instanceof Device) {
            return ((BleParam) ((Device) object).getParam(BleParam.class)).getUnicastAddress();
        }
        if (object instanceof Group) {
            return ((Group) object).getGroupAddress();
        }
        return 0;
    }

    private String getConvertCmd(BaseCmdParam cmdParam) {
        if (cmdParam != null) {
            BaseCmd convert2cmd = Injection.strategy().getCmdConvertStrategy(2).convert2cmd(cmdParam);
            return StringUtils.demToHexDouble(convert2cmd.getFunCode()) + StringUtils.byte2Str(convert2cmd.value(new Object[0]));
        }
        return "";
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getDimShortClickActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(1, R.string.dim_new_action_3, 1));
        arrayList.add(new Action(2, R.string.dim_new_action_4, 1));
        arrayList.add(new Action(3, R.string.dim_new_action_5, 1));
        arrayList.add(new Action(4, R.string.theme, 1));
        arrayList.add(new Action(5, R.string.advanced_mode, 1));
        arrayList.add(new Action(6, R.string.dim_new_action_1, 1));
        arrayList.add(new Action(7, R.string.dim_new_action_2, 1));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getDimKnobActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(35, R.string.dim_action_3, 1));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getDimLongClickActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(35, R.string.dim_action_3, 1));
        arrayList.add(new Action(38, R.string.gqx_action_only_selected, 1));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getDaliDimLongClickActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(35, R.string.dim_action_3, 1));
        arrayList.add(new Action(38, R.string.gqx_action_only_selected, 1));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getCtShortClickActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(1, R.string.ct_new_action_3, 2));
        arrayList.add(new Action(2, R.string.ct_new_action_4, 2));
        arrayList.add(new Action(3, R.string.ct_new_action_5, 2));
        arrayList.add(new Action(4, R.string.theme, 2));
        arrayList.add(new Action(5, R.string.advanced_mode, 2));
        arrayList.add(new Action(8, R.string.ct_new_action_1, 2));
        arrayList.add(new Action(9, R.string.ct_new_action_2, 2));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getCtKnobActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(37, R.string.dim_action_3, 2));
        arrayList.add(new Action(20, R.string.app_str_adjust_ct, 2));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getCtLongClickActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(37, R.string.dim_action_3, 2));
        arrayList.add(new Action(20, R.string.app_str_adjust_ct, 2));
        arrayList.add(new Action(38, R.string.gqx_action_only_selected, 1));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getDaliCtLongClickActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(37, R.string.dim_action_3, 2));
        arrayList.add(new Action(38, R.string.gqx_action_only_selected, 1));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getRgbShortClickActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(1, R.string.rgb_new_action_3, 3));
        arrayList.add(new Action(2, R.string.rgb_new_action_4, 3));
        arrayList.add(new Action(3, R.string.rgb_new_action_5, 3));
        arrayList.add(new Action(4, R.string.theme, 3));
        arrayList.add(new Action(18, R.string.general_mode, 3));
        arrayList.add(new Action(5, R.string.advanced_mode, 3));
        arrayList.add(new Action(16, R.string.rgb_new_action_1, 3));
        arrayList.add(new Action(17, R.string.rgb_new_action_2, 3));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getRgbKnobActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(19, R.string.dim_action_3, 3));
        arrayList.add(new Action(21, R.string.app_str_adjust_rgb, 3));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getRgbLongClickActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(19, R.string.dim_action_3, 2));
        arrayList.add(new Action(21, R.string.app_str_adjust_rgb, 2));
        arrayList.add(new Action(38, R.string.gqx_action_only_selected, 1));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getDaliRgbLongClickActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(21, R.string.app_str_adjust_rgb, 2));
        arrayList.add(new Action(38, R.string.gqx_action_only_selected, 1));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getRgbwShortClickActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(1, R.string.rgb_new_action_3, 3));
        arrayList.add(new Action(2, R.string.rgb_new_action_4, 3));
        arrayList.add(new Action(3, R.string.rgb_new_action_5, 3));
        arrayList.add(new Action(4, R.string.theme, 3));
        arrayList.add(new Action(18, R.string.general_mode, 3));
        arrayList.add(new Action(5, R.string.advanced_mode, 3));
        arrayList.add(new Action(16, R.string.rgb_new_action_1, 3));
        arrayList.add(new Action(17, R.string.rgb_new_action_2, 3));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getRgbwKnobActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(19, R.string.app_str_adjust_all_brt, 3));
        arrayList.add(new Action(22, R.string.app_str_adjust_rgb_brt, 3));
        arrayList.add(new Action(21, R.string.app_str_adjust_rgb, 3));
        arrayList.add(new Action(23, R.string.app_str_adjust_w_value, 3));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getRgbwLongClickActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(19, R.string.app_str_adjust_all_brt, 3));
        arrayList.add(new Action(22, R.string.app_str_adjust_rgb_brt, 3));
        arrayList.add(new Action(21, R.string.app_str_adjust_rgb, 3));
        arrayList.add(new Action(23, R.string.app_str_adjust_w_value, 3));
        arrayList.add(new Action(38, R.string.gqx_action_only_selected, 1));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getDaliRgbwLongClickActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(21, R.string.app_str_adjust_rgb, 3));
        arrayList.add(new Action(38, R.string.gqx_action_only_selected, 1));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getRgbcwShortClickActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(1, R.string.rgb_new_action_3, 3));
        arrayList.add(new Action(2, R.string.rgb_new_action_4, 3));
        arrayList.add(new Action(3, R.string.rgb_new_action_5, 3));
        arrayList.add(new Action(4, R.string.theme, 3));
        arrayList.add(new Action(18, R.string.general_mode, 3));
        arrayList.add(new Action(5, R.string.advanced_mode, 3));
        arrayList.add(new Action(16, R.string.rgb_new_action_1, 3));
        arrayList.add(new Action(17, R.string.rgb_new_action_2, 3));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getRgbcwKnobActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(19, R.string.app_str_adjust_all_brt, 3));
        arrayList.add(new Action(22, R.string.app_str_adjust_rgb_brt, 3));
        arrayList.add(new Action(21, R.string.app_str_adjust_rgb, 3));
        arrayList.add(new Action(20, R.string.app_str_adjust_cw_value, 3));
        arrayList.add(new Action(24, R.string.app_str_adjust_cw_brt, 3));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getRgbcwLongClickActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(19, R.string.app_str_adjust_all_brt, 3));
        arrayList.add(new Action(22, R.string.app_str_adjust_rgb_brt, 3));
        arrayList.add(new Action(21, R.string.app_str_adjust_rgb, 3));
        arrayList.add(new Action(20, R.string.app_str_adjust_cw_value, 3));
        arrayList.add(new Action(24, R.string.app_str_adjust_cw_brt, 3));
        arrayList.add(new Action(38, R.string.gqx_action_only_selected, 1));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getDaliRgbcwLongClickActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(21, R.string.app_str_adjust_rgb, 3));
        arrayList.add(new Action(38, R.string.gqx_action_only_selected, 1));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getDreamCurtainKnobActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(25, R.string.dry_curtain_action_1, 3));
        arrayList.add(new Action(32, R.string.dry_curtain_action_3, 3));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getDreamCurtainLongClickActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(25, R.string.dry_curtain_action_1, 3));
        arrayList.add(new Action(32, R.string.dry_curtain_action_3, 3));
        arrayList.add(new Action(39, R.string.curtain_action_2, 3));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getMusicPlayerKnobActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(33, R.string.musicplayer_action_3, 3));
        arrayList.add(new Action(34, R.string.musicplayer_action_4, 3));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getSpiShortClickActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(1, R.string.key_switch_action_4, 3));
        arrayList.add(new Action(2, R.string.rgb_new_action_4, 3));
        arrayList.add(new Action(3, R.string.rgb_new_action_5, 3));
        arrayList.add(new Action(36, R.string.spi_mode_and_list_action, 3));
        arrayList.add(new Action(16, R.string.rgb_new_action_1, 3));
        arrayList.add(new Action(17, R.string.rgb_new_action_2, 3));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getSpiKnobActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(22, R.string.dim_action_3, 3));
        arrayList.add(new Action(21, R.string.app_str_adjust_rgb, 3));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<Action> getSpiLongClickActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Action(22, R.string.dim_action_3, 2));
        arrayList.add(new Action(21, R.string.app_str_adjust_rgb, 2));
        arrayList.add(new Action(38, R.string.gqx_action_only_selected, 1));
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public void selectActionByIndex(int num, Object relateObject, final IAction<List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction>> iAction) {
        this.mIAction = null;
        this.targetRelateObject = null;
        this.indexNum = num;
        switch (num) {
            case 1:
                if (iAction != null) {
                    iAction.act(onOff(relateObject));
                    break;
                }
                break;
            case 2:
                if (iAction != null) {
                    iAction.act(on(relateObject));
                    break;
                }
                break;
            case 3:
                if (iAction != null) {
                    iAction.act(off(relateObject));
                    break;
                }
                break;
            case 4:
                this.targetRelateObject = relateObject;
                NavUtils.destination(ActSelectThemeMode.class).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(relateObject)).withBoolean(Constants.IS_GQ, true).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
                break;
            case 5:
                this.targetRelateObject = relateObject;
                NavUtils.destination(ActSelectDiyMode.class).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(relateObject)).withBoolean(Constants.IS_GQ, true).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
                break;
            case 6:
            case 7:
                this.targetRelateObject = relateObject;
                NavUtils.destination(ActDimSelectColor.class).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(relateObject)).withLong(Constants.CONTROL_ID, relateObject instanceof Role ? ((Role) relateObject).getId() : 0L).withBoolean(Constants.GROUP_CONTROL, false).withBoolean(Constants.IS_GQ, true).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
                break;
            case 8:
            case 9:
                this.targetRelateObject = relateObject;
                NavUtils.destination(ActCtSelectColor.class).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(relateObject)).withLong(Constants.CONTROL_ID, relateObject instanceof Role ? ((Role) relateObject).getId() : 0L).withBoolean(Constants.GROUP_CONTROL, false).withBoolean(Constants.IS_GQ, true).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
                break;
            default:
                switch (num) {
                    case 16:
                    case 17:
                        this.targetRelateObject = relateObject;
                        NavUtils.destination(ActSelectColor.class).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(relateObject)).withLong(Constants.CONTROL_ID, relateObject instanceof Role ? ((Role) relateObject).getId() : 0L).withBoolean(Constants.GROUP_CONTROL, false).withBoolean(Constants.IS_GQ, true).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
                        break;
                    case 18:
                        this.targetRelateObject = relateObject;
                        NavUtils.destination(ActSelectDefaultMode.class).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(relateObject)).withBoolean(Constants.IS_GQ, true).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
                        break;
                    case 19:
                        if (iAction != null) {
                            iAction.act(allBrt(relateObject));
                            break;
                        }
                        break;
                    case 20:
                        if (iAction != null) {
                            iAction.act(cw(relateObject));
                            break;
                        }
                        break;
                    case 21:
                        if (iAction != null) {
                            iAction.act(rgb(relateObject));
                            break;
                        }
                        break;
                    case 22:
                        if (iAction != null) {
                            iAction.act(rgbBrt(relateObject));
                            break;
                        }
                        break;
                    case 23:
                        if (iAction != null) {
                            iAction.act(w(relateObject));
                            break;
                        }
                        break;
                    case 24:
                        if (iAction != null) {
                            iAction.act(cwBrt(relateObject));
                            break;
                        }
                        break;
                    case 25:
                        if (iAction != null) {
                            iAction.act(trigDreamOpenClose(relateObject));
                            break;
                        }
                        break;
                    default:
                        switch (num) {
                            case 32:
                                if (iAction != null) {
                                    iAction.act(trigDreamRotateLeftRight(relateObject));
                                    break;
                                }
                                break;
                            case 33:
                                if (iAction != null) {
                                    iAction.act(musicPlayerVolume(relateObject));
                                    break;
                                }
                                break;
                            case 34:
                                if (iAction != null) {
                                    iAction.act(musicPlayerLastNext(relateObject));
                                    break;
                                }
                                break;
                            case 35:
                                if (iAction != null) {
                                    iAction.act(dimBrt(relateObject));
                                    break;
                                }
                                break;
                            case 36:
                                if (iAction != null) {
                                    this.targetRelateObject = relateObject;
                                    NavUtils.destination(ActSelectSpiForAction.class).withLong(Constants.CONTROL_ID, ProductRepository.getControlId(relateObject)).withBoolean(Constants.IS_GQ, true).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
                                    break;
                                }
                                break;
                            case 37:
                                if (iAction != null) {
                                    iAction.act(ctBrt(relateObject));
                                    break;
                                }
                                break;
                            case 38:
                                if (iAction != null) {
                                    iAction.act(onlySelected());
                                    break;
                                }
                                break;
                            case 39:
                                if (iAction != null) {
                                    iAction.act(trigDreamStop(relateObject));
                                    break;
                                }
                                break;
                        }
                }
        }
        if (this.targetRelateObject != null) {
            this.mIAction = new IAction<List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction>>(this) { // from class: com.ltech.smarthome.singleton.ComboCmdHelper.3
                @Override // com.ltech.smarthome.base.IAction
                public void act(final List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction> childActions) {
                    if (iAction != null) {
                        ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.singleton.ComboCmdHelper.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                iAction.act(childActions);
                            }
                        }, 200L);
                    }
                }
            };
        }
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public void selectColor(int red, int green, int blue, int rgbBrt, int wy, int wyBrt) {
        IAction<List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction>> iAction;
        Object obj = this.targetRelateObject;
        if (obj == null || (iAction = this.mIAction) == null) {
            return;
        }
        int i = this.indexNum;
        if (i == 7 || i == 9 || i == 17) {
            iAction.act(colorAndOff(obj, red, green, blue, rgbBrt, wy, wyBrt));
        } else {
            iAction.act(colorValue(obj, red, green, blue, rgbBrt, wy, wyBrt));
        }
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public void selectTheme(int num) {
        IAction<List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction>> iAction;
        Object obj = this.targetRelateObject;
        if (obj == null || (iAction = this.mIAction) == null) {
            return;
        }
        iAction.act(theme(obj, num));
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public void selectAdvanceMode(int num) {
        IAction<List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction>> iAction;
        Object obj = this.targetRelateObject;
        if (obj == null || (iAction = this.mIAction) == null) {
            return;
        }
        iAction.act(advanceMode(obj, num));
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public void selectGeneralMode(int num) {
        IAction<List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction>> iAction;
        Object obj = this.targetRelateObject;
        if (obj == null || (iAction = this.mIAction) == null) {
            return;
        }
        iAction.act(generalMode(obj, num));
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public void selectSpiMode(int num, boolean listPlay) {
        IAction<List<BleNetworkCmdParam.ComboCmdAction.ComboCmdChildAction>> iAction;
        Object obj = this.targetRelateObject;
        if (obj == null || (iAction = this.mIAction) == null) {
            return;
        }
        iAction.act(spiMode(obj, num, listPlay));
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public void setKeyData(List<KeyInfo> list) {
        Map<Integer, KeyInfo> hashMap;
        if (this.keyMap == null) {
            this.keyMap = new HashMap();
        }
        this.keyMap.clear();
        for (KeyInfo keyInfo : list) {
            if (this.keyMap.containsKey(Integer.valueOf(keyInfo.getZoneNum()))) {
                hashMap = this.keyMap.get(Integer.valueOf(keyInfo.getZoneNum()));
            } else {
                hashMap = new HashMap<>();
            }
            if (hashMap != null) {
                hashMap.put(Integer.valueOf(keyInfo.getActionType()), keyInfo);
            }
            this.keyMap.put(Integer.valueOf(keyInfo.getZoneNum()), hashMap);
        }
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public void setKeyData(int zone, List<KeyInfo> list) {
        if (this.keyMap == null) {
            this.keyMap = new HashMap();
        }
        HashMap hashMap = new HashMap();
        for (KeyInfo keyInfo : list) {
            hashMap.put(Integer.valueOf(keyInfo.getActionType()), keyInfo);
        }
        this.keyMap.put(Integer.valueOf(zone), hashMap);
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public void setKeyShort(int zone, KeyInfo info) {
        setKeyByZone(zone, 2, info);
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public void setKeyKnob(int zone, KeyInfo info) {
        setKeyByZone(zone, 3, info);
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public void setKeyLong(int zone, KeyInfo info) {
        setKeyByZone(zone, 4, info);
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public void setKeySelf(int zone, KeyInfo info) {
        setKeyByZone(zone, 6, info);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setKeyByZone(int zone, int type, KeyInfo info) {
        Map<Integer, KeyInfo> hashMap;
        KeyInfo keyInfo;
        Map<Integer, Map<Integer, KeyInfo>> map = this.keyMap;
        if (map != null) {
            if (type == -1) {
                map.remove(Integer.valueOf(zone));
                return;
            }
            if (map.containsKey(Integer.valueOf(zone))) {
                hashMap = this.keyMap.get(Integer.valueOf(zone));
            } else {
                hashMap = new HashMap<>();
            }
            if (hashMap != null) {
                if (info != null && hashMap.containsKey(Integer.valueOf(type)) && (keyInfo = hashMap.get(Integer.valueOf(type))) != null) {
                    info.setKeyinfoid(keyInfo.getKeyinfoid());
                    info.setIconIndex(keyInfo.getIconIndex());
                    info.setScreenStr(keyInfo.getScreenStr());
                    info.setScreenType(keyInfo.getScreenType());
                    info.setOptionValue(keyInfo.getOptionValue());
                    info.setOption(keyInfo.getOption());
                }
                hashMap.put(Integer.valueOf(type), info);
            }
            this.keyMap.put(Integer.valueOf(zone), hashMap);
        }
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public KeyInfo getKeyShort(int zone) {
        return getKeyByZone(zone, 2);
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public KeyInfo getKeyKnob(int zone) {
        return getKeyByZone(zone, 3);
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public KeyInfo getKeyLong(int zone) {
        return getKeyByZone(zone, 4);
    }

    private KeyInfo getKeyByZone(int zone, int type) {
        Map<Integer, KeyInfo> map;
        Map<Integer, Map<Integer, KeyInfo>> map2 = this.keyMap;
        if (map2 == null || !map2.containsKey(Integer.valueOf(zone)) || (map = this.keyMap.get(Integer.valueOf(zone))) == null) {
            return null;
        }
        return map.get(Integer.valueOf(type));
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public List<KeyInfo> getKeysByZone(int zone) {
        Map<Integer, KeyInfo> map;
        Map<Integer, Map<Integer, KeyInfo>> map2 = this.keyMap;
        if (map2 != null && map2.containsKey(Integer.valueOf(zone)) && (map = this.keyMap.get(Integer.valueOf(zone))) != null) {
            ArrayList arrayList = new ArrayList();
            Iterator<Map.Entry<Integer, KeyInfo>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getValue());
            }
            return arrayList;
        }
        return new ArrayList();
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x00b2, code lost:
    
        r2 = true;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00b5 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0029 A[SYNTHETIC] */
    @Override // com.ltech.smarthome.model.IComboCmd
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isShowKRange() {
        /*
            r10 = this;
            java.util.Map<java.lang.Integer, java.util.Map<java.lang.Integer, com.ltech.smarthome.model.key.KeyInfo>> r0 = r10.keyMap
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            java.util.Set r0 = r0.entrySet()
            java.util.Iterator r0 = r0.iterator()
            r2 = 0
        Lf:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto Lb6
            java.lang.Object r3 = r0.next()
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3
            java.lang.Object r3 = r3.getValue()
            java.util.Map r3 = (java.util.Map) r3
            java.util.Set r3 = r3.entrySet()
            java.util.Iterator r3 = r3.iterator()
        L29:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto Lf
            java.lang.Object r4 = r3.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            java.lang.Object r4 = r4.getValue()
            com.ltech.smarthome.model.key.KeyInfo r4 = (com.ltech.smarthome.model.key.KeyInfo) r4
            if (r4 == 0) goto L29
            int r5 = r4.getObjectType()
            r6 = 2
            r7 = 1
            if (r5 != r7) goto L5c
            com.ltech.smarthome.model.Repository r5 = com.ltech.smarthome.model.Injection.repo()
            com.ltech.smarthome.model.repo.ifun.IDevice r5 = r5.device()
            long r8 = r4.getObjectId()
            com.ltech.smarthome.model.bean.Device r4 = r5.getDeviceByDeviceId(r8)
            int r4 = com.ltech.smarthome.model.repo.ProductRepository.getLightColorType(r4)
            if (r4 != r6) goto Lb3
            goto Lb2
        L5c:
            int r5 = r4.getObjectType()
            if (r5 != r6) goto L79
            com.ltech.smarthome.model.Repository r5 = com.ltech.smarthome.model.Injection.repo()
            com.ltech.smarthome.model.repo.ifun.IGroup r5 = r5.group()
            long r8 = r4.getObjectId()
            com.ltech.smarthome.model.bean.Group r4 = r5.getGroupByGroupId(r8)
            int r4 = com.ltech.smarthome.model.repo.ProductRepository.getLightColorType(r4)
            if (r4 != r6) goto Lb3
            goto Lb2
        L79:
            int r5 = r4.getObjectType()
            r6 = 8
            if (r5 != r6) goto Lb3
            com.ltech.smarthome.model.Repository r5 = com.ltech.smarthome.model.Injection.repo()
            com.ltech.smarthome.model.repo.ifun.IDevice r5 = r5.device()
            long r8 = r4.getObjectId()
            com.ltech.smarthome.model.bean.Device r5 = r5.getDeviceByDeviceId(r8)
            if (r5 == 0) goto L9c
            int r4 = com.ltech.smarthome.model.repo.ProductRepository.getLightColorType(r5)
            r5 = 102(0x66, float:1.43E-43)
            if (r4 != r5) goto Lb3
            goto Lb2
        L9c:
            com.ltech.smarthome.model.Repository r5 = com.ltech.smarthome.model.Injection.repo()
            com.ltech.smarthome.model.repo.ifun.IGroup r5 = r5.group()
            long r8 = r4.getObjectId()
            com.ltech.smarthome.model.bean.Group r4 = r5.getGroupByGroupId(r8)
            boolean r4 = com.ltech.smarthome.ui.device.dalipro.DaliProHelper.isSupportCt(r4)
            if (r4 == 0) goto Lb3
        Lb2:
            r2 = 1
        Lb3:
            if (r2 == 0) goto L29
            return r7
        Lb6:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.singleton.ComboCmdHelper.isShowKRange():boolean");
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public Object getRelateObject(int zone) {
        List<KeyInfo> keysByZone = getKeysByZone(zone);
        if (keysByZone.isEmpty()) {
            return null;
        }
        return keysByZone.get(0).getRelateObject();
    }

    @Override // com.ltech.smarthome.model.IComboCmd
    public String getRelateName(int zone) {
        List<KeyInfo> keysByZone = getKeysByZone(zone);
        if (keysByZone.isEmpty()) {
            return null;
        }
        return keysByZone.get(0).getName();
    }

    public static class Action {
        private String name;
        private int num;
        private int type;

        public Action(int num, int name, int type) {
            this.name = ActivityUtils.getTopActivity().getString(name);
            this.num = num;
            this.type = type;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return this.num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}