package my.com.myviewset;

import android.content.Intent;
import android.os.Handler;

import com.app.kekoo.common.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initPresenter() {

    }


    @Override
    public void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}
