package com.ltech.smarthome.nfc;

import android.content.Intent;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.nfc.source.SourceHelper;
import com.ltech.nfc.source.SourceModel;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActCurrentSetFiveBinding;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.view.NumberSetView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActCurrentSetFive extends BaseNormalActivity<ActCurrentSetFiveBinding> {
    public int[] current;
    private BaseQuickAdapter<CurrentItem, BaseViewHolder> currentAdapter;
    private SourceModel sourceModel;
    private NumberSetView[] etCurrentArray = new NumberSetView[5];
    private List<CurrentItem> currentList = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean isNeedAddMarginTopEqualStatusBar() {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_current_set_five;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        showAsDialog(0.0f);
        SourceModel sourceModel = SourceHelper.sourceModel;
        this.sourceModel = sourceModel;
        setTitle(sourceModel.sourceName);
        setBackString(getString(R.string.cancel));
        ((ActCurrentSetFiveBinding) this.mViewBinding).title.tvBack.setTextColor(ContextCompat.getColor(this, R.color.color_text_gray));
        setEditString(getString(R.string.save));
        int[] intArrayExtra = getIntent().getIntArrayExtra(Constants.CURRENT);
        this.current = intArrayExtra;
        if (intArrayExtra == null || intArrayExtra.length != 5) {
            this.current = new int[5];
        }
        int i = 0;
        while (true) {
            int[] iArr = this.current;
            if (i < iArr.length) {
                iArr[i] = SourceHelper.sourceModel.getOutputCurrent(this.current[i]);
                i++;
            } else {
                this.currentList = getCurrentList();
                initCurrentView();
                return;
            }
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        setResult(5011, new Intent().putExtra(Constants.CURRENT, this.current));
        finishActivity();
    }

    private void initCurrentView() {
        ((ActCurrentSetFiveBinding) this.mViewBinding).tvCurrentTip.setText(getString(R.string.current_five_tip, new Object[]{Integer.valueOf(SourceHelper.sourceModel.IOutMin), Integer.valueOf(SourceHelper.sourceModel.IOutMax)}));
        if (this.currentAdapter == null) {
            BaseQuickAdapter<CurrentItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<CurrentItem, BaseViewHolder>(R.layout.view_five_current_item, this.currentList) { // from class: com.ltech.smarthome.nfc.ActCurrentSetFive.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(final BaseViewHolder helper, CurrentItem item) {
                    ((CardView) helper.getView(R.id.cardview)).setCardBackgroundColor(ActCurrentSetFive.this.getResources().getColor(item.colorRes));
                    helper.setText(R.id.tv_card, item.title);
                    ((AppCompatTextView) helper.getView(R.id.tv_card)).setTextSize(2, 10.0f);
                    helper.setText(R.id.tv_output, SourceHelper.sourceModel.getOutputParamFive(item.current));
                    NumberSetView numberSetView = (NumberSetView) helper.getView(R.id.current_set_view);
                    numberSetView.setRange(SourceHelper.sourceModel.IOutMin, SourceHelper.sourceModel.IOutMax);
                    numberSetView.setNumber(item.current);
                    numberSetView.setEditable(true);
                    numberSetView.setListener(new NumberSetView.OnNumberClickListener() { // from class: com.ltech.smarthome.nfc.ActCurrentSetFive.1.1
                        @Override // com.ltech.smarthome.view.NumberSetView.OnNumberClickListener
                        public void onNumberClick() {
                        }

                        @Override // com.ltech.smarthome.view.NumberSetView.OnNumberClickListener
                        public void numberChanged(int number) {
                            int layoutPosition = helper.getLayoutPosition();
                            ActCurrentSetFive.this.current[layoutPosition] = number;
                            ((CurrentItem) ActCurrentSetFive.this.currentList.get(layoutPosition)).current = number;
                            ActCurrentSetFive.this.currentAdapter.notifyItemChanged(layoutPosition);
                        }
                    });
                    if (ActCurrentSetFive.this.etCurrentArray[helper.getLayoutPosition()] == null) {
                        ActCurrentSetFive.this.etCurrentArray[helper.getLayoutPosition()] = numberSetView;
                    }
                }
            };
            this.currentAdapter = baseQuickAdapter;
            baseQuickAdapter.bindToRecyclerView(((ActCurrentSetFiveBinding) this.mViewBinding).rvCurrent);
            ((ActCurrentSetFiveBinding) this.mViewBinding).rvCurrent.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent ev) {
        InputMethodManager inputMethodManager;
        if (ev.getAction() == 0) {
            View currentFocus = getCurrentFocus();
            if (isShouldHideInput(currentFocus, ev) && (inputMethodManager = (InputMethodManager) getSystemService("input_method")) != null) {
                int i = 0;
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                while (true) {
                    NumberSetView[] numberSetViewArr = this.etCurrentArray;
                    if (i >= numberSetViewArr.length) {
                        break;
                    }
                    NumberSetView numberSetView = numberSetViewArr[i];
                    if (numberSetView != null && currentFocus == numberSetView.etNum) {
                        currentFocus.clearFocus();
                        int i2 = this.sourceModel.IOutMin;
                        String obj = ((AppCompatEditText) currentFocus).getText().toString();
                        if (!TextUtils.isEmpty(obj)) {
                            i2 = Integer.parseInt(obj);
                        }
                        this.current[i] = SourceHelper.sourceModel.getOutputCurrent(i2);
                        this.currentList.get(i).current = this.current[i];
                        this.currentAdapter.notifyItemChanged(i);
                    }
                    i++;
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    private List<CurrentItem> getCurrentList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new CurrentItem(this, "R", R.color.five_R, this.current[0]));
        arrayList.add(new CurrentItem(this, "G", R.color.five_G, this.current[1]));
        arrayList.add(new CurrentItem(this, "B", R.color.five_B, this.current[2]));
        arrayList.add(new CurrentItem(this, "C", R.color.five_C, this.current[3]));
        arrayList.add(new CurrentItem(this, ExifInterface.LONGITUDE_WEST, R.color.five_W, this.current[4]));
        return arrayList;
    }

    private class CurrentItem {
        public int colorRes;
        public int current;
        public String title;

        public CurrentItem(final ActCurrentSetFive this$0, String title, int colorRes, int current) {
            this.title = title;
            this.colorRes = colorRes;
            this.current = current;
        }
    }
}