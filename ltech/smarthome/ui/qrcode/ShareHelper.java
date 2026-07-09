package com.ltech.smarthome.ui.qrcode;

import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.huawei.hms.framework.common.ContainerUtils;
import com.justalk.cloud.lemon.MtcConfConstants;
import com.ltech.smarthome.model.bean.ModeContent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: ShareHelper.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\f\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001)B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0014\u0010\u001d\u001a\u00020\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001a0\tJ\"\u0010 \u001a\u00020\u00072\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\n0\t2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050\tJ\u0014\u0010\"\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010#\u001a\u00020\u0007J\u001a\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\n0\tJ\u000e\u0010%\u001a\u00020\u001a2\u0006\u0010&\u001a\u00020\nJ\u0014\u0010'\u001a\u00020\u00052\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u001a0\tR\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\"\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R \u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\f\"\u0004\b\u001c\u0010\u000e¨\u0006*"}, d2 = {"Lcom/ltech/smarthome/ui/qrcode/ShareHelper;", "", "<init>", "()V", "SHARE_DUV", "", "PREFIX_DUV", "", "wholeDataList", "", "Lcom/ltech/smarthome/ui/qrcode/ShareHelper$ShareData;", "getWholeDataList", "()Ljava/util/List;", "setWholeDataList", "(Ljava/util/List;)V", "shareType", "getShareType", "()I", "setShareType", "(I)V", "qrCode", "getQrCode", "()Ljava/lang/String;", "setQrCode", "(Ljava/lang/String;)V", "duvList", "Lcom/ltech/smarthome/model/bean/ModeContent;", "getDuvList", "setDuvList", "convert", "", MtcConfConstants.MtcConfRecordListKey, "createQrCode", "selectArray", "decodeQrCode", "data", "getAllIndex", "convertShareDataToDuv", "shareData", "getModeIndex", "modeList", "ShareData", "app_yingyongbaoRelease"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class ShareHelper {
    public static final String PREFIX_DUV = "lt://duvLocal";
    public static final int SHARE_DUV = 1;
    private static List<ShareData> wholeDataList;
    public static final ShareHelper INSTANCE = new ShareHelper();
    private static int shareType = 1;
    private static String qrCode = "";
    private static List<? extends ModeContent> duvList = new ArrayList();

    private ShareHelper() {
    }

    public final List<ShareData> getWholeDataList() {
        return wholeDataList;
    }

    public final void setWholeDataList(List<ShareData> list) {
        wholeDataList = list;
    }

    public final int getShareType() {
        return shareType;
    }

    public final void setShareType(int i) {
        shareType = i;
    }

    public final String getQrCode() {
        return qrCode;
    }

    public final void setQrCode(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        qrCode = str;
    }

    public final List<ModeContent> getDuvList() {
        return duvList;
    }

    public final void setDuvList(List<? extends ModeContent> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        duvList = list;
    }

    /* compiled from: ShareHelper.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\n¨\u0006\u000e"}, d2 = {"Lcom/ltech/smarthome/ui/qrcode/ShareHelper$ShareData;", "", "modeName", "", "content", "<init>", "(Ljava/lang/String;Ljava/lang/String;)V", "getContent", "()Ljava/lang/String;", "setContent", "(Ljava/lang/String;)V", "name", "getName", "setName", "app_yingyongbaoRelease"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public static final class ShareData {
        private String content;
        private String name;

        public ShareData(String modeName, String content) {
            Intrinsics.checkNotNullParameter(modeName, "modeName");
            Intrinsics.checkNotNullParameter(content, "content");
            this.content = content;
            this.name = modeName;
        }

        public final String getContent() {
            return this.content;
        }

        public final void setContent(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.content = str;
        }

        public final String getName() {
            return this.name;
        }

        public final void setName(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.name = str;
        }
    }

    public final void convert(List<? extends ModeContent> list) {
        Intrinsics.checkNotNullParameter(list, "list");
        List<? extends ModeContent> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (ModeContent modeContent : list2) {
            String modeName = modeContent.getModeName();
            Intrinsics.checkNotNullExpressionValue(modeName, "getModeName(...)");
            String content = modeContent.getContent();
            Intrinsics.checkNotNullExpressionValue(content, "getContent(...)");
            arrayList.add(new ShareData(modeName, content));
        }
        wholeDataList = arrayList;
    }

    public final String createQrCode(List<ShareData> list, List<Integer> selectArray) {
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(selectArray, "selectArray");
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            if (selectArray.contains(Integer.valueOf(i))) {
                arrayList.add(obj);
            }
            i = i2;
        }
        String json = GsonUtils.toJson(arrayList);
        qrCode = json;
        if (shareType == 1) {
            qrCode = "lt://duvLocal=\"" + json + "\"";
        }
        return qrCode;
    }

    public final List<ShareData> decodeQrCode(String data) {
        Intrinsics.checkNotNullParameter(data, "data");
        String substring = data.substring(StringsKt.indexOf$default((CharSequence) data, ContainerUtils.KEY_VALUE_DELIMITER, 0, false, 6, (Object) null) + 2, data.length() - 1);
        Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
        Object fromJson = GsonUtils.fromJson(substring, new TypeToken<List<? extends ShareData>>() { // from class: com.ltech.smarthome.ui.qrcode.ShareHelper$decodeQrCode$shareList$1
        }.getType());
        Intrinsics.checkNotNullExpressionValue(fromJson, "fromJson(...)");
        return (List) fromJson;
    }

    public final List<Integer> getAllIndex(List<ShareData> list) {
        Intrinsics.checkNotNullParameter(list, "list");
        List<ShareData> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        int i = 0;
        for (Object obj : list2) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            arrayList.add(Integer.valueOf(i));
            i = i2;
        }
        return arrayList;
    }

    public final ModeContent convertShareDataToDuv(ShareData shareData) {
        Intrinsics.checkNotNullParameter(shareData, "shareData");
        ModeContent modeContent = new ModeContent();
        modeContent.setModeIndex(getModeIndex(duvList));
        modeContent.setModeType(4);
        modeContent.setModeName(shareData.getName());
        modeContent.setContent(shareData.getContent());
        return modeContent;
    }

    public final int getModeIndex(List<? extends ModeContent> modeList) {
        Intrinsics.checkNotNullParameter(modeList, "modeList");
        List<? extends ModeContent> list = modeList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(((ModeContent) it.next()).getModeIndex()));
        }
        int i = 0;
        do {
            i++;
        } while (arrayList.contains(Integer.valueOf(i)));
        return i;
    }
}