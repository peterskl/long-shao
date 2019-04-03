package my.com.myviewset;

import android.view.View;

import com.app.kekoo.common.base.BaseActivity;
import com.app.kekoo.common.commonwidget.NormalTitleBar;

import butterknife.Bind;
import my.com.myviewset.widget.rollnumber.RollNumberTextView;

/**
 * Created by huneng on 2018-07-03.
 */

public class RollNumberActivity extends BaseActivity {
    @Bind(R.id.roll_number)
    RollNumberTextView rollNumber;
    @Bind(R.id.toolbar)
    NormalTitleBar toolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_roll_number;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarColor(R.color.colorPrimaryDark).titleBar(R.id.toolbar).init();
    }


    @Override
    public void initView() {
        toolbar.setTitleText("滚动数字");
        toolbar.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rollNumber.startAnimator(13800183, 16982745);
    }

}
