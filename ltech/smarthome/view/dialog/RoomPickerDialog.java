package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.core.content.ContextCompat;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogRoomPickerBinding;
import com.ltech.smarthome.view.MyTimePickerView;
import com.ltech.smarthome.view.dialog.RoomPickerDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class RoomPickerDialog extends SmartDialog<DialogRoomPickerBinding> {
    private SelectListener mSelectListener;
    private List<String> roomNameList = new ArrayList();
    private List<String> floorNameList = new ArrayList();
    private int selectFloorPosition = -1;
    private int selectRoomPosition = -1;

    public interface SelectListener {
        void confirm(int floorPosition, int roomPosition);

        void selectFloor(RoomPickerDialog dialog, int position, String floorName);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_room_picker;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.RoomPickerDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogRoomPickerBinding, RoomPickerDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogRoomPickerBinding viewBinding, final RoomPickerDialog dialog) {
            viewBinding.pickerViewRoom.setData(dialog.roomNameList);
            viewBinding.pickerViewRoom.setSelectedPosition(dialog.selectRoomPosition);
            viewBinding.pickViewFloor.setData(dialog.floorNameList);
            viewBinding.pickViewFloor.setSelectedPosition(dialog.selectFloorPosition);
            viewBinding.pickerViewRoom.setMainTextColor(ContextCompat.getColor(dialog.getContext(), R.color.color_text_black));
            viewBinding.pickViewFloor.setMainTextColor(ContextCompat.getColor(dialog.getContext(), R.color.color_text_black));
            viewBinding.pickViewFloor.setOnSelectListener(new MyTimePickerView.onSelectListener() { // from class: com.ltech.smarthome.view.dialog.RoomPickerDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.MyTimePickerView.onSelectListener
                public final void onSelect(int i, String str) {
                    RoomPickerDialog.AnonymousClass1.lambda$convertView$0(RoomPickerDialog.this, i, str);
                }
            });
            viewBinding.pickerViewRoom.setOnSelectListener(new MyTimePickerView.onSelectListener() { // from class: com.ltech.smarthome.view.dialog.RoomPickerDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.view.MyTimePickerView.onSelectListener
                public final void onSelect(int i, String str) {
                    RoomPickerDialog.this.selectRoomPosition = i;
                }
            });
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.RoomPickerDialog$1$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    RoomPickerDialog.AnonymousClass1.lambda$convertView$2(RoomPickerDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(RoomPickerDialog roomPickerDialog, int i, String str) {
            roomPickerDialog.selectFloorPosition = i;
            if (roomPickerDialog.mSelectListener != null) {
                roomPickerDialog.mSelectListener.selectFloor(roomPickerDialog, i, str);
            }
        }

        static /* synthetic */ void lambda$convertView$2(RoomPickerDialog roomPickerDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                roomPickerDialog.dismissDialog();
            } else {
                if (id != R.id.tv_finish) {
                    return;
                }
                if (roomPickerDialog.mSelectListener != null) {
                    roomPickerDialog.mSelectListener.confirm(roomPickerDialog.selectFloorPosition, roomPickerDialog.selectRoomPosition);
                }
                roomPickerDialog.dismissDialog();
            }
        }
    }

    public static RoomPickerDialog asDefault() {
        return (RoomPickerDialog) new RoomPickerDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    public RoomPickerDialog setRoomList(List<String> roomList) {
        this.roomNameList.clear();
        this.roomNameList.addAll(roomList);
        return this;
    }

    public RoomPickerDialog setFloorList(List<String> floorList) {
        this.floorNameList.clear();
        this.floorNameList.addAll(floorList);
        return this;
    }

    public RoomPickerDialog setSelectFloorPosition(int selectFloorPosition) {
        this.selectFloorPosition = selectFloorPosition;
        return this;
    }

    public RoomPickerDialog setSelectRoomPosition(int selectRoomPosition) {
        this.selectRoomPosition = selectRoomPosition;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "room_picker_dialog";
    }

    public RoomPickerDialog setSelectListener(SelectListener selectListener) {
        this.mSelectListener = selectListener;
        return this;
    }
}