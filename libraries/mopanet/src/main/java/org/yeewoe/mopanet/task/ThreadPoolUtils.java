package org.yeewoe.mopanet.task;

import java.util.Random;

public class ThreadPoolUtils {

    /**
     * 生成一个随机的long 数据
     *
     * @return long
     */
    public static long generateRandom() {
        Random random = new Random();
        long randomL = random.nextLong();

        return randomL;

    }

}
