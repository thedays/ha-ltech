package com.ltech.smarthome.ui.device.e6knob;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.E6ExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.singleton.Singleton;
import com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelVM;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant;
import com.ltech.smarthome.utils.cmd_assistant.ModeAssistant;
import com.ltech.smarthome.view.dialog.SelectListAndPicDialog;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.param.SceneCmdParam;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class E6Helper {
    private String actionInstruct = "";
    private Device controlDevice;
    private E6ExtParam extParam;
    private int keyPosition;
    public static final int[] ICON_ARRAY = {R.mipmap.pic_sq_click, R.mipmap.pic_sq_double_click, R.mipmap.pic_sq_whirl, R.mipmap.pic_sq_press_and_hold};
    public static final int[] ACTION_ARRAY = {R.string.single_click, R.string.double_click, R.string.rotate, R.string.long_press};

    public static E6Helper instance() {
        return (E6Helper) Singleton.getSingleton(E6Helper.class);
    }

    public void setControlObject(Device device, int position) {
        this.controlDevice = device;
        this.keyPosition = position;
        E6ExtParam e6ExtParam = new E6ExtParam();
        this.extParam = e6ExtParam;
        e6ExtParam.fillMapWithString(device.getExtParam());
        this.actionInstruct = this.extParam.getAction(position).getActionInstruct();
    }

    public Device getControlObject() {
        return this.controlDevice;
    }

    public String getActionInstruct() {
        return this.actionInstruct;
    }

    private DeviceAssistant getCmdHelper() {
        return CmdAssistant.getDeviceAssistant(this.controlDevice, 1 << this.keyPosition);
    }

    public ModeAssistant getModeCmdHelper() {
        return CmdAssistant.getModeCmdAssistant(this.controlDevice, 1 << this.keyPosition);
    }

    public int getQueryZone(String productId) {
        return (ProductId.ID_KNOB_PANEL_E6A.equals(productId) || ProductId.ID_KNOB_PANEL_E6T.equals(productId)) ? 30 : 31;
    }

    public void setKeyAction(String instruct) {
        final String str = instruct + E6Helper$$ExternalSyntheticBackport0.m("0", Math.max(0, 16 - instruct.length()));
        getCmdHelper().setE6Action(ActivityUtils.getTopActivity(), str, new IAction() { // from class: com.ltech.smarthome.ui.device.e6knob.E6Helper$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                E6Helper.this.lambda$setKeyAction$0(str, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setKeyAction$0(String str, Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupIndexData((LifecycleOwner) ActivityUtils.getTopActivity(), this.controlDevice.getDeviceId(), UpdateBackDataRequest.CONTROL_ACTION, getCmdHelper().setE6Action(str), this.keyPosition);
            E6ExtParam.E6Action action = this.extParam.getAction(this.keyPosition);
            action.setActionInstruct(str);
            this.extParam.setAction(this.keyPosition, action);
            updateParamExt(this.extParam, true);
        }
    }

    public void selectColor(int red, int green, int blue, int wyBrt, int wy, int rgbBrt) {
        String str = StringUtils.demToHexDouble(28) + StringUtils.demToHexDouble(red) + StringUtils.demToHexDouble(green) + StringUtils.demToHexDouble(blue) + StringUtils.demToHexDouble(wyBrt) + StringUtils.demToHexDouble(wy) + StringUtils.demToHexDouble(rgbBrt);
        this.actionInstruct = str;
        setKeyAction(str);
    }

    public void selectTheme(int num) {
        String str = StringUtils.demToHexDouble(29) + StringUtils.demToHexDouble(num);
        this.actionInstruct = str;
        setKeyAction(str);
    }

    public void selectGeneralMode(int num) {
        String str = StringUtils.demToHexDouble(31) + StringUtils.demToHexDouble(num);
        this.actionInstruct = str;
        setKeyAction(str);
    }

    public void selectAdvanceMode(int num) {
        String str = StringUtils.demToHexDouble(30) + StringUtils.demToHexDouble(num);
        this.actionInstruct = str;
        setKeyAction(str);
    }

    public void selectScene(int num) {
        String str = StringUtils.demToHexDouble(12) + StringUtils.demToHexDouble(num);
        this.actionInstruct = str;
        setKeyAction(str);
    }

    public void selectXy(float x, float y, int brt) {
        int round = Math.round((x / 1000.0f) * 65535.0f);
        int round2 = Math.round((y / 1000.0f) * 65535.0f);
        String str = StringUtils.demToHexDouble(32) + StringUtils.demToHexDouble(round % 256) + StringUtils.demToHexDouble(round / 256) + StringUtils.demToHexDouble(round2 % 256) + StringUtils.demToHexDouble(round2 / 256) + StringUtils.demToHexDouble(brt);
        this.actionInstruct = str;
        setKeyAction(str);
    }

    public void selectRgbwaf(int r, int g, int b2, int w, int a2, int f, int brt) {
        String str = StringUtils.demToHexDouble(33) + StringUtils.demToHexDouble(r) + StringUtils.demToHexDouble(g) + StringUtils.demToHexDouble(b2) + StringUtils.demToHexDouble(w) + StringUtils.demToHexDouble(a2) + StringUtils.demToHexDouble(f) + StringUtils.demToHexDouble(brt);
        this.actionInstruct = str;
        setKeyAction(str);
    }

    public void selectDmxChannel(int[] channel) {
        String str = StringUtils.demToHexDouble(34) + StringUtils.demToHexDouble(channel[0]) + StringUtils.demToHexDouble(channel[1]) + StringUtils.demToHexDouble(channel[2]) + StringUtils.demToHexDouble(channel[3]) + StringUtils.demToHexDouble(channel[4]);
        this.actionInstruct = str;
        setKeyAction(str);
    }

    public void selectLightOff() {
        String demToHexDouble = StringUtils.demToHexDouble(1);
        this.actionInstruct = demToHexDouble;
        setKeyAction(demToHexDouble);
    }

    public void updateParamExt(final E6ExtParam extParam, final boolean selectAction) {
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.controlDevice.getDeviceId(), extParam.getParamString()).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((AppCompatActivity) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.e6knob.E6Helper$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                E6Helper.this.lambda$updateParamExt$1(extParam, selectAction, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateParamExt$1(E6ExtParam e6ExtParam, boolean z, Object obj) throws Exception {
        this.controlDevice.setExtParam(e6ExtParam.getParamString());
        Injection.repo().device().saveDevice(this.controlDevice);
        if (z) {
            ActivityUtils.getTopActivity().setResult(3001);
        }
        ActivityUtils.getTopActivity().finish();
    }

    public String getTypeName(Context context, int lightType) {
        if (lightType == 1) {
            return context.getString(R.string.type_dim);
        }
        if (lightType == 2) {
            return context.getString(R.string.type_ct);
        }
        if (lightType == 3) {
            return context.getString(R.string.type_rgb);
        }
        if (lightType == 4) {
            return context.getString(R.string.type_rgbw);
        }
        if (lightType == 5) {
            return context.getString(R.string.type_rgbwy);
        }
        return context.getString(R.string.type_ct);
    }

    public int[] getObjectArray(E6ExtParam extParam, int position) {
        int[] iArr = {0, 0};
        if (!TextUtils.isEmpty(extParam.getAction(position).getObjectInstruct())) {
            iArr[0] = Integer.parseInt(extParam.getAction(position).getObjectInstruct().substring(0, 2), 16);
            iArr[1] = Integer.parseInt(extParam.getAction(position).getObjectInstruct().substring(2), 16);
        }
        return iArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0095  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void goSelectActionById(int r4, int r5) {
        /*
            r3 = this;
            r0 = 1
            if (r5 == r0) goto L8f
            r1 = 12
            if (r5 == r1) goto L88
            java.lang.String r1 = "product_id"
            java.lang.String r2 = "light_type"
            switch(r5) {
                case 28: goto L6a;
                case 29: goto L55;
                case 30: goto L40;
                case 31: goto L2b;
                case 32: goto L24;
                case 33: goto L1c;
                case 34: goto L10;
                default: goto Le;
            }
        Le:
            goto L92
        L10:
            java.lang.Class<com.ltech.smarthome.ui.scene.ActDmxChannelSelect> r5 = com.ltech.smarthome.ui.scene.ActDmxChannelSelect.class
            com.ltech.smarthome.utils.NavUtils$Builder r5 = com.ltech.smarthome.utils.NavUtils.destination(r5)
            com.ltech.smarthome.utils.NavUtils$Builder r4 = r5.withInt(r2, r4)
            goto L93
        L1c:
            java.lang.Class<com.ltech.smarthome.ui.scene.ActRgbwafSelect> r4 = com.ltech.smarthome.ui.scene.ActRgbwafSelect.class
            com.ltech.smarthome.utils.NavUtils$Builder r4 = com.ltech.smarthome.utils.NavUtils.destination(r4)
            goto L93
        L24:
            java.lang.Class<com.ltech.smarthome.ui.scene.ActXySelect> r4 = com.ltech.smarthome.ui.scene.ActXySelect.class
            com.ltech.smarthome.utils.NavUtils$Builder r4 = com.ltech.smarthome.utils.NavUtils.destination(r4)
            goto L93
        L2b:
            java.lang.Class<com.ltech.smarthome.ui.scene.ActSelectDefaultMode> r5 = com.ltech.smarthome.ui.scene.ActSelectDefaultMode.class
            com.ltech.smarthome.utils.NavUtils$Builder r5 = com.ltech.smarthome.utils.NavUtils.destination(r5)
            com.ltech.smarthome.utils.NavUtils$Builder r4 = r5.withInt(r2, r4)
            com.ltech.smarthome.model.bean.Device r5 = r3.controlDevice
            java.lang.String r5 = r5.getProductId()
            com.ltech.smarthome.utils.NavUtils$Builder r4 = r4.withString(r1, r5)
            goto L93
        L40:
            java.lang.Class<com.ltech.smarthome.ui.scene.ActSelectDiyMode> r5 = com.ltech.smarthome.ui.scene.ActSelectDiyMode.class
            com.ltech.smarthome.utils.NavUtils$Builder r5 = com.ltech.smarthome.utils.NavUtils.destination(r5)
            com.ltech.smarthome.utils.NavUtils$Builder r4 = r5.withInt(r2, r4)
            com.ltech.smarthome.model.bean.Device r5 = r3.controlDevice
            java.lang.String r5 = r5.getProductId()
            com.ltech.smarthome.utils.NavUtils$Builder r4 = r4.withString(r1, r5)
            goto L93
        L55:
            java.lang.Class<com.ltech.smarthome.ui.scene.ActSelectThemeMode> r5 = com.ltech.smarthome.ui.scene.ActSelectThemeMode.class
            com.ltech.smarthome.utils.NavUtils$Builder r5 = com.ltech.smarthome.utils.NavUtils.destination(r5)
            com.ltech.smarthome.utils.NavUtils$Builder r4 = r5.withInt(r2, r4)
            com.ltech.smarthome.model.bean.Device r5 = r3.controlDevice
            java.lang.String r5 = r5.getProductId()
            com.ltech.smarthome.utils.NavUtils$Builder r4 = r4.withString(r1, r5)
            goto L93
        L6a:
            if (r4 != r0) goto L73
            java.lang.Class<com.ltech.smarthome.ui.scene.ActDimSelectColor> r5 = com.ltech.smarthome.ui.scene.ActDimSelectColor.class
            com.ltech.smarthome.utils.NavUtils$Builder r5 = com.ltech.smarthome.utils.NavUtils.destination(r5)
            goto L83
        L73:
            r5 = 2
            if (r4 != r5) goto L7d
            java.lang.Class<com.ltech.smarthome.ui.scene.ActCtSelectColor> r5 = com.ltech.smarthome.ui.scene.ActCtSelectColor.class
            com.ltech.smarthome.utils.NavUtils$Builder r5 = com.ltech.smarthome.utils.NavUtils.destination(r5)
            goto L83
        L7d:
            java.lang.Class<com.ltech.smarthome.ui.scene.ActSelectColor> r5 = com.ltech.smarthome.ui.scene.ActSelectColor.class
            com.ltech.smarthome.utils.NavUtils$Builder r5 = com.ltech.smarthome.utils.NavUtils.destination(r5)
        L83:
            r5.withInt(r2, r4)
            r4 = r5
            goto L93
        L88:
            java.lang.Class<com.ltech.smarthome.ui.device.e6knob.ActSelectE6Scene> r4 = com.ltech.smarthome.ui.device.e6knob.ActSelectE6Scene.class
            com.ltech.smarthome.utils.NavUtils$Builder r4 = com.ltech.smarthome.utils.NavUtils.destination(r4)
            goto L93
        L8f:
            r3.selectLightOff()
        L92:
            r4 = 0
        L93:
            if (r4 == 0) goto La6
            java.lang.String r5 = "is_e6"
            com.ltech.smarthome.utils.NavUtils$Builder r4 = r4.withBoolean(r5, r0)
            com.ltech.smarthome.utils.NavUtils$Builder r4 = r4.withDefaultRequestCode()
            android.app.Activity r5 = com.blankj.utilcode.util.ActivityUtils.getTopActivity()
            r4.navigation(r5)
        La6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.e6knob.E6Helper.goSelectActionById(int, int):void");
    }

    public void showSceneKeyDialog(final FragmentActivity activity) {
        SelectListAndPicDialog.asDefault(false).setTitle(activity.getString(R.string.action_scene_tip)).setCancelString(activity.getString(R.string.cancel)).setSelectList(Arrays.asList(activity.getResources().getStringArray(R.array.smart_scene_panel_s8_key_select)).subList(0, 4)).setPicRes(R.mipmap.pic_e6_auto).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.e6knob.E6Helper$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                E6Helper.lambda$showSceneKeyDialog$2(FragmentActivity.this, (Integer) obj);
            }
        }).showDialog(activity);
    }

    static /* synthetic */ void lambda$showSceneKeyDialog$2(FragmentActivity fragmentActivity, Integer num) {
        SceneCmdParam sceneCmdParam = new SceneCmdParam();
        sceneCmdParam.setCmdType(13);
        sceneCmdParam.setPosition(num.intValue() + 1);
        sceneCmdParam.addExtParam(SceneHelper.OPTION, "12");
        sceneCmdParam.addExtParam(SceneHelper.OPTION_VALUE, String.valueOf(num.intValue() + 1));
        SceneHelper.instance().cmdParam = sceneCmdParam;
        fragmentActivity.setResult(3001);
        fragmentActivity.finish();
    }

    public void initKnobInfoView(final Context context, RecyclerView recyclerView, int type) {
        new BaseQuickAdapter<ActKnobPanelVM.ActionItem, BaseViewHolder>(this, R.layout.item_sq_panel_action, getActionList(context, type)) { // from class: com.ltech.smarthome.ui.device.e6knob.E6Helper.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, ActKnobPanelVM.ActionItem item) {
                helper.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                helper.getView(R.id.layout_item_bg).setBackgroundResource(R.drawable.shape_light_gray_bt_10_2);
                helper.setImageResource(R.id.iv_action, item.iconRes).setText(R.id.tv_action, item.actionName).setText(R.id.tv_action_tip, item.actionInfo).setGone(R.id.tv_action_tip, true).setGone(R.id.tv_action_1, false);
            }
        }.bindToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.e6knob.E6Helper.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(SizeUtils.dp2px(5.0f), SizeUtils.dp2px(5.0f), SizeUtils.dp2px(5.0f), SizeUtils.dp2px(5.0f));
            }
        });
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private List<ActKnobPanelVM.ActionItem> getActionList(Context context, int lightType) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 4; i++) {
            ActKnobPanelVM.ActionItem actionItem = new ActKnobPanelVM.ActionItem();
            actionItem.actionInfo = getRelateActionNameRes(context, lightType)[i];
            actionItem.iconRes = ICON_ARRAY[i];
            actionItem.actionName = context.getString(ACTION_ARRAY[i]);
            arrayList.add(actionItem);
        }
        return arrayList;
    }

    private String[] getRelateActionNameRes(Context context, int lightType) {
        if (lightType == 1) {
            return context.getResources().getStringArray(R.array.sq_dim_action_array);
        }
        if (lightType == 2) {
            return context.getResources().getStringArray(R.array.sq_ct_action_array);
        }
        if (lightType == 3) {
            return context.getResources().getStringArray(R.array.sq_rgb_action_array);
        }
        if (lightType == 4) {
            return context.getResources().getStringArray(R.array.sq_rgbw_action_array);
        }
        if (lightType == 5) {
            return context.getResources().getStringArray(R.array.sq_rgbwy_action_array);
        }
        return context.getResources().getStringArray(R.array.sq_ct_action_array);
    }
}