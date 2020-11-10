package com.example.myximalaya.interfaces;
// 页面发起的动作
public interface IRecommendPresenter {
    /**
     * 获取推荐内容
     */
    void getRecommendList();
    /**
     * 下拉刷新
     */
    void pull2RefreshMore();
    /**
     * 上拉加载
     */
    void loadMore();

    /**
     * 这个方法用于注册UI的回调
     * @param callback
     */
    void registerViewCallback(IRecommendViewCallback callback);

    /**
     * 取消UI的回调
     * @param callback
     */
    void unRegisterViewCallback(IRecommendViewCallback callback);
}
