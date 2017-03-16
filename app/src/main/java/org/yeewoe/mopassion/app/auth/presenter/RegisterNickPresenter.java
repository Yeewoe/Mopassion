package org.yeewoe.mopassion.app.auth.presenter;

import org.greenrobot.eventbus.EventBus;
import org.yeewoe.mopassion.app.auth.view.iview.IRegisterNickView;
import org.yeewoe.mopassion.app.auth.view.iview.IRegisterView;
import org.yeewoe.mopassion.event.KeyValueChangeEvent;
import org.yeewoe.mopassion.event.TabChangeEvent;
import org.yeewoe.mopassion.app.common.presenter.MopaPresenter;

/**
 * Created by acc on 2016/3/29.
 */
public class RegisterNickPresenter extends MopaPresenter {
    private IRegisterNickView view;
    public RegisterNickPresenter(IRegisterNickView view) {
        this.view = view;
    }

    public void next(String nick) {
        KeyValueChangeEvent keyValueChangeEvent = new KeyValueChangeEvent();
        keyValueChangeEvent.put(IRegisterView.DATA_KEY_ACCOUNT, nick);
        keyValueChangeEvent.put(IRegisterView.DATA_KEY_NICK_NAME, nick);
        EventBus.getDefault().post(keyValueChangeEvent);
        EventBus.getDefault().post(new TabChangeEvent(1));
    }

    @Override
    public void onDestroy() {

    }

}
