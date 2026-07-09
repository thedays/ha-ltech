package com.ltech.smarthome.ui.mode;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.SeekBar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.PickerAdapter;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActAddModeColorBinding;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.SelectorUtils;
import com.ltech.smarthome.view.ColorEditText;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.layoutmanager.PickerLayoutManager;
import com.smart.product_agreement.bean.AdvancedModeInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActAddModeColor extends VMActivity<ActAddModeColorBinding, ActAddModeColorVM> {
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
        return R.layout.act_add_mode_color;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        getWindow().setSoftInputMode(32);
        setTitle(getString(R.string.add_color_time));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        ((ActAddModeColorVM) this.mViewModel).lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, 3);
        ((ActAddModeColorVM) this.mViewModel).contentItem = (AdvancedModeInfo.ContentItem) getIntent().getParcelableExtra(Constants.MODE_ITEM_INFO);
        if (((ActAddModeColorVM) this.mViewModel).contentItem == null) {
            ((ActAddModeColorVM) this.mViewModel).contentItem = new AdvancedModeInfo.ContentItem();
        }
        int i = ((ActAddModeColorVM) this.mViewModel).lightType;
        if (i == 3) {
            setRBar();
            setGBar();
            setBBar();
            setBrtBar();
        } else if (i == 4) {
            setRBar();
            setGBar();
            setBBar();
            setBrtBar();
            setWBar();
        } else if (i == 5) {
            setRBar();
            setGBar();
            setBBar();
            setWyBar();
            setBrtBar();
        }
        initTimeBar();
        setColorList();
        setBgColorView();
    }

    private void setColorList() {
        final BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Integer, BaseViewHolder>(this, R.layout.item_color, ((ActAddModeColorVM) this.mViewModel).getColorList(this)) { // from class: com.ltech.smarthome.ui.mode.ActAddModeColor.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Integer item) {
                helper.setImageDrawable(R.id.civ_color, new ColorDrawable(item.intValue()));
            }
        };
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeColor$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActAddModeColor.this.lambda$setColorList$0(baseQuickAdapter, baseQuickAdapter2, view, i);
            }
        });
        baseQuickAdapter.bindToRecyclerView(((ActAddModeColorBinding) this.mViewBinding).rvColor);
        ((ActAddModeColorBinding) this.mViewBinding).rvColor.setLayoutManager(new GridLayoutManager(this, 8));
        ((ActAddModeColorBinding) this.mViewBinding).rvColor.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.mode.ActAddModeColor.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), 0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setColorList$0(BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
        int intValue = ((Integer) baseQuickAdapter.getData().get(i)).intValue();
        ((ActAddModeColorVM) this.mViewModel).setRed(Color.red(intValue));
        ((ActAddModeColorVM) this.mViewModel).setGreen(Color.green(intValue));
        ((ActAddModeColorVM) this.mViewModel).setBlue(Color.blue(intValue));
        ((ActAddModeColorBinding) this.mViewBinding).etRed.setText(String.valueOf(((ActAddModeColorVM) this.mViewModel).getRed()));
        ((ActAddModeColorBinding) this.mViewBinding).etGreen.setText(String.valueOf(((ActAddModeColorVM) this.mViewModel).getGreen()));
        ((ActAddModeColorBinding) this.mViewBinding).etBlue.setText(String.valueOf(((ActAddModeColorVM) this.mViewModel).getBlue()));
        ((ActAddModeColorBinding) this.mViewBinding).vsbRed.setProgress(((ActAddModeColorVM) this.mViewModel).getRed());
        ((ActAddModeColorBinding) this.mViewBinding).vsbGreen.setProgress(((ActAddModeColorVM) this.mViewModel).getGreen());
        ((ActAddModeColorBinding) this.mViewBinding).vsbBlue.setProgress(((ActAddModeColorVM) this.mViewModel).getBlue());
        setBgColorView();
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
        ((ActAddModeColorBinding) this.mViewBinding).pickViewHour.setLayoutManager(this.mHourManager);
        ((ActAddModeColorBinding) this.mViewBinding).pickViewMin.setLayoutManager(this.mMinuteManager);
        ((ActAddModeColorBinding) this.mViewBinding).pickViewSec.setLayoutManager(this.mSecondManager);
        ((ActAddModeColorBinding) this.mViewBinding).pickViewMs.setLayoutManager(this.mMillSecondManager);
        ((ActAddModeColorBinding) this.mViewBinding).pickViewHour.setAdapter(this.mHourAdapter);
        ((ActAddModeColorBinding) this.mViewBinding).pickViewMin.setAdapter(this.mMinuteAdapter);
        ((ActAddModeColorBinding) this.mViewBinding).pickViewSec.setAdapter(this.mSecondAdapter);
        ((ActAddModeColorBinding) this.mViewBinding).pickViewMs.setAdapter(this.mMillSecondAdapter);
        SelectorUtils.updateListData(arrayList, ((ActAddModeColorVM) this.mViewModel).getHourPos(), ((ActAddModeColorBinding) this.mViewBinding).pickViewHour);
        SelectorUtils.updateListData(arrayList2, ((ActAddModeColorVM) this.mViewModel).getMinPos(), ((ActAddModeColorBinding) this.mViewBinding).pickViewMin);
        SelectorUtils.updateListData(arrayList3, ((ActAddModeColorVM) this.mViewModel).getSecPos(), ((ActAddModeColorBinding) this.mViewBinding).pickViewSec);
        SelectorUtils.updateListData(arrayList4, ((ActAddModeColorVM) this.mViewModel).getMsPos(), ((ActAddModeColorBinding) this.mViewBinding).pickViewMs);
        this.mHourManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeColor$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView, int i5) {
                ActAddModeColor.this.lambda$initTimeBar$1(arrayList3, arrayList4, recyclerView, i5);
            }
        });
        this.mMinuteManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeColor$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView, int i5) {
                ActAddModeColor.this.lambda$initTimeBar$2(arrayList4, recyclerView, i5);
            }
        });
        this.mSecondManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeColor$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView, int i5) {
                ActAddModeColor.this.lambda$initTimeBar$3(arrayList, recyclerView, i5);
            }
        });
        this.mMillSecondManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeColor$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView, int i5) {
                ActAddModeColor.this.lambda$initTimeBar$4(arrayList, arrayList2, recyclerView, i5);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTimeBar$1(List list, List list2, RecyclerView recyclerView, int i) {
        ((ActAddModeColorVM) this.mViewModel).setHourPos(this.mHourManager.getPickedPosition());
        ((ActAddModeColorVM) this.mViewModel).setSecPos(0);
        ((ActAddModeColorVM) this.mViewModel).setMsPos(0);
        if (this.mViewBinding != 0) {
            SelectorUtils.updateListData(list, ((ActAddModeColorVM) this.mViewModel).getSecPos(), ((ActAddModeColorBinding) this.mViewBinding).pickViewSec);
            SelectorUtils.updateListData(list2, ((ActAddModeColorVM) this.mViewModel).getMsPos(), ((ActAddModeColorBinding) this.mViewBinding).pickViewMs);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTimeBar$2(List list, RecyclerView recyclerView, int i) {
        ((ActAddModeColorVM) this.mViewModel).setMinPos(this.mMinuteManager.getPickedPosition());
        ((ActAddModeColorVM) this.mViewModel).setMsPos(0);
        if (this.mViewBinding != 0) {
            SelectorUtils.updateListData(list, ((ActAddModeColorVM) this.mViewModel).getMsPos(), ((ActAddModeColorBinding) this.mViewBinding).pickViewMs);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTimeBar$3(List list, RecyclerView recyclerView, int i) {
        ((ActAddModeColorVM) this.mViewModel).setSecPos(this.mSecondManager.getPickedPosition());
        ((ActAddModeColorVM) this.mViewModel).setHourPos(0);
        if (this.mViewBinding != 0) {
            SelectorUtils.updateListData(list, ((ActAddModeColorVM) this.mViewModel).getHourPos(), ((ActAddModeColorBinding) this.mViewBinding).pickViewHour);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTimeBar$4(List list, List list2, RecyclerView recyclerView, int i) {
        ((ActAddModeColorVM) this.mViewModel).setMsPos(this.mMillSecondManager.getPickedPosition());
        ((ActAddModeColorVM) this.mViewModel).setHourPos(0);
        ((ActAddModeColorVM) this.mViewModel).setMinPos(0);
        if (this.mViewBinding != 0) {
            SelectorUtils.updateListData(list, ((ActAddModeColorVM) this.mViewModel).getHourPos(), ((ActAddModeColorBinding) this.mViewBinding).pickViewHour);
            SelectorUtils.updateListData(list2, ((ActAddModeColorVM) this.mViewModel).getMinPos(), ((ActAddModeColorBinding) this.mViewBinding).pickViewMin);
        }
    }

    private void setRBar() {
        ((ActAddModeColorBinding) this.mViewBinding).vsbRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeColor.3
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActAddModeColorVM) ActAddModeColor.this.mViewModel).setRed(progress);
                    ((ActAddModeColorBinding) ActAddModeColor.this.mViewBinding).etRed.setText(String.valueOf(progress));
                    ActAddModeColor.this.setBgColorView();
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActAddModeColorVM) ActAddModeColor.this.mViewModel).setRed(seekBar.getProgress());
                ((ActAddModeColorBinding) ActAddModeColor.this.mViewBinding).etRed.setText(String.valueOf(seekBar.getProgress()));
                ActAddModeColor.this.setBgColorView();
            }
        });
        ((ActAddModeColorBinding) this.mViewBinding).etRed.setListener(new ColorEditText.OnColorTextChangedListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeColor$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.view.ColorEditText.OnColorTextChangedListener
            public final void onTextChangedListener(String str) {
                ActAddModeColor.this.lambda$setRBar$5(str);
            }
        });
        ((ActAddModeColorBinding) this.mViewBinding).vsbRed.setProgress(((ActAddModeColorVM) this.mViewModel).contentItem.getRed());
        ((ActAddModeColorBinding) this.mViewBinding).etRed.setText(String.valueOf(((ActAddModeColorBinding) this.mViewBinding).vsbRed.getProgress()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setRBar$5(String str) {
        ((ActAddModeColorVM) this.mViewModel).setRed(Integer.parseInt(str));
        setBgColorView();
        ((ActAddModeColorBinding) this.mViewBinding).etRed.selectEnd();
        ((ActAddModeColorBinding) this.mViewBinding).vsbRed.setProgress(Integer.parseInt(str));
    }

    private void setGBar() {
        ((ActAddModeColorBinding) this.mViewBinding).vsbGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeColor.4
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActAddModeColorVM) ActAddModeColor.this.mViewModel).setGreen(progress);
                    ((ActAddModeColorBinding) ActAddModeColor.this.mViewBinding).etGreen.setText(String.valueOf(progress));
                    ActAddModeColor.this.setBgColorView();
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActAddModeColorVM) ActAddModeColor.this.mViewModel).setGreen(seekBar.getProgress());
                ((ActAddModeColorBinding) ActAddModeColor.this.mViewBinding).etGreen.setText(String.valueOf(seekBar.getProgress()));
                ActAddModeColor.this.setBgColorView();
            }
        });
        ((ActAddModeColorBinding) this.mViewBinding).etGreen.setListener(new ColorEditText.OnColorTextChangedListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeColor$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.view.ColorEditText.OnColorTextChangedListener
            public final void onTextChangedListener(String str) {
                ActAddModeColor.this.lambda$setGBar$6(str);
            }
        });
        ((ActAddModeColorBinding) this.mViewBinding).vsbGreen.setProgress(((ActAddModeColorVM) this.mViewModel).contentItem.getGreen());
        ((ActAddModeColorBinding) this.mViewBinding).etGreen.setText(String.valueOf(((ActAddModeColorBinding) this.mViewBinding).vsbGreen.getProgress()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setGBar$6(String str) {
        ((ActAddModeColorVM) this.mViewModel).setGreen(Integer.parseInt(str));
        setBgColorView();
        ((ActAddModeColorBinding) this.mViewBinding).etGreen.selectEnd();
        ((ActAddModeColorBinding) this.mViewBinding).vsbGreen.setProgress(Integer.parseInt(str));
    }

    private void setBBar() {
        ((ActAddModeColorBinding) this.mViewBinding).vsbBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeColor.5
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActAddModeColorVM) ActAddModeColor.this.mViewModel).setBlue(progress);
                    ((ActAddModeColorBinding) ActAddModeColor.this.mViewBinding).etBlue.setText(String.valueOf(progress));
                    ActAddModeColor.this.setBgColorView();
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActAddModeColorVM) ActAddModeColor.this.mViewModel).setBlue(seekBar.getProgress());
                ((ActAddModeColorBinding) ActAddModeColor.this.mViewBinding).etBlue.setText(String.valueOf(seekBar.getProgress()));
                ActAddModeColor.this.setBgColorView();
            }
        });
        ((ActAddModeColorBinding) this.mViewBinding).etBlue.setListener(new ColorEditText.OnColorTextChangedListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeColor$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.view.ColorEditText.OnColorTextChangedListener
            public final void onTextChangedListener(String str) {
                ActAddModeColor.this.lambda$setBBar$7(str);
            }
        });
        ((ActAddModeColorBinding) this.mViewBinding).vsbBlue.setProgress(((ActAddModeColorVM) this.mViewModel).getBlue());
        ((ActAddModeColorBinding) this.mViewBinding).etBlue.setText(String.valueOf(((ActAddModeColorBinding) this.mViewBinding).vsbBlue.getProgress()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setBBar$7(String str) {
        ((ActAddModeColorVM) this.mViewModel).setBlue(Integer.parseInt(str));
        setBgColorView();
        ((ActAddModeColorBinding) this.mViewBinding).etBlue.selectEnd();
        ((ActAddModeColorBinding) this.mViewBinding).vsbBlue.setProgress(Integer.parseInt(str));
    }

    private void setWyBar() {
        ((ActAddModeColorBinding) this.mViewBinding).groupWy.setVisibility(0);
        ((ActAddModeColorBinding) this.mViewBinding).groupW.setVisibility(0);
        ((ActAddModeColorBinding) this.mViewBinding).tvWTip.setText(getString(R.string.wy));
        ((ActAddModeColorBinding) this.mViewBinding).sbW.setIncludeZero(true);
        ((ActAddModeColorBinding) this.mViewBinding).sbW.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeColor.6
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActAddModeColorVM) ActAddModeColor.this.mViewModel).setWyBrt(LightUtils.progress2BrtHasBelowOne(progress));
                    ((ActAddModeColorBinding) ActAddModeColor.this.mViewBinding).tvW.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActAddModeColorVM) ActAddModeColor.this.mViewModel).setWyBrt(LightUtils.progress2BrtHasBelowOne(seekBar.getProgress()));
                ((ActAddModeColorBinding) ActAddModeColor.this.mViewBinding).tvW.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
            }
        });
        ((ActAddModeColorBinding) this.mViewBinding).vsbWy.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeColor.7
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActAddModeColorVM) ActAddModeColor.this.mViewModel).setwOrWy(progress);
                    ((ActAddModeColorBinding) ActAddModeColor.this.mViewBinding).etWy.setText(String.valueOf(progress));
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActAddModeColorVM) ActAddModeColor.this.mViewModel).setwOrWy(seekBar.getProgress());
                ((ActAddModeColorBinding) ActAddModeColor.this.mViewBinding).etWy.setText(String.valueOf(seekBar.getProgress()));
            }
        });
        ((ActAddModeColorBinding) this.mViewBinding).etWy.setListener(new ColorEditText.OnColorTextChangedListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeColor$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.view.ColorEditText.OnColorTextChangedListener
            public final void onTextChangedListener(String str) {
                ActAddModeColor.this.lambda$setWyBar$8(str);
            }
        });
        ((ActAddModeColorBinding) this.mViewBinding).sbW.setProgress(LightUtils.brt2ProgressHasBelowZero(((ActAddModeColorVM) this.mViewModel).getWyBrt()));
        ((ActAddModeColorBinding) this.mViewBinding).tvW.setText(((ActAddModeColorBinding) this.mViewBinding).sbW.getProgressHasBelowOne());
        ((ActAddModeColorBinding) this.mViewBinding).vsbWy.setProgress(((ActAddModeColorVM) this.mViewModel).getwOrWy());
        ((ActAddModeColorBinding) this.mViewBinding).etWy.setText(String.valueOf(((ActAddModeColorBinding) this.mViewBinding).vsbWy.getProgress()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setWyBar$8(String str) {
        ((ActAddModeColorVM) this.mViewModel).setwOrWy(Integer.parseInt(str));
        ((ActAddModeColorBinding) this.mViewBinding).etWy.selectEnd();
        ((ActAddModeColorBinding) this.mViewBinding).vsbWy.setProgress(Integer.parseInt(str));
    }

    private void setBrtBar() {
        ((ActAddModeColorBinding) this.mViewBinding).sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeColor.8
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActAddModeColorVM) ActAddModeColor.this.mViewModel).setRgbBrt(LightUtils.progress2BrtHasBelowOne(progress));
                    ((ActAddModeColorBinding) ActAddModeColor.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActAddModeColorVM) ActAddModeColor.this.mViewModel).setRgbBrt(LightUtils.progress2BrtHasBelowOne(seekBar.getProgress()));
                ((ActAddModeColorBinding) ActAddModeColor.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
            }
        });
        ((ActAddModeColorBinding) this.mViewBinding).sbBrt.setProgress(LightUtils.brt2ProgressHasBelowZero(((ActAddModeColorVM) this.mViewModel).getRgbBrt()));
        ((ActAddModeColorBinding) this.mViewBinding).tvBrt.setText(((ActAddModeColorBinding) this.mViewBinding).sbBrt.getProgressHasBelowOne());
    }

    private void setWBar() {
        ((ActAddModeColorBinding) this.mViewBinding).groupW.setVisibility(0);
        ((ActAddModeColorBinding) this.mViewBinding).tvWTip.setText(getString(R.string.w));
        ((ActAddModeColorBinding) this.mViewBinding).sbW.setIncludeZero(true);
        ((ActAddModeColorBinding) this.mViewBinding).sbW.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.mode.ActAddModeColor.9
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActAddModeColorVM) ActAddModeColor.this.mViewModel).setwOrWy(LightUtils.progress2BrtHasBelowOne(progress));
                    ((ActAddModeColorBinding) ActAddModeColor.this.mViewBinding).tvW.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActAddModeColorVM) ActAddModeColor.this.mViewModel).setwOrWy(LightUtils.progress2BrtHasBelowOne(seekBar.getProgress()));
                ((ActAddModeColorBinding) ActAddModeColor.this.mViewBinding).tvW.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
            }
        });
        ((ActAddModeColorBinding) this.mViewBinding).sbW.setProgress(LightUtils.brt2ProgressHasBelowZero(((ActAddModeColorVM) this.mViewModel).getwOrWy()));
        ((ActAddModeColorBinding) this.mViewBinding).tvW.setText(((ActAddModeColorBinding) this.mViewBinding).sbW.getProgressHasBelowOne());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBgColorView() {
        ((ActAddModeColorBinding) this.mViewBinding).vColor.setBackgroundColor(Color.rgb(((ActAddModeColorVM) this.mViewModel).getRed(), ((ActAddModeColorVM) this.mViewModel).getGreen(), ((ActAddModeColorVM) this.mViewModel).getBlue()));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        Intent intent = new Intent();
        ((ActAddModeColorVM) this.mViewModel).contentItem.setTime(AdvancedModeHelper.calculateTotalTime(((ActAddModeColorVM) this.mViewModel).getHourPos(), ((ActAddModeColorVM) this.mViewModel).getMinPos(), ((ActAddModeColorVM) this.mViewModel).getSecPos(), ((ActAddModeColorVM) this.mViewModel).getMsPos()));
        intent.putExtra(Constants.MODE_ITEM_INFO, ((ActAddModeColorVM) this.mViewModel).contentItem);
        setResult(6001, intent);
        finishActivity();
    }
}