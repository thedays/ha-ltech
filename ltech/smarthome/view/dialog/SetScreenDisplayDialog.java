package com.ltech.smarthome.view.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.blankj.utilcode.util.ScreenUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSetScreenDisplayBinding;
import com.ltech.smarthome.model.extra.TabContentdefault;
import com.ltech.smarthome.ui.device.screenpanel.FtSelectIcons;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.view.dialog.SetScreenDisplayDialog;
import com.smart.message.utils.StringUtils;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class SetScreenDisplayDialog extends SmartDialog<DialogSetScreenDisplayBinding> {
    private static MutableLiveData<Integer> iconIndex = new MutableLiveData<>(-1);
    private static DialogSetScreenDisplayBinding mViewBinding;
    private OnDialogCallback onDialogCallback;
    private String screenText;
    private int screenType;
    private boolean showTab = true;
    private List<TabContentdefault> tabContentList;

    public interface OnDialogCallback {
        void onCancel();

        boolean onSave();

        void onScreenChanged(int screenType, String screenStr, int iconIndex);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_set_screen_display;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SetScreenDisplayDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSetScreenDisplayBinding, SetScreenDisplayDialog> {
        final /* synthetic */ boolean val$isOpenElderlyMode;

        AnonymousClass1(final boolean val$isOpenElderlyMode) {
            this.val$isOpenElderlyMode = val$isOpenElderlyMode;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogSetScreenDisplayBinding viewBinding, final SetScreenDisplayDialog dialog) {
            SetScreenDisplayDialog.mViewBinding = viewBinding;
            if (dialog.screenText != null) {
                SetScreenDisplayDialog.mViewBinding.inputEdtTxt.setText(dialog.screenText);
            }
            dialog.initTabList(viewBinding);
            dialog.initRv(viewBinding, this.val$isOpenElderlyMode);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SetScreenDisplayDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SetScreenDisplayDialog.AnonymousClass1.lambda$convertView$0(DialogSetScreenDisplayBinding.this, dialog, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(DialogSetScreenDisplayBinding dialogSetScreenDisplayBinding, SetScreenDisplayDialog setScreenDisplayDialog, View view) {
            int id = view.getId();
            if (id == R.id.iv_clean) {
                dialogSetScreenDisplayBinding.inputEdtTxt.setText("");
            } else {
                if (id != R.id.save_btn) {
                    return;
                }
                setScreenDisplayDialog.onDialogCallback.onSave();
                setScreenDisplayDialog.dismissDialog();
            }
        }
    }

    public static SetScreenDisplayDialog asDefault(boolean isOpenElderlyMode) {
        return (SetScreenDisplayDialog) new SetScreenDisplayDialog().setViewConverter(new AnonymousClass1(isOpenElderlyMode)).setY(0).setOutCancel(true).setGravity(80);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRv(final DialogSetScreenDisplayBinding viewBinding, final boolean isOpenElderlyMode) {
        TabLayout.Tab text = viewBinding.tabsPattern.newTab().setText(R.string.text);
        createTabCustomView(getContext(), text, getString(R.string.text), true);
        TabLayout.Tab text2 = viewBinding.tabsPattern.newTab().setText(R.string.icon);
        createTabCustomView(getContext(), text2, getString(R.string.icon), false);
        viewBinding.tabsPattern.addTab(text);
        viewBinding.tabsPattern.addTab(text2);
        if (!isOpenElderlyMode) {
            TabLayout.Tab text3 = viewBinding.tabsPattern.newTab().setText(R.string.text_icon);
            createTabCustomView(getContext(), text3, getString(R.string.text_icon), false);
            viewBinding.tabsPattern.addTab(text3);
        }
        viewBinding.tabsPattern.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.view.dialog.SetScreenDisplayDialog.2
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                SetScreenDisplayDialog setScreenDisplayDialog = SetScreenDisplayDialog.this;
                setScreenDisplayDialog.createTabCustomView(setScreenDisplayDialog.getContext(), tab, tab.getText().toString(), true);
                if (SetScreenDisplayDialog.this.screenType != tab.getPosition() + 1) {
                    SetScreenDisplayDialog.this.screenType = tab.getPosition() + 1;
                    SetScreenDisplayDialog.this.initTabList(viewBinding);
                }
                viewBinding.inputEdtTxt.setFocusable(SetScreenDisplayDialog.this.screenType != 2);
                viewBinding.inputEdtTxt.setFocusableInTouchMode(SetScreenDisplayDialog.this.screenType != 2);
                viewBinding.layoutInput.setBackgroundResource(SetScreenDisplayDialog.this.screenType != 2 ? R.drawable.shape_white_round_bg_10 : R.drawable.shape_light_gray_bt_10);
                if (SetScreenDisplayDialog.this.screenType == 3) {
                    viewBinding.inputEdtTxt.setText(viewBinding.inputEdtTxt.getText().toString().split("\n")[0]);
                }
                SetScreenDisplayDialog.this.onDialogCallback.onScreenChanged(SetScreenDisplayDialog.this.screenType, SetScreenDisplayDialog.this.screenType == 2 ? "" : viewBinding.inputEdtTxt.getText().toString(), ((Integer) SetScreenDisplayDialog.iconIndex.getValue()).intValue());
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                SetScreenDisplayDialog setScreenDisplayDialog = SetScreenDisplayDialog.this;
                setScreenDisplayDialog.createTabCustomView(setScreenDisplayDialog.getContext(), tab, tab.getText().toString(), false);
            }
        });
        viewBinding.tabsPattern.selectTab(viewBinding.tabsPattern.getTabAt(this.screenType - 1));
        viewBinding.inputEdtTxt.setFilters(new InputFilter[]{new InputFilter() { // from class: com.ltech.smarthome.view.dialog.SetScreenDisplayDialog$$ExternalSyntheticLambda1
            @Override // android.text.InputFilter
            public final CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
                return SetScreenDisplayDialog.lambda$initRv$0(charSequence, i, i2, spanned, i3, i4);
            }
        }, new InputFilter() { // from class: com.ltech.smarthome.view.dialog.SetScreenDisplayDialog$$ExternalSyntheticLambda2
            @Override // android.text.InputFilter
            public final CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
                return SetScreenDisplayDialog.lambda$initRv$1(charSequence, i, i2, spanned, i3, i4);
            }
        }});
        viewBinding.inputEdtTxt.addTextChangedListener(new TextWatcher() { // from class: com.ltech.smarthome.view.dialog.SetScreenDisplayDialog.3
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String charSequence = s.toString();
                int i = isOpenElderlyMode ? 9 : 12;
                if (SetScreenDisplayDialog.this.screenType == 1) {
                    if (SetScreenDisplayDialog.this.getEncodeLength(charSequence) > i) {
                        if (charSequence.contains("\n")) {
                            String[] split = s.toString().split("\n");
                            if (split.length <= 1 || SetScreenDisplayDialog.this.getEncodeLength(split[1]) <= i) {
                                return;
                            }
                            viewBinding.inputEdtTxt.setText(split[0] + "\n" + StringUtils.getSecondLine(split[1], isOpenElderlyMode));
                            return;
                        }
                        for (int i2 = 1; i2 <= charSequence.length(); i2++) {
                            if (SetScreenDisplayDialog.this.getEncodeLength(charSequence.substring(0, i2)) > i) {
                                StringBuilder sb = new StringBuilder();
                                int i3 = i2 - 1;
                                sb.append(charSequence.substring(0, i3));
                                sb.append("\n");
                                sb.append(charSequence.substring(i3));
                                viewBinding.inputEdtTxt.setText(sb.toString());
                                return;
                            }
                        }
                        return;
                    }
                    return;
                }
                if (SetScreenDisplayDialog.this.screenType != 3 || SetScreenDisplayDialog.this.getEncodeLength(s.toString()) <= i) {
                    return;
                }
                viewBinding.inputEdtTxt.setText(s.subSequence(0, s.length() - 1));
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                String obj = viewBinding.inputEdtTxt.getText().toString();
                viewBinding.inputEdtTxt.setSelection(obj.length());
                OnDialogCallback onDialogCallback = SetScreenDisplayDialog.this.onDialogCallback;
                int i = SetScreenDisplayDialog.this.screenType;
                if (SetScreenDisplayDialog.this.screenType == 2) {
                    obj = "";
                }
                onDialogCallback.onScreenChanged(i, obj, ((Integer) SetScreenDisplayDialog.iconIndex.getValue()).intValue());
            }
        });
        iconIndex.observe(this, new Observer() { // from class: com.ltech.smarthome.view.dialog.SetScreenDisplayDialog$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SetScreenDisplayDialog.this.lambda$initRv$2(viewBinding, (Integer) obj);
            }
        });
        if (this.showTab) {
            return;
        }
        mViewBinding.layoutTop.setVisibility(8);
    }

    static /* synthetic */ CharSequence lambda$initRv$0(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        if (Pattern.compile("[\\u4e00-\\u9fa5a-zA-Z0-9 !\"#$%&'()*+,-./:;<=>?@^_`{|}~\n]+").matcher(charSequence.toString()).find()) {
            return null;
        }
        return "";
    }

    static /* synthetic */ CharSequence lambda$initRv$1(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        if (Pattern.compile("[゠-ヿ☀-➿✀-➿←-⇿ἰ0-ὯF🀀-🏿🐀-\u1f7ff]]", 66).matcher(charSequence.toString()).find()) {
            return "";
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$2(DialogSetScreenDisplayBinding dialogSetScreenDisplayBinding, Integer num) {
        if (iconIndex.getValue().intValue() > 0) {
            OnDialogCallback onDialogCallback = this.onDialogCallback;
            int i = this.screenType;
            onDialogCallback.onScreenChanged(i, i == 2 ? "" : dialogSetScreenDisplayBinding.inputEdtTxt.getText().toString(), num.intValue());
        }
    }

    public void initTabList(DialogSetScreenDisplayBinding viewBinding) {
        List<TabContentdefault> list = this.tabContentList;
        if (list == null) {
            this.tabContentList = new ArrayList();
        } else {
            list.clear();
        }
        this.tabContentList.add(new TabContentdefault(R.string.scenes, FtSelectIcons.newInstance(this.screenType, 1, iconIndex.getValue().intValue())));
        this.tabContentList.add(new TabContentdefault(R.string.rooms, FtSelectIcons.newInstance(this.screenType, 2, iconIndex.getValue().intValue())));
        this.tabContentList.add(new TabContentdefault(R.string.devices, FtSelectIcons.newInstance(this.screenType, 3, iconIndex.getValue().intValue())));
        viewBinding.viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.view.dialog.SetScreenDisplayDialog.4
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return ((TabContentdefault) SetScreenDisplayDialog.this.tabContentList.get(position)).getFragment();
            }

            @Override // androidx.viewpager2.adapter.FragmentStateAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
            public long getItemId(int position) {
                return super.getItemId(position);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return SetScreenDisplayDialog.this.tabContentList.size();
            }
        });
        viewBinding.tabsType.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.view.dialog.SetScreenDisplayDialog.5
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                SetScreenDisplayDialog setScreenDisplayDialog = SetScreenDisplayDialog.this;
                Context context = setScreenDisplayDialog.getContext();
                SetScreenDisplayDialog setScreenDisplayDialog2 = SetScreenDisplayDialog.this;
                setScreenDisplayDialog.createTabCustomView(context, tab, setScreenDisplayDialog2.getString(((TabContentdefault) setScreenDisplayDialog2.tabContentList.get(tab.getPosition())).getTitleRes()), true);
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                SetScreenDisplayDialog setScreenDisplayDialog = SetScreenDisplayDialog.this;
                Context context = setScreenDisplayDialog.getContext();
                SetScreenDisplayDialog setScreenDisplayDialog2 = SetScreenDisplayDialog.this;
                setScreenDisplayDialog.createTabCustomView(context, tab, setScreenDisplayDialog2.getString(((TabContentdefault) setScreenDisplayDialog2.tabContentList.get(tab.getPosition())).getTitleRes()), false);
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
                SetScreenDisplayDialog setScreenDisplayDialog = SetScreenDisplayDialog.this;
                Context context = setScreenDisplayDialog.getContext();
                SetScreenDisplayDialog setScreenDisplayDialog2 = SetScreenDisplayDialog.this;
                setScreenDisplayDialog.createTabCustomView(context, tab, setScreenDisplayDialog2.getString(((TabContentdefault) setScreenDisplayDialog2.tabContentList.get(tab.getPosition())).getTitleRes()), true);
            }
        });
        viewBinding.tabsType.setSelectedTabIndicatorColor(0);
        new TabLayoutMediator(viewBinding.tabsType, viewBinding.viewpager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ltech.smarthome.view.dialog.SetScreenDisplayDialog$$ExternalSyntheticLambda0
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i) {
                SetScreenDisplayDialog.this.lambda$initTabList$3(tab, i);
            }
        }).attach();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTabList$3(TabLayout.Tab tab, int i) {
        createTabCustomView(getContext(), tab, getString(this.tabContentList.get(i).getTitleRes()), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createTabCustomView(Context context, TabLayout.Tab tab, String text, boolean select) {
        View customView = tab.getCustomView();
        if (customView == null) {
            customView = LayoutInflater.from(context).inflate(R.layout.tab_text, (ViewGroup) null);
            customView.setLayoutParams(new ViewGroup.LayoutParams(ScreenUtils.getScreenWidth() / 4, -1));
            tab.setCustomView(customView);
        }
        AppCompatTextView appCompatTextView = (AppCompatTextView) customView.findViewById(R.id.tv_tab);
        appCompatTextView.setText(text);
        if (select) {
            appCompatTextView.setTextColor(ContextCompat.getColor(context, R.color.title_text));
            if (LanguageUtils.isRussian(context)) {
                appCompatTextView.setTextSize(13.0f);
                return;
            } else {
                appCompatTextView.setTextSize(14.0f);
                return;
            }
        }
        appCompatTextView.setTextColor(ContextCompat.getColor(context, R.color.color_text_dark_gray));
        appCompatTextView.setTextSize(14.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getEncodeLength(String input) {
        try {
            return input.getBytes("gbk").length;
        } catch (UnsupportedEncodingException unused) {
            return input.length();
        }
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        this.onDialogCallback.onCancel();
    }

    public static DialogSetScreenDisplayBinding getViewBinding() {
        return mViewBinding;
    }

    public SetScreenDisplayDialog setScreenType(int screenType) {
        if (screenType == 0) {
            screenType = 1;
        }
        this.screenType = screenType;
        return this;
    }

    public SetScreenDisplayDialog setShowTab(boolean b2) {
        this.showTab = b2;
        return this;
    }

    public SetScreenDisplayDialog setScreenText(String txt) {
        this.screenText = txt;
        return this;
    }

    public static void setIconIndex(int index) {
        iconIndex.setValue(Integer.valueOf(Math.max(index, 1000)));
    }

    public SetScreenDisplayDialog setOnDialogCallback(OnDialogCallback onDialogCallback) {
        this.onDialogCallback = onDialogCallback;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "dialog_set_screen_display";
    }
}