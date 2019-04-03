package my.com.myviewset;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.app.kekoo.common.base.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import my.com.myviewset.adapter.MainAdapter;
import my.com.myviewset.bean.MainBean;

public class MainActivity extends BaseActivity {
    private static final Class<?>[] ACTIVITY = {RollNumberActivity.class, WaterViewActivity.class, NewLineActivity.class, BannerActivity.class, ClassifyActivity.class, RollNumberActivity.class, RollNumberActivity.class, RollNumberActivity.class, RollNumberActivity.class, RollNumberActivity.class};
    private static final String[] TITLE = {"RollNumber", "WaterView", "AddNewLine", "Banner", "Classify", "EmptyView", "DragAndSwipe", "ItemClick", "ExpandableItem", "DataBinding"};
    private static final int[] IMG = {R.mipmap.badge_fushan, R.mipmap.badge_shenyang, R.mipmap.badge_guangzhou, R.mipmap.badge_huhehaote, R.mipmap.badge_jiangling, R.mipmap.badge_kaicheng, R.mipmap.badge_lasa, R.mipmap.badge_luguhu, R.mipmap.badge_kaicheng, R.mipmap.badge_shanghai,};
    private RecyclerView recyclerView;
    private ArrayList<MainBean> mDataList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {
        System.out.println("---------:测试");
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarColor(R.color.colorPrimaryDark).init();
    }

    @Override
    public void initView() {
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        MainAdapter adapter = new MainAdapter(R.layout.main_item,mDataList);
        adapter.openLoadAnimation();
        View top = getLayoutInflater().inflate(R.layout.top_view, (ViewGroup) recyclerView.getParent(), false);
        adapter.addHeaderView(top);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MainActivity.this, ACTIVITY[position]);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < TITLE.length; i++) {
            MainBean item = new MainBean();
            item.setTitle(TITLE[i]);
            item.setActivity(ACTIVITY[i]);
            item.setImageResource(IMG[i]);
            mDataList.add(item);
        }
    }

}
