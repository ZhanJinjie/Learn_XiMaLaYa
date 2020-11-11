package com.example.myximalaya.interfaces;
// 页面发起的动作
public interface IRecommendPresenter {
    /**
     * 获取推荐内容
     */
    void getRecommendList();
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
