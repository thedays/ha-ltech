package com.ltech.smarthome.model.bean;

import com.github.mikephil.charting.utils.Utils;
import com.ltech.smarthome.model.bean.PlayListInfo;
import com.ltech.smarthome.model.bean.PlayListInfo_;
import io.objectbox.BoxStore;
import io.objectbox.Cursor;
import io.objectbox.Transaction;
import io.objectbox.internal.CursorFactory;
import java.util.List;

/* loaded from: classes4.dex */
public final class PlayListInfoCursor extends Cursor<PlayListInfo> {
    private final PlayListInfo.ListConverter listConverter;
    private static final PlayListInfo_.PlayListInfoIdGetter ID_GETTER = PlayListInfo_.__ID_GETTER;
    private static final int __ID_num = PlayListInfo_.num.id;
    private static final int __ID_songCount = PlayListInfo_.songCount.id;
    private static final int __ID_name = PlayListInfo_.name.id;
    private static final int __ID_totalCount = PlayListInfo_.totalCount.id;
    private static final int __ID_list = PlayListInfo_.list.id;
    private static final int __ID_deviceId = PlayListInfo_.deviceId.id;

    static final class Factory implements CursorFactory<PlayListInfo> {
        Factory() {
        }

        @Override // io.objectbox.internal.CursorFactory
        public Cursor<PlayListInfo> createCursor(Transaction tx, long cursorHandle, BoxStore boxStoreForEntities) {
            return new PlayListInfoCursor(tx, cursorHandle, boxStoreForEntities);
        }
    }

    public PlayListInfoCursor(Transaction tx, long cursor, BoxStore boxStore) {
        super(tx, cursor, PlayListInfo_.__INSTANCE, boxStore);
        this.listConverter = new PlayListInfo.ListConverter();
    }

    @Override // io.objectbox.Cursor
    public long getId(PlayListInfo entity) {
        return ID_GETTER.getId(entity);
    }

    @Override // io.objectbox.Cursor
    public long put(PlayListInfo entity) {
        String name = entity.getName();
        int i = name != null ? __ID_name : 0;
        List<Integer> list = entity.getList();
        int i2 = list != null ? __ID_list : 0;
        long collect313311 = collect313311(this.cursor, entity.getId(), 3, i, name, i2, i2 != 0 ? this.listConverter.convertToDatabaseValue(list) : null, 0, null, 0, null, __ID_deviceId, entity.getDeviceId(), __ID_num, entity.getNum(), __ID_songCount, entity.getSongCount(), __ID_totalCount, entity.getTotalCount(), 0, 0, 0, 0, 0, 0.0f, 0, Utils.DOUBLE_EPSILON);
        entity.setId(collect313311);
        return collect313311;
    }
}