package com.ltech.smarthome.view.dialog;

import android.text.TextUtils;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.PickerAdapter;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogAddSceneBinding;
import com.ltech.smarthome.utils.SelectorUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.RadioImageTextView;
import com.ltech.smarthome.view.dialog.AddSceneDialog;
import com.ltech.smarthome.view.layoutmanager.PickerLayoutManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class AddSceneDialog extends SmartDialog<DialogAddSceneBinding> {
    private String defaultName;
    PickerLayoutManager mFloorManager;
    private OnSaveListener mOnSaveListener;
    private OnSelectFloorListener mOnSelectFloorListener;
    PickerLayoutManager mRoomManager;
    private boolean selectRoom;
    private String title;
    private boolean isLocal = true;
    private List<String> roomNameList = new ArrayList();
    private List<String> floorNameList = new ArrayList();
    private int selectFloorPosition = -1;
    private int selectRoomPosition = -1;

    public interface OnSaveListener {
        void cancel();

        void onSave(String name, boolean isLocal, int floorPos, int roomPos);
    }

    public interface OnSelectFloorListener {
        void selectFloor(AddSceneDialog dialog, int position, String floorName);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_add_scene;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.AddSceneDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogAddSceneBinding, AddSceneDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogAddSceneBinding viewBinding, final AddSceneDialog dialog) {
            if (dialog.title != null) {
                viewBinding.tvTitle.setText(dialog.title);
            }
            if (dialog.defaultName != null) {
                viewBinding.etName.setText(dialog.defaultName);
            }
            dialog.initTypeSelect(viewBinding);
            dialog.initRv(viewBinding);
            if (dialog.selectRoom) {
                viewBinding.viewDivider.setVisibility(0);
                viewBinding.pickerViewRoom.setVisibility(0);
                viewBinding.pickViewFloor.setVisibility(0);
                SelectorUtils.updateListData(dialog.roomNameList, dialog.selectRoomPosition, viewBinding.pickerViewRoom);
                SelectorUtils.updateListData(dialog.floorNameList, dialog.selectFloorPosition, viewBinding.pickViewFloor);
                dialog.getFloorManager().setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.AddSceneDialog$1$$ExternalSyntheticLambda0
                    @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
                    public final void onPicked(RecyclerView recyclerView, int i) {
                        AddSceneDialog.AnonymousClass1.lambda$convertView$0(AddSceneDialog.this, recyclerView, i);
                    }
                });
                dialog.getRoomManager().setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.AddSceneDialog$1$$ExternalSyntheticLambda1
                    @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
                    public final void onPicked(RecyclerView recyclerView, int i) {
                        AddSceneDialog.this.selectRoomPosition = i;
                    }
                });
            } else {
                viewBinding.viewDivider.setVisibility(4);
                viewBinding.pickerViewRoom.setVisibility(8);
                viewBinding.pickViewFloor.setVisibility(8);
            }
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.AddSceneDialog$1$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    AddSceneDialog.AnonymousClass1.lambda$convertView$2(DialogAddSceneBinding.this, dialog, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(AddSceneDialog addSceneDialog, RecyclerView recyclerView, int i) {
            addSceneDialog.selectFloorPosition = i;
            if (addSceneDialog.mOnSelectFloorListener != null) {
                addSceneDialog.mOnSelectFloorListener.selectFloor(addSceneDialog, i, (String) addSceneDialog.floorNameList.get(i));
            }
        }

        static /* synthetic */ void lambda$convertView$2(DialogAddSceneBinding dialogAddSceneBinding, AddSceneDialog addSceneDialog, View view) {
            int id = view.getId();
            if (id == R.id.iv_clear) {
                dialogAddSceneBinding.etName.setText("");
                return;
            }
            if (id == R.id.tv_cancel) {
                addSceneDialog.dismissDialog();
                if (addSceneDialog.mOnSaveListener != null) {
                    addSceneDialog.mOnSaveListener.cancel();
                    return;
                }
                return;
            }
            if (id != R.id.tv_save) {
                return;
            }
            if (TextUtils.isEmpty(dialogAddSceneBinding.etName.getText().toString())) {
                SmartToast.showShort(R.string.device_name_empty);
                return;
            }
            addSceneDialog.dismissDialog();
            if (addSceneDialog.mOnSaveListener != null) {
                addSceneDialog.mOnSaveListener.onSave(dialogAddSceneBinding.etName.getText().toString().trim(), addSceneDialog.isLocal, addSceneDialog.selectFloorPosition, addSceneDialog.selectRoomPosition);
            }
        }
    }

    public static AddSceneDialog asDefault() {
        return (AddSceneDialog) new AddSceneDialog().setViewConverter(new AnonymousClass1()).setGravity(80).setMargin(10).setOutCancel(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTypeSelect(final DialogAddSceneBinding viewBinding) {
        viewBinding.radioLocal.setCheck(true);
        viewBinding.radioLocal.setEnable(true);
        viewBinding.radioCloud.setEnable(true);
        viewBinding.radioLocal.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.view.dialog.AddSceneDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                AddSceneDialog.this.lambda$initTypeSelect$0(viewBinding, radioImageTextView, z);
            }
        });
        viewBinding.radioCloud.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.view.dialog.AddSceneDialog$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                AddSceneDialog.this.lambda$initTypeSelect$1(viewBinding, radioImageTextView, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTypeSelect$0(DialogAddSceneBinding dialogAddSceneBinding, RadioImageTextView radioImageTextView, boolean z) {
        this.isLocal = true;
        dialogAddSceneBinding.tvSave.setText(R.string.save);
        dialogAddSceneBinding.radioLocal.setCheck(true);
        dialogAddSceneBinding.radioCloud.setCheck(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTypeSelect$1(DialogAddSceneBinding dialogAddSceneBinding, RadioImageTextView radioImageTextView, boolean z) {
        this.isLocal = false;
        dialogAddSceneBinding.tvSave.setText(R.string.save);
        dialogAddSceneBinding.radioLocal.setCheck(false);
        dialogAddSceneBinding.radioCloud.setCheck(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRv(DialogAddSceneBinding viewBinding) {
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

    public PickerLayoutManager getRoomManager() {
        return this.mRoomManager;
    }

    public PickerLayoutManager getFloorManager() {
        return this.mFloorManager;
    }

    public AddSceneDialog setRoomList(List<String> roomList) {
        this.roomNameList.clear();
        this.roomNameList.addAll(roomList);
        return this;
    }

    public AddSceneDialog setFloorList(List<String> floorList) {
        this.floorNameList.clear();
        this.floorNameList.addAll(floorList);
        return this;
    }

    public AddSceneDialog setSelectFloorPosition(int selectFloorPosition) {
        this.selectFloorPosition = selectFloorPosition;
        return this;
    }

    public AddSceneDialog setSelectRoomPosition(int selectRoomPosition) {
        this.selectRoomPosition = selectRoomPosition;
        return this;
    }

    public AddSceneDialog setSelectRoom(boolean selectRoom) {
        this.selectRoom = selectRoom;
        return this;
    }

    public AddSceneDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public AddSceneDialog setDefaultName(String defaultName) {
        this.defaultName = defaultName;
        return this;
    }

    public AddSceneDialog setOnSaveListener(OnSaveListener mOnSaveListener) {
        this.mOnSaveListener = mOnSaveListener;
        return this;
    }

    public AddSceneDialog setOnSelectFloorListener(OnSelectFloorListener mOnSelectFloorListener) {
        this.mOnSelectFloorListener = mOnSelectFloorListener;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "add_scene_dialog";
    }
}