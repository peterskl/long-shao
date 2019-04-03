package my.com.myviewset.adapter;

import android.support.annotation.LayoutRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import my.com.myviewset.R;
import my.com.myviewset.bean.Movie;

/**
 * Created by huneng on 2018-07-07.
 */

public class BannerAdapter extends BaseQuickAdapter<Movie,BaseViewHolder> {

    public BannerAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Movie item) {
        helper.setText(R.id.lmi_title, item.filmName)
                .setText(R.id.lmi_actor, item.actors)
                .setText(R.id.lmi_grade, item.grade)
                .setText(R.id.lmi_describe, item.shortinfo);
        ImageView imageView = helper.getView(R.id.lmi_avatar);
        Glide.with(mContext).load(item.picaddr).into(imageView);
    }
}
