package com.example.advert.dao;

import com.example.advert.model.AdvertStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 广告统计数据访问层
 */
@Repository
public interface AdvertStatsRepository extends JpaRepository<AdvertStats, Long> {
    
    /**
     * 根据广告ID和日期查找统计
     */
    Optional<AdvertStats> findByAdvertIdAndStatsDate(Long advertId, LocalDate statsDate);
    
    /**
     * 根据广告ID查找所有统计
     */
    List<AdvertStats> findByAdvertIdOrderByStatsDateDesc(Long advertId);
    
    /**
     * 根据日期查找统计
     */
    List<AdvertStats> findByStatsDate(LocalDate statsDate);
    
    /**
     * 根据日期范围查找统计
     */
    List<AdvertStats> findByStatsDateBetweenOrderByStatsDateDesc(LocalDate startDate, LocalDate endDate);
    
    /**
     * 根据广告ID和日期范围查找统计
     */
    List<AdvertStats> findByAdvertIdAndStatsDateBetweenOrderByStatsDateDesc(Long advertId, LocalDate startDate, LocalDate endDate);
    
    /**
     * 统计指定日期范围内的总展示次数
     */
    @Query("SELECT SUM(s.viewCount) FROM AdvertStats s WHERE s.statsDate BETWEEN :startDate AND :endDate")
    Long sumViewCountByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    /**
     * 统计指定日期范围内的总点击次数
     */
    @Query("SELECT SUM(s.clickCount) FROM AdvertStats s WHERE s.statsDate BETWEEN :startDate AND :endDate")
    Long sumClickCountByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    /**
     * 统计指定广告的总展示次数
     */
    @Query("SELECT SUM(s.viewCount) FROM AdvertStats s WHERE s.advertId = :advertId")
    Long sumViewCountByAdvertId(@Param("advertId") Long advertId);
    
    /**
     * 统计指定广告的总点击次数
     */
    @Query("SELECT SUM(s.clickCount) FROM AdvertStats s WHERE s.advertId = :advertId")
    Long sumClickCountByAdvertId(@Param("advertId") Long advertId);
    
    /**
     * 计算指定广告的平均点击率
     */
    @Query("SELECT AVG(s.ctr) FROM AdvertStats s WHERE s.advertId = :advertId AND s.viewCount > 0")
    Double calculateAverageCtrByAdvertId(@Param("advertId") Long advertId);
    
    /**
     * 查找点击率最高的广告统计
     */
    @Query("SELECT s FROM AdvertStats s WHERE s.viewCount > 0 ORDER BY s.ctr DESC")
    List<AdvertStats> findTopCtrStats();
    
    /**
     * 查找展示次数最多的广告统计
     */
    @Query("SELECT s FROM AdvertStats s ORDER BY s.viewCount DESC")
    List<AdvertStats> findTopViewStats();
    
    /**
     * 查找点击次数最多的广告统计
     */
    @Query("SELECT s FROM AdvertStats s ORDER BY s.clickCount DESC")
    List<AdvertStats> findTopClickStats();
    
