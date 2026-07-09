package com.ltech.smarthome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.view.MultipleStatusView;

/* loaded from: classes3.dex */
public class RoomLoadingAdapter extends DefaultAdapter {
    public static final int STATUS_NO_FLOOR = 66;
    private IRetryCallback iRetryCallback;
    protected int noFloorStringRes = R.string.no_data;
    protected int noFloorTryStringRes = -1;
    protected int noFloorImageRes = R.mipmap.pic_empty_1;

    public interface IRetryCallback {
        void retry(int status);
    }

    @Override // com.ltech.smarthome.adapter.DefaultAdapter, com.ltech.smarthome.adapter.Gloading.Adapter
    public View getView(Gloading.Holder holder, View convertView, int status) {
        if (status == 66) {
            if (convertView == null) {
                convertView = new MultipleStatusView(holder.getContext());
            }
            ((MultipleStatusView) convertView).showExtView(getSetNetworkView(holder.getContext(), status), DEFAULT_LAYOUT_PARAMS);
            return convertView;
        }
        return super.getView(holder, convertView, status);
    }

    public View getSetNetworkView(Context context, final int status) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_ext, (ViewGroup) null);
        inflate.findViewById(R.id.ext_view).setBackgroundResource(this.bgRes);
        if (inflate.findViewById(R.id.tv_ext_tip) != null) {
            ((AppCompatTextView) inflate.findViewById(R.id.tv_ext_tip)).setText(getNoFloorStringRes());
        }
        if (inflate.findViewById(R.id.iv_ext) != null) {
            inflate.findViewById(R.id.iv_ext).setBackgroundResource(getNoFloorImageRes());
        }
        if (inflate.findViewById(R.id.ext_retry_view) != null) {
            if (this.noFloorTryStringRes > -1) {
                ((AppCompatButton) inflate.findViewById(R.id.ext_retry_view)).setText(context.getString(getNoFloorTryStringRes()));
                inflate.findViewById(R.id.ext_retry_view).setVisibility(0);
                inflate.findViewById(R.id.ext_retry_view).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.adapter.RoomLoadingAdapter$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        RoomLoadingAdapter.this.lambda$getSetNetworkView$0(status, view);
                    }
                });
                return inflate;
            }
            inflate.findViewById(R.id.ext_retry_view).setVisibility(8);
        }
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSetNetworkView$0(int i, View view) {
        IRetryCallback iRetryCallback = this.iRetryCallback;
        if (iRetryCallback != null) {
            iRetryCallback.retry(i);
        }
    }

    public int getNoFloorStringRes() {
        return this.noFloorStringRes;
    }

    public RoomLoadingAdapter setNoFloorStringRes(int noFloorStringRes) {
        this.noFloorStringRes = noFloorStringRes;
        return this;
    }

    public int getNoFloorTryStringRes() {
        return this.noFloorTryStringRes;
    }

    public RoomLoadingAdapter setNoFloorTryStringRes(int noFloorTryStringRes) {
        this.noFloorTryStringRes = noFloorTryStringRes;
        return this;
    }

    public int getNoFloorImageRes() {
        return this.noFloorImageRes;
    }

    public RoomLoadingAdapter setNoFloorImageRes(int noFloorImageRes) {
        this.noFloorImageRes = noFloorImageRes;
        return this;
    }

    public RoomLoadingAdapter setRetryCallback(IRetryCallback iRetryCallback) {
        this.iRetryCallback = iRetryCallback;
        return this;
    }
}