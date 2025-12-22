package vn.edu.hcmuaf.fit.pkcn.service.category;

import vn.edu.hcmuaf.fit.pkcn.dao.category.CategoryDao;

public class CategoryService {
    private CategoryDao categoryDao;
    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }
    public String getNameCategoryById(int categoryId) {
        return categoryDao.getNameCategoryById(categoryId);
    }
}
