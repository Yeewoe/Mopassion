package org.yeewoe.mopassion.app.nearby.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.adapter.MopaAdapter;
import org.yeewoe.mopassion.app.common.view.widget.MopaTextView;
import org.yeewoe.mopassion.app.nearby.model.NearByUserLineVo;
import org.yeewoe.mopassion.photo.fresco_lib.HeadThumbSimpleDraweeView;
import org.yeewoe.mopassion.utils.IntentManager;

import butterknife.Bind;

/**
 * Created by wyw on 2016/4/9.
 */
public class NearByUserLineAdapter extends MopaAdapter<NearByUserLineVo> {
    public NearByUserLineAdapter(Context context) {
        super(context);
    }

    @Override public int getItemLayoutId() {
        return R.layout.item_nearby_user_list;
    }

    @Override public MopaViewHolder getViewHolder(View view) {
        return new UserLineViewHolder(view);
    }

    @Override protected void bindViewHolder(MopaViewHolder mopaViewHolder, @NonNull final NearByUserLineVo data) {
        UserLineViewHolder viewHolder = (UserLineViewHolder) mopaViewHolder;
        viewHolder.mTxtUserName.setUser(data.getUser());
        viewHolder.mImgViHead.setImageHead(data.getUser());
        viewHolder.mTxtUserInfo.setText(context.getString(R.string.nearby_user_item_info, data.getLocTime(), data.getDistance()));
        viewHolder.mTxtUserContent.setText(data.getSource().getSignature());
        viewHolder.mViewItem.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                IntentManager.Contact.intentToDetail(context, data.getUser());
            }
        });
    }

    class UserLineViewHolder extends MopaViewHolder {

        @Bind(R.id.txt_user_name) MopaTextView mTxtUserName;
        @Bind(R.id.txt_user_info) MopaTextView mTxtUserInfo;
        @Bind(R.id.txt_user_content) MopaTextView mTxtUserContent;
        @Bind(R.id.imgvi_user_photo) HeadThumbSimpleDraweeView mImgViHead;

        public UserLineViewHolder(View convertView) {
            super(convertView);
        }
    }
}
