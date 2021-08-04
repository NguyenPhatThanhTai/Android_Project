package trang_chu;

public class RecommentForYou {
    private int Recomment_Image;
    private String Tittle;

    public RecommentForYou(int recomment_Image, String tittle) {
        Recomment_Image = recomment_Image;
        Tittle = tittle;
    }

    public int getRecomment_Image() {
        return Recomment_Image;
    }

    public void setRecomment_Image(int recomment_Image) {
        Recomment_Image = recomment_Image;
    }

    public String getTittle() {
        return Tittle;
    }

    public void setTittle(String tittle) {
        Tittle = tittle;
    }
}
