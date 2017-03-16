package org.yeewoe.mopassion.db.dao;

/**
 * DAO实现类的管理器
 * Created by wyw on 2016/4/1.
 */
public class DaoManager {

    private static MessageDao messageDao = null;

    private static UserDao userDao = null;

    private static TrendDao trendDao = null;

    public static MessageDao getMessageDao() {
        if (messageDao == null) {
            synchronized (DaoManager.class) {
                if (messageDao == null) {
                    messageDao = new MessageDaoImpl();
                }
            }
        }
        return messageDao;
    }

    public static UserDao getUserDao() {
        if (userDao == null) {
            synchronized (DaoManager.class) {
                if (userDao == null) {
                    userDao = new UserDaoImpl();
                }
            }
        }
        return userDao;
    }

    public static TrendDao getTrendDao() {
        if (trendDao == null) {
            synchronized (DaoManager.class) {
                if (trendDao == null) {
                    trendDao = new TrendDaoImpl();
                }
            }
        }
        return trendDao;
    }
}
