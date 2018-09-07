package com.weshowbuy.bottomdialog;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import me.shaohui.bottomdialog.BaseBottomDialog;

/**
 * ====================================================
 * package   : com.chuangkong.nansi.weixiaobai.ui
 * author    : Created by nansi.
 * time      : 2018/8/11  下午2:12
 * remark    :
 * ====================================================
 */

public class BottomTextEditDialog extends BaseBottomDialog {
    private EditText mEditText;
    private TextView tv_complete;
    private TextView tv_cancel;

    /**
     * 文本内容
     */
    private String mText = "";

    /**
     * hint
     */
    private String mHint = "";

    private int mCompleteTextColor = getResources().getColor(R.color.black);

    private int mCancelTextColor = getResources().getColor(R.color.black);

    /**
     * 最大输入数  默认无限制
     */
    private int mMaxLength = -1;

    /**
     * 最大行数
     */
    private int mMaxLines = -1;

    /**
     * 监听事件
     */
    private BottomEditDialogListener mListener;

    private FragmentManager mManager;

    @Override
    public int getLayoutRes() {
        return R.layout.bottom_dialog_edittext;
    }

    @Override
    public void bindView(View v) {
        mEditText = v.findViewById(R.id.edit_content);
        tv_complete = v.findViewById(R.id.tv_complete);
        tv_cancel = v.findViewById(R.id.tv_cancel);

        tv_complete.setTextColor(mCompleteTextColor);
        tv_cancel.setTextColor(mCancelTextColor);

        if (mMaxLength > 0) {
            InputFilter[] filters = {new InputFilter.LengthFilter(mMaxLength)};
            mEditText.setFilters(filters);
        }

        mEditText.setText(mText);
        mEditText.setHint(mHint);

        mEditText.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm =
                        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEditText, 0);
                mEditText.setFocusable(true);

                mEditText.setFocusableInTouchMode(true);
                mEditText.requestFocus();
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mListener != null) mListener.editTextInput(BottomTextEditDialog.this, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {

                if (mListener != null) {
                    BottomTextEditDialog.this.hideKeyboard();
                    mListener.complete(BottomTextEditDialog.this, mEditText.getText().toString());
                }
//            dismiss();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                if (mListener != null) {
                    BottomTextEditDialog.this.hideKeyboard();
                }
                BottomTextEditDialog.this.dismiss();
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tv_complete.getWindowToken(), 0);
    }

    public static BottomTextEditDialog onCreate(FragmentManager manager) {
        BottomTextEditDialog dialog = new BottomTextEditDialog();
        dialog.mManager = manager;
        return dialog;
    }

    public BottomTextEditDialog setListener(BottomEditDialogListener listener) {
        mListener = listener;
        return this;
    }

    public BottomTextEditDialog setMaxLines(int maxLines) {
        mMaxLines = maxLines;
        return this;
    }

    public BottomTextEditDialog setMaxEms(int maxEms) {
        mMaxLength = maxEms;
        return this;
    }

    public BottomTextEditDialog setText(String text) {
        mText = text;
        return this;
    }

    public BottomTextEditDialog setHint(String hint) {
        mHint = hint;
        return this;
    }

    public BottomTextEditDialog setCompleteButtonTextColor(int color) {
        mCompleteTextColor = color;
        return this;
    }

    public BottomTextEditDialog setCancelButtonTextColor(int color) {
        mCancelTextColor = color;
        return this;
    }

    public void show() {
        show(mManager);
    }

    @Override
    public float getDimAmount() {
        return 0.6f;
    }

    @Override
    public boolean getCancelOutside() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public abstract static class BottomEditDialogListener {
        void editTextInput(BottomTextEditDialog dialog, String inputStr) {

        }

        protected abstract void complete(BottomTextEditDialog dialog, String string);
    }
}


