package org.yeewoe.mopassion.app.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.mopassion.app.common.model.BaseVo;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 基类Adapter，抽象了公共逻辑和ViewHolder逻辑
 * Created by wyw on 2016/4/4.
 */
public abstract class MopaAdapter<T extends BaseVo> extends BaseAdapter {

    public interface OnAdapterChangeListener {

        void afterSetAll();
        void afterAddAll(boolean emptyResult, boolean hasMore);
    }
    protected List<T> entities;

    protected Context context;
    protected LayoutInflater layoutInflater;
    private OnAdapterChangeListener onAdapterChangeListener;
    public MopaAdapter(Context context) {
        this.context = context;
        this.entities = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
    }


    public void setOnAdapterChangeListener(OnAdapterChangeListener onAdapterChangeListener) {
        this.onAdapterChangeListener = onAdapterChangeListener;
    }

    public void setAll(List<T> entities) {
        this.entities = entities;
        if (this.entities == null) {
            this.entities = new ArrayList<>();
        }
        notifyDataSetChanged();
        if (onAdapterChangeListener != null) {
            onAdapterChangeListener.afterSetAll();
        }
    }

    public void end() {
        if (onAdapterChangeListener != null) {
            onAdapterChangeListener.afterAddAll(true, false);
        }
    }

    public void addAll(List<T> entities) {
        if (entities != null) {
            this.entities.addAll(entities);
        }
        notifyDataSetChanged();
        if (onAdapterChangeListener != null) {
            boolean emptyResult = !Checks.check(entities);
            onAdapterChangeListener.afterAddAll(emptyResult, !emptyResult);
        }
    }

    public void add(@NonNull T entity) {
        this.entities.add(entity);
        notifyDataSetChanged();
    }

    public void clearAll() {
        this.entities.clear();
        notifyDataSetChanged();
    }

    public void remove(@NonNull T entity) {
        this.entities.remove(entity);
        notifyDataSetChanged();
    }

    public void update(@NonNull T entity) {
        if (this.entities != null) {
            for (int i = 0; i < this.entities.size(); i++) {
                if (this.entities.get(i).equals(entity)) {
                    this.entities.set(i, entity);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override public int getCount() {
        return this.entities.size();
    }

    public long getStartId() {
        return this.entities.size();
    }

    @Override public T getItem(int position) {
        return this.entities.get(position);
    }

    @Override public long getItemId(int position) {
        return this.entities.get(position).getSid();
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        MopaViewHolder viewHolder;
        if (convertView == null) {
            convertView = getView(parent, position);
            viewHolder = getViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MopaViewHolder) convertView.getTag();
        }

        T entity = getItem(position);
        if (entity != null) {
            bindViewHolder(viewHolder, entity);
        }

        return convertView;
    }

    protected View getView(ViewGroup parent, int position) {
        return this.layoutInflater.inflate(getItemLayoutId(), parent, false);
    }

    public abstract int getItemLayoutId();

    public abstract MopaViewHolder getViewHolder(View view);

    protected abstract void bindViewHolder(MopaViewHolder mopaViewHolder, @NonNull T data);

    public static abstract class MopaViewHolder {
        public View mViewItem;
        public MopaViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
            mViewItem = convertView;
        }
    }
}
