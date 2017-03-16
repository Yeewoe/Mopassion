package org.yeewoe.mopassion.app.contact.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.adapter.MopaAdapter;
import org.yeewoe.mopassion.app.common.view.widget.MopaGenderView;
import org.yeewoe.mopassion.app.common.view.widget.MopaTextView;
import org.yeewoe.mopassion.app.contact.model.UserLineVo;
import org.yeewoe.mopassion.photo.fresco_lib.HeadThumbSimpleDraweeView;
import org.yeewoe.mopassion.utils.IntentManager;

import butterknife.Bind;

/**
 * Created by wyw on 2016/4/9.
 */
public class UserLineAdapter extends MopaAdapter<UserLineVo> {
    public UserLineAdapter(Context context) {
        super(context);
    }

    @Override public int getItemLayoutId() {
        return R.layout.item_user_list;
    }

    @Override public MopaViewHolder getViewHolder(View view) {
        return new UserLineViewHolder(view);
    }

    @Override protected void bindViewHolder(MopaViewHolder mopaViewHolder, @NonNull final UserLineVo data) {
        UserLineViewHolder viewHolder = (UserLineViewHolder) mopaViewHolder;
        viewHolder.mTxtUserName.setUser(data.getUser());
        viewHolder.mImgViHead.setImageHead(data.getUser());
        viewHolder.mTxtUserContent.setSignature(data.getSignature());
        viewHolder.mGenderView.setGender(data.getUser());
        viewHolder.mViewItem.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                IntentManager.Contact.intentToDetail(context, data.getUser());
            }
        });
    }

    class UserLineViewHolder extends MopaViewHolder {

        @Bind(R.id.txt_user_name) MopaTextView mTxtUserName;
        @Bind(R.id.txt_user_content) MopaTextView mTxtUserContent;
        @Bind(R.id.iv_trend_photo) HeadThumbSimpleDraweeView mImgViHead;
        @Bind(R.id.imgvi_gender) MopaGenderView mGenderView;

        public UserLineViewHolder(View convertView) {
            super(convertView);
        }
    }
}
