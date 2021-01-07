package com.yee.mvp.recyclerview;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 开发人员：叶斌一
 * 创建时间：2021/1/4 19:56
 * 功能描述：封装RecyclerView通用的ViewHolder
 */
public class RecyclerHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;

    public RecyclerHolder(@NonNull View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);

        if (view == null){
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;
    }

    public void setText(int viewId, String value){
        TextView textView = getView(viewId);
        textView.setText(value);
    }
}
