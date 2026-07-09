package com.ltech.smarthome.ui.scene;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.loc.at;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActCtSelectColorBinding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.KValue;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.light.CtControlHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.BrightVerticalSeekBar;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.smart.message.utils.LHomeLog;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class ActCtSelectColor extends VMActivity<ActCtSelectColorBinding, ActSelectColorVM> {
    private BaseQuickAdapter<KValue, BaseViewHolder> colorAdapter;
    private List<KValue> colorList;
    private CtControlHelper ctHelper;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_ct_select_color;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.select_color));
        setEditString(getString(R.string.confirm));
        ((ActSelectColorVM) this.mViewModel).isGq = getIntent().getBooleanExtra(Constants.IS_GQ, false);
        ((ActSelectColorVM) this.mViewModel).isE6 = getIntent().getBooleanExtra(Constants.IS_E6, false);
        ((ActSelectColorVM) this.mViewModel).isLocalScene = getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false);
        ((ActSelectColorVM) this.mViewModel).lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        ((ActSelectColorVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActSelectColorVM) this.mViewModel).isWaveSensorAction = getIntent().getBooleanExtra(Constants.WAVE_SENSOR_ACTION, false);
        ((ActSelectColorVM) this.mViewModel).initData();
        setColorView();
        if (((ActSelectColorVM) this.mViewModel).getWyBrt() == 0) {
            ((ActCtSelectColorBinding) this.mViewBinding).sbBrt.setCurrentProgress(100);
            ((ActSelectColorVM) this.mViewModel).setWyBrt(255);
        } else {
            ((ActCtSelectColorBinding) this.mViewBinding).sbBrt.setCurrentProgress(LightUtils.brt2ProgressHasBelowZero(((ActSelectColorVM) this.mViewModel).getWyBrt()));
        }
        ((ActCtSelectColorBinding) this.mViewBinding).tvBrt.setText(((ActCtSelectColorBinding) this.mViewBinding).sbBrt.getProgressPercent());
        initCtView();
        this.ctHelper.setProgress(255 - ((ActSelectColorVM) this.mViewModel).getWy());
        ((ActSelectColorVM) this.mViewModel).setWy(255 - this.ctHelper.getProgress());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        LHomeLog.i(getClass(), "选择颜色save ");
        if (ProductId.ID_SMART_PANEL_S6B.equals(((ActSelectColorVM) this.mViewModel).productId) && RelateInfoUtils.needShowTipDialog()) {
            RelateInfoUtils.showImageTipDialog(getString(R.string.s6b_click_tip), R.mipmap.pic_click_tip_s6b, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.scene.ActCtSelectColor$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    ActCtSelectColor.this.lambda$edit$0(imageTipDialog);
                }
            });
        } else {
            ((ActSelectColorVM) this.mViewModel).saveData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$0(ImageTipDialog imageTipDialog) {
        ((ActSelectColorVM) this.mViewModel).saveData();
        imageTipDialog.dismissDialog();
    }

    private void initCtView() {
        ((ActCtSelectColorBinding) this.mViewBinding).sbBrt.setOnProgressChangeListener(new BrightVerticalSeekBar.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActCtSelectColor.1
            @Override // com.ltech.smarthome.view.BrightVerticalSeekBar.OnProgressChangeListener
            public void onStartProgressChanged(BrightVerticalSeekBar bar) {
            }

            @Override // com.ltech.smarthome.view.BrightVerticalSeekBar.OnProgressChangeListener
            public void onProgressChanged(BrightVerticalSeekBar bar) {
                ((ActSelectColorVM) ActCtSelectColor.this.mViewModel).setWyBrt(LightUtils.progress2BrtHasBelowOne(bar.getProgress()));
                ((ActCtSelectColorBinding) ActCtSelectColor.this.mViewBinding).tvBrt.setText(bar.getProgressPercent());
                ((ActSelectColorVM) ActCtSelectColor.this.mViewModel).getLightCmdHelper().sendCtBrtHas1to9(ActCtSelectColor.this, bar.getProgress(), false);
            }

            @Override // com.ltech.smarthome.view.BrightVerticalSeekBar.OnProgressChangeListener
            public void onStopProgressChanged(BrightVerticalSeekBar bar) {
                ((ActSelectColorVM) ActCtSelectColor.this.mViewModel).setWyBrt(LightUtils.progress2BrtHasBelowOne(bar.getProgress()));
                ((ActSelectColorVM) ActCtSelectColor.this.mViewModel).getLightCmdHelper().sendCtBrtHas1to9(ActCtSelectColor.this, bar.getProgress(), true);
                ((ActCtSelectColorBinding) ActCtSelectColor.this.mViewBinding).tvBrt.setText(bar.getProgressPercent());
            }
        });
        ((ActCtSelectColorBinding) this.mViewBinding).tvBrt.setText(((ActCtSelectColorBinding) this.mViewBinding).sbBrt.getProgressPercent());
        this.ctHelper = new CtControlHelper(((ActCtSelectColorBinding) this.mViewBinding).ctsb, ((ActCtSelectColorBinding) this.mViewBinding).tvWy, ((ActSelectColorVM) this.mViewModel).getControlObject(), new CtControlHelper.OnCtChangedListener() { // from class: com.ltech.smarthome.ui.scene.ActCtSelectColor.2
            @Override // com.ltech.smarthome.ui.device.light.CtControlHelper.OnCtChangedListener
            public void onRangeChanged(int v, boolean finish) {
                ((ActSelectColorVM) ActCtSelectColor.this.mViewModel).setWy(255 - v);
                ((ActSelectColorVM) ActCtSelectColor.this.mViewModel).getLightCmdHelper().sendCW(ActCtSelectColor.this.activity, v, finish);
            }
        });
    }

    private void getColorList() {
        int i;
        Map<String, KValue> presetKValues;
        int[] iArr = {2700, 3500, 4000, 5000, 6000, 6500};
        int[] intArray = getResources().getIntArray(R.array.k_value_color);
        this.colorList = new ArrayList();
        int i2 = 0;
        while (true) {
            if (i2 >= 6) {
                break;
            }
            this.colorList.add(new KValue(iArr[i2], String.format("#%06X", Integer.valueOf(intArray[i2] & 16777215))));
            i2++;
        }
        Object controlObject = ((ActSelectColorVM) this.mViewModel).getControlObject();
        if (controlObject instanceof Group) {
            presetKValues = ((Group) controlObject).getPresetKValues();
        } else {
            Object controlObject2 = ((ActSelectColorVM) this.mViewModel).getControlObject();
            presetKValues = controlObject2 instanceof Device ? ((Device) controlObject2).getPresetKValues() : null;
        }
        if (presetKValues != null) {
            for (i = 1; i <= 6; i++) {
                if (presetKValues.containsKey(String.valueOf(i))) {
                    this.colorList.set(i - 1, presetKValues.get(String.valueOf(i)));
                }
            }
        }
    }

    private void setColorView() {
        getColorList();
        BaseQuickAdapter<KValue, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<KValue, BaseViewHolder>(R.layout.item_ct_color_selector, this.colorList) { // from class: com.ltech.smarthome.ui.scene.ActCtSelectColor.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, KValue k) {
                helper.setText(R.id.sel_tv, k.getValue() + at.k);
                CircleImageView circleImageView = (CircleImageView) helper.getView(R.id.iv);
                circleImageView.getLayoutParams().width = SizeUtils.dp2px(40.0f);
                circleImageView.getLayoutParams().height = SizeUtils.dp2px(40.0f);
                circleImageView.setImageDrawable(new ColorDrawable(Color.parseColor(((KValue) ActCtSelectColor.this.colorList.get(helper.getLayoutPosition())).getColor())));
            }
        };
        this.colorAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.ActCtSelectColor$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActCtSelectColor.this.lambda$setColorView$1(baseQuickAdapter2, view, i);
            }
        });
        this.colorAdapter.bindToRecyclerView(((ActCtSelectColorBinding) this.mViewBinding).rvColor);
        ((ActCtSelectColorBinding) this.mViewBinding).rvColor.setLayoutManager(new LinearLayoutManager(this, 0, false));
        ((ActCtSelectColorBinding) this.mViewBinding).rvColor.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.scene.ActCtSelectColor.4
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(10.0f), 0, ConvertUtils.dp2px(10.0f), 0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setColorView$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int ctK2Y = 255 - LightUtils.ctK2Y(Integer.parseInt(this.colorAdapter.getData().get(i).getValue()), this.ctHelper.getKMax(), this.ctHelper.getkMin());
        this.ctHelper.setProgress(ctK2Y);
        ((ActSelectColorVM) this.mViewModel).setWy(255 - ctK2Y);
        ((ActSelectColorVM) this.mViewModel).getLightCmdHelper().sendCW(this.activity, ctK2Y, true);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            setResult(3001);
            finishActivity();
        }
    }
}