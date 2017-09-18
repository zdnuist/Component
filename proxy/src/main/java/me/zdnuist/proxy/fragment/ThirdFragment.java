package me.zdnuist.proxy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.zdnuist.module.fragmentation.BaseSupportFragment;
import me.zdnuist.proxy.R;

/**
 * Created by zd on 2017/9/18.
 */

public class ThirdFragment  extends BaseSupportFragment{

  public static ThirdFragment newInstance(){
    ThirdFragment fragment = new ThirdFragment();
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.layout_fragment_first, container, false);
    return view;
  }

  @Override
  public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);

    if (findChildFragment(PageFragment.class) == null) {
      loadRootFragment(R.id.fl_first_container, PageFragment.newInstance("THIRD PRAGMENT"));
    }
  }
}
