package com.ltech.smarthome.ui.scene.local;

import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.parser.JSONLexer;
import com.blankj.utilcode.util.ConvertUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.device_param.BaseIrParam;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import kotlin.text.Typography;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.lang3.ClassUtils;

/* loaded from: classes4.dex */
public class ActSelectDeviceGroupForLocalAction extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> implements ISelectAction {
    private TextView okTv;

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$save$1(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_device));
        setEditString(getString(R.string.app_str_select_all));
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).layoutSort.setVisibility(8);
        ((ActSelect3Binding) this.mViewBinding).layoutSortAndType.setVisibility(0);
        TextView textView = new TextView(this);
        this.okTv = textView;
        textView.setTextColor(getResources().getColor(R.color.color_text_red));
        this.okTv.setTextSize(14.0f);
        this.okTv.setBackgroundColor(getResources().getColor(R.color.white));
        this.okTv.setGravity(17);
        this.okTv.setText(String.format(getResources().getString(R.string.app_str_edit_local_scene_select_finish), 0));
        ((ActSelect3Binding) this.mViewBinding).footerView.addView(this.okTv, new RelativeLayout.LayoutParams(-1, ConvertUtils.dp2px(60.0f)));
        this.okTv.setEnabled(false);
        this.okTv.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectDeviceGroupForLocalAction.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < ActSelectDeviceGroupForLocalAction.this.selectRoleIds.size(); i++) {
                    Role roleByRoleId = ((FtDeviceGroupVM) ActSelectDeviceGroupForLocalAction.this.mViewModel).getRoleByRoleId(ActSelectDeviceGroupForLocalAction.this.selectRoleIds.get(i).longValue());
                    if (roleByRoleId != null) {
                        arrayList.add(roleByRoleId);
                    }
                }
                SceneHelper.instance().maskType = MaskType.LOCAL;
                SceneHelper.instance().controlObject = arrayList;
                ActSelectDeviceGroupForLocalAction actSelectDeviceGroupForLocalAction = ActSelectDeviceGroupForLocalAction.this;
                actSelectDeviceGroupForLocalAction.lambda$save$1(actSelectDeviceGroupForLocalAction);
            }
        });
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void changeSelectCount(int integer) {
        this.okTv.setEnabled(integer > 0);
        this.okTv.setText(String.format(getResources().getString(R.string.app_str_select_finish), Integer.valueOf(integer)));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        super.showEmpty();
        this.okTv.setText(String.format(getResources().getString(R.string.app_str_edit_local_scene_select_finish), 0));
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        if (getIntent().getBooleanExtra(Constants.IS_DYNAMIC_SCENE, false)) {
            return ProductRepository.supportDynamicScene(group);
        }
        return (RelaySeparationHelper.isRelaySeparationSub(group) && group.getSubhide() == 1) ? false : true;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        BaseIrParam baseIrParam;
        if (getIntent().getBooleanExtra(Constants.IS_DYNAMIC_SCENE, false)) {
            return ProductRepository.supportDynamicScene(device);
        }
        String productId = device.getProductId();
        productId.hashCode();
        char c2 = 65535;
        switch (productId.hashCode()) {
            case -2133025272:
                if (productId.equals(ProductId.CG485_SUB_DEVICE)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1822884084:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB6)) {
                    c2 = 1;
                    break;
                }
                break;
            case -1819630261:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                    c2 = 2;
                    break;
                }
                break;
            case -1817691924:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                    c2 = 3;
                    break;
                }
                break;
            case -1805322688:
                if (productId.equals(ProductId.ID_BLE_LIGHT_DIM)) {
                    c2 = 4;
                    break;
                }
                break;
            case -1805199680:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CT)) {
                    c2 = 5;
                    break;
                }
                break;
            case -1804340546:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGB)) {
                    c2 = 6;
                    break;
                }
                break;
            case -1804278081:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBW)) {
                    c2 = 7;
                    break;
                }
                break;
            case -1803448738:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBWY)) {
                    c2 = '\b';
                    break;
                }
                break;
            case -1796419228:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                    c2 = '\t';
                    break;
                }
                break;
            case -1710907378:
                if (productId.equals(ProductId.ID_BLE_KBS)) {
                    c2 = '\n';
                    break;
                }
                break;
            case -1550133760:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB1)) {
                    c2 = 11;
                    break;
                }
                break;
            case -1309274422:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                    c2 = '\f';
                    break;
                }
                break;
            case -1308265372:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                    c2 = '\r';
                    break;
                }
                break;
            case -1287620343:
                if (productId.equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                    c2 = 14;
                    break;
                }
                break;
            case -1273434493:
                if (productId.equals(ProductId.ID_SENSOR_MR04)) {
                    c2 = 15;
                    break;
                }
                break;
            case -1265646206:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                    c2 = 16;
                    break;
                }
                break;
            case -1084555505:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                    c2 = 17;
                    break;
                }
                break;
            case -1082613022:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6)) {
                    c2 = 18;
                    break;
                }
                break;
            case -969622016:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
                    c2 = 19;
                    break;
                }
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = 20;
                    break;
                }
                break;
            case -732569219:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = 21;
                    break;
                }
                break;
            case -728269602:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6T)) {
                    c2 = 22;
                    break;
                }
                break;
            case -324427448:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                    c2 = 23;
                    break;
                }
                break;
            case 2003796:
                if (productId.equals(ProductId.CENTRAL_AIR_SUB_DEVICE)) {
                    c2 = 24;
                    break;
                }
                break;
            case 2003797:
                if (productId.equals(ProductId.FRESH_AIR_SUB_DEVICE)) {
                    c2 = 25;
                    break;
                }
                break;
            case 2003798:
                if (productId.equals(ProductId.FLOOR_HEAT_SUB_DEVICE)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case 166485422:
                if (productId.equals(ProductId.ID_BLE_SWITCH)) {
                    c2 = 27;
                    break;
                }
                break;
            case 186184655:
                if (productId.equals(ProductId.ID_SENSOR_MR03)) {
                    c2 = 28;
                    break;
                }
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = 29;
                    break;
                }
                break;
            case 294483828:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) {
                    c2 = 30;
                    break;
                }
                break;
            case 312618751:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6M)) {
                    c2 = 31;
                    break;
                }
                break;
            case 356111630:
                if (productId.equals(ProductId.ID_AS_PANEL_U1S)) {
                    c2 = ' ';
                    break;
                }
                break;
            case 356193315:
                if (productId.equals(ProductId.ID_AS_PANEL_U2S)) {
                    c2 = '!';
                    break;
                }
                break;
            case 376429092:
                if (productId.equals(ProductId.ID_AS_PANEL_U4S)) {
                    c2 = '\"';
                    break;
                }
                break;
            case 376488674:
                if (productId.equals(ProductId.ID_AS_PANEL_U5S)) {
                    c2 = '#';
                    break;
                }
                break;
            case 414687077:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CGD_PRO)) {
                    c2 = '$';
                    break;
                }
                break;
            case 427686243:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4)) {
                    c2 = '%';
                    break;
                }
                break;
            case 439998223:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6D)) {
                    c2 = Typography.amp;
                    break;
                }
                break;
            case 534249931:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB2)) {
                    c2 = '\'';
                    break;
                }
                break;
            case 662799966:
                if (productId.equals(ProductId.ID_BLE_LIGHT_SPI)) {
                    c2 = '(';
                    break;
                }
                break;
            case 811752507:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI)) {
                    c2 = ')';
                    break;
                }
                break;
            case 1479279198:
                if (productId.equals(ProductId.ID_SENSOR_MS03)) {
                    c2 = '*';
                    break;
                }
                break;
            case 1647983530:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6A)) {
                    c2 = '+';
                    break;
                }
                break;
            case 1786777444:
                if (productId.equals(ProductId.ID_BLE_MUSIC_PLAYER)) {
                    c2 = StringUtil.COMMA;
                    break;
                }
                break;
            case 1861788715:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB5)) {
                    c2 = Soundex.SILENT_MARKER;
                    break;
                }
                break;
            case 1921260107:
                if (productId.equals(ProductId.ID_BLE_DRY_CONTACT)) {
                    c2 = ClassUtils.PACKAGE_SEPARATOR_CHAR;
                    break;
                }
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = '/';
                    break;
                }
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = '0';
                    break;
                }
                break;
            case 1976427583:
                if (productId.equals(ProductId.ID_BLE_CURTAIN)) {
                    c2 = '1';
                    break;
                }
                break;
            case 2088187733:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4TE)) {
                    c2 = '2';
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
            case 1:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\n':
            case 14:
            case 22:
            case 24:
            case 25:
            case 26:
            case 31:
            case '$':
            case '&':
            case '(':
            case '+':
            case ',':
            case '.':
            case '1':
            case '2':
                return true;
            case 2:
            case 3:
            case '\t':
            case 11:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 28:
            case 29:
            case '%':
            case '\'':
            case '*':
            case '-':
            case '/':
            case '0':
                return ((BleParam) device.getParam(BleParam.class)).getGroupId() == 0;
            case '\f':
            case '\r':
            case 16:
            case 23:
            case 30:
            case ')':
                return !TextUtils.isEmpty(device.getMcuversion());
            case 27:
                if (RelaySeparationHelper.isPanelRelay(device)) {
                    if (device.getSubhide() == 1) {
                        return false;
                    }
                    Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getMacdeviceid());
                    return deviceByDeviceId == null || deviceByDeviceId.getParam() == null || deviceByDeviceId.getParam(BleParam.class) == null || ((BleParam) deviceByDeviceId.getParam(BleParam.class)).getGroupId() == 0;
                }
                return true;
            case ' ':
            case '!':
            case '\"':
            case '#':
                return AsHelper.isNewUb(device) && ((BleParam) device.getParam(BleParam.class)).getGroupId() == 0;
            default:
                return (!device.isSubDevice() || (baseIrParam = (BaseIrParam) device.getParam(BaseIrParam.class)) == null || baseIrParam.getUnicastAddress() == 0) ? false : true;
        }
    }
}