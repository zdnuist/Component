package me.zdnuist.proxy.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.zdnuist.proxy.R;
import me.zdnuist.proxy.adapter.MyRecycleAdapter.ZhiHuViewHolder;
import me.zdnuist.proxy.entity.ZhiHu;

public class ZhihuAdapter extends PagedListAdapter<ZhiHu, ZhiHuViewHolder> {

  public ZhihuAdapter() {
    super(DIFF_CALLBACK);
  }

  @Override
  public ZhiHuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.layout_item_recycle, parent, false);
    ZhiHuViewHolder holder = new ZhiHuViewHolder(view);
    return holder;
  }

  @Override
  public void onBindViewHolder(ZhiHuViewHolder holder, int position) {
    ZhiHu user = getItem(position);
    if (user != null) {
      holder.bindTo(user);
    } else {
      // Null defines a placeholder item - PagedListAdapter will automatically invalidate
      // this row when the actual object is loaded from the database
      holder.clear();
    }
  }


  public static final DiffCallback<ZhiHu> DIFF_CALLBACK = new DiffCallback<ZhiHu>() {
    @Override
    public boolean areItemsTheSame(@NonNull ZhiHu oldZh,
        @NonNull ZhiHu newZh) {
      // User properties may have changed if reloaded from the DB, but ID is fixed
      return TextUtils.equals(oldZh.title,newZh.title);
    }

    @Override
    public boolean areContentsTheSame(@NonNull ZhiHu oldZh,
        @NonNull ZhiHu newZh) {
      // NOTE: if you use equals, your object must properly override Object#equals()
      // Incorrectly returning false here will result in too many animations.
      return TextUtils.equals(oldZh.title,newZh.title);
    }
  };
}