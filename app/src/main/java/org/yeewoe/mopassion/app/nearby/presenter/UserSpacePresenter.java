package org.yeewoe.mopassion.app.nearby.presenter;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.callback.CallbackUtils;
import org.yeewoe.mopassion.app.common.adapter.MopaAdapter;
import org.yeewoe.mopassion.app.common.model.ImageWallVo;
import org.yeewoe.mopassion.app.common.presenter.LinePresenter;
import org.yeewoe.mopassion.app.location.manager.LocationPointInfo;
import org.yeewoe.mopassion.app.location.manager.LocationUtils;
import org.yeewoe.mopassion.app.nearby.model.TrendService;
import org.yeewoe.mopassion.app.nearby.view.iview.IUserSpaceView;
import org.yeewoe.mopassion.asynctask.LineAsyncTask;
import org.yeewoe.mopassion.constants.TrendConstants;
import org.yeewoe.mopassion.db.po.Trend;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.template.media.TMediaImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acc on 2016/7/27.
 */
public class UserSpacePresenter extends LinePresenter<ImageWallVo> {

    private final IUserSpaceView view;

    public UserSpacePresenter(IUserSpaceView view, MopaAdapter<ImageWallVo> adapter) {
        super(adapter, TrendConstants.COUNT_USER_TREND_FIRST_PAGE, TrendConstants.COUNT_USER_TREND_NEXT_PAGE);
        this.view = view;
    }

    @Override public boolean isPageMode() {
        return false;
    }

    @Override protected LineAsyncTask<ImageWallVo> initAsyncTask() {
        return new LineAsyncTask<ImageWallVo>(view, adapter) {
            @Override protected Callback.CallbackInfo<ImageWallVo> doInBackgroundAfterInit(Object param, long prev, int count) {
                ArrayList<TMediaImage> photoList = (ArrayList<TMediaImage>) param;
                Callback.CallbackInfo<ImageWallVo> result = new Callback.CallbackInfo<>();
                result.mTs = ImageWallVo.Convertor.convert(photoList);
                return result;
            }
        };
    }
}
