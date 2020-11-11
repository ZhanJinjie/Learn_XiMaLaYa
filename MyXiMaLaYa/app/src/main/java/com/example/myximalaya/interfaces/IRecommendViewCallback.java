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
     * 网络错误
     */
    void onNetworkError();

    /**
     * 数据为空
     */
    void onEmpty();

    /**
     * 正在加载
     */
    void  onLoading();
}
