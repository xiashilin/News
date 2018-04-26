package com.kaku.colorfulnews.mvp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaku.colorfulnews.R;
import com.kaku.colorfulnews.common.Constants;
import com.kaku.colorfulnews.mvp.entity.CollectBean;
import com.kaku.colorfulnews.mvp.ui.activities.NewsBrowserActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Email:1479714932@qq.com
 *
 * @author:xsl Date:2017/12/22,Time:17:00
 * Description:
 */

public class CollectAdapter extends RecyclerView.Adapter<ViewHolder> implements View.OnClickListener {

    private List<CollectBean> mItemList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener = null;

    public CollectAdapter(Context context, List<CollectBean> datas) {
        this.mItemList = datas;
        this.mContext = context;
    }

    public void setList(List<CollectBean> itemList) {
        this.mItemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.collect_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
        holder.title.setText(mItemList.get(position).getTitle());
        holder.time.setText(mItemList.get(position).getTime());
        if (!TextUtils.isEmpty(mItemList.get(position).getImg())) {
            Picasso.with(mContext).load(mItemList.get(position).getImg()).into(holder.img);
        } else {
            Picasso.with(mContext).load(R.mipmap.ic_no).into(holder.img);
        }

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, NewsBrowserActivity.class);
                intent.putExtra(Constants.NEWS_TITLE, mItemList.get(position).getTitle());
                intent.putExtra(Constants.NEWS_LINK, mItemList.get(position).getLink());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }


    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}

class ViewHolder extends RecyclerView.ViewHolder {

    View rootView;
    TextView title;
    TextView time;
    ImageView img;


    public ViewHolder(View itemView) {
        super(itemView);
        rootView = itemView.findViewById(R.id.cardView);
        title = itemView.findViewById(R.id.news_title);
        time = itemView.findViewById(R.id.news_time);
        img = itemView.findViewById(R.id.collect_img);
    }
}