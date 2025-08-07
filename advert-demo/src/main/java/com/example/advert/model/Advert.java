package com.example.advert.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 广告实体类
 */
@Entity
@Table(name = "adverts")
public class Advert {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "image_url", length = 500)
    private String imageUrl;
    
    @Column(name = "created_time")
    private LocalDateTime createdTime;
    
    @Column(name = "updated_time")
    private LocalDateTime updatedTime;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "category_id")
    private Long categoryId;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AdvertStatus status = AdvertStatus.DRAFT;
    
    @Column(name = "priority")
    private Integer priority = 0;
    
    @Column(name = "start_time")
    private LocalDateTime startTime;
    
    @Column(name = "end_time")
    private LocalDateTime endTime;
    
    @Column(name = "budget")
    private Double budget = 0.0;
    
    @Column(name = "spent_amount")
    private Double spentAmount = 0.0;
    
    // JSON字段示例：广告扩展属性
    @Column(name = "extended_properties", columnDefinition = "jsonb")
    private String extendedProperties;
    
    // JSON字段示例：广告标签
    @Column(name = "tags", columnDefinition = "jsonb")
    private String tags;
    
    // 广告状态枚举
    public enum AdvertStatus {
        DRAFT,      // 草稿
        PENDING,    // 待审核
        APPROVED,   // 已审核
        REJECTED,   // 已拒绝
        ACTIVE,     // 投放中
        PAUSED,     // 暂停
        COMPLETED   // 已完成
    }
    
    // 构造函数
    public Advert() {}
    
    public Advert(String title, String content, String imageUrl) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
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
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public Long getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public AdvertStatus getStatus() {
        return status;
    }
    
    public void setStatus(AdvertStatus status) {
        this.status = status;
    }
    
    public Integer getPriority() {
        return priority;
    }
    
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public Double getBudget() {
        return budget;
    }
    
    public void setBudget(Double budget) {
        this.budget = budget;
    }
    
    public Double getSpentAmount() {
        return spentAmount;
    }
    
    public void setSpentAmount(Double spentAmount) {
        this.spentAmount = spentAmount;
    }
    
    public String getExtendedProperties() {
        return extendedProperties;
    }
    
    public void setExtendedProperties(String extendedProperties) {
        this.extendedProperties = extendedProperties;
    }
    
    public String getTags() {
        return tags;
    }
    
    public void setTags(String tags) {
        this.tags = tags;
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
        return "Advert{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", isActive=" + isActive +
                '}';
    }
} 