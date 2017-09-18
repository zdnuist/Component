package me.zdnuist.proxy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Arrays;
import java.util.List;
import me.zdnuist.module.fragmentation.BaseSupportFragment;
import me.zdnuist.proxy.R;

/**
 * Created by zd on 2017/9/18.
 */

public class ViewPagerFragment extends BaseSupportFragment {

  private ViewPager mViewPager;
  private FragmentPagerAdapter mAdapter;

  public static ViewPagerFragment newInstance(){
    ViewPagerFragment fragment = new ViewPagerFragment();
    return  fragment;
  }


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.layout_fragment_viewpager,container,false);
    initView(view);
    return view;
  }

  private void initView(View view){
    mViewPager = view.findViewById(R.id.viewPager);
    mAdapter = new MyAdapter(getChildFragmentManager(),Arrays.asList("FIRST PAGE" , "SECOND FAGE"));
    mViewPager.setAdapter(mAdapter);

  }


  static class MyAdapter extends FragmentPagerAdapter{

    private final List<String> datas;

    public MyAdapter(FragmentManager fm , List<String> datas) {
      super(fm);
      this.datas = datas;
    }

    @Override
    public Fragment getItem(int position) {
      if(position == 0){
        return RecycleViewFragment.newInstance();
      }
      return PageFragment.newInstance(datas.get(position));
    }

    @Override
    public int getCount() {
      return datas.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return datas.get(position);
    }
  }
}
