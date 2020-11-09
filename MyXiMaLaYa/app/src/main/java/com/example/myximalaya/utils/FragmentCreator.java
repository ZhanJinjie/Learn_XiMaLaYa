package com.example.myximalaya.utils;

import com.example.myximalaya.base.BaseFragment;
import com.example.myximalaya.fragments.HistoryFragment;
import com.example.myximalaya.fragments.RecommendFragment;
import com.example.myximalaya.fragments.SubscriptionFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentCreator {
    public final static int INDEX_RECOMMEND = 0;
    public final static int INDEX_SUBSCRIPTION = 1;
    public final static int INDEX_HISTORY = 2;

    public final static int PAGE_COUNT = 3;

    // 缓存
    private static Map<Integer, BaseFragment> sCache = new HashMap<>();

    // 获取/创建  fragment
    public static BaseFragment getFragment(int index) {
        BaseFragment baseFragment = sCache.get(index);
        if (baseFragment != null) { // 存在已缓存的fragment
            return baseFragment;
        }
        // 创建新的fragment
        switch (index) {
            case INDEX_RECOMMEND: // 推荐
                baseFragment = new RecommendFragment();
                break;
            case INDEX_SUBSCRIPTION: // 订阅
                baseFragment = new SubscriptionFragment();
                break;
            case INDEX_HISTORY: // 历史
                baseFragment = new HistoryFragment();
                break;
        }
        sCache.put(index, baseFragment);
        return baseFragment;
    }
}
