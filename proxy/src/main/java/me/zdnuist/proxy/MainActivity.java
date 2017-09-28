package me.zdnuist.proxy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import me.zdnuist.module.fragmentation.BaseFragmentWrapperActivity;
import me.zdnuist.module.fragmentation.BaseSupportFragment;
import me.zdnuist.proxy.fragment.FirstFragment;
import me.zdnuist.proxy.fragment.PageFragment;
import me.zdnuist.proxy.fragment.SecondFragment;
import me.zdnuist.proxy.fragment.ThirdFragment;
import me.zdnuist.proxy.fragment.ViewPagerFragment;

public class MainActivity extends BaseFragmentWrapperActivity {

  private static final String TAG = "MainActivity";

  public static final int FIRST = 0;
  public static final int SECOND = 1;
  public static final int THIRD = 2;

  private BaseSupportFragment[] mFragments = new BaseSupportFragment[3];


  int prePosition = FIRST;

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
      = new BottomNavigationView.OnNavigationItemSelectedListener() {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()) {
        case R.id.navigation_home:
          if(prePosition == FIRST){
            onReselected(prePosition);
          }else {
            showHideFragment(mFragments[FIRST], mFragments[prePosition]);
          }
          prePosition = FIRST;
          return true;
        case R.id.navigation_dashboard:
          if(prePosition == SECOND){
            onReselected(prePosition);
          }else {
            showHideFragment(mFragments[SECOND], mFragments[prePosition]);
          }
          prePosition = SECOND;
          return true;
        case R.id.navigation_notifications:
          if(prePosition == THIRD){
            onReselected(prePosition);
          }else{
            showHideFragment(mFragments[THIRD] , mFragments[prePosition]);
          }
          prePosition = THIRD;
          return true;
      }
      return false;
    }

  };

  private void onReselected(int position) {
    final BaseSupportFragment currentFragment = mFragments[position];
    int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();

    Log.d(TAG , "onReselected : positon-" + position + ",count -" + count);
    // 如果不在该类别Fragment的主页,则回到主页;
    if (count > 1) {
      if (currentFragment instanceof FirstFragment) {
        currentFragment.popToChild(ViewPagerFragment.class, false);
      } else if (currentFragment instanceof SecondFragment) {
        currentFragment.popToChild(PageFragment.class, false);
      } else if (currentFragment instanceof ThirdFragment) {
        currentFragment.popToChild(PageFragment.class, false);
        return;
      }

      // 这里推荐使用EventBus来实现 -> 解耦
      if (count == 1) {
        // 在FirstPagerFragment中接收, 因为是嵌套的孙子Fragment 所以用EventBus比较方便
        // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
      }
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    BaseSupportFragment firstFragment = findFragment(FirstFragment.class);
    if(firstFragment == null){
      mFragments[FIRST] = FirstFragment.newInstance();
      mFragments[SECOND] = SecondFragment.newInstance();
      mFragments[THIRD] = ThirdFragment.newInstance();
    }else{
      mFragments[FIRST] = firstFragment;
      mFragments[SECOND] = findFragment(SecondFragment.class);
      mFragments[THIRD] = findFragment(ThirdFragment.class);
    }

    loadMultipleRootFragment(R.id.content, FIRST ,mFragments[FIRST],mFragments[SECOND],mFragments[THIRD]);

    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
  }

}
