package my.com.myviewset;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.kekoo.common.base.BaseActivity;
import com.app.kekoo.common.commonwidget.NormalTitleBar;

import butterknife.Bind;
import my.com.myviewset.widget.water.WaterView;

/**
 * Created by huneng on 2018-07-03.
 */

public class WaterViewActivity extends BaseActivity {
    private float temp = 0;
    private WaterView wv;
    private EditText et_range;
    private Button btn_range;
    @Bind(R.id.toolbar)
    NormalTitleBar toolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_water_view;
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
        toolbar.setTitleText("水波纹动画");
        toolbar.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        wv = (WaterView) findViewById(R.id.wv);
        et_range = (EditText) findViewById(R.id.et_range);
        btn_range = (Button) findViewById(R.id.btn_range);
        // 水波移动
//        ObjectAnimator translate = ObjectAnimator.ofFloat(wv,"wvTranslationX",0f,1f);
//        translate.setRepeatCount(ValueAnimator.INFINITE);
//        translate.setInterpolator(new LinearInterpolator());
//        translate.setDuration(1000);

//        start(null);

        initListener();
    }

//    public void goDemo(View view) {
//        startActivity(new Intent(this, DemoActivity.class));
//    }

    public void up(View view){
        wv.setProgress(temp);
        temp += 0.1f;

    }

    public void start(View view){
        ValueAnimator va = ValueAnimator.ofObject(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                wv.setProgress(fraction);
//                wv.setWaveTranslateRatio(fraction);
                return null;
            }
        }, 0, 1);
        va.setDuration(10000);
        va.start();
    }

    private void initListener(){
        btn_range.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = et_range.getText().toString();
                wv.setRange(Integer.valueOf(temp));
            }
        });

    }

    public void reset(View view){
        wv.setProgress(0);
        temp = 0;
    }
}
