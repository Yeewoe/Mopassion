package org.yeewoe.mopassion.db.dao;

import android.support.annotation.NonNull;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.mopassion.db.po.Media;
import org.yeewoe.mopassion.db.po.SendStatus;
import org.yeewoe.mopassion.mangers.UserDataMananger;
import org.yeewoe.mopassion.db.core.DaoFactory;
import org.yeewoe.mopassion.db.core.DbConstants;
import org.yeewoe.mopassion.db.po.Message;
import org.yeewoe.mopassion.db.utils.DaoUtils;
import org.yeewoe.mopassion.template.media.TMediaFactory;
import org.yeewoe.mopassion.template.media.TMediaVoice;
import org.yeewoe.mopassion.utils.LogCore;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Message DAO实现类
 * Created by wyw on 2016/4/1.
 */
public class MessageDaoImpl extends MessageDao {
    @Override
    public int update(Dao<?, Integer> dao, Message message, Message exist) throws SQLException {
        if (message == null) {
            LogCore.interfaceI("[update]message == null!!!");
            return -1;
        }

        if (exist == null) {
            LogCore.interfaceI("[update]exist == null!!!");
            return -1;
        }

        Dao<Message, Integer> momentDao = (Dao<Message, Integer>) dao;
        return update(message, exist, momentDao);
    }


    @Override
    public int insert(Dao<?, Integer> dao, Message message) throws SQLException {
        if (message == null) {
            LogCore.interfaceI("[insert]message == null!!!");
            return -1;
        }
        Dao<Message, Integer> messageDao = (Dao<Message, Integer>) dao;
        return insert(message, messageDao);
    }

    public void delete(int id) throws SQLException {
        deleteById(Message.class, id);
    }

    @Override
    public void delete(long sid) throws SQLException {
        deleteByServerId(Message.class, sid);
    }

    public void deleteLogically(long sid) throws SQLException {
        deleteLogicallyByServerId(Message.class, sid);
    }

    public void batchInsertOrUpdate(List<Message> messages) {
        BatchTaskOptimize batchTask = new BatchTaskOptimize<Message>() {
            @Override
            public boolean batchHandle(List<Message> objs) {
                final List<Message> cs = objs;
                try {
                    DaoFactory.getDao(Message.class).callBatchTasks(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            for (Message c : cs) {
                                if (c != null) {
                                    try {
                                        insertOrUpdate(c, c.getSid());
                                    } catch (SQLException e) {
                                        LogCore.interfaceI("insertOrUpdate", e);
                                    }
                                }
                            }
                            return true;
                        }
                    });
                } catch (Exception e) {
                    LogCore.interfaceI("callBatchTasks", e);
                }
                return true;
            }
        };
        batchTask.splitObjectsAndBatchHandle(messages);
    }

    @NonNull public List<Message> queryAll() throws SQLException {
        Dao<Message, Integer> dao = (Dao<Message, Integer>) DaoFactory.getDao(Message.class);
        QueryBuilder<Message, Integer> query = dao.queryBuilder();
        Where<Message, Integer> where = query.where();
        where.eq(DbConstants.TableInfo.Message.SELF_UID, UserDataMananger.getInstance().getMeUid());
        DaoUtils.setAndWhere(where);
        DaoUtils.setAndExist(where);
        query.orderBy(DbConstants.TableInfo.Base.CREATE_TIME, false);
        List<Message> result = query.query();
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

    @Override
    @NonNull public List<Message> queryAll(long toUid) throws SQLException {
        Dao<Message, Integer> dao = (Dao<Message, Integer>) DaoFactory.getDao(Message.class);
        QueryBuilder<Message, Integer> query = dao.queryBuilder();
        Where<Message, Integer> where = query.where();
        where.eq(DbConstants.TableInfo.Message.OTHER_UID, toUid);
        where.and();
        where.eq(DbConstants.TableInfo.Message.SELF_UID, UserDataMananger.getInstance().getMeUid());
        DaoUtils.setAndWhere(where);
        DaoUtils.setAndExist(where);
        query.orderBy(DbConstants.TableInfo.Base.CREATE_TIME, true); // 按时间升序
        List<Message> result = query.query();
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

    @Override public Message queryLast() throws SQLException {
        Dao<Message, Integer> dao = (Dao<Message, Integer>) DaoFactory.getDao(Message.class);
        QueryBuilder<Message, Integer> query = dao.queryBuilder();
        Where<Message, Integer> where = query.where();
        where.eq(DbConstants.TableInfo.Message.TO_UID, UserDataMananger.getInstance().getMeUid());
        DaoUtils.setAndWhere(where);
        query.orderBy(DbConstants.TableInfo.Base.SID, false);
        query.limit(1L);
        List<Message> result = query.query();
        if (Checks.check(result)) {
            return result.get(0);
        }
        return null;
    }

    @Override public void updateStatus(long sid, SendStatus sendStatus) throws SQLException {
        Dao<Message, Integer> dao = (Dao<Message, Integer>) DaoFactory.getDao(Message.class);
        UpdateBuilder<Message, Integer> updateBuilder = dao.updateBuilder();
        Where<Message, Integer> where = updateBuilder.where();
        where.eq(DbConstants.TableInfo.Base.SID, sid);
        DaoUtils.setAndWhere(where);
        updateBuilder.updateColumnValue(DbConstants.TableInfo.Base.SEND_STATUS, sendStatus);
        updateBuilder.update();
    }

    @Override public void updateVoiceBuffer(int id, String buffer) throws SQLException {
        Message message = queryById(Message.class, id);
        Media media = TMediaFactory.getInstance().fromJson(message.getContent());
        Object o = TMediaFactory.getInstance().toT(media);
        if (o instanceof TMediaVoice) {
            TMediaVoice voice = (TMediaVoice) o;
            voice.buffer = buffer;
            message.setContent(TMediaFactory.getInstance().fromTToJson(media.getName(), media.getSize(), voice));
            insertOrUpdate(message, id);
        }
    }
}
