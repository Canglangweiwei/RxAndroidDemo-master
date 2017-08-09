package com.ysr.myrrr;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.ysr.myrrr.adapter.CircleAdapter;
import com.ysr.myrrr.base.BaseFragment;
import com.ysr.myrrr.httputils.HttpPost;
import com.ysr.myrrr.httputils.NewsBean;
import com.ysr.myrrr.utils.LogUtils;

import butterknife.Bind;
import rx.Subscriber;


public class ZoneFragment extends BaseFragment
        implements OnRefreshListener,
        OnLoadMoreListener {

    @Bind(R.id.irc)
    IRecyclerView irc;

    private CircleAdapter mAdapter;

    public static ZoneFragment newInstance() {
        return new ZoneFragment();
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_zone;
    }

    @Override
    protected void initUi() {
        mAdapter = new CircleAdapter(getActivity());
        mAdapter.openLoadAnimation(new com.aspsine.irecyclerview.animation.ScaleInAnimation());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        irc.setLayoutManager(linearLayoutManager);
//        // 设置item间距，30dp
//        irc.addItemDecoration(new SpaceItemDecoration(30));
        irc.setAdapter(mAdapter);
    }

    @Override
    protected void initDatas() {
        sendRequest();
    }

    @Override
    protected void initListener() {
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);
    }

    private void sendRequest() {
        HttpPost httpPost = new HttpPost();
        httpPost.getTest(new Subscriber<NewsBean>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("onError", "连接失败");
            }

            @Override
            public void onNext(NewsBean newsBean) {
                LogUtils.d("加载完成");
                mAdapter.reset(newsBean.getV9LG4B3A0());
            }
        });
    }

    @Override
    public void onRefresh() {
        mAdapter.getPageBean().setRefresh(true);
        // 发起请求
        irc.setRefreshing(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAdapter.getPageBean().isRefresh()) {
                    irc.setRefreshing(false);
                }
            }
        }, 1200);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        mAdapter.getPageBean().setRefresh(false);
        // 发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!mAdapter.getPageBean().isRefresh()) {
                    // 加载成功
//                irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    // 没有多余数据了
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }, 1200);
    }
}
