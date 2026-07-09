package com.ltech.smarthome.ui.device.light;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.common.primitives.Ints;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.GradientScene;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.group.ActSortGroupDevice;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.message.base.BaseCmd;
import com.smart.message.base.BaseCmdParam;
import com.smart.message.utils.StringUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActColorLightVM extends BaseViewModel {
    public String bgUrl;
    public int colorType;
    public long controlId;
    public boolean groupControl;
    public MutableLiveData<Object> controlObject = new MutableLiveData<>();
    public MutableLiveData<Boolean> stateOn = new MutableLiveData<>();
    public MutableLiveData<Boolean> colorControlLiveData = new MutableLiveData<>(true);
    public SingleLiveEvent<Void> showWyDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showWDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> ChangeFunctionEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> stateOnUI = new MutableLiveData<>();
    public MutableLiveData<Boolean> gradientMode = new MutableLiveData<>(false);
    public SingleLiveEvent<Void> colorBrtControlDialogEvent = new SingleLiveEvent<>();
    public MutableLiveData<Bitmap> paletteBitmap = new MutableLiveData<>();
    public SingleLiveEvent<Void> showTakePicEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showPhotoAlbumEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> addGradientSceneEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> lightControlEvent = new SingleLiveEvent<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.light.ActColorLightVM$$ExternalSyntheticLambda3
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActColorLightVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.iv_change_brt /* 2131296972 */:
                this.colorBrtControlDialogEvent.call();
                break;
            case R.id.iv_change_pic /* 2131296973 */:
                showPicBgDialog();
                break;
            case R.id.iv_ct /* 2131296993 */:
                MutableLiveData<Boolean> mutableLiveData = this.colorControlLiveData;
                mutableLiveData.setValue(Boolean.valueOf(true ^ mutableLiveData.getValue().booleanValue()));
                break;
            case R.id.iv_function /* 2131297064 */:
                this.ChangeFunctionEvent.call();
                break;
            case R.id.iv_gradient /* 2131297081 */:
                this.gradientMode.setValue(true);
                break;
            case R.id.iv_normal /* 2131297158 */:
                this.gradientMode.setValue(false);
                break;
            case R.id.iv_open /* 2131297166 */:
                getLightCmdHelper().sendOnOff(ActivityUtils.getTopActivity(), true);
                this.stateOn.setValue(true);
                break;
            case R.id.iv_sort /* 2131297260 */:
                showSort();
                break;
            case R.id.iv_wy /* 2131297321 */:
                if (4 == this.colorType) {
                    this.showWDialogEvent.call();
                    break;
                } else {
                    this.showWyDialogEvent.call();
                    break;
                }
        }
    }

    public void showSort() {
        Object value = this.controlObject.getValue();
        if (value instanceof Group) {
            Group group = (Group) value;
            int size = group.getDeviceIds().size();
            long[] jArr = new long[size];
            for (int i = 0; i < size; i++) {
                jArr[i] = group.getDeviceIds().get(i).longValue();
            }
            NavUtils.destination(ActSortGroupDevice.class).withLong(Constants.GROUP_ID, group.getGroupId()).withLongArray(Constants.ROLE_ID_ARRAY, jArr).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
        }
    }

    public void showPicBgDialog() {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getContext().getString(R.string.please_choose)).setCancelString(getContext().getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getContext().getString(R.string.app_str_gradient_bg_1));
        arrayList.add(getContext().getString(R.string.app_str_gradient_bg_2));
        if (this.bgUrl != null) {
            arrayList.add(getContext().getString(R.string.app_str_gradient_bg_3));
        }
        arrayList.add(getContext().getString(R.string.camera));
        arrayList.add(getContext().getString(R.string.select_pic));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActColorLightVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActColorLightVM.this.lambda$showPicBgDialog$1((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPicBgDialog$1(Integer num) {
        int intValue = num.intValue();
        if (intValue == 0) {
            this.paletteBitmap.setValue(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.bg_palette_1));
            setColorPaletteType(1);
            return;
        }
        if (intValue == 1) {
            this.paletteBitmap.setValue(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.bg_palette_2));
            setColorPaletteType(2);
            return;
        }
        if (intValue == 2) {
            if (this.bgUrl != null) {
                Glide.with(getContext()).asBitmap().load(this.bgUrl).into((RequestBuilder<Bitmap>) new CustomTarget<Bitmap>() { // from class: com.ltech.smarthome.ui.device.light.ActColorLightVM.1
                    @Override // com.bumptech.glide.request.target.Target
                    public void onLoadCleared(Drawable placeholder) {
                    }

                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(Object bitmap, Transition transition) {
                        onResourceReady((Bitmap) bitmap, (Transition<? super Bitmap>) transition);
                    }

                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        ActColorLightVM.this.paletteBitmap.setValue(bitmap);
                    }
                });
                return;
            } else {
                this.showTakePicEvent.call();
                return;
            }
        }
        if (intValue != 3) {
            if (intValue != 4) {
                return;
            }
            this.showPhotoAlbumEvent.call();
        } else if (this.bgUrl != null) {
            this.showTakePicEvent.call();
        } else {
            this.showPhotoAlbumEvent.call();
        }
    }

    public int getColorPaletteType() {
        Object value = this.controlObject.getValue();
        if (!(value instanceof Group)) {
            return 0;
        }
        return SPUtils.getInstance().getInt("colorpalettetype#" + ((Group) value).getGroupId(), 1);
    }

    public void setColorPaletteType(int colorPaletteType) {
        Object value = this.controlObject.getValue();
        if (value instanceof Group) {
            SPUtils.getInstance().put("colorpalettetype#" + ((Group) value).getGroupId(), colorPaletteType);
        }
    }

    public LightAssistant getLightCmdHelper() {
        return CmdAssistant.getLightCmdAssistant(this.controlObject.getValue(), new int[0]);
    }

    public LightAssistant getLightCmdHelper(Object o) {
        return CmdAssistant.getLightCmdAssistant(o, new int[0]);
    }

    public List<Integer> getColorList(Context context) {
        return Ints.asList(context.getResources().getIntArray(R.array.static_default_color));
    }

    public List<Integer> getCtList(Context context) {
        return Ints.asList(context.getResources().getIntArray(R.array.static_default_ct));
    }

    public void createGradientScene(String s, List<Item> data) {
        String convertCmd;
        Object value = this.controlObject.getValue();
        if (value instanceof Group) {
            GradientScene gradientScene = new GradientScene();
            gradientScene.setGroupid(((Group) value).getGroupId());
            gradientScene.setGsname(s);
            String str = this.bgUrl;
            if (str == null) {
                str = String.valueOf(getColorPaletteType());
            }
            gradientScene.setImgurl(str);
            ArrayList arrayList = new ArrayList();
            for (Item item : data) {
                Device device = item.getDevice();
                if (item.on) {
                    if (ProductRepository.getLightColorType((Object) device) == 20) {
                        convertCmd = getConvertCmd(CmdAssistant.getLightCmdAssistant(device, new int[0]).sentColor(device.getDeviceState().getRed(), device.getDeviceState().getGreen(), device.getDeviceState().getBlue(), device.getDeviceState().isRGBOn() ? device.getDeviceState().getRgbBrt() : 0, device.getDeviceState().getWy(), device.getDeviceState().isWOn() ? device.getDeviceState().getWyBrt() : 0));
                    } else {
                        convertCmd = getConvertCmd(CmdAssistant.getLightCmdAssistant(device, new int[0]).sentColor(device.getDeviceState().getRed(), device.getDeviceState().getGreen(), device.getDeviceState().getBlue(), device.getDeviceState().isRGBOn() ? device.getDeviceState().getRgbBrt() : 0, device.getDeviceState().getWy(), device.getDeviceState().isWOn() ? device.getDeviceState().getWyBrt() : 0));
                    }
                } else {
                    convertCmd = getConvertCmd(CmdAssistant.getLightCmdAssistant(device, new int[0]).sendOnOff(item.on));
                }
                arrayList.add(new GradientScene.GroupSceneInfos(device.getDeviceId(), convertCmd, (int) item.x, (int) item.y, device.getUnicastAddress()));
            }
            gradientScene.setGroupsceneinfos(arrayList);
            ((ObservableSubscribeProxy) Injection.net().addGroupScene(gradientScene).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActColorLightVM$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActColorLightVM.this.lambda$createGradientScene$2(obj);
                }
            }, new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActColorLightVM$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActColorLightVM.this.lambda$createGradientScene$3((Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createGradientScene$2(Object obj) throws Exception {
        SmartToast.showCenterShort(getContext().getString(R.string.save_success));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createGradientScene$3(Throwable th) throws Exception {
        SmartToast.showCenterShort(getContext().getString(R.string.save_fail));
    }

    private String getConvertCmd(BaseCmdParam cmdParam) {
        if (cmdParam != null) {
            BaseCmd convert2cmd = Injection.strategy().getCmdConvertStrategy(2).convert2cmd(cmdParam);
            return StringUtils.demToHexDouble(convert2cmd.getFunCode()) + StringUtils.byte2Str(convert2cmd.value(new Object[0]));
        }
        return "";
    }

    public static class Item {
        private int color;
        private Device device;
        private boolean on;
        private boolean sel;
        private float x;
        private float y;

        public Item(Device device, boolean sel, boolean on) {
            this.device = device;
            this.sel = sel;
            this.on = on;
        }

        public float getX() {
            return this.x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return this.y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public int getColor() {
            return this.color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public boolean isOn() {
            return this.on;
        }

        public void setOn(boolean on) {
            this.on = on;
        }

        public Device getDevice() {
            return this.device;
        }

        public void setDevice(Device device) {
            this.device = device;
        }

        public boolean isSel() {
            return this.sel;
        }

        public void setSel(boolean sel) {
            this.sel = sel;
        }
    }
}