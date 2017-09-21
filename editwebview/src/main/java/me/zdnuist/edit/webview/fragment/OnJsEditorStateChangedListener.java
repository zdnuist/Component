package me.zdnuist.edit.webview.fragment;

import me.zdnuist.edit.webview.fragment.EditorFragmentAbstract.MediaType;
import org.json.JSONObject;

import java.util.Map;


public interface OnJsEditorStateChangedListener {
    void onDomLoaded();
    void onSelectionChanged(Map<String, String> selectionArgs);
    void onSelectionStyleChanged(Map<String, Boolean> changeSet);
    void onMediaTapped(String mediaId, MediaType mediaType, JSONObject meta, String uploadStatus);
    void onLinkTapped(String url, String title);
    void onMediaRemoved(String mediaId);
    void onMediaReplaced(String mediaId);
    void onVideoPressInfoRequested(String videoId);
    void onGetHtmlResponse(Map<String, String> responseArgs);
    void onActionFinished();
}
