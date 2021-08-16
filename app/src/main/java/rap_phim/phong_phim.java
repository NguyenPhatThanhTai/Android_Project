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
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieandroidproject.R;

import java.util.ArrayList;
import java.util.List;

import API.APIControllers;
import danh_sach_tap_phim.Film_List;
import danh_sach_tap_phim.Film_list_Adapter;
import trang_chu.HighRate;

public class phong_phim extends Fragment {
    private WebView wv_view;
    private Thread thread;
    private String roomId, movieId, userId;

    public phong_phim(String roomId, String movieId, String userId){
        this.roomId = roomId;
        this.movieId = movieId;
        this.userId = userId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phong_phim, container, false);
        wv_view = view.findViewById(R.id.wv_view);
        goUrl("http://trongeddy48-001-site1.etempurl.com/Room/RoomMovie?id="+movieId+"&roomid="+roomId+"&userid="+userId);

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
}
