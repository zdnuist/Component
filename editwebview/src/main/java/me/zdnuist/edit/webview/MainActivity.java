package me.zdnuist.edit.webview;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import java.util.ArrayList;
import me.zdnuist.edit.webview.fragment.EditorFragment;
import me.zdnuist.edit.webview.fragment.EditorFragmentAbstract.EditorDragAndDropListener;
import me.zdnuist.edit.webview.fragment.EditorFragmentAbstract.EditorFragmentListener;
import me.zdnuist.edit.webview.fragment.EditorFragmentAbstract.TrackableEvent;

public class MainActivity extends AppCompatActivity implements EditorFragmentListener,EditorDragAndDropListener {

  private final static String TAG = "MainActivity";

  WebView mWebview;
  Button startBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_main);
//    initView();

    if (savedInstanceState == null) {
      EditorFragment fragment = EditorFragment.newInstance("123","345");

      getFragmentManager().beginTransaction()
          .add(R.id.fragment_container, fragment, "TAG").commit();
    }
  }

  private void initView(){
    mWebview = findViewById(R.id.id_webview);
    startBtn = findViewById(R.id.id_btn);
    startBtn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        mWebview.loadUrl("file:///android_asset/editor.html");
      }
    });

  }

  @Override
  public void onEditorFragmentInitialized() {

  }

  @Override
  public void onSettingsClicked() {

  }

  @Override
  public void onAddMediaClicked() {

  }

  @Override
  public boolean onMediaRetryClicked(String mediaId) {
    return false;
  }

  @Override
  public void onMediaUploadCancelClicked(String mediaId) {

  }

  @Override
  public void onMediaDeleted(String mediaId) {

  }

  @Override
  public void onUndoMediaCheck(String undoedContent) {

  }

  @Override
  public void onFeaturedImageChanged(long mediaId) {

  }

  @Override
  public void onVideoPressInfoRequested(String videoId) {

  }

  @Override
  public String onAuthHeaderRequested(String url) {
    return null;
  }

  @Override
  public void onTrackableEvent(TrackableEvent event) {

  }

  @Override
  public void onMediaDropped(ArrayList<Uri> mediaUri) {

  }

  @Override
  public void onRequestDragAndDropPermissions(DragEvent dragEvent) {

  }
}
