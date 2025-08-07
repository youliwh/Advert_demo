package com.example.advert.controller;

import com.example.advert.dao.AdvertRepository;
import com.example.advert.dao.AdvertStatsRepository;
import com.example.advert.dao.CategoryRepository;
import com.example.advert.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计控制器
 */
@RestController
@RequestMapping("/api/statistics")
@CrossOrigin(origins = "*")
public class StatisticsController {
    
    private final AdvertRepository advertRepository;
    private final AdvertStatsRepository advertStatsRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    
    @Autowired
    public StatisticsController(AdvertRepository advertRepository,
                              AdvertStatsRepository advertStatsRepository,
                              CategoryRepository categoryRepository,
                              UserRepository userRepository) {
        this.advertRepository = advertRepository;
        this.advertStatsRepository = advertStatsRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }
    
    /**
     * 获取广告状态统计
     */
    @GetMapping("/advert-status")
    public ResponseEntity<Map<String, Object>> getAdvertStatusStats() {
        List<Object[]> statusStats = advertRepository.countAdvertsByStatus();
        Map<String, Object> result = new HashMap<>();
        result.put("statusStats", statusStats);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 获取每日统计数据
     */
    @GetMapping("/daily-stats")
    public ResponseEntity<Map<String, Object>> getDailyStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Object[]> dailyStats = advertStatsRepository.getDailyStats(startDate, endDate);
        Map<String, Object> result = new HashMap<>();
        result.put("dailyStats", dailyStats);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 获取综合统计信息
     */
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> dashboard = new HashMap<>();
        
        // 基础统计
        dashboard.put("totalAdverts", advertRepository.count());
        dashboard.put("activeAdverts", advertRepository.countByIsActiveTrue());
        dashboard.put("totalUsers", userRepository.count());
        dashboard.put("activeUsers", userRepository.countActiveUsers());
        
        // 状态统计
        dashboard.put("statusStats", advertRepository.countAdvertsByStatus());
        
        // 今日统计
        LocalDate today = LocalDate.now();
        Long todayViews = advertStatsRepository.sumViewCountByDateRange(today, today);
        Long todayClicks = advertStatsRepository.sumClickCountByDateRange(today, today);
        dashboard.put("todayViews", todayViews != null ? todayViews : 0L);
        dashboard.put("todayClicks", todayClicks != null ? todayClicks : 0L);
        
        return ResponseEntity.ok(dashboard);
    }
} 