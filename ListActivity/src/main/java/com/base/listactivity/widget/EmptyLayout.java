package com.base.listactivity.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.core.content.ContextCompat;

import com.base.listactivity.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by long on 2016/8/23.
 * 加载、空视图
 */
public class EmptyLayout extends FrameLayout implements View.OnClickListener {

    public static final int STATUS_HIDE = 1001;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_NO_NET = 2;
    public static final int STATUS_NO_DATA = 3;
    public static final int STATUS_CUSTOM = 4; // 自定义界面显示
    public static final int STATUS_NOLOGIN = 5; // 未登录占位
    public static final int STATUS_SERVER_ERROR = 6; // 服务器异常
    private Context mContext;
    private OnRetryListener mOnRetryListener; // 网络异常时触发重新加载的点击事件
    private OnToLoginListener mOnToLoginListener; //去登录
    private int mEmptyStatus = STATUS_LOADING;
    private int mBgColor;

    TextView mTvEmptyMessage;
    TextView mTvEmptyTitle;
    TextView mBtnEmptyMessage;
    ImageView mIvEmptyMessage;
    View mRlEmptyContainer;
    CustomRefreshHeadView mEmptyLoading;
    FrameLayout mEmptyLayout;

    OnClickListener netErrBtnClickListener; // 按钮显示的时候的点击事件

    String customStr; // 自定义时显示的字符串
    int customRes; // 自定义显示的图片

    String emptyMessageStr; // 空view文本
    String emptyTitleStr; // 空view显示的标题
    String emptyBtnStr; // 空view的时候，显示的标题
    int emptyIconRes; // 空view的时候，显示的图标

    boolean initEmptyStatus; // 用来控制setEmptyStatusView方法的调用次数，保证只调用一次

    public EmptyLayout(Context context) {
        this(context, null);
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    public String getEmptyMessageStr() {
        return emptyMessageStr;
    }

    public void setEmptyMessageStr(String emptyMessageStr) {
        this.emptyMessageStr = emptyMessageStr;
    }

    public String getEmptyTitleStr() {
        return emptyTitleStr;
    }

    public void setEmptyTitleStr(String emptyTitleStr) {
        this.emptyTitleStr = emptyTitleStr;
    }

    public String getEmptyBtnStr() {
        return emptyBtnStr;
    }

    public void setEmptyBtnStr(String emptyBtnStr) {
        this.emptyBtnStr = emptyBtnStr;
    }

    public int getEmptyIconRes() {
        return emptyIconRes;
    }

    public void setEmptyIconRes(int emptyIconRes) {
        this.emptyIconRes = emptyIconRes;
    }

    public OnClickListener getNetErrBtnClickListener() {
        return netErrBtnClickListener;
    }

    public void setEmptyStatusView(int emptyIconRes, String emptyTitleStr, String emptyMessageStr, String emptyBtnStr, OnClickListener netErrBtnClickListener) {
        if (!initEmptyStatus) {
            this.emptyIconRes = emptyIconRes;
            this.emptyTitleStr = emptyTitleStr;
            this.emptyMessageStr = emptyMessageStr;
            this.emptyBtnStr = emptyBtnStr;
            this.netErrBtnClickListener = netErrBtnClickListener;
        }
    }

    public void setEmptyStatusView(int emptyIconRes, String emptyTitleStr, String emptyMessageStr, String emptyBtnStr, OnClickListener netErrBtnClickListener, boolean initEmptyStatus) {
        this.emptyIconRes = emptyIconRes;
        this.emptyTitleStr = emptyTitleStr;
        this.emptyMessageStr = emptyMessageStr;
        this.emptyBtnStr = emptyBtnStr;
        this.netErrBtnClickListener = netErrBtnClickListener;
        this.initEmptyStatus = initEmptyStatus;
    }

    public void setNetErrBtnClickListener(OnClickListener netErrBtnClickListener) {
        this.netErrBtnClickListener = netErrBtnClickListener;
    }

    public String getCustomStr() {
        return customStr;
    }

    public void setCustomStr(String customStr) {
        this.customStr = customStr;
    }

    public int getCustomRes() {
        return customRes;
    }

    public void setCustomRes(int customRes) {
        this.customRes = customRes;
    }

    /**
     * 初始化
     */
    private void init(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.EmptyLayout);
        try {
            mBgColor = a.getColor(R.styleable.EmptyLayout_background_color, ContextCompat.getColor(getContext(), R.color.backgroud_color));
        } finally {
            a.recycle();
        }
        View.inflate(mContext, R.layout.layout_empty_loading, this);
        mTvEmptyMessage = (TextView) findViewById(R.id.tv_net_error);
        mTvEmptyTitle = (TextView) findViewById(R.id.tv_net_title);
        mBtnEmptyMessage = (TextView) findViewById(R.id.btn_net_error);
        mIvEmptyMessage = (ImageView) findViewById(R.id.iv_net_error);
        mEmptyLoading = (CustomRefreshHeadView) findViewById(R.id.empty_loading);
        mEmptyLayout = (FrameLayout) findViewById(R.id.empty_layout);
        mRlEmptyContainer = findViewById(R.id.rl_empty_container);
        mEmptyLayout.setBackgroundColor(mBgColor);

        mTvEmptyMessage.setOnClickListener(this);
        mIvEmptyMessage.setOnClickListener(this);
        _switchEmptyView();
    }

