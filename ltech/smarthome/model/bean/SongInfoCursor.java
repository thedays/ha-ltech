package com.ltech.smarthome.model.bean;

import com.github.mikephil.charting.utils.Utils;
import com.ltech.smarthome.model.bean.SongInfo_;
import io.objectbox.BoxStore;
import io.objectbox.Cursor;
import io.objectbox.Transaction;
import io.objectbox.internal.CursorFactory;

/* loaded from: classes4.dex */
public final class SongInfoCursor extends Cursor<SongInfo> {
    private static final SongInfo_.SongInfoIdGetter ID_GETTER = SongInfo_.__ID_GETTER;
    private static final int __ID_name = SongInfo_.name.id;
    private static final int __ID_author = SongInfo_.author.id;
    private static final int __ID_num = SongInfo_.num.id;
    private static final int __ID_totalCount = SongInfo_.totalCount.id;
    private static final int __ID_deviceId = SongInfo_.deviceId.id;
    private static final int __ID_state = SongInfo_.state.id;

    static final class Factory implements CursorFactory<SongInfo> {
        Factory() {
        }

        @Override // io.objectbox.internal.CursorFactory
        public Cursor<SongInfo> createCursor(Transaction tx, long cursorHandle, BoxStore boxStoreForEntities) {
            return new SongInfoCursor(tx, cursorHandle, boxStoreForEntities);
        }
    }

    public SongInfoCursor(Transaction tx, long cursor, BoxStore boxStore) {
        super(tx, cursor, SongInfo_.__INSTANCE, boxStore);
    }

    @Override // io.objectbox.Cursor
    public long getId(SongInfo entity) {
        return ID_GETTER.getId(entity);
    }

    @Override // io.objectbox.Cursor
    public long put(SongInfo entity) {
        String name = entity.getName();
        int i = name != null ? __ID_name : 0;
        String author = entity.getAuthor();
        long collect313311 = collect313311(this.cursor, entity.getId(), 3, i, name, author != null ? __ID_author : 0, author, 0, null, 0, null, __ID_deviceId, entity.getDeviceId(), __ID_num, entity.getNum(), __ID_totalCount, entity.getTotalCount(), __ID_state, entity.getState(), 0, 0, 0, 0, 0, 0.0f, 0, Utils.DOUBLE_EPSILON);
        entity.setId(collect313311);
        return collect313311;
    }
}