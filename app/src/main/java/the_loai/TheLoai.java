package the_loai;

public class TheLoai {
    private String MovieId;
    private String Name;
    private String Views;
    private String Episodes;
    private String Years;
    private String Description;
    private String Thumbnails;
    private String Fee;

    public TheLoai(String movieId, String name, String views, String episodes, String years, String description, String thumbnails, String fee) {
        MovieId = movieId;
        Name = name;
        Views = views;
        Episodes = episodes;
        Years = years;
        Description = description;
        Thumbnails = thumbnails;
        Fee = fee;
    }

    public String getMovieId() {
        return MovieId;
    }

    public void setMovieId(String movieId) {
        MovieId = movieId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getViews() {
        return Views;
    }

    public void setViews(String views) {
        Views = views;
    }

    public String getEpisodes() {
        return Episodes;
    }

    public void setEpisodes(String episodes) {
        Episodes = episodes;
    }

    public String getYears() {
        return Years;
    }

    public void setYears(String years) {
        Years = years;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getThumbnails() {
        return Thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        Thumbnails = thumbnails;
    }

    public String getFee() {
        return Fee;
    }

    public void setFee(String fee) {
        Fee = fee;
    }
}
