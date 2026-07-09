package com.ltech.smarthome.ui.device.light;

import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.mikephil.charting.utils.Utils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActAddDuvBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.ModeContent;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.control.ActChangeEngineerMode;
import com.ltech.smarthome.ui.device.light.ActAddDuv;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.SelectKDuvDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.StringUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActAddDuv extends BaseNormalActivity<ActAddDuvBinding> {
    private Device device;
    private String lastString;
    private BaseQuickAdapter<ColorPoint, BaseViewHolder> mAdapter;
    private int modeNum;
    private ModeContent mModeContent = new ModeContent();
    private List<ColorPoint> colorList = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_add_duv;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        long longExtra = getIntent().getLongExtra(Constants.MODE_ID, -1L);
        this.modeNum = getIntent().getIntExtra(Constants.MODE_NUM, 1);
        this.device = Injection.repo().device().getDeviceByDeviceId(getIntent().getLongExtra("device_id", -1L));
        this.colorList.clear();
        this.colorList.add(new ColorPoint(-1, 0, Utils.DOUBLE_EPSILON));
        if (longExtra != -1) {
            setTitle(getString(R.string.edit_duv));
            ModeContent modeById = Injection.repo().mode().getModeById(longExtra);
            this.mModeContent = modeById;
            if (!TextUtils.isEmpty(modeById.getContent())) {
                this.colorList.addAll(getColorList(this.mModeContent.getContent()));
            }
        } else {
            setTitle(getString(R.string.create_duv));
            this.mModeContent.setModeName(getIntent().getStringExtra(Constants.MODE_NAME));
            this.mModeContent.setModeType(4);
            this.mModeContent.setDeviceType(20);
            this.mModeContent.setModeIndex(this.modeNum);
            this.mModeContent.setContent("0000");
            ((ActAddDuvBinding) this.mViewBinding).layoutBottom.setVisibility(8);
        }
        this.lastString = GsonUtils.toJson(this.mModeContent);
        initAdapter();
        ((ActAddDuvBinding) this.mViewBinding).tvName.setText(this.mModeContent.getModeName());
        ((ActAddDuvBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.light.ActAddDuv$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActAddDuv.this.lambda$initView$3((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(View view) {
        int id = view.getId();
        if (id == R.id.layout_name) {
            EditDialog.asDefault().setContent(this.mModeContent.getModeName()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActAddDuv$$ExternalSyntheticLambda7
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActAddDuv.this.lambda$initView$0((String) obj);
                }
            }).showDialog(this);
            return;
        }
        if (id != R.id.tv_add_K) {
            if (id != R.id.tv_apply) {
                return;
            }
            NavUtils.destination(ActChangeEngineerMode.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withInt(Constants.ENGINEER_SET_TYPE, 11).withString(Constants.DUV_CALIBRATION_DATA, getModeString()).navigation(this);
        } else if (this.colorList.size() >= 25) {
            SmartToast.showCenterShort(getString(R.string.max_k_value_tip));
        } else {
            SelectKDuvDialog.asDefault().setTitle(getString(R.string.add_k_value)).setControlObject(this.device).setOnSaveListener(new SelectKDuvDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.device.light.ActAddDuv$$ExternalSyntheticLambda8
                @Override // com.ltech.smarthome.view.dialog.SelectKDuvDialog.OnSaveListener
                public final void onSave(int i, int i2) {
                    ActAddDuv.this.lambda$initView$2(i, i2);
                }
            }).showDialog(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(String str) {
        this.mModeContent.setModeName(str);
        ((ActAddDuvBinding) this.mViewBinding).tvName.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(int i, int i2) {
        Iterator<ColorPoint> it = this.colorList.iterator();
        while (it.hasNext()) {
            if (it.next().k == i) {
                SmartToast.showCenterShort(getString(R.string.k_value_repeat_tip));
                return;
            }
        }
        this.colorList.add(new ColorPoint(i, i2 * 0.001d));
        Collections.sort(this.colorList, new Comparator() { // from class: com.ltech.smarthome.ui.device.light.ActAddDuv$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ActAddDuv.lambda$initView$1((ActAddDuv.ColorPoint) obj, (ActAddDuv.ColorPoint) obj2);
            }
        });
        this.mAdapter.notifyDataSetChanged();
    }

    static /* synthetic */ int lambda$initView$1(ColorPoint colorPoint, ColorPoint colorPoint2) {
        return colorPoint.k - colorPoint2.k;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (this.mModeContent.getModeName().isEmpty()) {
            SmartToast.showShort(R.string.input_name);
        } else if (this.colorList.size() == 1) {
            SmartToast.showShort(R.string.data_empty_tip);
        } else {
            this.mModeContent.setContent(getModeString());
            ((ObservableSubscribeProxy) Injection.net().addMode(this.mModeContent.getModeName(), Injection.repo().home().getSelectPlace().getValue().getPlaceId(), 20, this.mModeContent.getModeType(), this.mModeContent.getModeIndex(), 0, this.mModeContent.getContent(), "", -1).delaySubscription(200L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActAddDuv$$ExternalSyntheticLambda9
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActAddDuv.this.lambda$edit$4((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.light.ActAddDuv$$ExternalSyntheticLambda10
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActAddDuv.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActAddDuv$$ExternalSyntheticLambda11
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActAddDuv.this.lambda$edit$5(obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$4(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$5(Object obj) throws Exception {
        this.lastString = GsonUtils.toJson(this.mModeContent);
        showSuccessDialog(getString(R.string.save_success));
        setResult(-1);
        finishActivity();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        this.mModeContent.setContent(getModeString());
        if (!this.lastString.equals(GsonUtils.toJson(this.mModeContent))) {
            MessageDialog.show(this, (String) null, getString(R.string.data_need_save_tip)).setOkButton(getString(R.string.save), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActAddDuv$$ExternalSyntheticLambda3
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$back$6;
                    lambda$back$6 = ActAddDuv.this.lambda$back$6(baseDialog, view);
                    return lambda$back$6;
                }
            }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActAddDuv$$ExternalSyntheticLambda4
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$back$7;
                    lambda$back$7 = ActAddDuv.this.lambda$back$7(baseDialog, view);
                    return lambda$back$7;
                }
            }).setCancelable(false);
        } else {
            super.back();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$back$6(BaseDialog baseDialog, View view) {
        edit();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$back$7(BaseDialog baseDialog, View view) {
        super.back();
        return false;
    }

    /* renamed from: com.ltech.smarthome.ui.device.light.ActAddDuv$1, reason: invalid class name */
    class AnonymousClass1 extends BaseQuickAdapter<ColorPoint, BaseViewHolder> {
        AnonymousClass1(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(final BaseViewHolder helper, ColorPoint item) {
            if (item.index == -1) {
                helper.setText(R.id.tv_1, R.string.color_point).setText(R.id.tv_2, R.string.k_value).setText(R.id.tv_3, R.string.title_duv).setText(R.id.tv_4, R.string.delete).setGone(R.id.tv_4, true).setGone(R.id.iv_4, false);
                return;
            }
            helper.setText(R.id.tv_1, String.valueOf(helper.getBindingAdapterPosition())).setText(R.id.tv_2, item.k + "K").setText(R.id.tv_3, String.format("%.3f", Double.valueOf(item.duv))).setGone(R.id.tv_4, false).setGone(R.id.iv_4, true);
            helper.getView(R.id.iv_4).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActAddDuv$1$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ActAddDuv.AnonymousClass1.this.lambda$convert$0(helper, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(BaseViewHolder baseViewHolder, View view) {
            ActAddDuv.this.colorList.remove(baseViewHolder.getBindingAdapterPosition());
            ActAddDuv.this.mAdapter.notifyDataSetChanged();
        }
    }

    private void initAdapter() {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(R.layout.item_k_and_duv, this.colorList);
        this.mAdapter = anonymousClass1;
        anonymousClass1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActAddDuv$$ExternalSyntheticLambda2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActAddDuv.this.lambda$initAdapter$10(baseQuickAdapter, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActAddDuvBinding) this.mViewBinding).rvContent);
        ((ActAddDuvBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAdapter$10(BaseQuickAdapter baseQuickAdapter, View view, final int i) {
        SelectKDuvDialog.asDefault().setTitle(getString(R.string.edit_k_value)).setControlObject(this.device).setK(this.colorList.get(i).k).setDuv((int) (this.colorList.get(i).duv * 1000.0d)).setOnSaveListener(new SelectKDuvDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.device.light.ActAddDuv$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.dialog.SelectKDuvDialog.OnSaveListener
            public final void onSave(int i2, int i3) {
                ActAddDuv.this.lambda$initAdapter$9(i, i2, i3);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAdapter$9(int i, int i2, int i3) {
        this.colorList.set(i, new ColorPoint(i2, i3 * 0.001d));
        Collections.sort(this.colorList, new Comparator() { // from class: com.ltech.smarthome.ui.device.light.ActAddDuv$$ExternalSyntheticLambda5
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ActAddDuv.lambda$initAdapter$8((ActAddDuv.ColorPoint) obj, (ActAddDuv.ColorPoint) obj2);
            }
        });
        this.mAdapter.notifyDataSetChanged();
    }

    static /* synthetic */ int lambda$initAdapter$8(ColorPoint colorPoint, ColorPoint colorPoint2) {
        return colorPoint.k - colorPoint2.k;
    }

    private String getModeString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.addZeroForNum(Integer.toHexString(this.colorList.size() - 1), 4));
        for (ColorPoint colorPoint : this.colorList) {
            if (colorPoint.index != -1) {
                sb.append(StringUtils.addZeroForNum(Integer.toHexString(colorPoint.k), 4));
                int i = (int) (colorPoint.duv * 10000.0d);
                if (i < 0) {
                    i += 65536;
                }
                sb.append(StringUtils.addZeroForNum(Integer.toHexString(i), 4));
            }
        }
        return sb.toString();
    }

    private List<ColorPoint> getColorList(String mode) {
        ArrayList arrayList = new ArrayList();
        String substring = mode.substring(4);
        int i = 0;
        while (i < substring.length() / 8) {
            int i2 = i * 8;
            int i3 = i2 + 4;
            int parseInt = Integer.parseInt(substring.substring(i2, i3), 16);
            int parseInt2 = Integer.parseInt(substring.substring(i3, i2 + 8), 16);
            if (parseInt2 > 200) {
                parseInt2 -= 65536;
            }
            i++;
            arrayList.add(new ColorPoint(i, parseInt, parseInt2 / 10000.0d));
        }
        return arrayList;
    }

    public static class ColorPoint {
        public double duv;
        public int index;
        public int k;

        public ColorPoint(int k, double duv) {
            this.k = k;
            this.duv = duv;
        }

        public ColorPoint(int index, int k, double duv) {
            this.index = index;
            this.k = k;
            this.duv = duv;
        }
    }
}