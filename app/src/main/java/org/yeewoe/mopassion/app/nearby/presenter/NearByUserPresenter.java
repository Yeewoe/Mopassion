package org.yeewoe.mopassion.app.nearby.presenter;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopassion.app.common.presenter.LinePresenter;
import org.yeewoe.mopassion.app.contact.view.iview.IContactFriendView;
import org.yeewoe.mopassion.app.location.manager.LocationPointInfo;
import org.yeewoe.mopassion.app.location.manager.LocationUtils;
import org.yeewoe.mopassion.app.nearby.model.LocUser;
import org.yeewoe.mopassion.app.nearby.model.NearByUserLineVo;
import org.yeewoe.mopassion.app.nearby.view.adapter.NearByUserLineAdapter;
import org.yeewoe.mopassion.asynctask.LineAsyncTask;
import org.yeewoe.mopassion.constants.TrendConstants;
import org.yeewoe.mopassion.db.po.MapPosition;
import org.yeewoe.mopassion.db.po.User;

import java.util.List;

/**
 * Created by wyw on 2016/4/9.
 */
public class NearByUserPresenter extends LinePresenter<NearByUserLineVo> {
    private final IContactFriendView view;

    /**
     * @param firstPageCount 首页条数
     * @param nextPageCount  分页条数
     */
    public NearByUserPresenter(IContactFriendView view, NearByUserLineAdapter adapter, int firstPageCount, int nextPageCount) {
        super(adapter, firstPageCount, nextPageCount);
        this.view = view;
    }

    @Override protected LineAsyncTask<NearByUserLineVo> initAsyncTask() {
        return new LineAsyncTask<NearByUserLineVo>(view, adapter) {
            @Override protected Callback.CallbackInfo<NearByUserLineVo> doInBackgroundAfterInit(Object param, long prev, int count) {
                LocationPointInfo cacheLocation = LocationUtils.loadLocation();
                Callback.CallbackInfo<NearByUserLineVo> result = new Callback.CallbackInfo<>();
                if (cacheLocation != null) {
                    Callback.CallbackInfo<LocUser> resultInfo = userService.getNearUserLineNet(0L, TrendConstants.VALUE_NEARBY_MAX_DISTANCE, (int) prev, count, new MapPosition(cacheLocation.addrStr, cacheLocation.longitude, cacheLocation.latitude));
                    if (resultInfo.bError) {
                        result.bError = true;
                        result.errorCode = resultInfo.errorCode;
                    } else {
                        List<User> users = userService.getFromPo(resultInfo.mTs, true, false);
                        result.mTs = NearByUserLineVo.Convertor.convert(resultInfo.mTs, users);
                    }
                }
                return result;
            }
        };
    }
}
