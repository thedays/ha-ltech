package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.PickerAdapter;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogRoomSelectorBinding;
import com.ltech.smarthome.view.dialog.RoomSelectDialog;
import com.ltech.smarthome.view.layoutmanager.PickerLayoutManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class RoomSelectDialog extends SmartDialog<DialogRoomSelectorBinding> {
    PickerLayoutManager mFloorManager;
    PickerLayoutManager mRoomManager;
    private SelectListener mSelectListener;
    private String saveText;
    private String title;
    private List<String> roomNameList = new ArrayList();
    private List<String> floorNameList = new ArrayList();
    private int selectFloorPosition = -1;
    private int selectRoomPosition = -1;

    public interface SelectListener {
        void confirm(int floorPosition, int roomPosition);

        void onFloorSelect(RoomSelectDialog dialog, int floorPosition);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_room_selector;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }

    /* renamed from: com.ltech.smarthome.view.dialog.RoomSelectDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogRoomSelectorBinding, RoomSelectDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogRoomSelectorBinding viewBinding, final RoomSelectDialog dialog) {
            dialog.initRv(viewBinding);
            viewBinding.pickerViewRoom.setVisibility(0);
            RoomSelectDialog.updateListData(dialog.roomNameList, dialog.selectRoomPosition, viewBinding.pickerViewRoom);
            viewBinding.pickViewFloor.setVisibility(0);
            RoomSelectDialog.updateListData(dialog.floorNameList, dialog.selectFloorPosition, viewBinding.pickViewFloor);
            dialog.getFloorManager().setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.RoomSelectDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
                public final void onPicked(RecyclerView recyclerView, int i) {
                    RoomSelectDialog.AnonymousClass1.lambda$convertView$0(RoomSelectDialog.this, recyclerView, i);
                }
            });
            if (dialog.title != null) {
                viewBinding.tvTitle.setText(dialog.title);
            }
            if (dialog.saveText != null) {
                viewBinding.tvFinish.setText(dialog.saveText);
            }
            dialog.getRoomManager().setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.RoomSelectDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
                public final void onPicked(RecyclerView recyclerView, int i) {
                    RoomSelectDialog.this.selectRoomPosition = i;
                }
            });
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.RoomSelectDialog$1$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    RoomSelectDialog.AnonymousClass1.lambda$convertView$2(RoomSelectDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(RoomSelectDialog roomSelectDialog, RecyclerView recyclerView, int i) {
            roomSelectDialog.selectFloorPosition = i;
            if (roomSelectDialog.mSelectListener != null) {
                roomSelectDialog.mSelectListener.onFloorSelect(roomSelectDialog, i);
            }
        }

        static /* synthetic */ void lambda$convertView$2(RoomSelectDialog roomSelectDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                roomSelectDialog.dismissDialog();
            } else {
                if (id != R.id.tv_finish) {
                    return;
                }
                if (roomSelectDialog.mSelectListener != null) {
                    roomSelectDialog.mSelectListener.confirm(roomSelectDialog.getFloorManager().getPickedPosition(), roomSelectDialog.getRoomManager().getPickedPosition());
                }
                roomSelectDialog.dismissDialog();
            }
        }
    }

    public static RoomSelectDialog asDefault() {
        return (RoomSelectDialog) new RoomSelectDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    public PickerLayoutManager getRoomManager() {
        return this.mRoomManager;
    }

    public void setRoomManager(PickerLayoutManager mRoomManager) {
        this.mRoomManager = mRoomManager;
    }

    public PickerLayoutManager getFloorManager() {
        return this.mFloorManager;
    }

    public void setFloorManager(PickerLayoutManager mFloorManager) {
        this.mFloorManager = mFloorManager;
    }

    public RoomSelectDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public RoomSelectDialog setRoomList(List<String> roomList) {
        this.roomNameList.clear();
        this.roomNameList.addAll(roomList);
        return this;
    }

    public RoomSelectDialog setSaveText(String saveText) {
        this.saveText = saveText;
        return this;
    }

    public RoomSelectDialog setFloorList(List<String> floorList) {
        this.floorNameList.clear();
        this.floorNameList.addAll(floorList);
        return this;
    }

    public RoomSelectDialog setSelectFloorPosition(int selectFloorPosition) {
        this.selectFloorPosition = selectFloorPosition;
        return this;
    }

    public RoomSelectDialog setSelectRoomPosition(int selectRoomPosition) {
        this.selectRoomPosition = selectRoomPosition;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateListData(List<String> dataList, int selectPosition, RecyclerView p) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < dataList.size(); i++) {
            arrayList.add(new SelectItem(i, dataList.get(i)));
        }
        int i2 = 0;
        for (int i3 = 0; i3 < dataList.size(); i3++) {
            if (((SelectItem) arrayList.get(i3)).position == selectPosition) {
                i2 = i3;
            }
        }
        p.scrollToPosition(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRv(DialogRoomSelectorBinding viewBinding) {
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

    public RoomSelectDialog setSelectListener(SelectListener selectListener) {
        this.mSelectListener = selectListener;
        return this;
    }

    private static final class SelectItem {
        String itemString;
        int position;

        public SelectItem(int position, String itemString) {
            this.position = position;
            this.itemString = itemString;
        }
    }
}