package org.yeewoe.commonutils.common.utils;

import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * JSON工具<br/>
 * 内置使用Gson
 * Created by wyw on 2016/3/5.
 */
public class JsonUtil {
    private static Gson gson;
    static {
        gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
    }

    /**
     * 获取JSON
     */
    public static String toJson(@NonNull Object object) {
        return gson.toJson(object);
    }

    /**
     * 转换JSON
     */
    public static <T> T fromJson(@NonNull String json, @NonNull Class<T> classOfT) {
        try {
            return gson.fromJson(json, classOfT);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 转换JSON，获得Cellection结果
     */
    public static <T> List<T> fromJsonToCollection(@NonNull String json) {
        return fromJson(json, new TypeToken<List<T>>() {}.getType());
    }

    /**
     * 转换JSON
     */
    public static <T> T fromJson(@NonNull String json, @NonNull Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    private JsonUtil() {
        throw new AssertionError("No instances.");
    }
}
