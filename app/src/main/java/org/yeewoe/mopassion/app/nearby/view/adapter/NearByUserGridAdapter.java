package org.yeewoe.mopassion.app.nearby.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.adapter.MopaAdapter;
import org.yeewoe.mopassion.app.common.view.widget.MopaGenderView;
import org.yeewoe.mopassion.app.common.view.widget.MopaTextView;
import org.yeewoe.mopassion.app.nearby.model.NearByUserLineVo;
import org.yeewoe.mopassion.photo.fresco_lib.WallPhotoSimpleDraweeView;
import org.yeewoe.mopassion.utils.IntentManager;

import butterknife.Bind;

/**
 * Created by wyw on 2016/4/9.
 */
public class NearByUserGridAdapter extends MopaAdapter<NearByUserLineVo> {
    public NearByUserGridAdapter(Context context) {
        super(context);
    }

    @Override public int getItemLayoutId() {
        return R.layout.item_discovery_cell;
    }

    @Override public MopaViewHolder getViewHolder(View view) {
        return new UserLineViewHolder(view);
    }

    @Override protected void bindViewHolder(MopaViewHolder mopaViewHolder, @NonNull final NearByUserLineVo data) {
        UserLineViewHolder viewHolder = (UserLineViewHolder) mopaViewHolder;
        viewHolder.mImgViHead.setImageHead(data.getUser());
        viewHolder.mTxtDistance.setText(context.getString(R.string.discovery_cell_item_distance, data.getDistance()));
        viewHolder.mGenderViewMain.setGender(data.getUser());
        viewHolder.mViewItem.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                IntentManager.Contact.intentToDetail(context, data.getUser());
            }
        });
    }

    class UserLineViewHolder extends MopaViewHolder {
        @Bind(R.id.imgvi_user_photo) WallPhotoSimpleDraweeView mImgViHead;
        @Bind(R.id.txt_distance) MopaTextView mTxtDistance;
        @Bind(R.id.genderview_main) MopaGenderView mGenderViewMain;

        public UserLineViewHolder(View convertView) {
            super(convertView);
        }
    }
}
