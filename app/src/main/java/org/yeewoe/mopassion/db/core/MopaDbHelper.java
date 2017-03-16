package org.yeewoe.mopassion.db.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.yeewoe.mopassion.db.po.Message;
import org.yeewoe.mopassion.db.po.Trend;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.utils.LogCore;

import java.sql.SQLException;

/**
 * OrmLite数据库核心类
 * Created by wyw on 2016/3/4.
 */
public class MopaDbHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "mopassion.db";
    // any time you make changes to your database objects, you may have to increase the database version

    /**
     * version 1: 原始版本
     * version 2: 增加了个用户地址字段
     */
    private static final int DATABASE_VERSION = 2;

    // the DAO object we use to access the SimpleData table
    private Dao<Message, Integer> messageDao = null;
    private Dao<User, Integer> userDao = null;
    private Dao<Trend, Integer> trendDao = null;

    public MopaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            LogCore.interfaceI("DB onCreate");
            createTable(connectionSource);
        } catch (SQLException e) {
            org.yeewoe.mopanet.utils.LogCore.e(e, "Can't create database");
            throw new RuntimeException(e);
        }
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        LogCore.interfaceI("DB onUpgrade");

        // update
        if (newVersion < oldVersion) {
            try {
                dropTable(connectionSource);
                createTable(connectionSource);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (newVersion == 2) {
                    DbUpdater.version2(this);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                /** 出现异常重新创建表 **/
                try {
                    createTable(connectionSource);
                    dropTable(connectionSource);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        messageDao = null;
        userDao = null;
        trendDao = null;
    }

    private void createTable(ConnectionSource connectionSource) throws SQLException {
        try {
            for (Class tableClass : DbConstants.TABLE_CLASSES) {
                TableUtils.createTable(connectionSource, tableClass);
            }
        } catch (Exception e) {
            LogCore.interfaceI("createTable fail!!!", e);
        }
    }

    private void dropTable(ConnectionSource connectionSource) throws SQLException {
        try {
            for (Class tableClass : DbConstants.TABLE_CLASSES) {
                TableUtils.dropTable(connectionSource, tableClass, true);
            }
        } catch (Exception e) {
            LogCore.interfaceI("createTable fail!!!", e);
        }
    }

    public Dao<Message, Integer> getMessageDao() throws SQLException {
        if (messageDao == null) {
            messageDao = getDao(Message.class);
        }
        return messageDao;
    }

    public Dao<User, Integer> getUserDao() throws SQLException {
        if (userDao == null) {
            userDao = getDao(User.class);
        }
        return userDao;
    }

    public Dao<Trend, Integer> getTrendDao() throws SQLException {
        if (trendDao == null) {
            trendDao = getDao(Trend.class);
        }
        return trendDao;
    }
}
