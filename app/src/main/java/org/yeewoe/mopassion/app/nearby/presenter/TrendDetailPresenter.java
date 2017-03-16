package org.yeewoe.mopassion.app.nearby.presenter;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopassion.app.common.presenter.MopaPresenter;
import org.yeewoe.mopassion.app.common.service.UserService;
import org.yeewoe.mopassion.app.nearby.view.iview.ITrendDetailView;

/**
 * Created by wyw on 2016/8/11.
 */
public class TrendDetailPresenter extends MopaPresenter {

    private final ITrendDetailView view;
    private final UserService userService;

    public TrendDetailPresenter(ITrendDetailView view) {
        this.view = view;
        this.userService = new UserService();
    }

    @Override public void onDestroy() {

    }

    /**
     * 关注
     */
    public void follow(long sid) {
        view.showLoading();
        userService.follow(sid, new Callback() {

            @Override public <T> void callback(CallbackInfo<T> info) {
                view.dismissLoading();
                if (info.bError) {
                    view.showError(info.errorCode);
                    return ;
                }
                view.showToast("关注成功");
            }
        });
    }
}
