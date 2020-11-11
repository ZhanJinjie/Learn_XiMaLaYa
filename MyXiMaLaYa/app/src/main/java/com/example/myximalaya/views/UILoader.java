package com.example.myximalaya.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.myximalaya.R;
import com.example.myximalaya.base.BaseApplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class UILoader extends FrameLayout {

    private View mLoadingView;
    private View mSuccessView;
    private View mNetWorkErrorView;
    private View mEmptyView;

    // 定义状态，枚举类
    public enum UIStatus {
        LOADING, SUCCESS, NETWORK_ERROR, EMPTY, NONE
    }

    public UIStatus mCurrentStatus = UIStatus.NONE;

    public UILoader(@NonNull Context context) {
        this(context, null);
    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    /**
     * 更新UI
     * @param status
     */
    public void updateStatus(UIStatus status) {
        mCurrentStatus = status;
        // 更新UI 一定要在主线程上
        BaseApplication.getsHandler().post(new Runnable() {
            @Override
            public void run() {
                switchUIByCurrentStatus();
            }
        });
    }

    /**
     * 初始化UI
     */
    private void init() {
        switchUIByCurrentStatus();
    }

    /**
     * 通过当前状态创建UI
     */
    private void switchUIByCurrentStatus() {
        // 1. 加载中
        if (mLoadingView == null) {
            mLoadingView = getLoadingView();
            addView(mLoadingView);
        }
        // 根据状态设置是否可见
        mLoadingView.setVisibility(mCurrentStatus==UIStatus.LOADING?VISIBLE:GONE);

        // 2. 成功，不知道显示什么，做成一个抽象类，让孩子去实现
        if (mSuccessView == null) {
            mSuccessView = getSuccessView(this);
            addView(mSuccessView);

        }
        // 根据状态设置是否可见
        mSuccessView.setVisibility(mCurrentStatus==UIStatus.SUCCESS?VISIBLE:GONE);

        // 3. 网络错误
        if (mNetWorkErrorView == null) {
            mNetWorkErrorView = getNetWorkErrorView();
            addView(mNetWorkErrorView);
        }
        // 根据状态设置是否可见
        mNetWorkErrorView.setVisibility(mCurrentStatus==UIStatus.NETWORK_ERROR?VISIBLE:GONE);

        // 4. 数据为空
        if (mEmptyView == null) {
            mEmptyView = getEmptyView();
            addView(mEmptyView);
        }
        // 根据状态设置是否可见
        mEmptyView.setVisibility(mCurrentStatus==UIStatus.EMPTY?VISIBLE:GONE);
    }

    protected abstract View getSuccessView(ViewGroup container);

    private View getEmptyView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_empty_view, this, false);
    }

    private View getNetWorkErrorView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_networkerror_view, this, false);
    }

    private View getLoadingView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_loading_view, this, false);
    }
}
