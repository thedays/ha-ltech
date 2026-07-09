package com.ltech.smarthome.ui.mode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.FtAdvancedModeBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.ModeContent;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.ModeUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.dialog.MultiSelectListDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.product_agreement.bean.AdvancedModeInfo;
import com.smart.product_agreement.bean.AdvancedModeList;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class FtAdvancedMode extends VMFragment<FtAdvancedModeBinding, BaseEditAdvancedModeVM> {
    private static FtAdvancedMode mFtAdvancedMode;
    private String B;
    private String ChangeTime;
    private String G;
    private String Rg;
    private String RgbBrt;
    private String StopTime;
    private String Wy;
    private String WyBrt;
    private String color_num;
    private String details;
    private String loop_num;
    private BaseQuickAdapter<ModeContent, BaseViewHolder> mAdapter;
    private List<String> select_list = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_advanced_mode;
    }

    public static FtAdvancedMode getObj() {
        return mFtAdvancedMode;
    }

    public static FtAdvancedMode newInstance(long controlId, boolean groupControl, int zoneNum, int lightType) {
        FtAdvancedMode ftAdvancedMode = new FtAdvancedMode();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.CONTROL_ID, controlId);
        bundle.putBoolean(Constants.GROUP_CONTROL, groupControl);
        bundle.putInt(Constants.ZONE_NUM, zoneNum);
        bundle.putInt(Constants.LIGHT_TYPE, lightType);
        ftAdvancedMode.setArguments(bundle);
        return ftAdvancedMode;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.VMFragment
    public BaseEditAdvancedModeVM getViewModel() {
        return (BaseEditAdvancedModeVM) new ViewModelProvider(getActivity()).get(ActCmdEditAdvancedModeVM.class);
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        mFtAdvancedMode = this;
        ((BaseEditAdvancedModeVM) this.mViewModel).placeId = Injection.repo().home().getSelectPlace().getValue().getPlaceId();
        Bundle arguments = getArguments();
        if (arguments != null) {
            ((BaseEditAdvancedModeVM) this.mViewModel).controlId = arguments.getLong(Constants.CONTROL_ID);
            ((BaseEditAdvancedModeVM) this.mViewModel).zoneNum = arguments.getInt(Constants.ZONE_NUM);
            ((BaseEditAdvancedModeVM) this.mViewModel).lightType = arguments.getInt(Constants.LIGHT_TYPE);
        }
        if (((BaseEditAdvancedModeVM) this.mViewModel).controlId != -1) {
            ((FtAdvancedModeBinding) this.mViewBinding).vPlayAll.setVisibility(0);
            ((FtAdvancedModeBinding) this.mViewBinding).circleImageView1.setImageResource(R.mipmap.light_mode_list);
            ((FtAdvancedModeBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.mode.FtAdvancedMode$$ExternalSyntheticLambda8
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    FtAdvancedMode.this.lambda$initView$0((View) obj);
                }
            }));
        }
        BaseQuickAdapter<ModeContent, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ModeContent, BaseViewHolder>(R.layout.item_mode, ((BaseEditAdvancedModeVM) this.mViewModel).modeContentList) { // from class: com.ltech.smarthome.ui.mode.FtAdvancedMode.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, ModeContent item) {
                if (((BaseEditAdvancedModeVM) FtAdvancedMode.this.mViewModel).controlId != -1) {
                    helper.setImageResource(R.id.iv_icon, ((BaseEditAdvancedModeVM) FtAdvancedMode.this.mViewModel).getPicList(this.mContext).get(item.getPicIndex()).intValue()).setText(R.id.tv_name, item.getModeName()).setText(R.id.tv_times, item.getPlayTimes() > 0 ? String.format(Locale.US, "%d%s", Integer.valueOf(item.getPlayTimes()), FtAdvancedMode.this.getString(R.string.times)) : FtAdvancedMode.this.getString(R.string.loop_play)).setVisible(R.id.tv_times, true).setVisible(R.id.iv_more, false);
                } else {
                    helper.setImageResource(R.id.iv_icon, ((BaseEditAdvancedModeVM) FtAdvancedMode.this.mViewModel).getPicList(this.mContext).get(item.getPicIndex()).intValue()).setText(R.id.tv_name, item.getModeName()).setText(R.id.tv_times, item.getPlayTimes() > 0 ? String.format(Locale.US, "%d%s", Integer.valueOf(item.getPlayTimes()), FtAdvancedMode.this.getString(R.string.times)) : FtAdvancedMode.this.getString(R.string.loop_play)).setVisible(R.id.tv_times, true).setText(R.id.iv_more, R.string.application).addOnClickListener(R.id.v_more_click);
                }
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.mode.FtAdvancedMode$$ExternalSyntheticLambda9
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                FtAdvancedMode.this.lambda$initView$1(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.mode.FtAdvancedMode$$ExternalSyntheticLambda10
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                FtAdvancedMode.this.lambda$initView$2(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((FtAdvancedModeBinding) this.mViewBinding).rvMode);
        ((FtAdvancedModeBinding) this.mViewBinding).rvMode.setItemAnimator(new RecyclerView.ItemAnimator(this) { // from class: com.ltech.smarthome.ui.mode.FtAdvancedMode.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
            public boolean animateAppearance(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo preLayoutInfo, RecyclerView.ItemAnimator.ItemHolderInfo postLayoutInfo) {
                return false;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
            public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, RecyclerView.ItemAnimator.ItemHolderInfo preLayoutInfo, RecyclerView.ItemAnimator.ItemHolderInfo postLayoutInfo) {
                return false;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
            public boolean animateDisappearance(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo preLayoutInfo, RecyclerView.ItemAnimator.ItemHolderInfo postLayoutInfo) {
                return false;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
            public boolean animatePersistence(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo preLayoutInfo, RecyclerView.ItemAnimator.ItemHolderInfo postLayoutInfo) {
                return false;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
            public void endAnimation(RecyclerView.ViewHolder item) {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
            public void endAnimations() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
            public boolean isRunning() {
                return false;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
            public void runPendingAnimations() {
            }
        });
        ((FtAdvancedModeBinding) this.mViewBinding).rvMode.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        ((FtAdvancedModeBinding) this.mViewBinding).rvMode.setHasFixedSize(true);
        ((FtAdvancedModeBinding) this.mViewBinding).rvMode.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.mode.FtAdvancedMode.3
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(5.0f));
            }
        });
        ((FtAdvancedModeBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((FtAdvancedModeBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((FtAdvancedModeBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((FtAdvancedModeBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.mode.FtAdvancedMode$$ExternalSyntheticLambda1
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                FtAdvancedMode.this.lambda$initView$3(refreshLayout);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        int id = view.getId();
        if (id == R.id.iv_list_times) {
            showPlayTimeDialog(true, 0);
            return;
        }
        if (id == R.id.iv_play_list) {
            showPlayListDialog();
        } else {
            if (id != R.id.v_play_all) {
                return;
            }
            if (this.select_list.size() == 0) {
                showPlayListDialog();
            } else {
                ((BaseEditAdvancedModeVM) this.mViewModel).playAllList(getActivity());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (((BaseEditAdvancedModeVM) this.mViewModel).controlId != -1) {
            ((BaseEditAdvancedModeVM) this.mViewModel).playMode(getActivity(), Collections.singletonList(Integer.valueOf(i)), this.mAdapter.getData().get(i).getPlayTimes(), false);
            return;
        }
        int i2 = i + 1;
        ((BaseEditAdvancedModeVM) this.mViewModel).clickPosition = i2;
        NavUtils.destination(ActEditAdvancedMode.class).withLong(Constants.CONTROL_ID, ((BaseEditAdvancedModeVM) this.mViewModel).controlId).withInt(Constants.ZONE_NUM, ((BaseEditAdvancedModeVM) this.mViewModel).zoneNum).withInt(Constants.MODE_NUM, i2).withInt(Constants.LIGHT_TYPE, ((BaseEditAdvancedModeVM) this.mViewModel).lightType).withInt(Constants.ICON_POSITION, this.mAdapter.getData().get(i).getPicIndex()).withInt(Constants.MODE_TIME, this.mAdapter.getData().get(i).getPlayTimes()).withString(Constants.MODE_NAME, ((BaseEditAdvancedModeVM) this.mViewModel).modeContentList.get(i).getModeName()).withString(Constants.MODE_DETAILS, ((BaseEditAdvancedModeVM) this.mViewModel).modeContentList.get(i).getContent()).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (view.getId() != R.id.v_more_click) {
            return;
        }
        ((BaseEditAdvancedModeVM) this.mViewModel).modeNum = i + 1;
        saveMode();
        NavUtils.destination(ActChangeAdvancedMode.class).withLong(Constants.PLACE_ID, ((BaseEditAdvancedModeVM) this.mViewModel).placeId).withInt(Constants.LIGHT_TYPE, ((BaseEditAdvancedModeVM) this.mViewModel).lightType).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(RefreshLayout refreshLayout) {
        ((BaseEditAdvancedModeVM) this.mViewModel).request.refresh();
        ((FtAdvancedModeBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            ((BaseEditAdvancedModeVM) this.mViewModel).controlId = arguments.getLong(Constants.CONTROL_ID);
            ((BaseEditAdvancedModeVM) this.mViewModel).groupControl = arguments.getBoolean(Constants.GROUP_CONTROL, false);
            ((BaseEditAdvancedModeVM) this.mViewModel).zoneNum = arguments.getInt(Constants.ZONE_NUM);
            ((BaseEditAdvancedModeVM) this.mViewModel).lightType = arguments.getInt(Constants.LIGHT_TYPE);
            if (((BaseEditAdvancedModeVM) this.mViewModel).groupControl) {
                Injection.repo().group().getGroupFromDb(((BaseEditAdvancedModeVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.FtAdvancedMode$$ExternalSyntheticLambda0
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        FtAdvancedMode.this.lambda$startObserve$4((Group) obj);
                    }
                });
            } else {
                Injection.repo().device().getDeviceFromDb(((BaseEditAdvancedModeVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.FtAdvancedMode$$ExternalSyntheticLambda2
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        FtAdvancedMode.this.lambda$startObserve$5((Device) obj);
                    }
                });
            }
        }
        ((BaseEditAdvancedModeVM) this.mViewModel).request = Injection.repo().mode().getModeList(this, ((BaseEditAdvancedModeVM) this.mViewModel).placeId, ((BaseEditAdvancedModeVM) this.mViewModel).lightType, 2);
        handleData(((BaseEditAdvancedModeVM) this.mViewModel).request, new IAction() { // from class: com.ltech.smarthome.ui.mode.FtAdvancedMode$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtAdvancedMode.this.lambda$startObserve$6((List) obj);
            }
        });
        ((BaseEditAdvancedModeVM) this.mViewModel).listPlayTime.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.FtAdvancedMode$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtAdvancedMode.this.lambda$startObserve$7((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Group group) {
        if (((BaseEditAdvancedModeVM) this.mViewModel).controlObject == null) {
            ((BaseEditAdvancedModeVM) this.mViewModel).controlObject = group;
            onRetry();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Device device) {
        if (((BaseEditAdvancedModeVM) this.mViewModel).controlObject == null) {
            ((BaseEditAdvancedModeVM) this.mViewModel).controlObject = device;
            if (ProductRepository.isE6Panel(device.getProductId()) && !ProductId.ID_KNOB_PANEL_E6M.equals(device.getProductId())) {
                ((FtAdvancedModeBinding) this.mViewBinding).tvTip.setText(getString(R.string.e6_mode_tip));
                ((FtAdvancedModeBinding) this.mViewBinding).tvTip.setVisibility(0);
            }
            onRetry();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(List list) {
        test();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((BaseEditAdvancedModeVM) this.mViewModel).modeContentList.set(r0.getModeIndex() - 1, (ModeContent) it.next());
            this.mAdapter.notifyItemChanged(r0.getModeIndex() - 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Integer num) {
        String str;
        if (num != null) {
            AppCompatTextView appCompatTextView = ((FtAdvancedModeBinding) this.mViewBinding).tvPlayList;
            String string = getString(R.string.play_list);
            if (num.intValue() == 0) {
                str = getString(R.string.loop_play_next_line);
            } else {
                str = num + getString(R.string.times);
            }
            appCompatTextView.setText(String.format("%s-%s", string, str));
            return;
        }
        ((FtAdvancedModeBinding) this.mViewBinding).tvPlayList.setText(getString(R.string.play_list));
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void onRetry() {
        super.onRetry();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalFragment
    public void showEmpty() {
        showContent();
    }

    private void queryModeList() {
        ((BaseEditAdvancedModeVM) this.mViewModel).queryModeList(getActivity(), new IAction() { // from class: com.ltech.smarthome.ui.mode.FtAdvancedMode$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtAdvancedMode.this.lambda$queryModeList$8((AdvancedModeList) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryModeList$8(AdvancedModeList advancedModeList) {
        showContent();
    }

    private void test() {
        showContent();
        AdvancedModeList advancedModeList = new AdvancedModeList();
        int size = ((BaseEditAdvancedModeVM) this.mViewModel).getNameList(getActivity()).size();
        for (int i = 0; i < size; i++) {
            AdvancedModeList.ModeItem modeItem = new AdvancedModeList.ModeItem();
            if (((BaseEditAdvancedModeVM) this.mViewModel).lightType == 1) {
                modeItem.Content = ModeUtils.DimAdvancedModeDetailsList[i];
            } else if (((BaseEditAdvancedModeVM) this.mViewModel).lightType == 2) {
                modeItem.Content = ModeUtils.CTAdvancedModeDetailsList[i];
            } else {
                modeItem.Content = ModeUtils.AdvancedModeDetailsList[i];
            }
            modeItem.modeName = ((BaseEditAdvancedModeVM) this.mViewModel).getNameList(getActivity()).get(i);
            modeItem.picIndex = i;
            modeItem.playTimes = 0;
            advancedModeList.getModeItemList().add(modeItem);
        }
        refreshModeDataView(advancedModeList);
    }

    private void refreshModeDataView(AdvancedModeList advancedModeList) {
        ((BaseEditAdvancedModeVM) this.mViewModel).modeContentList.clear();
        for (int i = 0; i < advancedModeList.getModeItemList().size(); i++) {
            ModeContent modeContent = new ModeContent();
            modeContent.setPicIndex(advancedModeList.getModeItemList().get(i).picIndex);
            modeContent.setPlayTimes(advancedModeList.getModeItemList().get(i).playTimes);
            modeContent.setContent(advancedModeList.getModeItemList().get(i).Content);
            if (TextUtils.isEmpty(advancedModeList.getModeItemList().get(i).modeName)) {
                modeContent.setModeName(((BaseEditAdvancedModeVM) this.mViewModel).getNameList(getActivity()).get(i));
            } else {
                modeContent.setModeName(advancedModeList.getModeItemList().get(i).modeName);
            }
            ((BaseEditAdvancedModeVM) this.mViewModel).modeContentList.add(modeContent);
        }
        this.mAdapter.setNewData(((BaseEditAdvancedModeVM) this.mViewModel).modeContentList);
        ((BaseEditAdvancedModeVM) this.mViewModel).listPlayTime.setValue(Integer.valueOf(advancedModeList.getListPlayTimes()));
        if (new File("/data/data/com.ltech.smarthome/shared_prefs/AdvancedTime.xml").exists()) {
            ((BaseEditAdvancedModeVM) this.mViewModel).listPlayTime.setValue(Integer.valueOf(getActivity().getSharedPreferences("AdvancedTime", 0).getInt("time", 0)));
        }
        if (new File("/data/data/com.ltech.smarthome/shared_prefs/AdvancedNum.xml").exists()) {
            for (int i2 = 0; i2 < ((BaseEditAdvancedModeVM) this.mViewModel).modeContentList.size(); i2++) {
                String string = getActivity().getSharedPreferences("AdvancedNum", 0).getString(String.valueOf(i2), "-1");
                if (!"-1".equals(string)) {
                    this.select_list.add(string);
                }
            }
        }
        ((BaseEditAdvancedModeVM) this.mViewModel).playList.clear();
        ((BaseEditAdvancedModeVM) this.mViewModel).playList.addAll(advancedModeList.getPlayList());
    }

    private void showPlayListDialog() {
        ArrayList arrayList = new ArrayList();
        File file = new File("/data/data/com.ltech.smarthome/shared_prefs/AdvancedNum.xml");
        for (int i = 0; i < ((BaseEditAdvancedModeVM) this.mViewModel).modeContentList.size(); i++) {
            if (file.exists()) {
                arrayList.add(new MultiSelectListDialog.SelectItem(((BaseEditAdvancedModeVM) this.mViewModel).modeContentList.get(i).getModeName(), getActivity().getSharedPreferences("AdvancedNum", 0).getString(String.valueOf(i), "").contains(String.valueOf(i))));
            } else {
                arrayList.add(new MultiSelectListDialog.SelectItem(((BaseEditAdvancedModeVM) this.mViewModel).modeContentList.get(i).getModeName(), ((BaseEditAdvancedModeVM) this.mViewModel).playList.contains(Integer.valueOf(i))));
            }
        }
        MultiSelectListDialog.asDefault().setTitle(getString(R.string.add_to_play_list)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.save)).setMultiSelectListener(new MultiSelectListDialog.IMultiSelectListener() { // from class: com.ltech.smarthome.ui.mode.FtAdvancedMode$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.view.dialog.MultiSelectListDialog.IMultiSelectListener
            public final void onSelect(List list) {
                FtAdvancedMode.this.lambda$showPlayListDialog$9(list);
            }
        }).setSelectList(arrayList).showDialog(getActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPlayListDialog$9(List list) {
        ((BaseEditAdvancedModeVM) this.mViewModel).playList.clear();
        SharedPreferences.Editor edit = getActivity().getSharedPreferences("AdvancedNum", 0).edit();
        edit.clear();
        if (list.size() == 0) {
            edit.clear();
            edit.commit();
        }
        this.select_list.clear();
        for (int i = 0; i < list.size(); i++) {
            String num = ((Integer) list.get(i)).toString();
            this.select_list.add(num);
            edit.putString(num, num);
            edit.commit();
        }
        ((BaseEditAdvancedModeVM) this.mViewModel).playList.addAll(list);
        ((BaseEditAdvancedModeVM) this.mViewModel).playAllList(getActivity());
    }

    private void showPlayTimeDialog(final boolean playList, final int position) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (int i2 = 0; i2 <= 255; i2++) {
            if (i2 == 0) {
                arrayList.add(getString(R.string.loop_play));
            } else {
                arrayList.add(i2 + getString(R.string.times));
            }
        }
        TimeSelectDialog minList = TimeSelectDialog.asDefault().setTitle(getString(R.string.set_play_times)).setMinList(arrayList);
        if (!playList) {
            i = this.mAdapter.getData().get(position).getPlayTimes();
        } else if (((BaseEditAdvancedModeVM) this.mViewModel).listPlayTime.getValue() != null) {
            i = ((BaseEditAdvancedModeVM) this.mViewModel).listPlayTime.getValue().intValue();
        }
        minList.setSelectMinPosition(i).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.mode.FtAdvancedMode$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                FtAdvancedMode.this.lambda$showPlayTimeDialog$10(playList, position, i3, i4);
            }
        }).showDialog(getActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPlayTimeDialog$10(boolean z, int i, int i2, int i3) {
        if (z) {
            SharedPreferences.Editor edit = getActivity().getSharedPreferences("AdvancedTime", 0).edit();
            edit.putInt("time", i2);
            edit.commit();
            ((BaseEditAdvancedModeVM) this.mViewModel).listPlayTime.setValue(Integer.valueOf(i2));
            if (this.select_list.size() == 0) {
                showPlayListDialog();
                return;
            } else {
                ((BaseEditAdvancedModeVM) this.mViewModel).playAllList(getActivity());
                return;
            }
        }
        this.mAdapter.getData().get(i).setPlayTimes(i2);
        this.mAdapter.notifyItemChanged(i);
        ((BaseEditAdvancedModeVM) this.mViewModel).playMode(getActivity(), Collections.singletonList(Integer.valueOf(i)), this.mAdapter.getData().get(i).getPlayTimes(), false);
    }

    private void saveMode() {
        showContent();
        this.details = ((BaseEditAdvancedModeVM) this.mViewModel).modeContentList.get(((BaseEditAdvancedModeVM) this.mViewModel).modeNum - 1).getContent();
        AdvancedModeInfo advancedModeInfo = new AdvancedModeInfo();
        ArrayList arrayList = new ArrayList();
        int i = 0;
        int i2 = 2;
        this.color_num = this.details.substring(0, 2);
        this.loop_num = this.details.substring(2, 4);
        int i3 = 16;
        ((BaseEditAdvancedModeVM) this.mViewModel).playtime = Integer.parseInt(this.loop_num, 16);
        int parseInt = Integer.parseInt(this.color_num, 16);
        String substring = this.details.substring(4);
        while (i < parseInt) {
            AdvancedModeInfo.ContentItem contentItem = new AdvancedModeInfo.ContentItem();
            int i4 = i * 20;
            int i5 = i4 + 16;
            int i6 = i4 + 17;
            String substring2 = substring.substring(i5, i6);
            if (substring2.startsWith("4")) {
                contentItem.setCTimeUnit(i2);
            }
            if (substring2.startsWith("8")) {
                contentItem.setCTimeUnit(3);
            }
            if (substring2.startsWith("0")) {
                contentItem.setCTimeUnit(1);
            }
            String substring3 = substring.substring(i6, i4 + 20);
            this.ChangeTime = substring3;
            contentItem.setCTimeNum(Integer.parseInt(substring3, i3));
            int i7 = i4 + 12;
            int i8 = i4 + 13;
            String substring4 = substring.substring(i7, i8);
            if (substring4.startsWith("4")) {
                contentItem.setTimeUnit(2);
            }
            if (substring4.startsWith("8")) {
                contentItem.setTimeUnit(3);
            }
            if (substring4.startsWith("0")) {
                contentItem.setTimeUnit(1);
            }
            int i9 = i4 + 2;
            this.Rg = substring.substring(i4, i9);
            int i10 = i4 + 4;
            this.G = substring.substring(i9, i10);
            int i11 = i4 + 6;
            this.B = substring.substring(i10, i11);
            int i12 = i4 + 8;
            this.WyBrt = substring.substring(i11, i12);
            int i13 = i4 + 10;
            this.Wy = substring.substring(i12, i13);
            this.RgbBrt = substring.substring(i13, i7);
            this.StopTime = substring.substring(i8, i5);
            i3 = 16;
            contentItem.setRed(Integer.parseInt(this.Rg, 16));
            contentItem.setGreen(Integer.parseInt(this.G, 16));
            contentItem.setBlue(Integer.parseInt(this.B, 16));
            contentItem.setWyBrt(Integer.parseInt(this.WyBrt, 16));
            contentItem.setwOrWy(Integer.parseInt(this.Wy, 16));
            contentItem.setRgbBrt(Integer.parseInt(this.RgbBrt, 16));
            contentItem.setTimeNum(Integer.parseInt(this.StopTime, 16));
            arrayList.add(contentItem);
            i++;
            i2 = 2;
        }
        advancedModeInfo.setInfoList(arrayList);
        ((BaseEditAdvancedModeVM) this.mViewModel).setModeInfo(advancedModeInfo);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3017 == resultCode) {
            test();
        }
        ((BaseEditAdvancedModeVM) this.mViewModel).request.refresh();
        if (3002 == resultCode && data != null) {
            ((BaseEditAdvancedModeVM) this.mViewModel).modeContentList.get(((BaseEditAdvancedModeVM) this.mViewModel).clickPosition - 1).setPicIndex(data.getIntExtra(Constants.ICON_POSITION, 0));
            this.mAdapter.notifyItemChanged(((BaseEditAdvancedModeVM) this.mViewModel).clickPosition - 1);
        }
        if (3006 == resultCode && data != null) {
            ((BaseEditAdvancedModeVM) this.mViewModel).modeContentList.get(((BaseEditAdvancedModeVM) this.mViewModel).clickPosition - 1).setModeName(data.getStringExtra(Constants.MODE_NAME));
            this.mAdapter.notifyItemChanged(((BaseEditAdvancedModeVM) this.mViewModel).clickPosition - 1);
        }
        if (3007 != resultCode || data == null) {
            return;
        }
        ((BaseEditAdvancedModeVM) this.mViewModel).modeContentList.get(((BaseEditAdvancedModeVM) this.mViewModel).clickPosition - 1).setPlayTimes(data.getIntExtra(Constants.MODE_NUM, 0));
        this.mAdapter.notifyItemChanged(((BaseEditAdvancedModeVM) this.mViewModel).clickPosition - 1);
    }
}