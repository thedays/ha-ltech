package com.ltech.smarthome.adapter;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import com.smart.message.utils.LHomeLog;

/* loaded from: classes3.dex */
public class Gloading {
    private static boolean DEBUG = false;
    public static final int STATUS_EMPTY_DATA = 4;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_LOAD_FAILED = 3;
    public static final int STATUS_LOAD_SUCCESS = 2;
    private static volatile Gloading mDefault;
    private Adapter mAdapter;

    public interface Adapter {
        void enableRootViewClick(boolean b2);

        View getView(Holder holder, View convertView, int status);
    }

    public static void debug(boolean debug) {
        DEBUG = debug;
    }

    private Gloading() {
    }

    public static Gloading from(Adapter adapter) {
        Gloading gloading = new Gloading();
        gloading.mAdapter = adapter;
        return gloading;
    }

    public static Gloading getDefault() {
        if (mDefault == null) {
            synchronized (Gloading.class) {
                if (mDefault == null) {
                    mDefault = new Gloading();
                }
            }
        }
        return mDefault;
    }

    public static void initDefault(Adapter adapter) {
        getDefault().mAdapter = adapter;
    }

    public Holder wrap(Activity activity) {
        return new Holder(this.mAdapter, activity, (ViewGroup) activity.findViewById(R.id.content));
    }

    public Holder wrap(View view) {
        FrameLayout frameLayout = new FrameLayout(view.getContext());
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null) {
            frameLayout.setLayoutParams(layoutParams);
        }
        if (view.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            int indexOfChild = viewGroup.indexOfChild(view);
            viewGroup.removeView(view);
            viewGroup.addView(frameLayout, indexOfChild);
        }
        frameLayout.addView(view, new FrameLayout.LayoutParams(-1, -1));
        return new Holder(this.mAdapter, view.getContext(), frameLayout);
    }

    public Holder cover(View view) {
        ViewParent parent = view.getParent();
        if (parent == null) {
            throw new RuntimeException("view has no parent to show gloading as cover!");
        }
        FrameLayout frameLayout = new FrameLayout(view.getContext());
        ((ViewGroup) parent).addView(frameLayout, view.getLayoutParams());
        return new Holder(this.mAdapter, view.getContext(), frameLayout);
    }

    public static class Holder {
        private int curState;
        private Adapter mAdapter;
        private Context mContext;
        private View mCurStatusView;
        private Object mData;
        private Runnable mEmptyTask;
        private Runnable mRetryTask;
        private SparseArray<View> mStatusViews;
        private ViewGroup mWrapper;

        private Holder(Adapter adapter, Context context, ViewGroup wrapper) {
            this.mStatusViews = new SparseArray<>(4);
            this.mAdapter = adapter;
            this.mContext = context;
            this.mWrapper = wrapper;
        }

        public Holder withRetry(Runnable task) {
            this.mRetryTask = task;
            return this;
        }

        public Holder withEmpty(Runnable task) {
            this.mEmptyTask = task;
            return this;
        }

        public Holder withData(Object data) {
            this.mData = data;
            return this;
        }

        public void showLoading() {
            showLoadingStatus(1);
        }

        public void showLoadSuccess() {
            showLoadingStatus(2);
        }

        public void showLoadFailed() {
            showLoadingStatus(3);
        }

        public void showEmpty() {
            showLoadingStatus(4);
        }

        public void setAdapter(Adapter mAdapter) {
            this.mAdapter = mAdapter;
        }

        public void showLoadingStatus(int status) {
            if (this.curState == status || !validate()) {
                return;
            }
            this.curState = status;
            View view = this.mStatusViews.get(status);
            if (view == null) {
                view = this.mCurStatusView;
            }
            try {
                View view2 = this.mAdapter.getView(this, view, status);
                if (view2 == null) {
                    Gloading.printLog(this.mAdapter.getClass().getName() + ".getView returns null");
                    return;
                }
                if (view2 == this.mCurStatusView && this.mWrapper.indexOfChild(view2) >= 0) {
                    if (this.mWrapper.indexOfChild(view2) != this.mWrapper.getChildCount() - 1) {
                        view2.bringToFront();
                    }
                    this.mCurStatusView = view2;
                    this.mStatusViews.put(status, view2);
                }
                View view3 = this.mCurStatusView;
                if (view3 != null) {
                    this.mWrapper.removeView(view3);
                }
                view2.setElevation(Float.MAX_VALUE);
                this.mWrapper.addView(view2);
                ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.width = -1;
                    layoutParams.height = -1;
                }
                this.mCurStatusView = view2;
                this.mStatusViews.put(status, view2);
            } catch (Exception e) {
                if (Gloading.DEBUG) {
                    e.printStackTrace();
                }
            }
        }

        private boolean validate() {
            if (this.mAdapter == null) {
                Gloading.printLog("Gloading.Adapter is not specified.");
            }
            if (this.mContext == null) {
                Gloading.printLog("Context is null.");
            }
            if (this.mWrapper == null) {
                Gloading.printLog("The mWrapper of loading status view is null.");
            }
            return (this.mAdapter == null || this.mContext == null || this.mWrapper == null) ? false : true;
        }

        public Context getContext() {
            return this.mContext;
        }

        public ViewGroup getWrapper() {
            return this.mWrapper;
        }

        public Runnable getRetryTask() {
            return this.mRetryTask;
        }

        public Runnable getEmptyTask() {
            return this.mEmptyTask;
        }

        public <T> T getData() {
            try {
                return (T) this.mData;
            } catch (Exception e) {
                if (!Gloading.DEBUG) {
                    return null;
                }
                e.printStackTrace();
                return null;
            }
        }

        public void enableRootViewClick(boolean b2) {
            this.mAdapter.enableRootViewClick(b2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void printLog(String msg) {
        if (DEBUG) {
            LHomeLog.i(Gloading.class, msg);
        }
    }
}