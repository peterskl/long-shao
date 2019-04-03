package my.com.myviewset.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import my.com.myviewset.R;
import my.com.myviewset.bean.ClassifyBean;
import my.com.myviewset.bean.ClassifyEvent;

/**
 * Created by Administrator on 2017/12/22.
 */

public class ClassifyGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ClassifyBean> mDataList = new ArrayList<>();

    public void setData(List<ClassifyBean> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemHolder itemHolder = new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.expand_icon_item_view, null));
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemHolder) {
            ((ItemHolder) holder).setData(mDataList.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new ClassifyEvent(ClassifyEvent.ALL_CLASSIFY_GRID_CLICK_EVENT, position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        private final TextView mTitleTv;
        private final ImageView mIconIv;

        public ItemHolder(View itemView) {
            super(itemView);
            mTitleTv = (TextView) itemView.findViewById(R.id.icon_title_tv);
            mIconIv = (ImageView) itemView.findViewById(R.id.icon_iv);
        }

        public void setData(ClassifyBean bean) {
            if (bean != null) {
                int imageUrl;
                imageUrl = bean.getMaleImg();
                Glide.with(itemView.getContext()).load(imageUrl).into(mIconIv);
                if (!TextUtils.isEmpty(bean.getCategoryName())) {
                    mTitleTv.setText(bean.getCategoryName());
                }
            }
        }
    }
}
