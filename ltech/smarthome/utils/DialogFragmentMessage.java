package com.ltech.smarthome.utils;

import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/* loaded from: classes4.dex */
public class DialogFragmentMessage extends DialogFragment {
    static final String ICON_RES_ID = "ICON_RES_ID";
    protected static final String MESSAGE = "MESSAGE";
    protected static final String TITLE = "TITLE";
    protected AlertDialog.Builder alertDialogBuilder;
    protected String message;
    protected String title;

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.title = getArguments().getString(TITLE);
            this.message = getArguments().getString(MESSAGE);
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        this.alertDialogBuilder.setTitle(this.title);
        this.alertDialogBuilder.setMessage(this.message);
        AlertDialog show = this.alertDialogBuilder.show();
        show.setCancelable(false);
        show.setCanceledOnTouchOutside(false);
        return show;
    }
}