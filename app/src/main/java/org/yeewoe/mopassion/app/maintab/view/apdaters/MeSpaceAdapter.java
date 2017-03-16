package org.yeewoe.mopassion.app.maintab.view.apdaters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.adapter.MopaAdapter;
import org.yeewoe.mopassion.app.common.model.ImageWallVo;
import org.yeewoe.mopassion.photo.fresco_lib.WallPhotoSimpleDraweeView;

import butterknife.Bind;

/**
 * Created by wyw on 2016/4/9.
 */
public class MeSpaceAdapter extends MopaAdapter<ImageWallVo> {
    public MeSpaceAdapter(Context context) {
        super(context);
    }

    @Override public int getItemLayoutId() {
        return R.layout.item_image_wall;
    }

    @Override public MopaViewHolder getViewHolder(View view) {
        return new UserLineViewHolder(view);
    }

    @Override public long getStartId() {
        return super.getStartId();
    }

    @Override protected void bindViewHolder(MopaViewHolder mopaViewHolder, @NonNull final ImageWallVo data) {
        UserLineViewHolder viewHolder = (UserLineViewHolder) mopaViewHolder;
        viewHolder.mImgViHead.setImageMedia(data.getImage());
    }

    class UserLineViewHolder extends MopaViewHolder {
        @Bind(R.id.imgvi_user_photo) WallPhotoSimpleDraweeView mImgViHead;

        public UserLineViewHolder(View convertView) {
            super(convertView);
        }
    }
}
