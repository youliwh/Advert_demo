package com.example.advert.service;

import com.example.advert.dao.CategoryRepository;
import com.example.advert.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 分类服务层
 */
@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    /**
     * 查询所有分类
     */
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    
    /**
     * 查询所有激活的分类
     */
    public List<Category> findAllActive() {
        return categoryRepository.findByIsActiveTrue();
    }
    
    /**
     * 根据ID查询分类
     */
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }
    
    /**
     * 根据名称查询分类
     */
    public List<Category> findByName(String name) {
        return categoryRepository.findByNameContainingIgnoreCaseAndIsActiveTrue(name);
    }
    
    /**
     * 查询顶级分类
     */
    public List<Category> findTopLevelCategories() {
        return categoryRepository.findTopLevelCategories();
    }
    
    /**
     * 查询子分类
     */
    public List<Category> findChildCategories(Long parentId) {
        return categoryRepository.findChildCategories(parentId);
    }
    
    /**
     * 获取分类树
     */
    public List<Category> getCategoryTree() {
        return categoryRepository.getCategoryTree();
    }
    
    /**
     * 保存分类
     */
    public Category save(Category category) {
        if (category.getId() == null) {
            // 新增时设置创建时间
            category.setCreatedTime(LocalDateTime.now());
        }
        category.setUpdatedTime(LocalDateTime.now());
        return categoryRepository.save(category);
    }
    
    /**
     * 更新分类
     */
    public Category update(Long id, Category category) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (existingCategory.isPresent()) {
            Category existing = existingCategory.get();
            existing.setName(category.getName());
            existing.setDescription(category.getDescription());
            existing.setParentId(category.getParentId());
            existing.setLevel(category.getLevel());
            existing.setSortOrder(category.getSortOrder());
            existing.setIsActive(category.getIsActive());
            existing.setUpdatedTime(LocalDateTime.now());
            return categoryRepository.save(existing);
        }
        return null;
    }
    
    /**
     * 根据ID删除分类（软删除）
     */
    public boolean deleteById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            Category existing = category.get();
            existing.setIsActive(false);
            existing.setUpdatedTime(LocalDateTime.now());
            categoryRepository.save(existing);
            return true;
        }
        return false;
    }
    
    /**
     * 激活分类
     */
    public boolean activateById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            Category existing = category.get();
            existing.setIsActive(true);
            existing.setUpdatedTime(LocalDateTime.now());
            categoryRepository.save(existing);
            return true;
        }
        return false;
    }
    
    /**
     * 获取分类统计信息
     */
    public Map<String, Object> getCategoryStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        // 总分类数
        long totalCategories = categoryRepository.count();
        statistics.put("totalCategories", totalCategories);
        
        // 激活分类数
        long activeCategories = categoryRepository.countByIsActiveTrue();
        statistics.put("activeCategories", activeCategories);
        
        // 顶级分类数
        long topLevelCategories = categoryRepository.countTopLevelCategories();
        statistics.put("topLevelCategories", topLevelCategories);
        
        // 按层级统计
        List<Object[]> levelStats = categoryRepository.countByLevel();
        Map<Integer, Long> levelStatistics = new HashMap<>();
        for (Object[] result : levelStats) {
            Integer level = (Integer) result[0];
            Long count = (Long) result[1];
            levelStatistics.put(level, count);
        }
        statistics.put("levelStats", levelStatistics);
        
        return statistics;
    }
} 