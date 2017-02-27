package com.baway.wangpeiyan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baway.wangpeiyan.R;
import com.baway.wangpeiyan.bean.Bean;
import com.baway.wangpeiyan.bean.ReBean;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

import static com.baway.wangpeiyan.R.id.imageView;

/**
 * Created by dell on 2017/2/27.
 */

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    List<ReBean.ItemsBeanHeadline> items;
    //图片在右侧
    private static final int TYPE_NORMAL = 1;
    //没有图片
    private static final int TYPE_PIC = 2;

    public Adapter(Context context, List<ReBean.ItemsBeanHeadline> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //判断返回那个类型的ViewHodler
        View view;
        if (viewType == TYPE_NORMAL) {
            view = LayoutInflater.from(context).inflate(R.layout.item1, parent, false);
            return new VideoViewHolder(view);
        }
        if (viewType == TYPE_PIC) {
            view = LayoutInflater.from(context).inflate(R.layout.item2, parent, false);
            return new ImageBigViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String content = items.get(position).getContent();
        Gson gson2 = new Gson();
        Bean bean = gson2.fromJson(content, Bean.class);
        if (holder instanceof VideoViewHolder) {
            VideoViewHolder viewHolder = (VideoViewHolder) holder;
            viewHolder.tv_news_title.setText(bean.getNickname());
            viewHolder.iv_news_pic.setText(bean.getBody());
        }
        if (holder instanceof ImageBigViewHolder) {
            ImageBigViewHolder viewHolder = (ImageBigViewHolder) holder;
            viewHolder.tv_news_title.setText(bean.getNickname());
            viewHolder.iv_news_pic.setText(bean.getBody());
            Glide.with(context).load("http://res.dxycdn.com/upload/"+bean.getUrl()).into(viewHolder.iamge);
        }
    }

    @Override
    public int getItemViewType(int position) {
        String content = items.get(position).getContent();
        Gson gson2 = new Gson();
        Bean bean = gson2.fromJson(content, Bean.class);
        //进行判断
        if (!TextUtils.isEmpty(bean.getUrl())) {
            //有图
            return TYPE_PIC;
        }
        //默认返回正常的
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //无图
    static class VideoViewHolder extends RecyclerView.ViewHolder {

        TextView tv_news_title;
        TextView iv_news_pic;

        public VideoViewHolder(View itemView) {
            super(itemView);
            tv_news_title = (TextView) itemView.findViewById(R.id.nameTv_);
            iv_news_pic = (TextView) itemView.findViewById(R.id.conten_);
        }
    }

    //有图
    static class ImageBigViewHolder extends RecyclerView.ViewHolder {

        TextView tv_news_title;
        TextView iv_news_pic;
        ImageView iamge;

        public ImageBigViewHolder(View itemView) {
            super(itemView);
            tv_news_title = (TextView) itemView.findViewById(R.id.nameTv);
            iv_news_pic = (TextView) itemView.findViewById(R.id.conten);
            iamge = (ImageView) itemView.findViewById(imageView);
        }
    }

}
