package me.zdnuist.proxy.fragment_new;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;
import me.zdnuist.module.fragmentation.BaseSupportFragment;
import me.zdnuist.proxy.R;
import me.zdnuist.proxy.adapter.ZhihuAdapter;
import me.zdnuist.proxy.entity.ZhiHu;
import me.zdnuist.proxy.viewmodel.ZhiHuViewModel;

/**
 * Created by zd on 2017/9/22.
 */

public class PageListFragment extends BaseSupportFragment {

  public static final String TAG = "PageListFragment";

  public static PageListFragment newInstance() {
    PageListFragment fragment = new PageListFragment();
    return fragment;
  }

  private RecyclerView mRecycleView;
  private ZhihuAdapter mAdapter;
  private Animator spruceAnimator;

  private boolean mLastItemVisible;

  private boolean isLoading;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.layout_fragment_recycleview, container, false);
    initView(view);
    return view;
  }

  private void initView(View view) {
    mRecycleView = view.findViewById(R.id.id_recycleview);
    mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()) {
      @Override
      public void onLayoutChildren(Recycler recycler, State state) {
        super.onLayoutChildren(recycler, state);
        initSpruce();
      }
    });
    mAdapter = new ZhihuAdapter();
//    mRecycleView.setAdapter(mAdapter);
    mRecycleView.addItemDecoration(new DividerItemDecoration(mRecycleView.getContext(),
        LinearLayoutManager.VERTICAL));
    mRecycleView.setAdapter(mAdapter);
  }

  @Override
  public void onResume() {
    super.onResume();
    if (spruceAnimator != null) {
      spruceAnimator.start();
    }
  }

  private void initSpruce() {
    if (mRecycleView.getChildCount() == 0) {
      return;
    }

    spruceAnimator = new Spruce.SpruceBuilder(mRecycleView)
        .sortWith(new DefaultSort(100))
        .animateWith(DefaultAnimations.shrinkAnimator(mRecycleView, 800),
            ObjectAnimator.ofFloat(mRecycleView, "translationX", -mRecycleView.getWidth(), 0f)
                .setDuration(800))
        .start();

    mRecycleView.addOnScrollListener(new OnScrollListener() {
      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

        if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastItemVisible && !isLoading) {
          Log.d(TAG, "isLoading");
          isLoading = true;
          viewModel.fetchDatas();
        }

      }

      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int totalItemCount = mRecycleView.getLayoutManager().getItemCount();
        mLastItemVisible = (totalItemCount > 0) && (getLastVisiblePosition() >= totalItemCount - 1);
      }
    });
  }


  public int getLastVisiblePosition() {
    int lastPosition = mRecycleView.getLayoutManager().getItemCount() - 1;
    RecyclerView.LayoutManager layoutManager = mRecycleView.getLayoutManager();
    if (layoutManager instanceof GridLayoutManager) {
      //通过LayoutManager找到当前显示的最后的item的position
      lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
    } else if (layoutManager instanceof LinearLayoutManager) {
      lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
      //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
      //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
      int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
      ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
      lastPosition = findMax(lastPositions);
    }
    return lastPosition;
  }

  private int findMax(int[] lastPositions) {
    int max = lastPositions[0];
    for (int value : lastPositions) {
      if (value > max) {
        max = value;
      }
    }
    return max;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    viewModel = ViewModelProviders.of(this).get(ZhiHuViewModel.class);
    Log.d(TAG, "zhihuViewModel:" + viewModel.hashCode());

    viewModel.getObservablePageList().observe(this, new Observer<PagedList<ZhiHu>>() {
      @Override
      public void onChanged(@Nullable PagedList<ZhiHu> zhiHus) {
        mAdapter.setList(zhiHus);
        isLoading = false;
      }
    });

  }

  ZhiHuViewModel viewModel;
}
