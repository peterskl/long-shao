package my.com.utils;

import android.widget.ImageView;

import com.app.kekoo.common.baseapp.BaseApplication;
import com.app.kekoo.common.glideloader.loader.ImageLoader;

import my.com.myviewset.R;


/**
 * Created by huneng on 2017/11/13.
 */

public class ImageLoadUtils {

    /**
     * url(String url) /／url
     file(String filePath) //加载SD卡资源
     file(File file) //加载SD卡资源
     res(int resId) //加载drawable资源
     content(String contentProvider) //加载ContentProvider资源
     raw(String rawPath) //加载raw资源
     asserts(String assertspath) //加载asserts资源
     thumbnail(float thumbnail)//缩略图
     rectRoundCorner(int rectRoundRadiusf) //形状为圆角矩形时的圆角半径
     priority(int priority) //优先级
     error(int errorResId) //错误占位图
     asSquare() //形状为正方形
     colorFilter(int color) //颜色滤镜
     diskCacheStrategy(DiskCacheStrategy diskCacheStrategy) //DiskCacheStrategy.NONE :不缓存图片 ／DiskCacheStrategy.SOURCE :缓存图片源文件／DiskCacheStrategy.RESULT:缓存修改过的图片／DiskCacheStrategy.ALL:缓存所有的图片，默认
     ignoreCertificateVerify(boolean ignoreCertificateVerify) // https是否忽略校验
     asCircle()//加载圆形图片
     placeHolder(int placeHolderResId) //占位图
     override(int oWidth, int oHeight) //加载图片时设置分辨率 a
     scale(int scaleMode) // CENTER_CROP等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示 ; FIT_CENTER 等比例缩放图片，宽或者是高等于ImageView的宽或者是高 默认：FIT_CENTER
     animate(int animationId ) 引入动画
     animate( Animation animation) 引入动画
     animate(ViewPropertyAnimation.Animator animato) 引入动画
     asBitmap(BitmapListener bitmapListener)// 使用bitmap不显示到imageview
     into(View targetView) //展示到imageview
     colorFilter(int filteColor) //颜色滤镜
     blur(int blurRadius) ／/高斯模糊
     brightnessFilter(float level) //调节图片亮度
     grayscaleFilter() //黑白效果
     swirlFilter() //漩涡效果
     toonFilter() //油画效果
     sepiaFilter() //水墨画效果
     contrastFilter(float constrasrLevel) //锐化效果
     invertFilter() //胶片效果
     pixelationFilter(float pixelationLevel) //马赛克效果
     sketchFilter() // //素描效果
     vignetteFilter() //晕映效果
     */



    public static void baseLoad(String url, ImageView imageView) {
        ImageLoader.with(BaseApplication.getAppContext())
                .url(url)//加载网络地址
                .placeHolder(R.mipmap.calendar_city_img_shanghai)//占位图
                .error(R.mipmap.calendar_city_img_taibei)
                .into(imageView);
    }
//
//    /**
//     * @param url
//     * @param imageView
//     * 高斯模糊加载
//     */
    public static void blur(String url, ImageView imageView) {
        ImageLoader.with(BaseApplication.getAppContext())
                .url(url)//加载网络地址
                .placeHolder(R.mipmap.calendar_city_img_shanghai)//占位图
                .error(R.mipmap.calendar_city_img_taibei)
                .blur(40)//设置高斯模糊
                .into(imageView);
    }

    public static void grayscaleFilter(String url, ImageView imageView) {
        ImageLoader.with(BaseApplication.getAppContext())
                .url(url)//加载网络地址
                .placeHolder(R.mipmap.calendar_city_img_shanghai)//占位图
                .error(R.mipmap.calendar_city_img_taibei)
                .grayscaleFilter()//设置高斯模糊
                .into(imageView);
    }

    public static void swirlFilter(String url, ImageView imageView) {
        ImageLoader.with(BaseApplication.getAppContext())
                .url(url)//加载网络地址
                .placeHolder(R.mipmap.calendar_city_img_shanghai)//占位图
                .error(R.mipmap.calendar_city_img_taibei)
                .swirlFilter()//设置高斯模糊
                .into(imageView);
    }

    public static void toonFilter(String url, ImageView imageView) {
        ImageLoader.with(BaseApplication.getAppContext())
                .url(url)//加载网络地址
                .placeHolder(R.mipmap.calendar_city_img_shanghai)//占位图
                .error(R.mipmap.calendar_city_img_taibei)
                .toonFilter()//设置高斯模糊
                .into(imageView);
    }

    public static void sepiaFilter(String url, ImageView imageView) {
        ImageLoader.with(BaseApplication.getAppContext())
                .url(url)//加载网络地址
                .placeHolder(R.mipmap.calendar_city_img_shanghai)//占位图
                .error(R.mipmap.calendar_city_img_taibei)
                .sepiaFilter()//设置高斯模糊
                .into(imageView);
    }

    public static void sketchFilter(String url, ImageView imageView) {
        ImageLoader.with(BaseApplication.getAppContext())
                .url(url)//加载网络地址
                .placeHolder(R.mipmap.calendar_city_img_shanghai)//占位图
                .error(R.mipmap.calendar_city_img_taibei)
                .sketchFilter()//设置高斯模糊
                .into(imageView);
    }

    public static void vignetteFilter(String url, ImageView imageView) {
        ImageLoader.with(BaseApplication.getAppContext())
                .url(url)//加载网络地址
                .placeHolder(R.mipmap.calendar_city_img_shanghai)//占位图
                .error(R.mipmap.calendar_city_img_taibei)
                .vignetteFilter()//设置高斯模糊
                .into(imageView);
    }

    public static void invertFilter(String url, ImageView imageView) {
        ImageLoader.with(BaseApplication.getAppContext())
                .url(url)//加载网络地址
                .placeHolder(R.mipmap.calendar_city_img_shanghai)//占位图
                .error(R.mipmap.calendar_city_img_taibei)
                .invertFilter()//设置高斯模糊
                .into(imageView);
    }

}
