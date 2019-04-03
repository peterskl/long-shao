package my.com.myviewset.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import my.com.myviewset.R;
import my.com.myviewset.bean.NewLineBean;

/**
 * Created by huneng on 2018-07-05.
 */

public class NewLineAdater extends BaseQuickAdapter<NewLineBean,BaseViewHolder> {
    public NewLineAdater(@LayoutRes int layoutResId, @Nullable List<NewLineBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewLineBean item) {
        helper.setText(R.id.text,item.getText());
    }
}
