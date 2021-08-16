package rap_phim;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
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

        wv_view.getSettings().setLoadsImagesAutomatically(true);
        wv_view.getSettings().setJavaScriptEnabled(true);
        wv_view.getSettings().setAllowFileAccess(true);
        wv_view.getSettings().setDomStorageEnabled(true);
        wv_view.getSettings().setMediaPlaybackRequiresUserGesture(false);
        wv_view.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv_view.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv_view.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        wv_view.loadUrl(url);

        wv_view.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                android.util.Log.d("===============================", consoleMessage.message());
                return true;
            }
        });
    }
}
