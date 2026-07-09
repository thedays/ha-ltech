package com.ltech.smarthome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import com.blankj.utilcode.util.NetworkUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.view.MultipleStatusView;

/* loaded from: classes3.dex */
public class DefaultAdapter implements Gloading.Adapter {
    protected static final RelativeLayout.LayoutParams DEFAULT_LAYOUT_PARAMS = new RelativeLayout.LayoutParams(-1, -1);
    protected String loadFailString;
    protected int loadFailStringRes = R.string.error_loading;
    protected int noNetworkStringRes = R.string.no_network;
    protected int emptyStringRes = R.string.no_data;
    protected int emptyTryStringRes = -1;
    protected int errorTryStringRes = -1;
    protected int emptyImageRes = R.mipmap.pic_empty_1;
    protected int errorImageRes = R.mipmap.pic_empty_2;
    protected int bgRes = R.color.color_bg_1;
    private boolean rootViewClickEnable = true;

    @Override // com.ltech.smarthome.adapter.Gloading.Adapter
    public View getView(final Gloading.Holder holder, View convertView, int status) {
        if (convertView == null) {
            convertView = new MultipleStatusView(holder.getContext());
        }
        if (status == 1) {
            ((MultipleStatusView) convertView).showLoading(getLoadingView(holder.getContext()), DEFAULT_LAYOUT_PARAMS);
            return convertView;
        }
        if (status == 2) {
            ((MultipleStatusView) convertView).showContent();
            return convertView;
        }
        if (status == 3) {
            MultipleStatusView multipleStatusView = (MultipleStatusView) convertView;
            multipleStatusView.setOnRetryClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.adapter.DefaultAdapter$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Gloading.Holder.this.getRetryTask().run();
                }
            });
            multipleStatusView.showError(getErrorView(holder.getContext()), DEFAULT_LAYOUT_PARAMS);
            return convertView;
        }
        if (status != 4) {
            return convertView;
        }
        MultipleStatusView multipleStatusView2 = (MultipleStatusView) convertView;
        multipleStatusView2.setOnRetryClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.adapter.DefaultAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Gloading.Holder.this.getEmptyTask().run();
            }
        });
        multipleStatusView2.showEmpty(getEmptyView(holder.getContext()), DEFAULT_LAYOUT_PARAMS);
        return convertView;
    }

    @Override // com.ltech.smarthome.adapter.Gloading.Adapter
    public void enableRootViewClick(boolean b2) {
        this.rootViewClickEnable = b2;
    }

    public View getLoadingView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_loading, (ViewGroup) null);
        inflate.findViewById(R.id.loading_view).setBackgroundResource(this.bgRes);
        inflate.setClickable(!this.rootViewClickEnable);
        inflate.setFocusable(!this.rootViewClickEnable);
        return inflate;
    }

    public View getErrorView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_error, (ViewGroup) null);
        inflate.findViewById(R.id.error_view).setBackgroundResource(this.bgRes);
        inflate.findViewById(R.id.iv_error).setBackgroundResource(this.errorImageRes);
        inflate.setClickable(!this.rootViewClickEnable);
        inflate.setFocusable(!this.rootViewClickEnable);
        if (inflate.findViewById(R.id.tv_error_tip) != null) {
            if (NetworkUtils.isConnected()) {
                ((AppCompatTextView) inflate.findViewById(R.id.tv_error_tip)).setText(this.loadFailStringRes == -1 ? this.loadFailString : context.getString(getLoadFailStringRes()));
            } else {
                ((AppCompatTextView) inflate.findViewById(R.id.tv_error_tip)).setText(context.getString(getNoNetworkStringRes()));
            }
        }
        if (inflate.findViewById(R.id.error_retry_view) != null && this.errorTryStringRes > -1) {
            ((AppCompatButton) inflate.findViewById(R.id.error_retry_view)).setText(context.getString(getErrorTryStringRes()));
            inflate.findViewById(R.id.error_retry_view).setVisibility(0);
        }
        return inflate;
    }

    public View getEmptyView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_empty, (ViewGroup) null);
        inflate.findViewById(R.id.empty_view).setBackgroundResource(this.bgRes);
        inflate.setClickable(!this.rootViewClickEnable);
        inflate.setFocusable(!this.rootViewClickEnable);
        if (inflate.findViewById(R.id.tv_empty_tip) != null) {
            ((AppCompatTextView) inflate.findViewById(R.id.tv_empty_tip)).setText(getEmptyStringRes());
        }
        if (inflate.findViewById(R.id.iv_empty) != null) {
            inflate.findViewById(R.id.iv_empty).setBackgroundResource(getEmptyImageRes());
        }
        if (inflate.findViewById(R.id.empty_retry_view) != null) {
            if (this.emptyTryStringRes > -1) {
                ((AppCompatButton) inflate.findViewById(R.id.empty_retry_view)).setText(context.getString(getEmptyTryStringRes()));
                inflate.findViewById(R.id.empty_retry_view).setVisibility(0);
                return inflate;
            }
            inflate.findViewById(R.id.empty_retry_view).setVisibility(8);
        }
        return inflate;
    }

    public DefaultAdapter loadFailStringRes(int res) {
        this.loadFailStringRes = res;
        return this;
    }

    public DefaultAdapter loadFailString(String content) {
        this.loadFailStringRes = -1;
        this.loadFailString = content;
        return this;
    }

    public DefaultAdapter noNetworkStringRes(int res) {
        this.noNetworkStringRes = res;
        return this;
    }

    public DefaultAdapter emptyStringRes(int res) {
        this.emptyStringRes = res;
        return this;
    }

    public DefaultAdapter errorTryStringRes(int res) {
        this.errorTryStringRes = res;
        return this;
    }

    public DefaultAdapter emptyTryStringRes(int res) {
        this.emptyTryStringRes = res;
        return this;
    }

    public DefaultAdapter emptyImageRes(int res) {
        this.emptyImageRes = res;
        return this;
    }

    public DefaultAdapter errorImageRes(int res) {
        this.errorImageRes = res;
        return this;
    }

    public DefaultAdapter bgRes(int res) {
        this.bgRes = res;
        return this;
    }

    public int getLoadFailStringRes() {
        return this.loadFailStringRes;
    }

    public int getNoNetworkStringRes() {
        return this.noNetworkStringRes;
    }

    public int getEmptyStringRes() {
        return this.emptyStringRes;
    }

    public int getEmptyTryStringRes() {
        return this.emptyTryStringRes;
    }

    public int getErrorTryStringRes() {
        return this.errorTryStringRes;
    }

    public int getEmptyImageRes() {
        return this.emptyImageRes;
    }
}