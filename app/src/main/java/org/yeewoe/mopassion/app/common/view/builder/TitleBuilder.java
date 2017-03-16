package org.yeewoe.mopassion.app.common.view.builder;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.view.widget.MopaTitleLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Title构建器
 * 必须包含 R.layout.layout_title_xxxx
 *
 * Created by wyw on 2016/3/8.
 */
public class TitleBuilder {
    private static final int TYPE_ACTIVITY = 0;
    private static final int TYPE_FRAGMENT = 1;

    private int type;
    private Context context;
    @Nullable private FragmentActivity activity;
    @Nullable private View rootView;
    @Nullable @Bind(R.id.title_container) MopaTitleLayout layoutContiner;
    @Nullable @Bind(R.id.title_center_txt) TextView txtCenter;
    @Nullable @Bind(R.id.title_left_txt) TextView txtLeft;
    @Nullable @Bind(R.id.title_right_txt) TextView txtRight;
    @Nullable @Bind(R.id.title_left_img) ImageButton imgBtnLeft;
    @Nullable @Bind(R.id.title_right_img) ImageButton imgBtnRight;
    @Nullable @Bind(R.id.title_center_img) ImageView imgViCenter;

    public TitleBuilder(@Nullable FragmentActivity context) {
        this.activity = context;
        this.context = this.activity;
        ButterKnife.bind(this, context);
        type = TYPE_ACTIVITY;
    }

    public TitleBuilder(@Nullable View rootView) {
        this.rootView = rootView;
        if (this.rootView != null) {
            this.context = this.rootView.getContext();
        }
        ButterKnife.bind(this, rootView);
        type = TYPE_FRAGMENT;
    }

    /**
     * 设置为后退模式
     */
    public TitleBuilder modeToBack(final FragmentActivity activity) {
        return modeToBack(activity, false);
    }

    /**
     * 设置为后退模式
     */
    public TitleBuilder modeToBack(final FragmentActivity activity, boolean isRight) {
        if (!isRight) {
            if (imgBtnLeft != null) {
                imgBtnLeft.setBackgroundResource(R.drawable.new_back_btn);
                imgBtnLeft.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        finish(activity);
                    }
                });
            }
            if (txtLeft != null) {
                txtLeft.setText(R.string.cancel);
                txtLeft.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        finish(activity);
                    }
                });
            }
        } else if (imgBtnRight != null) {
            imgBtnRight.setBackgroundResource(R.drawable.new_back_btn);
            imgBtnRight.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    finish(activity);
                }
            });
            if (imgBtnLeft != null) {
                imgBtnLeft.setVisibility(View.GONE);
            }
        }
        return this;
    }

    private void finish(FragmentActivity activity) {
        if (type == TYPE_ACTIVITY) {
            activity.finish();
        } else {
            if (activity.getSupportFragmentManager().getBackStackEntryCount() == 0) {
                activity.finish();
            } else {
                activity.getSupportFragmentManager().popBackStack();
            }
        }
    }

    /**
     * 设置为OK模式
     */
    public TitleBuilder modeToOk(View.OnClickListener onClickListener) {
        changeRightImgSrc(R.drawable.ic_title_confirm_white).modeToClick(onClickListener);
        return this;
    }

    /**
     * 设置为点击模式
     */
    public TitleBuilder modeToClick(View.OnClickListener onClickListener) {
        changeRightImgBg(R.color.blue).getRightImgBtn().setOnClickListener(onClickListener);
        return this;
    }



    public TitleBuilder changeCenterTxt(int resId) {
        if (txtCenter != null) {
            txtCenter.setText(resId);
        }
        return this;
    }

    public TitleBuilder changeCenterTxt(String text) {
        if (txtCenter != null) {
            txtCenter.setText(text);
        }
        return this;
    }


    public TitleBuilder changeLeftTxt(int resId) {
        if (txtLeft != null) {
            txtLeft.setText(resId);
        }
        return this;
    }

    public TitleBuilder changeLeftTxt(String text) {
        if (txtLeft != null) {
            txtLeft.setText(text);
        }
        return this;
    }


    public TitleBuilder changeRightTxt(int resId) {
        if (txtRight != null) {
            txtRight.setVisibility(View.VISIBLE);
            txtRight.setText(resId);
        }
        return this;
    }

    public TitleBuilder changeRightTxt(String text) {
        if (txtRight != null) {
            txtRight.setVisibility(View.VISIBLE);
            txtRight.setText(text);
        }
        return this;
    }


    public TitleBuilder changeToPrimaryColor() {
        if (txtCenter != null) {
            txtCenter.setTextColor(this.context.getResources().getColor(R.color.theme_color_dark));
        }
        return changeLeftToPrimaryColor().changeRightToPrimaryColor();
    }

    public TitleBuilder changeLeftToPrimaryColor() {
        if (txtLeft != null) {
            txtLeft.setTextColor(this.context.getResources().getColor(R.color.theme_color_dark));
        }
        return this;
    }

    public TitleBuilder changeRightToPrimaryColor() {
        if (txtRight != null) {
            txtRight.setTextColor(this.context.getResources().getColor(R.color.theme_color_dark));
        }
        return this;
    }

    public TitleBuilder changeCenterImg(int resId) {
        if (imgViCenter != null) {
            imgViCenter.setImageResource(resId);
        }
        return this;
    }

    public TitleBuilder changeRightImgBg(int res) {
        if (imgBtnRight != null) {
            imgBtnRight.setBackgroundResource(res);
        }
        return this;
    }

    public TitleBuilder changeRightImgSrc(int res) {
        if (imgBtnRight != null) {
            imgBtnRight.setBackgroundResource(res);
        }
        return this;
    }

    public TitleBuilder setVisible(int visiblity) {
        if (layoutContiner != null) {
            layoutContiner.setVisibility(visiblity);
        }
        return this;
    }

    public TitleBuilder leftGone() {
        if (imgBtnLeft != null) {
            imgBtnLeft.setVisibility(View.GONE);
        }
        if (txtLeft != null) {
            txtLeft.setVisibility(View.GONE);
        }
        return this;
    }

    public TitleBuilder toggleVisible() {
        if (layoutContiner != null) {
            if (layoutContiner.getVisibility() == View.VISIBLE) {
                layoutContiner.setVisibility(View.GONE);
            } else {
                layoutContiner.setVisibility(View.VISIBLE);
            }
        }
        return this;
    }

    /**
     * 设置为点击模式
     */
    public TitleBuilder clickRight(View.OnClickListener onClickListener) {
        if (txtRight != null) {
            txtRight.setOnClickListener(onClickListener);
        }
        if (imgBtnRight != null) {
            imgBtnRight.setOnClickListener(onClickListener);
        }
        return this;
    }

    public TextView getCenterTxt() {
        return txtCenter;
    }

    public ImageButton getLeftImgBtn() {
        return imgBtnLeft;
    }

    public ImageButton getRightImgBtn() {
        return imgBtnRight;
    }

    private View getViewById(int id) {
        switch (type) {
            case TYPE_ACTIVITY:
                if (activity != null) {
                    return activity.findViewById(id);
                } else {
                    return null;
                }
            case TYPE_FRAGMENT:
                if (rootView != null) {
                    return rootView.findViewById(id);
                }
            default:
                return null;
        }
    }

    public void setEnabled(boolean enable) {
        if (layoutContiner != null) {
            layoutContiner.setEnabled(enable);
        }
    }
}
