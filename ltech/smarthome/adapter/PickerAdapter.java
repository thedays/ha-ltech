package com.ltech.smarthome.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.BaseAdapter;

/* loaded from: classes3.dex */
public final class PickerAdapter extends MyAdapter<String> {
    private int layoutId;
    private int position;

    public PickerAdapter(Context context) {
        super(context);
        this.layoutId = R.layout.item_pick;
    }

    public PickerAdapter(Context context, int layout) {
        super(context);
        this.layoutId = layout;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    public void setPosition(int position) {
        this.position = position;
        notifyItemChanged(position);
    }

    public int getPosition() {
        return this.position;
    }

    private final class ViewHolder extends BaseAdapter.ViewHolder {
        private final TextView mPickerView;

        ViewHolder() {
            super(PickerAdapter.this, PickerAdapter.this.layoutId);
            this.mPickerView = (TextView) findViewById(R.id.tv_picker_name);
        }

        @Override // com.ltech.smarthome.adapter.BaseAdapter.ViewHolder
        public void onBindView(int position) {
            this.mPickerView.setText(PickerAdapter.this.getItem(position));
        }
    }
}