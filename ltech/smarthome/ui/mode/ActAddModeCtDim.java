package com.ltech.smarthome.ui.mode;

import android.content.Intent;
import android.widget.SeekBar;
import androidx.recyclerview.widget.RecyclerView;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.PickerAdapter;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActAddModeCtDimBinding;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.SelectorUtils;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.layoutmanager.PickerLayoutManager;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.bean.AdvancedModeInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActAddModeCtDim extends VMActivity<ActAddModeCtDimBinding, ActAddModeColorVM> {
    PickerAdapter mHourAdapter;
    PickerLayoutManager mHourManager;
    PickerAdapter mMillSecondAdapter;
    PickerLayoutManager mMillSecondManager;
    PickerAdapter mMinuteAdapter;
    PickerLayoutManager mMinuteManager;
    PickerAdapter mSecondAdapter;
    PickerLayoutManager mSecondManager;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_add_mode_ct_dim;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.add_color_time));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        ((ActAddModeColorVM) this.mViewModel).lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, 3);
        ((ActAddModeColorVM) this.mViewModel).contentItem = (AdvancedModeInfo.ContentItem) getIntent().getParcelableExtra(Constants.MODE_ITEM_INFO);
        if (((ActAddModeColorVM) this.mViewModel).contentItem == null) {
            ((ActAddModeColorVM) this.mViewModel).contentItem = new AdvancedModeInfo.ContentItem();
        }
        int i = ((ActAddModeColorVM) this.mViewModel).lightType;
        if (i == 1) {
            initDimBar();
        } else if (i == 2) {
            initCtBar();
        }
        initTimeBar();
    }

    private void initTimeBar() {
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        final ArrayList arrayList3 = new ArrayList();
        final ArrayList arrayList4 = new ArrayList();
        for (int i = 0; i < 25; i++) {
            if (i == 0) {
                arrayList4.add("000");
            } else {
                int i2 = i * 40;
                if (i2 < 100) {
                    arrayList4.add("0" + i2);
                } else {
                    arrayList4.add(Integer.toString(i2));
                }
            }
        }
        for (int i3 = 0; i3 < 60; i3++) {
            if (i3 < 10) {
                arrayList2.add("0" + i3);
                arrayList3.add("0" + i3);
            } else {
                arrayList2.add(Integer.toString(i3));
                arrayList3.add(Integer.toString(i3));
            }
        }
        for (int i4 = 0; i4 < 24; i4++) {
            if (i4 < 10) {
                arrayList.add("0" + i4);
            } else {
                arrayList.add(Integer.toString(i4));
            }
        }
        ((ActAddModeColorVM) this.mViewModel).setHourPos(AdvancedModeHelper.getHourPos(((ActAddModeColorVM) this.mViewModel).contentItem.getTime()));
        ((ActAddModeColorVM) this.mViewModel).setMinPos(AdvancedModeHelper.getMinPos(((ActAddModeColorVM) this.mViewModel).contentItem.getTime()));
        ((ActAddModeColorVM) this.mViewModel).setSecPos(AdvancedModeHelper.getSecPos(((ActAddModeColorVM) this.mViewModel).contentItem.getTime()));
        ((ActAddModeColorVM) this.mViewModel).setMsPos(AdvancedModeHelper.getMsPos(((ActAddModeColorVM) this.mViewModel).contentItem.getTime()));
        this.mHourAdapter = new PickerAdapter(this);
        this.mMinuteAdapter = new PickerAdapter(this);
        this.mSecondAdapter = new PickerAdapter(this);
        this.mMillSecondAdapter = new PickerAdapter(this);
        this.mHourAdapter.setData(arrayList);
        this.mMinuteAdapter.setData(arrayList2);
        this.mSecondAdapter.setData(arrayList3);
        this.mMillSecondAdapter.setData(arrayList4);
        this.mHourManager = new PickerLayoutManager.Builder(this).setAlpha(true).setMaxItem(5).build();
        this.mMinuteManager = new PickerLayoutManager.Builder(this).setAlpha(true).setMaxItem(5).build();
        this.mSecondManager = new PickerLayoutManager.Builder(this).setAlpha(true).setMaxItem(5).build();
        this.mMillSecondManager = new PickerLayoutManager.Builder(this).setAlpha(true).setMaxItem(5).build();
        ((ActAddModeCtDimBinding) this.mViewBinding).pickViewHour.setLayoutManager(this.mHourManager);
        ((ActAddModeCtDimBinding) this.mViewBinding).pickViewMin.setLayoutManager(this.mMinuteManager);
        ((ActAddModeCtDimBinding) this.mViewBinding).pickViewSec.setLayoutManager(this.mSecondManager);
        ((ActAddModeCtDimBinding) this.mViewBinding).pickViewMs.setLayoutManager(this.mMillSecondManager);
        ((ActAddModeCtDimBinding) this.mViewBinding).pickViewHour.setAdapter(this.mHourAdapter);
        ((ActAddModeCtDimBinding) this.mViewBinding).pickViewMin.setAdapter(this.mMinuteAdapter);
        ((ActAddModeCtDimBinding) this.mViewBinding).pickViewSec.setAdapter(this.mSecondAdapter);
        ((ActAddModeCtDimBinding) this.mViewBinding).pickViewMs.setAdapter(this.mMillSecondAdapter);
        SelectorUtils.updateListData(arrayList, ((ActAddModeColorVM) this.mViewModel).getHourPos(), ((ActAddModeCtDimBinding) this.mViewBinding).pickViewHour);
        SelectorUtils.updateListData(arrayList2, ((ActAddModeColorVM) this.mViewModel).getMinPos(), ((ActAddModeCtDimBinding) this.mViewBinding).pickViewMin);
        SelectorUtils.updateListData(arrayList3, ((ActAddModeColorVM) this.mViewModel).getSecPos(), ((ActAddModeCtDimBinding) this.mViewBinding).pickViewSec);
        SelectorUtils.updateListData(arrayList4, ((ActAddModeColorVM) this.mViewModel).getMsPos(), ((ActAddModeCtDimBinding) this.mViewBinding).pickViewMs);
        this.mHourManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeCtDim$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView, int i5) {
                ActAddModeCtDim.this.lambda$initTimeBar$0(arrayList3, arrayList4, recyclerView, i5);
            }
        });
        this.mMinuteManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeCtDim$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView, int i5) {
                ActAddModeCtDim.this.lambda$initTimeBar$1(arrayList4, recyclerView, i5);
            }
        });
        this.mSecondManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeCtDim$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView, int i5) {
                ActAddModeCtDim.this.lambda$initTimeBar$2(arrayList, recyclerView, i5);
            }
        });
        this.mMillSecondManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeCtDim$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView, int i5) {
                ActAddModeCtDim.this.lambda$initTimeBar$3(arrayList, arrayList2, recyclerView, i5);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTimeBar$0(List list, List list2, RecyclerView recyclerView, int i) {
        ((ActAddModeColorVM) this.mViewModel).setHourPos(this.mHourManager.getPickedPosition());
        ((ActAddModeColorVM) this.mViewModel).setSecPos(0);
        ((ActAddModeColorVM) this.mViewModel).setMsPos(0);
        if (this.mViewBinding != 0) {
            SelectorUtils.updateListData(list, ((ActAddModeColorVM) this.mViewModel).getSecPos(), ((ActAddModeCtDimBinding) this.mViewBinding).pickViewSec);
            SelectorUtils.updateListData(list2, ((ActAddModeColorVM) this.mViewModel).getMsPos(), ((ActAddModeCtDimBinding) this.mViewBinding).pickViewMs);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTimeBar$1(List list, RecyclerView recyclerView, int i) {
        ((ActAddModeColorVM) this.mViewModel).setMinPos(this.mMinuteManager.getPickedPosition());
        ((ActAddModeColorVM) this.mViewModel).setMsPos(0);
        if (this.mViewBinding != 0) {
            SelectorUtils.updateListData(list, ((ActAddModeColorVM) this.mViewModel).getMsPos(), ((ActAddModeCtDimBinding) this.mViewBinding).pickViewMs);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTimeBar$2(List list, RecyclerView recyclerView, int i) {
        ((ActAddModeColorVM) this.mViewModel).setSecPos(this.mSecondManager.getPickedPosition());
        ((ActAddModeColorVM) this.mViewModel).setHourPos(0);
        if (this.mViewBinding != 0) {
            SelectorUtils.updateListData(list, ((ActAddModeColorVM) this.mViewModel).getHourPos(), ((ActAddModeCtDimBinding) this.mViewBinding).pickViewHour);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTimeBar$3(List list, List list2, RecyclerView recyclerView, int i) {
        ((ActAddModeColorVM) this.mViewModel).setMsPos(this.mMillSecondManager.getPickedPosition());
        ((ActAddModeColorVM) this.mViewModel).setHourPos(0);
        ((ActAddModeColorVM) this.mViewModel).setMinPos(0);
        if (this.mViewBinding != 0) {
            SelectorUtils.updateListData(list, ((ActAddModeColorVM) this.mViewModel).getHourPos(), ((ActAddModeCtDimBinding) this.mViewBinding).pickViewHour);
            SelectorUtils.updateListData(list2, ((ActAddModeColorVM) this.mViewModel).getMinPos(), ((ActAddModeCtDimBinding) this.mViewBinding).pickViewMin);
        }
    }

    private void initCtBar() {
        ((ActAddModeCtDimBinding) this.mViewBinding).groupCt.setVisibility(0);
        ((ActAddModeCtDimBinding) this.mViewBinding).tvSetColorTip.setText(getString(R.string.set_color));
        ((ActAddModeCtDimBinding) this.mViewBinding).csbCtBar.setRange(0.0f, 255.0f);
        ((ActAddModeCtDimBinding) this.mViewBinding).csbCtBar.setSeekBarMode(1);
        ((ActAddModeCtDimBinding) this.mViewBinding).csbCtBar.setOnRangeChangedListener(new OnRangeChangedListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeCtDim.1
            @Override // com.jaygoo.widget.OnRangeChangedListener
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {
            }

            @Override // com.jaygoo.widget.OnRangeChangedListener
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                if (isFromUser) {
                    int stepK = 255 - LightUtils.getStepK((int) leftValue, 1);
                    ((ActAddModeColorVM) ActAddModeCtDim.this.mViewModel).setC(stepK);
                    ((ActAddModeColorVM) ActAddModeCtDim.this.mViewModel).setwOrWy(stepK);
                    ((ActAddModeCtDimBinding) ActAddModeCtDim.this.mViewBinding).tvCt.setText(LightUtils.ctY2K(stepK, 10000, 1000) + "K");
                }
            }

            @Override // com.jaygoo.widget.OnRangeChangedListener
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
                int stepK = 255 - LightUtils.getStepK((int) view.getLeftSeekBar().getProgress(), 1);
                ((ActAddModeColorVM) ActAddModeCtDim.this.mViewModel).setC(stepK);
                ((ActAddModeColorVM) ActAddModeCtDim.this.mViewModel).setwOrWy(stepK);
                ((ActAddModeCtDimBinding) ActAddModeCtDim.this.mViewBinding).tvCt.setText(LightUtils.ctY2K(stepK, 10000, 1000) + "K");
            }
        });
        ((ActAddModeCtDimBinding) this.mViewBinding).sbBrt.setIncludeZero(true);
        ((ActAddModeCtDimBinding) this.mViewBinding).sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeCtDim.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActAddModeColorVM) ActAddModeCtDim.this.mViewModel).setWyBrt(LightUtils.progress2BrtIncludeZero(progress));
                    ((ActAddModeCtDimBinding) ActAddModeCtDim.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActAddModeColorVM) ActAddModeCtDim.this.mViewModel).setWyBrt(LightUtils.progress2BrtIncludeZero(seekBar.getProgress()));
                ((ActAddModeCtDimBinding) ActAddModeCtDim.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
            }
        });
        ((ActAddModeCtDimBinding) this.mViewBinding).csbCtBar.setProgress(255 - ((ActAddModeColorVM) this.mViewModel).getwOrWy());
        ((ActAddModeCtDimBinding) this.mViewBinding).tvCt.setText(LightUtils.ctY2K(((ActAddModeColorVM) this.mViewModel).getC(), 10000, 1000) + "K");
        ((ActAddModeCtDimBinding) this.mViewBinding).sbBrt.setProgress(LightUtils.brt2ProgressHasBelowZero(((ActAddModeColorVM) this.mViewModel).getWyBrt()));
        ((ActAddModeCtDimBinding) this.mViewBinding).tvBrt.setText(((ActAddModeCtDimBinding) this.mViewBinding).sbBrt.getProgressHasBelowOne());
    }

    private void initDimBar() {
        ((ActAddModeCtDimBinding) this.mViewBinding).tvBrtTip.setText(getString(R.string.set_brt));
        ((ActAddModeCtDimBinding) this.mViewBinding).sbBrt.setIncludeZero(true);
        ((ActAddModeCtDimBinding) this.mViewBinding).sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeCtDim.3
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActAddModeColorVM) ActAddModeCtDim.this.mViewModel).setWyBrt(LightUtils.progress2BrtIncludeZero(progress));
                    ((ActAddModeCtDimBinding) ActAddModeCtDim.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActAddModeColorVM) ActAddModeCtDim.this.mViewModel).setWyBrt(LightUtils.progress2BrtIncludeZero(seekBar.getProgress()));
                ((ActAddModeCtDimBinding) ActAddModeCtDim.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
            }
        });
        ((ActAddModeCtDimBinding) this.mViewBinding).sbBrt.setProgress(LightUtils.brt2ProgressHasBelowZero(((ActAddModeColorVM) this.mViewModel).getWyBrt()));
        ((ActAddModeCtDimBinding) this.mViewBinding).tvBrt.setText(((ActAddModeCtDimBinding) this.mViewBinding).sbBrt.getProgressHasBelowOne());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        Intent intent = new Intent();
        ((ActAddModeColorVM) this.mViewModel).contentItem.setTime(AdvancedModeHelper.calculateTotalTime(((ActAddModeColorVM) this.mViewModel).getHourPos(), ((ActAddModeColorVM) this.mViewModel).getMinPos(), ((ActAddModeColorVM) this.mViewModel).getSecPos(), ((ActAddModeColorVM) this.mViewModel).getMsPos()));
        LHomeLog.i(getClass(), "save color=" + ((ActAddModeColorVM) this.mViewModel).contentItem.getC());
        intent.putExtra(Constants.MODE_ITEM_INFO, ((ActAddModeColorVM) this.mViewModel).contentItem);
        setResult(6001, intent);
        finishActivity();
    }
}