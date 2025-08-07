package com.example.advert.controller;

import com.example.advert.model.AdvertStats;
import com.example.advert.service.AdvertStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 广告统计控制器
 */
@RestController
@RequestMapping("/api/advert-stats")
@CrossOrigin(origins = "*")
public class AdvertStatsController {
    
    private final AdvertStatsService advertStatsService;
    
    @Autowired
    public AdvertStatsController(AdvertStatsService advertStatsService) {
        this.advertStatsService = advertStatsService;
    }
    
    /**
     * 获取所有广告统计数据
     */
    @GetMapping
    public ResponseEntity<List<AdvertStats>> getAllAdvertStats() {
        List<AdvertStats> stats = advertStatsService.findAll();
        return ResponseEntity.ok(stats);
    }
    
    /**
     * 根据ID获取广告统计数据
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdvertStats> getAdvertStatsById(@PathVariable Long id) {
        Optional<AdvertStats> stats = advertStatsService.findById(id);
        return stats.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * 根据广告ID获取统计数据
     */
    @GetMapping("/advert/{advertId}")
    public ResponseEntity<List<AdvertStats>> getStatsByAdvertId(@PathVariable Long advertId) {
        List<AdvertStats> stats = advertStatsService.findByAdvertId(advertId);
        return ResponseEntity.ok(stats);
    }
    
    /**
     * 根据日期范围获取统计数据
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<AdvertStats>> getStatsByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<AdvertStats> stats = advertStatsService.findByDateRange(startDate, endDate);
        return ResponseEntity.ok(stats);
    }
    
    /**
     * 获取今日统计数据
     */
    @GetMapping("/today")
    public ResponseEntity<List<AdvertStats>> getTodayStats() {
        List<AdvertStats> stats = advertStatsService.findTodayStats();
        return ResponseEntity.ok(stats);
    }
    
    /**
     * 获取本周统计数据
     */
    @GetMapping("/this-week")
    public ResponseEntity<List<AdvertStats>> getThisWeekStats() {
        List<AdvertStats> stats = advertStatsService.findThisWeekStats();
        return ResponseEntity.ok(stats);
    }
    
    /**
     * 获取本月统计数据
     */
    @GetMapping("/this-month")
    public ResponseEntity<List<AdvertStats>> getThisMonthStats() {
        List<AdvertStats> stats = advertStatsService.findThisMonthStats();
        return ResponseEntity.ok(stats);
    }
    
    /**
     * 获取统计数据汇总
     */
    @GetMapping("/summary")
    public ResponseEntity<Object> getStatsSummary() {
        return ResponseEntity.ok(advertStatsService.getStatsSummary());
    }
    
    /**
     * 创建新的统计数据
     */
    @PostMapping
    public ResponseEntity<AdvertStats> createAdvertStats(@RequestBody AdvertStats stats) {
        AdvertStats savedStats = advertStatsService.save(stats);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStats);
    }
    
    /**
     * 更新统计数据
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdvertStats> updateAdvertStats(@PathVariable Long id, @RequestBody AdvertStats stats) {
        AdvertStats updatedStats = advertStatsService.update(id, stats);
        if (updatedStats != null) {
            return ResponseEntity.ok(updatedStats);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * 删除统计数据
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvertStats(@PathVariable Long id) {
        boolean deleted = advertStatsService.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * 增加广告点击次数
     */
    @PostMapping("/{advertId}/increment-click")
    public ResponseEntity<Void> incrementClick(@PathVariable Long advertId) {
        boolean incremented = advertStatsService.incrementClick(advertId);
        if (incremented) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * 增加广告浏览次数
     */
    @PostMapping("/{advertId}/increment-view")
    public ResponseEntity<Void> incrementView(@PathVariable Long advertId) {
        boolean incremented = advertStatsService.incrementView(advertId);
        if (incremented) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * 健康检查接口
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("AdvertStats Service is running!");
    }
} 