package com.sjtu.charles.activity;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjtu.charles.adapter.RecyclerViewLoadMoreAdapter;
import com.sjtu.charles.nestedwebview.R;

import java.util.List;

/**
 * [description]
 * author: yifei
 * created at 16/10/10 上午10:00
 */

public class TestAdapter extends RecyclerViewLoadMoreAdapter {

    private static final String TAG = "TestAdapter";
    private static final int TYPE_ITEM = 0;
    private List<String> mDatas;

    public TestAdapter(List<String> datas) {
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            Log.d(TAG,"onCreateViewHolder");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, null);
            return new MyViewHolder(view);
        }
        return super.onCreateViewHolder(parent,viewType);
    }

    @Override
    public int getItemCount() {
        return mDatas.size() == 0 ? 0 : mDatas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 != getItemCount()) {
            return TYPE_ITEM;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder,position);
        if (holder instanceof MyViewHolder) {
            Log.d(TAG,"onBindViewHolder " + position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position);
                    }
                }
            });
            ((MyViewHolder) holder).textView.setText(mDatas.get(position));
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }

}
