package org.yeewoe.mopassion.db.core;

import org.yeewoe.mopassion.db.po.Message;
import org.yeewoe.mopassion.db.po.Trend;
import org.yeewoe.mopassion.db.po.User;

/**
 * Created by wyw on 2016/4/1.
 */
public class DbConstants {

    public final static Class[] TABLE_CLASSES  = new Class[] {
            Message.class, // 消息
            User.class, // 用户
            Trend.class // 动态
    };

    public static class TableInfo {
        public static class Base {

            public static final String ID = "id";
            public static final String SID = "sid";
            public static final String CREATE_TIME = "create_time";
            public static final String CREATE_BY = "create_by";
            public static final String UPDATE_TIME = "update_time";
            public static final String UPDATE_BY = "update_by";
            public static final String VERSION = "version";
            public static final String IS_DELETED = "is_deleted";
            public static final String OWNER_ID = "owner_id";
            public static final String SEND_STATUS = "send_status";
        }

        public static class Message {

            public static final String TABLE_NAME = "t_message";
            public static final String CONTENT = "content";
            public static final String TO_UID = "to_uid";
            public static final String TO_GID = "to_gid";
            public static final String FROM_UID = "from_uid";
            public static final String FROM_GID = "from_gid";
            public static final String OTHER_UID = "other_uid";
            public static final String SELF_UID = "self_uid";
            public static final String OTHER_GID = "other_gid";
        }

        public static class User {
            public static final String TABLE_NAME = "t_user";
            public static final String ACCOUNT = "account";
            public static final String NICK = "name";
            public static final String AGE = "age";
            public static final String GENDER = "gender";
            public static final String STAR = "star";
            public static final String JOB = "job";
            public static final String COLLEGE = "college";
            public static final String HOMELAND = "homeland";
            public static final String SPORT = "sport";
            public static final String SIGNATURE = "signature";
            public static final String JSON_HEADS = "json_heads";
            public static final String ADDRESS = "address";
            public static final String BIRTHDAY = "birthday";
        }

        public class Trend {
            public static final String TABLE_NAME = "t_trend";
            public static final String JSON_CONTENTS = "json_contents";
            public static final String TREND_VIEW = "trend_view";
            public static final String JSON_POSITION = "json_position";
        }
    }
}
