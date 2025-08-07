package com.example.advert.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 广告统计实体类
 */
@Entity
@Table(name = "advert_stats")
public class AdvertStats {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "advert_id", nullable = false)
    private Long advertId;
    
    @Column(name = "stats_date", nullable = false)
    private LocalDate statsDate;
    
    @Column(name = "view_count")
    private Long viewCount = 0L;
    
    @Column(name = "click_count")
    private Long clickCount = 0L;
    
    @Column(name = "ctr")
    private Double ctr = 0.0; // Click Through Rate 点击率
    
    @Column(name = "created_time")
    private LocalDateTime createdTime;
    
    @Column(name = "updated_time")
    private LocalDateTime updatedTime;
    
    // 构造函数
    public AdvertStats() {}
    
    public AdvertStats(Long advertId, LocalDate statsDate) {
        this.advertId = advertId;
        this.statsDate = statsDate;
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
    }
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getAdvertId() {
        return advertId;
    }
    
    public void setAdvertId(Long advertId) {
        this.advertId = advertId;
    }
    
    public LocalDate getStatsDate() {
        return statsDate;
    }
    
    public void setStatsDate(LocalDate statsDate) {
        this.statsDate = statsDate;
    }
    
    public Long getViewCount() {
        return viewCount;
    }
    
    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }
    
    public Long getClickCount() {
        return clickCount;
    }
    
    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }
    
    public Double getCtr() {
        return ctr;
    }
    
    public void setCtr(Double ctr) {
        this.ctr = ctr;
    }
    
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
    
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
    
    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }
    
    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
    
    // 计算点击率
    public void calculateCtr() {
        if (viewCount > 0) {
            this.ctr = (double) clickCount / viewCount * 100;
        }
    }
    
    // 增加展示次数
    public void incrementViewCount() {
        this.viewCount++;
        calculateCtr();
    }
    
    // 增加点击次数
    public void incrementClickCount() {
        this.clickCount++;
        calculateCtr();
    }
    
    @PrePersist
    protected void onCreate() {
        createdTime = LocalDateTime.now();
        updatedTime = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedTime = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "AdvertStats{" +
                "id=" + id +
                ", advertId=" + advertId +
                ", statsDate=" + statsDate +
                ", viewCount=" + viewCount +
                ", clickCount=" + clickCount +
                ", ctr=" + ctr +
                '}';
    }
} 