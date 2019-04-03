package my.com.myviewset;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.app.kekoo.common.base.BaseActivity;
import com.app.kekoo.common.commonwidget.NormalTitleBar;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import my.com.myviewset.bean.NewLineBean;
import my.com.myviewset.widget.newlineitem.NewlineLayout;

/**
 * Created by huneng on 2018-07-04.
 */

public class NewLineActivity extends BaseActivity {
    @Bind(R.id.tv_title3)
    TextView tvTitle3;
    @Bind(R.id.edit_3)
    TextView edit3;
    @Bind(R.id.ll_edit3)
    LinearLayout llEdit3;
    @Bind(R.id.toolbar)
    NormalTitleBar toolbar;
    @Bind(R.id.tv_title2)
    TextView tvTitle2;
    @Bind(R.id.edit_2)
    TextView edit2;
    @Bind(R.id.ll_edit2)
    LinearLayout llEdit2;
    @Bind(R.id.activity_save_custom)
    LinearLayout activitySaveCustom;
    private PopupWindow popHouseType;
    private PopupWindow popAreaType;

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_line;
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
        toolbar.setTitleText("自定义弹窗");
        toolbar.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initHouseType();
        initAreaType();
    }

    //意向户型
    String houseType;
    String strs = "";

    private void initHouseType() {
        final View popupView = LayoutInflater.from(this).inflate(R.layout.dialog_house_type, null);
        popHouseType = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //实例化一个ColorDrawable颜色为半透明
        calWidthAndHeight(mContext);
        popHouseType.setWidth(mWidth);
        popHouseType.setHeight(mHeight);
        popHouseType.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        popHouseType.setFocusable(true);
        popHouseType.setOutsideTouchable(true);
        popHouseType.update();
        //监听PopupWindow 取消时，回调方法，  可以做一些操作
        popHouseType.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                              @Override
                                              public void onDismiss() {
                                                  WindowManager.LayoutParams params = getWindow().getAttributes();
                                                  params.alpha = 1f;
                                                  getWindow().setAttributes(params);
                                              }
                                          }
        );
        final Button button1 = (Button) popupView.findViewById(R.id.button1);
        final Button button2 = (Button) popupView.findViewById(R.id.button2);
        final Button button3 = (Button) popupView.findViewById(R.id.button3);
        final Button button4 = (Button) popupView.findViewById(R.id.button4);
        final Button button5 = (Button) popupView.findViewById(R.id.button5);
        popupView.findViewById(R.id.ok_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popHouseType.dismiss();
            }
        });
        popupView.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                button1 = (Button) v.findViewById(R.id.button1);
                if (button1.isSelected()) {
                    houseType = "";
                    button1.setSelected(false);
                    button1.setTextColor(Color.parseColor("#333333"));
                } else {
                    houseType += "1室";
                    strs += "1" + ",";
                    button1.setSelected(true);
                    button1.setTextColor(Color.parseColor("#F7D8CB"));
                }

            }
        });
        popupView.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                button2 = (Button) v.findViewById(R.id.button2);
                if (button2.isSelected()) {
                    houseType = "";
                    button2.setSelected(false);
                    button2.setTextColor(Color.parseColor("#333333"));
                } else {
                    houseType += "2室";
                    strs += "2" + ",";
                    button2.setSelected(true);
                    button2.setTextColor(Color.parseColor("#F7D8CB"));
                }
            }
        });
        popupView.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                button3 = (Button) v.findViewById(R.id.button3);
                if (button3.isSelected()) {
                    houseType = "";
                    button3.setSelected(false);
                    button3.setTextColor(Color.parseColor("#333333"));
                } else {
                    houseType += "3室";
                    strs += "3" + ",";
                    button3.setSelected(true);
                    button3.setTextColor(Color.parseColor("#F7D8CB"));
                }
            }
        });
        popupView.findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                button4 = (Button) v.findViewById(R.id.button4);
                if (button4.isSelected()) {
                    houseType = "";
                    button4.setSelected(false);
                    button4.setTextColor(Color.parseColor("#333333"));
                } else {
                    houseType += "4室";
                    strs += "4" + ",";
                    button4.setSelected(true);
                    button4.setTextColor(Color.parseColor("#F7D8CB"));
                }
            }
        });
        popupView.findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                button5 = (Button) v.findViewById(R.id.button5);
                if (button5.isSelected()) {
                    houseType = "";
                    button5.setSelected(false);
                    button5.setTextColor(Color.parseColor("#333333"));
                } else {
                    houseType += "4室以上";
                    strs += "5";
                    button5.setSelected(true);
                    button5.setTextColor(Color.parseColor("#F7D8CB"));
                }
            }
        });

        popupView.findViewById(R.id.ok_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.e(houseType);
                Logger.e("---strs" + strs);
                edit3.setText(houseType.replace("null", ""));
                button1.setSelected(false);
                button1.setTextColor(Color.parseColor("#333333"));
                button2.setSelected(false);
                button2.setTextColor(Color.parseColor("#333333"));
                button3.setSelected(false);
                button3.setTextColor(Color.parseColor("#333333"));
                button4.setSelected(false);
                button4.setTextColor(Color.parseColor("#333333"));
                button5.setSelected(false);
                button5.setTextColor(Color.parseColor("#333333"));
                houseType = "";
                strs = "";
                popHouseType.dismiss();
            }
        });

        popupView.findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button1.setSelected(false);
                button1.setTextColor(Color.parseColor("#333333"));
                button2.setSelected(false);
                button2.setTextColor(Color.parseColor("#333333"));
                button3.setSelected(false);
                button3.setTextColor(Color.parseColor("#333333"));
                button4.setSelected(false);
                button4.setTextColor(Color.parseColor("#333333"));
                button5.setSelected(false);
                button5.setTextColor(Color.parseColor("#333333"));
                houseType = "";
                strs = "";
            }
        });


