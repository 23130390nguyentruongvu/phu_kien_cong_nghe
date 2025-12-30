package vn.edu.hcmuaf.fit.pkcn.service.category;

import vn.edu.hcmuaf.fit.pkcn.dao.category.CategoryDao;
import vn.edu.hcmuaf.fit.pkcn.model.category.Category;

import java.util.List;

public class CategoryService {
    private CategoryDao categoryDao;
    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }
    public String getNameCategoryById(int categoryId) {
        return categoryDao.getNameCategoryById(categoryId);
    }
    public List<Category> getParentCategories() {
        return categoryDao.getParentCategories();
    }
}
