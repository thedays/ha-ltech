package com.ltech.smarthome.model.repo;

import android.text.TextUtils;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.WrapperResource;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.DeviceState;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.repo.GroupRepository;
import com.ltech.smarthome.model.repo.ifun.IGroup;
import com.ltech.smarthome.model.repo.ifun.IUser;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.group.ListGroupResponse;
import com.ltech.smarthome.net.response.group.UpdateGroupResponse;
import com.ltech.smarthome.singleton.KeyCreator;
import com.ltech.smarthome.singleton.RateLimiter;
import com.ltech.smarthome.utils.CodeLibraryUtil;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.ObjectBoxLiveData;
import io.objectbox.query.QueryBuilder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class GroupRepository extends BaseRepository implements IGroup {
    public GroupRepository(BoxStore boxStore, RateLimiter limiter, KeyCreator keyCreator, IUser user) {
        super(boxStore, limiter, keyCreator, user);
    }

    /* renamed from: com.ltech.smarthome.model.repo.GroupRepository$1, reason: invalid class name */
    class AnonymousClass1 extends WrapperResource<Group, ListGroupResponse> {
        final /* synthetic */ LifecycleOwner val$owner;
        final /* synthetic */ long val$placeId;

        @Override // com.ltech.smarthome.model.WrapperResource
        protected boolean shouldFetch() {
            return true;
        }

        AnonymousClass1(final long val$placeId, final LifecycleOwner val$owner) {
            this.val$placeId = val$placeId;
            this.val$owner = val$owner;
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void fetchFail() {
            GroupRepository.this.mLimiter.reset(GroupRepository.this.mKeyCreator.groupKey(this.val$placeId));
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected QueryBuilder<Group> getDbQueryBuilder() {
            return GroupRepository.this.mBoxBuilderFactory.queryGroupList(GroupRepository.this.mUser.getUserId(), this.val$placeId);
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void netCall(Observer<ListGroupResponse> observer) {
            ((ObservableSubscribeProxy) Injection.net().listGroup(this.val$placeId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.val$owner, Lifecycle.Event.ON_DESTROY)))).subscribe(observer);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.model.WrapperResource
        public void saveDataFromNet(final ListGroupResponse response) {
            BoxStore boxStore = GroupRepository.this.mBoxStore;
            final long j = this.val$placeId;
            boxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.GroupRepository$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    GroupRepository.AnonymousClass1.this.lambda$saveDataFromNet$0(response, j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$saveDataFromNet$0(ListGroupResponse listGroupResponse, long j) {
            Box boxFor = GroupRepository.this.mBoxStore.boxFor(Group.class);
            List<Group> find = getDbQueryBuilder().build().find();
            ArrayList arrayList = new ArrayList(listGroupResponse.getTotal());
            if (listGroupResponse.getTotal() > 0) {
                for (ListGroupResponse.RowsBean rowsBean : listGroupResponse.getRows()) {
                    Group group = new Group();
                    group.setUserId(GroupRepository.this.mUser.getUserId());
                    group.setGroupId(rowsBean.getGroupid());
                    group.setPlaceId(rowsBean.getPlaceid());
                    group.setGroupName(rowsBean.getGroupname());
                    group.setModuleType(rowsBean.getType());
                    group.setControlType(rowsBean.getColortype());
                    group.setGroupIndex(rowsBean.getGroupindex());
                    group.setGroupAddress(TextUtils.isEmpty(rowsBean.getIndex()) ? 0 : Integer.parseInt(rowsBean.getIndex(), 16));
                    group.setExtParam(rowsBean.getParamext());
                    group.setRoomId(rowsBean.getRoomid());
                    group.setFloorId(rowsBean.getFloorid());
                    group.setCreatetime(rowsBean.getCreatetime());
                    group.setImgindex(rowsBean.getImgindex());
                    group.setMaxkelvin(rowsBean.getMaxkelvin());
                    group.setMinkelvin(rowsBean.getMinkelvin());
                    group.setMaingroupid(rowsBean.getMaingroupid());
                    group.setSubkey(rowsBean.getSubkey());
                    group.setSubhide(rowsBean.getSubhide());
                    group.setPresetKValues(rowsBean.getPresetKValues());
                    group.setEditTime(System.currentTimeMillis());
                    group.setColorPaletteUrl(rowsBean.getColorPaletteUrl());
                    group.setReportinstruct(rowsBean.getReportinstruct());
                    group.setLeaderSup(rowsBean.getLeaderSup());
                    Floor floor = Injection.repo().home().getFloor(rowsBean.getFloorid());
                    String str = "";
                    group.setFloorName(floor != null ? floor.getFloorName() : "");
                    Room room = Injection.repo().home().getRoom(rowsBean.getRoomid());
                    if (room != null) {
                        str = room.getRoomName();
                    }
                    group.setRoomName(str);
                    if (rowsBean.getDevices() != null) {
                        ArrayList arrayList2 = new ArrayList();
                        Iterator<ListGroupResponse.RowsBean.DevicesBean> it = rowsBean.getDevices().iterator();
                        while (it.hasNext()) {
                            arrayList2.add(Long.valueOf(it.next().getDeviceid()));
                        }
                        group.setDeviceIds(arrayList2);
                    }
                    if (!group.getDeviceIds().isEmpty()) {
                        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(group.getDeviceIds().get(0).longValue());
                        if (deviceByDeviceId != null) {
                            group.setFirstDevUniAddr(deviceByDeviceId.getUnicastAddress());
                        }
                    }
                    if (group.getMaingroupid() > 0) {
                        Iterator<ListGroupResponse.RowsBean> it2 = listGroupResponse.getRows().iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                ListGroupResponse.RowsBean next = it2.next();
                                if (next.getGroupid() == group.getMaingroupid()) {
                                    group.setMainModuleType(next.getType());
                                    group.setMainControlType(next.getColortype());
                                    break;
                                }
                            }
                        }
                    }
                    GroupRepository.restoreState(find, group);
                    arrayList.add(group);
                }
            }
            GroupRepository.this.mBoxBuilderFactory.queryGroupList(GroupRepository.this.mUser.getUserId(), j, -1L, -1L).build().remove();
            boxFor.put((Collection) arrayList);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IGroup
    public Listing<Group> getGroupList(LifecycleOwner owner, long placeId) {
        return new AnonymousClass1(placeId, owner).toListing();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IGroup
    public void saveGroup(final Group group) {
        if (this.mUser == null || this.mUser.getUserId() <= 0) {
            return;
        }
        Group groupById = getGroupById(group.getId());
        if (groupById == null) {
            this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.GroupRepository$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    GroupRepository.this.lambda$saveGroup$1(group);
                }
            });
        } else if (group.getEditTime() >= groupById.getEditTime()) {
            this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.GroupRepository$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    GroupRepository.this.lambda$saveGroup$0(group);
                }
            });
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IGroup
    /* renamed from: saveGroup2Db, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public long lambda$saveGroup$1(Group group) {
        LHomeLog.e("DB", getClass(), group.getGroupName() + "==" + group.getDeviceIds().toString());
        group.setUserId(this.mUser.getUserId());
        if (group.getDeviceIds().size() > 0) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(group.getDeviceIds().get(0).longValue());
            if (deviceByDeviceId != null) {
                group.setFirstDevUniAddr(deviceByDeviceId.getUnicastAddress());
            }
        }
        return this.mBoxStore.boxFor(Group.class).put((Box) group);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IGroup
    public void sortGroup(final List<Group> groups) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.GroupRepository$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                GroupRepository.this.lambda$sortGroup$2(groups);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sortGroup$2(List list) {
        int i = 0;
        while (i < list.size()) {
            Group group = (Group) list.get(i);
            i++;
            group.setGroupIndex(i);
        }
        this.mBoxStore.boxFor(Group.class).put((Collection) list);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IGroup
    public LiveData<Group> getGroupFromDb(long id) {
        final MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.addSource(new ObjectBoxLiveData(this.mBoxBuilderFactory.queryGroup(this.mUser.getUserId(), id).build()), new androidx.lifecycle.Observer() { // from class: com.ltech.smarthome.model.repo.GroupRepository$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                GroupRepository.lambda$getGroupFromDb$3(MediatorLiveData.this, (List) obj);
            }
        });
        return mediatorLiveData;
    }

    static /* synthetic */ void lambda$getGroupFromDb$3(MediatorLiveData mediatorLiveData, List list) {
        if (list.isEmpty()) {
            return;
        }
        mediatorLiveData.setValue((Group) list.get(0));
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IGroup
    public void removeGroupFromDb(final long id) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.GroupRepository$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                GroupRepository.this.lambda$removeGroupFromDb$4(id);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeGroupFromDb$4(long j) {
        List<Group> find = this.mBoxBuilderFactory.queryGroup(this.mUser.getUserId(), j).build().find();
        if (find.isEmpty()) {
            return;
        }
        this.mBoxStore.boxFor(Group.class).remove((Collection) find);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IGroup
    public void removeSubGroupFromDb(final long placeId, final long groupId) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.GroupRepository$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                GroupRepository.this.lambda$removeSubGroupFromDb$5(placeId, groupId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeSubGroupFromDb$5(long j, long j2) {
        for (Group group : this.mBoxBuilderFactory.queryGroupList(this.mUser.getUserId(), j, -1L, -1L).build().find()) {
            if (group.getMaingroupid() == j2) {
                removeGroupFromDb(group.getId());
            }
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IGroup
    public Group getGroupById(long id) {
        List<Group> find = this.mBoxBuilderFactory.queryGroup(this.mUser.getUserId(), id).build().find();
        if (find.isEmpty()) {
            return null;
        }
        return find.get(0);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IGroup
    public Group getGroupByGroupId(long groupId) {
        List<Group> find = this.mBoxBuilderFactory.queryGroupByGroupId(this.mUser.getUserId(), groupId).build().find();
        if (find.isEmpty()) {
            return null;
        }
        return find.get(0);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IGroup
    public List<Group> getGroupsByIds(List<Long> ids) {
        new ArrayList();
        return this.mBoxBuilderFactory.queryGroupsByIds(this.mUser.getUserId(), ids).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IGroup
    public List<Group> getSubGroup(long placeId, long groupId) {
        ArrayList arrayList = new ArrayList();
        for (Group group : this.mBoxBuilderFactory.queryGroupList(this.mUser.getUserId(), placeId, -1L, -1L).build().find()) {
            if (group.getMaingroupid() == groupId) {
                arrayList.add(group);
            }
        }
        return arrayList;
    }

    static void restoreState(List<Group> groupList, Group group) {
        Iterator<Group> it = groupList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Group next = it.next();
            if (next.equals(group)) {
                group.setId(next.getId());
                if (next.getGroupState() != null) {
                    group.setGroupState(next.getGroupState());
                }
            }
        }
        if (group.getGroupState() == null) {
            group.setGroupState(new DeviceState());
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IGroup
    public List<Group> getGroupListByPlaceId(long placeId) {
        new ArrayList();
        return this.mBoxBuilderFactory.queryGroupList(this.mUser.getUserId(), placeId).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IGroup
    public List<Group> getGroupByDeviceUnicastAddress(long place, long unicastAddress) {
        new ArrayList();
        return this.mBoxBuilderFactory.queryGroupByDeviceUnicastAddress(this.mUser.getUserId(), place, unicastAddress).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IGroup
    public List<Group> queryGroupByUnicastAddress(long place, long unicastAddress) {
        new ArrayList();
        return this.mBoxBuilderFactory.queryGroupByUnicastAddress(this.mUser.getUserId(), place, unicastAddress).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IGroup
    public List<Group> getGroupListByRoomIdFromDb(long placeId, long floorId, long roomId) {
        new ArrayList();
        return this.mBoxBuilderFactory.queryGroupList(this.mUser.getUserId(), placeId, floorId, roomId).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IGroup
    public void checkCodeLibrary(final LifecycleOwner owner, long groupId) {
        ((ObservableSubscribeProxy) Injection.net().detailGroup(groupId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<ListGroupResponse.RowsBean>() { // from class: com.ltech.smarthome.model.repo.GroupRepository.2
            @Override // io.reactivex.functions.Consumer
            public void accept(ListGroupResponse.RowsBean bean) throws Exception {
                String generateLightcodeLibraryData;
                if (bean != null) {
                    if (bean.getInstruct() == null || TextUtils.isEmpty(bean.getInstruct())) {
                        int parseInt = Integer.parseInt(bean.getColortype());
                        int parseInt2 = TextUtils.isEmpty(bean.getIndex()) ? 0 : Integer.parseInt(bean.getIndex(), 16);
                        switch (parseInt) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                                generateLightcodeLibraryData = CodeLibraryUtil.generateLightcodeLibraryData(parseInt2, parseInt);
                                break;
                            case 6:
                            case 13:
                            case 15:
                            case 17:
                            default:
                                generateLightcodeLibraryData = null;
                                break;
                            case 7:
                                generateLightcodeLibraryData = CodeLibraryUtil.generateBleSwitchtCodeLibrary(parseInt2);
                                break;
                            case 8:
                            case 9:
                            case 10:
                            case 11:
                            case 18:
                                generateLightcodeLibraryData = GroupRepository.this.getSmartPanelCodeLibrary(parseInt2, parseInt);
                                break;
                            case 12:
                                generateLightcodeLibraryData = CodeLibraryUtil.generateDefaultTrigCurCodeLibrary(parseInt2, 0);
                                break;
                            case 14:
                                generateLightcodeLibraryData = CodeLibraryUtil.generateCurtainCodeLibrary(parseInt2);
                                break;
                            case 16:
                                generateLightcodeLibraryData = CodeLibraryUtil.generateDefaultTrigCurCodeLibrary(parseInt2, 3);
                                break;
                        }
                        if (generateLightcodeLibraryData != null) {
                            GroupRepository.this.updataCodeLibrary(owner, bean.getGroupid(), generateLightcodeLibraryData);
                        }
                    }
                }
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getSmartPanelCodeLibrary(int unicastAddress, int type) {
        int i;
        if (type != 18) {
            switch (type) {
                case 8:
                    i = 1;
                    break;
                case 9:
                    i = 2;
                    break;
                case 10:
                    i = 3;
                    break;
                case 11:
                    break;
                default:
                    i = 0;
                    break;
            }
            return CodeLibraryUtil.generateDefaultSwitchPanelCodeLibrary(unicastAddress, i);
        }
        i = 4;
        return CodeLibraryUtil.generateDefaultSwitchPanelCodeLibrary(unicastAddress, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updataCodeLibrary(LifecycleOwner owner, long groupid, String json) {
        ((ObservableSubscribeProxy) Injection.net().updateGroupCodeLibrary(groupid, json).subscribeOn(Schedulers.io()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<UpdateGroupResponse>(this) { // from class: com.ltech.smarthome.model.repo.GroupRepository.3
            @Override // io.reactivex.functions.Consumer
            public void accept(UpdateGroupResponse updateGroupResponse) throws Exception {
            }
        });
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IGroup
    public List<Group> getGroupListByDeviceId(long placeId, long deviceId) {
        new ArrayList();
        return this.mBoxBuilderFactory.queryGroupListByDeviceId(this.mUser.getUserId(), placeId, deviceId).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IGroup
    public List<Group> getSubGroupListFromDb(long placeId, long floorId, long roomId, long deviceId) {
        new ArrayList();
        return this.mBoxBuilderFactory.querySubGroupList(this.mUser.getUserId(), placeId, floorId, roomId, deviceId).build().find();
    }
}