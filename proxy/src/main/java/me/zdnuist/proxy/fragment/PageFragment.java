package me.zdnuist.proxy.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import java.util.List;
import me.zdnuist.module.fragmentation.BaseSupportFragment;
import me.zdnuist.proxy.R;
import me.zdnuist.proxy.entity.ZhiHu;
import me.zdnuist.proxy.viewmodel.ZhiHuViewModel;

/**
 * Created by zd on 2017/9/18.
 */

public class PageFragment extends BaseSupportFragment {

  private final static String TAG = "PageFragment";

  private String mTitle;

  public static PageFragment newInstance(String text){
    Bundle args = new Bundle();
    args.putString(TAG , text);
    PageFragment fragment = new PageFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mTitle = getArguments().getString(TAG);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.layout_radio_group,container,false);
    initView(view);
    return view;
  }

  private void initView(View view) {
//    TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
//    tvTitle.setText(mTitle);

    RadioGroup radioGroup = view.findViewById(R.id.id_radio_group);
    radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        Log.d("zd" , "checkId");
      }
    });
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }
}
