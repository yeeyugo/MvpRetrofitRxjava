package com.yee.mvp.recyclerview;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 开发人员：叶斌一
 * 创建时间：2021/1/5 5:48
 * 功能描述：
 */
public abstract class NormalAdapter<T> extends RecyclerView.Adapter<NormalAdapter.VH> {
    private List<T> mData;

    public NormalAdapter(List<T> data) {
        mData = data;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return VH.get(parent, getLayoutId(viewType));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        convert(holder, mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public abstract int getLayoutId(int viewType);
    public abstract void convert(VH holder, T data, int position);

    static class VH extends RecyclerView.ViewHolder{
        private SparseArray<View> mViews;
        private View mConvertView;

        public VH(@NonNull View itemView) {
            super(itemView);
            mConvertView = itemView;
            mViews = new SparseArray<>();
        }

        public static VH get(ViewGroup parent, int layoutId){
            View convertView = LayoutInflater.from(parent.getContext())
                    .inflate(layoutId, parent, false);
            return new VH(convertView);
        }

        public <T extends View> T getView(int id){
            View view = mViews.get(id);
            if (null == view){
                view = mConvertView.findViewById(id);
                mViews.put(id, view);
            }
            return (T) view;
        }

        public void setText(int id, String value){
            TextView textView = getView(id);
            textView.setText(value);
        }
    }
}
