package com.example.myximalaya.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myximalaya.R;
import com.example.myximalaya.adapters.RecommendListAdapter;
import com.example.myximalaya.base.BaseFragment;
import com.example.myximalaya.utils.Constants;
import com.example.myximalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecommendFragment extends BaseFragment {
    private static final String Tag = "RecommendFragment";
    private RecyclerView mRecommendRv;
    private RecommendListAdapter mRecommendListAdapter;

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        // view 加载完成
        View rootView = layoutInflater.inflate(R.layout.fragment_recommend, container, false);
        // RecycleView 的使用
        // 1. 找到控件
        mRecommendRv = rootView.findViewById(R.id.recommend_list);
        // 2. 设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecommendRv.setLayoutManager(linearLayoutManager);
        // 3. 设置适配器
        mRecommendListAdapter = new RecommendListAdapter();
        mRecommendRv.setAdapter(mRecommendListAdapter);

        // 获取数据
        getRecommendData();

        // 返回 view， 给界面显示
        return rootView;
    }

    /**
     * 获取推荐内容
     * 3.10.6 获取猜你喜欢专辑   by 喜马拉雅SDK接入文档.html
     */
    private void getRecommendData() {
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
                        upRecommendUI(albumList);
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

    private void upRecommendUI(List<Album> albumList) {
        // 把数据设置给适配器，并且更新UI
        mRecommendListAdapter.setData(albumList);
    }

}
