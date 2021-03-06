package com.example.myximalaya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.example.myximalaya.adapters.IndicatorAdaptor;
import com.example.myximalaya.adapters.MainContentAdapter;
import com.example.myximalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.category.Category;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String Tag = "MainActivity";
    private MagicIndicator mMainIndicator;
    private ViewPager mContentViewPager;
    private IndicatorAdaptor mIndicatorAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    private void initEvent() {
        // 监听 indicator 的点击事件
        mIndicatorAdaptor.setOnIndicatorTabClickListener(new IndicatorAdaptor.OnIndicatorTabClickListener() {
            @Override
            public void onTabClick(int index) {
            if (mContentViewPager != null) {
                LogUtil.d(Tag, "indicator click--->" + index);
                // 设置 viewpager 的位置
                mContentViewPager.setCurrentItem(index);
            }
            }
        });
    }

    private void initView() {
        // 获取指示器 indicator
        mMainIndicator = this.findViewById(R.id.main_indicator);
        mMainIndicator.setBackgroundColor(this.getResources().getColor(R.color.main_color));
        // 创建indicator的适配器
        mIndicatorAdaptor = new IndicatorAdaptor(this);
        // 创建通用指示器
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true); // 自动调整样式平均分布
        commonNavigator.setAdapter(mIndicatorAdaptor); // 为indicator设置适配器

        // 获取 ViewPager
        mContentViewPager = this.findViewById(R.id.content_pager);
        // 创建内容适配器
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        MainContentAdapter mainContentAdapter = new MainContentAdapter(supportFragmentManager);
        // 内容适配器绑定到ViewPager组件上
        mContentViewPager.setAdapter(mainContentAdapter);
        // 把 indicator 和 viewpager 绑定到一起 （ 把viewpager 交给 indicator）
        mMainIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMainIndicator, mContentViewPager);

    }

    // 测试集成的SDK，是否可以拿到数据
    private void testSuccess() {
        Map<String, String> map = new HashMap<String, String>();
        CommonRequest.getCategories(map, new IDataCallBack<CategoryList>() {
            @Override
            public void onSuccess(CategoryList categoryList) {
                List<Category> categories = categoryList.getCategories();
                if (categories != null) {
                    int size = categories.size();
                    Log.d(Tag, "categories --< " + size);
                    for (Category category : categories) {
                        LogUtil.d(Tag, "category --> " + category.getCategoryName());
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                LogUtil.e(Tag, "error code -- " + i + ", error msg ==> " + s);
            }
        });
    }
}