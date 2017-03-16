package org.yeewoe.mopassion.db.utils;

import com.j256.ormlite.stmt.Where;

import org.yeewoe.mopassion.MopaApplication;
import org.yeewoe.mopassion.constants.SpConstants;
import org.yeewoe.mopassion.mangers.UserDataMananger;
import org.yeewoe.mopassion.db.core.DbConstants;

import java.sql.SQLException;

/**
 * Created by wyw on 2016/4/1.
 */
public class DaoUtils {
    public static Where<?, Integer> setAndWhere(Where<?, Integer> where) throws SQLException {
        where.and();
        if (UserDataMananger.getInstance().me == null) {
            // 避免进入getMeUid里面me为null的死循环
            return where.eq(DbConstants.TableInfo.Base.OWNER_ID, MopaApplication.getInstance().getSp().loadLongSharedPreference(SpConstants.Auth.LOGIN_ID));
        } else {
            return where.eq(DbConstants.TableInfo.Base.OWNER_ID, UserDataMananger.getInstance().getMeUid());
        }
    }

    public static Where<?, Integer> setAndExist(Where<?, Integer> where) throws SQLException {
        where.and();
        return where.eq(DbConstants.TableInfo.Base.IS_DELETED, false);
    }
}
