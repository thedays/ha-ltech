package com.ltech.smarthome.model.repo;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.ltech.nfc.source.SourceHelper;
import com.ltech.nfc.utils.JsonHelper;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.McuVersion;
import com.ltech.smarthome.model.repo.ifun.IMcu;
import com.ltech.smarthome.model.repo.ifun.IUser;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.mcu.ListMcuResponse;
import com.ltech.smarthome.singleton.KeyCreator;
import com.ltech.smarthome.singleton.RateLimiter;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.DownLoadFileRunnable;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.ThreadPoolManager;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class McuRepository extends BaseRepository implements IMcu {
    public McuRepository(BoxStore boxStore, RateLimiter limiter, KeyCreator keyCreator, IUser user) {
        super(boxStore, limiter, keyCreator, user);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IMcu
    public void getMcuListFromNet(LifecycleOwner owner, int versionCode) {
        ((ObservableSubscribeProxy) Injection.net().getMcuList(versionCode).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.model.repo.McuRepository$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                McuRepository.this.lambda$getMcuListFromNet$1((ListMcuResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getMcuListFromNet$1(ListMcuResponse listMcuResponse) throws Exception {
        int i;
        Box boxFor = this.mBoxStore.boxFor(McuVersion.class);
        List<McuVersion> find = boxFor.query().build().find();
        if (listMcuResponse.getTotal() > 0) {
            ArrayList arrayList = new ArrayList(listMcuResponse.getTotal());
            Iterator<ListMcuResponse.RowsBean> it = listMcuResponse.getRows().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ListMcuResponse.RowsBean next = it.next();
                if (!Constants.NFC_IOS.equals(next.getFirmwaretypecode())) {
                    McuVersion mcuVersion = new McuVersion();
                    mcuVersion.setDeviceversionid(next.getDeviceversionid());
                    mcuVersion.setProductid(next.getProductid());
                    mcuVersion.setVersionname(next.getVersionname());
                    mcuVersion.setVersionnumber(next.getVersionnumber());
                    mcuVersion.setFiledir(next.getFiledir());
                    mcuVersion.setFilename(next.getFilename());
                    mcuVersion.setUrl(next.getUrl());
                    mcuVersion.setEnable(next.getEnable());
                    mcuVersion.setCreatetime(next.getCreatetime());
                    mcuVersion.setBegincreatetime(next.getBegincreatetime());
                    mcuVersion.setEndcreatetime(next.getEndcreatetime());
                    mcuVersion.setType(next.getType());
                    mcuVersion.setPagesize(next.getPagesize());
                    mcuVersion.setPagenum(next.getPagenum());
                    mcuVersion.setProductname(next.getProductname());
                    mcuVersion.setRemark(next.getRemark());
                    mcuVersion.setFirmwaretypecode(next.getFirmwaretypecode());
                    mcuVersion.setStarttime(next.getStarttime());
                    mcuVersion.setEndcreatetime(next.getEndtime());
                    mcuVersion.setTimetype(next.getTimetype());
                    mcuVersion.setStarthour(next.getStarthour());
                    mcuVersion.setEndhour(next.getEndhour());
                    mcuVersion.setStatus(next.getStatus());
                    mcuVersion.setMeshUpdate(next.getMeshupdate() == 1);
                    arrayList.add(mcuVersion);
                }
            }
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                for (McuVersion mcuVersion2 : find) {
                    if (((McuVersion) arrayList.get(i2)).equals(mcuVersion2)) {
                        mcuVersion2.setMeshUpdate(((McuVersion) arrayList.get(i2)).isMeshUpdate());
                        arrayList.set(i2, mcuVersion2);
                    }
                }
            }
            boxFor.remove((Collection) find);
            boxFor.put((Collection) arrayList);
            find.removeAll(arrayList);
            for (i = 0; i < arrayList.size(); i++) {
                final McuVersion mcuVersion3 = (McuVersion) arrayList.get(i);
                LHomeLog.i(getClass(), "mcu version:" + mcuVersion3.getUrl());
                if (mcuVersion3.getFirmwareData() != null) {
                    LHomeLog.i(getClass(), "mcu type:" + mcuVersion3.getFirmwaretypecode() + " " + mcuVersion3.getFirmwareData().length);
                    if (Constants.NFC_ANDROID.equals(mcuVersion3.getFirmwaretypecode())) {
                        SourceHelper.sourceModelList = JsonHelper.getSourceList(mcuVersion3.getFirmwareData());
                    }
                } else {
                    ThreadPoolManager.getInstance().execute(new DownLoadFileRunnable(mcuVersion3.getUrl(), new DownLoadFileRunnable.DownloadCallback() { // from class: com.ltech.smarthome.model.repo.McuRepository$$ExternalSyntheticLambda1
                        @Override // com.ltech.smarthome.utils.DownLoadFileRunnable.DownloadCallback
                        public final void onSuccess(byte[] bArr) {
                            McuRepository.lambda$getMcuListFromNet$0(McuVersion.this, bArr);
                        }
                    }));
                }
            }
        }
    }

    static /* synthetic */ void lambda$getMcuListFromNet$0(McuVersion mcuVersion, byte[] bArr) {
        mcuVersion.setFirmwareData(bArr);
        if (Constants.NFC_ANDROID.equals(mcuVersion.getFirmwaretypecode())) {
            SourceHelper.sourceModelList = JsonHelper.getSourceList(bArr);
        }
        Injection.repo().mcu().saveMcu(mcuVersion);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IMcu
    public void saveMcu(final McuVersion... mcuVersions) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.McuRepository$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                McuRepository.this.lambda$saveMcu$2(mcuVersions);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveMcu$2(McuVersion[] mcuVersionArr) {
        this.mBoxStore.boxFor(McuVersion.class).put((Object[]) mcuVersionArr);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IMcu
    public McuVersion getDeviceVersion(String deviceModel) {
        return this.mBoxBuilderFactory.queryMcuByType(deviceModel).build().findFirst();
    }
}