    /**
     * 按日期统计总展示和点击次数
     */
    @Query("SELECT s.statsDate, SUM(s.viewCount), SUM(s.clickCount), AVG(s.ctr) " +
           "FROM AdvertStats s " +
           "WHERE s.statsDate BETWEEN :startDate AND :endDate " +
           "GROUP BY s.statsDate " +
           "ORDER BY s.statsDate")
    List<Object[]> getDailyStats(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    /**
     * 按广告ID统计总展示和点击次数
     */
    @Query("SELECT s.advertId, SUM(s.viewCount), SUM(s.clickCount), AVG(s.ctr) " +
           "FROM AdvertStats s " +
           "WHERE s.statsDate BETWEEN :startDate AND :endDate " +
           "GROUP BY s.advertId " +
           "ORDER BY SUM(s.viewCount) DESC")
    List<Object[]> getAdvertStats(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    /**
     * 查找指定日期范围内表现最好的广告
     */
    @Query("SELECT s.advertId, SUM(s.viewCount) as totalViews, SUM(s.clickCount) as totalClicks, " +
           "AVG(s.ctr) as avgCtr " +
           "FROM AdvertStats s " +
           "WHERE s.statsDate BETWEEN :startDate AND :endDate " +
           "GROUP BY s.advertId " +
           "HAVING SUM(s.viewCount) > 0 " +
           "ORDER BY avgCtr DESC")
    List<Object[]> findBestPerformingAdverts(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    /**
     * 统计各时间段的展示和点击趋势
     */
    @Query("SELECT EXTRACT(HOUR FROM s.createdTime) as hour, " +
           "SUM(s.viewCount) as totalViews, " +
           "SUM(s.clickCount) as totalClicks " +
           "FROM AdvertStats s " +
           "WHERE s.statsDate BETWEEN :startDate AND :endDate " +
           "GROUP BY EXTRACT(HOUR FROM s.createdTime) " +
           "ORDER BY hour")
    List<Object[]> getHourlyTrends(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    /**
     * 查找异常数据（点击率异常高的统计）
     */
    @Query("SELECT s FROM AdvertStats s WHERE s.ctr > :threshold AND s.viewCount > 0 ORDER BY s.ctr DESC")
    List<AdvertStats> findAnomalousStats(@Param("threshold") Double threshold);
    
    /**
     * 计算指定广告的增长率
     */
    @Query("SELECT " +
           "(SELECT SUM(s2.viewCount) FROM AdvertStats s2 WHERE s2.advertId = :advertId AND s2.statsDate BETWEEN :currentStart AND :currentEnd) as currentViews, " +
           "(SELECT SUM(s3.viewCount) FROM AdvertStats s3 WHERE s3.advertId = :advertId AND s3.statsDate BETWEEN :previousStart AND :previousEnd) as previousViews " +
           "FROM AdvertStats s WHERE s.advertId = :advertId")
    List<Object[]> calculateGrowthRate(@Param("advertId") Long advertId,
                                @Param("currentStart") LocalDate currentStart,
                                @Param("currentEnd") LocalDate currentEnd,
                                @Param("previousStart") LocalDate previousStart,
                                @Param("previousEnd") LocalDate previousEnd);
    
    /**
     * 查找连续多天无数据的广告
     */
    @Query("SELECT DISTINCT s.advertId FROM AdvertStats s " +
           "WHERE s.statsDate < :date AND s.advertId NOT IN " +
           "(SELECT s2.advertId FROM AdvertStats s2 WHERE s2.statsDate >= :date)")
    List<Long> findInactiveAdverts(@Param("date") LocalDate date);
    
    /**
     * 根据广告ID查找统计数据
     */
    List<AdvertStats> findByAdvertId(Long advertId);
    
    /**
     * 根据日期范围查找统计数据
     */
    List<AdvertStats> findByStatsDateBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * 统计今日总浏览量
     */
    @Query("SELECT COALESCE(SUM(s.viewCount), 0) FROM AdvertStats s WHERE s.statsDate = :today")
    Long sumTodayViews(@Param("today") LocalDate today);
    
    /**
     * 统计今日总点击量
     */
    @Query("SELECT COALESCE(SUM(s.clickCount), 0) FROM AdvertStats s WHERE s.statsDate = :today")
    Long sumTodayClicks(@Param("today") LocalDate today);
    
    /**
     * 计算今日平均CTR
     */
    @Query("SELECT COALESCE(AVG(s.ctr), 0.0) FROM AdvertStats s WHERE s.statsDate = :today AND s.viewCount > 0")
    Double avgTodayCTR(@Param("today") LocalDate today);
    
    /**
     * 统计本周总浏览量
     */
    @Query("SELECT COALESCE(SUM(s.viewCount), 0) FROM AdvertStats s WHERE s.statsDate BETWEEN :startOfWeek AND :endOfWeek")
    Long sumWeekViews(@Param("startOfWeek") LocalDate startOfWeek, @Param("endOfWeek") LocalDate endOfWeek);
    
    /**
     * 统计本周总点击量
     */
    @Query("SELECT COALESCE(SUM(s.clickCount), 0) FROM AdvertStats s WHERE s.statsDate BETWEEN :startOfWeek AND :endOfWeek")
    Long sumWeekClicks(@Param("startOfWeek") LocalDate startOfWeek, @Param("endOfWeek") LocalDate endOfWeek);
    
    /**
     * 统计本月总浏览量
     */
    @Query("SELECT COALESCE(SUM(s.viewCount), 0) FROM AdvertStats s WHERE s.statsDate BETWEEN :startOfMonth AND :endOfMonth")
    Long sumMonthViews(@Param("startOfMonth") LocalDate startOfMonth, @Param("endOfMonth") LocalDate endOfMonth);
    
    /**
     * 统计本月总点击量
     */
    @Query("SELECT COALESCE(SUM(s.clickCount), 0) FROM AdvertStats s WHERE s.statsDate BETWEEN :startOfMonth AND :endOfMonth")
    Long sumMonthClicks(@Param("startOfMonth") LocalDate startOfMonth, @Param("endOfMonth") LocalDate endOfMonth);
    
    /**
     * 统计今日总浏览量 - 默认方法
     */
    default Long sumTodayViews() {
        return sumTodayViews(LocalDate.now());
    }
    
    /**
     * 统计今日总点击量 - 默认方法
     */
    default Long sumTodayClicks() {
        return sumTodayClicks(LocalDate.now());
    }
    
    /**
     * 计算今日平均CTR - 默认方法
     */
    default Double avgTodayCTR() {
        return avgTodayCTR(LocalDate.now());
    }
    
    /**
     * 统计本周总浏览量 - 默认方法
     */
    default Long sumWeekViews() {
        LocalDate startOfWeek = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        return sumWeekViews(startOfWeek, endOfWeek);
    }
    
    /**
     * 统计本周总点击量 - 默认方法
     */
    default Long sumWeekClicks() {
        LocalDate startOfWeek = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        return sumWeekClicks(startOfWeek, endOfWeek);
    }
    
    /**
     * 统计本月总浏览量 - 默认方法
     */
    default Long sumMonthViews() {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);
        return sumMonthViews(startOfMonth, endOfMonth);
    }
    
    /**
     * 统计本月总点击量 - 默认方法
     */
    default Long sumMonthClicks() {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);
        return sumMonthClicks(startOfMonth, endOfMonth);
    }
} 