package com.example.advert.dao;

import com.example.advert.model.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
} 