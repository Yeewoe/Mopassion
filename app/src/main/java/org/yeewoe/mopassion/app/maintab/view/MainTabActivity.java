package org.yeewoe.mopassion.app.maintab.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.yeewoe.commonutils.common.cache.FragmentCacheCenter;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.view.acitivty.MopaActivity;
import org.yeewoe.mopassion.app.maintab.presenter.MainTabPresenter;
import org.yeewoe.mopassion.app.maintab.view.iview.IMainTabView;
import org.yeewoe.mopassion.constants.TrendConstants;
import org.yeewoe.mopassion.event.NetChangeEvent;
import org.yeewoe.mopassion.event.OnActivityResultReceiveEvent;
import org.yeewoe.mopassion.service.AppServiceManager;
import org.yeewoe.mopassion.service.AppServiceType;
import org.yeewoe.mopassion.utils.LogCore;

import butterknife.Bind;

public class MainTabActivity extends MopaActivity<MainTabPresenter> implements TabLayout.OnTabSelectedListener, IMainTabView {

    public static final int REQUEST_TREND_PUBLISH = 0x666;
    private FragmentCacheCenter fragmentCacheCenter = new FragmentCacheCenter();

    private Class[] fragmentClasses = new Class[]{
            NearByFragment.class,
            FindFragment.class,
            ImFragment.class,
            MeFragment.class
    };

    private int[] tabNameResIds = new int[]{
            R.string.main_tab_item_nearby,
            R.string.main_tab_item_discover,
            R.string.main_tab_item_im,
            R.string.main_tab_item_me,
    };

    private int[] tabImageResIds = new int[]{
            R.drawable.selector_tab_nearby,
            R.drawable.selector_tab_find,
            R.drawable.selector_tab_im,
            R.drawable.selector_tab_me,
    };

    @Bind(R.id.tablayout_main_tab) TabLayout tabLayoutMain;
    private boolean cancelFlag;

    @Override
    protected int getViewRootResId() {
        return R.layout.activity_main_tab;
    }

    @Override
    protected void bindIntent() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void bindLister() {
        tabLayoutMain.setOnTabSelectedListener(this);
    }

    @Override
    protected MainTabPresenter getPresenter() {
        return new MainTabPresenter(this);
    }

    @Override
    protected void bindData() {
        for (int i = 0; i < fragmentClasses.length; i++) {
            tabLayoutMain.addTab(tabLayoutMain.newTab().setCustomView(getTabView(i)));
//            tabLayoutMain.addTab(tabLayoutMain.newTab().setText("" + i));
        }

        // 开启同步服务
        AppServiceManager.getInstance().wakeUp(AppServiceType.SYNC);
    }

    @Override
    protected void afterOnStart() {

    }

    @Override
    protected void afterOnResume() {
        // 开始定位
        mPresenter.requestLocation();
    }

    @Override
    protected void beforeOnPause() {
        // 关闭定位
        mPresenter.stopLocation();
    }

    @Override
    protected void beforOnStop() {

    }

    @Override
    protected void beforeOnDestroy() {

    }

    @Override
    protected boolean isRegisterEvent() {
        return true;
    }

    @Subscribe
    public void onNetChangeEvent(@NonNull NetChangeEvent event) {
        showToast(event.toString());
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        LogCore.d("tab.getPosition=" + position);
        changeTab(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    private View getTabView(int position) {
        View view = getLayoutInflater().inflate(R.layout.view_main_tab_item, tabLayoutMain, false);
        TextView txtTabName = (TextView) view.findViewById(R.id.txt_tab_name);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        txtTabName.setText(getTabNameResId(position));
        imageView.setImageResource(getTabImageResId(position));
        return view;
    }

    private int getTabNameResId(int position) {
        return tabNameResIds[position];
    }

    private int getTabImageResId(int position) {
        return tabImageResIds[position];
    }

    private void changeTab(int position) {
        Class fragmentClass = fragmentClasses[position];

        Fragment fragmentTab;
        fragmentTab = fragmentCacheCenter.get(fragmentClass);
        if (fragmentTab == null) {
            if (fragmentClass == FindFragment.class) {
                fragmentTab = FindFragment.newInstance("", "");
            } else if (fragmentClass == ImFragment.class) {
                fragmentTab = ImFragment.newInstance("", "");
            } else if (fragmentClass == MeFragment.class) {
                fragmentTab = MeFragment.newInstance("", "");
            } else {
                fragmentTab = NearByFragment.newInstance("", "");
            }
        }
        fragmentCacheCenter.put(fragmentClass, fragmentTab);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_main_content, fragmentTab);
        fragmentTransaction.commit();
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_TREND_PUBLISH) {
                mEventBus.post(new OnActivityResultReceiveEvent(data.getExtras().getParcelable(TrendConstants.EXTRA_TREND_PUBLISHED)));
                // TODO 界面通知机制问题
            }
        }
    }

    @Override public boolean handleBackPressed() {
        if (!cancelFlag) {
            try {
                Toast.makeText(getApplicationContext(),
                        R.string.cancel_alert,
                        Toast.LENGTH_SHORT).show();
            } catch (InflateException | OutOfMemoryError e) {
                LogCore.i(Log.getStackTraceString(e));
            }

            cancelFlag = true;

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    cancelFlag = false;
                }
            }, 2000);
        } else {
            moveTaskToBack(true);
        }
        return true;
    }
}
