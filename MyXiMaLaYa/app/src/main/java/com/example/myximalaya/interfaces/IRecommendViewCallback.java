package com.example.myximalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;
// 逻辑层通知界面更新接口
public interface IRecommendViewCallback {
    /**
     * 获取推荐内容的结果
     *
     * @param result
     */
    void onRecommendListLoaded(List<Album> result);

    /**
     * 上拉刷新
     *
     * @param result
     */
    void onLoadMore(List<Album> result);

    /**
     * 下拉加载
     *
     * @param result
     */
    void onRefreshMore(List<Album> result);


}