    /**
     * 隐藏视图
     */
    public void hide() {
        mEmptyStatus = STATUS_HIDE;
        _switchEmptyView();
    }

    /**
     * 设置状态
     *
     * @param emptyStatus
     */
    public void setEmptyStatus(@EmptyStatus int emptyStatus) {
        mEmptyStatus = emptyStatus;
        _switchEmptyView();
    }

    /**
     * 获取状态
     *
     * @return 状态
     */
    public int getEmptyStatus() {
        return mEmptyStatus;
    }

    /**
     * 设置异常消息
     *
     * @param msg 显示消息
     */
    public void setEmptyMessage(String msg) {
        mTvEmptyMessage.setText(msg);
    }


    /**
     * 设置图标
     *
     * @param resId 资源ID
     */
    public void setEmptyIcon(int resId) {
        mIvEmptyMessage.setVisibility(VISIBLE);
        mIvEmptyMessage.setImageResource(resId);
    }

    /**
     * 设置图标
     */
    public void setEmptyIconVisible(boolean isShow) {
        mIvEmptyMessage.setVisibility(isShow ? VISIBLE : GONE);
    }

    /**
     * 设置异常消息字体颜色
     *
     * @param color
     */
    public void setEmptyMessageColor(int color) {
        mTvEmptyMessage.setTextColor(getResources().getColor(color));
    }

