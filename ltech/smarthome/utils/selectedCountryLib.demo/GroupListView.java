package com.ltech.smarthome.utils.selectedCountryLib.demo;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class GroupListView extends RelativeLayout {
    private GroupAdapter adapter;
    private int curFirstItem;
    private InnerAdapter innerAdapter;
    private ListView lvBody;
    private OnItemClickListener oicListener;
    private AbsListView.OnScrollListener osListener;
    private int titleHeight;
    private View tvTitle;

    public interface OnItemClickListener {
        void onItemClick(GroupListView parent, View view, int group, int position);
    }

    public GroupListView(Context context) {
        super(context);
        init(context);
    }

    public GroupListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GroupListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        ListView listView = new ListView(context);
        this.lvBody = listView;
        listView.setCacheColorHint(0);
        this.lvBody.setSelector(new ColorDrawable());
        this.lvBody.setVerticalScrollBarEnabled(false);
        this.lvBody.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: com.ltech.smarthome.utils.selectedCountryLib.demo.GroupListView.1
            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (GroupListView.this.osListener != null) {
                    GroupListView.this.osListener.onScrollStateChanged(view, scrollState);
                }
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                GroupListView.this.curFirstItem = firstVisibleItem;
                if (GroupListView.this.tvTitle != null) {
                    GroupListView.this.onScroll();
                }
                if (GroupListView.this.osListener != null) {
                    GroupListView.this.osListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
            }
        });
        this.lvBody.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.ltech.smarthome.utils.selectedCountryLib.demo.GroupListView.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                if (GroupListView.this.oicListener != null) {
                    GroupListView.this.oicListener.onItemClick(GroupListView.this, view, GroupListView.this.innerAdapter.getItemGroup(position), (position - ((Integer) GroupListView.this.innerAdapter.titleIndex.get(r1)).intValue()) - 1);
                }
            }
        });
        this.lvBody.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        addView(this.lvBody);
    }

    public void setDividerHeight(int height) {
        this.lvBody.setDividerHeight(height);
    }

    public void setDivider(Drawable divider) {
        this.lvBody.setDivider(divider);
    }

    public void setAdapter(GroupAdapter adapter) {
        this.adapter = adapter;
        InnerAdapter innerAdapter = new InnerAdapter(adapter);
        this.innerAdapter = innerAdapter;
        this.lvBody.setAdapter((ListAdapter) innerAdapter);
        setTitle();
    }

    public GroupAdapter getAdapter() {
        return this.adapter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDataSetChanged() {
        this.innerAdapter.notifyDataSetChanged();
        setTitle();
    }

    private void setTitle() {
        View view = this.tvTitle;
        if (view != null) {
            removeView(view);
        }
        if (this.innerAdapter.getCount() == 0) {
            return;
        }
        this.tvTitle = this.innerAdapter.getView(((Integer) this.innerAdapter.titleIndex.get(this.innerAdapter.getItemGroup(this.curFirstItem))).intValue(), null, this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(9);
        layoutParams.addRule(10);
        addView(this.tvTitle, layoutParams);
        this.tvTitle.measure(0, 0);
        this.titleHeight = this.tvTitle.getMeasuredHeight();
        onScroll();
    }

    public void setSelection(int group) {
        setSelection(group, -1);
    }

    public void setSelection(int group, int position) {
        this.lvBody.setSelection(((Integer) this.innerAdapter.titleIndex.get(group)).intValue() + position + 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScroll() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.tvTitle.getLayoutParams();
        if (this.innerAdapter.isLastItem(this.curFirstItem)) {
            this.adapter.onGroupChange(this.tvTitle, this.adapter.getGroupTitle(this.innerAdapter.getItemGroup(this.curFirstItem)));
            int top = this.lvBody.getChildAt(1).getTop();
            int i = this.titleHeight;
            if (top < i) {
                layoutParams.setMargins(0, top - i, 0, 0);
                this.tvTitle.setLayoutParams(layoutParams);
                return;
            }
        }
        layoutParams.topMargin = 0;
        this.tvTitle.setLayoutParams(layoutParams);
        if (this.innerAdapter.isTitle(this.curFirstItem)) {
            this.adapter.onGroupChange(this.tvTitle, this.adapter.getGroupTitle(this.innerAdapter.getItemGroup(this.curFirstItem)));
        }
    }

    public void setOnScrollListener(AbsListView.OnScrollListener l) {
        this.osListener = l;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.oicListener = listener;
    }

    private static class InnerAdapter extends BaseAdapter {
        private GroupAdapter adapter;
        private ArrayList<Object> listData = new ArrayList<>();
        private ArrayList<Integer> titleIndex = new ArrayList<>();
        private ArrayList<Integer> lastItemIndex = new ArrayList<>();

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return position;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public int getViewTypeCount() {
            return 2;
        }

        public InnerAdapter(GroupAdapter adapter) {
            this.adapter = adapter;
            init();
        }

        private void init() {
            this.listData.clear();
            this.titleIndex.clear();
            this.lastItemIndex.clear();
            int groupCount = this.adapter.getGroupCount();
            for (int i = 0; i < groupCount; i++) {
                int count = this.adapter.getCount(i);
                if (count > 0) {
                    this.titleIndex.add(Integer.valueOf(this.listData.size()));
                    this.listData.add(this.adapter.getGroupTitle(i));
                    for (int i2 = 0; i2 < count; i2++) {
                        this.listData.add(this.adapter.getItem(i, i2));
                    }
                    this.lastItemIndex.add(Integer.valueOf(this.listData.size() - 1));
                }
            }
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.listData.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int position) {
            return this.listData.get(position);
        }

        public int getItemGroup(int position) {
            int size = this.titleIndex.size();
            for (int i = 0; i < size; i++) {
                if (position < this.titleIndex.get(i).intValue()) {
                    return i - 1;
                }
            }
            return size - 1;
        }

        public boolean isTitle(int position) {
            int size = this.titleIndex.size();
            for (int i = 0; i < size; i++) {
                if (this.titleIndex.get(i).intValue() == position) {
                    return true;
                }
            }
            return false;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public int getItemViewType(int i) {
            return !isTitle(i) ? 1 : 0;
        }

        @Override // android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            int itemGroup = getItemGroup(position);
            if (!isTitle(position)) {
                return this.adapter.getView(itemGroup, (position - this.titleIndex.get(itemGroup).intValue()) - 1, convertView, parent);
            }
            if (convertView != null) {
                return this.adapter.getTitleView(itemGroup, convertView, parent);
            }
            return this.adapter.getTitleView(itemGroup, null, parent);
        }

        @Override // android.widget.BaseAdapter
        public void notifyDataSetChanged() {
            init();
            super.notifyDataSetChanged();
        }

        public boolean isLastItem(int position) {
            int size = this.lastItemIndex.size();
            for (int i = 0; i < size; i++) {
                if (this.lastItemIndex.get(i).intValue() == position) {
                    return true;
                }
            }
            return false;
        }
    }

    public static abstract class GroupAdapter {
        protected final GroupListView view;

        public abstract int getCount(int group);

        public abstract int getGroupCount();

        public abstract String getGroupTitle(int group);

        public abstract Object getItem(int group, int position);

        public abstract View getTitleView(int group, View convertView, ViewGroup parent);

        public abstract View getView(int group, int position, View convertView, ViewGroup parent);

        public abstract void onGroupChange(View titleView, String title);

        public GroupAdapter(GroupListView view) {
            this.view = view;
        }

        public void notifyDataSetChanged() {
            this.view.notifyDataSetChanged();
        }
    }
}