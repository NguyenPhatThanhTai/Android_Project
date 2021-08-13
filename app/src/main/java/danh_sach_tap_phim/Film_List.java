package danh_sach_tap_phim;

public class Film_List {
    private String Film_Name;
    private String Url;
    private String Ep;

    public Film_List(String film_Name, String url, String ep) {
        Film_Name = film_Name;
        Url = url;
        Ep = ep;
    }

    public String getFilm_Name() {
        return Film_Name;
    }

    public void setFilm_Name(String film_Name) {
        Film_Name = film_Name;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getEp() {
        return Ep;
    }

    public void setEp(String ep) {
        Ep = ep;
    }
}
