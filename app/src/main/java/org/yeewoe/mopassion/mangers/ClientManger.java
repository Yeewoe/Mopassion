package org.yeewoe.mopassion.mangers;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.mopassion.MopaApplication;
import org.yeewoe.mopassion.constants.SpConstants;
import org.yeewoe.mopassion.utils.TimeUtil;

/**
 * 客户端数据运行时
 * Created by wyw on 2016/3/28.
 */
public class ClientManger {
    private static ClientManger ourInstance = new ClientManger();

    public static ClientManger getInstance() {
        return ourInstance;
    }

    private String ticket = "";

    private ClientManger() {
    }

    /**
     * 获取服务端时间
     */
    public long getServerSwapTime() {
        return MopaApplication.getInstance().getSp().loadLongSharedPreference(SpConstants.Entry.SERVER_TIME_RANGE) + System.currentTimeMillis();
    }

    /**
     * 设置服务端时间，同时将时间差保存进SP
     */
    public void setServerSwapTime(long serverSwapTime) {
        MopaApplication.getInstance().getSp().saveSharedPreferences(SpConstants.Entry.SERVER_TIME_RANGE, System.currentTimeMillis() - TimeUtil.parseServerTime(serverSwapTime));
    }

    /**
     * 获取登录ticket
     */
    public String getTicket() {
        if (!Checks.check(ticket)) {
            ticket = MopaApplication.getInstance().getSp().loadStringSharedPreference(SpConstants.Auth.TICKET);
        }
        return ticket;
    }

    /**
     * 设置登录ticket，同时将保存进SP
     */
    public void setTicket(String ticket) {
        this.ticket = ticket;
        MopaApplication.getInstance().getSp().saveSharedPreferences(SpConstants.Auth.TICKET, ticket);
    }
}
