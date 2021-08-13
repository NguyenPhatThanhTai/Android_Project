package API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import danh_sach_tap_phim.Film_List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import trang_chu.HighRate;

public class APIControllers {
    public static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    //Lấy toàn bộ phim
    public List<HighRate> getApiMovie(){
        List<HighRate> list = new ArrayList<>();
        System.out.println("BAT DAU TEST API...");
        JSONObject json = null;
        try {

            json = readJsonFromUrl("http://trongeddy48-001-site1.etempurl.com/api/movie");

            JSONArray jsonArray = json.getJSONArray("MovieList");

            for (int i = 0; i < jsonArray.length(); i ++){
                list.add(new HighRate(
                        jsonArray.getJSONObject(i).getString("MovieId"),
                        jsonArray.getJSONObject(i).getString("Name"),
                        jsonArray.getJSONObject(i).getString("Views"),
                        jsonArray.getJSONObject(i).getString("Episodes"),
                        jsonArray.getJSONObject(i).getString("Years"),
                        jsonArray.getJSONObject(i).getString("Description"),
                        jsonArray.getJSONObject(i).getString("Thumbnails"),
                        jsonArray.getJSONObject(i).getString("Fee")
                ));
            }

            System.out.println(jsonArray);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return list;
    }

    //Lấy phim đầu tiên theo url phim
    public String getUrlById(String id){
        System.out.println("BAT DAU TEST API...");
        JSONObject json = null;
        String url = "";
        try {

            json = readJsonFromUrl("http://trongeddy48-001-site1.etempurl.com/api/FilmByMovieId?id="+id);

            JSONArray jsonArray = json.getJSONArray("FilmList");

            System.out.println(jsonArray);
            url = jsonArray.getJSONObject(0).getString("URL");

            System.out.println(jsonArray);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return url;
    }

    //Lấy danh sách tập
    public List<Film_List> getListEp(String NameMovie, String id){
        List<Film_List> list = new ArrayList<>();
        System.out.println("BAT DAU TEST API...");
        JSONObject json = null;
        String url = "";
        try {

            json = readJsonFromUrl("http://trongeddy48-001-site1.etempurl.com/api/FilmByMovieId?id="+id);

            JSONArray jsonArray = json.getJSONArray("FilmList");

            for (int i = 0; i < jsonArray.length(); i ++){
                list.add(new Film_List(NameMovie, jsonArray.getJSONObject(i).getString("URL"), jsonArray.getJSONObject(i).getString("Ep")));
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return list;
    }
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    Call post(String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
    public void DangNhap(String username, String password){
        String query_url = "http://trongeddy48-001-site1.etempurl.com/api/Login";
        String json = "{ \"username\" : \""+username+"\", " +
                "       \"password\" : \""+password+"\"}";
        System.out.println(json);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        post(query_url, json, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseStr = response.body().string();

                    try {
                        JSONObject jObject = new JSONObject(responseStr);
                        String fullname = jObject.getJSONObject("message").getString("FullName");
                        System.out.println(fullname);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Do what you want to do with the response.
                } else {
                    // Request not successful
                }
            }

        });
    }
}
