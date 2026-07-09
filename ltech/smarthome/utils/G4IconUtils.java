package com.ltech.smarthome.utils;

import android.text.TextUtils;
import android.util.Base64;
import com.ltech.smarthome.MyApplication;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/* loaded from: classes4.dex */
public class G4IconUtils {
    public static byte[] getThemeData(int iconIndex) {
        String stringFromAssets = getStringFromAssets(String.format("g4_theme_data_%s.txt", Integer.valueOf(iconIndex + 1)));
        if (TextUtils.isEmpty(stringFromAssets)) {
            return null;
        }
        return Base64.decode(stringFromAssets.getBytes(), 0);
    }

    private static String getStringFromAssets(String fileName) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(MyApplication.getContext().getResources().getAssets().open(fileName), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    sb.append(readLine);
                } else {
                    bufferedReader.close();
                    inputStreamReader.close();
                    return sb.toString().replace("\\n", "\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}