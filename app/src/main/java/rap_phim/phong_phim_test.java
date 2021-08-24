package rap_phim;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.movieandroidproject.R;

import com.example.movieandroidproject.IOnBackPressed;

public class phong_phim_test extends Fragment implements IOnBackPressed {
    String userId;
    private WebView wv_view;

    public phong_phim_test(String userId){
        this.userId = userId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phong_phim, container, false);
        wv_view = view.findViewById(R.id.wv_view);
        goUrl("http://trongeddy48-001-site1.etempurl.com/RoomMovie/Index?id=" + userId);

        return view;
    }

    private void goUrl(String addressBar)  {
        String url = addressBar.toString().trim();
        System.out.println("url phong o day" + url);
        if(url.isEmpty())  {
            Toast.makeText(getContext(),"Please enter url",Toast.LENGTH_SHORT).show();
            return;
        }
        wv_view.setWebViewClient(new WebViewClient());
        wv_view.setWebChromeClient(new MyChrome());
        wv_view.setWebContentsDebuggingEnabled(true);

        final WebSettings settings = wv_view.getSettings();

        settings.setLoadsImagesAutomatically(true);
        settings.setAllowContentAccess(true);
        settings.setBlockNetworkImage(false);
        settings.setAppCacheEnabled(true);
        settings.setSafeBrowsingEnabled(false);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setPluginState(WebSettings.PluginState.ON);
        wv_view.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        wv_view.loadUrl(url);

        wv_view.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(consoleMessage.message() + " Dòng thứ " + consoleMessage.lineNumber() + " thuộc " + consoleMessage.sourceId());
                    }
                });
                return true;
            }
        });
    }

    private class MyChrome extends WebChromeClient {

        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyChrome() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getContext().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)getActivity().getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getActivity().getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getActivity().getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getActivity().getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getActivity().getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getActivity().getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    @Override
    public boolean onBackPressed() {
        Fragment selectedFragment = new sanh_phim();
        FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
        manager.popBackStack();

        manager.beginTransaction()
                .setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,
                        R.anim.enter_right_to_left, R.anim.exit_right_to_left)
                .replace(R.id.fragment_container,
                        selectedFragment).commit();

        return true;
    }
}
