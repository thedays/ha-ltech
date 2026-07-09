package com.ltech.smarthome.utils;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: KotlinUtils.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007H\u0007J\u0016\u0010\b\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007H\u0007¨\u0006\t"}, d2 = {"Lcom/ltech/smarthome/utils/KotlinUtils;", "", "<init>", "()V", "getMax", "", "numbers", "", "getMin", "app_yingyongbaoRelease"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class KotlinUtils {
    public static final KotlinUtils INSTANCE = new KotlinUtils();

    private KotlinUtils() {
    }

    @JvmStatic
    public static final int getMax(List<Integer> numbers) {
        Intrinsics.checkNotNullParameter(numbers, "numbers");
        Integer num = (Integer) CollectionsKt.maxOrNull((Iterable) numbers);
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    @JvmStatic
    public static final int getMin(List<Integer> numbers) {
        Intrinsics.checkNotNullParameter(numbers, "numbers");
        Integer num = (Integer) CollectionsKt.minOrNull((Iterable) numbers);
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }
}