    /**
     * 切换视图
     */
    private void _switchEmptyView() {
        switch (mEmptyStatus) {
            case STATUS_LOADING:
                setVisibility(VISIBLE);
                mRlEmptyContainer.setVisibility(GONE);
                mEmptyLoading.setVisibility(VISIBLE);
                break;
            case STATUS_NO_DATA:
                setVisibility(VISIBLE);
                if (emptyIconRes == -1) {
                    setEmptyIconVisible(false);
                } else {
                    if (emptyIconRes == 0) {
                        setEmptyIcon(R.mipmap.ic_no_collection);
                    } else {
                        setEmptyIcon(emptyIconRes);
                    }
                }

                if (TextUtils.isEmpty(emptyMessageStr)) {
                    setEmptyMessage("暂无数据");
                } else {
                    setEmptyMessage(emptyMessageStr);
                }
                if (TextUtils.isEmpty(emptyTitleStr)) {
                    mTvEmptyTitle.setVisibility(GONE);
                } else {
                    mTvEmptyTitle.setVisibility(VISIBLE);
                    mTvEmptyTitle.setText(emptyTitleStr);
                }
                mEmptyLoading.setVisibility(GONE);
                if (TextUtils.isEmpty(emptyBtnStr)) {
                    mBtnEmptyMessage.setVisibility(GONE);
                } else {
                    mBtnEmptyMessage.setVisibility(VISIBLE);
                    mBtnEmptyMessage.setText(emptyBtnStr);
                    if (netErrBtnClickListener != null) {
                        mBtnEmptyMessage.setOnClickListener(netErrBtnClickListener);
                    }
                }
                mRlEmptyContainer.setVisibility(VISIBLE);
                break;
            case STATUS_NO_NET:
                setVisibility(VISIBLE);
                setEmptyIcon(R.mipmap.ic_not_network);
                setEmptyMessage("网络连接已断开，请检查网络，点击重试");
                setBtnHide();
                mEmptyLoading.setVisibility(GONE);
                mRlEmptyContainer.setVisibility(VISIBLE);
                break;
            case STATUS_CUSTOM:
                setVisibility(VISIBLE);
                if (customRes != 0) {
                    setEmptyIcon(customRes);
                } else {
                    mIvEmptyMessage.setVisibility(GONE);
                }
                if (!TextUtils.isEmpty(customStr)) {
                    setEmptyMessage(customStr);
                } else {
                    mTvEmptyMessage.setVisibility(GONE);
                }
                mEmptyLoading.setVisibility(GONE);
                if (netErrBtnClickListener != null) {
                    mBtnEmptyMessage.setVisibility(VISIBLE);
                } else {
                    mBtnEmptyMessage.setVisibility(GONE);
                }
                mRlEmptyContainer.setVisibility(VISIBLE);
                break;
            case STATUS_NOLOGIN:
                setVisibility(VISIBLE);
                mTvEmptyTitle.setVisibility(GONE);
                setEmptyIcon(R.mipmap.icon_nologin);
                setEmptyMessage("你还未登录，请点击登录");
                mBtnEmptyMessage.setVisibility(VISIBLE);
                mBtnEmptyMessage.setText("登录");
                mBtnEmptyMessage.setOnClickListener(loginListener);
                break;
            case STATUS_HIDE:
                setVisibility(GONE);
                break;
            case STATUS_SERVER_ERROR:
                setVisibility(VISIBLE);
                setEmptyIcon(R.mipmap.icon_server_error);
                mTvEmptyTitle.setVisibility(GONE);
                setEmptyMessage("哎呦，有点小异常，请稍后再试");
                setBtnHide();
                mEmptyLoading.setVisibility(GONE);
                mBtnEmptyMessage.setVisibility(GONE);
                mRlEmptyContainer.setVisibility(VISIBLE);
                break;
        }
    }

    // 清除空占位自定义的数据
    public void clearNodata() {
        emptyIconRes = 0;
        emptyMessageStr = null;
        emptyTitleStr = null;
        emptyBtnStr = null;
    }

    public void setBtnText(String s, OnClickListener clickListener) {
        netErrBtnClickListener = clickListener;
        mBtnEmptyMessage.setVisibility(VISIBLE);
        mBtnEmptyMessage.setText(s);
        mBtnEmptyMessage.setOnClickListener(netErrBtnClickListener);
    }

    public void setBtnHide() {
        netErrBtnClickListener = null;
        mBtnEmptyMessage.setVisibility(GONE);
    }

    private final OnClickListener loginListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnToLoginListener != null) {
                mOnToLoginListener.toLogin();
            }
        }
    };

    /**
     * 设置重试监听器
     *
     * @param retryListener 监听器
     */
    public void setRetryListener(OnRetryListener retryListener) {
        this.mOnRetryListener = retryListener;
    }

    @Override
    public void onClick(View view) {
        if (mOnRetryListener != null) {
            mOnRetryListener.onRetry();
        }
    }

    /**
     * 点击重试监听器
     */
    public interface OnRetryListener {
        void onRetry();
    }

    public interface OnToLoginListener {
        void toLogin();
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_LOADING, STATUS_NO_NET, STATUS_NO_DATA, STATUS_CUSTOM, STATUS_NOLOGIN, STATUS_SERVER_ERROR})
    public @interface EmptyStatus {
    }
}