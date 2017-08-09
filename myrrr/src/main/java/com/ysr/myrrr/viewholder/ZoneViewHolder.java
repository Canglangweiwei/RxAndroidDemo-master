package com.ysr.myrrr.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.ysr.myrrr.R;
import com.ysr.myrrr.httputils.NewsBean;

import butterknife.Bind;
import butterknife.ButterKnife;


@SuppressWarnings("ALL")
public class ZoneViewHolder extends RecyclerView.ViewHolder {

    private Context mContext;
    private View _itemView;

    @Bind(R.id.news_picture_iv)
    ImageView news_picture_iv;
    @Bind(R.id.news_title_tv)
    TextView news_title_tv;
    @Bind(R.id.news_ptime_tv)
    TextView news_ptime_tv;
    @Bind(R.id.news_source_tv)
    TextView news_source_tv;

    public static ZoneViewHolder create(Context context) {
        ZoneViewHolder imageViewHolder = new ZoneViewHolder(LayoutInflater.from(context).inflate(R.layout.item_circle_zone, null), context);
        return imageViewHolder;
    }

    private ZoneViewHolder(View itemView, final Context context) {
        super(itemView);
        this.mContext = context;
        this._itemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public void setData(NewsBean.V9LG4B3A0Bean resultBean, final int position) {
        Glide.with(mContext).load(resultBean.getCover()).into(news_picture_iv);
        news_title_tv.setText(resultBean.getTitle());
        news_ptime_tv.setText(resultBean.getPtime());
        news_source_tv.setText(resultBean.getVideosource());

        _itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ToastUitl.showShort("我点击了第" + position + "条数据");
            }
        });
    }
}
