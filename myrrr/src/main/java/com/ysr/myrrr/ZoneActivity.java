package com.ysr.myrrr;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;

import com.jaydenxiao.common.commonutils.NetUtil;
import com.jaydenxiao.common.commonwidget.NormalTitleBar;
import com.ysr.myrrr.base.BaseActivity;

import butterknife.Bind;


@SuppressWarnings("ALL")
public class ZoneActivity extends BaseActivity {

    @Bind(R.id.drawer_layout)
    LinearLayout drawerLayout;

    @Bind(R.id.ntb)
    NormalTitleBar ntb;

    @Override
    protected int initContentView() {
        return R.layout.activity_zone;
    }

    @Override
    protected void initUi() {
        if (!isPermitWriteStorage()) {
            requestPermissions(
                    new String[]{Manifest.permission.INTERNET},
                    PRTMISSION_WRITE_EXTERNAL_STORAGE);
            checkNetState();
        }
        initToolbar();
    }

    /**
     * 初始化标题栏
     */
    private void initToolbar() {
        // 标题栏
        ntb.setTitleText(getString(R.string.app_name));
        // 点击返回
        ntb.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZoneApplication.get().finishAllActivity();
            }
        });
    }

    @Override
    protected void initDatas() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        ZoneFragment fragment = ZoneFragment.newInstance();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    @Override
    protected void initListener() {

    }

    /**
     * 检查网络是否连接
     */
    private void checkNetState() {
        if (NetUtil.getNetworkType() == NetUtil.NETWORK_TYPE_NONE) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("网络状态提醒");
            builder.setMessage("当前网络不可用，是否打开网络设置?");
            builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (android.os.Build.VERSION.SDK_INT > 10) {
                        startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                    } else {
                        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                    }
                }
            });
            builder.create().show();
        }
    }

    private final static int PRTMISSION_WRITE_EXTERNAL_STORAGE = 1;

    // 判断sdl版本是否大于23
    private boolean isPermitWriteStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PRTMISSION_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }
    }


    private long newTime;

    /**
     * 监听返回键
     */
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - newTime > 2000) {
            newTime = System.currentTimeMillis();
            Snackbar snackbar = Snackbar.make(drawerLayout, getString(R.string.press_twice_exit), Snackbar.LENGTH_SHORT);
            snackbar.setAction(R.string.exit_directly, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ZoneApplication.get().finishAllActivity();
                }
            });
            snackbar.show();
        } else {
            moveTaskToBack(true);
        }
    }
}
