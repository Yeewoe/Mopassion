package org.yeewoe.mopassion.app.contact.presenter;

import org.yeewoe.commonutils.android.async.MyAsyncTask;
import org.yeewoe.commonutils.android.async.MyLocalAsyncTask;
import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopassion.app.common.presenter.DetailPresenter;
import org.yeewoe.mopassion.app.common.service.UserService;
import org.yeewoe.mopassion.app.contact.model.UserDetailVo;
import org.yeewoe.mopassion.app.contact.view.iview.IUserDetailView;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.utils.TimeUtil;

/**
 * Created by wyw on 2016/4/26.
 */
public class UserDetailPresenter extends DetailPresenter {
    private IUserDetailView view;
    private UserService userService;
    public UserDetailPresenter(IUserDetailView view) {
        this.view = view;
        this.userService = new UserService();
    }

    @Override public void onDestroy() {

    }

    @Override public void loadLocal(int id) {

    }

    @Override public void loadLocal(final long sid) {
        new MyLocalAsyncTask<Void, Integer, Callback.CallbackInfo<UserDetailVo>>() {

            @Override protected Callback.CallbackInfo<UserDetailVo> doInBackground(Void... params) {
                User user = userService.getLocal(sid);
                Callback.CallbackInfo<UserDetailVo> result = new Callback.CallbackInfo<UserDetailVo>();
                if (user != null) {
                    result.mT = UserDetailVo.Convertor.convert(user);
                } else {
                    result.bError = true;
                }
                return result;
            }

            @Override protected void onPostExecute(Callback.CallbackInfo<UserDetailVo> userDetailVoCallbackInfo) {
                if (!userDetailVoCallbackInfo.bError) {
                    view.initDetail(userDetailVoCallbackInfo.mT);
                }
            }
        }.execute();
    }

    @Override public void loadNet(final long sid) {
        new MyAsyncTask<Void, Integer, Callback.CallbackInfo<UserDetailVo>>() {

            @Override protected Callback.CallbackInfo<UserDetailVo> doInBackground(Void... params) {
                Callback.CallbackInfo<User> userCallbackInfo = userService.getNet(sid);
                Callback.CallbackInfo<UserDetailVo> result = new Callback.CallbackInfo<UserDetailVo>();
                if (!userCallbackInfo.bError) {
                    User user = userCallbackInfo.mT;
                    result.mT = UserDetailVo.Convertor.convert(user);
                } else {
                    result.bError = true;
                    result.errorCode = userCallbackInfo.errorCode;
                }
                return result;
            }

            @Override protected void onPostExecute(Callback.CallbackInfo<UserDetailVo> userDetailVoCallbackInfo) {
                if (userDetailVoCallbackInfo.bError) {
                    view.showError(userDetailVoCallbackInfo.errorCode);
                } else {
                    view.initDetail(userDetailVoCallbackInfo.mT);
                }
            }
        }.execute();
    }

    public void follow(long sid) {
        userService.follow(sid, new Callback() {

            @Override public <T> void callback(CallbackInfo<T> info) {
                if (info.bError) {
                    view.showError(info.errorCode);
                    return ;
                }
                view.showToast("关注成功");
            }
        });
    }
}
