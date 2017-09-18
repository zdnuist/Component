package me.zdnuist.module.fragmentation;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivityDelegate;
import me.yokeyword.fragmentation.SupportHelper;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by zd on 2017/9/18.
 */

public class BaseFragmentWrapperActivity extends AppCompatActivity implements ISupportActivity {

  final SupportActivityDelegate mDelegate = new SupportActivityDelegate(this);


  @Override
  public SupportActivityDelegate getSupportDelegate() {
    return mDelegate;
  }

  @Override
  public ExtraTransaction extraTransaction() {
    return mDelegate.extraTransaction();
  }

  @Override
  public FragmentAnimator getFragmentAnimator() {
    return mDelegate.getFragmentAnimator();
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mDelegate.onCreate(savedInstanceState);
  }

  @Override
  protected void onPostCreate(@Nullable Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    mDelegate.onPostCreate(savedInstanceState);
  }

  @Override
  protected void onDestroy() {
    mDelegate.onDestroy();
    super.onDestroy();
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
  }


  @Override
  public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
    mDelegate.setFragmentAnimator(fragmentAnimator);
  }

  @Override
  public FragmentAnimator onCreateFragmentAnimator() {
    return mDelegate.onCreateFragmentAnimator();
  }

  @Override
  public void onBackPressedSupport() {
    mDelegate.onBackPressedSupport();
  }

  /****************************************以下为可选方法(Optional methods)******************************************************/

  /**
   * 加载根Fragment, 即Activity内的第一个Fragment 或 Fragment内的第一个子Fragment
   *
   * @param containerId 容器id
   * @param toFragment  目标Fragment
   */
  public void loadRootFragment(int containerId, @NonNull ISupportFragment toFragment) {
    mDelegate.loadRootFragment(containerId, toFragment);
  }

  public void loadRootFragment(int containerId, ISupportFragment toFragment, boolean addToBackStack, boolean allowAnimation) {
    mDelegate.loadRootFragment(containerId, toFragment, addToBackStack, allowAnimation);
  }

  /**
   * 加载多个同级根Fragment,类似Wechat, QQ主页的场景
   */
  public void loadMultipleRootFragment(int containerId, int showPosition, ISupportFragment... toFragments) {
    mDelegate.loadMultipleRootFragment(containerId, showPosition, toFragments);
  }

  /**
   * show一个Fragment,hide其他同栈所有Fragment
   * 使用该方法时，要确保同级栈内无多余的Fragment,(只有通过loadMultipleRootFragment()载入的Fragment)
   * <p>
   * 建议使用更明确的{@link #showHideFragment(ISupportFragment, ISupportFragment)}
   *
   * @param showFragment 需要show的Fragment
   */
  public void showHideFragment(ISupportFragment showFragment) {
    mDelegate.showHideFragment(showFragment);
  }

  /**
   * show一个Fragment,hide一个Fragment ; 主要用于类似微信主页那种 切换tab的情况
   */
  public void showHideFragment(ISupportFragment showFragment, ISupportFragment hideFragment) {
    mDelegate.showHideFragment(showFragment, hideFragment);
  }

  /**
   * It is recommended to use {@link SupportFragment#start(ISupportFragment)}.
   */
  public void start(ISupportFragment toFragment) {
    mDelegate.start(toFragment);
  }

  /**
   * It is recommended to use {@link SupportFragment#start(ISupportFragment, int)}.
   *
   * @param launchMode Similar to Activity's LaunchMode.
   */
  public void start(ISupportFragment toFragment, @ISupportFragment.LaunchMode int launchMode) {
    mDelegate.start(toFragment, launchMode);
  }

  /**
   * It is recommended to use {@link SupportFragment#startForResult(ISupportFragment, int)}.
   * Launch an fragment for which you would like a result when it poped.
   */
  public void startForResult(ISupportFragment toFragment, int requestCode) {
    mDelegate.startForResult(toFragment, requestCode);
  }

  /**
   * It is recommended to use {@link SupportFragment#startWithPop(ISupportFragment)}.
   * Launch a fragment while poping self.
   */
  public void startWithPop(ISupportFragment toFragment) {
    mDelegate.startWithPop(toFragment);
  }

  /**
   * It is recommended to use {@link SupportFragment#replaceFragment(ISupportFragment, boolean)}.
   */
  public void replaceFragment(ISupportFragment toFragment, boolean addToBackStack) {
    mDelegate.replaceFragment(toFragment, addToBackStack);
  }

  /**
   * Pop the fragment.
   */
  public void pop() {
    mDelegate.pop();
  }

  /**
   * Pop the last fragment transition from the manager's fragment
   * back stack.
   * <p>
   * 出栈到目标fragment
   *
   * @param targetFragmentClass   目标fragment
   * @param includeTargetFragment 是否包含该fragment
   */
  public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment) {
    mDelegate.popTo(targetFragmentClass, includeTargetFragment);
  }

  /**
   * If you want to begin another FragmentTransaction immediately after popTo(), use this method.
   * 如果你想在出栈后, 立刻进行FragmentTransaction操作，请使用该方法
   */
  public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {
    mDelegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable);
  }

  public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, int popAnim) {
    mDelegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable, popAnim);
  }

  /**
   * 当Fragment根布局 没有 设定background属性时,
   * Fragmentation默认使用Theme的android:windowbackground作为Fragment的背景,
   * 可以通过该方法改变其内所有Fragment的默认背景。
   */
  public void setDefaultFragmentBackground(@DrawableRes int backgroundRes) {
    mDelegate.setDefaultFragmentBackground(backgroundRes);
  }

  /**
   * 得到位于栈顶Fragment
   */
  public ISupportFragment getTopFragment() {
    return SupportHelper.getTopFragment(getSupportFragmentManager());
  }

  /**
   * 获取栈内的fragment对象
   */
  public <T extends ISupportFragment> T findFragment(Class<T> fragmentClass) {
    return SupportHelper.findFragment(getSupportFragmentManager(), fragmentClass);
  }
}
