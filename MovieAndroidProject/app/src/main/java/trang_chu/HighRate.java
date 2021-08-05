package trang_chu;

public class HighRate {
    private String AnhBia;
    private String Ten;
    private String TheLoai;
    private String ThoiLuong;
    private String MoTa;

    public HighRate(String anhBia, String ten, String theLoai, String thoiLuong, String moTa) {
        AnhBia = anhBia;
        Ten = ten;
        TheLoai = theLoai;
        ThoiLuong = thoiLuong;
        MoTa = moTa;
    }

    public String getAnhBia() {
        return AnhBia;
    }

    public void setAnhBia(String anhBia) {
        AnhBia = anhBia;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getTheLoai() {
        return TheLoai;
    }

    public void setTheLoai(String theLoai) {
        TheLoai = theLoai;
    }

    public String getThoiLuong() {
        return ThoiLuong;
    }

    public void setThoiLuong(String thoiLuong) {
        ThoiLuong = thoiLuong;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }
}
