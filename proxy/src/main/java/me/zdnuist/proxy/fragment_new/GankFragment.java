package me.zdnuist.proxy.fragment_new;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import me.zdnuist.module.fragmentation.BaseSupportFragment;
import me.zdnuist.proxy.R;
import me.zdnuist.proxy.WebViewActivity;
import me.zdnuist.proxy.entity.Gank;
import me.zdnuist.proxy.entity.Ganks;
import me.zdnuist.proxy.viewmodel.GankViewModel;

/**
 * Created by zd on 2017/12/13.
 */

public class GankFragment extends BaseSupportFragment {

  GankAdatper mAdapter;

  public static GankFragment newInstance(String type){
    Bundle args = new Bundle();
    args.putString("_type",type);
    GankFragment gankFragment = new GankFragment();
    gankFragment.setArguments(args);
    return gankFragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.layout_fragment_recycleview,container,false);
    initView(view);
    return view;
  }

  private void initView(View view){

    RecyclerView recyclerView = view.findViewById(R.id.id_recycleview);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    mAdapter = new GankAdatper(getContext());
    recyclerView.setAdapter(mAdapter);
  }


  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    String type = getArguments().getString("_type");
    ViewModelProviders.of(this).get(GankViewModel.class).queryGanks(type).observe(this,ganksObserver);
  }

  private Observer<Ganks> ganksObserver = new Observer<Ganks>() {
    @Override
    public void onChanged(@Nullable Ganks ganks) {
      if(ganks != null && ganks.results !=null){
        mAdapter.setNewData(ganks.results);
      }
    }
  };

  static class GankAdatper extends BaseQuickAdapter<Gank,BaseViewHolder>{

    private Context context;

    public GankAdatper(Context context) {
      super(R.layout.layout_gank_item);
      this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final Gank item) {

      helper.setText(R.id.id_desc,item.desc);
      helper.setText(R.id.id_tag , context.getString(R.string.str_tag_from,item.source));
      ImageView imageView = helper.getView(R.id.id_img);

      if(TextUtils.equals(item.type,"福利")){
        imageView.setVisibility(View.VISIBLE);
        Glide.with(context).load(item.url).into(imageView);

        return;
      }

      if(item.images!= null && item.images.size() > 0){
        imageView.setVisibility(View.VISIBLE);
        Glide.with(context).load(item.images.get(0)).into(imageView);
      }else{
        imageView.setVisibility(View.GONE);
      }

      helper.itemView.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(context, WebViewActivity.class);
          intent.putExtra("_url" , item.url);
          context.startActivity(intent);
        }
      });
    }
  }
}
