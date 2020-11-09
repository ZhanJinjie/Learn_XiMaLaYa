package com.example.myximalaya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mMainIndicator = this.findViewById(R.id.main_indicator);
        mMainIndicator.setBackgroundColor(this.getResources().getColor(R.color.main_color));
        // 创建indicator的适配器
        IndicatorAdaptor indicatorAdaptor = new IndicatorAdaptor(this);
        CommonNavigator commonNavigator = new CommonNavigator(this); // 通用指示器
        commonNavigator.setAdapter(indicatorAdaptor); // 设置适配器

        // ViewPager
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

    // 测试是否可以拿到数据
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