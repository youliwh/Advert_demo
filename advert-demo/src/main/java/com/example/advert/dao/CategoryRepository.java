package com.example.advert.dao;

import com.example.advert.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分类数据访问层
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    /**
     * 查找所有激活的分类
     */
    List<Category> findByIsActiveTrue();
    
    /**
     * 根据父级ID查找子分类
     */
    List<Category> findByParentId(Long parentId);
    
    /**
     * 查找顶级分类（parentId为null）
     */
    List<Category> findByParentIdIsNull();
    
    /**
     * 根据级别查找分类
     */
    List<Category> findByLevel(Integer level);
    
    /**
     * 根据名称模糊查询
     */
    List<Category> findByNameContainingIgnoreCase(String name);
    
    /**
     * 根据激活状态查找分类
     */
    List<Category> findByIsActive(Boolean isActive);
    
    /**
     * 根据排序查找分类
     */
    List<Category> findByIsActiveTrueOrderBySortOrderAsc();
    
    /**
     * 查找指定父级下的激活分类
     */
    List<Category> findByParentIdAndIsActiveTrueOrderBySortOrderAsc(Long parentId);
    
    /**
     * 统计各级别的分类数量
     */
    @Query("SELECT c.level, COUNT(c) FROM Category c WHERE c.isActive = true GROUP BY c.level ORDER BY c.level")
    List<Object[]> countCategoriesByLevel();
    
    /**
     * 查找分类树（包含子分类数量）
     */
    @Query("SELECT c, (SELECT COUNT(sc) FROM Category sc WHERE sc.parentId = c.id AND sc.isActive = true) " +
           "FROM Category c WHERE c.parentId IS NULL AND c.isActive = true ORDER BY c.sortOrder")
    List<Object[]> findCategoryTree();
    
    /**
     * 查找指定分类的所有子分类（递归）
     */
    @Query("SELECT c FROM Category c WHERE c.parentId = :parentId AND c.isActive = true ORDER BY c.sortOrder")
    List<Category> findChildrenByParentId(@Param("parentId") Long parentId);
    
    /**
     * 查找分类路径（从根到指定分类）
     */
    @Query(value = "WITH RECURSIVE category_path AS (" +
           "  SELECT id, name, parent_id, level, 1 as path_level " +
           "  FROM categories WHERE id = :categoryId " +
           "  UNION ALL " +
           "  SELECT c.id, c.name, c.parent_id, c.level, cp.path_level + 1 " +
           "  FROM categories c " +
           "  INNER JOIN category_path cp ON c.id = cp.parent_id " +
           ") " +
           "SELECT * FROM category_path ORDER BY path_level DESC", nativeQuery = true)
    List<Object[]> findCategoryPath(@Param("categoryId") Long categoryId);
    
    /**
     * 统计各分类下的广告数量
     */
    @Query("SELECT c.name, COUNT(a) FROM Category c " +
           "LEFT JOIN Advert a ON c.id = a.categoryId AND a.isActive = true " +
           "WHERE c.isActive = true " +
           "GROUP BY c.id, c.name " +
           "ORDER BY COUNT(a) DESC")
    List<Object[]> countAdvertsByCategory();
    
    /**
     * 查找热门分类（广告数量最多的分类）
     */
    @Query("SELECT c FROM Category c " +
           "WHERE c.isActive = true AND c.id IN (" +
           "  SELECT a.categoryId FROM Advert a " +
           "  WHERE a.isActive = true " +
           "  GROUP BY a.categoryId " +
           "  ORDER BY COUNT(a) DESC" +
           ")")
    List<Category> findPopularCategories();
    
    /**
     * 查找指定分类的所有父级分类
     */
    @Query(value = "WITH RECURSIVE parent_categories AS (" +
           "  SELECT id, name, parent_id, level " +
           "  FROM categories WHERE id = :categoryId " +
           "  UNION ALL " +
           "  SELECT c.id, c.name, c.parent_id, c.level " +
           "  FROM categories c " +
           "  INNER JOIN parent_categories pc ON c.id = pc.parent_id " +
           ") " +
           "SELECT * FROM parent_categories WHERE id != :categoryId ORDER BY level", nativeQuery = true)
    List<Object[]> findParentCategories(@Param("categoryId") Long categoryId);
    
    /**
     * 查找分类的完整路径字符串
     */
    @Query(value = "SELECT c.name, " +
           "(SELECT STRING_AGG(pc.name, ' > ' ORDER BY pc.level) " +
           " FROM categories pc " +
           " WHERE pc.id IN (" +
           "   WITH RECURSIVE category_ancestors AS (" +
           "     SELECT id, parent_id, level FROM categories WHERE id = c.id " +
           "     UNION ALL " +
           "     SELECT ca.id, ca.parent_id, ca.level " +
           "     FROM categories ca " +
           "     INNER JOIN category_ancestors cpa ON ca.id = cpa.parent_id " +
           "   ) SELECT id FROM category_ancestors WHERE id != c.id" +
           " ))" +
           "FROM categories c WHERE c.is_active = true", nativeQuery = true)
    List<Object[]> findCategoryFullPaths();
    
    /**
     * 根据名称模糊查询激活分类
     */
    List<Category> findByNameContainingIgnoreCaseAndIsActiveTrue(String name);
    
    /**
     * 统计激活分类数量
     */
    Long countByIsActiveTrue();
    
    /**
     * 按层级统计分类数量
     */
    @Query("SELECT c.level, COUNT(c) FROM Category c GROUP BY c.level ORDER BY c.level")
    List<Object[]> countByLevel();
    
    /**
     * 统计parentId为null的分类数量
     */
    Long countByParentIdIsNull();
    
    /**
     * 查找顶级分类 - 默认实现
     */
    default List<Category> findTopLevelCategories() {
        return findByParentIdIsNull();
    }
    
    /**
     * 查找子分类 - 默认实现
     */
    default List<Category> findChildCategories(Long parentId) {
        return findByParentIdAndIsActiveTrueOrderBySortOrderAsc(parentId);
    }
    
    /**
     * 获取分类树 - 默认实现
     */
    default List<Category> getCategoryTree() {
        return findByIsActiveTrueOrderBySortOrderAsc();
    }
    
    /**
     * 统计顶级分类数量 - 默认实现
     */
    default Long countTopLevelCategories() {
        return countByParentIdIsNull();
    }
} 