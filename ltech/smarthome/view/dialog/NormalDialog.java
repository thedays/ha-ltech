package com.ltech.smarthome.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class NormalDialog extends Dialog {
    private TextView message;
    private String messageStr;

    /* renamed from: no, reason: collision with root package name */
    private TextView f6282no;
    private onNoOnclickListener noOnclickListener;
    private String noStr;
    private String titleStr;
    private TextView titleTV;
    private View view;
    private TextView yes;
    private onYesOnclickListener yesOnclickListener;
    private String yesStr;

    public interface onNoOnclickListener {
        void onNoClick();
    }

    public interface onYesOnclickListener {
        void onYesOnclick();
    }

    public NormalDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public void setNoOnclickListener(String str, onNoOnclickListener onNoOnclickListener2) {
        if (str != null) {
            this.noStr = str;
        }
        this.noOnclickListener = onNoOnclickListener2;
    }

    public void setYesOnclickListener(String str, onYesOnclickListener yesOnclickListener) {
        if (str != null) {
            this.yesStr = str;
        }
        this.yesOnclickListener = yesOnclickListener;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_order_grey);
        setCanceledOnTouchOutside(true);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getWindow().setLayout(-1, -1);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        this.yes = (TextView) findViewById(R.id.btn_selectPositive);
        this.f6282no = (TextView) findViewById(R.id.btn_selectNegative);
        this.titleTV = (TextView) findViewById(R.id.txt_dialog_title);
        this.message = (TextView) findViewById(R.id.tv_message);
        this.yes.setText(ActivityUtils.getTopActivity().getResources().getString(R.string.go_to_setting));
        this.f6282no.setText(ActivityUtils.getTopActivity().getResources().getString(R.string.no_open));
        this.titleTV.setText(R.string.please_open_gps);
        this.yes.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.view.dialog.NormalDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ActivityUtils.getTopActivity().startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 666);
                NormalDialog.this.dismiss();
            }
        });
    }

    public void initData() {
        String str = this.titleStr;
        if (str != null) {
            this.titleTV.setText(str);
        }
        String str2 = this.messageStr;
        if (str2 != null) {
            this.message.setText(str2);
        }
        String str3 = this.yesStr;
        if (str3 != null) {
            this.yes.setText(str3);
        }
        String str4 = this.noStr;
        if (str4 != null) {
            this.f6282no.setText(str4);
        }
    }

    private void initEvent() {
        this.f6282no.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.view.dialog.NormalDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (NormalDialog.this.noOnclickListener != null) {
                    NormalDialog.this.noOnclickListener.onNoClick();
                }
            }
        });
    }

    public void setTitle(String title) {
        this.titleStr = title;
        this.titleTV.setText(title);
    }

    public void setMessage(String message) {
        this.messageStr = message;
    }
}