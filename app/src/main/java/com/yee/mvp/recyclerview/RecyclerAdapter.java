package com.yee.mvp.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 开发人员：叶斌一
 * 创建时间：2021/1/4 20:07
 * 功能描述：封装RecyclerView通用Adapter
 */
public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerHolder> {

    private int mLayoutId;
    private List<T> mData;
    private LayoutInflater mInflater;
    private TypeSupport mTypeSupport;
    private OnRecyclerClickListener mClickListener;
    private OnRecyclerLongClickListener mLongClickListener;

    public RecyclerAdapter(Context context, List<T> data, int layoutId) {
        mData = data;
        mLayoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }

    public RecyclerAdapter(Context context, List<T> data, TypeSupport typeSupport) {
        this(context, data, -1);
        mTypeSupport = typeSupport;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mTypeSupport != null){
            mLayoutId = viewType;
        }
        View itemView = mInflater.inflate(mLayoutId, parent, false);
        return new RecyclerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        convert(holder, mData.get(position), position);

        if (mClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(position);
                }
            });
        }

        if (mLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongClickListener.onItemLongClick(position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mTypeSupport != null){
            mTypeSupport.getLayoutId(mData.get(position));
        }
        return super.getItemViewType(position);
    }

    public void setClickListener(OnRecyclerClickListener clickListener) {
        mClickListener = clickListener;
    }

    public void setLongClickListener(OnRecyclerLongClickListener longClickListener) {
        mLongClickListener = longClickListener;
    }

    public abstract void convert(RecyclerHolder holder, T item, int position);
}
