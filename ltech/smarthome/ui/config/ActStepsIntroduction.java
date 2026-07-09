package com.ltech.smarthome.ui.config;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.alibaba.fastjson.parser.JSONLexer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActStepsIntroductionBinding;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.utils.DeviceAddStepsHelper;
import com.ltech.smarthome.utils.LanguageUtils;
import io.netty.util.internal.StringUtil;
import java.util.List;
import kotlin.text.Typography;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.lang3.ClassUtils;

/* loaded from: classes4.dex */
public class ActStepsIntroduction extends BaseNormalActivity<ActStepsIntroductionBinding> implements OnStepsIntroductionListener {
    private List<Fragment> fragments;
    private int mCurPostion = 0;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_steps_introduction;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setViewByProductId();
        initViewPager();
    }

    private void initViewPager() {
        ((ActStepsIntroductionBinding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.config.ActStepsIntroduction.1
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return (Fragment) ActStepsIntroduction.this.fragments.get(position);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return ActStepsIntroduction.this.fragments.size();
            }
        });
        ((ActStepsIntroductionBinding) this.mViewBinding).viewpager.setUserInputEnabled(false);
        ((ActStepsIntroductionBinding) this.mViewBinding).viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.ltech.smarthome.ui.config.ActStepsIntroduction.2
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                ActStepsIntroduction.this.mCurPostion = position;
            }
        });
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private void setViewByProductId() {
        if (ConfigHelper.instance().productInfo == null) {
            return;
        }
        String productId = ConfigHelper.instance().productInfo.getProductId();
        String subProductKey = ConfigHelper.instance().productInfo.getSubProductKey();
        productId.hashCode();
        char c2 = 65535;
        switch (productId.hashCode()) {
            case -2060969856:
                if (productId.equals(ProductId.ID_AS_PANEL_UB8)) {
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
            case -1642166464:
                if (productId.equals(ProductId.ID_BLE_HAM)) {
                    c2 = 11;
                    break;
                }
                break;
            case -1550133760:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB1)) {
                    c2 = '\f';
                    break;
                }
                break;
            case -1343252468:
                if (productId.equals(ProductId.ID_RS485_BLE)) {
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
            case -1281119909:
                if (productId.equals(ProductId.ID_RC_B2)) {
                    c2 = 15;
                    break;
                }
                break;
            case -1273434493:
                if (productId.equals(ProductId.ID_SENSOR_MR04)) {
                    c2 = 16;
                    break;
                }
                break;
            case -1213322926:
                if (productId.equals(ProductId.ID_RC_B8)) {
                    c2 = 17;
                    break;
                }
                break;
            case -1201890867:
                if (productId.equals(ProductId.ID_SMART_PANEL_GQ_PRO)) {
                    c2 = 18;
                    break;
                }
                break;
            case -1084555505:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                    c2 = 19;
                    break;
                }
                break;
            case -1082613022:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6)) {
                    c2 = 20;
                    break;
                }
                break;
            case -1073881216:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB8)) {
                    c2 = 21;
                    break;
                }
                break;
            case -969622016:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
                    c2 = 22;
                    break;
                }
                break;
            case -961541705:
                if (productId.equals(ProductId.ID_SMART_PANEL_S6B)) {
                    c2 = 23;
                    break;
                }
                break;
            case -852623517:
                if (productId.equals(ProductId.ID_RC4S)) {
                    c2 = 24;
                    break;
                }
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = 25;
                    break;
                }
                break;
            case -833770237:
                if (productId.equals(ProductId.ID_SMART_SWITCH_SQ)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case -732569219:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = 27;
                    break;
                }
                break;
            case -728269602:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6T)) {
                    c2 = 28;
                    break;
                }
                break;
            case -249671171:
                if (productId.equals(ProductId.ID_RC_B5)) {
                    c2 = 29;
                    break;
                }
                break;
            case -208296259:
                if (productId.equals(ProductId.ID_RC4)) {
                    c2 = 30;
                    break;
                }
                break;
            case -207348713:
                if (productId.equals(ProductId.ID_KEY_SWITCH_1)) {
                    c2 = 31;
                    break;
                }
                break;
            case -206567420:
                if (productId.equals(ProductId.ID_KEY_SWITCH_2)) {
                    c2 = ' ';
                    break;
                }
                break;
            case -206510721:
                if (productId.equals(ProductId.ID_KEY_SWITCH_3)) {
                    c2 = '!';
                    break;
                }
                break;
            case -206454022:
                if (productId.equals(ProductId.ID_KEY_SWITCH_4)) {
                    c2 = '\"';
                    break;
                }
                break;
            case 13862565:
                if (productId.equals(ProductId.ID_BLE_RS8)) {
                    c2 = '#';
                    break;
                }
                break;
            case 42289893:
                if (productId.equals(ProductId.ID_SENSOR_HSD)) {
                    c2 = '$';
                    break;
                }
                break;
            case 70457728:
                if (productId.equals(ProductId.ID_DRY_CONTACT_TO_BLE)) {
                    c2 = '%';
                    break;
                }
                break;
            case 155753896:
                if (productId.equals(ProductId.ID_DOOR_SENSOR)) {
                    c2 = Typography.amp;
                    break;
                }
                break;
            case 166485422:
                if (productId.equals(ProductId.ID_BLE_SWITCH)) {
                    c2 = '\'';
                    break;
                }
                break;
            case 186184655:
                if (productId.equals(ProductId.ID_SENSOR_MR03)) {
                    c2 = '(';
                    break;
                }
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = ')';
                    break;
                }
                break;
            case 312618751:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6M)) {
                    c2 = '*';
                    break;
                }
                break;
            case 353722044:
                if (productId.equals(ProductId.ID_WIFI_CAMERA)) {
                    c2 = '+';
                    break;
                }
                break;
            case 356111630:
                if (productId.equals(ProductId.ID_AS_PANEL_U1S)) {
                    c2 = StringUtil.COMMA;
                    break;
                }
                break;
            case 356193315:
                if (productId.equals(ProductId.ID_AS_PANEL_U2S)) {
                    c2 = Soundex.SILENT_MARKER;
                    break;
                }
                break;
            case 359647590:
                if (productId.equals(ProductId.ID_SMART_SWITCH_SQ_PRO)) {
                    c2 = ClassUtils.PACKAGE_SEPARATOR_CHAR;
                    break;
                }
                break;
            case 376429092:
                if (productId.equals(ProductId.ID_AS_PANEL_U4S)) {
                    c2 = '/';
                    break;
                }
                break;
            case 376488674:
                if (productId.equals(ProductId.ID_AS_PANEL_U5S)) {
                    c2 = '0';
                    break;
                }
                break;
            case 377377599:
                if (productId.equals(ProductId.ID_BODY_SENSOR)) {
                    c2 = '1';
                    break;
                }
                break;
            case 414687077:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CGD_PRO)) {
                    c2 = '2';
                    break;
                }
                break;
            case 427686243:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4)) {
                    c2 = '3';
                    break;
                }
                break;
            case 439998223:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6D)) {
                    c2 = '4';
                    break;
                }
                break;
            case 534249931:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB2)) {
                    c2 = '5';
                    break;
                }
                break;
            case 612512450:
                if (productId.equals(ProductId.ID_HOME_KIT)) {
                    c2 = '6';
                    break;
                }
                break;
            case 613226983:
                if (productId.equals(ProductId.ID_SMART_SWITCH_SQB)) {
                    c2 = '7';
                    break;
                }
                break;
            case 662799966:
                if (productId.equals(ProductId.ID_BLE_LIGHT_SPI)) {
                    c2 = '8';
                    break;
                }
                break;
            case 1097035898:
                if (productId.equals(ProductId.ID_SCENE_PANEL_S8)) {
                    c2 = '9';
                    break;
                }
                break;
            case 1181428532:
                if (productId.equals(ProductId.ID_SMART_PANEL_GQ)) {
                    c2 = ':';
                    break;
                }
                break;
            case 1309050445:
                if (productId.equals(ProductId.ID_SMART_PANEL_GQX)) {
                    c2 = ';';
                    break;
                }
                break;
            case 1378424449:
                if (productId.equals(ProductId.ID_CENTRE_AIR_PRO_GATEWAY)) {
                    c2 = Typography.less;
                    break;
                }
                break;
            case 1473345811:
                if (productId.equals(ProductId.ID_RC_B1)) {
                    c2 = '=';
                    break;
                }
                break;
            case 1479279198:
                if (productId.equals(ProductId.ID_SENSOR_MS03)) {
                    c2 = Typography.greater;
                    break;
                }
                break;
            case 1647983530:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6A)) {
                    c2 = '?';
                    break;
                }
                break;
            case 1786777444:
                if (productId.equals(ProductId.ID_BLE_MUSIC_PLAYER)) {
                    c2 = '@';
                    break;
                }
                break;
            case 1861788715:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB5)) {
                    c2 = 'A';
                    break;
                }
                break;
            case 1921260107:
                if (productId.equals(ProductId.ID_BLE_DRY_CONTACT)) {
                    c2 = 'B';
                    break;
                }
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = 'C';
                    break;
                }
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = 'D';
                    break;
                }
                break;
            case 1976427583:
                if (productId.equals(ProductId.ID_BLE_CURTAIN)) {
                    c2 = 'E';
                    break;
                }
                break;
            case 2002295507:
                if (productId.equals(ProductId.ID_CENTRE_AIR_GATEWAY)) {
                    c2 = 'F';
                    break;
                }
                break;
            case 2088187733:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4TE)) {
                    c2 = 'G';
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                this.fragments = ub8Steps();
                break;
            case 1:
            case '\f':
            case 21:
            case '5':
            case 'A':
                this.fragments = ebSeriesSteps(productId);
                break;
            case 2:
            case 3:
            case '\t':
            case 19:
            case 22:
            case '3':
                this.fragments = panel123ProSteps(productId);
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '2':
            case '8':
                if (subProductKey.equals("07") || subProductKey.equals("08") || subProductKey.equals("06") || subProductKey.equals("0A")) {
                    this.fragments = switchSteps();
                    break;
                } else {
                    this.fragments = lightSteps();
                    break;
                }
                break;
            case '\n':
                this.fragments = switchKbsSteps();
                break;
            case 11:
                this.fragments = bleHamSteps();
                break;
            case '\r':
                this.fragments = cgLinkSteps();
                break;
            case 14:
                this.fragments = curH3Steps();
                break;
            case 15:
            case 17:
            case 29:
            case '=':
                this.fragments = rcBSteps(productId);
                break;
            case 16:
                this.fragments = mr04SensorSteps();
                break;
            case 18:
                if (subProductKey.equals(ProductId.BLE_SMART_PANEL_SUB_TYPE_GQ_MAX)) {
                    this.fragments = gqMaxSteps();
                    break;
                } else {
                    this.fragments = gqProSteps();
                    break;
                }
            case 20:
                this.fragments = panel123Steps(productId);
                break;
            case 23:
                this.fragments = panel6Steps();
                break;
            case 24:
                this.fragments = rc4SSteps();
                break;
            case 25:
            case 27:
            case 'C':
            case 'D':
                if (subProductKey.equals("09") || subProductKey.equals("0A") || subProductKey.equals("0B")) {
                    this.fragments = panel123cSteps(productId);
                    break;
                } else if (subProductKey.equals("11") || subProductKey.equals("12") || subProductKey.equals("13") || subProductKey.equals(ProductId.BLE_SMART_PANEL_SUB_TYPE_S4)) {
                    this.fragments = panel123Steps(productId);
                    break;
                }
                break;
            case 26:
                this.fragments = panelSqSteps();
                break;
            case 28:
            case '*':
            case '4':
            case '?':
                this.fragments = e6SeriesSteps(productId);
                break;
            case 30:
                this.fragments = rc4Steps();
                break;
            case 31:
            case ' ':
            case '!':
            case '\"':
                this.fragments = swichSteps();
                break;
            case '#':
                this.fragments = cgRs8Steps();
                break;
            case '$':
                this.fragments = hsdSteps();
                break;
            case '%':
            case 'B':
                this.fragments = drySteps(productId);
                break;
            case '&':
                this.fragments = doorSensorSteps();
                break;
            case '\'':
                if (subProductKey.equals(ProductId.BLE_SWITCH_SUB_TYPE_KBS1)) {
                    this.fragments = switchKbsSteps();
                    break;
                } else {
                    this.fragments = switchModuleSteps();
                    break;
                }
            case '(':
                this.fragments = mr03SensorSteps();
                break;
            case ')':
            case '9':
                this.fragments = panel48Steps(productId);
                break;
            case '+':
                this.fragments = wifiCameraSteps();
                break;
            case ',':
            case '-':
            case '/':
            case '0':
                this.fragments = usPanelSteps(productId);
                break;
            case '.':
                this.fragments = panelSqProSteps();
                break;
            case '1':
                this.fragments = bodySensorSteps();
                break;
            case '6':
                this.fragments = homeKitSteps();
                break;
            case '7':
                this.fragments = panelSqbSteps();
                break;
            case ':':
                this.fragments = gqSteps();
                break;
            case ';':
                this.fragments = gqxSteps();
                break;
            case '<':
                if (ConfigHelper.instance().productInfo.getSubProductKey() == "0D") {
                    this.fragments = centerAirMaxSteps();
                    break;
                } else {
                    this.fragments = centerAirProSteps();
                    break;
                }
            case '>':
                this.fragments = ms03SensorSteps();
                break;
            case '@':
                this.fragments = musicPlayerSteps();
                break;
            case 'E':
                if (ConfigHelper.instance().productInfo.getCurtainType() != 1 && ConfigHelper.instance().productInfo.getCurtainType() == 2) {
                    this.fragments = cur15Steps();
                    break;
                }
                break;
            case 'F':
                this.fragments = centerAirSteps();
                break;
            case 'G':
                this.fragments = g4teSteps();
                break;
        }
    }

    private List<Fragment> bodySensorSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_body_sensor_1), R.mipmap.pic_add_ps_1).withNormalStep(getString(R.string.app_str_network_config_ble_body_sensor_2), R.mipmap.pic_add_hs_2).withCountdownStep(getString(R.string.app_str_network_config_ble_body_sensor_3), 20000).withNormalStep(getString(R.string.app_str_network_config_ble_body_sensor_4), R.drawable.anim_body_sensor_flash).getData();
    }

    private List<Fragment> hsdSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_hsd_1), R.mipmap.pic_add_hsd_1).withNormalStep(getString(R.string.app_str_network_config_ble_hsd_2), R.drawable.anim_body_sensor_flash).getData();
    }

    private List<Fragment> mr03SensorSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), R.mipmap.pic_add_1).withNormalStep(getString(R.string.app_str_network_config_ble_wave_sensor03_2), R.mipmap.pic_add_mr3_2).getData();
    }

    private List<Fragment> mr04SensorSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), R.mipmap.pic_add_1).withNormalStep(getString(R.string.app_str_network_config_ble_wave_sensor04_2), R.mipmap.pic_add_mr4_2).getData();
    }

    private List<Fragment> ms03SensorSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), R.mipmap.pic_add_1).withNormalStep(getString(R.string.app_str_network_config_ble_wave_sensor03_2), R.mipmap.pic_add_ms3_2).getData();
    }

    private List<Fragment> doorSensorSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_door_sensor_step_1), R.mipmap.pic_add_cgdr_1).withNormalStep(getString(R.string.app_str_network_config_ble_door_sensor_step_2), R.mipmap.pic_add_cgdr_2).getData();
    }

    private List<Fragment> swichSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_switch_step_1), R.mipmap.pic_add_ps_1).withNormalStep(getString(R.string.app_str_network_config_ble_switch_step_2), R.mipmap.pic_add_ps_2).getData();
    }

    private List<Fragment> rc4Steps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_rc4_step_1), R.mipmap.pic_add_ps_1).withNormalStep(getString(R.string.app_str_network_config_ble_rc4_step_2), R.mipmap.pic_add_rc4_2).getData();
    }

    private List<Fragment> rc4SSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_rc4_step_1), R.mipmap.pic_add_ps_1).withNormalStep(getString(R.string.app_str_network_config_ble_rc4_step_2), R.mipmap.add_rc4sble).getData();
    }

    private List<Fragment> rcBSteps(String productId) {
        int i;
        int i2;
        productId.hashCode();
        i = 0;
        switch (productId) {
            case "3503908278750336":
                i2 = R.mipmap.pic_add_rc_b2_2;
                i = R.string.app_str_network_config_ble_rc_b2_step_2;
                break;
            case "3508084028410496":
                i2 = R.mipmap.pic_add_rc_b8_2;
                i = R.string.app_str_network_config_ble_rc_b8_step_2;
                break;
            case "3503908725640320":
                i2 = R.mipmap.pic_add_rc_b5_2;
                i = R.string.app_str_network_config_ble_rc_b2_step_2;
                break;
            case "3503907950824576":
                i2 = R.mipmap.pic_add_rc_b1_2;
                i = R.string.app_str_network_config_ble_rc_b1_step_2;
                break;
            default:
                i2 = 0;
                break;
        }
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_rc4_step_1), R.mipmap.pic_add_ps_1).withNormalStep(getString(i), i2).getData();
    }

    private List<Fragment> musicPlayerSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), R.mipmap.pic_add_1).withNormalStep(getString(R.string.app_str_network_config_ble_musicplayer_step_2), R.mipmap.pic_add_2).withNormalStep(getString(R.string.app_str_network_config_ble_musicplayer_step_3), R.drawable.anim_musicplayer_flash).getData();
    }

    private List<Fragment> drySteps(String productId) {
        productId.hashCode();
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), R.mipmap.pic_add_1).withNormalStep(getString(R.string.app_str_network_config_ble_dry_step_2), R.mipmap.pic_add_2).withNormalStep(getString(R.string.app_str_network_config_ble_dry_step_3), !productId.equals(ProductId.ID_DRY_CONTACT_TO_BLE) ? !productId.equals(ProductId.ID_BLE_DRY_CONTACT) ? 0 : R.drawable.anim_dry_flash : R.drawable.anim_dry_to_ble_flash).getData();
    }

    private List<Fragment> cur15Steps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), R.mipmap.pic_add_1).withNormalStep(getString(R.string.app_str_network_config_ble_curtain_step_2), R.mipmap.add_cgcur15_pic).withNormalStep(getString(R.string.app_str_network_config_ble_curtain_step_3), R.drawable.anim_cur15_flash).getData();
    }

    private List<Fragment> curH3Steps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), R.mipmap.pic_add_1).withNormalStep(getString(R.string.app_str_network_config_ble_curtain_step_2), R.mipmap.add_cgcurh3_pic).withNormalStep(getString(R.string.app_str_network_config_ble_curtain_step_3), R.drawable.anim_curh3_flash).getData();
    }

    private List<Fragment> centerAirSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), R.mipmap.pic_add_1).withNormalStep(getString(R.string.app_str_network_config_ble_centre_air_step_2), R.mipmap.pic_add_cgair_2).withNormalStep(getString(R.string.app_str_network_config_ble_centre_air_step_3), R.mipmap.pic_add_cgair_3).getData();
    }

    private List<Fragment> centerAirProSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), R.mipmap.pic_add_1).withNormalStep(getString(R.string.app_str_network_config_ble_centre_air_pro_step_2), R.mipmap.pic_add_cgairpro_2).withNormalStep(getString(R.string.app_str_network_config_ble_centre_air_pro_step_3), R.mipmap.pic_add_cgairpro_3).withNormalStep(getString(R.string.app_str_network_config_ble_centre_air_pro_step_4), R.mipmap.pic_add_cgairpro_4).withNormalStep(getString(R.string.app_str_network_config_ble_centre_air_pro_step_5), R.mipmap.pic_add_cgairpro_5).getData();
    }

    private List<Fragment> centerAirMaxSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), R.mipmap.pic_add_1).withNormalStep(getString(R.string.app_str_network_config_ble_centre_air_pro_step_2), R.mipmap.pic_add_cgairmax_2).withNormalStep(getString(R.string.app_str_network_config_ble_centre_air_max_step_3), R.mipmap.pic_add_cgairmax_3).withNormalStep(getString(R.string.app_str_network_config_ble_centre_air_max_step_4), R.mipmap.pic_add_cgairmax_4).withNormalStep(getString(R.string.app_str_network_config_ble_centre_air_max_step_5), R.mipmap.pic_add_cgairmax_5).getData();
    }

    private List<Fragment> panel48Steps(String productId) {
        int i;
        int i2;
        productId.hashCode();
        if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
            i = R.mipmap.pic_add_s4m_2;
            i2 = R.drawable.anim_s4m_flash;
        } else if (productId.equals(ProductId.ID_SCENE_PANEL_S8)) {
            i = R.mipmap.pic_add_s8m_2;
            i2 = R.drawable.anim_s8m_flash;
        } else {
            i = 0;
            i2 = 0;
        }
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), R.mipmap.pic_add_1).withNormalStep(getString(R.string.app_str_network_config_ble_smart_panel_s48step_2), i).withNormalStep(getString(R.string.app_str_network_config_ble_smart_panel_3), i2).getData();
    }

    private List<Fragment> panel6Steps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_smart_panel_s6_step_1), R.mipmap.pic_add_s6_1).withNormalStep(getString(R.string.app_str_network_config_ble_smart_panel_s6_step_2), R.mipmap.pic_add_s6_2).withNormalStep(getString(R.string.app_str_network_config_ble_smart_panel_s6_step_3), R.drawable.anim_s6b_flash).getData();
    }

    private List<Fragment> panel123cSteps(String productId) {
        int i;
        int i2;
        productId.hashCode();
        i = 0;
        switch (productId) {
            case "221042516351701":
                i = R.mipmap.pic_add_s1c_2;
                i2 = R.drawable.anim_s1c_flash;
                break;
            case "121042516340801":
                i = R.mipmap.pic_add_s3c_2;
                i2 = R.drawable.anim_s3c_flash;
                break;
            case "121042516345401":
                i = R.mipmap.pic_add_s2c_2;
                i2 = R.drawable.anim_s2c_flash;
                break;
            default:
                i2 = 0;
                break;
        }
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), R.mipmap.pic_add_1).withNormalStep(getString(R.string.app_str_network_config_ble_smart_panel_s123c_step_2), i).withNormalStep(getString(R.string.app_str_network_config_ble_smart_panel_3), i2).getData();
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private List<Fragment> panel123Steps(String productId) {
        productId.hashCode();
        int i = 0;
        char c2 = 65535;
        switch (productId.hashCode()) {
            case -1082613022:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6)) {
                    c2 = 0;
                    break;
                }
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = 1;
                    break;
                }
                break;
            case -732569219:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = 2;
                    break;
                }
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = 3;
                    break;
                }
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = 4;
                    break;
                }
                break;
        }
        int i2 = R.drawable.anim_s3_flash;
        switch (c2) {
            case 0:
                i = R.mipmap.add_s6_2;
                i2 = R.drawable.anim_s6b_flash;
                break;
            case 1:
                i = R.mipmap.pic_add_s1_2;
                i2 = R.drawable.anim_s1_flash;
                break;
            case 2:
                i = R.mipmap.pic_add_s4_2;
                break;
            case 3:
                i = R.mipmap.pic_add_s3_2;
                break;
            case 4:
                i = R.mipmap.pic_add_s2_2;
                i2 = R.drawable.anim_s2_flash;
                break;
            default:
                i2 = 0;
                break;
        }
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), R.mipmap.pic_add_s123_1).withNormalStep(getString(R.string.app_str_network_config_ble_smart_panel_s123_step_2), i).withNormalStep(getString(R.string.app_str_network_config_ble_smart_panel_3), i2).getData();
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private List<Fragment> panel123ProSteps(String productId) {
        int i;
        productId.hashCode();
        int i2 = 0;
        char c2 = 65535;
        switch (productId.hashCode()) {
            case -1819630261:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1817691924:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                    c2 = 1;
                    break;
                }
                break;
            case -1796419228:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                    c2 = 2;
                    break;
                }
                break;
            case -1084555505:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                    c2 = 3;
                    break;
                }
                break;
            case -969622016:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
                    c2 = 4;
                    break;
                }
                break;
            case 427686243:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4)) {
                    c2 = 5;
                    break;
                }
                break;
        }
        int i3 = R.mipmap.pic_add_s123_1;
        switch (c2) {
            case 0:
                i2 = R.mipmap.pic_add_s1_pro_2;
                i = R.mipmap.pic_add_s1_pro_3;
                break;
            case 1:
                i2 = R.mipmap.pic_add_s2_pro_2;
                i = R.mipmap.pic_add_s2_pro_3;
                break;
            case 2:
                i2 = R.mipmap.pic_add_s3_pro_2;
                i = R.mipmap.pic_add_s3_pro_3;
                break;
            case 3:
                i3 = R.mipmap.add_s6pro_1;
                i2 = R.mipmap.add_s6pro_2;
                i = R.mipmap.add_s6pro_3;
                break;
            case 4:
                i2 = LanguageUtils.isChinese(this) ? R.mipmap.add_g4pro_3 : R.mipmap.add_g4pro_3_en;
                i = LanguageUtils.isChinese(this) ? R.mipmap.add_g4pro_4 : R.mipmap.add_g4pro_4_en;
                i3 = R.mipmap.add_g4pro_2;
                break;
            case 5:
                i2 = LanguageUtils.isChinese(this) ? R.mipmap.add_g4_3 : R.mipmap.add_g4_3_en;
                i = LanguageUtils.isChinese(this) ? R.mipmap.add_g4_4 : R.mipmap.add_g4_4_en;
                i3 = R.mipmap.add_g4_2;
                break;
            default:
                i = 0;
                break;
        }
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), i3).withNormalStep(getString(R.string.app_str_network_config_ble_smart_panel_s123pro_step_2), i2).withNormalStep(getString(R.string.app_str_network_config_ble_smart_panel_s123pro_step_3), i).getData();
    }

    private List<Fragment> ebSeriesSteps(String productId) {
        int i;
        productId.hashCode();
        i = 0;
        switch (productId) {
            case "3557654002353408":
                i = R.mipmap.pic_add_eb6;
                break;
            case "3486586935738368":
                i = R.mipmap.pic_add_eb1;
                break;
            case "3537619681035968":
                i = R.mipmap.pic_add_eb8;
                break;
            case "3486587348451328":
                i = R.mipmap.pic_add_eb2;
                break;
            case "3486587769094144":
                i = R.mipmap.pic_add_eb5;
                break;
        }
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), R.mipmap.pic_add_eur_panel_1).withNormalStep(getString(R.string.app_str_network_config_ble_eb_series_2), R.mipmap.pic_add_eur_panel_2).withNormalStep(getString(R.string.app_str_network_config_ble_eb_series_3), i).getData();
    }

    private List<Fragment> panelSqSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), R.mipmap.pic_add_s123_1).withNormalStep(getString(R.string.app_str_network_config_ble_smart_panel_sq_step_2), R.mipmap.pic_add_sq_2).withNormalStep(getString(R.string.app_str_network_config_ble_smart_panel_sq_step_3), R.drawable.anim_sq_flash).getData();
    }

    private List<Fragment> panelSqbSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_smart_panel_sqb_step_1), R.mipmap.pic_add_sq_b_1).withNormalStep(getString(R.string.app_str_network_config_ble_smart_panel_sq_step_2), R.mipmap.pic_add_sq_2).withNormalStep(getString(R.string.app_str_network_config_ble_smart_panel_sq_step_3), R.drawable.anim_sq_flash).getData();
    }

    private List<Fragment> panelSqProSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), R.mipmap.pic_add_s123_1).withNormalStep(getString(R.string.app_str_network_config_ble_smart_panel_sq_pro_step_2), R.mipmap.pic_add_sq_pro_2).withNormalStep(getString(R.string.app_str_network_config_ble_smart_panel_sq_pro_step_3), R.mipmap.pic_add_sq_pro_3).getData();
    }

    private List<Fragment> gqxSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_gqx_step_1), R.mipmap.pic_add_gqx_1).withNormalStep(getString(R.string.app_str_network_config_gqx_step_2), R.mipmap.pic_add_gqx_2).withNormalStep(getString(R.string.app_str_network_config_gqx_step_3), R.drawable.anim_gqx_flsh).getData();
    }

    private List<Fragment> gqSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_gqx_step_1), R.mipmap.pic_add_gq_1).withNormalStep(getString(R.string.app_str_network_config_gqx_step_2), R.mipmap.pic_add_gq_2).withNormalStep(getString(R.string.app_str_network_config_gqx_step_3), R.drawable.anim_gq_flsh).getData();
    }

    private List<Fragment> gqProSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_gqpro_step_1), R.mipmap.add_gqpro_1).withNormalStep(getString(R.string.app_str_network_config_gqpro_step_2), R.mipmap.add_gqpro_2).withNormalStep(getString(R.string.app_str_network_config_gqpro_step_3), R.mipmap.add_gqpro).getData();
    }

    private List<Fragment> gqMaxSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_gqpro_step_1), R.mipmap.add_gqmax).withNormalStep(getString(R.string.app_str_network_config_gqpro_step_2), R.mipmap.add_gqpro_2).withNormalStep(getString(R.string.app_str_network_config_gqpro_step_3), R.mipmap.add_gqpro).getData();
    }

    private List<Fragment> lightSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_light_step_1), R.mipmap.pic_add_1).withNormalStep(getString(R.string.app_str_network_config_ble_light_step_2), R.mipmap.pic_add_2).withNormalStep(getString(R.string.app_str_network_config_ble_light_step_3), R.drawable.anim_light_flsh).getData();
    }

    private List<Fragment> switchSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_light_switch_step_1), R.mipmap.pic_add_1).withNormalStep(getString(R.string.app_str_network_config_ble_light_switch_step_2), R.mipmap.pic_add_2).withNormalStep(getString(R.string.app_str_network_config_ble_light_switch_step_3), R.drawable.anim_light_flsh).getData();
    }

    private List<Fragment> switchModuleSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_light_switch_module_step_1), R.mipmap.pic_add_1).withNormalStep(getString(R.string.app_str_network_config_ble_light_switch_module_step_2), R.mipmap.pic_add_2).withNormalStep(getString(R.string.app_str_network_config_ble_light_switch_module_step_3), R.drawable.anim_light_flsh).getData();
    }

    private List<Fragment> switchKbsSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_kbs_step_1), R.mipmap.add_pic_kbs).withNormalStep(getString(R.string.app_str_network_config_ble_kbs_step_2), R.mipmap.add_pic_kbs).withNormalStep(getString(R.string.app_str_network_config_ble_kbs_step_3), R.mipmap.add_pic_kbs).getData();
    }

    private List<Fragment> usPanelSteps(String productId) {
        int i;
        int i2;
        productId.hashCode();
        i = 0;
        switch (productId) {
            case "120042616091901":
                i = R.mipmap.pic_add_ub1_2;
                i2 = R.drawable.anim_u1s_flash;
                break;
            case "120042616094101":
                i = R.mipmap.pic_add_ub2_2;
                i2 = R.drawable.anim_u2s_flash;
                break;
            case "120042616101901":
                i = R.mipmap.pic_add_ub4_2;
                i2 = R.drawable.anim_u4s_flash;
                break;
            case "120042616103901":
                i = R.mipmap.pic_add_ub5_2;
                i2 = R.drawable.anim_u5s_flash;
                break;
            default:
                i2 = 0;
                break;
        }
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_us_step_1), R.mipmap.pic_add_ub8_1).withNormalStep(getString(R.string.app_str_network_config_ble_us_step_2), i).withNormalStep(getString(R.string.app_str_network_config_ble_us_step_3), i2).getData();
    }

    private List<Fragment> ub8Steps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), R.mipmap.pic_add_ub8_1).withNormalStep(getString(R.string.add_ub8_tip_step_2), R.mipmap.pic_add_ub8_2).getData();
    }

    private List<Fragment> g4teSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), R.mipmap.pic_add_g4te_1).withNormalStep(getString(R.string.app_str_network_config_g4te_step_2), LanguageUtils.isChinese(this) ? R.mipmap.pic_add_g4te_2 : R.mipmap.pic_add_g4te_2_en).withNormalStep(getString(R.string.app_str_network_config_ble_smart_panel_sq_pro_step_3), LanguageUtils.isChinese(this) ? R.mipmap.pic_add_g4te_3 : R.mipmap.pic_add_g4te_3_en).getData();
    }

    private List<Fragment> bleHamSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_ham_step_1), R.mipmap.pic_add_1).withNormalStep(getString(R.string.app_str_network_config_ble_ham_step_2), R.mipmap.pic_add_gateway_2).withNormalStep(getString(R.string.app_str_network_config_ble_ham_step_3), R.drawable.anim_ham_flsh).getData();
    }

    private List<Fragment> cgLinkSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), R.mipmap.ic_add_cg_link_1).withNormalStep(getString(R.string.app_str_network_config_rs485_step_2), R.mipmap.ic_add_cg_link_2).getData();
    }

    private List<Fragment> cgRs8Steps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), R.mipmap.ic_add_cg_rs8_1).withNormalStep(getString(R.string.app_str_network_config_rs485_step_2), R.mipmap.ic_add_cg_rs8_2).getData();
    }

    private List<Fragment> wifiCameraSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_wifi_camera_step_1), R.mipmap.pic_add_1).withScanStep(getString(R.string.app_str_network_config_wifi_camera_step_2), R.mipmap.pic_add_camera_2).getData();
    }

    private List<Fragment> homeKitSteps() {
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.add_home_kit_tip_step_1), R.mipmap.add_cgkit_1_on).withNormalStep(getString(R.string.add_home_kit_tip_step_2), R.mipmap.add_cgkit_2_on).getData();
    }

    private List<Fragment> e6SeriesSteps(String productId) {
        int i;
        productId.hashCode();
        switch (productId) {
            case "4002207473371776":
                i = R.mipmap.pic_add_e6t_1;
                break;
            case "4002205681371776":
                i = R.mipmap.pic_add_e6m_1;
                break;
            case "4002206816422528":
                i = R.mipmap.pic_add_e6a_1;
                break;
            default:
                i = R.mipmap.pic_add_e6d_1;
                break;
        }
        return new DeviceAddStepsHelper().withNormalStep(getString(R.string.app_str_network_config_ble_default_step_1), i).withNormalStep(getString(R.string.app_str_network_config_ble_smart_panel_sq_step_2), R.mipmap.pic_add_e6d_2).withNormalStep(getString(R.string.app_str_network_config_ble_smart_panel_sq_step_3), R.drawable.anim_e6d_flash).getData();
    }

    @Override // com.ltech.smarthome.ui.config.OnStepsIntroductionListener
    public void next() {
        if (this.mCurPostion == this.fragments.size() - 1) {
            return;
        }
        this.mCurPostion++;
        ((ActStepsIntroductionBinding) this.mViewBinding).viewpager.setCurrentItem(this.mCurPostion, true);
    }

    @Override // com.ltech.smarthome.ui.config.OnStepsIntroductionListener
    public void previous() {
        int i = this.mCurPostion;
        if (i == 0) {
            return;
        }
        this.mCurPostion = i - 1;
        ((ActStepsIntroductionBinding) this.mViewBinding).viewpager.setCurrentItem(this.mCurPostion, true);
    }
}