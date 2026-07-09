package com.ltech.smarthome.ui.device.gqpro;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.photo.ListPhotoResponse;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.SelectGqThemeDialog;
import com.smart.message.ResponseMsg;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActGqProVM extends BaseViewModel {
    public long controlId;
    public Device device;
    public boolean isEdit;
    private boolean isSelectedAll;
    public Listing<SuperPanelInfo> request;
    public int selPos;
    public SuperPanelInfo superPanelInfo;
    public MediatorLiveData<List<Role>> roleList = new MediatorLiveData<>();
    public SingleLiveEvent<Object> controlObject = new SingleLiveEvent<>();
    public MutableLiveData<List<ActionItem>> actions = new MutableLiveData<>();
    public MutableLiveData<Integer> deviceNum = new SingleLiveEvent();
    public MutableLiveData<Integer> sceneNum = new SingleLiveEvent();
    public MutableLiveData<Integer> picNum = new SingleLiveEvent();
    public MutableLiveData<List<ThemeItem>> themes = new MutableLiveData<>();
    public int maxCustomPic = 5;
    public List<ListPhotoResponse.RowsBean> photoList = new ArrayList();
    public MutableLiveData<Boolean> showCancelBtnEvent = new MutableLiveData<>();
    private int[] color = {R.drawable.shape_ovl_2b77ff, R.drawable.shape_ovl_01dd69, R.drawable.shape_ovl_ffbb00, R.drawable.shape_ovl_7b17f6, R.drawable.shape_ovl_595959, R.drawable.shape_ovl_fd6f1f, R.drawable.shape_ovl_ff2e74};

    public void initData() {
        Device deviceById = Injection.repo().device().getDeviceById(this.controlId);
        this.device = deviceById;
        this.controlObject.setValue(deviceById);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ActionItem(getContext().getString(R.string.app_str_personalized_setting), getContext().getString(R.string.app_str_has_no_upload), 0));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new ActionItem(getContext().getString(R.string.device), getContext().getString(R.string.str_not_add), 0));
        arrayList2.add(new ActionItem(getContext().getString(R.string.app_scene), getContext().getString(R.string.str_not_add), 0));
        arrayList.add(new ActionItem(getContext().getString(R.string.app_str_sync_device) + "/" + getContext().getString(R.string.app_scene), arrayList2, 1));
        this.actions.setValue(arrayList);
        getPhotoList();
    }

    private void getPhotoList() {
        if (this.device != null) {
            ((ObservableSubscribeProxy) Injection.net().listPhoto(this.device.getDeviceId(), this.device.getMaccode()).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProVM$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActGqProVM.this.lambda$getPhotoList$0((ListPhotoResponse) obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getPhotoList$0(ListPhotoResponse listPhotoResponse) throws Exception {
        this.photoList.clear();
        this.photoList.addAll(listPhotoResponse.getRows());
        this.picNum.setValue(Integer.valueOf(listPhotoResponse.getRows().size()));
    }

    public void initThemeData() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        arrayList.add(new TitleThemeItem(getContext().getString(R.string.app_str_system_theme), "", 0));
        arrayList.add(new DefaultThemeItem(getContext().getString(R.string.app_str_system_theme_4), LanguageUtils.isChinese(ActivityUtils.getTopActivity()) ? R.mipmap.gq_display_pic4 : R.mipmap.gq_display_pic4_en, LanguageUtils.isChinese(ActivityUtils.getTopActivity()) ? R.mipmap.gq_display_pic4_big : R.mipmap.gq_display_pic4_big_en, 1, 0));
        arrayList.add(new DefaultThemeItem(getContext().getString(R.string.app_str_system_theme_2), R.mipmap.gq_display_pic2, R.mipmap.gq_display_pic2_big, 2, 1));
        arrayList.add(new DefaultThemeItem(getContext().getString(R.string.app_str_system_theme_1), LanguageUtils.isChinese(ActivityUtils.getTopActivity()) ? R.mipmap.gq_display_pic1 : R.mipmap.gq_display_pic1_en, LanguageUtils.isChinese(ActivityUtils.getTopActivity()) ? R.mipmap.gq_display_pic1_big : R.mipmap.gq_display_pic1_big_en, 3, 2));
        arrayList.add(new DefaultThemeItem(getContext().getString(R.string.app_str_system_theme_5), R.mipmap.gq_display_pic5, R.mipmap.gq_display_pic5_big, 4, 3));
        arrayList.add(new DefaultThemeItem(getContext().getString(R.string.app_str_system_theme_3), LanguageUtils.isChinese(ActivityUtils.getTopActivity()) ? R.mipmap.gq_display_pic3 : R.mipmap.gq_display_pic3_en, LanguageUtils.isChinese(ActivityUtils.getTopActivity()) ? R.mipmap.gq_display_pic3_big : R.mipmap.gq_display_pic3_big_en, 5, 4));
        arrayList.add(new DefaultThemeItem(getContext().getString(R.string.app_str_system_theme_6), R.drawable.shape_circle_black, R.drawable.shape_circle_black, 6, 5));
        if (this.photoList == null) {
            this.photoList = new ArrayList();
        }
        boolean z = this.photoList.size() < 5;
        if (!this.photoList.isEmpty()) {
            arrayList.add(new TitleThemeItem(getContext().getString(R.string.app_str_custom_system_pic), getContext().getString(R.string.delete), 1));
            int i2 = 6;
            for (ListPhotoResponse.RowsBean rowsBean : this.photoList) {
                arrayList.add(new CustomThemeItem("", rowsBean.getUrl(), i + 33, rowsBean.getPictureid(), i2));
                this.maxCustomPic--;
                i2++;
                i++;
            }
        } else {
            arrayList.add(new TitleThemeItem(getContext().getString(R.string.app_str_custom_system_pic), "", 1));
        }
        if (z) {
            arrayList.add(new AddThemeItem());
        }
        Collections.sort(arrayList, new Comparator<ThemeItem>(this) { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProVM.1
            @Override // java.util.Comparator
            public int compare(ThemeItem o1, ThemeItem o2) {
                if ((o1 instanceof DefaultThemeItem) && (o2 instanceof DefaultThemeItem)) {
                    return o1.getSort() - o2.getSort();
                }
                return 0;
            }
        });
        this.themes.setValue(arrayList);
    }

    public void edit(boolean b2) {
        List<ThemeItem> value = this.themes.getValue();
        if (value != null) {
            this.isEdit = b2;
            int i = 0;
            for (ThemeItem themeItem : value) {
                themeItem.setNeedDel(false);
                if (themeItem.canDel) {
                    themeItem.setEdit(b2);
                }
                if (b2 && themeItem.getItemType() == 2) {
                    value.remove(themeItem);
                }
                if (themeItem.isCustom && !b2) {
                    i++;
                }
                if (themeItem.getItemType() == 0 && themeItem.getNum() == 1) {
                    if (b2) {
                        themeItem.setTitle(String.format(getContext().getString(R.string.app_str_custom_system_theme_has_choose), 0));
                        themeItem.setEditName(getContext().getString(R.string.app_str_select_all));
                    } else {
                        themeItem.setTitle(getContext().getString(R.string.app_str_custom_system_pic));
                        themeItem.setEditName(getContext().getString(R.string.delete));
                    }
                }
            }
            if (!b2 && i < 5) {
                value.add(new AddThemeItem());
            }
            this.themes.setValue(value);
        }
    }

    public void selectedAll() {
        List<ThemeItem> value = this.themes.getValue();
        if (value != null) {
            this.isSelectedAll = !this.isSelectedAll;
            TitleThemeItem titleThemeItem = null;
            int i = 0;
            for (int i2 = 0; i2 < value.size(); i2++) {
                ThemeItem themeItem = value.get(i2);
                if (themeItem.getItemType() == 0 && themeItem.getNum() == 1) {
                    titleThemeItem = (TitleThemeItem) themeItem;
                }
                if (themeItem.isCanDel()) {
                    themeItem.setNeedDel(this.isSelectedAll);
                    if (themeItem.isNeedDel()) {
                        i++;
                    }
                }
            }
            if (titleThemeItem != null) {
                this.showCancelBtnEvent.setValue(Boolean.valueOf(!this.isSelectedAll));
                titleThemeItem.setTitle(String.format(getContext().getString(R.string.app_str_custom_system_theme_has_choose), Integer.valueOf(i)));
                titleThemeItem.setEditName(this.isSelectedAll ? getContext().getString(R.string.app_str_cancel_select_all) : getContext().getString(R.string.app_str_select_all));
            }
            this.themes.setValue(value);
        }
    }

    public void selected(int pos) {
        List<ThemeItem> value = this.themes.getValue();
        if (value != null) {
            TitleThemeItem titleThemeItem = null;
            int i = 0;
            for (int i2 = 0; i2 < value.size(); i2++) {
                ThemeItem themeItem = value.get(i2);
                if (!this.isEdit) {
                    themeItem.setSel(false);
                }
                if (this.isEdit && themeItem.getItemType() == 0 && themeItem.getNum() == 1) {
                    titleThemeItem = (TitleThemeItem) themeItem;
                }
                if (themeItem.getNum() == pos) {
                    if (this.isEdit) {
                        if (themeItem.canDel) {
                            themeItem.setNeedDel(true ^ themeItem.isNeedDel());
                        }
                    } else {
                        themeItem.setSel(true);
                    }
                }
                if (this.isEdit && themeItem.canDel && themeItem.isNeedDel()) {
                    i++;
                }
            }
            if (this.isEdit && titleThemeItem != null) {
                this.isSelectedAll = i == 5;
                this.showCancelBtnEvent.setValue(Boolean.valueOf(i == 0));
                titleThemeItem.setTitle(String.format(getContext().getString(R.string.app_str_custom_system_theme_has_choose), Integer.valueOf(i)));
                titleThemeItem.setEditName(this.isSelectedAll ? getContext().getString(R.string.app_str_cancel_select_all) : getContext().getString(R.string.app_str_select_all));
            }
            this.themes.setValue(value);
            showLoadingDialog();
            CmdAssistant.getDeviceAssistant(this.controlObject.getValue(), new int[0]).setSmartPanelScreenTheme(getContext(), 1, pos, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProVM.2
                @Override // com.ltech.smarthome.base.IAction
                public void act(ResponseMsg msg) {
                    ActGqProVM.this.dismissLoadingDialog();
                    if (msg != null && msg.getStateCode() == 38) {
                        ActGqProVM actGqProVM = ActGqProVM.this;
                        actGqProVM.showErrorTipDialog(actGqProVM.getContext().getString(R.string.app_theme_no_found));
                    } else if (msg == null || msg.getStateCode() != 0) {
                        ActGqProVM actGqProVM2 = ActGqProVM.this;
                        actGqProVM2.showErrorTipDialog(actGqProVM2.getContext().getString(R.string.set_fail));
                    }
                }
            });
        }
    }

    public void delete() {
        List<ThemeItem> value = this.themes.getValue();
        if (value != null) {
            final ArrayList arrayList = new ArrayList();
            TitleThemeItem titleThemeItem = null;
            for (int size = value.size() - 1; size >= 0; size--) {
                ThemeItem themeItem = value.get(size);
                if (themeItem.getItemType() == 0 && themeItem.getNum() == 1) {
                    titleThemeItem = (TitleThemeItem) themeItem;
                }
                if (themeItem.isNeedDel()) {
                    this.maxCustomPic++;
                    arrayList.add(Long.valueOf(themeItem.getId()));
                    value.remove(themeItem);
                }
            }
            this.showCancelBtnEvent.setValue(true);
            if (titleThemeItem != null) {
                titleThemeItem.setTitle(String.format(getContext().getString(R.string.app_str_custom_system_theme_has_choose), 0));
            }
            this.themes.setValue(value);
            ((ObservableSubscribeProxy) Injection.net().deletePhoto(arrayList).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProVM$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActGqProVM.this.lambda$delete$1(arrayList, obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delete$1(List list, Object obj) throws Exception {
        for (int size = this.photoList.size() - 1; size >= 0; size--) {
            if (list.contains(Long.valueOf(this.photoList.get(size).getPictureid()))) {
                this.photoList.remove(size);
            }
        }
    }

    public List<SelectGqThemeDialog.Item> getPreviewData() {
        ArrayList arrayList = new ArrayList();
        List<ThemeItem> value = this.themes.getValue();
        if (value != null) {
            for (ThemeItem themeItem : value) {
                if (themeItem.getItemType() == 1) {
                    arrayList.add(themeItem.isCustom ? new SelectGqThemeDialog.Item(themeItem.getUrl()) : new SelectGqThemeDialog.Item(themeItem.bigImg));
                }
            }
        }
        return arrayList;
    }

    public void refreshTheme() {
        ((ObservableSubscribeProxy) Injection.net().listPhoto(this.device.getDeviceId(), this.device.getMaccode()).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGqProVM.this.lambda$refreshTheme$2((ListPhotoResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshTheme$2(ListPhotoResponse listPhotoResponse) throws Exception {
        this.photoList.clear();
        this.photoList.addAll(listPhotoResponse.getRows());
        initThemeData();
    }

    public Role getRoleByRoleId(long roleId, int objectType) {
        if (objectType == 1) {
            return Injection.repo().device().getDeviceByDeviceId(roleId);
        }
        if (objectType == 2) {
            return Injection.repo().group().getGroupByGroupId(roleId);
        }
        if (objectType == 3) {
            return Injection.repo().scene().getSceneBySceneId(roleId);
        }
        return null;
    }

    public Role getRoleBySceneId(long roleId) {
        return Injection.repo().scene().getSceneBySceneId(roleId);
    }

    public boolean filterRole(Role role) {
        Device device;
        if (role instanceof Device) {
            device = (Device) role;
            String productId = device.getProductId();
            productId.hashCode();
            switch (productId) {
                case "122041818260301":
                case "122041818283501":
                case "122041818304701":
                case "121120911474901":
                case "3683369128495808":
                case "4249823578721536":
                case "3701704216101056":
                case "221042516351701":
                case "123072510445601":
                case "121061709483801":
                case "221030816330401":
                case "3701703750123712":
                case "121042516340801":
                case "121042516345401":
                    return device.getParam() == null || device.getParam(BleParam.class) == null || ((BleParam) device.getParam(BleParam.class)).getGroupId() == 0;
                default:
                    return true;
            }
        }
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x01bb, code lost:
    
        if (((com.ltech.smarthome.model.device_param.DryContactBleParam) r3.getParam(com.ltech.smarthome.model.device_param.DryContactBleParam.class)).getSubType() == 3) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00c2, code lost:
    
        if (r4.equals(com.ltech.smarthome.model.product.ProductId.ID_SMART_PANEL_G4) == false) goto L27;
     */
    /* JADX WARN: Removed duplicated region for block: B:28:0x01c6 A[FALL_THROUGH] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List<com.ltech.smarthome.ui.device.gqpro.GqProBannerAdapter.Data> covertBannerData(java.util.List<com.ltech.smarthome.model.bean.Role> r20) {
        /*
            Method dump skipped, instructions count: 670
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.gqpro.ActGqProVM.covertBannerData(java.util.List):java.util.List");
    }

    public static class ActionItem {
        private List<ActionItem> list;
        private String sub;
        private String title;
        private int type;

        public ActionItem(String title, String sub, int type) {
            this.title = title;
            this.sub = sub;
            this.type = type;
        }

        public ActionItem(String title, List<ActionItem> list, int type) {
            this.title = title;
            this.type = type;
            this.list = list;
        }

        public List<ActionItem> getList() {
            return this.list;
        }

        public void setList(List<ActionItem> list) {
            this.list = list;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSub() {
            return this.sub;
        }

        public void setSub(String sub) {
            this.sub = sub;
        }
    }

    public static class ThemeItem {
        public static final int ITEM_TYPE_ADD = 2;
        public static final int ITEM_TYPE_ITEM = 1;
        public static final int ITEM_TYPE_TITLE = 0;
        private int bigImg;
        private boolean canDel;
        private boolean edit;
        private String editName;
        private long id;
        private int img;
        private boolean isCustom;
        private int itemType;
        private boolean needDel;
        private int num;
        private boolean sel;
        private int sort;
        private String title;
        private String url;

        public int getSort() {
            return this.sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public long getId() {
            return this.id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getBigImg() {
            return this.bigImg;
        }

        public void setBigImg(int bigImg) {
            this.bigImg = bigImg;
        }

        public boolean isEdit() {
            return this.edit;
        }

        public void setEdit(boolean edit) {
            this.edit = edit;
        }

        public boolean isNeedDel() {
            return this.needDel;
        }

        public void setNeedDel(boolean needDel) {
            this.needDel = needDel;
        }

        public String getEditName() {
            return this.editName;
        }

        public void setEditName(String editName) {
            this.editName = editName;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getImg() {
            return this.img;
        }

        public void setImg(int img) {
            this.img = img;
        }

        public boolean isCanDel() {
            return this.canDel;
        }

        public void setCanDel(boolean canDel) {
            this.canDel = canDel;
        }

        public boolean isCustom() {
            return this.isCustom;
        }

        public void setCustom(boolean custom) {
            this.isCustom = custom;
        }

        public boolean isSel() {
            return this.sel;
        }

        public void setSel(boolean sel) {
            this.sel = sel;
        }

        public int getNum() {
            return this.num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getItemType() {
            return this.itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }
    }

    public static class DefaultThemeItem extends ThemeItem {
        public DefaultThemeItem(String title, int img, int bigImg, int num, int sort) {
            setTitle(title);
            setImg(img);
            setBigImg(bigImg);
            setNum(num);
            setCustom(false);
            setCanDel(false);
            setSort(sort);
            setItemType(1);
        }
    }

    public static class AddThemeItem extends ThemeItem {
        public AddThemeItem() {
            setItemType(2);
        }
    }

    public static class CustomThemeItem extends ThemeItem {
        public CustomThemeItem(String title, String url, int num, long id, int sort) {
            setTitle(title);
            setUrl(url);
            setNum(num);
            setCustom(true);
            setCanDel(true);
            setSort(sort);
            setItemType(1);
            setId(id);
        }
    }

    public static class TitleThemeItem extends ThemeItem {
        public TitleThemeItem(String title, String edit, int num) {
            setNum(num);
            setTitle(title);
            setEditName(edit);
            setItemType(0);
        }
    }
}