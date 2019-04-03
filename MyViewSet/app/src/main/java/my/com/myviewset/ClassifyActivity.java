package my.com.myviewset;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.kekoo.common.base.BaseActivity;
import com.app.kekoo.common.commonwidget.NormalTitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import my.com.myviewset.adapter.ClassifyGridAdapter;
import my.com.myviewset.adapter.ClassifyPageAdapter;
import my.com.myviewset.bean.ClassifyBean;
import my.com.myviewset.bean.ClassifyEvent;
import my.com.myviewset.fragment.ClassifyPageFragment;
import my.com.myviewset.widget.ObservableHorizontalScrollView;
import my.com.myviewset.widget.PagerSlidingTabStrip;
import my.com.utils.DividerGridItemDecoration;

/**
 * Created by huneng on 2018-07-07.
 */

public class ClassifyActivity extends BaseActivity {
    @Bind(R.id.title_tab_layout)
    NormalTitleBar titleTabLayout;
    @Bind(R.id.tabStrip)
    PagerSlidingTabStrip mTabStrip;
    @Bind(R.id.white_gradient_layout)
    LinearLayout mWhiteGradientLayout;
    @Bind(R.id.show_all_classify_layout)
    LinearLayout mShowAllClassifyLayout;
    @Bind(R.id.tab_strip_layout)
    RelativeLayout tabStripLayout;
    @Bind(R.id.tmp_line_view)
    View tmpLineView;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.close_layout)
    LinearLayout mCloseLayout;
    @Bind(R.id.all_classify_title_layout)
    RelativeLayout allClassifyTitleLayout;
    @Bind(R.id.line_view)
    View lineView;
    @Bind(R.id.all_classify_grid_recycler_view)
    RecyclerView mGridRecyclerView;
    @Bind(R.id.all_classify_layout)
    RelativeLayout mAllClassifyLayout;
    private ClassifyPageAdapter mPagerAdapter;
    private List<ClassifyPageFragment> fragments = new ArrayList<>();
    private List<ClassifyBean> beanList = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_calssify;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {
        titleTabLayout.setTitleText("分类");
        titleTabLayout.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        mGridRecyclerView.setLayoutManager(mLayoutManager);
        mGridRecyclerView.addItemDecoration(new DividerGridItemDecoration(mContext, R.drawable.class_grid_divider));

        initData();
        mTabStrip.setIsNeedShortIndicatorLine(true);
        mTabStrip.setIndicatorIsRoundRect(true);
        mTabStrip.setScrollViewListener(new ObservableHorizontalScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableHorizontalScrollView scrollView, int x, int y, int oldx, int oldy) {
                int scrollX = scrollView.getScrollX();
                int width = scrollView.getMeasuredWidth();
                if (scrollX - (scrollView.getChildAt(0).getMeasuredWidth() - width) >= 0) {
                    mWhiteGradientLayout.setVisibility(View.INVISIBLE);
                } else {
                    mWhiteGradientLayout.setVisibility(View.VISIBLE);
                }
            }
        });


        mCloseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAllClassifyLayout.setVisibility(View.INVISIBLE);
            }
        });

        mShowAllClassifyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAllClassifyLayout.setVisibility(View.VISIBLE);
            }
        });

        mAllClassifyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAllClassifyLayout.setVisibility(View.INVISIBLE);
            }
        });


        mPagerAdapter = new ClassifyPageAdapter(getSupportFragmentManager(), mTabStrip);
        mViewPager.setAdapter(mPagerAdapter);
        mTabStrip.setViewPager(mViewPager);
        mTabStrip.setSelectedTextColor(0xffFF4338);
        mTabStrip.setTextColor(0xff909090);
        mPagerAdapter.setDataList(beanList,fragments);


        ClassifyGridAdapter mGridAdapter = new ClassifyGridAdapter();
        mGridRecyclerView.setAdapter(mGridAdapter);
        mGridAdapter.setData(beanList);
//
//        mClassifyPresenter.getClassifyData(-1);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleClassifyTabEvent(ClassifyEvent event) {
        switch (event.getWhat()) {
            case ClassifyEvent.ALL_CLASSIFY_GRID_CLICK_EVENT:
                int position = (int) event.getData();
                mViewPager.setCurrentItem(position);
                mAllClassifyLayout.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void initData() {

        for (int i = 0; i < 12; i++) {
            fragments.add(new ClassifyPageFragment());
        }

        beanList.add(new ClassifyBean("女装",R.mipmap.badge_fushan));
        beanList.add(new ClassifyBean("美妆个护",R.mipmap.badge_guangzhou));
        beanList.add(new ClassifyBean("鞋包配件",R.mipmap.badge_huhehaote));
        beanList.add(new ClassifyBean("母婴",R.mipmap.badge_jiangling));
        beanList.add(new ClassifyBean("车品",R.mipmap.badge_jingangshan));
        beanList.add(new ClassifyBean("饮食酒饮",R.mipmap.badge_jizhoudao));
        beanList.add(new ClassifyBean("家具百货",R.mipmap.badge_kaicheng));
        beanList.add(new ClassifyBean("内衣",R.mipmap.badge_lasa));
        beanList.add(new ClassifyBean("数码家电",R.mipmap.badge_luguhu));
        beanList.add(new ClassifyBean("家装家具",R.mipmap.badge_pingrang));
        beanList.add(new ClassifyBean("运动户外",R.mipmap.badge_qingdao));
        beanList.add(new ClassifyBean("男装",R.mipmap.badge_shanghai));
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarColor(R.color.colorPrimaryDark).titleBar(R.id.title_tab_layout).init();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
