package me.zdnuist.proxy.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import me.zdnuist.module.fragmentation.BaseSupportFragment;
import me.zdnuist.proxy.R;
import me.zdnuist.proxy.adapter.MyRecycleAdapter;
import me.zdnuist.proxy.entity.ZhiHu;
import me.zdnuist.proxy.viewmodel.ZhiHuViewModel;

/**
 * Created by zd on 2017/9/18.
 */

public class RecycleViewFragment extends BaseSupportFragment {

  public static final String TAG = "RecycleViewFragment";

  public static RecycleViewFragment newInstance(){
    RecycleViewFragment fragment = new RecycleViewFragment();
    return fragment;
  }

  private RecyclerView mRecycleView;
  private MyRecycleAdapter mAdapter;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.layout_fragment_recycleview , container, false);
    initView(view);
    return view;
  }

  private void initView(View view){
    mRecycleView = view.findViewById(R.id.id_recycleview);
    mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
    mAdapter = new MyRecycleAdapter(getContext());
    mRecycleView.setAdapter(mAdapter);
    mRecycleView.addItemDecoration(new DividerItemDecoration(mRecycleView.getContext(),
        LinearLayoutManager.VERTICAL));
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    ZhiHuViewModel viewModel = ViewModelProviders.of(this).get(ZhiHuViewModel.class);
    Log.d(TAG, "zhihuViewModel:" + viewModel.hashCode());
    viewModel.getObservableList().observe(this, new Observer<List<ZhiHu>>() {
      @Override
      public void onChanged(@Nullable List<ZhiHu> zhiHus) {
        if(zhiHus != null){
            mAdapter.setDatas(zhiHus);
        }
      }
    });
  }
}
