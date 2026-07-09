package com.ltech.smarthome.ui.device.light;

import androidx.lifecycle.Lifecycle;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActCtEditBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.KValue;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.device.light.CtControlHelper;
import com.ltech.smarthome.ui.mode.ColorArray;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class ActCtEdit extends BaseNormalActivity<ActCtEditBinding> {
    private long controlId;
    private Object controlObject;
    private CtControlHelper ctControlHelper;
    private boolean groupControl;
    private int index;
    private Map<String, KValue> presetKValues = new HashMap();
    private KValue kValue = new KValue();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_ct_edit;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.edit_color));
        setEditString(getString(R.string.save));
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        this.groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        this.index = getIntent().getIntExtra("index", 1);
        if (this.groupControl) {
            Group groupById = Injection.repo().group().getGroupById(this.controlId);
            this.controlObject = groupById;
            this.presetKValues = groupById.getPresetKValues();
        } else {
            Device deviceById = Injection.repo().device().getDeviceById(this.controlId);
            this.controlObject = deviceById;
            this.presetKValues = deviceById.getPresetKValues();
        }
        this.kValue = new KValue();
        initCtView();
    }

    private void initCtView() {
        this.ctControlHelper = new CtControlHelper(((ActCtEditBinding) this.mViewBinding).ctsb, ((ActCtEditBinding) this.mViewBinding).tvWy, this.controlObject, new CtControlHelper.OnCtChangedListener() { // from class: com.ltech.smarthome.ui.device.light.ActCtEdit.1
            @Override // com.ltech.smarthome.ui.device.light.CtControlHelper.OnCtChangedListener
            public void onRangeChanged(int v, boolean finish) {
                int k = ActCtEdit.this.ctControlHelper.getK(v);
                int yOf255 = ActCtEdit.this.ctControlHelper.getYOf255(v);
                String format = String.format("#%02X%02X%02X", Integer.valueOf(ColorArray.CT_COLOR_ARRAY_2[yOf255][0]), Integer.valueOf(ColorArray.CT_COLOR_ARRAY_2[yOf255][1]), Integer.valueOf(ColorArray.CT_COLOR_ARRAY_2[yOf255][2]));
                ActCtEdit.this.kValue.setValue(String.valueOf(k));
                ActCtEdit.this.kValue.setColor(format);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        this.presetKValues.put(String.valueOf(this.index), this.kValue);
        Object obj = this.controlObject;
        if (obj instanceof Group) {
            final Group group = (Group) obj;
            ((ObservableSubscribeProxy) Injection.net().updateGroupPresetKValues(group.getGroupId(), this.presetKValues).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActCtEdit$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj2) {
                    ActCtEdit.this.lambda$edit$0(group, obj2);
                }
            }, new SmartErrorComsumer());
        } else if (obj instanceof Device) {
            final Device device = (Device) obj;
            ((ObservableSubscribeProxy) Injection.net().updatePresetKValues(device.getDeviceId(), this.presetKValues).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActCtEdit$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj2) {
                    ActCtEdit.this.lambda$edit$1(device, obj2);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$0(Group group, Object obj) throws Exception {
        group.setPresetKValues(this.presetKValues);
        Injection.repo().group().saveGroup(group);
        finishActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$1(Device device, Object obj) throws Exception {
        device.setPresetKValues(this.presetKValues);
        Injection.repo().device().saveDevice(device);
        finishActivity();
    }
}