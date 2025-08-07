package com.example.advert.service;

import com.example.advert.dao.AdvertStatsRepository;
import com.example.advert.model.AdvertStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 广告统计服务层
 */
@Service
public class AdvertStatsService {
    
    private final AdvertStatsRepository advertStatsRepository;
    
    @Autowired
    public AdvertStatsService(AdvertStatsRepository advertStatsRepository) {
        this.advertStatsRepository = advertStatsRepository;
    }
    
    /**
     * 查询所有统计数据
     */
    public List<AdvertStats> findAll() {
        return advertStatsRepository.findAll();
    }
    
    /**
     * 根据ID查询统计数据
     */
    public Optional<AdvertStats> findById(Long id) {
        return advertStatsRepository.findById(id);
    }
    
    /**
     * 根据广告ID查询统计数据
     */
    public List<AdvertStats> findByAdvertId(Long advertId) {
        return advertStatsRepository.findByAdvertId(advertId);
    }
    
    /**
     * 根据日期范围查询统计数据
     */
    public List<AdvertStats> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return advertStatsRepository.findByStatsDateBetween(startDate, endDate);
    }
    
    /**
     * 查询今日统计数据
     */
    public List<AdvertStats> findTodayStats() {
        LocalDate today = LocalDate.now();
        return advertStatsRepository.findByStatsDate(today);
    }
    
    /**
     * 查询本周统计数据
     */
    public List<AdvertStats> findThisWeekStats() {
        LocalDate startOfWeek = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        return advertStatsRepository.findByStatsDateBetween(startOfWeek, endOfWeek);
    }
    
    /**
     * 查询本月统计数据
     */
    public List<AdvertStats> findThisMonthStats() {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);
        return advertStatsRepository.findByStatsDateBetween(startOfMonth, endOfMonth);
    }
    
    /**
     * 保存统计数据
     */
    public AdvertStats save(AdvertStats stats) {
        if (stats.getId() == null) {
            // 新增时设置创建时间
            stats.setCreatedTime(LocalDateTime.now());
        }
        stats.setUpdatedTime(LocalDateTime.now());
        return advertStatsRepository.save(stats);
    }
    
    /**
     * 更新统计数据
     */
    public AdvertStats update(Long id, AdvertStats stats) {
        Optional<AdvertStats> existingStats = advertStatsRepository.findById(id);
        if (existingStats.isPresent()) {
            AdvertStats existing = existingStats.get();
            existing.setAdvertId(stats.getAdvertId());
            existing.setStatsDate(stats.getStatsDate());
            existing.setViewCount(stats.getViewCount());
            existing.setClickCount(stats.getClickCount());
            existing.setCtr(stats.getCtr());
            existing.setUpdatedTime(LocalDateTime.now());
            return advertStatsRepository.save(existing);
        }
        return null;
    }
    
    /**
     * 根据ID删除统计数据
     */
    public boolean deleteById(Long id) {
        if (advertStatsRepository.existsById(id)) {
            advertStatsRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * 增加广告点击次数
     */
    public boolean incrementClick(Long advertId) {
        LocalDate today = LocalDate.now();
        Optional<AdvertStats> existingStats = advertStatsRepository.findByAdvertIdAndStatsDate(advertId, today);
        
        if (existingStats.isPresent()) {
            AdvertStats stats = existingStats.get();
            stats.setClickCount(stats.getClickCount() + 1);
            stats.setCtr(calculateCTR(stats.getViewCount(), stats.getClickCount()));
            stats.setUpdatedTime(LocalDateTime.now());
            advertStatsRepository.save(stats);
            return true;
        } else {
            // 创建新的统计数据
            AdvertStats newStats = new AdvertStats();
            newStats.setAdvertId(advertId);
            newStats.setStatsDate(today);
            newStats.setViewCount(0L);
            newStats.setClickCount(1L);
            newStats.setCtr(0.0);
            newStats.setCreatedTime(LocalDateTime.now());
            newStats.setUpdatedTime(LocalDateTime.now());
            advertStatsRepository.save(newStats);
            return true;
        }
    }
    
    /**
     * 增加广告浏览次数
     */
    public boolean incrementView(Long advertId) {
        LocalDate today = LocalDate.now();
        Optional<AdvertStats> existingStats = advertStatsRepository.findByAdvertIdAndStatsDate(advertId, today);
        
        if (existingStats.isPresent()) {
            AdvertStats stats = existingStats.get();
            stats.setViewCount(stats.getViewCount() + 1);
            stats.setCtr(calculateCTR(stats.getViewCount(), stats.getClickCount()));
            stats.setUpdatedTime(LocalDateTime.now());
            advertStatsRepository.save(stats);
            return true;
        } else {
            // 创建新的统计数据
            AdvertStats newStats = new AdvertStats();
            newStats.setAdvertId(advertId);
            newStats.setStatsDate(today);
            newStats.setViewCount(1L);
            newStats.setClickCount(0L);
            newStats.setCtr(0.0);
            newStats.setCreatedTime(LocalDateTime.now());
            newStats.setUpdatedTime(LocalDateTime.now());
            advertStatsRepository.save(newStats);
            return true;
        }
    }
    
    /**
     * 计算CTR（点击率）
     */
    private double calculateCTR(Long viewCount, Long clickCount) {
        if (viewCount == null || viewCount == 0) {
            return 0.0;
        }
        return (double) clickCount / viewCount * 100;
    }
    
    /**
     * 获取统计数据汇总
     */
    public Map<String, Object> getStatsSummary() {
        Map<String, Object> summary = new HashMap<>();
        
        // 今日总浏览量
        long todayViews = advertStatsRepository.sumTodayViews();
        summary.put("todayViews", todayViews);
        
        // 今日总点击量
        long todayClicks = advertStatsRepository.sumTodayClicks();
        summary.put("todayClicks", todayClicks);
        
        // 今日平均CTR
        double todayAvgCTR = advertStatsRepository.avgTodayCTR();
        summary.put("todayAvgCTR", todayAvgCTR);
        
        // 本周总浏览量
        long weekViews = advertStatsRepository.sumWeekViews();
        summary.put("weekViews", weekViews);
        
        // 本周总点击量
        long weekClicks = advertStatsRepository.sumWeekClicks();
        summary.put("weekClicks", weekClicks);
        
        // 本月总浏览量
        long monthViews = advertStatsRepository.sumMonthViews();
        summary.put("monthViews", monthViews);
        
        // 本月总点击量
        long monthClicks = advertStatsRepository.sumMonthClicks();
        summary.put("monthClicks", monthClicks);
        
        return summary;
    }
} 