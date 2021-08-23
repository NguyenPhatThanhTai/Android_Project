package Category;

public class Category {
    private String CategoryName;
    private String CategoryPicture;
    private String CategoryId;

    public Category(String categoryName, String categoryPicture, String categoryId) {
        CategoryName = categoryName;
        CategoryPicture = categoryPicture;
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryPicture() {
        return CategoryPicture;
    }

    public void setCategoryPicture(String categoryPicture) {
        CategoryPicture = categoryPicture;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }
}
