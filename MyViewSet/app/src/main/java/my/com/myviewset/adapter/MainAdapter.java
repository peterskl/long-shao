package my.com.myviewset.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import my.com.myviewset.R;
import my.com.myviewset.bean.MainBean;

/**
 * Created by huneng on 2018-07-03.
 */

public class MainAdapter extends BaseQuickAdapter<MainBean,BaseViewHolder> {
    public MainAdapter(@LayoutRes int layoutResId, @Nullable List<MainBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainBean item) {
        helper.setText(R.id.text, item.getTitle());
        helper.setImageResource(R.id.icon, item.getImageResource());
    }
}
