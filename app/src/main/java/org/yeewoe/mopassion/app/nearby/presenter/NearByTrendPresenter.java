package org.yeewoe.mopassion.app.nearby.presenter;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.callback.CallbackUtils;
import org.yeewoe.mopassion.app.common.presenter.LinePresenter;
import org.yeewoe.mopassion.app.common.service.UserService;
import org.yeewoe.mopassion.app.location.manager.LocationPointInfo;
import org.yeewoe.mopassion.app.location.manager.LocationUtils;
import org.yeewoe.mopassion.app.nearby.model.TrendLineVo;
import org.yeewoe.mopassion.app.nearby.view.adapter.NearTrendLineAdapter;
import org.yeewoe.mopassion.app.nearby.view.iview.INearByTrend;
import org.yeewoe.mopassion.app.nearby.model.TrendService;
import org.yeewoe.mopassion.asynctask.LineAsyncTask;
import org.yeewoe.mopassion.constants.TrendConstants;
import org.yeewoe.mopassion.db.po.MapPosition;
import org.yeewoe.mopassion.db.po.Trend;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.utils.LogCore;

import java.util.Collections;
import java.util.List;

/**
 * Created by wyw on 2016/4/7.
 */
public class NearByTrendPresenter extends LinePresenter<TrendLineVo> {

    private final INearByTrend view;
    private TrendService trendService;
    private UserService userService;

    public NearByTrendPresenter(INearByTrend view, NearTrendLineAdapter nearTrendLineAdapter) {
        super(nearTrendLineAdapter, TrendConstants.COUNT_NEAY_TREND_FIRST_PAGE, TrendConstants.COUNT_NEAY_TREND_NEXT_PAGE);
        this.view = view;
        this.trendService = new TrendService();
        this.userService = new UserService();
    }

    @Override protected LineAsyncTask<TrendLineVo> initAsyncTask() {
        LogCore.d("www-0");
        return new LineAsyncTask<TrendLineVo>(view, adapter) {
            @Override protected Callback.CallbackInfo<TrendLineVo> doInBackgroundAfterInit(Object param, long prev, int count) {
                LogCore.d("www-1");
                Callback.CallbackInfo<TrendLineVo> result = new Callback.CallbackInfo<>();
                LocationPointInfo cacheLocation = LocationUtils.loadLocation();
                if (cacheLocation != null) {
                    // 有缓存位置
                    Callback.CallbackInfo<Trend> nearTrendLine = trendService.getNearTrendLine(new MapPosition(cacheLocation), (int)param,
                            0, TrendConstants.VALUE_NEARBY_MAX_DISTANCE, (int) prev, count);
                    LogCore.d("www-2");
                    if (!nearTrendLine.bError) {
                        List<Trend> trends = nearTrendLine.mTs;
                        List<User> users = userService.getFromPo(trends, true, true);
                        LogCore.d("www-3");
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
