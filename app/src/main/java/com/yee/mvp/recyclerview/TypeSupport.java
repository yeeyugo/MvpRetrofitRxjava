package com.yee.mvp.recyclerview;

/**
 * 开发人员：叶斌一
 * 创建时间：2021/1/4 20:01
 * 功能描述：RecyclerView添加多type支持
 */
public interface TypeSupport<T> {
    int getLayoutId(T item);
}
