package com.ltech.smarthome.view.dialog;

import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.Dialog512ChannelSettingBinding;

/* loaded from: classes4.dex */
public class Channel512Dialog extends SmartDialog<Dialog512ChannelSettingBinding> {
    private OnCancelCallback cancelCallback;
    private int headAddress;
    private OnConfirmCallback mCallback;
    private int max;
    private int progress = 25;

    public interface OnCancelCallback {
        void onCancel();
    }

    public interface OnConfirmCallback {
        void onConfirmClick(Channel512Dialog dialog);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_512_channel_setting;
    }

    public static Channel512Dialog asDefault() {
        return (Channel512Dialog) new Channel512Dialog().setViewConverter(new SmartDialog.ViewConverter<Dialog512ChannelSettingBinding, Channel512Dialog>() { // from class: com.ltech.smarthome.view.dialog.Channel512Dialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(final Dialog512ChannelSettingBinding viewBinding, final Channel512Dialog dialog) {
                viewBinding.tvLabel1.setText(dialog.getString(R.string.app_str_first_address) + "(001-512)");
                viewBinding.tvLabel2.setText(dialog.getString(R.string.app_str_channel_num) + "(1-6)");
                dialog.max = 6;
                if (dialog.headAddress == 508) {
                    if (dialog.progress > 5) {
                        dialog.progress = 5;
                    }
                    dialog.max = 5;
                } else if (dialog.headAddress == 509) {
                    if (dialog.progress > 4) {
                        dialog.progress = 4;
                    }
                    dialog.max = 4;
                } else if (dialog.headAddress == 510) {
                    if (dialog.progress > 3) {
                        dialog.progress = 3;
                    }
                    dialog.max = 3;
                } else if (dialog.headAddress == 511) {
                    if (dialog.progress > 2) {
                        dialog.progress = 2;
                    }
                    dialog.max = 2;
                } else if (dialog.headAddress == 512) {
                    if (dialog.progress > 1) {
                        dialog.progress = 1;
                    }
                    dialog.max = 1;
                }
                int i = dialog.headAddress;
                viewBinding.edit.setText((i < 100 ? "00".substring(0, 3 - String.valueOf(i).length()) : "") + i);
                viewBinding.seekBar.setProgress((float) dialog.progress);
                viewBinding.tvValue.setText(((int) viewBinding.seekBar.getLeftSeekBar().getProgress()) + "");
                viewBinding.tvFinish.setOnClickListener(new View.OnClickListener(this) { // from class: com.ltech.smarthome.view.dialog.Channel512Dialog.1.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        if (dialog.mCallback != null) {
                            dialog.mCallback.onConfirmClick(dialog);
                        }
                        dialog.dismissDialog();
                    }
                });
                viewBinding.tvCancel.setOnClickListener(new View.OnClickListener(this) { // from class: com.ltech.smarthome.view.dialog.Channel512Dialog.1.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        dialog.dismissDialog();
                    }
                });
                viewBinding.edit.addTextChangedListener(new TextWatcher(this) { // from class: com.ltech.smarthome.view.dialog.Channel512Dialog.1.3
                    private boolean isUpdating = false;

                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (this.isUpdating) {
                            return;
                        }
                        this.isUpdating = true;
                        String replaceAll = s.toString().replaceAll("[^0-9]", "");
                        if (replaceAll.isEmpty()) {
                            viewBinding.edit.setText("00");
                            viewBinding.edit.setSelection(2);
                            return;
                        }
                        int parseInt = Integer.parseInt(replaceAll);
                        if (parseInt > 512) {
                            parseInt = 512;
                        }
                        String str = (parseInt < 100 ? "00".substring(0, 3 - String.valueOf(parseInt).length()) : "") + parseInt;
                        viewBinding.edit.setText(str);
                        viewBinding.edit.setSelection(str.length());
                    }

                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable s) {
                        if (this.isUpdating) {
                            this.isUpdating = false;
                            int parseInt = Integer.parseInt(s.toString());
                            if (parseInt == 0) {
                                parseInt = 1;
                            }
                            dialog.setHeadAddress(parseInt);
                            dialog.max = 6;
                            if (dialog.headAddress == 508) {
                                if (dialog.progress > 5) {
                                    dialog.progress = 5;
                                }
                                dialog.max = 5;
                            } else if (dialog.headAddress == 509) {
                                if (dialog.progress > 4) {
                                    dialog.progress = 4;
                                }
                                dialog.max = 4;
                            } else if (dialog.headAddress == 510) {
                                if (dialog.progress > 3) {
                                    dialog.progress = 3;
                                }
                                dialog.max = 3;
                            } else if (dialog.headAddress == 511) {
                                if (dialog.progress > 2) {
                                    dialog.progress = 2;
                                }
                                dialog.max = 2;
                            } else if (dialog.headAddress == 512) {
                                if (dialog.progress > 1) {
                                    dialog.progress = 1;
                                }
                                dialog.max = 1;
                            }
                            viewBinding.seekBar.setProgress(dialog.progress);
                        }
                    }
                });
                viewBinding.seekBar.setOnRangeChangedListener(new OnRangeChangedListener(this) { // from class: com.ltech.smarthome.view.dialog.Channel512Dialog.1.4
                    @Override // com.jaygoo.widget.OnRangeChangedListener
                    public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {
                    }

                    @Override // com.jaygoo.widget.OnRangeChangedListener
                    public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                        if (leftValue == 0.0f) {
                            view.setProgress(1.0f);
                        }
                        if (leftValue > dialog.max) {
                            view.setProgress(dialog.max);
                        }
                    }

                    @Override // com.jaygoo.widget.OnRangeChangedListener
                    public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
                        viewBinding.tvValue.setText(((int) view.getLeftSeekBar().getProgress()) + "");
                        dialog.setProgress((int) view.getLeftSeekBar().getProgress());
                    }
                });
            }
        }).setMargin(16).setY(16).setGravity(80);
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        OnCancelCallback onCancelCallback = this.cancelCallback;
        if (onCancelCallback != null) {
            onCancelCallback.onCancel();
        }
    }

    public Channel512Dialog setProgress(int p) {
        this.progress = p;
        return this;
    }

    public int getProgress() {
        return this.progress;
    }

    public Channel512Dialog setHeadAddress(int headAddress) {
        this.headAddress = headAddress;
        return this;
    }

    public int getHeadAddress() {
        return this.headAddress;
    }

    public Channel512Dialog setCallback(OnConfirmCallback callback) {
        this.mCallback = callback;
        return this;
    }

    public Channel512Dialog setCancelCallback(OnCancelCallback callback) {
        this.cancelCallback = callback;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "Channel512Dialog";
    }
}