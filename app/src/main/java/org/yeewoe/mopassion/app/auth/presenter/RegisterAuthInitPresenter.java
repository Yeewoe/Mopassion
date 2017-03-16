package org.yeewoe.mopassion.app.auth.presenter;

import org.greenrobot.eventbus.EventBus;
import org.yeewoe.mopassion.app.auth.view.iview.IRegisterAuthInitView;
import org.yeewoe.mopassion.app.auth.view.iview.IRegisterView;
import org.yeewoe.mopassion.event.KeyValueChangeEvent;
import org.yeewoe.mopassion.app.common.presenter.MopaPresenter;

/**
 * Created by wyw on 2016/3/29.
 */
public class RegisterAuthInitPresenter extends MopaPresenter {
    private IRegisterAuthInitView view;
    public RegisterAuthInitPresenter(IRegisterAuthInitView view) {
        this.view = view;
    }

    public void register(int gender) {
        KeyValueChangeEvent keyValueChangeEvent = new KeyValueChangeEvent();
        keyValueChangeEvent.put(IRegisterView.DATA_KEY_GENDER, gender);
        EventBus.getDefault().post(keyValueChangeEvent);
    }

    @Override
    public void onDestroy() {

    }
}
