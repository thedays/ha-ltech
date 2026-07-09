package com.ltech.smarthome.ui.circadianlighting;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.google.common.primitives.Ints;
import com.google.gson.reflect.TypeToken;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.loc.at;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActLightPlanDetailBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail;
import com.ltech.smarthome.ui.mode.ActSelectDeviceForMode;
import com.ltech.smarthome.ui.mode.ColorArray;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.SelectGradientStateDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.product_agreement.bean.RhythmsPlanInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes4.dex */
public class ActLightPlanDetail extends VMActivity<ActLightPlanDetailBinding, ActLightPlanDetailVM> {
    private int kMax;
    private int kMin;
    private BaseItemDraggableAdapter<RhythmsPlanInfo.Item, BaseViewHolder> mAdapter;
    private int mMaxkelvin;
    private int mMinkelvin;
    private BaseQuickAdapter<Integer, BaseViewHolder> mQuickSelAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_light_plan_detail;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.app_light_plan));
        setEditString(getString(R.string.save));
        initList();
        initIntQuickList();
        initSeekbar();
        ((ActLightPlanDetailBinding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActLightPlanDetailBinding) this.mViewBinding).title.ivSearch.setImageResource(R.mipmap.ic_import);
        ((ActLightPlanDetailBinding) this.mViewBinding).title.ivSearch.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActLightPlanDetail.this.lambda$initView$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        NavUtils.destination(ActSelectDeviceForMode.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withInt(Constants.MODE_NUM, ((ActLightPlanDetailVM) this.mViewModel).getPlanId()).withInt(Constants.MODE_TYPE, 3).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
    }

    private void initIntQuickList() {
        ((ActLightPlanDetailBinding) this.mViewBinding).quickRv.setLayoutManager(new LinearLayoutManager(this, 0, false));
        RecyclerView recyclerView = ((ActLightPlanDetailBinding) this.mViewBinding).quickRv;
        BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Integer, BaseViewHolder>(R.layout.item_ct_color_selector) { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder holder, Integer k) {
                int i;
                int i2;
                holder.setText(R.id.sel_tv, k + at.k);
                List<Integer> asList = Ints.asList(this.mContext.getResources().getIntArray(R.array.k_value_color));
                if (ActLightPlanDetail.this.mMaxkelvin + ActLightPlanDetail.this.mMinkelvin == 0) {
                    i = 6500;
                    i2 = 2700;
                } else {
                    i = ActLightPlanDetail.this.mMaxkelvin;
                    i2 = ActLightPlanDetail.this.mMinkelvin;
                }
                if (k.intValue() > i || k.intValue() < i2) {
                    holder.setImageDrawable(R.id.iv, new ColorDrawable(ActLightPlanDetail.this.getResources().getColor(R.color.gray)));
                    holder.itemView.setEnabled(false);
                } else {
                    holder.setImageDrawable(R.id.iv, new ColorDrawable(asList.get(holder.getLayoutPosition()).intValue()));
                    holder.itemView.setEnabled(true);
                }
            }
        };
        this.mQuickSelAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        ((ActLightPlanDetailBinding) this.mViewBinding).quickRv.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), 0);
            }
        });
        this.mQuickSelAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail.3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ((ActLightPlanDetailBinding) ActLightPlanDetail.this.mViewBinding).kTv.setText(ActLightPlanDetail.this.mQuickSelAdapter.getData().get(i) + "K");
                int ctK2Y = 255 - LightUtils.ctK2Y(((Integer) ActLightPlanDetail.this.mQuickSelAdapter.getData().get(i)).intValue(), ActLightPlanDetail.this.kMax, ActLightPlanDetail.this.kMin);
                RhythmsPlanInfo.Item item = (RhythmsPlanInfo.Item) ActLightPlanDetail.this.mAdapter.getData().get(((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).getSelectedPos());
                item.setC(ctK2Y);
                item.setwOrWy(255 - ctK2Y);
                ((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).getLightCmdHelper().sendCW(ActLightPlanDetail.this.activity, ctK2Y, false);
                ActLightPlanDetail.this.mAdapter.notifyItemChanged(((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).getSelectedPos());
                if (item.getC() > ((ActLightPlanDetailBinding) ActLightPlanDetail.this.mViewBinding).csbColorBar.getMaxProgress()) {
                    ((ActLightPlanDetailBinding) ActLightPlanDetail.this.mViewBinding).csbColorBar.setProgress(((ActLightPlanDetailBinding) ActLightPlanDetail.this.mViewBinding).csbColorBar.getMaxProgress());
                } else if (item.getC() < ((ActLightPlanDetailBinding) ActLightPlanDetail.this.mViewBinding).csbColorBar.getMinProgress()) {
                    ((ActLightPlanDetailBinding) ActLightPlanDetail.this.mViewBinding).csbColorBar.setProgress(((ActLightPlanDetailBinding) ActLightPlanDetail.this.mViewBinding).csbColorBar.getMinProgress());
                } else {
                    ((ActLightPlanDetailBinding) ActLightPlanDetail.this.mViewBinding).csbColorBar.setProgress(item.getC());
                }
            }
        });
    }

    private void initSeekbar() {
        ((ActLightPlanDetailBinding) this.mViewBinding).csbColorBar.setOnRangeChangedListener(new OnRangeChangedListener() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail.4
            @Override // com.jaygoo.widget.OnRangeChangedListener
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {
            }

            @Override // com.jaygoo.widget.OnRangeChangedListener
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
            }

            @Override // com.jaygoo.widget.OnRangeChangedListener
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                if (ActLightPlanDetail.this.mViewBinding != null && isFromUser) {
                    int stepK = LightUtils.getStepK((int) leftValue, 1);
                    RhythmsPlanInfo.Item item = (RhythmsPlanInfo.Item) ActLightPlanDetail.this.mAdapter.getData().get(((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).getSelectedPos());
                    item.setC(stepK);
                    int i = 255 - stepK;
                    item.setwOrWy(i);
                    ((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).getLightCmdHelper().sendCW(ActLightPlanDetail.this.activity, stepK, false);
                    ActLightPlanDetail.this.mAdapter.notifyItemChanged(((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).getSelectedPos());
                    ((ActLightPlanDetailBinding) ActLightPlanDetail.this.mViewBinding).kTv.setText(LightUtils.ctY2K(i, ActLightPlanDetail.this.kMax, ActLightPlanDetail.this.kMin) + "K");
                }
            }
        });
        ((ActLightPlanDetailBinding) this.mViewBinding).sbBrt.setIncludeZero(true);
        ((ActLightPlanDetailBinding) this.mViewBinding).sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail.5
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((RhythmsPlanInfo.Item) ActLightPlanDetail.this.mAdapter.getData().get(((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).getSelectedPos())).setWyBrt(LightUtils.progress2BrtIncludeZero(progress));
                    ActLightPlanDetail.this.mAdapter.notifyItemChanged(((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).getSelectedPos());
                    ((ActLightPlanDetailBinding) ActLightPlanDetail.this.mViewBinding).pTv.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                    ((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).getLightCmdHelper().sendCtBrtHas0to9(ActivityUtils.getTopActivity(), progress, false);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((RhythmsPlanInfo.Item) ActLightPlanDetail.this.mAdapter.getData().get(((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).getSelectedPos())).setWyBrt(LightUtils.progress2BrtIncludeZero(seekBar.getProgress()));
                ActLightPlanDetail.this.mAdapter.notifyItemChanged(((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).getSelectedPos());
                ((ActLightPlanDetailBinding) ActLightPlanDetail.this.mViewBinding).pTv.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                ((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).getLightCmdHelper().sendCtBrtHas0to9(ActivityUtils.getTopActivity(), seekBar.getProgress(), true);
            }
        });
    }

    private void initList() {
        ((ActLightPlanDetailBinding) this.mViewBinding).rv.clearAnimation();
        ((ActLightPlanDetailBinding) this.mViewBinding).rv.setItemAnimator(null);
        RecyclerView recyclerView = ((ActLightPlanDetailBinding) this.mViewBinding).rv;
        BaseItemDraggableAdapter<RhythmsPlanInfo.Item, BaseViewHolder> baseItemDraggableAdapter = new BaseItemDraggableAdapter<RhythmsPlanInfo.Item, BaseViewHolder>(R.layout.item_light_plan_node, ((ActLightPlanDetailVM) this.mViewModel).getData()) { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail.6
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, RhythmsPlanInfo.Item item) {
                Drawable drawable;
                int c2 = 255 - item.getC();
                helper.setBackgroundColor(R.id.iv_bg, Color.rgb(ColorArray.CT_COLOR_ARRAY[c2][0], ColorArray.CT_COLOR_ARRAY[c2][1], ColorArray.CT_COLOR_ARRAY[c2][2]));
                helper.getView(R.id.iv_bg).setAlpha(0.5f);
                helper.getView(R.id.view_brt_bg).setAlpha((((100 - item.getWyBrt()) * 175) / 255.0f) / 255.0f);
                View view = helper.getView(R.id.layout_bg);
                if (item.isSelect()) {
                    drawable = ActLightPlanDetail.this.getResources().getDrawable(R.drawable.shape_red_stroke_node);
                } else {
                    drawable = ActLightPlanDetail.this.getResources().getDrawable(R.drawable.shape_white_stroke_node);
                }
                view.setBackground(drawable);
                helper.setProgress(R.id.sb, item.getH() > 12 ? 24 - item.getH() : item.getH()).setEnabled(R.id.sb, false);
                helper.setVisible(R.id.triangle_iv, ((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).getSelectedPos() == helper.getLayoutPosition());
                helper.setText(R.id.tv_time, String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(item.getH()), Integer.valueOf(item.getM())));
            }
        };
        this.mAdapter = baseItemDraggableAdapter;
        recyclerView.setAdapter(baseItemDraggableAdapter);
        OnItemDragListener onItemDragListener = new OnItemDragListener(this) { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail.7
            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
            }

            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
            }
        };
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(this.mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(((ActLightPlanDetailBinding) this.mViewBinding).rv);
        itemDragAndSwipeCallback.setDragMoveFlags(15);
        this.mAdapter.enableDragItem(itemTouchHelper);
        this.mAdapter.setOnItemDragListener(onItemDragListener);
        ((ActLightPlanDetailBinding) this.mViewBinding).rv.setHasFixedSize(true);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail.8
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).getLastPos() != -1) {
                    adapter.notifyItemChanged(((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).getLastPos());
                }
                ActLightPlanDetail.this.setData(position);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setData(int pos) {
        if (this.mAdapter.getData().size() > pos) {
            selectView(pos);
            ((ActLightPlanDetailVM) this.mViewModel).setSelectedPos(pos);
            this.mAdapter.notifyItemChanged(pos);
            RhythmsPlanInfo.Item item = this.mAdapter.getData().get(pos);
            if (item.getC() > ((ActLightPlanDetailBinding) this.mViewBinding).csbColorBar.getMaxProgress()) {
                ((ActLightPlanDetailBinding) this.mViewBinding).csbColorBar.setProgress(((ActLightPlanDetailBinding) this.mViewBinding).csbColorBar.getMaxProgress());
            } else if (item.getC() < ((ActLightPlanDetailBinding) this.mViewBinding).csbColorBar.getMinProgress()) {
                ((ActLightPlanDetailBinding) this.mViewBinding).csbColorBar.setProgress(((ActLightPlanDetailBinding) this.mViewBinding).csbColorBar.getMinProgress());
            } else {
                ((ActLightPlanDetailBinding) this.mViewBinding).csbColorBar.setProgress(item.getC());
            }
            ((ActLightPlanDetailBinding) this.mViewBinding).kTv.setText(LightUtils.ctY2K(255 - item.getC(), this.kMax, this.kMin) + "K");
            ((ActLightPlanDetailBinding) this.mViewBinding).sbBrt.setProgress(LightUtils.brt2ProgressHasBelowZero(item.getWyBrt()));
            ((ActLightPlanDetailBinding) this.mViewBinding).pTv.setText(((ActLightPlanDetailBinding) this.mViewBinding).sbBrt.getProgressHasBelowOne());
            ((ActLightPlanDetailBinding) this.mViewBinding).timeTv.setText(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(item.getH()), Integer.valueOf(item.getM())));
        }
    }

    private void selectView(int pos) {
        int i = 0;
        while (i < ((ActLightPlanDetailVM) this.mViewModel).getData().size()) {
            ((ActLightPlanDetailVM) this.mViewModel).getData().get(i).setSelect(i == pos);
            i++;
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActLightPlanDetailVM) this.mViewModel).setBatch(getIntent().getBooleanExtra(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH, false));
        if (!((ActLightPlanDetailVM) this.mViewModel).isBatch()) {
            ((ActLightPlanDetailVM) this.mViewModel).setControlId(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
            ((ActLightPlanDetailVM) this.mViewModel).setGroupControl(getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false));
        }
        ((ActLightPlanDetailVM) this.mViewModel).setCurPlanId(getIntent().getIntExtra(Constants.LIGHT_RHYTHMS_PLAN_ID_CUR, 1));
        ((ActLightPlanDetailVM) this.mViewModel).setPlanId(getIntent().getIntExtra(Constants.LIGHT_RHYTHMS_PLAN_ID, 1));
        ((ActLightPlanDetailVM) this.mViewModel).setIsOn(getIntent().getBooleanExtra(Constants.LIGHT_RHYTHMS_ON_OFF, false));
        ((ActLightPlanDetailVM) this.mViewModel).setRepeat(getIntent().getIntExtra(Constants.LIGHT_RHYTHMS_REPEAT, 128));
        ((ActLightPlanDetailVM) this.mViewModel).setName(getIntent().getStringExtra(Constants.MODE_NAME));
        this.kMin = 2700;
        this.kMax = 6500;
        ((ActLightPlanDetailBinding) this.mViewBinding).csbColorBar.setSeekBarMode(1);
        ((ActLightPlanDetailBinding) this.mViewBinding).csbColorBar.setRange(0.0f, 255.0f);
        if (!getIntent().getBooleanExtra(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH, false)) {
            if (((ActLightPlanDetailVM) this.mViewModel).isGroupControl()) {
                Group groupById = Injection.repo().group().getGroupById(((ActLightPlanDetailVM) this.mViewModel).getControlId());
                ((ActLightPlanDetailVM) this.mViewModel).controlObject.setValue(groupById);
                this.mMaxkelvin = groupById.getMaxkelvin();
                this.mMinkelvin = groupById.getMinkelvin();
            } else {
                Device deviceById = Injection.repo().device().getDeviceById(((ActLightPlanDetailVM) this.mViewModel).getControlId());
                this.mMaxkelvin = deviceById.getMaxkelvin();
                this.mMinkelvin = deviceById.getMinkelvin();
                ((ActLightPlanDetailVM) this.mViewModel).controlObject.setValue(deviceById);
            }
        } else {
            this.mMinkelvin = 1000;
            this.mMaxkelvin = 10000;
        }
        if (this.mMaxkelvin + this.mMinkelvin > 0) {
            this.kMin = 1000;
            this.kMax = 10000;
            ((ActLightPlanDetailBinding) this.mViewBinding).csbColorBar.setRange(255 - LightUtils.ctK2Y(this.mMinkelvin, this.kMax, this.kMin), 255 - LightUtils.ctK2Y(this.mMaxkelvin, this.kMax, this.kMin));
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(2700);
        arrayList.add(3500);
        arrayList.add(4000);
        arrayList.add(5000);
        arrayList.add(6000);
        arrayList.add(6500);
        this.mQuickSelAdapter.replaceData(arrayList);
        List<RhythmsPlanInfo.Item> plan = ((ActLightPlanDetailVM) this.mViewModel).getPlan((Map) GsonUtils.fromJson(getIntent().getStringExtra(Constants.LIGHT_RHYTHMS_ONLINE_PLAN), new TypeToken<Map<Integer, String>>(this) { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail.9
        }.getType()));
        ((ActLightPlanDetailBinding) this.mViewBinding).rv.setLayoutManager(new GridLayoutManager(this, plan.size() < 12 ? plan.size() + 1 : plan.size()));
        this.mAdapter.replaceData(plan);
        showAddBtn(this.mAdapter.getData().size());
        setData(0);
        ((ActLightPlanDetailVM) this.mViewModel).delEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail.10
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActLightPlanDetail.this.del();
            }
        });
        ((ActLightPlanDetailVM) this.mViewModel).timeEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail.11
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActLightPlanDetail.this.showTimeDialog();
            }
        });
        ((ActLightPlanDetailVM) this.mViewModel).renameDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail.12
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActLightPlanDetail.this.showEditNameDialog();
            }
        });
        ((ActLightPlanDetailVM) this.mViewModel).changeGradientMode.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail.13
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                SelectGradientStateDialog asDefault = SelectGradientStateDialog.asDefault();
                asDefault.setSelectPosition(((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).selectedGradientModeData.getValue().intValue());
                asDefault.setGradientTime(((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).selectedGradientTime.getValue().intValue());
                asDefault.showDialog(ActLightPlanDetail.this.activity);
                asDefault.setOnSaveListener(new SelectGradientStateDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail.13.1
                    @Override // com.ltech.smarthome.view.dialog.SelectGradientStateDialog.OnSaveListener
                    public void cancel() {
                    }

                    @Override // com.ltech.smarthome.view.dialog.SelectGradientStateDialog.OnSaveListener
                    public boolean onSave(SelectGradientStateDialog.OnOffState onOffState, int selectPos) {
                        ((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).selectedGradientModeData.setValue(Integer.valueOf(selectPos));
                        ((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).selectedGradientTime.setValue(Integer.valueOf(onOffState.getSubValue()));
                        ((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).setGradientTime(onOffState.getSubValue());
                        return true;
                    }
                });
            }
        });
        ((ActLightPlanDetailVM) this.mViewModel).selectedGradientTime.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail.14
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                String str;
                AppCompatTextView appCompatTextView = ((ActLightPlanDetailBinding) ActLightPlanDetail.this.mViewBinding).gradientTv;
                if (integer.intValue() == 0) {
                    str = ActLightPlanDetail.this.getString(R.string.app_gradient_full);
                } else {
                    str = integer.intValue() + " min";
                }
                appCompatTextView.setText(str);
            }
        });
        ((ActLightPlanDetailVM) this.mViewModel).factoryResetEvent.observe(this, new AnonymousClass15());
    }

    /* renamed from: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail$15, reason: invalid class name */
    class AnonymousClass15 implements Observer<List<RhythmsPlanInfo.Item>> {
        AnonymousClass15() {
        }

        @Override // androidx.lifecycle.Observer
        public void onChanged(final List<RhythmsPlanInfo.Item> items) {
            ActLightPlanDetail actLightPlanDetail = ActLightPlanDetail.this;
            MessageDialog.show(actLightPlanDetail, actLightPlanDetail.getString(R.string.tips), ActLightPlanDetail.this.getString(R.string.reset_factory_defaults)).setOkButton(ActLightPlanDetail.this.getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail$15$$ExternalSyntheticLambda0
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$onChanged$0;
                    lambda$onChanged$0 = ActLightPlanDetail.AnonymousClass15.this.lambda$onChanged$0(items, baseDialog, view);
                    return lambda$onChanged$0;
                }
            }).setCancelButton(ActLightPlanDetail.this.getString(R.string.cancel));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$onChanged$0(List list, BaseDialog baseDialog, View view) {
            ((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).setName(ActLightPlanDetail.this.getResources().getStringArray(R.array.light_rhythms_diy)[((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).getPlanId() - 1]);
            ((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).selectedGradientModeData.setValue(Integer.valueOf(Integer.parseInt(((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).defaultPlans.get(((ActLightPlanDetailVM) ActLightPlanDetail.this.mViewModel).getPlanId() - 1).substring(4, 6), 16)));
            ((ActLightPlanDetailBinding) ActLightPlanDetail.this.mViewBinding).rv.setLayoutManager(new GridLayoutManager(ActLightPlanDetail.this.activity, list.size() < 12 ? list.size() + 1 : list.size()));
            ActLightPlanDetail.this.mAdapter.replaceData(list);
            ActLightPlanDetail actLightPlanDetail = ActLightPlanDetail.this;
            actLightPlanDetail.showAddBtn(actLightPlanDetail.mAdapter.getData().size());
            ActLightPlanDetail.this.setData(0);
            return false;
        }
    }

    protected void showEditNameDialog() {
        EditDialog.asDefault().setContent(((ActLightPlanDetailVM) this.mViewModel).getName()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightPlanDetail.this.lambda$showEditNameDialog$1((String) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$1(String str) {
        ((ActLightPlanDetailVM) this.mViewModel).setName(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void del() {
        if (this.mAdapter.getData().size() <= 4) {
            SmartToast.showShort(getString(R.string.app_circadian_lighting_del_node_tip));
            return;
        }
        this.mAdapter.remove(((ActLightPlanDetailVM) this.mViewModel).getSelectedPos());
        ((ActLightPlanDetailBinding) this.mViewBinding).rv.setLayoutManager(new GridLayoutManager(this.activity, this.mAdapter.getData().size() + 1));
        setData(0);
        showAddBtn(this.mAdapter.getData().size());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAddBtn(int count) {
        if (count >= 12 || this.mAdapter.getFooterLayoutCount() != 0) {
            return;
        }
        View inflate = LayoutInflater.from(this).inflate(R.layout.item_light_plan_node_add, (ViewGroup) ((ActLightPlanDetailBinding) this.mViewBinding).rv, false);
        this.mAdapter.addFooterView(inflate);
        inflate.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail.16
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                RhythmsPlanInfo.Item item = (RhythmsPlanInfo.Item) ActLightPlanDetail.this.mAdapter.getData().get(ActLightPlanDetail.this.mAdapter.getData().size() - 1);
                int h = item.getH();
                int m2 = item.getM();
                int i = h + 1;
                if (i > 23) {
                    i = 0;
                }
                ActLightPlanDetail.this.mAdapter.addData((BaseItemDraggableAdapter) new RhythmsPlanInfo.Item(item.getWyBrt(), item.getwOrWy(), i, m2));
                if (ActLightPlanDetail.this.mAdapter.getData().size() == 12) {
                    ActLightPlanDetail.this.mAdapter.removeAllFooterView();
                }
                ((ActLightPlanDetailBinding) ActLightPlanDetail.this.mViewBinding).rv.setLayoutManager(new GridLayoutManager(ActLightPlanDetail.this.activity, ActLightPlanDetail.this.mAdapter.getData().size() < 12 ? ActLightPlanDetail.this.mAdapter.getData().size() + 1 : ActLightPlanDetail.this.mAdapter.getData().size()));
            }
        });
    }

    public void showTimeDialog() {
        final RhythmsPlanInfo.Item item = this.mAdapter.getData().get(((ActLightPlanDetailVM) this.mViewModel).getSelectedPos());
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 24; i++) {
            arrayList.add(i < 10 ? "0" + i : String.valueOf(i));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < 60; i2++) {
            arrayList2.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        TimeSelectDialog.asDefault().setTitle(getString(R.string.app_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(false).setMinUnit(getString(R.string.hour)).setSecUnit(getString(R.string.min)).setSelectMinPosition(item.getH()).setSelectSecPosition(item.getM()).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetail$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActLightPlanDetail.this.lambda$showTimeDialog$2(item, i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showTimeDialog$2(RhythmsPlanInfo.Item item, int i, int i2) {
        item.setH(i);
        item.setM(i2);
        this.mAdapter.setData(((ActLightPlanDetailVM) this.mViewModel).getSelectedPos(), item);
        setData(((ActLightPlanDetailVM) this.mViewModel).getSelectedPos());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (!getIntent().getBooleanExtra(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH, false)) {
            ((ActLightPlanDetailVM) this.mViewModel).save();
        } else if (((ActLightPlanDetailVM) this.mViewModel).checkTime()) {
            ((ActLightPlanDetailVM) this.mViewModel).saveToServer(true);
        } else {
            ((ActLightPlanDetailVM) this.mViewModel).showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.app_circadian_lighting_save_error_tip));
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 6002 || data == null) {
            return;
        }
        List<RhythmsPlanInfo.Item> planDetail = ((ActLightPlanDetailVM) this.mViewModel).getPlanDetail(data.getStringExtra(Constants.MODE_DATA));
        if (planDetail.size() > 0) {
            ((ActLightPlanDetailBinding) this.mViewBinding).rv.setLayoutManager(new GridLayoutManager(this.activity, planDetail.size() < 12 ? planDetail.size() + 1 : planDetail.size()));
            this.mAdapter.replaceData(planDetail);
        }
    }
}