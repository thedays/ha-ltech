package com.ltech.smarthome.base;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.databinding.ViewDataBinding;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;

/* loaded from: classes3.dex */
public abstract class SmartDialog<V extends ViewDataBinding> extends BaseDialog<V> {
    private ViewConverter mViewConverter;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected void convertView(V viewBinding, BaseDialog dialog) {
        ViewConverter viewConverter = this.mViewConverter;
        if (viewConverter != null) {
            viewConverter.convertView(viewBinding, this);
        }
    }

    public void notifyDialog() {
        ViewConverter viewConverter = this.mViewConverter;
        if (viewConverter != null) {
            viewConverter.convertView(this.mViewBinding, this);
        }
    }

    public SmartDialog setViewConverter(ViewConverter viewConverter) {
        this.mViewConverter = viewConverter;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.mViewConverter = (ViewConverter) savedInstanceState.getParcelable(ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, this.mViewConverter);
    }

    @Override // com.ltech.smarthome.base.BaseDialog, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.mViewConverter = null;
    }

    public static abstract class ViewConverter<V, D extends BaseDialog> implements Parcelable {
        public static final Parcelable.Creator<ViewConverter> CREATOR = new Parcelable.Creator<ViewConverter>() { // from class: com.ltech.smarthome.base.SmartDialog.ViewConverter.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ViewConverter createFromParcel(Parcel source) {
                return new ViewConverter(this, source) { // from class: com.ltech.smarthome.base.SmartDialog.ViewConverter.1.1
                    @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
                    protected void convertView(Object viewBinding, BaseDialog dialog) {
                    }
                };
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ViewConverter[] newArray(int size) {
                return new ViewConverter[size];
            }
        };

        protected abstract void convertView(V viewBinding, D dialog);

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
        }

        public ViewConverter() {
        }

        protected ViewConverter(Parcel in) {
        }
    }
}