//        houseType = "";
    }

    //意向片区
    private String area;
    private List<String> keyList = new ArrayList<>();
    private Map<String, String> keyMap = new LinkedHashMap<>();
    private NewlineLayout newlineLayout;
    private void initAreaType() {
        final View popupView = LayoutInflater.from(this).inflate(R.layout.dialog_area, null);
        popAreaType = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //实例化一个ColorDrawable颜色为半透明
        calWidthAndHeightArea(mContext);
        popAreaType.setWidth(mWidth1);
        popAreaType.setHeight(mHeight1);
        popAreaType.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        popAreaType.setFocusable(true);
        popAreaType.setOutsideTouchable(true);
        popAreaType.update();
        //监听PopupWindow 取消时，回调方法，  可以做一些操作
        popAreaType.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                             @Override
                                             public void onDismiss() {
                                                 WindowManager.LayoutParams params = getWindow().getAttributes();
                                                 params.alpha = 1f;
                                                 getWindow().setAttributes(params);
                                             }
                                         }
        );

        final NewlineLayout newlineLayout2 = (NewlineLayout) popupView.findViewById(R.id.key_layout2);
        newlineLayout = (NewlineLayout) popupView.findViewById(R.id.key_layout);

        newlineLayout2.setDataList(keyList, true);
        newlineLayout.setDataList(keyList, false);

        popupView.findViewById(R.id.ok_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> selectList = newlineLayout.getSelectList();
                if (selectList.size() > 0) {
                    for (int i = 0; i < selectList.size(); i++) {
                        String s = selectList.get(i);
                        if (i!=selectList.size()-1) {
                            area += s + ",";
                        }else {
                            area += s ;
                        }
                    }
                    edit2.setText(area.replace("null", ""));
                    area = "";
                }
                popAreaType.dismiss();
            }
        });

        newlineLayout2.setClickCallBack(new NewlineLayout.ClickCallBack() {
            @Override
            public void onAddIconClick() {
                startActivityForResult(NewLineDataActivity.class, 1001);

            }
        });

        popupView.findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                area = "";
                List<String> selectList = newlineLayout.getSelectList();
                selectList.clear();
                newlineLayout.clearAllState();
            }
        });

    }


    @OnClick({R.id.ll_edit3, R.id.ll_edit2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_edit3:
                WindowManager.LayoutParams params5 = getWindow().getAttributes();
                params5.alpha = 0.5f;
                getWindow().setAttributes(params5);
                popHouseType.showAtLocation(findViewById(R.id.activity_save_custom)
                        , Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ll_edit2:
                WindowManager.LayoutParams params6 = getWindow().getAttributes();
                params6.alpha = 0.5f;
                getWindow().setAttributes(params6);
                popAreaType.showAtLocation(findViewById(R.id.activity_save_custom)
                        , Gravity.BOTTOM, 0, 0);
                break;
        }
    }

    /**
     * 设置PopupWindow的大小
     *
     * @param context
     */
    private int mWidth;
    private int mHeight;

    private void calWidthAndHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);

        mWidth = metrics.widthPixels;
        //设置高度为全屏高度的70%
        mHeight = (int) (metrics.heightPixels * 0.3);
    }

    /**
     * 设置PopupWindow的大小
     *
     * @param context
     */
    private int mWidth1;
    private int mHeight1;

    private void calWidthAndHeightArea(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);

        mWidth1 = metrics.widthPixels;
        //设置高度为全屏高度的70%
        mHeight1 = (int) (metrics.heightPixels * 0.5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            NewLineBean bean = (NewLineBean)data.getSerializableExtra("bean");
            if (keyList.size()>0) {
                for (int i = 0; i <keyList.size(); i++) {
                    if (keyList.get(i).equals(bean.getText())){
                        return;
                    }
                }
            }
            keyList.add(bean.getText());
            keyMap.put(bean.getKey(),bean.getText());
            newlineLayout.setDataList(keyList, false);
        }
    }

}
