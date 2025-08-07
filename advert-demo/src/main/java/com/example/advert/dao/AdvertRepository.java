package com.example.advert.dao;

import com.example.advert.model.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 广告数据访问层
 */
@Repository
public interface AdvertRepository extends JpaRepository<Advert, Long> {
    
    /**
     * 根据标题模糊查询
     */
    List<Advert> findByTitleContainingIgnoreCase(String title);
    
    /**
     * 查询所有激活的广告
     */
    List<Advert> findByIsActiveTrue();
    
    /**
     * 根据标题和激活状态查询
     */
    List<Advert> findByTitleContainingIgnoreCaseAndIsActiveTrue(String title);
    
    /**
     * 自定义查询：根据内容关键词查询
     */
    @Query("SELECT a FROM Advert a WHERE a.content LIKE %:keyword% AND a.isActive = true")
    List<Advert> findByContentKeyword(@Param("keyword") String keyword);
    
    /**
     * JSON查询：根据标签查询广告
     */
    @Query(value = "SELECT * FROM adverts WHERE tags::jsonb @> :tagJson AND is_active = true", nativeQuery = true)
    List<Advert> findByTag(@Param("tagJson") String tagJson);
    
    /**
     * JSON查询：根据扩展属性查询广告
     */
    @Query(value = "SELECT * FROM adverts WHERE extended_properties::jsonb ->> 'category' = :category AND is_active = true", nativeQuery = true)
    List<Advert> findByExtendedPropertyCategory(@Param("category") String category);
    
    /**
     * 根据分类ID查找广告
     */
    List<Advert> findByCategoryIdAndIsActiveTrue(Long categoryId);
    
    /**
     * 根据用户ID查找广告
     */
    List<Advert> findByUserIdAndIsActiveTrue(Long userId);
    
    /**
     * 根据状态查找广告
     */
    List<Advert> findByStatus(Advert.AdvertStatus status);
    
    /**
     * 根据优先级查找广告
     */
    List<Advert> findByIsActiveTrueOrderByPriorityDesc();
    
    /**
     * 查找投放中的广告
     */
    List<Advert> findByStatusAndIsActiveTrue(Advert.AdvertStatus status);
    
    /**
     * 根据预算范围查找广告
     */
    @Query("SELECT a FROM Advert a WHERE a.budget BETWEEN :minBudget AND :maxBudget AND a.isActive = true")
    List<Advert> findByBudgetRange(@Param("minBudget") Double minBudget, @Param("maxBudget") Double maxBudget);
    
    /**
     * 查找即将到期的广告
     */
    @Query("SELECT a FROM Advert a WHERE a.endTime BETWEEN :startTime AND :endTime AND a.isActive = true")
    List<Advert> findExpiringAdverts(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计各状态的广告数量
     */
    @Query("SELECT a.status, COUNT(a) FROM Advert a WHERE a.isActive = true GROUP BY a.status")
    List<Object[]> countAdvertsByStatus();
    
    /**
     * 统计各分类的广告数量
     */
    @Query("SELECT a.categoryId, COUNT(a) FROM Advert a WHERE a.isActive = true GROUP BY a.categoryId")
    List<Object[]> countAdvertsByCategory();
    
    /**
     * 统计各用户的广告数量
     */
    @Query("SELECT a.userId, COUNT(a) FROM Advert a WHERE a.isActive = true GROUP BY a.userId")
    List<Object[]> countAdvertsByUser();
    
    /**
     * 查找预算使用率最高的广告
     */
    @Query("SELECT a FROM Advert a WHERE a.budget > 0 AND a.isActive = true ORDER BY (a.spentAmount / a.budget) DESC")
    List<Advert> findHighBudgetUsageAdverts();
    
    /**
     * 查找ROI最高的广告（点击率/预算使用率）
     */
    @Query("SELECT a FROM Advert a WHERE a.budget > 0 AND a.isActive = true ORDER BY (a.spentAmount / a.budget) ASC")
    List<Advert> findHighRoiAdverts();
    
    /**
     * 多条件复杂查询
     */
    @Query("SELECT a FROM Advert a WHERE " +
           "(:categoryId IS NULL OR a.categoryId = :categoryId) AND " +
           "(:userId IS NULL OR a.userId = :userId) AND " +
           "(:status IS NULL OR a.status = :status) AND " +
           "(:minBudget IS NULL OR a.budget >= :minBudget) AND " +
           "(:maxBudget IS NULL OR a.budget <= :maxBudget) AND " +
           "a.isActive = true " +
           "ORDER BY a.priority DESC, a.createdTime DESC")
    List<Advert> findAdvertsByMultipleConditions(@Param("categoryId") Long categoryId,
                                                @Param("userId") Long userId,
                                                @Param("status") Advert.AdvertStatus status,
                                                @Param("minBudget") Double minBudget,
                                                @Param("maxBudget") Double maxBudget);
    
    /**
     * 查找热门广告（基于展示次数）
     */
    @Query("SELECT a FROM Advert a WHERE a.isActive = true ORDER BY a.priority DESC, a.createdTime DESC")
    List<Advert> findPopularAdverts();
    
    /**
     * 查找需要审核的广告
     */
    @Query("SELECT a FROM Advert a WHERE a.status = 'PENDING' AND a.isActive = true ORDER BY a.createdTime ASC")
    List<Advert> findPendingReviewAdverts();
    
    /**
     * 查找即将开始投放的广告
     */
    @Query("SELECT a FROM Advert a WHERE a.startTime BETWEEN :startTime AND :endTime AND a.status = 'APPROVED' AND a.isActive = true")
    List<Advert> findUpcomingAdverts(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计广告投放时间分布
     */
    @Query("SELECT CAST(a.startTime AS date) as startDate, COUNT(a) FROM Advert a " +
           "WHERE a.startTime IS NOT NULL AND a.isActive = true " +
           "GROUP BY CAST(a.startTime AS date) " +
           "ORDER BY startDate")
    List<Object[]> getAdvertStartDateDistribution();
    
    /**
     * 查找重复标题的广告
     */
    @Query("SELECT a.title, COUNT(a) FROM Advert a WHERE a.isActive = true GROUP BY a.title HAVING COUNT(a) > 1")
    List<Object[]> findDuplicateTitles();
    
    /**
     * 查找预算超支的广告
     */
    @Query("SELECT a FROM Advert a WHERE a.budget > 0 AND a.spentAmount > a.budget AND a.isActive = true")
    List<Advert> findOverBudgetAdverts();
    
    /**
     * 查找长时间未更新的广告
     */
    @Query("SELECT a FROM Advert a WHERE a.updatedTime < :threshold AND a.isActive = true")
    List<Advert> findStaleAdverts(@Param("threshold") LocalDateTime threshold);
    
    /**
     * 统计各时间段的广告创建数量
     */
    @Query("SELECT EXTRACT(HOUR FROM a.createdTime) as hour, COUNT(a) FROM Advert a " +
           "WHERE a.createdTime >= :startTime AND a.isActive = true " +
           "GROUP BY EXTRACT(HOUR FROM a.createdTime) " +
           "ORDER BY hour")
    List<Object[]> getAdvertCreationHourlyDistribution(@Param("startTime") LocalDateTime startTime);
    
    /**
     * 统计激活的广告数量
     */
    Long countByIsActiveTrue();
} 