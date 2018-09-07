package com.weshowbuy.bottomdialog;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import me.shaohui.bottomdialog.BaseBottomDialog;

/**
 * ====================================================
 * package   : com.chuangkong.nansi.weixiaobai.widgets
 * author    : Created by nansi.
 * time      : 2018/8/11  上午10:10
 * remark    :
 * ====================================================
 */

public class BottomDialog extends BaseBottomDialog {
    private RecyclerView recycler_view;
    private TextView tv_cancle;
    private TextView tv_title;
    private TextView tv_destory;
    private View line;
    private BottomSheetClick.ClickLister mListener;
    private BottomSheetClick.LongClickListener mLongClickListener;

    /**
     * 标题
     */
    private List<String> mTitles = new ArrayList<>();

    /**
     * 副标题数组
     */
    private List<String> mSubtitles = new ArrayList<>();

    /**
     * items
     */
    private List<BottomItem> mItems = new ArrayList<>();

    /**
     * 能否点击取消
     */
    private boolean mIsCancelOutside = super.getCancelOutside();

    /**
     * destorybutton 标题
     */
    private String mDestoryTitle = "";

    /**
     * 标题
     */
    private String mTitle = "";

    private String mTag = super.getFragmentTag();
    /**
     * 设置背景透明度  0 - 1
     */
    private float mDimAmount = 0.5f;

    private boolean mShowCancelBtn = true;

    private BottomDialogAdapter mAdapter;
    private FragmentManager mFragmentManager;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            mContext = context;
        } else {
            mContext = this.getActivity();
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_bottom_list;
    }

    @Override
    public void bindView(View v) {

        line = v.findViewById(R.id.line);
        tv_title = v.findViewById(R.id.tv_title);
        tv_cancle = v.findViewById(R.id.tv_cancel);
        tv_destory = v.findViewById(R.id.tv_destory);
        recycler_view = v.findViewById(R.id.recycler_view);

        recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
        recycler_view.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.decoration));
        recycler_view.addItemDecoration(dividerItemDecoration);
        mAdapter.replaceData(mItems);


        if (!TextUtils.isEmpty(mTitle)) {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(mTitle);
        }

        if (!TextUtils.isEmpty(mDestoryTitle)) {
            tv_destory.setVisibility(View.VISIBLE);
            tv_destory.setText(mDestoryTitle);
        }

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mListener != null) {
                    mListener.onClick(position);
                }
                BottomDialog.this.dismiss();
            }
        });

        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                if (mLongClickListener != null) {
                    mLongClickListener.onLongClick(position);
                    BottomDialog.this.dismiss();
                }
                return false;
            }
        });

        tv_destory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                if (mListener != null) {
                    mListener.onDestoryButtonClick();
                } else if (mLongClickListener != null) {
                    mLongClickListener.onDestoryButtonClick();
                }
                BottomDialog.this.dismiss();
            }
        });

        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                if (mListener != null) {
                    mListener.onCancel();
                } else if (mLongClickListener != null) {
                    mLongClickListener.onCancel();
                }
                BottomDialog.this.dismiss();
            }
        });

        if (mShowCancelBtn) {
            tv_cancle.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
        }else {
            tv_cancle.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }
    }

    public static BottomDialog onCreate(FragmentManager fragmentManager, Context context) {
        BottomDialog dialog = new BottomDialog();
        dialog.mFragmentManager = fragmentManager;
        dialog.mAdapter = new BottomDialogAdapter(R.layout.item_dialog, dialog.mItems);
        return dialog;
    }

    @Override
    public float getDimAmount() {
        return mDimAmount;
    }


    @Override
    public boolean getCancelOutside() {
        return mIsCancelOutside;
    }

    @Override
    public String getFragmentTag() {
        return mTag;
    }

    public BottomDialog setTitle(String title) {
        mTitle = title;
        return this;
    }

    public BottomDialog setCancelOutside(boolean cancelOutside) {
        this.mIsCancelOutside = cancelOutside;
        return this;
    }

    public BottomDialog setShowCanCelButton(boolean showCanCelButton) {
        this.mShowCancelBtn = showCanCelButton;
        return this;
    }

    public BottomDialog setDimAmount(float dim) {
        mDimAmount = dim;
        return this;
    }

    public BottomDialog setClickListener(BottomSheetClick.ClickLister listener) {
        mListener = listener;
        return this;
    }

    public BottomDialog setLongClickListener(BottomSheetClick.LongClickListener listener) {
        mLongClickListener = listener;
        return this;
    }

    public BottomDialog setItems(List<String> titles) {
        mTitles.clear();
        mTitles.addAll(titles);
        return this;
    }

    public BottomDialog setSubtitles(List<String> titles) {
        mSubtitles.clear();
        mSubtitles.addAll(titles);
        return this;
    }

    public BottomDialog setDestoryTitle(String title) {
        mDestoryTitle = title;
        return this;
    }

    public void show() {
        for (int i = 0; i < mTitles.size(); i++) {
            String title = mTitles.get(i);
            String subtitle = null;

            if (mSubtitles.size() != 0) {
                //按顺序取副标题
                if (i != mSubtitles.size()) {
                    subtitle = mSubtitles.get(i);
                }
            }
            BottomItem item = new BottomItem(title, subtitle);
            mItems.add(item);
        }
        mAdapter.replaceData(mItems);
        show(mFragmentManager);
    }


    public static class BottomDialogAdapter extends BaseQuickAdapter<BottomItem, BaseViewHolder> {

        public BottomDialogAdapter(int layoutResId, List<BottomItem> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, BottomItem item) {

            helper.setText(R.id.tv_title, item.getTitle());
            TextView subtitle = helper.getView(R.id.tv_subtitle);

            if (TextUtils.isEmpty(item.getSubtitle())) {
                subtitle.setVisibility(View.GONE);
            } else {
                subtitle.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.f);
                subtitle.setLayoutParams(params);
                subtitle.setText(item.getSubtitle());
            }

        }
    }

    private static class BottomItem {
        private String title;
        private String subtitle;

        public BottomItem(String title, String subtitle) {
            this.title = title;
            this.subtitle = subtitle;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }
    }

    public abstract static class BottomSheetClick {

        public abstract static class ClickLister extends BottomSheetClick {
            public abstract void onClick(int index);
        }

        public abstract static class LongClickListener extends BottomSheetClick{
            public abstract void onLongClick(int index);
        }

        protected void onCancel(){}
        protected void onDestoryButtonClick(){}
    }

}
