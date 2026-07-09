package com.ltech.smarthome.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.CircleDrawable;

/* loaded from: classes4.dex */
public class TextClickUtil {
    public static void setTextClick(Context context, TextView target, ClickableSpan clickableSpan, int clickTextRes, int textRes) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) context.getString(textRes, context.getString(clickTextRes)));
        spannableStringBuilder.setSpan(clickableSpan, spannableStringBuilder.length() - context.getString(clickTextRes).length(), spannableStringBuilder.length(), 34);
        target.setText(spannableStringBuilder);
        target.setMovementMethod(LinkMovementMethod.getInstance());
        target.setHighlightColor(context.getResources().getColor(17170445));
    }

    public static void setTextClick(Context context, TextView target, ClickableSpan clickableSpan, String clickText, String text) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) text);
        int indexOf = spannableStringBuilder.toString().indexOf(clickText);
        spannableStringBuilder.setSpan(clickableSpan, indexOf, clickText.length() + indexOf, 33);
        target.setText(spannableStringBuilder);
        target.setMovementMethod(LinkMovementMethod.getInstance());
        target.setHighlightColor(context.getResources().getColor(17170445));
    }

    public static void setTextClick(Context context, TextView target, ClickableSpan[] clickableSpans, int textRes, int... clickTextsRes) {
        String[] strArr = new String[clickTextsRes.length];
        for (int i = 0; i < clickTextsRes.length; i++) {
            strArr[i] = context.getString(clickTextsRes[i]);
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) context.getString(textRes, strArr));
        for (int i2 = 0; i2 < clickableSpans.length; i2++) {
            int indexOf = spannableStringBuilder.toString().indexOf(strArr[i2]);
            spannableStringBuilder.setSpan(clickableSpans[i2], indexOf, strArr[i2].length() + indexOf, 33);
        }
        target.setText(spannableStringBuilder);
        target.setMovementMethod(LinkMovementMethod.getInstance());
        target.setHighlightColor(context.getResources().getColor(17170445));
    }

    public static void setTextTwoStyle(Context context, AppCompatTextView target, String text1, String text2) {
        SpannableString spannableString = new SpannableString(text1 + text2);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_text_black)), 0, text1.length(), 18);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_text_gray)), text1.length(), spannableString.length(), 18);
        target.setText(spannableString.toString());
    }

    public static void setCircleText(AppCompatTextView textView, String color, String text) {
        SpannableString spannableString = new SpannableString("# " + text);
        spannableString.setSpan(new ImageSpan(new CircleDrawable(color), 1), 0, 1, 18);
        textView.setText(spannableString);
    }
}