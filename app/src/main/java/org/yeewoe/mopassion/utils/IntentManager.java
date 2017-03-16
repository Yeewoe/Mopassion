package org.yeewoe.mopassion.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import org.yeewoe.commonutils.common.utils.CatchUtil;
import org.yeewoe.mopassion.app.auth.view.LoginActivity;
import org.yeewoe.mopassion.app.auth.view.RegisterActivity;
import org.yeewoe.mopassion.app.common.model.UserHeadVo;
import org.yeewoe.mopassion.app.contact.model.UserDetailVo;
import org.yeewoe.mopassion.app.contact.view.ContactFansListActivity;
import org.yeewoe.mopassion.app.contact.view.ContactFollowerListActivity;
import org.yeewoe.mopassion.app.contact.view.UserDetailActivity;
import org.yeewoe.mopassion.app.contact.view.UserEditActivity;
import org.yeewoe.mopassion.app.help.view.HelpMainActivity;
import org.yeewoe.mopassion.app.im.view.MopaChatActivity;
import org.yeewoe.mopassion.app.maintab.view.MainTabActivity;
import org.yeewoe.mopassion.app.nearby.model.TrendLineVo;
import org.yeewoe.mopassion.app.nearby.model.TrendPublishParam;
import org.yeewoe.mopassion.app.nearby.view.TrendDetailActivity;
import org.yeewoe.mopassion.app.nearby.view.TrendPublishActivity;
import org.yeewoe.mopassion.app.nearby.view.TrendReviewActivity;
import org.yeewoe.mopassion.app.nearby.view.UserTrendListActivity;
import org.yeewoe.mopassion.app.setting.view.SettingMainActivity;
import org.yeewoe.mopassion.app.web.view.WebBrowserActivity;
import org.yeewoe.mopassion.photo.ui.PhotoViewActivity;
import org.yeewoe.mopassion.template.media.TMediaImage;

import java.util.ArrayList;

/**
 * Created by wyw on 2016/3/22.
 */
public class IntentManager {
    public static void intentToLoginActivity(Context context) {
        AuthUtil.logout();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        CatchUtil.startActivity(context, intent);
    }

    public static void intentToMainTab(Context context) {
        CatchUtil.startActivity(context, new Intent(context, MainTabActivity.class));
    }

    public static void intentToRegister(Context context) {
        CatchUtil.startActivity(context, new Intent(context, RegisterActivity.class));
    }



    /**
     * IM
     */
    public static class IM {

        /**
         * 聊天页面
         *
         * @param toUid 对象ID
         */
        public static void intentToChat(Context context, long toUid, String nick) {
            Intent intent = new Intent(context, MopaChatActivity.class);
            intent.putExtra(MopaChatActivity.EXTRA_TO_UID, toUid);
            intent.putExtra(MopaChatActivity.EXTRA_TO_NICK, nick);
            CatchUtil.startActivity(context, intent);
        }
    }


    /**
     * 动态
     */
    public static class Trend {
        /**
         * 发布动态页面
         */
        public static void intentToPublishTrend(Activity context, int requestCode) {
            Intent intent = new Intent(context, TrendPublishActivity.class);
            CatchUtil.startActivityForResult(context, intent, requestCode);
        }

        /**
         * 发布预览页面
         */
        public static void intentToReviewTrend(Context context, TrendPublishParam param) {
            Intent intent = new Intent(context, TrendReviewActivity.class);
            intent.putExtra(TrendReviewActivity.EXTRA_PARAM, param);
            CatchUtil.startActivity(context, intent);
        }

        /**
         * 查看指定人动态
         */
        public static void intentToUserTrendList(Context context, UserDetailVo userDetailVo) {
            if (userDetailVo == null) {
                return ;
            }
            Intent intent = new Intent(context, UserTrendListActivity.class);
            intent.putExtra(UserTrendListActivity.EXTRA_USER_DETAIL, userDetailVo);
            CatchUtil.startActivity(context, intent);
        }

        /**
         * 查看Trend详情
         */
        public static void intentToTrendDetail(Context context, TrendLineVo data) {
            Intent intent = new Intent(context, TrendDetailActivity.class);
            intent.putExtra(TrendDetailActivity.EXTRA_TREND, data);
            CatchUtil.startActivity(context, intent);
        }
    }

    /**
     * 图片
     */
    public static class Photo {

        /**
         * 查看图片
         */
        public static void intentToPhotoView(Context context, TMediaImage tMediaImage) {
            Intent intent = new Intent(context, PhotoViewActivity.class);
            ArrayList<TMediaImage> tMediaImageList = new ArrayList<>();
            tMediaImageList.add(tMediaImage);
            intent.putParcelableArrayListExtra(PhotoViewActivity.EXTRA_IMAGES, tMediaImageList);
            CatchUtil.startActivity(context, intent);
        }
    }

    /**
     * 联系人
     */
    public static class Contact {
        /**
         * 查看详情
         */
        public static void intentToDetail(Context context, UserHeadVo userHeadVo) {
            if (userHeadVo == null) {
                return ;
            }
            Intent intent = new Intent(context, UserDetailActivity.class);
            intent.putExtra(UserDetailActivity.EXTRA_USER_HEAD, userHeadVo);
            CatchUtil.startActivity(context, intent);
        }

        /**
         * 编辑信息
         */
        public static void intentToEdit(Context context, UserDetailVo userDetailVo) {
            if (userDetailVo == null) {
                return ;
            }
            Intent intent = new Intent(context, UserEditActivity.class);
            intent.putExtra(UserEditActivity.EXTRA_USER_DETAIL, userDetailVo);
            CatchUtil.startActivity(context, intent);
        }

        /**
         * 查看粉丝列表
         * @param context
         */
        public static void intentToFollowerList(Context context) {
            Intent intent = new Intent(context, ContactFollowerListActivity.class);
            CatchUtil.startActivity(context, intent);
        }

        /**
         * 查看粉丝列表
         */
        public static void intentToFansList(Context context) {
            Intent intent = new Intent(context, ContactFansListActivity.class);
            CatchUtil.startActivity(context, intent);
        }
    }

    /**
     * 设置
     */
    public static class Setting {
        /**
         * 主页面
         */
        public static void intentToMain(Context context) {
            Intent intent = new Intent(context, SettingMainActivity.class);
            CatchUtil.startActivity(context, intent);
        }
    }

    /**
     * 帮助
     */
    public static class Help {
        /**
         * 主页面
         */
        public static void intentToMain(Context context) {
            Intent intent = new Intent(context, HelpMainActivity.class);
            CatchUtil.startActivity(context, intent);
        }
    }

    public static class Web {
        public static void intentToWebBrowser(Context context, String url) {
            Intent intent = new Intent(context, WebBrowserActivity.class);
            intent.putExtra(WebBrowserActivity.EXTRA_URL, url);
            CatchUtil.startActivity(context, intent);
        }
    }
}
