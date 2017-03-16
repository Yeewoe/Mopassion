package org.yeewoe.mopassion.app.contact.presenter;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopassion.app.common.presenter.LinePresenter;
import org.yeewoe.mopassion.app.contact.model.UserLineVo;
import org.yeewoe.mopassion.app.contact.view.adapter.UserLineAdapter;
import org.yeewoe.mopassion.app.contact.view.iview.IUserFollowerView;
import org.yeewoe.mopassion.asynctask.LineAsyncTask;
import org.yeewoe.mopassion.constants.ContactConstants;
import org.yeewoe.mopassion.db.po.User;

import java.util.List;

/**
 * Created by acc on 2016/5/7.
 */
public class ContactFollowerPresenter extends LinePresenter<UserLineVo> {
    private IUserFollowerView view;

    public ContactFollowerPresenter(IUserFollowerView view, UserLineAdapter adapter) {
        super(adapter, ContactConstants.COUNT_FOLLOWER_LIST_FIRST_PAGE, ContactConstants.COUNT_FOLLOWER_LIST_NEXT_PAGE);
        this.view = view;
    }

    @Override public boolean isPageMode() {
        return false;
    }

    @Override protected LineAsyncTask<UserLineVo> initAsyncTask() {
        return new LineAsyncTask<UserLineVo>(view, adapter) {
            @Override protected Callback.CallbackInfo<UserLineVo> doInBackgroundAfterInit(Object param, long prev, int count) {
                Callback.CallbackInfo<User> resultInfo = userService.getFollowLineNet();
                Callback.CallbackInfo<UserLineVo> result = new Callback.CallbackInfo<>();
                if (resultInfo.bError) {
                    result.bError = true;
                    result.errorCode = resultInfo.errorCode;
                } else {
                    List<User> users = resultInfo.mTs;
                    result.mTs = UserLineVo.Convert.convert(users);
                }
                return result;
            }
        };
    }
}
