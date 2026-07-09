package com.ltech.smarthome.ui.device.r8;

import android.text.TextUtils;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.key.KeyInfo;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.device.KeyInfoResponse;
import com.ltech.smarthome.singleton.ComboCmdHelper;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActRc4sVM extends BaseViewModel {
    public long controlId;
    public int controlType;
    public Device device;
    public long placeId;
    public String productId;
    public MutableLiveData<List<BindContent>> deviceData = new MutableLiveData<>();
    public MutableLiveData<List<BindContent>> sceneData = new MutableLiveData<>();
    public MutableLiveData<Object> controlObject = new MutableLiveData<>();
    public MutableLiveData<Integer> index = new MutableLiveData<>(0);

    public void initDefaultList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BindContent(getContext().getString(R.string.zone) + "1", 2, 0));
        arrayList.add(new BindContent(getContext().getString(R.string.zone) + "2", 2, 1));
        arrayList.add(new BindContent(getContext().getString(R.string.zone) + "3", 2, 2));
        arrayList.add(new BindContent(getContext().getString(R.string.zone) + "4", 2, 3));
        this.deviceData.setValue(arrayList);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new BindContent(StringUtils.getString(R.string.scene_a), 1, R.mipmap.ic_a, 4));
        arrayList2.add(new BindContent(StringUtils.getString(R.string.scene_b), 1, R.mipmap.ic_b, 5));
        arrayList2.add(new BindContent(StringUtils.getString(R.string.scene_c), 1, R.mipmap.ic_c, 6));
        this.sceneData.setValue(arrayList2);
    }

    public void refreshRelateInfoList() {
        ((ObservableSubscribeProxy) Injection.net().getKeyInfo(this.device.getDeviceId()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4sVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActRc4sVM.this.lambda$refreshRelateInfoList$0((Disposable) obj);
            }
        }).doFinally(new ActRc4sVM$$ExternalSyntheticLambda2(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4sVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActRc4sVM.this.lambda$refreshRelateInfoList$1((KeyInfoResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshRelateInfoList$0(Disposable disposable) throws Exception {
        showLoadingDialog("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshRelateInfoList$1(KeyInfoResponse keyInfoResponse) throws Exception {
        String name;
        String name2;
        String name3;
        String name4;
        String name5;
        String name6;
        String str;
        String str2;
        ComboCmdHelper.getInstance().setKeyData(keyInfoResponse.getRows());
        Object relateObject = ComboCmdHelper.getInstance().getRelateObject(1);
        Role role = relateObject instanceof Role ? (Role) relateObject : null;
        Object relateObject2 = ComboCmdHelper.getInstance().getRelateObject(2);
        Role role2 = relateObject2 instanceof Role ? (Role) relateObject2 : null;
        Object relateObject3 = ComboCmdHelper.getInstance().getRelateObject(3);
        Role role3 = relateObject3 instanceof Role ? (Role) relateObject3 : null;
        Object relateObject4 = ComboCmdHelper.getInstance().getRelateObject(4);
        Role role4 = relateObject4 instanceof Role ? (Role) relateObject4 : null;
        Object relateObject5 = ComboCmdHelper.getInstance().getRelateObject(5);
        Role role5 = relateObject5 instanceof Role ? (Role) relateObject5 : null;
        Object relateObject6 = ComboCmdHelper.getInstance().getRelateObject(6);
        Role role6 = relateObject6 instanceof Role ? (Role) relateObject6 : null;
        Object relateObject7 = ComboCmdHelper.getInstance().getRelateObject(7);
        Role role7 = relateObject7 instanceof Role ? (Role) relateObject7 : null;
        String str3 = "";
        if (StringUtils.isEmpty(ComboCmdHelper.getInstance().getRelateName(1))) {
            name = role != null ? role.getName() : "";
        } else {
            name = ComboCmdHelper.getInstance().getRelateName(1);
        }
        if (StringUtils.isEmpty(ComboCmdHelper.getInstance().getRelateName(2))) {
            name2 = role2 != null ? role2.getName() : "";
        } else {
            name2 = ComboCmdHelper.getInstance().getRelateName(2);
        }
        if (StringUtils.isEmpty(ComboCmdHelper.getInstance().getRelateName(3))) {
            name3 = role3 != null ? role3.getName() : "";
        } else {
            name3 = ComboCmdHelper.getInstance().getRelateName(3);
        }
        if (StringUtils.isEmpty(ComboCmdHelper.getInstance().getRelateName(4))) {
            name4 = role4 != null ? role4.getName() : "";
        } else {
            name4 = ComboCmdHelper.getInstance().getRelateName(4);
        }
        if (StringUtils.isEmpty(ComboCmdHelper.getInstance().getRelateName(5))) {
            name5 = role5 != null ? role5.getName() : "";
        } else {
            name5 = ComboCmdHelper.getInstance().getRelateName(5);
        }
        if (StringUtils.isEmpty(ComboCmdHelper.getInstance().getRelateName(6))) {
            name6 = role6 != null ? role6.getName() : "";
        } else {
            name6 = ComboCmdHelper.getInstance().getRelateName(6);
        }
        if (!StringUtils.isEmpty(ComboCmdHelper.getInstance().getRelateName(7))) {
            str3 = ComboCmdHelper.getInstance().getRelateName(7);
        } else if (role7 != null) {
            str3 = role7.getName();
        }
        String str4 = str3;
        ArrayList arrayList = new ArrayList();
        if (name == null || TextUtils.isEmpty(name)) {
            StringBuilder sb = new StringBuilder();
            str = name2;
            sb.append(getContext().getString(R.string.zone));
            sb.append("1");
            name = sb.toString();
        } else {
            str = name2;
        }
        arrayList.add(new BindContent(name, 2, 0, role));
        if (str == null || TextUtils.isEmpty(str)) {
            str2 = getContext().getString(R.string.zone) + "2";
        } else {
            str2 = str;
        }
        arrayList.add(new BindContent(str2, 2, 1, role2));
        if (name3 == null || TextUtils.isEmpty(name3)) {
            name3 = getContext().getString(R.string.zone) + "3";
        }
        arrayList.add(new BindContent(name3, 2, 2, role3));
        if (name4 == null || TextUtils.isEmpty(name4)) {
            name4 = getContext().getString(R.string.zone) + "4";
        }
        arrayList.add(new BindContent(name4, 2, 3, role4));
        this.deviceData.setValue(arrayList);
        ArrayList arrayList2 = new ArrayList();
        if (name5 == null || TextUtils.isEmpty(name5)) {
            name5 = StringUtils.getString(R.string.scene_a);
        }
        Role role8 = role6;
        arrayList2.add(new BindContent(name5, 1, role5 instanceof Scene ? SceneHelper.getSceneIcon(getContext(), ((Scene) role5).getIconPos()) : R.mipmap.ic_a, 4, role5));
        if (name6 == null || TextUtils.isEmpty(name6)) {
            name6 = StringUtils.getString(R.string.scene_b);
        }
        arrayList2.add(new BindContent(name6, 1, role8 instanceof Scene ? SceneHelper.getSceneIcon(getContext(), ((Scene) role8).getIconPos()) : R.mipmap.ic_b, 5, role8));
        arrayList2.add(new BindContent((str4 == null || TextUtils.isEmpty(str4)) ? StringUtils.getString(R.string.scene_c) : str4, 1, role7 instanceof Scene ? SceneHelper.getSceneIcon(getContext(), ((Scene) role7).getIconPos()) : R.mipmap.ic_c, 6, role7));
        this.sceneData.setValue(arrayList2);
    }

    public int getSelIndex() {
        if (this.index.getValue() != null) {
            return this.index.getValue().intValue();
        }
        return 0;
    }

    public boolean isBind(int index) {
        int i = index + 1;
        Object relateObject = ComboCmdHelper.getInstance().getRelateObject(i);
        return (ComboCmdHelper.getInstance().getKeysByZone(i).isEmpty() || ComboCmdHelper.getInstance().getKeysByZone(i).get(0).getObjectId() == 0 || TextUtils.isEmpty(relateObject instanceof Role ? ((Role) relateObject).getName() : "")) ? false : true;
    }

    public void uploadData(final String name, final Object object) {
        ((ObservableSubscribeProxy) Injection.net().bindKeyInfo(this.device.getDeviceId(), ComboCmdHelper.getInstance().getKeysByZone(getSelIndex() + 1)).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4sVM$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActRc4sVM.this.lambda$uploadData$2((Disposable) obj);
            }
        }).doFinally(new ActRc4sVM$$ExternalSyntheticLambda2(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4sVM$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActRc4sVM.this.lambda$uploadData$3(name, object, (List) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$2(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$3(String str, Object obj, List list) throws Exception {
        int selIndex = getSelIndex();
        if (selIndex > 3) {
            int selIndex2 = getSelIndex() - 4;
            List<BindContent> value = this.sceneData.getValue();
            if (value != null && value.size() > selIndex2) {
                BindContent bindContent = value.get(selIndex2);
                bindContent.setName(str);
                if (obj instanceof Role) {
                    bindContent.setRole((Role) obj);
                }
                value.set(selIndex2, bindContent);
                this.sceneData.setValue(value);
            }
        } else {
            List<BindContent> value2 = this.deviceData.getValue();
            if (value2 != null && value2.size() > selIndex) {
                BindContent bindContent2 = value2.get(selIndex);
                bindContent2.setName(str);
                if (obj instanceof Role) {
                    bindContent2.setRole((Role) obj);
                }
                value2.set(selIndex, bindContent2);
                this.deviceData.setValue(value2);
            }
        }
        ComboCmdHelper.getInstance().setKeyData(getSelIndex() + 1, list);
    }

    public void changeName(final String name) {
        final List<KeyInfo> keysByZone = ComboCmdHelper.getInstance().getKeysByZone(getSelIndex() + 1);
        final KeyInfo keyInfo = keysByZone.get(0);
        keyInfo.setName(name);
        ((ObservableSubscribeProxy) Injection.net().bindKeyInfo(this.device.getDeviceId(), keyInfo).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4sVM$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActRc4sVM.this.lambda$changeName$4((Disposable) obj);
            }
        }).doFinally(new ActRc4sVM$$ExternalSyntheticLambda2(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4sVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActRc4sVM.this.lambda$changeName$5(name, keysByZone, keyInfo, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeName$4(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeName$5(String str, List list, KeyInfo keyInfo, Object obj) throws Exception {
        int selIndex = getSelIndex();
        if (selIndex > 3) {
            int selIndex2 = getSelIndex() - 4;
            List<BindContent> value = this.sceneData.getValue();
            if (value != null && value.size() > selIndex2) {
                BindContent bindContent = value.get(selIndex2);
                bindContent.setName(str);
                value.set(selIndex2, bindContent);
                this.sceneData.setValue(value);
            }
        } else {
            List<BindContent> value2 = this.deviceData.getValue();
            if (value2 != null && value2.size() > selIndex) {
                BindContent bindContent2 = value2.get(selIndex);
                bindContent2.setName(str);
                value2.set(selIndex, bindContent2);
                this.deviceData.setValue(value2);
            }
        }
        list.set(0, keyInfo);
        ComboCmdHelper.getInstance().setKeyData(getSelIndex() + 1, list);
    }

    public void unBindData() {
        ((ObservableSubscribeProxy) Injection.net().unbindKeyInfo(this.device.getDeviceId(), getSelIndex() + 1).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4sVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActRc4sVM.this.lambda$unBindData$6((Disposable) obj);
            }
        }).doFinally(new ActRc4sVM$$ExternalSyntheticLambda2(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4sVM$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActRc4sVM.this.lambda$unBindData$7(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unBindData$6(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unBindData$7(Object obj) throws Exception {
        int selIndex = getSelIndex();
        if (selIndex > 3) {
            int selIndex2 = getSelIndex() - 4;
            List<BindContent> value = this.sceneData.getValue();
            if (value != null && value.size() > selIndex2) {
                BindContent bindContent = value.get(selIndex2);
                if (selIndex2 == 0) {
                    bindContent.setName(StringUtils.getString(R.string.scene_a));
                    bindContent.setImg(R.mipmap.ic_a);
                } else if (selIndex2 == 1) {
                    bindContent.setName(StringUtils.getString(R.string.scene_b));
                    bindContent.setImg(R.mipmap.ic_b);
                } else if (selIndex2 == 2) {
                    bindContent.setName(StringUtils.getString(R.string.scene_c));
                    bindContent.setImg(R.mipmap.ic_c);
                }
                value.set(selIndex2, bindContent);
                this.sceneData.setValue(value);
            }
        } else {
            List<BindContent> value2 = this.deviceData.getValue();
            if (value2 != null && value2.size() > selIndex) {
                BindContent bindContent2 = value2.get(selIndex);
                bindContent2.setName(getContext().getString(R.string.zone) + (selIndex + 1));
                value2.set(selIndex, bindContent2);
                this.deviceData.setValue(value2);
            }
        }
        showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
    }

    public void uploadData(int type) {
        Observable<Object> bindKeyInfo;
        dismissLoadingDialog();
        if (type == 0) {
            bindKeyInfo = Injection.net().bindKeyInfo(this.device.getDeviceId(), ComboCmdHelper.getInstance().getKeyShort(getSelIndex() + 1));
        } else if (type == 1) {
            bindKeyInfo = Injection.net().bindKeyInfo(this.device.getDeviceId(), ComboCmdHelper.getInstance().getKeyKnob(getSelIndex() + 1));
        } else {
            bindKeyInfo = type == 2 ? Injection.net().bindKeyInfo(this.device.getDeviceId(), ComboCmdHelper.getInstance().getKeyLong(getSelIndex() + 1)) : null;
        }
        if (bindKeyInfo != null) {
            ((ObservableSubscribeProxy) bindKeyInfo.compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4sVM$$ExternalSyntheticLambda8
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActRc4sVM.this.lambda$uploadData$8((Disposable) obj);
                }
            }).doFinally(new ActRc4sVM$$ExternalSyntheticLambda2(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4sVM$$ExternalSyntheticLambda9
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActRc4sVM.this.lambda$uploadData$9(obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$8(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$9(Object obj) throws Exception {
        showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
    }

    public Place getCurPlace() {
        return Injection.repo().home().getSelPlace();
    }

    public static class BindContent {
        private int img;
        private int index;
        private String name;
        private Role role;
        private boolean showTip;
        private int type;

        public BindContent(String name, int type, boolean showTip) {
            this.name = name;
            this.type = type;
            this.showTip = showTip;
        }

        public BindContent(String name, int type, int img, int index) {
            this.name = name;
            this.type = type;
            this.img = img;
            this.index = index;
        }

        public BindContent(String name, int type, int index, Role role) {
            this.name = name;
            this.type = type;
            this.index = index;
            this.role = role;
        }

        public BindContent(String name, int type, int index) {
            this.name = name;
            this.type = type;
            this.index = index;
        }

        public BindContent(String name, int type, int img, int index, Role role) {
            this.name = name;
            this.type = type;
            this.img = img;
            this.index = index;
            this.role = role;
        }

        public Role getRole() {
            return this.role;
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getImg() {
            return this.img;
        }

        public void setImg(int img) {
            this.img = img;
        }

        public boolean isShowTip() {
            return this.showTip;
        }

        public void setShowTip(boolean showTip) {
            this.showTip = showTip;
        }
    }
}