package com.ltech.imageclip.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import androidx.fragment.app.Fragment;
import com.ltech.imageclip.fragment.ActivityResultHelper;
import java.util.Random;

/* loaded from: classes3.dex */
public class RouterFragment extends Fragment {
    private SparseArray<ActivityResultHelper.Callback> mCallbacks = new SparseArray<>();
    private Random mCodeGenerator = new Random();

    public static RouterFragment newInstance() {
        return new RouterFragment();
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    public void startActivityForResult(Intent intent, ActivityResultHelper.Callback callback) {
        int makeRequestCode = makeRequestCode();
        this.mCallbacks.put(makeRequestCode, callback);
        startActivityForResult(intent, makeRequestCode);
    }

    private int makeRequestCode() {
        int nextInt;
        int i = 0;
        do {
            nextInt = this.mCodeGenerator.nextInt(65535);
            i++;
            if (this.mCallbacks.indexOfKey(nextInt) < 0) {
                break;
            }
        } while (i < 10);
        return nextInt;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        ActivityResultHelper.Callback callback = this.mCallbacks.get(i);
        this.mCallbacks.remove(i);
        if (callback != null) {
            callback.onActivityResult(i2, intent);
        }
    }
}