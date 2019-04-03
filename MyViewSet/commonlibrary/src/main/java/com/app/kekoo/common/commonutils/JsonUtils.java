package com.app.kekoo.common.commonutils;

import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by mms on 2017/7/27.
 * gsom的二次封装
 */

public class JsonUtils {

    // 采取单例模式
    private static Gson gson = null;
    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private JsonUtils() {
    }


    /**
     * @param src :
     * @return :转化后的JSON数据
     * @Description : 将对象转为JSON串，此方法能够满足大部分需求
     */
    public static String toJson(Object src) {
        if (null == src) {
            return gson.toJson(JsonNull.INSTANCE);
        }
        try {
            return gson.toJson(src);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param gsonString 需要转换的字符串，
     * @param <T>
     * @return 返回map类型数据，多用于不确定键值对类型
     */
    public static <T> Map<String, T> GsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
    /**
     * @param json
     * @param classOfT
     * @return
     * @Description : 用来将JSON串转为对象，但此方法不可用来转带泛型的集合
     */
    public static <T> Object fromJson(String json, Class<T> classOfT) {
        try {
            return gson.fromJson(json, (Type) classOfT);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param json
     * @return
     * @Description : 用来将JSON串转为对象，此方法可用来转带泛型的集合，如：Type为 new
     * TypeToken<List<T>>(){}.getType()
     * ，其它类也可以用此方法调用，就是将List<T>替换为你想要转成的类
     */
    public static Object fromJson(String json, Type typeOfT) {
        try {
            return gson.fromJson(json, typeOfT);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取json中的某个值，也可先将其转成键值对再获取
     *
     * @param json
     * @param key
     * @return
     */
    public static String getValue(String json, String key) {
        try {
            JSONObject object = new JSONObject(json);
            return object.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param json 字符串
     * @return JSONObject类型数据
     */
    public static JSONObject getArray(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取json中的list值
     *
     * @param json
     * @return
     */
    public static String getListValue(String json) {
        try {
            JSONObject object = new JSONObject(json);
            return object.getString("list");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Double getDoubleValue(String json, String key) {
        try {
            JSONObject object = new JSONObject(json);
            return object.getDouble(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static int getIntValue(String json, String key) {
        try {
            JSONObject object = new JSONObject(json);
            return object.getInt(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
