package com.ltech.smarthome.model.bean;

import com.github.mikephil.charting.utils.Utils;
import com.ltech.smarthome.model.bean.McuVersion_;
import io.objectbox.BoxStore;
import io.objectbox.Cursor;
import io.objectbox.Transaction;
import io.objectbox.internal.CursorFactory;

/* loaded from: classes4.dex */
public final class McuVersionCursor extends Cursor<McuVersion> {
    private static final McuVersion_.McuVersionIdGetter ID_GETTER = McuVersion_.__ID_GETTER;
    private static final int __ID_deviceversionid = McuVersion_.deviceversionid.id;
    private static final int __ID_productid = McuVersion_.productid.id;
    private static final int __ID_versionname = McuVersion_.versionname.id;
    private static final int __ID_versionnumber = McuVersion_.versionnumber.id;
    private static final int __ID_filedir = McuVersion_.filedir.id;
    private static final int __ID_filename = McuVersion_.filename.id;
    private static final int __ID_url = McuVersion_.url.id;
    private static final int __ID_enable = McuVersion_.enable.id;
    private static final int __ID_createtime = McuVersion_.createtime.id;
    private static final int __ID_begincreatetime = McuVersion_.begincreatetime.id;
    private static final int __ID_endcreatetime = McuVersion_.endcreatetime.id;
    private static final int __ID_type = McuVersion_.type.id;
    private static final int __ID_pagesize = McuVersion_.pagesize.id;
    private static final int __ID_pagenum = McuVersion_.pagenum.id;
    private static final int __ID_productname = McuVersion_.productname.id;
    private static final int __ID_remark = McuVersion_.remark.id;
    private static final int __ID_firmwaretypecode = McuVersion_.firmwaretypecode.id;
    private static final int __ID_starttime = McuVersion_.starttime.id;
    private static final int __ID_endtime = McuVersion_.endtime.id;
    private static final int __ID_timetype = McuVersion_.timetype.id;
    private static final int __ID_starthour = McuVersion_.starthour.id;
    private static final int __ID_endhour = McuVersion_.endhour.id;
    private static final int __ID_status = McuVersion_.status.id;
    private static final int __ID_firmwareData = McuVersion_.firmwareData.id;
    private static final int __ID_meshUpdate = McuVersion_.meshUpdate.id;

    static final class Factory implements CursorFactory<McuVersion> {
        Factory() {
        }

        @Override // io.objectbox.internal.CursorFactory
        public Cursor<McuVersion> createCursor(Transaction tx, long cursorHandle, BoxStore boxStoreForEntities) {
            return new McuVersionCursor(tx, cursorHandle, boxStoreForEntities);
        }
    }

    public McuVersionCursor(Transaction tx, long cursor, BoxStore boxStore) {
        super(tx, cursor, McuVersion_.__INSTANCE, boxStore);
    }

    @Override // io.objectbox.Cursor
    public long getId(McuVersion entity) {
        return ID_GETTER.getId(entity);
    }

    @Override // io.objectbox.Cursor
    public long put(McuVersion entity) {
        String versionname = entity.getVersionname();
        int i = versionname != null ? __ID_versionname : 0;
        String filedir = entity.getFiledir();
        int i2 = filedir != null ? __ID_filedir : 0;
        String filename = entity.getFilename();
        int i3 = filename != null ? __ID_filename : 0;
        String url = entity.getUrl();
        int i4 = url != null ? __ID_url : 0;
        byte[] firmwareData = entity.getFirmwareData();
        collect430000(this.cursor, 0L, 1, i, versionname, i2, filedir, i3, filename, i4, url, firmwareData != null ? __ID_firmwareData : 0, firmwareData, 0, null, 0, null);
        String createtime = entity.getCreatetime();
        int i5 = createtime != null ? __ID_createtime : 0;
        String begincreatetime = entity.getBegincreatetime();
        int i6 = begincreatetime != null ? __ID_begincreatetime : 0;
        String endcreatetime = entity.getEndcreatetime();
        int i7 = endcreatetime != null ? __ID_endcreatetime : 0;
        String productname = entity.getProductname();
        collect400000(this.cursor, 0L, 0, i5, createtime, i6, begincreatetime, i7, endcreatetime, productname != null ? __ID_productname : 0, productname);
        String remark = entity.getRemark();
        int i8 = remark != null ? __ID_remark : 0;
        String firmwaretypecode = entity.getFirmwaretypecode();
        int i9 = firmwaretypecode != null ? __ID_firmwaretypecode : 0;
        String starttime = entity.getStarttime();
        int i10 = starttime != null ? __ID_starttime : 0;
        String endtime = entity.getEndtime();
        collect400000(this.cursor, 0L, 0, i8, remark, i9, firmwaretypecode, i10, starttime, endtime != null ? __ID_endtime : 0, endtime);
        String starthour = entity.getStarthour();
        int i11 = starthour != null ? __ID_starthour : 0;
        String endhour = entity.getEndhour();
        collect313311(this.cursor, 0L, 0, i11, starthour, endhour != null ? __ID_endhour : 0, endhour, 0, null, 0, null, __ID_deviceversionid, entity.getDeviceversionid(), __ID_productid, entity.getProductid(), __ID_versionnumber, entity.getVersionnumber(), __ID_enable, entity.getEnable(), __ID_type, entity.getType(), __ID_pagesize, entity.getPagesize(), 0, 0.0f, 0, Utils.DOUBLE_EPSILON);
        long collect004000 = collect004000(this.cursor, entity.getId(), 2, __ID_pagenum, entity.getPagenum(), __ID_timetype, entity.getTimetype(), __ID_status, entity.getStatus(), __ID_meshUpdate, entity.isMeshUpdate() ? 1L : 0L);
        entity.setId(collect004000);
        return collect004000;
    }
}