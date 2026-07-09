package com.ltech.smarthome.ui.device.light;

import androidx.exifinterface.media.ExifInterface;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.sun.jna.platform.win32.WinError;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import org.mozilla.javascript.ES6Iterator;

/* compiled from: LineOrder.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006H\u0007J\u001c\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00052\u0006\u0010\u0011\u001a\u00020\u0006H\u0007J\u0016\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00060\u00142\u0006\u0010\u0011\u001a\u00020\u0006H\u0007J\u0016\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00060\u00142\u0006\u0010\u0016\u001a\u00020\u0006H\u0007J\u0016\u0010\u0017\u001a\u00020\u00062\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u0014H\u0007J\u0016\u0010\u0019\u001a\u00020\u00072\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u0014H\u0007R\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001d\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u001d\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\tR\u001d\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\tR$\u0010\u001a\u001a\u00020\u001b8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u001c\u0010\u0003\u001a\u0004\b\u001a\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001d\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\t¨\u0006\""}, d2 = {"Lcom/ltech/smarthome/ui/device/light/LineOrder;", "", "<init>", "()V", "ctOrder", "", "", "", "getCtOrder", "()Ljava/util/Map;", "rgbOrder", "getRgbOrder", "rgbwOrder", "getRgbwOrder", "rgbcwOrder", "getRgbcwOrder", "getDefaultOrder", UpdateBackDataRequest.LIGHT_TYPE, "getMap", "getListByType", "", "getList", ES6Iterator.VALUE_PROPERTY, "getValue", "valueList", "getShortLineStr", "isHB4", "", "isHB4$annotations", "()Z", "setHB4", "(Z)V", "hb4Order", "getHb4Order", "app_yingyongbaoRelease"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class LineOrder {
    private static boolean isHB4;
    public static final LineOrder INSTANCE = new LineOrder();
    private static final Map<Integer, String> ctOrder = MapsKt.mapOf(TuplesKt.to(1, ExifInterface.LONGITUDE_WEST), TuplesKt.to(2, "C"));
    private static final Map<Integer, String> rgbOrder = MapsKt.mapOf(TuplesKt.to(1, "R"), TuplesKt.to(2, "G"), TuplesKt.to(3, "B"));
    private static final Map<Integer, String> rgbwOrder = MapsKt.mapOf(TuplesKt.to(1, "R"), TuplesKt.to(2, "G"), TuplesKt.to(3, "B"), TuplesKt.to(4, ExifInterface.LONGITUDE_WEST));
    private static final Map<Integer, String> rgbcwOrder = MapsKt.mapOf(TuplesKt.to(1, "R"), TuplesKt.to(2, "G"), TuplesKt.to(3, "B"), TuplesKt.to(4, "C"), TuplesKt.to(5, ExifInterface.LONGITUDE_WEST));
    private static final Map<Integer, String> hb4Order = MapsKt.mapOf(TuplesKt.to(0, "/"), TuplesKt.to(1, "R"), TuplesKt.to(2, "G"), TuplesKt.to(3, "B"), TuplesKt.to(4, "C"), TuplesKt.to(5, ExifInterface.LONGITUDE_WEST));

    @JvmStatic
    public static final int getDefaultOrder(int lightType) {
        if (lightType == 2) {
            return 12;
        }
        if (lightType == 3) {
            return 123;
        }
        if (lightType != 4) {
            return 12345;
        }
        return WinError.ERROR_PORT_UNREACHABLE;
    }

    @JvmStatic
    public static /* synthetic */ void isHB4$annotations() {
    }

    private LineOrder() {
    }

    public final Map<Integer, String> getCtOrder() {
        return ctOrder;
    }

    public final Map<Integer, String> getRgbOrder() {
        return rgbOrder;
    }

    public final Map<Integer, String> getRgbwOrder() {
        return rgbwOrder;
    }

    public final Map<Integer, String> getRgbcwOrder() {
        return rgbcwOrder;
    }

    @JvmStatic
    public static final Map<Integer, String> getMap(int lightType) {
        if (isHB4) {
            return hb4Order;
        }
        if (lightType == 2) {
            return ctOrder;
        }
        if (lightType == 3) {
            return rgbOrder;
        }
        if (lightType == 4) {
            return rgbwOrder;
        }
        return rgbcwOrder;
    }

    @JvmStatic
    public static final List<Integer> getListByType(int lightType) {
        return CollectionsKt.toList(getMap(lightType).keySet());
    }

    @JvmStatic
    public static final List<Integer> getList(int value) {
        String valueOf = String.valueOf(value);
        if (isHB4) {
            valueOf = Integer.toString(value, CharsKt.checkRadix(16));
            Intrinsics.checkNotNullExpressionValue(valueOf, "toString(...)");
        }
        ArrayList arrayList = new ArrayList();
        int length = valueOf.length();
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            String substring = valueOf.substring(i, i2);
            Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
            arrayList.add(Integer.valueOf(Integer.parseInt(substring)));
            i = i2;
        }
        return arrayList;
    }

    @JvmStatic
    public static final int getValue(List<Integer> valueList) {
        Intrinsics.checkNotNullParameter(valueList, "valueList");
        Iterator<T> it = valueList.iterator();
        String str = "";
        while (it.hasNext()) {
            int intValue = ((Number) it.next()).intValue();
            StringBuilder sb = new StringBuilder();
            sb.append((Object) str);
            sb.append(intValue);
            str = sb.toString();
        }
        if (isHB4) {
            return Integer.parseInt(str, CharsKt.checkRadix(16));
        }
        return Integer.parseInt(str);
    }

    @JvmStatic
    public static final String getShortLineStr(List<Integer> valueList) {
        Intrinsics.checkNotNullParameter(valueList, "valueList");
        Iterator<T> it = valueList.iterator();
        String str = "";
        while (it.hasNext()) {
            String str2 = rgbcwOrder.get(Integer.valueOf(((Number) it.next()).intValue()));
            StringBuilder sb = new StringBuilder();
            sb.append((Object) str);
            sb.append((Object) str2);
            str = sb.toString();
        }
        return str;
    }

    public static final boolean isHB4() {
        return isHB4;
    }

    public static final void setHB4(boolean z) {
        isHB4 = z;
    }

    public final Map<Integer, String> getHb4Order() {
        return hb4Order;
    }
}