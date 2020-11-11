package com.example.myximalaya.fragments;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myximalaya.R;
import com.example.myximalaya.adapters.RecommendListAdapter;
import com.example.myximalaya.base.BaseFragment;
import com.example.myximalaya.interfaces.IRecommendViewCallback;
import com.example.myximalaya.presenters.RecommendPresenter;
import com.example.myximalaya.views.UILoader;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecommendFragment extends BaseFragment implements IRecommendViewCallback {
    private static final String Tag = "RecommendFragment";
    private RecyclerView mRecommendRv;
    private RecommendListAdapter mRecommendListAdapter;
    private RecommendPresenter mRecommendPresenter;
    private UILoader mUiLoader;
    private View mRootView;

    @Override
    protected View onSubViewLoaded(final LayoutInflater layoutInflater, ViewGroup container) {


        mUiLoader = new UILoader(getContext()) {
            @Override
            protected View getSuccessView(ViewGroup container) {
                return createSuccessView(layoutInflater, container);
            }
        };


        // 获取到逻辑层的对象
        mRecommendPresenter = RecommendPresenter.getInstance();
        // 先要设置通知接口的注册
        mRecommendPresenter.registerViewCallback(this);
        // 获取推荐列表
        mRecommendPresenter.getRecommendList();

        // 返回 view， 给界面显示
        // 绑定UILoader 之前先解绑，不能绑定两个view
        if (mUiLoader.getParent() instanceof ViewGroup) {
            ((ViewGroup) mUiLoader.getParent()).removeView(mUiLoader); // 让老爸干掉自己
        }

        return mUiLoader;
    }

    private View createSuccessView(LayoutInflater layoutInflater, ViewGroup container) {
        // view 加载完成
        mRootView = layoutInflater.inflate(R.layout.fragment_recommend, container, false);

        // RecycleView 的使用
        // 1. 找到控件
        mRecommendRv = mRootView.findViewById(R.id.recommend_list);
        // 2. 设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecommendRv.setLayoutManager(linearLayoutManager);
        // 设置间距
        mRecommendRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = UIUtil.dip2px(view.getContext(), 5); // dp 转换 px
                outRect.bottom = UIUtil.dip2px(view.getContext(), 5); // dp 转换 px
                outRect.left = UIUtil.dip2px(view.getContext(), 5); // dp 转换 px
                outRect.right = UIUtil.dip2px(view.getContext(), 5); // dp 转换 px
            }
        });

        // 3. 设置适配器
        mRecommendListAdapter = new RecommendListAdapter();
        mRecommendRv.setAdapter(mRecommendListAdapter);
        return mRootView;
    }

    @Override
    public void onRecommendListLoaded(List<Album> result) {
        // 当我们获取到推荐内容的时候，这个方法就会被调用（成功了）
        // 把数据设置给适配器，并且更新UI
        mRecommendListAdapter.setData(result);
        mUiLoader.updateStatus(UILoader.UIStatus.SUCCESS);
    }

    @Override
    public void onNetworkError() {
        mUiLoader.updateStatus(UILoader.UIStatus.NETWORK_ERROR);
    }

    @Override
    public void onEmpty() {
        mUiLoader.updateStatus(UILoader.UIStatus.EMPTY);
    }

    @Override
    public void onLoading() {
        mUiLoader.updateStatus(UILoader.UIStatus.LOADING);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 取消接口的注册
        if (mRecommendPresenter != null) {
            mRecommendPresenter.unRegisterViewCallback(this);
        }
    }
}
