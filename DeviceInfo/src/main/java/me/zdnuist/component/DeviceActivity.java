package me.zdnuist.component;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import me.zdnuist.component.entity.DeviceConstant;
import me.zdnuist.component.fragment.DeviceInfoFragment;

public class DeviceActivity extends AppCompatActivity implements LifecycleRegistryOwner{

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_device);

    if (savedInstanceState == null) {
      int info = 0;
      if(getIntent() != null){
        info = getIntent().getIntExtra(DeviceConstant.ARGS_INFO, 0);
      }
      DeviceInfoFragment fragment = new DeviceInfoFragment();
      Bundle args = new Bundle();
      args.putInt(DeviceConstant.ARGS_INFO, info);
      fragment.setArguments(args);
      getSupportFragmentManager().beginTransaction()
          .add(R.id.fragment_container, fragment, DeviceInfoFragment.TAG).commit();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
//    getMenuInflater().inflate(R.menu.menu_device, menu);
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
}
