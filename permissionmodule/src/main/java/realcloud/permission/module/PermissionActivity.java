package realcloud.permission.module;

import android.Manifest.permission;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.functions.Consumer;

public class PermissionActivity extends AppCompatActivity implements View.OnClickListener{

  RxPermissions rxPermissions;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_permission);
    findViewById(R.id.id_tv).setOnClickListener(this);

    rxPermissions = new RxPermissions(this);
  }

  @Override
  public void onClick(View v) {

    switch (v.getId()){
      case R.id.id_tv:
        rxPermissions
            .request(permission.READ_CONTACTS)
            .subscribe(new Consumer<Boolean>() {
              @Override
              public void accept(Boolean aBoolean) throws Exception {
                Log.e("zd" , "b:" + aBoolean);
              }
            })
        ;



        break;
    }
  }
}
