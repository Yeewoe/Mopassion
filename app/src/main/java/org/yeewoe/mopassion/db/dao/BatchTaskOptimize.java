package org.yeewoe.mopassion.db.dao;

import org.yeewoe.mopassion.utils.LogCore;

import java.util.ArrayList;
import java.util.List;

public abstract class BatchTaskOptimize<T> {

    private final String TAG = "BatchTaskOptimize";

    protected int mUnit = 40;

    /**
     * batch handle objects
     *
     * @param objs List<T>
     * @return boolean
     */
    public abstract boolean batchHandle(List<T> objs);

    /**
     * when batchHandle is fail, will call this method
     */
    public void handleError(List<T> objs) {

    }

    public boolean splitObjectsAndBatchHandle(List<T> objs) {

        boolean result = false;
        if (objs == null) {
            LogCore.interfaceI("[BatchTaskOptimize.splitObjectsAndBatchHandle]List data is null or empty");
            return false;
        }

        int size = objs.size();
        int unit = mUnit;
        int times = size / unit;
        if (times <= 1) {
            result = batchHandle(objs);
        } else {
            times += 1;
            for (int i = 1; i <= times; i++) {
                List<T> ts = new ArrayList<>();
                for (int j = (i - 1) * unit; j < (i * unit); j++) {
                    if (j < size) {
                        ts.add(objs.get(j));
                    } else {
                        break;
                    }
                }
                result = batchHandle(ts);
                if (!result) {
                    handleError(objs);
                    return result;
                }
            }
        }
        return result;
    }

    /**
     * 设置每次批量处理的最大数量
     *
     * @param mUnit
     */
    public void setmUnit(int mUnit) {
        this.mUnit = mUnit;
    }

}
