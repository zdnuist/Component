package me.zdnuist.component.fragment;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import me.zdnuist.component.R;
import me.zdnuist.component.entity.DeviceConstant;
import me.zdnuist.component.entity.DeviceInfo;
import me.zdnuist.component.entity.NetworkInfo;
import me.zdnuist.component.viewmodel.DeviceInfoViewModel;

/**
 * Created by zd on 2017/9/12.
 */

public class DeviceInfoFragment extends LifecycleFragment {

  public static final String TAG = "DeviceInfoFragment";

  private TextView devieInfoShow ;

  ClipboardManager clipboardManager;

  private SensorManager sensorManager;


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view  = inflater.inflate(R.layout.fragment_device, container ,false);
    Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
    ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

    clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

    FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, devieInfoShow.getText().toString()));
        Snackbar.make(view, "已复制到剪切板中", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();

        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true);

        int[] attribute = new int[] { android.R.attr.actionBarSize };
        TypedArray array = getContext().obtainStyledAttributes(typedValue.resourceId, attribute);
        int size = array.getDimensionPixelSize(0,-1);
        array.recycle();

        Log.e(TAG,"actionBarSize:" + typedValue.toString());

      }
    });

    devieInfoShow = view.findViewById(R.id.id_device_info_show);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    DeviceInfoViewModel viewModel = ViewModelProviders.of(this).get(DeviceInfoViewModel.class);

    subscribeToModel(viewModel);
  }

  private SensorEventListener listener = new SensorEventListener() {
    @Override
    public void onSensorChanged(SensorEvent event) {
      //提示当前光照强度
      devieInfoShow.setText("当前光照强度：" + event.values[0] + "勒克斯");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
  };

  @Override
  public void onDestroy() {
    if(sensorManager != null){
      sensorManager.unregisterListener(listener);
    }
    super.onDestroy();
  }

  private void subscribeToModel(final DeviceInfoViewModel model) {

    int info = getArguments().getInt(DeviceConstant.ARGS_INFO);

    switch (info){
      case DeviceConstant.INFO_LIGHT:
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        break;
      case DeviceConstant.INFO_NETWORK:
        model.getNetworkInfo().observe(this, new Observer<NetworkInfo>() {
          @Override
          public void onChanged(@Nullable NetworkInfo networkInfo) {
            devieInfoShow.setText(getString(R.string.str_network_info, networkInfo.toString()));
          }
        });
        break;
      case DeviceConstant.INFO_LOCAL_NETWORK:
        model.getLocalNetwork().observe(this, new Observer<String>() {
          @Override
          public void onChanged(@Nullable String s) {
            devieInfoShow.setText(s);
          }
        });
        break;
      default:
        model.getDeviceInfo().observe(this, new Observer<DeviceInfo>() {
          @Override
          public void onChanged(@Nullable DeviceInfo deviceInfo) {
            devieInfoShow.setText(getString(R.string.str_device_info , deviceInfo.toString()));
          }
        });
        break;
    }

  }
}
