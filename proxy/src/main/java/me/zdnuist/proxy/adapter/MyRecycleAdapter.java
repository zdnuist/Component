package me.zdnuist.proxy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import me.zdnuist.proxy.R;
import me.zdnuist.proxy.adapter.MyRecycleAdapter.ZhiHuViewHolder;
import me.zdnuist.proxy.entity.ZhiHu;

/**
 * Created by zd on 2017/9/18.
 */

public class MyRecycleAdapter extends RecyclerView.Adapter<ZhiHuViewHolder> {

  private LayoutInflater mInflater;
  private List<ZhiHu> datas;

  public MyRecycleAdapter(Context context) {
    mInflater = LayoutInflater.from(context);
    datas = new ArrayList<>();
  }

  public void setDatas(List<ZhiHu> datas) {
    this.datas = datas;
    notifyDataSetChanged();
  }

  @Override
  public ZhiHuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.layout_item_recycle, parent, false);
    ZhiHuViewHolder holder = new ZhiHuViewHolder(view);
    return holder;
  }

  @Override
  public void onBindViewHolder(ZhiHuViewHolder holder, int position) {

    ZhiHu zhiHu = datas.get(position);
    if (zhiHu != null) {
      holder.titltView.setText(zhiHu.title);
      holder.linkView.setText(zhiHu.link);
      holder.descView.setText(Html.fromHtml(zhiHu.des));
    }

  }

  @Override
  public int getItemCount() {
    return datas.size();
  }

  static class ZhiHuViewHolder extends RecyclerView.ViewHolder {

    public TextView titltView;
    public TextView linkView;
    public TextView descView;

    public ZhiHuViewHolder(View itemView) {
      super(itemView);
      titltView = itemView.findViewById(R.id.textView);
      linkView = itemView.findViewById(R.id.textView2);
      linkView.setAutoLinkMask(Linkify.WEB_URLS);
      descView = itemView.findViewById(R.id.webView);
      descView.setAutoLinkMask(Linkify.WEB_URLS);
    }

    public void bindTo(ZhiHu zhiHu) {
      titltView.setText(zhiHu.title);
      linkView.setText(zhiHu.link);
//      descView.setText(Html.fromHtml(zhiHu.des));
    }

    public void clear() {
      titltView.setText("");
      linkView.setText("");
      descView.setText("");
    }
  }

}
