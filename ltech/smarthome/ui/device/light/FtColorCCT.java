package com.ltech.smarthome.ui.device.light;

import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.FtColorCctBinding;
import com.ltech.smarthome.model.device_param.W5bExtParam;
import com.ltech.smarthome.ui.device.light.ActAddDuv;
import com.ltech.smarthome.ui.device.light.FtColorCCT;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.W5BParam;
import com.ltech.smarthome.utils.W5bUtils;
import com.ltech.smarthome.view.CCTCoordinateView;
import com.ltech.smarthome.view.StepSetView;
import com.ltech.smarthome.view.dialog.AddColorPointDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class FtColorCCT extends BaseNormalFragment<FtColorCctBinding> {
    private BaseQuickAdapter<W5bExtParam.Point, BaseViewHolder> colorAdapter;
    private List<ActAddDuv.ColorPoint> colorList = new ArrayList();
    private W5bExtParam extParam;
    private ActColorCCLightVM mViewModel;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_color_cct;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        ActColorCCLightVM actColorCCLightVM = (ActColorCCLightVM) new ViewModelProvider(requireActivity()).get(ActColorCCLightVM.class);
        this.mViewModel = actColorCCLightVM;
        this.extParam = actColorCCLightVM.getExtParam();
        ((FtColorCctBinding) this.mViewBinding).cctCoordinateView.setOnColorChangedListener(new CCTCoordinateView.OnColorChangedListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorCCT$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.CCTCoordinateView.OnColorChangedListener
            public final void onColorChanged(float f, float f2, boolean z) {
                FtColorCCT.this.lambda$initView$0(f, f2, z);
            }
        });
        ((FtColorCctBinding) this.mViewBinding).layoutCt.setRange(1000, 20000);
        ((FtColorCctBinding) this.mViewBinding).layoutCt.setStep(50);
        ((FtColorCctBinding) this.mViewBinding).layoutCt.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorCCT$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                FtColorCCT.this.lambda$initView$1(i, z, z2);
            }
        });
        ((FtColorCctBinding) this.mViewBinding).layoutCt.setProgress(1000);
        ((FtColorCctBinding) this.mViewBinding).layoutCt.setValue("1000K");
        ((FtColorCctBinding) this.mViewBinding).layoutDuv.setRange(-20, 20);
        ((FtColorCctBinding) this.mViewBinding).layoutDuv.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorCCT$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                FtColorCCT.this.lambda$initView$2(i, z, z2);
            }
        });
        ((FtColorCctBinding) this.mViewBinding).layoutDuv.setProgress(0);
        ((FtColorCctBinding) this.mViewBinding).layoutBrt.setRange(1, 100);
        ((FtColorCctBinding) this.mViewBinding).layoutBrt.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorCCT$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                FtColorCCT.this.lambda$initView$3(i, z, z2);
            }
        });
        ((FtColorCctBinding) this.mViewBinding).layoutBrt.setProgress(100);
        ((FtColorCctBinding) this.mViewBinding).tvAdd.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorCCT$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FtColorCCT.this.lambda$initView$5(view);
            }
        });
        if (this.mViewModel.selectAction) {
            ((FtColorCctBinding) this.mViewBinding).tvDeleteTip.setVisibility(8);
            ((FtColorCctBinding) this.mViewBinding).tvAdd.setVisibility(8);
            checkPoint(this.mViewModel.selectK);
            checkColorPoint(this.mViewModel.selectK);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(float f, float f2, boolean z) {
        int convertXtoK = W5bUtils.convertXtoK(f);
        int i = ((convertXtoK / 50) + (convertXtoK % 50 > 24 ? 1 : 0)) * 50;
        if (z) {
            this.mViewModel.selectK = i;
            sendRgb(true);
        } else if (this.mViewModel.selectK != i) {
            this.mViewModel.selectK = i;
            sendRgb(false);
        }
        ((FtColorCctBinding) this.mViewBinding).layoutCt.setProgress(i);
        if (z) {
            checkPoint(this.mViewModel.selectK);
            checkColorPoint(this.mViewModel.selectK);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(int i, boolean z, boolean z2) {
        this.mViewModel.selectK = i;
        Double[] dArr = W5BParam.getKtoXY().get(Integer.valueOf(i));
        ((FtColorCctBinding) this.mViewBinding).cctCoordinateView.changePoint(dArr[0].floatValue(), dArr[1].floatValue(), i);
        ((FtColorCctBinding) this.mViewBinding).layoutCt.setValue(i + "K");
        if (z2) {
            sendRgb(z);
        }
        if (z) {
            checkPoint(this.mViewModel.selectK);
            checkColorPoint(this.mViewModel.selectK);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(int i, boolean z, boolean z2) {
        this.mViewModel.duv = i * 0.001d;
        ((FtColorCctBinding) this.mViewBinding).layoutDuv.setValue(String.format("%.3f", Double.valueOf(this.mViewModel.duv)));
        if (z2) {
            sendRgb(z);
            this.mViewModel.selectColorPoint = -1;
            BaseQuickAdapter<W5bExtParam.Point, BaseViewHolder> baseQuickAdapter = this.colorAdapter;
            if (baseQuickAdapter != null) {
                baseQuickAdapter.notifyDataSetChanged();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(int i, boolean z, boolean z2) {
        this.mViewModel.bri = LightUtils.progress2BrtHasBelowOne(i);
        ((FtColorCctBinding) this.mViewBinding).layoutBrt.setValue(i + "%");
        if (z2) {
            sendRgb(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$5(View view) {
        AddColorPointDialog.asDefault().setTitle(getString(R.string.add_color_point)).setControlObject(this.mViewModel.controlObject.getValue()).setK(this.mViewModel.selectK).setOnSaveListener(new AddColorPointDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorCCT$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.view.dialog.AddColorPointDialog.OnSaveListener
            public final void onSave(String str, float f) {
                FtColorCCT.this.lambda$initView$4(str, f);
            }
        }).showDialog(getActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$4(String str, float f) {
        Iterator<W5bExtParam.Point> it = this.extParam.getPointList(this.mViewModel.selectK).iterator();
        while (it.hasNext()) {
            if (it.next().getName().equals(str)) {
                SmartToast.showShort(R.string.name_repeat);
                return;
            }
        }
        this.extParam.addPoint(this.mViewModel.selectK, new W5bExtParam.Point(str, f, 0));
        this.mViewModel.uploadData(this.extParam, true);
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        this.mViewModel.updateUIEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.FtColorCCT$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtColorCCT.this.lambda$startObserve$6(obj);
            }
        });
        this.mViewModel.refreshObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.FtColorCCT$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtColorCCT.this.lambda$startObserve$8((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Object obj) {
        setColorAdapter(this.extParam.getPointList(this.mViewModel.selectK));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$8(Boolean bool) {
        this.mViewModel.getLightCmdHelper().queryDuvCalibration(getContext(), new IAction() { // from class: com.ltech.smarthome.ui.device.light.FtColorCCT$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtColorCCT.this.lambda$startObserve$7((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getStateCode() != 0 || responseMsg.getResData().length() <= 16) {
            return;
        }
        String substring = responseMsg.getResData().substring(16);
        this.colorList.clear();
        this.colorList.addAll(this.mViewModel.getColorList(substring));
    }

    private void checkPoint(int k) {
        setColorAdapter(this.extParam.getPointList(k));
        if (this.mViewModel.selectAction) {
            if (this.extParam.getPointList(k).isEmpty()) {
                ((FtColorCctBinding) this.mViewBinding).layoutPoint.setVisibility(8);
            } else {
                ((FtColorCctBinding) this.mViewBinding).layoutPoint.setVisibility(0);
            }
        }
        ((FtColorCctBinding) this.mViewBinding).tvDeleteTip.setVisibility(this.extParam.getPointList(k).isEmpty() ? 8 : 0);
        this.mViewModel.selectColorPoint = -1;
    }

    private void checkColorPoint(int k) {
        int i = 0;
        for (ActAddDuv.ColorPoint colorPoint : this.colorList) {
            if (colorPoint.k == k) {
                i = (int) (colorPoint.duv * 1000.0d);
            }
        }
        ((FtColorCctBinding) this.mViewBinding).layoutDuv.setProgress(i);
    }

    /* renamed from: com.ltech.smarthome.ui.device.light.FtColorCCT$1, reason: invalid class name */
    class AnonymousClass1 extends BaseQuickAdapter<W5bExtParam.Point, BaseViewHolder> {
        AnonymousClass1(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(final BaseViewHolder helper, final W5bExtParam.Point item) {
            helper.setText(R.id.tv_name, item.getName());
            CardView cardView = (CardView) helper.getView(R.id.color);
            FtColorCCT ftColorCCT = FtColorCCT.this;
            cardView.setCardBackgroundColor(ftColorCCT.getRgbByDuv(ftColorCCT.mViewModel.selectK, item.getDuv()));
            helper.setBackgroundRes(R.id.tv_name, FtColorCCT.this.mViewModel.selectColorPoint == helper.getBindingAdapterPosition() ? R.drawable.shape_black_stroke : 0);
            helper.getView(R.id.color).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorCCT$1$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FtColorCCT.AnonymousClass1.this.lambda$convert$0(item, helper, view);
                }
            });
            helper.getView(R.id.color).setOnLongClickListener(new View.OnLongClickListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorCCT$1$$ExternalSyntheticLambda2
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    boolean lambda$convert$2;
                    lambda$convert$2 = FtColorCCT.AnonymousClass1.this.lambda$convert$2(item, view);
                    return lambda$convert$2;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(W5bExtParam.Point point, BaseViewHolder baseViewHolder, View view) {
            FtColorCCT.this.mViewModel.duv = point.getDuv();
            FtColorCCT.this.mViewModel.selectColorPoint = baseViewHolder.getBindingAdapterPosition();
            FtColorCCT.this.colorAdapter.notifyDataSetChanged();
            FtColorCCT.this.sendRgb(true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$convert$2(final W5bExtParam.Point point, View view) {
            if (FtColorCCT.this.mViewModel.selectAction || point.getIsDefault() != 0) {
                return true;
            }
            MessageDialog.show((AppCompatActivity) FtColorCCT.this.getActivity(), R.string.delete_color_point, R.string.delete_data_no_recover).setOkButton(R.string.confirm, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorCCT$1$$ExternalSyntheticLambda0
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view2) {
                    boolean lambda$convert$1;
                    lambda$convert$1 = FtColorCCT.AnonymousClass1.this.lambda$convert$1(point, baseDialog, view2);
                    return lambda$convert$1;
                }
            }).setCancelButton(R.string.cancel);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$convert$1(W5bExtParam.Point point, BaseDialog baseDialog, View view) {
            FtColorCCT.this.extParam.removePoint(FtColorCCT.this.mViewModel.selectK, point);
            FtColorCCT.this.mViewModel.uploadData(FtColorCCT.this.extParam, false);
            return false;
        }
    }

    private void setColorAdapter(List<W5bExtParam.Point> list) {
        if (this.colorAdapter == null) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(R.layout.item_color_point, new ArrayList());
            this.colorAdapter = anonymousClass1;
            anonymousClass1.bindToRecyclerView(((FtColorCctBinding) this.mViewBinding).rvColorPoint);
            ((FtColorCctBinding) this.mViewBinding).rvColorPoint.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
            ((FtColorCctBinding) this.mViewBinding).rvColorPoint.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.light.FtColorCCT.2
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), 0);
                }
            });
        }
        this.colorAdapter.replaceData(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRgb(boolean finish) {
        this.mViewModel.getLightCmdHelper().sendCCT(getContext(), this.mViewModel.selectK, (int) (this.mViewModel.duv * 10000.0d), this.mViewModel.bri, finish);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getRgbByDuv(int K, double duv) {
        Double[] dArr = W5BParam.getKtoXY().get(Integer.valueOf(K));
        Double[] dArr2 = W5BParam.getKtoXY().get(Integer.valueOf(K == 20000 ? K - 50 : K + 50));
        double[] newXyFromDuv = W5bUtils.toNewXyFromDuv(duv, new double[]{dArr[0].doubleValue(), dArr[1].doubleValue()}, new double[]{dArr2[0].doubleValue(), dArr2[1].doubleValue()});
        int[] xyToRgb2 = W5bUtils.xyToRgb2((float) newXyFromDuv[0], (float) newXyFromDuv[1], 255);
        return Color.rgb(xyToRgb2[0], xyToRgb2[1], xyToRgb2[2]);
    }
}