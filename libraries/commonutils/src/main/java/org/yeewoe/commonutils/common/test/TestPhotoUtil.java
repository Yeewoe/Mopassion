package org.yeewoe.commonutils.common.test;

import java.util.Random;

/**
 * Created by wyw on 2016/7/17.
 */
public class TestPhotoUtil {
    public static final String[] DEFAULT_HEAD_URLS = new String[] {
            "http://www.qq1234.org/uploads/allimg/150709/8_150709172502_1.jpg",
            "http://www.qq1234.org/uploads/allimg/150709/8_150709172502_2.jpg",
            "http://www.qq1234.org/uploads/allimg/150709/8_150709172502_3.jpg",
            "http://www.qq1234.org/uploads/allimg/150709/8_150709172502_4.jpg",
            "http://www.qq1234.org/uploads/allimg/150709/8_150709172502_5.jpg",
            "http://www.qq1234.org/uploads/allimg/150709/8_150709172502_6.jpg",
            "http://www.qq1234.org/uploads/allimg/150709/8_150709172502_7.jpg",
            "http://www.qq1234.org/uploads/allimg/150709/8_150709172502_8.jpg",
            "http://www.qq1234.org/uploads/allimg/150709/8_150709172502_9.jpg",
            "http://www.qq1234.org/uploads/allimg/150709/8_150709172502_10.jpg",
            "http://www.qq1234.org/uploads/allimg/150709/8_150709172502_11.jpg",
    };

    public static String getRandomUrl() {
        Random random = new Random();
        int randomI = random.nextInt(DEFAULT_HEAD_URLS.length);
        return DEFAULT_HEAD_URLS[randomI];
    }
}
