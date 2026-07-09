package com.ltech.smarthome.utils.selectedCountryLib.demo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.utils.selectedCountryLib.demo.GroupListView;

/* loaded from: classes4.dex */
public class CountryListView extends RelativeLayout implements View.OnTouchListener {
    private CountryAdapter adapter;
    private LinearLayout llScroll;
    private GroupListView lvContries;
    private TextView tvScroll;

    public CountryListView(Context context) {
        super(context);
        init(context);
    }

    public CountryListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CountryListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        try {
            GroupListView groupListView = new GroupListView(context);
            this.lvContries = groupListView;
            groupListView.setDividerHeight(1);
            this.lvContries.setDivider(context.getResources().getDrawable(R.mipmap.cl_divider));
            CountryAdapter countryAdapter = new CountryAdapter(context, this.lvContries);
            this.adapter = countryAdapter;
            this.lvContries.setAdapter(countryAdapter);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            int dipToPx = dipToPx(context, 9.0f);
            layoutParams.setMargins(dipToPx, 0, dipToPx, 0);
            addView(this.lvContries, layoutParams);
            TextView textView = new TextView(context);
            this.tvScroll = textView;
            textView.setTextColor(context.getResources().getColor(R.color.white));
            this.tvScroll.setBackgroundResource(R.drawable.country_group_scroll_down);
            this.tvScroll.setTextSize(1, 48.0f);
            this.tvScroll.setTypeface(Typeface.DEFAULT);
            this.tvScroll.setVisibility(8);
            this.tvScroll.setGravity(17);
            int dipToPx2 = dipToPx(context, 80.0f);
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(dipToPx2, dipToPx2);
            layoutParams2.addRule(13);
            addView(this.tvScroll, layoutParams2);
            LinearLayout linearLayout = new LinearLayout(context);
            this.llScroll = linearLayout;
            linearLayout.setBackgroundResource(R.drawable.country_group_scroll_up);
            this.llScroll.setOrientation(1);
            this.llScroll.setOnTouchListener(this);
            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams3.addRule(11);
            layoutParams3.addRule(15);
            layoutParams3.rightMargin = dipToPx(context, 5.0f);
            addView(this.llScroll, layoutParams3);
            initScroll(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initScroll(Context context) {
        this.llScroll.removeAllViews();
        int groupCount = this.adapter.getGroupCount();
        int dipToPx = dipToPx(getContext(), 3.0f);
        int dipToPx2 = dipToPx(getContext(), 0.4f);
        for (int i = 0; i < groupCount; i++) {
            TextView textView = new TextView(context);
            textView.setTextColor(Color.parseColor("#fc3341"));
            textView.setText(this.adapter.getGroupTitle(i));
            textView.setGravity(17);
            textView.setPadding(dipToPx, dipToPx2, dipToPx, dipToPx2);
            this.llScroll.addView(textView);
        }
    }

    @Override // android.widget.RelativeLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getMeasuredHeight();
        getMeasuredHeight();
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x000d, code lost:
    
        if (r0 != 3) goto L13;
     */
    @Override // android.view.View.OnTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouch(android.view.View r4, android.view.MotionEvent r5) {
        /*
            r3 = this;
            int r0 = r5.getAction()
            r1 = 1
            if (r0 == 0) goto L2c
            if (r0 == r1) goto L1e
            r2 = 2
            if (r0 == r2) goto L10
            r5 = 3
            if (r0 == r5) goto L1e
            goto L39
        L10:
            float r0 = r5.getX()
            float r5 = r5.getY()
            android.view.ViewGroup r4 = (android.view.ViewGroup) r4
            r3.onScroll(r4, r0, r5)
            goto L39
        L1e:
            r5 = 2131230917(0x7f0800c5, float:1.80779E38)
            r4.setBackgroundResource(r5)
            android.widget.TextView r4 = r3.tvScroll
            r5 = 8
            r4.setVisibility(r5)
            goto L39
        L2c:
            float r0 = r5.getX()
            float r5 = r5.getY()
            android.view.ViewGroup r4 = (android.view.ViewGroup) r4
            r3.onScroll(r4, r0, r5)
        L39:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.utils.selectedCountryLib.demo.CountryListView.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }

    public void onScroll(ViewGroup llScroll, float x, float y) {
        int childCount = llScroll.getChildCount();
        for (int i = 0; i < childCount; i++) {
            TextView textView = (TextView) llScroll.getChildAt(i);
            if (x >= textView.getLeft() && x <= textView.getRight() && y >= textView.getTop() && y <= textView.getBottom()) {
                this.lvContries.setSelection(i);
                this.tvScroll.setVisibility(0);
                this.tvScroll.setText(textView.getText());
                return;
            }
        }
    }

    public void onSearch(String token) {
        this.adapter.search(token);
        this.adapter.notifyDataSetChanged();
        if (this.adapter.getGroupCount() == 0) {
            this.llScroll.setVisibility(8);
        } else {
            this.llScroll.setVisibility(0);
        }
        initScroll(getContext());
    }

    public void setOnItemClickListener(GroupListView.OnItemClickListener listener) {
        this.lvContries.setOnItemClickListener(listener);
    }

    public String[] getCountry(int group, int position) {
        return this.adapter.getItem(group, position);
    }

    public void setlocalItem2Frist(String name) {
        this.adapter.setlocalItem2Frist(name);
    }

    private int dipToPx(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}