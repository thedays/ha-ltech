package com.ltech.smarthome.model.bean;

import com.ltech.smarthome.model.bean.ModeContent_;
import io.objectbox.BoxStore;
import io.objectbox.Cursor;
import io.objectbox.Transaction;
import io.objectbox.internal.CursorFactory;

/* loaded from: classes4.dex */
public final class ModeContentCursor extends Cursor<ModeContent> {
    private static final ModeContent_.ModeContentIdGetter ID_GETTER = ModeContent_.__ID_GETTER;
    private static final int __ID_placeId = ModeContent_.placeId.id;
    private static final int __ID_userId = ModeContent_.userId.id;
    private static final int __ID_modeName = ModeContent_.modeName.id;
    private static final int __ID_Content = ModeContent_.Content.id;
    private static final int __ID_modeType = ModeContent_.modeType.id;
    private static final int __ID_modeIndex = ModeContent_.modeIndex.id;
    private static final int __ID_deviceType = ModeContent_.deviceType.id;
    private static final int __ID_moduleType = ModeContent_.moduleType.id;
    private static final int __ID_controlType = ModeContent_.controlType.id;
    private static final int __ID_picIndex = ModeContent_.picIndex.id;
    private static final int __ID_playTimes = ModeContent_.playTimes.id;

    static final class Factory implements CursorFactory<ModeContent> {
        Factory() {
        }

        @Override // io.objectbox.internal.CursorFactory
        public Cursor<ModeContent> createCursor(Transaction tx, long cursorHandle, BoxStore boxStoreForEntities) {
            return new ModeContentCursor(tx, cursorHandle, boxStoreForEntities);
        }
    }

    public ModeContentCursor(Transaction tx, long cursor, BoxStore boxStore) {
        super(tx, cursor, ModeContent_.__INSTANCE, boxStore);
    }

    @Override // io.objectbox.Cursor
    public long getId(ModeContent entity) {
        return ID_GETTER.getId(entity);
    }

    @Override // io.objectbox.Cursor
    public long put(ModeContent entity) {
        String modeName = entity.getModeName();
        int i = modeName != null ? __ID_modeName : 0;
        String content = entity.getContent();
        int i2 = content != null ? __ID_Content : 0;
        String moduleType = entity.getModuleType();
        int i3 = moduleType != null ? __ID_moduleType : 0;
        String controlType = entity.getControlType();
        collect400000(this.cursor, 0L, 1, i, modeName, i2, content, i3, moduleType, controlType != null ? __ID_controlType : 0, controlType);
        collect004000(this.cursor, 0L, 0, __ID_placeId, entity.getPlaceId(), __ID_userId, entity.getUserId(), __ID_modeType, entity.getModeType(), __ID_modeIndex, entity.getModeIndex());
        long collect004000 = collect004000(this.cursor, entity.getLightModeId(), 2, __ID_deviceType, entity.getDeviceType(), __ID_picIndex, entity.getPicIndex(), __ID_playTimes, entity.getPlayTimes(), 0, 0L);
        entity.setLightModeId(collect004000);
        return collect004000;
    }
}