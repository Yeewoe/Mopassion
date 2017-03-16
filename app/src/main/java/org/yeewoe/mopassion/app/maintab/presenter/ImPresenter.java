package org.yeewoe.mopassion.app.maintab.presenter;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.callback.CallbackUtils;
import org.yeewoe.mopassion.app.common.presenter.LinePresenter;
import org.yeewoe.mopassion.app.im.model.IMService;
import org.yeewoe.mopassion.app.maintab.model.ImLineVo;
import org.yeewoe.mopassion.app.maintab.view.apdaters.ImListAdapter;
import org.yeewoe.mopassion.app.maintab.view.iview.IImView;
import org.yeewoe.mopassion.asynctask.LineAsyncTask;
import org.yeewoe.mopassion.constants.ImConstants;
import org.yeewoe.mopassion.db.po.Message;
import org.yeewoe.mopassion.db.po.User;

import java.util.List;

/**
 * Created by wyw on 2016/3/29.
 */
public class ImPresenter extends LinePresenter<ImLineVo> {
    private IImView view;
    private IMService imService;

    public ImPresenter(IImView view, ImListAdapter adapter) {
        super(adapter, ImConstants.COUNT_IM_LINE_FIRST_PAGE, ImConstants.COUNT_IM_LINE_NEXT_PAGE);
        this.view = view;
        imService = new IMService();
    }

    @Override protected LineAsyncTask<ImLineVo> initAsyncTask() {
        return new LineAsyncTask<ImLineVo>(view, adapter) {

            @Override protected Callback.CallbackInfo<ImLineVo> doInBackgroundAfterInit(Object param, long prev, int count) {
                Callback.CallbackInfo<Message> callbackInfo = imService.getMessageLine();
                Callback.CallbackInfo<ImLineVo> result = new Callback.CallbackInfo<>();
                if (!callbackInfo.bError) {
                    List<Message> messages = callbackInfo.mTs;
                    List<User> users = userService.getFromMessage(messages, false, false); // TODO 这里不适合使用通用的getFromPo方法（因为id是从otherUid来的），须重构
                    result.mTs = ImLineVo.Converter.convert(messages, users);
                } else {
                    CallbackUtils.buildErrorCallbackInfo(result, callbackInfo);
                }
                return result;
            }

            @Override protected void onPostExecuteAfterInit(Callback.CallbackInfo<ImLineVo> imLineVoCallbackInfo) {
                if (!imLineVoCallbackInfo.bError) {
                    view.setTitle(imLineVoCallbackInfo.mTs == null ? 0 : imLineVoCallbackInfo.mTs.size());
                }

            }
        };
    }
}
