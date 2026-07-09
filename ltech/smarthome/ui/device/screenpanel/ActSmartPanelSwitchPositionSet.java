package com.ltech.smarthome.ui.device.screenpanel;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ArrayUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.badge.BadgeDrawable;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActPanelSwitchPositionSetBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSet;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.view.MyItemTouchHelper;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSmartPanelSwitchPositionSet extends VMActivity<ActPanelSwitchPositionSetBinding, ActSmartPanelSwitchPositionSetVM> {
    private int colorType;
    private BaseQuickAdapter<Integer, BaseViewHolder> displayAdapter;
    private BaseQuickAdapter<Integer, BaseViewHolder> infoAdapter;
    private boolean isG4;
    private BaseQuickAdapter<Integer, BaseViewHolder> keyAdapter;
    private GridLayoutManager mGridLayoutManager;

    static /* synthetic */ boolean lambda$back$2(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_panel_switch_position_set;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.app_str_switch_position));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        ((ActSmartPanelSwitchPositionSetVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActSmartPanelSwitchPositionSetVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActSmartPanelSwitchPositionSetVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        initTab();
    }

    private void initTab() {
        ((ActPanelSwitchPositionSetBinding) this.mViewBinding).tvTab1.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSet.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                List<Integer> list;
                ((ActPanelSwitchPositionSetBinding) ActSmartPanelSwitchPositionSet.this.mViewBinding).groupPage1.setVisibility(0);
                ((ActPanelSwitchPositionSetBinding) ActSmartPanelSwitchPositionSet.this.mViewBinding).groupPage2.setVisibility(8);
                ((ActPanelSwitchPositionSetBinding) ActSmartPanelSwitchPositionSet.this.mViewBinding).groupPage3.setVisibility(8);
                ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).setCurScreen(0);
                if (!ActSmartPanelSwitchPositionSet.this.isG4) {
                    list = ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).switchPosition;
                } else {
                    list = ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).switchPosition.subList(((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).getCurScreen() * 4, (((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).getCurScreen() + 1) * 4);
                }
                ActSmartPanelSwitchPositionSet.this.infoAdapter.replaceData(list);
            }
        });
        ((ActPanelSwitchPositionSetBinding) this.mViewBinding).tvTab2.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSet.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                List<Integer> list;
                ((ActPanelSwitchPositionSetBinding) ActSmartPanelSwitchPositionSet.this.mViewBinding).groupPage1.setVisibility(8);
                ((ActPanelSwitchPositionSetBinding) ActSmartPanelSwitchPositionSet.this.mViewBinding).groupPage2.setVisibility(0);
                ((ActPanelSwitchPositionSetBinding) ActSmartPanelSwitchPositionSet.this.mViewBinding).groupPage3.setVisibility(8);
                ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).setCurScreen(1);
                if (!ActSmartPanelSwitchPositionSet.this.isG4) {
                    list = ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).switchPosition;
                } else {
                    list = ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).switchPosition.subList(((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).getCurScreen() * 4, (((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).getCurScreen() + 1) * 4);
                }
                ActSmartPanelSwitchPositionSet.this.infoAdapter.replaceData(list);
            }
        });
        ((ActPanelSwitchPositionSetBinding) this.mViewBinding).tvTab3.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSet.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                List<Integer> list;
                ((ActPanelSwitchPositionSetBinding) ActSmartPanelSwitchPositionSet.this.mViewBinding).groupPage1.setVisibility(8);
                ((ActPanelSwitchPositionSetBinding) ActSmartPanelSwitchPositionSet.this.mViewBinding).groupPage2.setVisibility(8);
                ((ActPanelSwitchPositionSetBinding) ActSmartPanelSwitchPositionSet.this.mViewBinding).groupPage3.setVisibility(0);
                ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).setCurScreen(2);
                if (!ActSmartPanelSwitchPositionSet.this.isG4) {
                    list = ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).switchPosition;
                } else {
                    list = ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).switchPosition.subList(((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).getCurScreen() * 4, (((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).getCurScreen() + 1) * 4);
                }
                ActSmartPanelSwitchPositionSet.this.infoAdapter.replaceData(list);
            }
        });
    }

    private void startObjectObserve() {
        if (((ActSmartPanelSwitchPositionSetVM) this.mViewModel).groupControl) {
            Group groupById = Injection.repo().group().getGroupById(((ActSmartPanelSwitchPositionSetVM) this.mViewModel).controlId);
            this.isG4 = ProductRepository.getLightColorType((Object) groupById) == 21;
            ((ActSmartPanelSwitchPositionSetVM) this.mViewModel).controlObject.setValue(groupById);
            return;
        }
        Device deviceById = Injection.repo().device().getDeviceById(((ActSmartPanelSwitchPositionSetVM) this.mViewModel).controlId);
        this.isG4 = ProductRepository.getLightColorType((Object) deviceById) == 21;
        try {
            if (((ActSmartPanelSwitchPositionSetVM) this.mViewModel).controlObject.getValue() == null) {
                ((ActSmartPanelSwitchPositionSetVM) this.mViewModel).controlObject.setValue(deviceById);
            } else {
                if (HelpUtils.compareObject((Device) ((ActSmartPanelSwitchPositionSetVM) this.mViewModel).controlObject.getValue(), deviceById)) {
                    return;
                }
                ((ActSmartPanelSwitchPositionSetVM) this.mViewModel).controlObject.setValue(deviceById);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initList() {
        BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter = this.displayAdapter;
        if (baseQuickAdapter == null) {
            ((ActPanelSwitchPositionSetBinding) this.mViewBinding).rvDisplay.setLayoutManager(new LinearLayoutManager(this));
            RecyclerView recyclerView = ((ActPanelSwitchPositionSetBinding) this.mViewBinding).rvDisplay;
            BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<Integer, BaseViewHolder>(R.layout.item_panel_switch_position_set, ((ActSmartPanelSwitchPositionSetVM) this.mViewModel).switchPosition) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSet.4
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, Integer integer) {
                    helper.setText(R.id.tv1, (helper.getAdapterPosition() + 1) + "");
                    helper.setText(R.id.tv2, "");
                    if (((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).relayPositionMap != null && ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).relayPositionMap.containsKey(integer)) {
                        Object obj = ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).relayPositionMap.get(integer);
                        if (obj instanceof Group) {
                            helper.setText(R.id.tv2, ((Group) obj).getName());
                        } else if (obj instanceof Device) {
                            helper.setText(R.id.tv2, ((Device) obj).getName());
                        }
                        helper.setGone(R.id.iv, true);
                        return;
                    }
                    helper.setGone(R.id.iv, false);
                }

                private boolean inRange(int position) {
                    return position >= 0 && position < this.mData.size();
                }
            };
            this.displayAdapter = baseQuickAdapter2;
            recyclerView.setAdapter(baseQuickAdapter2);
            ((ActPanelSwitchPositionSetBinding) this.mViewBinding).rvDisplay.setHasFixedSize(true);
            ((ActPanelSwitchPositionSetBinding) this.mViewBinding).rvDisplay.getItemAnimator().setChangeDuration(0L);
            ((ActPanelSwitchPositionSetBinding) this.mViewBinding).rvDisplay.clearAnimation();
            ((ActPanelSwitchPositionSetBinding) this.mViewBinding).rvDisplay.setItemAnimator(null);
            new MyItemTouchHelper(new AnonymousClass5()).attachToRecyclerView(((ActPanelSwitchPositionSetBinding) this.mViewBinding).rvDisplay);
            return;
        }
        baseQuickAdapter.setNewData(((ActSmartPanelSwitchPositionSetVM) this.mViewModel).switchPosition);
    }

    /* renamed from: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSet$5, reason: invalid class name */
    class AnonymousClass5 extends MyItemTouchHelper.Callback {
        private int from;
        private int to;

        @Override // com.ltech.smarthome.view.MyItemTouchHelper.Callback
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return true;
        }

        @Override // com.ltech.smarthome.view.MyItemTouchHelper.Callback
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        }

        AnonymousClass5() {
        }

        @Override // com.ltech.smarthome.view.MyItemTouchHelper.Callback
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            if (((Integer) ActSmartPanelSwitchPositionSet.this.displayAdapter.getData().get(viewHolder.getAdapterPosition())).intValue() == 0) {
                return makeMovementFlags(0, 0);
            }
            return makeMovementFlags(3, 0);
        }

        @Override // com.ltech.smarthome.view.MyItemTouchHelper.Callback
        public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
            super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
            this.from = fromPos;
            this.to = toPos;
            LHomeLog.e(getClass(), fromPos + "==" + toPos + "(move)" + x + "  " + y);
        }

        @Override // com.ltech.smarthome.view.MyItemTouchHelper.Callback
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            List<Integer> list;
            super.onSelectedChanged(viewHolder, actionState);
            if (actionState != 0 || this.from <= -1 || this.to <= -1) {
                return;
            }
            LHomeLog.e(getClass(), this.from + "==" + this.to + "(up)");
            RelateInfoItem relateInfoItem = ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).relatedInfoList.get(this.to);
            Collections.swap(ActSmartPanelSwitchPositionSet.this.displayAdapter.getData(), this.from, this.to);
            ((ActPanelSwitchPositionSetBinding) ActSmartPanelSwitchPositionSet.this.mViewBinding).rvDisplay.post(new Runnable() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSet.5.1
                @Override // java.lang.Runnable
                public void run() {
                    ActSmartPanelSwitchPositionSet.this.displayAdapter.notifyDataSetChanged();
                }
            });
            if (relateInfoItem.infoName == null || relateInfoItem.type == 0) {
                ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).newPosition.clear();
                ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).newPosition.addAll(ActSmartPanelSwitchPositionSet.this.displayAdapter.getData());
                if (!ActSmartPanelSwitchPositionSet.this.isG4) {
                    list = ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).newPosition;
                } else {
                    list = ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).newPosition.subList(((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).getCurScreen() * 4, (((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).getCurScreen() + 1) * 4);
                }
                ActSmartPanelSwitchPositionSet.this.infoAdapter.setNewData(list);
            } else {
                MessageDialog.show(ActSmartPanelSwitchPositionSet.this.activity, "", ActSmartPanelSwitchPositionSet.this.getString(R.string.app_str_switch_position_tip)).setOkButton(ActSmartPanelSwitchPositionSet.this.getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSet$5$$ExternalSyntheticLambda0
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        boolean lambda$onSelectedChanged$0;
                        lambda$onSelectedChanged$0 = ActSmartPanelSwitchPositionSet.AnonymousClass5.this.lambda$onSelectedChanged$0(baseDialog, view);
                        return lambda$onSelectedChanged$0;
                    }
                }).setCancelButton(ActSmartPanelSwitchPositionSet.this.getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSet$5$$ExternalSyntheticLambda1
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        boolean lambda$onSelectedChanged$1;
                        lambda$onSelectedChanged$1 = ActSmartPanelSwitchPositionSet.AnonymousClass5.this.lambda$onSelectedChanged$1(baseDialog, view);
                        return lambda$onSelectedChanged$1;
                    }
                }).setCancelable(false);
            }
            this.from = -1;
            this.to = -1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$onSelectedChanged$0(BaseDialog baseDialog, View view) {
            List<Integer> list;
            ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).newPosition.clear();
            ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).newPosition.addAll(ActSmartPanelSwitchPositionSet.this.displayAdapter.getData());
            if (!ActSmartPanelSwitchPositionSet.this.isG4) {
                list = ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).newPosition;
            } else {
                list = ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).newPosition.subList(((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).getCurScreen() * 4, (((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).getCurScreen() + 1) * 4);
            }
            ActSmartPanelSwitchPositionSet.this.infoAdapter.setNewData(list);
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$onSelectedChanged$1(BaseDialog baseDialog, View view) {
            ActSmartPanelSwitchPositionSet.this.displayAdapter.replaceData(((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).newPosition);
            return false;
        }

        @Override // com.ltech.smarthome.view.MyItemTouchHelper.Callback
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
        }
    }

    private void initInfoList() {
        RecyclerView recyclerView;
        BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter = this.infoAdapter;
        if (baseQuickAdapter == null) {
            this.infoAdapter = new BaseQuickAdapter<Integer, BaseViewHolder>(R.layout.item_smart_panel_key) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSet.6
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, Integer integer) {
                    LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.layout_content);
                    ViewGroup.LayoutParams layoutParams = helper.itemView.getLayoutParams();
                    if (ActSmartPanelSwitchPositionSet.this.colorType == 19 || ActSmartPanelSwitchPositionSet.this.colorType == 11) {
                        linearLayout.setPadding(0, 0, 0, Utils.dip2px(this.mContext, 30.0f));
                        layoutParams.height = ActSmartPanelSwitchPositionSet.this.mGridLayoutManager.getHeight() / 2;
                        linearLayout.setGravity(17);
                    } else if (ActSmartPanelSwitchPositionSet.this.colorType == 21) {
                        layoutParams.height = ActSmartPanelSwitchPositionSet.this.mGridLayoutManager.getHeight() / 2;
                        TextView textView = (TextView) helper.getView(R.id.tv_device_name);
                        TextView textView2 = (TextView) helper.getView(R.id.tv_sub_text);
                        if (helper.getAdapterPosition() % 4 == 0) {
                            linearLayout.setGravity(BadgeDrawable.TOP_START);
                            textView.setGravity(GravityCompat.START);
                            textView2.setGravity(GravityCompat.START);
                            textView2.setPadding(ConvertUtils.dp2px(18.0f), 0, 0, 0);
                        } else if (helper.getAdapterPosition() % 4 == 1) {
                            linearLayout.setGravity(BadgeDrawable.TOP_END);
                            textView.setGravity(GravityCompat.END);
                            textView2.setGravity(GravityCompat.END);
                            textView2.setPadding(0, 0, ConvertUtils.dp2px(18.0f), 0);
                        } else if (helper.getAdapterPosition() % 4 == 2) {
                            linearLayout.setGravity(BadgeDrawable.BOTTOM_START);
                            textView.setGravity(GravityCompat.START);
                            textView2.setGravity(GravityCompat.START);
                            textView2.setPadding(ConvertUtils.dp2px(18.0f), 0, 0, 0);
                        } else {
                            linearLayout.setGravity(BadgeDrawable.BOTTOM_END);
                            textView.setGravity(GravityCompat.END);
                            textView2.setGravity(GravityCompat.END);
                            textView2.setPadding(0, 0, ConvertUtils.dp2px(18.0f), 0);
                        }
                    } else {
                        linearLayout.setGravity(81);
                        linearLayout.setPadding(0, 0, 0, Utils.dip2px(this.mContext, 30.0f));
                        layoutParams.height = ActSmartPanelSwitchPositionSet.this.mGridLayoutManager.getHeight();
                    }
                    helper.setText(R.id.tv_device_name, "").setTextColor(R.id.tv_device_name, ActSmartPanelSwitchPositionSet.this.getResources().getColor(R.color.white));
                    if (((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).relayPositionMap != null && ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).relayPositionMap.containsKey(integer)) {
                        Object obj = ((ActSmartPanelSwitchPositionSetVM) ActSmartPanelSwitchPositionSet.this.mViewModel).relayPositionMap.get(integer);
                        if (obj instanceof Group) {
                            helper.setText(R.id.tv_device_name, ((Group) obj).getName());
                        } else if (obj instanceof Device) {
                            helper.setText(R.id.tv_device_name, ((Device) obj).getName());
                        }
                    } else {
                        helper.setText(R.id.tv_device_name, "");
                    }
                    if (ActSmartPanelSwitchPositionSet.this.isG4) {
                        ((TextView) helper.getView(R.id.tv_sub_text)).setTextSize(16.0f);
                        ((TextView) helper.getView(R.id.tv_device_name)).setTextSize(14.0f);
                    } else {
                        ((TextView) helper.getView(R.id.tv_sub_text)).setTextSize(12.0f);
                        ((TextView) helper.getView(R.id.tv_device_name)).setTextSize(12.0f);
                    }
                    helper.setText(R.id.tv_sub_text, (ActSmartPanelSwitchPositionSet.this.getRealItemPosition(helper.getAdapterPosition()) + 1) + "").setTextColor(R.id.tv_sub_text, ActSmartPanelSwitchPositionSet.this.getResources().getColor(R.color.white));
                }
            };
            if (this.isG4) {
                recyclerView = ((ActPanelSwitchPositionSetBinding) this.mViewBinding).rvKeyInfo2;
            } else {
                recyclerView = ((ActPanelSwitchPositionSetBinding) this.mViewBinding).rvKeyInfo;
            }
            this.infoAdapter.bindToRecyclerView(recyclerView);
            int i = this.colorType;
            if (i == 19) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager((Context) this, 3, 1, true);
                this.mGridLayoutManager = gridLayoutManager;
                recyclerView.setLayoutManager(gridLayoutManager);
            } else if (i == 21 || i == 11) {
                GridLayoutManager gridLayoutManager2 = new GridLayoutManager((Context) this, 2, 1, false);
                this.mGridLayoutManager = gridLayoutManager2;
                recyclerView.setLayoutManager(gridLayoutManager2);
            } else if (i == 18) {
                GridLayoutManager gridLayoutManager3 = new GridLayoutManager((Context) this, 4, 1, false);
                this.mGridLayoutManager = gridLayoutManager3;
                recyclerView.setLayoutManager(gridLayoutManager3);
            } else if (i == 8) {
                GridLayoutManager gridLayoutManager4 = new GridLayoutManager((Context) this, 1, 1, false);
                this.mGridLayoutManager = gridLayoutManager4;
                recyclerView.setLayoutManager(gridLayoutManager4);
            } else if (i == 9) {
                GridLayoutManager gridLayoutManager5 = new GridLayoutManager((Context) this, 2, 1, false);
                this.mGridLayoutManager = gridLayoutManager5;
                recyclerView.setLayoutManager(gridLayoutManager5);
            } else if (i == 10) {
                GridLayoutManager gridLayoutManager6 = new GridLayoutManager((Context) this, 3, 1, false);
                this.mGridLayoutManager = gridLayoutManager6;
                recyclerView.setLayoutManager(gridLayoutManager6);
            } else {
                GridLayoutManager gridLayoutManager7 = new GridLayoutManager(this, 3);
                this.mGridLayoutManager = gridLayoutManager7;
                recyclerView.setLayoutManager(gridLayoutManager7);
                recyclerView.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSet.7
                    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        outRect.set(0, 0, 0, 0);
                    }
                });
            }
            recyclerView.setHasFixedSize(true);
            ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            return;
        }
        baseQuickAdapter.replaceData(((ActSmartPanelSwitchPositionSetVM) this.mViewModel).switchPosition);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        startObjectObserve();
        ((ActSmartPanelSwitchPositionSetVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSet$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSmartPanelSwitchPositionSet.this.lambda$startObserve$0(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Object obj) {
        int[] iArr;
        List<Integer> list;
        int[] drawableResourceArray;
        this.colorType = ProductRepository.getLightColorType(obj);
        ((ActSmartPanelSwitchPositionSetVM) this.mViewModel).initRelateInfoList(obj);
        initList();
        initInfoList();
        if (obj instanceof Device) {
            Device device = (Device) obj;
            if (ProductId.ID_SMART_PANEL_G4.equals(device.getProductId()) || ProductId.ID_SMART_PANEL_G4_PRO.equals(device.getProductId())) {
                ((ActPanelSwitchPositionSetBinding) this.mViewBinding).groupTab.setVisibility(0);
                ((ActPanelSwitchPositionSetBinding) this.mViewBinding).groupPage1.setVisibility(0);
                ((ActPanelSwitchPositionSetBinding) this.mViewBinding).layoutScreen.setVisibility(8);
                if (ProductId.ID_SMART_PANEL_G4.equals(device.getProductId())) {
                    drawableResourceArray = HelpUtils.getDrawableResourceArray(this, R.array.g4_bg_panel_bg);
                } else {
                    drawableResourceArray = ProductId.ID_SMART_PANEL_G4_PRO.equals(device.getProductId()) ? HelpUtils.getDrawableResourceArray(this, R.array.g4_pro_bg_panel_bg) : null;
                }
                if (drawableResourceArray != null) {
                    setBg(drawableResourceArray);
                }
            } else {
                if (!ProductId.ID_SMART_SWITCH_S6_PRO.equals(device.getProductId())) {
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivSwitch5.setVisibility(0);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s1c_shadown_1);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s1c_shadown_3);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s1c_shadown_2);
                }
                if (ProductId.ID_SMART_SWITCH_S2C.equals(device.getProductId())) {
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s2c_normal_1);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).layoutScreen.setVisibility(8);
                } else if (ProductId.ID_SMART_SWITCH_S3C.equals(device.getProductId())) {
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s3c_normal_1);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).layoutScreen.setVisibility(8);
                } else if (ProductId.ID_SMART_SWITCH_S4.equals(device.getProductId())) {
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s4_normal_1);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).layoutScreen.setVisibility(8);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s4_shadown_1);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s4_shadown_3);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s4_shadown_2);
                } else if (ProductId.ID_SWITCH_PANEL_S4M.equals(device.getProductId())) {
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s4m_pic_1);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).layoutScreen.setVisibility(8);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s4m_pic_3);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s4m_pic_4);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s4m_pic_2);
                } else if (ProductId.ID_SMART_SWITCH_S2_PRO.equals(device.getProductId())) {
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.switchposition_s2pro_pic1);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).layoutScreen.setVisibility(0);
                } else if (ProductId.ID_SMART_SWITCH_S3_PRO.equals(device.getProductId())) {
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.switchposition_s3pro_pic2);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).layoutScreen.setVisibility(0);
                } else if (ProductId.ID_SMART_SWITCH_S6.equals(device.getProductId())) {
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s6_key_set);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).layoutScreen.setVisibility(8);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s4_shadown_1);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s4_shadown_3);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s4_shadown_2);
                }
                ((ActPanelSwitchPositionSetBinding) this.mViewBinding).groupTab.setVisibility(8);
            }
        } else if (obj instanceof Group) {
            Group group = (Group) obj;
            int lightColorType = ProductRepository.getLightColorType((Object) group);
            if (21 == lightColorType) {
                ((ActPanelSwitchPositionSetBinding) this.mViewBinding).groupTab.setVisibility(0);
                ((ActPanelSwitchPositionSetBinding) this.mViewBinding).groupPage1.setVisibility(0);
                ((ActPanelSwitchPositionSetBinding) this.mViewBinding).layoutScreen.setVisibility(8);
                if (((ActSmartPanelSwitchPositionSetVM) this.mViewModel).relateInfoAssistant.getSwitchShowType() == 2) {
                    iArr = new int[]{R.mipmap.img_g4pro_bg7};
                } else {
                    iArr = new int[]{R.mipmap.img_g4_bg7};
                }
                setBg(iArr);
            } else {
                if (19 != lightColorType) {
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivSwitch5.setVisibility(0);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s1c_shadown_1);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s1c_shadown_3);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s1c_shadown_2);
                }
                if (8 == lightColorType) {
                    if (((ActSmartPanelSwitchPositionSetVM) this.mViewModel).relateInfoAssistant.getSwitchShowType() == 2) {
                        ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.s1_pro_normal);
                    } else {
                        ((ActPanelSwitchPositionSetBinding) this.mViewBinding).layoutScreen.setVisibility(8);
                        ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s1c_normal_1);
                    }
                } else if (9 == lightColorType) {
                    if (((ActSmartPanelSwitchPositionSetVM) this.mViewModel).relateInfoAssistant.getSwitchShowType() == 2) {
                        ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.switchposition_s2pro_pic1);
                    } else {
                        ((ActPanelSwitchPositionSetBinding) this.mViewBinding).layoutScreen.setVisibility(8);
                        ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s2c_normal_1);
                    }
                } else if (10 == lightColorType) {
                    if (((ActSmartPanelSwitchPositionSetVM) this.mViewModel).relateInfoAssistant.getSwitchShowType() == 2) {
                        ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.switchposition_s3pro_pic2);
                    } else {
                        ((ActPanelSwitchPositionSetBinding) this.mViewBinding).layoutScreen.setVisibility(8);
                        ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s3c_normal_1);
                    }
                } else if (18 == lightColorType) {
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).layoutScreen.setVisibility(8);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s4_normal_1);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s4_shadown_1);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s4_shadown_3);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s4_shadown_2);
                } else if (11 == lightColorType) {
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).layoutScreen.setVisibility(8);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s4m_pic_1);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s4m_pic_3);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s4m_pic_4);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s4m_pic_2);
                } else if (19 == lightColorType && (getProPanelCount(group) == 0 || ((ActSmartPanelSwitchPositionSetVM) this.mViewModel).relateInfoAssistant.getSwitchShowType() != 2)) {
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivS4.setImageResource(R.mipmap.bg_s6_key_set);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).layoutScreen.setVisibility(8);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivShadowLeft.setImageResource(R.mipmap.bg_s4_shadown_1);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivShadowRight.setImageResource(R.mipmap.bg_s4_shadown_3);
                    ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivSwitch5.setImageResource(R.mipmap.bg_s4_shadown_2);
                }
                ((ActPanelSwitchPositionSetBinding) this.mViewBinding).groupTab.setVisibility(8);
            }
        }
        if (this.isG4) {
            list = ((ActSmartPanelSwitchPositionSetVM) this.mViewModel).switchPosition.subList(((ActSmartPanelSwitchPositionSetVM) this.mViewModel).getCurScreen() * 4, (((ActSmartPanelSwitchPositionSetVM) this.mViewModel).getCurScreen() + 1) * 4);
        } else {
            list = ((ActSmartPanelSwitchPositionSetVM) this.mViewModel).switchPosition;
        }
        this.infoAdapter.replaceData(list);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (!ArrayUtils.equals(((ActSmartPanelSwitchPositionSetVM) this.mViewModel).newPosition.toArray(), ((ActSmartPanelSwitchPositionSetVM) this.mViewModel).oldList.toArray())) {
            MessageDialog.show(this, "", getString(R.string.need_save_action)).setOkButton(getString(R.string.ir_exit), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSet$$ExternalSyntheticLambda1
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$back$1;
                    lambda$back$1 = ActSmartPanelSwitchPositionSet.this.lambda$back$1(baseDialog, view);
                    return lambda$back$1;
                }
            }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSet$$ExternalSyntheticLambda2
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    return ActSmartPanelSwitchPositionSet.lambda$back$2(baseDialog, view);
                }
            }).setCancelable(false);
        } else {
            super.back();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$back$1(BaseDialog baseDialog, View view) {
        super.back();
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        ((ActSmartPanelSwitchPositionSetVM) this.mViewModel).save();
    }

    private void setBg(int[] imgs) {
        try {
            Object value = ((ActSmartPanelSwitchPositionSetVM) this.mViewModel).controlObject.getValue();
            if (value instanceof Device) {
                int parseInt = Integer.parseInt(((Device) value).getPanelColor()) - 1;
                if (imgs == null || parseInt >= imgs.length) {
                    return;
                }
                ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivS4.setImageResource(imgs[parseInt]);
                return;
            }
            if (((ActSmartPanelSwitchPositionSetVM) this.mViewModel).controlObject.getValue() instanceof Group) {
                ((ActPanelSwitchPositionSetBinding) this.mViewBinding).ivS4.setImageResource(imgs[0]);
            }
        } catch (Exception e) {
            LHomeLog.e(getClass(), e.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getRealItemPosition(int p) {
        return 21 == ProductRepository.getLightColorType(((ActSmartPanelSwitchPositionSetVM) this.mViewModel).controlObject.getValue()) ? (((ActSmartPanelSwitchPositionSetVM) this.mViewModel).getCurScreen() * 4) + p : p;
    }

    public int getProPanelCount(Group group) {
        int i = 0;
        if (group != null && !group.getDeviceIds().isEmpty()) {
            Iterator<Long> it = group.getDeviceIds().iterator();
            while (it.hasNext()) {
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().longValue());
                if (deviceByDeviceId != null && ProductRepository.isScreenPanel(deviceByDeviceId.getProductId())) {
                    i++;
                }
            }
        }
        return i;
    }
}