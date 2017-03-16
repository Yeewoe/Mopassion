package org.yeewoe.mopassion.db.core;

import java.sql.SQLException;

/**
 * Created by acc on 2016/8/25.
 */
public class DbUpdater {
    public static void version2(MopaDbHelper mopaDbHelper) throws SQLException {
        /** 用户信息表，新增地址地段　**/
        mopaDbHelper.getUserDao().executeRaw(
                "ALTER TABLE " +
                        DbConstants.TableInfo.User.TABLE_NAME +
                        " ADD COLUMN " + DbConstants.TableInfo.User.ADDRESS +
                        " TEXT;");
    }
}
