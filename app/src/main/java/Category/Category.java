package Category;

public class Category {
    private String CategoryName;
    private Integer CategoryPicture;

    public Category(String categoryName, Integer categoryPicture) {
        CategoryName = categoryName;
        CategoryPicture = categoryPicture;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public Integer getCategoryPicture() {
        return CategoryPicture;
    }

    public void setCategoryPicture(Integer categoryPicture) {
        CategoryPicture = categoryPicture;
    }
}
