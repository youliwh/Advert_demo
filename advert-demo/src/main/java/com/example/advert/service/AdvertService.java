package com.example.advert.service;

import com.example.advert.dao.AdvertRepository;
import com.example.advert.model.Advert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 广告服务层
 */
@Service
public class AdvertService {
    
    private final AdvertRepository advertRepository;
    
    @Autowired
    public AdvertService(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
    }
    
    /**
     * 查询所有广告
     */
    public List<Advert> findAll() {
        return advertRepository.findAll();
    }
    
    /**
     * 查询所有激活的广告
     */
    public List<Advert> findAllActive() {
        return advertRepository.findByIsActiveTrue();
    }
    
    /**
     * 根据ID查询广告
     */
    public Optional<Advert> findById(Long id) {
        return advertRepository.findById(id);
    }
    
    /**
     * 根据标题模糊查询
     */
    public List<Advert> findByTitle(String title) {
        return advertRepository.findByTitleContainingIgnoreCaseAndIsActiveTrue(title);
    }
    
    /**
     * 根据内容关键词查询
     */
    public List<Advert> findByContentKeyword(String keyword) {
        return advertRepository.findByContentKeyword(keyword);
    }
    
    /**
     * 根据标签查询
     */
    public List<Advert> findByTag(String tag) {
        String tagJson = "[\"" + tag + "\"]";
        return advertRepository.findByTag(tagJson);
    }
    
    /**
     * 根据扩展属性查询
     */
    public List<Advert> findByExtendedProperty(String property, String value) {
        return advertRepository.findByExtendedPropertyCategory(value);
    }
    
    /**
     * 保存广告
     */
    public Advert save(Advert advert) {
        if (advert.getId() == null) {
            // 新增时设置创建时间
            advert.setCreatedTime(LocalDateTime.now());
        }
        advert.setUpdatedTime(LocalDateTime.now());
        return advertRepository.save(advert);
    }
    
    /**
     * 更新广告
     */
    public Advert update(Long id, Advert advert) {
        Optional<Advert> existingAdvert = advertRepository.findById(id);
        if (existingAdvert.isPresent()) {
            Advert existing = existingAdvert.get();
            existing.setTitle(advert.getTitle());
            existing.setContent(advert.getContent());
            existing.setImageUrl(advert.getImageUrl());
            existing.setIsActive(advert.getIsActive());
            existing.setUpdatedTime(LocalDateTime.now());
            return advertRepository.save(existing);
        }
        return null;
    }
    
    /**
     * 删除广告（软删除）
     */
    public boolean deleteById(Long id) {
        Optional<Advert> advert = advertRepository.findById(id);
        if (advert.isPresent()) {
            Advert existing = advert.get();
            existing.setIsActive(false);
            existing.setUpdatedTime(LocalDateTime.now());
            advertRepository.save(existing);
            return true;
        }
        return false;
    }
    
    /**
     * 硬删除广告
     */
    public boolean hardDeleteById(Long id) {
        if (advertRepository.existsById(id)) {
            advertRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * 激活广告
     */
    public boolean activateById(Long id) {
        Optional<Advert> advert = advertRepository.findById(id);
        if (advert.isPresent()) {
            Advert existing = advert.get();
            existing.setIsActive(true);
            existing.setUpdatedTime(LocalDateTime.now());
            advertRepository.save(existing);
            return true;
        }
        return false;
    }
} 