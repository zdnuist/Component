package me.zdnuist.proxy.fragment_new;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import me.zdnuist.module.fragmentation.BaseSupportFragment;
import me.zdnuist.proxy.PlaceFragmentActivity;
import me.zdnuist.proxy.R;
import me.zdnuist.proxy.viewmodel.GankViewModel;

/**
 * Created by zd on 2017/12/13.
 */

public class BoradFragment extends BaseSupportFragment implements View.OnClickListener{

  private TextView aTv, fTv, iTv;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.layout_board, container, false);
    initView(view);
    return view;
  }

  private void initView(View view) {
    aTv = view.findViewById(R.id.id_tv_a);
    fTv = view.findViewById(R.id.id_tv_f);
    iTv = view.findViewById(R.id.id_tv_i);

    aTv.setOnClickListener(this);
    fTv.setOnClickListener(this);
    iTv.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    String type = null;
    switch (v.getId()){
      case R.id.id_tv_a:
        type = GankViewModel.TYPE_ANDROID;
        break;
      case R.id.id_tv_f:
        type = GankViewModel.TYPE_FULI;
        break;
      case R.id.id_tv_i:
        type = GankViewModel.TYPE_IOS;
        break;
    }

    if(type != null){
      Intent intent = new Intent(getActivity(), PlaceFragmentActivity.class);
      intent.putExtra("_type" , type);
      getActivity().startActivity(intent);
    }
  }
}
