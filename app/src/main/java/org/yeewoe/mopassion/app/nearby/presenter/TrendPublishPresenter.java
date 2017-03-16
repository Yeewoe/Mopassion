package org.yeewoe.mopassion.app.nearby.presenter;

import org.yeewoe.mopassion.app.common.presenter.MopaPresenter;
import org.yeewoe.mopassion.app.location.manager.LocationPointInfo;
import org.yeewoe.mopassion.app.location.manager.MopaLocationClient;
import org.yeewoe.mopassion.app.nearby.model.TrendService;
import org.yeewoe.mopassion.app.nearby.view.iview.ITrendPublishView;

import java.io.File;

/**
 * Created by wyw on 2016/4/8.
 */
public class TrendPublishPresenter extends MopaPresenter implements MopaLocationClient.NotifyLocationListener {
    private final ITrendPublishView view;

    public TrendPublishPresenter(ITrendPublishView view) {
        super(view);
        this.view = view;
    }

    @Override public void onDestroy() {
    }

    @Override public void onLocationChange(LocationPointInfo locationPointInfo) {
        view.setSite(locationPointInfo);
    }

    @Override protected void handleCompress(File pic) {
        view.addPic(pic);
    }
}
