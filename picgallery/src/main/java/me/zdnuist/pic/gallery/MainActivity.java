package me.zdnuist.pic.gallery;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import me.zdnuist.pic.gallery.entity.ImgObj;
import me.zdnuist.pic.gallery.util.FileUtil;
import me.zdnuist.pic.gallery.viewmodel.PicViewModel;

public class MainActivity extends AppCompatActivity implements LifecycleRegistryOwner{

  private final static String TAG = "MainActivity";

  /**
   * The {@link android.support.v4.view.PagerAdapter} that will provide
   * fragments for each of the sections. We use a
   * {@link FragmentPagerAdapter} derivative, which will keep every
   * loaded fragment in memory. If this becomes too memory intensive, it
   * may be best to switch to a
   * {@link android.support.v4.app.FragmentStatePagerAdapter}.
   */
  private SectionsPagerAdapter mSectionsPagerAdapter;

  /**
   * The {@link ViewPager} that will host the section contents.
   */
  private ViewPager mViewPager;

  private PicViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

    // Set up the ViewPager with the sections adapter.
    mViewPager = (ViewPager) findViewById(R.id.container);
    mViewPager.setAdapter(mSectionsPagerAdapter);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "是否下载图片？", Snackbar.LENGTH_LONG)
            .setAction("是", new OnClickListener() {
              @Override
              public void onClick(View view) {
                final ImgObj obj = (ImgObj) ((SectionsPagerAdapter)mViewPager.getAdapter()).getItemObj(mViewPager.getCurrentItem());
                if(obj != null){
                  Glide.with(MainActivity.this).download(obj.downloadUrl).listener(
                      new RequestListener<File>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
                            Target<File> target,
                            boolean isFirstResource) {
                          return false;
                        }

                        @Override
                        public boolean onResourceReady(File resource, Object model,
                            Target<File> target,
                            DataSource dataSource, boolean isFirstResource) {
                          Log.d(TAG, "target:" + target.toString());
                          return false;
                        }
                      }).into(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(File resource,
                        Transition<? super File> transition) {
                      Log.d(TAG, "File:" + resource.getPath());
                      String targetName = obj.downloadUrl.substring(obj.downloadUrl.lastIndexOf("/")+1);
                      FileUtil.copyFile(MainActivity.this,resource,targetName);

                    }
                  });
                }
              }
            }).show();
      }
    });

    viewModel = ViewModelProviders.of(this).get(PicViewModel.class);
    viewModel.getObservableList().observe(this, new Observer<List<ImgObj>>() {
      @Override
      public void onChanged(@Nullable List<ImgObj> imgObjs) {
        if(imgObjs != null && imgObjs.size() > 0){
          mSectionsPagerAdapter.setImages(imgObjs);
        }
      }
    });
    mViewPager.addOnPageChangeListener(mOnPageChangeListener);

  }

  private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

      Log.d(TAG , "onPageSelected:" + position);
      if(position == mViewPager.getAdapter().getCount() - 5){
        viewModel.refresh();
      }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
  };

  @Override
  protected void onDestroy() {
    if(mOnPageChangeListener != null){
      mViewPager.removeOnPageChangeListener(mOnPageChangeListener);
    }
    super.onDestroy();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public LifecycleRegistry getLifecycle() {
    return mRegistry;
  }

  private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

  /**
   * A placeholder fragment containing a simple view.
   */
  public static class PlaceholderFragment extends LifecycleFragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_OBJ = "ARG_SECTION_OBJ";

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(ImgObj o) {
      PlaceholderFragment fragment = new PlaceholderFragment();
      Bundle args = new Bundle();
      args.putSerializable(ARG_SECTION_OBJ, o);
      fragment.setArguments(args);
      return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_main, container, false);
      ImageView imageView = (ImageView) rootView.findViewById(R.id.section_label);
      ImgObj imgObj = (ImgObj) getArguments().getSerializable(ARG_SECTION_OBJ);
      if(imgObj != null){
//        RequestOptions options = new RequestOptions();
//        options.
        Glide.with(this).load(imgObj.imageUrl)
            .thumbnail(Glide.with(this).load(imgObj.thumbnailUrl))
            .into(imageView);
      }
      return rootView;
    }


  }

  /**
   * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
   * one of the sections/tabs/pages.
   */
  public class SectionsPagerAdapter extends FragmentPagerAdapter {

    List<ImgObj> imgs = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    public void setImages(List<ImgObj> list){
      this.imgs.addAll(list);
      notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
      return PlaceholderFragment.newInstance(imgs.get(position));
    }

    @Override
    public int getCount() {
      return imgs.size();
    }

    public ImgObj getItemObj(int position){
      return imgs.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
      ImgObj o = imgs.get(position);
      return o.fromPageTitle;
    }
  }
}
