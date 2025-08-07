package com.example.advert.controller;

import com.example.advert.model.Category;
import com.example.advert.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 分类控制器
 */
@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    
    private final CategoryService categoryService;
    
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    /**
     * 获取所有分类
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }
    
    /**
     * 获取所有激活的分类
     */
    @GetMapping("/active")
    public ResponseEntity<List<Category>> getActiveCategories() {
        List<Category> categories = categoryService.findAllActive();
        return ResponseEntity.ok(categories);
    }
    
    /**
     * 根据ID获取分类
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.findById(id);
        return category.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * 根据名称搜索分类
     */
    @GetMapping("/search/name")
    public ResponseEntity<List<Category>> searchByName(@RequestParam String name) {
        List<Category> categories = categoryService.findByName(name);
        return ResponseEntity.ok(categories);
    }
    
    /**
     * 获取顶级分类
     */
    @GetMapping("/top-level")
    public ResponseEntity<List<Category>> getTopLevelCategories() {
        List<Category> categories = categoryService.findTopLevelCategories();
        return ResponseEntity.ok(categories);
    }
    
    /**
     * 获取子分类
     */
    @GetMapping("/{id}/children")
    public ResponseEntity<List<Category>> getChildCategories(@PathVariable Long id) {
        List<Category> categories = categoryService.findChildCategories(id);
        return ResponseEntity.ok(categories);
    }
    
    /**
     * 获取分类树
     */
    @GetMapping("/tree")
    public ResponseEntity<List<Category>> getCategoryTree() {
        List<Category> categories = categoryService.getCategoryTree();
        return ResponseEntity.ok(categories);
    }
    
    /**
     * 创建新分类
     */
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryService.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }
    
    /**
     * 更新分类
     */
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Category updatedCategory = categoryService.update(id, category);
        if (updatedCategory != null) {
            return ResponseEntity.ok(updatedCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * 删除分类（软删除）
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        boolean deleted = categoryService.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * 激活分类
     */
    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activateCategory(@PathVariable Long id) {
        boolean activated = categoryService.activateById(id);
        if (activated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * 获取分类统计信息
     */
    @GetMapping("/statistics")
    public ResponseEntity<Object> getCategoryStatistics() {
        return ResponseEntity.ok(categoryService.getCategoryStatistics());
    }
    
    /**
     * 健康检查接口
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Category Service is running!");
    }
} 