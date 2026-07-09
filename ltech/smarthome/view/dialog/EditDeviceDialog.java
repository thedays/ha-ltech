package com.ltech.smarthome.view.dialog;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.PickerAdapter;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogEditDeviceBinding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.SelectorUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.EditDeviceDialog;
import com.ltech.smarthome.view.layoutmanager.PickerLayoutManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class EditDeviceDialog extends SmartDialog<DialogEditDeviceBinding> {
    BaseQuickAdapter<String, BaseViewHolder> iconAdapter;
    private String iconLabel;
    private int[] iconRes;
    private int[] iconValueRes;
    private String label;
    LinearLayoutManager linearLayoutManager;
    private boolean location;
    PickerLayoutManager mFloorManager;
    private IAction<Void> mOnLocationListener;
    private OnSaveListener mOnSaveListener;
    private OnSelectFloorListener mOnSelectFloorListener;
    PickerLayoutManager mRoomManager;
    private Role role;
    private boolean selectRoom;
    private String title;
    private List<String> roomNameList = new ArrayList();
    private List<String> floorNameList = new ArrayList();
    private int imageIndex = -1;
    private int lastImageIndex = -1;
    private int selectFloorPosition = -1;
    private int selectRoomPosition = -1;

    public interface OnSaveListener {
        void cancel();

        boolean onSave(String name, int imgIndex, int floorPos, int roomPos);
    }

    public interface OnSelectFloorListener {
        void selectFloor(EditDeviceDialog dialog, int position, String floorName);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_edit_device;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.EditDeviceDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogEditDeviceBinding, EditDeviceDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogEditDeviceBinding viewBinding, final EditDeviceDialog dialog) {
            if (dialog.title != null) {
                viewBinding.tvTitle.setText(dialog.title);
            }
            if (dialog.label != null) {
                viewBinding.tvLabel.setText(dialog.label);
            }
            if (dialog.iconLabel != null) {
                viewBinding.tvIcon.setText(dialog.iconLabel);
            }
            if (dialog.location) {
                viewBinding.ivLocation.setVisibility(0);
            }
            viewBinding.etDeviceName.setText(dialog.role.getName());
            dialog.initImageList(viewBinding);
            dialog.initRv(viewBinding);
            if (dialog.selectRoom) {
                viewBinding.viewDivider.setVisibility(0);
                viewBinding.pickerViewRoom.setVisibility(0);
                viewBinding.pickViewFloor.setVisibility(0);
                SelectorUtils.updateListData(dialog.roomNameList, dialog.selectRoomPosition, viewBinding.pickerViewRoom);
                SelectorUtils.updateListData(dialog.floorNameList, dialog.selectFloorPosition, viewBinding.pickViewFloor);
                dialog.getFloorManager().setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.EditDeviceDialog$1$$ExternalSyntheticLambda0
                    @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
                    public final void onPicked(RecyclerView recyclerView, int i) {
                        EditDeviceDialog.AnonymousClass1.lambda$convertView$0(EditDeviceDialog.this, recyclerView, i);
                    }
                });
                dialog.getRoomManager().setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.EditDeviceDialog$1$$ExternalSyntheticLambda1
                    @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
                    public final void onPicked(RecyclerView recyclerView, int i) {
                        EditDeviceDialog.this.selectRoomPosition = i;
                    }
                });
            } else {
                viewBinding.viewDivider.setVisibility(4);
                viewBinding.pickerViewRoom.setVisibility(8);
                viewBinding.pickViewFloor.setVisibility(8);
            }
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.EditDeviceDialog$1$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    EditDeviceDialog.AnonymousClass1.lambda$convertView$2(DialogEditDeviceBinding.this, dialog, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(EditDeviceDialog editDeviceDialog, RecyclerView recyclerView, int i) {
            editDeviceDialog.selectFloorPosition = i;
            if (editDeviceDialog.mOnSelectFloorListener != null) {
                editDeviceDialog.mOnSelectFloorListener.selectFloor(editDeviceDialog, i, (String) editDeviceDialog.floorNameList.get(i));
            }
        }

        static /* synthetic */ void lambda$convertView$2(DialogEditDeviceBinding dialogEditDeviceBinding, EditDeviceDialog editDeviceDialog, View view) {
            switch (view.getId()) {
                case R.id.iv_clear /* 2131296977 */:
                    dialogEditDeviceBinding.etDeviceName.setText("");
                    break;
                case R.id.iv_location /* 2131297121 */:
                    if (editDeviceDialog.mOnLocationListener != null) {
                        editDeviceDialog.mOnLocationListener.act(null);
                        break;
                    }
                    break;
                case R.id.tv_cancel /* 2131298504 */:
                    editDeviceDialog.dismissDialog();
                    if (editDeviceDialog.mOnSaveListener != null) {
                        editDeviceDialog.mOnSaveListener.cancel();
                        break;
                    }
                    break;
                case R.id.tv_save /* 2131298929 */:
                    if (TextUtils.isEmpty(dialogEditDeviceBinding.etDeviceName.getText().toString())) {
                        SmartToast.showShort(R.string.device_name_empty);
                        break;
                    } else if (editDeviceDialog.mOnSaveListener != null) {
                        if (editDeviceDialog.mOnSaveListener.onSave(dialogEditDeviceBinding.etDeviceName.getText().toString().trim(), editDeviceDialog.imageIndex >= 0 ? new int[]{1, 4, 10, 9, 8, 7, 3, 2, 16, 5, 6, 11, 15, 13, 14, 12}[editDeviceDialog.imageIndex] - 1 : editDeviceDialog.imageIndex, editDeviceDialog.selectFloorPosition, editDeviceDialog.selectRoomPosition)) {
                            editDeviceDialog.dismissDialog();
                            break;
                        }
                    } else {
                        editDeviceDialog.dismissDialog();
                        break;
                    }
                    break;
            }
        }
    }

    public static EditDeviceDialog asDefault() {
        return (EditDeviceDialog) new EditDeviceDialog().setViewConverter(new AnonymousClass1()).setGravity(80).setMargin(10).setOutCancel(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initImageList(DialogEditDeviceBinding viewBinding) {
        int lightColorType = ProductRepository.getLightColorType(this.role);
        if (ProductRepository.isDaliLightGroup(this.role)) {
            lightColorType = DaliProHelper.convertDaliType(this.role);
        }
        if (lightColorType != 0 && (lightColorType <= 5 || lightColorType == 7)) {
            viewBinding.layoutIcon.setVisibility(0);
            if (this.role instanceof Group) {
                this.iconRes = IconRepository.getLightGroupIconSelect(getContext());
                this.iconValueRes = IconRepository.getLightGroupIconValue(getContext());
                this.imageIndex = ((Group) this.role).getImgindex();
            } else {
                this.iconRes = IconRepository.getLightIconSelect(getContext());
                this.iconValueRes = IconRepository.getLightIconValue(getContext());
                this.imageIndex = ((Device) this.role).getImageIndex();
            }
            if (this.imageIndex >= 0) {
                int i = 0;
                while (true) {
                    int[] iArr = this.iconRes;
                    if (i >= iArr.length) {
                        break;
                    }
                    if (iArr[i] == this.iconValueRes[this.imageIndex]) {
                        this.imageIndex = i;
                        break;
                    }
                    i++;
                }
            }
            String[] lightIconName = NameRepository.getLightIconName(getContext());
            this.linearLayoutManager = new LinearLayoutManager(getContext(), 0, false);
            BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_select_device_icon_horizontal, Arrays.asList(lightIconName)) { // from class: com.ltech.smarthome.view.dialog.EditDeviceDialog.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, String item) {
                    helper.setImageResource(R.id.iv_icon, EditDeviceDialog.this.iconRes[helper.getAdapterPosition()]).setText(R.id.tv_name, item);
                    ImageView imageView = (ImageView) helper.getView(R.id.iv_icon);
                    imageView.setColorFilter(ContextCompat.getColor(EditDeviceDialog.this.getContext(), R.color.color_border_gray));
                    imageView.setColorFilter(helper.getAdapterPosition() == EditDeviceDialog.this.imageIndex ? 0 : ContextCompat.getColor(EditDeviceDialog.this.getContext(), R.color.color_border_gray));
                }
            };
            this.iconAdapter = baseQuickAdapter;
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.EditDeviceDialog$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                    EditDeviceDialog.this.lambda$initImageList$0(baseQuickAdapter2, view, i2);
                }
            });
            this.iconAdapter.bindToRecyclerView(viewBinding.rvIcon);
            viewBinding.rvIcon.setLayoutManager(this.linearLayoutManager);
            return;
        }
        viewBinding.layoutIcon.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initImageList$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int i2 = this.imageIndex;
        if (i2 != i) {
            this.lastImageIndex = i2;
            this.imageIndex = i;
            if (i != -1) {
                baseQuickAdapter.notifyItemChanged(i);
            }
            int i3 = this.lastImageIndex;
            if (i3 != -1) {
                baseQuickAdapter.notifyItemChanged(i3);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRv(DialogEditDeviceBinding viewBinding) {
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

    public EditDeviceDialog setRoomList(List<String> roomList) {
        this.roomNameList.clear();
        this.roomNameList.addAll(roomList);
        return this;
    }

    public EditDeviceDialog setFloorList(List<String> floorList) {
        this.floorNameList.clear();
        this.floorNameList.addAll(floorList);
        return this;
    }

    public EditDeviceDialog setSelectFloorPosition(int selectFloorPosition) {
        this.selectFloorPosition = selectFloorPosition;
        return this;
    }

    public EditDeviceDialog setSelectRoomPosition(int selectRoomPosition) {
        this.selectRoomPosition = selectRoomPosition;
        return this;
    }

    public EditDeviceDialog setSelectRoom(boolean selectRoom) {
        this.selectRoom = selectRoom;
        return this;
    }

    public EditDeviceDialog setLocation(boolean location) {
        this.location = location;
        return this;
    }

    public EditDeviceDialog setRole(Role role) {
        this.role = role;
        return this;
    }

    public EditDeviceDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public EditDeviceDialog setLabel(String label) {
        this.label = label;
        return this;
    }

    public EditDeviceDialog setIconLabel(String label) {
        this.iconLabel = label;
        return this;
    }

    public EditDeviceDialog setOnSaveListener(OnSaveListener mOnSaveListener) {
        this.mOnSaveListener = mOnSaveListener;
        return this;
    }

    public EditDeviceDialog setOnSelectFloorListener(OnSelectFloorListener mOnSelectFloorListener) {
        this.mOnSelectFloorListener = mOnSelectFloorListener;
        return this;
    }

    public EditDeviceDialog setOnLocationListener(IAction<Void> mOnLocationListener) {
        this.mOnLocationListener = mOnLocationListener;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "edit_device_dialog";
    }
}