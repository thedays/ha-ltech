package com.ltech.smarthome.ui.device.screenpanel;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.databinding.FtPageScreenPanelDetailBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.ui.scene.ActSelectCgdPro;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.ScreenIconUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.ltech.smarthome.view.dialog.SetScreenDisplayDialog;
import com.ltech.smarthome.view.dialog.TimeIntervalSelectDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class FtPageScreenPanelDetail extends VMFragment<FtPageScreenPanelDetailBinding, ActSmartPanelVM> {
    private int curScreenNum;
    private BaseQuickAdapter<RelateInfoItem, BaseViewHolder> keyAdapter;
    private RelatedInfoExtParam.RelateInfo relateInfo;
    private int selectPos;

    static /* synthetic */ boolean lambda$showNoPermissionDialog$2(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_page_screen_panel_detail;
    }

    @Override // com.ltech.smarthome.base.VMFragment
    protected boolean shareVM() {
        return true;
    }

    public static FtPageScreenPanelDetail newInstance(int pos) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        FtPageScreenPanelDetail ftPageScreenPanelDetail = new FtPageScreenPanelDetail();
        ftPageScreenPanelDetail.setArguments(bundle);
        return ftPageScreenPanelDetail;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        if (getArguments() != null) {
            this.curScreenNum = getArguments().getInt("pos", 0);
        }
        if (ProductRepository.getLightColorType(((ActSmartPanelVM) this.mViewModel).controlObject.getValue()) == 21) {
            ((FtPageScreenPanelDetailBinding) this.mViewBinding).layoutTime.setVisibility(Injection.repo().device().getExistGateway() != null ? 0 : 8);
        }
        ((FtPageScreenPanelDetailBinding) this.mViewBinding).layoutCustom.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                NavUtils.destination(ActSmartPanelTheme.class).withBoolean(Constants.GROUP_CONTROL, ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).groupControl).withInt(Constants.SELECT_POSITION, ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).curScreen.getValue() != null ? ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).curScreen.getValue().intValue() : 0).withInt(Constants.SELECT_THEME, ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).themeNum[((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).curScreen.getValue().intValue()]).withLong(Constants.CONTROL_ID, ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).controlId).withInt(Constants.SELECT_SCREENSAVER_TIME, ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).screensaverTime).withInt(Constants.SELECT_SCREENSAVER_MODE, ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).screensaverMod).withArrayList(Constants.SELECT_DOWNLOAD_SCREENSAVER, (ArrayList) ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).downloadThemes).withIntArray(Constants.SELECT_APPLY_SCREENSAVER, ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).themeNum).withDefaultRequestCode().navigation(FtPageScreenPanelDetail.this.getActivity());
            }
        });
        ((FtPageScreenPanelDetailBinding) this.mViewBinding).layoutTime.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                TimeIntervalSelectDialog.asDefault().setClose(((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).isScreenTimeClose[FtPageScreenPanelDetail.this.curScreenNum]).setTitle(FtPageScreenPanelDetail.this.getString(R.string.app_str_show_screen_time)).setActionTitle(FtPageScreenPanelDetail.this.getString(R.string.app_str_custom_time)).setSelectListener(new TimeIntervalSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail.2.1
                    @Override // com.ltech.smarthome.view.dialog.TimeIntervalSelectDialog.SelectListener
                    public void confirm(int startMinPosition, int startSecPosition, int endMinPosition, int endSecPosition) {
                        int parseInt = Integer.parseInt(String.format(Locale.getDefault(), "%02d%02d", Integer.valueOf(startMinPosition), Integer.valueOf(startSecPosition)));
                        int parseInt2 = Integer.parseInt(String.format(Locale.getDefault(), "%02d%02d", Integer.valueOf(endMinPosition), Integer.valueOf(endSecPosition)));
                        for (int i = 0; i < ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).screenTime.length; i++) {
                            if (FtPageScreenPanelDetail.this.curScreenNum != i && !((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).isScreenTimeClose[i] && FtPageScreenPanelDetail.this.isIntersect(Integer.parseInt(String.format(Locale.getDefault(), "%02d%02d", Integer.valueOf(((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).screenTime[i][0]), Integer.valueOf(((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).screenTime[i][1]))), Integer.parseInt(String.format(Locale.getDefault(), "%02d%02d", Integer.valueOf(((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).screenTime[i][2]), Integer.valueOf(((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).screenTime[i][3]))), parseInt, parseInt2)) {
                                SmartToast.showShort(FtPageScreenPanelDetail.this.getString(R.string.app_str_custom_time_repeat));
                                return;
                            }
                        }
                        ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).screenTime[FtPageScreenPanelDetail.this.curScreenNum][0] = startMinPosition;
                        ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).screenTime[FtPageScreenPanelDetail.this.curScreenNum][1] = startSecPosition;
                        ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).screenTime[FtPageScreenPanelDetail.this.curScreenNum][2] = endMinPosition;
                        ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).screenTime[FtPageScreenPanelDetail.this.curScreenNum][3] = endSecPosition;
                        ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).isScreenTimeClose[FtPageScreenPanelDetail.this.curScreenNum] = false;
                        FtPageScreenPanelDetail.this.bindTime();
                        ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).setScreenShowTime(FtPageScreenPanelDetail.this.curScreenNum);
                    }

                    @Override // com.ltech.smarthome.view.dialog.TimeIntervalSelectDialog.SelectListener
                    public void close() {
                        ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).isScreenTimeClose[FtPageScreenPanelDetail.this.curScreenNum] = true;
                        FtPageScreenPanelDetail.this.bindTime();
                        ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).setSmartPanelShowTimeClose(FtPageScreenPanelDetail.this.curScreenNum);
                    }
                }).setSelectStartMinPosition(((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).screenTime[FtPageScreenPanelDetail.this.curScreenNum][0]).setSelectStartSecPosition(((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).screenTime[FtPageScreenPanelDetail.this.curScreenNum][1]).setSelectEndMinPosition(((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).screenTime[FtPageScreenPanelDetail.this.curScreenNum][2]).setSelectEndSecPosition(((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).screenTime[FtPageScreenPanelDetail.this.curScreenNum][3]).showDialog(FtPageScreenPanelDetail.this.getActivity());
            }
        });
        ((FtPageScreenPanelDetailBinding) this.mViewBinding).sb.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail.3
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).setSmartPanelShowScreen(FtPageScreenPanelDetail.this.curScreenNum, isChecked);
                ((FtPageScreenPanelDetailBinding) FtPageScreenPanelDetail.this.mViewBinding).layoutTime.setVisibility(isChecked ? 0 : 8);
            }
        });
        initList();
        if (ProductRepository.getLightColorType(((ActSmartPanelVM) this.mViewModel).controlObject.getValue()) == 21) {
            if (this.curScreenNum == 0) {
                ((FtPageScreenPanelDetailBinding) this.mViewBinding).layoutShow.setVisibility(8);
            } else {
                ((FtPageScreenPanelDetailBinding) this.mViewBinding).layoutShow.setVisibility(0);
            }
            ((FtPageScreenPanelDetailBinding) this.mViewBinding).layoutCustom.setVisibility(0);
            ((FtPageScreenPanelDetailBinding) this.mViewBinding).layoutTime.setVisibility(0);
            return;
        }
        ((FtPageScreenPanelDetailBinding) this.mViewBinding).layoutShow.setVisibility(8);
        ((FtPageScreenPanelDetailBinding) this.mViewBinding).layoutCustom.setVisibility(8);
        ((FtPageScreenPanelDetailBinding) this.mViewBinding).layoutTime.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isIntersect(int start1, int end1, int start2, int end2) {
        return Math.max(start1, start2) <= Math.min(end1, end2);
    }

    private void initList() {
        BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_page_smart_panel_key_set) { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail.4
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Code restructure failed: missing block: B:122:0x01bb, code lost:
            
                if (r21.relateInfo.action != 20) goto L79;
             */
            /* JADX WARN: Code restructure failed: missing block: B:67:0x01a5, code lost:
            
                if (r21.type != 2) goto L79;
             */
            /* JADX WARN: Removed duplicated region for block: B:114:0x0297  */
            /* JADX WARN: Removed duplicated region for block: B:116:0x029e  */
            /* JADX WARN: Removed duplicated region for block: B:83:0x02b3  */
            /* JADX WARN: Removed duplicated region for block: B:95:0x0301  */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void convert(com.chad.library.adapter.base.BaseViewHolder r20, com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem r21) {
                /*
                    Method dump skipped, instructions count: 1201
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail.AnonymousClass4.convert(com.chad.library.adapter.base.BaseViewHolder, com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem):void");
            }
        };
        this.keyAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemChildClickListener(new AnonymousClass5());
        this.keyAdapter.bindToRecyclerView(((FtPageScreenPanelDetailBinding) this.mViewBinding).rv);
        ((FtPageScreenPanelDetailBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((FtPageScreenPanelDetailBinding) this.mViewBinding).rv.setNestedScrollingEnabled(false);
        ((FtPageScreenPanelDetailBinding) this.mViewBinding).rv.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail.6
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ConvertUtils.dp2px(15.0f);
            }
        });
    }

    /* renamed from: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail$5, reason: invalid class name */
    class AnonymousClass5 implements BaseQuickAdapter.OnItemChildClickListener {
        static /* synthetic */ boolean lambda$onItemChildClick$0(BaseDialog baseDialog, View view) {
            return false;
        }

        AnonymousClass5() {
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
        public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, final int position) {
            RelatedInfoExtParam.RelateInfo relateInfo = ((RelateInfoItem) FtPageScreenPanelDetail.this.keyAdapter.getData().get(position)).relateInfo;
            int id = view.getId();
            int i = 0;
            if (id == R.id.layout_display) {
                FtPageScreenPanelDetail.this.selectPos = position;
                FtPageScreenPanelDetail.this.relateInfo = null;
                SetScreenDisplayDialog.setIconIndex(((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).relateInfoAssistant.getRelateInfo(FtPageScreenPanelDetail.this.getRealItemPosition(position)) == null ? 0 : ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).relateInfoAssistant.getRelateInfo(FtPageScreenPanelDetail.this.getRealItemPosition(position)).iconIndex);
                SetScreenDisplayDialog screenText = SetScreenDisplayDialog.asDefault(((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).isOpenElderLyMode).setScreenText(((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).relateInfoAssistant.getRelateInfo(position) == null ? "" : ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).relateInfoAssistant.getRelateInfo(FtPageScreenPanelDetail.this.getRealItemPosition(position)).screenStr);
                if (!FtPageScreenPanelDetail.this.isS6Pro() && ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).relateInfoAssistant.getRelateInfo(position) != null && (((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).relateInfoAssistant.getRelateInfo(position).screenType != 3 || !((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).isOpenElderLyMode)) {
                    i = ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).relateInfoAssistant.getRelateInfo(position).screenType;
                }
                screenText.setScreenType(i).setShowTab(!FtPageScreenPanelDetail.this.isS6Pro()).setOnDialogCallback(new SetScreenDisplayDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail.5.2
                    @Override // com.ltech.smarthome.view.dialog.SetScreenDisplayDialog.OnDialogCallback
                    public boolean onSave() {
                        FtPageScreenPanelDetail.this.setScreenData(FtPageScreenPanelDetail.this.getRealItemPosition(FtPageScreenPanelDetail.this.selectPos));
                        return false;
                    }

                    @Override // com.ltech.smarthome.view.dialog.SetScreenDisplayDialog.OnDialogCallback
                    public void onCancel() {
                        FtPageScreenPanelDetail.this.selectPos = -1;
                        FtPageScreenPanelDetail.this.keyAdapter.notifyDataSetChanged();
                    }

                    @Override // com.ltech.smarthome.view.dialog.SetScreenDisplayDialog.OnDialogCallback
                    public void onScreenChanged(int screenType, String screenStr, int iconIndex) {
                        FtPageScreenPanelDetail.this.relateInfo = new RelatedInfoExtParam.RelateInfo();
                        FtPageScreenPanelDetail.this.relateInfo.screenType = screenType;
                        FtPageScreenPanelDetail.this.relateInfo.screenStr = screenStr;
                        FtPageScreenPanelDetail.this.relateInfo.iconIndex = iconIndex;
                        FtPageScreenPanelDetail.this.keyAdapter.notifyDataSetChanged();
                    }
                }).setHeight((int) (FtPageScreenPanelDetail.this.getActivity().getWindowManager().getDefaultDisplay().getHeight() * 0.17f)).showDialog(FtPageScreenPanelDetail.this.getActivity());
                return;
            }
            if (id == R.id.layout_time) {
                FtPageScreenPanelDetail.this.selectPos = position;
                if (relateInfo.type == 3) {
                    FtPageScreenPanelDetail ftPageScreenPanelDetail = FtPageScreenPanelDetail.this;
                    ftPageScreenPanelDetail.showLongClickBindDialog(ftPageScreenPanelDetail.getRealItemPosition(ftPageScreenPanelDetail.selectPos));
                    return;
                } else {
                    FtPageScreenPanelDetail.this.showDelayTimeDialog();
                    return;
                }
            }
            if (id != R.id.tv_sub_text) {
                return;
            }
            if (Injection.repo().home().getSelectPlace().getValue().isMember()) {
                FtPageScreenPanelDetail.this.showNoPermissionDialog();
                return;
            }
            if (((RelateInfoItem) FtPageScreenPanelDetail.this.keyAdapter.getData().get(position)).infoName == null || ((RelateInfoItem) FtPageScreenPanelDetail.this.keyAdapter.getData().get(position)).type == 0) {
                int i2 = ((RelateInfoItem) FtPageScreenPanelDetail.this.keyAdapter.getData().get(position)).relateInfo.switchIndex;
                if (((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).relayObjectMap == null || !((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(i2))) {
                    ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).showBindDialog(FtPageScreenPanelDetail.this.getRealItemPosition(position));
                    return;
                }
                Object obj = ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).relayObjectMap.get(Integer.valueOf(i2));
                String str = FtPageScreenPanelDetail.this.getString(R.string.on_off) + i2;
                if (obj instanceof Device) {
                    str = ((Device) obj).getName();
                } else if (obj instanceof Group) {
                    str = ((Group) obj).getName();
                }
                MessageDialog.show((AppCompatActivity) FtPageScreenPanelDetail.this.getActivity(), FtPageScreenPanelDetail.this.getString(R.string.app_str_relay_bind_tip), String.format(FtPageScreenPanelDetail.this.getString(R.string.app_str_relay_bind_content2), str)).setCancelable(false).setOkButton(FtPageScreenPanelDetail.this.getString(R.string.app_str_continue_to_bind), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail.5.1
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).showBindDialog(FtPageScreenPanelDetail.this.getRealItemPosition(position));
                        return false;
                    }
                }).setCancelButton(FtPageScreenPanelDetail.this.getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail$5$$ExternalSyntheticLambda0
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view2) {
                        return FtPageScreenPanelDetail.AnonymousClass5.lambda$onItemChildClick$0(baseDialog, view2);
                    }
                });
                return;
            }
            FtPageScreenPanelDetail ftPageScreenPanelDetail2 = FtPageScreenPanelDetail.this;
            ftPageScreenPanelDetail2.showUnbindDialog(ftPageScreenPanelDetail2.getRealItemPosition(position));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getTimeStr(int time, int delayType) {
        String string;
        if (time == 65535) {
            return getString(R.string.mr_stay_time_11);
        }
        if (delayType == 0) {
            string = getString(R.string.app_str_delay_off_execute_time);
        } else {
            string = getString(R.string.app_str_delay_execute_time);
        }
        return String.format(string, time + getString(R.string.sec));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindTime() {
        if (((ActSmartPanelVM) this.mViewModel).isScreenTimeClose[this.curScreenNum]) {
            ((FtPageScreenPanelDetailBinding) this.mViewBinding).tvShowTime.setText(getString(R.string.off_1));
        } else {
            ((FtPageScreenPanelDetailBinding) this.mViewBinding).tvShowTime.setText(String.format(Locale.getDefault(), "%02d:%02d - %02d:%02d", Integer.valueOf(((ActSmartPanelVM) this.mViewModel).screenTime[this.curScreenNum][0]), Integer.valueOf(((ActSmartPanelVM) this.mViewModel).screenTime[this.curScreenNum][1]), Integer.valueOf(((ActSmartPanelVM) this.mViewModel).screenTime[this.curScreenNum][2]), Integer.valueOf(((ActSmartPanelVM) this.mViewModel).screenTime[this.curScreenNum][3])));
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        super.startObserve();
        ((ActSmartPanelVM) this.mViewModel).controlObject.observe(this, new Observer<Object>() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail.7
            @Override // androidx.lifecycle.Observer
            public void onChanged(Object o) {
                ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        FtPageScreenPanelDetail.this.refresh();
                    }
                }, 1000L);
            }
        });
        ((ActSmartPanelVM) this.mViewModel).screenStateEvent.observe(this, new Observer<Object>() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail.8
            @Override // androidx.lifecycle.Observer
            public void onChanged(Object o) {
                FtPageScreenPanelDetail.this.refresh();
            }
        });
        ((ActSmartPanelVM) this.mViewModel).sceneBrtEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail.9
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                FtPageScreenPanelDetail.this.showSceneBrtDialog();
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        refresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refresh() {
        List<RelateInfoItem> list;
        if (this.keyAdapter == null || this.mViewBinding == 0) {
            return;
        }
        ((FtPageScreenPanelDetailBinding) this.mViewBinding).sb.setCheckedNotByUser(((ActSmartPanelVM) this.mViewModel).isScreenShow[this.curScreenNum]);
        if (ProductRepository.getLightColorType(((ActSmartPanelVM) this.mViewModel).controlObject.getValue()) == 21) {
            ((FtPageScreenPanelDetailBinding) this.mViewBinding).layoutTime.setVisibility(((ActSmartPanelVM) this.mViewModel).isScreenShow[this.curScreenNum] ? 0 : 8);
        }
        bindTime();
        if (((ActSmartPanelVM) this.mViewModel).relatedInfoList != null) {
            try {
                if (ProductRepository.getLightColorType(((ActSmartPanelVM) this.mViewModel).controlObject.getValue()) == 21) {
                    List<RelateInfoItem> list2 = ((ActSmartPanelVM) this.mViewModel).relatedInfoList;
                    int i = this.curScreenNum;
                    list = new ArrayList<>(list2.subList(i * 4, (i + 1) * 4));
                } else {
                    list = ((ActSmartPanelVM) this.mViewModel).relatedInfoList;
                }
                this.keyAdapter.setNewData(list);
            } catch (Exception e) {
                LHomeLog.e(getClass(), e.toString());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setScreenData(final int position) {
        byte[] bArr;
        int i;
        byte[] bArr2;
        int i2;
        byte[] bArr3;
        int i3;
        RelatedInfoExtParam.RelateInfo relateInfo = this.relateInfo;
        if (relateInfo == null) {
            return;
        }
        final int i4 = 1;
        try {
        } catch (UnsupportedEncodingException e) {
            e = e;
            bArr = null;
        }
        if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeWord.getValue()) {
            String[] split = this.relateInfo.screenStr.split("\n");
            byte[] bytes = split.length > 0 ? split[0].getBytes("gb2312") : null;
            try {
                r4 = split.length > 1 ? split[1].getBytes("gb2312") : null;
                byte[] bArr4 = bytes;
                bArr3 = r4;
                r4 = bArr4;
            } catch (UnsupportedEncodingException e2) {
                e = e2;
                bArr = bytes;
                i = 1;
                e.printStackTrace();
                bArr2 = null;
                i2 = i;
                r4 = bArr;
                showLoadingDialog("");
                final int i5 = i2;
                final byte[] bArr5 = bArr2;
                CmdAssistant.getDeviceAssistant(((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), new int[0]).setPanelScreenData(getActivity(), position, i4, r4, i2, bArr2, new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail$$ExternalSyntheticLambda5
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        FtPageScreenPanelDetail.this.lambda$setScreenData$0(position, i4, r4, i5, bArr5, (ResponseMsg) obj);
                    }
                });
            }
        } else {
            if (this.relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIcon.getValue()) {
                try {
                    int sendIconIndex = ScreenIconUtils.getSendIconIndex(this.relateInfo.iconIndex);
                    bArr3 = null;
                    r4 = new byte[]{(byte) ((sendIconIndex >> 8) & 255), (byte) (sendIconIndex & 255)};
                    i3 = 0;
                } catch (UnsupportedEncodingException e3) {
                    e = e3;
                    bArr = null;
                    i4 = 2;
                    i = 0;
                    e.printStackTrace();
                    bArr2 = null;
                    i2 = i;
                    r4 = bArr;
                    showLoadingDialog("");
                    final int i52 = i2;
                    final byte[] bArr52 = bArr2;
                    CmdAssistant.getDeviceAssistant(((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), new int[0]).setPanelScreenData(getActivity(), position, i4, r4, i2, bArr2, new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail$$ExternalSyntheticLambda5
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj) {
                            FtPageScreenPanelDetail.this.lambda$setScreenData$0(position, i4, r4, i52, bArr52, (ResponseMsg) obj);
                        }
                    });
                }
            } else if (this.relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIconWord.getValue()) {
                try {
                    int sendIconIndex2 = ScreenIconUtils.getSendIconIndex(this.relateInfo.iconIndex);
                    bArr = new byte[]{(byte) ((sendIconIndex2 >> 8) & 255), (byte) (sendIconIndex2 & 255)};
                    try {
                        bArr3 = this.relateInfo.screenStr.getBytes("gb2312");
                        r4 = bArr;
                        i3 = 1;
                    } catch (UnsupportedEncodingException e4) {
                        e = e4;
                        i4 = 2;
                        i = 1;
                        e.printStackTrace();
                        bArr2 = null;
                        i2 = i;
                        r4 = bArr;
                        showLoadingDialog("");
                        final int i522 = i2;
                        final byte[] bArr522 = bArr2;
                        CmdAssistant.getDeviceAssistant(((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), new int[0]).setPanelScreenData(getActivity(), position, i4, r4, i2, bArr2, new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail$$ExternalSyntheticLambda5
                            @Override // com.ltech.smarthome.base.IAction
                            public final void act(Object obj) {
                                FtPageScreenPanelDetail.this.lambda$setScreenData$0(position, i4, r4, i522, bArr522, (ResponseMsg) obj);
                            }
                        });
                    }
                } catch (UnsupportedEncodingException e5) {
                    e = e5;
                    bArr = null;
                }
            } else {
                bArr3 = null;
            }
            i4 = 2;
            i2 = i3;
            bArr2 = bArr3;
            showLoadingDialog("");
            final int i5222 = i2;
            final byte[] bArr5222 = bArr2;
            CmdAssistant.getDeviceAssistant(((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), new int[0]).setPanelScreenData(getActivity(), position, i4, r4, i2, bArr2, new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail$$ExternalSyntheticLambda5
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    FtPageScreenPanelDetail.this.lambda$setScreenData$0(position, i4, r4, i5222, bArr5222, (ResponseMsg) obj);
                }
            });
        }
        i3 = 1;
        i2 = i3;
        bArr2 = bArr3;
        showLoadingDialog("");
        final int i52222 = i2;
        final byte[] bArr52222 = bArr2;
        CmdAssistant.getDeviceAssistant(((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), new int[0]).setPanelScreenData(getActivity(), position, i4, r4, i2, bArr2, new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtPageScreenPanelDetail.this.lambda$setScreenData$0(position, i4, r4, i52222, bArr52222, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScreenData$0(int i, int i2, byte[] bArr, int i3, byte[] bArr2, ResponseMsg responseMsg) {
        dismissLoadingDialog();
        if (responseMsg != null && (responseMsg.getStateCode() == 0 || responseMsg.getStateCode() == 153)) {
            RelatedInfoExtParam.RelateInfo relateInfo = ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getRelateInfo(i);
            if (relateInfo == null) {
                relateInfo = new RelatedInfoExtParam.RelateInfo();
            }
            relateInfo.screenType = this.relateInfo.screenType;
            relateInfo.screenStr = this.relateInfo.screenStr;
            relateInfo.iconIndex = this.relateInfo.iconIndex;
            ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.setRelateInfo(i, relateInfo);
            ((ActSmartPanelVM) this.mViewModel).uploadData();
            if (!((ActSmartPanelVM) this.mViewModel).groupControl) {
                ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.TEXT, CmdAssistant.getDeviceAssistant(((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), new int[0]).setPanelScreenData(i, i2, bArr, i3, bArr2));
                ReplaceHelper.instance().backupIndexData(getActivity(), ((Device) ((ActSmartPanelVM) this.mViewModel).controlObject.getValue()).getDeviceId(), i + 1);
            }
            refresh();
            return;
        }
        showErrorDialog(getString(R.string.save_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getRealItemPosition(int p) {
        return (this.curScreenNum * 4) + p;
    }

    public void refreshRelateInfoList(Device device) {
        RelateInfoUtils.initRelateInfoList(device);
        ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant = RelateInfoUtils.relateInfoAssistant;
        ((ActSmartPanelVM) this.mViewModel).relatedInfoList.clear();
        ((ActSmartPanelVM) this.mViewModel).relatedInfoList.addAll(RelateInfoUtils.relatedInfoList);
        ((ActSmartPanelVM) this.mViewModel).updateUIEvent.setValue(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUnbindDialog(final int position) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.reset_relate));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtPageScreenPanelDetail.this.lambda$showUnbindDialog$1(position, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(getActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$1(int i, Integer num) {
        ((ActSmartPanelVM) this.mViewModel).unBindRelateInfo(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isS6Pro() {
        return ProductRepository.getLightColorType(((ActSmartPanelVM) this.mViewModel).controlObject.getValue()) == 19;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNoPermissionDialog() {
        MessageDialog.show((AppCompatActivity) getActivity(), getString(R.string.app_member_no_permission), getString(R.string.app_member_no_permission_tip)).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return FtPageScreenPanelDetail.lambda$showNoPermissionDialog$2(baseDialog, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDelayTimeDialog() {
        RelatedInfoExtParam.RelateInfo relateInfo = this.keyAdapter.getData().get(this.selectPos).relateInfo;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i <= 60; i++) {
            arrayList.add(i + getResources().getString(R.string.sec));
        }
        TimeSelectDialog.asDefault().setTitle(getString(R.string.delay_time)).setMinList(arrayList).withUnit(true).setMinCenter(true).setSelectMinPosition(relateInfo.delay).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i2, int i3) {
                FtPageScreenPanelDetail.this.lambda$showDelayTimeDialog$3(i2, i3);
            }
        }).showDialog(getActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDelayTimeDialog$3(int i, int i2) {
        ((ActSmartPanelVM) this.mViewModel).setDelayActionTime(getRealItemPosition(this.selectPos), i, new IAction<Integer>() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail.10
            @Override // com.ltech.smarthome.base.IAction
            public void act(Integer i3) {
                RelateInfoItem relateInfoItem = (RelateInfoItem) FtPageScreenPanelDetail.this.keyAdapter.getData().get(FtPageScreenPanelDetail.this.selectPos);
                relateInfoItem.relateInfo.delay = i3.intValue();
                FtPageScreenPanelDetail.this.keyAdapter.setData(FtPageScreenPanelDetail.this.selectPos, relateInfoItem);
                RelateInfoAssistant relateInfoAssistant = ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).relateInfoAssistant;
                FtPageScreenPanelDetail ftPageScreenPanelDetail = FtPageScreenPanelDetail.this;
                relateInfoAssistant.setRelateInfo(ftPageScreenPanelDetail.getRealItemPosition(ftPageScreenPanelDetail.selectPos), relateInfoItem.relateInfo);
                RelateInfoAssistant relateInfoAssistant2 = ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).relateInfoAssistant;
                FtPageScreenPanelDetail ftPageScreenPanelDetail2 = FtPageScreenPanelDetail.this;
                relateInfoAssistant2.setRelateLongClickInfo(ftPageScreenPanelDetail2.getRealItemPosition(ftPageScreenPanelDetail2.selectPos), new RelatedInfoExtParam.RelateInfo());
                ((ActSmartPanelVM) FtPageScreenPanelDetail.this.mViewModel).uploadData();
            }
        });
    }

    public void showSceneBrtDialog() {
        final RelatedInfoExtParam.RelateInfo relateInfo = this.keyAdapter.getData().get(getRealItemPosition(this.selectPos)).relateInfo;
        RelateInfoItem relateInfoItem = ((ActSmartPanelVM) this.mViewModel).longClickMap.get(Integer.valueOf(getRealItemPosition(this.selectPos)));
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(true).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setSelectPosition(relateInfoItem == null ? -1 : relateInfoItem.relateInfo.action - 1);
        arrayList.add(getString(R.string.key_switch_action_7));
        arrayList.add(getString(R.string.key_switch_action_8));
        arrayList.add(getString(R.string.key_switch_action_9));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtPageScreenPanelDetail.this.lambda$showSceneBrtDialog$4(relateInfo, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSceneBrtDialog$4(RelatedInfoExtParam.RelateInfo relateInfo, Integer num) {
        int intValue = num.intValue();
        if (intValue == 0) {
            ((ActSmartPanelVM) this.mViewModel).subscribe(getRealItemPosition(this.selectPos), 1, relateInfo.objectId);
        } else if (intValue == 1) {
            ((ActSmartPanelVM) this.mViewModel).subscribe(getRealItemPosition(this.selectPos), 2, relateInfo.objectId);
        } else {
            if (intValue != 2) {
                return;
            }
            ((ActSmartPanelVM) this.mViewModel).subscribe(getRealItemPosition(this.selectPos), 3, relateInfo.objectId);
        }
    }

    public void showLongClickBindDialog(final int position) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getContext().getString(R.string.long_press)).setCancelString(getContext().getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getContext().getString(R.string.app_str_delay_execute));
        arrayList.add(getContext().getString(R.string.app_str_execute_cur_scene_brt));
        arrayList.add(getContext().getString(R.string.app_str_execute_other_local_scene));
        arrayList.add(getContext().getString(R.string.app_str_execute_other_dali_scene));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtPageScreenPanelDetail.this.lambda$showLongClickBindDialog$5(position, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showLongClickBindDialog$5(int i, Integer num) {
        int intValue = num.intValue();
        if (intValue == 0) {
            showDelayTimeDialog();
            return;
        }
        if (intValue == 1) {
            ((ActSmartPanelVM) this.mViewModel).sceneBrtEvent.call();
        } else if (intValue == 2) {
            NavUtils.destination(ActSmartPanelSelectScene.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActSmartPanelVM) this.mViewModel).controlId).withInt(Constants.RELATED_POSITION, i).withBoolean(Constants.GROUP_CONTROL, ((ActSmartPanelVM) this.mViewModel).groupControl).withInt(Constants.RELATED_POSITION, i).withInt(Constants.CLICK_TYPE, 2).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
        } else {
            if (intValue != 3) {
                return;
            }
            NavUtils.destination(ActSelectCgdPro.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActSmartPanelVM) this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, ((ActSmartPanelVM) this.mViewModel).groupControl).withInt(Constants.RELATED_POSITION, i).withInt(Constants.CLICK_TYPE, 2).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
        }
    }
}