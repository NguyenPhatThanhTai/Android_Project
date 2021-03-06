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

import Category.Category;
import Dangnhap_Dangki.NguoiDung;
import comment.comment;
import danh_sach_tap_phim.Film_List;
import luu_phim.luu_phim;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import the_loai.TheLoai;
import tim_kiem.TimKiem;
import trang_chu.HighRate;
import trang_chu.RecommentForYou;

public class APIControllers {
    NguoiDung nguoidung = null;

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

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    Call post(String url, String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        return call;
    }

    //L???y to??n b??? phim
    public List<HighRate> getApiMovie() {
        List<HighRate> list = new ArrayList<>();
        System.out.println("BAT DAU TEST API...");
        JSONObject json = null;
        int num = 6;
        try {

            json = readJsonFromUrl("http://trongeddy48-001-site1.etempurl.com/api/movie");

            JSONArray jsonArray = json.getJSONArray("MovieList");

            if (jsonArray.length() < 6) {
                num = jsonArray.length();
            }

            for (int i = 0; i < num; i++) {
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    //L???y to??n b??? phim
    public List<RecommentForYou> getAllMovie() {
        List<RecommentForYou> list = new ArrayList<>();
        System.out.println("BAT DAU TEST API...");
        JSONObject json = null;
        try {
            json = readJsonFromUrl("http://trongeddy48-001-site1.etempurl.com/api/movie");

            JSONArray jsonArray = json.getJSONArray("MovieList");

            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new RecommentForYou(
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    //L???y phim ?????u ti??n theo url phim
    public String getUrlById(String id) {
        System.out.println("BAT DAU TEST API...");
        JSONObject json = null;
        String url = "";
        try {
            json = readJsonFromUrl("http://trongeddy48-001-site1.etempurl.com/api/FilmByMovieId?id=" + id);

            JSONArray jsonArray = json.getJSONArray("FilmList");

            System.out.println(jsonArray);
            url = jsonArray.getJSONObject(0).getString("URL");

            System.out.println(jsonArray);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return url;
    }

    //L???y danh s??ch t???p
    public List<Film_List> getListEp(String NameMovie, String id) {
        List<Film_List> list = new ArrayList<>();
        System.out.println("BAT DAU TEST API...");
        JSONObject json = null;
        try {

            json = readJsonFromUrl("http://trongeddy48-001-site1.etempurl.com/api/FilmByMovieId?id=" + id);

            JSONArray jsonArray = json.getJSONArray("FilmList");

            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new Film_List(NameMovie, jsonArray.getJSONObject(i).getString("URL"), jsonArray.getJSONObject(i).getString("Ep")));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    //T???o ph??ng
    public String createRoom() {
        JSONObject json = null;
        String roomId;

        try {
            json = readJsonFromUrl("http://trongeddy48-001-site1.etempurl.com/api/CreateRoom");

            JSONObject jsonObject = new JSONObject(json.toString());
            roomId = jsonObject.getString("RoomId");
            return roomId;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //Add nguoi dung vao phong
    public String addViewerToRoom(String roomId, String UserId) {
        JSONObject json = null;
        String check = "not ok";

        try {
            json = readJsonFromUrl("http://trongeddy48-001-site1.etempurl.com/api/AddUserToRoom?id=" + roomId + "&user=" + UserId);

            JSONObject jsonObject = new JSONObject(json.toString());
            check = jsonObject.getString("Room");

            return check;
        } catch (Exception ex) {
            ex.printStackTrace();
            return check;
        }
    }

    public NguoiDung DangKi(String username, String password, String fullname, String birthday,
                            String address, String phone, String email){
        String query_url = "http://trongeddy48-001-site1.etempurl.com/api/Register";
        String json = "{ \"username\" : \""+username+"\", " +
                "\"password\" : \""+password+"\", " +
                "\"fullname\" : \""+fullname+"\", " +
                "\"birthday\" : \""+birthday+"\", " +
                "\"address\" : \""+address+"\", " +
                "\"phone\" : \""+phone+"\", " +
                "\"email\" : \""+email+"\"}";
        try {
            Response response = post(query_url, json).execute();
            String responseStr = response.body().string();

            JSONObject jObject = new JSONObject(responseStr);
            String UserId = jObject.getJSONObject("message").getString("UserId");
            String Username = jObject.getJSONObject("message").getString("Username");
            String Password = jObject.getJSONObject("message").getString("Password");
            String FullName = jObject.getJSONObject("message").getString("FullName");
            String Birthday = jObject.getJSONObject("message").getString("Birthday");
            String Address = jObject.getJSONObject("message").getString("Address");
            String Phone = jObject.getJSONObject("message").getString("Phone");
            String Email = jObject.getJSONObject("message").getString("Email");
            String Avatar = jObject.getJSONObject("message").getString("Avatar");
            String Wallet = jObject.getJSONObject("message").getString("Wallet");

            nguoidung = new NguoiDung(UserId, Username, Password, FullName, Birthday, Address,
                    Phone, Email, Avatar, Wallet);

        } catch (Exception e) {
            nguoidung = null;
            e.printStackTrace();
        }

        return nguoidung;
    }

    public NguoiDung DangNhap(String username, String password) {
        String query_url = "http://trongeddy48-001-site1.etempurl.com/api/Login";
        String json = "{ \"username\" : \"" + username + "\", " +
                "       \"password\" : \"" + password + "\"}";
        try {
            Response response = post(query_url, json).execute();
            String responseStr = response.body().string();

            JSONObject jObject = new JSONObject(responseStr);
            String UserId = jObject.getJSONObject("message").getString("UserId");
            String Username = jObject.getJSONObject("message").getString("Username");
            String Password = jObject.getJSONObject("message").getString("Password");
            String FullName = jObject.getJSONObject("message").getString("FullName");
            String Birthday = jObject.getJSONObject("message").getString("Birthday");
            String Address = jObject.getJSONObject("message").getString("Address");
            String Phone = jObject.getJSONObject("message").getString("Phone");
            String Email = jObject.getJSONObject("message").getString("Email");
            String Avatar = jObject.getJSONObject("message").getString("Avatar");
            String Wallet = jObject.getJSONObject("message").getString("Wallet");

            nguoidung = new NguoiDung(UserId, Username, Password, FullName, Birthday, Address,
                    Phone, Email, Avatar, Wallet);

        } catch (Exception e) {
            nguoidung = null;
            e.printStackTrace();
        }

        return nguoidung;
    }

    public NguoiDung ThongTinCaNhan(String id) {
        System.out.println("BAT DAU TEST API...");
        JSONObject json = null;
        String url = "";
        try {
            String querry_json = "http://trongeddy48-001-site1.etempurl.com/api/User?id=" + id;
            json = readJsonFromUrl(querry_json);
            JSONObject jsonObject = new JSONObject(json.toString());
            String userid = jsonObject.getJSONObject("User").getString("UserId");
            String username = jsonObject.getJSONObject("User").getString("Username");
            String password = jsonObject.getJSONObject("User").getString("Password");
            String fullName = jsonObject.getJSONObject("User").getString("FullName");
            String birthday = jsonObject.getJSONObject("User").getString("Birthday");
            String address = jsonObject.getJSONObject("User").getString("Address");
            String phone = jsonObject.getJSONObject("User").getString("Phone");
            String email = jsonObject.getJSONObject("User").getString("Email");
            String avatar = jsonObject.getJSONObject("User").getString("Avatar");
            String wallet = jsonObject.getJSONObject("User").getString("Wallet");

            nguoidung = new NguoiDung(userid, username, password, fullName, birthday, address,
                    phone, email, avatar, wallet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return nguoidung;
    }

    public List<TimKiem> getTimKiem(String tk) {
        System.out.println("BAT DAU TEST API...");
        JSONObject json = null;
        String url = "";
        List<TimKiem> list = new ArrayList<>();
        try {
            json = readJsonFromUrl("http://trongeddy48-001-site1.etempurl.com/api/SearchFromMovie?search=" + tk);

            JSONArray jsonArray = json.getJSONArray("Messages");
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add(new TimKiem(
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
                return list;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<TheLoai> getTheLoai(String tl) {
        System.out.println("BAT DAU TEST API...");
        JSONObject json = null;
        String url = "";
        List<TheLoai> list = new ArrayList<>();
        try {
            json = readJsonFromUrl("http://trongeddy48-001-site1.etempurl.com/api/MovieByCate?id=" + tl);

            JSONArray jsonArray = json.getJSONArray("MovieList");
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new TheLoai(
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
            return list;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Category> getAllCate() {
        List<Category> list = new ArrayList<>();
        System.out.println("BAT DAU TEST API...");
        JSONObject json = null;
        try {
            json = readJsonFromUrl("http://trongeddy48-001-site1.etempurl.com/api/Category");

            JSONArray jsonArray = json.getJSONArray("CategoryList");

            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new Category(
                        jsonArray.getJSONObject(i).getString("Name"),
                        jsonArray.getJSONObject(i).getString("Icon"),
                        jsonArray.getJSONObject(i).getString("CategoryId")
                ));
            }

            System.out.println(jsonArray);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public Boolean LuuPhim(String movieId, String userId) {
        System.out.println("BAT DAU TEST API...");
        JSONObject json = null;
        String url = "";
        try {
            String querry_json = "http://trongeddy48-001-site1.etempurl.com/api/SaveMovie?movieId=" + movieId + "&userId="+ userId;
            json = readJsonFromUrl(querry_json);
            JSONObject jsonObject = new JSONObject(json.toString());
            String check = jsonObject.getString("message");

            if(check.equals("ok"))
            {
                return true;
            }
            else
            {
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Boolean CheckLuu(String movieId, String userId) {
        System.out.println("BAT DAU TEST API...");
        JSONObject json = null;
        String url = "";
        try {
            String querry_json = "http://trongeddy48-001-site1.etempurl.com/api/CheckSaved?movieId=" + movieId + "&userId="+ userId;
            json = readJsonFromUrl(querry_json);
            JSONObject jsonObject = new JSONObject(json.toString());
            String check = jsonObject.getString("message");

            if(check.equals("ok"))
            {
                return true;
            }
            else
            {
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Boolean HuyLuu(String movieId, String userId) {
        System.out.println("BAT DAU TEST API...");
        JSONObject json = null;
        String url = "";
        try {
            String querry_json = "http://trongeddy48-001-site1.etempurl.com/api/CancelSave?movieId=" + movieId + "&userId="+ userId;
            json = readJsonFromUrl(querry_json);
            JSONObject jsonObject = new JSONObject(json.toString());
            String check = jsonObject.getString("message");

            if(check.equals("ok"))
            {
                return true;
            }
            else
            {
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<luu_phim> getListSavedFilm(String id) {
        System.out.println("BAT DAU TEST API...");
        JSONObject json = null;
        String url = "";
        List<luu_phim> list = new ArrayList<>();
        try {
            json = readJsonFromUrl("http://trongeddy48-001-site1.etempurl.com/api/UserSavedFilm?id=" + id);

            JSONArray jsonArray = json.getJSONArray("ListSavedFilm");
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new luu_phim(
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
            return list;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Boolean Views(String movieId) {
        System.out.println("BAT DAU TEST API...");
        JSONObject json = null;
        String url = "";
        try {
            String querry_json = "http://trongeddy48-001-site1.etempurl.com/api/UpdateViews?id=" + movieId;
            json = readJsonFromUrl(querry_json);
            JSONObject jsonObject = new JSONObject(json.toString());
            String check = jsonObject.getString("message");

            if(check.equals("ok"))
            {
                return true;
            }
            else
            {
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String checkVersionUpdate(String currentVersion){
        System.out.println("BAT DAU TEST API...");
        JSONObject json = null;
        String urlUpdate = null;
        try {
            json = readJsonFromUrl("http://trongeddy48-001-site1.etempurl.com/api/verapp");
            JSONObject jsonObject = new JSONObject(json.toString());
            String newVersion = jsonObject.getJSONObject("Verapp").getString("Ver");
            System.out.println("=====================================" + newVersion);
            if(Integer.parseInt(currentVersion) < Integer.parseInt(newVersion)){
                urlUpdate = jsonObject.getJSONObject("Verapp").getString("URLVer");
            }
            return urlUpdate;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean addComment(String movieId, String Name, String Comment){
        System.out.println("BAT DAU TEST API...");
        JSONObject json = null;
        String url = "";
        try {
            String querry_json = "http://trongeddy48-001-site1.etempurl.com/api/AddComment?MovieId="+ movieId +"&GuestName="+ Name +"&Comment=" + Comment;
            json = readJsonFromUrl(querry_json);
            JSONObject jsonObject = new JSONObject(json.toString());
            String check = jsonObject.getString("message");

            System.out.println("Check tra ve la +++++++++++++++++" + check);

            if(check.equals("ok"))
            {
                return true;
            }
            else
            {
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<comment> getListComment(String id){
        System.out.println("BAT DAU TEST API...");
        JSONObject json = null;
        String url = "";
        List<comment> list = new ArrayList<>();
        try {
            json = readJsonFromUrl("http://trongeddy48-001-site1.etempurl.com/api/Comment?id=" + id);

            JSONArray jsonArray = json.getJSONArray("CommentList");
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new comment(
                        jsonArray.getJSONObject(i).getString("CommentId"),
                        jsonArray.getJSONObject(i).getString("GuestName"),
                        jsonArray.getJSONObject(i).getString("Comment1"),
                        jsonArray.getJSONObject(i).getString("Created"),
                        id
                ));
            }

            System.out.println(jsonArray);
            return list;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public NguoiDung getNguoidung(String id){
        System.out.println("BAT DAU TEST API...");
        JSONObject json = null;
        String url = "";
        try {
            String querry_json = "http://trongeddy48-001-site1.etempurl.com/api/User?id=" + id;
            json = readJsonFromUrl(querry_json);
            JSONObject jsonObject = new JSONObject(json.toString());
            NguoiDung nguoiDung = new NguoiDung(
                    jsonObject.getJSONObject("User").getString("UserId"),
                    jsonObject.getJSONObject("User").getString("Username"),
                    jsonObject.getJSONObject("User").getString("Password"),
                    jsonObject.getJSONObject("User").getString("FullName"),
                    jsonObject.getJSONObject("User").getString("Birthday"),
                    jsonObject.getJSONObject("User").getString("Address"),
                    jsonObject.getJSONObject("User").getString("Phone"),
                    jsonObject.getJSONObject("User").getString("Email"),
                    jsonObject.getJSONObject("User").getString("Avatar"),
                    jsonObject.getJSONObject("User").getString("Wallet"));

            return nguoiDung;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}