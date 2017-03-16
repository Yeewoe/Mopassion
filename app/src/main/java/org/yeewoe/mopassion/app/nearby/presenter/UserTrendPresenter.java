package org.yeewoe.mopassion.app.nearby.presenter;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.callback.CallbackUtils;
import org.yeewoe.mopassion.app.common.presenter.LinePresenter;
import org.yeewoe.mopassion.app.location.manager.LocationPointInfo;
import org.yeewoe.mopassion.app.location.manager.LocationUtils;
import org.yeewoe.mopassion.app.nearby.model.TrendLineVo;
import org.yeewoe.mopassion.app.nearby.model.TrendService;
import org.yeewoe.mopassion.app.nearby.view.adapter.UserTrendLineAdapter;
import org.yeewoe.mopassion.app.nearby.view.iview.IUserTrendView;
import org.yeewoe.mopassion.asynctask.LineAsyncTask;
import org.yeewoe.mopassion.constants.TrendConstants;
import org.yeewoe.mopassion.db.po.Trend;
import org.yeewoe.mopassion.db.po.User;

import java.util.List;

/**
 * Created by wyw on 2016/5/9.
 */
public class UserTrendPresenter extends LinePresenter<TrendLineVo> {
    private final IUserTrendView view;
    private TrendService trendService;

    public UserTrendPresenter(IUserTrendView view, UserTrendLineAdapter adapter) {
        super(adapter, TrendConstants.COUNT_USER_TREND_FIRST_PAGE, TrendConstants.COUNT_USER_TREND_NEXT_PAGE);
        this.view = view;
        this.trendService = new TrendService();
    }

    @Override protected LineAsyncTask<TrendLineVo> initAsyncTask() {
        return new LineAsyncTask<TrendLineVo>(view, adapter) {
            @Override protected Callback.CallbackInfo<TrendLineVo> doInBackgroundAfterInit(Object param, long prev, int count) {
                long uid = (long) param;
                Callback.CallbackInfo<TrendLineVo> result = new Callback.CallbackInfo<>();
                LocationPointInfo cacheLocation = LocationUtils.loadLocation();
                if (cacheLocation != null) {
                    Callback.CallbackInfo<Trend> nearTrendLine = trendService.getUserTrendLine(uid, prev, count);
                    if (!nearTrendLine.bError) {
                        List<Trend> trends = nearTrendLine.mTs;
                        List<User> users = userService.getFromPo(trends, true, true);
                        result.mTs = TrendLineVo.Convertor.convert(trends, users);
                    } else {
                        CallbackUtils.buildErrorCallbackInfo(result, nearTrendLine);
                    }
                }

                return result;
            }
        };
    }
}
