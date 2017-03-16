package org.yeewoe.mopassion.app.maintab.presenter;

import org.yeewoe.mopassion.app.common.presenter.MopaPresenter;
import org.yeewoe.mopassion.app.location.manager.LocationPointInfo;
import org.yeewoe.mopassion.app.location.manager.MopaLocationClient;
import org.yeewoe.mopassion.app.location.model.LocationService;
import org.yeewoe.mopassion.app.maintab.view.iview.IMainTabView;

/**
 * Created by wyw on 2016/4/5.
 */
public class MainTabPresenter extends MopaPresenter implements MopaLocationClient.NotifyLocationListener {

    private final IMainTabView view;
    private LocationService locationService;

    public MainTabPresenter(IMainTabView iMainTabView) {
        this.view = iMainTabView;
        this.locationService = new LocationService();
    }

    @Override public void onDestroy() {

    }

    @Override public void notify(LocationPointInfo locationPointInfo) {
        super.notify(locationPointInfo);
        // 定位回调
        if (locationPointInfo.isSuccess) {
            view.showToast("当前位置：" + locationPointInfo.addrStr);
        } else {
            view.showToast(locationPointInfo.errorStr);
        }
    }

}
