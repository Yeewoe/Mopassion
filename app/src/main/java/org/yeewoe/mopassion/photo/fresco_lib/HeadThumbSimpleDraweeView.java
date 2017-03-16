package org.yeewoe.mopassion.photo.fresco_lib;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;

import com.facebook.drawee.generic.GenericDraweeHierarchy;

import org.yeewoe.commonutils.common.test.TestPhotoUtil;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.model.UserHeadVo;
import org.yeewoe.mopassion.db.po.Gender;
import org.yeewoe.mopassion.mangers.UserDataMananger;
import org.yeewoe.mopassion.utils.IntentManager;

/**
 * 标准头像缩略图
 * Created by wyw on 2016/4/24.
 */
public class HeadThumbSimpleDraweeView extends MopaSimpleDraweeView {
    protected UserHeadVo userHeadVo;

    public HeadThumbSimpleDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        init(context);
    }

    public HeadThumbSimpleDraweeView(Context context) {
        super(context);
        init(context);
    }

    public HeadThumbSimpleDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HeadThumbSimpleDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    protected void init(Context context) {
//        setProgressBar(new LoadingProgressDrawable(context));
//        setPlaceholderDrawable(context.getResources().getDrawable(R.drawable.default_head));
        setRoundingHierarchy();
        setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                IntentManager.Contact.intentToDetail(getContext(), userHeadVo);
            }
        });
    }

    public void setMeHead() {
        setImageHead(UserHeadVo.Convertor.convert(UserDataMananger.getInstance().getMe()));
    }

    public void setImageHead(UserHeadVo head) {
        userHeadVo = head;
        if (head == null || head.getHead() == null) {
            if (head != null && head.getGender() != null && head.getGender() == Gender.MALE) {
                setRoundDraweeViewResId(R.drawable.default_head_male);
            } else {
                setRoundDraweeViewResId(R.drawable.default_head_female);
            }
//            setImageURI(Uri.parse(TestPhotoUtil.getRandomUrl()));
        } else {
            if (head.getGender() != null && head.getGender() == Gender.MALE) {
                setPlaceholderDrawable(getContext().getResources().getDrawable(R.drawable.default_head_male));
            } else {
                setPlaceholderDrawable(getContext().getResources().getDrawable(R.drawable.default_head_female));
            }

            setRoundDraweeViewMedia(head.getHead());
        }
    }


    public void modeToShow() {
        setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                if (userHeadVo != null && userHeadVo.getHead() != null) {
                    IntentManager.Photo.intentToPhotoView(getContext(), userHeadVo.getHead());
                } else {
//                    IntentManager.Photo.intentToPhotoView(getContext(), null);
                }
            }
        });
    }
}
