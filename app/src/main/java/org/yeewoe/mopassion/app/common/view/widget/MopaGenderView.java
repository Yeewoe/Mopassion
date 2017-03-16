package org.yeewoe.mopassion.app.common.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.model.UserHeadVo;
import org.yeewoe.mopassion.db.po.Gender;

/**
 * 性别控件
 * Created by wyw on 2016/7/17.
 */
public class MopaGenderView extends ImageView{
    public MopaGenderView(Context context) {
        super(context);
    }

    public MopaGenderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MopaGenderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    public void setGender(UserHeadVo user) {
        if (user != null && user.getGender() != null) {
            if (user.getGender() == Gender.MALE) {
                setImageResource(R.drawable.male);
            } else {
                setImageResource(R.drawable.female);
            }
        } else {
            setImageResource(R.drawable.female);
        }
    }
}
