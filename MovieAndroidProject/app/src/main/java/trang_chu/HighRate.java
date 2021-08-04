package trang_chu;

public class HighRate {
    private int imageId;
    private String tittle;

    public HighRate(int imageId, String tittle) {
        this.imageId = imageId;
        this.tittle = tittle;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }
}
