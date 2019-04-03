package my.com.myviewset.widget.newlineitem;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import my.com.myviewset.R;


/**
 * Created by cpt on 2018/6/5.
 */

public class NewLineItemView extends FrameLayout {

    private TextView mTvTitle;
    private String mTitleStr;
    private boolean isSelect = false;

    public NewLineItemView(@NonNull Context context) {
        this(context, null);
    }

    public NewLineItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewLineItemView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.new_line_layout_view, this, true);
        setBackgroundResource(R.drawable.line_item_text_normal_bg);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mTvTitle != null) {
            String tmpStr= mTitleStr;
            tmpStr = TextUtils.ellipsize(tmpStr, mTvTitle.getPaint(), w, TextUtils.TruncateAt.MIDDLE).toString();
            mTvTitle.setText(tmpStr);
        }
    }

    private void init() {
        mTvTitle = (TextView) findViewById(R.id.title);
    }

    public void setText(String text) {
        mTitleStr = text;
        mTvTitle.setText(mTitleStr);
        if (!mTitleStr.equals("+")) {
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    isSelect = !isSelect;
                    setBackgroundResource(isSelect ? R.drawable.line_item_text_select_bg : R.drawable.line_item_text_normal_bg);
                    mTvTitle.setTextColor(isSelect ? 0xffFF9000 : 0xff333333);
                }
            });
        }
    }

    public void changeState(boolean isSelect){
        this.isSelect = isSelect;
        setBackgroundResource(isSelect ? R.drawable.line_item_text_select_bg : R.drawable.line_item_text_normal_bg);
        mTvTitle.setTextColor(isSelect ? 0xffFF9000 : 0xff333333);
    }

    public boolean isSelect() {
        return isSelect;
    }

    public String getText() {
        return mTitleStr;
    }
}
