package com.app.kekoo.common.baseapi;

/**
 * Created by ${mms} on 2016/12/8.
 */

import com.app.kekoo.common.baseapp.BaseApplication;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 自动管理Cookies
 */
public class CookiesManager implements CookieJar {
    private final PersistentCookieStore cookieStore = new PersistentCookieStore(BaseApplication.getAppContext());
    List<Cookie> myookies = new ArrayList<>();


    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        Logger.i(url+"===="+cookies.toString());
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
                cookies.add(item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        Logger.e(cookies.toString());
        return myookies;
    }
}
