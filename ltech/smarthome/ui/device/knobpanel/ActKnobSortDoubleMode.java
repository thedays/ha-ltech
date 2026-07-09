package com.ltech.smarthome.ui.device.knobpanel;

import android.content.Intent;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.BaseSortActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ActKnobSortDoubleMode extends BaseSortActivity<String> {
    private String[] contentArray;
    private Device device;
    private int[] orderArray;
    private RelateInfoAssistant relateInfoAssistant;

    @Override // com.ltech.smarthome.base.BaseSortActivity
    protected int itemLayout() {
        return R.layout.item_sort;
    }

    @Override // com.ltech.smarthome.base.BaseSortActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.mode_memorize));
        setBackImage(R.mipmap.icon_back);
        setBackString("");
        setEditString(getString(R.string.save));
        if (getIntent().getIntExtra(Constants.LIGHT_TYPE, -1) == 5) {
            this.contentArray = getResources().getStringArray(R.array.mode_rgbwy);
        } else {
            this.contentArray = getResources().getStringArray(R.array.mode_rgbw);
        }
        this.device = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        ArrayList arrayList = new ArrayList();
        int i = 0;
        if (this.device.getProductId().equals(ProductId.ID_SMART_SWITCH_SQB)) {
            RelateInfoAssistant relateInfoAssistant = new RelateInfoAssistant(this.device);
            this.relateInfoAssistant = relateInfoAssistant;
            if (relateInfoAssistant.getSwitchSort0() != 0 && this.relateInfoAssistant.getSwitchSort1() != 0 && this.relateInfoAssistant.getSwitchSort2() != 0) {
                this.orderArray = new int[]{this.relateInfoAssistant.getSwitchSort0(), this.relateInfoAssistant.getSwitchSort1(), this.relateInfoAssistant.getSwitchSort2()};
            } else {
                this.orderArray = new int[]{1, 2, 3};
            }
        } else {
            int[] intArrayExtra = getIntent().getIntArrayExtra(Constants.MODE_SET);
            if (intArrayExtra == null) {
                intArrayExtra = new int[]{1, 2, 3};
            }
            this.orderArray = intArrayExtra;
        }
        while (true) {
            int[] iArr = this.orderArray;
            if (i < iArr.length) {
                arrayList.add(this.contentArray[iArr[i] - 1]);
                i++;
            } else {
                setDataList(arrayList);
                return;
            }
        }
    }

    @Override // com.ltech.smarthome.base.BaseSortActivity
    protected List<String> getItemList() {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseSortActivity
    public void convertView(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item);
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseSortActivity
    protected void saveData() {
        if (this.device.getProductId().equals(ProductId.ID_SMART_SWITCH_SQB)) {
            RelateInfoUtils.showImageTipDialog(getString(R.string.sq_click_tip), R.mipmap.pic_click_tip, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobSortDoubleMode$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    ActKnobSortDoubleMode.this.lambda$saveData$0(imageTipDialog);
                }
            });
        } else {
            lambda$saveData$0(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: save, reason: merged with bridge method [inline-methods] */
    public void lambda$saveData$0(final ImageTipDialog dialog) {
        for (int i = 0; i < this.dataList.size(); i++) {
            String str = (String) this.dataList.get(i);
            int i2 = 0;
            while (true) {
                String[] strArr = this.contentArray;
                if (i2 < strArr.length) {
                    if (str.equals(strArr[i2])) {
                        this.orderArray[i] = i2 + 1;
                    }
                    i2++;
                }
            }
        }
        if (dialog != null) {
            CmdAssistant.getDeviceAssistant(this.device, new int[0]).setKnobOrderWithoutDialog(this, this.orderArray, new IAction() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobSortDoubleMode$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActKnobSortDoubleMode.this.lambda$save$1(dialog, (Boolean) obj);
                }
            });
        } else {
            CmdAssistant.getDeviceAssistant(this.device, new int[0]).setKnobOrder(this, this.orderArray, new IAction() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobSortDoubleMode$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActKnobSortDoubleMode.this.lambda$save$2((Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$1(ImageTipDialog imageTipDialog, Boolean bool) {
        imageTipDialog.dismissDialog();
        if (bool.booleanValue()) {
            uploadData();
        } else {
            ((BaseNormalActivity) ActivityUtils.getTopActivity()).showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$2(Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupData(this, this.device.getDeviceId(), UpdateBackDataRequest.KNOB_SORT, CmdAssistant.getDeviceAssistant(this.device, new int[0]).setKnobOrder(this.orderArray));
            Intent intent = new Intent();
            intent.putExtra(Constants.MODE_SET, this.orderArray);
            setResult(3005, intent);
            finishActivity();
        }
    }

    private void uploadData() {
        try {
            JSONObject jSONObject = this.device.getExtParam() != null ? new JSONObject(this.device.getExtParam()) : new JSONObject();
            for (int i = 0; i < this.orderArray.length; i++) {
                jSONObject.put("switchSort" + i, this.orderArray[i]);
            }
            this.device.setExtParam(jSONObject.toString());
            ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.device.getDeviceId(), jSONObject.toString()).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobSortDoubleMode$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActKnobSortDoubleMode.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobSortDoubleMode$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActKnobSortDoubleMode.this.lambda$uploadData$3(obj);
                }
            }, new SmartErrorComsumer());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$3(Object obj) throws Exception {
        Injection.repo().device().saveDevice(this.device);
        Intent intent = new Intent();
        intent.putExtra(Constants.MODE_SET, this.orderArray);
        setResult(3005, intent);
        finishActivity();
    }
}