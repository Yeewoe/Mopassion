package org.yeewoe.mopassion.app.contact.presenter;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopassion.app.common.presenter.LinePresenter;
import org.yeewoe.mopassion.app.contact.model.UserLineVo;
import org.yeewoe.mopassion.app.contact.view.adapter.UserLineAdapter;
import org.yeewoe.mopassion.app.contact.view.iview.IContactFansView;
import org.yeewoe.mopassion.asynctask.LineAsyncTask;
import org.yeewoe.mopassion.db.po.User;

import java.util.List;

/**
 * Created by acc on 2016/5/7.
 */
public class ContactFansPresenter extends LinePresenter<UserLineVo> {
    private IContactFansView view;

    public ContactFansPresenter(IContactFansView view, UserLineAdapter adapter, int firstPageCount, int nextPageCount) {
        super(adapter, firstPageCount, nextPageCount);
        this.view = view;
    }

    @Override protected LineAsyncTask<UserLineVo> initAsyncTask() {
        return new LineAsyncTask<UserLineVo>(view, adapter) {
            @Override protected Callback.CallbackInfo<UserLineVo> doInBackgroundAfterInit(Object param, long prev, int count) {
                Callback.CallbackInfo<User> resultInfo = userService.getFansLineNet((int) prev, count);
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
