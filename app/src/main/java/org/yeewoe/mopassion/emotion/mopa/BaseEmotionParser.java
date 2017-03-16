package org.yeewoe.mopassion.emotion.mopa;

import java.util.ArrayList;
import java.util.List;

/**
 * 表情解析基类
 * Created by wyw on 2016/6/14.
 */
public abstract class BaseEmotionParser {

    public static final String SUFFIX = ".png";

    /**
     * 组装编号
     */
    protected abstract int[] buildNo();

    /**
     * 文件前缀
     */
    protected abstract String buildPrevStr();

    /**
     * 文件后缀
     */
    protected String buildSuffixStr() {
        return SUFFIX;
    }

    /**
     * 获取表情数量
     */
    public int getNum() {
        int[] buildNo = buildNo();
        return buildNo != null ? buildNo.length : 0;
    }

    /**
     * 获取编码列表
     */
    public String[] getNoStr() {
        int[] buildNo = buildNo();
        if (buildNo == null) {
            return new String[0];
        } else {
            String[] result = new String[buildNo.length];
            for (int i = 0; i < buildNo.length; i++) {
                result[i] = buildNo[i] + "";
            }
            return result;
        }
    }

    /**
     * 获取编码列表
     */
    public List<String> getNoArray() {
        int[] buildNo = buildNo();
        if (buildNo == null) {
            return new ArrayList<>();
        } else {
            ArrayList<String> result = new ArrayList<>(buildNo.length);
            for (int no : buildNo) {
                result.add(no + "");
            }
            return result;
        }
    }

    /**
     * 获取前缀
     */
    public String getPrevName() {
        return buildPrevStr();
    }
}
