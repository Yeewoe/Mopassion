package org.yeewoe.mopassion.db.core;

import android.support.annotation.NonNull;

import com.j256.ormlite.dao.Dao;

import org.yeewoe.mopassion.db.po.Message;
import org.yeewoe.mopassion.db.po.Trend;
import org.yeewoe.mopassion.db.po.User;

import java.sql.SQLException;

/**
 *
 * Created by wyw on 2016/4/1.
 */
public class DaoFactory {
    @NonNull public static Dao<?, Integer> getDao(Class cls) {
        try {
            if (cls == Message.class) {
                return DbHelperManager.getInstance().getMessageDao();
            } else if (cls == User.class) {
                return DbHelperManager.getInstance().getUserDao();
            } else if (cls == Trend.class) {
                return DbHelperManager.getInstance().getTrendDao();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
