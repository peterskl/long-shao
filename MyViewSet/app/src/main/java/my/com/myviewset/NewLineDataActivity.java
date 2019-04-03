package my.com.myviewset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.kekoo.common.base.BaseActivity;
import com.app.kekoo.common.commonwidget.NormalTitleBar;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import my.com.myviewset.adapter.NewLineAdater;
import my.com.myviewset.bean.NewLineBean;

/**
 * Created by huneng on 2018-07-05.
 */

public class NewLineDataActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    NormalTitleBar toolbar;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<NewLineBean> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_data;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarColor(R.color.colorPrimaryDark).titleBar(toolbar).init();
    }

    @Override
    public void initView() {
        toolbar.setTitleText("小区搜索");
        toolbar.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NewLineAdater adater = new NewLineAdater(R.layout.new_data_item,list);
        recyclerView.setAdapter(adater);
        adater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                NewLineBean bean = (NewLineBean) adapter.getItem(position);
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", bean);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initData() {
        list.add(new NewLineBean("1","广州恒大地产"));
        list.add(new NewLineBean("2","深圳碧桂园"));
        list.add(new NewLineBean("3","北京颐和山庄"));
        list.add(new NewLineBean("4","广州颐和盛世"));
        list.add(new NewLineBean("5","深圳碧桂园"));
        list.add(new NewLineBean("6","深圳颐和盛世"));
        list.add(new NewLineBean("7","广州颐和山庄"));
        list.add(new NewLineBean("8","北京碧桂园"));
        list.add(new NewLineBean("9","深圳其它地区"));
        list.add(new NewLineBean("10","北京恒大地产"));
        list.add(new NewLineBean("11","深圳恒大地产"));
        list.add(new NewLineBean("12","上海恒大地产"));
        list.add(new NewLineBean("13","杭州颐和盛世"));
        list.add(new NewLineBean("14","上海颐和山庄"));
        list.add(new NewLineBean("15","杭州碧桂园"));
        list.add(new NewLineBean("16","广州其他地区"));
    }


}
