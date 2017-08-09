package com.ysr.myrrr;

import android.app.Activity;

import com.jaydenxiao.common.baseapp.BaseApplication;
import com.ysr.myrrr.httputils.HttpRetrofit;

import java.util.Stack;

@SuppressWarnings("ALL")
public class ZoneApplication extends BaseApplication {

    private Stack<Activity> activityStack;// activity栈

    private static ZoneApplication sApp;
    public HttpRetrofit httpRetrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        httpRetrofit = new HttpRetrofit();
    }

    public static ZoneApplication get() {
        return sApp;
    }

    /**
     * 把一个activity压入栈列中
     *
     * @param actvity
     */
    public void pushActivityToStack(Activity actvity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(actvity);
    }

    /**
     * 获取栈顶的activity，先进后出原则
     */
    public Activity getLastActivityFromStack() {
        return activityStack.lastElement();
    }

    /**
     * 从栈列中移除一个activity
     *
     * @param activity
     */
    public void popActivityFromStack(Activity activity) {
        if (activityStack != null && activityStack.size() > 0) {
            if (activity != null) {
                activity.finish();
                activityStack.remove(activity);
                activity = null;
            }
        }
    }

    /**
     * 退出所有activity
     */
    public void finishAllActivity() {
        if (activityStack != null) {
            while (activityStack.size() > 0) {
                Activity activity = getLastActivityFromStack();
                if (activity == null) {
                    break;
                }
                popActivityFromStack(activity);
            }
        }
    }
}
