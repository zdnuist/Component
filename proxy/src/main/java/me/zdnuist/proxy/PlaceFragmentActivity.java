package me.zdnuist.proxy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import me.zdnuist.module.fragmentation.BaseFragmentWrapperActivity;
import me.zdnuist.proxy.fragment_new.GankFragment;

/**
 * Created by zd on 2017/12/13.
 */

public class PlaceFragmentActivity extends BaseFragmentWrapperActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_place);
    String type = getIntent().getStringExtra("_type");
    loadRootFragment(R.id.id_root,GankFragment.newInstance(type),false,false);
  }
}
