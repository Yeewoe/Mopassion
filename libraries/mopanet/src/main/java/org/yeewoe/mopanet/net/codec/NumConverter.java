package org.yeewoe.mopanet.net.codec;

public class NumConverter {

    /**
     * ת��shortΪbyte
     */
    public static byte[] shortToByteArray(short s) {
        byte b[] = new byte[2];
        b[1] = (byte) (s >> 8);
        b[0] = (byte) (s >> 0);
        return b;
    }

    /**
     * ͨ��byte����ȡ��short
     *
     * @param b
     * @return
     */
    public static short byteArrayToShort(byte[] b) {
        return (short) (((b[1] << 8) | b[0] & 0xff));
    }

    /**
     * ��32λ��intֵ�ŵ�4�ֽڵ�byte����
     *
     * @param num
     * @return
     */
    public static byte[] intToByteArray(int num) {
        byte[] result = new byte[4];
        result[3] = (byte) (num >>> 24);//ȡ���8λ�ŵ�0�±�
        result[2] = (byte) (num >>> 16);//ȡ�θ�8Ϊ�ŵ�1�±�
        result[1] = (byte) (num >>> 8); //ȡ�ε�8λ�ŵ�2�±�
        result[0] = (byte) (num);      //ȡ���8λ�ŵ�3�±�
        return result;
    }

    /**
     * ��4�ֽڵ�byte����ת��һ��intֵ
     *
     * @param b
     * @return
     */
    public static int byteArrayToInt(byte[] b) {
        byte[] a = new byte[4];
        int i = a.length - 1, j = b.length - 1;
        for (; i >= 0; i--, j--) {//��b��β��(��intֵ�ĵ�λ)��ʼcopy���
            if (j >= 0)
                a[i] = b[j];
            else
                a[i] = 0;//���b.length����4,�򽫸�λ��0
        }
        int v0 = (a[3] & 0xff) << 24;//&0xff��byteֵ�޲���ת��int,����Java�Զ����������,�ᱣ����λ�ķ��λ
        int v1 = (a[2] & 0xff) << 16;
        int v2 = (a[1] & 0xff) << 8;
        int v3 = (a[0] & 0xff);
        return v0 + v1 + v2 + v3;
    }


    /**
     * ��64λ��longֵ�ŵ�8�ֽڵ�byte����
     *
     * @param num
     * @return ����ת�����byte����
     */
    public static byte[] longToByteArray(long num) {
        byte[] result = new byte[8];
        result[0] = (byte) (num >>> 56);// ȡ���8λ�ŵ�0�±�
        result[1] = (byte) (num >>> 48);// ȡ���8λ�ŵ�0�±�
        result[2] = (byte) (num >>> 40);// ȡ���8λ�ŵ�0�±�
        result[3] = (byte) (num >>> 32);// ȡ���8λ�ŵ�0�±�
        result[4] = (byte) (num >>> 24);// ȡ���8λ�ŵ�0�±�
        result[5] = (byte) (num >>> 16);// ȡ�θ�8Ϊ�ŵ�1�±�
        result[6] = (byte) (num >>> 8); // ȡ�ε�8λ�ŵ�2�±�
        result[7] = (byte) (num); // ȡ���8λ�ŵ�3�±�
        return result;
    }

    /**
     * ��8�ֽڵ�byte����ת��һ��longֵ
     *
     * @param byteArray
     * @return ת�����long����ֵ
     */
    public static long byteArrayToLong(byte[] byteArray) {
        byte[] a = new byte[8];
        int i = a.length - 1, j = byteArray.length - 1;
        for (; i >= 0; i--, j--) {// ��b��β��(��intֵ�ĵ�λ)��ʼcopy���
            if (j >= 0)
                a[i] = byteArray[j];
            else
                a[i] = 0;// ���b.length����4,�򽫸�λ��0
        }
        // ע��˴���byte����ת����int��������ڣ������ת����Ҫ���Ƚ������е�Ԫ��ת����long��������λ������
        // ��ֱ����λ�Ʋ������ò�����ȷ�����ΪJavaĬ�ϲ�������ʱ�������������Ὣ������Ϊint�����Դ�˴�����ע�⡣
        long v0 = (long) (a[0] & 0xff) << 56;// &0xff��byteֵ�޲���ת��int,����Java�Զ����������,�ᱣ����λ�ķ��λ
        long v1 = (long) (a[1] & 0xff) << 48;
        long v2 = (long) (a[2] & 0xff) << 40;
        long v3 = (long) (a[3] & 0xff) << 32;
        long v4 = (long) (a[4] & 0xff) << 24;
        long v5 = (long) (a[5] & 0xff) << 16;
        long v6 = (long) (a[6] & 0xff) << 8;
        long v7 = (long) (a[7] & 0xff);
        return v0 + v1 + v2 + v3 + v4 + v5 + v6 + v7;
    }


    /**
     * floatת��byte
     *
     * @param x
     */
    public static void floatToByteArray(float x) {
        byte[] bb = new byte[4];
        int l = Float.floatToIntBits(x);
        for (int i = 0; i < 4; i++) {
            bb[i] = new Integer(l).byteValue();
            l = l >> 8;
        }
    }

    /**
     * ͨ��byte����ȡ��float
     *
     * @param bb
     * @return
     */
    public static float byteArrayToFloat(byte[] b) {
        int l;
        l = b[0];
        l &= 0xff;
        l |= ((long) b[1] << 8);
        l &= 0xffff;
        l |= ((long) b[2] << 16);
        l &= 0xffffff;
        l |= ((long) b[3] << 24);
        return Float.intBitsToFloat(l);
    }
}
