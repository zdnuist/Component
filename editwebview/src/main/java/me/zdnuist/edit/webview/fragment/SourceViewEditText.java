package me.zdnuist.edit.webview.fragment;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;

/**
 * An EditText with support for {@link org.wordpress.android.editor.OnImeBackListener} and typeface setting
 * using a custom XML attribute.
 */
public class SourceViewEditText extends AppCompatEditText {

    private OnImeBackListener mOnImeBackListener;

    public SourceViewEditText(Context context) {
        super(context);
    }

    public SourceViewEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SourceViewEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (this.mOnImeBackListener != null) {
                this.mOnImeBackListener.onImeBack();
            }
        }
        return super.onKeyPreIme(keyCode, event);
    }

    public void setOnImeBackListener(OnImeBackListener listener) {
        this.mOnImeBackListener = listener;
    }
}
