package Dangnhap_Dangki;

public class NguoiDung {
    private String UserId;
    private String Username;
    private String Password;
    private String FullName;
    private String Birthday;
    private String Address;
    private String Phone;
    private String Email;
    private String Avatar;
    private String Wallet;

    public NguoiDung() {
    }

    public NguoiDung(String userId, String username, String password, String fullName, String birthday, String address, String phone, String email, String avatar, String wallet) {
        UserId = userId;
        Username = username;
        Password = password;
        FullName = fullName;
        Birthday = birthday;
        Address = address;
        Phone = phone;
        Email = email;
        Avatar = avatar;
        Wallet = wallet;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getWallet() {
        return Wallet;
    }

    public void setWallet(String wallet) {
        Wallet = wallet;
    }
}
