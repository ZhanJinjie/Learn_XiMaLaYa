package com.example.myximalaya.presenters;

import com.example.myximalaya.interfaces.IRecommendPresenter;
import com.example.myximalaya.interfaces.IRecommendViewCallback;
import com.example.myximalaya.utils.Constants;
import com.example.myximalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendPresenter implements IRecommendPresenter {

    private static final String Tag = "RecommendPresenter";

    private List<IRecommendViewCallback> mCallbacks = new ArrayList<>();

    private RecommendPresenter(){}

    private static RecommendPresenter sInstance = null;

    /**
     * 获取单例对象
     * @return sInstance
     */
    public static RecommendPresenter getInstance() {
        // 懒汉式单例
        if (sInstance == null) {
            synchronized (RecommendPresenter.class) {
                if (sInstance == null) {
                    sInstance = new RecommendPresenter();
                }
            }
        }
        return sInstance;
    }
    /**
     * 获取推荐内容
     * 3.10.6 获取猜你喜欢专辑   by 喜马拉雅SDK接入文档.html
     */
    @Override
    public void getRecommendList() {
        // 封装参数
        Map<String, String> map = new HashMap<String, String>();
        // 这个参数是指一页数据返回多少条
        map.put(DTransferConstants.LIKE_COUNT, Constants.RECOMMEND_COUNT + "");
        CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {
            @Override
            public void onSuccess(GussLikeAlbumList gussLikeAlbumList) {
                LogUtil.d(Tag, "thread name ---> " + Thread.currentThread().getName());
                // 数据获取成功
                if (gussLikeAlbumList != null) {
                    List<Album> albumList = gussLikeAlbumList.getAlbumList();
                    if (albumList != null) {
                        // 数据回来之后更新UI
                        handlerRecommendResult(albumList);
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                // 数据获取出错
                LogUtil.d(Tag, "error --> " + i);
                LogUtil.d(Tag, "errorMsg --> " + s);
            }
        });
    }

    private void handlerRecommendResult(List<Album> albumList) {
        // 通知UI更新
        if (mCallbacks != null) {
            for (IRecommendViewCallback callback : mCallbacks) {
                callback.onRecommendListLoaded(albumList);
            }
        }
    }

    @Override
    public void pull2RefreshMore() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void registerViewCallback(IRecommendViewCallback callback) {
        if (mCallbacks!=null && !mCallbacks.contains(callback)) { // 防止重复加入
            mCallbacks.add(callback);
        }
    }

    @Override
    public void unRegisterViewCallback(IRecommendViewCallback callback) {
        if (mCallbacks != null) {
            mCallbacks.remove(mCallbacks);
        }
    }
}
