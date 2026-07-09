package com.ltech.smarthome.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;

/* loaded from: classes4.dex */
public class DialogFragmentPermissionRationale extends DialogFragmentMessage {
    private static final String PERMISSION_DENIED_FOREVER = "PERMISSION_DENIED_FOREVER";
    private boolean isDeniedForever;

    public interface StoragePermissionListener {
        void requestPermission();
    }

    public static DialogFragmentPermissionRationale newInstance(final boolean permissionDeniedForever, final String title, final String message) {
        Bundle bundle = new Bundle();
        DialogFragmentPermissionRationale dialogFragmentPermissionRationale = new DialogFragmentPermissionRationale();
        bundle.putBoolean(PERMISSION_DENIED_FOREVER, permissionDeniedForever);
        bundle.putString("TITLE", title);
        bundle.putString("MESSAGE", message);
        dialogFragmentPermissionRationale.setArguments(bundle);
        return dialogFragmentPermissionRationale;
    }

    @Override // com.ltech.smarthome.utils.DialogFragmentMessage, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.isDeniedForever = getArguments().getBoolean(PERMISSION_DENIED_FOREVER);
        }
    }

    @Override // com.ltech.smarthome.utils.DialogFragmentMessage, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        this.alertDialogBuilder = new AlertDialog.Builder(requireActivity());
        this.alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.ltech.smarthome.utils.DialogFragmentPermissionRationale$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DialogFragmentPermissionRationale.this.lambda$onCreateDialog$0(dialogInterface, i);
            }
        });
        if (this.isDeniedForever) {
            this.message += "setting";
            this.alertDialogBuilder.setNeutralButton("setting", new DialogInterface.OnClickListener() { // from class: com.ltech.smarthome.utils.DialogFragmentPermissionRationale$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    DialogFragmentPermissionRationale.this.lambda$onCreateDialog$1(dialogInterface, i);
                }
            });
        }
        return super.onCreateDialog(savedInstanceState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateDialog$0(DialogInterface dialogInterface, int i) {
        ((StoragePermissionListener) getParentFragment()).requestPermission();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateDialog$1(DialogInterface dialogInterface, int i) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", getContext().getPackageName(), null));
        startActivity(intent);
    }
}