package com.ltech.smarthome.ui.device.eurpanel;

import android.content.Context;
import android.view.View;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelVM;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActEurPanelEb6VM extends ActEurPanelVM {
    public static final int ACTION_NUM = 4;
    public RelatedInfoExtParam.RelateInfo relateInfo;
    public static final int[] ICON_ARRAY_MUSIC = {R.mipmap.pic_sq_click, R.mipmap.pic_sq_double_click_music, R.mipmap.pic_sq_whirl, R.mipmap.pic_sq_press_and_hold_music};
    public static final int[] ACTION_ARRAY_MUSIC = {R.string.single_click, R.string.double_click, R.string.rotate, R.string.press_and_rotate};
    public static final int[] ICON_ARRAY = {R.mipmap.pic_sq_click, R.mipmap.pic_sq_double_click, R.mipmap.pic_sq_whirl, R.mipmap.pic_sq_press_and_hold};
    public static final int[] ACTION_ARRAY = {R.string.single_click, R.string.double_click, R.string.rotate, R.string.long_press};
    public List<ActKnobPanelVM.ActionItem> actionItemList = new ArrayList();
    public SingleLiveEvent<Void> showBindEvent = new SingleLiveEvent<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb6VM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActEurPanelEb6VM.this.lambda$new$0((View) obj);
        }
    });

    public void initRelateInfoList(Device device) {
        RelateInfoUtils.initRelateInfoList(device);
        this.relateInfoAssistant = RelateInfoUtils.relateInfoAssistant;
        RelatedInfoExtParam.RelateInfo relateInfo = this.relateInfoAssistant.getRelateInfo(0);
        this.relateInfo = relateInfo;
        if (relateInfo == null) {
            this.relateInfo = new RelatedInfoExtParam.RelateInfo();
        }
        this.actionItemList.clear();
        for (int i = 0; i < 4; i++) {
            ActKnobPanelVM.ActionItem actionItem = new ActKnobPanelVM.ActionItem();
            if (this.relateInfo.objectId != 0) {
                actionItem.actionInfo = getRelateActionNameRes(ActivityUtils.getTopActivity(), this.relateInfo)[i];
                actionItem.iconRes = getRelateIconRes(this.relateInfo)[i];
                actionItem.actionName = ActivityUtils.getTopActivity().getString(getRelateActionName(this.relateInfo)[i]);
            } else {
                actionItem.iconRes = ICON_ARRAY[i];
                actionItem.actionName = ActivityUtils.getTopActivity().getString(ACTION_ARRAY[i]);
            }
            this.actionItemList.add(actionItem);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (view.getId() != R.id.tv_bind_name) {
            return;
        }
        this.showBindEvent.call();
    }

    private int[] getRelateActionName(RelatedInfoExtParam.RelateInfo relateInfo) {
        if (relateInfo.isRelateDeviceInfo()) {
            if (this.deviceList.getValue() != null) {
                for (Device device : this.deviceList.getValue()) {
                    if (device.getDeviceId() == relateInfo.objectId) {
                        if (device.getProductId().equals(ProductId.ID_BLE_MUSIC_PLAYER)) {
                            return ACTION_ARRAY_MUSIC;
                        }
                        return ACTION_ARRAY;
                    }
                }
            }
        } else if (relateInfo.isRelateGroupInfo() && this.groupList.getValue() != null) {
            Iterator<Group> it = this.groupList.getValue().iterator();
            while (it.hasNext()) {
                if (it.next().getGroupId() == relateInfo.objectId) {
                    return ACTION_ARRAY;
                }
            }
        }
        return ACTION_ARRAY;
    }

    private int[] getRelateIconRes(RelatedInfoExtParam.RelateInfo relateInfo) {
        if (relateInfo.isRelateDeviceInfo()) {
            if (this.deviceList.getValue() != null) {
                for (Device device : this.deviceList.getValue()) {
                    if (device.getDeviceId() == relateInfo.objectId) {
                        if (device.getProductId().equals(ProductId.ID_BLE_MUSIC_PLAYER)) {
                            return ICON_ARRAY_MUSIC;
                        }
                        return ICON_ARRAY;
                    }
                }
            }
        } else if (relateInfo.isRelateGroupInfo() && this.groupList.getValue() != null) {
            Iterator<Group> it = this.groupList.getValue().iterator();
            while (it.hasNext()) {
                if (it.next().getGroupId() == relateInfo.objectId) {
                    return ICON_ARRAY;
                }
            }
        }
        return ICON_ARRAY;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00c9, code lost:
    
        switch(r17) {
            case 0: goto L128;
            case 1: goto L128;
            case 2: goto L128;
            case 3: goto L128;
            case 4: goto L128;
            case 5: goto L127;
            case 6: goto L126;
            case 7: goto L125;
            case 8: goto L127;
            default: goto L131;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00d6, code lost:
    
        return r21.getResources().getStringArray(com.ltech.smarthome.R.array.dry_curtain_action_array);
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00e2, code lost:
    
        return r21.getResources().getStringArray(com.ltech.smarthome.R.array.sq_musicplayer_action_array);
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00eb, code lost:
    
        return r21.getResources().getStringArray(com.ltech.smarthome.R.array.sq_curtain_action_array);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00ec, code lost:
    
        r3 = com.ltech.smarthome.model.repo.ProductRepository.getLightColorType((java.lang.Object) r16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00f4, code lost:
    
        if (com.ltech.smarthome.model.repo.ProductRepository.isDaliLightGroup(r16) == false) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00f6, code lost:
    
        r3 = r22.keyActionExtra - 100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00fa, code lost:
    
        if (r3 == 1) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00fc, code lost:
    
        if (r3 == 2) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00fe, code lost:
    
        if (r3 == 3) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0100, code lost:
    
        if (r3 == 4) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0102, code lost:
    
        if (r3 == 5) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x010c, code lost:
    
        return r21.getResources().getStringArray(com.ltech.smarthome.R.array.sq_ct_action_array);
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0115, code lost:
    
        return r21.getResources().getStringArray(com.ltech.smarthome.R.array.sq_rgbwy_action_array);
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x011e, code lost:
    
        return r21.getResources().getStringArray(com.ltech.smarthome.R.array.sq_rgbw_action_array);
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x012a, code lost:
    
        return r21.getResources().getStringArray(com.ltech.smarthome.R.array.sq_rgb_action_array);
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0133, code lost:
    
        return r21.getResources().getStringArray(com.ltech.smarthome.R.array.sq_ct_action_array);
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x013f, code lost:
    
        return r21.getResources().getStringArray(com.ltech.smarthome.R.array.sq_dim_action_array);
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String[] getRelateActionNameRes(android.content.Context r21, com.ltech.smarthome.model.device_param.RelatedInfoExtParam.RelateInfo r22) {
        /*
            Method dump skipped, instructions count: 610
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb6VM.getRelateActionNameRes(android.content.Context, com.ltech.smarthome.model.device_param.RelatedInfoExtParam$RelateInfo):java.lang.String[]");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00c9, code lost:
    
        switch(r17) {
            case 0: goto L127;
            case 1: goto L127;
            case 2: goto L127;
            case 3: goto L127;
            case 4: goto L127;
            case 5: goto L126;
            case 6: goto L125;
            case 7: goto L124;
            case 8: goto L126;
            default: goto L130;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00d2, code lost:
    
        return r21.getString(com.ltech.smarthome.R.string.dry_curtain_tip);
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00da, code lost:
    
        return r21.getString(com.ltech.smarthome.R.string.musicplayer_tip);
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00df, code lost:
    
        return r21.getString(com.ltech.smarthome.R.string.curtain_tip);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00e0, code lost:
    
        r3 = com.ltech.smarthome.model.repo.ProductRepository.getLightColorType((java.lang.Object) r16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00e8, code lost:
    
        if (com.ltech.smarthome.model.repo.ProductRepository.isDaliLightGroup(r16) == false) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00ea, code lost:
    
        r3 = r22.keyActionExtra - 100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00ee, code lost:
    
        if (r3 == 1) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00f0, code lost:
    
        if (r3 == 2) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00f2, code lost:
    
        if (r3 == 3) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00f4, code lost:
    
        if (r3 == 4) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00f6, code lost:
    
        if (r3 == 5) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00fc, code lost:
    
        return r21.getString(com.ltech.smarthome.R.string.ct_tip);
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0101, code lost:
    
        return r21.getString(com.ltech.smarthome.R.string.rgbwy_tip);
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0106, code lost:
    
        return r21.getString(com.ltech.smarthome.R.string.rgbw_tip);
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x010e, code lost:
    
        return r21.getString(com.ltech.smarthome.R.string.rgb_tip);
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0113, code lost:
    
        return r21.getString(com.ltech.smarthome.R.string.ct_tip);
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x011b, code lost:
    
        return r21.getString(com.ltech.smarthome.R.string.dim_tip);
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getInstructionName(android.content.Context r21, com.ltech.smarthome.model.device_param.RelatedInfoExtParam.RelateInfo r22) {
        /*
            Method dump skipped, instructions count: 504
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb6VM.getInstructionName(android.content.Context, com.ltech.smarthome.model.device_param.RelatedInfoExtParam$RelateInfo):java.lang.String");
    }

    public String getRelateInfoString(Context context, RelatedInfoExtParam.RelateInfo relateInfo) {
        if (relateInfo == null || relateInfo.objectId == 0) {
            return context.getString(R.string.no_bind_object);
        }
        if (relateInfo.isRelateDeviceInfo()) {
            if (this.deviceList.getValue() != null) {
                for (Device device : this.deviceList.getValue()) {
                    if (device.getDeviceId() == relateInfo.objectId) {
                        return device.getDeviceName();
                    }
                }
            }
        } else if (relateInfo.isRelateGroupInfo()) {
            if (this.groupList.getValue() != null) {
                for (Group group : this.groupList.getValue()) {
                    if (group.getGroupId() == relateInfo.objectId) {
                        return group.getGroupName();
                    }
                }
            }
        } else if (relateInfo.isRelateSceneInfo()) {
            for (Scene scene : Injection.repo().scene().getSceneListByPlaceId(this.placeId, true)) {
                if (scene.getSceneId() == relateInfo.objectId) {
                    return scene.getSceneName();
                }
            }
        }
        return context.getString(R.string.no_bind_object);
    }

    public String getDefaultInfoString(int position) {
        String[] strArr;
        int convertType = EurHelper.convertType(this.controlObject.getValue());
        if (convertType == 1) {
            strArr = new String[]{getContext().getString(R.string.brt) + ":25%", getContext().getString(R.string.brt) + ":50%", getContext().getString(R.string.brt) + ":75%", getContext().getString(R.string.brt) + ":100%"};
        } else if (convertType == 2) {
            strArr = new String[]{"CT:2700K\n" + getContext().getString(R.string.brt) + ":25%", "CT:2700K\n" + getContext().getString(R.string.brt) + ":50%", "CT:2700K\n" + getContext().getString(R.string.brt) + ":75%", "CT:2700K\n" + getContext().getString(R.string.brt) + ":100%"};
        } else if (convertType == 3) {
            strArr = new String[]{"#ff0000" + getContext().getString(R.string.brt) + ":100%", "#00ff00" + getContext().getString(R.string.brt) + ":100%", "#0000ff" + getContext().getString(R.string.brt) + ":100%", "#ffffff" + getContext().getString(R.string.brt) + ":100%"};
        } else if (convertType == 4) {
            strArr = new String[]{"#ff0000" + getContext().getString(R.string.brt) + ":100%\nW:25%", "#00ff00" + getContext().getString(R.string.brt) + ":100%\nW:50%", "#0000ff" + getContext().getString(R.string.brt) + ":100%\nW:75%", "#ffffff" + getContext().getString(R.string.brt) + ":100%\nW:100%"};
        } else {
            strArr = new String[]{"#ff0000" + getContext().getString(R.string.brt) + ":100%\nCW:255 " + getContext().getString(R.string.brt) + ":25%", "#00ff00" + getContext().getString(R.string.brt) + ":100%\nCW:255 " + getContext().getString(R.string.brt) + ":50%", "#0000ff" + getContext().getString(R.string.brt) + ":100%\nCW:255 " + getContext().getString(R.string.brt) + ":75%", "#ffffff" + getContext().getString(R.string.brt) + ":100%\nCW:255 " + getContext().getString(R.string.brt) + ":100%"};
        }
        return strArr[position];
    }
}