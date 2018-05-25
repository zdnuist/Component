package me.zdnuist.component;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import me.zdnuist.component.entity.DeviceConstant;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    initView();
  }

  private void initView(){
    findViewById(R.id.id_func_1).setOnClickListener(this);
    findViewById(R.id.id_func_2).setOnClickListener(this);
    findViewById(R.id.id_func_3).setOnClickListener(this);
    findViewById(R.id.id_func_4).setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    int info = 0;
    switch (v.getId()){
      case R.id.id_func_1:
        info = 0;
        break;
      case R.id.id_func_2:
        info = 1;
        break;
      case R.id.id_func_3:
        info = 2;
        break;
      case R.id.id_func_4:
        info = 3;
        break;
    }

    Intent intent = new Intent(this, DeviceActivity.class);
    intent.putExtra(DeviceConstant.ARGS_INFO, info);
    startActivity(intent);
  }
}
