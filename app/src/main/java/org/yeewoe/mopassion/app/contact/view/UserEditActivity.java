package org.yeewoe.mopassion.app.contact.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import org.yeewoe.commonutils.common.assist.UIVerifier;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.view.acitivty.MopaActivity;
import org.yeewoe.mopassion.app.common.view.builder.TitleBuilder;
import org.yeewoe.mopassion.app.common.view.widget.HeadPhotoGridLayout;
import org.yeewoe.mopassion.app.common.view.widget.MopaTextView;
import org.yeewoe.mopassion.app.contact.model.UserDetailVo;
import org.yeewoe.mopassion.app.contact.model.UserEditParam;
import org.yeewoe.mopassion.app.contact.presenter.UserEditPresenter;
import org.yeewoe.mopassion.app.contact.view.iview.IUserDetailView;
import org.yeewoe.mopassion.utils.SoftWareUtil;
import org.yeewoe.mopassion.utils.TimeUtil;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 个人信息编辑页
 * Created by wyw on 2016/4/26.
 */
public class UserEditActivity extends MopaActivity<UserEditPresenter> implements IUserDetailView {
    public static final String EXTRA_USER_DETAIL = "extra_user_detail";
    public static final String EXTRA_USER_SID = "extra_user_sid";

    public UserDetailVo userDetailVo;
    private TitleBuilder title;

    @Bind(R.id.til_nick) TextInputLayout mTILNick;
    @Bind(R.id.til_signature) TextInputLayout mTILSignature;
    @Bind(R.id.til_address) TextInputLayout mTILAddress;
    @Bind(R.id.txt_birthday) MopaTextView mTxtBirthday;
    @Bind(R.id.head_photo_gl_main) HeadPhotoGridLayout mHeadPhotoGridLayoutMain;

    @Override protected boolean isScaleIn() {
        return true;
    }

    @Override protected int getViewRootResId() {
        return R.layout.activity_user_edit;
    }

    @Override protected void bindIntent() {
        this.userDetailVo = getIntent().getParcelableExtra(EXTRA_USER_DETAIL);
    }

    @Override protected void initTitle() {
        title = new TitleBuilder(this);
        title.changeCenterTxt(R.string.edit).changeRightTxt(R.string.confirm).changeLeftToPrimaryColor().changeRightToPrimaryColor().clickRight(new View.OnClickListener() {
            @Override public void onClick(View v) {

                SoftWareUtil.hideSoftWare(UserEditActivity.this); // 缩键盘

                if (!mVerifier.verifyEmpty(mTILNick.getEditText())) {
                    mTILNick.setError(getString(R.string.user_edit_nick_name_empty));
                    return ;
                }

                mPresenter.submit(new UserEditParam(mHeadPhotoGridLayoutMain.getMediaImageList(),
                        mHeadPhotoGridLayoutMain.getFileList(), userDetailVo.getSid(),
                        userDetailVo.getAccount(), mTILNick.getEditText().getText().toString(),
                        mTILSignature.getEditText().getText().toString(),
                        mTILAddress.getEditText().getText().toString(),
                        mTxtBirthday.getTag() == null ? 0L : (long) mTxtBirthday.getTag()));
            }
        }).modeToBack(this);
    }

    @Override protected void bindLister() {

    }

    @Override protected UserEditPresenter getPresenter() {
        return new UserEditPresenter(this);
    }

    @Override protected void bindData() {
        mTILNick.setHint(getString(R.string.user_edit_nick_hint));
        mTILSignature.setHint(getString(R.string.user_edit_signature_hint));
        mTILAddress.setHint(getString(R.string.address));
        mHeadPhotoGridLayoutMain.setActivityView(this);
        initDetail(userDetailVo);
        mPresenter.loadNet(userDetailVo.getSid());
    }

    @Override protected void afterOnStart() {

    }

    @Override protected void afterOnResume() {

    }

    @Override protected void beforeOnPause() {

    }

    @Override protected void beforOnStop() {

    }

    @Override protected void beforeOnDestroy() {

    }

    @Override public Activity getActivity() {
        return this;
    }

    @Override public void initDetail(UserDetailVo data) {
        userDetailVo = data;
        mTILNick.getEditText().setText(data.getNickname());
        mTILSignature.getEditText().setText(data.getSignature());
        mTILAddress.getEditText().setText(data.getAddress());
        if (data.getBirthday() > 0) {
            long birthday = data.getBirthday();
            bindBirthday(birthday);
        }
        mHeadPhotoGridLayoutMain.setPhotos(data.getHeadPhotos());
    }

    private void bindBirthday(long birthday) {
        mTxtBirthday.setText(TimeUtil.parseTime(birthday, TimeUtil.pattern5));
        mTxtBirthday.setTag(birthday);
    }

    private long getBirthday() {
        return mTxtBirthday.getTag() != null ? (long) mTxtBirthday.getTag() : 0L;
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mHeadPhotoGridLayoutMain.handleActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.ll_birthday_container) void onBirthdayClick(View view) {

        long birthday = getBirthday();
        Calendar current = TimeUtil.getCalendar();
        if (birthday > 0) {
            /** 初始化 **/
            current.setTimeInMillis(birthday);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar birthday = TimeUtil.getCalendar();
                birthday.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                bindBirthday(birthday.getTimeInMillis());
            }
        }, current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setCanceledOnTouchOutside(true);
        datePickerDialog.show();
    }


}
