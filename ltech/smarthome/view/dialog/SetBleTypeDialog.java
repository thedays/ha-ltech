package com.ltech.smarthome.view.dialog;

import android.text.TextUtils;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.PickerAdapter;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSetBleTypeBinding;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.SelectorUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.RadioImageTextView;
import com.ltech.smarthome.view.dialog.SetBleTypeDialog;
import com.ltech.smarthome.view.layoutmanager.PickerLayoutManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SetBleTypeDialog extends SmartDialog<DialogSetBleTypeBinding> {
    private static DialogSetBleTypeBinding mViewBinding;
    private int changeType;
    private String label;
    private boolean location;
    PickerLayoutManager mFloorManager;
    private OnChangeTrigTypeListener mOnChangeTrigTypeListener;
    private OnChangeTypeListener mOnChangeTypeListener;
    private IAction<Void> mOnLocationListener;
    private OnSaveListener mOnSaveListener;
    private OnSelectFloorListener mOnSelectFloorListener;
    PickerLayoutManager mRoomManager;
    private OnEurPanelSelectListener onEurPanelSelectListener;
    private int outputType;
    private String productId;
    private boolean selectRoom;
    private int selectType;
    private String title;
    private boolean isDryToBle = false;
    private ObservableField<String> content = new ObservableField<>();
    private int dryContactSelectType = -1;
    private int eurPanelType = -1;
    private int asPanelType = -1;
    private int g4teType = -1;
    private int functionZoneControlSelect = 4;
    private int functionControlTypeSelect = 3;
    private List<String> roomNameList = new ArrayList();
    private List<String> floorNameList = new ArrayList();
    private int selectFloorPosition = -1;
    private int selectRoomPosition = -1;
    private int deviceIndex = -1;
    private boolean addGroup = false;

    public interface OnChangeTrigTypeListener {
        void setType(int outputType, boolean isDryToBle);
    }

    public interface OnChangeTypeListener {
        void setType(int outputType, int deviceType, boolean outputChange);
    }

    public interface OnEurPanelSelectListener {
        void onSelect(int zoneControl, int controlType);
    }

    public interface OnSaveListener {
        void cancel();

        boolean onSave(String name, boolean changeType, int type, int outputType, int floorPos, int roomPos, int zoneControl, int controlType);
    }

    public interface OnSelectFloorListener {
        void selectFloor(SetBleTypeDialog dialog, int position, String floorName);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_set_ble_type;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SetBleTypeDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSetBleTypeBinding, SetBleTypeDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogSetBleTypeBinding viewBinding, final SetBleTypeDialog dialog) {
            SetBleTypeDialog.mViewBinding = viewBinding;
            viewBinding.setContent(dialog.content);
            dialog.showLightTypeGroup(viewBinding, false);
            if (dialog.asPanelType != -1) {
                if (dialog.asPanelType == 4) {
                    dialog.functionControlTypeSelect = 4;
                    viewBinding.groupAsPanelRgb.setVisibility(0);
                    viewBinding.groupTvType.setVisibility(8);
                } else if (dialog.asPanelType == 5) {
                    dialog.functionControlTypeSelect = 5;
                    viewBinding.groupEurPanel.setVisibility(8);
                    viewBinding.groupTvType.setVisibility(8);
                } else {
                    dialog.functionControlTypeSelect = dialog.asPanelType;
                    viewBinding.groupEurPanel.setVisibility(8);
                    viewBinding.groupTvType.setVisibility(8);
                }
                dialog.initAsPanel(viewBinding);
            } else if (dialog.eurPanelType != -1) {
                dialog.initEurPanel(viewBinding, dialog.eurPanelType);
                if (dialog.eurPanelType == 11) {
                    viewBinding.groupEurPanel.setVisibility(8);
                    viewBinding.groupAsPanelRgb.setVisibility(0);
                    viewBinding.radioControlTypeRgbwy.setVisibility(0);
                    viewBinding.groupTvType.setVisibility(8);
                } else if (dialog.eurPanelType == 5) {
                    viewBinding.groupEurPanel.setVisibility(0);
                    viewBinding.groupAsPanelRgb.setVisibility(0);
                    viewBinding.radioControlTypeRgbwy.setVisibility(0);
                    viewBinding.groupTvType.setVisibility(8);
                } else if (dialog.eurPanelType == 6) {
                    dialog.functionZoneControlSelect = 1;
                    dialog.functionControlTypeSelect = 1;
                    dialog.changeType = 31;
                    dialog.initWithSwitchType(viewBinding, dialog.functionControlTypeSelect, dialog.changeType);
                    dialog.showLightTypeGroup(viewBinding, true);
                    viewBinding.groupTvType.setVisibility(0);
                    viewBinding.viewSecondRow.setVisibility(0);
                    viewBinding.tvTypeTip.setText(R.string.control_type);
                    dialog.setContent(dialog.getString(R.string.eur_knob_name_dim));
                } else {
                    dialog.functionControlTypeSelect = dialog.eurPanelType;
                    viewBinding.groupEurPanel.setVisibility(0);
                    viewBinding.groupTvType.setVisibility(8);
                }
            } else if (dialog.selectType != 0) {
                if (dialog.outputType != 0) {
                    dialog.initOutputType(viewBinding, dialog.outputType);
                    dialog.selectType = 2;
                    viewBinding.groupOutput.setVisibility(0);
                } else {
                    viewBinding.groupOutput.setVisibility(8);
                }
                if (dialog.changeType != 0) {
                    viewBinding.groupTvType.setVisibility(0);
                    viewBinding.viewSecondRow.setVisibility(0);
                    if (ProductRepository.isE6Panel(dialog.productId)) {
                        viewBinding.tvTypeTip.setText(R.string.control_type);
                        if (ProductId.ID_KNOB_PANEL_E6M.equals(dialog.productId)) {
                            dialog.selectType = 5;
                        } else {
                            dialog.selectType = 2;
                            dialog.setContent(dialog.getString(R.string.e6_ct_short));
                        }
                    }
                    if (dialog.selectType == 20) {
                        dialog.showLightTypeGroup(viewBinding, true);
                        viewBinding.radioRgb.setTextContent("RGB");
                        viewBinding.radioRgbw.setTextContent("RGBW");
                        viewBinding.radioRgbwy.setTextContent("RGBCW");
                        dialog.selectType = 5;
                    } else {
                        dialog.showLightTypeGroup(viewBinding, true);
                    }
                    dialog.initWithSwitchType(viewBinding, dialog.selectType, dialog.changeType);
                } else {
                    dialog.initNoSwitchType(viewBinding, dialog.selectType);
                    viewBinding.groupTvType.setVisibility(8);
                }
            } else if (dialog.dryContactSelectType != -1) {
                dialog.initDrySwitchType(viewBinding, dialog.dryContactSelectType);
                if (dialog.isDryToBle) {
                    viewBinding.tbType.setVisibility(0);
                } else {
                    viewBinding.dryContactType.setVisibility(0);
                    viewBinding.viewSecondRow.setVisibility(0);
                }
                viewBinding.groupTvType.setVisibility(0);
            } else if (dialog.g4teType != -1) {
                dialog.initG4teType(viewBinding);
                viewBinding.groupG4te.setVisibility(0);
                viewBinding.groupTvType.setVisibility(0);
                viewBinding.tvTypeTip.setText(dialog.getString(R.string.te_type));
            } else {
                viewBinding.groupOutput.setVisibility(8);
                viewBinding.dryContactType.setVisibility(8);
                viewBinding.groupTvType.setVisibility(8);
            }
            if (dialog.title != null) {
                viewBinding.tvTitle.setText(dialog.title);
            }
            if (dialog.label != null) {
                viewBinding.tvLabel.setText(dialog.label);
            }
            if (dialog.location) {
                viewBinding.ivLocation.setVisibility(0);
            }
            dialog.initRoomData();
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SetBleTypeDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SetBleTypeDialog.AnonymousClass1.lambda$convertView$0(SetBleTypeDialog.this, viewBinding, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(SetBleTypeDialog setBleTypeDialog, DialogSetBleTypeBinding dialogSetBleTypeBinding, View view) {
            switch (view.getId()) {
                case R.id.iv_clear /* 2131296977 */:
                    setBleTypeDialog.setContent("");
                    break;
                case R.id.iv_location /* 2131297121 */:
                    if (setBleTypeDialog.mOnLocationListener != null) {
                        setBleTypeDialog.mOnLocationListener.act(null);
                        break;
                    }
                    break;
                case R.id.tv_cancel /* 2131298504 */:
                    setBleTypeDialog.dismissDialog();
                    if (setBleTypeDialog.mOnSaveListener != null) {
                        setBleTypeDialog.mOnSaveListener.cancel();
                        break;
                    }
                    break;
                case R.id.tv_save /* 2131298929 */:
                    if (TextUtils.isEmpty(dialogSetBleTypeBinding.etDeviceName.getText().toString())) {
                        SmartToast.showShort(R.string.device_name_empty);
                        break;
                    } else {
                        setBleTypeDialog.save();
                        break;
                    }
            }
        }
    }

    public static SetBleTypeDialog asDefault() {
        return (SetBleTypeDialog) new SetBleTypeDialog().setViewConverter(new AnonymousClass1()).setGravity(80).setMargin(10).setY(16).setOutCancel(false);
    }

    public void initRoomData() {
        DialogSetBleTypeBinding dialogSetBleTypeBinding = mViewBinding;
        if (dialogSetBleTypeBinding == null) {
            return;
        }
        initRv(dialogSetBleTypeBinding);
        if (this.selectRoom) {
            mViewBinding.viewDivider.setVisibility(0);
            mViewBinding.pickerViewRoom.setVisibility(0);
            mViewBinding.pickViewFloor.setVisibility(0);
            SelectorUtils.updateListData(this.roomNameList, this.selectRoomPosition, mViewBinding.pickerViewRoom);
            SelectorUtils.updateListData(this.floorNameList, this.selectFloorPosition, mViewBinding.pickViewFloor);
            getFloorManager().setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.SetBleTypeDialog$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
                public final void onPicked(RecyclerView recyclerView, int i) {
                    SetBleTypeDialog.this.lambda$initRoomData$0(recyclerView, i);
                }
            });
            getRoomManager().setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.SetBleTypeDialog$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
                public final void onPicked(RecyclerView recyclerView, int i) {
                    SetBleTypeDialog.this.lambda$initRoomData$1(recyclerView, i);
                }
            });
            return;
        }
        mViewBinding.viewDivider.setVisibility(4);
        mViewBinding.pickerViewRoom.setVisibility(8);
        mViewBinding.pickViewFloor.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRoomData$0(RecyclerView recyclerView, int i) {
        this.selectFloorPosition = i;
        OnSelectFloorListener onSelectFloorListener = this.mOnSelectFloorListener;
        if (onSelectFloorListener != null) {
            onSelectFloorListener.selectFloor(this, i, this.floorNameList.get(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRoomData$1(RecyclerView recyclerView, int i) {
        this.selectRoomPosition = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void save() {
        int i = this.selectType;
        int i2 = this.dryContactSelectType;
        if (i2 != -1) {
            if (this.isDryToBle) {
                i2++;
            }
            i = i2;
        }
        int i3 = this.g4teType;
        int i4 = i3 != -1 ? i3 : i;
        OnSaveListener onSaveListener = this.mOnSaveListener;
        if (onSaveListener == null) {
            dismissDialog();
            return;
        }
        if (onSaveListener.onSave(this.content.get(), this.changeType != 0, i4, this.outputType, this.selectFloorPosition, this.selectRoomPosition, this.functionZoneControlSelect, this.functionControlTypeSelect)) {
            dismissDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLightTypeGroup(DialogSetBleTypeBinding viewBinding, boolean visible) {
        viewBinding.radioDim.setVisibility(visible ? 0 : 8);
        viewBinding.radioCt.setVisibility(visible ? 0 : 8);
        viewBinding.radioRgb.setVisibility(visible ? 0 : 8);
        viewBinding.radioRgbw.setVisibility(visible ? 0 : 8);
        viewBinding.radioRgbwy.setVisibility(visible ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initNoSwitchType(DialogSetBleTypeBinding viewBinding, int type) {
        this.selectType = type;
        setEnable(viewBinding, viewBinding.radioDim, 1 == this.selectType);
        setEnable(viewBinding, viewBinding.radioCt, 2 == this.selectType);
        setEnable(viewBinding, viewBinding.radioRgb, 3 == this.selectType);
        setEnable(viewBinding, viewBinding.radioRgbw, 4 == this.selectType);
        setEnable(viewBinding, viewBinding.radioRgbwy, 5 == this.selectType);
        refreshCheck(viewBinding);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initWithSwitchType(DialogSetBleTypeBinding viewBinding, int type, int changeType) {
        this.selectType = type;
        if (ProductId.ID_KNOB_PANEL_E6A.equals(this.productId)) {
            viewBinding.viewSecondRow.setVisibility(8);
            viewBinding.radioRgb.setVisibility(8);
            viewBinding.radioRgbw.setVisibility(8);
            viewBinding.radioRgbwy.setVisibility(8);
        }
        if (this.outputType == 3) {
            viewBinding.radio512.setVisibility(0);
            setEnable(viewBinding, viewBinding.radio512, true);
        } else {
            viewBinding.radio512.setVisibility(8);
            setEnable(viewBinding, viewBinding.radio512, false);
        }
        setEnable(viewBinding, viewBinding.radioDim, (changeType & 1) == 1);
        setEnable(viewBinding, viewBinding.radioCt, (changeType & 2) == 2);
        setEnable(viewBinding, viewBinding.radioRgb, (changeType & 4) == 4);
        setEnable(viewBinding, viewBinding.radioRgbw, (changeType & 8) == 8);
        setEnable(viewBinding, viewBinding.radioRgbwy, (changeType & 16) == 16);
        if (((1 << (this.selectType - 1)) & changeType) == 0) {
            this.selectType = 1;
        }
        refreshCheck(viewBinding);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initOutputType(DialogSetBleTypeBinding viewBinding, int type) {
        initRadioButton(viewBinding, viewBinding.radio010V);
        initRadioButton(viewBinding, viewBinding.radioDali);
        initRadioButton(viewBinding, viewBinding.radioDmx);
        refreshOutputCheck(viewBinding);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initDrySwitchType(DialogSetBleTypeBinding viewBinding, int type) {
        setContent(getString(this.isDryToBle ? R.string.app_dry_to_ble_type1 : R.string.app_str_curtain));
        if (this.isDryToBle) {
            initRadioButton(viewBinding, viewBinding.radioTbType1);
            initRadioButton(viewBinding, viewBinding.radioTbType2);
            initRadioButton(viewBinding, viewBinding.radioTbType3);
        } else {
            initRadioButton(viewBinding, viewBinding.radioCurtain);
            initRadioButton(viewBinding, viewBinding.radioCurtainDream);
            initRadioButton(viewBinding, viewBinding.radioScene);
            initRadioButton(viewBinding, viewBinding.radioScene8);
        }
        refreshDryCheck(viewBinding);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initEurPanel(DialogSetBleTypeBinding viewBinding, int eurPanelType) {
        initRadioButton(viewBinding, viewBinding.radioZoneMulti);
        initRadioButton(viewBinding, viewBinding.radioZoneSingle);
        initRadioButton(viewBinding, viewBinding.radioControlTypeRgb);
        initRadioButton(viewBinding, viewBinding.radioControlTypeRgbw);
        initRadioButton(viewBinding, viewBinding.radioControlTypeRgbwy);
        refreshEurCheck(viewBinding);
        if (eurPanelType == 5) {
            setContent(getString(this.addGroup ? R.string.eur_group_rgb : R.string.eur_name_rgb));
        } else if (eurPanelType == 11) {
            setContent(getString(R.string.add_rc_rgb));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initAsPanel(DialogSetBleTypeBinding viewBinding) {
        initRadioButton(viewBinding, viewBinding.radioControlTypeRgb);
        initRadioButton(viewBinding, viewBinding.radioControlTypeRgbw);
        refreshAsCheck(viewBinding);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initG4teType(DialogSetBleTypeBinding viewBinding) {
        initRadioButton(viewBinding, viewBinding.radioAc);
        initRadioButton(viewBinding, viewBinding.radioAir);
        initRadioButton(viewBinding, viewBinding.radioAcAndAir);
        refreshG4teCheck(viewBinding);
    }

    private void initRadioButton(final DialogSetBleTypeBinding viewBinding, final RadioImageTextView radioImageTextView) {
        radioImageTextView.setTextContentColor(ContextCompat.getColor(getContext(), R.color.black));
        radioImageTextView.setEnable(true);
        radioImageTextView.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.view.dialog.SetBleTypeDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView2, boolean z) {
                SetBleTypeDialog.this.lambda$initRadioButton$2(radioImageTextView, viewBinding, radioImageTextView2, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRadioButton$2(RadioImageTextView radioImageTextView, DialogSetBleTypeBinding dialogSetBleTypeBinding, RadioImageTextView radioImageTextView2, boolean z) {
        switch (radioImageTextView.getId()) {
            case R.id.radio_010V /* 2131297918 */:
                this.outputType = 1;
                this.changeType = 3;
                initWithSwitchType(dialogSetBleTypeBinding, this.selectType, 3);
                refreshOutputCheck(dialogSetBleTypeBinding);
                sendChangeType(true);
                break;
            case R.id.radio_ac /* 2131297923 */:
                this.g4teType = 1;
                refreshG4teCheck(dialogSetBleTypeBinding);
                break;
            case R.id.radio_ac_and_air /* 2131297924 */:
                this.g4teType = 3;
                refreshG4teCheck(dialogSetBleTypeBinding);
                break;
            case R.id.radio_air /* 2131297926 */:
                this.g4teType = 2;
                refreshG4teCheck(dialogSetBleTypeBinding);
                break;
            case R.id.radio_control_type_rgb /* 2131297932 */:
                this.functionControlTypeSelect = 3;
                int i = this.eurPanelType;
                if (i == 5 || this.asPanelType == 4) {
                    setContent(getString(this.addGroup ? R.string.eur_group_rgb : R.string.eur_name_rgb));
                } else if (i == 11) {
                    setContent(getString(R.string.add_rc_rgb));
                }
                refreshEurCheck(dialogSetBleTypeBinding);
                sendEurPanelSelect();
                break;
            case R.id.radio_control_type_rgbw /* 2131297933 */:
                this.functionControlTypeSelect = 4;
                int i2 = this.eurPanelType;
                if (i2 == 5 || this.asPanelType == 4) {
                    setContent(getString(this.addGroup ? R.string.eur_group_rgbw : R.string.eur_name_rgbw));
                } else if (i2 == 11) {
                    setContent(getString(R.string.add_rc_rgbw));
                }
                refreshEurCheck(dialogSetBleTypeBinding);
                sendEurPanelSelect();
                break;
            case R.id.radio_control_type_rgbwy /* 2131297934 */:
                this.functionControlTypeSelect = 5;
                int i3 = this.eurPanelType;
                if (i3 == 5) {
                    setContent(getString(this.addGroup ? R.string.eur_group_rgbcw : R.string.eur_panel_eb5_short));
                } else if (i3 == 11) {
                    setContent(getString(R.string.add_rc_b5));
                }
                refreshEurCheck(dialogSetBleTypeBinding);
                sendEurPanelSelect();
                break;
            case R.id.radio_curtain /* 2131297938 */:
                this.dryContactSelectType = 0;
                setContent(getString(R.string.app_str_curtain));
                refreshDryCheck(dialogSetBleTypeBinding);
                sendTrigChangeType();
                break;
            case R.id.radio_curtain_dream /* 2131297939 */:
                this.dryContactSelectType = 3;
                setContent(getString(R.string.set_dream_curtain));
                refreshDryCheck(dialogSetBleTypeBinding);
                sendTrigChangeType();
                break;
            case R.id.radio_dali /* 2131297940 */:
                this.outputType = 2;
                this.changeType = 7;
                initWithSwitchType(dialogSetBleTypeBinding, this.selectType, 7);
                refreshOutputCheck(dialogSetBleTypeBinding);
                sendChangeType(true);
                break;
            case R.id.radio_dmx /* 2131297943 */:
                this.outputType = 3;
                this.changeType = 31;
                initWithSwitchType(dialogSetBleTypeBinding, this.selectType, 31);
                refreshOutputCheck(dialogSetBleTypeBinding);
                sendChangeType(true);
                break;
            case R.id.radio_scene /* 2131297973 */:
                this.dryContactSelectType = 1;
                setContent(getString(R.string.set_composition_15));
                refreshDryCheck(dialogSetBleTypeBinding);
                sendTrigChangeType();
                break;
            case R.id.radio_scene8 /* 2131297974 */:
                this.dryContactSelectType = 2;
                setContent(getString(R.string.set_composition_8));
                refreshDryCheck(dialogSetBleTypeBinding);
                sendTrigChangeType();
                break;
            case R.id.radio_tb_type1 /* 2131297979 */:
                this.dryContactSelectType = 0;
                setContent(getString(R.string.app_dry_to_ble_type1));
                refreshDryCheck(dialogSetBleTypeBinding);
                sendTrigChangeType();
                break;
            case R.id.radio_tb_type2 /* 2131297980 */:
                this.dryContactSelectType = 1;
                setContent(getString(R.string.app_dry_to_ble_type2));
                refreshDryCheck(dialogSetBleTypeBinding);
                sendTrigChangeType();
                break;
            case R.id.radio_tb_type3 /* 2131297981 */:
                this.dryContactSelectType = 2;
                setContent(getString(R.string.app_dry_to_ble_type3));
                refreshDryCheck(dialogSetBleTypeBinding);
                sendTrigChangeType();
                break;
            case R.id.radio_zone_multi /* 2131297983 */:
                this.functionZoneControlSelect = 4;
                refreshEurCheck(dialogSetBleTypeBinding);
                sendEurPanelSelect();
                break;
            case R.id.radio_zone_single /* 2131297984 */:
                this.functionZoneControlSelect = 1;
                refreshEurCheck(dialogSetBleTypeBinding);
                sendEurPanelSelect();
                break;
        }
    }

    private void initRv(DialogSetBleTypeBinding viewBinding) {
        PickerAdapter pickerAdapter = new PickerAdapter(getContext());
        PickerAdapter pickerAdapter2 = new PickerAdapter(getContext());
        pickerAdapter.setData(this.roomNameList);
        pickerAdapter2.setData(this.floorNameList);
        this.mRoomManager = new PickerLayoutManager.Builder(getContext()).setAlpha(true).setMaxItem(5).build();
        this.mFloorManager = new PickerLayoutManager.Builder(getContext()).setAlpha(true).setMaxItem(5).build();
        viewBinding.pickerViewRoom.setLayoutManager(this.mRoomManager);
        viewBinding.pickViewFloor.setLayoutManager(this.mFloorManager);
        viewBinding.pickerViewRoom.setAdapter(pickerAdapter);
        viewBinding.pickViewFloor.setAdapter(pickerAdapter2);
    }

    private void setEnable(final DialogSetBleTypeBinding viewBinding, final RadioImageTextView radioImageTextView, boolean enable) {
        if (enable) {
            radioImageTextView.setTextContentColor(ContextCompat.getColor(getContext(), R.color.black));
            radioImageTextView.setEnable(true);
            radioImageTextView.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.view.dialog.SetBleTypeDialog$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
                public final void onCheckChanged(RadioImageTextView radioImageTextView2, boolean z) {
                    SetBleTypeDialog.this.lambda$setEnable$3(radioImageTextView, viewBinding, radioImageTextView2, z);
                }
            });
        } else {
            radioImageTextView.setTextContentColor(ContextCompat.getColor(getContext(), R.color.color_text_gray));
            radioImageTextView.setEnable(false);
            radioImageTextView.setListener(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setEnable$3(RadioImageTextView radioImageTextView, DialogSetBleTypeBinding dialogSetBleTypeBinding, RadioImageTextView radioImageTextView2, boolean z) {
        switch (radioImageTextView.getId()) {
            case R.id.radio_512 /* 2131297922 */:
                this.selectType = 31;
                break;
            case R.id.radio_ct /* 2131297935 */:
                this.selectType = 2;
                break;
            case R.id.radio_dim /* 2131297942 */:
                this.selectType = 1;
                break;
            case R.id.radio_rgb /* 2131297970 */:
                this.selectType = 3;
                break;
            case R.id.radio_rgbw /* 2131297971 */:
                this.selectType = 4;
                break;
            case R.id.radio_rgbwy /* 2131297972 */:
                this.selectType = 5;
                break;
        }
        refreshCheck(dialogSetBleTypeBinding);
        if (this.eurPanelType == 6) {
            this.functionControlTypeSelect = this.selectType;
            sendEurPanelSelect();
            int i = this.selectType;
            if (i == 1) {
                setContent(getString(R.string.eur_knob_name_dim));
                return;
            }
            if (i == 2) {
                setContent(getString(R.string.eur_knob_name_ct));
                return;
            }
            if (i == 3) {
                setContent(getString(R.string.eur_knob_name_rgb));
                return;
            } else if (i == 4) {
                setContent(getString(R.string.eur_knob_name_rgbw));
                return;
            } else {
                if (i != 5) {
                    return;
                }
                setContent(getString(R.string.eur_knob_name_rgbcw));
                return;
            }
        }
        if (ProductRepository.isE6Panel(this.productId)) {
            int i2 = this.selectType;
            if (i2 == 1) {
                setContent(getString(R.string.e6_dim_short));
                return;
            }
            if (i2 == 2) {
                setContent(getString(R.string.e6_ct_short));
                return;
            }
            if (i2 == 3 || i2 == 4) {
                setContent(getString(R.string.e6_rgb_short));
                return;
            } else {
                if (i2 != 5) {
                    return;
                }
                setContent(getString(R.string.e6_rgbcw_short));
                return;
            }
        }
        sendChangeType(false);
        int i3 = this.selectType;
        if (i3 == 1) {
            setContent(getString(R.string.ble_device_light_dim));
            return;
        }
        if (i3 == 2) {
            setContent(getString(R.string.ble_device_light_ct));
            return;
        }
        if (i3 == 3) {
            setContent(getString(R.string.ble_device_light_rgb));
            return;
        }
        if (i3 == 4) {
            setContent(getString(R.string.ble_device_light_rgbw));
        } else if (i3 == 5) {
            setContent(getString(R.string.ble_device_light_rgbwy));
        } else {
            if (i3 != 31) {
                return;
            }
            setContent(getString(R.string.ble_device_light_512));
        }
    }

    private void sendChangeType(boolean outputChange) {
        OnChangeTypeListener onChangeTypeListener = this.mOnChangeTypeListener;
        if (onChangeTypeListener != null) {
            onChangeTypeListener.setType(this.outputType, this.selectType, outputChange);
        }
    }

    private void sendTrigChangeType() {
        OnChangeTrigTypeListener onChangeTrigTypeListener = this.mOnChangeTrigTypeListener;
        if (onChangeTrigTypeListener != null) {
            onChangeTrigTypeListener.setType(this.dryContactSelectType, this.isDryToBle);
        }
    }

    private void sendEurPanelSelect() {
        OnEurPanelSelectListener onEurPanelSelectListener = this.onEurPanelSelectListener;
        if (onEurPanelSelectListener != null) {
            onEurPanelSelectListener.onSelect(this.functionZoneControlSelect, this.functionControlTypeSelect);
        }
    }

    private void refreshDryCheck(DialogSetBleTypeBinding viewBinding) {
        if (this.isDryToBle) {
            viewBinding.radioTbType1.setCheck(this.dryContactSelectType == 0);
            viewBinding.radioTbType2.setCheck(1 == this.dryContactSelectType);
            viewBinding.radioTbType3.setCheck(2 == this.dryContactSelectType);
        } else {
            viewBinding.radioCurtain.setCheck(this.dryContactSelectType == 0);
            viewBinding.radioCurtainDream.setCheck(3 == this.dryContactSelectType);
            viewBinding.radioScene.setCheck(1 == this.dryContactSelectType);
            viewBinding.radioScene8.setCheck(2 == this.dryContactSelectType);
        }
    }

    private void refreshOutputCheck(DialogSetBleTypeBinding viewBinding) {
        viewBinding.radio010V.setCheck(1 == this.outputType);
        viewBinding.radioDali.setCheck(2 == this.outputType);
        viewBinding.radioDmx.setCheck(3 == this.outputType);
    }

    private void refreshEurCheck(DialogSetBleTypeBinding viewBinding) {
        viewBinding.radioZoneMulti.setCheck(4 == this.functionZoneControlSelect);
        viewBinding.radioZoneSingle.setCheck(1 == this.functionZoneControlSelect);
        viewBinding.radioControlTypeRgb.setCheck(3 == this.functionControlTypeSelect);
        viewBinding.radioControlTypeRgbw.setCheck(4 == this.functionControlTypeSelect);
        viewBinding.radioControlTypeRgbwy.setCheck(5 == this.functionControlTypeSelect);
    }

    private void refreshAsCheck(DialogSetBleTypeBinding viewBinding) {
        viewBinding.radioControlTypeRgb.setCheck(3 == this.functionControlTypeSelect);
        viewBinding.radioControlTypeRgbw.setCheck(4 == this.functionControlTypeSelect);
    }

    private void refreshCheck(DialogSetBleTypeBinding viewBinding) {
        boolean z = false;
        viewBinding.radioDim.setCheck(1 == this.selectType);
        viewBinding.radioCt.setCheck(2 == this.selectType);
        viewBinding.radioRgb.setCheck(3 == this.selectType);
        viewBinding.radioRgbw.setCheck(4 == this.selectType);
        viewBinding.radioRgbwy.setCheck(5 == this.selectType);
        RadioImageTextView radioImageTextView = viewBinding.radio512;
        if (this.changeType == 31 && 31 == this.selectType) {
            z = true;
        }
        radioImageTextView.setCheck(z);
    }

    private void refreshG4teCheck(DialogSetBleTypeBinding viewBinding) {
        viewBinding.radioAc.setCheck(1 == this.g4teType);
        viewBinding.radioAir.setCheck(2 == this.g4teType);
        viewBinding.radioAcAndAir.setCheck(3 == this.g4teType);
    }

    public PickerLayoutManager getRoomManager() {
        return this.mRoomManager;
    }

    public PickerLayoutManager getFloorManager() {
        return this.mFloorManager;
    }

    public SetBleTypeDialog setRoomList(List<String> roomList) {
        this.roomNameList.clear();
        this.roomNameList.addAll(roomList);
        return this;
    }

    public SetBleTypeDialog setFloorList(List<String> floorList) {
        this.floorNameList.clear();
        this.floorNameList.addAll(floorList);
        return this;
    }

    public SetBleTypeDialog setSelectFloorPosition(int selectFloorPosition) {
        this.selectFloorPosition = selectFloorPosition;
        return this;
    }

    public SetBleTypeDialog setSelectRoomPosition(int selectRoomPosition) {
        this.selectRoomPosition = selectRoomPosition;
        return this;
    }

    public SetBleTypeDialog setSelectRoom(boolean selectRoom) {
        this.selectRoom = selectRoom;
        return this;
    }

    public SetBleTypeDialog setLocation(boolean location) {
        this.location = location;
        return this;
    }

    public SetBleTypeDialog setContent(String content) {
        if (this.deviceIndex == -1 || "".equals(content)) {
            this.content.set(content);
            return this;
        }
        if (LanguageUtils.isChinese(ActivityUtils.getTopActivity())) {
            this.content.set(content + this.deviceIndex);
            return this;
        }
        this.content.set(content + " " + this.deviceIndex);
        return this;
    }

    public SetBleTypeDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SetBleTypeDialog setLabel(String label) {
        this.label = label;
        return this;
    }

    public SetBleTypeDialog controlType(int type, int changeType, int outputType) {
        this.selectType = type;
        this.outputType = outputType;
        if (outputType == 0) {
            this.changeType = changeType;
            return this;
        }
        if (outputType == 1) {
            this.changeType = 3;
            return this;
        }
        if (outputType == 2) {
            this.changeType = 7;
            return this;
        }
        if (outputType == 3) {
            this.changeType = 31;
        }
        return this;
    }

    public SetBleTypeDialog setDeviceIndex(int deviceIndex) {
        this.deviceIndex = deviceIndex;
        return this;
    }

    public SetBleTypeDialog setAddGroup(boolean addGroup) {
        this.addGroup = addGroup;
        return this;
    }

    public SetBleTypeDialog subType(int type) {
        this.dryContactSelectType = type;
        return this;
    }

    public SetBleTypeDialog g4teType(int g4teType) {
        this.g4teType = g4teType;
        return this;
    }

    public SetBleTypeDialog eurPanelType(int type) {
        this.eurPanelType = type;
        return this;
    }

    public int getEurPanelType() {
        return this.eurPanelType;
    }

    public void setAsPanelType(int asPanelType) {
        this.asPanelType = asPanelType;
    }

    public int getAsPanelType() {
        return this.asPanelType;
    }

    public SetBleTypeDialog setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public SetBleTypeDialog setOnSaveListener(OnSaveListener mOnSaveListener) {
        this.mOnSaveListener = mOnSaveListener;
        return this;
    }

    public SetBleTypeDialog setOnSelectFloorListener(OnSelectFloorListener mOnSelectFloorListener) {
        this.mOnSelectFloorListener = mOnSelectFloorListener;
        return this;
    }

    public SetBleTypeDialog setOnLocationListener(IAction<Void> mOnLocationListener) {
        this.mOnLocationListener = mOnLocationListener;
        return this;
    }

    public void setOnTypeChangeListener(OnChangeTypeListener mOnChangeTypeListener) {
        this.mOnChangeTypeListener = mOnChangeTypeListener;
    }

    public void setOnEurPanelSelectListener(OnEurPanelSelectListener onEurPanelSelectListener) {
        this.onEurPanelSelectListener = onEurPanelSelectListener;
    }

    public void setOnChangeTrigTypeListener(OnChangeTrigTypeListener mOnChangeTypeListener) {
        this.mOnChangeTrigTypeListener = mOnChangeTypeListener;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "set_ble_type_dialog";
    }

    public void setIsDryToBle(boolean b2) {
        this.isDryToBle = b2;
    }
}