package com.ltech.smarthome.view.layoutmanager;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.xiaomi.mipush.sdk.Constants;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;

/* compiled from: LooperPickerLayoutManager.kt */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b)\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0016\u0018\u0000 \u0092\u00012\u00020\u00012\u00020\u0002:\u0006\u0092\u0001\u0093\u0001\u0094\u0001BE\b\u0007\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0003\u0010\b\u001a\u00020\t\u0012\b\b\u0003\u0010\n\u001a\u00020\t\u0012\b\b\u0003\u0010\u000b\u001a\u00020\t¢\u0006\u0004\b\f\u0010\rJ\b\u00107\u001a\u000208H\u0016J\b\u00109\u001a\u00020\u0007H\u0016J,\u0010:\u001a\u00020.2\n\u0010;\u001a\u00060<R\u00020=2\u0006\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020\u00042\u0006\u0010A\u001a\u00020\u0004H\u0016J\u0018\u0010B\u001a\u00020.2\u0006\u0010C\u001a\u00020\u00042\u0006\u0010D\u001a\u00020\u0004H\u0002J\u001c\u0010E\u001a\u00020.2\n\u0010;\u001a\u00060<R\u00020=2\u0006\u0010>\u001a\u00020?H\u0016J\u0010\u0010F\u001a\u00020.2\u0006\u0010G\u001a\u00020=H\u0016J\u0012\u0010H\u001a\u00020.2\b\u0010>\u001a\u0004\u0018\u00010?H\u0016J\b\u0010I\u001a\u00020\u0007H\u0016J\b\u0010J\u001a\u00020\u0007H\u0016J$\u0010K\u001a\u00020\u00042\u0006\u0010L\u001a\u00020\u00042\n\u0010;\u001a\u00060<R\u00020=2\u0006\u0010>\u001a\u00020?H\u0016J$\u0010M\u001a\u00020\u00042\u0006\u0010N\u001a\u00020\u00042\n\u0010;\u001a\u00060<R\u00020=2\u0006\u0010>\u001a\u00020?H\u0016J\u0010\u0010O\u001a\u00020.2\u0006\u0010-\u001a\u00020\u0004H\u0016J \u0010P\u001a\u00020.2\u0006\u0010G\u001a\u00020=2\u0006\u0010>\u001a\u00020?2\u0006\u0010-\u001a\u00020\u0004H\u0016J\u0012\u0010Q\u001a\u0004\u0018\u00010R2\u0006\u0010S\u001a\u00020\u0004H\u0016J\u0010\u0010T\u001a\u00020.2\u0006\u0010>\u001a\u00020\u0004H\u0016J,\u0010U\u001a\u00020.2\n\u0010;\u001a\u00060<R\u00020=2\u0006\u0010>\u001a\u00020?2\u0006\u0010V\u001a\u00020\u00042\u0006\u0010W\u001a\u00020\u0004H\u0002J\b\u0010X\u001a\u00020\u0004H\u0002J\b\u0010Y\u001a\u00020\u0004H\u0002J \u0010Z\u001a\u00020.2\u0006\u0010[\u001a\u00020%2\u0006\u0010V\u001a\u00020\u00042\u0006\u0010W\u001a\u00020\u0004H\u0002J$\u0010\\\u001a\u00020\u00042\u0006\u0010]\u001a\u00020\u00042\n\u0010;\u001a\u00060<R\u00020=2\u0006\u0010>\u001a\u00020?H\u0002J$\u0010^\u001a\u00020\u00042\u0006\u0010]\u001a\u00020\u00042\n\u0010;\u001a\u00060<R\u00020=2\u0006\u0010>\u001a\u00020?H\u0002J\u0018\u0010_\u001a\u00020\u00072\u0006\u0010W\u001a\u00020\u00042\u0006\u0010]\u001a\u00020\u0004H\u0002J\u0018\u0010`\u001a\u00020\u00072\u0006\u0010W\u001a\u00020\u00042\u0006\u0010>\u001a\u00020?H\u0002J\u0010\u0010a\u001a\u00020\u00042\u0006\u0010W\u001a\u00020\u0004H\u0002J\u0010\u0010b\u001a\u00020\u00072\u0006\u0010>\u001a\u00020?H\u0002J\u0010\u0010c\u001a\u00020%2\u0006\u0010W\u001a\u00020\u0004H\u0002J\u0010\u0010d\u001a\u00020\u00042\u0006\u0010W\u001a\u00020\u0004H\u0002J\u0010\u0010e\u001a\u00020\u00042\u0006\u0010W\u001a\u00020\u0004H\u0002J\u0010\u0010f\u001a\u00020\u00042\u0006\u0010W\u001a\u00020\u0004H\u0002J\u001c\u0010g\u001a\u00020%2\n\u0010;\u001a\u00060<R\u00020=2\u0006\u0010W\u001a\u00020\u0004H\u0002J\u001c\u0010h\u001a\u00020.2\u0006\u0010]\u001a\u00020\u00042\n\u0010;\u001a\u00060<R\u00020=H\u0002J\b\u0010i\u001a\u00020.H\u0002J\b\u0010j\u001a\u00020.H\u0002J\n\u0010k\u001a\u0004\u0018\u00010%H\u0002J\b\u0010l\u001a\u00020\u0004H\u0002J\b\u0010m\u001a\u00020.H\u0002J\b\u0010n\u001a\u00020\u0004H\u0002J\b\u0010o\u001a\u00020\u0004H\u0002J\u001c\u0010p\u001a\u00020%2\n\u0010;\u001a\u00060<R\u00020=2\u0006\u0010-\u001a\u00020\u0004H\u0002J\u0010\u0010q\u001a\u00020.2\u0006\u0010-\u001a\u00020\u0004H\u0002J\u0010\u0010r\u001a\u00020\u00042\u0006\u0010s\u001a\u00020\u0004H\u0002J\u0012\u0010t\u001a\u00020.2\b\b\u0002\u0010-\u001a\u00020\u0004H\u0002J\u0018\u0010u\u001a\u00020.2\u0006\u0010v\u001a\u00020%2\u0006\u0010w\u001a\u00020\u0004H\u0002J\u0018\u0010x\u001a\u00020.2\u0006\u0010y\u001a\u00020\u00042\u0006\u0010w\u001a\u00020\u0004H\u0002J2\u0010z\u001a\u00020.2%\u0010{\u001a!\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b+\u0012\b\b,\u0012\u0004\b\b(-\u0012\u0004\u0012\u00020.0*j\u0002`|¢\u0006\u0002\u0010}J2\u0010~\u001a\u00020.2%\u0010{\u001a!\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b+\u0012\b\b,\u0012\u0004\b\b(-\u0012\u0004\u0012\u00020.0*j\u0002`|¢\u0006\u0002\u0010}J\u0006\u0010\u007f\u001a\u00020.J\u0007\u0010\u0080\u0001\u001a\u00020\u0004J\t\u0010\u0081\u0001\u001a\u00020.H\u0016J\u001b\u0010\u0082\u0001\u001a\u00020\t2\u0007\u0010\u0083\u0001\u001a\u00020\t2\u0007\u0010\u0084\u0001\u001a\u00020\u0004H\u0002J\u0019\u0010\u0085\u0001\u001a\u00020\u00042\u0006\u0010w\u001a\u00020\u00042\u0006\u0010-\u001a\u00020\u0004H\u0002J\t\u0010\u0086\u0001\u001a\u00020.H\u0002J\u0019\u0010\u0087\u0001\u001a\u00020.2\u0006\u0010[\u001a\u00020%2\u0006\u0010-\u001a\u00020\u0004H\u0016J\u0019\u0010\u0088\u0001\u001a\u00020.2\u0006\u0010[\u001a\u00020%2\u0006\u0010-\u001a\u00020\u0004H\u0016J\u000f\u0010\u0089\u0001\u001a\u00020.2\u0006\u0010{\u001a\u000200J\u000f\u0010\u008a\u0001\u001a\u00020.2\u0006\u0010{\u001a\u000200J\u0007\u0010\u008b\u0001\u001a\u00020.J\u0013\u0010\u008c\u0001\u001a\u00020.2\b\u0010\u008d\u0001\u001a\u00030\u008e\u0001H\u0002J\u0015\u0010\u008f\u0001\u001a\u00020.2\n\u0010;\u001a\u00060<R\u00020=H\u0002J\t\u0010\u0090\u0001\u001a\u00020.H\u0002J\t\u0010\u0091\u0001\u001a\u00020.H\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0005\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000f\"\u0004\b\u0013\u0010\u0011R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\n\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0018\"\u0004\b\u001c\u0010\u001aR\u001a\u0010\u000b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0018\"\u0004\b\u001e\u0010\u001aR\u000e\u0010\u001f\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020'X\u0082\u0004¢\u0006\u0002\n\u0000R/\u0010(\u001a#\u0012\u001f\u0012\u001d\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b+\u0012\b\b,\u0012\u0004\b\b(-\u0012\u0004\u0012\u00020.0*0)X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010/\u001a\b\u0012\u0004\u0012\u0002000)X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u00101\u001a\u0002028BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b5\u00106\u001a\u0004\b3\u00104¨\u0006\u0095\u0001"}, d2 = {"Lcom/ltech/smarthome/view/layoutmanager/LooperPickerLayoutManager;", "Landroidx/recyclerview/widget/RecyclerView$LayoutManager;", "Landroidx/recyclerview/widget/RecyclerView$SmoothScroller$ScrollVectorProvider;", "orientation", "", "visibleCount", "isLoop", "", "scaleX", "", "scaleY", "alpha", "<init>", "(IIZFFF)V", "getOrientation", "()I", "setOrientation", "(I)V", "getVisibleCount", "setVisibleCount", "()Z", "setLoop", "(Z)V", "getScaleX", "()F", "setScaleX", "(F)V", "getScaleY", "setScaleY", "getAlpha", "setAlpha", "mPendingFillPosition", "mItemWidth", "mItemHeight", "mPendingScrollToPosition", "mRecycleViews", "Ljava/util/HashSet;", "Landroid/view/View;", "mSnapHelper", "Landroidx/recyclerview/widget/LinearSnapHelper;", "mOnItemSelectedListener", "", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "position", "", "mOnItemFillListener", "Lcom/ltech/smarthome/view/layoutmanager/LooperPickerLayoutManager$OnItemFillListener;", "mOrientationHelper", "Landroidx/recyclerview/widget/OrientationHelper;", "getMOrientationHelper", "()Landroidx/recyclerview/widget/OrientationHelper;", "mOrientationHelper$delegate", "Lkotlin/Lazy;", "generateDefaultLayoutParams", "Landroidx/recyclerview/widget/RecyclerView$LayoutParams;", "isAutoMeasureEnabled", "onMeasure", "recycler", "Landroidx/recyclerview/widget/RecyclerView$Recycler;", "Landroidx/recyclerview/widget/RecyclerView;", "state", "Landroidx/recyclerview/widget/RecyclerView$State;", "widthSpec", "heightSpec", "setWidthAndHeight", "width", "height", "onLayoutChildren", "onItemsChanged", "recyclerView", "onLayoutCompleted", "canScrollHorizontally", "canScrollVertically", "scrollHorizontallyBy", "dx", "scrollVerticallyBy", "dy", "scrollToPosition", "smoothScrollToPosition", "computeScrollVectorForPosition", "Landroid/graphics/PointF;", "targetPosition", "onScrollStateChanged", "fillLayout", "anchor", "fillDirection", "getOffsetCount", "getFixVisibleCount", "layoutChunk", "child", "scrollBy", "delta", "fillScroll", "canNotFillScroll", "checkScrollToEdge", "getFixLastScroll", "hasMore", "getAnchorView", "getAnchorPosition", "getAnchor", "getPendingFillPosition", "nextView", "recycleChildren", "recycleStart", "recycleEnd", "getSelectedView", "getItemSpace", "getScrollOffset", "getItemOffset", "getOffsetSpace", "getViewForPosition", "checkToPosition", "fixSmoothToPosition", "toPosition", "dispatchOnItemSelectedListener", "scrollToCenter", "centerView", "centerPosition", "smoothOffsetChildren", "amount", "addOnItemSelectedListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lcom/ltech/smarthome/view/layoutmanager/OnItemSelectedListener;", "(Lkotlin/jvm/functions/Function1;)V", "removeOnItemSelectedListener", "removeAllOnItemSelectedListener", "getSelectedPosition", "transformChildren", "getScale", "scale", "intervalCount", "getIntervalCount", "dispatchOnItemFillListener", "onItemSelected", "onItemUnSelected", "addOnItemFillListener", "removeOnItemFillListener", "removeAllItemFillListener", "logDebug", "msg", "", "logChildCount", "logChildrenPosition", "logRecycleChildren", "Companion", "OnItemFillListener", "Builder", "app_yingyongbaoRelease"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes4.dex */
public class LooperPickerLayoutManager extends RecyclerView.LayoutManager implements RecyclerView.SmoothScroller.ScrollVectorProvider {
    public static final float ALPHA = 1.0f;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static boolean DEBUG = true;
    private static final int FILL_END = 1;
    private static final int FILL_START = -1;
    public static final int HORIZONTAL = 0;
    public static final boolean IS_LOOP = false;
    private static final int ORIENTATION = 1;
    public static final float SCALE_X = 1.0f;
    public static final float SCALE_Y = 1.0f;
    private static final String TAG = "PickerLayoutManager";
    public static final int VERTICAL = 1;
    public static final int VISIBLE_COUNT = 3;
    private float alpha;
    private boolean isLoop;
    private int mItemHeight;
    private int mItemWidth;
    private final Set<OnItemFillListener> mOnItemFillListener;
    private final Set<Function1<Integer, Unit>> mOnItemSelectedListener;

    /* renamed from: mOrientationHelper$delegate, reason: from kotlin metadata */
    private final Lazy mOrientationHelper;
    private int mPendingFillPosition;
    private int mPendingScrollToPosition;
    private final HashSet<View> mRecycleViews;
    private final LinearSnapHelper mSnapHelper;
    private int orientation;
    private float scaleX;
    private float scaleY;
    private int visibleCount;

    /* compiled from: LooperPickerLayoutManager.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\t"}, d2 = {"Lcom/ltech/smarthome/view/layoutmanager/LooperPickerLayoutManager$OnItemFillListener;", "", "onItemSelected", "", "itemView", "Landroid/view/View;", "position", "", "onItemUnSelected", "app_yingyongbaoRelease"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public interface OnItemFillListener {
        void onItemSelected(View itemView, int position);

        void onItemUnSelected(View itemView, int position);
    }

    public LooperPickerLayoutManager() {
        this(0, 0, false, 0.0f, 0.0f, 0.0f, 63, null);
    }

    public LooperPickerLayoutManager(int i) {
        this(i, 0, false, 0.0f, 0.0f, 0.0f, 62, null);
    }

    public LooperPickerLayoutManager(int i, int i2) {
        this(i, i2, false, 0.0f, 0.0f, 0.0f, 60, null);
    }

    public LooperPickerLayoutManager(int i, int i2, boolean z) {
        this(i, i2, z, 0.0f, 0.0f, 0.0f, 56, null);
    }

    public LooperPickerLayoutManager(int i, int i2, boolean z, float f) {
        this(i, i2, z, f, 0.0f, 0.0f, 48, null);
    }

    public LooperPickerLayoutManager(int i, int i2, boolean z, float f, float f2) {
        this(i, i2, z, f, f2, 0.0f, 32, null);
    }

    private final float getScale(float scale, int intervalCount) {
        if (scale == 1.0f) {
            return scale;
        }
        float f = 1;
        return f - ((f - scale) * intervalCount);
    }

    private final void getScrollOffset() {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean isAutoMeasureEnabled() {
        return false;
    }

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException
        	at jadx.core.dex.visitors.InitCodeVariables.collectConnectedVars(InitCodeVariables.java:119)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:82)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:74)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:29)
        */
    public /* synthetic */ LooperPickerLayoutManager(int r2, int r3, boolean r4, float r5, float r6, float r7, int r8, kotlin.jvm.internal.DefaultConstructorMarker r9) {
        /*
            r1 = this;
            r9 = r8 & 1
            if (r9 == 0) goto L5
            r2 = 1
        L5:
            r9 = r8 & 2
            if (r9 == 0) goto La
            r3 = 3
        La:
            r9 = r8 & 4
            if (r9 == 0) goto Lf
            r4 = 0
        Lf:
            r9 = r8 & 8
            r0 = 1065353216(0x3f800000, float:1.0)
            if (r9 == 0) goto L17
            r5 = 1065353216(0x3f800000, float:1.0)
        L17:
            r9 = r8 & 16
            if (r9 == 0) goto L1d
            r6 = 1065353216(0x3f800000, float:1.0)
        L1d:
            r8 = r8 & 32
            if (r8 == 0) goto L2a
            r9 = 1065353216(0x3f800000, float:1.0)
            r7 = r5
            r8 = r6
            r5 = r3
            r6 = r4
            r3 = r1
            r4 = r2
            goto L31
        L2a:
            r9 = r7
            r8 = r6
            r6 = r4
            r7 = r5
            r4 = r2
            r5 = r3
            r3 = r1
        L31:
            r3.<init>(r4, r5, r6, r7, r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.view.layoutmanager.LooperPickerLayoutManager.<init>(int, int, boolean, float, float, float, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final int getOrientation() {
        return this.orientation;
    }

    public final void setOrientation(int i) {
        this.orientation = i;
    }

    public final int getVisibleCount() {
        return this.visibleCount;
    }

    public final void setVisibleCount(int i) {
        this.visibleCount = i;
    }

    /* renamed from: isLoop, reason: from getter */
    public final boolean getIsLoop() {
        return this.isLoop;
    }

    public final void setLoop(boolean z) {
        this.isLoop = z;
    }

    public final float getScaleX() {
        return this.scaleX;
    }

    public final void setScaleX(float f) {
        this.scaleX = f;
    }

    public final float getScaleY() {
        return this.scaleY;
    }

    public final void setScaleY(float f) {
        this.scaleY = f;
    }

    public final float getAlpha() {
        return this.alpha;
    }

    public final void setAlpha(float f) {
        this.alpha = f;
    }

    public LooperPickerLayoutManager(int i, int i2, boolean z, float f, float f2, float f3) {
        this.orientation = i;
        this.visibleCount = i2;
        this.isLoop = z;
        this.scaleX = f;
        this.scaleY = f2;
        this.alpha = f3;
        this.mPendingFillPosition = -1;
        this.mPendingScrollToPosition = -1;
        this.mRecycleViews = new HashSet<>();
        this.mSnapHelper = new LinearSnapHelper();
        this.mOnItemSelectedListener = new LinkedHashSet();
        this.mOnItemFillListener = new LinkedHashSet();
        this.mOrientationHelper = LazyKt.lazy(new Function0() { // from class: com.ltech.smarthome.view.layoutmanager.LooperPickerLayoutManager$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                OrientationHelper mOrientationHelper_delegate$lambda$0;
                mOrientationHelper_delegate$lambda$0 = LooperPickerLayoutManager.mOrientationHelper_delegate$lambda$0(LooperPickerLayoutManager.this);
                return mOrientationHelper_delegate$lambda$0;
            }
        });
        if (this.visibleCount % 2 != 0) {
            return;
        }
        throw new IllegalArgumentException("visibleCount == " + this.visibleCount + " 不能是偶数");
    }

    /* compiled from: LooperPickerLayoutManager.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082T¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0005X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\fX\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0015X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0015X\u0080T¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/ltech/smarthome/view/layoutmanager/LooperPickerLayoutManager$Companion;", "", "<init>", "()V", "HORIZONTAL", "", "VERTICAL", "FILL_START", "FILL_END", "TAG", "", "DEBUG", "", "getDEBUG", "()Z", "setDEBUG", "(Z)V", "ORIENTATION", "VISIBLE_COUNT", "IS_LOOP", "SCALE_X", "", "SCALE_Y", "ALPHA", "app_yingyongbaoRelease"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean getDEBUG() {
            return LooperPickerLayoutManager.DEBUG;
        }

        public final void setDEBUG(boolean z) {
            LooperPickerLayoutManager.DEBUG = z;
        }
    }

    private final OrientationHelper getMOrientationHelper() {
        Object value = this.mOrientationHelper.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (OrientationHelper) value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final OrientationHelper mOrientationHelper_delegate$lambda$0(LooperPickerLayoutManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.orientation == 0) {
            return OrientationHelper.createHorizontalHelper(this$0);
        }
        return OrientationHelper.createVerticalHelper(this$0);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (this.orientation == 0) {
            return new RecyclerView.LayoutParams(-2, -1);
        }
        return new RecyclerView.LayoutParams(-1, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        Intrinsics.checkNotNullParameter(recycler, "recycler");
        Intrinsics.checkNotNullParameter(state, "state");
        if (state.getItemCount() == 0) {
            super.onMeasure(recycler, state, widthSpec, heightSpec);
            return;
        }
        if (state.isPreLayout()) {
            return;
        }
        View viewForPosition = recycler.getViewForPosition(0);
        Intrinsics.checkNotNullExpressionValue(viewForPosition, "getViewForPosition(...)");
        addView(viewForPosition);
        viewForPosition.measure(widthSpec, heightSpec);
        this.mItemWidth = getDecoratedMeasuredWidth(viewForPosition);
        int decoratedMeasuredHeight = getDecoratedMeasuredHeight(viewForPosition);
        this.mItemHeight = decoratedMeasuredHeight;
        logDebug("mItemWidth == " + this.mItemWidth + " -- mItemHeight == " + decoratedMeasuredHeight);
        detachAndScrapView(viewForPosition, recycler);
        setWidthAndHeight(this.mItemWidth, this.mItemHeight);
    }

    private final void setWidthAndHeight(int width, int height) {
        if (this.orientation == 0) {
            setMeasuredDimension(width * this.visibleCount, height);
        } else {
            setMeasuredDimension(width, height * this.visibleCount);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Intrinsics.checkNotNullParameter(recycler, "recycler");
        Intrinsics.checkNotNullParameter(state, "state");
        logDebug("onLayoutChildren");
        if (this.mPendingScrollToPosition != -1 && state.getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }
        if (state.isPreLayout()) {
            return;
        }
        logDebug("state.itemCount -- " + state.getItemCount());
        this.mPendingFillPosition = 0;
        int i = this.mPendingScrollToPosition;
        boolean z = i != -1;
        if (z) {
            this.mPendingFillPosition = i;
        } else if (getChildCount() != 0) {
            this.mPendingFillPosition = getSelectedPosition();
        }
        logDebug("mPendingFillPosition == " + this.mPendingFillPosition);
        if (this.mPendingFillPosition >= state.getItemCount()) {
            this.mPendingFillPosition = state.getItemCount() - 1;
        }
        detachAndScrapAttachedViews(recycler);
        fillLayout(recycler, state, getOffsetSpace(), 1);
        if (getChildCount() != 0) {
            this.mPendingFillPosition = getPendingFillPosition(-1);
            fillLayout(recycler, state, getAnchor(-1), -1);
        }
        if (z) {
            dispatchOnItemSelectedListener(getSelectedPosition());
        }
        transformChildren();
        dispatchOnItemFillListener();
        logDebug("width == " + getWidth() + " -- height == " + getHeight());
        logChildCount(recycler);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsChanged(RecyclerView recyclerView) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        super.onItemsChanged(recyclerView);
        logDebug("onItemsChanged");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.mPendingScrollToPosition = -1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollHorizontally() {
        return this.orientation == 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return this.orientation == 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        Intrinsics.checkNotNullParameter(recycler, "recycler");
        Intrinsics.checkNotNullParameter(state, "state");
        if (this.orientation == 1) {
            return 0;
        }
        return scrollBy(dx, recycler, state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        Intrinsics.checkNotNullParameter(recycler, "recycler");
        Intrinsics.checkNotNullParameter(state, "state");
        if (this.orientation == 0) {
            return 0;
        }
        return scrollBy(dy, recycler, state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void scrollToPosition(int position) {
        this.mPendingScrollToPosition = position;
        if (getChildCount() != 0) {
            requestLayout();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        Intrinsics.checkNotNullParameter(state, "state");
        if (getChildCount() == 0) {
            return;
        }
        checkToPosition(position);
        int fixSmoothToPosition = fixSmoothToPosition(position);
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext());
        linearSmoothScroller.setTargetPosition(fixSmoothToPosition);
        startSmoothScroll(linearSmoothScroller);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
    public PointF computeScrollVectorForPosition(int targetPosition) {
        if (getChildCount() == 0) {
            return null;
        }
        View findSnapView = this.mSnapHelper.findSnapView(this);
        Intrinsics.checkNotNull(findSnapView);
        int i = targetPosition < getPosition(findSnapView) ? -1 : 1;
        if (this.orientation == 0) {
            return new PointF(i, 0.0f);
        }
        return new PointF(0.0f, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onScrollStateChanged(int state) {
        View selectedView;
        super.onScrollStateChanged(state);
        if (getChildCount() == 0) {
            return;
        }
        logDebug("onScrollStateChanged -- " + state);
        if (state != 0 || (selectedView = getSelectedView()) == null) {
            return;
        }
        scrollToCenter(selectedView, getPosition(selectedView));
    }

    private final void fillLayout(RecyclerView.Recycler recycler, RecyclerView.State state, int anchor, int fillDirection) {
        for (int offsetCount = fillDirection == -1 ? getOffsetCount() : getFixVisibleCount(); offsetCount > 0 && hasMore(state); offsetCount--) {
            View nextView = nextView(recycler, fillDirection);
            if (fillDirection == -1) {
                addView(nextView, 0);
            } else {
                addView(nextView);
            }
            measureChildWithMargins(nextView, 0, 0);
            layoutChunk(nextView, anchor, fillDirection);
            if (fillDirection == -1) {
                anchor -= getMOrientationHelper().getDecoratedMeasurement(nextView);
            } else {
                anchor += getMOrientationHelper().getDecoratedMeasurement(nextView);
            }
        }
    }

    private final int getOffsetCount() {
        return (this.visibleCount - 1) / 2;
    }

    private final int getFixVisibleCount() {
        return this.isLoop ? this.visibleCount : (this.visibleCount + 1) / 2;
    }

    private final void layoutChunk(View child, int anchor, int fillDirection) {
        LooperPickerLayoutManager looperPickerLayoutManager;
        View view;
        int i;
        int decoratedMeasurement;
        int i2;
        int i3;
        if (this.orientation == 0) {
            int paddingTop = getPaddingTop();
            int paddingTop2 = (getPaddingTop() + getMOrientationHelper().getDecoratedMeasurementInOther(child)) - getPaddingBottom();
            if (fillDirection == -1) {
                looperPickerLayoutManager = this;
                view = child;
                i3 = anchor;
                i2 = anchor - getMOrientationHelper().getDecoratedMeasurement(child);
            } else {
                looperPickerLayoutManager = this;
                view = child;
                i2 = anchor;
                i3 = getMOrientationHelper().getDecoratedMeasurement(child) + anchor;
            }
            i = paddingTop;
            decoratedMeasurement = paddingTop2;
        } else {
            int paddingLeft = getPaddingLeft();
            int decoratedMeasurementInOther = getMOrientationHelper().getDecoratedMeasurementInOther(child) - getPaddingRight();
            if (fillDirection == -1) {
                looperPickerLayoutManager = this;
                view = child;
                decoratedMeasurement = anchor;
                i = anchor - getMOrientationHelper().getDecoratedMeasurement(child);
            } else {
                looperPickerLayoutManager = this;
                view = child;
                i = anchor;
                decoratedMeasurement = getMOrientationHelper().getDecoratedMeasurement(child) + anchor;
            }
            i2 = paddingLeft;
            i3 = decoratedMeasurementInOther;
        }
        looperPickerLayoutManager.layoutDecoratedWithMargins(view, i2, i, i3, decoratedMeasurement);
    }

    private final int scrollBy(int delta, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() == 0 || delta == 0) {
            return 0;
        }
        int fillScroll = fillScroll(delta, recycler, state);
        getMOrientationHelper().offsetChildren(-fillScroll);
        recycleChildren(delta, recycler);
        transformChildren();
        dispatchOnItemFillListener();
        logChildCount(recycler);
        return fillScroll;
    }

    private final int fillScroll(int delta, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int abs = Math.abs(delta);
        int abs2 = Math.abs(delta);
        logDebug("delta == " + delta);
        int i = delta > 0 ? 1 : -1;
        if (!canNotFillScroll(i, abs)) {
            if (checkScrollToEdge(i, state)) {
                int fixLastScroll = getFixLastScroll(i);
                if (i == -1) {
                    return Math.max(fixLastScroll, delta);
                }
                return Math.min(fixLastScroll, delta);
            }
            this.mPendingFillPosition = getPendingFillPosition(i);
            while (abs2 > 0 && hasMore(state)) {
                int anchor = getAnchor(i);
                View nextView = nextView(recycler, i);
                if (i == -1) {
                    addView(nextView, 0);
                } else {
                    addView(nextView);
                }
                measureChildWithMargins(nextView, 0, 0);
                layoutChunk(nextView, anchor, i);
                abs2 -= getMOrientationHelper().getDecoratedMeasurement(nextView);
            }
        }
        return delta;
    }

    private final boolean canNotFillScroll(int fillDirection, int delta) {
        View anchorView = getAnchorView(fillDirection);
        return fillDirection == -1 ? getMOrientationHelper().getDecoratedStart(anchorView) + delta < getMOrientationHelper().getStartAfterPadding() : getMOrientationHelper().getDecoratedEnd(anchorView) - delta > getMOrientationHelper().getEndAfterPadding();
    }

    private final boolean checkScrollToEdge(int fillDirection, RecyclerView.State state) {
        if (this.isLoop) {
            return false;
        }
        int anchorPosition = getAnchorPosition(fillDirection);
        if (fillDirection == -1 && anchorPosition == 0) {
            return true;
        }
        return fillDirection == 1 && anchorPosition == state.getItemCount() - 1;
    }

    private final int getFixLastScroll(int fillDirection) {
        View anchorView = getAnchorView(fillDirection);
        if (fillDirection == -1) {
            return (getMOrientationHelper().getDecoratedStart(anchorView) - getMOrientationHelper().getStartAfterPadding()) - getOffsetSpace();
        }
        return (getMOrientationHelper().getDecoratedEnd(anchorView) - getMOrientationHelper().getEndAfterPadding()) + getOffsetSpace();
    }

    private final boolean hasMore(RecyclerView.State state) {
        if (this.isLoop) {
            return true;
        }
        int i = this.mPendingFillPosition;
        return i >= 0 && i < state.getItemCount();
    }

    private final View getAnchorView(int fillDirection) {
        if (fillDirection == -1) {
            View childAt = getChildAt(0);
            Intrinsics.checkNotNull(childAt);
            return childAt;
        }
        View childAt2 = getChildAt(getChildCount() - 1);
        Intrinsics.checkNotNull(childAt2);
        return childAt2;
    }

    private final int getAnchorPosition(int fillDirection) {
        return getPosition(getAnchorView(fillDirection));
    }

    private final int getAnchor(int fillDirection) {
        View anchorView = getAnchorView(fillDirection);
        if (fillDirection == -1) {
            return getMOrientationHelper().getDecoratedStart(anchorView);
        }
        return getMOrientationHelper().getDecoratedEnd(anchorView);
    }

    private final int getPendingFillPosition(int fillDirection) {
        return getAnchorPosition(fillDirection) + fillDirection;
    }

    private final View nextView(RecyclerView.Recycler recycler, int fillDirection) {
        View viewForPosition = getViewForPosition(recycler, this.mPendingFillPosition);
        this.mPendingFillPosition += fillDirection;
        return viewForPosition;
    }

    private final void recycleChildren(int delta, RecyclerView.Recycler recycler) {
        if (delta > 0) {
            recycleStart();
        } else {
            recycleEnd();
        }
        logRecycleChildren();
        Iterator<View> it = this.mRecycleViews.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            View next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            removeAndRecycleView(next, recycler);
        }
        this.mRecycleViews.clear();
    }

    private final void recycleStart() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            Intrinsics.checkNotNull(childAt);
            if (getMOrientationHelper().getDecoratedEnd(childAt) >= getMOrientationHelper().getStartAfterPadding() - getItemOffset()) {
                return;
            }
            this.mRecycleViews.add(childAt);
        }
    }

    private final void recycleEnd() {
        int childCount = getChildCount();
        while (true) {
            childCount--;
            if (-1 >= childCount) {
                return;
            }
            View childAt = getChildAt(childCount);
            Intrinsics.checkNotNull(childAt);
            if (getMOrientationHelper().getDecoratedStart(childAt) <= getMOrientationHelper().getEndAfterPadding() + getItemOffset()) {
                return;
            } else {
                this.mRecycleViews.add(childAt);
            }
        }
    }

    private final View getSelectedView() {
        return this.mSnapHelper.findSnapView(this);
    }

    private final int getItemSpace() {
        if (this.orientation == 0) {
            return this.mItemWidth;
        }
        return this.mItemHeight;
    }

    private final int getItemOffset() {
        return getItemSpace() / 2;
    }

    private final int getOffsetSpace() {
        return getOffsetCount() * getItemSpace();
    }

    private final View getViewForPosition(RecyclerView.Recycler recycler, int position) {
        if (!this.isLoop && (position < 0 || position >= getItemCount())) {
            throw new IllegalArgumentException("position <0 or >= itemCount with !isLoop");
        }
        if (this.isLoop && position > getItemCount() - 1) {
            View viewForPosition = recycler.getViewForPosition(position % getItemCount());
            Intrinsics.checkNotNullExpressionValue(viewForPosition, "getViewForPosition(...)");
            return viewForPosition;
        }
        if (this.isLoop && position < 0) {
            View viewForPosition2 = recycler.getViewForPosition(getItemCount() + position);
            Intrinsics.checkNotNullExpressionValue(viewForPosition2, "getViewForPosition(...)");
            return viewForPosition2;
        }
        View viewForPosition3 = recycler.getViewForPosition(position);
        Intrinsics.checkNotNullExpressionValue(viewForPosition3, "getViewForPosition(...)");
        return viewForPosition3;
    }

    private final void checkToPosition(int position) {
        if (position < 0 || position > getItemCount() - 1) {
            throw new IllegalArgumentException("position is " + position + ",must be >= 0 and < itemCount,");
        }
    }

    private final int fixSmoothToPosition(int toPosition) {
        int offsetCount = getOffsetCount();
        return getSelectedPosition() < toPosition ? toPosition + offsetCount : toPosition - offsetCount;
    }

    static /* synthetic */ void dispatchOnItemSelectedListener$default(LooperPickerLayoutManager looperPickerLayoutManager, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: dispatchOnItemSelectedListener");
        }
        if ((i2 & 1) != 0) {
            i = looperPickerLayoutManager.getSelectedPosition();
        }
        looperPickerLayoutManager.dispatchOnItemSelectedListener(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dispatchOnItemSelectedListener(int position) {
        if (this.mOnItemSelectedListener.isEmpty() || position < 0) {
            return;
        }
        Iterator<Function1<Integer, Unit>> it = this.mOnItemSelectedListener.iterator();
        while (it.hasNext()) {
            it.next().invoke(Integer.valueOf(position));
        }
    }

    private final void scrollToCenter(View centerView, int centerPosition) {
        getMOrientationHelper().offsetChildren(((getMOrientationHelper().getTotalSpace() / 2) - (getMOrientationHelper().getDecoratedMeasurement(centerView) / 2)) - getMOrientationHelper().getDecoratedStart(centerView));
        dispatchOnItemSelectedListener(centerPosition);
    }

    private final void smoothOffsetChildren(int amount, final int centerPosition) {
        final Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = amount;
        ValueAnimator ofInt = ValueAnimator.ofInt(amount, 0);
        ofInt.setInterpolator(new LinearInterpolator());
        ofInt.setDuration(300L);
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.ltech.smarthome.view.layoutmanager.LooperPickerLayoutManager$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                LooperPickerLayoutManager.smoothOffsetChildren$lambda$2(LooperPickerLayoutManager.this, intRef, valueAnimator);
            }
        });
        ofInt.addListener(new Animator.AnimatorListener() { // from class: com.ltech.smarthome.view.layoutmanager.LooperPickerLayoutManager$smoothOffsetChildren$2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                LooperPickerLayoutManager.this.dispatchOnItemSelectedListener(centerPosition);
            }
        });
        ofInt.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void smoothOffsetChildren$lambda$2(LooperPickerLayoutManager this$0, Ref.IntRef lastValue, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(lastValue, "$lastValue");
        Intrinsics.checkNotNullParameter(it, "it");
        Object animatedValue = it.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        int intValue = ((Integer) animatedValue).intValue();
        this$0.getMOrientationHelper().offsetChildren(lastValue.element - intValue);
        lastValue.element = intValue;
    }

    public final void addOnItemSelectedListener(Function1<? super Integer, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mOnItemSelectedListener.add(listener);
    }

    public final void removeOnItemSelectedListener(Function1<? super Integer, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mOnItemSelectedListener.remove(listener);
    }

    public final void removeAllOnItemSelectedListener() {
        this.mOnItemSelectedListener.clear();
    }

    public final int getSelectedPosition() {
        View selectedView;
        if (getChildCount() == 0 || (selectedView = getSelectedView()) == null) {
            return -1;
        }
        return getPosition(selectedView);
    }

    public void transformChildren() {
        View selectedView;
        if (getChildCount() == 0 || (selectedView = getSelectedView()) == null) {
            return;
        }
        int position = getPosition(selectedView);
        if (getChildCount() == 0) {
            return;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            Intrinsics.checkNotNull(childAt);
            int position2 = getPosition(childAt);
            if (position2 == position) {
                childAt.setScaleX(1.0f);
                childAt.setScaleY(1.0f);
                childAt.setAlpha(1.0f);
            } else {
                float scale = getScale(this.scaleX, getIntervalCount(position, position2));
                float scale2 = getScale(this.scaleY, getIntervalCount(position, position2));
                childAt.setScaleX(scale);
                childAt.setScaleY(scale2);
                childAt.setAlpha(this.alpha);
            }
        }
    }

    private final int getIntervalCount(int centerPosition, int position) {
        if (!this.isLoop) {
            return Math.abs(centerPosition - position);
        }
        if (position <= centerPosition || position - centerPosition <= this.visibleCount) {
            return (position >= centerPosition || centerPosition - position <= this.visibleCount) ? Math.abs(position - centerPosition) : position + 1;
        }
        return getItemCount() - position;
    }

    private final void dispatchOnItemFillListener() {
        View selectedView;
        if (getChildCount() == 0 || this.mOnItemFillListener.isEmpty() || (selectedView = getSelectedView()) == null) {
            return;
        }
        int position = getPosition(selectedView);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt != null) {
                int position2 = getPosition(childAt);
                if (position2 == position) {
                    onItemSelected(childAt, position2);
                } else {
                    onItemUnSelected(childAt, position2);
                }
            }
        }
    }

    public void onItemSelected(View child, int position) {
        Intrinsics.checkNotNullParameter(child, "child");
        Iterator<OnItemFillListener> it = this.mOnItemFillListener.iterator();
        while (it.hasNext()) {
            it.next().onItemSelected(child, position);
        }
    }

    public void onItemUnSelected(View child, int position) {
        Intrinsics.checkNotNullParameter(child, "child");
        Iterator<OnItemFillListener> it = this.mOnItemFillListener.iterator();
        while (it.hasNext()) {
            it.next().onItemUnSelected(child, position);
        }
    }

    public final void addOnItemFillListener(OnItemFillListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mOnItemFillListener.add(listener);
    }

    public final void removeOnItemFillListener(OnItemFillListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mOnItemFillListener.remove(listener);
    }

    public final void removeAllItemFillListener() {
        this.mOnItemFillListener.clear();
    }

    /* compiled from: LooperPickerLayoutManager.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u0005J\u000e\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0006\u001a\u00020\u0005J\u000e\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\u0011\u001a\u00020\u000e2\b\b\u0001\u0010\t\u001a\u00020\nJ\u0010\u0010\u0012\u001a\u00020\u000e2\b\b\u0001\u0010\u000b\u001a\u00020\nJ\u0010\u0010\u0013\u001a\u00020\u000e2\b\b\u0001\u0010\f\u001a\u00020\nJ\u0006\u0010\u0014\u001a\u00020\u0015R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/ltech/smarthome/view/layoutmanager/LooperPickerLayoutManager$Builder;", "", "<init>", "()V", "orientation", "", "visibleCount", "isLoop", "", "scaleX", "", "scaleY", "alpha", "setOrientation", "", "setVisibleCount", "setIsLoop", "setScaleX", "setScaleY", "setAlpha", "build", "Lcom/ltech/smarthome/view/layoutmanager/LooperPickerLayoutManager;", "app_yingyongbaoRelease"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public static final class Builder {
        private boolean isLoop;
        private int orientation = 1;
        private int visibleCount = 3;
        private float scaleX = 1.0f;
        private float scaleY = 1.0f;
        private float alpha = 1.0f;

        public final void setOrientation(int orientation) {
            this.orientation = orientation;
        }

        public final void setVisibleCount(int visibleCount) {
            this.visibleCount = visibleCount;
        }

        public final void setIsLoop(boolean isLoop) {
            this.isLoop = isLoop;
        }

        public final void setScaleX(float scaleX) {
            this.scaleX = scaleX;
        }

        public final void setScaleY(float scaleY) {
            this.scaleY = scaleY;
        }

        public final void setAlpha(float alpha) {
            this.alpha = alpha;
        }

        public final LooperPickerLayoutManager build() {
            return new LooperPickerLayoutManager(this.orientation, this.visibleCount, this.isLoop, this.scaleX, this.scaleY, this.alpha);
        }
    }

    private final void logDebug(String msg) {
        if (DEBUG) {
            Log.d(TAG, hashCode() + " -- " + msg);
        }
    }

    private final void logChildCount(RecyclerView.Recycler recycler) {
        if (DEBUG) {
            logDebug("childCount == " + getChildCount() + " -- scrapSize == " + recycler.getScrapList().size());
            logChildrenPosition();
        }
    }

    private final void logChildrenPosition() {
        if (DEBUG) {
            StringBuilder sb = new StringBuilder();
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                Intrinsics.checkNotNull(childAt);
                sb.append(getPosition(childAt));
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
            }
            logDebug("children == " + ((Object) sb));
        }
    }

    private final void logRecycleChildren() {
        if (DEBUG) {
            StringBuilder sb = new StringBuilder();
            Iterator<View> it = this.mRecycleViews.iterator();
            Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
            while (it.hasNext()) {
                View next = it.next();
                Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                sb.append(getPosition(next));
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
            }
            if (sb.length() == 0) {
                return;
            }
            logDebug("recycle children == " + ((Object) sb));
        }
    }
}