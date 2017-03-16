package org.yeewoe.mopassion.app.maintab.view.apdaters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.adapter.MopaAdapter;
import org.yeewoe.mopassion.app.maintab.model.ImLineVo;
import org.yeewoe.mopassion.photo.fresco_lib.HeadThumbSimpleDraweeView;
import org.yeewoe.mopassion.utils.IntentManager;

import butterknife.Bind;

/**
 * Created by wyw on 2016/3/29.
 */
public class ImListAdapter extends MopaAdapter<ImLineVo> {

    public ImListAdapter(Context context) {
        super(context);
    }

    @Override public int getItemLayoutId() {
        return R.layout.item_im_list;
    }

    @Override public MopaViewHolder getViewHolder(View view) {
        return new ImListViewHolder(view);
    }

    @Override protected void bindViewHolder(MopaViewHolder mopaViewHolder, @NonNull final ImLineVo data) {
        ImListViewHolder viewHolder = (ImListViewHolder) mopaViewHolder;
        viewHolder.mChatNameTV.setText(data.getUser().getName());
        viewHolder.mChatLastContentTV.setText(data.getMsg());
        viewHolder.mHeadSimpleDraweeView.setImageHead(data.getUser());
        viewHolder.mChatLastTimeTV.setText(data.getTime());
        viewHolder.mViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentManager.IM.intentToChat(context, data.getUser().getSid(), data.getUser().getName());
            }
        });
    }


    class ImListViewHolder extends MopaViewHolder {
        @Bind(R.id.txt_chat_name) TextView mChatNameTV;
        @Bind(R.id.txt_chat_last_content) TextView mChatLastContentTV;
        @Bind(R.id.txt_chat_last_time) TextView mChatLastTimeTV;
        @Bind(R.id.img_head) HeadThumbSimpleDraweeView mHeadSimpleDraweeView;


        public ImListViewHolder(View convertView) {
            super(convertView);
        }
    }

}
