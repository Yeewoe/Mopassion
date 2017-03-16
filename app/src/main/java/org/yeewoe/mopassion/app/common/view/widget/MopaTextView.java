package org.yeewoe.mopassion.app.common.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.model.UserHeadVo;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.emotion.EmotionUtil;
import org.yeewoe.mopassion.mangers.UserDataMananger;

/**
 * 基类TextView
 * Created by wyw on 2016/4/7.
 */
public class MopaTextView extends TextView {
    public MopaTextView(Context context) {
        super(context);
    }

    public MopaTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override public void setText(CharSequence text, BufferType type) {
        if (text == null) {
            text = "";
        }
        text = EmotionUtil.parseToMsg(text.toString(), getContext(), false);
        super.setText(text, type);
    }

    public void setUser(UserHeadVo user) {
        if (user == null) {
            super.setText(getContext().getString(R.string.none));
        } else {
            super.setText(user.getName());
        }
    }

    public void setMe() {
        User me = UserDataMananger.getInstance().getMe();
        super.setText(me.getNick());
    }

    public void setSignature(String signature) {
        if (Checks.check(signature)) {
            setText(signature);
        } else {
            setText(getContext().getString(R.string.no_signature));
        }
    }
}
