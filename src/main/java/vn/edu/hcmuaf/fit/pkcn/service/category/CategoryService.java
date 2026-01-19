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
    public boolean isCategoryParent(int categoryId) {
        return categoryDao.isCategoryParent(categoryId);
    }
    public List<Category> getSubCategories() {
        return categoryDao.getSubCategories();
    }
    public List<Category> getSubCategories(int parentId) {
        return categoryDao.getSubCategories(parentId);
    }

}
